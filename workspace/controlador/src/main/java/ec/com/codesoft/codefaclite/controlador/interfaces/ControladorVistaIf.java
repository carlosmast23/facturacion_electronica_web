/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.interfaces;

import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;

/**
 *
 * @auhor
 */
public interface ControladorVistaIf {
    /**
     * Metodo que permite obtener el controlador que va a controlar el flujo de
     * las vistas
     * @return 
     */
    public ModelControladorAbstract getControladorVista();
    
}
