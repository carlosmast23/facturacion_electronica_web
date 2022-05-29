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
public enum GeneroEnum {
      /**
     *GENERO TABLA ESTUDIANTES
     */
    MASCULINO("M"),
    FEMENINO("F"),;

    private String estado;

    private GeneroEnum(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public static GeneroEnum getEnum(String estado) {
        for (GeneroEnum enumerador : GeneroEnum.values()) {
            if (enumerador.estado.equals(estado)) {
                return enumerador;
            }
        }
        return null;
    }
}
