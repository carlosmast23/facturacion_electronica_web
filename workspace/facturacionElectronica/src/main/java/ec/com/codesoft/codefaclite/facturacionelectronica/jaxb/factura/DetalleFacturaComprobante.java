/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.DetalleComprobanteAbstract;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.ImpuestoComprobante;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlType(propOrder = {
    "codigoPrincipal",
    "descripcion",
    "cantidad",
    "precioUnitario",
    "precioSinSubsidio",
    "descuento",
    "precioTotalSinImpuesto",
    "impuestos"})
public class DetalleFacturaComprobante extends DetalleComprobanteAbstract {

    private String descripcion;
    //private BigDecimal cantidad;
    //private BigDecimal precioUnitario;
    //private BigDecimal descuento;
    //Precio Unitario*Cantidad-Descuento
    private BigDecimal precioTotalSinImpuesto;

    private BigDecimal precioSinSubsidio;

    private String codigoPrincipal;

    //@XmlElementWrapper(name = "formasDePago")
    //@XmlElement(name = "formaPago")
    private List<ImpuestoComprobante> impuestos;

    public DetalleFacturaComprobante() {
    }

    @XmlElement(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlElement(name = "precioTotalSinImpuesto")
    public BigDecimal getPrecioTotalSinImpuesto() {
        return precioTotalSinImpuesto;
    }

    public void setPrecioTotalSinImpuesto(BigDecimal precioTotalSinImpuesto) {
        this.precioTotalSinImpuesto = precioTotalSinImpuesto;
    }

    @XmlElementWrapper(name = "impuestos")
    @XmlElement(name = "impuesto")
    public List<ImpuestoComprobante> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(List<ImpuestoComprobante> impuestos) {
        this.impuestos = impuestos;
    }

    @XmlElement(name = "codigoPrincipal")
    public String getCodigoPrincipal() {
        return codigoPrincipal;
    }

    public void setCodigoPrincipal(String codigoPrincipal) {
        this.codigoPrincipal = codigoPrincipal;
    }

    @XmlElement(name = "precioSinSubsidio")
    public BigDecimal getPrecioSinSubsidio() {
        return precioSinSubsidio;
    }

    public void setPrecioSinSubsidio(BigDecimal precioSinSubsidio) {
        this.precioSinSubsidio = precioSinSubsidio;
    }

    /**
     * Metodos Adicionales
     */
    public BigDecimal calcularSubsidioDetalle() {
        
        if(getPrecioSinSubsidio()==null)
        {
            return BigDecimal.ZERO;
        }
        
        BigDecimal subsidioTotal=BigDecimal.ZERO;
        for (ImpuestoComprobante impuesto : getImpuestos()) {

            if (impuesto.getCodigo().equals("2"))//TODO: Parametriza esta valor por el momento e codigo 1 significa IVA
            {
                if (impuesto.getValor().compareTo(BigDecimal.ZERO) == 0) {
                    subsidioTotal = subsidioTotal.add(getPrecioSinSubsidio().multiply(getCantidad()).subtract(getPrecioTotalSinImpuesto())).setScale(2,BigDecimal.ROUND_HALF_UP);

                } else {
                    BigDecimal constanteImpuesto=impuesto.getTarifa().divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP).add(BigDecimal.ONE);
                    BigDecimal subsidioTotalTmp=constanteImpuesto.multiply(getPrecioSinSubsidio());
                    subsidioTotalTmp=subsidioTotalTmp.multiply(getCantidad());
                    //subsidioTotal = subsidioTotalTmp.setScale(2, BigDecimal.ROUND_HALF_UP);
                    
                    //Descontar el valor del valor pagado menos el subsidio añadiendo el iva del producto
                    BigDecimal precioTotal=getPrecioTotalSinImpuesto();
                    precioTotal=precioTotal.multiply(constanteImpuesto);
                    subsidioTotal=subsidioTotalTmp.subtract(precioTotal).setScale(2, BigDecimal.ROUND_HALF_UP);

                }
            } // Solo me interesa este codigo y no el que diga ICE
        }
        return subsidioTotal;
    }

}
