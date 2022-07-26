/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.DetalleComprobanteAbstract;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.DetalleAdicional;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.ImpuestoComprobante;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author
 */
@XmlType(propOrder = 
        {"codigoInterno",
            "codigoAdicional",
            "descripcion",
            "cantidad",
            "precioUnitario",
            "descuento",
            "precioTotalSinImpuesto",
            "detallesAdicionales",
            "impuestos"})
public class DetalleNotaCreditoComprobante extends DetalleComprobanteAbstract{
    private String codigoInterno;
    private String codigoAdicional;
    private String descripcion;
    //private BigDecimal cantidad;
    //private BigDecimal precioUnitario;
    //private BigDecimal descuento;
    private BigDecimal precioTotalSinImpuesto;

        
    private List<DetalleAdicional> detallesAdicionales;
    
    private List<ImpuestoComprobante> impuestos;

    public DetalleNotaCreditoComprobante() {
    }
    
    @XmlElement(name="codigoInterno")
    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    @XmlElement(name="codigoAdicional")    
    public String getCodigoAdicional() {
        return codigoAdicional;
    }

    public void setCodigoAdicional(String codigoAdicional) {
        this.codigoAdicional = codigoAdicional;
    }
    
    @XmlElement(name="descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    @XmlElement(name="precioTotalSinImpuesto")
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
    
    @XmlElementWrapper(name = "detallesAdicionales")
    @XmlElement(name = "detAdicional")
    public List<DetalleAdicional> getDetallesAdicionales() {
        return detallesAdicionales;
    }

    public void setDetallesAdicionales(List<DetalleAdicional> detallesAdicionales) {
        this.detallesAdicionales = detallesAdicionales;
    }
    

    
}
