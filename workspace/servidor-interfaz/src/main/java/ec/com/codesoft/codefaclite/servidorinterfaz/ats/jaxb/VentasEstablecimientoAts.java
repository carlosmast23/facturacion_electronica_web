/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author
 */
@XmlType(propOrder = {"codEstab","ventasEstab","ivaComp"})
public class VentasEstablecimientoAts implements Serializable{
    private String codEstab;
    private BigDecimal ventasEstab;
    private BigDecimal ivaComp;

    public VentasEstablecimientoAts() {
    }

    @XmlElement(name = "codEstab")
    public String getCodEstab() {
        return codEstab;
    }

    @XmlElement(name = "ventasEstab")
    public BigDecimal getVentasEstab() {
        return ventasEstab;
    }

    @XmlElement(name = "ivaComp")
    public BigDecimal getIvaComp() {
        return ivaComp;
    }

    public void setCodEstab(String codEstab) {
        this.codEstab = codEstab;
    }

    public void setVentasEstab(BigDecimal ventasEstab) {
        this.ventasEstab = ventasEstab;
    }

    public void setIvaComp(BigDecimal ivaComp) {
        this.ivaComp = ivaComp;
    }
    
       
    
    
}
