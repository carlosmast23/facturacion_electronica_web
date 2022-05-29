/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.pos;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.CajaBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.CajaPermisoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.UsuarioBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaPermiso;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import es.mityc.firmaJava.libreria.utilidades.Utilidades;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class CajaPermisoModelControlador extends ModelControladorAbstract<CajaPermisoModelControlador.CommonIf, CajaPermisoModelControlador.SwingIf, CajaPermisoModelControlador.WebIf> implements VistaCodefacIf
{

    private CajaPermiso cajaPermiso;
    private List<GeneralEnumEstado> estadosLista;
    
    public CajaPermisoModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CajaPermisoModelControlador.CommonIf interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }

    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        cajaPermiso = new CajaPermiso();
        cajaPermiso.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        estadosLista = UtilidadesLista.arrayToList(GeneralEnumEstado.values());
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
            setearDatos();
            ServiceFactory.getFactory().getCajaPermisoServiceIf().grabar(cajaPermiso);
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
            setearDatos();
            ServiceFactory.getFactory().getCajaPermisoServiceIf().editar(cajaPermiso);
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
            ServiceFactory.getFactory().getCajaPermisoServiceIf().eliminar(cajaPermiso);
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
        CajaPermisoBusquedaDialogo cajaPermisoBusquedaDialogo = new CajaPermisoBusquedaDialogo(session);
        return cajaPermisoBusquedaDialogo;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        cajaPermiso = (CajaPermiso)entidad;
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
         public String getDescripcionText();
    }
    
    public interface SwingIf extends CajaPermisoModelControlador.CommonIf
    {
        //TODO: Implementacion de las interfaces solo necesarias para Swing
    }
    
    public interface WebIf extends CajaPermisoModelControlador.CommonIf
    {
        //TODO: Implementacion de las interafaces solo para la web
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //                      FUNCIONES
    ////////////////////////////////////////////////////////////////////////////
    public void listenerBotonBuscarCaja()
    {
        CajaBusquedaDialogo cajaBusquedaDialogo = new CajaBusquedaDialogo(session);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(cajaBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        if (buscarDialogoModel.getResultado() != null) 
        {   
            cajaPermiso.setCaja((Caja)buscarDialogoModel.getResultado());
        }
    }
    
    public void listenerBotonBuscarUsuario()
    {
        UsuarioBusquedaDialogo usuarioBusquedaDialogo = new UsuarioBusquedaDialogo(session);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(usuarioBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        if(buscarDialogoModel.getResultado() != null)
        {
            cajaPermiso.setUsuario((Usuario)buscarDialogoModel.getResultado());
        }
    }
    
    public void setearDatos(){
        cajaPermiso.setDescripcion(getInterfaz().getDescripcionText());
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    //                      GET AND SET
    ////////////////////////////////////////////////////////////////////////////

    public CajaPermiso getCajaPermiso() {
        return cajaPermiso;
    }

    public void setCajaPermiso(CajaPermiso cajaPermiso) {
        this.cajaPermiso = cajaPermiso;
    }

    public List<GeneralEnumEstado> getEstadosLista() {
        return estadosLista;
    }

    public void setEstadosLista(List<GeneralEnumEstado> estadosLista) {
        this.estadosLista = estadosLista;
    }
    
    
}
