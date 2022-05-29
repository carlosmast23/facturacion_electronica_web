/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.guiaRetencion;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlType(propOrder
        = {"codigoInterno",
            "codigoAdicional",
            "descripcion",
            "cantidad"})
public class DetalleGuiaRemisionComprobante implements Serializable{
    private String codigoInterno;
    private String codigoAdicional;
    private String descripcion;
    private Integer cantidad;

    public DetalleGuiaRemisionComprobante() {
    }

    @XmlElement(name = "codigoInterno")
    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    @XmlElement(name = "codigoAdicional")
    public String getCodigoAdicional() {
        return codigoAdicional;
    }

    public void setCodigoAdicional(String codigoAdicional) {
        this.codigoAdicional = codigoAdicional;
    }

    @XmlElement(name = "descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlElement(name = "cantidad")
    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    
    
}
