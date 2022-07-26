/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author
 */
@XmlType(propOrder = {"tipoIDInformante",
    "idInformante",
    "razonSocial",
    "anio",
    "mes",
    "regimenMicroempresa",
    "numEstabRuc",
    "totalVentas",
    "codigoOperativo",
    "compras",
    "ventas",
    "ventasEstablecimiento",
    "anuladosAts"})
@XmlRootElement(name = "iva")
public class AtsJaxb implements Serializable
{
    private String tipoIDInformante;
    private String idInformante;
    private String razonSocial;
    private Integer anio;
    private String mes;
    
    /**
     * Este campo va a permitir especificar si el cliente esta en el regimen microempresa
     */
    private String regimenMicroempresa;
    /**
     * Corresponde al numero de establecimientos activos que voy a realizar el ats
     */
    private String numEstabRuc;
    private BigDecimal totalVentas;
    private String codigoOperativo;
    
    private List<CompraAts> compras;
    private List<VentaAts> ventas;
    private List<VentasEstablecimientoAts> ventasEstablecimiento;
    private List<AnuladoAts> anuladosAts;
    
    /**
     * Variable adicional solo para informar de alertas al momento de generar los ats
     */
    private List<String> alertas;
    
    
    /**
     * DATOS ADICIONALES QUE NO APARECEN EN EL XML
     */
    //@XmlTransient
    //public BigDecimal totalIva;

    public AtsJaxb() {
    }

    @XmlElementWrapper(name = "anulados")
    @XmlElement(name = "detalleAnulados")    
    public List<AnuladoAts> getAnuladosAts() {
        return anuladosAts;
    }

    @XmlElementWrapper(name = "compras")
    @XmlElement(name = "detalleCompras")    
    public List<CompraAts> getCompras() {
        return compras;
    }

    @XmlElementWrapper(name = "ventasEstablecimiento")
    @XmlElement(name = "ventaEst")
    public List<VentasEstablecimientoAts> getVentasEstablecimiento() {
        return ventasEstablecimiento;
    }

    @XmlElementWrapper(name = "ventas")
    @XmlElement(name = "detalleVentas")
    public List<VentaAts> getVentas() {
        return ventas;
    }

    @XmlElement(name = "TipoIDInformante")
    public String getTipoIDInformante() {
        return tipoIDInformante;
    }

    @XmlElement(name = "IdInformante")
    public String getIdInformante() {
        return idInformante;
    }

    @XmlElement(name = "razonSocial")
    public String getRazonSocial() {
        return razonSocial;
    }

    @XmlElement(name = "Anio")
    public Integer getAnio() {
        return anio;
    }

    @XmlElement(name = "Mes")
    public String getMes() {
        return mes;
    }
    
    @XmlElement(name = "regimenMicroempresa")
    public String getRegimenMicroempresa() {
        return regimenMicroempresa;
    }

    @XmlElement(name = "numEstabRuc")
    public String getNumEstabRuc() {
        return numEstabRuc;
    }

    @XmlElement(name = "totalVentas")
    public BigDecimal getTotalVentas() {
        return totalVentas;
    }

    @XmlElement(name = "codigoOperativo")
    public String getCodigoOperativo() {
        return codigoOperativo;
    }
    
    @XmlTransient
    public List<String> getAlertas() {
        return alertas;
    }

    public void setTipoIDInformante(String tipoIDInformante) {
        this.tipoIDInformante = tipoIDInformante;
    }

    public void setIdInformante(String idInformante) {
        this.idInformante = idInformante;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public void setNumEstabRuc(String numEstabRuc) {
        this.numEstabRuc = numEstabRuc;
    }

    public void setTotalVentas(BigDecimal totalVentas) {
        this.totalVentas = totalVentas;
    }

    public void setCodigoOperativo(String codigoOperativo) {
        this.codigoOperativo = codigoOperativo;
    }

    public void setVentas(List<VentaAts> ventas) {
        this.ventas = ventas;
    }

    public void setVentasEstablecimiento(List<VentasEstablecimientoAts> ventasEstablecimiento) {
        this.ventasEstablecimiento = ventasEstablecimiento;
    }

    public void setCompras(List<CompraAts> compras) {
        this.compras = compras;
    }

    public void setAnuladosAts(List<AnuladoAts> anuladosAts) {
        this.anuladosAts = anuladosAts;
    }

    public void setRegimenMicroempresa(String regimenMicroempresa) {
        this.regimenMicroempresa = regimenMicroempresa;
    }

    public void setAlertas(List<String> alertas) {
        this.alertas = alertas;
    }
    
    
    
    
    /**
     *=========================> METODOS PERSONALIZADOS <====================
     */
    /**
     * Metodo que calcula el tot
     */
    public void calcularTotalVentas()
    {
        this.totalVentas=BigDecimal.ZERO;
        //this.totalIva=BigDecimal.ZERO;
        
        if(ventas!=null)
        {
            for (VentaAts venta : ventas) {
                if(venta.getTipoEmision().equals(ComprobanteEntity.TipoEmisionEnum.NORMAL.getCodigoSri()))
                {   
                    this.totalVentas=this.totalVentas.add(venta.getBaseImpGrav());
                }
                //this.totalIva=this.totalIva.add(venta.getMontoIva());
            }
        }
    }
    
    
    
}
