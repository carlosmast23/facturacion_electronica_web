/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.init;

//import com.sun.xml.internal.ws.client.ClientTransportException;
import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.jtattoo.plaf.aero.AeroLookAndFeel;
import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
import com.jtattoo.plaf.fast.FastLookAndFeel;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import com.jtattoo.plaf.mcwin.McWinLookAndFeel;
import com.jtattoo.plaf.mint.MintLookAndFeel;
import com.jtattoo.plaf.smart.SmartLookAndFeel;
import com.jtattoo.plaf.texture.TextureLookAndFeel;
import ec.com.codesoft.codefaclite.controlador.comprobantes.MonitorComprobanteModel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.AyudaCodefacModel;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.PanelSecundarioAbstract;
import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.ValidadorCodefacModel;
import ec.com.codesoft.codefaclite.controlador.logs.LogControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.general.ParametrosClienteEscritorio;
import ec.com.codesoft.codefaclite.corecodefaclite.general.ParametrosClienteEscritorio.TipoClienteSwingEnum;
import ec.com.codesoft.codefaclite.main.actualizacion.ActualizacionSistemaUtil;
import ec.com.codesoft.codefaclite.main.archivos.ArchivoConfiguracionesCodefac;
import ec.com.codesoft.codefaclite.servicios.ServidorSMS;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.Licencia;
import ec.com.codesoft.codefaclite.licence.ValidacionLicenciaCodefac;
import ec.com.codesoft.codefaclite.licence.NoExisteLicenciaException;
import ec.com.codesoft.codefaclite.licence.ValidacionLicenciaExcepcion;
import ec.com.codesoft.codefaclite.main.archivos.ArchivoConfiguracionesCodefac.ModoActualizacionEnum;
import ec.com.codesoft.codefaclite.main.model.ConfiguracionesInicalesModel;
import ec.com.codesoft.codefaclite.main.model.DescargaModel;
import ec.com.codesoft.codefaclite.main.model.GeneralPanelModel;
import ec.com.codesoft.codefaclite.main.model.HiloPublicidadCodefac;
import ec.com.codesoft.codefaclite.main.model.IngresarDatosClienteModel;
import ec.com.codesoft.codefaclite.main.model.LoginModel;
import ec.com.codesoft.codefaclite.main.model.ModoAplicativoModel;
import ec.com.codesoft.codefaclite.main.model.ServidorMonitorModel;
import ec.com.codesoft.codefaclite.main.model.SplashScreenModel;
import ec.com.codesoft.codefaclite.main.model.ValidarLicenciaModel;
import ec.com.codesoft.codefaclite.main.other.ActualizarVersionCodefac;
import ec.com.codesoft.codefaclite.main.other.ArchivoDescarga;
import ec.com.codesoft.codefaclite.main.other.BaseDatosCredenciales;
import ec.com.codesoft.codefaclite.main.other.TareasProgramadasCodefac;
import ec.com.codesoft.codefaclite.main.panel.publicidad.Publicidad;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
import ec.com.codesoft.codefaclite.main.utilidades.UtilidadServicioWeb;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servicios.controller.ControllerServiceUtil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.PersistenciaDuplicadaException;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.tareasProgramadas.GestorTareasProgramadas;
import ec.com.codesoft.codefaclite.servidor.tareasProgramadas.RespaldoProgramadoTarea;
//import ec.com.codesoft.codefaclite.servidor.service.UtilidadesService;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesServidor;

import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EmpresaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ActualizarSistema;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EstiloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ModoSistemaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UtilidadesServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PerfilServiceIf;
import ec.com.codesoft.codefaclite.utilidades.archivos.UtilidadesDirectorios;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;
import ec.com.codesoft.codefaclite.utilidades.sql.UtilidadSql;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadVarios;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSistema;
import ec.com.codesoft.codefaclite.utilidades.web.UtilidadesWeb;
import ec.com.codesoft.codefaclite.ws.codefac.test.service.WebServiceCodefac;
import java.awt.Font;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author
 */
public class Main {
    
    //Variable que me servira para tener un icono en la barra de tareas porque como solo uso dialogos , por ratos no existe ninguna referencia para que el usuario pueda saber si la aplicacion se esta ejecutando
    private static JFrame frameAplicacion=new JFrame();

    private static final Logger LOG = Logger.getLogger(Main.class.getName());
    
    
    /**
     * TODO: Ver si mejor se usa un enum
     * Variable para saber el modo que inicia el aplicativo
     */
    public static Integer modoAplicativo;

    public static void main(String[] args) 
    {
        try
        {
            System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
            //Desabilito para que no se veo nada de la pantalla que contiene el proceso de inicio
            frameAplicacion.setUndecorated(true);
            frameAplicacion.setIconImage(ParametrosSistemaCodefac.iconoSistema);
            frameAplicacion.setVisible(true);

            System.setProperty("sun.net.client.defaultConnectTimeout", "2000"); //Establece el tiempo de espera para las conexiones con el servidor

            //Configurar diferente tipo de letra para los dialogos
            UIManager.put("OptionPane.messageFont", new Font("Arial", Font.PLAIN, 14));

            /**
             * Configura el archivo para guardar la configuracion inicial en propertys de como va a iniciar el aplicativo
             */
            cargarConfiguracionesIniciales();

            //Verifica si se esta ejecutando la ultima version o manda a actualizar
            ActualizarVersionCodefac.verificarUltimaVersionCodefac();

            //Configurar los log el directorio y donde se va a mandar a grabar los datos
            configurarLogs();


            /**
             * Carga el tema seleccionado por defecto en aarchivo codefac.ini
             */
            cargarTemaCodefac();
            cargarPuertoSistema();

            /**
             * Verificar si estan actualizaciones pendientes de una nueva version
             */
            verificarActualizacionBaseDatosVersion();

            /**
             * Seleccionar el modo de inicio de Codefac si no selecciona un modo no
             * le permite acceder a los siguiente funcionalidad, que pueden ser CLIENTE , CLIENTE-SERVIDOR, SERVIDOR
             */
            iniciarModoAplicativo(true);

            /**
             * Funcionalidad complementaria que inicia todos los componentes
             * necesarios
             */
            iniciarComponentes();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            LOG.log(Level.INFO,e.getMessage());
        }
    }
    
    private static void configurarLogs()
    {
        LogControlador logControlador=new LogControlador();    
        LOG.log(Level.INFO,"Iniciado configuracion de los logs");
                
    }

    //Lee el archivo de configuraciones de cada computador como por ejemplo
    //para saber la modalidad por defecto que se debe ejcutar el aplicativo
    private static void cargarConfiguracionesIniciales() 
    {
        ArchivoConfiguracionesCodefac.getInstance().cargarConfiguracionesIniciales();
    }
    
    
        
    /**
     * Verifica si existe o selecciona el modo del aplicativo (Cliente,
     * Servidor, Cliente-Servidor)
     *
     * @param configuracionesDefecto elige si desea que esocja las
     * configuraciones guardas o que siempre pregunte el modo de inicio
     */
    public static void iniciarModoAplicativo(Boolean configuracionesDefecto) {
        //Si existen configuraciones iniciales solo las carga
        PropertiesConfiguration propiedadesIniciales=ArchivoConfiguracionesCodefac.getInstance().getPropiedadesIniciales();
        if (propiedadesIniciales != null && configuracionesDefecto) {
            String modoAplicativoStr = propiedadesIniciales.getString(ArchivoConfiguracionesCodefac.CAMPO_MODO_APLICATIVO);
            if (modoAplicativoStr != null) {
                modoAplicativo = Integer.parseInt(modoAplicativoStr);
                return; //sio existe no continua buscando el modo de aplicativo
            }
        }
        
        /**
         * Seleccionar el modo de inicio de Codefac si no selecciona un modo no
         * le permite acceder a los siguiente funcionalidad
         */
        ModoAplicativoModel dialogAplicativo = new ModoAplicativoModel(frameAplicacion, true);
        dialogAplicativo.setLocationRelativeTo(null);
        dialogAplicativo.setVisible(true);

        //Si no selecciona ninguna opcion salir del aplicativo
        if (dialogAplicativo.getModo() == null) {
            System.exit(0);
        } else {
            try {
                modoAplicativo = dialogAplicativo.getModo();
                propiedadesIniciales.setProperty(ArchivoConfiguracionesCodefac.CAMPO_MODO_APLICATIVO, modoAplicativo + "");
                if(dialogAplicativo.versionPrueba)
                {
                    propiedadesIniciales.setProperty(ArchivoConfiguracionesCodefac.CAMPO_IP_ULTIMO_ACCESO_SERVIDOR,ParametrosSistemaCodefac.IP_SERVIDOR_PRUEBA);
                    propiedadesIniciales.setProperty(ArchivoConfiguracionesCodefac.CAMPO_TIPO_CLIENTE,TipoClienteSwingEnum.REMOTO.getNombre());
                }   
                
                ArchivoConfiguracionesCodefac.getInstance().guardar();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    private static String consultarUltimaVersionBd()
    {
        String query="SELECT VALOR FROM PARAMETRO WHERE NOMBRE = '"+ParametroCodefac.VERSION_SISTEMA+"'";
        String versionGrabada=UtilidadSql.ejecutarConsultaSqlPrimerResultado(AbstractFacade.usuarioDb, AbstractFacade.claveDb, query,"VALOR");
        return versionGrabada;
    }
    
    private static String obtenerUltimaVersion() 
    {
        //Primero intenta obtener la version de la base de datos
        //String query="SELECT VALOR FROM PARAMETRO WHERE NOMBRE = '"+ParametroCodefac.VERSION_SISTEMA+"'";
        //String versionGrabada=UtilidadSql.ejecutarConsultaSqlPrimerResultado(AbstractFacade.usuarioDb, AbstractFacade.claveDb, query,"VALOR");
        String versionGrabada=consultarUltimaVersionBd();
        //Si no encuentra la version en la ultima base de datos cargo la anterior
        if(versionGrabada==null)
        {
            PropertiesConfiguration propiedadesIniciales=ArchivoConfiguracionesCodefac.getInstance().getPropiedadesIniciales();
            versionGrabada=propiedadesIniciales.getString(ArchivoConfiguracionesCodefac.CAMPO_VERSION);
        }
        return versionGrabada;
    }
    
    private static void cargarCredencialesBaseDatos()
    {
        //Obtiene una instancia de un objeto donde puedo interactuar con la base de datos
        BaseDatosCredenciales credenciales = BaseDatosCredenciales.getInstance();
        //verificar si existen los datos creados
        if (credenciales.cargarDatos()) {
            String usuarioDb = credenciales.getUsuario();
            String claveDb = credenciales.getClave();
            //Si falta algun datos del usuario y la clave abro la pantalla de crear credenciales
            if (usuarioDb != null || claveDb != null) { //TODO: hacer una validacion tambien cuando falte alguno de los datos por algun motivo anormal
                AbstractFacade.usuarioDb = usuarioDb;
                AbstractFacade.claveDb = claveDb;
            }
        } else {
            DialogoCodefac.mensaje("Alerta", "No se puede actualizar la base de datos , error en las credenciales", DialogoCodefac.MENSAJE_ADVERTENCIA);
        }
    }
    
    //Funcion que verifica si se instalo una nueva version y ejecuta los scripts para actualizar la base de datos
    private static void verificarActualizacionBaseDatosVersion()
    {   
        PropertiesConfiguration propiedadesIniciales=ArchivoConfiguracionesCodefac.getInstance().getPropiedadesIniciales();
        //Si el usuario inicia el programa en modo cliente no debe hacer esta validacion de actualizar datos
        String modoAplicativo = propiedadesIniciales.getString(ArchivoConfiguracionesCodefac.CAMPO_MODO_APLICATIVO);
        
        //Si no tiene modo o tiene modo cliente no se tiene que hacer ninguna actualizacion
        if (modoAplicativo==null || modoAplicativo.equals(ModoAplicativoModel.MODO_CLIENTE.toString())) 
        {
            return ;
        }
        
        LOG.log(Level.INFO," Iniciando verificarActualizacionBaseDatosVersion");
        //PropertiesConfiguration propiedadesIniciales=ArchivoConfiguracionesCodefac.getInstance().getPropiedadesIniciales();
        //String versionGrabada=propiedadesIniciales.getString(ArchivoConfiguracionesCodefac.CAMPO_VERSION);
        cargarCredencialesBaseDatos();
        String versionGrabada=obtenerUltimaVersion();
        
        
        if(versionGrabada!=null)
        {
            if(!versionGrabada.equals(""))
            {
                //Solo si existe el dato en el archivo , verifico que el dato almacenado sea diferente y superior a la version ejecutada para actualizar
                if(UtilidadesSistema.compareVersion(versionGrabada,ParametrosSistemaCodefac.VERSION)==-1)
                {
                   
                    try {
                        
                        
                        
                        //Solo actualizar si es un modo servidor , o cliente servidor
                        if (modoAplicativo!=null && !modoAplicativo.equals(ModoAplicativoModel.MODO_CLIENTE.toString())) 
                        {
                            //qdjkqwe

                            //TODO: Metodo que ejecuta los scripts para actualizar el sistema
                            if(UtilidadesServidor.actualizarBaseDatos(versionGrabada))
                            {
                                //Solo actualizo el archivo de la versión si efectivamente se realizo las modificaciones en la base de datos
                                //propiedadesIniciales.setProperty(ArchivoConfiguracionesCodefac.CAMPO_VERSION,ParametrosSistemaCodefac.VERSION);
                                //ArchivoConfiguracionesCodefac.getInstance().guardar();
                                grabarVersionNueva();
                            }
                        }                        
                        
                    } catch (Exception ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }
        }
        else
        {
            //Si no hay dato no se actualiza porque asumo que es la primera vez que se usa el sistema
            //propiedadesIniciales.setProperty(ArchivoConfiguracionesCodefac.CAMPO_VERSION, ParametrosSistemaCodefac.VERSION);
            //ArchivoConfiguracionesCodefac.getInstance().guardar();
            grabarVersionNueva();
        }
        LOG.log(Level.INFO," Terminando verificarActualizacionBaseDatosVersion");
        
    }

    private static void grabarVersionNueva()
    {
        String versionGrabadaBd=consultarUltimaVersionBd();
        if(versionGrabadaBd==null)
        {
            String sqlInsert="INSERT INTO PARAMETRO (NOMBRE,VALOR) VALUES('"+ParametroCodefac.VERSION_SISTEMA+"','"+ParametrosSistemaCodefac.VERSION+"')";
            UtilidadSql.ejecutarProcesoSql(AbstractFacade.usuarioDb, AbstractFacade.claveDb, sqlInsert);
        }
        else
        {
            //Grabar en la base de datos
            String sqlProceso="UPDATE PARAMETRO SET VALOR = '"+ParametrosSistemaCodefac.VERSION+"' WHERE NOMBRE = '"+ParametroCodefac.VERSION_SISTEMA+"' ";
            UtilidadSql.ejecutarProcesoSql(AbstractFacade.usuarioDb, AbstractFacade.claveDb, sqlProceso);
        }          
       
        
        //Grabar en el archivo
        try {
            PropertiesConfiguration propiedadesIniciales=ArchivoConfiguracionesCodefac.getInstance().getPropiedadesIniciales();
            propiedadesIniciales.setProperty(ArchivoConfiguracionesCodefac.CAMPO_VERSION, ParametrosSistemaCodefac.VERSION);
            ArchivoConfiguracionesCodefac.getInstance().guardar();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Cargar Recursos servicios protocolo RMI
     */
    public static void cargarRecursosRmiServidor(String ipServidor) {
        //Iniciar el Protocolo RMI para activar el servidor en modo REMOTO para recibir peticiones por la red
        ControllerServiceUtil.cargarRecursosServidor(ipServidor);        
        
    }
    
    

    public static void cargarRecursosRmiCliente(String ipServidor) {
            ServiceFactory.newController(ipServidor);
    }

    public static void iniciarComponentes() {
        try {
            //Desactivo la pantalla del hilo de ejecuion porque el splashScreen tiene un una pantalla que se muestra en la barra de tareas
            frameAplicacion.setVisible(false);
            
            SplashScreenModel splashScren = new SplashScreenModel();
            splashScren.agregarPorcentaje(40, "Cargando base de datos");
            splashScren.agregarPorcentaje(60, "Cargando datos session");
            splashScren.agregarPorcentaje(80, "Creando controlador codefac");
            splashScren.agregarPorcentaje(100, "Cargando ventanas");
            splashScren.setVisible(true);
            splashScren.iniciar();
            
            String ipServidor ="";
            
            if (modoAplicativo.equals(ModoAplicativoModel.MODO_CLIENTE)) 
            {
                    //System.setProperty("java.rmi.server.hostname","192.168.100.2"); 
                    //Consultar si existe grabado la ip del servidor para cargar por defecto la ultima
                    PropertiesConfiguration propiedadesIniciales=ArchivoConfiguracionesCodefac.getInstance().getPropiedadesIniciales();
                    String ipServidorDefecto=propiedadesIniciales.getString(ArchivoConfiguracionesCodefac.CAMPO_IP_ULTIMO_ACCESO_SERVIDOR);
                    String tipoCliente=propiedadesIniciales.getString(ArchivoConfiguracionesCodefac.CAMPO_TIPO_CLIENTE);
                    //Cargar los recursos para funcionar en modo cliente
                    //ipServidor = JOptionPane.showInputDialog("Ingresa la Ip del servidor: ",(ipServidorDefecto==null)?"":ipServidorDefecto);
                    IngresarDatosClienteModel dialogDatosCliente= new IngresarDatosClienteModel(ipServidorDefecto,ParametrosClienteEscritorio.TipoClienteSwingEnum.buscarPorNombre(tipoCliente));
                    dialogDatosCliente.setVisible(true);
                    IngresarDatosClienteModel.Respuesta respuesta= dialogDatosCliente.obtenerDatosIngresados();
                    cargarRecursosRmiCliente(respuesta.ipPublica);
                    ParametrosClienteEscritorio.tipoClienteEnum=respuesta.tipoClienteEnum;
                    //verificarConexionesPermitidas();
                    
                    //Grabar la ip del ultimo servidor accedido para no ingresar nuevamente el dato
                    propiedadesIniciales.setProperty(ArchivoConfiguracionesCodefac.CAMPO_IP_ULTIMO_ACCESO_SERVIDOR, respuesta.ipPublica + "");
                    propiedadesIniciales.setProperty(ArchivoConfiguracionesCodefac.CAMPO_TIPO_CLIENTE, respuesta.tipoClienteEnum.getNombre() + "");
                    ArchivoConfiguracionesCodefac.getInstance().guardar();
                    
                    LOG.log(Level.INFO, "Modo Cliente Activado");
            }
            else
            {
                /**
                 * Componentes iniciales que utilizo tanto para modo SERVIDOR y modo CLIENTE-SERVIDOR
                 */
                componentesBaseDatos(false);
                
                /**
                 * TODO: Al archivo de propiedades hacer el patron SINGLETON por que solo se debe cargar una sola vez para consultar desde cualquier lugar
                 * Buscar si tiene configurado una ip en el archivo de configuracion para iniciar el servidor con ese numero de ip
                 */
                PropertiesConfiguration propiedadesIniciales=ArchivoConfiguracionesCodefac.getInstance().getPropiedadesIniciales();
                String ipServidorDefecto=propiedadesIniciales.getString(ArchivoConfiguracionesCodefac.CAMPO_IP_SERVIDOR);
                
                //Si no encuentra una ip fija ,el sistema busca una ip por defecto
                if(ipServidorDefecto==null || ipServidorDefecto.isEmpty())
                {
                    ipServidor=UtilidadVarios.obtenerIpServidor();
                }
                else
                {
                    ipServidor=ipServidorDefecto;
                }
                
                cargarConfiguracionIpPublica(propiedadesIniciales);
                                
                cargarRecursosRmiServidor(ipServidor); 
                                
                cargarRecursosRmiCliente(ipServidor);
                
                //Iniciar proceso de tareas programadas
                TareasProgramadasCodefac.getInstance().iniciar();
                
               
                //Cargar el servidor de mensajeria
                ServidorSMS.getInstance().iniciarServidor();
                
                ejecutarActualizacionesCodefac();
                
                if (modoAplicativo.equals(ModoAplicativoModel.MODO_SERVIDOR)) {
                    //Crear el pantalla que va a manterner encendida la conexion con los clientes
                    ServidorMonitorModel monitor = new ServidorMonitorModel();
                    UtilidadesServidor.monitorUpdate = monitor;
                    monitor.setLocationRelativeTo(null);
                    monitor.setVisible(true);
                    LOG.log(Level.INFO, "Modo Servidor activado");
                }
                else
                {
                    if (modoAplicativo.equals(ModoAplicativoModel.MODO_CLIENTE_SERVIDOR)) {
                        //verificarConexionesPermitidas(); //Todo este codigo se lo debe manejar en el login
                        LOG.log(Level.INFO, "Modo Cliente Servidor Activado");
                    }
                }
   
            }            

            //Si el aplicativo debe iniciar en modo servidor se cierra la pantalla de carga del slashScreen de codefac porque no necesita cargar mas modulos
            if (modoAplicativo.equals(ModoAplicativoModel.MODO_SERVIDOR)) {
                splashScren.termino();
                return;
            }

            splashScren.siguiente();
            
            
            //session.setParametrosCodefac(getParametros());
            splashScren.siguiente();
            

            /**
             * Seteando la session de los datos a utilizar en el aplicativo
             */
            GeneralPanelModel panel = new GeneralPanelModel();
            //panel.setSessionCodefac(session);
            splashScren.siguiente();

            /**
             * Añadir menus y ventanas a la aplicacion principal
             */
            //panel.setVentanasMenuList(agregarMenuVentana(panel));
            //panel.setVentanasMenuList(null);
            panel.setPanelesSecundarios(agregarPanelesSecundarios());
            panel.agregarPanelesSecundarios();
            /**
             * Establecer propiedades del formulario principal
             */
            panel.setIconImage(ParametrosSistemaCodefac.iconoSistema); // NOI18N
            panel.setExtendedState(MAXIMIZED_BOTH);
            splashScren.siguiente();
            splashScren.termino();

            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
                        /**
             * ===============================================================
             * ACTIVAR EL SERVICIO WEB DEPENDIENDO LA CONFIGURACION
             * ===============================================================
             */
            activarServicioWeb();

            /**
             * Si el usuario devuuelto es incorrecto terminar el aplicativo
             */
            Sucursal sucursalDefecto= ArchivoConfiguracionesCodefac.getInstance().getSucursalPorDefecto();
            LoginModel.DatosLogin  datosLogin= cargarLoginUsuario(panel,sucursalDefecto);
            if (datosLogin.usuario == null) {
                LOG.log(Level.WARNING, "Error en la licencia ");
                //return;
            }
            
            //panel.setSessionCodefac(session);
            SessionCodefac session=panel.getSessionCodefac(); //Solo obtengo la session porque se supone que ya fue creada en el login
            panel.setVentanasMenuList(null);
            
            //TODO:Analizar que este codigo de activar o desactivar el tema de la publicidad deberia ejecutar el login de manera independiente
            //Agregando Hilo de Publicidad si es usuario Gratuito
            if (    session.getTipoLicenciaEnum().equals(TipoLicenciaEnum.GRATIS) && 
                    ParametrosSistemaCodefac.MODO.equals(ModoSistemaEnum.PRODUCCION) ) {
                HiloPublicidadCodefac hiloPublicidad = new HiloPublicidadCodefac(panel);
                hiloPublicidad.setPublicidades(obtenerPublicidades());
                hiloPublicidad.start();
                panel.setHiloPublicidadCodefac(hiloPublicidad);
            }

            
            //TODO:Aqui esta verificando para ver si podemos poner publicidad
           
            panel.ipServidor=ipServidor;
            panel.setearEtiquetasPiePaginaPantallaPrincipal();
            
            panel.iniciarComponentesGenerales();
            //frameAplicacion.dispose(); //Libero el recurso de la pantalla que tiene el icono en la barra de tareas
            panel.setVisible(true);
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    
    private static void cargarConfiguracionIpPublica(PropertiesConfiguration propiedadesIniciales)
    {
        String ipPublica = propiedadesIniciales.getString(ArchivoConfiguracionesCodefac.CAMPO_IP_PUBLICA_SERVIDOR);
        //TODO: Esta linea se debe descomentar para funcionar con una ip publica pero generaba erro con la libreria healthmarketscience , literalmente esto sirve para decir que se procesen todas las peticiones que viene desde la ip publica
        if (ipPublica != null && !ipPublica.isEmpty()) {
            System.setProperty("java.rmi.server.hostname", ipPublica);
            //Este parametro es muy importante para funcionar con la libreria healthmarketscience que sirve para enviar archivos por rmi y configurar el puerto
            String puertStr=ParametrosSistemaCodefac.PUERTO_COMUNICACION_RED+"";
            System.setProperty("com.healthmarketscience.rmiio.exporter.port", puertStr);
            ParametrosClienteEscritorio.puertoCallBack = ParametrosSistemaCodefac.PUERTO_COMUNICACION_RED;

        }
    }
    
    
    /**
     * Validacion que me permite verificar las licencias de la empresa y el tema de los pagos
     * @param empresa 
     */
    public static void seleccionarDirectorioRecursos(Empresa empresa)
    {
        try {
            ParametroCodefac parametroDirectorioRecursos = ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS,empresa);
            //Si no existe el parametro seteo la ruta por defecto que va a ser el directorio del usuario para no tener problemas de permisos
            if (parametroDirectorioRecursos == null || parametroDirectorioRecursos.getValor().equals("")) {
                String directorioUsuario = System.getProperty("user.home") + "/codefacRecursos"; 
                
                if (parametroDirectorioRecursos == null) {
                    //Abrir un dialogo para preguntar si desea cambiar de ubicacion de la carpeta de recursos
                    if (DialogoCodefac.dialogoPregunta("Directorio Recursos", "Por defecto la carpeta de recursos se creará en el directorio del usuario \n Desea cambiar el directorio por defecto? ", DialogoCodefac.MENSAJE_ADVERTENCIA)) {
                        /*JFileChooser f = new JFileChooser();
                        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        f.showSaveDialog(null);
                        if (f.getSelectedFile() != null) {
                            directorioUsuario = f.getSelectedFile().getPath();
                        }*/
                        directorioUsuario= UtilidadesDirectorios.buscarDirectorio();
                    }
                    /**
                     * TODO: Unir con un metodo generico en la parte de la guia de configuracion
                     */
                    parametroDirectorioRecursos = new ParametroCodefac();
                    parametroDirectorioRecursos.setNombre(ParametroCodefac.DIRECTORIO_RECURSOS);
                    parametroDirectorioRecursos.setValor(directorioUsuario);
                    parametroDirectorioRecursos.setEmpresa(empresa);
                    ServiceFactory.getFactory().getParametroCodefacServiceIf().grabar(parametroDirectorioRecursos);
                } else {
                    parametroDirectorioRecursos.setValor(directorioUsuario);
                    ServiceFactory.getFactory().getParametroCodefacServiceIf().editar(parametroDirectorioRecursos);
                }
            }
            
            //ServiceFactory.getFactory().getUtilidadesServiceIf().obtenerLicenciaEmpresa(empresa);
            //verificarLicencia(parametroDirectorioRecursos.getValor(),empresa);
            
            //Seteo el path de los directorio como una referencia global de todo el sistema
            //UtilidadesServidor.pathRecursos = parametroDirectorioRecursos.getValor(); TODO: Ver si ya no es necesario setear esa variable porque se setean al momento de hacer Login supuestamente
            
            //Verificar si la fecha maximo de pago no esta vencida para que el sistema alerte o no deje seguir usando el software
            //TODO: Ver si esta validacion se la hace antes , la unica razon porque se lo hace en esta parte es porque la variable global del usuario esta en el metodo verificarLicencia
            //verificarFechaMaximaPago(UtilidadesServidor.mapEmpresasLicencias.get(empresa).usuarioLicencia);
            
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
    
    /**
     * Este metodo sirve para buscar un nuevo directorio para los recursos
     * TODO: Esta de ver si se puede reutilizar con el metodo de arriba
     * @param empresa 
     */
    public static void actualizarDirectorioLicencia(Empresa empresa) {
        try {
            ParametroCodefac parametroDirectorioRecursos = ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS, empresa);
            String directorioUsuario = UtilidadesDirectorios.buscarDirectorio();
            
            //Si el  nuevo directorio seleccionado es null o no se seleccciona entonces no modifico nada
            if(directorioUsuario==null || directorioUsuario.trim().isEmpty())
            {
                return;
            }
            
            if(parametroDirectorioRecursos==null)
            {
                parametroDirectorioRecursos = new ParametroCodefac();            
            }    
            parametroDirectorioRecursos.setNombre(ParametroCodefac.DIRECTORIO_RECURSOS);
            parametroDirectorioRecursos.setValor(directorioUsuario);
            parametroDirectorioRecursos.setEmpresa(empresa);
            
            if(parametroDirectorioRecursos.getId()==null)
            {
                ServiceFactory.getFactory().getParametroCodefacServiceIf().grabar(parametroDirectorioRecursos);
            }
            else
            {
                ServiceFactory.getFactory().getParametroCodefacServiceIf().editar(parametroDirectorioRecursos);
            }

        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void activarServicioWeb()
    {
        String respuestaServicioWeb = ArchivoConfiguracionesCodefac.getInstance().obtenerValor(ArchivoConfiguracionesCodefac.CAMPO_ACTIVAR_SERVICIO_WEB);
        if (respuestaServicioWeb != null && respuestaServicioWeb.equals("si")) {
            UtilidadServicioWeb.activarServicioWeb();
        }
    }
    
    public static LoginModel.DatosLogin cargarLoginUsuario(GeneralPanelModel generalPanel,Sucursal sucursal) {
        frameAplicacion.setVisible(true); //muestro el hilo de ejcucion porque el login es un dialog que no tiene icono en la barra de tareas
        LoginModel loginModel = new LoginModel(generalPanel);
        
        
        loginModel.setearSucursalPorDefecto(sucursal);
        loginModel.setVisible(true);
        
        //if(loginModel.salirAplicacion)System.exit(0);
        
        LoginModel.DatosLogin usuarioLogin = loginModel.getDatosLogin();
        frameAplicacion.dispose();
        loginModel.getTxtUsuario().requestFocus();
        loginModel.dispose();
        
        //Grabar en el archivo por defecto cual fue la sucursal seleccionada para ingresar al sistema
        ArchivoConfiguracionesCodefac.getInstance().setSucursalPorDefecto(usuarioLogin.sucursal);
        
        return usuarioLogin;
    }

    /**
     * Verifica si no se exedio el numero de conexiones permitidas
     *
     * @return
     */
    /*private static void verificarConexionesPermitidas() {
        try {
            Boolean respuesta = ServiceFactory.getFactory().getUtilidadesServiceIf().verificarConexionesServidor();
            if (!respuesta) {
                
                DialogoCodefac.mensaje("Error", "Excedio el numero de clientes permitidos", DialogoCodefac.MENSAJE_INCORRECTO);
                System.exit(0);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }*/

    /**
     * Metodo para cargar los estilos disponibles
     */
    private static void cargarEstilosCodefac() {
        /*
            try {
            UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel"); //El tema es interesante
            //UIManager.setLookAndFeel("net.java.dev.nimbus");
            //UIManager.setLookAndFeel(NimbusLookAndFeel.class.getCanonicalName ());
            //UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
            //UIManager.setLookAndFeel(new PlasticLookAndFeel());
            //UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
            //UIManager.setLookAndFeel(new WindowsLookAndFeel()); //El tema es interesante
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //UIManager.getLookAndFeelDefaults();
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
         */

    }

   
   
    /**
     * Agrega todas las pantallas secundarias que puedo utilizar en la pantalla secuendaria del menu principal
     * @return 
     */
    public static Map<String,PanelSecundarioAbstract> agregarPanelesSecundarios()
    {
        Map<String,PanelSecundarioAbstract> paneles=new HashMap<String,PanelSecundarioAbstract>();
        //paneles.put(PanelSecundarioAbstract.PANEL_AYUDA,new AyudaCodefacModel() );
        paneles.put(PanelSecundarioAbstract.PANEL_MONITOR,MonitorComprobanteModel.getInstance());
        paneles.put(PanelSecundarioAbstract.PANEL_VALIDACION,new ValidadorCodefacModel());

        return paneles;
    }

    public static List<Publicidad> obtenerPublicidades() {
        List<Publicidad> publicidades = new ArrayList<Publicidad>();
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("angelicaPerfumes.png"), "https://www.facebook.com/avonbellezacosmeticos/", 3, "Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("anunciateConNosotros.png"), "https://www.facebook.com/codefac.ec/", 6, "Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("desarrolloSoftware.png"), "https://www.facebook.com/codesoft.ec/", 3, "Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("publicidadCodesoft.png"), "https://www.facebook.com/codesoft.ec/", 3, "Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("publicidadPaquete.png"), "https://www.facebook.com/codefac.ec/", 10, "Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("publicidadVirtualMall.png"), "https://www.facebook.com/vmquito/", 5, "Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("tol2dox.png"), "https://www.facebook.com/toldos.max.5", 2, "Dale click a la imagen para mas información"));
        publicidades.add(new Publicidad(RecursoCodefac.IMAGENES_PUBLICIDAD.getResourceURL("virtuallMallMensajeria.png"), "https://www.facebook.com/vmquito/", 4, "Dale click a la imagen para mas información"));
        return publicidades;
    }

    /**
     * Verifica y carga el Entity manager 
     */
    public static void componentesBaseDatos(Boolean repetirCredenciales) {
        /**
         * Verificar si existen las credenciales de la base de datos o las genero
         */
        boolean ingresarCredenciales=false;
        ConfiguracionesInicalesModel.ModoEnum tipoCredencial=ConfiguracionesInicalesModel.ModoEnum.REGISTRAR; //Por defecto hacemos que sea registrar el dialog
        
        if(!repetirCredenciales) //Solo ingresar si la variables es false , que quiere decir que haga la verificacion normal
        {
            BaseDatosCredenciales credenciales=BaseDatosCredenciales.getInstance();            
            //verificar si existen los datos creados
            if(credenciales.cargarDatos())
            {
                String usuarioDb=credenciales.getUsuario();
                String claveDb=credenciales.getClave();
                //Si falta algun datos del usuario y la clave abro la pantalla de crear credenciales
                if(usuarioDb==null || claveDb==null)
                {
                    ingresarCredenciales=true;
                }

            }
            else
            {
                //Si no existe las credenciales verifico si ya existe la base de datos para ver que pantalla abrir para las crdenciales
                if(UtilidadesArchivos.verificarExiteArchivo("Derby2.DB")) //Todo: Setear en una variable global
                {
                    tipoCredencial=ConfiguracionesInicalesModel.ModoEnum.ACCEDER;
                }                
                ingresarCredenciales=true;
            }
        }
        else //Si pide repetir las credenciales directamente abro el dialogo de las credenciales
        {
            ingresarCredenciales=true;
            tipoCredencial=ConfiguracionesInicalesModel.ModoEnum.ACCEDER;
        }

        //Si no existen o faltan credenciales abro la pantalla para crear
        if (ingresarCredenciales) 
        {
            abrirDialogoCredencialesDB(tipoCredencial);
        }
        
        
        //Obtengo los datos para la base de datos
        BaseDatosCredenciales credenciales = BaseDatosCredenciales.getInstance();
        credenciales.cargarDatos();
        AbstractFacade.usuarioDb = credenciales.getUsuario();
        AbstractFacade.claveDb = credenciales.getClave();
        
        /**
         * Cargar la persistencia del servidor
         */
        try {
            
            AbstractFacade.cargarEntityManager();
        } catch (PersistenceException e) {
            try {
                System.out.println(e.getMessage());
                UtilidadesServidor.crearBaseDatos();
                
                AbstractFacade.cargarEntityManager();
            } catch (PersistenceException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PersistenciaDuplicadaException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                //System.out.println(ex.getErrorCode());
                
                if(ex.getSQLState().equals("08004")) //Este error es cuando las credenciales son incorrectas
                {
                    DialogoCodefac.mensaje("Error base de datos","Las credencias de la base de datos son incorrectas",DialogoCodefac.MENSAJE_ADVERTENCIA);                                    
                    componentesBaseDatos(true);//solicitar nuevamente las credenciales                    
                    //System.exit(0); //Si las credenciales son incorrectas se sale del sistema
                }
                else
                {
                    DialogoCodefac.mensaje("Error al crear la base de datos",ex.getMessage()+"\n Se recomienda eliminar y volver a crear la db",DialogoCodefac.MENSAJE_ADVERTENCIA);                
                }
                
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                //TODO: Mejorar esta parte eliminando la base de datos para evitar que ejecuten con problemas en la base
            }

        } catch (PersistenciaDuplicadaException ex) {
            DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            System.exit(0);//Salir si existe otra instancia abierta
        }

    }
    
    private static void abrirDialogoCredencialesDB(ConfiguracionesInicalesModel.ModoEnum tipoRegistro)
    {
        ConfiguracionesInicalesModel configuraciones = new ConfiguracionesInicalesModel(tipoRegistro);
        configuraciones.setVisible(true);
        if (!configuraciones.datosGrabados) {
            System.exit(0); //Si no se grabo ningun dato se cierra porque el sistema no puede funcionar sin credenciales
        }
    }

    
    public static List<Perfil> obtenerPerfilesUsuario(Usuario usuario) {
        try {
            PerfilServiceIf servicio = ServiceFactory.getFactory().getPerfilServicioIf();
            return servicio.obtenerPerfilesPorUsuario(usuario);
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static void cargarTemaCodefac() {
        ArchivoConfiguracionesCodefac archivo=ArchivoConfiguracionesCodefac.getInstance();
        String tema=archivo.obtenerValor(ArchivoConfiguracionesCodefac.CAMPO_TEMA);
        if(tema==null)
        {
            EstiloCodefacEnum estiloDefecto=ParametrosSistemaCodefac.TEMA_DEFECTO_CODEFAC;
            ArchivoConfiguracionesCodefac.getInstance().agregarCampo(ArchivoConfiguracionesCodefac.CAMPO_TEMA,estiloDefecto.getNombre());
            setearEstiloSistema(estiloDefecto);
        }
        else
        {
            setearEstiloSistema(EstiloCodefacEnum.findByNombre(tema));
        }
        LOG.log(Level.INFO,"Cargando Tema Codefac ...");
    }
    
    private static void cargarPuertoSistema() {
        ArchivoConfiguracionesCodefac archivo = ArchivoConfiguracionesCodefac.getInstance();
        String puerto = archivo.obtenerValor(ArchivoConfiguracionesCodefac.CAMPO_PUERTO_SISTEMA);
        if (puerto !=null) {
            //Si existe un puerto en el archivo de configuracion cambio el puerto por defecto del aplicativo
            ParametrosSistemaCodefac.PUERTO_COMUNICACION_RED=Integer.parseInt(puerto);
        }
        
        String puertoSms= archivo.obtenerValor(ArchivoConfiguracionesCodefac.CAMPO_PUERTO_SMS);
        if (puertoSms !=null) {
            //Si existe un puerto en el archivo de configuracion cambio el puerto por defecto del aplicativo
            ParametrosSistemaCodefac.PUERTO_APP_MOVIL_SMS=Integer.parseInt(puertoSms);
        }
        LOG.log(Level.INFO,"Cargando cargarPuertoSistema ...");
        
    }
    
    public static void setearEstiloSistema(EstiloCodefacEnum estiloCodefacEnum)
    {
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
            //repaint();
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

    /**
     * Metodo que permite lanzar todas las actulizaciones pendientes en la aplicacione que son codigo en el aplicativo
     */
    private static void ejecutarActualizacionesCodefac() {
        try {
            List<ActualizarSistema> actualizaciones=ServiceFactory.getFactory().getActualizarSistemaServiceIf().obtenerCambiosPendientes();
            for (ActualizarSistema actualizacion : actualizaciones) {
                 String nombreMetodo=actualizacion.getNombreMetodo();
                 Method metodo=ActualizacionSistemaUtil.class.getMethod(nombreMetodo,null);
                 if(metodo!=null)
                 {
                      metodo.invoke(null);
                      //Si el metodo se ejecuta correctamente en la base le cambio de etado
                      actualizacion.setCambioActualizadoEnum(EnumSiNo.SI);
                      ServiceFactory.getFactory().getActualizarSistemaServiceIf().editar(actualizacion);
                      LOG.log(Level.INFO,"Actualizado metodo  :"+nombreMetodo);
                 }
                 else
                 {
                     LOG.log(Level.WARNING,"El metodo no se encontro para actualizar con el nombre :"+nombreMetodo);
                 }
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
