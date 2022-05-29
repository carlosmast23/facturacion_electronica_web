/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobante.reporte;

import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RetencionServiceIf;
import static ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha.formatDate;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author Carlos
 */
public class ControladorReporteRetencion {
    private Persona proveedor;
    private Date fechaInicio;
    private Date fechaFin;
    private SriRetencionIva sriRetencionIva;
    private SriRetencionRenta sriRetencionRenta;
    private SriRetencion sriRetencion;
    private ComprobanteEntity.ComprobanteEnumEstado estadoEnum;
    private Empresa empresa;
    
    private List<RetencionDetalle> dataretencion;
    private List<ReporteRetencionesData> dataReporte;
    
    private Map<String,SriRetencion> mapTipoRetencion;
    
    private Map<String, BigDecimal> mapSumatoriaRetencionIva;
    private Map<String, BigDecimal> mapSumatoriaRetencionRenta;
    
    public String nombreCabeceras[] = {"Clave de Acceso", "Preimpreso", "Proveedor", "Fecha", "Estado", "Tipo", "Compra", "Base Imponible", "Porcentaje", "Código", "Valor"};
    //public InputStream path=RecursoCodefac.JASPER_COMPRA.getResourceInputStream("reporte_retenciones.jrxml");


    public ControladorReporteRetencion(Persona proveedor, Date fechaInicio, Date fechaFin, SriRetencionIva sriRetencionIva, SriRetencionRenta sriRetencionRenta,SriRetencion sriRetencion,ComprobanteEntity.ComprobanteEnumEstado estadoEnum,Empresa empresa) {
        this.proveedor = proveedor;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.sriRetencionIva = sriRetencionIva;
        this.sriRetencionRenta = sriRetencionRenta;
        this.sriRetencion=sriRetencion;
        this.estadoEnum = estadoEnum;
        this.empresa=empresa;
        cargarMapsTiposRetenciones();
    }
    
    public void generarReporte()
    {
        try {
            RetencionServiceIf fs = ServiceFactory.getFactory().getRetencionServiceIf();
            dataretencion = fs.obtenerRetencionesReportes(proveedor, fechaInicio, fechaFin, sriRetencionIva, sriRetencionRenta, sriRetencion, estadoEnum,empresa);
            dataReporte = new ArrayList<ReporteRetencionesData>();
            for (RetencionDetalle retencion : dataretencion) {
                ReporteRetencionesData data = new ReporteRetencionesData(retencion.getRetencion().getClaveAcceso(),
                        retencion.getRetencion().getPreimpreso(),
                        retencion.getRetencion().getProveedor().getRazonSocial(),
                        retencion.getRetencion().getFechaEmision().toString(),
                        retencion.getRetencion().getEstadoEnum().getNombre(),
                        mapTipoRetencion.get(retencion.getCodigoSri()).getNombre(),
                        retencion.getRetencion().getPreimpresoDocumentoFormato(),
                        retencion.getBaseImponible().toString(),
                        retencion.getPorcentajeRetener().toString() + " %",
                        retencion.getCodigoRetencionSri(),
                        retencion.getValorRetenido().toString());
                

                dataReporte.add(data);
            }
            
            construirMapSumatorias();
            
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorReporteRetencion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void construirMapSumatorias()
    {
        mapSumatoriaRetencionIva=new HashMap<String,BigDecimal>();
        mapSumatoriaRetencionRenta=new HashMap<String,BigDecimal>();
        
        for (RetencionDetalle retencionDetalle : dataretencion) 
        {
            if(estadoEnum.equals(estadoEnum.TODOS_SRI))
            {
                //Solo hacer la sumatoria con las retenciones que estan autorizadas
                if(!retencionDetalle.getRetencion().getEstadoEnum().equals(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO))
                {
                    continue;
                }
            }
            //Sumar para los totales del iva
            if(mapTipoRetencion.get(retencionDetalle.getCodigoSri()).getNombre().equals("IVA"))
            {
                if(mapSumatoriaRetencionIva.get(retencionDetalle.getCodigoRetencionSri())==null)
                {
                    BigDecimal valor=retencionDetalle.getValorRetenido();
                    mapSumatoriaRetencionIva.put(retencionDetalle.getCodigoRetencionSri(),valor);
                }
                else
                {
                    BigDecimal valor=mapSumatoriaRetencionIva.get(retencionDetalle.getCodigoRetencionSri());
                    valor=valor.add(retencionDetalle.getValorRetenido());
                    mapSumatoriaRetencionIva.put(retencionDetalle.getCodigoRetencionSri(),valor);
                }
            }
            else
            {
                //Sumar para los totales de la renta
                if(mapTipoRetencion.get(retencionDetalle.getCodigoSri()).getNombre().equals("RENTA"))
                {
                    if (mapSumatoriaRetencionRenta.get(retencionDetalle.getCodigoRetencionSri()) == null) 
                    {
                        BigDecimal valor = retencionDetalle.getValorRetenido();
                        mapSumatoriaRetencionRenta.put(retencionDetalle.getCodigoRetencionSri(), valor);
                    } else 
                    {
                        BigDecimal valor = mapSumatoriaRetencionRenta.get(retencionDetalle.getCodigoRetencionSri());
                        valor = valor.add(retencionDetalle.getValorRetenido());
                        mapSumatoriaRetencionRenta.put(retencionDetalle.getCodigoRetencionSri(), valor);
                    }
                    
                }
                    
            }
        }
    }
    
    public File obtenerArchivoReportePdf(InterfazComunicacionPanel panelPadre)
    {
        
        //ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, "Reporte Documentos ", OrientacionReporteEnum.HORIZONTAL);
        String nombreArchivo=UtilidadesArchivos.generarNombreArchivoUnico("reporte","pdf");
        String pathGrabar="tmp\\"+nombreArchivo; //TODO: Camabiar por algun nombre en funcion de la fecha para que se unico y no genere problemas
        
        ReporteCodefac.generarReporteInternalFramePlantillaArchivo(obtenePathReporte(), obtenerMapReporte(),dataReporte, panelPadre, "Reporte Retención", OrientacionReporteEnum.VERTICAL,FormatoHojaEnum.A4,pathGrabar);
        File file=new File(pathGrabar);
        if(file.exists())
        {
            return file;
        }
        return null;
    }
    
    public void obtenerReportePdf(InterfazComunicacionPanel panelPadre)
    {
        ReporteCodefac.generarReporteInternalFramePlantilla(obtenePathReporte(), obtenerMapReporte(), dataReporte, panelPadre, "Reporte Retenciones ");  
    
    }
    
    public Map<String, Object> obtenerMapReporte()
    {
        InputStream pathSubReporte = null;
        try {
            List<ValorRetencionesData> datavc = new ArrayList<ValorRetencionesData>();
            for (Map.Entry<String, BigDecimal> entry : mapSumatoriaRetencionRenta.entrySet()) {
                String key = entry.getKey();
                BigDecimal value = entry.getValue();
                
                datavc.add(new ValorRetencionesData(
                        key, value.toString()
                ));
                
            }   List<ValorRetencionesData> datav = new ArrayList<ValorRetencionesData>();
            for (Map.Entry<String, BigDecimal> entry : mapSumatoriaRetencionIva.entrySet()) {
                String key = entry.getKey();
                BigDecimal value = entry.getValue();
                
                datav.add(new ValorRetencionesData(
                        key, value.toString()
                ));
                
            }   
            RecursosServiceIf service = ServiceFactory.getFactory().getRecursosServiceIf();
            pathSubReporte = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER_COMPRA, "subreporte_retencion.jrxml"));
            JasperReport reportPiePagina = JasperCompileManager.compileReport(pathSubReporte);
            RecursosServiceIf service2 = ServiceFactory.getFactory().getRecursosServiceIf();
            InputStream pathSubReporte2 = RemoteInputStreamClient.wrap(service2.getResourceInputStream(RecursoCodefac.JASPER_COMPRA, "subreporte_retencion_renta.jrxml"));
            JasperReport reportPiePagina2 = JasperCompileManager.compileReport(pathSubReporte2);
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("SUBREPORTE_RUTA", reportPiePagina);
            parameters.put("SUBREPORTE_RUTA_RENTA", reportPiePagina2);
            String cliente = "";
            if (proveedor == null) {
                cliente = "Todos";
            } else {
                cliente = proveedor.getRazonSocial();
            }   String niva = "";
            if (sriRetencionIva == null) {
                niva = "Todos";
            } else {
                niva = sriRetencionIva.getNombre();
            }   String nrenta = "";
            if (sriRetencionIva == null) {
                nrenta = "Todos";
            } else {
                nrenta = sriRetencionRenta.getNombre();
            }   String ntipo = "";
            if (sriRetencion == null) {
                ntipo = "Todos";
            } else {
                ntipo = sriRetencion.toString();
            }   parameters.put("tipo", ntipo);
            
            parameters.put("estado",estadoEnum.getNombre());
            parameters.put("niva", niva);
            parameters.put("nretencion", nrenta);
            parameters.put("proveedor", cliente);
            parameters.put("fechainicio", formatDate(fechaInicio, "yyyy-MM-dd"));
            parameters.put("fechafin", formatDate(fechaFin, "yyyy-MM-dd"));
            parameters.put("listaIva", datavc);
            parameters.put("listaRenta", datav);
            return parameters;
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorReporteRetencion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ControladorReporteRetencion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControladorReporteRetencion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pathSubReporte.close();
            } catch (IOException ex) {
                Logger.getLogger(ControladorReporteRetencion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    public void obtenerReporteExcel() throws IOException, FileNotFoundException, IllegalArgumentException, IllegalAccessException
    {
        Excel excel = new Excel();        
        excel.gestionarIngresoInformacionExcel(nombreCabeceras, dataReporte);
        excel.abrirDocumento();
    }
    
    public File obtenerArchivoReporteExcel() throws IOException, FileNotFoundException, IllegalArgumentException, IllegalAccessException {
        Excel excel = new Excel();
        //String nombreCabeceras[] = {"Clave de Acceso", "Preimpreso", "Proveedor", "Fecha", "Estado", "Tipo", "Compra", "Base Imponible", "Porcentaje", "Código", "Valor"};
        excel.gestionarIngresoInformacionExcel(nombreCabeceras, dataReporte);
        return excel.obtenerArchivo();
    }
    
    private void cargarMapsTiposRetenciones()
    {
        try {
            mapTipoRetencion = new HashMap<String, SriRetencion>();
            List<SriRetencion> retenecionesTipo = ServiceFactory.getFactory().getSriRetencionServiceIf().obtenerTodos();
            for (SriRetencion sriRetencion : retenecionesTipo) {
                mapTipoRetencion.put(sriRetencion.getCodigo(), sriRetencion);
                //getCmbTipo().addItem(sriRetencion);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorReporteRetencion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Map<String, BigDecimal> getMapSumatoriaRetencionIva() {
        return mapSumatoriaRetencionIva;
    }

    public Map<String, BigDecimal> getMapSumatoriaRetencionRenta() {
        return mapSumatoriaRetencionRenta;
    }

    public List<ReporteRetencionesData> getDataReporte() {
        return dataReporte;
    }
    
    public InputStream obtenePathReporte()
    {
        return RecursoCodefac.JASPER_COMPRA.getResourceInputStream("reporte_retenciones.jrxml");        
    }
    
    
    
    
}
