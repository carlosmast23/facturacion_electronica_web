/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import com.sun.mail.handlers.multipart_mixed;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.facade.KardexFacade;
import ec.com.codesoft.codefaclite.servidor.facade.KardexFacade.StockPromedioYCantidadRespuesta;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesServidor;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexItemEspecifico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.auxiliar.KardexDetalleTmp;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FechaFormatoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.RotacionInventarioRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.TransferenciaBodegaRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.fecha.ObtenerFecha;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author
 */
public class KardexService extends ServiceAbstract<Kardex,KardexFacade> implements KardexServiceIf
{
    
    /**
     * Entidad de control para manejar transacciones con la base de datos
     */
    private EntityManager em;
    
    public KardexService() throws RemoteException {
        super(KardexFacade.class);
        em=AbstractFacade.entityManager;
    }
    
    /**
     * Metodo que permite buscar el kardex por bodega y el el producto
     * @return 
     */
    //TODO:Analizar para poder mostrar el listado de todos los karde 
    @Deprecated
    public Kardex buscarKardexPorProductoyBodega(Bodega bodega,Producto producto) throws java.rmi.RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("bodega",bodega);
        mapParametros.put("producto",producto);   
        List<Kardex> listaKardex=getFacade().findByMap(mapParametros);
        //List<Kardex> listaKardex=obtenerPorMap(mapParametros);
        
        if(listaKardex!=null && listaKardex.size()>0)
        {
            return listaKardex.get(0);
        }
        
        return null;
    }
    
    public List<Kardex> buscarPorProductoyBodega(Bodega bodega,Producto producto) throws java.rmi.RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("bodega",bodega);
        mapParametros.put("producto",producto);   
        List<Kardex> listaKardex=getFacade().findByMap(mapParametros);
        //List<Kardex> listaKardex=obtenerPorMap(mapParametros);
                
        return listaKardex;
    }
    
    public Kardex buscarKardexPorProductoyBodegayLote(Bodega bodega,Producto producto,Lote lote) throws java.rmi.RemoteException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bodega", bodega);
        map.put("producto", producto);
        map.put("lote", lote);
        
        List<Kardex> listaKardex=getFacade().findByMap(map);
        //List<Kardex> listaKardex=obtenerPorMap(mapParametros);
        
        if(listaKardex!=null && listaKardex.size()>0)
        {
            return listaKardex.get(0);
        }
        
        return null;
    }
    
    /**
     * @deprecated 
     * TODO: Este metodo solo es temporal hasta solucionar un problema con el reporte de factura con costos
     * @param producto
     * @return
     * @throws java.rmi.RemoteException 
     */
   public Kardex buscarKardexPorProducto(Producto producto) throws java.rmi.RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        //mapParametros.put("bodega",bodega);
        mapParametros.put("producto",producto);   
        List<Kardex> listaKardex=getFacade().findByMap(mapParametros);
        //List<Kardex> listaKardex=obtenerPorMap(mapParametros);
        
        if(listaKardex!=null && listaKardex.size()>0)
        {
            return listaKardex.get(0);
        }
        
        return null;
    }
   
   /**
    * Metodo que me permite crear un nuevo kardex cuando no existe creado
    * @return
    * @throws java.rmi.RemoteException
    * @throws ServicioCodefacException 
    */
   @Deprecated //TODO: Hacer que funcione con los Lotes
   public void crearKardexSiNoExisteSinTransaccion(Producto producto) throws java.rmi.RemoteException,ServicioCodefacException
   {
       //Validar que tengan una empresa creada
       if(producto.getEmpresa()==null)
       {
           throw new ServicioCodefacException("No se puede crear el kardex de un producto que no tiene una empresa");
       }
       
       BodegaService bodegaService = new BodegaService();
       List<Bodega> bodegaVentaList = bodegaService.obtenerActivosPorEmpresa(producto.getEmpresa());
       if (bodegaVentaList.size() == 0) {
           throw new ServicioCodefacException("Configure primero una bodega ");
       }

       for (Bodega bodega : bodegaVentaList) {
           List<Kardex> kardexList = buscarPorProductoYBodega(producto, bodega);
           //Solo crear el kardex si no existe para esa bodega
           if (kardexList.size() == 0) 
           {
               Kardex kardex = ServiceFactory.getFactory().getKardexServiceIf().crearObjeto(bodega, producto,null);
               entityManager.persist(kardex);
           }
       }
   }
    
    /**
     * Obtiene los valores modificos del stock y la reserva para grabar en el Kardex
     * @return 
     */
    public List<Kardex> getKardexModificados(Producto productoEnsamble,BigDecimal cantidadEnsamble,Bodega bodega,ProductoEnsamble.EnsambleAccionEnum accion) throws java.rmi.RemoteException,ServicioCodefacException
    {
        //Integer cantidadEnsamble=Integer.parseInt(getTxtCantidad().getText());
        
        //Bodega bodega = (Bodega) getCmbBodega().getSelectedItem();
        //String accion = getCmbAccion().getSelectedItem().toString();
        List<Kardex> kardeList=new ArrayList<Kardex>();
        
        for(ProductoEnsamble componenteProducto: productoEnsamble.getDetallesEnsamble())
        {
            try {
                Vector<String> fila=new Vector<String>();
                BigDecimal cantidadProducto=componenteProducto.getCantidad();
                
                Producto componente=componenteProducto.getComponenteEnsamble();
                //Map<String,Object> mapParametros=new HashMap<String,Object>();
                //mapParametros.put("producto",componente);
                //mapParametros.put("bodega",bodega);
                KardexServiceIf servicioKardex=ServiceFactory.getFactory().getKardexServiceIf();
                Kardex kardexComponente= servicioKardex.buscarKardexPorProductoyBodega(bodega,componente);
                
                //Si no existe el kardex del componente que intento facturar lo debo crear
                if(kardexComponente==null)
                {
                    kardexComponente=consultarOCrearStockSinPersistencia(componente, bodega,null);
                }
                                
                
                if(kardexComponente!=null)
                {
                    BigDecimal cantidadTotal=cantidadEnsamble.multiply(cantidadProducto);
                    //Kardex kardexComponente=listaKardex.get(0);
                    //Este paso lo hago porque cuando seteo un valor a una entidad cuando esta asociado automaticamente se refleja en la base de datos
                    //ServiceAbstract.desasociarEntidadRecursivo(kardexComponente);
                    
                    if(accion.equals(ProductoEnsamble.EnsambleAccionEnum.AGREGAR)  || accion.equals(ProductoEnsamble.EnsambleAccionEnum.CONSTRUIR_FACTURA))
                    {
                        kardexComponente.setReserva(new BigDecimal(kardexComponente.getReserva()).add(cantidadTotal).intValue());
                        kardexComponente.setStock(kardexComponente.getStock().subtract(cantidadTotal));
                    }
                    else
                    {
                        kardexComponente.setReserva(new BigDecimal(kardexComponente.getReserva()).subtract(cantidadTotal).intValue());
                        kardexComponente.setStock(kardexComponente.getStock().add(cantidadTotal));
                    }
                    
                    //Agregar el detalle de kardex
                    KardexDetalle kardexDetalle=new KardexDetalle();
                    kardexDetalle.setCantidad(cantidadTotal);
                    
                    if(accion.equals(ProductoEnsamble.EnsambleAccionEnum.AGREGAR) ||
                            accion.equals(ProductoEnsamble.EnsambleAccionEnum.CONSTRUIR_FACTURA))
                    {
                        kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.ENSAMBLE_EGRESO.getCodigo());
                    }
                    else
                    {
                        kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.ENSAMBLE_INGRESO.getCodigo());
                    }
                    
                    kardexDetalle.setPrecioTotal(cantidadTotal.multiply(kardexComponente.getPrecioUltimo()));
                    kardexDetalle.setPrecioUnitario(kardexComponente.getPrecioUltimo());
                    kardexDetalle.setReferenciaDocumentoId(null);
                    kardexDetalle.setFechaCreacion(UtilidadesFecha.getFechaHoyTimeStamp());
                    kardexDetalle.setFechaDocumento(UtilidadesFecha.getFechaHoy());
                    kardexDetalle.setFechaIngreso(UtilidadesFecha.getFechaHoyTimeStamp());
                    
                    kardexComponente.addDetalleKardex(kardexDetalle);
                    
                    kardeList.add(kardexComponente);
                    
                }
            } catch (RemoteException ex) {
                Logger.getLogger(KardexService.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        
        return kardeList;
        
    }
    
    public  Kardex consultarOCrearStockSinPersistencia(Producto producto, Bodega bodega,Lote lote) throws RemoteException, ServicioCodefacException
    {
        
        //Producto producto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(detalle.getReferenciaId());
        //Map<String,Object> mapParametros=new HashMap<String,Object>();
        //mapParametros.put("producto", producto);
        KardexService kardexService = new KardexService();
        //List<Kardex> kardexs = kardexService.buscarKardexPorProductoyBodegayLote(bodega,producto,lote);
        
        Kardex kardex = kardexService.buscarKardexPorProductoyBodegayLote(bodega,producto,lote);

        //Kardex kardex = null;
        if (kardex == null) 
        {
            kardex = kardexService.crearObjeto(bodega, producto,lote);
            entityManager.persist(kardex);
            entityManager.flush();
        } 
        //else {
        //    kardex = kardex.get(0);
        //}
        
        return kardex;

    }
    
    
    
    public void ingresoEgresoInventarioEnsamble(Bodega bodegaOrigenMateriales,Bodega bodegaDestino,Producto productoEnsamble,BigDecimal cantidad,ProductoEnsamble.EnsambleAccionEnum accion,Boolean validarStockComponentes) throws java.rmi.RemoteException,ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                ingresoEgresoInventarioEnsambleSinTransaccion(bodegaOrigenMateriales,bodegaDestino, productoEnsamble,cantidad, ProductoEnsamble.EnsambleAccionEnum.AGREGAR,validarStockComponentes);
            }
        });
        
        
    }
    
    public Kardex ingresoEgresoInventarioEnsambleSinTransaccion(Bodega bodegaOrigenMateriales,Bodega bodegaDestino, Producto productoEnsamble,BigDecimal cantidad,ProductoEnsamble.EnsambleAccionEnum accion,Boolean validarStockComponentes) throws java.rmi.RemoteException,ServicioCodefacException
    {
        /**
         * ==========> Validar Disponibilidad de los producto<==========
         */
        
        //Verificar si tiene habilitada la opcion de FACTURA INVENTARIO NEGATIVO activado para validar esta opcion 
        if(validarStockComponentes && (accion.equals(ProductoEnsamble.EnsambleAccionEnum.CONSTRUIR_FACTURA) )
                && ParametroUtilidades.comparar(bodegaOrigenMateriales.getEmpresa(),ParametroCodefac.FACTURAR_INVENTARIO_NEGATIVO,EnumSiNo.NO))
        {
            //verifica que EXISTA STOCK EN LOS COMPONENTES PARA EL COMBO//
            validarEnsambleComponentes(productoEnsamble, bodegaOrigenMateriales, cantidad);
        }
        
        /**
         * ===============> Buscar el Ensamble de producto o crear*/
        //TODO: Mejorar esta parte para obtener directamente desde un servicio
        Map<String, Object> parametrosMap = new HashMap<String, Object>();
        parametrosMap.put("bodega", bodegaDestino);
        parametrosMap.put("producto", productoEnsamble);
        List<Kardex> kardexList = obtenerPorMap(parametrosMap);

        //Obtener o crear el kardex si no existe
        Kardex kardexEnsamble = null;
        if (kardexList != null && kardexList.size() > 0) {
            kardexEnsamble = kardexEnsamble = kardexList.get(0);
        } else {
            kardexEnsamble = crearObjeto(bodegaDestino, productoEnsamble,null);
            entityManager.persist(kardexEnsamble);
        }
        
        
        List<Kardex> componentesKardex = getKardexModificados(productoEnsamble, cantidad, bodegaOrigenMateriales, accion);
        //Actualizar los detalles de los componentes del kardex                        
        for (Kardex kardexComponente : componentesKardex) {
            entityManager.merge(kardexComponente);

        }

        //CALCULAR EL COSTO DEL ENSAMBLE
        BigDecimal costoIndividualEnsamble = BigDecimal.ZERO;
        List<ProductoEnsamble> listaComponentes = kardexEnsamble.getProducto().getDetallesEnsamble();
        for (ProductoEnsamble componenteEmsamble : listaComponentes) {
            parametrosMap = new HashMap<String, Object>();
            parametrosMap.put("bodega", bodegaDestino);
            parametrosMap.put("producto", componenteEmsamble.getComponenteEnsamble());
            kardexList = obtenerPorMap(parametrosMap);
            
            //Solo calculo el costo del ensamble si los otros productos ya estana ingresados en el kardex
            if(kardexList.size()>0)
            {
                costoIndividualEnsamble = costoIndividualEnsamble.add(new BigDecimal(componenteEmsamble.getCantidad().toString()).multiply(kardexList.get(0).getPrecioUltimo()));
            }
        }
        
        
        ///Actualizar los totales del emsamble
        //kardex.setPrecioPromedio(kardex.getPrecioPromedio().add(costoIndividualEnsamble).divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP));
        //kardex.setPrecioTotal(kardex.getPrecioTotal().add(costoIndividualEnsamble));
        //kardex.setPrecioUltimo(costoIndividualEnsamble);

        //Agregar o descontar el stock cuando se ingrese o salga mercaderia
        //if (accion.equals(ProductoEnsamble.EnsambleAccionEnum.AGREGAR) || accion.equals(ProductoEnsamble.EnsambleAccionEnum.CONSTRUIR_FACTURA) ) {
        //    kardex.setStock(kardex.getStock() + cantidad);
        //} else {
        //    kardex.setStock(kardex.getStock() - cantidad);
        //}

        //Crear el registro del kardex detalle 
        KardexDetalle kardexDetalle = new KardexDetalle();

        //Agregar o descontar el stock cuando se ingrese o salga mercaderia
        
        if (accion.equals(ProductoEnsamble.EnsambleAccionEnum.AGREGAR)) {
            kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.ENSAMBLE_INGRESO.getCodigo());
        }
        else if(accion.equals(ProductoEnsamble.EnsambleAccionEnum.CONSTRUIR_FACTURA)) 
        {
            kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.ENSAMBLE_CONSTRUIR_VENTA.getCodigo());
        
        }else if(accion.equals(ProductoEnsamble.EnsambleAccionEnum.QUITAR))
        {
            kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.ENSAMBLE_EGRESO.getCodigo());
        }

        kardexDetalle.setCantidad(cantidad);
        kardexDetalle.setPrecioTotal(costoIndividualEnsamble.multiply(new BigDecimal(cantidad.toString())));
        kardexDetalle.setPrecioUnitario(costoIndividualEnsamble);
        kardexDetalle.setReferenciaDocumentoId(null);
        kardexDetalle.setFechaCreacion(UtilidadesFecha.getFechaHoyTimeStamp());
        kardexDetalle.setFechaDocumento(UtilidadesFecha.getFechaHoy());
        kardexDetalle.setFechaIngreso(UtilidadesFecha.getFechaHoyTimeStamp());

        kardexEnsamble.addDetalleKardex(kardexDetalle);
        recalcularValoresKardex(kardexEnsamble, kardexDetalle);
        entityManager.persist(kardexDetalle);
        entityManager.merge(kardexEnsamble);
        entityManager.flush();
        return kardexEnsamble;
    }
    
    /**
     * Metodo que permite verificar si tiene el ensamble tiene la cantidad necesario de stock de sus componentes
     * @param productoEnsamble
     * @param bodega
     * @param cantidad
     * @throws java.rmi.RemoteException
     * @throws ServicioCodefacException 
     */
    private void validarEnsambleComponentes( Producto productoEnsamble,Bodega bodega,BigDecimal cantidad) throws java.rmi.RemoteException,ServicioCodefacException
    {        
  
        for(ProductoEnsamble componenteProducto: productoEnsamble.getDetallesEnsamble())
        {
            Producto componente=componenteProducto.getComponenteEnsamble();
            KardexServiceIf servicioKardex=ServiceFactory.getFactory().getKardexServiceIf();
            Kardex kardexResultado= servicioKardex.buscarKardexPorProductoyBodega(bodega,componente);
            
            if(kardexResultado==null)
            {
                throw new ServicioCodefacException("El producto "+productoEnsamble.getNombre()+" no tiene sufiente stock de "+componente.getNombre()+" para construir");
            }
            else
            {
                BigDecimal productosFaltantes=kardexResultado.getStock().subtract(componenteProducto.getCantidad().multiply(cantidad));
                //Integer productosFaltantes=kardexResultado.getStock()-componenteProducto.getCantidad()*cantidad;
                //boolean disponible=
                //TODO:Si no existe la cantidad disponible del producto lanza una exceptcion
                
                //if(productosFaltantes<0)
                if(productosFaltantes.compareTo(BigDecimal.ZERO)<0)                
                {
                    throw new ServicioCodefacException("El producto "+productoEnsamble.getNombre()+" no tiene sufiente stock de "+componente.getNombre()+"para construir, faltante = "+Math.abs(productosFaltantes.floatValue()));
                }
            }
            
        }
    }
    
    public void ingresarInventario(KardexDetalle detalle,Lote lote) throws java.rmi.RemoteException,ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                grabarKardexDetallSinTransaccion(detalle,lote,false);
            }
        });
    }
    
        public void transferirProductoBodegas(Producto producto,Bodega bodegaOrigen,Bodega bodegaDestino, String descripcion,BigDecimal cantidad,BigDecimal precio,Date fechaTransaccion) throws java.rmi.RemoteException,ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                ///========> Validaciones basicas de los datos ingresados <=========================//
                //if(cantidad<=0)
                if(cantidad.compareTo(BigDecimal.ZERO)<=0)
                {
                    throw new ServicioCodefacException("Solo se puede hacer transferencias de cantidades positivas");
                }
                
                if(bodegaOrigen.equals(bodegaDestino))
                {
                    throw new ServicioCodefacException("No se puede hacer una transferencia a la misma bodega");
                }
                
                
                Empresa empresa=producto.getEmpresa();
                //==========> Buscar Primero para ver si existe el kardex del producto y la bodega <==========//
                List<Kardex> kardexResultado=buscarPorProductoYBodega(producto, bodegaOrigen);
                if(kardexResultado==null || kardexResultado.size()==0)
                {
                    throw new ServicioCodefacException("No existe un kardex para el producto en la bodega");
                }
                
                //==============> Verificar si tiene la cantidad disponible para transferir <============//
                Kardex kardexOrigen=kardexResultado.get(0);
                
                if(cantidad.compareTo(kardexOrigen.getStock())>0)
                //if(cantidad>kardexOrigen.getStock())
                {
                    throw new ServicioCodefacException("Cantidad insuficiente para hacer la transferencia");
                }
                
                BigDecimal precioTransferencia=precio;
                //Si no tiene ingresado un precio el seteo el mismo del kardex original
                if(precioTransferencia==null)
                {
                    precioTransferencia=kardexOrigen.getCostoPromedio();
                }
                
                //=============> Obtener el Kardex del producto de destino o crearlo <==================//
                kardexResultado=buscarPorProductoYBodega(producto, bodegaDestino);
                Kardex kardexDestino=null; //Referencia para guardar el kardex de destino
                if(kardexResultado==null || kardexResultado.size()==0)
                {
                    //Si no existe el kardex de destino creo uno similar con los datos del otro Kardex
                    kardexDestino=crearObjeto(bodegaDestino,producto,null);
                    entityManager.persist(kardexDestino);
                    
                }
                else//Si existe el kardex solo lo cargo
                {
                    kardexDestino=kardexResultado.get(0);
                }
                
                
                //===========> Crear los detalles de los kardex para hacer los movimientos <===============//
                KardexDetalle kardexDetalleOrigen=crearKardexDetalleSinPersistencia(
                        kardexOrigen, 
                        TipoDocumentoEnum.TRANSFERENCIA_MERCADERIA_ORIGEN, 
                        precioTransferencia, 
                        cantidad);
                
                KardexDetalle kardexDetalleDestino=crearKardexDetalleSinPersistencia(
                        kardexDestino, 
                        TipoDocumentoEnum.TRANSFERENCIA_MERCADERIA_DESTINO, 
                        precioTransferencia, 
                        cantidad);
                                
                entityManager.persist(kardexDetalleOrigen);
                entityManager.persist(kardexDetalleDestino);
                
                //Ejecuto la actualizacion para saber los id de los otro documento
                entityManager.flush();
                
                //Enlazar las referencias de los kardex
                kardexDetalleOrigen.setReferenciaDocumentoId(kardexDetalleDestino.getId());
                kardexDetalleDestino.setReferenciaDocumentoId(kardexDetalleOrigen.getId());
                
                //actualizar los nuevos valores de la referencias de las transferencias
                entityManager.merge(kardexDetalleOrigen);
                entityManager.merge(kardexDetalleDestino);
                
                kardexOrigen.addDetalleKardex(kardexDetalleOrigen);
                kardexDestino.addDetalleKardex(kardexDetalleDestino);                
                
                
                //===========> Recalcular los totales de los nuevos kardex <=============//
                recalcularValoresKardex(kardexOrigen, kardexDetalleOrigen);
                recalcularValoresKardex(kardexDestino, kardexDetalleDestino);
                entityManager.merge(kardexOrigen);
                entityManager.merge(kardexDestino);
                

                
                //===========> Guardar las referencias de los otros kardex detalle para algun reporte <=========//
                kardexDetalleDestino.setReferenciaDocumentoId(kardexDetalleOrigen.getId());
                kardexDetalleOrigen.setReferenciaDocumentoId(kardexDetalleDestino.getId());
                entityManager.merge(kardexDetalleDestino);
                entityManager.merge(kardexDetalleOrigen);
                
                
            }
        });
    }
    
    /**
     * Metodo que mer permite calcular el stock , los precios promedios y demas valores 
     * @param kardex
     * @param kardexDetalle 
     */
    public void recalcularValoresKardex(Kardex kardex,KardexDetalle kardexDetalle) throws java.rmi.RemoteException,ServicioCodefacException
    {

        BigDecimal costoPonderado=kardex.getCostoPromedio(); 
        
        //Si el movimiento del kardex detalle esta clasificado como que afecta a el inventario entonces hago el resto de calculos
        if(kardexDetalle.getCodigoTipoDocumentoEnum().getAfectaCostoInventario())
        {
            //ALMACENA EL ULTIMO VALOR INGRESADO SIEMPRE QUE SEA UNA COMPRA
            kardex.setPrecioUltimo(kardexDetalle.getPrecioUnitario());
            //Calcular el precio promedio con respecto al nuevo valor
            if(kardex.getCostoPromedio().compareTo(BigDecimal.ZERO)>0)
            {
                costoPonderado=calcularPrecioPonderado(kardex,kardexDetalle);
                kardex.setCostoPromedio(costoPonderado);            
            }
            else
            {
                kardex.setCostoPromedio(kardexDetalle.getPrecioUnitario());
            }
            
            
        }
         
        BigDecimal signo=new BigDecimal(kardexDetalle.getCodigoTipoDocumentoEnum().getSignoInventarioNumero());
        //Integer stockFinal=kardex.getStock()+signo.intValue()*kardexDetalle.getCantidad().intValue();
        BigDecimal cantidadMovimiento=signo.multiply(kardexDetalle.getCantidad());
        BigDecimal stockFinal=kardex.getStock().add(cantidadMovimiento);
        kardex.setStock(stockFinal);
        
        //Actualizar los totales de los lotes
        /*Lote lote=kardexDetalle.getLote();
        //Solo sumar cantidades positivas
        if(signo.compareTo(BigDecimal.ZERO)>0)
        {
            lote.setTotal(lote.getTotal().add(cantidadMovimiento));
        }
        
        lote.setStock(lote.getStock().add(cantidadMovimiento));
        ServiceFactory.getFactory().getLoteSeviceIf().editarSinTransaccion(lote);*/
        
        //CALCULAR EL PRECIO CON EL STOCK FINAL
        kardex.calcularPrecioTotal();
        
        
    }
    
    /**
     * Formula general para calcular el precio ponderado del producto
     * @param kardex
     * @param kardexDetalle
     * @return 
     */
    private BigDecimal calcularPrecioPonderado(Kardex kardex,KardexDetalle kardexDetalle)
    {
        /**
         * =================> VALIDACIONES PARA EVITAR ERROES <===============
         */
        //S no existe stock no se hace el calculo del precio promedio porque eso solo va a generar errores
        
        if(kardex.getStock().compareTo(BigDecimal.ZERO)<=0)
         //if(kardex.getStock()<=0)
         {
             return kardex.getCostoPromedio(); 
         }
        
        /**
         * =================> PROCESOS DE CALCULO DEL STOCK <=================
         * Para calculoar el stock se utiliza la siguiente formular
         * costo=(stock*costoActual)*(cantidad*precioUnit)/(stock+cantidad)
         */
        BigDecimal stock=kardex.getStock();
        BigDecimal costoPonderado=kardex.getCostoPromedio();
        
        BigDecimal cantidadUnitaria=kardexDetalle.getCantidad();
        BigDecimal precioUnitario=kardexDetalle.getPrecioUnitario();
        
        //Primero calculo el numerador 
        BigDecimal resultadoCosto= costoPonderado.multiply(stock).add(precioUnitario.multiply(cantidadUnitaria));
        BigDecimal cantidadTotal=stock.add(cantidadUnitaria);
        //Calculo el denominador que es dividir para el total de productos
        resultadoCosto=resultadoCosto.divide(cantidadTotal,4,RoundingMode.HALF_UP); //Por defecto dejo 4 decimales porque puede ser que para productos de centavos el calculo sea muy impresiso
        
        return resultadoCosto;
    }
    
    public  KardexDetalle crearKardexDetalleSinPersistencia(Kardex kardex,TipoDocumentoEnum tipoDocumentoEnum,BigDecimal precioUnitario,BigDecimal cantidad) throws java.rmi.RemoteException,ServicioCodefacException
    {
        KardexDetalle movimientoOrigen = new KardexDetalle();
        BigDecimal total=precioUnitario.multiply(new BigDecimal(cantidad.toString()));
        movimientoOrigen.setCantidad(cantidad);
        movimientoOrigen.setCodigoTipoDocumentoEnum(tipoDocumentoEnum);
        movimientoOrigen.setFechaCreacion(UtilidadesFecha.getFechaHoyTimeStamp());
        movimientoOrigen.setFechaIngreso(UtilidadesFecha.getFechaHoyTimeStamp());
        movimientoOrigen.setFechaDocumento(UtilidadesFecha.getFechaHoy());
        
        movimientoOrigen.setKardex(kardex);
        //movimientoOrigen.setNombreLegal("");
        movimientoOrigen.setPrecioTotal(total);
        movimientoOrigen.setPrecioUnitario(precioUnitario);
        //movimientoOrigen.setPuntoEmision("");
        movimientoOrigen.setReferenciaDocumentoId(null);
        return movimientoOrigen;
    }
    
    public void anularInventario(Kardex kardex) throws java.rmi.RemoteException,ServicioCodefacException
    {
        /**
         * Validaciones del Stock
         */
        //if(kardex.getStock()==0)
        if(kardex.getStock().compareTo(BigDecimal.ZERO)==0)
        {
            throw new ServicioCodefacException("No se puede eliminar porque el Stock esta en 0");
        }
                
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                BigDecimal stockAnular= kardex.getStock().multiply(new BigDecimal("-1"));
                KardexDetalle kardexDetalle=new KardexDetalle();                
                kardexDetalle.setPrecioUnitario(kardex.getCostoPromedio());
                
                if(stockAnular.compareTo(BigDecimal.ZERO)>0)
                //if(stockAnular>0)
                {
                    kardexDetalle.setCantidad(stockAnular);
                    kardexDetalle.setCodigoTipoDocumentoEnum(TipoDocumentoEnum.ANULAR_MERCADERIA_POSITIVO);
                }
                else
                {
                    kardexDetalle.setCantidad(stockAnular.multiply(new BigDecimal("-1")));
                    kardexDetalle.setCodigoTipoDocumentoEnum(TipoDocumentoEnum.ANULAR_MERCADERIA_NEGATIVO);
                }
                kardexDetalle.recalcularTotalSinGarantia();
                kardexDetalle.setFechaIngreso(UtilidadesFecha.getFechaHoyTimeStamp());
                kardexDetalle.setFechaDocumento(UtilidadesFecha.getFechaHoy());
                kardexDetalle.setKardex(kardex);
                
                grabarKardexDetallSinTransaccion(kardexDetalle,null,false);
            }
        });
        
        
    }
    
    public void ingresarInventario(List<KardexDetalle> detalles) throws java.rmi.RemoteException,ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                for (KardexDetalle detalle : detalles) {
                    grabarKardexDetallSinTransaccion(detalle,null,false);
                    
                }
                
            }
        });
    }
    
    
    public void grabarKardexDetallSinTransaccion(KardexDetalle detalle,Lote lote,Boolean forzarGrabarCantidadCero) throws RemoteException, ServicioCodefacException
    {
        /**
         * ==============================================================
         *            VALIDACIONES PARA LOS DETALLES DE KARDEX
         * ==============================================================
         */
        validarDetallesKardex(detalle,forzarGrabarCantidadCero);
        
        //Buscar si ya existe el kardex o si no existe los creamos
        //Map<String, Object> map = new HashMap<String, Object>();
        //map.put("bodega", detalle.getKardex().getBodega());
        //map.put("producto", detalle.getKardex().getProducto());
        //map.put("lote", detalle.getKardex().getProducto());
        Kardex kardex =buscarKardexPorProductoyBodegayLote(detalle.getKardex().getBodega(), detalle.getKardex().getProducto(), lote);

        //List<Kardex> kardexList = getFacade().findByMap(map);

        //TODO:Ver si se puede crear una sola funcion estandar de Kardex
        //Kardex kardex = null;
        if (kardex == null ) {
            //Si no existe completo los datos para crear el kardex
            kardex = detalle.getKardex();
            //kardex.setBodega(bodega);
            kardex.setFechaCreacion(UtilidadesFecha.getFechaHoy());
            kardex.setFechaModificacion(UtilidadesFecha.getFechaHoy());
            kardex.setCostoPromedio((kardex.getCostoPromedio()!=null)?kardex.getCostoPromedio():BigDecimal.ZERO);
            kardex.setPrecioTotal(BigDecimal.ZERO);
            kardex.setPrecioUltimo(BigDecimal.ZERO);
            //kardex.setProducto(value.getProductoProveedor().getProducto());
            kardex.setStock(BigDecimal.ZERO);
            kardex.setReserva(0);
            kardex.setEstadoEnum(GeneralEnumEstado.ACTIVO);
            kardex.setLote(lote);
            em.persist(kardex); //grabando la persistencia
        } 
        //else {
            //Si existe el kardex solo busco el primer registro
        //    kardex = kardexList.get(0);
        //}

        //Agregar la fecha de creacion del sistema
        detalle.setFechaCreacion(UtilidadesFecha.getFechaHoyTimeStamp());
        detalle.setKardex(kardex);
        kardex.addDetalleKardex(detalle);
        em.persist(detalle);

        recalcularValoresKardex(kardex, detalle); //Actualiza los valores desde un mismo lugar
        kardex = em.merge(kardex);

        //Actualizar la compra de referencia para saber que ya fue ingresada
        switch (detalle.getCodigoTipoDocumentoEnum()) {
            case COMPRA_INVENTARIO:
                //Actualizar referencia de la compra para que sepa que ya fue ingresado la mercaderia
                //TODO:Mejorar esta parte porque esta grabando muchas veces lo mismo cuando hay varios detalles apuntando a la misma compra
                CompraService compraService = new CompraService();
                Compra compra = compraService.buscarPorId(detalle.getReferenciaDocumentoId());
                compra.setInventarioIngreso(EnumSiNo.SI.getLetra());
                em.merge(compra);
                break;

        }
    }
    
    private void validarDetallesKardex(KardexDetalle detalle,Boolean forzarCantidadCero) throws java.rmi.RemoteException,ServicioCodefacException
    {
         /**
         * ==============================================================
         *            VALIDACIONES PARA LOS DETALLES DE KARDEX
         * ==============================================================
         */
        if(detalle.getKardex()==null)
        {
            throw new ServicioCodefacException("No se puede grabar sin referencia de Kardex vacio");
        }
        
        if(!forzarCantidadCero)
        {
            if(detalle.getCantidad()==null || detalle.getCantidad().compareTo(BigDecimal.ZERO)==0)
            //if(detalle.getCantidad()==null || detalle.getCantidad()==0)
            {
                throw new ServicioCodefacException("No se puede ingresar cantidad negativas de stock o que sean 0");
            }
        }
        
        if(detalle.getFechaDocumento()==null)
        {
            throw new ServicioCodefacException("No se puede grabar sin fecha del documento");
        }
        
        if(detalle.getFechaIngreso()==null)
        {
            throw new ServicioCodefacException("No se puede grabar sin fecha de ingreso");
        }
        
        if(detalle.getKardex().getProducto().getGarantiaEnum().equals(EnumSiNo.SI))
        {
            if(detalle.getDetallesEspecificos()==null || detalle.getDetallesEspecificos().size()<=0)
            {
                throw new ServicioCodefacException("Los productos con inventario tiene que tener productos unicos");
            }
            
            for (KardexItemEspecifico detallesEspecifico : detalle.getDetallesEspecificos()) 
            {
                //Verifico solo los productos especificos que aun no han sido grabados con id null
                if(detallesEspecifico.getId()==null)
                {
                    if(detallesEspecifico.getCodigoEspecifico()==null || detallesEspecifico.getCodigoEspecifico().trim().isEmpty())
                    {
                        throw new ServicioCodefacException("No se puede grabar un item unico con CÓDIGO VACIO");
                    }
                }
            }
        }
        
        
    }
    
    /**
     * TODO: Ver si no voy a usar este metodo borrar
     * Metodo para el ingreso de nuevos productos del kardex
     * @param detalles 
     * @deprecated 
     */
    public void ingresarInventario(Map<KardexDetalle,CompraDetalle> detalles,Bodega bodega) throws java.rmi.RemoteException,ServicioCodefacException
    {
        EntityTransaction transaction=em.getTransaction();
        try
        {            
            transaction.begin();
            Compra compra=null; //referencia para almacenar la compra ingresada a inventario

            for (Map.Entry<KardexDetalle, CompraDetalle> entry : detalles.entrySet()) {

                KardexDetalle kardexDetalle = entry.getKey();
                CompraDetalle value = entry.getValue();
                compra=value.getCompra();
                Producto producto=value.getProductoProveedor().getProducto();

                //Verificar si existe el karde o lo crea;
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("bodega", bodega);
                map.put("producto",producto);
                List<Kardex> kardexList=obtenerPorMap(map);

                Kardex kardex=null;
                if(kardexList==null | kardexList.size()==0)
                {
                    //Creando el kardex cuando no existe en la base de datos
                    kardex=new Kardex();
                    crearObjeto(bodega, value.getProductoProveedor().getProducto(),null);/*
                    kardex.setBodega(bodega);
                    kardex.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                    kardex.setFechaModificacion(UtilidadesFecha.getFechaHoy());
                    kardex.setPrecioPromedio(BigDecimal.ZERO);
                    kardex.setPrecioTotal(BigDecimal.ZERO);
                    kardex.setPrecioUltimo(BigDecimal.ZERO);
                    kardex.setProducto(value.getProductoProveedor().getProducto());
                    kardex.setStock(0);
                    kardex.setReserva(0);*/
                    em.persist(kardex); //grabando la persistencia
                }            
                else
                {
                    //Si existe el kardex solo creo
                    kardex=kardexList.get(0);
                }

                /**
                 * Grabar los detalles de los kardes y actualizar los valores en el kardex
                 */
                kardexDetalle.setKardex(kardex);
                kardex.addDetalleKardex(kardexDetalle);
                //em.persist(kardexDetalle); //grabando el kardex detalle

                //Esto va a depender del tipo de flujo del inventario es decir para saber si la suma o resta pero por defecto
                // el metodo es solo de ingreso asi que no considero el otro caso
                //kardex.setStock(kardex.getStock()+kardexDetalle.getCantidad());
                //kardex.setPrecioPromedio(kardex.getPrecioPromedio().add(kardexDetalle.getPrecioUnitario()).divide(new BigDecimal("2"),2,RoundingMode.HALF_UP));
                //kardex.setPrecioTotal(kardex.getPrecioTotal().add(kardexDetalle.getPrecioTotal()));
                //kardex.setPrecioUltimo(kardexDetalle.getPrecioUnitario());
                recalcularValoresKardex(kardex,kardexDetalle);
                kardex=em.merge(kardex);
            }
            
            if(compra!=null)
            {
                compra.setInventarioIngreso(EnumSiNo.SI.getLetra());
                em.merge(compra);
            }

            transaction.commit(); //si todo sale bien ejecuto en la base de datos
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            transaction.rollback();
            throw  new ServicioCodefacException("Error al grabar el inventario");            
        } 
    
    }
    
    public List<KardexDetalle> obtenerConsultaPorFecha(Date fechaInicial , Date fechaFinal,Producto producto,Bodega bodega,Integer cantidadMovimientos) throws java.rmi.RemoteException
    {
        List<KardexDetalle> datosConsulta=getFacade().obtenerConsultaPorFechaFacade(fechaInicial, fechaFinal, producto, bodega,cantidadMovimientos);
        //Invertir la lista porque los resultados estan invertidos
        //Collections.reverse(datosConsulta);
        
        BigDecimal cantidadSaldo = BigDecimal.ZERO;
        BigDecimal precioUnitarioSaldo = BigDecimal.ZERO;
        BigDecimal precioTotalSaldo = BigDecimal.ZERO;
        
        List<KardexDetalle> datosConsultaTemp=new ArrayList<KardexDetalle>();
        if(cantidadMovimientos!=null && (datosConsulta.size()-cantidadMovimientos)>0 )
        {
            int numeroCorte=datosConsulta.size()-cantidadMovimientos;
            List<KardexDetalle> valoresAnteriores=datosConsulta.subList(0,numeroCorte);
            List<KardexDetalle> datosConsultaTemp2=datosConsulta.subList(numeroCorte,datosConsulta.size());
            datosConsultaTemp=new ArrayList<KardexDetalle>(valoresAnteriores);
            
            //Calcular los saldos anteriores
            for (KardexDetalle valorAnterior : valoresAnteriores) {
                
                if(valorAnterior.getCodigoTipoDocumentoEnum().getSignoInventario().equals(TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO))
                {
                    cantidadSaldo=cantidadSaldo.add(valorAnterior.getCantidad());
                    precioTotalSaldo=precioTotalSaldo.add(valorAnterior.getPrecioTotal());
                }
                else
                {
                    cantidadSaldo=cantidadSaldo.add(valorAnterior.getCantidad());
                    precioTotalSaldo=precioTotalSaldo.subtract(valorAnterior.getPrecioTotal());
                }                
                
            }
            
        }
        
        //Si existe fecha de corta obtenego los saldos anteriores
        if(fechaInicial!=null)
        {
            List<Object[]> saldosAnteriores=getFacade().obtenerConsultaSaldoAnterior(fechaInicial, producto, bodega);
            
            for (Object[] saldosAnterior : saldosAnteriores) {
                String codigoTipoDocumento=(String) saldosAnterior[0];
                //Integer cantidad=((Long) saldosAnterior[1]).intValue();
                BigDecimal cantidad=new BigDecimal(saldosAnterior[1]+"");
                BigDecimal precioUnitario=(BigDecimal) saldosAnterior[2];
                BigDecimal precioTotal=(BigDecimal) saldosAnterior[3];
                
                TipoDocumentoEnum tipoDocumento=TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(codigoTipoDocumento);
                
                if(tipoDocumento.getSignoInventario().equals(TipoDocumentoEnum.AFECTA_INVENTARIO_POSITIVO))
                {
                    cantidadSaldo=cantidadSaldo.add(cantidad);
                    precioTotalSaldo=precioTotalSaldo.add(precioTotal);                    
                }
                else
                {
                    cantidadSaldo=cantidadSaldo.subtract(cantidad);
                    precioTotalSaldo=precioTotalSaldo.subtract(precioTotal);
                }
                
                precioUnitarioSaldo=precioTotalSaldo.divide(cantidadSaldo,2,BigDecimal.ROUND_HALF_UP); //Todo: Ver si esta varible se elimina porque el precio promedio puedo calcular del total       
            }                        
        }
        
        if(cantidadSaldo.compareTo(BigDecimal.ZERO)>0)
        //if(cantidadSaldo>0)
        {
            ///////////////======================> CONSTRUIR KARDEX DETALLE ADICIONAL DE LOS SALDOS <==============================//////////////////
            KardexDetalle kardexDetalle = new KardexDetalle();
            kardexDetalle.setCantidad(cantidadSaldo);
            kardexDetalle.setPrecioUnitario(precioUnitarioSaldo);
            kardexDetalle.setPrecioTotal(precioTotalSaldo);
            kardexDetalle.setFechaCreacion(UtilidadesFecha.castDateSqlToTimeStampSql(fechaInicial));
            kardexDetalle.setFechaIngreso(UtilidadesFecha.castDateSqlToTimeStampSql(fechaInicial));
            kardexDetalle.setCodigoTipoDocumento(TipoDocumentoEnum.SALDO_ANTERIOR.getCodigo());
            //kardexDetalle.set            
            datosConsulta.add(0, kardexDetalle);
        }
        
        for (int j = 0; j < datosConsultaTemp.size(); j++) 
        {
            KardexDetalle kardexDetalleTemp = datosConsultaTemp.get(j);
            if (datosConsulta.contains(kardexDetalleTemp)) 
            {
                datosConsulta.remove(kardexDetalleTemp);
            }
        }
        
        /*
        for(int i=0;i<datosConsulta.size();i++){
        //for (KardexDetalle kardexDetalle : datosConsulta) {
            KardexDetalle kardexDetalle=datosConsulta.get(i);
            
            for(int j=0;j<datosConsultaTemp.size();j++)
            {
                KardexDetalle kardexDetalleTemp=datosConsultaTemp.get(j);
                if(kardexDetalleTemp.getId().equals(kardexDetalle.getId()))
                {
                    datosConsulta.remove(kardexDetalle);
                }
            }
            //if(datosConsultaTemp.contains(kardexDetalle))
            //{
            //    datosConsulta.remove(kardexDetalle);
            //}
        }
        */
        
        return datosConsulta;
    }
    
    public Integer consultarCantidadStockMinimo(Empresa empresa) throws java.rmi.RemoteException
    {
        return getFacade().consultarStockMinimoCantidadFacade(empresa).intValue();
    }
    
    public List<Object[]> consultarStockMinimo(Bodega bodega,CategoriaProducto categoria,Empresa empresa) throws java.rmi.RemoteException
    {
        return getFacade().consultarStockMinimoFacade(bodega,categoria);
    }
    
    public List<Object[]> consultarStock(Bodega bodega,CategoriaProducto categoria,Empresa empresa) throws java.rmi.RemoteException
    {
        return getFacade().consultarStockFacade(bodega,categoria,empresa);
    }

    public List<Kardex> buscarPorProducto(Producto producto) throws java.rmi.RemoteException,ServicioCodefacException
    {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("producto", producto);
        //KardexService kardexService = new KardexService();
        return getFacade().findByMap(mapParametros);
    }
    
    public List<Kardex> buscarPorProducto(Producto producto,GeneralEnumEstado estadoEnum) throws java.rmi.RemoteException,ServicioCodefacException
    {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("producto", producto);
        mapParametros.put("estado", estadoEnum.getEstado());
        //KardexService kardexService = new KardexService();
        List<Kardex> listaOriginal=getFacade().findByMap(mapParametros);
        List<Kardex> listaFiltrada=new ArrayList<Kardex>();
        for (Kardex kardex : listaOriginal) {
            
            if(kardex.getBodega()==null)
            {
                Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, "Revisar kadex con bodega NULL ?? "+kardex.getId()+" - "+kardex.getProducto());
                continue;
            }
            
            listaFiltrada.add(kardex);
        }
                
                
        return listaFiltrada;
    }
    
    public List<Kardex> buscarPorProductoYBodega(Producto producto,Bodega bodega) throws java.rmi.RemoteException,ServicioCodefacException
    {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("producto", producto);
        mapParametros.put("bodega",bodega);
        
        //KardexService kardexService = new KardexService();
        return getFacade().findByMap(mapParametros);
    }
    
    /**
     * TODO: Este metodo me parece que esta de remplazar al momento de hacer los ingresos desde la pantalla de inventario porque esta duplicado
     * @param bodega
     * @param producto
     * @return 
     */
    public Kardex crearObjeto(Bodega bodega,Producto producto,Lote lote) throws java.rmi.RemoteException,ServicioCodefacException
    {
        Kardex kardex=new Kardex();
        kardex.setLote(lote);
        kardex.setBodega(bodega);
        kardex.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        kardex.setFechaModificacion(UtilidadesFecha.getFechaHoy());
        kardex.setCostoPromedio(BigDecimal.ZERO);
        kardex.setPrecioTotal(BigDecimal.ZERO);
        kardex.setPrecioUltimo(BigDecimal.ZERO);
        kardex.setProducto(producto);
        kardex.setReserva(0);
        kardex.setStock(BigDecimal.ZERO);
        kardex.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        return kardex;
        
    }
    
    public boolean obtenerSiNoExisteStockProducto(Bodega bodega, Producto producto, BigDecimal cantidad)
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("bodega",bodega);
        mapParametros.put("producto",producto);   
        List<Kardex> listaKardex=getFacade().findByMap(mapParametros);
        
        if(listaKardex!=null && listaKardex.size()>0)
        {
            Kardex kardex = listaKardex.get(0);
            System.out.println(kardex.getStock());
            System.out.println(cantidad);
            if(kardex.getStock().compareTo(cantidad)>=0)
            {
            //if(kardex.getStock()>= cantidad){
                return true;
            }
            else
            {
                return false;
            }
        }
        return false;    
    }

    @Override
    public List<Kardex> buscarPorBodega(Bodega bodega) throws RemoteException, ServicioCodefacException {
        return (List<Kardex>)ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                //Kardex k;
                //        k.get
                Map<String,Object> mapParametros=new HashMap<String,Object>();
                mapParametros.put("bodega",bodega);
                return getFacade().findByMap(mapParametros);
            }
        });
    }
    
    public Kardex construirKardexVacioSinPersistencia() throws java.rmi.RemoteException,ServicioCodefacException
    {
        Kardex kardexNuevo=new Kardex();
        kardexNuevo.setStock(BigDecimal.ZERO);
        kardexNuevo.setPrecioTotal(BigDecimal.ZERO);
        kardexNuevo.setCostoPromedio(BigDecimal.ZERO);
        kardexNuevo.setPrecioUltimo(BigDecimal.ZERO);
        //kardexNuevo.se
        return kardexNuevo;
    }
    
    /**
     * Otimizar metodo para hacer una consulta filtrando las bodegas de destino 
     * @param fechaInicial
     * @param fechaFinal
     * @param bodegaDestino
     * @return
     * @throws java.rmi.RemoteException
     * @throws ServicioCodefacException 
     */
    public List<TransferenciaBodegaRespuesta> consultarMovimientosTransferencia(java.util.Date fechaInicial, java.util.Date fechaFinal,Bodega bodegaDestino) throws java.rmi.RemoteException,ServicioCodefacException
    {
        List<KardexDetalle> kardexDetalleList=getFacade().consultarMovimientosTransferenciaFacade(fechaInicial, fechaFinal);
        
        List<TransferenciaBodegaRespuesta> resultado=new ArrayList<TransferenciaBodegaRespuesta>();
        
        
        for (KardexDetalle kardexDetalle : kardexDetalleList) 
        {
            KardexDetalle kardexDetalleDestino=null;
            if(kardexDetalle.getReferenciaDocumentoId()!=null)
            {
                KardexDetalleService kardexDetalleService=new KardexDetalleService();
                kardexDetalleDestino=kardexDetalleService.buscarPorId(kardexDetalle.getReferenciaDocumentoId());
            }
            
            //Si existe un filtro de bodega destino primero la comparo o si no lo agrego directo
            if(bodegaDestino!=null)
            {
                if(kardexDetalleDestino.getKardex()!=null && kardexDetalleDestino.getKardex().getBodega().equals(bodegaDestino))
                {
                    resultado.add(new TransferenciaBodegaRespuesta(kardexDetalle, kardexDetalleDestino));                
                }
            }
            else
            {
                resultado.add(new TransferenciaBodegaRespuesta(kardexDetalle, kardexDetalleDestino));                
            }
        }
        
        //KardexDetalle kd;
        //kd.setCodigoTipoDocumentoEnum(TipoDocumentoEnum.transfe);
        return resultado;
    }   
    
     /**     *
     * TODO: Unificar este metodo con la de factura que existe un metodo similar
     * @param detalle 
     */
    public KardexDetalle afectarInventario(Bodega bodega,BigDecimal cantidad,BigDecimal precioUnitario,BigDecimal total,Long referenciaKardexId,Long referenciaProductoId,TipoDocumentoEnum tipoDocumento,String puntoEmision,String puntoEstablecimiento,Integer secuencial,Date fechaDocumento) throws RemoteException,ServicioCodefacException
    {
        try {
            Producto producto=ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(referenciaProductoId);
            
            //Map<String,Object> mapParametros=new HashMap<String,Object>();
            //mapParametros.put("producto", producto);
            KardexService kardexService=new KardexService();
            //Kardex kardexProducto=kardexService.buscarKardexPorProductoyBodega(bodega, producto);
            Kardex kardexProducto=ServiceFactory.getFactory().getKardexServiceIf().consultarOCrearStockSinPersistencia(producto, bodega,null);
            //List<Kardex> kardexs= kardexService.getFacade().findByMap(mapParametros);
            //TODO: Definir especificamente cual es la bodega principal
            //if(kardexProducto!=null && kardexProducto.size()>0)
            //{
                //TODO: Analizar caso cuando se resta un producto especifico
                Kardex kardex= kardexProducto;
                KardexDetalle kardexDetalle=new KardexDetalle();
                kardexDetalle.setFechaCreacion(UtilidadesFecha.getFechaHoyTimeStamp());
                kardexDetalle.setFechaIngreso(UtilidadesFecha.getFechaHoyTimeStamp());
                kardexDetalle.setCantidad(cantidad);
                kardexDetalle.setCodigoTipoDocumento(tipoDocumento.getCodigo());
                kardexDetalle.setPrecioTotal(total);
                kardexDetalle.setPrecioUnitario(precioUnitario);
                kardexDetalle.setReferenciaDocumentoId(referenciaKardexId);
                kardexDetalle.setPuntoEmision(puntoEmision);
                kardexDetalle.setPuntoEstablecimiento(puntoEstablecimiento);
                kardexDetalle.setSecuencial(secuencial);
                kardexDetalle.setFechaDocumento(fechaDocumento);
                
                kardex.addDetalleKardex(kardexDetalle);
                
                //Actualizar los valores del kardex
                kardex.setStock(kardex.getStock().add(kardexDetalle.getCantidad()));
                //kardex.setPrecioPromedio(kardex.getPrecioPromedio().add(kardexDetalle.getPrecioUnitario()).divide(new BigDecimal("2"), 2, RoundingMode.HALF_UP));
                kardex.setPrecioTotal(kardex.getPrecioTotal().add(kardexDetalle.getPrecioTotal()));
                //kardex.setPrecioUltimo(kardexDetalle.getPrecioUnitario());
                
                entityManager.merge(kardex);
                return kardexDetalle;
            //}
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(NotaCreditoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public List<RotacionInventarioRespuesta> consultarRotacionInventario(Date fechaInicio,Date fechaFinal,Sucursal sucursal) throws RemoteException,ServicioCodefacException
    {
        List<RotacionInventarioRespuesta> respuestaList=new ArrayList<RotacionInventarioRespuesta>();
        List<Producto> productosInventario=getFacade().obtenerListaProductosInventario(sucursal.getEmpresa());
        
        Map<Producto,BigDecimal> mapCantidadVentas=getFacade().obtenerCantidadVentas(fechaInicio, fechaFinal, sucursal);
        Map<Producto,StockPromedioYCantidadRespuesta> mapStockPromedio=getFacade().obtenerStockComprasPromedioYCantidad(fechaInicio, fechaFinal, sucursal);
        
        for (Producto producto : productosInventario) 
        {
            BigDecimal cantidadVentas=mapCantidadVentas.get(producto);
            StockPromedioYCantidadRespuesta stockPromedio=mapStockPromedio.get(producto);
            
            
            
            
            RotacionInventarioRespuesta rotacionInventario=new RotacionInventarioRespuesta();
            rotacionInventario.producto=producto;
            rotacionInventario.cantidadVentas=cantidadVentas;
            
            rotacionInventario.stockPromedio=stockPromedio.promedio;
            rotacionInventario.cantidadStockCompras=stockPromedio.cantidad;
            
                    
            respuestaList.add(rotacionInventario);
        }
        
        return respuestaList;
        
    }

            
}

