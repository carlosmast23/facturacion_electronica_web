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
public enum CajaEnum {
    /**
     * Valor por defecto de la sesion de la caja cuando se crea
     */
    ACTIVO("A", "ACTIVO"),
    
    /**
     * Valor cuando se produce un desperfecto en la Caja utilizada
     */
    FINALIZADO("D", "Dañada"),
    
    /**
     * Valor para no cerrar definitivamente el estado de la sesion de la caja, por alguna razon o motivo
     */
    INACTIVO("I", "INACTIVO"),
    
    /**
     * Valor cuando la sesion de una caja se ha eliminado definitivamente
     */
    ELIMINADO("E", "ELIMINADO"),;
    
     private String estado;
    private String nombre;

    private CajaEnum(String estado,String nombre) {
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
   
    
    public static CajaEnum getEnum(String estado) {
        for (CajaEnum enumerador : CajaEnum.values()) {
            if (enumerador.estado.equals(estado)) {
                return enumerador;
            }
        }
        return null;
    }
}
