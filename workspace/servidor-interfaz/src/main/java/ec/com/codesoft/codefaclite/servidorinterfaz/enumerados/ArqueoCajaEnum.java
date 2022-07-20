/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 *
 * @auhor
 */
public enum ArqueoCajaEnum {
    
    /**
     * Valor por defecto del arqueio de caja cuando no hay novedades
     */
    CUADRO("C", "CUADRO"),
    
    /**
     * Valor cuando se finaliza el uso de la caja al finalizar el dia
     */
    NO_CUADRO("N", "NO CUADRO");
    
    private String estado;
    private String nombre;

    private ArqueoCajaEnum(String estado, String nombre) {
        this.estado = estado;
        this.nombre=nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }
   
    
    public static ArqueoCajaEnum getEnum(String estado) {
        for (ArqueoCajaEnum enumerador : ArqueoCajaEnum.values()) {
            if (enumerador.estado.equals(estado)) {
                return enumerador;
            }
        }
        return null;
    }
}
