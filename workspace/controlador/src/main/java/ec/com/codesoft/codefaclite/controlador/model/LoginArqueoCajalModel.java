/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.panel.DatoAdicionalDialog;
import ec.com.codesoft.codefaclite.controlador.panel.LoginArqueoCajaDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.LoginRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class LoginArqueoCajalModel extends LoginArqueoCajaDialog{
    
    private Usuario usuario;
    private final SessionCodefacInterface session;
    
    public LoginArqueoCajalModel(SessionCodefacInterface session) {
        super(null,true);
        this.session = session;
        //Centrar el dialogo en el centro de la pantalla
        setLocationRelativeTo(null);
        agregarListenerBotones();
    }

    private void ingresarSistema()
    {
        String clave = new String(getPswContrasena().getPassword());
        String usuarioTxt = getTxtUsuario().getText();
        if (!usuarioTxt.equals("") && !clave.equals("")) {
            try {
                LoginRespuesta loginRespuesta = ServiceFactory.getFactory().getUsuarioServicioIf().login(usuarioTxt, clave, session.getEmpresa());
                
                //Mostrar las alertas del sistema 
                if(loginRespuesta.alertas!=null)
                {
                    DialogoCodefac.mensaje("Alertas",loginRespuesta.obtenerAlertasConFormato(),DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
                
                switch(loginRespuesta.estadoEnum)
                {
                    case CORRECTO_USUARIO:
                        setVisible(false);
                        usuario=loginRespuesta.usuario;
                        
                        break;
                    case INCORRECTO_USUARIO:
                        DialogoCodefac.mensaje("Error Login",LoginRespuesta.EstadoLoginEnum.INCORRECTO_USUARIO.getMensaje(), DialogoCodefac.MENSAJE_INCORRECTO);
                        break;
                    default:
                        DialogoCodefac.mensaje("Error Login",loginRespuesta.estadoEnum.getMensaje(), DialogoCodefac.MENSAJE_INCORRECTO);
                        break;
                }
                
               
            } catch (RemoteException ex) {
                DialogoCodefac.mensaje("Error Login", "Datos Incorrectos", DialogoCodefac.MENSAJE_INCORRECTO);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(LoginArqueoCajalModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
        
    }
    
    private void agregarListenerBotones() 
    {
        getBtnIdentificarse().addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarSistema();
            }
        });
    }

    public Usuario getUsuario() {
        return usuario;
    }    
}
