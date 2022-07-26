/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.utilidades;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author
 */
public class MensajeMb implements Serializable{
    
    public static ModelControladorAbstract.MensajeVistaInterface intefaceMensaje=new ModelControladorAbstract.MensajeVistaInterface() {
        public void mensaje(CodefacMsj codefacMensaje) {
            MensajeMb.mensaje(codefacMensaje);
        }

        public boolean dialogoPregunta(CodefacMsj codefacMensaje) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

    //public static final String ID_MENSAJE_DIALOGO = "messages";
    public static Map<Integer, FacesMessage.Severity> homologarAlertasMap=new HashMap<Integer,FacesMessage.Severity>(){{
        put(DialogoCodefac.MENSAJE_ADVERTENCIA,FacesMessage.SEVERITY_WARN);
        put(DialogoCodefac.MENSAJE_INCORRECTO,FacesMessage.SEVERITY_FATAL);
        put(DialogoCodefac.MENSAJE_CORRECTO,FacesMessage.SEVERITY_INFO);
    }};

    public static void mensaje(String titulo, String mensaje, FacesMessage.Severity tipoMensaje) {
        FacesMessage message = new FacesMessage(tipoMensaje, titulo, mensaje);
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    public static void mostrarMensajeDialogo(String titulo, String mensaje, FacesMessage.Severity tipoMensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(tipoMensaje,titulo,mensaje));        
    }
    
    
    public static void mensaje(CodefacMsj codefacMensaje) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(homologarAlertasMap.get(codefacMensaje.modoMensaje.getCodigo()),codefacMensaje.titulo,codefacMensaje.mensaje));        
    }
    
    

}
