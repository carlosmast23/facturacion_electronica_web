/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobante.reporte;

/**
 *
 * @author CodesoftDesarrollo
 */
public class ValorRetencionesData {
    private String codigoRet;
    private String valorRet;

    public ValorRetencionesData(String codigoRet, String valorRet) {
        this.codigoRet = codigoRet;
        this.valorRet = valorRet;
    }

    public String getCodigoRet() {
        return codigoRet;
    }

    public void setCodigoRet(String codigoRet) {
        this.codigoRet = codigoRet;
    }

    public String getValorRet() {
        return valorRet;
    }

    public void setValorRet(String valorRet) {
        this.valorRet = valorRet;
    }


}
