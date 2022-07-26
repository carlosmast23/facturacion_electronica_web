/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.main.panel.ModoAplicativoDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author
 */
public class ModoAplicativoModel extends ModoAplicativoDialog{
    
    
    public static final Integer MODO_SERVIDOR=1;
    public static final Integer MODO_CLIENTE=2;
    public static final Integer MODO_CLIENTE_SERVIDOR=3;
    
    private Integer modo;
    public Boolean versionPrueba=false;
    
    public ModoAplicativoModel(Frame parent, boolean modal) {
        super(parent, modal);
        listenerBotones();
        
    }

    private void listenerBotones() {
        getBtnIniciarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modo=MODO_CLIENTE;
                dispose();
            }
        });
        
        getBtnIniciarClienteServidor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modo=MODO_CLIENTE_SERVIDOR;
                dispose();
            }
        });
        
        getBtnIniciarServidor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modo=MODO_SERVIDOR;
                dispose();
            }
        });
        
        getBtnIniciarModoPrueba().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modo=MODO_CLIENTE;
                versionPrueba=true;
                dispose();
            }
        });
    }

    public Integer getModo() {
        return modo;
    }
    
    
    
    
    
}
