/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.lote;

import com.sun.xml.txw2.annotation.XmlCDATA;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
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
@XmlRootElement(name = ComprobanteElectronico.LOTE)
@XmlType(propOrder = {"claveAcceso","ruc","comprobantes"})
public class LoteComprobante {
    private String claveAcceso;
    private String ruc;
    private List<String> comprobantes;

    public LoteComprobante() {
    }


    @XmlElementWrapper(name = "comprobantes")
    @XmlElement(name = "comprobante")
    @XmlCDATA
    public List<String> getComprobantes() {
        return comprobantes;
    }

    public void setComprobantes(List<String> comprobantes) {
        this.comprobantes = comprobantes;
    }
    

    @XmlElement(name = "claveAcceso")
    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    @XmlElement(name = "ruc")
    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }
        
    @XmlAttribute(name = "version")
    public String getVersionAttribute() {
        return "1.0.0";
    }
    
}
