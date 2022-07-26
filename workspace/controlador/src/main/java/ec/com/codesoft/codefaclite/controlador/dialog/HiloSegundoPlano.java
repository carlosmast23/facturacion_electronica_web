/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.dialog;

import ec.com.codesoft.codefaclite.controlador.panel.DialogoCargando;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class HiloSegundoPlano extends Thread{
    private ProcesoSegundoPlano proceso;
    private DialogoCargando dialogo;
    private ExcepcionCodefacLite excepcion;

    public HiloSegundoPlano(ProcesoSegundoPlano proceso, DialogoCargando dialogo) {
        this.proceso = proceso;
        this.dialogo = dialogo;
    }

    
    
    @Override
    public void run() {
        try {
            proceso.procesar();
            dialogo.setVisible(false);
        } catch (ExcepcionCodefacLite ex) {
            excepcion=ex;
            Logger.getLogger(HiloSegundoPlano.class.getName()).log(Level.SEVERE, null, ex);
            dialogo.setVisible(false);
        }
    }
    
    public void verificarError() throws ExcepcionCodefacLite
    {
        if(excepcion!=null)
        {
            throw excepcion;
        }
    }
    
}
