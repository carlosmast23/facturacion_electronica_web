/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.reporte;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronicoFacturaAndLiquidacionAbstract;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.DetalleFacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FormaPagoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.ImpuestoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.TotalImpuesto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 */
public class FacturaElectronicaReporte extends FacturaLiquidacionCompraAbstractReport{
    private FacturaComprobante facturaComprobante;

    public FacturaElectronicaReporte(FacturaComprobante faturaComprobante) 
    {
        super(faturaComprobante);
        this.facturaComprobante = faturaComprobante;
    }

    @Override
    public Map<String, Object> getMapInfoCliente() {
        Map<String,Object> map=super.getMapInfoCliente(); //To change body of generated methods, choose Tools | Templates.
        map.put("cliente_nombres",facturaComprobante.getInformacionFactura().getRazonSocialComprador());
        map.put("direccion_comprador",facturaComprobante.getInformacionFactura().getDireccionComprador());
        map.put("cliente_identificacion",facturaComprobante.getInformacionFactura().getIdentificacionComprador());
        
        /**
         * TODO: Esta parte de subsidios solo esta programado para productos que no llevan iva por ejemplo el gas, falta terminar para productos con iva
         * POSIBLE SOLUCION: Recorrer cada detalle y calcular el subsidio solo de los productos 
         */
        BigDecimal valorTotalSubsidio=calcularTotalSubsidioConImpuestos();
        if(valorTotalSubsidio.compareTo(BigDecimal.ZERO)==0)
        {
            map.put("valorTotalSinSubsidio","0");
            map.put("ahorroPorSubsidio","0");
        }
        else
        {
            map.put("valorTotalSinSubsidio",totalFactura().add(valorTotalSubsidio)+"");
            map.put("ahorroPorSubsidio",valorTotalSubsidio+"");
        }
        
        return map;
    }

    /**
     *TODO: Este metodo no esta tomado en cuenta descuentos en el calculo
     * @return 
     */
    private BigDecimal calcularTotalSubsidioConImpuestos()
    {
        BigDecimal subsidioTotal=BigDecimal.ZERO;
        for (DetalleFacturaComprobante detalle : facturaComprobante.getDetalles()) {
            subsidioTotal=subsidioTotal.add(detalle.calcularSubsidioDetalle());
        }
        return subsidioTotal;
    }
    
    
    
   
}
