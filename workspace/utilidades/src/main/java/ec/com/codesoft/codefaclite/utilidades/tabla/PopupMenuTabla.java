/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.tabla;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 *
 * @author robert
 */
public class PopupMenuTabla implements PopupMenuListener, ActionListener
{
    private final JPopupMenu popupMenu;
    private final JTable tabla;
    private List<JMenuItem> menuItems;
    private OpcionMenuListener opcion;
    private String opcionElegida;
    private Action action;

    public PopupMenuTabla(JTable tabla, List<JMenuItem> menuItems) 
    {
        this.popupMenu = new JPopupMenu();
        this.tabla = tabla;
        this.popupMenu.addPopupMenuListener(this);
        this.menuItems = menuItems;
        agregarMenuItems();
        this.tabla.setComponentPopupMenu(popupMenu);
    }
    
    
    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        SwingUtilities.invokeLater(new Runnable() 
        {
                    @Override
                    public void run() {
                        int rowAtPoint = tabla.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), tabla));
                        if (rowAtPoint > -1) {
                            tabla.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                        }
                    }
        });
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        SwingUtilities.invokeLater(new Runnable() 
        {
                    @Override
                    public void run() {
                         // force row selection upon exiting popup menu
                        // does not work; rowAtPoint always returns -1
                        int rowAtPoint = tabla.rowAtPoint(SwingUtilities.convertPoint(null, new Point(0, 0), tabla));
                        //int rowAtPoint = table.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), table));
                        if (rowAtPoint > -1) {
                            ListSelectionModel model = tabla.getSelectionModel();
                            model.clearSelection();
                        }    
                    }
        });
    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {
        popupMenu.setVisible(false);
    }

    private void agregarMenuItems() {
        for (JMenuItem menuItem : menuItems) {
            this.popupMenu.add(menuItem);
        }
    }

    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    private void crearMenuItems(String []menuOpciones) {
        this.menuItems = new ArrayList<>();
        for (String menuOpcione : menuOpciones) 
        {   
            this.menuItems.add(new JMenuItem(menuOpcione));
        }
        for (JMenuItem item: menuItems) {
            //item.addActionListener(new OpcionMenuListener());
            item.addActionListener(this);
        }
    }

    public List<JMenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<JMenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int fila = tabla.getSelectedRow();
        opcionElegida = e.getActionCommand();
        System.out.println("Menu opcion:"  + e.getActionCommand());
        System.out.println("NUmero de fila " + fila);
        
    }

    public String getOpcionElegida() {
        return opcionElegida;
    }

    public void setOpcionElegida(String opcionElegida) {
        this.opcionElegida = opcionElegida;
    }
    
    
    
}
