 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.InformacionComprobanteAbstract;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.TotalImpuesto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
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
    "tipoIdentificacionComprador",
    "razonSocialComprador",
    "identificacionComprador",
    "direccionComprador",    
    "totalSinImpuestos",
    "totalSubsidio",
    "totalDescuento",
    "totalImpuestos",
    "importeTotal",
    "formaPagos"})
public class InformacionFactura extends InformacionComprobanteAbstract{

    private String tipoIdentificacionComprador;
    private String razonSocialComprador;
    private String identificacionComprador;
    private String direccionComprador;
    private BigDecimal totalSubsidio;
    
   
    public InformacionFactura() {
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

    @XmlElement(name = "direccionComprador")   
    public String getDireccionComprador() {
        return direccionComprador;
    }

    @XmlElement(name = "totalSubsidio")   
    public BigDecimal getTotalSubsidio() {
        return totalSubsidio;
    }

    public void setTotalSubsidio(BigDecimal totalSubsidio) {
        this.totalSubsidio = totalSubsidio;
    }
    
    

    public void setDireccionComprador(String direccionComprador) {
        this.direccionComprador = direccionComprador;
    }

    @Override
    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacionComprador=tipoIdentificacion;
    }

    @Override
    public void setRazonSocial(String razonSocial) {
        this.razonSocialComprador=razonSocial;
    }

    @Override
    public void setIdentificacion(String identificacion) {
        this.identificacionComprador=identificacion;
    }

    @Override
    public void setDireccion(String direccion) {
        this.direccionComprador=direccion;
    }


}
