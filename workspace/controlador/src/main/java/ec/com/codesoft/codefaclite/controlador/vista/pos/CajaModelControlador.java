/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.pos;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.CajaBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.vista.crm.EjemploModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.crm.ProductoModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract.MensajeVistaInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmisionUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import es.mityc.firmaJava.libreria.utilidades.Utilidades;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class CajaModelControlador extends ModelControladorAbstract<CajaModelControlador.CommonIf, CajaModelControlador.SwingIf, CajaModelControlador.WebIf> implements VistaCodefacIf
{
    private Caja caja;
    private List<CajaEnum> estadosList;
    private List<Sucursal> sucursalList;
    /**
     * Controlador Generico
     * @param mensajeVista
     * @param session
     * @param interfaz
     * @param tipoVista
     */
    public CajaModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CajaModelControlador.CommonIf interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }
    
    /**
    * Metodo iniciar
    * @throws java.rmi.RemoteException
    */ 
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        caja = new Caja();
        
        caja.setNombre("");
        caja.setDescripcion("");
        caja.setEstadoEnum(CajaEnum.ACTIVO);
        
        sucursalList = ServiceFactory.getFactory().getSucursalServiceIf().obtenerTodos();
        estadosList = UtilidadesLista.arrayToList(CajaEnum.values());
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
       iniciar();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        try
        {
            //Obtener datos desde vista
            setearDatos();
            ServiceFactory.getFactory().getCajaServiceIf().grabar(caja);
            //Mensaje
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        }
        catch(ServicioCodefacException e)
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
            //Obtener datos desde vista
            setearDatos();
            ServiceFactory.getFactory().getCajaServiceIf().editar(caja);
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CajaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        try
        {
            Boolean respuesta = dialogoPregunta(MensajeCodefacSistema.Preguntas.ELIMINAR_REGISTRO);
            if(!respuesta){
                throw new ServicioCodefacException("Error elimando Caja");
            }            
            ServiceFactory.getFactory().getCajaServiceIf().eliminar(caja);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(CajaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
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
        CajaBusquedaDialogo cajaBusquedaDialogo = new CajaBusquedaDialogo(session);
        return cajaBusquedaDialogo;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        caja = (Caja) entidad;
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public interface CommonIf
    {        
        public PuntoEmision getPuntoEmision();
        public String getDescripcion();
    }
    
    public interface SwingIf extends CajaModelControlador.CommonIf
    {
        //TODO: Implementacion de las interfaces solo necesarias para Swing
    }
    
    public interface WebIf extends CajaModelControlador.CommonIf
    {
        //TODO: Implementacion de las interafaces solo para la web
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //                      GET AND SET
    ////////////////////////////////////////////////////////////////////////////

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public List<CajaEnum> getEstadosList() {
        return estadosList;
    }

    public void setEstadosList(List<CajaEnum> estadosList) {
        this.estadosList = estadosList;
    }

    public List<Sucursal> getSucursalList() {
        return sucursalList;
    }

    public void setSucursalList(List<Sucursal> sucursalList) {
        this.sucursalList = sucursalList;
    } 
    
    ////////////////////////////////////////////////////////////////////////////
    // Funciones
    ////////////////////////////////////////////////////////////////////////////
    public void setearDatos(){
        caja.setDescripcion(getInterfaz().getDescripcion());
        caja.setPuntoEmision(getInterfaz().getPuntoEmision());
    }
}   
