/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.inventario;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.inventario.ReporteInventarioStockControlador;
import ec.com.codesoft.codefaclite.controlador.vista.crm.RutaModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/**
 *
 * @author Robert
 */
@ManagedBean
@ViewScoped
public class ReporteInventarioStockMb extends GeneralAbstractMb implements ControladorVistaIf, ReporteInventarioStockControlador.WebIf
{
    private ReporteInventarioStockControlador controlador;
    
    public ModelControladorAbstract getControladorVista() {
        return controlador;
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String titulo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        controlador=new ReporteInventarioStockControlador(MensajeMb.intefaceMensaje,sessionMb.getSession(), this, ModelControladorAbstract.TipoVista.WEB);
    }

    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    ////////////////////////////////////////////////////////////////////////////
    ///                     GET AND SET
    ////////////////////////////////////////////////////////////////////////////

    public ReporteInventarioStockControlador getControlador() {
        return controlador;
    }

    public void setControlador(ReporteInventarioStockControlador controlador) {
        this.controlador = controlador;
    }
    
    
}
