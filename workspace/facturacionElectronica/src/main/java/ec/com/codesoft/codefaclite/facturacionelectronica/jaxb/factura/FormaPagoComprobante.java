/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author
 */
@XmlType(propOrder = {"formaPago","total","plazo","unidadTiempo"})
public class FormaPagoComprobante implements Serializable{    
    
    private String formaPago;
    
    private BigDecimal total;
    
    private BigDecimal plazo;
    
    private String unidadTiempo;

    public FormaPagoComprobante() {
    }

    @XmlElement(name = "formaPago")
    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    @XmlElement(name = "total")
    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    @XmlElement(name = "plazo")
    public BigDecimal getPlazo() {
        return plazo;
    }

    public void setPlazo(BigDecimal plazo) {
        this.plazo = plazo;
    }

    @XmlElement(name = "unidadTiempo")
    public String getUnidadTiempo() {
        return unidadTiempo;
    }

    public void setUnidadTiempo(String unidadTiempo) {
        this.unidadTiempo = unidadTiempo;
    }
    
    
}
