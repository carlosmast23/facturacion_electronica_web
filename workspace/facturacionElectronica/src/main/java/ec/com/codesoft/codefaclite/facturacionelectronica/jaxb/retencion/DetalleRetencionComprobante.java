/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.retencion;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = 
        {"codigo",
            "codigoRetencion",
            "baseImponible",
            "porcentajeRetener",
            "valorRetenido",
            "codDocSustento",
            "numDocSustento",
            "fechaEmisionDocSustento"})
/**
 *
 * @author
 */
public class DetalleRetencionComprobante implements Serializable{
    /**
     * El codigo corresponde al tipo de Retencion ejemplos
     * | IMPUESTO A RETENER | CODIGO |
     * | RENTA              | 1      |
     * | IVA                | 2      |
     * | ISD                | 6      |
     */
    private String codigo;
    private String codigoRetencion;
    private BigDecimal baseImponible;
    private BigDecimal porcentajeRetener;
    private BigDecimal valorRetenido;
    private String codDocSustento;
    private String numDocSustento;
    private String fechaEmisionDocSustento;

    public DetalleRetencionComprobante() {
    }

    @XmlElement(name="codigo")    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    @XmlElement(name="codigoRetencion")    
    public String getCodigoRetencion() {
        return codigoRetencion;
    }

    public void setCodigoRetencion(String codigoRetencion) {
        this.codigoRetencion = codigoRetencion;
    }

    @XmlElement(name="baseImponible")    
    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    @XmlElement(name="porcentajeRetener")    
    public BigDecimal getPorcentajeRetener() {
        return porcentajeRetener;
    }

    public void setPorcentajeRetener(BigDecimal porcentajeRetener) {
        this.porcentajeRetener = porcentajeRetener;
    }

    @XmlElement(name="valorRetenido")    
    public BigDecimal getValorRetenido() {
        return valorRetenido;
    }

    public void setValorRetenido(BigDecimal valorRetenido) {
        this.valorRetenido = valorRetenido;
    }

    @XmlElement(name="codDocSustento")    
    public String getCodDocSustento() {
        return codDocSustento;
    }

    public void setCodDocSustento(String codDocSustento) {
        this.codDocSustento = codDocSustento;
    }

    @XmlElement(name="numDocSustento")    
    public String getNumDocSustento() {
        return numDocSustento;
    }

    public void setNumDocSustento(String numDocSustento) {
        this.numDocSustento = numDocSustento;
    }

    @XmlElement(name="fechaEmisionDocSustento")    
    public String getFechaEmisionDocSustento() {
        return fechaEmisionDocSustento;
    }

    public void setFechaEmisionDocSustento(String fechaEmisionDocSustento) {
        this.fechaEmisionDocSustento = fechaEmisionDocSustento;
    }
    
    
    
    
}
