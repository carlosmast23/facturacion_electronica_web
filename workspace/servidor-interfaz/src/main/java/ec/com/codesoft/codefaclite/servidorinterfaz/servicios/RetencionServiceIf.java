/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface RetencionServiceIf extends ServiceAbstractIf<Retencion> {

   public List<Object[]> obtenerRetencionesCodigo(Persona persona, Date fi, Date ff, SriRetencionIva iva,SriRetencionRenta renta,String tipo) throws java.rmi.RemoteException;
   public List<RetencionDetalle> obtenerRetencionesReportes(Persona persona, Date fi, Date ff, SriRetencionIva iva, SriRetencionRenta renta, SriRetencion sriRetencion,ComprobanteEntity.ComprobanteEnumEstado estadoEnum,Empresa empresa) throws RemoteException;
   public List<Object[]> obtenerRetencionesIvaPorCompra(Compra compra,SriRetencion sriRetencion)throws RemoteException;
   public List<RetencionDetalle> obtenerRetencionesRentaPorCompra(Compra compra,SriRetencion sriRetencion)throws RemoteException;
   public List<Retencion> obtenerRetencionesPorCompra(Compra compra) throws ServicioCodefacException, RemoteException;
   public List<Retencion> obtenerRetencionesSinDetalleReportes(Persona persona, Date fi, Date ff, SriRetencionIva iva, SriRetencionRenta renta, SriRetencion sriRetencion,ComprobanteEntity.ComprobanteEnumEstado estadoEnum,Empresa empresa) throws RemoteException;

}
