/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.util;

/**
 *
 * @author Carlos
 */
public enum ExcepcionDataBaseEnum {
    CLAVE_DUPLICADO("Ya existe un dato guardado con el código principal que intenta ingresar"),
    DESCONOCIDO("Se produjo un error en la base de datos");
    
    private String mensaje;

    private ExcepcionDataBaseEnum(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
    
    
    
}
