/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceAbstractIf;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author CARLOS_CODESOFT
 */
public interface HerramientasCodefacServiceIf extends ServiceAbstractIf<Object>{

    public List buscarClavesRepetidasBaseDatosLista(String nombreTabla,String nombrePk) throws RemoteException,ServicioCodefacException;
    public void corregirDatosDuplicadosPk(List<Factura> facturasProcesarList) throws RemoteException,ServicioCodefacException;
}
