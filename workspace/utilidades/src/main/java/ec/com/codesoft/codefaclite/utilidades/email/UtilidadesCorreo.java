/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.email;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

/**
 *
 * @author CARLOS_CODESOFT
 */
public abstract class UtilidadesCorreo {
    
    /**
     * TODO: Parametrizar los mensajes
     * @param usuario
     * @param password
     * @param smtpHost
     * @param puerto
     * @throws Exception 
     */
    public static void verificarCredencialesCorreoCodefa(String usuario,String password,String smtpHost,Integer puerto)  throws Exception
    {
        //Validaciones iniciales
        if(smtpHost==null || smtpHost.trim().isEmpty())
        {
            throw new Exception("No se puede validar sin el Host Smtp");
        }
        
        if(password==null || password.trim().isEmpty())
        {
            throw new Exception("No se puede validar sin la clave");
        }
        
        if(usuario==null || usuario.trim().isEmpty())
        {
            throw new Exception("No se puede validar sin el usaurio");
        }
        
        if(puerto==null)
        {
            throw new Exception("No se puede validar sin un puerto");
        }
        
        try {
            List<String> correos = new ArrayList<String>();
            correos.add(usuario);
            String desc = "Bienvenido a Codefac-Lite. <br>"
                    + "Estimado/a usuario le informamos que su cuenta en Codefac-Lite ha sido activada exitosamente. Ahora ya puedes aprovechar los beneficios de nuestro sistema de facturación electrónica.\n"
                    + "<br><br> <b>NOTA.- Este mensaje fue enviado automáticamente por el sistema, por favor no responda a este correo.</b>";
            
            PropiedadCorreo propiedadCorreo=new PropiedadCorreo(smtpHost, puerto);
            
            CorreoElectronico correoElectronico = new CorreoElectronico(usuario, "Sistema Codefac", new String(password), desc, correos, "Notificación Codefac", propiedadCorreo);
            correoElectronico.sendMail();
            //TODO: Verificar si se va a dar uso de esta funcionalidad
            //TODO: Agregar una variable para la informacion del consumidor final
            //configurarCorreoDeConsumidorFinal();
            //DialogoCodefac.mensaje("Exito","El correo y la clave son correctos",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (AuthenticationFailedException ex) {
            ex.printStackTrace();
            System.out.println("Fallo al autentificar el usuario");
            //getTxtPasswordCorreo().setText("");
            //DialogoCodefac.mensaje("Error Correo", "Las credenciales de su correo son incorrectas", DialogoCodefac.MENSAJE_INCORRECTO);
            throw new Exception("Fallo al autentificar el usuario");
        } catch (MessagingException ex) {
            ex.printStackTrace();
            Logger.getLogger(UtilidadesCorreo.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Los datos ingresados son incorrectos");
            //DialogoCodefac.mensaje("Error Correo", "Los datos ingresados son incorrectos.\n" + ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
        } catch (SmtpNoExisteException ex) {
            ex.printStackTrace();
            System.out.println("Fallo al autentificar el usuario");
            throw new Exception("Fallo al autentificar el usuario");
            //getTxtPasswordCorreo().setText("");
            //DialogoCodefac.mensaje("Error Correo", "Ingrese un correo valido", DialogoCodefac.MENSAJE_INCORRECTO);
        }
    }
    
}
