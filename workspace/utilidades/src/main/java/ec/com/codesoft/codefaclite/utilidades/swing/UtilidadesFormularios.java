/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.swing;

import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public class UtilidadesFormularios {
    
    
    
    public static void bloquerLimiteIngresoCampoTexto(JTextField campoTexto, int limite)
    {
        campoTexto.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {            
                limitarTamanioTextField(e,campoTexto,limite);
            }
        });
    }
    
    public static void llenarAutomaticamenteCamposTexto(JTextField campoTexto, int limite)
    {
        campoTexto.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                completarTextField(campoTexto,limite);
            }
        });
    }
    
    public static void habilitarComponentes(Boolean habilitado,JComponent ...campos)
    {
        for (JComponent campo : campos) {
            campo.setEnabled(habilitado);
        }
        
    }
    
    private static void completarTextField(JTextField campoTexto, int limite) {
        String texto = campoTexto.getText();
        texto = UtilidadesTextos.llenarCarateresIzquierda(texto, limite, "0");
        campoTexto.setText(texto);
    }
    
    private static void limitarTamanioTextField(KeyEvent e,JTextField campoTexto, int limite)
    {
        if(campoTexto.getText().length()>limite)
        {
            campoTexto.setText(campoTexto.getText().substring(0,limite));
        }
        /*if ((campoTexto.getText() + e.getKeyChar()).length() > limite) {
            e.consume();
        }*/
    }
    
}
