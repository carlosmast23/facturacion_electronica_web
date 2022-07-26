/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author
 */
@XmlType(propOrder = {
    "pagoLocExt",
    "paisEfecPagoGen",
    "paisEfecPago",
    "aplicConvDobTrib",
    "pagExtSujRetNorLeg"})
public class PagoExteriorAts implements Serializable{
    private String pagoLocExt;
    private String paisEfecPagoGen;
    private String paisEfecPago;
    private String aplicConvDobTrib;
    private String pagExtSujRetNorLeg;

    public PagoExteriorAts() {
    }

    @XmlElement(name = "pagoLocExt")
    public String getPagoLocExt() {
        return pagoLocExt;
    }

    @XmlElement(name = "paisEfecPagoGen")
    public String getPaisEfecPagoGen() {
        return paisEfecPagoGen;
    }

    @XmlElement(name = "paisEfecPago")
    public String getPaisEfecPago() {
        return paisEfecPago;
    }

    @XmlElement(name = "aplicConvDobTrib")
    public String getAplicConvDobTrib() {
        return aplicConvDobTrib;
    }

    @XmlElement(name = "pagExtSujRetNorLeg")
    public String getPagExtSujRetNorLeg() {
        return pagExtSujRetNorLeg;
    }

    public void setPagoLocExt(String pagoLocExt) {
        this.pagoLocExt = pagoLocExt;
    }

    public void setPaisEfecPagoGen(String paisEfecPagoGen) {
        this.paisEfecPagoGen = paisEfecPagoGen;
    }

    public void setPaisEfecPago(String paisEfecPago) {
        this.paisEfecPago = paisEfecPago;
    }

    public void setAplicConvDobTrib(String aplicConvDobTrib) {
        this.aplicConvDobTrib = aplicConvDobTrib;
    }

    public void setPagExtSujRetNorLeg(String pagExtSujRetNorLeg) {
        this.pagExtSujRetNorLeg = pagExtSujRetNorLeg;
    }

    
    
}
