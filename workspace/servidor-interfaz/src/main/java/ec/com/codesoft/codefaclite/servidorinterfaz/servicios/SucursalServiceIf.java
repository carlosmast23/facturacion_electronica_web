/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 */
public interface SucursalServiceIf  extends ServiceAbstractIf<Sucursal>{
    
    public abstract List<Sucursal> consultarActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException, RemoteException; 
    public abstract Sucursal obtenerPorCodigo(Integer codigo) throws ServicioCodefacException, RemoteException;
    public abstract Sucursal obtenerMatrizPorSucursal(Empresa empresa) throws ServicioCodefacException, RemoteException;
    //public Sucursal consultarMatrizPorEmpresa(Empresa empresa) throws ServicioCodefacException, RemoteException;
}
