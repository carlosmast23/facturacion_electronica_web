/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "RETENCION_DETALLE")
@XmlRootElement
public class RetencionDetalle implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;

    @Column(name = "BASE_IMPONIBLE")
    private BigDecimal baseImponible;
    
    @Column(name = "PORCENTAJE_RETENER")
    private BigDecimal porcentajeRetener;
    
    @Column(name = "VALOR_RETENIDO")
    private BigDecimal valorRetenido;
    
    @Column(name = "CODIGO_SRI")
    private String codigoSri;
    
    @Column(name = "CODIGO_RETENCION_SRI")
    private String codigoRetencionSri;
    
    @JoinColumn(name = "RETENCION_ID")
    @ManyToOne
    private Retencion retencion;

    public RetencionDetalle() {
    }

    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    public BigDecimal getPorcentajeRetener() {
        return porcentajeRetener;
    }

    public void setPorcentajeRetener(BigDecimal porcentajeRetener) {
        this.porcentajeRetener = porcentajeRetener;
    }

    public BigDecimal getValorRetenido() {
        return valorRetenido;
    }

    public void setValorRetenido(BigDecimal valorRetenido) {
        this.valorRetenido = valorRetenido;
    }

    public String getCodigoSri() {
        return codigoSri;
    }

    public void setCodigoSri(String codigoSri) {
        this.codigoSri = codigoSri;
    }

    public String getCodigoRetencionSri() {
        return codigoRetencionSri;
    }

    public void setCodigoRetencionSri(String codigoRetencionSri) {
        this.codigoRetencionSri = codigoRetencionSri;
    }

    public Retencion getRetencion() {
        return retencion;
    }

    public void setRetencion(Retencion retencion) {
        this.retencion = retencion;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    
}
