/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.guiaRetencion;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author
 */
@XmlType(propOrder = {
    "dirEstablecimiento",
    "dirPartida",
    "razonSocialTransportista",
    "tipoIdentificacionTransportista",
    "rucTransportista",
    "rise",
    "obligadoContabilidad",
    "contribuyenteEspecial",
    "fechaIniTransporte",
    "fechaFinTransporte",
    "placa",})
public class InformacionGuiaRemision implements Serializable{
    
    private String dirEstablecimiento;
    private String dirPartida;
    private String razonSocialTransportista;
    private String tipoIdentificacionTransportista;
    private String rucTransportista;
    private String rise;
    private String obligadoContabilidad;
    private String contribuyenteEspecial;
    private String fechaIniTransporte;
    private String fechaFinTransporte;
    private String placa;

    public InformacionGuiaRemision() {
    }

    @XmlElement(name = "dirEstablecimiento")
    public String getDirEstablecimiento() {
        return dirEstablecimiento;
    }

    public void setDirEstablecimiento(String dirEstablecimiento) {
        this.dirEstablecimiento = dirEstablecimiento;
    }

    @XmlElement(name = "dirPartida")
    public String getDirPartida() {
        return dirPartida;
    }

    public void setDirPartida(String dirPartida) {
        this.dirPartida = dirPartida;
    }

    @XmlElement(name = "razonSocialTransportista")
    public String getRazonSocialTransportista() {
        return razonSocialTransportista;
    }

    public void setRazonSocialTransportista(String razonSocialTransportista) {
        this.razonSocialTransportista = razonSocialTransportista;
    }

    @XmlElement(name = "tipoIdentificacionTransportista")
    public String getTipoIdentificacionTransportista() {
        return tipoIdentificacionTransportista;
    }

    public void setTipoIdentificacionTransportista(String tipoIdentificacionTransportista) {
        this.tipoIdentificacionTransportista = tipoIdentificacionTransportista;
    }

    @XmlElement(name = "rucTransportista")
    public String getRucTransportista() {
        return rucTransportista;
    }

    public void setRucTransportista(String rucTransportista) {
        this.rucTransportista = rucTransportista;
    }

    @XmlElement(name = "rise")
    public String getRise() {
        return rise;
    }

    public void setRise(String rise) {
        this.rise = rise;
    }

    @XmlElement(name = "obligadoContabilidad")
    public String getObligadoContabilidad() {
        return obligadoContabilidad;
    }

    public void setObligadoContabilidad(String obligadoContabilidad) {
        this.obligadoContabilidad = obligadoContabilidad;
    }

    @XmlElement(name = "contribuyenteEspecial")
    public String getContribuyenteEspecial() {
        return contribuyenteEspecial;
    }

    public void setContribuyenteEspecial(String contribuyenteEspecial) {
        this.contribuyenteEspecial = contribuyenteEspecial;
    }

    @XmlElement(name = "fechaIniTransporte")
    public String getFechaIniTransporte() {
        return fechaIniTransporte;
    }

    public void setFechaIniTransporte(String fechaIniTransporte) {
        this.fechaIniTransporte = fechaIniTransporte;
    }

    @XmlElement(name = "fechaFinTransporte")
    public String getFechaFinTransporte() {
        return fechaFinTransporte;
    }

    public void setFechaFinTransporte(String fechaFinTransporte) {
        this.fechaFinTransporte = fechaFinTransporte;
    }

    @XmlElement(name = "placa")
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    
    
}
