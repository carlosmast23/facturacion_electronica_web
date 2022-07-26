/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.test;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class TestReflexion {
    public static void main(String[] args) {
        try {
            Class clase=VentanaEnum.CLIENTE.getClase();
            GeneralPanelInterface panel= (GeneralPanelInterface) clase.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(TestReflexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TestReflexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
