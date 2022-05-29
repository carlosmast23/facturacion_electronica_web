/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.excel;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class TipoDato {

    public TipoDato() {
    }

    public TipoDato(Object valor, Excel.TipoDataEnum tipoData) {
        this.valor = valor;
        this.tipoData = tipoData;
    }

    public Object valor;
    public Excel.TipoDataEnum tipoData;

}
