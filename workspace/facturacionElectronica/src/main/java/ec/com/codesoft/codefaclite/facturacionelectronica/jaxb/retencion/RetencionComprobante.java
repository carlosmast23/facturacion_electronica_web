/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.retencion;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author
 */
@XmlRootElement(name = ComprobanteElectronico.RETENCION)
@XmlType(propOrder = {"informacionTributaria","infoRetencion","detalles","informacionAdicional"})
public class RetencionComprobante extends ComprobanteElectronico {
    private InformacionRetencion infoRetencion;
    private List<DetalleRetencionComprobante> detalles;

    public RetencionComprobante() {
    }

    @XmlElement(name = "infoCompRetencion")
    public InformacionRetencion getInfoRetencion() {
        return infoRetencion;
    }

    public void setInfoRetencion(InformacionRetencion infoRetencion) {
        this.infoRetencion = infoRetencion;
    }

    @XmlElementWrapper(name = "impuestos")
    @XmlElement(name = "impuesto")
    public List<DetalleRetencionComprobante> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleRetencionComprobante> detalles) {
        this.detalles = detalles;
    }
    
    
    
    @Override
    public String getTipoDocumento() {
        return ComprobanteEnum.COMPROBANTE_RETENCION.getCodigo();
    }

    @Override
    public String getFechaEmision() {
        return this.infoRetencion.getFechaEmision();
    }

    @Override
    public String getRazonSocialComprador() {
        return this.infoRetencion.getRazonSocialSujetoRetenido();
    }
    
    @XmlAttribute(name = "version")
    public String getVersionAttribute() {
        return "1.0.0";
    }

    @Override
    public String getDireccionEstablecimiento() {
        return infoRetencion.getDirEstablecimiento();
    }

    @Override
    public String getObligadoLlevarContabilidad() {
        return infoRetencion.getObligadoContabilidad();
    }

    @Override
    public String getContribuyenteEspecial() {
        return infoRetencion.getContribuyenteEspecial();
    }
    
    
}
