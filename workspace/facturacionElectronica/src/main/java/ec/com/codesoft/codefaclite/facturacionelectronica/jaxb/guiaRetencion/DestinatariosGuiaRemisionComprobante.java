/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.guiaRetencion;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author
 */
@XmlType(propOrder
        = {"identificacionDestinatario",
            "razonSocialDestinatario",
            "dirDestinatario",
            "motivoTraslado",
            "docAduaneroUnico",
            "codEstabDestino",
            "ruta",
            "codDocSustento",
            "numDocSustento",
            "numAutDocSustento",
            "fechaEmisionDocSustento",
            "detalles"})
public class DestinatariosGuiaRemisionComprobante implements Serializable{
    private String identificacionDestinatario;
    private String razonSocialDestinatario;
    private String dirDestinatario;
    private String motivoTraslado;
    private String docAduaneroUnico;
    private String codEstabDestino;
    private String ruta;
    private String codDocSustento;
    private String numDocSustento;
    private String numAutDocSustento;
    private String fechaEmisionDocSustento;
    
    private List<DetalleGuiaRemisionComprobante> detalles;

    public DestinatariosGuiaRemisionComprobante() {
    }

    @XmlElementWrapper(name = "detalles")
    @XmlElement(name = "detalle")
    public List<DetalleGuiaRemisionComprobante> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleGuiaRemisionComprobante> detalles) {
        this.detalles = detalles;
    }
    
    

    @XmlElement(name = "identificacionDestinatario")
    public String getIdentificacionDestinatario() {
        return identificacionDestinatario;
    }

    public void setIdentificacionDestinatario(String identificacionDestinatario) {
        this.identificacionDestinatario = identificacionDestinatario;
    }

    @XmlElement(name = "razonSocialDestinatario")
    public String getRazonSocialDestinatario() {
        return razonSocialDestinatario;
    }

    public void setRazonSocialDestinatario(String razonSocialDestinatario) {
        this.razonSocialDestinatario = razonSocialDestinatario;
    }

    @XmlElement(name = "dirDestinatario")
    public String getDirDestinatario() {
        return dirDestinatario;
    }

    public void setDirDestinatario(String dirDestinatario) {
        this.dirDestinatario = dirDestinatario;
    }

    @XmlElement(name = "motivoTraslado")
    public String getMotivoTraslado() {
        return motivoTraslado;
    }

    public void setMotivoTraslado(String motivoTraslado) {
        this.motivoTraslado = motivoTraslado;
    }

    @XmlElement(name = "docAduaneroUnico")
    public String getDocAduaneroUnico() {
        return docAduaneroUnico;
    }

    public void setDocAduaneroUnico(String docAduaneroUnico) {
        this.docAduaneroUnico = docAduaneroUnico;
    }

    @XmlElement(name = "codEstabDestino")
    public String getCodEstabDestino() {
        return codEstabDestino;
    }

    public void setCodEstabDestino(String codEstabDestino) {
        this.codEstabDestino = codEstabDestino;
    }

    @XmlElement(name = "ruta")
    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    @XmlElement(name = "codDocSustento")
    public String getCodDocSustento() {
        return codDocSustento;
    }

    public void setCodDocSustento(String codDocSustento) {
        this.codDocSustento = codDocSustento;
    }

    @XmlElement(name = "numDocSustento")
    public String getNumDocSustento() {
        return numDocSustento;
    }

    public void setNumDocSustento(String numDocSustento) {
        this.numDocSustento = numDocSustento;
    }

    @XmlElement(name = "numAutDocSustento")
    public String getNumAutDocSustento() {
        return numAutDocSustento;
    }

    public void setNumAutDocSustento(String numAutDocSustento) {
        this.numAutDocSustento = numAutDocSustento;
    }

    @XmlElement(name = "fechaEmisionDocSustento")
    public String getFechaEmisionDocSustento() {
        return fechaEmisionDocSustento;
    }

    public void setFechaEmisionDocSustento(String fechaEmisionDocSustento) {
        this.fechaEmisionDocSustento = fechaEmisionDocSustento;
    }
    
    
    
}
