/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.sistema;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Carlos
 */
@ManagedBean(eager = true)
@ApplicationScoped
public class ParametrosWeb implements Serializable{
    public static final String ejemplo="asdasd";
    private String tipoProforma="proforma";
    private String tipoFactura="factura";
    private String campoTipoFacturaOProforma="tipo";
    

    public String getTipoProforma() {
        return tipoProforma;
    }

    public void setTipoProforma(String tipoProforma) {
        this.tipoProforma = tipoProforma;
    }

    public String getTipoFactura() {
        return tipoFactura;
    }

    public void setTipoFactura(String tipoFactura) {
        this.tipoFactura = tipoFactura;
    }

    public String getCampoTipoFacturaOProforma() {
        return campoTipoFacturaOProforma;
    }

    public void setCampoTipoFacturaOProforma(String campoTipoFacturaOProforma) {
        this.campoTipoFacturaOProforma = campoTipoFacturaOProforma;
    }
    
    
    
    
}
