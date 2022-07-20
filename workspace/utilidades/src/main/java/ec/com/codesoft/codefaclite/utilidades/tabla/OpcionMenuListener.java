/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.tabla;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @auhor
 */
public class OpcionMenuListener implements ActionListener
{
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Menu opcion:"  + e.getActionCommand());
    }
    
}
