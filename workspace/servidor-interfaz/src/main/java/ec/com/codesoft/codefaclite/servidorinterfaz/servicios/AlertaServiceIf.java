/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.common.AlertaResponse;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModoProcesarEnum;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author CARLOS_CODESOFT
 */
public interface AlertaServiceIf extends Remote {
    
    public void procesoBloqueado(Empresa empresaIf) throws RemoteException,ServicioCodefacException;
    
    public List<AlertaResponse> actualizarNotificaciones(Empresa empresa,ModoProcesarEnum modoEnum) throws RemoteException,ServicioCodefacException;
    public List<AlertaResponse> actualizarNotificacionesCargaRapida(Empresa empresa) throws RemoteException,ServicioCodefacException;
    public List<AlertaResponse> actualizarNotificacionesCargaLenta(Empresa empresa,ModoProcesarEnum modoEnum) throws RemoteException,ServicioCodefacException;
    
}
