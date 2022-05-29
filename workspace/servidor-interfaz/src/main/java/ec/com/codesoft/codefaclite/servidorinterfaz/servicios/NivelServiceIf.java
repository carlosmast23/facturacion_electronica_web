/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo
 */
public interface NivelServiceIf extends ServiceAbstractIf<Nivel>{
    public List<Nivel> obtenerNivelesActivos() throws RemoteException;
    public Nivel obtenerNivelPorNombreYEstado(String nombre,GeneralEnumEstado estado) throws RemoteException;
    
}
