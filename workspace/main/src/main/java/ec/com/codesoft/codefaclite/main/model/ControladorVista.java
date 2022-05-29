/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class ControladorVista {
    private List<GeneralPanelInterface> listaPaneles;

    public ControladorVista() {
        this.listaPaneles=new ArrayList<GeneralPanelInterface>();
    }
    
    public void agregarVista(GeneralPanelInterface panel)
    {
        this.listaPaneles.add(panel);
    }

    public List<GeneralPanelInterface> getListaPaneles() {
        return listaPaneles;
    }
    
    
}
