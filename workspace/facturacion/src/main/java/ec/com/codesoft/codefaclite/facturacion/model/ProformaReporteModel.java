/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.facturacion.panel.ProformaReportePanel;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ReporteFacturaData;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.facturacion.reportdata.ReporteProformaData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.apache.xalan.xsltc.compiler.util.StringStack;

/**
 *
 * @author
 */
public class ProformaReporteModel extends ProformaReportePanel {

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    private Persona cliente;
    private List<Factura> proformasConsulta;
    
    private List<ReporteProformaData> datosReporte;
    private Map<String,BigDecimal> mapTotales;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        iniciarComponentes();
        listenerBotones();
        listenerChecks();
        validacionDatosIngresados=false;
    }

    private void iniciarComponentes() {
        getCmbEstado().removeAllItems();
        //TODO:Toca establecer un solo estado
        getCmbEstado().addItem(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO);
        getCmbEstado().addItem(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO);
        getCmbEstado().addItem(ComprobanteEntity.ComprobanteEnumEstado.FACTURADO_PROFORMA);

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
    
    private void construirDataReporte()
    {
        mapTotales=new HashMap<String,BigDecimal>();
        datosReporte=new ArrayList<ReporteProformaData>();
        
        if(proformasConsulta!=null)
        {
            for (Factura proforma : proformasConsulta) {
                
                ReporteProformaData dato=new ReporteProformaData();
                dato.setNumeroFactura((proforma.getSecuencial()!=null)?proforma.getSecuencial().toString():"");
                dato.setFechaFactura(dateFormat.format(proforma.getFechaEmision()));
                dato.setIdentificacionCliente(proforma.getIdentificacion());
                dato.setRazonSocialCliente(proforma.getRazonSocial());
                dato.setNombreLegalCliente((proforma.getCliente().getEstablecimientos().get(0).getNombreComercial()!=null)?proforma.getCliente().getEstablecimientos().get(0).getNombreComercial():"");
                dato.setSubtotalDoceFactura(proforma.getSubtotalImpuestos().toString());
                dato.setSubtotalCeroFactura(proforma.getSubtotalSinImpuestos().toString());
                dato.setDescFactura(proforma.getDescuentoImpuestos().add(proforma.getDescuentoSinImpuestos()).toString());
                dato.setIvaDoceFactura(proforma.getIva().toString());
                dato.setTotalFinal(proforma.getTotal().toString());
                datosReporte.add(dato);
                
                agregarValorTotal("acum", proforma.getSubtotalSinImpuestos());
                //acum = acum.add(factura.getSubtotalSinImpuestos());
                agregarValorTotal("acumdoce", proforma.getSubtotalImpuestos());
                //acumdoce = acumdoce.add(factura.getSubtotalImpuestos());
                agregarValorTotal("acumiva", proforma.getIva());
                //acumiva = acumiva.add(factura.getIva());
                agregarValorTotal("acumdesc", proforma.getDescuentoImpuestos().add(proforma.getDescuentoSinImpuestos()));
            }
        }
    }
    
    private void agregarValorTotal(String nombre, BigDecimal valor) {
        BigDecimal valorTmp = mapTotales.get(nombre);
        if (valorTmp == null) {
            mapTotales.put(nombre, valor);
        } else {
            valorTmp = valorTmp.add(valor);
            mapTotales.put(nombre, valorTmp);
        }
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        Date fechaInicio = null;
        Date fechaFin = null;
        
        //List<ReporteFacturaData> datosReporte=new ArrayList<ReporteFacturaData>();
        //Map<String,BigDecimal> mapTotales=new HashMap<String,BigDecimal>();

        //BigDecimal acum = BigDecimal.ZERO, acumdoce = BigDecimal.ZERO, acumiva = BigDecimal.ZERO, acumdesc = BigDecimal.ZERO;
        ComprobanteEntity.ComprobanteEnumEstado estadoFactura = (ComprobanteEntity.ComprobanteEnumEstado) getCmbEstado().getSelectedItem();
        String estadoStr = estadoFactura.getEstado();

        if (getDateFechaInicio().getDate() != null) {
            fechaInicio = new Date(getDateFechaInicio().getDate().getTime());
        }
        if (getDateFechaFin().getDate() != null) {
            fechaFin = new Date(getDateFechaFin().getDate().getTime());
        }
        
        String estadoText = estadoFactura.getNombre();
        final InputStream path = RecursoCodefac.JASPER_FACTURACION.getResourceInputStream("reporte_proformas.jrxml");
        String cliente = "";
        if (this.cliente == null) {
            cliente = "TODOS";
        } else {
            cliente = this.cliente.getRazonSocial();
        }
        
             /**
         * Genera el reporte
         */

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("fechainicio", (fechaInicio != null) ? dateFormat.format(fechaInicio) : "");
        parameters.put("fechafin", (fechaFin != null) ? dateFormat.format(fechaFin) : "");
        parameters.put("cliente", cliente);
        parameters.put("subtotal",mapTotales.get("acum").toString());
        parameters.put("subtotaliva",mapTotales.get("acumdoce").toString());
        parameters.put("valoriva",mapTotales.get("acumiva").toString());
        //BigDecimal total = acum.add(acumdoce).add(acumiva);
        BigDecimal total = mapTotales.get("acum").add(mapTotales.get("acumdoce").add(mapTotales.get("acumiva")));
        parameters.put("total", total.toString());
        
        //BigDecimal subtotal = acum.add(acumdoce);
        BigDecimal subtotal = mapTotales.get("acum").add(mapTotales.get("acumdoce"));
        parameters.put("totalsubtotales", subtotal.toString());
        parameters.put("descuentos",mapTotales.get("acumdesc").toString());
        parameters.put("estadofactura", estadoText);

        DialogoCodefac.dialogoReporteOpciones(new ReporteDialogListener() {

            @Override
            public void excel() {
                try {
                    Excel excel = new Excel();
                    String[] titulosVector = obtenerCabecera();
                    //String nombreCabeceras[] = titulosVector.toArray(new String[titulosVector.size()]); //Convertir en array
                    excel.gestionarIngresoInformacionExcel(titulosVector, datosReporte);
                    excel.abrirDocumento();
                } catch (Exception exc) {
                    exc.printStackTrace();
                    DialogoCodefac.mensaje("Error", "El archivo Excel se encuentra abierto", DialogoCodefac.MENSAJE_INCORRECTO);
                }
            }

            @Override
            public void pdf() {
                ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, datosReporte, panelPadre, "Reporte Proformas ", OrientacionReporteEnum.HORIZONTAL);
            }
        });
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

    private void listenerBotones() {
        
        getBtnLimpiarFechaInicio().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDateFechaInicio().setDate(null);
            }
        });
        
        getBtnLimpiarFechaFin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getDateFechaFin().setDate(null);
            }
        });
        
        getBtnBuscarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteEstablecimientoBusquedaDialogo clienteBusquedaDialogo = new ClienteEstablecimientoBusquedaDialogo(session);
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                buscarDialogoModel.getResultado();
                if (buscarDialogoModel.getResultado() != null) {
                    cliente=((PersonaEstablecimiento) buscarDialogoModel.getResultado()).getPersona();
                    getTxtCliente().setText(cliente.toString());
                }
            }
        });
        
        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    //Date
                    java.sql.Date fechaInicial=null;
                    java.sql.Date fechaFinal=null;
                    
                    if(getDateFechaInicio().getDate()!=null)
                    {
                        fechaInicial=new Date(getDateFechaInicio().getDate().getTime());
                    }
                    
                    if (getDateFechaFin().getDate() != null) {
                        fechaFinal = new Date(getDateFechaFin().getDate().getTime());
                    }
                    
                    
                    proformasConsulta=ServiceFactory.getFactory().getFacturacionServiceIf().consultarProformasReporte(cliente,fechaInicial,fechaFinal,session.getEmpresa(),(ComprobanteEntity.ComprobanteEnumEstado) getCmbEstado().getSelectedItem());
                    construirDataReporte();
                    construirTabla();
                    //ServiceFactory.getFactory().getFacturacionServiceIf().obtenerFacturasReporte(cliente,getDateFechaInicio().getDate(),getDateFechaFin().getDate(), title, formularioCerrando, referido, formularioCerrando)
                } catch (RemoteException ex) {
                    Logger.getLogger(ProformaReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(ProformaReporteModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    private  String[] obtenerCabecera()
    {
        String titulo[]={"Secuencial","Fecha","Identificación","Razón Social","Nombre Legal","Subtotal 12%","Subtotal 0%","Descuentos","IVA","Total"};
        return titulo;
    }
    
    private void construirTabla()
    {
        String titulo[]=obtenerCabecera();
        
        DefaultTableModel modeloTabla=new DefaultTableModel(titulo,0);
        
        if(datosReporte!=null)
        {
            for (ReporteFacturaData proformaData : datosReporte) {
                modeloTabla.addRow(new String[]{ 
                    proformaData.getNumeroFactura(),
                    //proformaData.getReferencia(),
                    proformaData.getFechaFactura(),
                    proformaData.getIdentificacionCliente(),
                    proformaData.getRazonSocialCliente(),
                    proformaData.getNombreLegalCliente(),
                    proformaData.getSubtotalDoceFactura(),
                    proformaData.getSubtotalDoceFactura(),
                    proformaData.getDescFactura(),
                    proformaData.getIvaDoceFactura(),
                    proformaData.getTotalFinal(),
                });
            }

        }
        
        getTblProforma().setModel(modeloTabla);
        UtilidadesTablas.bloquerTodasColumnasTabla(getTblProforma());
    }

    private void listenerChecks() {
        getChkTodos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Limpiar el campo de busqueda si quiere buscar todos los cliente
                if(getChkTodos().isSelected())
                {
                    cliente=null;
                    getTxtCliente().setText("");
                    getBtnBuscarCliente().setEnabled(false);
                }
                else
                {
                    getBtnBuscarCliente().setEnabled(true);
                }
            }
        });
    }
    
    

}
