/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.AtsJaxb;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.VentaAts;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoAtsEnum;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author
 */
public interface AtsServiceIf extends Remote {
    public List<VentaAts> consultarVentasAts(java.sql.Date fechaInicial,java.sql.Date fechaFinal,Empresa empresa) throws  RemoteException,ServicioCodefacException;
    public AtsJaxb consultarAts(TipoAtsEnum tipoAtsEnum,Integer anio, MesEnum mes,Empresa empresa,String numeroSucursal,boolean  comprasBool, boolean  ventasBool,boolean anuladosBool) throws  RemoteException,ServicioCodefacException;
    
}
