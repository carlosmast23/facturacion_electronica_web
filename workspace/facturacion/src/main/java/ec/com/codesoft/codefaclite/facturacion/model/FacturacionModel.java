/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.model.DatoAdicionalModel;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteData;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.facturacion.busqueda.EstudianteBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.FacturaBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoInventarioBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProductoInventarioEspecificoDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ProformaBusqueda;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ReferidoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.componentes.ComponenteDatosComprobanteElectronicosInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.utilidades.ComprobanteElectronicoComponente;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import static ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface.ESTADO_EDITAR;
import static ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface.ESTADO_GRABAR;
import ec.com.codesoft.codefaclite.controlador.core.swing.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.controlador.model.LoginArqueoCajalModel;
import ec.com.codesoft.codefaclite.controlador.panel.LoginArqueoCajaDialog;
import ec.com.codesoft.codefaclite.controlador.utilidades.UtilidadReportes;
import ec.com.codesoft.codefaclite.facturacion.busqueda.FacturaBusquedaPresupuesto;
import ec.com.codesoft.codefaclite.facturacion.busqueda.RubroEstudianteBusqueda;
import ec.com.codesoft.codefaclite.facturacion.callback.ClienteFacturaImplComprobante;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.ManagerReporteFacturaFisica;
import ec.com.codesoft.codefaclite.facturacion.other.RenderPersonalizadoCombo;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturacionPanel;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ComprobanteVentaData;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaFisicaDataMap;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador.FacturaModelInterface;
import ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador.FormatoReporteEnum;
import static ec.com.codesoft.codefaclite.controlador.vista.factura.FacturaModelControlador.obtenerDecimalesRedondeo;
import ec.com.codesoft.codefaclite.facturacion.reportdata.DetalleFacturaFisicaData;
import ec.com.codesoft.codefaclite.facturacionelectronica.AlertaComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import static ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService.ETAPA_AUTORIZAR;
import static ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService.ETAPA_ENVIAR;
import static ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService.ETAPA_FIRMAR;
import static ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService.ETAPA_PRE_VALIDAR;
import static ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService.ETAPA_RIDE;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.MetodosEnvioInterface;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataFactura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteFisicoDisenio;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteFisicoDisenioServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DatosAdicionalesComprobanteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import static ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac.CARPETA_DATOS_TEMPORALES;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import ec.com.codesoft.codefaclite.utilidades.formato.ComprobantesUtilidades;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;
import ec.com.codesoft.codefaclite.utilidades.tabla.ButtonColumn;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadIva;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadVarios;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSistema;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSwingX;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.collections4.map.HashedMap;
import org.jdesktop.swingx.prompt.PromptSupport;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.general.ParametrosClienteEscritorio;
import ec.com.codesoft.codefaclite.facturacion.nocallback.FacturaRespuestaNoCallBack;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataLiquidacionCompra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity.TipoEmisionEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexItemEspecifico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Prestamo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.ArqueoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.IngresoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ConfiguracionImpresoraEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModoProcesarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.CarteraParametro;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.FacturaParametro;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.FacturaLoteRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.ReferenciaDetalleFacturaRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesFormularios;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesImpuestos;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.RoundingMode;
import javax.swing.event.ChangeEvent;
import org.jdesktop.swingx.JXTaskPane;

/**
 *
 * @author Carlos
 */
public class FacturacionModel extends FacturacionPanel implements InterfazPostConstructPanel,ComponenteDatosComprobanteElectronicosInterface,FacturaModelInterface,Serializable{

    public static final String NOMBRE_REPORTE_FACTURA_ELECTRONICA="Comprobante de Venta";
    public static final String NOMBRE_REPORTE_FACTURA_INTERNA="Comprobante de Venta Interna";
    //private Persona persona;
    protected Factura factura;
    private Estudiante estudiante;
    //private DefaultTableModel modeloTablaFormasPago;
    private DefaultTableModel modeloTablaDetallesProductos;
    private DefaultTableModel modeloTablaDatosAdicionales;
    private Producto productoSeleccionado;
    private Kardex kardexSeleccionado;
    private RubroEstudiante rubroSeleccionado;
    private Presupuesto presupuestoSeleccionado;
    //private int fila;
    private java.util.Date fechaMax;
    private java.util.Date fechaMin;
    //private Empleado vendedor;
    
    /**
     * Mapa de datos adicionales que se almacenan temporalmente y sirven para la
     * facturacion electronica como por ejemplo el correo
     */
    //private Map<String, String> datosAdicionales;
    
    private FacturaModelControlador controlador;
    
    /**
     * Esta variable utilizo para corregir el comportamiento del listener y evitar ciclos en ese combo
     */
    private Boolean ejecutarListenerComboDocumento=false;
    
    /**
     * Referencia que permite tener cual es el detalle que se va a agregar o a editar
     */
    private FacturaDetalle facturaDetalleSeleccionado;
    
    /**
     * Referencia para saber si estan en modo de editar un detalle o no 
     */
    private Boolean modoEdicionDetalle;

    

    public FacturacionModel() {
        setearFechas();        
        addListenerButtons();
        listenerComponentes();
        addListenerCombos();
        addListenerCamposTexto();
        addListenerTablas();
        addPopUpListener();
        addListenerChecks();
        addListerFechas();
        initComponenesGraficos();
        initModelTablaFormaPago();
        initModelTablaDetalleFactura();
        initModelTablaDatoAdicional();        
        //setearVariablesIniciales();
    }
    
    /**
     * TODO: unir con la parte web para no mostrar información no necesaria para el usuario
     */
    private void setearVisibilidadComponentes()
    {
        try {
            
            getChkPagoConCartera().setVisible(false);
            if(ParametroUtilidades.comparar(session.getEmpresa(),ParametroCodefac.ACTIVAR_CARTERA,EnumSiNo.SI))
            {
                getChkPagoConCartera().setVisible(true);
                
            }  
            
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JXTaskPane panelLateral=buscarPanelCategoriaLateral(FacturacionPanel.NOMBRE_PANEL_LATERAL_REENVIO_CORREO_PROFORMA);
        if(panelLateral!=null)
        {
            panelLateral.setVisible(false);
        }
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) throws ExcepcionCodefacLite {
        this.factura = factura;
        setearValoresFactura();
    }

    
    private void setearFechas() {
        //this.fechaMax = new java.util.Date();
        definirFechaMinFacturacion();

    }

    private void setearVariablesIniciales() {
        agregarFechaEmision();
        this.factura.setSubtotalImpuestos(BigDecimal.ZERO);
        this.factura.setSubtotalSinImpuestos(BigDecimal.ZERO);
        this.factura.setIva(BigDecimal.ZERO);
        this.factura.setTotal(BigDecimal.ZERO);
        //this.subTotalDescuentoConImpuesto = new BigDecimal(0);
        //this.subTotalDescuentoSinImpuesto = new BigDecimal(0);
        this.factura.setDescuentoImpuestos(new BigDecimal(0));
        //this.subtotalSinImpuestosDescuento=BigDecimal.ZERO;
        this.factura.setDescuentoImpuestos(new BigDecimal(0));
        //calcularIva12();
        //datosAdicionales = new HashMap<String, String>();
    }

    private boolean verificarCamposValidados() {
        //bandera para comprobar que todos los campos esten validados
        boolean b = true;
        List<JTextField> camposValidar = new ArrayList<JTextField>();
        //Ingresar los campos para validar 
        camposValidar.add(getTxtValorUnitario());
        camposValidar.add(getTxtCantidad());
        //camposValidar.add(getTxtDescripcion());
        camposValidar.add(getTxtDescuento());
        //Obtener el estado de validacion de los campos
        for (JTextField campo : camposValidar) {
            Color color=campo.getBackground();
            //System.out.println(color.getRed()+"-"+color.getGreen()+"-"+color.getBlue());
            //SeaGlassLookAndFeel.
            if (!compararColores(color,Color.white)) {
                b = false;
            }
        }
        return b;
    }
    
    private boolean compararColores(Color color1,Color color2) //TODO: Artificio para comparar colores cuando se manejan templates no compara directo los objetos
    {
        if(color1.getRed()==color2.getRed() && color1.getGreen()==color2.getGreen() && color1.getBlue()==color2.getBlue())
        {
            return true;
        }
        return false;
    }
    
    
    private List<String> obtenerCorreosFactura()
    {
        ArrayList<String> correos=new ArrayList<String>();
        
        if(factura.getDatosAdicionales()!=null)
        {
            for (FacturaAdicional datoAdicional : factura.getDatosAdicionales()) {
               if(FacturaAdicional.Tipo.TIPO_CORREO.getLetra().equals(datoAdicional.getTipo()))
               {
 
                   correos.add(datoAdicional.getValor());

               }
            }
        }
        return correos;
    }
    
    public void cargarFacturaDesdeProforma(Factura proforma) 
    {
        //Metodo para actualizar las referencias editadas , ene este caso el cliente cuando cambios los datos
        //Todo: Ver como se puede optimizar
        try {
            factura = (Factura) ServiceFactory.getFactory().getUtilidadesServiceIf().mergeEntity(proforma);
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        factura.setId(null); //Con este artificio no tengo que copiar a un nuevo objeto y al grabar me reconoce como un nuevo dato
        //TODO: Este artificio sirve para poder establecer la fecha actual del sistema y no la fecha del pedido
        factura.setFechaEmision(UtilidadesFecha.getFechaHoy());
        factura.setProforma(proforma);

        //Todo: revisar que el cambio sea correcto
        //Actualizo con los nuevo valores del cliente si se modifico y viene de un presupuesto
        //setearValoresCliente();
        cargarDatosBuscar();
        //DialogoCodefac.dialogoPregunta(MensajeCodefacSistema.Preguntas.ELIMINAR_REGISTRO)
    }

    private void addListenerButtons() { 
        
        
        getBtnGenerarCartera().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(!estadoFormulario.equals(ESTADO_EDITAR))
                {
                    DialogoCodefac.mensaje("Solo se puede generar la cartera de una factura generada",DialogoCodefac.MENSAJE_INCORRECTO);
                    return ;
                }
                
                if(!DialogoCodefac.dialogoPregunta("Está seguro que desea genera la cartera ? ",DialogoCodefac.MENSAJE_ADVERTENCIA))
                {
                    return;
                }
                
                try {
                    ServiceFactory.getFactory().getFacturacionServiceIf().grabarCartera(factura);
                    DialogoCodefac.mensaje("Cartera generada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                    DialogoCodefac.mensaje(ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
                } catch (RemoteException ex) {
                    Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        
                       
        getBtnCargarProforma().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProformaBusqueda proformaBusqueda = new ProformaBusqueda(session.getEmpresa());
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(proformaBusqueda);
                buscarDialogoModel.setVisible(true);
                if(buscarDialogoModel.getResultado()!=null)
                {
                    Factura proforma = (Factura) buscarDialogoModel.getResultado();
                    cargarFacturaDesdeProforma(proforma);
                }
            }
        });
        
       
        getBtnBuscarReferenciaContacto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReferidoBusquedaDialogo busquedaDialog = new ReferidoBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedaDialog);
                buscarDialogoModel.setVisible(true);
                Persona referidoTmp=(Persona) buscarDialogoModel.getResultado();
                if(referidoTmp!=null)
                {
                    factura.setReferido(referidoTmp);
                    getTxtReferenciaContacto().setText(referidoTmp.getIdentificacion()+" - "+referidoTmp.getNombresCompletos());
                }
            }
        });
        
        getBtnAgregarDatosAdicionales().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatoAdicionalModel datoAdicional=new DatoAdicionalModel();
                datoAdicional.setVisible(true);
                
                String valor=datoAdicional.valor;
                String campo=datoAdicional.campo;
                
                FacturaAdicional.Tipo tipoEnum=datoAdicional.tipo;
                
                if(factura!=null && valor!=null && tipoEnum!=null)
                {
                    if(tipoEnum.equals(FacturaAdicional.Tipo.TIPO_CORREO))
                    {
                        factura.addDatoAdicional(new FacturaAdicional(
                                valor,
                                FacturaAdicional.Tipo.TIPO_CORREO,
                                ComprobanteAdicional.CampoDefectoEnum.CORREO));
                        //factura.addDatosAdicionalCorreo(valor,FacturaAdicional.Tipo.TIPO_CORREO,ComprobanteAdicional.CampoDefectoEnum.CORREO);
                    }
                    else if(tipoEnum.equals(FacturaAdicional.Tipo.TIPO_GUIA_REMISION))
                    {
                        factura.addDatoAdicional(new FacturaAdicional(
                                valor,
                                FacturaAdicional.Tipo.TIPO_GUIA_REMISION,
                                ComprobanteAdicional.CampoDefectoEnum.GUIA_REMISION));
                    }
                    else
                    {
                        FacturaAdicional dato=new FacturaAdicional();
                        dato.setCampo(campo);
                        dato.setTipo(tipoEnum.getLetra());
                        dato.setNumero(0);
                        dato.setValor(valor);
                                
                        factura.addDatoAdicional(dato);
                    }
                    cargarTablaDatosAdicionales();
                }
                
                //datosAdicionales.put("", title);
            }
        });
        
                
        getBtnBuscarEstudiante().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                //Verificar si ya existen detalles cargados se tienen que eliminar se debe preguntar al usuario
                if(factura!=null && factura.getDetalles()!=null && factura.getDetalles().size()>0)
                {
                    Boolean pregunta=DialogoCodefac.dialogoPregunta("Advertencia","Si selecciona otro estudiante se eliminaron los detalles cargados, desea continuar?",DialogoCodefac.MENSAJE_ADVERTENCIA);
                    if(pregunta)
                    {
                        factura.getDetalles().clear();
                        //Actualizar los detalles vacios en la vista
                        cargarDatosDetalles();
                        
                    }
                    else //Si selecciona no termina la ejecucion
                    {
                        return;
                    }
                }                
                
                EstudianteBusquedaDialogo clienteBusquedaDialogo = new EstudianteBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                Estudiante estudianteTmp=(Estudiante) buscarDialogoModel.getResultado();
                
                if(estudianteTmp!=null)
                {
                    estudiante=estudianteTmp;     
                    setearValoresAcademicos(estudiante);
                    cargarDatosAdicionalesAcademicos();
                    cargarTablaDatosAdicionales();           
                    if(estudiante.getRepresentante()==null && estudiante.getRepresentante2()==null)
                    {
                        DialogoCodefac.mensaje("Advertencia","El estudiante no tienen ningun representante asignado", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    }
                    else
                    {
                        factura.setCliente((Persona) getCmbRepresentante().getSelectedItem());   
                        getCmbRepresentante().setSelectedIndex(getCmbRepresentante().getSelectedIndex());
                        controlador.cargarFormaPago();
                    }
                }
                
                
            }
        });
        
        getBtnBuscarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerBuscarCliente();
            }
        });

        getBtnAgregarFormaPago().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!factura.getDetalles().isEmpty()) {
                    FormaPagoDialogModel dialog = new FormaPagoDialogModel(null, true);
                    dialog.setLocationRelativeTo(null);
                    dialog.setVisible(true);
                    FormaPago formaPago = dialog.getFormaPago();
                    try {

                        factura.addFormaPago(formaPago);
                        controlador.agregarFormaPagoConCartera();
                        verificarSumaFormaPago();

                        cargarFormasPagoTabla();
                    } catch (Exception ex) {
                        System.out.println("No existe forma de pago");
                    }

                }
            }
        });

        getBtnAgregarProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TipoDocumentoEnum tipoDocumentoEnum=controlador.getTipoDocumentoEnumSeleccionado();
                
                switch(tipoDocumentoEnum)
                {
                    case ACADEMICO:
                        agregarRubroAcademico();
                        break;
                    case PRESUPUESTOS:
                        agregarPresupuesto();
                        break;
                    case INVENTARIO:
                        try {
                            agregarProductoInventario(EnumSiNo.SI);
                        } catch (RemoteException ex) {
                            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ServicioCodefacException ex) {
                            DialogoCodefac.mensaje(ex.getMessage(),DialogoCodefac.MENSAJE_ADVERTENCIA);
                            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case LIBRE:
                        agregarProductoSinInventario(EnumSiNo.NO);
                        break;
                
                }
                
            }
        });

        getTblDetalleFactura().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = getTblDetalleFactura().getSelectedRow();
                if(fila>=0)
                {
                    modoEdicionDetalle=true;
                    //setear valores para cargar de nuevo en los campos de la factura
                    FacturaDetalle facturaDetalle = factura.getDetalles().get(fila);
                    getTxtValorUnitario().setText(facturaDetalle.getPrecioUnitario() + "");
                    getTxtCantidad().setText(facturaDetalle.getCantidad() + "");
                    getTxtDescripcion().setText(facturaDetalle.getDescripcion());
                    getTxtDescripcion().setCaretPosition(0);
                    getTxtDescuento().setText(facturaDetalle.getDescuento() + "");
                    getCheckPorcentaje().setSelected(false);
                    getBtnEditarDetalle().setEnabled(true);
                    getBtnAgregarDetalleFactura().setEnabled(false);
                    getBtnAgregarProducto().setEnabled(false);
                    getBtnCrearProducto().setEnabled(false);
                    getCmbIva().setSelectedItem(EnumSiNo.NO);
                }
            }
        });

        getBtnAgregarDetalleFactura().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //System.out.println(panelPadre.validarPorGrupo("detalles"));
                    DocumentoEnum documentoSeleccionado=(DocumentoEnum) getCmbDocumento().getSelectedItem();
                    controlador.agregarDetallesFactura(facturaDetalleSeleccionado,documentoSeleccionado,kardexSeleccionado);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        getTxtValorUnitario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarItemFacturaListener();
            }
        });

        getTxtCantidad().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarItemFacturaListener();               
            }
        });

        
        getBtnEditarDetalle().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerEditar();
            }
        });
        

        getBtnAgregarCliente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerAgregarCliente();
            }
        });

        getBtnCrearProducto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerCrearProducto();
            }
        });

        getjDateFechaEmision().addPropertyChangeListener("date", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if(estadoFormulario.equals(ESTADO_GRABAR))
                {
                    PuntoEmision puntoEmision=(PuntoEmision) getCmbPuntoEmision().getSelectedItem();
                    if(puntoEmision.getTipoFacturacionEnum().equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA))
                    {                    
                        java.util.Date fecha = getjDateFechaEmision().getDate();
                        if (!ComprobarRangoDeFechaPermitido(fecha)) 
                        {
                            DialogoCodefac.mensaje("Advertencia fecha", "La fecha seleccionada esta fuera del rango de autorizaciòn del SRI", DialogoCodefac.MENSAJE_ADVERTENCIA);
                            getjDateFechaEmision().setDate(UtilidadesFecha.getFechaHoy()); //volver a setear la fecha de hoy para que no puedan grabar con un fecha incorrecta
                        }
                    }
                }
            }
        });

        
        getCmbDocumento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getCmbDocumento().getSelectedItem()!=null)
                {
                    cargarSecuencial();
                }
            }
        });
        
        getBtnLimpiarVendedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                factura.setVendedor(null);
                getTxtVendedor().setText("");
                FacturaAdicional facturaAdicional= (FacturaAdicional) factura.obtenerDatoAdicionalPorCampo(ComprobanteAdicional.CampoDefectoEnum.VENDEDOR);
                if(facturaAdicional!=null)
                {
                    factura.getDatosAdicionales().remove(facturaAdicional);
                    cargarTablaDatosAdicionales();
                }
            }
        });

        getBtnBuscarVendedor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmpleadoBusquedaDialogo busquedaDialog = new EmpleadoBusquedaDialogo();
                busquedaDialog.setTipoEnum(Departamento.TipoEnum.Ventas);
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedaDialog);
                buscarDialogoModel.setVisible(true);
                Empleado empleadoTmp = (Empleado) buscarDialogoModel.getResultado();
                if (empleadoTmp != null) {
                    //vendedor=empleadoTmp;
                    factura.setVendedor(empleadoTmp);
                    getTxtVendedor().setText(empleadoTmp.getIdentificacion() + " - " + empleadoTmp.getNombresCompletos());
                    //factura.setVendedor(null);
                    
                    /*factura.addDatoAdicional(new FacturaAdicional(
                            ComprobanteAdicional.CampoDefectoEnum.VENDEDOR.getNombre(), 
                            factura.getVendedor().getNombresCompletos(), 
                            ComprobanteAdicional.Tipo.TIPO_OTRO) {
                    });*/                                  
                                        
                    //factura.addDatoAdicional(ComprobanteAdicional.CampoDefectoEnum.VENDEDOR.getNombre(),factura.getVendedor().getNombresCompletos());
                    cargarTablaDatosAdicionales();
                }

            }
        });
        
        getBtnArqueoCaja().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try 
                {
                    LoginArqueoCajalModel loginArqueoCajalModel = new LoginArqueoCajalModel(session);
                    loginArqueoCajalModel.setVisible(true);
                    
                    if(loginArqueoCajalModel.getUsuario() == null)
                    {
                        DialogoCodefac.mensaje("Advertencia", "Verifique sus credenciales", DialogoCodefac.MENSAJE_ADVERTENCIA);
                        throw new ExcepcionCodefacLite("Verifique las credenciales para la autentificación");
                        
                    }
                    
                    if(loginArqueoCajalModel.getUsuario().getEmpleado() == null)
                    {
                        DialogoCodefac.mensaje("Advertencia", "Necesita tener un empleado ligado", DialogoCodefac.MENSAJE_ADVERTENCIA);
                        throw new ExcepcionCodefacLite("Usuario no contiene ligado un empleado");
                    }
                    
                    btnListenerArqueoCaja(loginArqueoCajalModel.getUsuario());
                } 
                catch (ExcepcionCodefacLite | RemoteException | ServicioCodefacException ex) 
                {
                    Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        
        getBtnAplicarDescuentoGlobal().addActionListener(listenerDescuentoGlobal);

    }
    
    //TODO: Unificar esta parte en el controlador para luego poder usar en la interfaz web    
    private ActionListener listenerDescuentoGlobal=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            String descuentoStr=getTxtDescuentoGlobal().getText();
            
            BigDecimal descuentoLeido=new BigDecimal(descuentoStr);
            
            //Esta variable va a almacenar siempre el descuento antes de impuestos
            //y cuando el usuario quiera poner un descuento incluido iva primero hago la conversion interna            
            
            for (FacturaDetalle detalle : factura.getDetalles()) {
                
                
                BigDecimal descuentoValor = BigDecimal.ZERO;

                if (getChkPorcentajeDescuentoGlobal().isSelected()) {

                    descuentoValor = descuentoLeido.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP).multiply(detalle.getSubtotalSinDescuentos());

                } else {
                    BigDecimal descuentoValorGlobal=BigDecimal.ZERO;
                    if (getCmbIvaDescuento().getSelectedItem().equals(EnumSiNo.NO)) {
                        //Cuando ingresa el valor que no incluye el iva, lo agrego directamente
                        descuentoValorGlobal = descuentoLeido;
                    } else if (getCmbIvaDescuento().getSelectedItem().equals(EnumSiNo.SI)) {
                        BigDecimal ivaDefecto = new BigDecimal(session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO).getValor());
                        descuentoValorGlobal = UtilidadesImpuestos.quitarValorIva(ivaDefecto, descuentoLeido, 6);
                    }
                    
                    //Calcular el descuento individual por cada producto
                    BigDecimal subtotalFactura=factura.getSubtotalImpuestos().add(factura.getDescuentoSinImpuestos());
                    BigDecimal porcentajeDecimalDescuentoGeneral=descuentoValorGlobal.divide(subtotalFactura,6,BigDecimal.ROUND_HALF_UP);
                    
                    descuentoValor = porcentajeDecimalDescuentoGeneral.multiply(detalle.getSubtotalSinDescuentos());

                }

                //Solo grabar con 2 decimales por que el Sri no permite más en los descuentos
                detalle.setDescuento(descuentoValor.setScale(2, BigDecimal.ROUND_HALF_UP));                         
            }
            
            for (FacturaDetalle detalle : factura.getDetalles()) {
                //Solo grabar con 2 decimales por que el Sri no permite más en los descuentos
                detalle.calcularTotalesDetallesFactura();
            }            
            
            cargarDatosDetalles();            
            controlador.cargarTotales();
            
        }
    };
    
    
    private void agregarItemFacturaListener()
    {
        if (getBtnAgregarDetalleFactura().isEnabled()) {
            try {
                //Si esta habilitado el boton de agregar funciona para agregar
                DocumentoEnum documentoSeleccionado=(DocumentoEnum) getCmbDocumento().getSelectedItem();
                controlador.agregarDetallesFactura(facturaDetalleSeleccionado,documentoSeleccionado,kardexSeleccionado);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else //Si no esta habilitado el boton de editar funciona como para editar
        {
            btnListenerEditar();
        }

    }
    
    public void cargarSecuencial()
    {
        DocumentoEnum documentoEnum=(DocumentoEnum) getCmbDocumento().getSelectedItem();
        ComprobanteElectronicoComponente.cargarSecuencial(session.getUsuario(),documentoEnum,session.getSucursal(), getCmbPuntoEmision(), getLblEstablecimiento(), getLblSecuencial());
    }
    
    public void cargarSecuencialConsulta()
    {
        ComprobanteElectronicoComponente.cargarSecuencialConsulta(factura,getCmbPuntoEmision(),getLblEstablecimiento(),getLblSecuencial());
    }
    
    private void btnListenerEditar()
    {
        int fila = getTblDetalleFactura().getSelectedRow();
        if(fila>=0)
        {
            try {
                FacturaDetalle facturaDetalle = factura.getDetalles().get(fila);
                //Buscar la referencia de las variables depedendiendo del modulo seleccionado
                TipoDocumentoEnum tipoDocumentoEnum = controlador.getTipoDocumentoEnumSeleccionado();
                switch (tipoDocumentoEnum) {
                    case LIBRE:
                    case INVENTARIO:
                        Producto producto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                        productoSeleccionado = producto;
                        break;
                        
                    case PRESUPUESTOS:
                        Presupuesto presupuesto=ServiceFactory.getFactory().getPresupuestoServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                        presupuestoSeleccionado = presupuesto;
                        break;
                        
                    case ACADEMICO:
                        RubroEstudiante rubroEstudiante = ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                        rubroSeleccionado = rubroEstudiante;
                        break;
                        
                }
                
                controlador.actualizarFacturaDetalleCatalogo(facturaDetalle);
                
                DocumentoEnum documentoSeleccionado=(DocumentoEnum) getCmbDocumento().getSelectedItem();
                if(controlador.agregarDetallesFactura(facturaDetalle,documentoSeleccionado,kardexSeleccionado))
                {
                    habilitarModoIngresoDatos();
                }
            } catch (RemoteException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void habilitarModoIngresoDatos() {
        getBtnEditarDetalle().setEnabled(false);
        getBtnAgregarDetalleFactura().setEnabled(true);
        getBtnAgregarProducto().setEnabled(true);
        getBtnCrearProducto().setEnabled(true);
    }
    
    private void btnListenerEliminar() {
        int fila = getTblDetalleFactura().getSelectedRow();
        if(fila>=0)
        {
            eliminarDetalleModelo(fila);
            controlador.cargarTotales();
            getBtnEditarDetalle().setEnabled(false);
            getBtnAgregarDetalleFactura().setEnabled(true);
            getBtnAgregarProducto().setEnabled(true);
            getBtnCrearProducto().setEnabled(true);
        }
    }
    
    private void eliminarDetalleModelo(int fila)
    {
        modeloTablaDetallesProductos.removeRow(fila);
        factura.getDetalles().remove(fila);
    }
    
    private void btnListenerCrearProducto() {
        TipoDocumentoEnum tipoDocumento=controlador.getTipoDocumentoEnumSeleccionado();
        
        EnumSiNo manejoInventario=EnumSiNo.NO;
        switch (tipoDocumento) {
            case INVENTARIO:
                manejoInventario=EnumSiNo.SI;
                break;

        }
                
        ObserverUpdateInterface observer = new ObserverUpdateInterface<Producto>() {
            @Override
            public void updateInterface(Producto entity) {
                if (entity != null) {
                    productoSeleccionado = entity;
                    FacturaDetalle facturaDetalle=controlador.crearFacturaDetalle(
                            productoSeleccionado.getValorUnitario(), 
                            productoSeleccionado.getPrecioSinSubsidio(), 
                            productoSeleccionado.getNombre(), 
                            productoSeleccionado.getCodigoPersonalizado(), 
                            productoSeleccionado.getCatalogoProducto(), 
                            entity.getIdProducto(), 
                            null,
                            TipoDocumentoEnum.LIBRE,
                            BigDecimal.ZERO); //TODO: El metodo libre esta de revisar porque no se desde que pantalla estan usando si es con inventario o con no
                    controlador.setearValoresProducto(facturaDetalle);
                    setFacturaDetalleSeleccionado(facturaDetalle);
                    //Establecer puntero en la cantidad para agregar
                    getTxtCantidad().requestFocus();
                    getTxtCantidad().selectAll();
                }
            }
        };
                
        panelPadre.crearDialogoCodefac(observer, VentanaEnum.PRODUCTO, false, new Object[]{manejoInventario,getTxtCodigoDetalle().getText()},this);
        
    }

    private void btnListenerAgregarCliente()
    {
        Object[] parametros={OperadorNegocioEnum.CLIENTE,getTxtCliente().getText()};
        panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<Persona>() {
            @Override
            public void updateInterface(Persona entity) {
                factura.setCliente(entity);
                factura.setSucursal(entity.getEstablecimientos().get(0));
                
                if (factura.getCliente() != null) {
                    cargarCliente(entity.getEstablecimientos().get(0));
                }
            }
        },VentanaEnum.CLIENTE, false,parametros,this);
    }
    
    private void btnListenerBuscarCliente() {
        ClienteEstablecimientoBusquedaDialogo clienteBusquedaDialogo = new ClienteEstablecimientoBusquedaDialogo(session);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        //factura.setCliente((Persona) buscarDialogoModel.getResultado());        
        cargarCliente((PersonaEstablecimiento) buscarDialogoModel.getResultado());        
    }
    
        
    
    
    private void cargarCliente(PersonaEstablecimiento cliente)
    {
        if (cliente != null) {
            factura.setCliente(cliente.getPersona());
            factura.setSucursal(cliente);
            //Elimino datos adicionales del anterior cliente si estaba seleccionado
            //factura.eliminarTodosDatosAdicionales();
            
            controlador.cargarFormaPago();
            setearValoresCliente();
            controlador.cargarDatosAdicionales(factura);
            cargarTablaDatosAdicionales();
            getTxtCodigoDetalle().requestFocus();
            
        };
    }
    
    private boolean verificaDatoComboRepresentante(Persona persona)
    {
        DefaultComboBoxModel modelo=(DefaultComboBoxModel) getCmbRepresentante().getModel();
        
        if(modelo.getIndexOf(persona)<0)
        {
            return false;
        }
        return true;
    }
    
    private void agregarPresupuesto()
    {
        FacturaBusquedaPresupuesto presupuestoDialog=null;
        if(getChkFiltroPresupuestoCliente().isSelected())
        {
            presupuestoDialog = new FacturaBusquedaPresupuesto(factura.getCliente());
        }
        else
        {
            presupuestoDialog = new FacturaBusquedaPresupuesto();
        }
        
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(presupuestoDialog);
        buscarDialogoModel.setVisible(true);

        Presupuesto presupuestoTmp = (Presupuesto) buscarDialogoModel.getResultado();

        if (presupuestoTmp != null) {

            if(verificarExistePresupuestoAgregado(presupuestoTmp))
            {
                 DialogoCodefac.mensaje("Advertencia","EL presupuesto ya esta agregado, no se puede agregar nuevamente",DialogoCodefac.MENSAJE_ADVERTENCIA);
                 return;
            }            
            presupuestoSeleccionado=presupuestoTmp;
            
            String descripcion="P"+presupuestoSeleccionado.getId()+" OT"+presupuestoSeleccionado.getOrdenTrabajoDetalle().getOrdenTrabajo().getId()+"  "+presupuestoSeleccionado.getDescripcion();
            FacturaDetalle facturaDetalle=controlador.crearFacturaDetalle(
                    presupuestoSeleccionado.getTotalVenta(), 
                    null, //No tiene valor del subsidio
                    descripcion, 
                    presupuestoSeleccionado.getId().toString(), 
                    presupuestoSeleccionado.getCatalogoProducto(), 
                    presupuestoSeleccionado.getId(),
                    null,
                    controlador.getTipoDocumentoEnumSeleccionado(),
                    BigDecimal.ZERO);
            
            controlador.setearValoresProducto(facturaDetalle);
            //controlador.setearValoresProducto(presupuestoSeleccionado.getTotalVenta(),descripcion,presupuestoSeleccionado.getId().toString(),presupuestoSeleccionado.getCatalogoProducto());
            for (PersonaEstablecimiento establecimiento : presupuestoSeleccionado.getPersona().getEstablecimientos()) {
                cargarCliente(establecimiento);
                break;
            }
            
        }
    }
    
    private void agregarRubroAcademico() 
    {
        RubroEstudianteBusqueda rubroBusquedaDialogo = new RubroEstudianteBusqueda(estudiante);
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(rubroBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        RubroEstudiante rubroEstudianteTmp = (RubroEstudiante) buscarDialogoModel.getResultado();

        if (rubroEstudianteTmp != null) {
            //Verificar que no se puedan agregar rubros que ya estan en los detalles para facturar
            if(verificarExisteRubroAgregado(rubroEstudianteTmp))
            {
                DialogoCodefac.mensaje("Advertencia","EL rubro ya esta agregado, no se puede agregar nuevamente",DialogoCodefac.MENSAJE_ADVERTENCIA);
                return;
            }            
            
            rubroSeleccionado=rubroEstudianteTmp;            
            BigDecimal descuentoValor=rubroEstudianteTmp.obtenerValorSaldoDescuento();
            
            FacturaDetalle facturaDetalle=controlador.crearFacturaDetalle(
                    rubroEstudianteTmp.obtenerSaldoIncluidoDescuento(), 
                    null, //Este tipo de valores no tienen subsidio
                    rubroEstudianteTmp.getRubroNivel().getNombre(), 
                    rubroEstudianteTmp.getId().toString(), 
                    rubroEstudianteTmp.getRubroNivel().getCatalogoProducto(), 
                    rubroEstudianteTmp.getId(), 
                    null,
                    controlador.getTipoDocumentoEnumSeleccionado(),
                    descuentoValor);
            controlador.setearValoresProducto(facturaDetalle);
            setFacturaDetalleSeleccionado(facturaDetalle);
        }
        
    }
    
    /**
     * Funcion que verifica si existe un rubro dentro de la lista de la factura
     * @param rubroEstudiante
     * @return 
     */
    private boolean verificarExisteRubroAgregado(RubroEstudiante rubroEstudiante)
    {
        for (FacturaDetalle facturaDetalle : factura.getDetalles()) 
        {
            //Verificar solo los que son de tipo academico
            if(facturaDetalle.getTipoDocumentoEnum().equals(TipoDocumentoEnum.ACADEMICO))
            {
                try {
                    RubroEstudiante rubroEstudianteTmp=ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                    
                    if(rubroEstudianteTmp!=null)
                    {
                        if(rubroEstudianteTmp.equals(rubroEstudiante))
                        {
                            return true;
                        }
                    }                    
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }
    
    private boolean verificarExistePresupuestoAgregado(Presupuesto presupuesto)
    {
        for (FacturaDetalle facturaDetalle : factura.getDetalles()) 
        {
            //Verificar solo los que son de tipo academico
            if(facturaDetalle.getTipoDocumentoEnum().equals(TipoDocumentoEnum.PRESUPUESTOS))
            {
                try {
                    Presupuesto presupuestoTmp=ServiceFactory.getFactory().getPresupuestoServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                    
                    if(presupuestoTmp!=null)
                    {
                        if(presupuestoTmp.equals(presupuesto))
                        {
                            return true;
                        }
                    }                    
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }
    
    private void agregarProductoSinInventario(EnumSiNo manejaInventario) {
            ProductoBusquedaDialogo productoBusquedaDialogo = new ProductoBusquedaDialogo(manejaInventario,session.getEmpresa());
            BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoBusquedaDialogo);
            buscarDialogoModel.setVisible(true);
            productoSeleccionado = (Producto) buscarDialogoModel.getResultado();
            getCmbIva().setSelectedItem(EnumSiNo.NO);    
            controlador.agregarProductoVista(productoSeleccionado,null);
    }
    
    private void agregarProductoInventario(EnumSiNo manejaInventario) throws RemoteException, ServicioCodefacException
    {
        //Bodega activa de venta
        BodegaServiceIf service = ServiceFactory.getFactory().getBodegaServiceIf();
        Bodega bodegaVenta = service.obtenerBodegaVenta(session.getSucursal());
        
        if(bodegaVenta==null)
        {
            throw new ServicioCodefacException("No existe un tipo de Bodega de Venta Configurado");
        }
                
        if(manejaInventario.equals(EnumSiNo.SI))
        {
            ProductoInventarioBusquedaDialogo productoInventarioBusquedaDialogo = new ProductoInventarioBusquedaDialogo(manejaInventario, session.getEmpresa(),bodegaVenta);
            BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(productoInventarioBusquedaDialogo);
            buscarDialogoModel.setVisible(true);
            Kardex kardex=(Kardex) buscarDialogoModel.getResultado();
            kardexSeleccionado = kardex;
            productoSeleccionado=kardexSeleccionado.getProducto();
            
        }
        else if(manejaInventario.equals(EnumSiNo.NO))
        {   
            ProductoBusquedaDialogo busqueda=new ProductoBusquedaDialogo(manejaInventario,session.getEmpresa());
            BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busqueda);
            buscarDialogoModel.setVisible(true);
            productoSeleccionado=(Producto) buscarDialogoModel.getResultado();
            
        }
        
        
        
        
        /**
         * ==========================================================================
         *         FALTA IMPLEMENTAR EL METODO PARA MANEJAR PRODUCTOS INDIVIDUALES
         * ==========================================================================
         */
        int cantidadItemsIndividuales=ServiceFactory.getFactory().getItemEspecificoServiceIf().obtenerCantidadItemsEspecificosPorKardex(productoSeleccionado);
        if(productoSeleccionado.getGarantiaEnum().equals(EnumSiNo.SI) && cantidadItemsIndividuales>0)
        {
            ProductoInventarioEspecificoDialogo dialogoEspecifico=new ProductoInventarioEspecificoDialogo(productoSeleccionado);
            BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(dialogoEspecifico);
            buscarDialogoModel.setVisible(true);
            KardexItemEspecifico kardexItemEspecifico=(KardexItemEspecifico)buscarDialogoModel.getResultado();
            /*
            TODO:Solucion para por el momento agregar el codigo invividual pero falta programar en solo servicios para dar de baja a los productos individuales y que no me aparesca en la lista
            */
            productoSeleccionado.setNombre("["+kardexItemEspecifico.getCodigoEspecifico()+"] "+productoSeleccionado.getNombre());
        }
        //productoSeleccionado = (Producto)resultados[0];
        getCmbIva().setSelectedItem(EnumSiNo.NO);
        
        if(manejaInventario.equals(EnumSiNo.SI))
        {
            controlador.agregarProductoVista(kardexSeleccionado.getProducto(),kardexSeleccionado.getLote());
        }
        else if(manejaInventario.equals(EnumSiNo.NO))
        {   
            controlador.agregarProductoVista(productoSeleccionado,null);
        }
        
    }
    

    
    /*private void agregarProductoVista(Producto productoSeleccionado) {
        if (productoSeleccionado == null) {
            return;
        }
        
        verificarProductoConNotaVentaInterna(productoSeleccionado);
        this.productoSeleccionado=productoSeleccionado;
        
        cargarPrecios(productoSeleccionado);
        String descripcion=productoSeleccionado.getNombre();
        descripcion+=(productoSeleccionado.getCaracteristicas()!=null)?" "+productoSeleccionado.getCaracteristicas():"";
        descripcion=descripcion.replace("\n"," ");
        
        setearValoresProducto(productoSeleccionado.getValorUnitario(),descripcion,productoSeleccionado.getCodigoPersonalizado(),productoSeleccionado.getCatalogoProducto());
        //setearValoresProducto(productoSeleccionado.getValorUnitario(), productoSeleccionado.getNombre()+"",productoSeleccionado.getCodigoPersonalizado(),productoSeleccionado.getCatalogoProducto());
    }*/
    
    
    
    public void cargarPrecios(Producto producto) {
        getCmbPreciosVenta().removeAllItems();
        for (Producto.PrecioVenta precioVenta : producto.obtenerPreciosVenta()) {
            getCmbPreciosVenta().addItem(precioVenta);            
        }
    }
    
    public void validacionesGrabar() throws ExcepcionCodefacLite
    {
        if(session.getMatriz()==null)
        {
            DialogoCodefac.mensaje("Alerta","No tiene configurado un establecimiento principal o matriz", DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("No tiene configurado un establecimiento principal o matriz");
        }
        
        if (!verificarSumaFormaPago()) {
            DialogoCodefac.mensaje("Alerta","Formas de pago erroneas", DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("Formas de pago erroneas");
        }
        
        if(getCmbPuntoEmision().getSelectedItem()==null)
        {
            DialogoCodefac.mensaje("Alerta", "No se puede facturar sin un punto de venta configurado", DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("No se puede facturar sin un punto de venta configurado");
        }

        if (factura.getCliente() == null) {
            DialogoCodefac.mensaje("Alerta", "Necesita seleccionar un cliente", DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("Necesita seleccionar un Cliente");
        }
        
        /*if(!factura.getCliente().validarIdentificacion().equals(Persona.ValidacionCedulaEnum.VALIDACION_CORRECTA))
        {
            DialogoCodefac.mensaje("Error con el cliente", factura.getCliente().validarIdentificacion().getMensaje(), DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("Error con la identificacion del cliente seleccionado");
        }*/

        if (factura.getDetalles().isEmpty()) {
            DialogoCodefac.mensaje("Alerta", "No se puede facturar sin detalles", DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("Necesita seleccionar detalles ");
        }
        
        //Verificar que si consumidor final no permita facturar un valor superior a 200 dolares
        if (Persona.TipoIdentificacionEnum.CLIENTE_FINAL.getIdentificacion().equals(factura.getCliente().getIdentificacion()))
        {
            //Validacion movida al momento de guardar en el servicio
           //if(Persona.TipoIdentificacionEnum.CLIENTE_FINAL.getMontoMaximo().compareTo(factura.getTotal())<0)
           //{
           //    DialogoCodefac.mensaje("Alerta","El Monto no puede ser superior a 200$ para el CLIENTE FINAL",DialogoCodefac.MENSAJE_ADVERTENCIA);
           //    throw new ExcepcionCodefacLite("El Monto no puede ser superior a 200$ para el CLIENTE FINAL");
           //}
           
        }
        else
        {
            DocumentoEnum documentoEnum=(DocumentoEnum) getCmbDocumento().getSelectedItem();
            //Advertir cuando no exista ningun correo para que el usuario pueda ingresar antes de enviar al cliente
            if (!factura.verificarExistenCorreosIngresados() && !documentoEnum.equals(documentoEnum.NOTA_VENTA_INTERNA)) {
                if (!DialogoCodefac.dialogoPregunta("Advertencia", "No esta ningun correo ingresado para informar al cliente.\nDesea continuar de todos modos?", DialogoCodefac.MENSAJE_ADVERTENCIA)) {
                    throw new ExcepcionCodefacLite("Cancelado por el usuario");
                }
            }
        }
        
        Persona.TipoIdentificacionEnum tipoIdentificacionEnum=factura.getCliente().getTipoIdentificacionEnum();
        if(tipoIdentificacionEnum==null)
        {
            DialogoCodefac.mensaje("Alerta", "Cliente no configurado el tipo de identificación", DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("Cliente no configurado el tipo de identificación");
        }
               

    }
    
    /*private ComprobanteDataFactura obtenerComprobanteDataFactura()
    {
        ComprobanteDataFactura comprobanteData = new ComprobanteDataFactura(factura);
        comprobanteData.setMapInfoAdicional(factura.getMapAdicional());
        return comprobanteData;
    }*/
    
    

    @Override
    public void grabar() throws ExcepcionCodefacLite {

        try {
            //ParametrosClienteEscritorio.tipoClienteEnum=ParametrosClienteEscritorio.TipoClienteSwingEnum.REMOTO;
            validacionesGrabar();
            PuntoEmision puntoEmision=(PuntoEmision) getCmbPuntoEmision().getSelectedItem();
            
            Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Esta seguro que desea facturar?", DialogoCodefac.MENSAJE_ADVERTENCIA);
            if (!respuesta) {
                throw new ExcepcionCodefacLite("Cancelacion usuario");
            }
            
            Factura facturaProcesando; //referencia que va a tener la factura procesada para que los listener no pierdan la referencia a la variable del metodo.
            
            FacturacionServiceIf servicio = ServiceFactory.getFactory().getFacturacionServiceIf();
            setearValoresDefaultFactura(CrudEnum.CREAR);
            
            //TODO: Por el momento dejo seteado para grabar distinto para la las liquidaciones de compra porque en verdad en el mismo procedimiento
            if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.LIQUIDACION_COMPRA))
            {
                factura=servicio.grabarLiquidacionCompra(factura);
            }
            else
            {
                CarteraParametro carteraParametro=new CarteraParametro(
                        getChkHabilitarCredito().isSelected(), 
                        (Integer) getTxtDiasCredito().getValue());
                
                
                if(puntoEmision.getTipoFacturacionEnum().equals(TipoEmisionEnum.NORMAL))
                {
                    //List<FacturaParametro> facturasProcesar=new ArrayList<FacturaParametro>();
                    ///facturasProcesar.add(new FacturaParametro(factura, carteraParametro, prestamo));
                           
                    List<FacturaParametro> facturasProcesar =obtenerFacturasManualesProcesar(factura,carteraParametro);
                    FacturaLoteRespuesta respuestaManual=ServiceFactory.getFactory().getFacturacionServiceIf().grabarLote(facturasProcesar);
                    //Imprmir facturas manuales cuando son más de 1 factura o continua con el proceso normal
                    if(facturasProcesar.size()==1)
                    {
                        mostrarErrorFacturaLote(respuestaManual);
                        factura=respuestaManual.procesadosList.get(0);
                    }
                    else if(facturasProcesar.size()>1)
                    {
                        /*if(respuestaManual.procesadosList.size()==0)
                        {
                            String mensaje=UtilidadesLista.castListToString(respuestaManual.noProcesadosList,"\br");
                            DialogoCodefac.mensaje("Error ","No se puede grabar: \nCausa: "+mensaje, DialogoCodefac.MENSAJE_INCORRECTO);            
                            throw new ExcepcionCodefacLite("Error al grabar: "+mensaje);
                        }*/
                        mostrarErrorFacturaLote(respuestaManual);
                        
                        List<JasperPrint> reportesPendientes=new ArrayList<JasperPrint>();
                        for (Factura facturaProcesada : respuestaManual.procesadosList) 
                        {
                           DocumentoEnum documentoEnum=factura.getCodigoDocumentoEnum();
                           JasperPrint jasperPrint=facturaManual(facturaProcesada, documentoEnum, true,true);
                           reportesPendientes.add(jasperPrint);
                           //facturaManual(facturaProcesada,documentoEnum,true);
                        }
                        
                        ConfiguracionImpresoraEnum configuracion=FacturaModelControlador.obtenerConfiguracionImpresora(session);
                        //ReporteCodefac.generarReporteInternalFrame(reporteNuevo, parametros, detalles, panelPadre, "Muestra Previa",configuracion);
                        ReporteCodefac.generarReporteInternalFrame(UtilidadReportes.unificarReportes(reportesPendientes), panelPadre, "Comprobantes de Ventas", configuracion);
                        //UtilidadReportes.visualizarReporteVentanaExterna(UtilidadReportes.unificarReportes(reportesPendientes));
                        return;
                    }
                    
                }
                else                
                {
                    factura=servicio.grabar(factura,crearDatosPrestamo(),carteraParametro);
                    if(session.getTipoLicenciaEnum().equals(TipoLicenciaEnum.GRATIS))
                    {
                        DialogoCodefac.mensaje(new CodefacMsj("Gracias por utilizar la versión GRATUITA de CODEFAC para facturar, si te gusto nuestro sistema puedes contratar planes de pago con SOPORTE y MÁS CARACTERISTICAS. \n Presione aceptar para continuar con el proceso.", CodefacMsj.TipoMensajeEnum.CORRECTO));
                    }
                }
            }
            
            facturaProcesando = factura;
            
            
            //Si la factura en manual no continua el proceso de facturacion electronica
            //TODO: Ver si esta logica va incluida en el servidor
            //if(session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).getValor().equals(ComprobanteEntity.TipoEmisionEnum.NORMAL.getLetra()))
            //TODO: Ver como hacer las facturas fisicas
            DocumentoEnum documentoEnum=factura.getCodigoDocumentoEnum();
            if(documentoEnum.equals(DocumentoEnum.NOTA_VENTA_INTERNA))
            {
                DialogoCodefac.mensaje("Correcto", "La nota de venta interna se grabo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
                //facturaManual(factura.getCodigoDocumentoEnum());
                FacturaModelControlador.imprimirComprobanteVenta(facturaProcesando,NOMBRE_REPORTE_FACTURA_INTERNA,true,session,panelPadre);
            }
            else
            {
                
                if((documentoEnum.equals(DocumentoEnum.LIQUIDACION_COMPRA) || documentoEnum.equals(DocumentoEnum.FACTURA)) && puntoEmision.getTipoFacturacionEnum().equals(TipoEmisionEnum.ELECTRONICA))
                {
                    facturarElectricamente(facturaProcesando);
                }
                else if(puntoEmision.getTipoFacturacionEnum().equals(TipoEmisionEnum.NORMAL))
                {
                    facturaManual(factura,documentoEnum,true,false);
                    DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
                }
                
            }
            
     
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error ","No se puede grabar: \nCausa: "+ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);            
            throw new ExcepcionCodefacLite("Error al grabar: "+ex.getMessage());
            
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error ",ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);            
            throw new ExcepcionCodefacLite("Error al grabar: "+ex.getMessage());
        }
        
        actualizaCombosPuntoVenta(); //Metodo para actualizar los secuenciales de los poutnos de venta en cualquier caso
    }
    
    private void mostrarErrorFacturaLote(FacturaLoteRespuesta respuestaManual) throws ExcepcionCodefacLite
    {
        if (respuestaManual.procesadosList.size() == 0) 
        {
            String mensaje ="";
            for (FacturaLoteRespuesta.Error error : respuestaManual.noProcesadosList) 
            {
                mensaje+=error.error+"\n";
            }
            //String mensaje = UtilidadesLista.castListToString(respuestaManual.noProcesadosList, "\br");
            DialogoCodefac.mensaje("Error ", "No se puede grabar: \nCausa: " + mensaje, DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Error al grabar: " + mensaje);
        }
    }
    
    private List<FacturaParametro> obtenerFacturasManualesProcesar(Factura factura,CarteraParametro carteraParametro) throws ServicioCodefacException
    {
        List<FacturaParametro> respuestaList=new ArrayList<FacturaParametro>();
        
        String numeroDetallesTxt=ParametroUtilidades.obtenerValorParametro(session.getEmpresa(),ParametroCodefac.NUMERO_MAXIMO_DETALLES_FACTURA);
        if(numeroDetallesTxt!=null)
        {
            Integer numeroMaxDetalleFactura=Integer.parseInt(numeroDetallesTxt);
            List<Factura> facturasDividas=factura.dividirFactura(numeroMaxDetalleFactura);
            for (Factura facturaTmp : facturasDividas) 
            {
                respuestaList.add(new FacturaParametro(facturaTmp,carteraParametro, crearDatosPrestamo()));
            }        
        }
        else
        {
            throw new ServicioCodefacException("Falta configurar el número de detalles para facturas manuales");
        }
        return respuestaList;
    }
    
    private Prestamo crearDatosPrestamo()
    {
        //Solo cosntruir si esta seleccionado la opcion
        if(getChkActivarFinanciamiento().isSelected())
        {
            Prestamo prestamo=new Prestamo();
            prestamo.setCuotaInicial(new BigDecimal(getTxtFinanciamientoEntrada().getText()));
            prestamo.setPlazo((Integer) getTxtFinanciamientoNumeroCuotas().getValue());
            prestamo.setTazaInteres(new BigDecimal(getTxtFinanciamientoTarifa().getText()));
            prestamo.setDiaPago((Integer) getTxtFinanciamientoDiaPago().getValue());
            return prestamo;
        }
        return null;
    }
    
    /*private void generarNotaVentaInterna()
    {
        as
    }*/
    
    private void facturarElectricamente(Factura facturaProcesando) throws RemoteException
    {
        ComprobanteDataInterface comprobanteData = FacturaModelControlador.obtenerComprobanteData(facturaProcesando);
        //comprobanteData.setMapInfoAdicional(getMapAdicional(factura));
        //ParametrosClienteEscritorio.tipoClienteEnum=ParametrosClienteEscritorio.TipoClienteSwingEnum.REMOTO;
        
        ClienteInterfaceComprobante cic = new ClienteFacturaImplComprobante(this, facturaProcesando, true);
        ComprobanteServiceIf comprobanteServiceIf = ServiceFactory.getFactory().getComprobanteServiceIf();

        //if (ServiceFactory.getFactory().getComprobanteServiceIf().verificarDisponibilidadSri(session.getEmpresa())) {
            //Boolean repuestaFacturaElectronica = DialogoCodefac.dialogoPregunta("Correcto", "La factura se grabo correctamente,Desea autorizar en el SRI ahora?", DialogoCodefac.MENSAJE_CORRECTO);
            Boolean repuestaFacturaElectronica = true;

            //Si quiere que se procese en ese momento le ejecuto el proceso normal
            if (repuestaFacturaElectronica) {
                //Verificar que existe comunicacion con el Sri
                cic = new ClienteFacturaImplComprobante(this, facturaProcesando, false);
                if (ParametrosClienteEscritorio.tipoClienteEnum.equals(ParametrosClienteEscritorio.TipoClienteSwingEnum.REMOTO)) {
                    cic = null;
                }

                comprobanteServiceIf.procesarComprobante(comprobanteData, facturaProcesando, session.getUsuario(), cic);

                //TODO: Ver si se une esta parte con la parte superior porque se repite
                if (ParametrosClienteEscritorio.tipoClienteEnum.equals(ParametrosClienteEscritorio.TipoClienteSwingEnum.REMOTO)) {
                    FacturaRespuestaNoCallBack respuestaNoCallBack = new FacturaRespuestaNoCallBack(factura, this, true);
                    respuestaNoCallBack.iniciar();
                }

            } else {
                //TODO: Ver si se une esta parte con la parte superior porque se repite
                if (ParametrosClienteEscritorio.tipoClienteEnum.equals(ParametrosClienteEscritorio.TipoClienteSwingEnum.REMOTO)) {
                    cic = null;
                }

                //Solo genera el pdf pero no envia al SRI
                comprobanteServiceIf.procesarComprobanteOffline(comprobanteData, facturaProcesando, session.getUsuario(), cic);
                DialogoCodefac.mensaje("Correcto", "El comprobante esta firmado , no se olvide de enviar al SRI en un periodo maximo de 48 horas", DialogoCodefac.MENSAJE_CORRECTO);

                //TODO: Ver si se une esta parte con la parte superior porque se repite
                if (ParametrosClienteEscritorio.tipoClienteEnum.equals(ParametrosClienteEscritorio.TipoClienteSwingEnum.REMOTO)) {
                    FacturaRespuestaNoCallBack respuestaNoCallBack = new FacturaRespuestaNoCallBack(factura, this, true);
                    respuestaNoCallBack.iniciar();
                }

            }

        

        //=====================> Imprimir comprobante de venta <==============================//
        //imprimirComprobanteVenta(factura);
    }
    
    private JasperPrint facturaManual(Factura factura,DocumentoEnum documentoEnum,Boolean activarConfiguracionesImpresion,Boolean getJasperPrint) throws ServicioCodefacException
    {
        try 
        {
            InputStream reporteOriginal = null;
            if(documentoEnum.NOTA_VENTA_INTERNA.equals(documentoEnum))
            {                   
                
            }else if (documentoEnum.NOTA_VENTA.equals(documentoEnum)) {
                reporteOriginal = RecursoCodefac.JASPER_COMPROBANTES_FISICOS.getResourceInputStream("nota_venta.jrxml");
            } else if(documentoEnum.FACTURA.equals(documentoEnum)) {
                reporteOriginal = RecursoCodefac.JASPER_COMPROBANTES_FISICOS.getResourceInputStream("factura_fisica.jrxml");
            }
            
            /**
             * =========> ESTE RESTO DE CODIGO SIRVE PARA CONSULTAR LAS PLANTILLAS DE LAS FACTURAS Y NOTAS DE VENTAS <=============
             */
            ManagerReporteFacturaFisica manager = new ManagerReporteFacturaFisica(reporteOriginal);
            ComprobanteFisicoDisenioServiceIf servicioComprobanteDisenio = ServiceFactory.getFactory().getComprobanteFisicoDisenioServiceIf();
            
            /**
             * Consultando el documento para poder editar como la factura y nota de venta 
             */
            ComprobanteFisicoDisenio documento = servicioComprobanteDisenio.buscarPorCodigoDocumento(documentoEnum.getCodigo());
            manager.setearNuevosValores(documento);
            
            InputStream reporteNuevo = manager.generarNuevoDocumento();
            
            Boolean imprimirTitulos=false;
            if(ParametroUtilidades.comparar(session.getEmpresa(),ParametroCodefac.MOSTRAR_TITULO_FACT_FISICA,EnumSiNo.SI))
            {
                imprimirTitulos=true;
            }
            
            Map<String, Object> parametros = getParametroReporte(factura,documentoEnum,imprimirTitulos);
            
            //TODO: Metodo remporal hasta ver una mejor manera
            agregarGuiaRemisionManual(factura, parametros);
            
            //Llenar los datos de los detalles
            List<DetalleFacturaFisicaData> detalles = new ArrayList<DetalleFacturaFisicaData>();
            
            
            //TODO: Agregado cabezera temporal
            if(imprimirTitulos)
            {
                DetalleFacturaFisicaData detalleCabecera = new DetalleFacturaFisicaData();
                detalleCabecera.setCantidad("<b>Cantidad</b>");
                detalleCabecera.setCodigoPrincipal("<b>Código</b>");
                detalleCabecera.setDescripcion("<b>Descripción</b>");
                detalleCabecera.setDescuentoDetalle("<b>Descuento</b>");
                detalleCabecera.setValorTotal("<b>Total</b>");
                detalleCabecera.setValorUnitario("<b>Val. Unit</b>");  
                detalleCabecera.setIvaDetalle("<b>IVA</b>");
                detalles.add(detalleCabecera);
                
            }
            
            for (FacturaDetalle detalleFactura : factura.getDetalles()) {
                DetalleFacturaFisicaData detalle = new DetalleFacturaFisicaData();
                
                Integer numeroDecimales=FacturaModelControlador.obtenerDecimalesRedondeo(session.getEmpresa());
                Integer numeroDecimalesCantidad=FacturaModelControlador.obtenerCantidadProducto(session.getEmpresa());
                
                detalle.setCantidad(detalleFactura.getCantidad().setScale(numeroDecimalesCantidad, RoundingMode.HALF_UP) + "");
                String descripcionConSaltosDeLinea=detalleFactura.getDescripcion().replace("\n", "<br>");
                detalle.setDescripcion(descripcionConSaltosDeLinea);                
                detalle.setValorTotal(detalleFactura.getTotal().setScale(numeroDecimales, RoundingMode.HALF_UP) + "");
                detalle.setValorUnitario(detalleFactura.getPrecioUnitario().setScale(numeroDecimales,RoundingMode.HALF_UP) + "");
                detalle.setCodigoPrincipal(obtenerCodigoProducto(detalleFactura));
                detalle.setDescuentoDetalle(detalleFactura.getDescuento().toString()); 
                detalle.setIvaDetalle(detalleFactura.getIva()+"");
                detalles.add(detalle);
            }
            
            ConfiguracionImpresoraEnum configuracion = null;
            if (activarConfiguracionesImpresion) {
                configuracion = FacturaModelControlador.obtenerConfiguracionImpresora(session);
            }
            
            //TODO: Cambio temporal para la Julieta
            if(getJasperPrint.booleanValue())
            {
                return ReporteCodefac.generarReporteInternalFrameJasperPrint(reporteNuevo, parametros, detalles, panelPadre, "Muestra Previa",configuracion);
            }            
            ReporteCodefac.generarReporteInternalFrame(reporteNuevo, parametros, detalles, panelPadre, "Muestra Previa",configuracion);
            
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            throw  ex; //Relanza el error al proceso principal
            
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
        }
        return null;
    
    }
    
    //TODO: Metodo temporal para poder imprimir una guia de remision cuando tiene ligado posteriormente 
    @Deprecated
    public void agregarGuiaRemisionManual(Factura factura,Map<String, Object> parametros) throws ServicioCodefacException, RemoteException
    {
        List<GuiaRemision> guiaRemisionList= ServiceFactory.getFactory().getDestinatarioGuiaRemisionServiceIf().buscarGuiaRemisionPorFactura(factura);
                
        List<String> secuencialesGuiaRemision=new ArrayList<String>();
        for (GuiaRemision guiaRemision : guiaRemisionList) {
            secuencialesGuiaRemision.add(guiaRemision.getSecuencial()+"");
        }
        
        String guiasRemisionTxt=UtilidadesLista.castListToString(secuencialesGuiaRemision," - ");
        
        if(guiasRemisionTxt!=null)
        {
            parametros.put("guiaRemision",guiasRemisionTxt);
        }        
    }
    
    
    
    
    public Map<String, Object> getParametroReporte(Factura factura,DocumentoEnum documento,Boolean imprimirTitulos)
    {
        FacturaFisicaDataMap dataMap=new FacturaFisicaDataMap(imprimirTitulos);
        String ivaStr = session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO).valor;
        return dataMap.getMap(factura, documento, ivaStr);
        /*Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("fechaEmision","Fecha: "+factura.getFechaEmision().toString());
        parametros.put("razonSocial","Razón Social: "+ factura.getRazonSocial());
        parametros.put("direccion","Dirección: "+ factura.getDireccion());
        parametros.put("telefono","Telefono: "+ factura.getTelefono());
        parametros.put("correoElectronico", (factura.getCliente().getCorreoElectronico() != null) ? factura.getCliente().getCorreoElectronico() : "");
        parametros.put("identificacion","Identificación: "+ factura.getIdentificacion());
        
        //Datos cuando es una nota de venta
        if(DocumentoEnum.NOTA_VENTA.equals(documento))
        {
        parametros.put("subtotal","Subtotal: "+ factura.getSubtotalImpuestos().add(factura.getSubtotalSinImpuestos()).toString());
        parametros.put("descuento","Descuento: "+ factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()).toString());
        parametros.put("total","Total: "+ factura.getTotal() + "");
        }
        else
        {   //Datos cuando es una factura
        parametros.put("subtotalAntesImpuestos","Subtotal 1: "+ factura.getSubtotalImpuestos().add(factura.getSubtotalSinImpuestos()).toString());
        parametros.put("subtotalImpuesto","Subtotal 2: "+ factura.getSubtotalImpuestos().toString());
        parametros.put("subtotalSinImpuesto","Subtotal 3: "+ factura.getSubtotalSinImpuestos().toString());
        parametros.put("descuento","Descuento: "+ factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()).toString());
        parametros.put("subtotalConDescuento","Subt Desc: "+ factura.getSubtotalImpuestos().add(factura.getSubtotalSinImpuestos()).subtract((factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()))).toString());
        parametros.put("valorIva","Val Iva: "+ factura.getIva().toString());
        parametros.put("total","Total: "+ factura.getTotal() + "");
        String ivaStr = session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO).valor;
        parametros.put("iva","Iva: "+ ivaStr);
        
        }
        
        //Agregar forma de pago
        parametros.putAll(getMapFormaPagoReporteFacturaFisica(factura));
        
        return parametros;*/
        //return null;
    }
    
    /**
     * TODO: Parametrizar los formatos de las formas de pago porque ahorita esta quemado en el codigo
     * TODO: Ver si toda esta parte la puede construir el servidor
     * @param venta
     * @return 
     */
    /*private Map<String,Object> getMapFormaPagoReporteFacturaFisica(Factura venta)
    {
        Map<String, Object> parametros = new HashMap<String, Object>();
        
        if(venta.getFormaPagos()!=null && venta.getFormaPagos().size()>0)
        {
            for (FormaPago formaPago : venta.getFormaPagos()) 
            {
                //FormaPago formaPago=venta.getFormaPagos().get(0);
                if(formaPago.getSriFormaPago().getAlias().equals("Efectivo"))
                {
                    parametros.put("formaPagoEfectivo","X");

                }else if(formaPago.getSriFormaPago().getAlias().equals("Otros"))
                {
                    //parametros.put("formaPagoCheque","X");
                    parametros.put("formaPagoOtros","X");

                }else if(formaPago.getSriFormaPago().getAlias().equals("Dinero electrónico"))
                {
                    parametros.put("formaPagoDineroElec","X");
                }else if(formaPago.getSriFormaPago().getAlias().equals("Tarjeta crédito"))
                {
                    parametros.put("formaPagoTarjetaCred","X");
                }else
                {
                    parametros.put("formaPagoOtros","X");
                }
            }
            
        }
        else
        {
            parametros.put("formaPagoEfectivo","X");
        }
        return parametros;
    }*/

    @Override
    public void editar() throws ExcepcionCodefacLite {
        if(!factura.getEstadoEnum().equals(ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR) && !factura.getCodigoDocumentoEnum().equals(factura.getCodigoDocumentoEnum().PROFORMA))
        {
            DialogoCodefac.mensaje("Advertencia","La factura no se pueden modificar ",DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("cancelar el evento editar");
        }
        
        if(!DialogoCodefac.dialogoPregunta(new CodefacMsj("Solo debe editar las ventas con supervición de una persona de SOPORTE.\nDesea continuar de todos modos? ", CodefacMsj.TipoMensajeEnum.ERROR)))
        {
            throw new ExcepcionCodefacLite("cancelar el evento editar");
        }
        
        try {
            setearValoresDefaultFactura(CrudEnum.EDITAR);
            ServiceFactory.getFactory().getFacturacionServiceIf().editarProforma(factura);
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
            
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje(ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite(ex.getMessage());
            
        }
        //ServiceFactory.getFactory().getComp
        
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite,RemoteException {
        //Eliminar solo si esta en modo editar
        if (estadoFormulario.equals(ESTADO_EDITAR)) {

            //TODO: Para los documentos legales toca separar para electronicas y fisicas
            if(factura.getCodigoDocumentoEnum().getDocumentoLegal())
            {
                //Verificar que la factura no tenga notas de credito aplicando porque no podria eliminar si se da esta condicion
                if (!factura.getEstadoNotaCreditoEnum().equals(Factura.EstadoNotaCreditoEnum.SIN_ANULAR)) {
                    DialogoCodefac.mensaje(MensajeCodefacSistema.FacturasMensajes.ERROR_ELIMINAR_AFECTA_NOTA_CREDITO);
                    throw new ExcepcionCodefacLite("error");
                }

                ComprobanteElectronicoComponente.eliminarComprobante(this, factura, getLblEstadoFactura());
            }else //Cuando no es un documento legal elimino directamente
            {
                //todo:verificar si tengo que poner el estado o eso se lo hace automaticamente
                if(DialogoCodefac.dialogoPregunta("Advertencia","Está seguro que quiere eliminar el registro ?",DialogoCodefac.MENSAJE_ADVERTENCIA))
                {                                        
                    try {
                        ServiceFactory.getFactory().getFacturacionServiceIf().eliminarFactura(factura);
                    } catch (ServicioCodefacException ex) {
                        DialogoCodefac.mensaje(ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
                        Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);                        
                        throw new ExcepcionCodefacLite(ex.getMessage());
                        
                    }
                }
                else
                {
                    throw new ExcepcionCodefacLite("Cancelar evento eliminar porque no esta en modo editar");
                }
            }
            
            
            
        }else{
            throw new ExcepcionCodefacLite("Cancelar evento eliminar porque no esta en modo editar");
        }
    }

    @Override
    public void imprimir() {
        if (this.factura != null && estadoFormulario.equals(ESTADO_EDITAR)) {
            
            if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.FACTURA) || factura.getCodigoDocumentoEnum().equals(DocumentoEnum.LIQUIDACION_COMPRA))
            {
                if(factura.getTipoFacturacionEnum().equals(TipoEmisionEnum.ELECTRONICA))
                {
                    try {
                        String claveAcceso = this.factura.getClaveAcceso();
                        if(claveAcceso==null)
                        {
                            DialogoCodefac.mensaje("Advertencia","No se puede generar el reporte porque no tiene clave de acceso",DialogoCodefac.MENSAJE_ADVERTENCIA);
                            return;
                        }
                        
                        String[] opciones = {"Ride", "Comprobante Venta", "Cancelar"};
                        int opcionSeleccionada = DialogoCodefac.dialogoPreguntaPersonalizada("Reporte", "Por favor seleccione el tipo de reporte?", DialogoCodefac.MENSAJE_CORRECTO, opciones);
                        switch (opcionSeleccionada) 
                        {
                            case 0: //opcion para RIDE
                                byte[] byteReporte= ServiceFactory.getFactory().getComprobanteServiceIf().getReporteComprobante(claveAcceso,factura.getEmpresa());
                                JasperPrint jasperPrint=(JasperPrint) UtilidadesRmi.deserializar(byteReporte);
                                
                                panelPadre.crearReportePantalla(jasperPrint, factura.getPreimpreso());
                                break;

                            case 1: //opcion para comprobantes Venta
                                FacturaModelControlador.imprimirComprobanteVenta(factura, NOMBRE_REPORTE_FACTURA_ELECTRONICA,false,session,panelPadre);
                                break;

                            case 2: //Cancelar
                                break;
                        }
                        
                        //int opcionSeleccionada = DialogoCodefac.dialogoPreguntaPersonalizada("Alerta", "Por favor seleccione una opción?", DialogoCodefac.MENSAJE_ADVERTENCIA, opciones);

                    } catch (RemoteException ex) {
                        Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if (factura.getTipoFacturacionEnum().equals(TipoEmisionEnum.NORMAL))
                {
                    /**
                     * Imprimir facturas manuales
                     */
                    try {
                        facturaManual(factura,factura.getCodigoDocumentoEnum(),false,false);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }else if(factura.getCodigoDocumentoEnum().equals(DocumentoEnum.NOTA_VENTA_INTERNA))
            {
                FacturaModelControlador.imprimirComprobanteVenta(factura, FacturacionModel.NOMBRE_REPORTE_FACTURA_INTERNA,false,session,panelPadre);
            }
        }
    }
    

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public InterfaceModelFind getBusquedaInterface()
    {
        FacturaBusqueda facturaBusqueda = null;
        ParametroCodefac siNofiltrarFacturaPorUsuario = session.getParametrosCodefac().get(ParametroCodefac.FILTRAR_FACTURAS_POR_USUARIO);
        EnumSiNo enumSiNo = EnumSiNo.getEnumByLetra((siNofiltrarFacturaPorUsuario != null ) ? siNofiltrarFacturaPorUsuario.getValor() : null);
        if(enumSiNo != null && enumSiNo.equals(EnumSiNo.SI)){
            facturaBusqueda = new FacturaBusqueda(session.getSucursal(), session.getUsuario());
        }else{
            facturaBusqueda = new FacturaBusqueda(session.getSucursal());
        }
        
        return facturaBusqueda;
    }
    
    private void cargarDatosBuscar()
    {
        TipoDocumentoEnum tipoReferenciaEnum =null;
        ///Cargar los datos de la factura segun el tipo de datos del primer detalle
        if(factura.getDetalles().size()==0)
        {
            DialogoCodefac.mensaje(new CodefacMsj("La factura no tiene detalles", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return;
        }
        else
        {
            tipoReferenciaEnum = factura.getDetalles().get(0).getTipoDocumentoEnum();
        }        
        
        controlador.setTipoDocumentoEnumSeleccionado(tipoReferenciaEnum);
        //getCmbTipoDocumento().setSelectedItem(tipoReferenciaEnum);
        seleccionarPanelTipoDocumento(tipoReferenciaEnum);

        switch (tipoReferenciaEnum) {
            case ACADEMICO:
                try {
                    //getCmbTipoDocumento().setSelectedItem(tipoReferenciaEnum)                        

                    FacturaAdicional facturaAdicional = buscarCampoAdicionalPorNombre(DatosAdicionalesComprobanteEnum.CODIGO_ESTUDIANTE.getNombre());
                    Long estudianteInscritoId = Long.parseLong(facturaAdicional.getValor());
                    estudiante = ServiceFactory.getFactory().getEstudianteServiceIf().buscarPorId(estudianteInscritoId);

                    //setearValoresAcademicos(estudiante);
                    getTxtEstudiante().setText(estudiante.getNombreCompleto());
                    getCmbRepresentante().removeAllItems();
                    getCmbRepresentante().addItem(factura.getCliente());

                } catch (RemoteException ex) {
                    Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;

            case PRESUPUESTOS:
                //getTxtClientePresupuesto().setText(factura.getCliente().toString());
                //getLblNombresClientePresupuesto().setText(factura.getCliente().toString());                    
                break;

            case INVENTARIO:
            case LIBRE:
                //getCmbTipoDocumento().getSelectedItem().equals(TipoDocumentoEnum.INVENTARIO);
                setearValoresCliente();
                break;

        }
        
        cargarDatosDetalles();
        controlador.limpiarDetalleFactura();
        controlador.cargarTotales();
        cargarValoresAdicionales();
        cargarFormasPagoTabla();
        cargarTablaDatosAdicionales();
        
        getPnlDatosAdicionales().habiliarBotonAutorizar();
        
        
            
        //verificarActivarBtnCargarProforma(false);
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        InterfaceModelFind busquedaInterface=getBusquedaInterface();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(busquedaInterface);
        buscarDialogoModel.setVisible(true);
        Factura facturaTmp = (Factura) buscarDialogoModel.getResultado();
        
        if (facturaTmp != null) {
            limpiar();
            this.factura = facturaTmp;
            cargarDatosBuscar();
            validacionesParaEditar();
           
        } else {
            throw new ExcepcionCodefacLite("cancelar ejecucion");
        }
    }
    
    private FacturaAdicional buscarCampoAdicionalPorNombre(String nombre)
    {
        for(FacturaAdicional facturaAdicional : factura.getDatosAdicionales())
        {
            if(facturaAdicional.getCampo().equals(nombre))
            {
                return facturaAdicional;
            }
        }
        return null;
    }

    @Override
    public void limpiar() {
        this.factura = new Factura();
        modoEdicionDetalle=false;
        //this.for=new ArrayList<FormaPago>();
        this.factura.setDetalles(new ArrayList<FacturaDetalle>());

        //Setear los valores de la empresa 
        //getLblRuc().setText(session.getEmpresa().getIdentificacion());
        //getLblDireccion().setText(session.getSucursal().getDirecccion());
        //getLblTelefonos().setText(session.getMatriz().getTelefono());
        //getLblNombreComercial().setText(session.getEmpresa().getNombreLegal());
        FacturacionServiceIf servicio = ServiceFactory.getFactory().getFacturacionServiceIf();
        getLblEstadoFactura().setText("Procesando");

        //datosAdicionales = new HashMap<String, String>();
        //facturaElectronica = new FacturacionElectronica(session, this.panelPadre);

        limpiarVistaDatosCliente();
        //Limpiar campos de los detalles de la factura
        getTxtCodigoDetalle().setText("");
        getTxtValorUnitario().setText("");
        getTxtCantidad().setText("");
        getTxtDescripcion().setText("");
        getTxtDescuento().setText("");
        getTxtReferenciaContacto().setText("");
        //getTxtClientePresupuesto().setText("");
        getCheckPorcentaje().setSelected(false);

        //Limpiar los datos de la tabla factura
        initModelTablaDetalleFactura();
        //Limpiar los datos forma pago
        initModelTablaFormaPago();
        //Limpiar los datos adicional
        initModelTablaDatoAdicional();

        //Limpiar los labels de los calculos
        getLblSubtotalSinImpuesto().setText("0.00");
        getLblSubtotal12().setText("0.00");
        getLblSubtotal0().setText("0.00");
        getLblSubtotalNoObjetoDeIva().setText("0.00");
        getLblSubTotalDescuentoConImpuesto().setText("0.00");
        getLblSubTotalDescuentoSinImpuesto().setText("0.00");
        getLblSubtotalExentoDeIva().setText("0.00");
        getLblTotalDescuento().setText("0.00");
        getLblValorIce().setText("0.00");
        getLblIva12().setText("0.00");
        getLblValorIRBPNR().setText("0.00");
        getLblPropina10().setText("0.00");
        getTxtValorTotal().setText("0.00");

        getBtnEditarDetalle().setEnabled(false);
        getBtnAgregarDetalleFactura().setEnabled(true);
        getBtnAgregarProducto().setEnabled(true);
        getBtnCrearProducto().setEnabled(true);
        
        //Borrar los datos del estudiantes y los representantes
        getCmbRepresentante().removeAllItems();
        getTxtEstudiante().setText("");
        
        //Limpiar las variables de la facturacion
        setearVariablesIniciales();
        cargarSecuencial();
        
        getTxtValorRecibido().setText("");
        getLblVuelto().setText("00.00");
        
        getChkActivarFechaVencimiento().setSelected(false);
        getCmbFechaVencimiento().setDate(null);
        getCmbFechaVencimiento().setEnabled(false);
        getTxtVendedor().setText("");
        
        getCmbPreciosVenta().removeAllItems();
        getCmbConsumidorFinal().setSelected(false); //Ver si esta dato esta parametrizado en configuraciones
        getTxtDiasCredito().setValue(0);
        getTxtDescuentoGlobal().setText("0");
        
        
        
        habilitarPermisosEdicionFactura();
        
        EnumSiNo habilitarCredito=ParametroUtilidades.obtenerValorParametroEnum(session.getEmpresa(),ParametroCodefac.CREDITO_DEFECTO_VENTAS,EnumSiNo.NO);
        if(habilitarCredito==null)
        {
            habilitarCredito=EnumSiNo.NO; //Si no existe ningun dato dejo por defecto en no
        }
                    
        getChkHabilitarCredito().setSelected(habilitarCredito.getBool());

    }
    
    private void habilitarPermisosEdicionFactura()
    {
        try {
            if(ParametroUtilidades.comparar(session.getEmpresa(), ParametroCodefac.EDITAR_PRECIO_UNIT_FACTURA, EnumSiNo.NO))
            {
                getTxtValorUnitario().setEnabled(false);
            }
            
            if(ParametroUtilidades.comparar(session.getEmpresa(), ParametroCodefac.EDITAR_DESCUENTO_FACTURA, EnumSiNo.NO))
            {
                getTxtDescuento().setEnabled(false);
            }
            
            if(ParametroUtilidades.comparar(session.getEmpresa(), ParametroCodefac.EDITAR_DESCRIPCION_FACTURA, EnumSiNo.NO))
            {
                getTxtDescripcion().setEnabled(false);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    private void limpiarVistaDatosCliente()
    {
        
        //Limpiar los campos del cliente
        getLblNombreCliente().setText("");
        getLblTelefonoCliente().setText("");
        getLblDireccionCliente().setText("");
        getTxtCliente().setText("");
    }
    
    
//    @Override
    public String getNombre() {
        return "Facturacion";
    }

    @Override
    public String getURLAyuda() {
      //return "https://docs.google.com/document/d/1AJGbwPOj4rw0uiSMUv0FJQRMlNoYNRbl2TcoE_24ivk/edit#heading=h.i205r9ivb9na";
        return "https://docs.google.com/document/d/e/2PACX-1vRxHiHd5vpEu1In25BKtCXigpl4m1phGAZwNR7Rh2Jm-Xqe7ffQpivlYJsMAWHFBS0BOnYxj4dpUi7H/pub?embedded=true#h.1vkhsaz68ozt";
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
    
    private DefaultTableModel initModelTablaFormaPago() {
        Vector<String> titulo = new Vector<>();
        titulo.add("forma pago");
        titulo.add("Valor");
        //titulo.add("Tipo");
        //titulo.add("Tiempo");

        DefaultTableModel modeloTablaFormasPago = new DefaultTableModel(titulo, 0);
        getTblFormasPago().setModel(modeloTablaFormasPago);
        return modeloTablaFormasPago;
    }


    public void cargarFormasPagoTabla() {

        //Crea el modelo con el titulo de las formas de pago
        DefaultTableModel modeloTablaFormasPago=initModelTablaFormaPago();

        List<FormaPago> formasPago = factura.getFormaPagos();
        for (FormaPago formaPago : formasPago) {
            Vector<String> fila = new Vector<>();
            fila.add(formaPago.getSriFormaPago().getAlias());
            fila.add(formaPago.getTotal().toString());
            //fila.add(formaPago.getUnidadTiempo());
            //fila.add(formaPago.getPlazo() + "");
            modeloTablaFormasPago.addRow(fila);
        }
        getTblFormasPago().setModel(modeloTablaFormasPago);
    }

    private void initModelTablaDetalleFactura() {
        Vector<String> titulo = new Vector<>();
        titulo.add("Codigo");
        titulo.add("Valor Uni");
        titulo.add("Cantidad");
        titulo.add("Descripcion");
        titulo.add("Descuento");
        titulo.add("Valor Total");
        this.modeloTablaDetallesProductos = new DefaultTableModel(titulo, 0);
        //this.modeloTablaDetallesProductos.isCellEditable
        getTblDetalleFactura().setModel(modeloTablaDetallesProductos);
    }

    private void initModelTablaDatoAdicional() {
        //Vector<String> titulo = new Vector<>();
        //titulo.add("Objecto");
        //titulo.add("Nombre");
        //titulo.add("Valor");
        

        this.modeloTablaDatosAdicionales=UtilidadesTablas.crearModeloTabla(new String[]{"","Nombre","Valor"},new Class[]{FacturaAdicional.class,String.class,String.class});
        //this.modeloTablaDatosAdicionales = new DefaultTableModel(titulo, 0);
        getTblDatosAdicionales().setModel(modeloTablaDatosAdicionales);
        
        UtilidadesTablas.ocultarColumna(getTblDatosAdicionales(),0); //Ocultar la fila del objeto para poder volver a modificar
        
        modeloTablaDatosAdicionales.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int columnaModificada=e.getColumn();
                int filaModificada=e.getFirstRow();
                
                if(filaModificada<0 || columnaModificada<0) //Si no existe ninguna fila seleccionada no ejecuta ninguna accion 
                    return;
                
                Object dato=modeloTablaDatosAdicionales.getValueAt(filaModificada, columnaModificada);
                //TableModel modelo = ((TableModel) (e.getSource()));
                //String datoOriginal=modelo.getValueAt(filaModificada,columnaModificada)
                
                ComprobanteAdicional comprobanteAdicional=factura.getDatosAdicionales().get(filaModificada);
                switch(columnaModificada)
                {
                    //Caso cuando se edite el nombre del dato adicional
                    case 1:
                        comprobanteAdicional.setCampo(dato.toString());
                        break;
                        
                    //Caso cuando se edite el valor del dato adicional
                    case 2:
                        comprobanteAdicional.setValor(dato.toString());
                        break;
                        
                }
            }
        });
    }

    /**
     * Metodo que actualiza los valores en la tabla
     */
    private void cargarTablaDatosAdicionales() {
        initModelTablaDatoAdicional();
        
        for (FacturaAdicional datoAdicional : factura.getDatosAdicionales()) {
            Vector dato = new Vector();
            dato.add(datoAdicional);
            dato.add(datoAdicional.getCampo());
            dato.add(datoAdicional.getValor());
            
            this.modeloTablaDatosAdicionales.addRow(dato);
        }

    }

    /**
     * Cargar los detalles de las facturas agregados
     */
    public void cargarDatosDetalles() {
        String[] titulo ={
        "Codigo",
        "ValorUni",
        "Cantidad",
        "Descripcion",
        "Descuento",
        "Valor Total",
        ""}; //Columna para el boton de eliminar

        List<FacturaDetalle> detalles = factura.getDetallesOrdenados();

        //this.modeloTablaDetallesProductos = new DefaultTableModel(titulo, 0);
        this.modeloTablaDetallesProductos=UtilidadesTablas.crearModeloTabla(titulo,new Class[]{
            String.class,
            String.class,
            String.class,
            String.class,
            String.class,
            String.class,});
        
        for (FacturaDetalle detalle : detalles) {
            try {
                
                TipoDocumentoEnum tipoReferenciaEnum=detalle.getTipoDocumentoEnum();
                Vector<String> fila = new Vector<String>();
                
                if(detalle.getCodigoPrincipal()!=null)
                {
                    fila.add(detalle.getCodigoPrincipal());
                }
                else
                {
                    /**
                     * Para evitar retrocompatibilidad con datos anteriores
                     */
                    ReferenciaDetalleFacturaRespuesta respuesta= ServiceFactory.getFactory().getFacturacionServiceIf().obtenerReferenciaDetalleFactura(tipoReferenciaEnum,detalle.getReferenciaId());
                    if (respuesta.objecto != null) {
                        switch(respuesta.tipoDocumentoEnum)
                        {
                            case LIBRE:
                            case INVENTARIO:
                                fila.add(((Producto)respuesta.objecto).getCodigoPersonalizado());
                                break;
                            case ACADEMICO:
                                fila.add(((RubroEstudiante)respuesta.objecto).getId()+"");
                                break;
                        }
                        
                    } else {
                        fila.add("Sin Código");
                    }
                }
                
                //Cargar los totales
                fila.add(detalle.getPrecioUnitario().toString());               
                //Producto producto=ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(detalle.getReferenciaId());

                fila.add(detalle.getCantidad().toString());
                fila.add(detalle.getDescripcion());
                fila.add((detalle.getDescuento()!=null)?detalle.getDescuento().toString():"");
                fila.add(detalle.getTotal().toString());
                fila.add("Eliminar"); //Boton de eliminar para la tabla
                modeloTablaDetallesProductos.addRow(fila);
            } catch (RemoteException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        getTblDetalleFactura().setModel(this.modeloTablaDetallesProductos);
        
        UtilidadesTablas.definirTamanioColumnas(getTblDetalleFactura(),new Integer[]{100,100,80,600,80,100,100}); //Definir los tamanios definidos para la tabla principal
        
        ButtonColumn botonEliminar=new ButtonColumn(getTblDetalleFactura(),new AbstractAction() { //Agregado boton de eliminar a la tabla
            @Override
            public void actionPerformed(ActionEvent e) {
                btnListenerEliminar();
            }
        }, 6); 
        //botonEliminar.setMnemonic(KeyEvent.VK_D);
        
        modeloTablaDetallesProductos.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int columnaModificada=e.getColumn();
                int filaModificada=e.getFirstRow();
                
                if(filaModificada<0 || columnaModificada<0 || columnaModificada==6) //Si no existe ninguna fila seleccionada no ejecuta ninguna accion o si es lacolumna  6 que es el boton de eliminar
                    return;
                
                Object dato=modeloTablaDetallesProductos.getValueAt(filaModificada, columnaModificada);
                //TableModel modelo = ((TableModel) (e.getSource()));
                //String datoOriginal=modelo.getValueAt(filaModificada,columnaModificada)
                
                switch(columnaModificada)
                {
                    case 2:
                        getTxtCantidad().setText(dato.toString());
                        btnListenerEditar();
                        break;
                        
                }
                
            }
        });
        
        
    }
    
    private String obtenerCodigoProducto(FacturaDetalle facturaDetalle)
    {
        try {
            switch(facturaDetalle.getTipoDocumentoEnum())
            {
                case ACADEMICO:
                    RubroEstudiante rubroEstudiante = ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                    return rubroEstudiante.getId().toString();
                    
                    
                case PRESUPUESTOS:
                    //Presupuesto presupuesto=ServiceFactory.getFactory().getPresupuestoServiceIf().buscarPorId(detalle.getReferenciaId());
                    return facturaDetalle.getReferenciaId().toString();
                    
                    
                case INVENTARIO:case LIBRE:
                    Producto producto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                    return producto.getCodigoPersonalizado();
                    
            }            
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Sin Código";
    }

    private void agregarFechaEmision() {

    }


    
    public void calcularSubtotales() {
        this.factura.setSubtotalSinImpuestos(this.factura.getSubtotalSinImpuestos().subtract(this.factura.getSubtotalImpuestos()));
        this.factura.setDescuentoSinImpuestos(this.factura.getSubtotalImpuestos().add(this.factura.getSubtotalSinImpuestos()).subtract(factura.getSubtotalImpuestos()));
    }


    public BigDecimal obtenerValorIva() {
        try {
            Map<String, Object> map = new HashMap<>();
            ImpuestoDetalleServiceIf impuestoDetalleService =ServiceFactory.getFactory().getImpuestoDetalleServiceIf();
            map.put("tarifa", 12); //TODO Parametrizar el iva con la variable del sistema
            List<ImpuestoDetalle> listaImpuestoDetalles = impuestoDetalleService.buscarImpuestoDetallePorMap(map);
            listaImpuestoDetalles.forEach((iD) -> {
                BigDecimal iva = iD.getPorcentaje();
            });
            return new BigDecimal(0.120);
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
    
    private void setearValoresAcademicos(Estudiante estudiante)
    {
       
        getTxtEstudiante().setText(estudiante.getNombreCompleto());
        
        //Cargar los representantes por defecto
        getCmbRepresentante().removeAllItems();
        if(estudiante.getRepresentante()!=null)
        {
            getCmbRepresentante().addItem(estudiante.getRepresentante());
        }
        
        if(estudiante.getRepresentante2()!=null)
        {
            getCmbRepresentante().addItem(estudiante.getRepresentante2());
        }

    }
    
    private void cargarDatosAdicionalesAcademicos() {
        //Quita todos los datos anteriores para cargar los datos del estudiante
        if(factura.getDatosAdicionales()!=null)
            factura.getDatosAdicionales().clear();
        
        //Cargar el correo solo cuando exista 
        //factura.addDatosAdicionalCorreo(factura.getCliente().getCorreoElectronico());

        //factura.addDatoAdicional(new FacturaAdicional(title, title, ComprobanteAdicional.Tipo.TIPO_OTRO));
        factura.addDatoAdicional(new FacturaAdicional(DatosAdicionalesComprobanteEnum.NOMBRE_ESTUDIANTE.getNombre(), estudiante.getNombreCompleto(),ComprobanteAdicional.Tipo.TIPO_OTRO));

        factura.addDatoAdicional(new FacturaAdicional(DatosAdicionalesComprobanteEnum.CODIGO_ESTUDIANTE.getNombre(), estudiante.getIdEstudiante() + "",ComprobanteAdicional.Tipo.TIPO_OTRO));
        
        try {
            EstudianteInscrito estudianteInscrito= ServiceFactory.getFactory().getEstudianteInscritoServiceIf().buscarEstudianteMatriculadoPeriodoActivo(estudiante);
            factura.addDatoAdicional(new FacturaAdicional(DatosAdicionalesComprobanteEnum.CURSO_ESTUDIANTE.getNombre(), estudianteInscrito.getNivelAcademico().getNombre(), ComprobanteAdicional.Tipo.TIPO_OTRO));
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    

    private void setearValoresCliente() {
        
        
        TipoDocumentoEnum tipoDocumentoEnum=controlador.getTipoDocumentoEnumSeleccionado();
        
        if(tipoDocumentoEnum==null)
        {
            DialogoCodefac.mensaje(new CodefacMsj("Por favor seleccione un tipo de documento", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
        }
        
        //Cargar los tipos de documentos segun el tipo de dcumento
        switch(tipoDocumentoEnum)
        {
            case PRESUPUESTOS:            
            case ACADEMICO:
            case INVENTARIO:
            case LIBRE:
                getTxtCliente().setText(factura.getCliente().getIdentificacion());
                getLblNombreCliente().setText(factura.getCliente().getRazonSocial());
                getLblDireccionCliente().setText(factura.getSucursal().getDireccion());
                getLblTelefonoCliente().setText(factura.getSucursal().getTelefonoConvencional());
                break;
                
                //getTxtClientePresupuesto().setText(factura.getCliente().toString());

        
        }

        /**
         * Cargar el estado de la factura
         */
        ComprobanteEntity.ComprobanteEnumEstado estadoEnum = ComprobanteEntity.ComprobanteEnumEstado.getEnum(factura.getEstado());
        
        if (estadoEnum != null) {
            getLblEstadoFactura().setText(estadoEnum.getNombre());
        }

        factura.setCliente(factura.getCliente());

        //Cargar la fecha de vencimiento de la factura si existe ingresado una fecha
        if(estadoFormulario.equals(ESTADO_GRABAR))
        {
            FacturaAdicional datoAdicional=(FacturaAdicional) factura.obtenerDatoAdicionalPorCampo(ComprobanteAdicional.CampoDefectoEnum.FECHA_VENCIMIENTO);
            if(factura.getCliente().getDiasCreditoCliente()!=null && !factura.getCliente().getDiasCreditoCliente().equals(0))
            {
                //Eliminar dato anterior si ya fue ingresado

                if(datoAdicional!=null)
                {
                    factura.getDatosAdicionales().remove(datoAdicional);
                }

                java.util.Date fechaNueva = UtilidadesFecha.sumarDiasFecha(new Date(getjDateFechaEmision().getDate().getTime()), factura.getCliente().getDiasCreditoCliente());
                getCmbFechaVencimiento().setDate(fechaNueva);
                getChkActivarFechaVencimiento().setSelected(true);
                listenerChkFechaVencimiento();

            }
            else
            {
                if(datoAdicional!=null)
                {
                    factura.getDatosAdicionales().remove(datoAdicional);
                }
            }
        }
        
        //Agregar datos adicionales de los días de credito si tienen asignado
        if(factura.getCliente().getDiasCreditoCliente()!=null)
        {
            getTxtDiasCredito().setValue(factura.getCliente().getDiasCreditoCliente());
        }
        
    }

    

    protected void setearValoresDefaultFactura(CrudEnum crudEnum){
        /**
         * Todo: Carlos 
         */
        Persona.TipoIdentificacionEnum tipoIdentificacionEnum=factura.getCliente().getTipoIdentificacionEnum();
        String codigoSri=tipoIdentificacionEnum.getCodigoSriVenta();
        factura.setTipoIdentificacionCodigoSri(codigoSri); //TODO: Ver si esta variable se debe grabar en el servidor
        factura.setEmpresa(session.getEmpresa());
        //factura.setEstado(Factura.ESTADO_FACTURADO);
        //factura.setFechaCreacion(UtilidadesFecha.castDateToTimeStamp(UtilidadesFecha.getFechaHoy()));
        factura.setFechaEmision(new Date(getjDateFechaEmision().getDate().getTime()));
        //factura.setIvaSriId(iva);
        factura.setPuntoEmision(getPuntoEmisionSeleccionado().getPuntoEmision());
        factura.setPuntoEmisionId(getPuntoEmisionSeleccionado().getId());
        factura.setPuntoEstablecimiento(new BigDecimal(session.getSucursal().getCodigoSucursal().toString()));
        
        //Cuando la facturacion es electronica
        PuntoEmision puntoEmisionSeleccionada=getPuntoEmisionSeleccionado();

        /**
         * TODO: Seteado los valores temporales pero toca cambiar esta parte y setear
         * los valores directamente en la factura
         */
        //factura.setTotal(new BigDecimal(getTxtValorTotal().getText()));
        //factura.setSubtotalSinImpuestos(new BigDecimal(getLblSubtotal0().getText()));
        //factura.setSubtotalImpuestos(new BigDecimal(getLblSubtotal12().getText()));
        //factura.setIva(new BigDecimal(getLblIva12().getText()));
        //factura.setSubtotalSinImpuestos(factura.getSubtotalSinImpuestos().add(factura.getSubtotalImpuestos()));
       
        
        //Solo debe modificar el documento de la factura cuando esta creando por primera vez
        if(crudEnum.equals(CrudEnum.CREAR))
        {
            DocumentoEnum documentoEnum=(DocumentoEnum) getCmbDocumento().getSelectedItem();
            factura.setCodigoDocumento(documentoEnum.getCodigo());
        }
                
        factura.setContribuyenteEspecial(session.getEmpresa().getContribuyenteEspecial());
        
        factura.setObligadoLlevarContabilidad(session.getEmpresa().getObligadoLlevarContabilidad());
        factura.setDireccionEstablecimiento(session.getSucursal().getDirecccion());
        factura.setDireccionMatriz(session.getMatriz().getDirecccion());
        factura.setEmpresa(session.getEmpresa());
        factura.setUsuario(session.getUsuario());
        factura.setSucursalEmpresa(session.getSucursal());
        
        factura.setVentaCreditoEnum(EnumSiNo.getEnumByBoolean(getChkHabilitarCredito().isSelected()));
        factura.setDiasCredito((Integer) getTxtDiasCredito().getValue());
        
        factura.setCodigoOrigenTransaccionEnum(Factura.OrigenTransaccionEnum.ESCRITORIO);
        //factura.setVendedor(vendedor);
        
        //factura.setIvaSriId(session.get;
        
        /**
         * Redondeo los valores de los precios unitario de los detalles de la factura
         * Nota: este proceso lo hago al final porque para los totales necesitaba tener los valores exactos de los precios unitarios, pero como ya va a generar la factura puedo redondeal los valores unitario
         */
        //for (FacturaDetalle facturaDetalle : factura.getDetalles()) {
        //    facturaDetalle.setPrecioUnitario(facturaDetalle.getPrecioUnitario().setScale(2,RoundingMode.HALF_UP));
        //}

    }
    
    private PuntoEmision getPuntoEmisionSeleccionado()
    {
        return (PuntoEmision) getCmbPuntoEmision().getSelectedItem();
    }

    private void initComponenesGraficos() {
        URL path = RecursoCodefac.IMAGENES_ICONOS.getResourceURL("pequenos/mas-ico.png");
        getBtnAgregarDetalleFactura().setIcon(new ImageIcon(path));
        getBtnAgregarDetalleFactura().setText("");
        getBtnAgregarDetalleFactura().setToolTipText("Agregar detalle factura");

        getBtnEditarDetalle().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("pequenos/edit_icon.png")));
        getBtnEditarDetalle().setText("");
        getBtnEditarDetalle().setToolTipText("Editar detalle factura");


        getBtnAgregarCliente().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("pequenos/usuario.png")));
        getBtnAgregarCliente().setText("");
        getBtnAgregarCliente().setToolTipText("Crear nuevo cliente");

        //getBtnAgregarProducto().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("pequenos/list.png")));
        getBtnAgregarProducto().setText("");
        getBtnAgregarProducto().setToolTipText("Agregar producto a la factura");

        //getBtnCrearProducto().setIcon(new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("pequenos/add2.png")));
        getBtnCrearProducto().setText("");
        getBtnCrearProducto().setToolTipText("Crear nuevo producto");

        getBtnAgregarFormaPago().setText("");
        getBtnAgregarFormaPago().setToolTipText("Agregar formas e pago");

    }

    public boolean ComprobarRangoDeFechaPermitido(java.util.Date fecha) {
        //if(fecha!=null && fechaMin!=null && fechaMax!=null)
        //{
            boolean fechaDespues = fecha.after(fechaMin);
            boolean fechaAntes = (fecha.before(fechaMax));

            if (fechaDespues && fechaAntes) {
                return true;
            }
        //}
        return false;
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        setearValoresVista();     
        System.out.println("Ingresando a iniciar");
        
        controlador=new FacturaModelControlador(session,this,DialogoCodefac.intefaceMensaje);
        controlador.iniciar();
        if (!validacionParametrosCodefac()) {
            dispose();
            throw new ExcepcionCodefacLite("No cumple validacion inicial");
            
        }
        
        iniciarValoresIniciales();
        setearVisibilidadComponentes();
        
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        getjDateFechaEmision().setDate(new java.util.Date());
    }
        

    @Override
    public boolean salirSinGrabar() {
        if(factura.getCliente()!=null || (factura.getDetalles()!=null && factura.getDetalles().size()>0) )
        {
            //Boolean respuesta = DialogoCodefac.dialogoPregunta("Alerta", "Si desea continuar se perderan los datos sin guardar?", DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;            
        }
        return true;
    }
    
    

    public void definirFechaMinFacturacion() {

        getjDateFechaEmision().setDate(new java.util.Date());
        //this.getjDateFechaEmision().setEditable(false);
        //((JTextField) this.getjDateFechaEmision().getDateEditor()).setEditable(false);

        this.fechaMax = new java.util.Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.fechaMax);
        calendar.add(Calendar.DAY_OF_MONTH, - ParametrosSistemaCodefac.MAX_DIAS_FACURAR);
        this.fechaMin = calendar.getTime();

    }
    


    private void cargarValoresAdicionales() {
        getLblEstadoFactura().setText((factura.getEstadoEnum()!=null)?factura.getEstadoEnum().getNombre():"Sin estado");
        
        //Solo cargar el secuencial cuando no es una proforma
        //if(!factura.getCodigoDocumentoEnum().equals(DocumentoEnum.PROFORMA))
        //{
            cargarSecuencialConsulta();
        //}
        
        getjDateFechaEmision().setDate(factura.getFechaEmision());
        
        //Carlos los datos dle vendedor
        if(factura.getVendedor()!=null)
        {
            getTxtVendedor().setText(factura.getVendedor().getIdentificacion() + " - " + factura.getVendedor().getNombresCompletos());
        }
        
        if(factura.getReferido()!=null)
        {
            getTxtReferenciaContacto().setText(factura.getReferido().getIdentificacion() + " - " + factura.getReferido().getNombresCompletos());
        }
        
        if(factura.getFechaVencimiento()!=null)
        {
            getCmbFechaVencimiento().setDate(factura.getFechaVencimiento());
        }
        
        if(factura.getVentaCredito()!=null)
        {
            getChkHabilitarCredito().setSelected(factura.getVentaCreditoEnum().getBool());
        }
        
        if(factura.getDiasCredito()!=null)
        {
            getTxtDiasCredito().setValue(factura.getDiasCredito());
        }
        
    }

    private boolean validacionParametrosCodefac() {
        System.out.println("Ingreso a la validacion de Paremtros Codefac");
        String mensajeValidacion = "Esta pantalla requiere : \n";
        boolean validado = true;
        
        if (session.getEmpresa() == null) 
        {
            mensajeValidacion += " - Información de Empresa \n";
            validado = false;
        }
        else         
        {        
       
            try //Validacion cunando es facturacion electronica
            {
                DocumentoEnum documentoEnum=(DocumentoEnum) getCmbDocumento().getSelectedItem();
                
                //Solo hacer estas validaciones para facturas electronicas
                if(documentoEnum.getComprobanteElectronico())
                {
                
                    if (session.getParametrosCodefac().get(ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA).getValor().equals("")) {
                        mensajeValidacion += " - Archivo Firma\n";
                        validado = false;
                    }


                    String claveFirmaElectronica=UtilidadesEncriptar.desencriptar(session.getParametrosCodefac().get(ParametroCodefac.CLAVE_FIRMA_ELECTRONICA).getValor(),ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);

                    if (claveFirmaElectronica.equals("")) {
                        mensajeValidacion += " - Clave Firma\n";
                        validado = false;
                    }

                    if (session.getParametrosCodefac().get(ParametroCodefac.CORREO_USUARIO).getValor().equals("")) {
                        mensajeValidacion += " - Correo\n";
                        validado = false;
                    }

                    if (session.getParametrosCodefac().get(ParametroCodefac.CORREO_USUARIO).getValor().equals("")) {
                        mensajeValidacion += " - Clave Correo \n";
                        validado = false;
                    }
                }
                
                if (session.getEmpresa() == null) {
                    mensajeValidacion += " - Información de Empresa \n";
                    validado = false;
                }
            } catch (Exception ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        if (!validado) {
            //mensajeValidacion=mensajeValidacion.substring(0,mensajeValidacion.length()-2);
            DialogoCodefac.mensaje("Acceso no permitido", mensajeValidacion + "\nPor favor complete estos datos en configuración para usar esta pantalla", DialogoCodefac.MENSAJE_ADVERTENCIA);
        }

        return validado;

    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean verificarSumaFormaPago() {
        
        //Obtiene el total de las formas de pago
        BigDecimal totalFormasPago = factura.getTotalFormasPago();
        int res = factura.getTotal().compareTo(totalFormasPago);
        if (res == -1) 
        {
            DialogoCodefac.mensaje("Advertencia", "La forma de pago sobrepasa el valor a Facturar", DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        } 
        else 
        {
            /*if(res==1)
            {
                DialogoCodefac.mensaje("Advertencia", "La formas de pago es inferior a el valor a Facturar", DialogoCodefac.MENSAJE_ADVERTENCIA);
                return false;
            }*/
            return true;
        }
    }
    

    
    
    //TODO: Para optimizar y mejorar el codigo analizar para utilizar una sola funcion con la anterior
    public void agregarDetallesFactura(BigDecimal cantidad,BigDecimal valorUnitario,String descripcion,Boolean descuentoPorcentaje,BigDecimal descuento,Object referencia) {
        FacturaDetalle facturaDetalle = new FacturaDetalle();

            
            try {
                //FacturaDetalle facturaDetalle = new FacturaDetalle();
                facturaDetalle.setCantidad(cantidad);
                facturaDetalle.setDescripcion(descripcion);
                BigDecimal valorTotalUnitario =valorUnitario;
                facturaDetalle.setPrecioUnitario(valorTotalUnitario.setScale(2, BigDecimal.ROUND_HALF_UP));
                
                //Variable del producto para verificar otros datos como el iva
                CatalogoProducto catalogoProducto=null;
                //Seleccionar la referencia dependiendo del tipo de documento
                TipoDocumentoEnum tipoDocumentoEnum=controlador.getTipoDocumentoEnumSeleccionado();
                
                switch (tipoDocumentoEnum) {
                    case ACADEMICO:
                        RubroEstudiante rubroSeleccionado = (RubroEstudiante) referencia;
                        facturaDetalle.setReferenciaId(rubroSeleccionado.getId());
                        facturaDetalle.setTipoDocumento(TipoDocumentoEnum.ACADEMICO.getCodigo());
                        catalogoProducto = rubroSeleccionado.getRubroNivel().getCatalogoProducto();
                        break;

                    case PRESUPUESTOS:
                        Presupuesto presupuesto=(Presupuesto)referencia;
                        facturaDetalle.setReferenciaId(presupuesto.getId());
                        facturaDetalle.setTipoDocumento(TipoDocumentoEnum.PRESUPUESTOS.getCodigo());
                        catalogoProducto = ServiceFactory.getFactory().getPresupuestoServiceIf().buscarPorId(facturaDetalle.getReferenciaId()).getCatalogoProducto();
                        break;

                    case INVENTARIO:
                    case LIBRE:
                        Producto productoSeleccionado = (Producto) referencia;
                        facturaDetalle.setReferenciaId(productoSeleccionado.getIdProducto());
                        facturaDetalle.setTipoDocumento(TipoDocumentoEnum.INVENTARIO.getCodigo());
                        catalogoProducto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(facturaDetalle.getReferenciaId()).getCatalogoProducto();
                        break;
                }
                
                facturaDetalle.setCatalogoProducto(catalogoProducto);
                facturaDetalle.setValorIce(BigDecimal.ZERO);
                
                if (!descuentoPorcentaje) {
                    facturaDetalle.setDescuento(descuento.setScale(2,BigDecimal.ROUND_HALF_UP));
                } else {
                    BigDecimal porcentajeDescuento = descuento;
                    
                    porcentajeDescuento = porcentajeDescuento.divide(new BigDecimal(100));
                    BigDecimal total = facturaDetalle.getCantidad().multiply(facturaDetalle.getPrecioUnitario());
                    descuento = total.multiply(porcentajeDescuento);
                    facturaDetalle.setDescuento(descuento.setScale(2, BigDecimal.ROUND_HALF_UP));
                }
                
                //Calular el total despues del descuento porque necesito esa valor para grabar
                //BigDecimal setTotal = facturaDetalle.getCantidad().multiply(facturaDetalle.getPrecioUnitario()).subtract(facturaDetalle.getDescuento());
                //facturaDetalle.setTotal(setTotal.setcale(2, BigDecimal.ROUND_HALF_UP));a
                facturaDetalle.calcularTotalDetalle();
                facturaDetalle.setIvaPorcentaje(catalogoProducto.getIva().getTarifa());
                /**
                 * Revisar este calculo del iva para no calcular 2 veces al mostrar
                 */
                
                if (catalogoProducto.getIva().getTarifa().equals(0)) {
                    facturaDetalle.setIva(BigDecimal.ZERO);
                } else {
                    BigDecimal iva = facturaDetalle.getTotal().multiply(obtenerValorIva()).setScale(2, BigDecimal.ROUND_HALF_UP);
                    facturaDetalle.setIva(iva);
                }
                
                if (facturaDetalle.getCantidad().multiply(facturaDetalle.getPrecioUnitario()).compareTo(facturaDetalle.getDescuento()) > 0) {
                    
                    factura.addDetalle(facturaDetalle);

                    cargarDatosDetalles();
                    controlador.limpiarDetalleFactura();
                    controlador.cargarTotales();
                } else {
                    DialogoCodefac.mensaje("Alerta", "El valor de Descuento excede, el valor de PrecioTotal del Producto", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    controlador.limpiarDetalleFactura();
                }
            } catch (RemoteException ex) {
                Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    
    /*private boolean validarAgregarInventario()
    {
        try {
            
            if(ParametroUtilidades.comparar(session.getEmpresa(), ParametroCodefac.FACTURAR_INVENTARIO_NEGATIVO, EnumSiNo.NO))
            {
                try {                    
                    //Verifico si el producto es inventario y esta activo la opción de construir ensamble en la venta porque en ese caso
                    //tampoco debe validar el inventario en la vista para el ensamble
                    if(ParametroUtilidades.comparar(session.getEmpresa(), ParametroCodefac.CONSTRUIR_ENSAMBLES_FACTURAR, EnumSiNo.SI))
                    {
                         //Si tengo que construir el ensamble no valido en la vista porque puede tener stock insuficiente pero despues de construir si puede generar
                        return true;
                    }
                    
                    
                    boolean verifadorStock = controlador.verificarExistenciaStockProducto();
                    //Verificar si agrego los datos al fomurlaro cuando no existe inventario
                    if (!verifadorStock) {
                        
                        DialogoCodefac.mensaje("Advertencia", "No existe stock para el producto", DialogoCodefac.MENSAJE_ADVERTENCIA);
                        return false;
                    } else {                        
                        return true;
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Por defecto si no tiene nada seleccionado si permito agregar el inventario
        return true;
        
    }*/
    
    @Deprecated
    //TODO: Parece que este metodo ya no voy a usar
    private void setearFacturaDetalle(FacturaDetalle facturaDetalle) {
        facturaDetalle.setCantidad(new BigDecimal(getTxtCantidad().getText()));
        facturaDetalle.setDescripcion(getTxtDescripcion().getText());
    }
    
    
    
    /**
     * Metodo para setear valores de la factura de manera externa
     */
    public void setearValoresFactura()
    {
        if (factura != null) {
            this.factura = factura;
            ///Cargar los datos de la factura
            setearValoresCliente();
            cargarDatosDetalles();
            controlador.limpiarDetalleFactura();
            controlador.cargarTotales();
            cargarValoresAdicionales();
            //cargarFormasPagoTabla();
        }
    }

    public void iniciarValoresIniciales() {               
                
        //Buscar todos los documentos permitidos para facturar
        List<DocumentoEnum> tiposDocumento=controlador.buscarDocumentosFactura();
        
        UtilidadesComboBox.llenarComboBox(getCmbDocumento(),tiposDocumento);
        //Setear valores por defecto
        //TODO: Esta logica esta de unificar con la pantalla web
        try {            
            DocumentoEnum documentoSeleccionado=ParametroUtilidades.obtenerValorBaseDatos(session.getEmpresa(),ParametroCodefac.DOCUMENTO_DEFECTO_VISTA_FACTURA,DocumentoEnum.PROFORMA);
            if(documentoSeleccionado!=null)
            {
                getCmbDocumento().setSelectedItem(documentoSeleccionado);
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getCmbIva().removeAllItems();
        getCmbIva().addItem(EnumSiNo.SI);
        getCmbIva().addItem(EnumSiNo.NO);
        
        getCmbIvaDescuento().removeAllItems();
        getCmbIvaDescuento().addItem(EnumSiNo.SI);
        getCmbIvaDescuento().addItem(EnumSiNo.NO);
        
        
        cargarComboPuntosVenta();
       
    }
    
    private void actualizaCombosPuntoVenta()
    {
        int indiceSeleccionado=getCmbPuntoEmision().getSelectedIndex();
        cargarComboPuntosVenta();
        getCmbPuntoEmision().setSelectedIndex(indiceSeleccionado);
    }
    
    private void cargarComboPuntosVenta()
    {
                
        //Cargar Puntos de Venta disponibles para la sucursal

        try {
            List<PuntoEmision> puntosVenta = ServiceFactory.getFactory().getPuntoVentaServiceIf().obtenerActivosPorSucursal(session.getSucursal());
            getCmbPuntoEmision().removeAllItems();
            //Canfigurar un cell render para las sucursales
            //getCmbPuntoEmision().setRenderer(new RenderPersonalizadoCombo());

            for (PuntoEmision puntoVenta : puntosVenta) {
                getCmbPuntoEmision().addItem(puntoVenta);
            }
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    private void crearClienteFinal()
    {
        
    }
    
    private void eliminarTodosLosDetalles()
    {
        UtilidadesTablas.eliminarTodosLosDatos(modeloTablaDetallesProductos);
        factura.getDetalles().clear();
    }

    private void addListenerCombos() {
        
        getCmbConsumidorFinal().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getCmbConsumidorFinal().isSelected())//Si esta seleccionado cliente final cargo ese dato
                {
                    try {
                            List<PersonaEstablecimiento> resultados=ServiceFactory.getFactory().getPersonaEstablecimientoServiceIf().buscarActivoPorIdentificacion(Persona.IDENTIFICACION_CONSUMIDOR_FINAL,session.getEmpresa()); //Todo crear mejor un metodo que ya obtener filtrado los datos
                            if(resultados.size()==0)
                            {
                                if(DialogoCodefac.dialogoPregunta("Crear Consumidor Final","No existe el Consumidor Final, lo desea crear?",DialogoCodefac.MENSAJE_ADVERTENCIA))
                                {
                                    Persona consumidorFinal=ServiceFactory.getFactory().getPersonaServiceIf().crearConsumidorFinal(session.getEmpresa());
                                    cargarCliente(consumidorFinal.getEstablecimientos().get(0));
                                }
                            }
                            else
                            {
                                cargarCliente(resultados.get(0));
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ServicioCodefacException ex) {
                            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
                else//Si esta sin seleccionar limpio el campo para que puedan cargar otro dato
                {
                    limpiarVistaDatosCliente();
                    factura.setCliente(null);
                }
            }
        });
        
        getCmbPreciosVenta().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Producto.PrecioVenta precioVenta=(Producto.PrecioVenta) getCmbPreciosVenta().getSelectedItem();
                
                if(precioVenta!=null)
                {
                    getTxtValorUnitario().setText(precioVenta.precio.toString());
                }
            }
        });
        
        getCmbPuntoEmision().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(estadoFormulario.equals(ESTADO_GRABAR))
                {
                    cargarSecuencial();
                }
            }
        });
        
        getCmbTipoDocumento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) getCmbTipoDocumento().getSelectedItem();                    
                seleccionarPanelTipoDocumento(tipoDocumentoEnum);
                controlador.limpiarDetalleFactura();
            }
        });
        
        
       getCmbDocumento().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                
                if(!ejecutarListenerComboDocumento)
                {
                    ejecutarListenerComboDocumento=true;
                    return;
                }
                
                DocumentoEnum documentoAnterior= (DocumentoEnum) e.getItem();
                DocumentoEnum documentoNuevo=(DocumentoEnum) getCmbDocumento().getSelectedItem();
                
                if(documentoNuevo==null)
                {
                    return;
                }
                
                if(documentoAnterior.equals(documentoNuevo))
                {
                    return;
                }
                
                if(documentoNuevo.equals(DocumentoEnum.NOTA_VENTA_INTERNA)
                        || documentoAnterior.equals(DocumentoEnum.NOTA_VENTA_INTERNA) )
                {
                    controlador.limpiarDetalleFactura();
                    if(factura!=null && factura.getDetalles()!=null && factura.getDetalles().size()>0)
                    {
                        if(DialogoCodefac.dialogoPregunta("Si cambia el tipo de documento los detalles ingresados se perderan , desea continuar ?",DialogoCodefac.MENSAJE_ADVERTENCIA))
                        {                               
                            eliminarTodosLosDetalles();
                            controlador.cargarTotales();
                        }
                        else
                        {
                            (new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    ejecutarListenerComboDocumento=false;
                                    getCmbDocumento().setSelectedItem(documentoAnterior);
                                }
                            })).start();
                        
                            //throw new Error("No se debe cambiar de item");
                            /*e.setSource(documentoAnterior);
                            getCmbDocumento().setSelectedItem(documentoAnterior);
                            getCmbDocumento().SETsE
                            //e.notify();
                            getCmbDocumento().validate();
                            getCmbDocumento().repaint();
                            DialogoCodefac.mensaje("hicimos todo",DialogoCodefac.MENSAJE_ADVERTENCIA);*/
                            
                        }
                    }
                }
            }                
            
        });
              
        
        
        
        getCmbRepresentante().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(factura!=null)
                {
                    //Si cambie el combo de representante tambien los seteo en la factura
                    Persona persona=(Persona) getCmbRepresentante().getSelectedItem();
                    
                    //Solo ejecutar el listener si existe un persona seleccionada
                    if(persona!=null)
                    {
                        factura.setCliente(persona);
                        cargarCliente(persona.getEstablecimientos().get(0)); //carga los datos del representante en los campos del cliente
                        
                        //Modificar el correo principal de los datos adicionales por el del nuevo cliente
                        FacturaAdicional facturaAdicional=(FacturaAdicional) factura.obtenerDatoAdicionalPorCampo(FacturaAdicional.CampoDefectoEnum.CORREO);
                        //Si el campo ya existe solo lo modifico
                        if(facturaAdicional!=null)
                        {   
                            if(persona.getCorreoElectronico()!=null)
                                facturaAdicional.setValor(persona.getCorreoElectronico());
                            else//Si no existe correo lo elimina de la lista
                                factura.getDatosAdicionales().remove(facturaAdicional);
                        }
                        else //Si no existe el campo de correo lo crea
                        {
                            //Solo agregar si el cliente tiene un correo por defecto
                            if(persona.getCorreoElectronico()!=null)
                            {
                                factura.addDatoAdicional(new FacturaAdicional(persona.getCorreoElectronico(),FacturaAdicional.Tipo.TIPO_CORREO,ComprobanteAdicional.CampoDefectoEnum.CORREO));

                            }
                        }
                        
                        cargarTablaDatosAdicionales();

                        //Cargar las formas de pago por defecto del cliente
                        controlador.cargarFormaPago();
                        
                    }
                }
            }
        });
    }
    
    
    private void seleccionarPanelTipoDocumento(TipoDocumentoEnum tipoDocumentoEnum)
    {
        getCmbPreciosVenta().setVisible(false);
        if (tipoDocumentoEnum != null) {
            switch(tipoDocumentoEnum)
            {
                case ACADEMICO:
                    activarTabDatos(1);

                    break;
                case PRESUPUESTOS:
                    activarTabDatos(2);
                    break;
                case INVENTARIO: case LIBRE:
                    activarTabDatos(0);
                    
                    //Activar el combo de varios precios cuando selecciono cualquiera de las 2 opciones
                    getCmbPreciosVenta().setVisible(true);
                    
                    break;
            }
        }        
        
    }
    
    
    private void activarTabDatos(int indice)
    {
        for (int i = 0; i < getPanelTabDatos().getTabCount(); i++) {
            if(i==indice)
            {
                getPanelTabDatos().setEnabledAt(i,true);
                getPanelTabDatos().setSelectedIndex(i);
            }
            else
            {
                getPanelTabDatos().setEnabledAt(i,false);
            }
        }
            
        
    }


    @Override
    public void postConstructorExterno(Object[] parametros) {
        
        //Antes de crear un nueva factura limpio todas las facturas
        //TODO: Ver si esta accion de limpiar  debe ser por defecto o se debe mejor implementar en el controlador
        limpiar();
        
        
        DocumentoEnum documentoEnum=(DocumentoEnum) parametros[0];
        TipoDocumentoEnum tipoDocumentoEnum=(TipoDocumentoEnum) parametros[1];
        
        getCmbDocumento().setSelectedItem(documentoEnum);
        getCmbTipoDocumento().setSelectedItem(tipoDocumentoEnum);
        
        
        estudiante=(Estudiante) parametros[2];
        factura.setCliente((Persona) parametros[3]);
        
        //Agregar los detalles enviados cuando son enviados desde el modulo academicos
        if(tipoDocumentoEnum.equals(TipoDocumentoEnum.ACADEMICO))
        {
            setearValoresAcademicos(estudiante);
            cargarDatosAdicionalesAcademicos();
            cargarTablaDatosAdicionales();
            
            List<RubroEstudiante> rubrosEstudiantes=(List<RubroEstudiante>) parametros[4];
            for (RubroEstudiante rubro : rubrosEstudiantes) {
                
                String descripcion=rubro.getRubroNivel().getNombre();
                
                if(rubro.getProcentajeDescuento()>0)
                {
                    descripcion+="("+rubro.getNombreDescuento()+"-"+rubro.getProcentajeDescuento()+"%)";                    

                }                
                agregarDetallesFactura(BigDecimal.ONE,rubro.getSaldo(),descripcion,true,new BigDecimal(rubro.getProcentajeDescuento()+""),rubro);
            }
            
            //Cargar los representate seteado en el combo box
            getCmbRepresentante().setSelectedIndex(getCmbRepresentante().getSelectedIndex());

        }
        else
        {
            //TODO: implementar para el otro caso cuando sea de otros modulos
        
        }
        
        controlador.cargarFormaPago();
        
        
        
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addListenerCamposTexto() {
        
        getTxtValorRecibido().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                
                String valorTextoRecibido=getTxtValorRecibido().getText();
                
                if (e.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                    if (valorTextoRecibido.isEmpty()) {
                        return;//Si no existe nada ingresado no hago ningun calculo
                    }
                    
                    BigDecimal valorTotal=new BigDecimal(getTxtValorTotal().getText());
                    BigDecimal valorRecibido=new BigDecimal(valorTextoRecibido);
                    getLblVuelto().setText(valorRecibido.subtract(valorTotal).toString());
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        //Evento para buscar y cargar un cliente o abrir una nueva ventana para crear el cliente
        getTxtCliente().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                String identificacion=getTxtCliente().getText();
                if(!identificacion.equals(""))
                {
                    
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        try {
                            List<PersonaEstablecimiento> resultados=ServiceFactory.getFactory().getPersonaEstablecimientoServiceIf().buscarActivoPorIdentificacion(identificacion,session.getEmpresa()); //Todo crear mejor un metodo que ya obtener filtrado los datos
                            if(resultados.size()==0)
                            {
                                if(DialogoCodefac.dialogoPregunta("Crear Cliente","No existe el Cliente, lo desea crear?",DialogoCodefac.MENSAJE_ADVERTENCIA))
                                {
                                    btnListenerAgregarCliente();
                                }
                            }
                            else
                            {
                                cargarCliente(resultados.get(0));
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ServicioCodefacException ex) {
                            Logger.getLogger(FacturacionModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        
        getTxtCodigoDetalle().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Producto producto=FacturaModelControlador.listenerBuscarProducto(getTxtCodigoDetalle().getText(), controlador.getTipoDocumentoEnumSeleccionado(), session.getEmpresa());

                    if (producto == null) {
                        if (DialogoCodefac.dialogoPregunta("Crear Producto", "No existe el Producto, lo desea crear?", DialogoCodefac.MENSAJE_ADVERTENCIA)) {
                            btnListenerCrearProducto();
                        }
                    } else {
                        controlador.agregarProductoVista(producto,null);
                    }
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

    private void addListenerTablas() {
       
          
        getTblDetalleFactura().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                getTblDetalleFactura().addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {}

                    @Override
                    public void keyPressed(KeyEvent e) {
                        //Evento cuando se desea eliminar un dato de los detalles
                        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                            btnListenerEliminar();
                        }      
                        
                        //Permite salir del modo edicion y regresa al modo ingreso
                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                            btnListenerEditar();
                        }

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {}
                });
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
       
    }

    private void setearValoresVista() {
        UtilidadesSwingX.placeHolder("Identificación", getTxtCliente());
        UtilidadesSwingX.placeHolder("Código Producto", getTxtCodigoDetalle());
        UtilidadesSwingX.placeHolder("Descripción", getTxtDescripcion());
        
        // Activar o desactivar la vista de pagar con cartera segun sea el caso
        if(!session.verificarExisteModulo(ModuloCodefacEnum.CARTERA))
        {
            getChkPagoConCartera().setVisible(false);
        }
        
        
        
    }

    private void addPopUpListener() {
        JPopupMenu jPopupMenu=new JPopupMenu();
        JMenuItem jMenuItemDatoAdicional=new JMenuItem("Eliminar");
        jMenuItemDatoAdicional.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada=getTblDatosAdicionales().getSelectedRow();
                if(filaSeleccionada>=0)
                {
                    FacturaAdicional facturaAdicional=(FacturaAdicional)getTblDatosAdicionales().getValueAt(filaSeleccionada,0); //Obtener el objeto de la columna
                    factura.getDatosAdicionales().remove(facturaAdicional);
                    cargarTablaDatosAdicionales();//Volver a cargar los datos adicionales en la tabla de la vista
                }
            }
        });
        
        jPopupMenu.add(jMenuItemDatoAdicional);
        getTblDatosAdicionales().setComponentPopupMenu(jPopupMenu);
        
        
        //Agregar pop up para la tabla de formas de pago
        JPopupMenu jPopupMenu2=new JPopupMenu();
        JMenuItem jMenuItemFormaPago=new JMenuItem("Eliminar");
        jMenuItemFormaPago.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada=getTblFormasPago().getSelectedRow();
                if(filaSeleccionada>=0)
                {
                    factura.getFormaPagos().remove(filaSeleccionada);
                    cargarFormasPagoTabla();
                }
            }
        });
        
        jPopupMenu2.add(jMenuItemFormaPago);
        getTblFormasPago().setComponentPopupMenu(jPopupMenu2);
        
        

                
    }
    
    

    
    /**
     * TODO: Por el momento dejo de esta manera porque en proforma como herada sobrescribe estos metodos y causa conflicto toca ver como corregir ese probelma
     * @param facturaProcesando
     * @return 
     */
    //public static List<ComprobanteVentaData> getDetalleDataReporte(Factura facturaProcesando) 
    //{
    //    return getDetalleDataReporte(facturaProcesando);
    //}    
    
    //public Map<String, Object> getMapParametrosReporte(Factura facturaProcesando) {
    //    return controlador.getMapParametrosReporte(facturaProcesando);
    //}

    @Override
    public void eventoCambiarEstado() {
        if(estadoFormulario.equals(ESTADO_GRABAR))
        {
            getBtnCargarProforma().setEnabled(true);
            
            //getBtnAutorizarComprobante().setEnabled(false);
            //getBtnReProcesarComprobante().setEnabled(false);
            getPnlDatosAdicionales().habilitar(false);
            
            getBtnBuscarReferenciaContacto().setEnabled(true);
            getBtnBuscarVendedor().setEnabled(true);
            getBtnBuscarVendedor().setEnabled(true);
            getBtnLimpiarVendedor().setEnabled(true);
            getChkActivarFechaVencimiento().setEnabled(true);
            
            getBtnAgregarCliente().setEnabled(true);
            getBtnBuscarCliente().setEnabled(true);
            getTxtCliente().setEnabled(true);
            
            getjDateFechaEmision().setEnabled(true);
            getCmbPuntoEmision().setEnabled(true);
            getBtnAgregarFormaPago().setEnabled(true);
            
            getBtnAgregarProducto().setEnabled(true);
            getBtnCrearProducto().setEnabled(true);
            getBtnAgregarDetalleFactura().setEnabled(true);
            
            getCmbDocumento().setEnabled(true);
            getCmbTipoDocumento().setEnabled(true);
            getCmbFechaVencimiento().setEnabled(true);
        }
        else
        {
            getBtnCargarProforma().setEnabled(false);
            getPnlDatosAdicionales().habilitar(true);
            //getBtnAutorizarComprobante().setEnabled(true);
            
            getBtnBuscarReferenciaContacto().setEnabled(false);
            getBtnBuscarVendedor().setEnabled(false);
            getBtnBuscarVendedor().setEnabled(false);
            getBtnLimpiarVendedor().setEnabled(false);
            getChkActivarFechaVencimiento().setEnabled(false);
            
            getBtnAgregarCliente().setEnabled(false);
            getBtnBuscarCliente().setEnabled(false);
            getTxtCliente().setEnabled(false);
            
            getjDateFechaEmision().setEnabled(false);
            getCmbPuntoEmision().setEnabled(false);
            getBtnAgregarFormaPago().setEnabled(false);
            
            getBtnAgregarProducto().setEnabled(false);
            getBtnCrearProducto().setEnabled(false);
            getBtnAgregarDetalleFactura().setEnabled(false);
            
            getCmbDocumento().setEnabled(false);
            getCmbTipoDocumento().setEnabled(false);
            getCmbFechaVencimiento().setEnabled(false);

        }
    }

    private void addListenerChecks() {
        
        getChkActivarFinanciamiento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UtilidadesFormularios.habilitarComponentes(getChkActivarFinanciamiento().isSelected(),getTxtFinanciamientoEntrada(),getTxtFinanciamientoTarifa(),getTxtFinanciamientoDiaPago(),getTxtFinanciamientoNumeroCuotas());
            }
        });
        
        getChkActivarFechaVencimiento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listenerChkFechaVencimiento();
            }
        });
        
        getChkPagoConCartera().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listerChkPagoConCartera();
            }
        });
    }
    
    private void listerChkPagoConCartera()
    {
        controlador.cargarFormaPago();
    }
                
    private void listenerChkFechaVencimiento()
    {
        if (getChkActivarFechaVencimiento().isSelected()) {
                    if(getCmbFechaVencimiento().getDate()!=null)
                    {
                        String fechaStr = UtilidadesFecha.formatoDiaMesAño(new java.sql.Date(getCmbFechaVencimiento().getDate().getTime()));
                        factura.addDatoAdicional(new FacturaAdicional(ComprobanteAdicional.CampoDefectoEnum.FECHA_VENCIMIENTO.getNombre(), fechaStr,ComprobanteAdicional.Tipo.TIPO_OTRO));
                        factura.setFechaVencimiento(new java.sql.Date(getCmbFechaVencimiento().getDate().getTime()));
                    
                    }                    
                    getCmbFechaVencimiento().setEnabled(true);
                    getChkActivarFechaVencimiento().setSelected(true);

                } else {
                    FacturaAdicional fechaVencimientoDato=(FacturaAdicional) factura.obtenerDatoAdicionalPorCampo(ComprobanteAdicional.CampoDefectoEnum.FECHA_VENCIMIENTO);
                    if(fechaVencimientoDato!=null)
                    {
                        factura.getDatosAdicionales().remove(fechaVencimientoDato);
                    }
                    factura.setFechaVencimiento(null);
                    getCmbFechaVencimiento().setEnabled(false);
                    getChkActivarFechaVencimiento().setSelected(false);

                }
                cargarTablaDatosAdicionales();            
        
    }

    private void addListerFechas() {
        getCmbFechaVencimiento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getCmbFechaVencimiento().getDate()!=null)
                {
                    FacturaAdicional fechaVencimientoDato=(FacturaAdicional) factura.obtenerDatoAdicionalPorCampo(ComprobanteAdicional.CampoDefectoEnum.FECHA_VENCIMIENTO);
                    String fechaStr = UtilidadesFecha.formatoDiaMesAño(new java.sql.Date(getCmbFechaVencimiento().getDate().getTime()));                    
                    fechaVencimientoDato.setValor(fechaStr);
                    factura.setFechaVencimiento(new java.sql.Date(getCmbFechaVencimiento().getDate().getTime()));
                    
                    cargarTablaDatosAdicionales();                       
                    
                }
            }
        });
    }
    
    private void btnListenerArqueoCaja(Usuario usuario) throws ExcepcionCodefacLite, RemoteException, ServicioCodefacException
    {
        if(!usuario.getEmpleado().getDepartamento().getTipoEnum().equals(Departamento.TipoEnum.Supervisor))
        {
            DialogoCodefac.mensaje("Advertencia", "NO tiene autorización para realizar arqueo de caja", DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("Usuario no tiene autorización para realizar el arqueo de caja");
        }
        
        CajaSession cajaSession = ServiceFactory.getFactory().getCajaSesionServiceIf().obtenerCajaSessionPorPuntoEmisionYUsuario(getPuntoEmisionSeleccionado().getPuntoEmision(), session.getUsuario());
        if(cajaSession == null)
        {
            DialogoCodefac.mensaje("Advertencia", "No exite una sesión de caja activa", DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ServicioCodefacException("No exite una sesión de caja activa");
        }
        
        BigDecimal totalVentas = BigDecimal.ZERO;
        totalVentas = cajaSession.getValorApertura();
        if(!cajaSession.getIngresosCaja().isEmpty())
        {      
            for(IngresoCaja ingresoCaja: cajaSession.getIngresosCaja())
            {
                totalVentas = totalVentas.add(ingresoCaja.getValor());
            }            
        }
        
        Object[] parametros={cajaSession, usuario, totalVentas};
        panelPadre.crearDialogoCodefac(new ObserverUpdateInterface<ArqueoCaja>() 
        {
            @Override
            public void updateInterface(ArqueoCaja entity) 
            {
                DialogoCodefac.mensaje("Se realizo un arqueo de caja exitoso", DialogoCodefac.MENSAJE_CORRECTO);
            }
        },VentanaEnum.ARQUEO_CAJA, false,parametros,this);
    }
    
    

    private void listenerComponentes() {
        getPnlDatosAdicionales().setComprobante(this);
    }

    @Override
    public ComprobanteEntity getComprobante() {
        return factura;
    }

    @Override
    public Empresa getEmpresa() {
        return session.getEmpresa();
    }

    @Override
    public InterfazComunicacionPanel getPanelPadre() {
        return panelPadre;
    }

    @Override
    public List<ComprobanteAdicional> getDatosAdicionales() {
        return (List<ComprobanteAdicional>)(Object) factura.getDatosAdicionales();
    }

    private void validacionesParaEditar() {
        //Solo para el caso que este sin autorizar dejo habilitado estos campos para que puedan ser modificados
        if (factura.getEstadoEnum().equals(ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR)) {
            getBtnAgregarCliente().setEnabled(true);
            getBtnBuscarCliente().setEnabled(true);
            getTxtCliente().setEnabled(true);
            getjDateFechaEmision().setEnabled(true);
        }
    }
    
    

    @Override
    public ClienteInterfaceComprobante getInterfaceComprobante() throws RemoteException{
        return new ClienteFacturaImplComprobante((FacturacionModel) formularioActual, factura, false);        
    }

    @Override
    public DocumentoEnum obtenerDocumentoSeleccionado() {
        return (DocumentoEnum) getCmbDocumento().getSelectedItem();
    }

    @Override
    public void setProductoSeleccionado(Producto producto) {
        this.productoSeleccionado=producto;
    }

    @Override
    public void setearValoresProducto(BigDecimal valorUnitario, String descripcion, String codigo, CatalogoProducto catologoProducto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosDetalleVista(BigDecimal valorUnitario,BigDecimal descuento,String descripcion,String codigo) {
        getTxtValorUnitario().setText(valorUnitario+"");
        getTxtDescripcion().setText(descripcion);
        getTxtDescripcion().setCaretPosition(0);
        getTxtDescuento().setText(descuento.toString());
        //getTxtValorUnitario().setText(productoSeleccionado.getValorUnitario().toString());
        //Dar foco a la cantidad a ingresar
        getTxtCantidad().setText("1");
        getTxtCodigoDetalle().setText(codigo);
        getTxtCantidad().requestFocus();
        getTxtCantidad().selectAll();
    }

    @Override
    public void habilitarComboIva(Boolean opcion) {
        getCmbIva().setEnabled(opcion);
    }

    @Override
    public void setComboIva(EnumSiNo enumSiNo) {
        getCmbIva().setSelectedItem(enumSiNo);
    }

    @Override
    public void setTxtValorUnitario(String valorUnitario) {
        getTxtValorUnitario().setText(valorUnitario);
    }

    @Override
    public TipoDocumentoEnum obtenerTipoDocumentoSeleccionado() {
        return controlador.getTipoDocumentoEnumSeleccionado();
    }

    @Override
    public Producto obtenerProductoSeleccionado() {
        return productoSeleccionado;
    }

    @Override
    public Presupuesto obtenerPresupuestoSeleccionado() {
        return presupuestoSeleccionado;
    }

    @Override
    public RubroEstudiante obtenerRubroSeleccionado() {
        return rubroSeleccionado;
    }

    @Override
    public String obtenerTxtDescuento() {
        return getTxtDescuento().getText();
    }

    @Override
    public String obtenerTxtCantidad() {
        return getTxtCantidad().getText();
    }

    @Override
    public String obtenerTxtDescripcion() {
        return getTxtDescripcion().getText();
    }

    @Override
    public String obtenerTxtValorUnitario() {
        return getTxtValorUnitario().getText();
    }

    @Override
    public EnumSiNo obtenerComboIva() {
        return (EnumSiNo) getCmbIva().getSelectedItem();
    }

    @Override
    public Factura obtenerFactura() {
        return factura;
    }

    @Override
    public Boolean obtenerCheckPorcentajeSeleccion() {
        return getCheckPorcentaje().isSelected();
    }

    @Override
    public void limpiarComboPrecioVenta() {
        getCmbPreciosVenta().removeAllItems();
    }

    @Override
    public void focoTxtCodigoDetalle() {
        getTxtCodigoDetalle().requestFocus();
        getTxtCodigoDetalle().selectAll();
    }

    @Override
    public void setearCantidadTxt(String cantidad) {
        getTxtCantidad().setText(cantidad);
    }

    @Override
    public void setearDescripcionTxt(String descripcion) {
        getTxtDescripcion().setText(descripcion);
        getTxtDescripcion().setCaretPosition(0);
    }

    @Override
    public void setearValorUnitarioTxt(String valorUnitario) {
        getTxtValorUnitario().setText(valorUnitario);
    }

    @Override
    public void setearDescuentoTxt(String descuento) {
        getTxtDescuento().setText(descuento);
    }

    @Override
    public void setearCodigoDetalleTxt(String codigoDetalle) {
        getTxtCodigoDetalle().setText(codigoDetalle);
    }

    public FacturaDetalle getFacturaDetalleSeleccionado() {
        return facturaDetalleSeleccionado;
    }

    public void setFacturaDetalleSeleccionado(FacturaDetalle facturaDetalleSeleccionado) {
        this.facturaDetalleSeleccionado = facturaDetalleSeleccionado;
    }

    public Boolean getModoEdicionDetalle() {
        return modoEdicionDetalle;
    }

    public void setModoEdicionDetalle(Boolean modoEdicionDetalle) {
        this.modoEdicionDetalle = modoEdicionDetalle;
    }
    
    public Boolean isPagoConCartera()
    {
        return getChkPagoConCartera().isSelected();
    }

    public FacturaModelControlador getControlador() {
        return controlador;
    }

    public void setControlador(FacturaModelControlador controlador) {
        this.controlador = controlador;
    }
    
    

    @Override
    public void cargarTotalesVista() {
        getLblSubtotalSinImpuesto().setText("" + factura.getSubtotalSinImpuestos().add(factura.getSubtotalImpuestos()));
        getLblSubtotal12().setText("" + factura.getSubtotalImpuestos());
        getLblSubtotal0().setText("" + factura.getSubtotalSinImpuestos());
        getLblValorIce().setText(""+factura.getIce());
        getLblIva12().setText("" + factura.getIva());
        getTxtValorTotal().setText("" + this.factura.getTotal());
        getLblSubTotalDescuentoConImpuesto().setText("" + factura.getDescuentoImpuestos());
        getLblSubTotalDescuentoSinImpuesto().setText("" + factura.getDescuentoSinImpuestos());
        getLblTotalDescuento().setText("" + factura.getDescuentoImpuestos().add(factura.getDescuentoSinImpuestos()));
    }

    @Override
    public Boolean validarIngresoDetalle() {
        return panelPadre.validarPorGrupo("detalles");
    }

    @Override
    public Integer filaSeleccionadaTablaDetalle() {
        return getTblDetalleFactura().getSelectedRow();
    }

    @Override
    public void seleccionarFilaTablaDetalle(int filaSeleccionada) {
        getTblDetalleFactura().setRowSelectionInterval(filaSeleccionada,filaSeleccionada);
    }

    @Override
    public void limpiarIngresoDetalleVista() {
        setearCantidadTxt("1");
        setearDescripcionTxt("");
        setearValorUnitarioTxt("");
        setearDescuentoTxt("0");
        setearCodigoDetalleTxt("");
        
    }

    @Override
    public ComprobanteDataInterface obtenerComprobanteData() {
        return FacturaModelControlador.obtenerComprobanteData(factura);
    }

    @Override
    public Estudiante getEStudiante() {
        return estudiante;
    }

}
