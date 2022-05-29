/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.utilidades;

import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class UtilidadReportes {
    public static JasperPrint unificarReportes(List<JasperPrint> jasperList)
    {
        JasperPrint reporteUnido=null;
        for (JasperPrint jasperPrint : jasperList) {
            if (reporteUnido == null) 
            {
                reporteUnido = jasperPrint;
            } else 
            {
                List pages = jasperPrint.getPages();
                for (int j = 0; j < pages.size(); j++) {
                    JRPrintPage nuevasPaginas = (JRPrintPage) pages.get(j);
                    reporteUnido.addPage(nuevasPaginas);
                }
                //reporteUnido.addPage(page);
            }
        }
        return reporteUnido;
    }
    
    public static void visualizarReporteVentanaExterna(JasperPrint jasperPrint)
    {
        JasperViewer.viewReport(jasperPrint,false);
    }
    
    /**
     * Metodo que me permite grabar un archivo jasper como pdf de forma temporal
     * @param jasperPrint
     * @return 
     */
    public static String grabarArchivoJasperTemporal(JasperPrint jasperPrint)
    {
        String nombreArchivo=UtilidadesArchivos.generarNombreArchivoUnico("tmp","pdf");
        String pathGrabar=ParametrosSistemaCodefac.CARPETA_DATOS_TEMPORALES+"\\"+nombreArchivo;         
        try {
            JasperExportManager.exportReportToPdfFile(jasperPrint,pathGrabar);
            return pathGrabar;
        } catch (JRException ex) {
            Logger.getLogger(UtilidadReportes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
