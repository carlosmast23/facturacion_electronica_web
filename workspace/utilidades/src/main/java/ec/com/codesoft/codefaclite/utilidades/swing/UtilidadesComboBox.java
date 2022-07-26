/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.swing;

import java.util.List;
import javax.swing.JComboBox;

/**
 *
 * @author
 */
public class UtilidadesComboBox {

    public static void crearCabeceraComboBox(JComboBox comboBox, String titulo) {
        //comboBox.setRenderer(new MyComboBoxRenderer(titulo));
        //comboBox.setSelectedIndex(-1);
        comboBox.setEditable(true);
        comboBox.setSelectedItem("Seleccione : ");
        comboBox.setEditable(false);

    }
    
    
    public static void llenarComboBox(JComboBox comboBox,Object[] datos)
    {
        comboBox.removeAllItems();
        for (Object object : datos) 
        {
            comboBox.addItem(object);
        }
    }
    
    public static void llenarComboBox(JComboBox comboBox,List datos)
    {
        llenarComboBox(comboBox, datos, Boolean.TRUE);
    }
    
    public static void llenarComboBox(JComboBox comboBox,List datos,Boolean limpiar)
    {
        if(limpiar)
        {
            comboBox.removeAllItems();
        }
        
        for (Object object : datos) 
        {
            comboBox.addItem(object);
        }
    }
}
