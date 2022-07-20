/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing;

import ec.com.codesoft.codefaclite.controlador.panel.DialogoCargando;
import java.util.List;
import javax.swing.SwingWorker;

/**
 *
 * @auhor
 */
public class EsperaSwingWorker extends SwingWorker<Integer, Integer>{
    
    private DialogoCargando dialogo;

    public EsperaSwingWorker(String texto) {
        //this.dialogo = new DialogoCargando();
        //this.dialogo.agregarTexto(texto);
    }
    

    @Override
    protected Integer doInBackground() throws Exception {
        //TODO: En esta parte se va a ejecutar el proceso en segundo plano
        dialogo.setVisible(true);
        for (int i = 0; i < 10; i++) {
            System.out.println("imprimiendo "+i);
            Thread.sleep(1000l);
            publish(i + 1);
        }
        dialogo.setVisible(false);
        return 1000;
    }

    @Override
    protected void process(List<Integer> chunks) 
    {
        System.out.println("Chunk:"+chunks.get(0));
        dialogo.agregarTexto("cargando "+chunks.get(0));
    }

    @Override
    protected void done() {
        System.out.println("done ...");
    }
    
    
    
    
    
}
