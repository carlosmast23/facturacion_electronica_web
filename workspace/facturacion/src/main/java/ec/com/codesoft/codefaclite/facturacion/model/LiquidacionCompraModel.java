/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.LiquidacionCompraBusqueda;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class LiquidacionCompraModel extends FacturacionModel{

    public LiquidacionCompraModel() {
        super();
    }
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        super.iniciar(); //To change body of generated methods, choose Tools | Templates.
        valoresIniciales();
        getBtnCargarProforma().setEnabled(false);
        //getCmbPuntoEmision().setVisible(false);
        //getLblEstablecimiento().setVisible(false);
    }
    
     private void valoresIniciales() {
        //nombre de la pantalla
        setTitle(VentanaEnum.LIQUIDACION_COMPRA.getNombre());

        //cargar el documento de proforma
        getCmbDocumento().removeAllItems();
        getCmbDocumento().addItem(DocumentoEnum.LIQUIDACION_COMPRA);

        //desactivar el panel formas de pago porque no utilizo
        getPanelFormasPago().setVisible(false);    
        getPnlVuelto().setVisible(false);
        getCmbConsumidorFinal().setVisible(false);
        
    }
     
     
     
    /* @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            validacionesGrabar(); //Metodo que realiza validaciones previas antes de grabar
            FacturacionServiceIf servicio = ServiceFactory.getFactory().getFacturacionServiceIf();
            setearValoresDefaultFactura();
            //factura.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
            factura = servicio.grabarLiquidacionCompra(factura);
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
            //imprimirProforma();
        
        } catch (RemoteException ex) {
            Logger.getLogger(ProformaModel.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ProformaModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
        }        

    }*/

    @Override
    public InterfaceModelFind getBusquedaInterface() {
        LiquidacionCompraBusqueda compraModel=new LiquidacionCompraBusqueda(session.getSucursal());
        return compraModel;        
    }
    
    
}
