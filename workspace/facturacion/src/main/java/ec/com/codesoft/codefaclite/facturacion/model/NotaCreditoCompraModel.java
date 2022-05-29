/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class NotaCreditoCompraModel extends NotaCreditoModel
{
    @Override
    protected void iniciarComponentesVista() {
        getToolBarSecuencialNCVenta().setVisible(false);
        getPnlSecuencialNCCompra().setVisible(true);
    }
    
}
