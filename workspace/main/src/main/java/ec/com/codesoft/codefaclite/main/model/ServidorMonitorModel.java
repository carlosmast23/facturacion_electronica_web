/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.main.panel.ServidorMonitorPanel;
import ec.com.codesoft.codefaclite.servidor.service.interfacePanel.ServidorMonitorUpdateInterface;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadVarios;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author
 */
public class ServidorMonitorModel extends ServidorMonitorPanel implements ServidorMonitorUpdateInterface{
                
    public ServidorMonitorModel() {
        valoresIniciales();
        listenerBotones();
    }

    private void listenerBotones() {
        getBtnTerminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean salir= DialogoCodefac.dialogoPregunta("Advertencia","Advertencia: Se perderan todas las conexiones con los clientes \n Esta seguro que desea salir ?",DialogoCodefac.MENSAJE_ADVERTENCIA);
                if(salir)
                {
                    System.exit(0);
                }
            }
        });
        
        
    }

    private void valoresIniciales() {
        //Todo: Este valor esta de revisar porque esta cogiendo por defecto el primero de la interfaz y cuando tenga varias interfacez va a generar error
        //String ipServidor=InetAddress.getLocalHost().getHostAddress();
        String ipServidor=UtilidadVarios.obtenerIpServidor();
        getLblDireccionIp().setText(ipServidor);
        getLblEstado().setText("Activo");
    }

    @Override
    public void actualizarNumeroConexiones(String[] conexiones) {
        getLblClientesRegistrados().setText(conexiones.length+"");
        
        //Actualizar los valores de la tabla
        getLstClientesConectados().removeAll();
        DefaultListModel listModel = new DefaultListModel();
        for (String conexion : conexiones) {
            listModel.addElement(conexion.toString());
        }
        getLstClientesConectados().setModel(listModel);
    }
    
}
