/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.email;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author
 */
public class CorreoElectronico {

    /**
     * Es el nombre alterno para que le llegue en vez del nombre del emisor al cliente
     * Nota: Esto por lo general puede ser el nombre de la empresa
     */
    private String alias;
    private String usuario;
    private String clave;
    private PropiedadCorreo propiedadCorreo;

    private String mensaje;
    private List<String> to;
    private String subject;
    private Map<String,String> pathFiles;

    /**
     * TODO: Hacer que el constructor solo tengo los datos necesarios para iniciar la sesion
     * @param usuario
     * @param alias
     * @param clave
     * @param mensaje
     * @param to
     * @param subject
     * @param propiedadCorreo 
     */

    public CorreoElectronico(String usuario,String alias, String clave, String mensaje, List<String> to, String subject,PropiedadCorreo propiedadCorreo) {
        this.usuario = usuario;
        this.alias=alias;
        this.clave = clave;
        this.mensaje = mensaje;
        this.to = to;
        this.subject = subject;
        this.pathFiles=new HashMap<>();
        setearPropiedad(propiedadCorreo);
    }
    
    public CorreoElectronico(String usuario,String alias, String clave, String mensaje, List<String> to, String subject,Map<String,String> pathFiles,PropiedadCorreo propiedadCorreo) {
        this.usuario = usuario;
        this.alias=alias;
        this.clave = clave;
        this.mensaje = mensaje;
        this.to = to;
        this.subject = subject;
        this.pathFiles=pathFiles;
        setearPropiedad(propiedadCorreo);
    }
    
    public void setearPropiedad(PropiedadCorreo propiedadCorreo)
    {
        if(propiedadCorreo==null)
        {
            //Si no existe las propiedades trata de buscar las propiedades por defecto
            PropiedadCorreo propiedadPorDefecto=PropiedadCorreo.obtenerPropiedadesPorDefecto(this.usuario);
            if(propiedadPorDefecto!=null)
            {
                this.propiedadCorreo=propiedadPorDefecto;
            }
            
        }
        else
        {
            this.propiedadCorreo=propiedadCorreo;
        }
    }
    
    private void validar()throws SmtpNoExisteException
    {
                //Verificar si existe un servidor smtp registrado
        if(propiedadCorreo==null)
            throw new SmtpNoExisteException("No existe servidor smtp");
    }
    
    private Properties crearPropiedadesConexion()
    {
        Properties props = new Properties();
        if(propiedadCorreo.getPort().toString().equals("465"))
        {            
            props.put("mail.smtps.host", propiedadCorreo.getHost());
            props.put("mail.smtps.auth", "true");
            props.put("mail.transport.protocol", "smtps");
            props.put("mail.smtps.ssl.protocols", "TLSv1.1 TLSv1.2");
        }
        else
        {
            props.put("mail.smtp.ssl.trust", "*");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host",propiedadCorreo.getHost());
            props.put("mail.smtp.port",propiedadCorreo.getPort().toString());
            props.put("mail.transport.protocol", "smtp");
            //props.put("mail.smtp.socketFactory.fallback", "true");
            //TODO: Sin este código no funcionaba para hostings como Godaddy
            // Accept only TLS 1.1 and 1.2
            props.setProperty("mail.smtp.ssl.protocols", "TLSv1.1 TLSv1.2");
        }
        System.out.println("====>PROPIEDADES EMAIL <===========");
        System.out.println(props);
        return props;
    }
    
    private Session obtenerSession(Properties props)
    {
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, clave);
            }
        });
        
        //session.setDebug(true);
        return session;
    }
    
    private Message crearMensaje(Session session) throws MessagingException, UnsupportedEncodingException 
    {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(usuario, alias));

        /**
         * Agregar varios destinatarios
         */
        String correosStr = String.join(",", to);
        System.out.println(correosStr);

        //InternetAddress.parse(correosStr);
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(correosStr));

        message.setSubject(subject);
        Multipart multipart = new MimeMultipart();

        // Agregar Archivos
        for (Map.Entry<String, String> entry : pathFiles.entrySet()) {
            BodyPart messageBodyPart = new MimeBodyPart();
            //String filename = "E:\\ejemplo3.xml";
            DataSource source = new FileDataSource(entry.getValue());
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(entry.getKey());
            multipart.addBodyPart(messageBodyPart);
        }

        /**
         * Habilitar contenido html
         */
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(mensaje, "text/html; charset=utf-8");

        multipart.addBodyPart(htmlPart);

        //message.setContent(multipart);
        message.setContent(multipart);
        return message;
    }
    
    public void sendMail(String mensaje, List<String> to, String subject,Map<String,String> pathFiles) throws AuthenticationFailedException, MessagingException, SmtpNoExisteException {
        this.mensaje=mensaje;
        this.to=to;
        this.subject=subject;
        this.pathFiles=pathFiles;
        sendMail();
    }
    

    @Deprecated
    public void sendMail() throws AuthenticationFailedException, MessagingException, SmtpNoExisteException {
        
        //Validacion que exista un destinatario antes de enviar un correo
        if(to==null || to.size()==0)
        {
            return;
        }
        
        if(to.get(0).trim().isEmpty())
        {
            return;
        }
        
        try {
            Boolean crearNuevaConexionEmail=true;
            
            if(sessionLoteActivo)
            {
               if(transport!=null)
               {
                   crearNuevaConexionEmail=false;
               }
            }

            
            if(crearNuevaConexionEmail)
            {
                validar();
                Properties props = crearPropiedadesConexion();
                session = obtenerSession(props);
                transport = crearTransporteMensaje(session);
            }
            
            Message message = crearMensaje(session);
            
            transport.sendMessage(message,
                message.getRecipients(Message.RecipientType.TO));
            
            if (!sessionLoteActivo) {
                transport.close();
            }
                        
            //transport.close();

        } catch (AuthenticationFailedException e) {
            throw e;
        } catch (MessagingException ex) {
            throw ex;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CorreoElectronico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Transport crearTransporteMensaje(Session session) throws NoSuchProviderException, MessagingException
    {
        Transport transport = session.getTransport();

        transport.connect(propiedadCorreo.getHost(), propiedadCorreo.getPort(), usuario, clave);
        
        //transport.close();
        
        return transport;
    }
    
    
    public Map<String, String> getPathFiles() {
        return pathFiles;
    }

    public void setPathFiles(Map<String, String> pathFiles) {
        this.pathFiles = pathFiles;
    }

    /**
     * Crear un parametro general para abrirUnaInstanciaParaEnviarVarios Correos
     * TODO: Tomar en cuenta que si se tiene varias instancias usando el computador puede generar problemas
     * TODO: Buscar otra forma para procesa en especie de lote de varios mensajes
     */
    private Transport transport;
    private Session session;
    
    public Boolean sessionLoteActivo=false;
    
    
    public void cerrarSession()
    {
        if(transport!=null)
        {
            try {
                transport.close();
                transport=null;
                session=null;
            } catch (MessagingException ex) {
                Logger.getLogger(CorreoElectronico.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
     
    
    

}
