/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author
 */
public class ReporteComprobantesElectronicosProxy {
    public URL pathRecursos;
    public JasperReport jasperReport;
    
    public static List<ReporteComprobantesElectronicosProxy> lista=new ArrayList<ReporteComprobantesElectronicosProxy>();

    public ReporteComprobantesElectronicosProxy(URL pathRecursos) {
        this.pathRecursos = pathRecursos;
    }

    public ReporteComprobantesElectronicosProxy(URL pathRecursos, JasperReport jasperReport) {
        this.pathRecursos = pathRecursos;
        this.jasperReport = jasperReport;
    }
    
    
    public static void agregar(URL pathRecursos,JasperReport jasperReport)
    {
        ReporteComprobantesElectronicosProxy reporte=new ReporteComprobantesElectronicosProxy(pathRecursos, jasperReport);
        lista.add(reporte);
    }
    
    public static JasperReport obtenerReporte(URL pathRecursos)
    {
        for (ReporteComprobantesElectronicosProxy reporte : lista) 
        {
            if(reporte.pathRecursos.getPath().equals(pathRecursos.getPath()))
            {
                return reporte.jasperReport;
            }
        }
        return null;
    }
    
    
    
    
            
}
