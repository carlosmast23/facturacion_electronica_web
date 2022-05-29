/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.varios;

/**
 *
 * @author Carlos
 */
public class InterfazRed {
    public String mac;
    public String nombre;

    public InterfazRed(String mac, String nombre) {
        this.mac = mac;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
}
