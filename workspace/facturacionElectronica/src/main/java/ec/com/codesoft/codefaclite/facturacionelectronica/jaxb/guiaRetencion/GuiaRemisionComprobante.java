/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.guiaRetencion;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.InformacionComprobanteAbstract;
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
@XmlRootElement(name = ComprobanteElectronico.GUIA_REMISION)
@XmlType(propOrder = {
    "informacionTributaria",
    "infoGuiaRemision",
    "destinatarios",
    "informacionAdicional"})
public class GuiaRemisionComprobante extends ComprobanteElectronico{
    
    private InformacionGuiaRemision infoGuiaRemision;
    
    private List<DestinatariosGuiaRemisionComprobante> destinatarios;

    @Override
    public String getTipoDocumento() {
        return ComprobanteEnum.GUIA_REMISION.getCodigo();
    }

    @Override
    public String getFechaEmision() {
        return infoGuiaRemision.getFechaIniTransporte();
    }

    @Override
    public String getRazonSocialComprador() {
        return infoGuiaRemision.getRazonSocialTransportista();
    }
    
    @XmlAttribute(name = "version")
    public String getVersionAttribute() {
        return "1.1.0";
    }

    
    @XmlElement(name = "infoGuiaRemision")
    public InformacionGuiaRemision getInfoGuiaRemision() {
        return infoGuiaRemision;
    }

    public void setInfoGuiaRemision(InformacionGuiaRemision infoGuiaRemision) {
        this.infoGuiaRemision = infoGuiaRemision;
    }

    @XmlElementWrapper(name = "destinatarios")
    @XmlElement(name = "destinatario")
    public List<DestinatariosGuiaRemisionComprobante> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(List<DestinatariosGuiaRemisionComprobante> destinatarios) {
        this.destinatarios = destinatarios;
    }

    @Override
    public String getDireccionEstablecimiento() {
        return infoGuiaRemision.getDirEstablecimiento();
    }

    @Override
    public String getObligadoLlevarContabilidad() {
        return infoGuiaRemision.getObligadoContabilidad();
    }

    @Override
    public String getContribuyenteEspecial() {
        return infoGuiaRemision.getObligadoContabilidad(); 
    }

    
    
    
    
    
}
