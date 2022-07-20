/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.pos;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.CajaBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.CajaPermisoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.TurnoAsignadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.TurnoBusquedaDiagolo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaPermiso;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Turno;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.TurnoAsignado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @auhor
 */
public class TurnoAsignadoModelControlador extends ModelControladorAbstract<TurnoAsignadoModelControlador.CommonIf, TurnoAsignadoModelControlador.SwingIf, TurnoAsignadoModelControlador.WebIf> implements  VistaCodefacIf
{
    
    private TurnoAsignado turnoAsignado;
    private List<GeneralEnumEstado> estadoLista;
    
    public TurnoAsignadoModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, TurnoAsignadoModelControlador.CommonIf interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }
    

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        turnoAsignado = new TurnoAsignado();
        turnoAsignado.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        estadoLista = UtilidadesLista.arrayToList(GeneralEnumEstado.values());        
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        iniciar();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        try
        {       
            //Grabar
            ServiceFactory.getFactory().getTurnoAsignadoServiceIf().grabar(turnoAsignado);
            //Mensaje
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
            
        }catch(ServicioCodefacException e)
        {
            mostrarMensaje(new CodefacMsj("Error", e.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO));
            try {
                throw e;
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(CajaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        try {
            //Editar
            ServiceFactory.getFactory().getTurnoAsignadoServiceIf().editar(turnoAsignado);
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ArqueoCajaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        try {
            Boolean respuesta = dialogoPregunta(MensajeCodefacSistema.Preguntas.ELIMINAR_REGISTRO);
            if(!respuesta){
                throw new ServicioCodefacException("Error eliminando Turno Asignado");
            }
            ServiceFactory.getFactory().getTurnoAsignadoServiceIf().eliminar(turnoAsignado);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ArqueoCajaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        TurnoAsignadoBusquedaDialogo turnoAsignadoBusquedaDialogo = new TurnoAsignadoBusquedaDialogo(session);
        return turnoAsignadoBusquedaDialogo;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        turnoAsignado = (TurnoAsignado) entidad;
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //                      INTERFACE
    ////////////////////////////////////////////////////////////////////////////
    
    public interface CommonIf
    {        
         
    }
    
    public interface SwingIf extends TurnoAsignadoModelControlador.CommonIf
    {
        //TODO: Implementacion de las interfaces solo necesarias para Swing
    }
    
    public interface WebIf extends TurnoAsignadoModelControlador.CommonIf
    {
        //TODO: Implementacion de las interafaces solo para la web
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //                      FUNCIONES
    ////////////////////////////////////////////////////////////////////////////
    
    public void listenerBotonBuscarCaja()
    {
        CajaPermisoBusquedaDialogo cajaPermisoBusquedaDialogo = new CajaPermisoBusquedaDialogo(session);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(cajaPermisoBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        if (buscarDialogoModel.getResultado() != null) 
        {   
            turnoAsignado.setCajaPermiso((CajaPermiso)buscarDialogoModel.getResultado());
        }
    }
    
    public void listenerBotonBuscarTurnoAsignar()
    {
        TurnoBusquedaDiagolo turnoBusquedaDiagolo = new TurnoBusquedaDiagolo(session);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(turnoBusquedaDiagolo);
        buscarDialogoModel.setVisible(true);
        if(buscarDialogoModel.getResultado() != null)
        {
            turnoAsignado.setTurno((Turno)buscarDialogoModel.getResultado());
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    //                      GET AND SET
    ////////////////////////////////////////////////////////////////////////////

    public TurnoAsignado getTurnoAsignado() {
        return turnoAsignado;
    }

    public void setTurnoAsignado(TurnoAsignado turnoAsignado) {
        this.turnoAsignado = turnoAsignado;
    }

    public List<GeneralEnumEstado> getEstadoLista() {
        return estadoLista;
    }

    public void setEstadoLista(List<GeneralEnumEstado> estadoLista) {
        this.estadoLista = estadoLista;
    }
   
}
