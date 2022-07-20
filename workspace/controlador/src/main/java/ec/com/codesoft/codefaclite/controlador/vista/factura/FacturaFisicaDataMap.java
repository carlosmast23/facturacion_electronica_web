/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.factura;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DatosAdicionalesComprobanteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @auhor
 */
public class FacturaFisicaDataMap {
    public String fechaEmision;
    public String razonSocial;
    public String direccion;
    public String telefono;
    public String correoElectronico;
    public String identificacion;
    public String subtotal;
    public String descuento;
    public String total;
    public String subtotalAntesImpuestos;
    public String subtotalImpuesto;
    public String subtotalSinImpuesto;
    public String subtotalConDescuento;
    public String valorIva;
    public String iva;
    public String guiaRemision;
    
    public Boolean imprimirConTitulos;

    public FacturaFisicaDataMap(Boolean imprimirConTitulos){
        this.imprimirConTitulos = imprimirConTitulos;
    }
    
    public Map<String,Object> getMap(Factura factura,DocumentoEnum documento,String ivaStr)
    {
        SimpleDateFormat sdf=ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA;
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("fechaEmision",aplicarFormato("Fecha Emisión: ", sdf.format(factura.getFechaEmision())));
        parametros.put("razonSocial",aplicarFormato("Razón Social: ",factura.getRazonSocial()));
        parametros.put("direccion",aplicarFormato("Dirección: ",factura.getDireccion()));
        parametros.put("telefono",aplicarFormato("Telefono: ",factura.getTelefono()));
        parametros.put("correoElectronico",aplicarFormato("Correo: ", (factura.getCliente().getCorreoElectronico() != null) ? factura.getCliente().getCorreoElectronico() : ""));
        parametros.put("identificacion",aplicarFormato("Identificación: ",factura.getIdentificacion()));
        parametros.put("guiaRemision",aplicarFormato("Guía Remisión: ",obtenerGuiaRemision(factura)));

        //Datos cuando es una nota de venta
        if(DocumentoEnum.NOTA_VENTA.equals(documento))
        {
            parametros.put("subtotal",aplicarFormato("Subtotal: ",factura.getSubtotalImpuestos().add(factura.getSubtotalSinImpuestos()).toString()));
            parametros.put("descuento",aplicarFormato("Descuento: ",factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()).toString()));
            parametros.put("total",aplicarFormato("Total: ",factura.getTotal() + ""));        
        }
        else
        {   //Datos cuando es una factura
            parametros.put("subtotalAntesImpuestos",aplicarFormato("Subtotal 1: ",factura.getSubtotalImpuestos().add(factura.getSubtotalSinImpuestos()).toString()));
            parametros.put("subtotalImpuesto",aplicarFormato("Subtotal 2: ",factura.getSubtotalImpuestos().toString()));
            parametros.put("subtotalSinImpuesto",aplicarFormato("Subtotal 3: ",factura.getSubtotalSinImpuestos().toString()));
            parametros.put("descuento",aplicarFormato("Descuento: ",factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()).toString()));
            parametros.put("subtotalConDescuento",aplicarFormato("Subtotal 4: ",factura.getSubtotalImpuestos().add(factura.getSubtotalSinImpuestos()).subtract((factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()))).toString()));
            parametros.put("valorIva",aplicarFormato("Valor Iva: ",factura.getIva().toString()));
            parametros.put("total",aplicarFormato("Total: ",factura.getTotal() + ""));
            //String ivaStr = session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO).valor;
            parametros.put("iva",aplicarFormato("Iva: ",ivaStr));
        
        }
        
        //Agregar forma de pago
        parametros.putAll(getMapFormaPagoReporteFacturaFisica(factura));
        
        return parametros;
    
    }
    
    private String obtenerGuiaRemision(Factura factura)
    {
        for(FacturaAdicional facturaAdicional : factura.getDatosAdicionales())
        {            
            if(facturaAdicional.getTipoEnum().equals(ComprobanteAdicional.Tipo.TIPO_GUIA_REMISION))
            {
                return facturaAdicional.getValor();
            }
        }        
        return "";
    }
    
    private String aplicarFormato(String titulo,String dato)
    {
        String datoFormato=dato;
        if(imprimirConTitulos)
        {
            if(dato!=null)
            {
                datoFormato="<b>"+titulo+"</b>"+dato;
            }
            else
            {
                datoFormato="<b>"+titulo+"</b>";
            }
        }
        return datoFormato;
        
    }
    
    private Map<String,Object> getMapFormaPagoReporteFacturaFisica(Factura venta)
    {
        Map<String, Object> parametros = new HashMap<String, Object>();
        
        if(venta.getFormaPagos()!=null && venta.getFormaPagos().size()>0)
        {
            for (FormaPago formaPago : venta.getFormaPagos()) 
            {
                //FormaPago formaPago=venta.getFormaPagos().get(0);
                if(formaPago.getSriFormaPago().getAlias().equals("Efectivo"))
                {
                    parametros.put("formaPagoEfectivo","X");

                }else if(formaPago.getSriFormaPago().getAlias().equals("Otros"))
                {
                    //parametros.put("formaPagoCheque","X");
                    parametros.put("formaPagoOtros","X");

                }else if(formaPago.getSriFormaPago().getAlias().equals("Dinero electrónico"))
                {
                    parametros.put("formaPagoDineroElec","X");
                }else if(formaPago.getSriFormaPago().getAlias().equals("Tarjeta crédito"))
                {
                    parametros.put("formaPagoTarjetaCred","X");
                }else
                {
                    parametros.put("formaPagoOtros","X");
                }
            }
            
        }
        else
        {
            parametros.put("formaPagoEfectivo","X");
        }
        return parametros;
    }
    
}
