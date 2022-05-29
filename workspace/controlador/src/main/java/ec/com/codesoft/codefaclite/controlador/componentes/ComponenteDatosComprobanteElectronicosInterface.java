/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.componentes;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.core.swing.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface ComponenteDatosComprobanteElectronicosInterface {
    public ComprobanteEntity getComprobante();
    public Empresa getEmpresa();
    public InterfazComunicacionPanel getPanelPadre();
    public List<ComprobanteAdicional> getDatosAdicionales();
    public GeneralPanelInterface.EstadoFormularioEnum getEstadoFormularioEnum();
    public ClienteInterfaceComprobante getInterfaceComprobante() throws RemoteException;
    public ComprobanteDataInterface obtenerComprobanteData();
    
}
