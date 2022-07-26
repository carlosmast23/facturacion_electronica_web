/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author
 */
@XmlType(propOrder = {
    "tipoComprobante",
    "establecimiento",
    "puntoEmision",
    "secuencialInicio",
    "secuencialFin",
    "autorizacion"})
public class AnuladoAts implements Serializable{
    /**
     * Este valor corresponde al tipo documento que corresponde a la Tabla 4 del manual del sri
     */
    private String tipoComprobante;
    private String establecimiento;
    private String puntoEmision;
    private Integer  secuencialInicio;
    private Integer secuencialFin;
    private String autorizacion;

    public AnuladoAts() {
    }

    @XmlElement(name = "tipoComprobante")
    public String getTipoComprobante() {
        return tipoComprobante;
    }

    @XmlElement(name = "establecimiento")
    public String getEstablecimiento() {
        return establecimiento;
    }

    @XmlElement(name = "puntoEmision")
    public String getPuntoEmision() {
        return puntoEmision;
    }

    @XmlElement(name = "secuencialInicio")
    public Integer getSecuencialInicio() {
        return secuencialInicio;
    }

    @XmlElement(name = "secuencialFin")
    public Integer getSecuencialFin() {
        return secuencialFin;
    }

    @XmlElement(name = "autorizacion")
    public String getAutorizacion() {
        return autorizacion;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public void setPuntoEmision(String puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public void setSecuencialInicio(Integer secuencialInicio) {
        this.secuencialInicio = secuencialInicio;
    }

    public void setSecuencialFin(Integer secuencialFin) {
        this.secuencialFin = secuencialFin;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }
    
    
    
}
