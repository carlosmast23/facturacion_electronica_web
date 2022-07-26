/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.varios;

import java.awt.Color;
import java.awt.Font;
import javax.swing.text.JTextComponent;
import org.jdesktop.swingx.prompt.PromptSupport;

/**
 *
 * @author
 */
public class UtilidadesSwingX {
    
    public static void placeHolder(String texto,JTextComponent componente)
    {
        PromptSupport.setPrompt(texto, componente);
        PromptSupport.setForeground(Color.lightGray, componente);
        PromptSupport.setFontStyle(Font.BOLD, componente);
    }
}
