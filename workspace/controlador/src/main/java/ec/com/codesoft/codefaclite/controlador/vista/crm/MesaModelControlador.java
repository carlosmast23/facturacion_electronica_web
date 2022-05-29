/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.controlador.vista.crm;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.MesaBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Mesa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Ruta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DellWin10
 */
public class MesaModelControlador extends ModelControladorAbstract<MesaModelControlador.CommonIf,MesaModelControlador.SwingIf,MesaModelControlador.WebIf> implements VistaCodefacIf  {

    private Mesa mesa;

    public MesaModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CommonIf interfaz, TipoVista tipoVista) 
    {
        super(mensajeVista, session, interfaz, tipoVista);
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        try {            
            ServiceFactory.getFactory().getMesaSeviceIf().grabar(mesa, session.getEmpresa(), session.getUsuario());
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ZonaControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ERROR));
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        try {            
            ServiceFactory.getFactory().getMesaSeviceIf().editar(mesa, session.getEmpresa(), session.getUsuario());
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ZonaControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ERROR));
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        if(this.dialogoPregunta(MensajeCodefacSistema.Preguntas.ELIMINAR_REGISTRO))
        {
            try {
                ServiceFactory.getFactory().getMesaSeviceIf().eliminar(mesa);
                mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);                
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(RutaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
                mostrarMensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ERROR));
                throw new ExcepcionCodefacLite(ex.getMessage());
            }
        }
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        mesa=new Mesa();
        mesa.setCapacidad(1);
        mesa.setNumero(1);
        mesa.setNombre("");
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        return new MesaBusquedaDialogo();
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        this.mesa=(Mesa) entidad;
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //             METODOS GET AND SET
    ///////////////////////////////////////////////////////////////////////////

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }
    

    ///////////////////////////////////////////////////////////////////////////
    //             METODOS QUE CONTIENEN INTERFACES PARA LA IMPLEMTACION
    ///////////////////////////////////////////////////////////////////////////
    
    public interface CommonIf
    {
        //TODO: Implementacion de todas las interfaces comunes
    }
    
    public interface SwingIf extends CommonIf
    {
        //TODO: Implementacion de las interfaces solo necesarias para Swing
    }
    
    public interface WebIf extends CommonIf
    {
        //TODO: Implementacion de las interafaces solo para la web
    }
}
