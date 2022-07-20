/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing;

import ec.com.codesoft.codefaclite.controlador.panel.DialogoCargando;
import java.awt.FlowLayout;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @auhor
 */
public class PruebaSwing {

    public static void main(String args[]) {
        
        try {
            EsperaSwingWorker swingWorker=new EsperaSwingWorker("Ejemplo");
            swingWorker.execute();
            swingWorker.get();
            
            /*try {
            JProgressBar dialogoProgreso = new JProgressBar(0, 100);
            dialogoProgreso.setStringPainted(true);
            Worker2 worker = new Worker2(dialogoProgreso);
            dialogoProgreso.setVisible(true);
            worker.execute();
            
            // espera a que termine
            worker.get();

            dialogoProgreso.setVisible(false);
            } catch (InterruptedException ex) {
            Logger.getLogger(PruebaSwing.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
            Logger.getLogger(PruebaSwing.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        } catch (InterruptedException ex) {
            Logger.getLogger(PruebaSwing.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(PruebaSwing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static class  Dialogo extends JDialog {
        //un dialogo con barra de progreso

        JProgressBar progressBar;

        public Dialogo() {
            setTitle("Espere por favor");
            setLayout(new FlowLayout());
            
            progressBar = new JProgressBar();
            progressBar.setIndeterminate(true);

            add(new JLabel("Analizando: "));
            add(progressBar);
        }
}

}
