/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;

/**
 *
 * @author Carlos
 */
public class LiquidacionCompraReporteModel extends FacturaReporteModel{

    public LiquidacionCompraReporteModel() {
        super();
    }

    public void datosIniciales()
    {
        setTitle(VentanaEnum.REPORTE_LIQUIDACION_COMPRA.getNombre());
    }
    
    @Override
    public void cargarDocumentosCombo() {
        getCmbDocumento().removeAllItems();
        getCmbDocumento().addItem(DocumentoEnum.LIQUIDACION_COMPRA);    
    }
    
}
