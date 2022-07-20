/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.utilidades;

import ec.com.codesoft.codefaclite.codefacweb.mb.facturacion.ProformaMb;
import ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @auhor
 */
public class UtilidadesReporteWeb {

    public static void generarReporteHojaNuevaPdf(JasperPrint jasperPrint,String nombre) {

        try {
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
            //byte[] bytes = JasperRunManager.runReportToPdf(path, mapParametros, new JRBeanCollectionDataSource(dataReporte));
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            //response.setHeader("Content-disposition", "inline; filename=proforma");
            response.setHeader("Content-Disposition", "inline; filename=\""+nombre+"\"");
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());

            ServletOutputStream outStream = response.getOutputStream();
            baos.writeTo(outStream);
            //outStream.write(baos, 0, baos.size());

            outStream.flush();
            outStream.close();

            FacesContext.getCurrentInstance().responseComplete();

        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ProformaMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
