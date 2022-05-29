/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.auxiliar.KardexDetalleTmp;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.TransferenciaBodegaRespuesta;
import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;
import java.util.Map;


/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface KardexServiceIf extends ServiceAbstractIf<Kardex>
{    
    public Kardex buscarKardexPorProductoyBodega(Bodega bodega,Producto producto) throws java.rmi.RemoteException;
    public void ingresoEgresoInventarioEnsamble(Bodega bodegaOrigenMateriales,Bodega bodegaDestino,Producto productoEnsamble,BigDecimal cantidad,ProductoEnsamble.EnsambleAccionEnum accion,Boolean validarStockComponentes) throws java.rmi.RemoteException,ServicioCodefacException;
    public void ingresarInventario(Map<KardexDetalle,CompraDetalle> detalles,Bodega bodega) throws java.rmi.RemoteException,ServicioCodefacException;
    public void ingresarInventario(List<KardexDetalle> detalles) throws java.rmi.RemoteException,ServicioCodefacException;
    public void ingresarInventario(KardexDetalle detalle,Lote lote) throws java.rmi.RemoteException,ServicioCodefacException;
    public List<KardexDetalle> obtenerConsultaPorFecha(Date fechaInicial , Date fechaFinal,Producto producto,Bodega bodega,Integer cantidadMovimientos) throws java.rmi.RemoteException;
    public List<Object[]> consultarStockMinimo(Bodega bodega,CategoriaProducto categoria,Empresa empresa) throws java.rmi.RemoteException;    
    public List<Kardex> buscarPorProducto(Producto producto) throws java.rmi.RemoteException,ServicioCodefacException;
    public List<Kardex> buscarPorBodega(Bodega bodega) throws java.rmi.RemoteException,ServicioCodefacException;
    public List<Object[]> consultarStock(Bodega bodega,CategoriaProducto categoria,Empresa empresa) throws java.rmi.RemoteException;
    public List<Kardex> buscarPorProductoYBodega(Producto producto,Bodega bodega) throws java.rmi.RemoteException,ServicioCodefacException;
    public void transferirProductoBodegas(Producto producto,Bodega bodegaOrigen,Bodega bodegaDestino, String descripcion,BigDecimal cantidad,BigDecimal precio,Date fechaTransaccion) throws java.rmi.RemoteException,ServicioCodefacException;
    public  KardexDetalle crearKardexDetalleSinPersistencia(Kardex kardex,TipoDocumentoEnum tipoDocumentoEnum,BigDecimal precioUnitario,BigDecimal cantidad) throws java.rmi.RemoteException,ServicioCodefacException;
    public void recalcularValoresKardex(Kardex kardex,KardexDetalle kardexDetalle) throws java.rmi.RemoteException,ServicioCodefacException;
    public boolean obtenerSiNoExisteStockProducto(Bodega bodega, Producto producto, BigDecimal cantidad) throws java.rmi.RemoteException;
    
    public List<Kardex> getKardexModificados(Producto productoEnsamble,BigDecimal cantidadEnsamble,Bodega bodega,ProductoEnsamble.EnsambleAccionEnum accion) throws java.rmi.RemoteException,ServicioCodefacException;
    public Kardex ingresoEgresoInventarioEnsambleSinTransaccion(Bodega bodegaOrigenMateriales,Bodega bodegaDestino, Producto productoEnsamble,BigDecimal cantidad,ProductoEnsamble.EnsambleAccionEnum accion,Boolean validarStockComponentes) throws java.rmi.RemoteException,ServicioCodefacException;
    
    public Kardex construirKardexVacioSinPersistencia() throws java.rmi.RemoteException,ServicioCodefacException;
    
    public List<Kardex> buscarPorProducto(Producto producto,GeneralEnumEstado generalEnumEstado) throws java.rmi.RemoteException,ServicioCodefacException;
    /**
     * Este metodo permite crear un movimiento que permite dejar en 0 el Stock actual
     * @param kardex
     * @throws java.rmi.RemoteException
     * @throws ServicioCodefacException 
     */
    public void anularInventario(Kardex kardex) throws java.rmi.RemoteException,ServicioCodefacException;
    
    public Kardex buscarKardexPorProducto(Producto producto) throws java.rmi.RemoteException;
    
    public  Kardex consultarOCrearStockSinPersistencia(Producto producto, Bodega bodega,Lote lote) throws RemoteException, ServicioCodefacException;
    
    public List<TransferenciaBodegaRespuesta> consultarMovimientosTransferencia(java.util.Date fechaInicial, java.util.Date fechaFinal,Bodega bodegaDestino) throws java.rmi.RemoteException,ServicioCodefacException;
    
    public KardexDetalle afectarInventario(Bodega bodega,BigDecimal cantidad,BigDecimal precioUnitario,BigDecimal total,Long referenciaKardexId,Long referenciaProductoId,TipoDocumentoEnum tipoDocumento,String puntoEmision,String puntoEstablecimiento,Integer secuencial,Date fechaDocumento) throws RemoteException,ServicioCodefacException;
    
    public Integer consultarCantidadStockMinimo(Empresa empresa) throws java.rmi.RemoteException;
    
    public List<Kardex> buscarPorProductoyBodega(Bodega bodega,Producto producto) throws java.rmi.RemoteException;
    
    public Kardex crearObjeto(Bodega bodega,Producto producto,Lote lote) throws java.rmi.RemoteException,ServicioCodefacException;
    
    public void crearKardexSiNoExisteSinTransaccion(Producto producto) throws java.rmi.RemoteException,ServicioCodefacException;

}
