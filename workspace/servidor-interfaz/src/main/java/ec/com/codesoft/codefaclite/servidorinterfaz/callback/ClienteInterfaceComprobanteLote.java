/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.callback;

import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteData;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public interface ClienteInterfaceComprobanteLote extends Remote{
    
    public void termino(List<ComprobanteData> comprobantes) throws RemoteException;
    
    public void iniciado() throws RemoteException;
    
    public void procesando(int etapa) throws RemoteException;
    
    public void error(ComprobanteElectronicoException cee) throws RemoteException;
    
}
