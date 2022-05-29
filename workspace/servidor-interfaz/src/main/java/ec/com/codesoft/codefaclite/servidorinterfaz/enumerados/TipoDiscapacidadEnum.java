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
public enum TipoDiscapacidadEnum {

    NINGUNA("Ninguna", "n"),
    AUDITIVA("Auditiva", "a"),
    FISICA("Fisica", "f"),
    INTELECTUAL("Intelectual", "i"),
    LENGUAJE("Lenguaje", "l"),
    PSICOSOCIAL("Psicosocial", "p"),
    VISUAL("Visual", "v")
    ;

    private TipoDiscapacidadEnum(String nombre, String letra) {
        this.nombre = nombre;
        this.letra = letra;
    }

    private String nombre;
    private String letra;

    public String getNombre() {
        return nombre;
    }

    public String getLetra() {
        return letra;
    }

    public static TipoDiscapacidadEnum getEnumByLetra(String letra) {

        for (TipoDiscapacidadEnum enumerador : TipoDiscapacidadEnum.values()) {
            if (enumerador.getLetra().equals(letra)) {
                return enumerador;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
