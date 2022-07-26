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
@XmlType(propOrder = {
    "codRetAir",
    "baseImpAir",
    "porcentajeAir",
    "valRetAir"})
public class AirAts implements Serializable{
    private String codRetAir;
    private BigDecimal baseImpAir;
    private BigDecimal porcentajeAir;
    private BigDecimal valRetAir;

    public AirAts() {
    }

    @XmlElement(name = "codRetAir")
    public String getCodRetAir() {
        return codRetAir;
    }

    @XmlElement(name = "baseImpAir")
    public BigDecimal getBaseImpAir() {
        return baseImpAir;
    }

    @XmlElement(name = "porcentajeAir")
    public BigDecimal getPorcentajeAir() {
        return porcentajeAir;
    }

    @XmlElement(name = "valRetAir")
    public BigDecimal getValRetAir() {
        return valRetAir;
    }

    public void setCodRetAir(String codRetAir) {
        this.codRetAir = codRetAir;
    }

    public void setBaseImpAir(BigDecimal baseImpAir) {
        this.baseImpAir = baseImpAir;
    }

    public void setPorcentajeAir(BigDecimal porcentajeAir) {
        this.porcentajeAir = porcentajeAir;
    }

    public void setValRetAir(BigDecimal valRetAir) {
        this.valRetAir = valRetAir;
    }
    
    
    
}
