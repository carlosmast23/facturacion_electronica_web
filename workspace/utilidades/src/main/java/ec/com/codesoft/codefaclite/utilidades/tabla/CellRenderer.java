/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.tabla;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class CellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //establecemos el fondo blanco o vacío
        setBackground(null);
        //COnstructor de la clase DefaultTableCellRenderer
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        //Creamos un color para las filas. El 200, 200, 200 en RGB es un color gris
        Color c = new Color(200, 200, 200);
        
        //Establecemos las filas que queremos cambiar el color.
        if(table.getValueAt(row, 0) == null)
        {
           setBackground(c);
        }
        
        return this;
    }
       
}
