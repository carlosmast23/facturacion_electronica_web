/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class IndexAdminMb extends GeneralAbstractMb implements Serializable  {

    @Override
    public void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public void cargarBusqueda(Object obj) throws ExcepcionCodefacLite, UnsupportedOperationException {
        
    }

    @Override
    public String titulo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        return "Bienvenido";
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

}
