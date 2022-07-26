/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.Compensacion;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.TotalImpuesto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author
 */
@XmlType(propOrder = {"fechaEmision",
    "dirEstablecimiento",
    "tipoIdentificacionComprador",
    "razonSocialComprador",
    "identificacionComprador",
    "contribuyenteEspecial",
    "obligadoContabilidad",
    "rise",
    "codDocModificado",
    "numDocModificado",
    "fechaEmisionDocSustento",
    "totalSinImpuestos",
    "compensaciones",
    "valorModificacion",
    "moneda",
    "totalModificacion",
    "totalImpuestos",
    "motivo",})
public class InformacionNotaCredito implements Serializable{

    private String fechaEmision;

    private String dirEstablecimiento;

    private String tipoIdentificacionComprador;

    private String razonSocialComprador;

    private String identificacionComprador;

    private String contribuyenteEspecial;

    private String obligadoContabilidad;

    private String rise;

    private String codDocModificado;

    private String numDocModificado;

    private String fechaEmisionDocSustento;

    /**
     * Es el valor de la nota de credito sin los impuestos
     */
    private BigDecimal totalSinImpuestos;

    /**
     * Es el valor total de la nota de credito incluido impuestos
     */
    private BigDecimal valorModificacion;

    private String moneda;

    private BigDecimal totalModificacion;

    private String motivo;
    
    private List<TotalImpuesto> totalImpuestos;
    private List<Compensacion> compensaciones;

    public InformacionNotaCredito() {
    }
    
     @XmlElement(name = "fechaEmision")   
    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    @XmlElement(name = "dirEstablecimiento")
    public String getDirEstablecimiento() {
        return dirEstablecimiento;
    }

    public void setDirEstablecimiento(String dirEstablecimiento) {
        this.dirEstablecimiento = dirEstablecimiento;
    }
    @XmlElement(name = "tipoIdentificacionComprador")
    public String getTipoIdentificacionComprador() {
        return tipoIdentificacionComprador;
    }

    public void setTipoIdentificacionComprador(String tipoIdentificacionComprador) {
        this.tipoIdentificacionComprador = tipoIdentificacionComprador;
    }
    @XmlElement(name = "razonSocialComprador")
    public String getRazonSocialComprador() {
        return razonSocialComprador;
    }

    public void setRazonSocialComprador(String razonSocialComprador) {
        this.razonSocialComprador = razonSocialComprador;
    }
    @XmlElement(name = "identificacionComprador")
    public String getIdentificacionComprador() {
        return identificacionComprador;
    }

    public void setIdentificacionComprador(String identificacionComprador) {
        this.identificacionComprador = identificacionComprador;
    }
    @XmlElement(name = "contribuyenteEspecial")
    public String getContribuyenteEspecial() {
        return contribuyenteEspecial;
    }

    public void setContribuyenteEspecial(String contribuyenteEspecial) {
        this.contribuyenteEspecial = contribuyenteEspecial;
    }
    @XmlElement(name = "obligadoContabilidad")
    public String getObligadoContabilidad() {
        return obligadoContabilidad;
    }

    public void setObligadoContabilidad(String obligadoContabilidad) {
        this.obligadoContabilidad = obligadoContabilidad;
    }
    @XmlElement(name = "rise")
    public String getRise() {
        return rise;
    }

    public void setRise(String rise) {
        this.rise = rise;
    }
    @XmlElement(name = "codDocModificado")
    public String getCodDocModificado() {
        return codDocModificado;
    }

    public void setCodDocModificado(String codDocModificado) {
        this.codDocModificado = codDocModificado;
    }
    @XmlElement(name = "numDocModificado")
    public String getNumDocModificado() {
        return numDocModificado;
    }

    public void setNumDocModificado(String numDocModificado) {
        this.numDocModificado = numDocModificado;
    }
    @XmlElement(name = "fechaEmisionDocSustento")
    public String getFechaEmisionDocSustento() {
        return fechaEmisionDocSustento;
    }

    public void setFechaEmisionDocSustento(String fechaEmisionDocSustento) {
        this.fechaEmisionDocSustento = fechaEmisionDocSustento;
    }
    @XmlElement(name = "totalSinImpuestos")
    public BigDecimal getTotalSinImpuestos() {
        return totalSinImpuestos;
    }

    public void setTotalSinImpuestos(BigDecimal totalSinImpuestos) {
        this.totalSinImpuestos = totalSinImpuestos;
    }
    @XmlElement(name = "valorModificacion")
    public BigDecimal getValorModificacion() {
        return valorModificacion;
    }

    public void setValorModificacion(BigDecimal valorModificacion) {
        this.valorModificacion = valorModificacion;
    }
    @XmlElement(name = "moneda")
    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }
    @XmlElement(name = "motivo")
    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
    @XmlElement(name = "totalModificacion")
    public BigDecimal getTotalModificacion() {
        return totalModificacion;
    }

    public void setTotalModificacion(BigDecimal totalModificacion) {
        this.totalModificacion = totalModificacion;
    }

    
    
    @XmlElementWrapper(name = "totalConImpuestos")
    @XmlElement(name = "totalImpuesto")
    public List<TotalImpuesto> getTotalImpuestos() {
        return totalImpuestos;
    }

    public void setTotalImpuestos(List<TotalImpuesto> totalImpuestos) {
        this.totalImpuestos = totalImpuestos;
    }

    @XmlElementWrapper(name = "compensaciones")
    @XmlElement(name = "compensacion")
    public List<Compensacion> getCompensaciones() {
        return compensaciones;
    }

    public void setCompensaciones(List<Compensacion> compensaciones) {
        this.compensaciones = compensaciones;
    }
    


    
    
}
