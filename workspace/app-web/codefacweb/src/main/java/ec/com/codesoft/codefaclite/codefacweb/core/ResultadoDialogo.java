/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.core;

import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import java.io.Serializable;

/**
 *
 * @author
 */
public class ResultadoDialogo implements Serializable{
    private String titulo;
    private String mensaje;

    public ResultadoDialogo(String titulo, String mensaje) {
        this.titulo = titulo;
        this.mensaje = mensaje;
    }

    public ResultadoDialogo(CodefacMsj codefacMensaje) {
        this.titulo=codefacMensaje.titulo;
        this.mensaje=codefacMensaje.mensaje;
    }

    public ResultadoDialogo() {
    }
    
    

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
}
