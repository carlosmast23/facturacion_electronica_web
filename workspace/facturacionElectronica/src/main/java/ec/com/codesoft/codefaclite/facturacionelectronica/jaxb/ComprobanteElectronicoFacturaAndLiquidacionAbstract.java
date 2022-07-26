/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.DetalleFacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.InformacionFactura;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author
 */
@XmlTransient
public abstract class ComprobanteElectronicoFacturaAndLiquidacionAbstract extends ComprobanteElectronico {
    
    //private InformacionFactura informacionFactura;
    private List<DetalleFacturaComprobante> detalles;
    
    
    public abstract InformacionComprobanteAbstract getInformacionComprobante();
    public abstract void setInformacionComprobante(InformacionComprobanteAbstract informacionFactura);
    
    //@Override
    //public String getTipoDocumento() {
    //    return ComprobanteEnum.FACTURA.getCodigo();
    //}
    

    @XmlElementWrapper(name = "detalles")
    @XmlElement(name = "detalle")
    public List<DetalleFacturaComprobante> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleFacturaComprobante> detalles) {
        this.detalles = detalles;
    }

    /*@XmlElement(name = "infoFactura")
    public InformacionFactura getInformacionFactura() {
        return informacionFactura;
    }*/

    @Override
    public String getFechaEmision() {
        return this.getInformacionComprobante().getFechaEmision();
    }

    //public void setInformacionC(InformacionFactura informacionFactura) {
    //    this.informacionFactura = informacionFactura;
    //}


    @XmlAttribute(name = "version")
    public String getVersionAttribute() {
        return "1.0.0";
    }

    /*@Override
    public String getRazonSocialComprador() {
        return getInformacionComprobante().getRazon;
    }*/

    @Override
    public String getDireccionEstablecimiento() {
        return getInformacionComprobante().getDirEstablecimiento();
    }
    
}
