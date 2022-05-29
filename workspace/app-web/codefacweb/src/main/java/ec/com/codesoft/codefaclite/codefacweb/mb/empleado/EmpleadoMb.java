/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.empleado;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class EmpleadoMb extends GeneralAbstractMb implements Serializable {

    private Empleado empleado;

    @PostConstruct
    public void init() {
        empleado = new Empleado();
    }

    @Override
    public String linkAyuda() {
        System.out.println("mandando ayuda");
        //return "http://www.cf.codesoft-ec.com/ayuda";
        return "http://www.al-code.com/";
    }

    public void ayuda() {
        System.out.println("Si funciona esto si veo la luz");
    }

    @Override
    public void grabar() {
        try {
            ServiceFactory.getFactory().getEmpleadoServiceIf().grabar(empleado);
            System.out.println("Empleado guardado");
            MensajeMb.mensaje("Correctamente","Ejemplo guardado correctamente",FacesMessage.SEVERITY_INFO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(EmpleadoMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(EmpleadoMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void buscar() {

    }


    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        return new EmpleadoBusquedaDialogo();
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * METODOS GET AND SET
     * @return 
     */

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String titulo() {
        return "Empleado";
    }

    @Override
    public void editar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
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
        this.empleado = (Empleado) entidad;
    }

    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
