/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.reporte;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronicoFacturaAndLiquidacionAbstract;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.DetalleFacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FormaPagoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.TotalImpuesto;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public abstract class FacturaLiquidacionCompraAbstractReport extends ComprobanteElectronicoReporte{
    
    private ComprobanteElectronicoFacturaAndLiquidacionAbstract comprobante;
    private Map<String,String> mapCodeAndNameFormaPago;

    public FacturaLiquidacionCompraAbstractReport(ComprobanteElectronicoFacturaAndLiquidacionAbstract comprobante) {
        super(comprobante);
        this.comprobante =comprobante;
    }
    
    
    
    @Override
    public Map<String,Object> getMapInfoCliente()
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("fecha_emision", comprobante.getInformacionComprobante().getFechaEmision());
        map.put("obligado_contabilidad",comprobante.getInformacionComprobante().getObligadoContabilidad());
        return map;
    }
    
    @Override
    public Map<String,Object> getMapTotales()
    {
        /**
         * Calcular los valores de los subtotales 0 y con impuestos         * 
         */
        List<TotalImpuesto> impuestos=comprobante.getInformacionComprobante().getTotalImpuestos();
        /*BigDecimal subTotalCero=BigDecimal.ZERO;
        BigDecimal subTotalImpuesto=BigDecimal.ZERO;
        BigDecimal iva=BigDecimal.ZERO;
        BigDecimal ice=BigDecimal.ZERO;
        
        for (TotalImpuesto impuesto : impuestos) {
            
            if(impuesto.getCodigo().equals("2"))//TODO: Parametriza esta valor por el momento e codigo 1 significa IVA
            {
                if(impuesto.getValor().compareTo(BigDecimal.ZERO)==0)
                {
                    subTotalCero=subTotalCero.add(impuesto.getBaseImponible());

                }
                else
                {
                    subTotalImpuesto=subTotalImpuesto.add(impuesto.getBaseImponible());
                    iva=iva.add(impuesto.getValor());
                }
            }
            else if(impuesto.getCodigo().equals("3")) //TODO: Parametrizar este valor por el momento significa ICE
            {
                ice=ice.add(impuesto.getValor());
            }
        }*/
        ImpuestosTotalesResponse respuesta= calcularImpuestos(impuestos);
        
        Map<String,Object> map=new HashMap<String,Object>();
        
        map.put("subtotal_cero",respuesta.subTotalCero.toString());
        map.put("subtotal",respuesta.subTotalImpuesto.toString());
        map.put("descuento",comprobante.getInformacionComprobante().getTotalDescuento().toString());
        
        map.put("ice",respuesta.ice.toString());
        map.put("iva",respuesta.iva.toString());
        map.put("total",totalFactura()+"");
        /**
         * Falta setear el iva que se esta usando en el sistema
         */
        map.put("iva_porcentaje","12");
        return map;
    }
    
    @Override
    public List<Object> getDetalles()
    {
        List<Object> detalles=new ArrayList<Object>();
        List<DetalleFacturaComprobante> detalleComprobante=comprobante.getDetalles();
        for (DetalleFacturaComprobante detalleFacturaComprobante : detalleComprobante) {
            DetalleReporteData data=new DetalleReporteData();
            data.setCantidad(detalleFacturaComprobante.getCantidad()+"");
            data.setCodigo(detalleFacturaComprobante.getCodigoPrincipal());
            data.setDescripcion(detalleFacturaComprobante.getDescripcion());
            data.setPorcentajeIva(detalleFacturaComprobante.getImpuestos().get(0).getTarifa().setScale(0, RoundingMode.HALF_UP)+"");
            data.setDescuento(detalleFacturaComprobante.getDescuento()+"");
            data.setPrecio_total(detalleFacturaComprobante.getPrecioTotalSinImpuesto()+"");
            data.setPrecio_unitario(detalleFacturaComprobante.getPrecioUnitario()+"");
            if(detalleFacturaComprobante.calcularSubsidioDetalle().compareTo(BigDecimal.ZERO)==0)
            {
                data.setSubsidio("0");
            }
            else
            {
                data.setSubsidio(detalleFacturaComprobante.getPrecioSinSubsidio()+"");
                //data.setSubsidio(
                //        detalleFacturaComprobante.calcularSubsidioDetalle().subtract(detalleFacturaComprobante.getPrecioTotalSinImpuesto())+""); //Valor el subsidio para emitir en la factura
            }
            detalles.add(data);
        }
        
        return detalles;
    }

    @Override
    public List getListFormasPago() {
        List<FormaPagoComprobante> formasPago=comprobante.getInformacionComprobante().getFormaPagos();
        List<FormaPagoData> formaPagosData=new ArrayList<FormaPagoData>();
        if(formasPago!=null)
        {
            for (FormaPagoComprobante formaPagoComprobante : formasPago) {
                FormaPagoData formaPagoData=new FormaPagoData();
                if(mapCodeAndNameFormaPago!=null)
                {
                    String nombreFormaPago=mapCodeAndNameFormaPago.get(formaPagoComprobante.getFormaPago());
                    formaPagoData.setNombre((nombreFormaPago!=null)?nombreFormaPago:"Sin Nombre");
                }
                else
                {
                    formaPagoData.setNombre("Sin Nombre");
                }
                formaPagoData.setValor(formaPagoComprobante.getTotal().toString());            
                formaPagosData.add(formaPagoData);
            }
        }
        return formaPagosData;
    }

    public void setMapCodeAndNameFormaPago(Map<String, String> mapCodeAndNameFormaPago) {
        this.mapCodeAndNameFormaPago = mapCodeAndNameFormaPago;
    }
    
    public BigDecimal totalFactura()
    {
        return comprobante.getInformacionComprobante().getImporteTotal();
    }
    
}
