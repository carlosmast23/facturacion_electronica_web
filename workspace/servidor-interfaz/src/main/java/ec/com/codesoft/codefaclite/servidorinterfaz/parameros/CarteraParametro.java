/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.parameros;

import java.io.Serializable;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class CarteraParametro implements Serializable{
    public Boolean habilitarCredito;
    public Integer diasCredito;

    public CarteraParametro(Boolean habilitarCredito, Integer diasCredito) {
        this.habilitarCredito = habilitarCredito;
        this.diasCredito = diasCredito;
    }
    
}
