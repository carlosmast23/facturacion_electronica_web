/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.liquidacionCompra;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronicoFacturaAndLiquidacionAbstract;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.InformacionComprobanteAbstract;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.DetalleFacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.InformacionFactura;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 *TODO: Ver si se puede crear una clase abstracta anterior que agrupe las funcionalidades reptidas de factura y cñoquidacion de compras
 * @author
 */
@XmlRootElement(name = ComprobanteElectronico.LIQUIDACION_COMPRA)
@XmlType(propOrder = {"informacionTributaria","informacionLiquidacionCompra","detalles","informacionAdicional"})
public class LiquidacionCompraComprobante extends ComprobanteElectronicoFacturaAndLiquidacionAbstract{
    
    
    private InformacionLiquidacionCompra informacionLiquidacionCompra;
    //private List<DetalleFacturaComprobante> detalles;
    
    @Override
    public String getTipoDocumento() {
        return ComprobanteEnum.LIQUIDACION_COMPRA.getCodigo();
    }
    

    /*@XmlElementWrapper(name = "detalles")
    @XmlElement(name = "detalle")
    public List<DetalleFacturaComprobante> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleFacturaComprobante> detalles) {
        this.detalles = detalles;
    }*/

    @XmlElement(name = "infoLiquidacionCompra")
    public InformacionLiquidacionCompra getInformacionLiquidacionCompra() {
        return informacionLiquidacionCompra;
    }

    public void setInformacionLiquidacionCompra(InformacionLiquidacionCompra informacionLiquidacionCompra) {
        this.informacionLiquidacionCompra = informacionLiquidacionCompra;
    }

    

    @XmlAttribute(name = "version")
    public String getVersionAttribute() {
        return "1.0.0";
    }

    @Override
    public String getRazonSocialComprador() {
        return informacionLiquidacionCompra.getRazonSocialProveedor();
    }

    /*@Override
    public String getDireccionEstablecimiento() {
        return getInformacionFactura().getDirEstablecimiento();
    }*/

    @Override
    @XmlTransient
    public InformacionComprobanteAbstract getInformacionComprobante() {
        return informacionLiquidacionCompra;
    }

    @Override
    public void setInformacionComprobante(InformacionComprobanteAbstract informacionFactura) {
        this.informacionLiquidacionCompra=(InformacionLiquidacionCompra) informacionFactura;
    }

    @Override
    public String getObligadoLlevarContabilidad() {
        return informacionLiquidacionCompra.getObligadoContabilidad();
    }

    @Override
    public String getContribuyenteEspecial() {
        return informacionLiquidacionCompra.getContribuyenteEspecial();
    }


    
    
}
