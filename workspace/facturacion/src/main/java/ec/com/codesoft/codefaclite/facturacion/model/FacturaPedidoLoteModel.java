/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaPedidoLoteModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.controlador.vista.transporte.GuiaRemisionLoteControlador;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ITableBindingAddData;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.facturacion.callback.ClienteFacturaLoteImplComprobante;
import ec.com.codesoft.codefaclite.facturacion.interfaz.InterfaceCallbakClient;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturaPedidoLotePanel;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobanteLote;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteData;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class FacturaPedidoLoteModel extends FacturaPedidoLotePanel implements ControladorVistaIf,FacturaPedidoLoteModelControlador.SwingIf {

    private FacturaPedidoLoteModel formThis=this;
    
    private FacturaPedidoLoteModelControlador controlador;

    public FacturaPedidoLoteModel() {
        /**
         * Desactivo el ciclo de vida para controlar manualmente
         */
        //super.cicloVida = false;
        //super.validacionDatosIngresados = false;
    }
    
    
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        this.controlador=new FacturaPedidoLoteModelControlador(DialogoCodefac.intefaceMensaje, session,this, ModelControladorAbstract.TipoVista.ESCRITORIO);
        crearModeloTabla();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        return "https://docs.google.com/document/d/e/2PACX-1vRxHiHd5vpEu1In25BKtCXigpl4m1phGAZwNR7Rh2Jm-Xqe7ffQpivlYJsMAWHFBS0BOnYxj4dpUi7H/pub?embedded=true#h.3l5c2w2a02kz";
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

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, true);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, true);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public ModelControladorAbstract getControladorVista() {
        return controlador;
    }

    public FacturaPedidoLoteModelControlador getControlador() {
        return controlador;
    }

    public void setControlador(FacturaPedidoLoteModelControlador controlador) {
        this.controlador = controlador;
    }
    
    public void crearModeloTabla()
    {   
        String titulo[]=new String[]{
            "Objeto",
            "Seleccion",
            "Secuencial",
            "Fecha",
            "Identificación",
            "Razón Social",
            "Nombre Legal",
            "Vendedor",
            "Ruta",
            "Documento",
            "Total",
            "Crédito",
            "Dias"};
        
        DefaultTableModel modelo=UtilidadesTablas.crearModeloTabla(
                titulo, 
                new Class[]{
                    Object.class,
                    Boolean.class,
                    Object.class,
                    Object.class,
                    Object.class,
                    Object.class,
                    Object.class,
                    Object.class,
                    Object.class,
                    DocumentoEnum.class,
                    Object.class,
                    Boolean.class,
                    Integer.class,
                }
        );
        
        getTblDatos().setModel(modelo);
        
        JPopupMenu popup = new JPopupMenu();        
        JMenuItem jMenuItemCambiarDocumento = new JMenuItem("Cambiar Documento"); 
        popup.add(jMenuItemCambiarDocumento);
        jMenuItemCambiarDocumento.addActionListener(itemListener);
        
        getTblDatos().setComponentPopupMenu(popup);
        

        UtilidadesTablas.definirTamanioColumnas(getTblDatos(),new Integer[]{0});
    }
    
    private ActionListener itemListener=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            DocumentoEnum seleccion = (DocumentoEnum) JOptionPane.showInputDialog(
                    null,
                    "Seleccione el documento ",
                    "Selector de opciones",
                    JOptionPane.QUESTION_MESSAGE,
                    null, // null para icono defecto
                    new DocumentoEnum[]{DocumentoEnum.FACTURA,DocumentoEnum.NOTA_VENTA_INTERNA},
                    "opcion 1");
            
            if(seleccion!=null)
            {
                controlador.editarDocumentoPopUp(seleccion);
                getTblDatos().updateUI();
            }
        }
    };
    
   
    
    
    
    public ITableBindingAddData getTableBindingAddData()
    {
        return new ITableBindingAddData<FacturaPedidoLoteModelControlador.FacturaDataTable>() {
            @Override
            public Object[] addData(FacturaPedidoLoteModelControlador.FacturaDataTable valueTmp) {
                Factura value=valueTmp.factura;
                String nombreComercial=(value.getSucursal().getNombreComercial()!=null)?value.getSucursal().getNombreComercial():"";
                String vendedor=(value.getVendedor()!=null)?value.getVendedor().getNombresCompletos():"";
                
                //Valores por defeto para el Credito
                Boolean credito=true;
                Integer diasCredito=0;
                
                if(value.getVentaCredito()!=null)
                {
                    credito=value.getVentaCreditoEnum().getBool();
                }
                
                if(value.getDiasCredito()!=null)
                {
                    diasCredito=value.getDiasCredito();
                }
                
                
                return new Object[]{
                    valueTmp,
                    value.getSecuencial(),
                    value.getFechaEmision(),
                    value.getIdentificacion(),
                    value.getRazonSocial(),
                    nombreComercial,
                    vendedor,
                    "",//Ruta                    
                    valueTmp.documentoEnum,
                    value.getTotal(),
                    credito,
                    diasCredito
                };
            }

            @Override
            public void setData(FacturaPedidoLoteModelControlador.FacturaDataTable objetoOriginal, Object objetoModificado, Integer columnaModificada) {
                final int COLUMNA_OBJETO=0;
                final int COLUMNA_CREDITO=11;
                final int COLUMNA_DIAS_CREDITO=12;
                
                switch (columnaModificada) {
                    case COLUMNA_OBJETO:
                        break;

                    case COLUMNA_CREDITO:
                        objetoOriginal.credito=(Boolean) objetoModificado;
                        break;

                    case COLUMNA_DIAS_CREDITO:
                        objetoOriginal.dias=(Integer) objetoModificado;
                        break;

                }
                
                
            }
        };
    };

    @Override
    public void abrirGuiaRemision(GuiaRemision guiaRemision) {
        Object[] parametros=new Object[]{
            guiaRemision,
            false,
            true
        };
        panelPadre.crearVentanaCodefac(VentanaEnum.GUIA_REMISION,true, parametros);
    }

    @Override
    public void cerrarPantalla() {
        //dispose();
    }

    @Override
    public ClienteInterfaceComprobanteLote getInterfaceCallBack() {
        ClienteInterfaceComprobanteLote cic =null;
        try {
            cic = new ClienteFacturaLoteImplComprobante(this,new InterfaceCallbakClient() {
                @Override
                public void terminoProceso(List<ComprobanteData> comprobantes) {
                    generarReporteUnificado(comprobantes);
                    //formThis.estadoNormal();
                    //getCmbCarpetaComprobante().setSelectedIndex(getCmbCarpetaComprobante().getSelectedIndex()); //Vuelve a cargar los comprobantes
                }
            });
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaPedidoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cic;
    }

    private void generarReporteUnificado(List<ComprobanteData> comprobantes)
    {
        JasperPrint reporteUnido=null;
        if(comprobantes!=null)
        {
        for (ComprobanteData comprobante : comprobantes) {
            try {
                    byte[] byteReporte= ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(comprobante.getNumeroAutorizacion(),session.getEmpresa());
                    JasperPrint jasperPrint=(JasperPrint) UtilidadesRmi.deserializar(byteReporte);

                    if(reporteUnido==null)
                    {
                        reporteUnido=jasperPrint;
                    }else
                    {
                        List pages = jasperPrint.getPages();
                        for (int j = 0; j < pages.size(); j++) {
                            JRPrintPage nuevasPaginas = (JRPrintPage) pages.get(j);
                            reporteUnido.addPage(nuevasPaginas);
                        }
                        //reporteUnido.addPage(page);
                    }

                } catch (RemoteException ex) {
                    Logger.getLogger(FacturaPedidoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FacturaPedidoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(FacturaPedidoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            JasperViewer.viewReport(reporteUnido,false);
        }
    }

    @Override
    public void generarNotasVentaInterna(List<Factura> facturas) 
    {
        FacturaModelControlador.imprimirComprobanteVentaLote(facturas, "Notas de venta",true, session, panelPadre);
    }

}
