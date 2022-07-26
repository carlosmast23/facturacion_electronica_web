/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author
 */
public class ComprobanteLote implements Serializable{
    
    private List<ComprobanteDataInterface> comprobantes;

    public ComprobanteLote(List<ComprobanteDataInterface> comprobantes) {
        this.comprobantes = comprobantes;
    }

    public List<ComprobanteDataInterface> getComprobantes() {
        return comprobantes;
    }

    public void setComprobantes(List<ComprobanteDataInterface> comprobantes) {
        this.comprobantes = comprobantes;
    }
        
}
