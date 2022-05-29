/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 *
 * @author CodesoftDesarrollo
 */
public enum GeneralEnumEstado {
    /**
     * Valor por defecto del cliente
     */
    ACTIVO("A","Activo"),
    /**
     * Estado inactivo del cliente seguramente por algun motivo que el usuario
     * cosidere
     */
    INACTIVO("I","Inactivo"),
    /**
     * Estado cuando un cliente es eliminado de forma permanente
     */
    ELIMINADO("E","Eliminado"),
    
    /**
     * Estado cuando se quiere especificar que se anula por el proceso es decir:
     * (Cuando se anula una factura con nota de credito se anula)
     */
    ANULADO("N","Anulado"),
    
    ;

    private String estado;
    private String nombre;

    private GeneralEnumEstado(String estado,String nombre) {
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
    
    

    public static GeneralEnumEstado getEnum(String estado) {
        for (GeneralEnumEstado enumerador : GeneralEnumEstado.values()) {
            if (enumerador.estado.equals(estado)) {
                return enumerador;
            }
        }
        return null;
    }
}
