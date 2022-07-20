
import java.awt.BorderLayout;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @auhor
 */
public class TestDialogoEspera {
    
    public static void main(String[] args)
    {
        JPanel panel = new JPanel();
        //panel.add(showWaitBtn);
        JFrame frame = new JFrame("Frame");
        frame.getContentPane().add(panel);
        //frame.setVisible(true);
        
        
        final JDialog loading = new JDialog(frame);
        JPanel p1 = new JPanel(new BorderLayout());
        p1.add(new JLabel("ESPERE Por favor ..."), BorderLayout.CENTER);
        loading.setUndecorated(true);
        loading.getContentPane().add(p1);
        loading.pack();
        loading.setLocationRelativeTo(null);
        loading.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        loading.setModal(true);
        

        loading.setVisible(true);
        SwingWorker<Void,String> tareaFondo=new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                
                for (int i = 0; i < 10; i++) {
                    System.out.println("1");
                    Thread.sleep(1000);
                }
                return null;
            }

            @Override
            protected void done() {
                System.out.println("finalizo tarea");
                loading.dispose();
            }


        };
        
        tareaFondo.execute();
        try {
            tareaFondo.get();
        } catch (InterruptedException ex) {
            Logger.getLogger(TestDialogoEspera.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(TestDialogoEspera.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
