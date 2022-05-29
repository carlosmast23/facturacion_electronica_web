/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Carlos
 */
public class EjemploCrm {
    public static void main(String[] args) {
        try {
            //String path=EjemploCrm.class.getResource("/img/iconos/edit-icon.png").getPath().toString();
            String path=EjemploCrm.class.getResource("/reportes/crm/reporteEjemplo.jrxml").getPath();
            InputStream input=EjemploCrm.class.getResource("/reportes/crm/reporteEjemplo.jrxml").openStream();
            //JasperReport report=(JasperReport) JRLoader.loadObject(input);
            
            System.out.println(path);
            
            Map parameters = new HashMap();
            parameters.put("nombre","carlos");
            JasperReport report =JasperCompileManager.compileReport(input);
            JasperPrint print =JasperFillManager.fillReport(report, parameters,new JREmptyDataSource());
            JasperViewer.viewReport(print,false);
            //JasperPrintManager.printReport(print, false);
            System.out.println("abriendo jasperreport");
        } catch (JRException ex) {
            Logger.getLogger(EjemploCrm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EjemploCrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
