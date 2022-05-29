/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.factura;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import static ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac.dialogoPregunta;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.vistas.core.ControladorCampoTextoAnot;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesReflexion;
import java.io.Serializable;


/**
 *TODO: Hacer un metodo generico que me permita saber el estado si creando o editando
 * @author CARLOS_CODESOFT
 * T= interfaz base
 * E= interfaz de escririo
 * W= interaz web
 */
public abstract class ModelControladorAbstract <T,E extends  T,W extends T> implements Serializable
{
    protected TipoVista tipoVista;
    /**
     * Interfaz me va a permitir mandar mensajes de forma estandar
     */
    protected MensajeVistaInterface mensajeVista;
    
    protected SessionCodefacInterface session;
    
    private T interfaz;    
    

    @Deprecated
    public ModelControladorAbstract(MensajeVistaInterface mensajeVista) {
        this.mensajeVista = mensajeVista;
    }
    
    @Deprecated
    public ModelControladorAbstract(MensajeVistaInterface mensajeVista,SessionCodefacInterface session) {
        this.mensajeVista = mensajeVista;
        this.session=session;
    }

    public ModelControladorAbstract(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, T interfaz,TipoVista tipoVista) {
        this.mensajeVista = mensajeVista;
        this.session = session;
        this.interfaz = interfaz;
        this.tipoVista=tipoVista;
    }

    /**
     * Devuelve la interfaz general para todas las vistas
     * @return 
     */
    public T getInterfaz()
    {
        return interfaz;
    }
    
    public E getInterazEscritorio()
    {
        return  (E) interfaz;
    }
    
    public W getInterazWeb()
    {
        return  (W) interfaz;
    }
    
    
    /**
     * Metodo que me permite enviar un mensaje para mostrar en las vistas dependiendo cada caso
     * @param mensaje 
     */
    public void mostrarMensaje(CodefacMsj mensaje)
    {
        if(mensajeVista!=null)
        {
            mensajeVista.mensaje(mensaje);
        }
    }
    
    /**
     * Metodo que me permite confirmar la accion y enviar un mensaje para mostrar en las vistas dependiendo cada caso
     * @param mensaje 
     * @return  
     */
    public boolean dialogoPregunta(CodefacMsj mensaje)
    {
        Boolean respuesta = null;
        if(mensajeVista != null){
            respuesta = mensajeVista.dialogoPregunta(mensaje);
        }
        return respuesta;
    }
    
    /**
     * Metodo que me permite funcionar de medio de comunicación para activar los listener de la pantalla
     * @param value 
     */
    public void setProperty(Object value,Object newValue)
    {
        if(value instanceof ControladorCampoTextoAnot)
        {
            value=newValue;
            //Avisar al componente que sucedio una modificación en los datos
            UtilidadesReflexion.ejecutarAnotacionEnPropiedades(Class.class,ControladorCampoTextoAnot.class,new UtilidadesReflexion.AnotacionIf<ControladorCampoTextoAnot>() {
                @Override
                public void ejecutar(ControladorCampoTextoAnot anotacion) {
                    
                }
            });
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Enum que me permite clasificar con que vista estoy trabajando especialmente util para poder mostrar los mensajes
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public enum TipoVista
    {
        ESCRITORIO,
        WEB;
    }
    
    public interface MensajeVistaInterface
    {
        public void mensaje(CodefacMsj codefacMensaje);
        public boolean dialogoPregunta(CodefacMsj codefacMensaje);
    }
}
