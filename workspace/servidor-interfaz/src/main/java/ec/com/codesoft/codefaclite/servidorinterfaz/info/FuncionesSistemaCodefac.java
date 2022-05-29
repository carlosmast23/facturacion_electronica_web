/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.info;

import javax.swing.JOptionPane;

/**
 *Clase que va a contener funciones o metodos comunes para todo el sistema
 * @author Carlos
 */
public abstract class FuncionesSistemaCodefac {
    public static void servidorConexionPerdida() {
        JOptionPane.showMessageDialog(null,"No exite comunicación con el servidor, Codefac se tiene que cerrar \n\n Posibles causas:\n - El servidor esta apagado \n - Su computador no tiene conexión a la red","Error",JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }
}
