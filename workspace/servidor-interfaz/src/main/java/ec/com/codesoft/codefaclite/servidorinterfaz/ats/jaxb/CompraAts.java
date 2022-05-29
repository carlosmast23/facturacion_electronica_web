/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb;

import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlType(propOrder = {
    "codSustento",
    "tpIdProv",
    "idProv",
    "tipoComprobante",
    "parteRel",
    "fechaRegistro",
    "establecimiento",
    "puntoEmision",
    "secuencial",
    "fechaEmision",
    "autorizacion",
    "baseNoGraIva",
    "baseImponible",
    "baseImpGrav",
    "baseImpExe",
    "montoIce",
    "montoIva",
    "valRetBien10",
    "valRetServ20",
    "valorRetBienes",
    "valRetServ50",
    "valorRetServicios",
    "valRetServ100",
    "totbasesImpReemb",
    "pagoExteriorAts",
    "formasDePago",
    "detalleAir",
    "estabRetencion1",
    "ptoEmiRetencion1",
    "secRetencion1",
    "autRetencion1",
    "fechaEmiRet1",
    "reembolsos"})
public class CompraAts implements Serializable {

    private String codSustento;
    private String tpIdProv;
    private String idProv;
    private String tipoComprobante;
    private String parteRel;
    private String fechaRegistro;
    private String establecimiento;
    private String puntoEmision;
    private String secuencial;
    private String fechaEmision;
    private String autorizacion;
    private BigDecimal baseNoGraIva;
    private BigDecimal baseImponible;
    private BigDecimal baseImpGrav;
    private BigDecimal baseImpExe;
    private BigDecimal montoIce;
    private BigDecimal montoIva;
    private BigDecimal valRetBien10;
    private BigDecimal valRetServ20;
    //30% valor de retenciones
    private BigDecimal valorRetBienes;
    private BigDecimal valRetServ50;
    //70% valor de servicios
    private BigDecimal valorRetServicios;
    private BigDecimal valRetServ100;
    private BigDecimal totbasesImpReemb;
    private PagoExteriorAts pagoExteriorAts;
    //Todo: Queda pendiente hacer los pagos al exterior
    private List<FormaDePagoAts> formasDePago;
    private List<AirAts> detalleAir;

    private String estabRetencion1;
    private String ptoEmiRetencion1;
    /**
     * Secuencial del documento de retencion
     */
    private String secRetencion1;
    private String autRetencion1;
    private String fechaEmiRet1;
    
    private List<ReembolsoAts> reembolsos;

    public CompraAts() {
    }

    @XmlElement(name = "pagoExterior")
    public PagoExteriorAts getPagoExteriorAts() {
        return pagoExteriorAts;
    }

    @XmlElement(name = "codSustento")
    public String getCodSustento() {
        return codSustento;
    }

    @XmlElement(name = "tpIdProv")
    public String getTpIdProv() {
        return tpIdProv;
    }

    @XmlElement(name = "idProv")
    public String getIdProv() {
        return idProv;
    }

    @XmlElement(name = "tipoComprobante")
    public String getTipoComprobante() {
        return tipoComprobante;
    }

    @XmlElement(name = "parteRel")
    public String getParteRel() {
        return parteRel;
    }

    @XmlElement(name = "fechaRegistro")
    public String getFechaRegistro() {
        return fechaRegistro;
    }

    @XmlElement(name = "establecimiento")
    public String getEstablecimiento() {
        return establecimiento;
    }

    @XmlElement(name = "puntoEmision")
    public String getPuntoEmision() {
        return puntoEmision;
    }

    @XmlElement(name = "secuencial")
    public String getSecuencial() {
        return secuencial;
    }

    @XmlElement(name = "fechaEmision")
    public String getFechaEmision() {
        return fechaEmision;
    }

    @XmlElement(name = "autorizacion")
    public String getAutorizacion() {
        return autorizacion;
    }

    @XmlElement(name = "baseNoGraIva")
    public BigDecimal getBaseNoGraIva() {
        return baseNoGraIva;
    }

    @XmlElement(name = "baseImponible")
    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    @XmlElement(name = "baseImpGrav")
    public BigDecimal getBaseImpGrav() {
        return baseImpGrav;
    }

    @XmlElement(name = "baseImpExe")
    public BigDecimal getBaseImpExe() {
        return baseImpExe;
    }

    @XmlElement(name = "montoIce")
    public BigDecimal getMontoIce() {
        return montoIce;
    }

    @XmlElement(name = "montoIva")
    public BigDecimal getMontoIva() {
        return montoIva;
    }

    @XmlElement(name = "valRetBien10")
    public BigDecimal getValRetBien10() {
        return valRetBien10;
    }

    @XmlElement(name = "valRetServ20")
    public BigDecimal getValRetServ20() {
        return valRetServ20;
    }

    @XmlElement(name = "valorRetBienes")
    public BigDecimal getValorRetBienes() {
        return valorRetBienes;
    }

    @XmlElement(name = "valRetServ50")
    public BigDecimal getValRetServ50() {
        return valRetServ50;
    }

    @XmlElement(name = "valorRetServicios")
    public BigDecimal getValorRetServicios() {
        return valorRetServicios;
    }

    @XmlElement(name = "valRetServ100")
    public BigDecimal getValRetServ100() {
        return valRetServ100;
    }

    @XmlElement(name = "totbasesImpReemb")
    public BigDecimal getTotbasesImpReemb() {
        return totbasesImpReemb;
    }

    @XmlElementWrapper(name = "formasDePago")
    @XmlElement(name = "formaPago")
    public List<FormaDePagoAts> getFormasDePago() {
        return formasDePago;
    }

    @XmlElementWrapper(name = "air")
    @XmlElement(name = "detalleAir")
    public List<AirAts> getDetalleAir() {
        return detalleAir;
    }

    @XmlElement(name = "codSustento")
    public String getEstabRetencion1() {
        return estabRetencion1;
    }

    @XmlElement(name = "codSustento")
    public String getPtoEmiRetencion1() {
        return ptoEmiRetencion1;
    }

    @XmlElement(name = "codSustento")
    public String getSecRetencion1() {
        return secRetencion1;
    }

    @XmlElement(name = "codSustento")
    public String getAutRetencion1() {
        return autRetencion1;
    }

    @XmlElement(name = "codSustento")
    public String getFechaEmiRet1() {
        return fechaEmiRet1;
    }

    @XmlElementWrapper(name = "reembolsos")
    @XmlElement(name = "reembolso")
    public List<ReembolsoAts> getReembolsos() {
        return reembolsos;
    }
    
    

    //////////////////////////////////
    public void setCodSustento(String codSustento) {
        this.codSustento = codSustento;
    }

    public void setTpIdProv(String tpIdProv) {
        this.tpIdProv = tpIdProv;
    }

    public void setIdProv(String idProv) {
        this.idProv = idProv;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public void setParteRel(String parteRel) {
        this.parteRel = parteRel;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public void setPuntoEmision(String puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public void setSecuencial(String secuencial) {
        this.secuencial = secuencial;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public void setBaseNoGraIva(BigDecimal baseNoGraIva) {
        this.baseNoGraIva = baseNoGraIva;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    public void setBaseImpGrav(BigDecimal baseImpGrav) {
        this.baseImpGrav = baseImpGrav;
    }

    public void setBaseImpExe(BigDecimal baseImpExe) {
        this.baseImpExe = baseImpExe;
    }

    public void setMontoIce(BigDecimal montoIce) {
        this.montoIce = montoIce;
    }

    public void setMontoIva(BigDecimal montoIva) {
        this.montoIva = montoIva;
    }

    public void setValRetBien10(BigDecimal valRetBien10) {
        this.valRetBien10 = valRetBien10;
    }

    public void setValRetServ20(BigDecimal valRetServ20) {
        this.valRetServ20 = valRetServ20;
    }

    public void setValorRetBienes(BigDecimal valorRetBienes) {
        this.valorRetBienes = valorRetBienes;
    }

    public void setValRetServ50(BigDecimal valRetServ50) {
        this.valRetServ50 = valRetServ50;
    }

    public void setValorRetServicios(BigDecimal valorRetServicios) {
        this.valorRetServicios = valorRetServicios;
    }

    public void setValRetServ100(BigDecimal valRetServ100) {
        this.valRetServ100 = valRetServ100;
    }

    public void setTotbasesImpReemb(BigDecimal totbasesImpReemb) {
        this.totbasesImpReemb = totbasesImpReemb;
    }

    public void setFormasDePago(List<FormaDePagoAts> formasDePago) {
        this.formasDePago = formasDePago;
    }

    public void setDetalleAir(List<AirAts> detalleAir) {
        this.detalleAir = detalleAir;
    }

    public void setEstabRetencion1(String estabRetencion1) {
        this.estabRetencion1 = estabRetencion1;
    }

    public void setPtoEmiRetencion1(String ptoEmiRetencion1) {
        this.ptoEmiRetencion1 = ptoEmiRetencion1;
    }

    public void setSecRetencion1(String secRetencion1) {
        this.secRetencion1 = secRetencion1;
    }

    public void setAutRetencion1(String autRetencion1) {
        this.autRetencion1 = autRetencion1;
    }

    public void setFechaEmiRet1(String fechaEmiRet1) {
        this.fechaEmiRet1 = fechaEmiRet1;
    }

    public void setPagoExteriorAts(PagoExteriorAts pagoExteriorAts) {
        this.pagoExteriorAts = pagoExteriorAts;
    }

    public void setReembolsos(List<ReembolsoAts> reembolsos) {
        this.reembolsos = reembolsos;
    }
    
    
    
    /**
     * Metodos Adicionales
     */
    @XmlTransient
    public String getPreimpreso()
    {
       return UtilidadesTextos.llenarCarateresIzquierda(puntoEmision,3,"0")+"-"+UtilidadesTextos.llenarCarateresIzquierda(establecimiento,3,"0")+"-"+UtilidadesTextos.llenarCarateresIzquierda(secuencial+"",9,"0");
    }
    
    
    
    

}
