
import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
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
public class TestDialogoProcesando {

    public static void main(String args[]) {
        JButton showWaitBtn = new JButton(new DialogoEspera("Mostrar dialogo de espera"));
        
        JPanel panel = new JPanel();
        panel.add(showWaitBtn);
        JFrame frame = new JFrame("Frame");
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static class DialogoEspera extends AbstractAction {

        //protected static final long SLEEP_TIME = 3 * 1000;

        public DialogoEspera(String nombre) {
            super(nombre);
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
            SwingWorker<Void, Void> mySwingWorker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {

                    // mimic some long-running process here...
                    //Thread.sleep(SLEEP_TIME);
                    return null;
                }
            };

            Window win = SwingUtilities.getWindowAncestor((AbstractButton) evt.getSource());
            final JDialog dialog = new JDialog(win, "Dialogo", ModalityType.APPLICATION_MODAL);
            dialog.setUndecorated(true);

            mySwingWorker.addPropertyChangeListener(new PropertyChangeListener() {

                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if (evt.getPropertyName().equals("state")) 
                    {
                        if (evt.getNewValue() == SwingWorker.StateValue.DONE) 
                        {
                            dialog.dispose();
                        }
                    }
                }
            });
            
            mySwingWorker.execute();

            JProgressBar progressBar = new JProgressBar();
            progressBar.setIndeterminate(true);
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(progressBar, BorderLayout.CENTER);
            panel.add(new JLabel("Espere Por favor......."), BorderLayout.PAGE_START);
            dialog.add(panel);
            dialog.pack();
            dialog.setLocationRelativeTo(win);
            dialog.setVisible(true);
        }
    }

}
