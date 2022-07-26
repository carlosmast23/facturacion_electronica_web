/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.dialogos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 *
 * @author
 */
public class DialogoFecha extends DialogoFechaPanel{
    /**
     * Variable para almacenar los resultado de la fecha seleccinado
     */
    private Date fechaResultado;
    
        
    public DialogoFecha() {        
        super(null,true);
        //setVisible(true);
        setLocationRelativeTo(null);
        listenerBotones();
    }
    
     public DialogoFecha(Date fechaSetear) {
        super(null,true);
        getCmbFecha().setDate(fechaSetear);
        //setVisible(true);
        setLocationRelativeTo(null);
        listenerBotones();
    }

    
    
    private void listenerBotones() {
        
        getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fechaResultado=getCmbFecha().getDate();
                dispose();
            }
        });
        
        getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public Date getFechaResultado() {
        return fechaResultado;
    }
    
}
