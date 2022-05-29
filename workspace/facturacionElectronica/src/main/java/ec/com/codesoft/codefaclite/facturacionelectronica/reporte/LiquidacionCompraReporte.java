/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.reporte;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.liquidacionCompra.LiquidacionCompraComprobante;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class LiquidacionCompraReporte extends FacturaLiquidacionCompraAbstractReport{
    private LiquidacionCompraComprobante liquidacionComprobante;

    public LiquidacionCompraReporte(LiquidacionCompraComprobante liquidacionComprobante) 
    {
        super(liquidacionComprobante);
        this.liquidacionComprobante = liquidacionComprobante;
    }

    @Override
    public Map<String, Object> getMapInfoCliente() {
        Map<String,Object> map=super.getMapInfoCliente(); //To change body of generated methods, choose Tools | Templates.
        map.put("cliente_nombres",liquidacionComprobante.getInformacionLiquidacionCompra().getRazonSocialProveedor());
        map.put("direccion_comprador",liquidacionComprobante.getInformacionLiquidacionCompra().getDireccionProveedor());
        map.put("cliente_identificacion",liquidacionComprobante.getInformacionLiquidacionCompra().getIdentificacionProveedor());
        return map;
    }
   
}
