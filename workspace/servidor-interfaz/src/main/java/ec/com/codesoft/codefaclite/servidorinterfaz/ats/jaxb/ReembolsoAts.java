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
 *TODO: Poner los mismo nombrs y etiquetas de xml y de la clase
 * @auhor
 */
@XmlType(propOrder = {
    "tipoComprobanteRemb",
    "tpIdProvRemb",
    "idProvRemb",
    "establecimientoRemb",
    "puntoEmisionRemb",
    "secuencialRemb",
    "fechaEmisionRemb",
    "autorizacionRemb",
    "baseImponibleRemb",
    "baseImpGravRemb",
    "baseNoGraIvaRemb",
    "baseImpExeReembRemb",
    "montoIceRemb",
    "montoIvaRemb"})
public class ReembolsoAts implements Serializable{
    
    private String tipoComprobanteRemb;
    private String tpIdProvRemb;
    private String idProvRemb;
    private String establecimientoRemb;
    private String puntoEmisionRemb;
    private String secuencialRemb;
    private String fechaEmisionRemb;
    private String autorizacionRemb;
    private BigDecimal baseImponibleRemb;
    private BigDecimal baseImpGravRemb;
    private BigDecimal baseNoGraIvaRemb;
    private BigDecimal baseImpExeReembRemb;
    private BigDecimal montoIceRemb;
    private BigDecimal montoIvaRemb;

    public ReembolsoAts() {
    }

    @XmlElement(name = "tipoComprobanteReemb")
    public String getTipoComprobanteRemb() {
        return tipoComprobanteRemb;
    }

    public void setTipoComprobanteRemb(String tipoComprobanteRemb) {
        this.tipoComprobanteRemb = tipoComprobanteRemb;
    }

    @XmlElement(name = "tpIdProvReemb")
    public String getTpIdProvRemb() {
        return tpIdProvRemb;
    }

    public void setTpIdProvRemb(String tpIdProvRemb) {
        this.tpIdProvRemb = tpIdProvRemb;
    }

    @XmlElement(name = "idProvReemb")
    public String getIdProvRemb() {
        return idProvRemb;
    }

    public void setIdProvRemb(String idProvRemb) {
        this.idProvRemb = idProvRemb;
    }

    @XmlElement(name = "establecimientoReemb")
    public String getEstablecimientoRemb() {
        return establecimientoRemb;
    }

    public void setEstablecimientoRemb(String establecimientoRemb) {
        this.establecimientoRemb = establecimientoRemb;
    }

    @XmlElement(name = "puntoEmisionReemb")
    public String getPuntoEmisionRemb() {
        return puntoEmisionRemb;
    }

    public void setPuntoEmisionRemb(String puntoEmisionRemb) {
        this.puntoEmisionRemb = puntoEmisionRemb;
    }

    @XmlElement(name = "secuencialReemb")
    public String getSecuencialRemb() {
        return secuencialRemb;
    }

    public void setSecuencialRemb(String secuencialRemb) {
        this.secuencialRemb = secuencialRemb;
    }

    @XmlElement(name = "fechaEmisionReemb")
    public String getFechaEmisionRemb() {
        return fechaEmisionRemb;
    }

    public void setFechaEmisionRemb(String fechaEmisionRemb) {
        this.fechaEmisionRemb = fechaEmisionRemb;
    }

    @XmlElement(name = "autorizacionReemb")
    public String getAutorizacionRemb() {
        return autorizacionRemb;
    }

    public void setAutorizacionRemb(String autorizacionRemb) {
        this.autorizacionRemb = autorizacionRemb;
    }

    @XmlElement(name = "baseImponibleReemb")
    public BigDecimal getBaseImponibleRemb() {
        return baseImponibleRemb;
    }

    public void setBaseImponibleRemb(BigDecimal baseImponibleRemb) {
        this.baseImponibleRemb = baseImponibleRemb;
    }

    @XmlElement(name = "baseImpGravReemb")
    public BigDecimal getBaseImpGravRemb() {
        return baseImpGravRemb;
    }

    public void setBaseImpGravRemb(BigDecimal baseImpGravRemb) {
        this.baseImpGravRemb = baseImpGravRemb;
    }

    @XmlElement(name = "baseNoGraIvaReemb")
    public BigDecimal getBaseNoGraIvaRemb() {
        return baseNoGraIvaRemb;
    }

    public void setBaseNoGraIvaRemb(BigDecimal baseNoGraIvaRemb) {
        this.baseNoGraIvaRemb = baseNoGraIvaRemb;
    }

    @XmlElement(name = "baseImpExeReemb")
    public BigDecimal getBaseImpExeReembRemb() {
        return baseImpExeReembRemb;
    }

    public void setBaseImpExeReembRemb(BigDecimal baseImpExeReembRemb) {
        this.baseImpExeReembRemb = baseImpExeReembRemb;
    }

    @XmlElement(name = "montoIceRemb")
    public BigDecimal getMontoIceRemb() {
        return montoIceRemb;
    }

    public void setMontoIceRemb(BigDecimal montoIceRemb) {
        this.montoIceRemb = montoIceRemb;
    }

    @XmlElement(name = "montoIvaRemb")
    public BigDecimal getMontoIvaRemb() {
        return montoIvaRemb;
    }

    public void setMontoIvaRemb(BigDecimal montoIvaRemb) {
        this.montoIvaRemb = montoIvaRemb;
    }
    
    
    
}
