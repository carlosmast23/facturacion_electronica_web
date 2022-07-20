/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @auhor
 */

@Entity
@Table(name = "COMPRA_ITEM_REEMBOLSO")
public class CompraFacturaReembolso implements Serializable
{
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JoinColumn(name="FACTURA_ID")
    @ManyToOne(optional = false)
    private Factura factura;
    
    @JoinColumn(name="COMPRA_ID")
    @ManyToOne
    private Compra compra;
    
    @Column(name = "TIPO_COMPROBANTE")
    private String tipoComprobanteReemb;
    
    @Column(name = "TP_ID_PROV")
    private String tpIdProvReemb;
    
    @Column(name = "ID_PROV")
    private String idProvReemb;
    
    @Column(name = "ESTABLECIMIENTO")
    private String establecimientoReemb;
    
    @Column(name = "PUNTO_EMISION")
    private String puntoEmisionReemb;
    
    @Column(name = "SECUENCIAL")
    private String secuencialReemb;
    
    @Column(name = "FECHA_EMISION")
    private Date fechaEmisionReemb;
    
    @Column(name = "AUTORIZACION")
    private String autorizacionReemb;
    
    @Column(name = "BASE_IMPONIBLE")
    private BigDecimal baseImponibleReemb;
    
    @Column(name = "BASE_IMP_GRAV")
    private BigDecimal baseImpGravReemb;
    
    @Column(name = "BASE_NO_GRAIVA")
    private BigDecimal baseNoGraIvaReemb;
    
    @Column(name = "BASE_IMP_EXE")
    private BigDecimal baseImpExeReemb;
    
    @Column(name = "MONTO_ICE")
    private BigDecimal montoIceRemb;
    
    @Column(name = "MONTO_IVA")
    private BigDecimal montoIvaRemb;
    

    public CompraFacturaReembolso() {
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public String getTipoComprobanteReemb() {
        return tipoComprobanteReemb;
    }

    public void setTipoComprobanteReemb(String tipoComprobanteReemb) {
        this.tipoComprobanteReemb = tipoComprobanteReemb;
    }

    public String getTpIdProvReemb() {
        return tpIdProvReemb;
    }

    public void setTpIdProvReemb(String tpIdProvReemb) {
        this.tpIdProvReemb = tpIdProvReemb;
    }

    public String getIdProvReemb() {
        return idProvReemb;
    }

    public void setIdProvReemb(String idProvReemb) {
        this.idProvReemb = idProvReemb;
    }

    public String getEstablecimientoReemb() {
        return establecimientoReemb;
    }

    public void setEstablecimientoReemb(String establecimientoReemb) {
        this.establecimientoReemb = establecimientoReemb;
    }

    public String getPuntoEmisionReemb() {
        return puntoEmisionReemb;
    }

    public void setPuntoEmisionReemb(String puntoEmisionReemb) {
        this.puntoEmisionReemb = puntoEmisionReemb;
    }

    public String getSecuencialReemb() {
        return secuencialReemb;
    }

    public void setSecuencialReemb(String secuencialReemb) {
        this.secuencialReemb = secuencialReemb;
    }

    public Date getFechaEmisionReemb() {
        return fechaEmisionReemb;
    }

    public void setFechaEmisionReemb(Date fechaEmisionReemb) {
        this.fechaEmisionReemb = fechaEmisionReemb;
    }

    public String getAutorizacionReemb() {
        return autorizacionReemb;
    }

    public void setAutorizacionReemb(String autorizacionReemb) {
        this.autorizacionReemb = autorizacionReemb;
    }

    public BigDecimal getBaseImponibleReemb() {
        return baseImponibleReemb;
    }

    public void setBaseImponibleReemb(BigDecimal baseImponibleReemb) {
        this.baseImponibleReemb = baseImponibleReemb;
    }

    public BigDecimal getBaseImpGravReemb() {
        return baseImpGravReemb;
    }

    public void setBaseImpGravReemb(BigDecimal baseImpGravReemb) {
        this.baseImpGravReemb = baseImpGravReemb;
    }

    public BigDecimal getBaseNoGraIvaReemb() {
        return baseNoGraIvaReemb;
    }

    public void setBaseNoGraIvaReemb(BigDecimal baseNoGraIvaReemb) {
        this.baseNoGraIvaReemb = baseNoGraIvaReemb;
    }

    public BigDecimal getBaseImpExeReemb() {
        return baseImpExeReemb;
    }

    public void setBaseImpExeReemb(BigDecimal baseImpExeReemb) {
        this.baseImpExeReemb = baseImpExeReemb;
    }

    public BigDecimal getMontoIceRemb() {
        return montoIceRemb;
    }

    public void setMontoIceRemb(BigDecimal montoIceRemb) {
        this.montoIceRemb = montoIceRemb;
    }

    public BigDecimal getMontoIvaRemb() {
        return montoIvaRemb;
    }

    public void setMontoIvaRemb(BigDecimal montoIvaRemb) {
        this.montoIvaRemb = montoIvaRemb;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CompraFacturaReembolso other = (CompraFacturaReembolso) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
    
}
