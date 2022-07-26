/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.swing;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author
 */
public class MyComboBoxRenderer extends JLabel implements ListCellRenderer {
  private String _title;

  public MyComboBoxRenderer(String title) {
    _title = title;
  }

  @Override
  public Component getListCellRendererComponent(JList list, Object value,
      int index, boolean isSelected, boolean hasFocus) {
    if (index == -1 && value == null)
      setText(_title);
    else
      setText(value.toString());
    return this;
  }
}