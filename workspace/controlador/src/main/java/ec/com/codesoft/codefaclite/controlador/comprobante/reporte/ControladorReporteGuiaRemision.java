/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobante.reporte;

import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Transportista;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DestinatarioGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DetalleProductoGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.GuiaRemisionServiceIf;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author
 */
public class ControladorReporteGuiaRemision {
    
    private java.sql.Date fechaInicial;
    private java.sql.Date fechaFinal;
    private ComprobanteEntity.ComprobanteEnumEstado estado;
    private List<GuiaRemision> listaConsulta;
    private List<GuiaTransporteData> listReporte;
    private Transportista transportista;
    private Persona destinatario;
    private String codigoProducto;
    private Empresa empresa;

    public ControladorReporteGuiaRemision(Date fechaInicial, Date fechaFinal, ComprobanteEntity.ComprobanteEnumEstado estado,Transportista transportista,Persona destinatario,String codigoProducto,Empresa empresa) {
        this.fechaInicial = fechaInicial;
        this.fechaFinal = fechaFinal;
        this.estado = estado;
        this.transportista=transportista;
        this.destinatario=destinatario;
        this.codigoProducto=codigoProducto;
        this.empresa=empresa;
    }
   
    
    public void generarReporte() throws ServicioCodefacException
    {
        try {
            GuiaRemisionServiceIf guiaRemisionServiceIf=ServiceFactory.getFactory().getGuiaRemisionServiceIf();
            listaConsulta=guiaRemisionServiceIf.obtenerConsulta(fechaInicial,fechaFinal,estado, transportista,destinatario,codigoProducto,empresa);
            llenarDatosReporte();
        //} catch (ServicioCodefacException ex) {
        //    Logger.getLogger(ControladorReporteGuiaRemision.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ControladorReporteGuiaRemision.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(MensajeCodefacSistema.ErrorComunicacion.ERROR_COMUNICACION_SERVIDOR);
        }
        
    }
    
    public List<SubreporteGuiaRemisionProductoData> obtenerSubtotalesProducto()
    {
        List<SubreporteGuiaRemisionProductoData> subReporte=new ArrayList<SubreporteGuiaRemisionProductoData>();
        if(listaConsulta!=null)
        {
            for (GuiaRemision guiaRemision : listaConsulta) 
            {
                for (DestinatarioGuiaRemision destinatario : guiaRemision.getDestinatarios()) 
                {
                    for (DetalleProductoGuiaRemision detalleProducto : destinatario.getDetallesProductos()) {
                        SubreporteGuiaRemisionProductoData.agregarDatoLista(subReporte, detalleProducto);
                    }
                   
                }

            }
        }
        return subReporte;
    }
    
    

    public List<GuiaRemision> getListaConsulta() {
        return listaConsulta;
    }
    
    public void obtenerReporteExcel() throws IOException, FileNotFoundException, IllegalArgumentException, IllegalAccessException
    {
        Excel excel = new Excel();
        String nombreCabeceras[] = getCabeceraReporteExcel();
        excel.gestionarIngresoInformacionExcel(nombreCabeceras, listReporte);
        excel.abrirDocumento();
    }
    
    public File obtenerReporteArchivoExcel() throws IOException, FileNotFoundException, IllegalArgumentException, IllegalAccessException {
        Excel excel = new Excel();
        String nombreCabeceras[] = getCabeceraReporteExcel();
        excel.gestionarIngresoInformacionExcel(nombreCabeceras, listReporte);
        return excel.obtenerArchivo();
    }
    
    public void obtenerReportePdf(InterfazComunicacionPanel panelPadre) throws IOException, FileNotFoundException, IllegalArgumentException, IllegalAccessException {
        InputStream path =obtenerPathReporte();        
        InputStream subreporte=RecursoCodefac.JASPER_TRANSPORTE.getResourceInputStream("subreporteGuiaRemisionProductos.jrxml");
        Map<String,Object> parametros = new HashMap<String,Object>();
        try {
            JasperReport subreporteJasper = JasperCompileManager.compileReport(subreporte);
            parametros.put("subreporte_productos_jasper",subreporteJasper);
        } catch (JRException ex) {
            Logger.getLogger(ControladorReporteGuiaRemision.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        parametros.put("subreporte_productos", obtenerSubtotalesProducto());
       
        
        
        ReporteCodefac.generarReporteInternalFramePlantilla(path, parametros, listReporte, panelPadre, "Reporte Guía Remisión ", OrientacionReporteEnum.HORIZONTAL);
    }
    
    /**
     * Todo: Funcionar con el reporte de arriba porque se repite mucho codigo
     * @param panelPadre
     * @throws IOException
     * @throws FileNotFoundException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException 
     */
    public void obtenerReporteAgrupadoArchivoPdf(InterfazComunicacionPanel panelPadre) throws IOException, FileNotFoundException, IllegalArgumentException, IllegalAccessException {
        InputStream path =obtenerPathReporteAgrupado();        
        InputStream subreporte=RecursoCodefac.JASPER_TRANSPORTE.getResourceInputStream("subreporteGuiaRemisionProductos.jrxml");
        Map<String,Object> parametros = new HashMap<String,Object>();
        try {
            JasperReport subreporteJasper = JasperCompileManager.compileReport(subreporte);
            parametros.put("subreporte_productos_jasper",subreporteJasper);
        } catch (JRException ex) {
            Logger.getLogger(ControladorReporteGuiaRemision.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        parametros.put("subreporte_productos", obtenerSubtotalesProducto());
        ordenarListaDatos();
        ReporteCodefac.generarReporteInternalFramePlantilla(path,parametros,listReporte, panelPadre, "Reporte Agrupado", OrientacionReporteEnum.HORIZONTAL);
        
    }
    
    public void ordenarListaDatos()
    {
        Collections.sort(listReporte,new Comparator<GuiaTransporteData>() {
            @Override
            public int compare(GuiaTransporteData o1, GuiaTransporteData o2) {
                if(o1.getIdentififacion().equals("0103445417001") && o2.getIdentififacion().equals("1723612030"))
                {
                    System.out.println("Comparando"+o1.getIdentififacion()+"=>"+o2.getIdentififacion());
                }
                
                int comparacion=o1.getDestinatarios().compareTo(o2.getDestinatarios());
                if(comparacion==0) //Si las identificaciones son iguales procedo a compar por el siguiente campo
                {
                    comparacion=o1.getFechaInicio().compareTo(o2.getFechaInicio());
                }
                //return o1.getIdentififacion().compareTo(o2.getIdentififacion())+o1.getFechaInicio().compareTo(o2.getFechaInicio());
                return comparacion;
            }
        });
    }
    
    public File obtenerReporteArchivoPdf(InterfazComunicacionPanel panelPadre) throws IOException, FileNotFoundException, IllegalArgumentException, IllegalAccessException {
        //InputStream path = obtenerPathReporte();
        //Map parameters = new HashMap();
        //ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, listReporte, panelPadre, "Reporte Guía Remisión ", OrientacionReporteEnum.HORIZONTAL);
        
        String nombreArchivo=UtilidadesArchivos.generarNombreArchivoUnico("reporte","pdf");
        String pathGrabar="tmp\\"+nombreArchivo; //TODO: Camabiar por algun nombre en funcion de la fecha para que se unico y no genere problemas
        
        ReporteCodefac.generarReporteInternalFramePlantillaArchivo(obtenerPathReporte(),new HashMap(),listReporte, panelPadre, "Reporte Guía Remisión", OrientacionReporteEnum.HORIZONTAL,FormatoHojaEnum.A4,pathGrabar);
        File file=new File(pathGrabar);
        if(file.exists())
        {
            return file;
        }
        return null;
    }
    
    public String[] getCabeceraReporteExcel()
    {
        return new String[]{"Clave de Acceso","Preimpreso","Identificación","Transportista","Destinatarios","Estado","FechaInicio","FechaFin","Facturas","Placa","# Items"};
    }
    
    public InputStream obtenerPathReporte()
    {
        return RecursoCodefac.JASPER_TRANSPORTE.getResourceInputStream("reporte_guiaRemision.jrxml");
    }
    
    public InputStream obtenerPathReporteAgrupado()
    {
        return RecursoCodefac.JASPER_TRANSPORTE.getResourceInputStream("reporte_guiaRemision_agrupado.jrxml");
    }

    private void llenarDatosReporte() {
        listReporte=new ArrayList<GuiaTransporteData>();
        for (GuiaRemision guiaRemision : listaConsulta) {
            GuiaTransporteData data=new GuiaTransporteData();
            data.setDireccionPartida(guiaRemision.getDireccionPartida());
            data.setEstado(guiaRemision.getEstadoEnum().getNombre());
            data.setFechaFin(guiaRemision.getFechaFinTransporte().toString());
            data.setFechaInicio(guiaRemision.getFechaIniciaTransporte().toString());
            data.setIdentififacion((guiaRemision.getIdentificacion()!=null)?guiaRemision.getIdentificacion().toString():"");
            data.setPlaca(guiaRemision.getPlaca());
            data.setPreimpreso(guiaRemision.getPreimpreso());
            data.setTransportista(guiaRemision.getRazonSocial());
            data.setClaveAcceso(guiaRemision.getClaveAcceso());
            data.setCantidadItems(guiaRemision.obtenerTotalItems().toString());
            data.setFacturas(UtilidadesLista.castListToString(guiaRemision.obtenerFacturasTransportadas(),","));
            data.setDestinatarios(UtilidadesLista.castListToString(guiaRemision.getDestinatarios(),",",new UtilidadesLista.CastListInterface<DestinatarioGuiaRemision>() {
                @Override
                public String getString(DestinatarioGuiaRemision dato) {
                    return dato.getRazonSocial();
                }
           }));
            listReporte.add(data);
        }
    }

    public List<GuiaTransporteData> getListReporte() {
        return listReporte;
    }
    
    
}
