/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Carlos
 */
public class CountryRenderer extends JLabel implements ListCellRenderer<JPanel> {

    CountryRenderer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
    @Override
    public Component getListCellRendererComponent(JList<? extends JPanel> list, JPanel country, int index,
        boolean isSelected, boolean cellHasFocus) {
         /* 
        String code = country.getCode();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/images/" + code + ".png"));
         
        setIcon(imageIcon);*/
        setText("SDASDASD");
         
        return this;
    }
}
