/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.facturacion.panel.DashBoardPanel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.DashBoardData;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ReportDataAbstract;
import java.io.InputStream;
import java.rmi.RemoteException;
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
 * @author DellWin10
 */
public class DashBoardModel extends DashBoardPanel{

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        
        try {
            ReportDataAbstract<DashBoardData> reporte=ServiceFactory.getFactory().getUtilidadesServiceIf().consultarDashboard(null,null);
            
            DialogoCodefac.dialogoReporteOpciones( new ReporteDialogListener() {
                @Override
                public void excel() {
                    try{
                        Excel excel = new Excel();
                        String nombreCabeceras[] = {"Identificación", "Nombres completos","Nombre Legal", "Telefono", "Dirección","Email"};
                        //excel.gestionarIngresoInformacionExcel(nombreCabeceras, data);
                        excel.abrirDocumento();
                    }
                    catch(Exception exc)
                    {
                        exc.printStackTrace();
                        DialogoCodefac.mensaje("Error","El archivo Excel se encuentra abierto",DialogoCodefac.MENSAJE_INCORRECTO);
                    }  
                }

                @Override
                public void pdf() {
                    try {
                        InputStream path = RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("dashboard.jrxml");
                        
                        
                        InputStream pathSubReporteTopProducto = RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("dashboard_top_productos.jrxml");
                        
                        JasperReport reportPiePagina = JasperCompileManager.compileReport(pathSubReporteTopProducto);
                        
                        reporte.agregarParametro("subreporte_top_productos",reportPiePagina);
                        
                        ReporteCodefac.generarReporteInternalFramePlantilla(path,panelPadre, reporte);
                        dispose();
                        setVisible(false);
                    } catch (JRException ex) {
                        Logger.getLogger(DashBoardModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(DashBoardModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
