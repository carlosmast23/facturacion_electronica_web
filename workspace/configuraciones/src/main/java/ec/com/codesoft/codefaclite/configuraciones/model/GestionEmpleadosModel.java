/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.panel.GestionEmpleadosPanel;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Nacionalidad;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneroEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EmpleadoServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class GestionEmpleadosModel extends GestionEmpleadosPanel
{
    private Empleado empleado;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        empleado = new Empleado();
        iniciarCombos();
        limpiarCampos();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        empleado = new Empleado();
        limpiar();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            setearDatos();
            EmpleadoServiceIf servicio = ServiceFactory.getFactory().getEmpleadoServiceIf();
            servicio.grabar(empleado);
            DialogoCodefac.mensaje("Grabar", "Se guardo el empleado correctamente", DialogoCodefac.MENSAJE_CORRECTO);
            limpiar();
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GestionEmpleadosModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(GestionEmpleadosModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        try {
            setearDatos();
            EmpleadoServiceIf servicio = ServiceFactory.getFactory().getEmpleadoServiceIf();
            servicio.editar(empleado);
            DialogoCodefac.mensaje("Editar", "Se edito correctamente el departamento ", DialogoCodefac.MENSAJE_CORRECTO);
            limpiar();
        } catch (RemoteException ex) {
            Logger.getLogger(GestionEmpleadosModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        //super.buscar(); //To change body of generated methods, choose Tools | Templates.
        EmpleadoBusquedaDialogo empleadosBusquedaDialogo = new EmpleadoBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(empleadosBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        Empleado empleadoTemp = (Empleado) buscarDialogoModel.getResultado();
        if(empleadoTemp != null)
        {
            this.empleado = empleadoTemp;
            mostrarDatos();
        }
    }
    
    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        boolean respuesta;
        if(this.estadoFormulario.equals(ESTADO_EDITAR))
        {
            if(this.empleado != null)
            {
                respuesta = DialogoCodefac.dialogoPregunta("Advertencia", "Esta seguro que desea eliminar el empleado? ", DialogoCodefac.MENSAJE_ADVERTENCIA);
                if(respuesta)
                {
                    try {
                        this.empleado.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                        EmpleadoServiceIf servicio = ServiceFactory.getFactory().getEmpleadoServiceIf();
                        servicio.editar(empleado);
                        limpiar();
                        DialogoCodefac.mensaje("Eliminar", "El empleado se elimino correctamente", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    } catch (RemoteException ex) {
                        Logger.getLogger(DepartamentoModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        limpiarCampos();
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void iniciarCombos()
    {
        try {
            getCmbDepartamento().removeAllItems();
            List<Departamento> departamentos = ServiceFactory.getFactory().getDepartamentoServiceIf().obtenerActivos();
            for (Departamento departamento : departamentos) {
                getCmbDepartamento().addItem(departamento);
            }
            
            getCmbEstado().removeAllItems();
            for(GeneralEnumEstado generalEnumEstado : GeneralEnumEstado.values())
            {
                getCmbEstado().addItem(generalEnumEstado);
            }
            
            getCmbNacionalidad().removeAllItems();
            List<Nacionalidad> nacionalidades = ServiceFactory.getFactory().getNacionalidadServiceIf().obtenerTodos();
            for (Nacionalidad nacionalidad : nacionalidades) {
                getCmbNacionalidad().addItem(nacionalidad);
                
                if(nacionalidad.getIso().equals(Nacionalidad.ISO_NACIONALIDAD_DEFECTO))
                {
                    getCmbNacionalidad().setSelectedItem(nacionalidad);
                }
            }
            
            getCmbSexo().removeAllItems();
            for(GeneroEnum genero : GeneroEnum.values())
            {
                getCmbSexo().addItem(genero);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GestionEmpleadosModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GestionEmpleadosModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void limpiarCampos()
    {
        getTxtApellidos().setText("");
        getTxtCargo().setText("");
        getTxtCelular().setText("");
        getTxtCorreoElectronico().setText("");
        getTxtIdentificacion().setText("");
        getTxtNombres().setText("");
        getTxtTelefono().setText("");
        getTxtAreaDireccion().setText("");
        getTxtCodigo().setText("");
        
        //getCmbNacionalidad().setSelectedItem(ABORT);
    }
    
    public void setearDatos()
    {
        this.empleado.setApellidos(""+getTxtApellidos().getText());
        this.empleado.setCargo(""+getTxtCargo().getText());
        this.empleado.setCorreoElectronico(""+getTxtCorreoElectronico().getText());
        Departamento departamento = (Departamento) getCmbDepartamento().getSelectedItem();
        if(departamento != null){
            this.empleado.setDepartamento(departamento);
        }
        this.empleado.setDireccion(""+getTxtAreaDireccion().getText());
        GeneralEnumEstado generalEnumEstado = (GeneralEnumEstado) getCmbEstado().getSelectedItem();
        if(generalEnumEstado != null){
            this.empleado.setEstado(generalEnumEstado.getEstado());
        }
        this.empleado.setIdentificacion(""+getTxtIdentificacion().getText());
        Nacionalidad nacionalidad = (Nacionalidad) getCmbNacionalidad().getSelectedItem();
        if(generalEnumEstado != null){
            this.empleado.setNacionalidad(nacionalidad);
        }
        this.empleado.setNombres(""+getTxtNombres().getText());
        GeneroEnum generoEnum = (GeneroEnum) getCmbSexo().getSelectedItem();
        if(generoEnum != null){
            this.empleado.setSexo(generoEnum.getEstado());
        }
        this.empleado.setTelefonoCelular(""+getTxtCelular().getText());
        this.empleado.setTelefonoConvencional(""+getTxtTelefono().getText());
        this.empleado.setCodigo(getTxtCodigo().getText());
    }
    
    public void mostrarDatos()
    {
        /**
         * Cargar datos en Text 
         */
        
        getTxtApellidos().setText(this.empleado.getApellidos());
        getTxtAreaDireccion().setText(this.empleado.getDireccion());
        getTxtCargo().setText(this.empleado.getCargo());
        getTxtCelular().setText(this.empleado.getTelefonoCelular());
        getTxtCorreoElectronico().setText(this.empleado.getCorreoElectronico());
        getTxtIdentificacion().setText(this.empleado.getIdentificacion());
        getTxtNombres().setText(this.empleado.getNombres());
        getTxtTelefono().setText(this.empleado.getTelefonoConvencional());
        getTxtCodigo().setText(this.empleado.getCodigo());
        
        /**
         * Cargar datos en combos
         */
                
        Departamento departamento = this.empleado.getDepartamento();
        if(departamento != null){
            getCmbDepartamento().setSelectedItem(departamento);
        }
        Nacionalidad nacionalidad = this.empleado.getNacionalidad();
        if(nacionalidad != null){
            getCmbNacionalidad().setSelectedItem(nacionalidad);
        }
        GeneroEnum generoEnum = GeneroEnum.getEnum(this.empleado.getSexo());
        if(generoEnum != null)
        {
            getCmbSexo().setSelectedItem(generoEnum);
        }
        GeneralEnumEstado generalEnumEstado = GeneralEnumEstado.getEnum(this.empleado.getEstado());
        if(generalEnumEstado != null)
        {
            getCmbEstado().setSelectedItem(generalEnumEstado);
        }
        
    }
}
