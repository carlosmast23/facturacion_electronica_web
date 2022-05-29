/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos;

import java.io.Serializable;

/**
 *
 * @author Carlos
 */
public class ComprobanteMensaje implements Serializable{
    private String identificador;
    private String mensaje;
    private String informacionAdicional;
    private String tipo;

    public ComprobanteMensaje() {
    }
    
    

    public ComprobanteMensaje(String identificador, String mensaje, String informacionAdicional, String tipo) {
        this.identificador = identificador;
        this.mensaje = mensaje;
        this.informacionAdicional = informacionAdicional;
        this.tipo = tipo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getInformacionAdicional() {
        return informacionAdicional;
    }

    public void setInformacionAdicional(String informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
    
    
}
