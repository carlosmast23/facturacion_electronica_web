/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.core;

import java.io.Serializable;

/**
 *
 * @auhor
 */
public abstract class GeneralPublicoAbstractMb implements Serializable{
    public static final String EJEMPLO="hola";
    
    private ControllerPublicoMb controladorPlantilla;   
    
    public abstract void postAddController();

    public ControllerPublicoMb getControladorPlantilla() {
        return controladorPlantilla;
    }

    public void setControladorPlantilla(ControllerPublicoMb controladorPlantilla) {
        this.controladorPlantilla = controladorPlantilla;
    }
    
    public void agregarControlador(ControllerPublicoMb controladorPlantilla)
    {        
        setControladorPlantilla(controladorPlantilla);
        postAddController();
        
    }    
        
}
