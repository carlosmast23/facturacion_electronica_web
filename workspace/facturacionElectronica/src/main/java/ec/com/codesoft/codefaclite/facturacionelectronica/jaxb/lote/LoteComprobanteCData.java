/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.lote;

import com.sun.xml.txw2.annotation.XmlCDATA;

/**
 *
 * @author
 */
public class LoteComprobanteCData {
    
    private String cData;

    @XmlCDATA
    public String getcData() {
        return cData;
    }

    public void setcData(String cData) {
        this.cData = cData;
    }
    
    
    
}
