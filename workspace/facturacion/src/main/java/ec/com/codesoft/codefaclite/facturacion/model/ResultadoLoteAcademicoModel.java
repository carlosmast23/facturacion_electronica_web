/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.facturacion.panel.ResultadoLoteAcademicoPanel;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteData;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.ws.recepcion.Comprobante;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author
 */
public class ResultadoLoteAcademicoModel extends ResultadoLoteAcademicoPanel{
    
    private List<ComprobanteData> comprobantes;

    public ResultadoLoteAcademicoModel(List<ComprobanteData> comprobantes) {
        this.comprobantes = comprobantes;
        cargarDatos();
        iniciarListener();
    }
    
    
    private void cargarDatos()
    {
        String[] titulo={"Clave Acceso","Preimpreso","Cliente","Estudiante","Estado"};
        DefaultTableModel modeloTabla=new DefaultTableModel(titulo,0);
        
        for (ComprobanteData comprobanteData : comprobantes) 
        {
            Vector<String> fila=new Vector<String>();
            fila.add(comprobanteData.getNumeroAutorizacion());
            fila.add(comprobanteData.getPreimpreso());
            
            ComprobanteElectronico comprobante= comprobanteData.getComprobante();
            if(comprobante!=null)
            {
                fila.add(comprobante.getRazonSocialComprador());
                
                fila.add(buscarInfoAdicionalPorTitulo(comprobante.getInformacionAdicional(),"Estudiante"));
            }
            else
            {
                fila.add("");
                fila.add("");
            }
                
            fila.add(comprobanteData.getEstado());
            modeloTabla.addRow(fila);
        }
        
        getTblComprobantes().setModel(modeloTabla);        
    }
    
    private String buscarInfoAdicionalPorTitulo(List<InformacionAdicional> detalles, String titulo) {
        if (detalles != null) {
            for (InformacionAdicional infoAdicional : detalles) {
                if (infoAdicional.getNombre().equals(titulo)) {
                    return infoAdicional.getValor();
                }
            }
        }
        return "";
    }

    private void iniciarListener() {
        getBtnRide().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarRide();
            }
        });
        
        getBtnAbrir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirPantallaFactura();
            }
        });
    }
    
    private void abrirPantallaFactura()
    {
        int fila = getTblComprobantes().getSelectedRow();
        String claveAcceso = getTblComprobantes().getValueAt(fila, 0).toString();
        
        if (fila >= 0) {
            try {
                
                FacturacionServiceIf servicio=ServiceFactory.getFactory().getFacturacionServiceIf();
                //Map<String,Object> mapParametros=new HashMap<String,Object>();
                List<Factura> facturas=servicio.obtenerTodos(); //Todo: deberia filtrar por emepresa o algo
                
                if(facturas.size()>0)
                {
                    FacturacionModel facturacionModel = new FacturacionModel();
                    Factura factura = facturas.get(0);
                    panelPadre.crearVentanaCodefac(facturacionModel, true);
                    //facturacionModel.iniciarValoresIniciales();
                    facturacionModel.setFactura(factura);

                }
                
            } catch (RemoteException ex) {
                Logger.getLogger(ResultadoLoteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExcepcionCodefacLite ex) {
                Logger.getLogger(ResultadoLoteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        else 
        {
            DialogoCodefac.mensaje("Error", "Seleccione una fila para abrir", DialogoCodefac.MENSAJE_INCORRECTO);
        }
    }
    
    private void generarRide() {
        int fila = getTblComprobantes().getSelectedRow();
        String claveAcceso = getTblComprobantes().getValueAt(fila, 0).toString();

        if (fila >= 0) {
            try {
                byte[] reporteByte = ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(claveAcceso,session.getEmpresa());
                JasperPrint jasperPrint = (JasperPrint) UtilidadesRmi.deserializar(reporteByte);
                panelPadre.crearReportePantalla(jasperPrint, claveAcceso);

            } catch (RemoteException ex) {
                Logger.getLogger(ResultadoLoteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ResultadoLoteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ResultadoLoteAcademicoModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            DialogoCodefac.mensaje("Error", "Seleccione una fila para imprimir", DialogoCodefac.MENSAJE_INCORRECTO);
        }
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
    public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
