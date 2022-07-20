/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.producto;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.GestionRetencionRentaDialogo;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @auhor
 */
public class GestionRetencionRentaControlador extends ModelControladorAbstract<GestionRetencionRentaControlador.CommonIf,GestionRetencionRentaControlador.SwingIf,GestionRetencionRentaControlador.WebIf> implements VistaCodefacIf{
    
    private SriRetencionRenta sriRetencionRenta;
           
    public GestionRetencionRentaControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CommonIf interfaz, TipoVista tipoVista) {
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
        ServiceFactory.getFactory().getSriRetencionIvaServiceIf().obtenerTodosOrdenadoPorCodigo();
        ServiceFactory.getFactory().getSriRetencionRentaServiceIf().obtenerTodosOrdenadoPorCodigo();
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        try {
            ServiceFactory.getFactory().getSriRetencionRentaServiceIf().editar(sriRetencionRenta);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GestionRetencionRentaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        if(tipoVista.ESCRITORIO.equals(tipoVista))
        {
            getInterazEscritorio().limpiar();
        }
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
        return new GestionRetencionRentaDialogo();
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        this.sriRetencionRenta=(SriRetencionRenta) entidad;
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /////////////////////// GET AND SET ///////////////////////////////

    public SriRetencionRenta getSriRetencionRenta() {
        return sriRetencionRenta;
    }

    public void setSriRetencionRenta(SriRetencionRenta sriRetencionRenta) {
        this.sriRetencionRenta = sriRetencionRenta;
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
        public void limpiar();
    }
    
    public interface WebIf extends CommonIf
    {
        //TODO: Implementacion de las interafaces solo para la web
    }
}
