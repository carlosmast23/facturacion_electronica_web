/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;



import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.jtattoo.plaf.aero.AeroLookAndFeel;
import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
import com.jtattoo.plaf.fast.FastLookAndFeel;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.jtattoo.plaf.mint.MintLookAndFeel;
import com.jtattoo.plaf.smart.SmartLookAndFeel;
import com.jtattoo.plaf.texture.TextureLookAndFeel;
import ec.com.codesoft.codefaclite.configuraciones.model.CalculadoraModel;
import ec.com.codesoft.codefaclite.configuraciones.model.ComprobantesConfiguracionModel;
import ec.com.codesoft.codefaclite.configuraciones.model.RespaldarInformacionModel;
import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.PanelSecundarioAbstract;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.PanelSecundarioListener;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.ValidadorCodefacModel;
import ec.com.codesoft.codefaclite.controlador.utilidades.UtilidadesCoreCodefac;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.controlador.vistas.core.BindingFactoryComponents;
import ec.com.codesoft.codefaclite.controlador.vistas.core.ControladorCampoTextoAnot;
import ec.com.codesoft.codefaclite.controlador.vistas.core.RoutingComponentBinding;
import ec.com.codesoft.codefaclite.controlador.vistas.core.TextFieldBinding;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ComponentBindingAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.proxy.ReporteProxy;
import ec.com.codesoft.codefaclite.corecodefaclite.ayuda.AyudaCodefacAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.corecodefaclite.util.CampoBuscarAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.util.CursorPorDefectoAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.util.LimpiarAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ConsolaGeneral;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ValidacionCodefacAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.validacionPersonalizadaAnotacion;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.core.swing.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.dialog.ProcesoSegundoPlano;
import ec.com.codesoft.codefaclite.controlador.dialogos.EsperaSwingWorker;
import ec.com.codesoft.codefaclite.controlador.dialogos.EsperaSwingWorkerIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.RespaldosModelUtilidades;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.crm.model.ClienteModel;
import ec.com.codesoft.codefaclite.crm.model.ProductoModel;
import ec.com.codesoft.codefaclite.facturacion.model.FacturacionModel;
import ec.com.codesoft.codefaclite.main.archivos.ArchivoConfiguracionesCodefac;
import ec.com.codesoft.codefaclite.main.init.Main;
import ec.com.codesoft.codefaclite.main.interfaces.BusquedaCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.Licencia;
import ec.com.codesoft.codefaclite.licence.ValidacionLicenciaCodefac;
import static ec.com.codesoft.codefaclite.main.init.Main.modoAplicativo;
import static ec.com.codesoft.codefaclite.main.init.Main.obtenerPerfilesUsuario;
import ec.com.codesoft.codefaclite.main.panel.GeneralPanelForm;
import ec.com.codesoft.codefaclite.main.panel.VentanaManualUsuario;
import ec.com.codesoft.codefaclite.main.panel.WidgetVentasDiarias;
import ec.com.codesoft.codefaclite.main.report.VisualizadorJRViewer;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
import ec.com.codesoft.codefaclite.main.utilidades.UtilidadServicioWeb;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.AccesoDirecto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AccesoDirectoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PermisoVentana;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CategoriaMenuEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ConfiguracionImpresoraEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EstiloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModoProcesarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.FuncionesSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ModoSistemaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.MenuCodefacRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UtilidadesServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.imagen.UtilidadImagen;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadVarios;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesReflexion;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesReflexion.ResponseAnotacion;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesReflexion.ResponseAnotacionMetodo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuContainer;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.MenuElement;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.plaf.SplitPaneUI;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.text.JTextComponent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.swing.JRViewerToolbar;
import net.sf.jasperreports.view.JRSaveContributor;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.view.save.JRPdfSaveContributor;
import org.jfree.util.Log;

/**
 *
 * @author Carlos
 */
public class GeneralPanelModel extends GeneralPanelForm implements InterfazComunicacionPanel{

    private static final Logger LOG = Logger.getLogger(GeneralPanelModel.class.getName());
    
    private GeneralPanelModel generalPanelModel=this;
    private ControladorVista controladorVista;
    private ControladorCodefacInterface panelActual;
    private SwingBrowser browser ;
    private SwingBrowser browserPublicidad ;
    //private List<VentanaEnum> ventanasMenuList;
    private SessionCodefac sessionCodefac;
    private Map<String,PanelSecundarioAbstract> panelesSecundariosMap;
    
    
    /**
     * ========================================================================
     *          PARAMETROS DE CONFIGURACION DE LA PANTALLA SECUNDARIA
     * Esta pantalla me sirve para dividir de forma forma horizontal y vertical
     * la seccion secundaria
     * ========================================================================
     */
    private static double PROPORCION_HORIZONTAL=0.75d;
    private static double PROPORCION_VERTICAL=0.6d;
    
    private static double PROPORCION_HORIZONTAL_DEFAULT=0.75d;
    private static double PROPORCION_VERTICAL_DEFAULT=0.6d;
    
    private static double PROPORCION_HORIZONTAL_MIN=0.95d;
    private static double PROPORCION_VERTICAL_MIN=0.95d;
    
    
    private static double PROPORCION_HORIZONTAL_INICIAL=0.999999999d;
    private static double PROPORCION_VERTICAL_INICIAL=0.7d;
    
    /**
     * Variable que va a controlar cada que tiempo se va a mostrar publicidad
     */
    private HiloPublicidadCodefac hiloPublicidadCodefac;
    
    /**
     * Referencia al widget de Virtuall Mall para poder trabajar con este objeto
     */
    private WidgetVirtualMallModelo widgetVirtualMall;
    private WidgetVentasDiarias widgetVentasDiarias;
    private WidgetNotificacionCodefacModelo widgetNotificacionCodefac;
    
    /**
     * Varible que almacena la ip del servidor para setear en la pantalla
     */
    public String ipServidor; //Todo: agrupar estos datos de mejor maneras
    
    /**
     * Map que me permite tener grabadas las pantallas abiertas
     */
    private Map<GeneralPanelInterface,JMenuItem> mapPantallaAbiertas;
    
    /**
     * Variable para tener una lista de los iconos del sistema
     */
    private List<IconoPanel> listaIconos;
    
   
    public GeneralPanelModel() 
    {
        getjPanelSeleccion().setVisible(false);//Asumo que cuando se abre por primera vez la pantalla esta oculta
        mapPantallaAbiertas=new HashMap<GeneralPanelInterface, JMenuItem>();
       
        
    }
    
        
    /**
     * Iniciar todos los componentes de la aplicacion de escritorio
     */
    public void iniciarComponentesGenerales()
    {
        iniciarComponentesPantalla();
        iniciarComponentes();        
        agregarListenerBotonesDefecto();
        agregarListenerBotones();
        agregarListenerSplit();
        agregarListenerFrame();
        agregarListenerGraphics();
        agregarListenerItemMenu();
        cargarDatosAdicionales();
        actualizarTituloCodefac();        
        habilitarBotones(false);  
        abrirAsistenteConfiguracion();
        
    }
    
    public void abrirAsistenteConfiguracion()
    {
        /**
         * Solo abrir el asistente cuando no tiene grabado ninguna empresa
         */
        if(sessionCodefac.getEmpresa()==null)
        {
            VentanaEnum ventanaAsistente=VentanaEnum.ASISTENTE_CONFIGURACION;
            abrirVentanaCodefac((ControladorCodefacInterface) ventanaAsistente.getInstance(), ventanaAsistente);
        }
    }   
    
    public void agregarPanelesSecundarios()
    {
        for (Map.Entry<String, PanelSecundarioAbstract> entry : panelesSecundariosMap.entrySet()) {
            String key = entry.getKey();
            PanelSecundarioAbstract value = entry.getValue();
            getjPanelSeleccion().addTab(value.getNombrePanel(), (Component) value);
            value.addListenerPanelSecundario(new PanelSecundarioListener() {
                @Override
                public void mostrar() {
                    mostrarPanelSecundario(true,key);
                }
            });
        }
    }
        
    private void agregarListenerFrame()
    {
        
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                
            }

            @Override
            public void windowClosing(WindowEvent e) {
                eventoCerrarSistema();
            }

            @Override
            public void windowClosed(WindowEvent e){ }

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        //TODO: Falta implementar la funcionalidad para estos botones
        JPopupMenu popup = new JPopupMenu();
        JMenuItem jmenuItemOrdenarDefecto=new JMenuItem("Ordenar por defecto");
        
        popup.add(new JMenuItem("Nuevo"));
        popup.add(new JMenuItem("Actualizar"));
        popup.add(jmenuItemOrdenarDefecto);
        
        jmenuItemOrdenarDefecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                widgetVentasDiarias.setLocation(120,200);
                
                if(listaIconos!=null)
                {
                    for (IconoPanel iconoPanel : listaIconos) {
                        switch(iconoPanel.getTitulo())
                        {
                            case "Factura":
                                iconoPanel.setNuevaPosicion(30, 50);
                                break;
                                
                                case "Producto":
                                iconoPanel.setNuevaPosicion(30,250);
                                break;
                                
                                case "Calculadora":
                                iconoPanel.setNuevaPosicion(30,350);
                                break;
                                
                                case "Cliente":
                                iconoPanel.setNuevaPosicion(30,150);
                                break;
                                
                                case "Configurar":
                                iconoPanel.setNuevaPosicion(30,450);
                                break;
                        }
                        
                    }   
                }
                
                
            }
        });
        
        
        //popup.add(new JMenuItem("tres"));
        
        getjDesktopPane1().setComponentPopupMenu(popup);
    }
    
    @Override
    public void cerrarSession()
    {
        cerrarTodasPantallas();
        setVisible(false);
        Sucursal sucursalDefecto = ArchivoConfiguracionesCodefac.getInstance().getSucursalPorDefecto();
        LoginModel.DatosLogin datosLogin = Main.cargarLoginUsuario(this, sucursalDefecto);
        //Main.validacionesEmpresa(datosLogin.empresa, this); //Haciendo verificacion de validacion de la licencia y datos de la empresa
        sessionCodefac.setUsuario(datosLogin.usuario);
        sessionCodefac.setSucursal(datosLogin.sucursal);
        sessionCodefac.setMatriz(datosLogin.matriz);
        sessionCodefac.setPerfiles(Main.obtenerPerfilesUsuario(datosLogin.usuario));
        sessionCodefac.setEmpresa(datosLogin.empresa);
        setVentanasMenuList(null);
        actualizarNotificacionesCodefac();
        MonitorComprobanteModel.getInstance().eliminarTodosDatos();
        setearEtiquetasPiePaginaPantallaPrincipal();        
        abrirAsistenteConfiguracion();
        setVisible(true);
        
        //Runtime garbage = Runtime.getRuntime();
        //garbage.gc();
        
        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.INFO, "Liberando memoria GC...");
    }
    
    private void generarRespaldoBaseDatosPorCorreo()
    {
        try {
            //Respaldar solo cuando tiene configurado el parametro de grabar la base de datos
            if(ParametroUtilidades.comparar(sessionCodefac.getEmpresa(),ParametroCodefac.ParametrosRespaldoDB.DB_RESPALDO_AUTOMATICO_SALIR,EnumSiNo.SI))
            {
                //Validar que solo se ejectute en el SERVIDOR o en CLIENTE-SERVIDOR
                if(modoAplicativo.equals(ModoAplicativoModel.MODO_CLIENTE))
                {
                    return;
                }                
                
                try {
                    //Enviar al correo y generar el respaldo de la base de datos
                    RespaldosModelUtilidades.generarRespaldoUbicacion(true, sessionCodefac.getEmpresa(),null,false);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Evento que se ejecuta cuando cierran el sistema
     */
    private void eventoCerrarSistema()
    {
        String[] opciones = {"Salir", "Cambiar usuario", "Cancelar"};
        int opcionSeleccionada = DialogoCodefac.dialogoPreguntaPersonalizada("Alerta", "Por favor seleccione una opción?", DialogoCodefac.MENSAJE_ADVERTENCIA, opciones);
        switch (opcionSeleccionada) {
            case 0: {
                try {
                    //opcion de salir
                    
                    DialogoCodefac.mostrarDialogoCargando(new ProcesoSegundoPlano() {
                        @Override
                        public void procesar() {
                            //Grabar las posiciones de los widgets al momento de salir
                            grabarDatosPosicionesWidgetSalir();
                            generarRespaldoBaseDatosPorCorreo();
                            
                            //Solo detener la publicidad cuando exista
                            if (hiloPublicidadCodefac != null) {
                                hiloPublicidadCodefac.hiloPublicidad = false;
                            }
                            UtilidadServicioWeb.apagarServicioWeb(); //Apagar el servicio web
                        }
                        
                        @Override
                        public String getMensaje() {
                            return "Cerrando el Sistema ...";
                        }
                        
                    });
                } catch (ExcepcionCodefacLite ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                
                                            
                
                dispose();
                System.exit(0);
                break;



            case 1: //opcion cambiar de usuario
                cerrarSession();
                break;

            case 2:
                break;
        }
    }
    
    private void cerrarTodasPantallas()
    {
        JInternalFrame[] ventanas = getjDesktopPane1().getAllFrames();
        for (JInternalFrame ventana : ventanas) {
            ventana.dispose();

        }

    }
    
    private void grabarDatosPosicionesWidgetSalir()
    {
        ParametroCodefacServiceIf servicio=ServiceFactory.getFactory().getParametroCodefacServiceIf();
        //Grabar el celular si es distinto de vacio
        if(!widgetVirtualMall.getTxtCelular().equals(""))
        {
            try {
                ParametroCodefac parametro=servicio.getParametroByNombre(ParametroCodefac.CELULAR_VIRTUAL_MALL,sessionCodefac.getEmpresa());
                //Si no existe el parametro la crea en la base de datos
                if(parametro==null)
                {
                    parametro=new ParametroCodefac();
                    parametro.setNombre(ParametroCodefac.CELULAR_VIRTUAL_MALL);
                    parametro.setValor(widgetVirtualMall.getTxtCelular().getText());
                    parametro.setEmpresa(sessionCodefac.getEmpresa());
                    servicio.grabar(parametro);
                }
                else
                {
                    parametro.setValor(widgetVirtualMall.getTxtCelular().getText());
                    servicio.editar(parametro);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    private void agregarListenerGraphics()
    {
                
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                establecerImagenFondo();
            }
        
        });
    }
    
    public void establecerImagenFondo()
    {
        Image fondoImg=null;
        fondoImg = new javax.swing.ImageIcon(getClass().getResource("/img/general/fondoGeneral.png")).getImage();
        if(sessionCodefac!=null)
        {
            //Solo establecer fondos personalizados para usuarios pro
            if(sessionCodefac.getTipoLicenciaEnum().equals(TipoLicenciaEnum.PRO))
            {
                try {
                    ParametroCodefacServiceIf servicio=ServiceFactory.getFactory().getParametroCodefacServiceIf();
                    Map<String,ParametroCodefac> map=servicio.getParametrosMap(sessionCodefac.getEmpresa());
                    if(map!=null)
                    {
                        ParametroCodefac parametroCodefac=map.get(ParametroCodefac.IMAGEN_FONDO);
                        if(parametroCodefac!=null)
                        {
                            String valor=parametroCodefac.getValor();
                            if(!valor.equals(""))
                            {
                                
                                InputStream inputStream=RemoteInputStreamClient.wrap(ServiceFactory.getFactory().getRecursosServiceIf().getResourceInputStreamByFile(sessionCodefac.getEmpresa(),DirectorioCodefac.IMAGENES,valor));
                                //String pathImagen=map.get(ParametroCodefac.DIRECTORIO_RECURSOS).valor + "/" + DirectorioCodefac.IMAGENES.getNombre() + "/"+valor;
                                BufferedImage bufferImage= ImageIO.read(inputStream);
                                fondoImg = bufferImage;
                            }
                        }
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        getjDesktopPane1().setBorder(new Fondo(fondoImg));

        getjSplitPanel().setDividerLocation(PROPORCION_HORIZONTAL);
        getjSplitPanelVerticalSecundario().setDividerLocation(PROPORCION_VERTICAL);
        
    }
    
    private void agregarListenerSplit()
    {
        SplitPaneUI spui = this.getjSplitPanel().getUI();
        if (spui instanceof BasicSplitPaneUI) {
          // Setting a mouse listener directly on split pane does not work, because no events are being received.
          ((BasicSplitPaneUI) spui).getDivider().addMouseListener(new MouseAdapter() {
              @Override
              public void mouseReleased(MouseEvent e) {
                  //System.out.println("mouseReleasedE");
                  int division=getjSplitPanel().getDividerLocation();
                  int ancho=getWidth();
                  PROPORCION_HORIZONTAL=(double)division/(double)ancho;
                  //System.out.println("division:"+division+"ancho:"+ancho+"p1:"+PROPORCION_HORIZONTAL+">"+PROPORCION_HORIZONTAL_MIN);
                  //if(PROPORCION_HORIZONTAL>PROPORCION_HORIZONTAL_MIN)
                 //{
                      //PROPORCION_HORIZONTAL=PROPORCION_HORIZONTAL_INICIAL;
                  //}
                  getjSplitPanel().setDividerLocation(PROPORCION_HORIZONTAL);
                  
                  //System.out.println("division: "+division+"ancho: "+ancho+"proporcionos: "+proporcion);
              }
             
            });
        }
        
        SplitPaneUI spui2 = this.getjSplitPanelVerticalSecundario().getUI();
        if (spui2 instanceof BasicSplitPaneUI) {
          // Setting a mouse listener directly on split pane does not work, because no events are being received.
          ((BasicSplitPaneUI) spui2).getDivider().addMouseListener(new MouseAdapter() {
              @Override
              public void mouseReleased(MouseEvent e) {
                  //System.out.println("mouseReleasedE");
                  /*
                  int division=getjSplitPanelVerticalSecundario().getDividerLocation();
                  int ancho=getHeight();
                  PROPORCION_VERTICAL=(double)division/(double)ancho;
                  if(PROPORCION_VERTICAL>PROPORCION_VERTICAL_MIN)
                  {
                      PROPORCION_VERTICAL=PROPORCION_VERTICAL_INICIAL;
                  }
                  getjSplitPanel().setDividerLocation(PROPORCION_VERTICAL);
                  */
                  //System.out.println("division: "+division+"ancho: "+ancho+"proporcionos: "+proporcion);
              }             
            });
        }
        
    
    }
    
    /**
     * Carga toda la ayuda por defecto sin importar el panel
     */
    private void cargarAyudaTodo()
    {

        PanelSecundarioAbstract panelSecundario = panelesSecundariosMap.get(PanelSecundarioAbstract.PANEL_AYUDA);
        JPanel jpanel = (JPanel) panelSecundario;
        int ancho=getjPanelSeleccion().getWidth()-1;
        int alto=getjPanelSeleccion().getHeight()-1;

        if(browser!=null)
        {
            //Verifacar si la url cargada es la misma no volver a cargar
            if(!browser.getUrl().equals("http://www.cf.codesoft-ec.com/ayuda"))
            {
                browser = new SwingBrowser();
                browser.loadURL("http://www.cf.codesoft-ec.com/ayuda");
                browser.setBounds(1, 1,ancho,alto);
                jpanel.removeAll();
                jpanel.add(browser);
            }
            else
            {
                jpanel.removeAll();
                jpanel.add(browser);
            }
        }
        else
        {
            browser = new SwingBrowser();
            browser.loadURL("http://www.cf.codesoft-ec.com/ayuda");
            browser.setBounds(1, 1,ancho,alto);
            jpanel.removeAll();
            jpanel.add(browser);
        }
        //getjSplitPanelVerticalSecundario().setLeftComponent(getJPanelContenidoAuxiliar());
            
    }
    
    private void cargarPublicidad()
    {
            browserPublicidad = new SwingBrowser();
            browserPublicidad.loadURL(ParametrosSistemaCodefac.LINK_PUBLICIDAD_CODEDAC);
            browserPublicidad.setBounds(1, 1, getjPanelPublicidadContenido().getWidth() - 1, getjPanelPublicidadContenido().getHeight() - 1);
            getjPanelPublicidadContenido().removeAll();
            getjPanelPublicidadContenido().add(browserPublicidad);            
            //PROPORCION_VERTICAL=PROPORCION_VERTICAL_DEFAULT;
            //getjSplitPanelVerticalSecundario().setDividerLocation(PROPORCION_VERTICAL);
    }
    
    /**
     * Todo: Revisar si no existe alguna mejora en este metodo
     * @param ventana
     * @param menuControlador
     * @return 
     */
    private ControladorCodefacInterface abrirVentanaCodefac(ControladorCodefacInterface ventana,VentanaEnum menuControlador )
    {
        cambiarCursorEspera();
        //ControladorCodefacInterface ventana= (ControladorCodefacInterface) menuControlador.getInstance();
        if (!verificarPantallaCargada(ventana)) {
            //Este artificio se realiza porque cuando se reutilizaba un referencia de la pantalla generaba problemas con los dialogos7
            ventana = (ControladorCodefacInterface) menuControlador.createNewInstance();
            ventana.reconstruirPantalla(); //Metodo adicional que construye las pantallas laterales
            agregarListenerMenu(ventana, menuControlador.isMaximizado(), null, null);
        } else {
            try {
                if (ventana.isIcon()) {
                    ventana.setIcon(false);
                } else {
                    ventana.setSelected(true);
                }

            } catch (PropertyVetoException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        cambiarCursorNormal();
        return ventana;
    }
    
    /**
     * Verifica si la pantalla ya esta cargada en el escritorio
     * @return 
     */
    private boolean verificarPantallaCargada(ControladorCodefacInterface ventana)
    {
         JInternalFrame[] ventanas=getjDesktopPane1().getAllFrames();
         for (JInternalFrame ventanTemp : ventanas) {
            if(ventanTemp.equals(ventana))
            {
                return true;
            }
        }
         return false;
    }
    
    private String getTituloOriginal(String titulo)
    {
        int inicio=titulo.indexOf("[");
        if(inicio<0)
            return titulo;
        else
            return titulo.substring(0,inicio-1);
    }
    
    private void agregarListenerBotones()
    {
        getBtnHome().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               minimizarTodasVentanas();
            }
        });
        
        getBtnNuevo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame frame= getjDesktopPane1().getSelectedFrame();
                ControladorCodefacInterface frameInterface=(ControladorCodefacInterface) frame;
                
                /**
                 * Si ciclo de vida esta desactivado controlo manualmente el ciclo de vida
                 */
                if(!frameInterface.cicloVida)
                {
                    UtilidadesCoreCodefac.ejecutarLimpiar(frameInterface);
                    UtilidadesCoreCodefac.ejecutarLimpiar(UtilidadesCoreCodefac.getControladorTodoVista(frameInterface));                    
                    //frameInterface.limpiar();
                    frameInterface.procesarModoForzado=false;
                    return;
                }
                
                try
                {
                    boolean respuesta=true;
                    if(!frameInterface.salirSinGrabar())
                    {
                        if(frameInterface.estadoFormulario.equals(GeneralPanelInterface.ESTADO_GRABAR)) //Agregada validacion solo en el estado de grabar para que se ejecute cuando este desde la pantalla de 
                        {
                            respuesta=DialogoCodefac.dialogoPregunta("Advertencia","Existen datos ingresados , está seguro que desea limpiar la ventana?",DialogoCodefac.MENSAJE_ADVERTENCIA);
                        }
                    }
                    
                    //Solo si la respuesta es grabar ejecuta el metodo
                    if(respuesta)
                    {
                        frameInterface.nuevo();                        
                    }
                    else
                    {
                        return ; //cancelar la operacion si el usuario escoge no
                    }
                    
                }
                catch(UnsupportedOperationException exception)
                {
                    System.out.println("metodo no implementado"); 
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, exception);
                } catch (ExcepcionCodefacLite ex) {
                    //Cancela el ciclo de vida normal si manda una excecion
                    System.out.println(ex.getMessage()); 
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                } catch (RemoteException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    FuncionesSistemaCodefac.servidorConexionPerdida();
                }
                
                String tituloOriginal=getTituloOriginal(frame.getTitle());
                frame.setTitle(tituloOriginal+" [Nuevo]");
                frameInterface.estadoFormulario= ControladorCodefacInterface.ESTADO_GRABAR;
                frameInterface.eventoCambiarEstado();
                limpiarAnotaciones(frameInterface);
                
                
                try
                {
                    UtilidadesCoreCodefac.ejecutarLimpiar(frameInterface);                    
                    UtilidadesCoreCodefac.ejecutarLimpiar(UtilidadesCoreCodefac.getControladorTodoVista(frameInterface));
                    //frameInterface.limpiar();
                    frameInterface.procesarModoForzado=false;
                }
                catch(UnsupportedOperationException exception)
                {
                    System.out.println("metodo no implementado"); 
                }
                frameInterface.actualizarBindingComponent(false,true);
                    
                limpiarCamposValidacion(frameInterface);
                frameInterface.consola=new ConsolaGeneral();
                mostrarConsola(frameInterface.consola,true);                
            }
        });
        
        getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listenerBotonGuardar();
            }
        });
        
        
        
        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JInternalFrame frame= getjDesktopPane1().getSelectedFrame();
                ControladorCodefacInterface frameInterface=(ControladorCodefacInterface) frame;

                listenerBuscar(frame,new BusquedaCodefacInterface() {
                    @Override
                    public void buscar() throws ExcepcionCodefacLite {
                        
                        try {
                            //Si existe implementado el metodo buscar por defecto se ejecuta este metodo
                            frameInterface.buscar();
                            frameInterface.actualizarBindingComponent(false,true);
                        } catch (UnsupportedOperationException ex) {
                            //Este metodo se ejecuta si no existe implementacion del metodo buscar
                            /////////////////////////////////////////////////////////////////////////////////////////
                            //              BUSCAR EL DIALOGO PRIMERO EN EL CONTROLADOR
                            /////////////////////////////////////////////////////////////////////////////////////////
                            BuscarDialogoModel buscarDialogoModel = null;
                            VistaCodefacIf frameControlador = UtilidadesCoreCodefac.getControladorTodoVista(frameInterface);
                            if (frameControlador != null) {
                                buscarDialogoModel = new BuscarDialogoModel(frameControlador.obtenerDialogoBusqueda());
                            } else {
                                buscarDialogoModel = new BuscarDialogoModel(frameInterface.obtenerDialogoBusqueda());
                            }               
                            
                            //BuscarDialogoModel buscarDialogoModel=new BuscarDialogoModel(frameInterface.obtenerDialogoBusqueda());
                            ejectutarDialogoBusqueda(buscarDialogoModel,true,frameInterface,false);
                            frameInterface.actualizarBindingComponent(false,true);
                            
                        }catch (ExcepcionCodefacLite ex) {
                            LOG.log(Level.SEVERE,"Mensaje:"+ex.getMessage());
                            //Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                            throw ex;
                        } catch (RemoteException ex) {
                            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                            FuncionesSistemaCodefac.servidorConexionPerdida();
                        }
                    }
                });

                               
            }
        });
        
        getBtnActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try
                {
                    JInternalFrame frame= getjDesktopPane1().getSelectedFrame();
                    ControladorCodefacInterface frameInterface=(ControladorCodefacInterface) frame;
                    
                    /**
                     * Si ciclo de vida esta desactivado controlo manualmente el
                     * ciclo de vida
                     */
                    if (!frameInterface.cicloVida) {
                        frameInterface.actualizar();
                        return;
                    }
                    
                    frameInterface.actualizar();
                    limpiarCamposValidacion(frameInterface);
                }
                catch (UnsupportedOperationException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    //getjButton4().setEnabled(false);
                } catch (ExcepcionCodefacLite ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RemoteException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    FuncionesSistemaCodefac.servidorConexionPerdida();
                }
                               
            }
        });
        
        getBtnEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try
                {
                    JInternalFrame frame= getjDesktopPane1().getSelectedFrame();
                    ControladorCodefacInterface frameInterface=(ControladorCodefacInterface) frame;
                    
                    /**
                     * Si ciclo de vida esta desactivado controlo manualmente el
                     * ciclo de vida
                     */
                    if (!frameInterface.cicloVida) {
                        frameInterface.eliminar();
                        return;
                    }
                    
                    String tituloOriginal=getTituloOriginal(frame.getTitle());     
                    
                    //Ejecutar el metodo eliminar del controlador si existe
                    VistaCodefacIf frameControlador= UtilidadesCoreCodefac.getControladorTodoVista(frameInterface);
                    if(frameControlador!=null)
                    {                    
                        frameControlador.eliminar();
                    }
                    else
                    {
                        frameInterface.eliminar();
                    }
                    
                    
                    frameInterface.estadoFormulario= ControladorCodefacInterface.ESTADO_GRABAR;
                    frameInterface.eventoCambiarEstado();
                    limpiarAnotaciones(frameInterface);
                    
                    UtilidadesCoreCodefac.ejecutarLimpiar(frameInterface);
                    UtilidadesCoreCodefac.ejecutarLimpiar(UtilidadesCoreCodefac.getControladorTodoVista(frameInterface));
                    //frameInterface.limpiar();
                    frameInterface.procesarModoForzado=false;
                    
                    limpiarCamposValidacion(frameInterface);
                    
                    frameInterface.consola=new ConsolaGeneral();
                    mostrarConsola(frameInterface.consola,true);
                    frame.setTitle(tituloOriginal+" [Nuevo]");
                    
                    //Actualizar Vista
                    frameInterface.actualizarBindingComponent(false,true);           
                }
                catch (UnsupportedOperationException ex) {
                    System.err.println("Metodo no implementado");
                   // Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    //getjButton4().setEnabled(false);
                } catch (ExcepcionCodefacLite ex) {
                    //Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    System.err.println(ex.getMessage());
                } catch (RemoteException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    FuncionesSistemaCodefac.servidorConexionPerdida();
                }
                    
            }
        });
        
        getBtnImprimir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try
                {
                    cambiarCursorEspera();
                    JInternalFrame frame = getjDesktopPane1().getSelectedFrame();
                    ControladorCodefacInterface frameInterface = (ControladorCodefacInterface) frame;
                
                    /**
                     * Si ciclo de vida esta desactivado controlo manualmente el
                     * ciclo de vida
                     */
                    if (!frameInterface.cicloVida) {
                        frameInterface.imprimir();
                        cambiarCursorNormal();
                        return;
                    }
                    
                    frameInterface.imprimir();
                    limpiarCamposValidacion(frameInterface);
                    
                }
                catch (UnsupportedOperationException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    //getjButton4().setEnabled(false);
                } catch (ExcepcionCodefacLite ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RemoteException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    FuncionesSistemaCodefac.servidorConexionPerdida();
                }                
                cambiarCursorNormal();
                
            }
        });
        
        getBtnAyuda().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //abrirPanelSecundario();
                    abrirManualUsuario();
                }
            });
        
        
         
         getBtnSalirPantallaPublicidad().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    getjSplitPanelVerticalSecundario().setDividerLocation(0.9999999999d);
                    PROPORCION_VERTICAL=0.9999999999d;
                    //browser=null;
                }
            });
        
    }
    
    private void listenerBotonGuardar() {

        JInternalFrame frame = getjDesktopPane1().getSelectedFrame();
        ControladorCodefacInterface frameInterface = (ControladorCodefacInterface) frame;
        /**
         * Verificar si el proceso es normal o la ventan funciona como dialogo
         */
        if (frameInterface.modoDialogo) {
            if (validarFormulario(frameInterface, ValidacionCodefacAnotacion.GRUPO_FORMULARIO)) {
                try {
                    DialogInterfacePanel interfaz = (DialogInterfacePanel) frame;
                    Object resultado = interfaz.getResult();
                    frame.dispose();
                    mostrarPanelSecundario(false);
                    //Setear el focus al formulario que abrio el dialogo
                    frameInterface.formOwnerFocus.moveToFront();
                    frameInterface.formOwnerFocus.setSelected(true);

                    frameInterface.formOwner.updateInterface(resultado);

                } catch (ExcepcionCodefacLite ex) {
                    DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));                    
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (PropertyVetoException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                mostrarPanelSecundario(true);
            }
            return;

        }

        try {
            boolean procesoTerminado = false;

            if (frameInterface.estadoFormulario.equals(ControladorCodefacInterface.ESTADO_GRABAR)) {

                if (validarFormulario(frameInterface, ValidacionCodefacAnotacion.GRUPO_FORMULARIO)) {
                    try {
                        frameInterface.actualizarBindingComponent(false,true);
                        
                        VistaCodefacIf frameControlador= UtilidadesCoreCodefac.getControladorTodoVista(frameInterface);
                        if(frameControlador!=null)
                        {
                            frameControlador.grabar();
                        }
                        else
                        {
                            frameInterface.grabar();
                        }

                        /////==========> AGREGAR VALIDACION SI POR ALGUN MOTIVO NO ESTA IMPLEMENTADO LLAMAR AL METODO NUEVO =========////
                        try {
                            frameInterface.nuevo(); //TODO: Ver si despues de grabar si se debe llamar al evento nuevo
                        } catch (UnsupportedOperationException ex) {
                            LOG.log(Level.WARNING, "Metodo nuevo no implementeado en ventana " + frameInterface.getName());
                        }

                        procesoTerminado = true;
                    } catch (ExcepcionCodefacLite ex) {
                        //ex.printStackTrace();
                        System.err.println(ex.getMessage());
                        //JOptionPane.showMessageDialog(null,ex.getMessage());
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (RemoteException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                        FuncionesSistemaCodefac.servidorConexionPerdida();
                    }

                } else {
                    mostrarConsola(frameInterface.consola, true);
                    //JOptionPane.showMessageDialog(null,"Error de validacion Nuevo");
                }
            } else {
                if (validarFormulario(frameInterface, ValidacionCodefacAnotacion.GRUPO_FORMULARIO)) {
                    try {
                        //Logica para alternar entre controlador y vista model
                        VistaCodefacIf frameControlador= UtilidadesCoreCodefac.getControladorTodoVista(frameInterface);
                        if(frameControlador!=null)
                        {
                            frameControlador.editar();
                        }else
                        {                        
                            frameInterface.editar();
                        }
                        procesoTerminado = true;
                    } catch (ExcepcionCodefacLite ex) {
                        //ex.printStackTrace();
                        LOG.log(Level.WARNING, ex.getMessage());
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (RemoteException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                        FuncionesSistemaCodefac.servidorConexionPerdida();
                    }

                } else {
                    mostrarConsola(frameInterface.consola, true);
                    //JOptionPane.showMessageDialog(null,"Error de validacion Editar");
                }

            }

            /**
             * Si ciclo de vida esta desactivado controlo manualmente el ciclo
             * de vida
             */
            if (!frameInterface.cicloVida) {
                UtilidadesCoreCodefac.ejecutarLimpiar(frameInterface);
                UtilidadesCoreCodefac.ejecutarLimpiar(UtilidadesCoreCodefac.getControladorTodoVista(frameInterface));
                
                //frameInterface.limpiar();
                frameInterface.procesarModoForzado=false;
                return;
            }

            if (procesoTerminado) {
                String tituloOriginal = getTituloOriginal(frame.getTitle());
                frame.setTitle(tituloOriginal + " [Nuevo]");
                frameInterface.estadoFormulario = ControladorCodefacInterface.ESTADO_GRABAR;
                frameInterface.eventoCambiarEstado();
                limpiarAnotaciones(frameInterface);                
                
                UtilidadesCoreCodefac.ejecutarLimpiar(frameInterface);
                UtilidadesCoreCodefac.ejecutarLimpiar(UtilidadesCoreCodefac.getControladorTodoVista(frameInterface));
                                
                limpiarCamposValidacion(frameInterface);
                frameInterface.actualizarBindingComponent(false,true);
            }
        } catch (UnsupportedOperationException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Metodo no implementado boton editar");
            //Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            //getjButton4().setEnabled(false);
        }
    }
            
    
    private void abrirPanelSecundario()
    {
        if (!getjPanelSeleccion().isVisible())//Si no esta visible el panel lo muestra
        {
            //cargarAyuda();
            mostrarPanelSecundario(true, PanelSecundarioAbstract.PANEL_AYUDA);
        } else //Si esta vosible el panel lo oculta
        {
            //cargarAyuda();
            mostrarPanelSecundario(false, PanelSecundarioAbstract.PANEL_AYUDA);
        }
    }
    
    /**
     * Listener que ejecuta el dialogo para buscar datos
     * @param frame
     * @param busquedaInterface
     * @param validacionDatosIngresados variable para saber si quiere validar si existen datos 
     */
    private void listenerBuscar(JInternalFrame frame,BusquedaCodefacInterface busquedaInterface)
    {        
        ControladorCodefacInterface frameInterface=(ControladorCodefacInterface) frame;
        
        //Variable para devolver al estado original si cancela o lanza una excepcion la busqueda        
        String estadoFomularioTemp=frameInterface.estadoFormulario;
            try
                {
                    /**
                     * Si ciclo de vida esta desactivado controlo manualmente el
                     * ciclo de vida
                     */
                    if (!frameInterface.cicloVida) {
                       busquedaInterface.buscar();
                       frameInterface.actualizarBindingComponent(false,true);
                        return;
                    }

                    
                    frameInterface.estadoFormulario= ControladorCodefacInterface.ESTADO_EDITAR; 
                    frameInterface.eventoCambiarEstado();
                    busquedaInterface.buscar();
                    frameInterface.actualizarBindingComponent(false,true);
                    
                    
                    limpiarCamposValidacion(frameInterface);
                    mostrarPanelSecundario(false);
                    String tituloOriginal = getTituloOriginal(frame.getTitle());
                    frame.setTitle(tituloOriginal + " [Editar]");

                }
                catch (ExcepcionCodefacLite ex) 
                {
                    frameInterface.estadoFormulario=estadoFomularioTemp; //Regresa al estado original si se lanza alguna excepcion
                    frameInterface.eventoCambiarEstado();
                    LOG.log(Level.WARNING,ex.getMessage());
                    //Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    //Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    /**
     * 
     * @param dialogModel
     * @param frameInterface
     * @param cargarDirecto //si esta en tru y existe un solo dato carga directamente sin confirmacion del usuario
     * @throws ExcepcionCodefacLite 
     */
    private void ejectutarDialogoBusqueda(BuscarDialogoModel dialogModel,boolean  validacionDatosIngresados,GeneralPanelInterface frameInterface,boolean cargarDirecto) throws ExcepcionCodefacLite
    {   
        //Si no existe datos no muestro nada
        if (dialogModel.getTamanioConsulta() == 0) {
            DialogoCodefac.mensaje("Info", "No existe resultados en la busqueda", DialogoCodefac.MENSAJE_ADVERTENCIA);
            throw new ExcepcionCodefacLite("No existen datos en la consulta para abrir el dialogo de busqueda");
        } 
        
       
        //Si desea cargar directo , lo hace cuando existe un solo resultado
        if(cargarDirecto)
        {
            if(dialogModel.getTamanioConsulta() == 1)
            {
                Object resultado=dialogModel.obtenerResultadoLista(0); //obtiene el primer resultado para cargar en la pantalla
                
                //Preguntar si desea cargar los datos perdiendo los datos ingresados
                if(preguntarCargarDatosBuscar(validacionDatosIngresados,frameInterface))
                {
                    //Cargar datos desde el controlador en caso de que exista
                    VistaCodefacIf frameControlador = UtilidadesCoreCodefac.getControladorTodoVista(frameInterface);
                    if (frameControlador != null) {
                        frameControlador.cargarDatosPantalla(resultado);
                    }
                    //frameInterface.cargarDatosPantalla(resultado);
                    UtilidadesCoreCodefac.ejecutarCargarDatosPantalla(frameInterface, resultado);
                    
                }
                return;
            }
        }

        //Si no carga directo el dato se abre la pantalla normal de seleccion para el usuario
        dialogModel.setVisible(true);
        Object resultado=dialogModel.getResultado();                
        if(resultado!=null)
        {
            //Preguntar si desea cargar los datos perdiendo los datos ingresados
            if(preguntarCargarDatosBuscar(validacionDatosIngresados,frameInterface))
            {
                //Cargar datos desde el controlador en caso de que exista
                VistaCodefacIf frameControlador = UtilidadesCoreCodefac.getControladorTodoVista(frameInterface);
                if (frameControlador != null) {
                    frameControlador.cargarDatosPantalla(resultado);
                }
                //frameInterface.cargarDatosPantalla(resultado);
                UtilidadesCoreCodefac.ejecutarCargarDatosPantalla(frameInterface,resultado);
            }
            else
            {
                throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar, no desea cargar los datos");
            }
        }
        else
        {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar, no selecciono ningun dato");
        }
    }
    
    private boolean preguntarCargarDatosBuscar(boolean  validacionDatosIngresados,GeneralPanelInterface frameInterface)
    {
        //Solo ejecutar si requiere validacion de campos ingresados
        if(validacionDatosIngresados)
        {
            if (!frameInterface.salirSinGrabar()) {
                boolean respuesta = DialogoCodefac.dialogoPregunta("Advertencia", "Existen datos ingresados , está seguro que desea cargar de todos modos?", DialogoCodefac.MENSAJE_ADVERTENCIA);
                return respuesta;
            }
        }
        return true;
    }
    
    /**
     * TODO: optimizar este codigo porque existe 2 repetidos con diferentes parametros
     * @param opcion 
     */
    private void mostrarPanelSecundario(boolean  opcion)
    {
        if(opcion)
        {
            //Valores para mostrar en la pantalla secundaria
            PROPORCION_HORIZONTAL = PROPORCION_HORIZONTAL_DEFAULT;
            PROPORCION_VERTICAL = PROPORCION_VERTICAL_DEFAULT;
            getjPanelSeleccion().setVisible(opcion); //Vuelve visible el componente secundario
        }
        else
        {
            PROPORCION_HORIZONTAL = PROPORCION_HORIZONTAL_INICIAL;
            PROPORCION_VERTICAL = 0.9999999999999999d;
            getjPanelSeleccion().setVisible(opcion); //Vuelve invisible el componente secundario

        }
        
        getjSplitPanel().setDividerLocation(PROPORCION_HORIZONTAL);
        getjSplitPanelVerticalSecundario().setDividerLocation(PROPORCION_VERTICAL);


    }
    
    private void mostrarPanelSecundario(boolean  opcion,String panelSecundario)
    {
        if(opcion)
        {
            //Valores para mostrar en la pantalla secundaria
            PROPORCION_HORIZONTAL = PROPORCION_HORIZONTAL_DEFAULT;
            PROPORCION_VERTICAL = PROPORCION_VERTICAL_DEFAULT;
            getjPanelSeleccion().setVisible(opcion); //Vuelve visible el componente secundario
        }
        else
        {
            PROPORCION_HORIZONTAL = PROPORCION_HORIZONTAL_INICIAL; //proporcion que oculta en  la pantalla
            PROPORCION_VERTICAL = 0.9999999999999999d; //Proporcion que oculta en la pantall
            getjPanelSeleccion().setVisible(opcion); //Vuelve invisible el componente secundario

        }
        
        getjSplitPanel().setDividerLocation(PROPORCION_HORIZONTAL);
        getjSplitPanelVerticalSecundario().setDividerLocation(PROPORCION_VERTICAL);
        getjPanelSeleccion().setSelectedComponent(panelesSecundariosMap.get(panelSecundario)); //Seleccionar el tab del panel secundario seleccionado

    }
    private boolean verificarPermisosVentana(List<String> permisosVentana,List<Perfil> rolesUsuario)
    {
        //Si la ventana no tiene permisos o no esta implementado puede acceder
        if(permisosVentana==null)
            return true;
        
        for (Perfil rolUsuario : rolesUsuario) {
            for (String permisoVentana : permisosVentana) {
                if(rolUsuario.getNombre().equals(permisoVentana))
                {
                    return true;
                }
            }
        }
        return false;
    
    }
   
    
    
    
   
    private void addbindingVista(ControladorCodefacInterface vista,Boolean get,Boolean set)
    {
        //Obtiene un mapa , con todos los compontBinding y sus implementaciones para buscar
        for (Map.Entry<Class,Class> entry : RoutingComponentBinding.routingMap.entrySet()) {
            Class claseBindingControlador = entry.getKey();
            Class componentBindingAbstractClass=entry.getValue();
            
            List<ResponseAnotacionMetodo> anotaciones=UtilidadesReflexion.buscarAnotacionEnMetodos(vista.getClass(),claseBindingControlador);
            
            for (ResponseAnotacionMetodo<TextFieldBinding> anotacion : anotaciones) {
                try {
                    ComponentBindingAbstract implementacion = (ComponentBindingAbstract) componentBindingAbstractClass.newInstance();
                    implementacion.init(vista, anotacion);
                    implementacion.ejecutar(get,set);
                    //Agregar a la vista los nuevos binding en cada vista
                    vista.agregarBindingComponent(implementacion);
                    
                } catch (InstantiationException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        }
    }
    

    
    
    
    private void agregarListenerMenu(ControladorCodefacInterface panel,boolean maximisado,Integer ancho ,Integer alto)
    {
        try {
            
            //Recargar parametros de configuracion
            sessionCodefac.setParametrosCodefac(null);
            
            //Anular el metodo de cierre automatico para controlar manualmente
            panel.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
            
            ////////////////////////////////////////////////////////////////////
            //          INYECCION DE DEPENDENDIAS DE LAS REFERENCIAS
            ////////////////////////////////////////////////////////////////////           
            panel.estadoFormulario= ControladorCodefacInterface.ESTADO_GRABAR;
            panel.eventoCambiarEstado();
            panel.panelPadre=generalPanelModel;
            panel.session=sessionCodefac;
            
            try {
                //Ejecuta la vista por defecto
                UtilidadesCoreCodefac.ejecutarIniciar(panel);
                //Ejecuta el iniciar del controlador general si existe 
                UtilidadesCoreCodefac.ejecutarIniciar(UtilidadesCoreCodefac.getControladorTodoVista(panel));          
            } catch (ExcepcionCodefacLite ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                //Si ocurre un problema al momento de iniciar cancelo la apertura
                return ;
            }
                        
            
            //Agregar el listener que controla las acciones del formulario (cerrar, maximar)
            panel.addInternalFrameListener(listenerFrame);
            
            ////////////////////////////////////////////////////////////////////
            //          CARGAR EL TITULO DE LA PANTALLA
            ////////////////////////////////////////////////////////////////////
            //String tituloOriginal=getTituloOriginal(panel.getTitle());
            
            //Por defecto intenta cargar los nombres declarados en el archivo que tiene las ventanas
            VentanaEnum ventanaEnum=VentanaEnum.getByClass(panel.getClass());
            String tituloOriginal="";
            if(ventanaEnum!=null)
            {
                tituloOriginal=ventanaEnum.getNombre();
            }
            else //Si por algun motivo no logra cargar del archivo de vista obtiene el archivo de su propiedad de nombres
            {
                tituloOriginal=getTituloOriginal(panel.getTitle());
            }
            
            panel.setTitle(tituloOriginal+" [Nuevo]");
            try
            {
                getjDesktopPane1().add(panel);
            }
            catch(java.lang.IllegalArgumentException ex)
            {
                System.out.println("Error al agregar el panel a la vista");
                ex.printStackTrace();
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            panel.setMaximum(maximisado);
            panel.show();
            getBtnNuevo().requestFocus();
            agregarValidadores(panel); //Agregar validadores para los campos de ingreso
            agregarAyudas(panel);
            
            ////////////////////////////////////////////////////////////////////
            //          EJECUTAR LOS METODOS DE LIMPIAR Y NUEVO
            ////////////////////////////////////////////////////////////////////

            UtilidadesCoreCodefac.ejecutarLimpiar(panel);
            UtilidadesCoreCodefac.ejecutarLimpiar(UtilidadesCoreCodefac.getControladorTodoVista(panel));
            
            UtilidadesCoreCodefac.ejecutarNuevo(panel);
            UtilidadesCoreCodefac.ejecutarNuevo(UtilidadesCoreCodefac.getControladorTodoVista(panel));
            
            //================================================================//
            //         CONSTRUIR LOS CONTROLADORES PARA MANEJAR EL MVVC
            //================================================================//
            addbindingVista(panel,true,true);
            
            /**
             * Mostrar la pantalla centrada cuando no se muestra maximisado
             */
            if(!maximisado)
            {
                Dimension desktopSize =getjDesktopPane1().getSize(); //tamanio del escritorio
                Dimension jInternalFrameSize = panel.getPreferredSize(); //tamanio de la ventana
                
                if(ancho==null & alto==null)
                {
                    double anchoTmp=(double)(desktopSize.width - jInternalFrameSize.width) /(double) 2;
                    double altoTmp=(double)(desktopSize.height - jInternalFrameSize.height) /(double) 2;
                    //double alto=(double)(desktopSize.height) /(double) 2;
                    panel.setLocation((int)anchoTmp,(int)altoTmp);
                }
                else
                {
                    panel.setSize(ancho,desktopSize.height);
                    double anchoTmp = (double) (desktopSize.width - jInternalFrameSize.width) / (double) 2;
                    double altoTmp = (double) (desktopSize.height - jInternalFrameSize.height) / (double) 4;
                    panel.setLocation((int)anchoTmp,0);
                }
            }
            
            
            panel.consola=new ConsolaGeneral();
            mostrarConsola(panel.consola,true);            
            //Agregar ventana al combo de ventanas abiertas
            agregarVentanaAbierta(panel,false);
            
            //TODO: Metodo de prueba para solucionar si se cambia de pantalla por ejemplo con abrir con parametros no estaba funcionando los permisos de 
            //habilitarConfiguracioneBotones();
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.INFO,"Abriendo pantalla : "+panel.getTitle());
                        
        } catch (PropertyVetoException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo para agregar en un metodo todas las ventanas abiertas
     * @param panel 
     */
    private void agregarVentanaAbierta(GeneralPanelInterface panel,Boolean reporte)
    {
        JMenuItem jmenuItem=mapPantallaAbiertas.get(panel);
        if(jmenuItem==null)
        {
            if(reporte)
            {
                jmenuItem=new JMenuItem(panel.toString());
            }
            else
            {    
                jmenuItem=new JMenuItem(panel.toString());
                seleccionarVentanaActivaMenu();
            }
            //jmenuItem.setFont(new Font("Arial", Font.BOLD, 13));
            
            jmenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (panel != null) {
                        try {
                            if (panel.isIcon()) {
                                panel.setIcon(false);
                            }

                            panel.setSelected(true);
                            seleccionarVentanaActivaMenu();
                        } catch (PropertyVetoException ex) {
                            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            
            mapPantallaAbiertas.put(panel, jmenuItem);
        }
        
        getjMenuVentanasActivas().add(jmenuItem);
        
    }
    
    /**
     * 
     * Metodo que permite poner check en la ventana que este seleccionada
     */
    private void seleccionarVentanaActivaMenu()
    {
        GeneralPanelInterface panel= getPanelActivo();
        
        for (Map.Entry<GeneralPanelInterface, JMenuItem> entry : mapPantallaAbiertas.entrySet()) {
            GeneralPanelInterface key = entry.getKey();
            JMenuItem value = entry.getValue();            
            if(panel!=null)
            {
                if(panel.equals(key))
                {
                    value.setFont(new Font("Arial", Font.BOLD, 13));      
                }
                else
                {
                    value.setFont(new Font("Arial", Font.PLAIN, 13));
                }
            }
            
        }
    }
    
    /**
     * Metodo para quitar del menu alguna ventana abierta
     */
    private void quitarVentanaAbierta(GeneralPanelInterface panel)
    {
        JMenuItem jmenuItem=mapPantallaAbiertas.get(panel);
        if(jmenuItem!=null)
        {
            //Eliminar la referencia del map
            mapPantallaAbiertas.remove(panel);
            //Eliminar la referencia del menu
            getjMenuVentanasActivas().remove(jmenuItem);
        }
    }
    
    /**
     * Metodo que cargaba las ayudar como una especie de código en cada campo
     * Metodo en desuso por que las ayudas se cargan de otro firma directa
     * TODO: En todo caso revisar si no se puede rehusar
     * @param panel
     * @deprecated
     */
    @Deprecated 
    private void agregarAyudas(ControladorCodefacInterface panel)
    {
        Class classVentana=panel.getClass();
        Method[] metodos=classVentana.getMethods();
        for (Method metodo : metodos) {
            AyudaCodefacAnotacion validacion=metodo.getAnnotation(AyudaCodefacAnotacion.class);
            //System.out.println(metodo.getName());
            if(validacion!=null)
            {
                try {
                    JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                    InputStream input=RecursoCodefac.AYUDA.getResourceInputStream(validacion.recurso());
                    String htmlText=UtilidadVarios.getStringHtmltoUrl(input);
                    File file = new File(RecursoCodefac.AYUDA.getResourceURL(validacion.recurso()).getPath());
                    //File file = new File(getClass().getResource("/pagina/ayudaHtml.html").toURI());
                    
                    String path="file:"+file.getParentFile().toURI().getPath();
                    htmlText=htmlText.replace("[recurso]",path);
                    ToolTipManager.sharedInstance().setDismissDelay(100000);
                    componente.setToolTipText(htmlText);

                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    //Metodo que permite agregar un metodo para la pantalla de busqueda directo desde el formulario
    //TODO: Ver si se puede optimizar de mejor manera con los otros validadores porque es muchas repeticiones
    private void agregarListenerBusqueda(ControladorCodefacInterface panel)
    {
       boolean validado=true;
       
       Class classVentana=panel.getClass();
        Method[] metodos=classVentana.getMethods();
        for (Method metodo : metodos) {
            LimpiarAnotacion validacion=metodo.getAnnotation(LimpiarAnotacion.class);

            if(validacion!=null)
            {
                validado=false;
                try {
                    JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                    componente.setText("");
                    
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
    
    private void limpiarAnotaciones(ControladorCodefacInterface panel)
    {
       boolean validado=true;
       
       Class classVentana=panel.getClass();
        Method[] metodos=classVentana.getMethods();
        for (Method metodo : metodos) {
            LimpiarAnotacion validacion=metodo.getAnnotation(LimpiarAnotacion.class);

            if(validacion!=null)
            {
                validado=false;
                try {
                    JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                    componente.setText("");
                    
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
    //TODO: optimizar para que exista un solo meotodo para correr las validaciones para que no exista tantos recorridos de gana
    private void limpiarCamposValidacion(ControladorCodefacInterface panel)
    {
       ConsolaGeneral consola=new ConsolaGeneral();
       
       Class classVentana=panel.getClass();
        Method[] metodos=classVentana.getMethods();
        for (Method metodo : metodos) {
            
            //Si no es metodo de tipo get ya no valida
            if(metodo.getName().indexOf("get")!=0)
            {
                continue;
            }
            
            LimpiarAnotacion validacion=metodo.getAnnotation(LimpiarAnotacion.class);
            //System.out.println(metodo.getName());
            if(validacion!=null)
            {
                try {
                    JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                    componente.setBackground(Color.WHITE);
                    
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            CursorPorDefectoAnotacion anotacionCursor=metodo.getAnnotation(CursorPorDefectoAnotacion.class);
            if(anotacionCursor!=null)
            {
                try {
                    JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                    componente.requestFocus(); //Seteo el focus del campo por defecto
                    
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }

    }
    
    private boolean validarFormulario(ControladorCodefacInterface panel,String grupo)
    {
       //Volver a crear los errores pendientes
       panel.consola=new ConsolaGeneral();
       boolean validado=true;       
       Class classVentana=panel.getClass();
        Method[] metodos=classVentana.getMethods();
        for (Method metodo : metodos) {
            ValidacionCodefacAnotacion validacion=metodo.getAnnotation(ValidacionCodefacAnotacion.class);
            //System.out.println(metodo.getName());
            if(validacion!=null)
            {
                //Validar todos los campos cuando no tiene puesto el grupo
                if(grupo==null || validacion.grupo().equals(grupo))
                {
                    try {
                        JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                        Vector<String> errores=validarComponente(validacion,componente,panel);

                        if(errores.size()>0)
                        {
                            //Si Existe errores pinto de colo amarillo
                            componente.setBackground(new Color(255,255,102));
                        }
                        else
                        {
                            //Si no existe error pinto de blanco
                            componente.setBackground(Color.white);
                        }

                        for (String error : errores) {
                            panel.consola.agregarDatos(validacion.nombre(),error,componente);
                            validado=false;
                        }



                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return validado;
    }
    
    //TODO optimizar metodo porque existen varios con codigo simila
    private boolean validarFormulario(ControladorCodefacInterface panel,String grupo,String nombreComponente)
    {
       //Volver a crear los errores pendientes
       panel.consola=new ConsolaGeneral();
       boolean validado=true;       
       Class classVentana=panel.getClass();
        Method[] metodos=classVentana.getMethods();
        for (Method metodo : metodos) {
            ValidacionCodefacAnotacion validacion=metodo.getAnnotation(ValidacionCodefacAnotacion.class);
            //System.out.println(metodo.getName());
            if(validacion!=null)
            {
                if(validacion.grupo().equals(grupo))
                {
                    if(validacion.nombre().equals(nombreComponente))
                    {
                        try {
                            JTextComponent componente = (JTextComponent) metodo.invoke(panel);
                            Vector<String> errores = validarComponente(validacion, componente, panel);

                            if (errores.size() > 0) {
                                //Si Existe errores pinto de colo amarillo
                                componente.setBackground(new Color(255, 255, 102));
                            } else {
                                //Si no existe error pinto de blanco
                                componente.setBackground(Color.white);
                            }

                            for (String error : errores) {
                                panel.consola.agregarDatos(validacion.nombre(), error, componente);
                                validado = false;
                            }

                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalArgumentException ex) {
                            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (InvocationTargetException ex) {
                            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    
                    }
                    
                }
            }
        }
        return validado;
    }
    
    
    

    
    private Vector<String> validarComponente(ValidacionCodefacAnotacion validacion,JTextComponent componente,GeneralPanelInterface panel)
    {
        Vector<String> validar=new Vector<String>();
        if(validacion.requerido())
        {
            if(componente.getText().equals(""))
            {
                validar.add("campo requerido");
                
            }
        }
        else
        {
            //Si el campo no es requerido y no tiene valor no hago ninguna otra validacion
            if (componente.getText().equals("")) {
                return validar;
            }
        }
        
        if(componente.getText().length()<validacion.min())
        {
            validar.add("tamaño min requerido");
        }
        
        if(componente.getText().length()>validacion.max())
        {
            validar.add("tamaño max requerido");
        }
        
        if(!validacion.expresionRegular().equals("")){
            //System.out.println(componente.getText()+"->"+validacion.nombre()+"->"+validacion.expresionRegular());
            if(!Pattern.matches(validacion.expresionRegular(),componente.getText()))
            {
                //Verifica si la etiqueta tiene un error personalizado o muestro el error por defecto
                if(!validacion.expresionRegularMensaje().equals(""))
                {
                    validar.add(validacion.expresionRegularMensaje());
                }
                else
                {
                    validar.add("expresion regular fallo");
                }
            }
        }
        
        String[] personalizados = validacion.personalizado();
        for (String personalizado : personalizados) {
            if (!personalizado.equals("")) {
                Method[] metodosValidar = panel.getClass().getMethods();
                for (Method method : metodosValidar) {
                    ///System.out.println(method.getName());
                    validacionPersonalizadaAnotacion validacionPersonalizada = method.getAnnotation(validacionPersonalizadaAnotacion.class);

                    if (validacionPersonalizada != null) {
                        if (personalizado.equals(method.getName())) {
                            try {
                                boolean resultado = (boolean) method.invoke(panel);
                                if (!resultado) {
                                    validar.add(validacionPersonalizada.errorTitulo());
                                }
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InvocationTargetException ex) {
                                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        }
        

        
        return validar;
    }
    
    private void mostrarConsola(ConsolaGeneral consola,Boolean actualizarVista)
    {
       PanelSecundarioAbstract panel=panelesSecundariosMap.get(PanelSecundarioAbstract.PANEL_VALIDACION);
       //Esta funcion se encarga de enviar el modelo a la tabla para mostrar los datos
       panel.actualizar(consola.getModeloTabla());
       
       JTable tablaValidacion=(JTable) panel.getPropertyByNombre(ValidadorCodefacModel.PROPIEDAD_TABLA);       
       tablaValidacion.addMouseListener(new MouseListener() {           
           @Override
           public void mouseClicked(MouseEvent e) {
               int filaSeleccionado=tablaValidacion.getSelectedRow();
               if(filaSeleccionado>=0)
               {
                   ControladorCodefacInterface frameInterface=(ControladorCodefacInterface) getPanelActivo();
                   frameInterface.consola.seleccionarFila(filaSeleccionado);
               }
           }

           @Override
           public void mousePressed(MouseEvent e) {}
           @Override
           public void mouseReleased(MouseEvent e) {}
           @Override
           public void mouseEntered(MouseEvent e) {}
           @Override
           public void mouseExited(MouseEvent e) {}
       });
               
       if(consola.getModeloTabla().getRowCount()>0)
       {
           mostrarPanelSecundario(true,PanelSecundarioAbstract.PANEL_VALIDACION);
       }
       else
       {
           mostrarPanelSecundario(false);
       }
      
    }
    
    private void agregarValidadores(GeneralPanelInterface panel)
    {
       Class classVentana=panel.getClass();
        Method[] metodos=classVentana.getMethods();
        for (Method metodo : metodos) {
            //solo hacer validaciones para metodos que empicen con la palabra get
            if(metodo.getName().indexOf("get")!=0)
            {
                continue;
            }
            
            ValidacionCodefacAnotacion validacion=metodo.getAnnotation(ValidacionCodefacAnotacion.class);
            if(validacion!=null)
            {
                if(validacion.grupo().equals(ValidacionCodefacAnotacion.GRUPO_FORMULARIO))
                {                    
                    try {
                        JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                        
                        //Poner un borde doble solo cuando el campo es requerido para el usuario
                        if(validacion.requerido())
                        {                            
                            componente.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, new Color(122, 138, 153)));                                                    
                        }
                        
                        componente.addFocusListener(new FocusListener() {
                            @Override
                            public void focusGained(FocusEvent e) {

                            }

                            @Override
                            public void focusLost(FocusEvent e) {

                                //Verifica que no existen datos ingresados porque no debe validar nada
                                //Optimizar este codigo porque este metodo es para otra funcionalidad mas complicada
                                String letra=componente.getText();
                                if(panel.salirSinGrabar() && componente.getText().equals(""))
                                {
                                    return;
                                }

                                //Este codigo se pone porque despues de cambiar de pantalla se ejecuta el evento de focus de la anterior
                                //y eso me genera problemas cuando quiero manejar los eventos de las jinternalFrame
                                //TODO: Revisar este codigo
                                if(!panel.equals(getPanelActivo()))
                                {
                                    //System.out.println("no validar porque cambio de pantalla");
                                    return;
                                }
                                //TODO: Revisar esta solucion porque es muy rebuscada
                                if (panel.sinAcciones) {
                                    panel.sinAcciones = false;
                                    return;
                                }

                                if(panel.formularioCerrando)
                                {
                                    return;
                                }


                                Vector<String> errores = validarComponente(validacion, componente, panel);
                                panel.consola.quitarDato(componente);
                                for (String error : errores) {
                                    panel.consola.agregarDatos(validacion.nombre(), error, componente);
                                }

                                if(errores.size()>0)
                                {
                                    componente.setBackground(new Color(255,255,102));
                                    mostrarConsola(panel.consola,true);                                
                                }
                                else
                                {
                                    panel.consola.quitarDato(componente);
                                    componente.setBackground(Color.white);
                                    mostrarConsola(panel.consola,true);   

                                }

                            }
                        });

                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            CursorPorDefectoAnotacion anotacionCursorDefecto=metodo.getAnnotation(CursorPorDefectoAnotacion.class);
            if(anotacionCursorDefecto!=null)
            {
                try {
                    JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                    componente.requestFocus(); //Setear con el puntero ese campo
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
            //Buscar Anotaciones para la etiqueta de busqueda por campo
            CampoBuscarAnotacion buscarAnotacion=metodo.getAnnotation(CampoBuscarAnotacion.class);
            if(buscarAnotacion!=null)
            {
                try {
                    JTextComponent componente=(JTextComponent) metodo.invoke(panel);
                    ImageIcon icon = new ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("find2-ico.png"));
                    componente.setBorder(BorderFactory.createMatteBorder(0, 16, 0, 0, icon));
                    componente.addKeyListener(new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {}

                        @Override
                        public void keyPressed(KeyEvent e) {
                            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                                JInternalFrame frame = getjDesktopPane1().getSelectedFrame();
                                
                                listenerBuscar(frame,new BusquedaCodefacInterface() {
                                    @Override
                                    public void buscar() throws ExcepcionCodefacLite {

                                        try
                                        {
                                            /////////////////////////////////////////////////////////////////////////////////////////
                                            //              BUSCAR EL DIALOGO PRIMERO EN EL CONTROLADOR
                                            /////////////////////////////////////////////////////////////////////////////////////////
                                            BuscarDialogoModel dialogBuscar=null;
                                            VistaCodefacIf frameControlador = UtilidadesCoreCodefac.getControladorTodoVista(panel);
                                            if (frameControlador != null) {
                                                dialogBuscar=new BuscarDialogoModel(frameControlador.obtenerDialogoBusqueda());
                                            }
                                            else
                                            {
                                                dialogBuscar=new BuscarDialogoModel(panel.obtenerDialogoBusqueda());
                                            }
                                            
                                            
                                            //Ejecutar la pantalla de dialogo solo si hay algo de texto
                                            if (!componente.getText().equals("")) {
                                                try {
                                                    dialogBuscar.getTxtBuscar().setText(componente.getText());
                                                    dialogBuscar.ejecutarConsulta();
                                                    //Verifico que exista coincidencias con el dato ingreso o cancelo la busquda
                                                     ejectutarDialogoBusqueda(dialogBuscar,false,panel,true);                                                   
                                                    
                                                } catch (ExcepcionCodefacLite ex) {
                                                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                                                    throw ex;
                                                }
                                            } else {
                                                throw new ExcepcionCodefacLite("No existen datos en los campos para abrir el dialogo de busqueda");
                                            }
                                        }
                                        catch(UnsupportedOperationException ex) {
                                            //si no existe implentada la funcion no se muestra nada
                                            LOG.log(Level.INFO,"No se puede mostrar la busqueda desde un campo porque no esta implementado el dialogo de busqueda");
                                        }

                                    }
                                });
                
                            }
                        }

                        @Override
                        public void keyReleased(KeyEvent e) {}
                    });
                }
                //Verificar tambien para ver si implementa el etiqueta de busqueda por  campo
                catch (IllegalAccessException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    
    InternalFrameListener listenerFrame=new InternalFrameListener() {
                        @Override
                        public void internalFrameOpened(InternalFrameEvent e) {
                        }

                        @Override
                        public void internalFrameClosing(InternalFrameEvent e) {
                            //JInternalFrame todo[]= getjDesktopPane1().getAllFrames();
                            Boolean respuesta=true;
                            GeneralPanelInterface panelCerrando=(GeneralPanelInterface) e.getInternalFrame();                            
                            if(!panelCerrando.salirSinGrabar())
                            {
                                if(panelCerrando.estadoFormulario.equals(GeneralPanelInterface.ESTADO_GRABAR)) //Agregada validacion solo en el estado de grabar para que se ejecute cuando este desde la pantalla de 
                                {
                                    respuesta=DialogoCodefac.dialogoPregunta("Advertencia","Existen datos ingresados , está seguro que desea cerrar la ventana?",DialogoCodefac.MENSAJE_ADVERTENCIA);
                                }
                            }
                            
                            //Solo cerrar si la respuesta es si
                            if(respuesta)
                            {                                
                                ControladorCodefacInterface panel = (ControladorCodefacInterface) getjDesktopPane1().getSelectedFrame();
                                panel.formularioCerrando = true;
                                //cargarAyuda();
                                mostrarPanelSecundario(false);
                                e.getInternalFrame().dispose();
                                getjDesktopPane1().remove(panel);
                                //quitarVentanaAbierta(panelCerrando); //
                            }                                                        
                        }

                        @Override
                        public void internalFrameClosed(InternalFrameEvent e) {
                            //JInternalFrame todo[]= getjDesktopPane1().getAllFrames();
                            GeneralPanelInterface panelCerrando=(GeneralPanelInterface)e.getInternalFrame();
                            quitarVentanaAbierta(panelCerrando); //
                            //if (verificarTodasPantallasMinimizadas(e.getInternalFrame())) {
                            habilitarBotones(false);
                            //}
                            //Seleccionar la siguiente ventana por defecto
                            JInternalFrame[] ventanas=getjDesktopPane1().getAllFrames();
                            for (JInternalFrame ventana : ventanas) {
                                    try {
                                        //Seleccionar la ventana si no esta como icono y no es la misma que se esta eliminando
                                        if(!ventana.isIcon() && !panelCerrando.equals(ventana))
                                        {
                                            ventana.setSelected(true);
                                        }
                                    } catch (PropertyVetoException ex) {
                                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                            }
                            
                            mostrarPanelSecundario(false);
                        }

                        @Override
                        public void internalFrameIconified(InternalFrameEvent e) {
                            //Veifico si ya no existen pantallas para establecer el foco entonces desahabilito los botones
                            ControladorCodefacInterface controlador=(ControladorCodefacInterface) e.getInternalFrame();
                            controlador.sinAcciones=true; //Este artificio utilizo para que cuando minimice no se ejecuten los validadores
                            
                            if (verificarTodasPantallasMinimizadas(e.getInternalFrame())) {
                                habilitarBotones(false);
                                
                            }
                            mostrarPanelSecundario(false);

                        }

                        @Override
                        public void internalFrameDeiconified(InternalFrameEvent e) {
                            //JOptionPane.showMessageDialog(null,"internalFrameDeiconified");
                            habilitarConfiguracioneBotones();
                            //mostrarPanelSecundario(true);
                        }

                        @Override
                        public void internalFrameActivated(InternalFrameEvent e) {
                            //JOptionPane.showMessageDialog(null,"internalFrameActivated");
                            habilitarConfiguracioneBotones();
                            
                            System.out.println("pantalla activida");
                            //mostrarPanelSecundario(true,PanelSecundarioAbstract.PANEL_MONITOR);
                            ControladorCodefacInterface panel = getPanelActivo();
                            if (panel != null) {
                                System.out.println("Panel Activo: "+panel.getTitle());
                                if (panel.consola != null) {
                                    mostrarConsola(getPanelActivo().consola, false);
                                }
                                else
                                    System.out.println("consola null");
                            }else
                                System.out.println("panel null");

                            //s
                           
                        }
                        
                        //Evento cuando se desactiva las pantallas
                        @Override
                        public void internalFrameDeactivated(InternalFrameEvent e) {}
     };
    
           
    
    private void habilitarBotones(Boolean opcion)
    {
        getBtnActualizar().setEnabled(opcion);
        //getBtnAyuda().setEnabled(opcion); //Siempre debe estar habilitado
        getBtnBuscar().setEnabled(opcion);
        getBtnEliminar().setEnabled(opcion);
        getBtnGuardar().setEnabled(opcion);
        getBtnImprimir().setEnabled(opcion);
        getBtnNuevo().setEnabled(opcion);
    }
    
    private ControladorCodefacInterface getPanelActivo()
    {
        JInternalFrame frame = getjDesktopPane1().getSelectedFrame();
        ControladorCodefacInterface frameInterface = (ControladorCodefacInterface) frame;
        return frameInterface;
    }
 
    /**
     * Habilita solo los botones disponibles para la pantalla
     */
    private void habilitarConfiguracioneBotones()
    {
        habilitarBotones(false);//Descativo todas las ventanas para luego activar segun los permisos
        JInternalFrame frame = getjDesktopPane1().getSelectedFrame();
        ControladorCodefacInterface frameInterface = (ControladorCodefacInterface) frame;
        
        //Primero verificar si la pantalla es DIALOGO, en tal motivo solo activo el boton de guardar
        if(frameInterface.modoDialogo)
        {
            getBtnGuardar().setEnabled(true);
            return;
        }
        
        //Si no es du dialogo continuo con las validaciones normales
        try
        {
            Map<Integer, Boolean> mapPermisos = frameInterface.permisosFormulario();
            for (Map.Entry<Integer, Boolean> entry : mapPermisos.entrySet()) {
                Integer key = entry.getKey();
                Boolean value = entry.getValue();
                JButton boton=null;

                switch (key) {
                    case ControladorCodefacInterface.BOTON_GRABAR:
                        boton=getBtnGuardar();
                        break;

                    case ControladorCodefacInterface.BOTON_ELIMINAR:
                        boton=getBtnEliminar();
                        break;

                    case ControladorCodefacInterface.BOTON_IMPRIMIR:
                        boton=getBtnImprimir();
                        break;

                    case ControladorCodefacInterface.BOTON_AYUDA:
                        boton=getBtnAyuda();
                        break;

                    case ControladorCodefacInterface.BOTON_NUEVO:
                        boton=getBtnNuevo();
                        break;

                    case ControladorCodefacInterface.BOTON_REFRESCAR:
                        boton=getBtnActualizar();
                        break;

                    case ControladorCodefacInterface.BOTON_BUSCAR:
                        boton=getBtnBuscar();
                        break;
                }
                
                boton.setEnabled(value);
                
                //Si es un super usuario no debe hacer mas validaciones de los botonew
                if(sessionCodefac.getUsuario().getNick().equals(Usuario.SUPER_USUARIO))
                {
                    continue; //continua con la siguiete validacion sin verificar permisos por roles de usuario
                }
                //Adicional de validar si la pantalla tiene disponible la opcion verificar si el usuario tiene permisos para los botones
                if(value) //Validar solo si el valor es positivo porque solo debo poder desactivar opciones
                {
                    //No validar para el boton de ayuda porque siempre tiene que estar activo
                    if(key!=ControladorCodefacInterface.BOTON_AYUDA)
                    {
                        Boolean permisoBotonUsuario = permisoBotonesRoles(frameInterface, key);
                        if (!permisoBotonUsuario) {
                            boton.setEnabled(false); //Descivar si no tiene permisos
                        }
                    }
                }
                
                
            }
        }
        catch(java.lang.UnsupportedOperationException uoe)
        {
            //Si no esta implementado el metodo poner todos los botones en falso
            habilitarBotones(false);
        }
    }
    
    /**
     * Verificar si el usuario tiene permisos para activar el boton del menu
     * @return 
     */
    private boolean permisoBotonesRoles(ControladorCodefacInterface frame,Integer boton )
    {
        String claseNombre=frame.getClass().getName();
        List<Perfil> perfiles= sessionCodefac.getPerfiles();
        for (Perfil perfil : perfiles) {
            for (PermisoVentana permisoVentana : perfil.getVentanasPermisos()) {
                if(permisoVentana.getVentanaEnum().getClaseNombre().equals(claseNombre))
                {
                    if ((permisoVentana.getPermisoGrabar().equals("s") || permisoVentana.getPermisoEditar().equals("s")) && ControladorCodefacInterface.BOTON_NUEVO == boton) 
                        return true;                    
                    
                    if(permisoVentana.getPermisoBuscar().equals("s") && ControladorCodefacInterface.BOTON_BUSCAR==boton)
                        return true;
                    
                    if(permisoVentana.getPermisoEditar().equals("s") && ControladorCodefacInterface.BOTON_GRABAR==boton)
                        return true;
                    
                    if(permisoVentana.getPermisoEliminar().equals("s") && ControladorCodefacInterface.BOTON_ELIMINAR==boton)
                        return true;
                    
                    if(permisoVentana.getPermisoGrabar().equals("s") && ControladorCodefacInterface.BOTON_GRABAR==boton)
                        return true;
                    
                    if(permisoVentana.getPermisoImprimir().equals("s") && ControladorCodefacInterface.BOTON_IMPRIMIR==boton)
                        return true;                    
                }
            }
        }
        
        return false;
    }
    
    
    private void iniciarComponentes()
    {
        try {
            controladorVista=new ControladorVista();
            
            //Cargar configuraciones de los divisores
            PROPORCION_HORIZONTAL=PROPORCION_HORIZONTAL_INICIAL;
            PROPORCION_VERTICAL=PROPORCION_VERTICAL_INICIAL;
            
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            
            getjSplitPanel().setDividerLocation(PROPORCION_HORIZONTAL);
            getjSplitPanelVerticalSecundario().setDividerLocation(PROPORCION_VERTICAL);
            
            //Cargar el fondo de la pantalla
            Image fondoImg=new javax.swing.ImageIcon(getClass().getResource("/img/general/fondoGeneral.png")).getImage();
            getjDesktopPane1().setBorder(new Fondo(fondoImg));
            
            //Setear el segundo componente de la ventana auxliar
            getjSplitPanel().setRightComponent(getJpanelAuxiliar());
            
            //Cargar el componente de publicidad para que siempre exista
            cargarPublicidad();
            
            AccesoDirectoServiceIf servicio=ServiceFactory.getFactory().getAccesoDirectoServiceIf();            
            
            //mapBuscar.put("nombre","WidgetVirtualMall");
            AccesoDirecto accesoDirecto=servicio.buscarPorNombre("WidgetVirtualMall");
            int x=accesoDirecto.x;
            int y=accesoDirecto.y;
            
            widgetVirtualMall=new WidgetVirtualMallModelo(getjDesktopPane1());
            widgetVirtualMall.setPreferredSize(new Dimension(x,y));
            widgetVirtualMall.setBounds(x,y,260, 400);
            widgetVirtualMall.addListenerIcono(new IconoInterfaz() {
                @Override
                public void doubleClick() {
                }
                
                @Override
                public void grabarNuevaPosicion(Point nuevaPosicion) {
                    try {
                        //Map<String, Object> mapBuscar;
                        AccesoDirectoServiceIf servicio = ServiceFactory.getFactory().getAccesoDirectoServiceIf();
                        
                        //mapBuscar = new HashMap<>();
                        //mapBuscar.put("nombre","WidgetVirtualMall");
                        AccesoDirecto accesoDirecto=servicio.buscarPorNombre("WidgetVirtualMall");
                        //AccesoDirecto acceso=servicio.obtenerPorMap(mapBuscar).get(0);
                        accesoDirecto.setX((int)nuevaPosicion.getX());
                        accesoDirecto.setY((int)nuevaPosicion.getY());
                        servicio.editar(accesoDirecto);
                    } catch (RemoteException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            getjDesktopPane1().add(widgetVirtualMall);
            widgetVirtualMall.setVisible(false); //TODO: Por el momento dejo desactivado este componente porque no estamos usando

            
            int xVd=100;
            int yVd=200;
            
            widgetVentasDiarias = new VentasDiariasModel(getjDesktopPane1(),sessionCodefac);
            widgetVentasDiarias.panelPadre=this;
            widgetVentasDiarias.setPreferredSize(new Dimension(xVd,yVd));
            widgetVentasDiarias.setBounds(xVd,yVd,250,330);
            
            widgetVentasDiarias.addListenerIcono(new IconoInterfaz() {
                @Override
                public void doubleClick() {
                }
                
                @Override
                public void grabarNuevaPosicion(Point nuevaPosicion) {
                    try {
                        Map<String, Object> mapBuscar;
                        AccesoDirectoServiceIf servicio = ServiceFactory.getFactory().getAccesoDirectoServiceIf();
                        mapBuscar = new HashMap<>();
                        mapBuscar.put("nombre","WidgetVentasDiarias");
                        //AccesoDirecto acceso=servicio.obtenerPorMap(mapBuscar).get(0);
                        AccesoDirecto accesoDirecto=servicio.buscarPorNombre("WidgetVentasDiarias");
                        accesoDirecto.setX((int)nuevaPosicion.getX());
                        accesoDirecto.setY((int)nuevaPosicion.getY());
                        servicio.editar(accesoDirecto);
                    } catch (RemoteException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            //
            getjDesktopPane1().add(widgetVentasDiarias);
            widgetVentasDiarias.setVisible(true);
            /***
             * fin widget Ventas diarias
             */
            
            /**
             * =================================================================
             *              WIDGET NOTIFICACIONES CODEFAC
             * =================================================================
             */
            widgetNotificacionCodefac = new WidgetNotificacionCodefacModelo(getjDesktopPane1(), sessionCodefac.getEmpresa());
            widgetNotificacionCodefac.panelPadre = this;
            widgetNotificacionCodefac.setPreferredSize(new Dimension(500, 200));
            widgetNotificacionCodefac.setBounds(100, 0, 400, 180);
            //widgetVentasDiarias.addListenerIcono();/
            getjDesktopPane1().add(widgetNotificacionCodefac);
            //widgetNotificacionCodefac.setVisible(true);

            
            /**
             * ===============> FIN WIDGET NOTIFICACIONES CODEFAC <=============
             */
            AccesoDirecto accesoDirectoTmp=null;
            listaIconos=new ArrayList<IconoPanel>();
            
            crearWidget("factura.png", FacturacionModel.class,"Factura");
            crearWidget("producto.png", ProductoModel.class,"Producto");
            crearWidget("calculadora.png", CalculadoraModel.class,"Calculadora");
            crearWidget("cliente.png", ClienteModel.class,"Cliente");
            crearWidget("configuracion.png", ComprobantesConfiguracionModel.class,"Configurar");
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void crearWidget(String nombreImg,Class clase,String titulo)
    {
        try {
            AccesoDirectoServiceIf servicio=ServiceFactory.getFactory().getAccesoDirectoServiceIf();
            URL url = RecursoCodefac.IMAGENES_ACCESO_DIRECTO.getResourceURL(nombreImg);
            AccesoDirecto accesoDirectoTmp = servicio.buscarPorNombre(clase.getName());
            IconoPanel iconoFactura = new IconoPanel(titulo, url, getjDesktopPane1(), accesoDirectoTmp.x, accesoDirectoTmp.y);
            iconoFactura.addListenerIcono(new ListenerIcono(clase, true));
            getjDesktopPane1().add(iconoFactura);
            listaIconos.add(iconoFactura);
            
        } catch (RemoteException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void seleccionaPanel(ControladorCodefacInterface panelInterface)
    {
        
        this.panelActual=panelInterface;
        this.controladorVista.agregarVista(panelInterface);
    }
    
    public void agregarReportePantalla()
    {
        try {
            // Se construye el panel que ira dentro del JInternalFrame
            JPanel p = new JPanel();
            p.setLayout(new FlowLayout());
            p.add(new JLabel("JPanel para el repote jasper"));
            p.add(new JTextField(10));
            
            // Se construye el JInternalFrame
            JInternalFrame internal = new JInternalFrame("Un Internal Frame");
            internal.add(p);
            getjDesktopPane1().add(internal);
            internal.setMaximum(true);
            internal.show();
        } catch (PropertyVetoException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //public GeneralPanelInterface pantallaReporte=new GeneralPanelInterface() {
    //        
    //}
    
    @Override
    public void crearReportePantalla(JasperPrint jasperPrint,String nombrePantalla) {
        crearReportePantalla(jasperPrint, nombrePantalla, ConfiguracionImpresoraEnum.NINGUNA);
    }
    
   
    
    @Override
    public void crearReportePantalla(JasperPrint jasperPrint,String nombrePantalla,ConfiguracionImpresoraEnum configuracionImpresora) {
        //JRViewer viewer=new  JRViewer(jasperPrint);
        JRViewer viewer=new VisualizadorJRViewer(jasperPrint);
        viewer.setZoomRatio(0.6f);
        
        
        if(configuracionImpresora!=null)
        {
            if(configuracionImpresora.equals(ConfiguracionImpresoraEnum.IMPRESORA_POR_DEFECTO) || configuracionImpresora.equals(ConfiguracionImpresoraEnum.SELECCIONAR_IMPRESORA))
            {   
                Boolean mostrarSeleccionImpresora=true;
                if(configuracionImpresora.equals(ConfiguracionImpresoraEnum.IMPRESORA_POR_DEFECTO))
                {
                    mostrarSeleccionImpresora=false;
                }
                
                try 
                { 
                    JasperPrintManager.printReport(jasperPrint, mostrarSeleccionImpresora);
                    LOG.log(Level.INFO,"Imprimiendo desde el computador");
                } catch (JRException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
        GeneralPanelInterface internal = new GeneralPanelInterface() {
            @Override
            public void iniciar() throws ExcepcionCodefacLite, RemoteException {}

            @Override
            public void nuevo() throws ExcepcionCodefacLite, RemoteException {}

            @Override
            public void grabar() throws ExcepcionCodefacLite, RemoteException{ }

            @Override
            public void editar() throws ExcepcionCodefacLite, RemoteException {}

            @Override
            public void eliminar() throws ExcepcionCodefacLite, RemoteException {}

            @Override
            public void imprimir() throws ExcepcionCodefacLite, RemoteException {}

            @Override
            public void actualizar() throws ExcepcionCodefacLite, RemoteException {}

            @Override
            public void limpiar() {}

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

            @Override
            public String toString() {
                return nombrePantalla;
            }
            
            
            
        };
        
        internal.addInternalFrameListener(new InternalFrameListener() {
            @Override
            public void internalFrameOpened(InternalFrameEvent e) {}

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {}

            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                quitarVentanaAbierta(internal); //
            }

            @Override
            public void internalFrameIconified(InternalFrameEvent e) {}

            @Override
            public void internalFrameDeiconified(InternalFrameEvent e) {}

            @Override
            public void internalFrameActivated(InternalFrameEvent e) {}

            @Override
            public void internalFrameDeactivated(InternalFrameEvent e) {}
        });
        
        internal.setClosable(true);
        internal.setIconifiable(true);
        internal.setMaximizable(true);
        internal.setResizable(true);
        internal.setTitle(nombrePantalla);
        
        //internal.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/report-ico.png"))); // NOI18N
        internal.setFrameIcon(new javax.swing.ImageIcon(RecursoCodefac.IMAGENES_ICONOS.getResourceURL("report-ico.png"))); // NOI18N
        try {
            if (internal.isIcon()) {
                internal.setIcon(false);
            } else if (internal.isMaximum()) {
                internal.setMaximum(false);
            }
        } catch(PropertyVetoException e) {
            return;
        }
        Dimension pantallaPrincipal=getjDesktopPane1().getSize();
        Dimension pantallaReporte=new Dimension((int)((double)pantallaPrincipal.width/(double)2),(int)((double)pantallaPrincipal.height*3/(double)4));
        //internal.setPreferredSize(pantallaReporte);
        internal.setSize(pantallaReporte);
        internal.validate();
        internal.add(viewer);
        getjDesktopPane1().add(internal);
        internal.show();
        
        //Agregar al menu cuando se abren reportes
        agregarVentanaAbierta(internal,true);
        //getjMenuVentanasActivas().add(internal);as
        //asdasd
    }

    /**
     * Metodo para poder abrir una ventana desde otra ventana
     * @param panel 
     */
    @Override
    public void crearVentanaCodefac(GeneralPanelInterface panel,boolean maximizado) {
        agregarListenerMenu((ControladorCodefacInterface) panel,maximizado,null,null);
    }
    
    /*public List<VentanaEnum> getVentanasMenuList() {
        return ventanasMenuList;
    }*/

    public void setVentanasMenuList(List<VentanaEnum> ventanasMenuList) {
        try {
            //this.ventanasMenuList=VentanaEnum.getListValues();
            
            this.getJMenuBar().removeAll();
            //List<JMenu> menus=ServiceFactory.getFactory().getPerfilServicioIf().construirMenuPermisosUsuario(sessionCodefac);
            MenuCodefacRespuesta respuestaMenu=ServiceFactory.getFactory().getPerfilServicioIf().construirMenuPermisosUsuario(sessionCodefac);
            List<JMenu> menus=construirMenuSwing(respuestaMenu);
            //List<JMenu> menus=construirMenuDinamico();
            this.getJMenuBar().add(getjMenuInicio());
            this.getJMenuBar().add(getjMenuUtilidades());
            
            for (JMenu menu : menus) {
                this.getJMenuBar().add(menu);
            }
            this.getJMenuBar().add(getjMenuAyuda());
            this.getJMenuBar().add(getjMenuEstadoComprobantes());
            this.getJMenuBar().add(getjMenuVentanasActivas());
            //actualizarMenuCodefac();
            //agregarListenerMenu();
        } catch (RemoteException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private List<JMenu> construirMenuSwing(MenuCodefacRespuesta respuestaMenu)
    {
        List<JMenu> menusRespuesta=new ArrayList<JMenu>();
        for (ModuloCodefacEnum moduloEnum : respuestaMenu.getModulosDisponibles()) 
        {
            JMenu menuModulo = new JMenu(moduloEnum.getNombre());
            menuModulo.setIcon(moduloEnum.getIcono());
            menuModulo.setFont(new Font("Arial",2,15));
            
            List<CategoriaMenuEnum> categoriaEnumList=respuestaMenu.getCategoriasActivasMap().get(moduloEnum);
            for (CategoriaMenuEnum categoriaMenuEnum : categoriaEnumList) 
            {
                JMenu menuCategoria=new JMenu(categoriaMenuEnum.getNombre());
                menuCategoria.setIcon(categoriaMenuEnum.getIcono());
                menuCategoria.setFont(new Font("Arial", 0, 13));
                List<VentanaEnum> ventanaEnumList=respuestaMenu.buscarVentanasPorCategoriaYModulo(moduloEnum, categoriaMenuEnum);
                
                for (VentanaEnum ventanaEnum : ventanaEnumList) 
                {
                    
                    String nombreVentana = "Sin nombre";
                    try {
                        LOG.log(Level.INFO, moduloEnum.getNombre() + ":" + categoriaMenuEnum.getNombre() + "->" + ventanaEnum.getNombre());
                        nombreVentana = ventanaEnum.getNombre();
                    } catch (java.lang.UnsupportedOperationException uoe) {
                        LOG.log(Level.WARNING, ventanaEnum.getClass().getSimpleName() + ": Ventana sin implementar nombre");
                    }
                    
                    JMenuItem menuVentana = new JMenuItem(nombreVentana);
                    menuVentana.setFont(new Font("Arial", 0, 13));

                    //Agregar atajo de teclado si existe
                    if (ventanaEnum.getTeclaAtajo() != null) {
                        menuVentana.setAccelerator(KeyStroke.getKeyStroke(ventanaEnum.getTeclaAtajo(),InputEvent.ALT_MASK));
                    }
                    
                    menuVentana.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            abrirVentanaCodefac((ControladorCodefacInterface)ventanaEnum.getInstance(),ventanaEnum);
                        }
                    });
                    menuCategoria.add(menuVentana);
                    
                }
                menuModulo.add(menuCategoria);
                
            }
            menusRespuesta.add(menuModulo);
        }
        return menusRespuesta;
    }
    
    private void funcionEjemploRecorrerMenu(List<JMenu> menus)
    {
        /*for (JMenu menu : menus) {
            System.out.println("Primer nivel -> "+menu.getText());
            menu.get
            for (MenuElement subElement : menu.getSubElements()) {
                subElement.
                System.out.println("Segundo nivel -> "+subElement.toString());
            }
        }*/
    }
    
    
    
    
    
   
    
    
    /**
     * Permite actualizar los menus disponibles segun los modulos que tengan permisos
     */
    /*
    private void actualizarMenuCodefac()
    {
        for (MenuControlador menuControlador : ventanasMenuList) {
            
            
            if (!menuControlador.verificarPermisoModulo(sessionCodefac.getModulosMap())) //Verifica si la pantalla tiene permisos para los modulos cargados en
            {
                //Si no tiene permise solo oculto el menu para que no acceda
                menuControlador.getMenuItem().setVisible(false);

            }
            else
            {
                menuControlador.getMenuItem().setVisible(true);
            }
        }
        
    }*/

    @Override
    @Deprecated //No tener dependencia con las pantallas para que se puedan generar los reportes de forma independiente desde el modulo del Servidor
    public Map<String, Object> mapReportePlantilla(OrientacionReporteEnum orientacionEnum,FormatoHojaEnum formatoReporte) {
        return ReporteCodefac.mapReportePlantilla(sessionCodefac.getSucursal(), sessionCodefac.getUsuario(), orientacionEnum, formatoReporte);
//        InputStream inputStream = null;
//
//        SimpleDateFormat formateador = new SimpleDateFormat("d MMM yyyy HH:mm:ss");
//        Map<String, Object> parametros = new HashMap<String, Object>();
//        parametros.put("pl_fecha_hora", formateador.format(new Date()));
//        parametros.put("pl_usuario", sessionCodefac.getUsuario().getNick());
//        parametros.put("pl_direccion", sessionCodefac.getSucursal().getDirecccion()); //TODO: Ver si agregar la direccion general de la matriz
//        parametros.put("pl_razon_social", sessionCodefac.getEmpresa().getRazonSocial());
//        
//        String contribuyenteMicroempresas=EnumSiNo.NO.getLetra();
//        if(sessionCodefac.getEmpresa().getContribuyenteRegimenMicroempresasEnum()!=null)
//        {
//            if(sessionCodefac.getEmpresa().getContribuyenteRegimenMicroempresasEnum().equals(EnumSiNo.SI))
//            {
//                contribuyenteMicroempresas=EnumSiNo.SI.getLetra();
//            }
//        }
//        
//        parametros.put("pl_contribuyenteRegimenMicroempresas", contribuyenteMicroempresas);
//        
//        String nombreComercial=sessionCodefac.getEmpresa().getNombreLegal();
//        if(nombreComercial==null || nombreComercial.trim().isEmpty())
//        {
//            nombreComercial=sessionCodefac.getEmpresa().getRazonSocial();
//        }
//        else
//        {
//            parametros.put("pl_razon_social", sessionCodefac.getEmpresa().getRazonSocial());
//        }
//        
//        parametros.put("pl_nombre_empresa", nombreComercial);
//        parametros.put("pl_telefonos", sessionCodefac.getMatriz().getTelefono());
//        parametros.put("pl_celular", sessionCodefac.getMatriz().getCelular());
//        parametros.put("pl_facebook", sessionCodefac.getEmpresa().getFacebook());
//        parametros.put("pl_instagram",(sessionCodefac.getEmpresa().getInstagram()!=null)?sessionCodefac.getEmpresa().getInstagram():"");
//        parametros.put("pl_ruc", sessionCodefac.getEmpresa().getIdentificacion());        
//        
//        /**
//         * Agregado valdación cuando no llenen ningun dato que salgo información del sistema cuando el usuario tiene una licencia gratuita
//         * @author Carlos Sanchez
//         * @fecha 03/11/2018
//         */
//        if(sessionCodefac.getTipoLicenciaEnum().equals(TipoLicenciaEnum.GRATIS))
//        {
//            parametros.put("pl_adicional", ParametrosSistemaCodefac.MensajesSistemaCodefac.MENSAJE_PIE_PAGINA_GRATIS);
//        }
//        else
//        {
//            parametros.put("pl_adicional", sessionCodefac.getEmpresa().getAdicional());
//        }        
//        
//        
//        try {    
//            RecursosServiceIf service= ServiceFactory.getFactory().getRecursosServiceIf();
//            String nombreImagen=sessionCodefac.getEmpresa().getImagenLogoPath();
//            //service.getResourceInputStream(RecursoCodefac.AYUDA, file);
//            
//           if(sessionCodefac.getTipoLicenciaEnum().equals(TipoLicenciaEnum.GRATIS))
//            {
//                inputStream=RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream("sin_imagen.jpg");
//            }
//            else
//            {
//                RemoteInputStream remoteInputStream = service.getResourceInputStreamByFile(sessionCodefac.getEmpresa(),DirectorioCodefac.IMAGENES, nombreImagen);
//                //verifica que existe una imagen
//                if (remoteInputStream != null) {
//                    inputStream = RemoteInputStreamClient.wrap(remoteInputStream);
//                } 
//                else //Si no existe 
//                {
//                    inputStream = RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream("sin_imagen.jpg");
//                }            
//            }
//
//            parametros.put("pl_url_img1",UtilidadImagen.castInputStreamToImage(inputStream));
//            
//            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.IMAGENES_REDES_SOCIALES, "instagram.png"));
//            parametros.put("pl_img_instagram",UtilidadImagen.castInputStreamToImage(inputStream));
//            
//            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.IMAGENES_REDES_SOCIALES, "facebook.png"));
//            parametros.put("pl_img_facebook",UtilidadImagen.castInputStreamToImage(inputStream));
//            
//            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.IMAGENES_REDES_SOCIALES, "whatsapp.png"));
//            parametros.put("pl_img_whatsapp",UtilidadImagen.castInputStreamToImage(inputStream));
//            
//            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.IMAGENES_REDES_SOCIALES, "telefono.png"));
//            parametros.put("pl_img_telefono",UtilidadImagen.castInputStreamToImage(inputStream));
//            
//            inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.IMAGENES_GENERAL, "codefac-logotipo.png"));
//            //parametros.put("pl_img_logo_pie",UtilidadImagen.castInputStreamToImage(inputStream));
//            parametros.put("pl_img_logo_pie",null);
//            
//            String nombreReporteEncabezado="";
//            String nombreReportePiePagina="";
//            
//            switch(formatoReporte)
//            {
//                case TICKET:
//                        nombreReporteEncabezado = "encabezadoTicket.jrxml";
//                        nombreReportePiePagina = "pie_paginaTicket.jrxml";
//                        break;
//                
//                case A5:
//                    switch (orientacionEnum) {
//                        case HORIZONTAL:
//                            break;
//
//                        case VERTICAL:
//                            nombreReporteEncabezado = "encabezadoA5.jrxml";
//                            nombreReportePiePagina = "pie_paginaA5.jrxml";
//                            break;
//                    }
//                    break;
//                    
//                case A4:
//                    switch (orientacionEnum) {
//                        case HORIZONTAL:
//                            nombreReporteEncabezado = "encabezado_horizontal.jrxml";
//                            nombreReportePiePagina = "pie_pagina_horizontal.jrxml";
//                            break;
//
//                        case VERTICAL:
//                            nombreReporteEncabezado = "encabezado.jrxml";
//                            nombreReportePiePagina = "pie_pagina.jrxml";
//                            break;
//                    }
//                    break;
//                
//            }
//            
//            JasperReport reportCabecera=ReporteProxy.buscar(RecursoCodefac.JASPER, nombreReporteEncabezado);
//            if(reportCabecera==null)
//            {
//                inputStream = RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER,nombreReporteEncabezado));
//                reportCabecera = JasperCompileManager.compileReport(inputStream);
//                ReporteProxy.agregar(RecursoCodefac.JASPER, nombreReporteEncabezado,reportCabecera);
//            }
//            
//            parametros.put("pl_url_cabecera",reportCabecera);
//            
//            JasperReport reportPiePagina=ReporteProxy.buscar(RecursoCodefac.JASPER, nombreReportePiePagina);
//            if(reportPiePagina==null)
//            {
//                inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER,nombreReportePiePagina));
//                reportPiePagina = JasperCompileManager.compileReport(inputStream);
//                ReporteProxy.agregar(RecursoCodefac.JASPER, nombreReportePiePagina,reportPiePagina);
//            }            
//            parametros.put("pl_url_piepagina",reportPiePagina);
//            //System.out.println(parametros.get("SUBREPORT_DIR"));            
//        } catch (RemoteException ex) {
//            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (JRException ex) {
//            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return parametros;
    }

    public SessionCodefac getSessionCodefac() {
        return sessionCodefac;
    }

    public void setSessionCodefac(SessionCodefac sessionCodefac) {
        this.sessionCodefac = sessionCodefac;
    }
    
    private void buscarParametros()
    {
        try {
            /**
             * Agrear un subproceso que se encarga siempre de leer datos para pruebas
             */
            //Thread.sleep(10);
            ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametrosMap(sessionCodefac.getEmpresa());
            //System.out.println("COnsultando parametros");
        } catch (RemoteException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * construir la session con los datos del login
     * @param datosLogin 
     */
    public void setSessionCodefac(LoginModel.DatosLogin  datosLogin) {
        try {
            
            SessionCodefac session = ServiceFactory.getFactory().getUtilidadesServiceIf().getSessionPreConstruido(datosLogin.empresa);
            //panel.setSessionCodefac(session);
            
            session.setUsuario(datosLogin.usuario);
            session.setPerfiles(obtenerPerfilesUsuario(datosLogin.usuario));
            session.setSucursal(datosLogin.sucursal);
            session.setMatriz(datosLogin.matriz);
            //session.setEmpresa(datosLogin.empresa);
            this.sessionCodefac=session;
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //TODO: METODO DE PRUEBA PARA HACER PRUEBAS DE CONCURRENCIA
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    buscarParametros();
                }
            }
        }).start();*/
        
    }
    
     @Override
    public void crearDialogoCodefac(ObserverUpdateInterface panel,VentanaEnum ventanEnum,boolean maximizado,GeneralPanelInterface panelPadre)
    {
        crearDialogoVentana(ventanEnum.getClase(), panel, maximizado,null,panelPadre);    
    }
    
    @Override
    public void crearDialogoCodefac(ObserverUpdateInterface panel,VentanaEnum ventanEnum,boolean maximizado,Object[] parametrosPostConstructor,GeneralPanelInterface panelPadre)
    {
        crearDialogoVentana(ventanEnum.getClase(), panel, maximizado,parametrosPostConstructor,panelPadre);    
    }

    @Override
    public void crearDialogoCodefac(ObserverUpdateInterface panel,String namePanel, boolean maximizado,GeneralPanelInterface panelPadre) {
        
        Class clase=buscarPanelDialog(namePanel);
        crearDialogoVentana(clase, panel, maximizado,null,panelPadre);
                
    }
    
    public void crearVentanaCodefac(VentanaEnum ventanEnum,boolean maximizado,Object[] parametrosPostConstructor)
    {
        //TODO: Mejorar esta parte porque no tiene sentido mandar una ventana nueva siempre creo yo
        ControladorCodefacInterface ventana= abrirVentanaCodefac((ControladorCodefacInterface) ventanEnum.getInstance(), ventanEnum);
      
        //Validacion para verificar si implementa la interfaz del postcostructod
        if (ventana instanceof InterfazPostConstructPanel) {
            ((InterfazPostConstructPanel) ventana).postConstructorExterno(parametrosPostConstructor);
        }
    }   
    
    private void crearDialogoVentana(Class clase,ObserverUpdateInterface panel,boolean maximizado,Object[] parametrosPostConstructor,GeneralPanelInterface panelPadre)
    {
        if(clase!=null)
        {
            try {
                Constructor constructor=clase.getConstructor();
                Object ventanaGeneral=constructor.newInstance();
                ControladorCodefacInterface ventana=(ControladorCodefacInterface) ventanaGeneral;
                //Verificar si el objeto implementa el metodo para comportarse como dialogo
                if(ventana instanceof  DialogInterfacePanel)
                {
                    ventana.modoDialogo=true;
                    ventana.formOwnerFocus=panelPadre;
                    ventana.formOwner=panel;
                    agregarListenerMenu(ventana,maximizado,null,null);
                    habilitarBotones(false);
                    getBtnGuardar().setEnabled(true);
                    
                    //Validacion para verificar si implementa la interfaz del postcostructod
                    if (ventana instanceof InterfazPostConstructPanel) {
                        ((InterfazPostConstructPanel) ventana).postConstructorExterno(parametrosPostConstructor);
                    }
                }
                else
                {
                    System.err.println("La clase que desea abrir no implementa la interfaz DialogInterfacePanel");
                }
                
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * TODO: En esta lista solo deberia poder recorrer las ventanas permitidas
     * Buscar los panes que se pueden usar como dialogos
     */
    private Class buscarPanelDialog(String nombre)
    {
        for (VentanaEnum menuControlador : VentanaEnum.getListValues()) {
            Class<GeneralPanelInterface> clase=menuControlador.getClase();
            if(clase.getName().equals(nombre))
            {
                 return menuControlador.getClase();
            }
        }
        return null;
    }


    public Map<String,PanelSecundarioAbstract> getPanelesSecundarios() {
        return panelesSecundariosMap;
    }

    public void setPanelesSecundarios(Map<String,PanelSecundarioAbstract> panelesSecundariosMap) {
        this.panelesSecundariosMap = panelesSecundariosMap;
    }

    @Override
    public boolean validarPorGrupo(String nombre) {
        ControladorCodefacInterface panel=getPanelActivo();
        return validarFormulario(panel,nombre);
    }

    public HiloPublicidadCodefac getHiloPublicidadCodefac() {
        return hiloPublicidadCodefac;
    }

    public void setHiloPublicidadCodefac(HiloPublicidadCodefac hiloPublicidadCodefac) {
        this.hiloPublicidadCodefac = hiloPublicidadCodefac;
    }

    private void agregarListenerBotonesDefecto() {
        getjMenuItemSalir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventoCerrarSistema();

            }
        });
        
        getjMenuItemContenido().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarAyudaTodo();
                mostrarPanelSecundario(true,PanelSecundarioAbstract.PANEL_AYUDA);
            }
        });
        
        getjMenuItemAcerca().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                AcercaModel.getInstance().setUsuario(sessionCodefac.getUsuarioLicencia());
                AcercaModel.getInstance().setLicencia(sessionCodefac.getTipoLicenciaEnum().getNombre());
                AcercaModel.getInstance().setVisible(true);
            }
        });
        
        getjMenuItemAcerca().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                
                AcercaModel.getInstance().setUsuario(sessionCodefac.getUsuarioLicencia());
                AcercaModel.getInstance().setLicencia(sessionCodefac.getTipoLicenciaEnum().getNombre());
                AcercaModel.getInstance().setVisible(true);
            }
        });
        
               
        getjMenuItemEnviarDatosSoporte().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 try {
                    //TODO: Solucion temporal pero ver otra manera mejor para solo enviar los parametros necesarios y no toda la empresa
                    String correoEnviar=ParametrosSistemaCodefac.CORREO_SOPORTE;
                    DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.PROCESO_ESPERA);
                    RespaldosModelUtilidades.generarRespaldoUbicacion(true,sessionCodefac.getEmpresa(),correoEnviar,true);
                    DialogoCodefac.mensaje("Correcto","El proceso termino correctamente",DialogoCodefac.MENSAJE_CORRECTO);
                    //generarRespaldoUbicacion(getChkEnviarCorreo().isSelected());
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(RespaldarInformacionModel.class.getName()).log(Level.SEVERE, null, ex);
                    DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                }
            }
        });
                
        getBtnManualUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirManualUsuario();                
            }
        });
        
        /*getjMenuItemMonitor().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MonitorComprobanteModel.getInstance().mostrar();
            }
        });*/
        
        getjMenuItemInicio().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               minimizarTodasVentanas();
            }
        });
        
        getjMenuItemActualizarLicencia().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuarioLicencia=sessionCodefac.getUsuarioLicencia();
                //String tipoLicencia=WebServiceCodefac.getTipoLicencia(usuarioLicencia);
                Licencia licenciaInternet=new Licencia();
                try {
                    licenciaInternet.cargarLicenciaOnline(usuarioLicencia);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                ValidacionLicenciaCodefac validacionLicencia = new ValidacionLicenciaCodefac(sessionCodefac.getParametrosCodefac().get(ParametroCodefac.DIRECTORIO_RECURSOS).getValor());
                
                //TODO: Analizar la opcion para comparar tambien el numero de usuario y modulos para generar una nueva licencia
                                
                if(validacionLicencia.getLicencia().compararOtraLicencia(licenciaInternet))
                {
                    DialogoCodefac.mensaje("Advertencia","No necesita actualizar su licencia \n Si desea contratar una nueva licencia visite nuestra página Web", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    return;
                }
                
                
                boolean respuesta=DialogoCodefac.dialogoPregunta("Confirmar","Existe una actualización de su licencia , desea continuar?",DialogoCodefac.MENSAJE_ADVERTENCIA);
                if(respuesta)
                {
                    try {
                        //dispose();
                        ParametroCodefacServiceIf servicio=ServiceFactory.getFactory().getParametroCodefacServiceIf();
                        String pathBase = servicio.getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS,sessionCodefac.getEmpresa()).valor;
                        ValidacionLicenciaCodefac validacion = new ValidacionLicenciaCodefac();
                        validacion.setPath(pathBase);
                        ValidarLicenciaModel dialogo=new ValidarLicenciaModel(null,true,true,sessionCodefac.getEmpresa());
                        //dialogo.validacionLicenciaCodefac=validacion;
                        dialogo.setVisible(true);
                        //ec.com.codesoft.codefaclite.main.init.Main.iniciarComponentes();
                    } catch (RemoteException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
            }
                
        });
        
    }
    
    private void abrirManualUsuario()
    {
        try
        {   //Cuando presiona ayuda busca el link configurado para la pantalla
            JInternalFrame frame = getjDesktopPane1().getSelectedFrame();
            ControladorCodefacInterface frameInterface = (ControladorCodefacInterface) frame;
            String urlAyuda = frameInterface.getURLAyuda();
            agregarListenerMenu(new VentanaManualUsuario(urlAyuda), false, 800, 900);
        } catch (UnsupportedOperationException ex) {
            // Si no encuentra el link abre por defecto desde el inicio
            agregarListenerMenu(new VentanaManualUsuario(), false, 800, 900);
        } catch (java.lang.NullPointerException ex) {
            agregarListenerMenu(new VentanaManualUsuario(), false, 800, 900);
        }
    }
    
    private void minimizarTodasVentanas()
    {
        JInternalFrame[] ventanas = getjDesktopPane1().getAllFrames();
        for (JInternalFrame ventana : ventanas) {
            try {
                ventana.setIcon(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        getjDesktopPane1().revalidate();
        getjDesktopPane1().repaint();
        
    }
    
    /**
     * Verifica si todas las pantallas estan minimizadas
     * @return 
     */
    private Boolean verificarTodasPantallasMinimizadas(JInternalFrame internalFrameActual)
    {
       
        JInternalFrame[] ventanas = getjDesktopPane1().getAllFrames();
        for (JInternalFrame ventana : ventanas) {

            if(!ventana.equals(internalFrameActual))
            {
                
                if(!ventana.isIcon())
                {
                    return  false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean validarPorGrupo(String nombre, String componente) {
        ControladorCodefacInterface panel=getPanelActivo();
        return validarFormulario(panel,nombre,componente);
    }

    private void cargarDatosAdicionales() {
        
        try {
            /**
             * Setear el parametro del celular si ya fue ingresado alguna vez
             */
            ParametroCodefacServiceIf servicio=ServiceFactory.getFactory().getParametroCodefacServiceIf();
            ParametroCodefac parametro=servicio.getParametroByNombre(ParametroCodefac.CELULAR_VIRTUAL_MALL,sessionCodefac.getEmpresa());
            if(parametro!=null)
            {
                widgetVirtualMall.getTxtCelular().setText(parametro.getValor());
            }
        } catch (RemoteException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Cargar los temas en el menu
        for(EstiloCodefacEnum estiloEnum : EstiloCodefacEnum.values())
        {
            JMenuItem menuItemTema=new JMenuItem(estiloEnum.getNombre());
            menuItemTema.setFont(new Font("Arial", Font.PLAIN, 13));   
            menuItemTema.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    seleccionarTema(estiloEnum.getNombre());
                    //DialogoCodefac.mensaje("Correcto","Tema modificado correctamente", DialogoCodefac.MENSAJE_CORRECTO);
                    //Guardar en el arcivo de configuraciones
                    try {
                        ArchivoConfiguracionesCodefac.getInstance().agregarCampo(ArchivoConfiguracionesCodefac.CAMPO_TEMA, estiloEnum.getNombre());
                        ArchivoConfiguracionesCodefac.getInstance().guardar();
                        
                    } catch (IOException ex) {
                        Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            
            getJmenuTemas().add(menuItemTema);            
        }
                
    }
    
    /**
     * Metodo que permite establecer el nuevo tema para cambiar la apariencia
     */
    private void seleccionarTema(String nombreTema)
    {
        EstiloCodefacEnum estiloCodefacEnum=EstiloCodefacEnum.findByNombre(nombreTema);
        try {
            Properties props = new Properties();
            props.put("logoString", "Codefac");
            
            TextureLookAndFeel.setCurrentTheme(new Properties(props));
            AeroLookAndFeel.setCurrentTheme(new Properties(props));
            //SeaGlassLookAndFeel.setCurrentTheme(props);
            McWinLookAndFeel.setCurrentTheme(new Properties(props));
            MintLookAndFeel.setCurrentTheme(new Properties(props));
            GraphiteLookAndFeel.setCurrentTheme(new Properties(props));
            FastLookAndFeel.setCurrentTheme(new Properties(props));
            AluminiumLookAndFeel.setCurrentTheme(new Properties(props));
            AeroLookAndFeel.setCurrentTheme(new Properties(props));
            AcrylLookAndFeel.setCurrentTheme(new Properties(props));
            SmartLookAndFeel.setCurrentTheme(new Properties(props));
            
            UIManager.setLookAndFeel(estiloCodefacEnum.getClassName());

            SwingUtilities.updateComponentTreeUI(this);
            repaint();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void iniciarComponentesPantalla() {
        String anioActualStr=UtilidadesFecha.obtenerAnioStr(UtilidadesFecha.getFechaHoy());
        getLblPiePagina().setText("Todos los derechos reservados por @Codesoft "+anioActualStr);
    }

    private void agregarListenerItemMenu() {
        
        getItemActualizar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getBtnActualizar().doClick();
            }
        });
        
        getItemAyuda().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getBtnAyuda().doClick();
            }
        });
        
        getjMenuEstadoComprobantes().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirPanelSecundario();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        getjMenuEstadoComprobantes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirPanelSecundario();
            }
        });
        
        getItemBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getBtnBuscar().doClick();
            }
        });
        
        getItemEliminar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getBtnEliminar().doClick();
            }
        });
        
        getItemGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getBtnGuardar().doClick();
            }
        });
        
        getItemImprimir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getBtnImprimir().doClick();
            }
        });

        getItemNuevo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getBtnNuevo().doClick();
            }
        });
        
        getItemCerrarVentana().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorCodefacInterface pantalla =getPanelActivo();
                if(pantalla!=null)
                {
                    boolean respuesta=true;
                    if(!pantalla.salirSinGrabar())
                    {
                        respuesta=DialogoCodefac.dialogoPregunta("Advertencia","Existen datos ingresados , está seguro que desea salir?",DialogoCodefac.MENSAJE_ADVERTENCIA);
                        if(!respuesta)
                        {
                            return;
                        }
                    }
                    pantalla.dispose();
                    
                }
            }
        });
    }
    
    /**
     * Metodo que permite escribir los datos inferiores de la empresa y el usuario
     */
    public void setearEtiquetasPiePaginaPantallaPrincipal()
    {
        try {
            String nombreUsuario=(sessionCodefac.getUsuario()!=null)?sessionCodefac.getUsuario().getNick():"Sin usuario";
            String sucursal=(sessionCodefac.getSucursal()!=null)?sessionCodefac.getSucursal().getNombre():"Sin sucursal";
            getLblNombreEmpresa().setText(" Empresa: " + ((sessionCodefac.getEmpresa() != null) ? sessionCodefac.getEmpresa().getNombreLegal() : "Sin asignar") + " | "+sucursal+" | Usuario: " + nombreUsuario);
            
            //Obtener el tipo de licencia para imprimir en la pantalla inicio
            UtilidadesServiceIf utilidadesService = ServiceFactory.getFactory().getUtilidadesServiceIf();
            String licenciaNombre="sin licencia";
            if(sessionCodefac.getEmpresa()!=null)
            {
                TipoLicenciaEnum tipoLicenciaEnum = utilidadesService.getTipoLicencia(sessionCodefac.getEmpresa());
                licenciaNombre=tipoLicenciaEnum.getNombre();
            }
            
            getLblTextoSecundario().setText("Servidor IP: " + ipServidor + " | Licencia: " + licenciaNombre + " | Versión: " + ParametrosSistemaCodefac.VERSION);
        } catch (RemoteException ex) {
            Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizarNotificacionesCodefac() {
        this.widgetNotificacionCodefac.setEmpresa(sessionCodefac.getEmpresa()); //Actualizo este dato porque puede ser que esta cambiando de empresa
        this.widgetNotificacionCodefac.actualizarNotificaciones(ModoProcesarEnum.NORMAL);
    }
    
    @Override
    public void actualizarTituloCodefac()
    {
        ParametroCodefac parametroCodefac=sessionCodefac.getParametrosCodefac().get(ParametroCodefac.MODO_FACTURACION);
        if(parametroCodefac==null)
        {
            setTitle(ParametrosSistemaCodefac.NOMBRE_SISTEMA);
        }
        else
        {
            String valor=parametroCodefac.getValor();
            setTitle(ParametrosSistemaCodefac.NOMBRE_SISTEMA+" | "+valor);
        }        
        
    }

       
    public class ListenerIcono implements IconoInterfaz 
    {
        private Class ventanaClase;
        private Boolean maximizado;

        public ListenerIcono(Class ventanaClase, Boolean maximizado) {
            this.ventanaClase = ventanaClase;
            this.maximizado = maximizado;
        }

        
        @Override
        public void doubleClick() {
            try {
                System.out.println("Ejemplo:"+ventanaClase.getName());
                Constructor contructor = ventanaClase.getConstructor();
                ControladorCodefacInterface ventana = (ControladorCodefacInterface) contructor.newInstance();
                ventana.reconstruirPantalla(); //Esto sirve para la pantalla se reconstruya con los menus secundarios
                agregarListenerMenu(ventana,maximizado,null,null);
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void grabarNuevaPosicion(Point nuevaPosicion) {
            try {
                Map<String, Object> mapBuscar;
                AccesoDirectoServiceIf servicio = ServiceFactory.getFactory().getAccesoDirectoServiceIf();
                
                mapBuscar = new HashMap<>();
                mapBuscar.put("nombre", ventanaClase.getName());
                AccesoDirecto acceso=servicio.buscarPorNombre(ventanaClase.getName());
                acceso.setX((int)nuevaPosicion.getX());
                acceso.setY((int)nuevaPosicion.getY());
                servicio.editar(acceso);
            } catch (RemoteException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(GeneralPanelModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void cambiarCursorEspera()
    {
        //if (frame != null) {
            RootPaneContainer root = (RootPaneContainer) getRootPane().getTopLevelAncestor();
            root.getGlassPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            root.getGlassPane().setVisible(true);
        //}
    }
    
    /**
     * Interfaz que me permite volver a establecer el curso de forma normal
     * @param frame 
     */
    public void cambiarCursorNormal()
    {
        RootPaneContainer root = (RootPaneContainer) getRootPane().getTopLevelAncestor();
        root.getGlassPane().setCursor(Cursor.getDefaultCursor());
    }
    
    //TODO: HACER QUE ESTOS METODOS TAMBIEN SIRVAN PARA OTRAS FUNCIONALIDADES DE ESTA CLASE
    @Override
    public void cambiarEstadoFormularioEditar(GeneralPanelInterface frame) {
        String tituloOriginal = getTituloOriginal(frame.getTitle());
        frame.setTitle(tituloOriginal + " [Editar]");
        frame.estadoFormulario= ControladorCodefacInterface.ESTADO_EDITAR;
    }

    //TODO: HACER QUE ESTOS METODOS TAMBIEN SIRVAN PARA OTRAS FUNCIONALIDADES DE ESTA CLASE
    @Override
    public void cambiarEstadoFormularioNuevo(GeneralPanelInterface frame) {
        String tituloOriginal = getTituloOriginal(frame.getTitle());
        frame.setTitle(tituloOriginal + " [Nuevo]");
        frame.estadoFormulario= ControladorCodefacInterface.ESTADO_GRABAR;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //              CLASES E INTERFACES ADICIONALES
    ////////////////////////////////////////////////////////////////////////////
    
   
}