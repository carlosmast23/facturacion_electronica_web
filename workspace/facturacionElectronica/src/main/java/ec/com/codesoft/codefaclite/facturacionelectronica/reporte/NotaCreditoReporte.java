/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.reporte;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.TotalImpuesto;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito.DetalleNotaCreditoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito.NotaCreditoComprobante;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 */
public class NotaCreditoReporte extends ComprobanteElectronicoReporte{
    
    private NotaCreditoComprobante comprobante;

    public NotaCreditoReporte(ComprobanteElectronico comprobante) {
        super(comprobante);
        this.comprobante = (NotaCreditoComprobante) comprobante;
    }
    
    @Override
    public Map<String,Object> getMapInfoCliente()
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("cliente_nombres",comprobante.getInfoNotaCredito().getRazonSocialComprador());
        map.put("cliente_identificacion",comprobante.getInfoNotaCredito().getIdentificacionComprador());
        map.put("fecha_emision", comprobante.getInfoNotaCredito().getFechaEmision());
        map.put("obligado_contabilidad",comprobante.getInfoNotaCredito().getObligadoContabilidad());
        return map;
    }
    
    @Override
    public Map<String,Object> getMapTotales()
    {
        /**
         * Calcular los valores de los subtotales 0 y con impuestos  
         * Todo: Unir este calculo con el de facturas porque es el mismo 
         */
        List<TotalImpuesto> impuestos=comprobante.getInfoNotaCredito().getTotalImpuestos();
        //comprobante.getDetalles();
        /*BigDecimal subTotalCero=BigDecimal.ZERO;
        BigDecimal subTotalImpuesto=BigDecimal.ZERO;
        BigDecimal iva=BigDecimal.ZERO;
        //BigDecimal descuentos=BigDecimal.ZERO;
        BigDecimal ice=BigDecimal.ZERO;
        
        for (TotalImpuesto impuesto : impuestos) {
            if(impuesto.getValor().compareTo(BigDecimal.ZERO)==0)
            {
                //subTotalCero=subTotalCero.add(impuesto.getBaseImponible().add(new BigDecimal(impuesto.getDescuentoAdicional())));
                subTotalCero=subTotalCero.add(impuesto.getBaseImponible());
                
            }
            else
            {
                //subTotalImpuesto=subTotalImpuesto.add(impuesto.getBaseImponible().add(new BigDecimal(impuesto.getDescuentoAdicional())));
                subTotalImpuesto=subTotalImpuesto.add(impuesto.getBaseImponible());
                iva=iva.add(impuesto.getValor());
            }
        }*/
        ImpuestosTotalesResponse respuesta= calcularImpuestos(impuestos);
        
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("subtotal_cero",respuesta.subTotalCero.toString());
        map.put("subtotal",respuesta.subTotalImpuesto.toString());
        map.put("descuento",comprobante.obtenerTotalDescuentos().toString());
        //map.put("iva",comprobante.getInfoNotaCredito().getTotalSinImpuestos().subtract(comprobante.getInfoNotaCredito().getValorModificacion()).toString());
        //map.put("iva",comprobante.obtenerTotalImpuestos().toString());
        map.put("iva",respuesta.iva.toString());
        map.put("total",comprobante.getInfoNotaCredito().getValorModificacion()+"");
        map.put("ice",respuesta.ice.toString());
        /**
         * Falta setear el iva que se esta usando en el sistema
         */
        map.put("iva_porcentaje","12");
        
        ComprobanteEnum enumerador=ComprobanteEnum.getEnumByCodigo(comprobante.getInfoNotaCredito().getCodDocModificado());
        map.put("comprobanteModificado",enumerador.getNombre()+": "+comprobante.getInfoNotaCredito().getNumDocModificado());
        map.put("razonModificado",comprobante.getInfoNotaCredito().getMotivo());
        map.put("fechaDocumentoModificado",comprobante.getInfoNotaCredito().getFechaEmisionDocSustento());
        
        return map;
    }
    
    @Override
    public List<Object> getDetalles()
    {
        List<Object> detalles=new ArrayList<Object>();
        List<DetalleNotaCreditoComprobante> detalleComprobante=comprobante.getDetalles();
        for (DetalleNotaCreditoComprobante detalleFacturaComprobante : detalleComprobante) {
            DetalleReporteData data=new DetalleReporteData();
            data.setCantidad(detalleFacturaComprobante.getCantidad()+"");
            data.setCodigo(detalleFacturaComprobante.getCodigoInterno());
            data.setDescripcion(detalleFacturaComprobante.getDescripcion());
            data.setDescuento(detalleFacturaComprobante.getDescuento()+"");
            data.setPrecio_total(detalleFacturaComprobante.getPrecioTotalSinImpuesto()+"");
            data.setPrecio_unitario(detalleFacturaComprobante.getPrecioUnitario()+"");
            detalles.add(data);
        }
        
        return detalles;
    }

    @Override
    public List getListFormasPago() {
        return null;
    }

   
}