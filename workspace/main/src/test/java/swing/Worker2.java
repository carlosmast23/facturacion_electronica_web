/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing;

import java.util.List;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class Worker2 extends SwingWorker<Double, Integer> {
    // Esta JProgressBar la recibiremos en el constructor o en
    // un parametro setProgreso()
    private JProgressBar progreso;

    public Worker2(JProgressBar progreso) {
        this.progreso = progreso;
    }
    

    @Override
    protected void process(List<Integer> chunks) {
        System.out.println("process() esta en el hilo "
                + Thread.currentThread().getName());
        progreso.setValue(chunks.get(0));
    }

    @Override
    protected Double doInBackground() throws Exception {
        System.out.println("doInBackground() esta en el hilo "
                + Thread.currentThread().getName());
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("interrumpido");
            }

            // Se pasa valor para la barra de progreso. ESto llamara al metodo
            // process() en el hilo de despacho de eventos.
            publish(i + 1);
        }

        // Supuesto resultado de la tarea que tarda mucho.
        return 100.0;
    }
}
