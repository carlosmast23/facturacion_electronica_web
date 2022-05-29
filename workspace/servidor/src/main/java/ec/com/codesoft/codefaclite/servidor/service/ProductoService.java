/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoProductoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.ProductoFacade;
import ec.com.codesoft.codefaclite.servidor.facade.UtilidadFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesExpresionesRegulares;
import ec.com.codesoft.codefaclite.utilidades.validadores.ExpresionRegular;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author PC
 */
public class ProductoService extends ServiceAbstract<Producto,ProductoFacade> implements ProductoServiceIf
{
    private ProductoFacade productoFacade;
    
    public ProductoService() throws RemoteException
    {
        super(ProductoFacade.class);
        this.productoFacade = new ProductoFacade();
    }
    
    private void generarCodigoProducto(Producto producto) throws RemoteException,ServicioCodefacException
    {
        UtilidadesService utilidadService=new UtilidadesService();
        Integer ultimoId=utilidadService.obtenerCodigoMaximoPorId(Producto.NOMBRE_TABLA,Producto.NOMBRE_CAMPO_ID);
        producto.setCodigoPersonalizado(ultimoId+"");
    }
      
    public Producto grabar(Producto p,Boolean generarCodigo) throws RemoteException, ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                grabarSinTransaccion(p,generarCodigo,true);
            }
        });        
        
        //TODO: Metodo temporal porque cuando se ejecuta sin esta parte causa conflicto al utilizar el producto en el resto de pantallas
        //Al utilizar por segunda vez el mismo objeto por algun motivo genera un error y luego de eso ya funciona correctamente
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException, RemoteException {

                    Producto pTmp = getFacade().find(p.getIdProducto());
                    entityManager.merge(pTmp);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return p;
    }
    
    //TODO: revisar una mejor solucion con lo de generar kardex
    public void grabarSinTransaccion(Producto p,Boolean generarCodigo,Boolean generarKardex) throws java.rmi.RemoteException,ServicioCodefacException{
        
        if(generarCodigo)
        {
            generarCodigoProducto(p);
        }
        
        p.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        validarGrabarProducto(p,CrudEnum.CREAR);
        
        CatalogoProducto catalogoProducto = p.getCatalogoProducto();
        p.setCatalogoProducto(null);
        entityManager.flush();
        entityManager.persist(p);
        entityManager.flush();
        
        //Si el catalogo producto no esta creado primero crea la entidad        
        if (catalogoProducto.getId() == null) {
            CategoriaProducto categoriaProducto = catalogoProducto.getCategoriaProducto();
            if(categoriaProducto!=null)
            {
                if (categoriaProducto.getIdCatProducto() == null)//Si no existe la categoria tambien se los crea
                {
                    entityManager.persist(categoriaProducto);
                    entityManager.flush();
                }
            }

            entityManager.persist(catalogoProducto);
            entityManager.flush();
            
            p.setCatalogoProducto(catalogoProducto);
        }

        //Si no son ensables remover datos para no tener incoherencias
        if (!TipoProductoEnum.EMSAMBLE.getLetra().equals(p.getTipoProductoCodigo())) {
            if (p.getDetallesEnsamble() != null) {
                p.getDetallesEnsamble().clear();
            }
        }
        
        entityManager.merge(p);
        entityManager.flush();
        
        //Crear los KARDEX CUANDO NO EXISTA
        if(generarKardex)
        {
            if(p.getManejarInventarioEnum().equals(EnumSiNo.SI))
            {
                ServiceFactory.getFactory().getKardexServiceIf().crearKardexSiNoExisteSinTransaccion(p);
            }
        }
        
    }
    
    private void validarGrabarProducto(Producto p,CrudEnum estadoEnum) throws java.rmi.RemoteException,ServicioCodefacException    
    {
        if(p.getCodigoPersonalizado()==null || p.getCodigoPersonalizado().trim().isEmpty())
        {
            throw new ServicioCodefacException("El Código principal del producto no puede estar vacio ");
        }
        
        if(p.getCodigoPersonalizado().length()>Producto.TAMANIO_MAX_CODIGO)
        {
            throw new ServicioCodefacException("El código debe tener un tamaño maximo de "+Producto.TAMANIO_MAX_CODIGO+" caracteres ");
        }
        
        //TODO: Analizar porque el Sri supuestamente si deja mandar productos con valor 0 , por el momento solo pongo los menores que 0
        if(p.getValorUnitario().compareTo(BigDecimal.ZERO)<0)
        {
            throw new ServicioCodefacException("El valor unitario del producto no puede ser menor que cero");
        }
                
        if(p.getCatalogoProducto()==null)
        {
            throw new ServicioCodefacException("No se puede grabar el producto sin un catalago de producto");
        }        
        
        if(p.getCatalogoProducto().getIva()==null)
        {
            throw new ServicioCodefacException("No se puede grabar el producto sin especificar un porcentaje de Iva");
        }
        
        //TODO: Esta validacion por el momento queda comentada por que siempre se esta grabando con ese dato en No
        //Validar que cuando requiera imprimir el código tenga un formato correcto
        //if(p.getGenerarCodigoBarrasEnum().equals(EnumSiNo.SI))
        //{
            //TODO: Validar para caracteres generales 
            //Link: https://es.wikipedia.org/wiki/Code_39
            //if(!UtilidadesExpresionesRegulares.validar(p.getCodigoPersonalizado(),ExpresionRegular.soloNumeros))
            //{
            //    throw new ServicioCodefacException("El Código solo puede ser numérico cuando se selecciona la opción de generar código de barras");
            //}
        //}
        
        
        /**
         * TODO: Ver si este tipo de validacion se puede convertir en algo estandar para todos lo servicios
         * =================================================================
         *          VALIDAR QUE NO EXITAN PRODUCTOS DUPLICADOS
         * =================================================================
         */
        if(estadoEnum.equals(CrudEnum.EDITAR))
        {
            Producto productoTmp=getFacade().find(p.getIdProducto());
            if(productoTmp.getCodigoPersonalizado().equals(p.getCodigoPersonalizado()))
            {
                return ;
            }
        }
        
        Producto productoDuplicado=this.buscarProductoActivoPorCodigo(p.getCodigoPersonalizado(),p.getEmpresa());

        if (productoDuplicado != null) {
            throw new ServicioCodefacException("Ya existe un producto ingresado con el mismo código principal");
        }

        ///////////////////////////////////////////////////////////////////////
        ///             HACER UNAS CORRECIONES ANTES DE GRABAR
        ///////////////////////////////////////////////////////////////////////
        //Quitar espacios en blanco
        p.setNombre(p.getNombre().trim());
        
    }
    
    public void editarProducto(Producto producto) throws java.rmi.RemoteException,ServicioCodefacException
    {
        validarGrabarProducto(producto,CrudEnum.EDITAR);
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                //Buscar componentes ensambles eliminados
                List<ProductoEnsamble> productoEnsambleEliminar=productoEnsamblesEliminados(producto);
                
                //TODO: Por el momento ELIMINO directamente de la base de datos pero se deberia manejar por ESTADOS
                for (ProductoEnsamble productoEnsamble : productoEnsambleEliminar) 
                {
                    ProductoEnsamble productoEnsambleTmp=entityManager.merge(productoEnsamble);
                    entityManager.remove(productoEnsambleTmp);
                }
                
                
                entityManager.merge(producto);
                
                //Crear los KARDEX CUANDO NO EXISTA
                if (producto.getManejarInventarioEnum().equals(EnumSiNo.SI)) {
                    ServiceFactory.getFactory().getKardexServiceIf().crearKardexSiNoExisteSinTransaccion(producto);
                }

                
            }
        });
        
        //TODO: Metodo temporal porque cuando se ejecuta sin esta parte causa conflicto al utilizar el producto en el resto de pantallas
        //Al utilizar por segunda vez el mismo objeto por algun motivo genera un error y luego de eso ya funciona correctamente
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException, RemoteException {

                    Producto pTmp = getFacade().find(producto.getIdProducto());
                    List<Kardex> kardexList=ServiceFactory.getFactory().getKardexServiceIf().buscarPorProducto(pTmp);
                    for (Kardex kardex : kardexList) {
                        entityManager.merge(kardex);
                    }
                    
                    entityManager.merge(pTmp);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    private List<ProductoEnsamble> productoEnsamblesEliminados(Producto producto) throws ServicioCodefacException, RemoteException
    {
        if(producto.getTipoProductoEnum().equals(TipoProductoEnum.EMSAMBLE))
        {
            List<ProductoEnsamble> productosActualesList= producto.getDetallesEnsamble();
            List<ProductoEnsamble> productoOriginalList= ServiceFactory.getFactory().getProductoEnsambleServiceIf().buscarPorProducto(producto);
            return UtilidadesLista.restarListas(productoOriginalList, productosActualesList);
        }
        else
        {
            return new ArrayList<ProductoEnsamble>();
        }
    }
    
    public void eliminarProducto(Producto p) throws ServicioCodefacException, RemoteException
    {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {

                //Hacer validaciones mas complicadas si sale que el producto maneja inventario
                if(p.getManejarInventarioEnum().equals(EnumSiNo.SI))
                {
                    //Si el producto no maneja inventario lo puede eliminar directamente
                    KardexService kardexService = new KardexService();
                    List<Kardex> resultadoKardex = kardexService.buscarPorProducto(p, GeneralEnumEstado.ACTIVO);
                    List<String> stockPositivoBodega = new ArrayList<String>();

                    for (Kardex kardex : resultadoKardex) {

                        if(kardex.getEstadoEnum().equals(GeneralEnumEstado.ACTIVO))
                        {                        
                            //if (kardex.getStock() > 0) 
                            if (kardex.getStock().compareTo(BigDecimal.ZERO)==0) 
                            {
                                //Si los kardex no tiene problema y estan con saldos en 0 los elimino y si no se cumple con todos no importa porque se realiza un rollback
                                kardex.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                                entityManager.merge(kardex);

                            } else 
                            {
                                System.out.println(kardex.getStock());
                                Logger.getLogger(ServiceAbstract.class.getName()).log(Level.WARNING, null, "Producto no se puede borrar por que tiene stock "+kardex.getStock()+" en producto "+kardex.getProducto().getNombre() );
                                //Agrego a la lista la bodega con el kardex que tiene problema antes de eliminar
                                stockPositivoBodega.add(kardex.getBodega().getNombre());                    
                            }
                        }
                    }

                    if (stockPositivoBodega.size() > 0) {
                        throw new ServicioCodefacException("No se puede eliminar el producto porque tiene stock en las bodegas: " + UtilidadesLista.castListToString(stockPositivoBodega, ","));
                    }
                }

                //============================================//
                // SI NO TIENEN NINGUNA RESTRICCION ENTONCES ELIMINO EL PRODUCTO Y EL KARDEX //
                //============================================//
                p.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(p);
            }
        });
       
        
    }
    
    public List<Producto> buscar(Empresa empresa)
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        //mapParametros.put("estado",estadoEnum.getEstado());
        mapParametros.put("empresa",empresa);
        
        List<Producto> productos=getFacade().findByMap(mapParametros);
        /*if(productos.size()>0)
        {
            return productos.;
        }*/
        return productos;
        //return productoFacade.findAll();
    }
    
    public Producto buscarPorNombreyEstado(String nombre,GeneralEnumEstado estadoEnum,Empresa empresa) throws RemoteException
    {

        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("nombre",nombre);
        mapParametros.put("estado",estadoEnum.getEstado());
        mapParametros.put("empresa",empresa);
        
        List<Producto> productos=getFacade().findByMap(mapParametros);
        if(productos.size()>0)
        {
            return productos.get(0);
        }
        return null;
        
    }
    
    public Producto buscarProductoActivoPorCodigo(String codigo,Empresa empresa) throws ServicioCodefacException, RemoteException
    {
        //Producto p;
        //p.getCodigoPersonalizado();
        //p.getEstado();GeneralEnumEstado
        
        Map<String,Object> mapParametros=new HashMap<String,Object>();        
        mapParametros.put("codigoPersonalizado",codigo);
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());        
        
        //Cuando este configurado como datos compartidos no tomo en cuenta de donde esta cogiendo la empresa
        if(!ParametroUtilidades.comparar(empresa,ParametroCodefac.DATOS_COMPARTIDOS_EMPRESA,EnumSiNo.SI))
        {
            mapParametros.put("empresa",empresa);        
        }        
        
        
        
        List<Producto> productos=getFacade().findByMap(mapParametros);
        if(productos.size()>0)
        {
            return productos.get(0);
        }
        return null;
        
    }
                
    public List<Producto> obtenerTodosActivos(Empresa empresa) throws java.rmi.RemoteException
    {
        //Producto producto;
        //producto.getEstado()
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        mapParametros.put("empresa",empresa);
        List<Producto> resultados=getFacade().findByMap(mapParametros);
        return resultados;
    }
    
    public Producto buscarGenerarCodigoBarras(EnumSiNo enumSiNo,Empresa empresa ) throws ServicioCodefacException,RemoteException
    {
        //Producto p;
        //p.getEmpresa();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
        mapParametros.put("generarCodigoBarras", EnumSiNo.SI.getLetra());
        mapParametros.put("empresa",empresa);
        

        List<Producto> resultado=getFacade().findByMap(mapParametros);
        if(resultado.size()>0)
        {
            return resultado.get(0);
        }
        return null;
    }
    
    public Producto crearProductoPorDefectoSinTransaccion(Empresa empresa,Integer ivaDefecto) throws RemoteException, ServicioCodefacException
    {
        Producto producto=new Producto();
        producto.setCodigoPersonalizado("001");
        producto.setNombre("libre");
        producto.setValorUnitario(new BigDecimal("1"));
        producto.setManejarInventarioEnum(EnumSiNo.NO);
        producto.setGenerarCodigoBarrasEnum(EnumSiNo.NO);
        producto.setTipoProductoEnum(TipoProductoEnum.PRODUCTO);
        producto.setEmpresa(empresa);
        CatalogoProducto catalogoProducto=crearCatalogoProductoDefectoSinTransaccion(empresa,producto.getNombre(),ivaDefecto);
        producto.setCatalogoProducto(catalogoProducto);
        return producto;        
    }
    
    private CatalogoProducto crearCatalogoProductoDefectoSinTransaccion(Empresa empresa,String nombre,Integer ivaDefecto) throws RemoteException, ServicioCodefacException
    {
        CatalogoProducto catalogoProducto=new CatalogoProducto();
        
        //TODO: Unir codigo
       // CategoriaProducto categoriaProducto=categoriaSeleccionada;
        //catalogoProducto.setCategoriaProducto(categoriaProducto);
        
        /*if(this.getIceSeleccionado()!=null && !this.getIceSeleccionado().getClass().equals(String.class))
        {
            ImpuestoDetalle ice= this.getIceSeleccionado();
            catalogoProducto.setIce(ice);
        }
        
        if( this.getIrbpnrSeleccionado()!=null && !this.getIrbpnrSeleccionado().getClass().equals(String.class))
        {
            ImpuestoDetalle ibpnr=this.getIrbpnrSeleccionado();
            catalogoProducto.setIrbpnr(ibpnr);
        }*/
        //ParametroCodefacService parametroService=new ParametroCodefacService();
        //TODO: Ver si para temnas recurentes creo metodos especificos para que devuelvan el valor convertido
        //ParametroCodefac parametroIvaDefecto=parametroService.getParametroByNombre(ParametroCodefac.IVA_DEFECTO, empresa);
        
        ImpuestoDetalleService ivaImpuestoService=new ImpuestoDetalleService();
        ImpuestoDetalle impuestoDetalle=ivaImpuestoService.buscarPorTarifa(ivaDefecto);
       
        catalogoProducto.setIva(impuestoDetalle);
        
        catalogoProducto.setNombre(nombre);
        
        TipoProductoEnum tipoProductoEnum=TipoProductoEnum.PRODUCTO;
        catalogoProducto.setModuloCod(ModuloCodefacEnum.INVENTARIO.getCodigo());
        return catalogoProducto;
    }
    
    public void grabarConInventario(Producto p,KardexDetalle kardexDetalle) throws ServicioCodefacException,java.rmi.RemoteException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                //Solo grabo el producto cuando no esta creado previamente
                if(p.getIdProducto()==null)
                {
                    grabarSinTransaccion(p,false,false); //graba el producto                
                }
                                
                KardexService kardexService=new KardexService();
                
                //Solo grabar cuando existen datos diferente de null
                if(kardexDetalle!=null)
                {
                    kardexService.grabarKardexDetallSinTransaccion(kardexDetalle,null,true);
                }
                
            }
        });
    }
    
    public List<Producto> buscarPorCategoria(Empresa empresa, CategoriaProducto categoria)
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        //mapParametros.put("estado",estadoEnum.getEstado());
        mapParametros.put("empresa",empresa);
        
        List<Producto> productos=getFacade().findByMap(mapParametros);
        /*if(productos.size()>0)
        {
            return productos.;
        }*/
        return productos;
        //return productoFacade.findAll();
    }
    
    public List<Producto> buscarProductoActivo(Producto producto,Empresa empresa) throws ServicioCodefacException, RemoteException
    {
        Map<String,Object> mapParametros = new HashMap<String,Object>();        
        mapParametros.put("producto",producto);
        mapParametros.put("empresa",empresa);        
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());        
        
        List<Producto> productos = getFacade().findByMap(mapParametros);
        if(productos.size()>0)
        {
            return productos;
        }
        return null;
    }
    

}
