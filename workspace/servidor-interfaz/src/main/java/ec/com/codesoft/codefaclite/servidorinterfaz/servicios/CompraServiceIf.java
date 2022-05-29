/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import com.healthmarketscience.rmiio.RemoteInputStream;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.CarteraParametro;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface CompraServiceIf extends ServiceAbstractIf<Compra>
{
    public void grabarCompra(Compra compra,CarteraParametro carteraParametro) throws ServicioCodefacException,java.rmi.RemoteException;
    public void editarCompra(Compra compra) throws ServicioCodefacException,java.rmi.RemoteException;
    public List<Compra> obtenerTodos() throws java.rmi.RemoteException;
    public List<Compra> obtenerCompraReporte(Persona proveedor, Date fechaInicial, Date fechaFin, DocumentoEnum de, TipoDocumentoEnum tde,GeneralEnumEstado estadoEnum,Empresa empresa) throws ServicioCodefacException,java.rmi.RemoteException;
    public List<Compra> obtenerCompraDisenable() throws java.rmi.RemoteException;
    public void eliminarCompra(Compra compra) throws ServicioCodefacException,RemoteException;
    public void eliminarCompraSinTransaccion(Compra compra) throws ServicioCodefacException, RemoteException;
    public Compra obtenerCompraDesdeXml(RemoteInputStream archivoCompraXml,Empresa empresa) throws RemoteException,ServicioCodefacException;
    
}
