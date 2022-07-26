/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.test;

import ec.com.codesoft.codefaclite.main.model.SplashScreenModel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class CargaMain {

    public static void main(String[] args) {

        SplashScreenModel model = new SplashScreenModel();

        model.agregarPorcentaje(10,"Iniciando valor 1");
        model.agregarPorcentaje(20,"Iniciando valor 2");
        model.agregarPorcentaje(50,"Iniciando valor 3");
        model.agregarPorcentaje(90,"Iniciando valor 4");
        model.agregarPorcentaje(100,"Iniciando valor 5");
        model.setVisible(true);
        model.iniciar();
        agregarProceso(model,3000l);
        agregarProceso(model,5000l);
        agregarProceso(model,6000l);
        agregarProceso(model,2000l);
        agregarProceso(model,1000l);
        agregarProceso(model,3000l);
        model.termino();

    }

    public static void agregarProceso(SplashScreenModel model, Long tiempo) {
        try {
            Thread.sleep(tiempo);
            System.out.println("proceso: " + tiempo);
            model.siguiente();
        } catch (InterruptedException ex) {
            Logger.getLogger(CargaMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
