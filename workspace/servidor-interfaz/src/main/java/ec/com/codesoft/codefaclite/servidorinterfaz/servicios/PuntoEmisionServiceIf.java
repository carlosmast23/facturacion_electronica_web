/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author
 */
public interface PuntoEmisionServiceIf extends ServiceAbstractIf<PuntoEmision>{
    public abstract List<PuntoEmision> obtenerActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException, RemoteException;
    public abstract List<PuntoEmision> obtenerActivosPorSucursal(Sucursal sucursal) throws ServicioCodefacException, RemoteException;
    public abstract PuntoEmision obtenerPorCodigo(Integer codigo,Sucursal sucursal) throws ServicioCodefacException, RemoteException;
    //public abstract Boolean validarPuntoEmisionSinTransaccion(PuntoEmision puntoEmision) throws ServicioCodefacException, RemoteException;
}
