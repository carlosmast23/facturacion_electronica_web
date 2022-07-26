/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.busqueda.PuntoEmisionBusquedaDialogo;
import ec.com.codesoft.codefaclite.configuraciones.panel.PuntoEmisionPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class PuntoEmisionModel extends PuntoEmisionPanel{
    
    PuntoEmision puntoEmision;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        valoresIniciales();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        try {
            setearDatos();
            ServiceFactory.getFactory().getPuntoVentaServiceIf().grabar(puntoEmision);
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(SucursalModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        try {
            setearDatos();
            ServiceFactory.getFactory().getPuntoVentaServiceIf().editar(puntoEmision);
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(SucursalModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        try {
            setearDatos();
            ServiceFactory.getFactory().getPuntoVentaServiceIf().eliminar(puntoEmision);
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(PuntoEmisionModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite(ex.getMessage());
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
        puntoEmision=new PuntoEmision();
        
        getCmbSucursal().setSelectedIndex(0);
        getCmbTipoFacturacion().setSelectedIndex(0);
        
        getTxtPuntoEmision().setValue(new Integer(1));
        
        getTxtFactura().setValue(new Integer(1));
        getTxtNotaCredito().setValue(new Integer(1));
        getTxtNotaDebito().setValue(new Integer(1));
        getTxtGuiaRemision().setValue(new Integer(1));
        getTxtRetenciones().setValue(new Integer(1));
        getTxtRetenciones().setValue(new Integer(1));
        getTxtNotaVenta().setValue(new Integer(1));
        getTxtNotaVentaInterna().setValue(new Integer(1));
        getTxtLiquidacionCompra().setValue(new Integer(1));
        getTxtGuiaRemisionInterna().setValue(new Integer(1));
        
        
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
        return new PuntoEmisionBusquedaDialogo(session.getEmpresa());
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        puntoEmision=(PuntoEmision) entidad;
        
        getTxtDescripcion().setText(puntoEmision.getDescripcion());
        getTxtPuntoEmision().setValue(puntoEmision.getPuntoEmision());
        getTxtFactura().setValue(puntoEmision.getSecuencialFactura());
        getTxtNotaCredito().setValue(puntoEmision.getSecuencialNotaCredito());
        getTxtNotaDebito().setValue(puntoEmision.getSecuencialNotaDebito());
        getTxtGuiaRemision().setValue(puntoEmision.getSecuencialGuiaRemision());
        getTxtRetenciones().setValue(puntoEmision.getSecuencialRetenciones());
        getTxtNotaVenta().setValue(puntoEmision.getSecuencialNotaVenta());
        getTxtNotaVentaInterna().setValue(puntoEmision.getSecuencialNotaVentaInterna());
        getTxtLiquidacionCompra().setValue(puntoEmision.getSecuencialLiquidacionCompra());
        getTxtGuiaRemisionInterna().setValue(puntoEmision.getSecuencialGuiaRemisionInterna());
        
        getCmbSucursal().setSelectedItem(puntoEmision.getSucursal());
        getCmbTipoFacturacion().setSelectedItem(puntoEmision.getTipoFacturacionEnum());
        
    }

    private void valoresIniciales() {
        
        getCmbTipoFacturacion().removeAllItems();
        ComprobanteEntity.TipoEmisionEnum[] tipos = ComprobanteEntity.TipoEmisionEnum.values();
        for (ComprobanteEntity.TipoEmisionEnum tipo : tipos) {
            getCmbTipoFacturacion().addItem(tipo);
        }
        
        try {
            List<Sucursal> sucursales=ServiceFactory.getFactory().getSucursalServiceIf().consultarActivosPorEmpresa(session.getEmpresa()); //TODO: Remplazar por un metodo que solo obtenga sucursal activas
            getCmbSucursal().removeAllItems();
            for (Sucursal sucursal : sucursales) {
                getCmbSucursal().addItem(sucursal);
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(PuntoEmisionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(PuntoEmisionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void setearDatos() {
        puntoEmision.setDescripcion(getTxtDescripcion().getText());
        puntoEmision.setPuntoEmision((Integer) getTxtPuntoEmision().getValue());
        
        puntoEmision.setSecuencialFactura((Integer) getTxtFactura().getValue());
        puntoEmision.setSecuencialNotaCredito((Integer) getTxtNotaCredito().getValue());
        puntoEmision.setSecuencialNotaDebito((Integer) getTxtNotaDebito().getValue());
        puntoEmision.setSecuencialGuiaRemision((Integer) getTxtGuiaRemision().getValue());
        puntoEmision.setSecuencialRetenciones((Integer) getTxtRetenciones().getValue());
        puntoEmision.setSecuencialNotaVenta((Integer) getTxtNotaVenta().getValue());
        puntoEmision.setSecuencialNotaVentaInterna((getTxtNotaVentaInterna().getValue()!=null)?(Integer)getTxtNotaVentaInterna().getValue():new Integer(0));
        puntoEmision.setSecuencialLiquidacionCompra((Integer) getTxtLiquidacionCompra().getValue());
        puntoEmision.setSecuencialGuiaRemisionInterna((Integer) getTxtGuiaRemisionInterna().getValue());
        
        Sucursal sucursal=(Sucursal) getCmbSucursal().getSelectedItem();
        puntoEmision.setSucursal(sucursal);
        
        ComprobanteEntity.TipoEmisionEnum tipoEmisionEnum=(ComprobanteEntity.TipoEmisionEnum) getCmbTipoFacturacion().getSelectedItem();
        puntoEmision.setTipoFacturacion(tipoEmisionEnum.getLetra());
        
        
    }
    
}
