/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.model;

import ec.com.codesoft.codefaclite.controlador.panel.ReporteDialogo;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Carlos
 */
public class ReporteDialogModel extends ReporteDialogo{
    
    private ReporteDialogListener listener;
    
    public ReporteDialogModel() 
    {
        super(null,true);
        listenerBotones();
        //Centra el dialogo
        this.setLocationRelativeTo(null);
        
    }
    
    private void listenerBotones() {
        getBtnPdf().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(listener!=null)
                {
                    listener.pdf();
                    dispose();
                }
            }
        });
        
        getBtnExcel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.excel();
                dispose();
            }
        });
        
        getBtnSalir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    
    
    
        public void addListener(ReporteDialogListener listener)
    {
        this.listener=listener;
    }

    
    
}
