/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.busqueda.DepartamentoBusquedaDialogo;
import ec.com.codesoft.codefaclite.configuraciones.panel.DepartamentoPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.DepartamentoServiceIf;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Service;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class DepartamentoModel extends DepartamentoPanel
{
    private Departamento departamento;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        iniciarCombo();
        limpiarCampos();
        valoresIniciales();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        departamento = new Departamento();
        limpiarCampos();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            setearDatos();
            DepartamentoServiceIf servicio = ServiceFactory.getFactory().getDepartamentoServiceIf();
            servicio.grabar(departamento);
            DialogoCodefac.mensaje("Grabar", "Se guardo el departamento correctamente", DialogoCodefac.MENSAJE_CORRECTO);
            limpiar();
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(DepartamentoModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite(ex.getMessage());
            
        } catch (RemoteException ex) {
            Logger.getLogger(DepartamentoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        try {
            setearDatos();
            DepartamentoServiceIf servicio = ServiceFactory.getFactory().getDepartamentoServiceIf();
            servicio.editar(departamento);
            DialogoCodefac.mensaje("Editar", "Se edito correctamente el departamento ", DialogoCodefac.MENSAJE_CORRECTO);
            limpiar();
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(DepartamentoModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(DepartamentoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        boolean respuesta;
        if(this.estadoFormulario.equals(ESTADO_EDITAR))
        {
            if(this.departamento != null)
            {
                respuesta = DialogoCodefac.dialogoPregunta("Advertencia", "Esta seguro que desea eliminar el departamento? ", DialogoCodefac.MENSAJE_ADVERTENCIA);
                if(respuesta)
                {
                    try {
                        this.departamento.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                        DepartamentoServiceIf servicio = ServiceFactory.getFactory().getDepartamentoServiceIf();
                        servicio.eliminar(departamento);
                        limpiar();
                        DialogoCodefac.mensaje("Eliminar", "El departamento se elimino correctamente", SOMEBITS);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(DepartamentoModel.class.getName()).log(Level.SEVERE, null, ex);
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

    /*@Override
    public void buscar() throws ExcepcionCodefacLite {
        //super.buscar(); 
        DepartamentoBusquedaDialogo busquedaDialogo = new DepartamentoBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedaDialogo);
        buscarDialogoModel.setVisible(true);
        Departamento departamentoTemp = (Departamento) buscarDialogoModel.getResultado();
        if(departamentoTemp != null)        
        {
            this.departamento = departamentoTemp;
            mostrarDatos();
        }  
        else
        {
            throw new ExcepcionCodefacLite("cancelado metodo buscar");
        }
    }*/

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        DepartamentoBusquedaDialogo busquedaDialogo = new DepartamentoBusquedaDialogo(GeneralEnumEstado.ACTIVO);
        //BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedaDialogo);
        return busquedaDialogo;  
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        this.departamento = (Departamento) entidad;
        mostrarDatos();
    }
    
    @Override
    public List<String> getPerfilesPermisos() {
        List<String> permisos = new ArrayList<String>();
        permisos.add(Perfil.PERFIl_ADMINISTRADOR);
        permisos.add(Perfil.PERFIl_OPERADOR);
        return permisos;
    }
    
    public void iniciarCombo()
    {
        this.getCmbEstado().removeAllItems();
        for(GeneralEnumEstado gee : GeneralEnumEstado.values())
        {
            this.getCmbEstado().addItem(gee);
        }
    }
    
    public void limpiarCampos()
    {
        this.getTxtCodigo().setText("");
        this.getTxtDescripcion().setText("");
        this.getTxtNombre().setText("");
        
        if(getCmbTipo().getModel().getSize()>0)
            this.getCmbTipo().setSelectedIndex(0);
    }
    
    public void mostrarDatos()
    {
        this.getTxtCodigo().setText("" + this.departamento.getCodigo());
        this.getTxtDescripcion().setText("" + this.departamento.getDescripcion());
        this.getTxtNombre().setText("" + this.departamento.getNombre());
        GeneralEnumEstado generalEnumEstado = GeneralEnumEstado.getEnum(this.departamento.getEstado());
        this.getCmbEstado().setSelectedItem(generalEnumEstado);
        this.getCmbTipo().setSelectedItem(this.departamento.getTipoEnum());
    }
    
    public void setearDatos()
    {
        this.departamento.setCodigo(this.getTxtCodigo().getText());
        this.departamento.setDescripcion(this.getTxtDescripcion().getText());
        this.departamento.setNombre(this.getTxtNombre().getText());
        GeneralEnumEstado generalEnumEstado = (GeneralEnumEstado) this.getCmbEstado().getSelectedItem(); 
        this.departamento.setEstado(generalEnumEstado.getEstado());
        
        Departamento.TipoEnum tipoEnum=(Departamento.TipoEnum) this.getCmbTipo().getSelectedItem();
        this.departamento.setTipoEnum(tipoEnum);
    }

    private void valoresIniciales() {
        getCmbTipo().removeAllItems();
        for (Departamento.TipoEnum value : Departamento.TipoEnum.values()) {
            getCmbTipo().addItem(value);
        }
    }
}
