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
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.sri.SriSustentoComprobanteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraServiceIf;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ControladorReporteCompra {

    private Persona proveedor;
    private Date fechaInicio;
    private Date fechaFinal;
    private DocumentoEnum documentoEnum;
    private TipoDocumentoEnum tipoDocumentoEnum;
    private GeneralEnumEstado estadoEnum;
    private List<Compra> compras;
    /**
     * Variable que me permite sabes si busco por un proveedor o todos los
     * proveedores
     * Todo: Ver si eleiminar esta varaible porque bastaria con setear null la variable de proveedor
     */
    private boolean banderaBusqueda;
    private Empresa empresa;

    //Variables que me permiten realizar la sumatoria de los totales de cada compra
    private BigDecimal subtotal = BigDecimal.ZERO;
    private BigDecimal subtotal0 = BigDecimal.ZERO;
    private BigDecimal subtotal12 = BigDecimal.ZERO;
    private BigDecimal descuento0 = BigDecimal.ZERO;
    private BigDecimal descuento12 = BigDecimal.ZERO;
    private BigDecimal descuento = BigDecimal.ZERO;
    private BigDecimal iva = BigDecimal.ZERO;
    private BigDecimal total = BigDecimal.ZERO;

    public ControladorReporteCompra(Persona proveedor, Date fechaInicio, Date fechaFinal, DocumentoEnum documentoEnum, TipoDocumentoEnum tipoDocumentoEnum, GeneralEnumEstado estadoEnum, boolean banderaBusqueda,Empresa empresa) {
        this.proveedor = proveedor;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.documentoEnum = documentoEnum;
        this.tipoDocumentoEnum = tipoDocumentoEnum;
        this.estadoEnum = estadoEnum;
        this.banderaBusqueda = banderaBusqueda;
        this.empresa=empresa;
    }

    public void generarReporte() {
        try {
            encerarValoresTotales();
            CompraServiceIf compraServiceIf = ServiceFactory.getFactory().getCompraServiceIf();
            compras = compraServiceIf.obtenerCompraReporte(proveedor, fechaInicio, fechaFinal, documentoEnum, tipoDocumentoEnum, estadoEnum,empresa);
            sumarTotalesComprasIndividuales(compras);

        } catch (RemoteException ex) {
            Logger.getLogger(ControladorReporteCompra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ControladorReporteCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Compra> getCompras() {
        return compras;
    }
    
    /**
     * Todo: Unir con el reporte normal de pdf para reutilizar los componentes
     * @param panelPadre
     * @return 
     */
    public File reporteCompraPdfGetFile(InterfazComunicacionPanel panelPadre) {
        InputStream path = RecursoCodefac.JASPER_COMPRA.getResourceInputStream("reporte_compra.jrxml");
        
        String nombreArchivo=UtilidadesArchivos.generarNombreArchivoUnico("reporteCompra","pdf");
        String pathGrabar="tmp\\"+nombreArchivo; 
        ReporteCodefac.generarReporteInternalFramePlantillaArchivo(path, getParametros(), compraDataReportes(compras), panelPadre, "Reporte Compra",OrientacionReporteEnum.VERTICAL,FormatoHojaEnum.A4,pathGrabar);
        File file=new File(pathGrabar);
        if(file.exists())
        {
            return file;
        }
        return null;
    }

    public void reporteCompraPdf(InterfazComunicacionPanel panelPadre) {
        InputStream path = RecursoCodefac.JASPER_COMPRA.getResourceInputStream("reporte_compra.jrxml");
        ReporteCodefac.generarReporteInternalFramePlantilla(path, getParametros(), compraDataReportes(compras), panelPadre, "Reporte Compra");
    }
    
    public void reporteCompraAgrupadaCategoriaPdf(InterfazComunicacionPanel panelPadre)
    {
        InputStream path = RecursoCodefac.JASPER_COMPRA.getResourceInputStream("reporte_compra_agrupado_categoria.jrxml");
        ReporteCodefac.generarReporteInternalFramePlantilla(path, getParametros(), construirDataAgrupado(compras), panelPadre, "Reporte Compra Agrupado");
    }
    
    public void reporteCompraAgrupadaSustentoSriPdf(InterfazComunicacionPanel panelPadre)
    {
        InputStream path = RecursoCodefac.JASPER_COMPRA.getResourceInputStream("reporte_compra_agrupado_sustento_sri.jrxml");
        ReporteCodefac.generarReporteInternalFramePlantilla(path, getParametros(), construirDataAgrupadoSri(compras), panelPadre, "Reporte Compra Sustento Sri");
    }
    
    //Todo:Reutilizar el otro metodo de generar el excel
    public File reporteCompraExcelGetFile() {
        try {
            Excel excel = new Excel();
            String[] nombreCabeceras = {"Preimpreso","Documento","Autorización", "Identificación", "Nombre", "Fecha", "Subtotal12", "Sutotal0", "Descuento","IVA","Total"};
            excel.gestionarIngresoInformacionExcel(nombreCabeceras, compraDataReportes(compras));
            return excel.obtenerArchivo();
        } catch (IOException ex) {
            Logger.getLogger(ControladorReporteCompra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ControladorReporteCompra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ControladorReporteCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void reporteCompraExcel() {
        try {
            Excel excel = new Excel();
            String[] nombreCabeceras = {"Preimpreso","Documento","Autorización", "Identificación", "Nombre", "Fecha", "Subtotal12", "Sutotal0", "Descuento","IVA","Total"};
            excel.gestionarIngresoInformacionExcel(nombreCabeceras, compraDataReportes(compras));
            excel.abrirDocumento();
        } catch (IOException ex) {
            Logger.getLogger(ControladorReporteCompra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ControladorReporteCompra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ControladorReporteCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void reporteCompraAgrupadaCategoriaExcel()
    {
        try {
            Excel excel = new Excel();
            String[] nombreCabeceras = new String[] {"Categoria","Descripción Compra","Descripción detalle","Producto","Compra","Sustento Sri","Fecha","Subtotal12", "Sutotal0", "Descuento","IVA","Total"};
            excel.gestionarIngresoInformacionExcel(nombreCabeceras,construirDataAgrupado(compras));
            excel.abrirDocumento();
        } catch (IOException ex) {
            Logger.getLogger(ControladorReporteCompra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ControladorReporteCompra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ControladorReporteCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<CompraAgrupadoCategoriaData> construirDataAgrupadoSri(List<Compra> compras) {
        
        Map<SriSustentoComprobanteEnum, List<CompraDetalle>> mapAgrupado = new HashMap<SriSustentoComprobanteEnum, List<CompraDetalle>>();
        
        for (Compra compra : compras) {            
            for (CompraDetalle detalle : compra.getDetalles()) {
                
                //Si no existe un valor del sustento obtengo de la compra , esto para tener compatiblidad con los cambios anteriores
                SriSustentoComprobanteEnum sustentoSri=null;
                if(detalle.getCodigoSustentoSriEnum()==null)
                {
                    sustentoSri=compra.getCodigoSustentoSriEnum();
                }
                else
                {
                    sustentoSri=detalle.getCodigoSustentoSriEnum();
                }   
                
                List<CompraDetalle> detalles = mapAgrupado.get(sustentoSri);
                if (detalles == null) {
                    detalles= new ArrayList<CompraDetalle>();
                    detalles.add(detalle);
                    mapAgrupado.put(sustentoSri, detalles);
                } else {
                    detalles.add(detalle);
                }
                
            }
        }
        
        List<CompraAgrupadoCategoriaData> resultado = new ArrayList<CompraAgrupadoCategoriaData>();
        for (Map.Entry<SriSustentoComprobanteEnum, List<CompraDetalle>> entry : mapAgrupado.entrySet()) {
            SriSustentoComprobanteEnum sustentoSri = entry.getKey();
            List<CompraDetalle> detalle = entry.getValue();
            
            for (CompraDetalle compraDetalle : detalle) {
                resultado.add(crearCategoriaData(compraDetalle));
            }
        }
        return resultado;
         
    }
    
    public List<CompraAgrupadoCategoriaData> construirDataAgrupado(List<Compra> compras) {
        Map<CategoriaProducto, List<CompraDetalle>> mapAgrupadoCategoria = new HashMap<CategoriaProducto, List<CompraDetalle>>();

        //List<CompraAgrupadoCategoriaData> resultado=new ArrayList<CompraAgrupadoCategoriaData>();
        //Agrupar todos los detalles segun el tipo de categoria en un map
        for (Compra compra : compras) {
            for (CompraDetalle compraDetalle : compra.getDetalles()) {
                CategoriaProducto categoria = compraDetalle.getProductoProveedor().getProducto().getCatalogoProducto().getCategoriaProducto();
                List<CompraDetalle> detalles = mapAgrupadoCategoria.get(categoria);
                if (detalles == null) {
                    detalles= new ArrayList<CompraDetalle>();
                    detalles.add(compraDetalle);
                    mapAgrupadoCategoria.put(categoria, detalles);
                } else {
                    detalles.add(compraDetalle);
                }
            }
        }

        //Crear la nueva lista para el reporte con el map clasificado
        List<CompraAgrupadoCategoriaData> resultado = new ArrayList<CompraAgrupadoCategoriaData>();
        for (Map.Entry<CategoriaProducto, List<CompraDetalle>> entry : mapAgrupadoCategoria.entrySet()) {
            CategoriaProducto categoria = entry.getKey();
            List<CompraDetalle> detallesCompra = entry.getValue();

            for (CompraDetalle compraDetalle : detallesCompra) {

                /*CompraAgrupadoCategoriaData compraAgrupadoData = new CompraAgrupadoCategoriaData();
                compraAgrupadoData.setCategoria(categoria.getNombre());
                compraAgrupadoData.setProducto(compraDetalle.getProductoProveedor().getProducto().getNombre());
                compraAgrupadoData.setCompra(compraDetalle.getCompra().getPreimpreso());
                compraAgrupadoData.setFecha(compraDetalle.getCompra().getFechaFactura().toString());
                compraAgrupadoData.setIva((compraDetalle.getIva()!=null)?compraDetalle.getIva():BigDecimal.ZERO);
                compraAgrupadoData.setSubtotalDescuento(compraDetalle.getDescuento());
                compraAgrupadoData.setTotal((compraDetalle.getTotalCalculado()!=null)?compraDetalle.getTotalCalculado():BigDecimal.ZERO);
                
                if(compraDetalle.isImpuestoIvaCero())
                {
                    compraAgrupadoData.setSubtotalCero((compraDetalle.getSubtotal()!=null)?compraDetalle.getSubtotal():BigDecimal.ZERO);
                    compraAgrupadoData.setSubtotalDoce(BigDecimal.ZERO);
                }
                else
                {
                    compraAgrupadoData.setSubtotalCero(BigDecimal.ZERO);
                    compraAgrupadoData.setSubtotalDoce((compraDetalle.getSubtotal()!=null)?compraDetalle.getSubtotal():BigDecimal.ZERO);
                }*/
                
                //resultado.add(compraAgrupadoData);
                resultado.add(crearCategoriaData(compraDetalle));
            }

        }

        return resultado;
    }
    
    private CompraAgrupadoCategoriaData crearCategoriaData(CompraDetalle compraDetalle) {
        CompraAgrupadoCategoriaData compraAgrupadoData = new CompraAgrupadoCategoriaData();
        
        CategoriaProducto categoria=compraDetalle.getProductoProveedor().getProducto().getCatalogoProducto().getCategoriaProducto();
        compraAgrupadoData.setCategoria((categoria!=null)?categoria.getNombre():"");
        
        SriSustentoComprobanteEnum sustentoSriEnum=compraDetalle.getCodigoSustentoSriEnum();
        compraAgrupadoData.setSustentoSri((sustentoSriEnum!=null)?sustentoSriEnum.getDescripcionCorta():"");
        compraAgrupadoData.setDescripcionCompra(compraDetalle.getCompra().getObservacion());
        compraAgrupadoData.setDescripcionDetalle(compraDetalle.getDescripcion());
        
        compraAgrupadoData.setProducto(compraDetalle.getProductoProveedor().getProducto().getNombre());
        compraAgrupadoData.setCompra(compraDetalle.getCompra().getPreimpreso());
        compraAgrupadoData.setFecha(compraDetalle.getCompra().getFechaFactura().toString());
        //System.out.println(compraDetalle.getIva());
        //compraAgrupadoData.setIva((compraDetalle.getIva() != null) ? compraDetalle.getIva() : BigDecimal.ZERO);
        if(compraDetalle.getCompra().getSecuencial()==64573)
        {
            System.out.println("revisar ...");
        }
        compraAgrupadoData.setIva((compraDetalle.getIva() != null) ? compraDetalle.obtenerIvaCalculado() : BigDecimal.ZERO);
        
        compraAgrupadoData.setSubtotalDescuento(compraDetalle.getDescuento());
        compraAgrupadoData.setTotal((compraDetalle.getTotalCalculado() != null) ? compraDetalle.getTotalCalculado() : BigDecimal.ZERO);

        if (compraDetalle.isImpuestoIvaCero()) {
            compraAgrupadoData.setSubtotalCero((compraDetalle.getSubtotal() != null) ? compraDetalle.getSubtotal() : BigDecimal.ZERO);
            compraAgrupadoData.setSubtotalDoce(BigDecimal.ZERO);
        } else {
            compraAgrupadoData.setSubtotalCero(BigDecimal.ZERO);
            compraAgrupadoData.setSubtotalDoce((compraDetalle.getSubtotal() != null) ? compraDetalle.getSubtotal() : BigDecimal.ZERO);
        }
        return compraAgrupadoData;
    }

    public void sumarTotalesComprasIndividuales(List<Compra> compras) {
        for (Compra compra : compras) {
            BigDecimal subtotalImpuestos = (compra.getSubtotalImpuestos() != null) ? compra.getSubtotalImpuestos() : BigDecimal.ZERO;
            BigDecimal subtotalSinImpuestos = (compra.getSubtotalSinImpuestos() != null) ? compra.getSubtotalSinImpuestos() : BigDecimal.ZERO;
            BigDecimal descuentoImpuestos = (compra.getDescuentoImpuestos() != null) ? compra.getDescuentoImpuestos() : BigDecimal.ZERO;
            BigDecimal descuentoSinImpuestos = (compra.getDescuentoSinImpuestos() != null) ? compra.getDescuentoSinImpuestos() : BigDecimal.ZERO;
            BigDecimal iva = (compra.getIva() != null) ? compra.getIva() : BigDecimal.ZERO;
            BigDecimal total = (compra.getTotal() != null) ? compra.getTotal() : BigDecimal.ZERO;

            this.subtotal = this.subtotal.add(subtotalImpuestos.add(subtotalSinImpuestos));
            //this.subtotal = sumarValores(compra.getSubtotalImpuestos(), compra.getSubtotalSinImpuestos());
            this.subtotal0 = this.subtotal0.add(subtotalSinImpuestos);
            this.subtotal12 = this.subtotal12.add(subtotalImpuestos);
            this.descuento = this.descuento.add(descuentoImpuestos.add(descuentoSinImpuestos));
//            this.descuento = sumarValores(compra.getDescuentoImpuestos(), compra.getDescuentoSinImpuestos());
            this.descuento0 = this.descuento0.add(descuentoSinImpuestos);
            this.descuento12 = this.descuento12.add(descuentoImpuestos);
            this.iva = this.iva.add(iva);
            this.total = this.total.add(total);
        }
    }

    public Map<String, Object> getParametros() {
        Map parametros = new HashMap();
        if (banderaBusqueda) {
            parametros.put("identificacion", "TODOS");
            parametros.put("nombre", "TODOS");
        } else {
            parametros.put("identificacion", this.proveedor.getIdentificacion() + "");
            parametros.put("nombre", this.proveedor.getRazonSocial() + "");

        }

        //Parametros estaticos que se envian para realizar el Reporte
        parametros.put("tipodocumento",(tipoDocumentoEnum!=null)?tipoDocumentoEnum.getNombre() + "":"Todos");
        parametros.put("documento", (documentoEnum!=null)?documentoEnum.getNombre() + "":"Todos");
        parametros.put("fechainicial",(fechaInicio!=null)?this.fechaInicio + "":"");
        parametros.put("fechafinal",(fechaFinal!=null)?this.fechaFinal + "":"");
        parametros.put("subtotal", this.subtotal + "");
        parametros.put("subtotal12", this.subtotal12 + "");
        parametros.put("subtotal0", this.subtotal0 + "");
        parametros.put("descuento", this.descuento + "");
        parametros.put("iva", this.iva + "");
        parametros.put("total", this.total + "");
        return parametros;
    }
    
    public List<CompraDataReporte> compraDataReportes(List<Compra> compras)
    {
        List<CompraDataReporte> compraDataReportes = new ArrayList<>();
        //Parametros dinámicos que se envian para realizar el Reporte
        for (Compra compra : compras) {
            CompraDataReporte cdr = new CompraDataReporte();
            cdr.setPreimpreso(compra.getPreimpreso());
            cdr.setDocumento(compra.getCodigoDocumentoEnum().getNombre());
            cdr.setAutorizacion(compra.getAutorizacion());
            cdr.setIdentificacion(compra.getProveedor().getIdentificacion());
            cdr.setNombre(compra.getProveedor().getRazonSocial());
            cdr.setFecha(compra.getFechaFactura() + "");
            cdr.setSubtotal(sumarValores(compra.getSubtotalImpuestos(), compra.getSubtotalSinImpuestos()) + "");
            cdr.setSubtotal0((compra.getSubtotalSinImpuestos()!=null)?compra.getSubtotalSinImpuestos().toString():BigDecimal.ZERO.toString());
            cdr.setSubtotal12((compra.getSubtotalImpuestos())!=null?compra.getSubtotalImpuestos().toString():BigDecimal.ZERO.toString());
            cdr.setDescuento(sumarValores(compra.getDescuentoImpuestos(), compra.getDescuentoSinImpuestos()) + "");
            cdr.setDescuento0((compra.getDescuentoSinImpuestos()!=null)?compra.getDescuentoSinImpuestos().toString():BigDecimal.ZERO.toString());
            cdr.setDescuento12((compra.getDescuentoImpuestos()!=null)?compra.getDescuentoImpuestos().toString():BigDecimal.ZERO.toString());
            cdr.setIva((compra.getIva()!=null)?compra.getIva().toString():BigDecimal.ZERO.toString());
            cdr.setTotal((compra.getTotal()!=null)?compra.getTotal().toString():BigDecimal.ZERO.toString());
            compraDataReportes.add(cdr);
        }

        Collections.sort(compraDataReportes, new Comparator<CompraDataReporte>() {
            public int compare(CompraDataReporte obj1, CompraDataReporte obj2) {
                return obj1.getNombre().compareTo(obj2.getNombre());
            }
        });
        return compraDataReportes;
    }
    
    public BigDecimal sumarValores(BigDecimal d1, BigDecimal d2) {
        if (d1 == null) {
            d1 = BigDecimal.ZERO;
        }

        if (d2 == null) {
            d2 = BigDecimal.ZERO;
        }
        return d1.add(d2);
    }
    
    private void encerarValoresTotales() {
        subtotal = BigDecimal.ZERO;
        subtotal0 = BigDecimal.ZERO;
        subtotal12 = BigDecimal.ZERO;
        descuento0 = BigDecimal.ZERO;
        descuento12 = BigDecimal.ZERO;
        descuento = BigDecimal.ZERO;
        iva = BigDecimal.ZERO;
        total = BigDecimal.ZERO;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getSubtotal0() {
        return subtotal0;
    }

    public BigDecimal getSubtotal12() {
        return subtotal12;
    }

    public BigDecimal getDescuento0() {
        return descuento0;
    }

    public BigDecimal getDescuento12() {
        return descuento12;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public BigDecimal getTotal() {
        return total;
    }


    
}
