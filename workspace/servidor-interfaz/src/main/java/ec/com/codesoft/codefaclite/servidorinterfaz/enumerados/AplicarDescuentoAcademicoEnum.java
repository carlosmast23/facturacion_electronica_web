/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 *
 * @author CARLOS_CODESOFT
 */
public enum AplicarDescuentoAcademicoEnum {

    /**
     * Fuciona el descuento actual con los descuentos aplicados previamente
     */
    FUSIONAR_DESCUENTO("Fusionar descuentos"),
    /**
     * Aplicar solo el descuento actual sin tomar en cuenta otros descuentos si
     * los tiene
     */
    APLICAR_SOLO_DESCUENTO_ACTUAL_SI_TIENE_OTROS_DESCUENTOS("Aplicar solo descuento actual aunque tenga otros descuentos asignados"),
    /**
     *
     */
    NO_APLICAR_DESCUENTO_SI_YA_TIENE_ASIGANDO_DESCUENTO("No aplicar el descuento actual cuando tenga otros descuentos asignados");

    private String nombre;

    private AplicarDescuentoAcademicoEnum(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
