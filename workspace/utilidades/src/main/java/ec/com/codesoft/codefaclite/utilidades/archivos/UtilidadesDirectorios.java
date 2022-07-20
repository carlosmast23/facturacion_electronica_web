/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.archivos;

import javax.swing.JFileChooser;

/**
 *
 * @auhor
 */
public abstract class UtilidadesDirectorios {
    public static String buscarDirectorio()
    {
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        f.showSaveDialog(null);
        if (f.getSelectedFile() != null) {
            return f.getSelectedFile().getPath();
        }
        return null;
    }
}
