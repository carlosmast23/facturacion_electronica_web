/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.panelessecundariomodel;

import ec.com.codesoft.codefaclite.controlador.panel.ValidadorCodefacPanel;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.MouseListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class ValidadorCodefacModel extends ValidadorCodefacPanel{
    public static final String PROPIEDAD_TABLA="tabla";

    public ValidadorCodefacModel() {
        
    }
    
    @Override
    public String getNombrePanel() {
        return ValidadorCodefacModel.PANEL_VALIDACION;
    }

    @Override
    public void actualizar(Object obj) {
        DefaultTableModel model=(DefaultTableModel) obj;
        getTblDatosValidar().setModel(model);
        getTblDatosValidar().repaint();
    }
    
    @Override
    public Object getPropertyByNombre(String nombre) {
        switch(nombre)
        {
            case PROPIEDAD_TABLA:
                return getTblDatosValidar();
        }
        return null;
    }

    
}
