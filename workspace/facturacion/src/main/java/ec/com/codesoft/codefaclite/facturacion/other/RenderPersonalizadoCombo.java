/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.other;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

/**
 *
 * @author
 */
public class RenderPersonalizadoCombo implements ListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Sucursal sucursal=(Sucursal)value;
        JTextField jTextField=new JTextField(sucursal.getCodigoSucursal());
        return jTextField;
    }
    
}
