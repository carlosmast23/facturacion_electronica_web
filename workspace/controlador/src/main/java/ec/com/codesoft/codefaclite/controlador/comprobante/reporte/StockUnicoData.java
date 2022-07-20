/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobante.reporte;

import java.io.Serializable;

/**
 *
 * @auhor
 */
public class StockUnicoData implements Serializable{
    
    private String codigoUnico;

    public StockUnicoData(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public void setCodigoUnico(String codigoUnico) {
        this.codigoUnico = codigoUnico;
    }
    
    
}
