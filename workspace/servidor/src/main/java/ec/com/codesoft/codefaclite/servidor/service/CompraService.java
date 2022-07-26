/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.DetalleFacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.servidor.facade.CompraDetalleFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.CompraFacade;
import ec.com.codesoft.codefaclite.servidor.service.cartera.CarteraService;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.ReembolsoAts;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra.RetencionEnumCompras;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraFacturaReembolso;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModoProcesarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.CarteraParametro;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import ec.com.codesoft.codefaclite.utilidades.sri.ComprobantesElectronicosParametros;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesExpresionesRegulares;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.validadores.ExpresionRegular;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class CompraService extends ServiceAbstract<Compra,CompraFacade> implements CompraServiceIf{
    
    CompraFacade compraFacade;
    CompraDetalleFacade compraDetalleFacade;
    
    
    public CompraService() throws RemoteException {
        super(CompraFacade.class);
        this.compraFacade = new CompraFacade();
        this.compraDetalleFacade = new CompraDetalleFacade();
    }
    
    /**
     * TODO: Separar la logica solo de subir un archivo para tener independiente para copiar en los directorios
     * TODO: Y revisar que el codigo es bastante similar con el que esta en UtilidadesCodefacArchivos.java y 
     * @param archivoCompraXml
     * @return
     * @throws RemoteException
     * @throws ServicioCodefacException 
     */
    public Compra obtenerCompraDesdeXml(RemoteInputStream archivoCompraXml,Empresa empresa) throws RemoteException,ServicioCodefacException
    {        
        InputStream inputStream=null;
        try {            
            ParametroCodefac parametroEmpresa = ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS, empresa);
            String pathServidor = parametroEmpresa.getValor();
            
            Path pathDirectorioTmp=Paths.get(pathServidor+"/"+DirectorioCodefac.TEMP.getNombre());
            
            UtilidadesArchivos.crearRutaDirectorio(pathDirectorioTmp.toString());
            
            Path fileTmp=Files.createTempFile(pathDirectorioTmp, "compra", ".xml");
            //Path file = Files.createTempFile(pathServidor+"/"+DirectorioCodefac.TEMP, "compra",".xml");
            
            //File fileDestino=new File(pathServidor+"/"+DirectorioCodefac.TEMP+"/"+archivoCompraXml.);
            OutputStream outputStream = new FileOutputStream(fileTmp.toFile());            
            inputStream = RemoteInputStreamClient.wrap(archivoCompraXml);
                        
            //Grabar en el disco de forma temporal para procesar el archivo xml
            UtilidadesArchivos.grabarInputStreamEnArchivo(inputStream, outputStream);
            
            //Obtener el comprobante desde el xml
            ComprobanteElectronico comprobanteElectronico=ComprobanteElectronicoService.obtenerComprobanteDataDesdeXml(fileTmp.toFile());
            Compra compra=generarCompraDesdeXml((FacturaComprobante) comprobanteElectronico, empresa);
            
                        
            return compra;
        } catch (IOException ex) {
            Logger.getLogger(CompraService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(CompraService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    
    //TODO: Ver si se puede abstraer para tener un metodo generico que se encargue de llenar los datos principales y luego se pueda llenar el resto
    private Compra generarCompraDesdeXml(FacturaComprobante comprobanteElectronico,Empresa empresa) throws RemoteException, ServicioCodefacException
    {
        Compra compraNueva=new Compra();
        
        compraNueva.setEmpresa(empresa);
        
        //obtener los datos del PROVEEDOR
        Persona proveedor=cargarProveedorCompraDesdeXml(comprobanteElectronico,empresa);
        compraNueva.setProveedor(proveedor);
        
        //obtener los datos de la CLAVE DE ACCESO
        String claveAcceso=comprobanteElectronico.getInformacionTributaria().getClaveAcceso();
        compraNueva.setAutorizacion(claveAcceso);        
        compraNueva.setClaveAcceso(claveAcceso);
        
        //obtener el ESTABLECIMIENTO
        BigDecimal establecimientoNumero=new BigDecimal(comprobanteElectronico.getInformacionTributaria().getEstablecimiento());
        compraNueva.setPuntoEstablecimiento(establecimientoNumero);
        
        //obtener el PUNTO_EMISION
        Integer puntoEmision=Integer.parseInt(comprobanteElectronico.getInformacionTributaria().getPuntoEmision());
        compraNueva.setPuntoEmision(puntoEmision);
        
        //obtener el SECUENCIAL
        Integer secuencial=Integer.parseInt(comprobanteElectronico.getInformacionTributaria().getSecuencial());
        compraNueva.setSecuencial(secuencial);
        
        //obtener la FECHA DE EMISION
        java.util.Date fechaEmision=ComprobantesElectronicosUtil.stringToDate(comprobanteElectronico.getFechaEmision());
        compraNueva.setFechaEmision(fechaEmision);
        compraNueva.setFechaFactura(UtilidadesFecha.castDateUtilToSql(fechaEmision));
        
        //Datos por DEFECTO
        compraNueva.setObservacion("Compra Electrónica");
        compraNueva.setTipoFacturacion(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA);
        
        //Por defecto en estado emitir retencion le selecciono en NO
        RetencionEnumCompras estadoRetencion=Compra.RetencionEnumCompras.SIN_CONTABILIDAD;
        compraNueva.setEstadoRetencionEnum(estadoRetencion);
        
        //Cargar los DETALLES DE LA COMPRA
        List<CompraDetalle> detallesCompra=cargarProductoCompraDetalleDesdeXml(comprobanteElectronico, compraNueva);
        compraNueva.setDetalles(detallesCompra);
        
        
        return compraNueva;
    }
    
    private List<CompraDetalle> cargarProductoCompraDetalleDesdeXml(FacturaComprobante comprobanteElectronico,Compra compra) throws ServicioCodefacException, RemoteException
    {
        List<CompraDetalle> detalles=new ArrayList<CompraDetalle>();        
        for (DetalleFacturaComprobante detalleXml : comprobanteElectronico.getDetalles()) 
        {       
            CompraDetalle compraDetalle=new CompraDetalle();
            //Buscar si existe el producto cargado con el Código principal
            String codigoPrincipal=detalleXml.getCodigoPrincipal();
            ProductoProveedor productoProveedor=ServiceFactory.getFactory().getProductoProveedorServiceIf().buscarActivoPorCodigoProveedor(codigoPrincipal,compra.getEmpresa());
            //Producto producto=ServiceFactory.getFactory().getProductoServiceIf().buscarProductoActivoPorCodigo(codigoPrincipal,compra.getEmpresa());
            
            if(productoProveedor!=null)
            {
                //List<ProductoProveedor> productoProveedorList=ServiceFactory.getFactory().getProductoProveedorServiceIf().buscarProductoProveedorActivo(producto, compra.getProveedor());
                //if(productoProveedorList.size()>0)
                //{
                compraDetalle.setProductoProveedor(productoProveedor);
                //}                
            }
            else
            {
                //Crear temporalmente los datos para mandar los impuestos que deben crear en el nuevo producto}
                ProductoProveedor productoProveedorTmp=new ProductoProveedor();
                Producto productoTmp=new Producto();
                CatalogoProducto catalogoProductoTmp=new CatalogoProducto();
                ImpuestoDetalle impuestoDetalleIvaTmp=ServiceFactory.getFactory().getImpuestoDetalleServiceIf().buscarPorCodigo(Integer.parseInt(detalleXml.getImpuestos().get(0).getCodigoPorcentaje()));
                
                
                
                productoProveedorTmp.setProducto(productoTmp);
                productoTmp.setCatalogoProducto(catalogoProductoTmp);
                catalogoProductoTmp.setIva(impuestoDetalleIvaTmp);                
                compraDetalle.setProductoProveedor(productoProveedorTmp);
                
                //TODO: Si no existe el producto falta programar esta parte
            }
            
            //Agregar la CANTIDAD del detalle
            BigDecimal cantidad = detalleXml.getCantidad();
            compraDetalle.setCantidad(cantidad);

            //Agregar la DESCRIPCION del detalle
            String descripcion = detalleXml.getDescripcion();
            compraDetalle.setDescripcion(descripcion);      
            
            //Agregar el PRECIO UNITARIO
            BigDecimal precioUnitario=detalleXml.getPrecioUnitario();
            compraDetalle.setPrecioUnitario(precioUnitario);            
            
            //Agregar el DESCUENTO
            BigDecimal descuento= detalleXml.getDescuento();
            compraDetalle.setDescuento(descuento);
            
            //Agregar el CODIGO DEL PROVEEDOR ORIGINAL DE LA COMPRA
            compraDetalle.setCodigoProveedor(codigoPrincipal);
            //Calculor los totales previos
            compraDetalle.calcularSubtotalSinIva();
            
            detalles.add(compraDetalle);
            
        }
        return detalles;
    }
   
    
    private Persona cargarProveedorCompraDesdeXml(FacturaComprobante comprobanteElectronico,Empresa empresa) throws ServicioCodefacException, RemoteException
    {
        ///TODO: Revisar el problema para grabar en Utf porque algunos datos estan viniendo con tildes
        String rucProveedor=comprobanteElectronico.getInformacionTributaria().getRuc();
        Persona proveedor=ServiceFactory.getFactory().getPersonaServiceIf().buscarPorIdentificacionYestado(rucProveedor, GeneralEnumEstado.ACTIVO);
        //Si no existe el proveedor entonces creo un nuevo proveedor
        if(proveedor==null)
        {
            String razonSocial=comprobanteElectronico.getInformacionTributaria().getRazonSocial();
            String direccion=comprobanteElectronico.getInformacionTributaria().getDirecionMatriz();
            //String tipoIdentificacion=comprobanteElectronico.getTipoDocumento()
            
                        
            proveedor=ServiceFactory.getFactory().getPersonaServiceIf().crearPlantillaPersona(
                    empresa, 
                    rucProveedor, 
                    Persona.TipoIdentificacionEnum.RUC,  //Todo: Por el momento siempre dejo RUC, por que si me mandan factura electronica tiene que ser ruc
                    razonSocial, 
                    direccion, 
                    OperadorNegocioEnum.PROVEEDOR
            );
            
            proveedor= ServiceFactory.getFactory().getPersonaServiceIf().grabarConValidacion(proveedor, Boolean.TRUE,false);
        }
        
        return proveedor;
    }
    
    @Override
    public void editarCompra(Compra compra) throws ServicioCodefacException
    {
        //TODO: Editar este metodo porque el de grabar es muy similar
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                //Recorro todos los detalles para verificar si existe todos los productos proveedor o los grabo o los edito con los nuevos valores
                    for (CompraDetalle compraDetalle : compra.getDetalles()) {
                        if (compraDetalle.getProductoProveedor().getId() == null) {
                            entityManager.persist(compraDetalle.getProductoProveedor());
                        } else {
                            entityManager.merge(compraDetalle.getProductoProveedor());
                        }
                    }
                    
                    //Eliminar detalles de reembolso
                    List<CompraFacturaReembolso> reembolsosEliminar=obtenerDetallesEliminarReembolso(compra);   
                    
                    for (CompraFacturaReembolso compraFacturaReembolso : reembolsosEliminar) {
                        compraFacturaReembolso= entityManager.merge(compraFacturaReembolso);
                        entityManager.remove(compraFacturaReembolso);                        
                    }

                    entityManager.merge(compra);
                    
                    
                    
                    
            }
        });
    }
    
    private List<CompraFacturaReembolso> obtenerDetallesEliminarReembolso(Compra compra) throws ServicioCodefacException, RemoteException
    {
        List<CompraFacturaReembolso> reembolsosEliminar=new ArrayList<CompraFacturaReembolso>();
                
        if(compra.getFacturaReembolsoList()!=null)
        {            
            List<CompraFacturaReembolso> reembolsosOriginales =ServiceFactory.getFactory().getCompraFacturaReembolsoServiceIf().buscarPorCompra(compra);
            List<CompraFacturaReembolso> reembolsoList=compra.getFacturaReembolsoList();
            
            for (CompraFacturaReembolso reembolsoOriginal : reembolsosOriginales) {
                
                Boolean eliminarReembolso=true;
                for (CompraFacturaReembolso reembolso : reembolsoList) {
                    if(reembolso.getId().equals(reembolsoOriginal.getId()))
                    {
                        eliminarReembolso=false;
                        break;
                    }
                }
                
                if(eliminarReembolso)
                {
                    reembolsosEliminar.add(reembolsoOriginal);
                }
                
            }
            
        }
        return reembolsosEliminar;
    }
    
    
    @Override
    public void grabarCompra(Compra compra,CarteraParametro carteraParametro) throws ServicioCodefacException, RemoteException
    {
        llenarDatosPorDefecto(compra);
        validarDatosCompra(compra);
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
               
                compra.setInventarioIngreso(EnumSiNo.NO.getLetra()); //La primera vez que grabo por defecto grabo para poder ingresar al inventario
                //Recorro todos los detalles para verificar si existe todos los productos proveedor o los grabo o los edito con los nuevos valores
                for (CompraDetalle compraDetalle : compra.getDetalles()) 
                {
                    if (compraDetalle.getProductoProveedor().getId() == null) 
                    {
                        entityManager.persist(compraDetalle.getProductoProveedor());
                    } 
                    else 
                    {
                        entityManager.merge(compraDetalle.getProductoProveedor());
                    }
                }
                
                List<CompraFacturaReembolso> compraList=compra.getFacturaReembolsoList();                
                compra.setFacturaReembolsoList(null);
                
                entityManager.persist(compra);
                entityManager.flush();
                
                //Si la factura tiene FACTURAS DE REEMBOLSO empiezo a recorrer para poder grabar
                if(compraList!=null)
                {
                    for (CompraFacturaReembolso facturaReembolso : compraList) 
                    {    
                        //Todo: por el momento no guardo la compra por que puede causar problemas
                        facturaReembolso.setCompra(compra);
                        if( facturaReembolso.getId() == null )
                        {                            
                            entityManager.persist(facturaReembolso);                            
                        }
                        else
                        {
                            entityManager.merge(facturaReembolso);
                        }
                        entityManager.flush();//facturaReembolso.setCompra(compra);                                                
                    }
                }
                //Despues de grabar seteo nuevamente la lista en la compra
                compra.setFacturaReembolsoList(compraList);
                entityManager.merge(compra);
                
                
                grabarCartera(compra,carteraParametro); //Grabo la cartera desde de grabar la compra para tener el id de referencia que necesito en cartera
                
            }
        });
        
        //TODO: Falta retornar el tipo de dato por ejemplo en los dialogos necesita obtener el nuevo dato modificado.
    }
    
    private void llenarDatosPorDefecto(Compra compra) throws RemoteException, ServicioCodefacException
    {
        //Para documento de nota de venta interna ingreso unos datos adicionales automaticos
        DocumentoEnum documentoEnum=compra.getCodigoDocumentoEnum();
        if(documentoEnum.equals(DocumentoEnum.NOTA_VENTA_INTERNA))
        {
            if(compra.getAutorizacion()==null || compra.getAutorizacion().trim().isEmpty())
            {
                compra.setAutorizacion("000000");
            }
            
            if(compra.getPuntoEstablecimiento()==null || compra.getPuntoEstablecimiento().compareTo(BigDecimal.ZERO)==0)
            {
                compra.setPuntoEstablecimiento(new BigDecimal("999"));
            }
            
            if(compra.getPuntoEmision()==null || compra.getPuntoEmision()==0 )
            {
                compra.setPuntoEmision(999);
            }
            
            if(compra.getSecuencial()==null || compra.getPuntoEmision().equals("000000000") )
            {
                Integer secuencial=getFacade().obtenerMaximoCodigoNotaVentaInterna(compra.getPuntoEmision(),compra.getPuntoEstablecimiento(),compra.getEmpresa());
                compra.setSecuencial(secuencial);
            }
            
        }
    }
    
    private void validarDatosCompra(Compra compra) throws RemoteException, ServicioCodefacException
    {
        if (compra.getPuntoEstablecimiento() == null) 
        {
            throw new ServicioCodefacException("El establecimiento no puede ser vacio");
        }
        
        if (compra.getPuntoEmision() == null) 
        {
            throw new ServicioCodefacException("EL punto de emisión no puede ser vacio");
        }
        
        if (compra.getSecuencial() == null) 
        {
            throw new ServicioCodefacException("El secuencial no puede ser vacio");
        }
        
                
        //La validacion de los secuenciales solo debe funcionar cuando no es un documento interno por que puede ser que no tenga datos de secuenciales
        DocumentoEnum documentoEnum=compra.getCodigoDocumentoEnum();
        if(!documentoEnum.equals(DocumentoEnum.NOTA_VENTA_INTERNA))
        {
                        
            if (compra.getPuntoEstablecimiento().compareTo(BigDecimal.ZERO) == 0) 
            {
                throw new ServicioCodefacException("El establecimiento no puede ser 0");
            }
            if (compra.getPuntoEmision() == 0) 
            {
                throw new ServicioCodefacException("EL punto de emisión no puede ser 0");
            }
            if (compra.getSecuencial() == 0) 
            {
                throw new ServicioCodefacException("El secuencial no puede ser 0");
            }
            
            if (compra.getAutorizacion()== null) {
                throw new ServicioCodefacException("La autorización no puede ser vacio");
            }
            
            if (compra.getAutorizacion().toString().trim().isEmpty()) {
                throw new ServicioCodefacException("La autorización no puede estar vacia");
            }
            
            if(compra.getPuntoEstablecimiento().toString().length()>3 && compra.getPuntoEmision().toString().length()>3 && compra.getPuntoEmision().toString().length()>9)
            {
                throw new ServicioCodefacException("Revise el formato del secuencial de la compra que es incorrecto");
            }
        }
        
        /**
         * =====================================================================
         *          VALIDAR EL TAMANIO DE LA AUTORIZACION
         * =====================================================================
         */
        if(compra.getAutorizacion()!=null)
        {
            if(compra.getAutorizacion().length()>ComprobantesElectronicosParametros.TAMANIO_MAXIMO_AUTORIZACION)
            {
                throw new ServicioCodefacException("El tamanio de la autorización no puede ser superior a "+ComprobantesElectronicosParametros.TAMANIO_MAXIMO_AUTORIZACION);
            }
        }
        
        /**
         * =====================================================================
         *          VALIDAR EL INGRESO DE DATOS NO REPETIDOS
         * =====================================================================
         * TODO: Poner en metodo facade porque seguro esta funcion utilice algunas veces mas para las vistas
         * Solo hacer esta validacion cuando no sea consumidor final
         */
        if(!compra.getIdentificacion().equals(Persona.IDENTIFICACION_CONSUMIDOR_FINAL))
        {
            Map<String,Object> mapParametros=new HashMap<String,Object>();
            mapParametros.put("puntoEstablecimiento",compra.getPuntoEstablecimiento());
            mapParametros.put("puntoEmision",compra.getPuntoEmision());
            mapParametros.put("secuencial",compra.getSecuencial());
            mapParametros.put("empresa",compra.getEmpresa());
            mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
            mapParametros.put("codigoDocumento", compra.getCodigoDocumentoEnum().getCodigo());
            mapParametros.put("proveedor", compra.getProveedor());
            
            //compra.get

            List<Compra> resultadoCompra= getFacade().findByMap(mapParametros);
            if(resultadoCompra.size()>0)
            {
                throw new ServicioCodefacException("No se puede ingresar compras repetidas del mismo proveedor");
            }
        }
        
        validarAutorizacionPorDocumento(compra);
        
        /**
         * Validar que este ingresando una compra repetida
         */
        //if(getFacade().verificarCompraRepetida(compra))
        //{
        //    throw new ServicioCodefacException("No se puede ingresar compras repetidas");
        //}
        
        
    }
    
    private void validarAutorizacionPorDocumento(Compra compra) throws RemoteException, ServicioCodefacException
    {
        //TODO: Crear un documento para FACTURAS DEL EXTERIOR y que en esas facturas les permita ingresar numeros y letras en las autorizaciones
        //para todos los demas casos debe permitir ingresar solo numeros en el campo de autorizacion
        
        Boolean validacionCorrecta= UtilidadesExpresionesRegulares.validar(compra.getAutorizacion(), ExpresionRegular.soloNumeros);
        if(!validacionCorrecta)
        {
            throw new ServicioCodefacException("La autorización de este tipo de compra solo puede ser números ");
        }
    }
    
    private void grabarCartera(Compra compra,CarteraParametro carteraParametro) throws RemoteException, ServicioCodefacException
    {
        //Grabar en la cartera si todo el proceso anterior fue correcto
        CarteraService carteraService = new CarteraService();
        carteraService.grabarDocumentoCartera(compra, Cartera.TipoCarteraEnum.PROVEEDORES,carteraParametro,CrudEnum.CREAR);
    }

    public void eliminarCompra(Compra compra) throws ServicioCodefacException,RemoteException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {                
                eliminarCompraSinTransaccion(compra);
            }
        });
    }
    
    public void eliminarCompraSinTransaccion(Compra compra) throws ServicioCodefacException, RemoteException {
        eliminarRetencionCompraSinTransaccion(compra);

        //Solo crear movimiento de egreso de mercaderia cuando ya fueron ingresados al inventario
        if (compra.getInventarioIngresoEnum().equals(EnumSiNo.SI)) {
            for (CompraDetalle detalle : compra.getDetalles()) {
                eliminarDetalleCompra(detalle);
            }
        }

        /**
         * ==================================================================================
         * ELIMINACION EN CARTERA DE LAS COMPRAS
         * ==================================================================================
         */
        eliminarCarteraCompra(compra);
    }
    
    private void eliminarCarteraCompra(Compra entity) throws RemoteException, ServicioCodefacException
    {        
        //Cartera cartera;
        //cartera.
        CarteraService carteraService=new CarteraService();
        Cartera carteraCompra=carteraService.buscarCarteraPorReferencia(entity.getId(),entity.getCodigoDocumentoEnum(), GeneralEnumEstado.ACTIVO, Cartera.TipoCarteraEnum.PROVEEDORES,entity.getSucursalEmpresa());                
        if(carteraCompra!=null)
        {
            carteraService.eliminarCarteraSinTransaccion(carteraCompra,ModoProcesarEnum.NORMAL);
            
        }
    }
    

    private void eliminarDetalleCompra(CompraDetalle compraDetalle) throws RemoteException, ServicioCodefacException
    {   
        
        Bodega bodega = obtenerBodegaDevolucion(compraDetalle);
        KardexService kardexService=new KardexService();
        kardexService.afectarInventario(
                bodega,
                compraDetalle.getCantidad(),
                compraDetalle.getPrecioUnitario(),
                compraDetalle.getTotal(),
                compraDetalle.getId(),
                compraDetalle.getProductoProveedor().getProducto().getIdProducto(),
                TipoDocumentoEnum.ELIMINADO_COMPRA,
                compraDetalle.getCompra().getPuntoEmision().toString(),
                compraDetalle.getCompra().getPuntoEstablecimiento().toString(),
                compraDetalle.getCompra().getSecuencial(),
                compraDetalle.getCompra().getFechaEmision()
        );

        
    }
    
    private Bodega obtenerBodegaDevolucion(CompraDetalle compraDetalle) throws RemoteException, ServicioCodefacException
    {
        KardexDetalleService kardexDetalleService=new KardexDetalleService();
        KardexDetalle kardexDetalle=kardexDetalleService.consultarPorReferencia(compraDetalle.getCompra().getCodigoTipoDocumentoEnum(),compraDetalle.getCompra().getId(),compraDetalle.getProductoProveedor().getProducto());
        if(kardexDetalle!=null)
        {
            return kardexDetalle.getKardex().getBodega();
        }
        return null;
    }
    
    private void eliminarRetencionCompraSinTransaccion(Compra compra) throws ServicioCodefacException, RemoteException
    {
        RetencionService retencionService = new RetencionService();
        List<Retencion> retencionesAsociadas = retencionService.obtenerRetencionesPorCompra(compra);

        if (retencionesAsociadas.size() == 0) {
            compra.setEstado(GeneralEnumEstado.ELIMINADO.getEstado()); //Cambiar el estado de la compra
            entityManager.merge(compra);
        } else {
            //Obtener las retenciones asociadas
            String retencionesStr = "";
            for (Retencion retencionesAsociada : retencionesAsociadas) {
                retencionesStr += retencionesAsociada.getPreimpreso() + "  ";
            }
            //retencionesStr=UtilidadesTextos.quitarUltimaLetra(retencionesStr);

            throw new ServicioCodefacException("No se puede eliminar porque existe retenciones asociadas " + retencionesStr);
        }
    }
    
    
    

    @Override
    public List<Compra> obtenerTodos()
    {
        return compraFacade.findAll();
    }
    
    @Override
    public List<Compra> obtenerCompraReporte(Persona proveedor, Date fechaInicial, Date fechaFin, DocumentoEnum de, TipoDocumentoEnum tde,GeneralEnumEstado estadoEnum,Empresa empresa) throws ServicioCodefacException,java.rmi.RemoteException
    {
        return compraFacade.obtenerCompraReporte(proveedor, fechaInicial, fechaFin, de, tde,estadoEnum,empresa);
    }    
    
    @Override
    public List<Compra> obtenerCompraDisenable()
    {
        return compraFacade.getCompraRetencionDisenable();
    }
}
