
import ec.com.codesoft.codefaclite.main.init.Main;
import ec.com.codesoft.codefaclite.main.panel.LoginFormDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EstiloCodefacEnum;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carlos
 */
public class TestTemas {
    public static void main(String[] args) {
        //Main.setearEstiloSistema(EstiloCodefacEnum.GLASS);
        LoginFormDialog form=new LoginFormDialog(null, true);
        
         form.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                //JOptionPane.showMessageDialog(null,"adasd");
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                //JOptionPane.showMessageDialog(null,"adasd");
            }

            @Override
            public void componentShown(ComponentEvent e) {
                //JOptionPane.showMessageDialog(null,"adasd");
                form.getTxtClave().requestFocus();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                //JOptionPane.showMessageDialog(null,"adasd");
            }
        });
        
        //form.getTxtUsuario().setText("asdasdasd");
        
        //form.getTxtClave().requestFocus();
        form.setVisible(true);
       
        //form.getTxtUsuario().requestFocus();
        
        //form.setVisible(true);
    }
}
