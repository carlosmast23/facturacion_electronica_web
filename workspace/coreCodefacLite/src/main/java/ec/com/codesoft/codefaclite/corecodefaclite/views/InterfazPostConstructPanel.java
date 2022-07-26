/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.views;

/**
 * Interfaz que permite establecer un constructor adicional despues de cargar la pantalla cuando se necesite
 * abrir la pantalla desde otra pantalla y enviar parametros para que se abra o carga con una logica en particular
 * @author
 */
public interface InterfazPostConstructPanel {
    public void postConstructorExterno(Object[] parametros);
}
