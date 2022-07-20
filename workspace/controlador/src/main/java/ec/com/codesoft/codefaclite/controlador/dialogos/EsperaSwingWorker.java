/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.dialogos;

import ec.com.codesoft.codefaclite.controlador.panel.DialogoCargando;
import java.util.List;
import javax.swing.SwingWorker;

/**
 *TODO: Tener en cuenta cuando se utilicen varaibles de ambito general de la clase , toca pasar por parametro por que tiene un compartamiento extraño
 * Preocurar que la clase interna que va a implementar la interfaz EsperaSwingWorkerIf sea lo menos dependiente posible como una funcion
 * @auhor
 */
public class EsperaSwingWorker extends SwingWorker<Integer, Integer> {
    private DialogoCargando dialogo;
    
    private EsperaSwingWorkerIf interfaz;
    
    /**
     * TODO: Se usa este artificio por que en las clases internas las variables
     * de un ambito superior se pierden los valores
     */
    private Object[] parametros;
    
    public EsperaSwingWorker(String tituloDialogo,Object[] parametros,EsperaSwingWorkerIf interfaz) {
        this.dialogo = new DialogoCargando(null);
        this.dialogo.agregarTexto(tituloDialogo);
        this.interfaz=interfaz;
        this.parametros=parametros;
    }
    
    @Override
    protected Integer doInBackground() throws Exception {
        //TODO: En esta parte se va a ejecutar el proceso en segundo plano
        dialogo.setVisible(true);
        interfaz.ejecutarTarea(parametros);
        dialogo.setVisible(false);
        return 1000;
    }
    
        @Override
    protected void process(List<Integer> chunks) 
    {
        //TODO: Metodo que permite comunicarse para publicar algun evento
        //funciona con el metodo publish desde doInBckgorund
        //System.out.println("Chunk:"+chunks.get(0));
        //dialogo.agregarTexto("cargando "+chunks.get(0));
    }

    @Override
    protected void done() {
        System.out.println("done ...");
        dialogo.setVisible(false);
    }
}
