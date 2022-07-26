/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 *
 * @author
 */
public abstract class SriEnum {
    
    /**
     * TABLA 14 DEL DOCUMENTO DE LOS ATS
     */
    public enum TipoIdentificacion
    {
        PERSONA_NATURAL("Persona Natural","01"),
        SOCIEDAD("Sociedad","02");

        private TipoIdentificacion(String nombre, String codigo) {
            this.nombre = nombre;
            this.codigo = codigo;
        }
        
        public String nombre;
        public String codigo;
    }
}
