/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 *
 * @author Carlos
 */
public enum TipoRubroEnum {
    /**
     * Cuando el usuario se va a encargar de gestionarl este rubro manualmente
     */
    Manual("u","Manual"),
    /**
     * Rubro que se debe generar mensualmente
     */
    Mensual("m","Mensual"),    
    /**
     * Rubro que se debe generar cada año
     */
    Anual("a","Anual"),
    /**
     * Rubro unico que solo se debe generar una sola vez
     */
    Unico("u","Unico");

    private String letra;
    
    private String nombre;

    private TipoRubroEnum(String letra, String nombre) {
        this.letra = letra;
        this.nombre = nombre;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
    
    
}
