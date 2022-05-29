/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.panelessecundariomodel;

import java.awt.event.MouseListener;

/**
 *
 * @author Carlos
 */
public abstract class PanelSecundarioAbstract extends javax.swing.JPanel {
    public static final String PANEL_AYUDA="Ayuda";
    public static final String PANEL_MONITOR="Monitor";
    public static final String PANEL_VALIDACION="Validacion";
    
    private PanelSecundarioListener listener;
    
    public abstract String getNombrePanel();
    /**
     * Metodo que implementa como se van a actualizar los datos en la vista
     * @param obj 
     */
    public abstract void actualizar(Object obj);
    
    /**
     * Metodo que devuelve una propiedad del objeto dependiendo el nombre que se pase por parametro
     * @param listener 
     */
    public abstract Object getPropertyByNombre(String nombre); 
    
    
    public void addListenerPanelSecundario(PanelSecundarioListener listener)
    {
        this.listener=listener;
    }
    
    public void mostrar()
    {
        this.listener.mostrar();
    }
    

}
