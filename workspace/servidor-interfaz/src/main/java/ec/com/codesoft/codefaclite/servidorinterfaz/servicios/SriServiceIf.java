/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriIdentificacion;
import java.rmi.Remote;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface SriServiceIf extends Remote
{
    public SriFormaPago obtenerFormarPagoDefecto() throws java.rmi.RemoteException;
    public SriFormaPago obtenerFormarPagoConCartera() throws java.rmi.RemoteException;
    public List<SriFormaPago> obtenerFormasPagoActivo() throws java.rmi.RemoteException;
    public List<SriIdentificacion> obtenerIdentificaciones(String tipo) throws java.rmi.RemoteException;
}
