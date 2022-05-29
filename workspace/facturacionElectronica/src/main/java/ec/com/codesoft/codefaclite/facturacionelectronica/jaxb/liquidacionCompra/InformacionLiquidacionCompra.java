/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.liquidacionCompra;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.InformacionComprobanteAbstract;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.InformacionFactura;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlType(propOrder = {
    "fechaEmision",
    "dirEstablecimiento",
    "contribuyenteEspecial",
    "obligadoContabilidad",
    "tipoIdentificacionProveedor",
    "razonSocialProveedor",
    "identificacionProveedor",
    "direccionProveedor",    
    "totalSinImpuestos",
    "totalDescuento",
    "totalImpuestos",
    "importeTotal",
    "formaPagos"})
public class InformacionLiquidacionCompra extends InformacionComprobanteAbstract{
    private String tipoIdentificacionProveedor;
    private String razonSocialProveedor;
    private String identificacionProveedor;
    private String direccionProveedor;

    public InformacionLiquidacionCompra() {
    }

    @XmlElement(name = "tipoIdentificacionProveedor")
    public String getTipoIdentificacionProveedor() {
        return tipoIdentificacionProveedor;
    }

    public void setTipoIdentificacionProveedor(String tipoIdentificacionProveedor) {
        this.tipoIdentificacionProveedor = tipoIdentificacionProveedor;
    }

    @XmlElement(name = "razonSocialProveedor")
    public String getRazonSocialProveedor() {
        return razonSocialProveedor;
    }

    public void setRazonSocialProveedor(String razonSocialProveedor) {
        this.razonSocialProveedor = razonSocialProveedor;
    }

    @XmlElement(name = "identificacionProveedor")
    public String getIdentificacionProveedor() {
        return identificacionProveedor;
    }

    public void setIdentificacionProveedor(String identificacionProveedor) {
        this.identificacionProveedor = identificacionProveedor;
    }

    @XmlElement(name = "direccionProveedor")
    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    @Override
    public void setTipoIdentificacion(String tipoIdentificacion) {
        tipoIdentificacionProveedor=tipoIdentificacion;
    }

    @Override
    public void setRazonSocial(String razonSocial) {
        this.razonSocialProveedor=razonSocial;
    }

    @Override
    public void setIdentificacion(String identificacion) {
        this.identificacionProveedor=identificacion;
    }

    @Override
    public void setDireccion(String direccion) {
        this.direccionProveedor=direccion;
    }

    
    
    
}
