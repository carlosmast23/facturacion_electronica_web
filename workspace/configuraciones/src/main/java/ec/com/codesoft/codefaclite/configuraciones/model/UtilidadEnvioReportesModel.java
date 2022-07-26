/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.panel.UtilidadEnvioReportesPanel;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ControladorReporteCompra;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ControladorReporteFactura;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ControladorReporteGuiaRemision;
import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ControladorReporteRetencion;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb.AtsJaxb;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.CorreoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoArchivoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoReporteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoAtsEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.UtilidadesServidorXml;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.xml.UtilidadesXml;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class UtilidadEnvioReportesModel extends UtilidadEnvioReportesPanel {

    private Empleado empleadoSeleccionado;

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        cargarValoresIniciales();
        listenerBotones();
        listenerFechas();
        validacionDatosIngresados = false;
        actionListenerFecha();//Para cargar por defectos los valores de los ats la primera vez
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
        getCmbTipoEstadoReporte().setSelectedItem(ComprobanteEntity.ComprobanteEnumEstado.TODOS_SRI);
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

    private void cargarValoresIniciales() {

        //Cargar por defecto el primer dia del mes actual
        Date primerDiaMes = UtilidadesFecha.fechaInicioMes(new Date());
        getCmbFechaInicial().setDate(primerDiaMes);

        getCmbFechaFinal().setDate(UtilidadesFecha.getFechaHoy());

        for (ComprobanteEntity.ComprobanteEnumEstado objeto : ComprobanteEntity.ComprobanteEnumEstado.values()) 
        {
            getCmbTipoEstadoReporte().addItem(objeto);
        }

        //for (FormatoReporteEnum valor : FormatoReporteEnum.values()) {
        //    getCmbFormatoReporte().addItem(valor);
        //}
        UtilidadesComboBox.llenarComboBox(getCmbMesAts(), MesEnum.values());
        
        //Cargar los valores por defecto de los ats
        UtilidadesComboBox.llenarComboBox(getCmbTipoAts(),TipoAtsEnum.values());
    }

    private void listenerBotones() {
        getBtnBuscarEmpleado().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                EmpleadoBusquedaDialogo busquedaDialog = new EmpleadoBusquedaDialogo();
                //busquedaDialog.setTipoEnum(Departamento.TipoEnum.Ventas);
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedaDialog);
                buscarDialogoModel.setVisible(true);
                Empleado empleadoTmp = (Empleado) buscarDialogoModel.getResultado();
                if (empleadoTmp != null) {
                    empleadoSeleccionado = empleadoTmp;
                    getTxtEmpleadoDatos().setText(empleadoTmp.toString());
                }
            }

        });

        /**
         * Todo: Optimizar la forma de enviar los reportes porque esta generando
         * 2 veces la misma consulta para generar en excel y pdf
         */
        getBtnEnviarCorreo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, String> archivosAdjuntos = new HashMap<String, String>();
                //FormatoReporteEnum formatoReporteEnum=(FormatoReporteEnum) getCmbFormatoReporte().getSelectedItem();
                Boolean formatoPdf = getChkPdf().isSelected();
                Boolean formatoExcel = getChkExcel().isSelected();

                if (!validacionCampos()) {
                    return;
                }
                panelPadre.cambiarCursorEspera();

                if (getChkVentas().isSelected()) {
                    if (formatoPdf) {
                        generarReporteFacturasYNotaCredito(DocumentoEnum.FACTURA, FormatoReporteEnum.PDF, archivosAdjuntos);
                    }

                    if (formatoExcel) {
                        generarReporteFacturasYNotaCredito(DocumentoEnum.FACTURA, FormatoReporteEnum.EXCEL, archivosAdjuntos);
                    }
                }

                if (getChkNotaCredito().isSelected()) {
                    if (formatoPdf) {
                        generarReporteFacturasYNotaCredito(DocumentoEnum.NOTA_CREDITO, FormatoReporteEnum.PDF, archivosAdjuntos);
                    }

                    if (formatoExcel) {
                        generarReporteFacturasYNotaCredito(DocumentoEnum.NOTA_CREDITO, FormatoReporteEnum.EXCEL, archivosAdjuntos);
                    }
                }

                if (getChkRetencion().isSelected()) {
                    if (formatoPdf) {
                        generarReporteRetenciones(FormatoReporteEnum.PDF, archivosAdjuntos);
                    }

                    if (formatoExcel) {
                        generarReporteRetenciones(FormatoReporteEnum.EXCEL, archivosAdjuntos);
                    }
                }

                if (getChkGuiaRemision().isSelected()) {

                    if (formatoPdf) {
                        generarReporteGuiaRemision(FormatoReporteEnum.PDF, archivosAdjuntos);
                    }

                    if (formatoExcel) {
                        generarReporteGuiaRemision(FormatoReporteEnum.EXCEL, archivosAdjuntos);
                    }

                }

                if (getChkCompras().isSelected()) {
                    if (formatoPdf) {
                        generarReporteCompra(FormatoReporteEnum.PDF, archivosAdjuntos);
                    }
                    //Todo: falta implementar

                    if (formatoExcel) {
                        generarReporteCompra(FormatoReporteEnum.EXCEL, archivosAdjuntos);
                    }
                    //Todo: falta implementar
                }

                if (getChkEnviarAts().isSelected()) {
                    generarAts(archivosAdjuntos);
                }

                /**
                 * Metodo final para enviar los correos
                 */
                CorreoCodefac correoCodefac = new CorreoCodefac() {};
                
                 ArrayList<String> correos = new ArrayList<String>();
                        correos.add(empleadoSeleccionado.getCorreoElectronico());

                try {
                    //correoCodefac.enviarCorreo(empresa, title, title, correos, archivosAdjuntos);
                    correoCodefac.enviarCorreo(session.getEmpresa(),"Los archivos de los reportes estan adjuntos en el mensaje.","Reportes Generados Codefac",correos,archivosAdjuntos);
                    panelPadre.cambiarCursorNormal();
                    DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.PROCESO_CORRECTO);
                } catch (CorreoCodefac.ExcepcionCorreoCodefac ex) {
                    Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
                    DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
                    panelPadre.cambiarCursorNormal();
                }

            }
        });
    }

    private void generarAts(Map<String, String> archivosAdjuntos) {
        try {
            String establecimiento=session.getSucursal().getCodigoSucursalFormatoTexto();
            int anio=(int)(getTxtAnioAts().getValue());
            MesEnum mesEnum=(MesEnum)(getCmbMesAts().getSelectedItem());
            TipoAtsEnum tipoAtsEnum= (TipoAtsEnum) getCmbTipoAts().getSelectedItem();
            
            AtsJaxb atsJaxb = ServiceFactory.getFactory().getAtsServiceIf().consultarAts(
                    tipoAtsEnum,
                    anio,
                    mesEnum,
                    session.getEmpresa(),
                    establecimiento,
                    true, //Generar ventas
                    true, //Generar compras
                    true); //generar anulados
            
            File file = new File(ParametrosSistemaCodefac.CARPETA_DATOS_TEMPORALES + "/ejemplo.xml");
            UtilidadesServidorXml.convertirObjetoXmlEnArchivo(atsJaxb, file);
            if(file.exists()) //Solo si existe creado lo agrego al reporte
            {
                archivosAdjuntos.put(FormatoArchivoEnum.XML.agregarExtension("Ats"+anio+mesEnum.getNombre()),file.getPath());
            }
            
            //ParametrosSistemaCodefac.CARPETA_DATOS_TEMPORALES
        } catch (RemoteException ex) {
            Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generarReporteGuiaRemision(FormatoReporteEnum formatoReporteEnum, Map<String, String> archivosAdjuntos) {
        try {
            ControladorReporteGuiaRemision controladorReporte = new ControladorReporteGuiaRemision(
                    new java.sql.Date(getCmbFechaInicial().getDate().getTime()),
                    new java.sql.Date(getCmbFechaFinal().getDate().getTime()),
                    (ComprobanteEntity.ComprobanteEnumEstado) getCmbTipoEstadoReporte().getSelectedItem(),
                    null,
                    null,
                    null,
                    session.getEmpresa());

            controladorReporte.generarReporte();

            File archivoReporte = null;
            if (formatoReporteEnum.EXCEL.equals(formatoReporteEnum)) {

                try {
                    archivoReporte = controladorReporte.obtenerReporteArchivoExcel();
                } catch (IOException ex) {
                    Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (formatoReporteEnum.PDF.equals(formatoReporteEnum)) {
                try {
                    archivoReporte = controladorReporte.obtenerReporteArchivoPdf(panelPadre);
                } catch (IOException ex) {
                    Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            archivosAdjuntos.put("reporteGuiaRemision." + formatoReporteEnum.getExtension(), archivoReporte.getPath());
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error", "No se puede generar el reporte de las guías de remisión\nCausa:" + ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
        }

    }

    private void generarReporteCompra(FormatoReporteEnum formatoReporteEnum, Map<String, String> archivosAdjuntos) {
        ControladorReporteCompra controladorReporte = new ControladorReporteCompra(
                null,
                new java.sql.Date(getCmbFechaInicial().getDate().getTime()),
                new java.sql.Date(getCmbFechaFinal().getDate().getTime()),
                null,
                null,
                GeneralEnumEstado.ACTIVO,
                true,
                session.getEmpresa());
        controladorReporte.generarReporte();
        File archivoReporte = null;
        if (formatoReporteEnum.EXCEL.equals(formatoReporteEnum)) {

            try {
                archivoReporte = controladorReporte.reporteCompraExcelGetFile();
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (formatoReporteEnum.PDF.equals(formatoReporteEnum)) {
            try {
                archivoReporte = controladorReporte.reporteCompraPdfGetFile(panelPadre);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        archivosAdjuntos.put("reporteCompra." + formatoReporteEnum.getExtension(), archivoReporte.getPath());

    }

    private void generarReporteRetenciones(FormatoReporteEnum formatoReporteEnum, Map<String, String> archivosAdjuntos) {
        ControladorReporteRetencion controladorReporte = new ControladorReporteRetencion(
                null,
                new java.sql.Date(getCmbFechaInicial().getDate().getTime()),
                new java.sql.Date(getCmbFechaFinal().getDate().getTime()),
                null,
                null,
                null,
                (ComprobanteEntity.ComprobanteEnumEstado) getCmbTipoEstadoReporte().getSelectedItem(),session.getEmpresa());
        controladorReporte.generarReporte();

        File archivoReporte = null;
        if (formatoReporteEnum.EXCEL.equals(formatoReporteEnum)) {

            try {
                archivoReporte = controladorReporte.obtenerArchivoReporteExcel();
            } catch (IOException ex) {
                Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(UtilidadEnvioReportesModel.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (formatoReporteEnum.PDF.equals(formatoReporteEnum)) {
            archivoReporte = controladorReporte.obtenerArchivoReportePdf(panelPadre);
        }

        archivosAdjuntos.put("reporteRetencion." + formatoReporteEnum.getExtension(), archivoReporte.getPath());

    }

    private void generarReporteFacturasYNotaCredito(DocumentoEnum documentoEnum, FormatoReporteEnum formatoReporteEnum, Map<String, String> archivosAdjuntos) {
        //DocumentosConsultarEnum documentoEnum = null;
        String tituloReporte = "";

        if (documentoEnum.equals(DocumentoEnum.FACTURA)) {
            //documentoEnum = DocumentosConsultarEnum.VENTAS;
            tituloReporte = "FacturasReporte";
        } else if (documentoEnum.equals(DocumentoEnum.NOTA_CREDITO)) {
            //documentoEnum = DocumentosConsultarEnum.NOTA_CREDITO;
            tituloReporte = "NotaCreditoReporte";
        }

        ControladorReporteFactura controlador = new ControladorReporteFactura(
                null,
                new java.sql.Date(getCmbFechaInicial().getDate().getTime()),
                new java.sql.Date(getCmbFechaFinal().getDate().getTime()),
                (ComprobanteEntity.ComprobanteEnumEstado) getCmbTipoEstadoReporte().getSelectedItem(),
                false,
                null,
                false,
                true,
                documentoEnum,
                session.getEmpresa(),
                null); //Sucursal

        controlador.generarReporte();

        File archivoReporte = null;
        if (formatoReporteEnum.EXCEL.equals(formatoReporteEnum)) {
            archivoReporte = controlador.obtenerArchivoReporteExcel();
        } else if (formatoReporteEnum.PDF.equals(formatoReporteEnum)) {
            archivoReporte = controlador.obtenerArchivoReportePdf(panelPadre);
        }
        archivosAdjuntos.put(tituloReporte + "." + formatoReporteEnum.getExtension(), archivoReporte.getPath());
    }

    private boolean validacionCampos() {
        if (empleadoSeleccionado == null) {
            DialogoCodefac.mensaje("Advertencia", "Seleccione un empleado para enviar los reportes", DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }

        return true;
    }

    private void listenerFechas() {
        getCmbFechaInicial().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListenerFecha();
            }
        });
    }

    private void actionListenerFecha() {
        Date date = getCmbFechaInicial().getDate();
        cargarDatosAts(date);
    }

    private void cargarDatosAts(Date fechaSeleccion) {
        int anio = UtilidadesFecha.obtenerAnio(new java.sql.Date(fechaSeleccion.getTime()));
        getTxtAnioAts().setValue(anio);
        MesEnum mesEnum = MesEnum.obtenerPorNumero(UtilidadesFecha.obtenerMes(new java.sql.Date(fechaSeleccion.getTime())));
        getCmbMesAts().setSelectedItem(mesEnum);
    }

}
