/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Carlos
 */
@XmlTransient
public abstract class DetalleComprobanteAbstract implements Serializable{
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal descuento;
    
    @XmlElement(name="cantidad")
    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }
    
    @XmlElement(name="precioUnitario")
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    
    @XmlElement(name="descuento")
    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }
    
}
