/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobante.reporte;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.controlador.reportes.AgrupadoReporteIf;
import ec.com.codesoft.codefaclite.controlador.reportes.CampoAgruparIf;
import ec.com.codesoft.codefaclite.controlador.reportes.EnumReporteAgruparIf;
import ec.com.codesoft.codefaclite.controlador.reportes.UtilidadReporteJasper;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.ReferenciaDetalleFacturaRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NotaCreditoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class ControladorReporteFactura {
    
    //TODO: Falta hacer 2 notas de credito para los calculos de los anulados    
    
    private PersonaEstablecimiento persona;
    private Date fechaInicio;
    private Date fechaFin;
    private ComprobanteEntity.ComprobanteEnumEstado estadoFactura;
    private Boolean filtrarReferidos;
    private Persona referido;
    protected Boolean reporteAgrupado;
    private Boolean afectarNotaCredito;
    private DocumentoEnum documentoConsultaEnum;
    
    /**
     * Parametros que me permite desglosar el reporte
     */
    private Boolean reporteConDetallesFactura;
    
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    //private Map<String,BigDecimal> mapTotales;
    
    private List<ReporteFacturaData> data;

    private TotalSumatoria total;    //en este campo se van a calcular el valor total de todos los comprobantes     
    private TotalSumatoria totalAnulados; //en esta variable se van a calcular los totales de los anulados
    private TotalSumatoria totalNotasCredito; //en este campo se van a guardar los valores solo de las notas de credito
    private PuntoEmision puntoEmision; //en este campo me permite filtrar por el punto de emision
    private Sucursal sucursal;
    protected Empresa empresa;
    private Usuario usuario;
    private List<Factura> datafact;
    
    private Boolean agregarCostos;
    

    public ControladorReporteFactura(Empresa empresa) {
        this.empresa=empresa;
        this.data = new ArrayList<ReporteFacturaData>();
        this.reporteConDetallesFactura=false;
        this.agregarCostos=false;
    }
    
    public ControladorReporteFactura(Empresa empresa, Usuario usuario){
        this.empresa = empresa;
        this.usuario = usuario;
        this.data = new ArrayList<ReporteFacturaData>();
        this.reporteConDetallesFactura=false;
        this.agregarCostos=false;
    }
    
    
    public ControladorReporteFactura(PersonaEstablecimiento persona, Date fechaInicio, Date fechaFin, ComprobanteEntity.ComprobanteEnumEstado estadoFactura, Boolean filtrarReferidos, Persona referido, Boolean reporteAgrupado, Boolean afectarNotaCredito, DocumentoEnum documentoConsultaEnum,Empresa empresa,Sucursal sucursal) {
        this.persona = persona;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estadoFactura = estadoFactura;
        this.filtrarReferidos = filtrarReferidos;
        this.referido = referido;
        this.reporteAgrupado = reporteAgrupado;
        this.afectarNotaCredito = afectarNotaCredito;
        this.documentoConsultaEnum = documentoConsultaEnum;
        this.empresa=empresa;
        //this.mapTotales = new HashMap<String,BigDecimal>();
        this.data = new ArrayList<ReporteFacturaData>();
        this.sucursal=sucursal;
        this.reporteConDetallesFactura=false;
        this.agregarCostos=false;
    }
    
    /**
     * Construir los objetos de los totales para utilizar en las sumatorias
     */
    private void buildMapTotales()
    {
        total=new TotalSumatoria();
        totalAnulados=new TotalSumatoria();
        totalNotasCredito=new TotalSumatoria();
        
        //return this.mapTotales;
    }
    
    public void generarReporte() {
        try {
            buildMapTotales();
            FacturacionServiceIf fs = ServiceFactory.getFactory().getFacturacionServiceIf();
            /**
             * ===============================================================
             *              OBTENER TODAS LAS FACTURAS POR FILTROS
             * ===============================================================
             */
            //datafact;
            if(usuario != null)
            {
                datafact = fs.obtenerFacturasReporte(persona, fechaInicio, fechaFin, estadoFactura, filtrarReferidos, referido, reporteAgrupado,puntoEmision,empresa,documentoConsultaEnum,sucursal, usuario);
            }
            else{
                datafact = fs.obtenerFacturasReporte(persona, fechaInicio, fechaFin, estadoFactura, filtrarReferidos, referido, reporteAgrupado,puntoEmision,empresa,documentoConsultaEnum,sucursal,null);
            }   
            /**
             * ===============================================================
             *           AGREGAR EL COSTO DE LA VENTA DISPONIBLE EN EL EXCEL
             * ===============================================================
             * TODO: Este metodo solo es temporal hasta ver una forma mucha mas optima
             */
            //Map<Factura,BigDecimal> mapCostos=fs.obtenerCostoFacturas(datafact);
            Map<Factura,BigDecimal> mapCostos=new HashMap<Factura,BigDecimal>();
            if(agregarCostos)
            {
                mapCostos=fs.obtenerCostoFacturas(datafact);
            }
            
            /**
             * =====================================================================
             *          SERVICIO PARA BUSCAR LAS NOTAS DE CREDITO SEGUN LOS FILTROS
             * =====================================================================
             */
            NotaCreditoServiceIf nc = ServiceFactory.getFactory().getNotaCreditoServiceIf();
            List<NotaCredito> dataNotCre = null;
            
            /**             
             * Todo: Analizar si las notas de credito se deben obtener por cada sucursal de la persona
             */
            if (documentoConsultaEnum.equals(DocumentoEnum.FACTURA) || documentoConsultaEnum.equals(DocumentoEnum.NOTA_VENTA_INTERNA)) {
                dataNotCre = nc.obtenerNotasReporte((persona!=null)?persona.getPersona():null, null, null, ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO,empresa);
            } else {
                dataNotCre = nc.obtenerNotasReporte((persona!=null)?persona.getPersona():null, fechaInicio, fechaFin, estadoFactura,empresa);
            }
            
            //Array para obtener los datos consultados para los reportes
            data = new ArrayList<ReporteFacturaData>();
            
            switch (documentoConsultaEnum) {
                case NOTA_VENTA:
                case LIQUIDACION_COMPRA:
                case NOTA_VENTA_INTERNA:
                case FACTURA:
                    for (Factura factura : datafact) {
                        //Valores iniciales por factura
                        BigDecimal totalMenosNotaCredito = BigDecimal.ZERO;
                        BigDecimal totalNotaCredito = BigDecimal.ZERO;
                        Boolean calcularTotalesSinNotasCredito = false;
                        String preimpresoNotaCreditoAfecta = "";
                        String valorNotaCredito = "";
                        ComprobanteEntity.ComprobanteEnumEstado estadoEnum= factura.getEstadoEnum();
                        
                        BigDecimal descuentoConImpuestos = (factura.getDescuentoImpuestos() != null) ? factura.getDescuentoImpuestos() : BigDecimal.ZERO;
                        BigDecimal descuentoSinImpuestos = (factura.getDescuentoSinImpuestos() != null) ? factura.getDescuentoSinImpuestos() : BigDecimal.ZERO;
                        
                                               
                        total.addSubtotalSinImpuesto(factura.getSubtotalSinImpuestos());
                        total.addSubtotalConImpuesto(factura.getSubtotalImpuestos());
                        total.addsValorImpuesto(factura.getIva());
                        total.addDescuentoconImpuesto(descuentoConImpuestos);
                        total.addDescuentoSinImpuesto(descuentoSinImpuestos);   
                        
                        /**
                         * =============================================================
                         *   SUMAR LOS VALORES DE LOS COMPROBANTES ELIMINADOS EN EL SRI
                         * =============================================================
                         */
                        if(estadoEnum.equals(estadoEnum.ELIMINADO_SRI) && estadoFactura.equals(estadoEnum.TODOS_SRI))
                        {
                            totalAnulados.addSubtotalSinImpuesto(factura.getSubtotalSinImpuestos());
                            totalAnulados.addSubtotalConImpuesto(factura.getSubtotalImpuestos());
                            totalAnulados.addsValorImpuesto(factura.getIva());
                            totalAnulados.addDescuentoconImpuesto(descuentoConImpuestos);
                            totalAnulados.addDescuentoSinImpuesto(descuentoSinImpuestos);                            
                        }
                                                
                        /**
                         * ========================================================================
                         *  BUSCAR LOS VALORES DE LAS NOTAS DE CREDITO QUE AFECTAN A LA FACTURA
                         *  Nota: Solo si activa si el filtro lo requiere
                         * ========================================================================
                         */
                        if (afectarNotaCredito) {                        
                            NotaCredito notaCredito = verificarPorFactura(factura, dataNotCre);
                            if (notaCredito != null) {                                
                                //Calculo de los valores cuando existe una nota de credito
                                preimpresoNotaCreditoAfecta = notaCredito.getPreimpreso();
                                totalNotaCredito = notaCredito.getTotal();
                                totalMenosNotaCredito = factura.getTotal().subtract(notaCredito.getTotal());
                                
                                //Solo hacer la sumatoria de los valores de las notas de credito
                                //TODO: Ver si se puede optimizar para sumar todo y luego restar de las notas de credito
                                totalNotasCredito.addSubtotalSinImpuesto(notaCredito.getSubtotalCero());
                                totalNotasCredito.addSubtotalConImpuesto(notaCredito.getSubtotalDoce());
                                totalNotasCredito.addsValorImpuesto(notaCredito.getValorIvaDoce());
                                totalNotasCredito.addDescuentoconImpuesto(notaCredito.getDescuentoImpuestos());
                                totalNotasCredito.addDescuentoSinImpuesto(notaCredito.getDescuentoSinImpuestos());


                            } else {
                                //Calculo de los valores cuenado no existe nota de credito
                                calcularTotalesSinNotasCredito = true;
                            }
                        } else {
                            calcularTotalesSinNotasCredito = true;
                        }
                        
                        //Hacer el calculo normal sin notas de credito
                        if (calcularTotalesSinNotasCredito) {
                            totalMenosNotaCredito = factura.getTotal();
                        }
                        
                        /**
                         * =====================================================
                         *       CALCULO ADICIONAL PARA LAS COMISIONES
                         * =====================================================
                         */
                        BigDecimal valorComision = BigDecimal.ZERO;
                        if (factura.getReferido() != null) {
                            BigDecimal porcentajeComision = (factura.getReferido().getContactoClientePorcentaje() != null) ? factura.getReferido().getContactoClientePorcentaje() : BigDecimal.ZERO;
                            
                            //Cuando los valores son distintos es porque aplica una nota de credito
                            if (factura.getTotal().compareTo(totalMenosNotaCredito) != 0) {
                                BigDecimal porcentajeImpuestosOriginal = factura.getIva().divide(factura.getTotal(), 8, BigDecimal.ROUND_HALF_UP); //Obtenego cual es la proporcion del iva original , por si afecta un nota de credito valor a calcular la comision con esta proporicion
                                valorComision = totalMenosNotaCredito.divide(porcentajeImpuestosOriginal.add(BigDecimal.ONE), 2, BigDecimal.ROUND_HALF_UP);
                                valorComision = valorComision.multiply(porcentajeComision.divide(new BigDecimal("100"), 8, BigDecimal.ROUND_HALF_UP));
                                valorComision = valorComision.setScale(2, BigDecimal.ROUND_HALF_UP);
                            } else { //Este caso es cuando no aplica ninguna nota de credito
                                valorComision = factura.getTotal().subtract(factura.getIva());
                                valorComision = valorComision.multiply(porcentajeComision.divide(new BigDecimal("100"), 8, BigDecimal.ROUND_HALF_UP));
                                valorComision = valorComision.setScale(8, BigDecimal.ROUND_HALF_UP);
                            }
                        }
                        
                        /**
                         * ======================================================
                         *      CONSTRUIR EL MODELO PARA EL REPORTE
                         * ======================================================
                         */
                        if(factura.getCliente()==null)
                        {
                            Logger.getLogger(ControladorReporteFactura.class.getName()).log(Level.WARNING,"Factura con problema de identificación: "+factura.getPreimpreso()+", id="+factura.getId());
                        }
                        
                        String nombreComercial="";
                        if(factura.getSucursal()!=null)
                        {
                            nombreComercial=factura.getSucursal().getNombreComercial();
                        }
                        
                        ReporteFacturaData reporteData = new ReporteFacturaData(
                                factura.getPreimpreso(),
                                dateFormat.format(factura.getFechaEmision()),
                                (factura.getFechaAutorizacionSri()!=null)?dateFormat.format(factura.getFechaAutorizacionSri()):"",
                                (factura.getCliente()!=null)?factura.getCliente().getIdentificacion():"",
                                (factura.getCliente()!=null)?factura.getCliente().getRazonSocial():"",
                                nombreComercial,
                                (factura.getEstadoEnum() != null) ? factura.getEstadoEnum().getNombre() : "Sin estado",
                                (factura.getTipoFacturacionEnum() != null) ? factura.getTipoFacturacionEnum().getNombre() : "Sin definir",
                                (factura.getCodigoDocumentoEnum() != null) ? factura.getCodigoDocumentoEnum().getNombre() : "",
                                factura.getSubtotalImpuestos().toString(),
                                factura.getSubtotalSinImpuestos().toString(),
                                factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()).toString(),
                                factura.getIva().toString(),
                                factura.getTotal().toString(),
                                totalNotaCredito.toString(),
                                preimpresoNotaCreditoAfecta,
                                totalMenosNotaCredito.toString(),
                                (factura.getReferido() != null) ? factura.getReferido().getRazonSocial() : "",
                                (factura.getReferido() != null) ? factura.getReferido().getIdentificacion() : "",
                                (factura.getReferido() != null) ? factura.getReferido().getContactoClientePorcentaje().toString() : "0",
                                valorComision.toString(),
                                factura.getClaveAcceso(),
                                factura.getPuntoEmision().toString()
                        );
                        
                        //Agregar datos adicionales al modelo de la factura
                        reporteData.setFechaMaximaPago((factura.getFechaVencimiento() != null) ? UtilidadesFecha.formatoDiaMesAño(factura.getFechaVencimiento()) : "");
                        reporteData.setVendedor((factura.getVendedor() != null) ? factura.getVendedor().getNombresCompletos() : "");
                        reporteData.setCosto((mapCostos.get(factura)!=null)?mapCostos.get(factura).toString():"0");     
                        //Agregar por defecto la primera forma de pago que se aplico en la factura
                        //TODO: Terminar de aplicar la misma logica para las notas de credito
                        //TODO: Analizar como debe funcionar cuando se tiene varias formas de pago , seguramente se tiene que dividir los registros en varios
                        reporteData.setFormaPago(obtenerFormaPago(factura));
                        reporteData.mostrarReferido = filtrarReferidos; //Variables para saber si se debe mostrar las personas que le refieren
                        
                        //Agregando Zona y Ruta
                        reporteData.setZona(buscarZona(factura));
                        reporteData.setRuta((factura.getRutaNombre()!=null)?factura.getRutaNombre():"");
                        
                        if(reporteConDetallesFactura)
                        {
                            List<ReporteFacturaData> respuesta=convertirDatosReportePorProducto(factura, reporteData);
                            data.addAll(respuesta);
                        }
                        else
                        {
                            //Agregar la cantidad de producto cuando no es individual por productos
                            reporteData.setCantidad(factura.cantidadTotalProductos()+"");
                            data.add(reporteData);
                        }
                        
                    }
                    
                    break;
                    
                case NOTA_CREDITO:
                    for (NotaCredito nota : dataNotCre) {
                        Vector<String> fila = new Vector<String>();
                        ComprobanteEntity.ComprobanteEnumEstado estadoEnum = ComprobanteEntity.ComprobanteEnumEstado.getEnum(nota.getEstado());
                        
                        ReporteFacturaData reporteData = new ReporteFacturaData(
                                nota.getPreimpreso(),
                                dateFormat.format(nota.getFechaEmision()),
                                (nota.getFechaAutorizacionSri()!=null)?dateFormat.format(nota.getFechaAutorizacionSri()):"",
                                nota.getCliente().getIdentificacion(),
                                nota.getCliente().getRazonSocial(),
                                (nota.getCliente().getEstablecimientos().get(0).getNombreComercial()!= null) ? nota.getCliente().getEstablecimientos().get(0).getNombreComercial(): "",
                                (nota.getEstadoEnum() != null) ? nota.getEstadoEnum().getNombre() : "Sin estado",
                                (nota.getTipoFacturacionEnum() != null) ? nota.getTipoFacturacionEnum().getNombre() : "",
                                (nota.getCodigoDocumentoEnum() != null) ? nota.getCodigoDocumentoEnum().getNombre() : "",
                                nota.getSubtotalDoce().toString(),
                                nota.getSubtotalCero().toString(),
                                "0", //Ver si es necesario calcular los descuentos en las notas de credito
                                nota.getValorIvaDoce().toString(),
                                nota.getTotal().toString(),
                                "0",
                                nota.getNumDocModificado(),
                                nota.getTotal().toString(),
                                (nota.getRazonSocial() != null) ? nota.getRazonSocial() : "",
                                (nota.getIdentificacion() != null) ? nota.getIdentificacion() : "",
                                "0",
                                "0",//TODO: Revisar porque en esta parte me late que no necesito calcular el iva
                                nota.getClaveAcceso(),
                                nota.getPuntoEmision().toString()
                        );
                        
                        reporteData.mostrarReferido = filtrarReferidos; //Variables para saber si se debe mostrar las personas que le refieren
                        data.add(reporteData);
                        
                                                //Sumar solo si esta buscando todos los comprobantes del Sri y esta eliminado en el Sri
                        if(estadoEnum.equals(estadoEnum.ELIMINADO_SRI) && estadoFactura.equals(estadoEnum.TODOS_SRI))
                        {
                            totalAnulados.addSubtotalSinImpuesto(nota.getSubtotalSinImpuestos());
                            totalAnulados.addSubtotalConImpuesto(nota.getSubtotalImpuestos());
                            totalAnulados.addsValorImpuesto(nota.getIva());
                            totalAnulados.addDescuentoconImpuesto(nota.getDescuentoImpuestos());
                            totalAnulados.addDescuentoSinImpuesto(nota.getDescuentoSinImpuestos());                            
                        }
                        
                        
                        totalNotasCredito.addSubtotalSinImpuesto(nota.getSubtotalCero());
                        totalNotasCredito.addSubtotalConImpuesto(nota.getSubtotalDoce());
                        totalNotasCredito.addsValorImpuesto(nota.getIva());
                        totalNotasCredito.addDescuentoconImpuesto(nota.getDescuentoImpuestos());
                        totalNotasCredito.addDescuentoSinImpuesto(nota.getDescuentoSinImpuestos());
                        //TODO: ver si agregar el descuento
                        
                    }
                    
                    break;
                    
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorReporteFactura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ControladorReporteFactura.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    //TODO: Metodo temporal para hacer retrocompatible con datos de facturas anteriores
    @Deprecated
    private String buscarZona(Factura factura)
    {
        if(factura.getZonaNombre()!=null)
        {
            return factura.getZonaNombre();
        }
        else
        {
            if(factura.getSucursal()!=null)
            {
                if(factura.getSucursal().getZona()!=null)
                {
                    return factura.getSucursal().getZona().getNombre();
                }
            }
        }
        return "";
    }
    
    private String obtenerFormaPago(Factura factura)
    {
        if(factura.getFormaPagos()!=null && factura.getFormaPagos().size()>0)
        {
            return factura.getFormaPagos().get(0).getSriFormaPago().getNombre();
        }
        return "Sin forma de pago";
    }
    
    /**
     * Metodo que permite convertir el dato de reporte original para separar por detalles para el reporte de productos
     * @param factura
     * @param reporteData 
     */
    private List<ReporteFacturaData> convertirDatosReportePorProducto(Factura factura,ReporteFacturaData reporteData)
    {
        List<ReporteFacturaData> resultados=new ArrayList<ReporteFacturaData>();
        try {            
            for (FacturaDetalle detalle : factura.getDetalles()) 
            {
                //detalle.getIva()
                ReporteFacturaData dataFacturaCopia=(ReporteFacturaData) reporteData.clone();
                dataFacturaCopia.setCantidad(detalle.getCantidad()+"");
                dataFacturaCopia.setDescFactura(detalle.getDescuento()+"");
                dataFacturaCopia.setIvaDoceFactura(detalle.getIva()+"");
                //dataFacturaCopia.setTotalFactura(detalle.getPrecioUnitario()+"");
                dataFacturaCopia.setTotalFactura(detalle.calcularTotalFinal()+"");
                if(!factura.getEstadoNotaCreditoEnum().equals(Factura.EstadoNotaCreditoEnum.SIN_ANULAR))
                {
                    dataFacturaCopia.setTotalFinal(BigDecimal.ZERO+"");
                }
                else
                {
                    dataFacturaCopia.setTotalFinal(detalle.calcularTotalFinal()+"");
                }
                dataFacturaCopia.setNombreProducto(detalle.getDescripcion());
                dataFacturaCopia.setCodigoProducto(detalle.getCodigoPrincipal());
                
                //TODO: Por el momento se realiza la consulta directa del producto pero esta parte tiene que estar en el servidor
                String nombreCategoria="Sin asignar";
                ReferenciaDetalleFacturaRespuesta respuesta=ServiceFactory.getFactory().getFacturacionServiceIf().obtenerReferenciaDetalleFactura(detalle.getTipoDocumentoEnum(),detalle.getReferenciaId());
                if (respuesta.objecto != null) {
                    switch (respuesta.tipoDocumentoEnum) {
                        case LIBRE:
                        case INVENTARIO:
                            Producto producto=(Producto) respuesta.objecto;
                            if(producto.getCatalogoProducto().getCategoriaProducto()!=null)
                            {
                                nombreCategoria=producto.getCatalogoProducto().getCategoriaProducto().getNombre();
                            }
                            break;
                    }
                }
                                
                dataFacturaCopia.setCategoria(nombreCategoria);
                dataFacturaCopia.setPrecioUnitarioReporte(detalle.getPrecioUnitario().setScale(0,BigDecimal.ROUND_DOWN).toString());
                resultados.add(dataFacturaCopia);
            }
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ControladorReporteFactura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorReporteFactura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ControladorReporteFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultados;
        
    }
    
    private NotaCredito verificarPorFactura(Factura factura, List<NotaCredito> notasCredito) {
        for (NotaCredito notaCredito : notasCredito) {
            if (notaCredito.getFactura() != null && notaCredito.getFactura().equals(factura)) {
                return notaCredito;
            }
        }
        return null;
    }
    
    /**
     * TODO: ver si se puede unificar la forma de crear la cabecera con el de la pantalla de Factura Reporte
     * @return 
     */
    public Vector<String>  crearCabezeraTabla()
    {
        Vector<String> titulo = new Vector<>();
        titulo.add("Clave de Acceso");
        titulo.add("Fecha Max Pago");
        titulo.add("Vendedor");

        titulo.add("Preimpreso");
        titulo.add("Referencia");
        titulo.add("Fecha");
        titulo.add("Identificación");
        titulo.add("Razón social");
        
        titulo.add("Nombre legal");
            
        titulo.add("Documento");
        titulo.add("Estado");
        titulo.add("Tipo");
        titulo.add("Subtotal 12%");
        titulo.add("Subtotal 0% ");
        titulo.add("Descuentos");
        titulo.add("IVA 12%");
        titulo.add("Valor Afecta");
        titulo.add("Total");
        return titulo;
    }
    
    public File obtenerArchivoReporteExcel()
    {
        try {
            Excel excel = new Excel();
            Vector<String> titulosVector = crearCabezeraTabla();
            String nombreCabeceras[] = titulosVector.toArray(new String[titulosVector.size()]); //Convertir en array
            excel.gestionarIngresoInformacionExcel(nombreCabeceras, data);
            return excel.obtenerArchivo();
        } catch (IOException ex) {
            Logger.getLogger(ControladorReporteFactura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ControladorReporteFactura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ControladorReporteFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private String obtenerTituloReportePdf()
    {
        String titulo = "Reporte ";
        return titulo+documentoConsultaEnum.getNombre();
        /*if (documentoConsultaEnum.equals(DocumentoEnum.FACTURA)) {
            titulo += "Facturas";
        } else if (documentoConsultaEnum.equals(DocumentoEnum.NOTA_CREDITO)) {
            titulo += "Notas de Crédito";
        }else if (documentoConsultaEnum.equals(DocumentoEnum.NOTA_VENTA_INTERNA)) {
            titulo += "Notas de Venta Interna";
        }*/
        //return titulo;
    }
    
    public File obtenerArchivoReportePdf(InterfazComunicacionPanel panelPadre)
    {
        String titulo = obtenerTituloReportePdf();
        InputStream path=getReporte();
        //ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Documentos ", OrientacionReporteEnum.HORIZONTAL);
        String nombreArchivo=UtilidadesArchivos.generarNombreArchivoUnico("reporte","pdf");
        String pathGrabar="tmp\\"+nombreArchivo; //TODO: Camabiar por algun nombre en funcion de la fecha para que se unico y no genere problemas
        
        ReporteCodefac.generarReporteInternalFramePlantillaArchivo(path, mapParametrosReportePdf(), data, panelPadre,titulo, OrientacionReporteEnum.HORIZONTAL,FormatoHojaEnum.A4,pathGrabar);
        File file=new File(pathGrabar);
        if(file.exists())
        {
            return file;
        }
        return null;
    }
    
    
    public void obtenerReportePdf(InterfazComunicacionPanel panelPadre)
    {
        String titulo = obtenerTituloReportePdf();
        InputStream path=getReporte();
        //ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Documentos ", OrientacionReporteEnum.HORIZONTAL);
        //String nombreArchivo=UtilidadesArchivos.generarNombreArchivoUnico("reporte","pdf");
        //String pathGrabar="\\tmp\\"+nombreArchivo; //TODO: Camabiar por algun nombre en funcion de la fecha para que se unico y no genere problemas
        
        ReporteCodefac.generarReporteInternalFramePlantilla(path, mapParametrosReportePdf(), data, panelPadre,titulo, OrientacionReporteEnum.HORIZONTAL,FormatoHojaEnum.A4);
        
        
    }
    
    public void obtenerReportePdfAgrupadoPorPuntosEstablecimiento(InterfazComunicacionPanel panelPadre)
    {
        String titulo = "Reporte Ventas Agrupado por Punto de Emisión";
        InputStream path=getReportePuntosEmision();
        //ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Documentos ", OrientacionReporteEnum.HORIZONTAL);
        //String nombreArchivo=UtilidadesArchivos.generarNombreArchivoUnico("reporte","pdf");
        //String pathGrabar="\\tmp\\"+nombreArchivo; //TODO: Camabiar por algun nombre en funcion de la fecha para que se unico y no genere problemas
        
        ordenarListaPorPuntoEmision(data);
        ReporteCodefac.generarReporteInternalFramePlantilla(path, mapParametrosReportePdf(), data, panelPadre,titulo, OrientacionReporteEnum.HORIZONTAL,FormatoHojaEnum.A4);
        
        
    }
    
    public void obtenerReporteAgrupadoPorProducto(InterfazComunicacionPanel panelPadre)
    {
        String titulo = "Ventas Agrupado por Producto";
        InputStream path=getReportePorProductos();
        ordenarListaPorProducto(data);
        
        /*for (ReporteFacturaData report : data) {
            System.out.println("claveAcceso="+report.getClaveAcceso()+" , cantidad="+report.getCantidad());
        }*/
        ReporteCodefac.generarReporteInternalFramePlantilla(path, mapParametrosReportePdf(), data, panelPadre,titulo, OrientacionReporteEnum.HORIZONTAL,FormatoHojaEnum.A4);
    }
    
    public void obtenerReporteAgrupadoPorProductoCategoria(InterfazComunicacionPanel panelPadre)
    {
        String titulo = "Ventas Agrupado por Categoria";
        InputStream path=getReportePorCategorias();
        ordenarListaPorProductoCategoria(data);
        ReporteCodefac.generarReporteInternalFramePlantilla(path, mapParametrosReportePdf(), data, panelPadre,titulo, OrientacionReporteEnum.HORIZONTAL,FormatoHojaEnum.A4);
    }
    
    public void obtenerReporteAgrupadoPorPrecio(InterfazComunicacionPanel panelPadre)
    {
        String titulo = "Ventas Agrupado por Precio";
        InputStream path=getReportePorPrecios();
        ordenarListaPorPrecio(data);
        ReporteCodefac.generarReporteInternalFramePlantilla(path, mapParametrosReportePdf(), data, panelPadre,titulo, OrientacionReporteEnum.HORIZONTAL,FormatoHojaEnum.A4);
    }
    
    public void obtenerReporteAgrupadoPorFormasDePago(InterfazComunicacionPanel panelPadre)
    {
        String titulo = "Ventas Agrupado por Formas de Pago";
        InputStream path=getReportePorPrecios();
        ordenarListaPorPrecio(data);
        ReporteCodefac.generarReporteInternalFramePlantilla(path, mapParametrosReportePdf(), data, panelPadre,titulo, OrientacionReporteEnum.HORIZONTAL,FormatoHojaEnum.A4);
    }
    
    public void obtenerReporteAgrupado(InterfazComunicacionPanel panelPadre,TipoReporteEnum tipoReporteEnum)
    {
        String titulo = "Ventas "+tipoReporteEnum.getNombre();
        InputStream path=getReporteAgrupado();
        //List<AgrupadoReporteIf> datosProcesar=(ArrayList<AgrupadoReporteIf>)(ArrayList<?>)data;
        
        //tipoReporteEnum.procesarDatosReporte(datosProcesar);
        UtilidadReporteJasper.obtenerDatosReporteAgrupado(tipoReporteEnum, data);
        
        //ordenarListaPorPrecio(data);
        ReporteCodefac.generarReporteInternalFramePlantilla(path, mapParametrosReportePdf(), data, panelPadre,titulo, OrientacionReporteEnum.HORIZONTAL,FormatoHojaEnum.A4);
    }
    
    
    public void obtenerReporteAgrupadoPorVendedor(InterfazComunicacionPanel panelPadre)
    {
        String titulo = "Reporte Ventas Agrupado por Vendedores";
        InputStream path=getReportePorVendedores();
        ordenarListaPorVendedores(data);
        ReporteCodefac.generarReporteInternalFramePlantilla(path, mapParametrosReportePdf(), data, panelPadre,titulo, OrientacionReporteEnum.HORIZONTAL,FormatoHojaEnum.A4);
    }
    
    private void ordenarListaPorVendedores(List<ReporteFacturaData> reporteData)
    {
        Collections.sort(reporteData,new Comparator<ReporteFacturaData>(){
            public int compare(ReporteFacturaData obj1,ReporteFacturaData  obj2)
                {
                    return obj1.getVendedor().compareTo(obj2.getVendedor());
                }
        });
        
        //Collections.sort(new Comparator<>);
    }
    
    /**
     * Metodo que permite ordenar la lista de resultados por productos
     * @param reporteData 
     */
    private void ordenarListaPorProducto(List<ReporteFacturaData> reporteData)
    {
        Collections.sort(reporteData,new Comparator<ReporteFacturaData>(){
            public int compare(ReporteFacturaData obj1, ReporteFacturaData obj2) 
            {
                int comparacion=obj1.getNombreProducto().compareTo(obj2.getNombreProducto());
                if(comparacion==0)
                {
                    comparacion=obj1.getCodigoProducto().compareTo(obj2.getCodigoProducto());
                }
                return comparacion;
            }
        });
    }
    
    private void ordenarListaPorProductoCategoria(List<ReporteFacturaData> reporteData)
    {
        Collections.sort(reporteData,new Comparator<ReporteFacturaData>(){
            public int compare(ReporteFacturaData obj1, ReporteFacturaData obj2) 
            {
                //String categoriaProducto1=obj1.getCategoria().cop
                return obj1.getCategoria().compareTo(obj2.getCategoria());
                //if(comparacion==0)
                //{
                //    comparacion=obj1.getCodigoProducto().compareTo(obj2.getCodigoProducto());
                //}
                //return comparacion;
            }
        });
    }
    
    private void ordenarListaPorPrecio(List<ReporteFacturaData> reporteData)
    {
        Collections.sort(reporteData,new Comparator<ReporteFacturaData>(){
            public int compare(ReporteFacturaData obj1, ReporteFacturaData obj2) 
            {
                return obj1.getPrecioUnitarioReporte().compareTo(obj2.getPrecioUnitarioReporte());
            }
        });
    }
    
    
    /**
     * Metodo que me permite organizar la lista por 
     * @param reporteData 
     */
    private void ordenarListaPorPuntoEmision(List<ReporteFacturaData> reporteData)
    {
        Collections.sort(reporteData,new Comparator<ReporteFacturaData>(){
            public int compare(ReporteFacturaData obj1,ReporteFacturaData  obj2)
                {
                    return obj1.getPuntoEmision().compareTo(obj2.getPuntoEmision());
                }
        });
        
        //Collections.sort(new Comparator<>);
    }
    
    protected InputStream getReporte()
    {
        return RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("reporte_documentos.jrxml");
    }
    
    protected InputStream getReportePuntosEmision()
    {
        return RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("reporte_ventas_punto_emision.jrxml");
    }
    
    protected InputStream getReportePorProductos()
    {//reporte_factura_por_producto
        return RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("reporte_factura_por_producto.jrxml");
    }
    
    protected InputStream getReportePorCategorias()
    {//reporte_factura_por_producto
        return RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("reporte_factura_por_categoria.jrxml");
    }
    
    protected InputStream getReportePorVendedores()
    {
        return RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("reporte_ventas_por_vendedor.jrxml");
    }
   
    protected InputStream getReportePorPrecios()
    {//reporte_factura_por_producto
        return RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("reporte_factura_por_precios.jrxml");
    }
    
    protected InputStream getReporteAgrupado()
    {//reporte_factura_por_producto
        return RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("reporte_factura_agrupado_generico.jrxml");
    }

    public List<ReporteFacturaData> getData() {
        return data;
    }

    /*
    public Map<String, BigDecimal> getMapTotales() {
        return mapTotales;
    }*/
    
    public Map<String,Object> mapParametrosReportePdf()
    {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("fechainicio", (fechaInicio != null) ? dateFormat.format(fechaInicio) : "");
        parameters.put("fechafin", (fechaFin != null) ? dateFormat.format(fechaFin) : "");
        parameters.put("tipodocumento", documentoConsultaEnum.toString());
        parameters.put("cliente", (persona!= null) ? persona.getPersona().getRazonSocial():"");
        parameters.put("estadofactura", estadoFactura.getNombre());
        parameters.put("afectaNotaCredito",(documentoConsultaEnum.equals(documentoConsultaEnum.NOTA_CREDITO))?false:afectarNotaCredito);//Si el documento es nota de credito siempre mando descativado
        
        if(estadoFactura.equals(ComprobanteEntity.ComprobanteEnumEstado.TODOS_SRI))
        {
            parameters.putAll(totalAnulados.buildMap(EtiquetaReporteEnum.ANULADOS));
        }
        
       if(
               documentoConsultaEnum.equals(documentoConsultaEnum.FACTURA) || 
               documentoConsultaEnum.equals(documentoConsultaEnum.NOTA_VENTA) || 
               documentoConsultaEnum.equals(documentoConsultaEnum.NOTA_VENTA_INTERNA) ||
               documentoConsultaEnum.equals(documentoConsultaEnum.LIQUIDACION_COMPRA))
       {
           
           TotalSumatoria totalSinNotasCredito=total.subtract(totalNotasCredito);
           TotalSumatoria totalSinNotasCreditoYAnulados=totalSinNotasCredito.subtract(totalAnulados);
           parameters.putAll(total.buildMap(EtiquetaReporteEnum.TOTAL));
           parameters.putAll(totalSinNotasCreditoYAnulados.buildMap(EtiquetaReporteEnum.NOTA_CREDITO));
           
       }
       else if(documentoConsultaEnum.equals(documentoConsultaEnum.NOTA_CREDITO))
       {
           TotalSumatoria totalSinAnulados=totalNotasCredito.subtract(totalAnulados);
           parameters.putAll(totalSinAnulados.buildMap(EtiquetaReporteEnum.TOTAL));
       }
        
                
        return parameters;
    }
    
    public TotalSumatoria totalSinNotaCredito()
    {
        return total.subtract(totalNotasCredito);
    }
    

    
    public class TotalSumatoria
    {
        private BigDecimal subtotalSinImpuesto;
        private BigDecimal subtotalConImpuesto;
        private BigDecimal descuentoSinImpuesto;
        private BigDecimal descuentoconImpuesto;
        private BigDecimal valorImpuesto; //Valor del Iva

        public TotalSumatoria() {
            this.subtotalSinImpuesto = BigDecimal.ZERO;
            this.subtotalConImpuesto = BigDecimal.ZERO;
            this.descuentoSinImpuesto = BigDecimal.ZERO;
            this.descuentoconImpuesto = BigDecimal.ZERO;
            this.valorImpuesto=BigDecimal.ZERO;
        }
        
        

        public TotalSumatoria(BigDecimal subtotalSinImpuesto, BigDecimal subtotalConImpuesto, BigDecimal descuentoSinImpuesto, BigDecimal descuentoconImpuesto) {
            this.subtotalSinImpuesto = subtotalSinImpuesto;
            this.subtotalConImpuesto = subtotalConImpuesto;
            this.descuentoSinImpuesto = descuentoSinImpuesto;
            this.descuentoconImpuesto = descuentoconImpuesto;
        }
        
        public TotalSumatoria add(TotalSumatoria total)
        {
            TotalSumatoria totalTmp=new TotalSumatoria();
            totalTmp.descuentoSinImpuesto=this.descuentoSinImpuesto.add(total.descuentoSinImpuesto);
            totalTmp.descuentoconImpuesto=this.descuentoconImpuesto.add(total.descuentoconImpuesto);
            totalTmp.subtotalConImpuesto=this.subtotalConImpuesto.add(total.subtotalConImpuesto);
            totalTmp.subtotalSinImpuesto=this.subtotalSinImpuesto.add(total.subtotalSinImpuesto);
            totalTmp.valorImpuesto=this.valorImpuesto.add(total.valorImpuesto);
            return totalTmp;
        }
        
        public TotalSumatoria subtract(TotalSumatoria total)
        {
            TotalSumatoria totalTmp=new TotalSumatoria();
            totalTmp.descuentoSinImpuesto=this.descuentoSinImpuesto.subtract(total.descuentoSinImpuesto);
            totalTmp.descuentoconImpuesto=this.descuentoconImpuesto.subtract(total.descuentoconImpuesto);
            totalTmp.subtotalConImpuesto=this.subtotalConImpuesto.subtract(total.subtotalConImpuesto);
            totalTmp.subtotalSinImpuesto=this.subtotalSinImpuesto.subtract(total.subtotalSinImpuesto);
            totalTmp.valorImpuesto=this.valorImpuesto.subtract(total.valorImpuesto);
            return totalTmp;
        }

        public BigDecimal getSubtotalSinImpuesto() {
            return subtotalSinImpuesto;
        }

        public BigDecimal getSubtotalConImpuesto() {
            return subtotalConImpuesto;
        }
        
        public BigDecimal getSubtotalSinImpuestoMenosDescuento() {
            return subtotalSinImpuesto.subtract(descuentoSinImpuesto);
        }

        public BigDecimal getSubtotalConImpuestoMenosDescuento() {
            return subtotalConImpuesto.subtract(descuentoconImpuesto);
        }

        public BigDecimal getDescuentoSinImpuesto() {
            return descuentoSinImpuesto;
        }

        public BigDecimal getDescuentoconImpuesto() {
            return descuentoconImpuesto;
        }

        public BigDecimal getValorImpuesto() {
            return valorImpuesto;
        }
        
        
              
        public BigDecimal obtenerSubtotal()
        {
            return getSubtotalConImpuestoMenosDescuento().add(getSubtotalSinImpuestoMenosDescuento());
        }
        
        public BigDecimal obtenerTotal()
        {
            return obtenerSubtotal().add(valorImpuesto);
        }
        
        public BigDecimal obtenerTotalDescuentos()
        {
            return descuentoSinImpuesto.add(descuentoconImpuesto);
        }

        public void addSubtotalSinImpuesto(BigDecimal subtotalSinImpuesto) {
            if(subtotalSinImpuesto!=null)
                this.subtotalSinImpuesto=this.subtotalSinImpuesto.add(subtotalSinImpuesto);
        }

        public void addSubtotalConImpuesto(BigDecimal subtotalConImpuesto) {
            if(subtotalConImpuesto!=null)
                this.subtotalConImpuesto=this.subtotalConImpuesto.add(subtotalConImpuesto);
        }

        public void addDescuentoSinImpuesto(BigDecimal descuentoSinImpuesto) {
            if(descuentoSinImpuesto!=null)
                this.descuentoSinImpuesto=this.descuentoSinImpuesto.add(descuentoSinImpuesto);
        }

        public void addDescuentoconImpuesto(BigDecimal descuentoconImpuesto) {
            if(descuentoconImpuesto!=null)
                this.descuentoconImpuesto=this.descuentoconImpuesto.add(descuentoconImpuesto);
        }

        public void addsValorImpuesto(BigDecimal valorImpuesto) {
            if(valorImpuesto!=null)
                this.valorImpuesto=this.valorImpuesto.add(valorImpuesto);
        }
        
        public Map<String,Object> buildMap(EtiquetaReporteEnum nombreEnum)
        {
            Map<String,Object> parametros=new HashMap<String,Object>();
            parametros.put(nombreEnum.obtenerNombreEtiqueta("subtotal"),getSubtotalSinImpuestoMenosDescuento().toString());
            parametros.put(nombreEnum.obtenerNombreEtiqueta("subtotaliva"),getSubtotalConImpuestoMenosDescuento().toString());
            parametros.put(nombreEnum.obtenerNombreEtiqueta("valoriva"),valorImpuesto.toString());
            parametros.put(nombreEnum.obtenerNombreEtiqueta("total"),obtenerTotal().toString());
            parametros.put(nombreEnum.obtenerNombreEtiqueta("totalsubtotales"),obtenerSubtotal().toString());
            parametros.put(nombreEnum.obtenerNombreEtiqueta("descuentos"),obtenerTotalDescuentos().toString());
            
            return parametros;
        }
    
    }
    
    public enum EtiquetaReporteEnum {
        ANULADOS("Anulado"),
        NOTA_CREDITO("NC"),
        TOTAL("");

        private EtiquetaReporteEnum(String prefijo) {
            this.prefijo = prefijo;
        }

        private String prefijo;

        public String obtenerNombreEtiqueta(String nombre) {
            return nombre + prefijo;
        }

    };

    public void setPersona(PersonaEstablecimiento persona) {
        this.persona = persona;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setEstadoFactura(ComprobanteEntity.ComprobanteEnumEstado estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    public void setFiltrarReferidos(Boolean filtrarReferidos) {
        this.filtrarReferidos = filtrarReferidos;
    }

    public void setReferido(Persona referido) {
        this.referido = referido;
    }

    public void setReporteAgrupado(Boolean reporteAgrupado) {
        this.reporteAgrupado = reporteAgrupado;
    }

    public void setAfectarNotaCredito(Boolean afectarNotaCredito) {
        this.afectarNotaCredito = afectarNotaCredito;
    }

    public void setDocumentoConsultaEnum(DocumentoEnum documentoConsultaEnum) {
        this.documentoConsultaEnum = documentoConsultaEnum;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setData(List<ReporteFacturaData> data) {
        this.data = data;
    }

    public void setTotal(TotalSumatoria total) {
        this.total = total;
    }

    public void setTotalAnulados(TotalSumatoria totalAnulados) {
        this.totalAnulados = totalAnulados;
    }

    public void setTotalNotasCredito(TotalSumatoria totalNotasCredito) {
        this.totalNotasCredito = totalNotasCredito;
    }

    public PuntoEmision getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(PuntoEmision puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Boolean getReporteConDetallesFactura() {
        return reporteConDetallesFactura;
    }

    public void setReporteConDetallesFactura(Boolean reporteConDetallesFactura) {
        this.reporteConDetallesFactura = reporteConDetallesFactura;
    }

    public List<Factura> getDatafact() {
        return datafact;
    }

    public void setDatafact(List<Factura> datafact) {
        this.datafact = datafact;
    }

    public Boolean getAgregarCostos() {
        return agregarCostos;
    }

    public void setAgregarCostos(Boolean agregarCostos) {
        this.agregarCostos = agregarCostos;
    }
    
    
    public enum TipoReporteEnum implements EnumReporteAgruparIf
    {
        NORMAL("Normal",null),
        AGRUPADO_POR_VENDEDOR("Agrupado por vendedor",new CampoAgruparIf() {
            @Override
            public String obtenerCampoAgrupar(AgrupadoReporteIf dato) {
                return dato.getValorCampoAgrupar(TipoReporteEnum.AGRUPADO_POR_VENDEDOR).toString();
                //return dato.getVendedor();
            }
        }),
        
        AGRUPADO_POR_PUNTO_EMISION("Agrupado por punto de emisión",new CampoAgruparIf() {
            @Override
            public String obtenerCampoAgrupar(AgrupadoReporteIf dato) {
                return dato.getValorCampoAgrupar(TipoReporteEnum.AGRUPADO_POR_PUNTO_EMISION).toString();
                //return dato.getPuntoEmision();
            }
        }),
        
        AGRUPADO_POR_PRODUCTO("Agrupado por producto",new CampoAgruparIf() {
            @Override
            public String obtenerCampoAgrupar(AgrupadoReporteIf dato) {
                return dato.getValorCampoAgrupar(TipoReporteEnum.AGRUPADO_POR_PRODUCTO).toString();
                //return dato.getNombreProducto();
            }
        }),
        
        AGRUPADO_POR_CATEGORIA("Agrupado por categoria",new CampoAgruparIf() {
            @Override
            public String obtenerCampoAgrupar(AgrupadoReporteIf dato) {
                return dato.getValorCampoAgrupar(TipoReporteEnum.AGRUPADO_POR_CATEGORIA).toString();
                //return dato.getCategoria();
            }
        }),
        
        AGRUPADO_POR_FORMA_PAGO("Agrupado por forma de pago",new CampoAgruparIf() {
            @Override
            public String obtenerCampoAgrupar(AgrupadoReporteIf dato) {
                return dato.getValorCampoAgrupar(TipoReporteEnum.AGRUPADO_POR_FORMA_PAGO).toString();
                //return dato.getFormaPago();
            }
        }),
        
        AGRUPADO_POR_VALOR("Agrupado por valores",new CampoAgruparIf() {
            @Override
            public String obtenerCampoAgrupar(AgrupadoReporteIf dato) {
                return dato.getValorCampoAgrupar(TipoReporteEnum.AGRUPADO_POR_VALOR).toString();
                //return dato.getPrecioUnitarioReporte();
            }
        }),
        
        AGRUPADO_POR_RUTA("Agrupado por ruta",new CampoAgruparIf() {
            @Override
            public String obtenerCampoAgrupar(AgrupadoReporteIf dato) {
                return dato.getValorCampoAgrupar(TipoReporteEnum.AGRUPADO_POR_RUTA).toString();
                //return dato.getRuta();
            }
        }),
        
        AGRUPADO_POR_ZONA("Agrupado por zonas",new CampoAgruparIf() {
            @Override
            public String obtenerCampoAgrupar(AgrupadoReporteIf dato) {
                return dato.getValorCampoAgrupar(TipoReporteEnum.AGRUPADO_POR_ZONA).toString();
                //return dato.getZona();
            }
        });
        
        
        
        
        private String nombre;
        private CampoAgruparIf campoAgruparIf;
        

        private TipoReporteEnum(String nombre,CampoAgruparIf campoAgruparIf) {
            this.nombre = nombre;
            this.campoAgruparIf=campoAgruparIf;
        }
        

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
        @Override
        public CampoAgruparIf getCampoAgrupar() {
            return campoAgruparIf;
        }
       
        @Override
        public String toString() {
            return nombre;
        }
        
        public static TipoReporteEnum getEnum(String nombre) {

            for (TipoReporteEnum enumerador : TipoReporteEnum.values()) {
                if(enumerador.nombre.equals(nombre)){
                    return enumerador;
                }
            }
            return null;
        }
        
        /**
         * Metodo principal que me permite llenar el dato de FILTRO
         * y tambien ordenar los datos de acuerdo al parametro para AGRUPAR
         */
        /*public void procesarDatosReporte(List<AgrupadoReporteIf> reporteData)
        {
            llenarDatoAgrupacion(reporteData);
            ordenarAgrupar(reporteData);
            
        }*/
        
        
        /**
         * Metodo que me permite agrupar los datos antes de poder procesar para el reporte
         * @param reporteData 
         */
        /*public void ordenarAgrupar(List<AgrupadoReporteIf> reporteData)
        {            
            Collections.sort(reporteData,new Comparator<AgrupadoReporteIf>(){
                public int compare(AgrupadoReporteIf obj1, AgrupadoReporteIf obj2) 
                {                    
                    return obj1.getCampoAgrupado().compareTo(obj2.getCampoAgrupado());
                }
             });
        }
        
        public void llenarDatoAgrupacion(List<AgrupadoReporteIf> agrupadoReporteIf)
        {
            for (AgrupadoReporteIf reporteFacturaData : agrupadoReporteIf) 
            {
                String datoAgrupar=campoAgruparIf.obtenerCampoAgrupar((ReporteFacturaData) reporteFacturaData);
                reporteFacturaData.setCampoAgrupado(datoAgrupar);
            }
        }*/
                        
    }
    

    
    
}
