/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.email.CorreoElectronico;
import ec.com.codesoft.codefaclite.utilidades.email.PropiedadCorreo;
import ec.com.codesoft.codefaclite.utilidades.email.SmtpNoExisteException;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

/**
 *
 * @author Carlos
 */
public class CorreoCodefac {
    private CorreoElectronico correoElectronico;
    
    /*public abstract String getMensaje();
    public abstract String getTitulo();
    public abstract Map<String,String> getPathFiles();
    public abstract List<String> getDestinatorios();*/
    
    public Boolean modoSession;
    

    public CorreoCodefac() 
    {
        modoSession=false;
    }
    
    //public void enviarCorreo(Empresa empresa,String mensaje, String subject, List<String> destinatorios, Map<String, String> pathFiles) throws ExcepcionCorreoCodefac
    //{
        
    //}
    
    /**
     * TODO: Mejorar esta parte para parametrizar mejor desde donde tengo que enviar los correos
     * @param empresa
     * @param mensaje
     * @param titulo
     * @param destinatorios
     * @param pathFiles key=alias_nombre , value=path archivo
     * @throws ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.CorreoCodefac.ExcepcionCorreoCodefac 
     */
    public void enviarCorreo(Empresa empresa,String mensaje, String titulo, List<String> destinatorios, Map<String, String> pathFiles) throws ExcepcionCorreoCodefac
    {
        try
        {
            //ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.CORREO_USUARIO);
            String correo=ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.CORREO_USUARIO,empresa).getValor();
            

            String clave=ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.CORREO_CLAVE,empresa).getValor();
            //Obtener clave desencriptada
            //clave=UtilidadesEncriptar.desencriptar(clave,ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
            
            //Construir los datos de las propiedades si existen
            String smtpHost=ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.SMTP_HOST,empresa).getValor();
            String smtpPort=ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.SMTP_PORT,empresa).getValor();
            
            //PropiedadCorreo propiedadCorreo=null;
            //if(!smtpHost.isEmpty() && !smtpPort.isEmpty())
            //{
            //    propiedadCorreo=new PropiedadCorreo(smtpHost,new Integer(smtpPort));
            //} 
            
            //Agregar el nombre de la empresa o la razon social
            String alias = obtenerNombreEmpresaCorreo(empresa);
            
            //if(modoSession)
            //{
                //Si no existe la referencia previamente creada creo uno nuevo
            //    if (correoElectronico == null) {
            //        correoElectronico = new CorreoElectronico(correo, alias, clave, mensaje, destinatorios,titulo, propiedadCorreo);
            //        correoElectronico.sessionLoteActivo=true;
            //    }
            //}
            //else
            //{
                //Si tiene esta opcion siempre creo una nueva conexion
            //    correoElectronico = new CorreoElectronico(correo, alias, clave, mensaje, destinatorios, titulo, propiedadCorreo);
            //}
            
            
            enviarCorreo(correo, clave, smtpHost, smtpPort, alias, mensaje, titulo, destinatorios, pathFiles);
            //correoElectronico.setPathFiles(getPathFiles());
            
            try
            {
            //    correoElectronico.sendMail(mensaje, destinatorios, titulo, pathFiles);
            }catch(RuntimeException e)
            {
                e.printStackTrace();
                throw new ExcepcionCorreoCodefac(e.getMessage());
            //} catch (MessagingException ex) {
            //    Logger.getLogger(CorreoCodefac.class.getName()).log(Level.SEVERE, null, ex);
            //    throw new ExcepcionCorreoCodefac(ex.getMessage());
            }
            
            
        }catch(RemoteException ex)
        {
            Logger.getLogger(CorreoCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CorreoCodefac.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCorreoCodefac(ex.getMessage());
        }

        
    }
    
        
    //Metodo que me permite enviar un correo directamente desde una cuenta de correo del sistema
    public void enviarCorreoCuentaSistema(Empresa empresa,String mensaje, String titulo, List<String> destinatorios, Map<String, String> pathFiles) throws ExcepcionCorreoCodefac
    {
        String alias = obtenerNombreEmpresaCorreo(empresa);
        String correo=ParametrosSistemaCodefac.CORREO_DEFECTO_USUARIO;
        String claveEncriptada=ParametrosSistemaCodefac.CORREO_DEFECTO_CLAVE;
        String smtp=ParametrosSistemaCodefac.CORREO_DEFECTO_HOST;
        //String smtp="465";
        //String puerto=ParametrosSistemaCodefac.CORREO_DEFECTO_PUERTO;
        String puerto="465";
        
        enviarCorreo(correo, claveEncriptada, smtp, puerto, alias, mensaje, titulo, destinatorios, pathFiles);
    }
    
    public void enviarCorreo(String correo,String claveEncriptada,String smtpHost,String smtpPort,String nombreEmpresa,String mensaje, String titulo, List<String> destinatorios, Map<String, String> pathFiles) throws ExcepcionCorreoCodefac
    {
        try
        {
            //ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.CORREO_USUARIO);
            //String correo=ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.CORREO_USUARIO,empresa).getValor();
            

            //String clave=ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.CORREO_CLAVE,empresa).getValor();
            //Obtener clave desencriptada
            String clave=UtilidadesEncriptar.desencriptar(claveEncriptada,ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
            
            //Construir los datos de las propiedades si existen
            //String smtpHost=ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.SMTP_HOST,empresa).getValor();
            //String smtpPort=ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.SMTP_PORT,empresa).getValor();
            
            PropiedadCorreo propiedadCorreo=null;
            if(!smtpHost.isEmpty() && !smtpPort.isEmpty())
            {
                propiedadCorreo=new PropiedadCorreo(smtpHost,new Integer(smtpPort));
            } 
            
            //Agregar el nombre de la empresa o la razon social
            //String alias=empresa.getNombreLegal();
            String alias=nombreEmpresa;
            //if(alias==null || alias.trim().isEmpty())
            //{
            //    alias=empresa.getRazonSocial();
            //}
            
            if(modoSession)
            {
                //Si no existe la referencia previamente creada creo uno nuevo
                if (correoElectronico == null) {
                    correoElectronico = new CorreoElectronico(correo, alias, clave, mensaje, destinatorios,titulo, propiedadCorreo);
                    correoElectronico.sessionLoteActivo=true;
                }
            }
            else
            {
                //Si tiene esta opcion siempre creo una nueva conexion
                correoElectronico = new CorreoElectronico(correo, alias, clave, mensaje, destinatorios, titulo, propiedadCorreo);
            }
            
            
           
            //correoElectronico.setPathFiles(getPathFiles());
            
            try
            {
                correoElectronico.sendMail(mensaje, destinatorios, titulo, pathFiles);
            }catch(RuntimeException e)
            {
                e.printStackTrace();
                throw new ExcepcionCorreoCodefac(e.getMessage());
            //} catch (MessagingException ex) {
            //    Logger.getLogger(CorreoCodefac.class.getName()).log(Level.SEVERE, null, ex);
            //    throw new ExcepcionCorreoCodefac(ex.getMessage());
            } catch (SmtpNoExisteException ex) {
                Logger.getLogger(CorreoCodefac.class.getName()).log(Level.SEVERE, null, ex);
                throw new ExcepcionCorreoCodefac(ex.getMessage());
            }catch (AuthenticationFailedException ex) {
                Logger.getLogger(CorreoCodefac.class.getName()).log(Level.SEVERE, null, ex);
                throw new ExcepcionCorreoCodefac("Error de autentificación de las credenciales del correo configurado en el sistema");
            }
            
            
        }catch(RemoteException ex)
        {
            Logger.getLogger(CorreoCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CorreoCodefac.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCorreoCodefac(ex.getMessage());
        }

        
    }

    
    private String obtenerNombreEmpresaCorreo(Empresa empresa)
    {
        String alias = empresa.getNombreLegal();
        if (alias == null || alias.trim().isEmpty()) {
            alias = empresa.getRazonSocial();
        }
        return alias;
    }

    public CorreoElectronico getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(CorreoElectronico correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Boolean getModoSession() {
        return modoSession;
    }

    public void setModoSession(Boolean modoSession) {
        this.modoSession = modoSession;
    }
    
    
    
    
    public class ExcepcionCorreoCodefac extends Exception {

        public ExcepcionCorreoCodefac(String message) {
            super(message);
        }
    
    }
    
    public void cerrarSesionCodefacCorreo()
    {
        if(correoElectronico!=null)
        {
            correoElectronico.cerrarSession();
        }
    }
    
}
