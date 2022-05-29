/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.dialog;

import java.io.Serializable;

/**
 *
 * @author Carlos
 */
public class ColumnaDialogo implements Serializable{
    private String Nombre;
    private  Integer tamanio;
    /**
     * Porcentaje de cada columna
     * el valor puede es entre 0 y 1
     */
    private Double porcentaje;
    

    public ColumnaDialogo(String Nombre, Integer tamanio) {
        this.Nombre = Nombre;
        this.tamanio = tamanio;
        this.porcentaje=-1d;
    }

    public ColumnaDialogo(String Nombre, Double porcentaje) {
        this.Nombre = Nombre;
        this.porcentaje = porcentaje;
        this.tamanio=-1;
    }
    
    
    

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public Integer getTamanio() {
        return tamanio;
    }

    public void setTamanio(Integer tamanio) {
        this.tamanio = tamanio;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }
    
    
    
    
}
