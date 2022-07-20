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
public enum CajaSessionEnum {
    
    /**
     * Valor por defecto de la sesion de la caja cuando se crea
     */
    ACTIVO("A", "ACTIVO"),
    
    /**
     * Valor cuando se finaliza el uso de la caja al finalizar el dia
     */
    FINALIZADO("F", "FINALIZADO"),
    
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

    private CajaSessionEnum(String estado,String nombre) {
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
   
    
    public static CajaSessionEnum getEnum(String estado) {
        for (CajaSessionEnum enumerador : CajaSessionEnum.values()) {
            if (enumerador.estado.equals(estado)) {
                return enumerador;
            }
        }
        return null;
    }
}
