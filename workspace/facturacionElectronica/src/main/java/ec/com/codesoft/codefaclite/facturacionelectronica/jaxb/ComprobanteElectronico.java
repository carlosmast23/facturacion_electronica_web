/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionTributaria;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionAdicional;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author
 */
@XmlTransient
public abstract class ComprobanteElectronico implements Serializable{

    public static final String FACTURA = "factura";
    public static final String NOTA_CREDITO = "notaCredito";
    public static final String RETENCION = "comprobanteRetencion";
    public static final String GUIA_REMISION = "guiaRemision";
    public static final String LIQUIDACION_COMPRA = "liquidacionCompra";
    
    
    public static final String LOTE = "lote";

    public static final String MODO_FACTURACION_NORMAL = "1";
    protected InformacionTributaria informacionTributaria;
    protected List<InformacionAdicional> informacionAdicional;

    public abstract String getTipoDocumento();

    public abstract String getFechaEmision();
    
    /**
     * Necesito el nombre para enviar a los correos los datos
     * 
     */
    public abstract String getRazonSocialComprador();
    
    /**
     * Necesito este dato en los reportes para imprimir el numero de establecimiento
     * @return 
     */
    public abstract String getDireccionEstablecimiento();
    

    public abstract String getObligadoLlevarContabilidad();
    
    public abstract String getContribuyenteEspecial();
    
    /**
     * Variable adicional que permite almacenar los correos en una lista que no se procesa al generar el xml
     */
    protected List<String> correos;
    

    public ComprobanteElectronico() {
    }

    /**
     * Compobante electronico
     *
     * @return
     */
    @XmlAttribute(name = "id")
    public String getIdAttribute() {
        return "comprobante";
    }

    //@XmlAttribute(name = "version")
    //public abstract String getVersionAttribute();

    @XmlAttribute(name = "xmlns:ds")
    public String getXmlnsdsAttribute() {
        return "http://www.w3.org/2000/09/xmldsig#";
    }

    @XmlAttribute(name = "xmlns:xsi")
    public String getXmlnsxsiAttribute() {
        return "http://www.w3.org/2001/XMLSchema-instance";
    }

    @XmlElement(name = "infoTributaria")
    public InformacionTributaria getInformacionTributaria() {
        return informacionTributaria;
    }

    public void setInformacionTributaria(InformacionTributaria informacionTributaria) {
        this.informacionTributaria = informacionTributaria;
    }

    @XmlElementWrapper(name = "infoAdicional")
    @XmlElement(name = "campoAdicional")
    public List<InformacionAdicional> getInformacionAdicional() {
        return informacionAdicional;
    }

    public void setInformacionAdicional(List<InformacionAdicional> informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    public String getIdentificacion() {
        return this.informacionTributaria.getRuc();
    }

    @XmlTransient
    public List<String> getCorreos() {
        return correos;
    }

    public void setCorreos(List<String> correos) {
        this.correos = correos;
    }
    
    @XmlTransient
    public String getPreimpreso()
    {
        return informacionTributaria.getEstablecimiento()+"-"+informacionTributaria.getPuntoEmision()+"-"+informacionTributaria.getSecuencial();
    }

    
}
