/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.main.init.Main;
import ec.com.codesoft.codefaclite.main.panel.LoginFormDialog;
import ec.com.codesoft.codefaclite.main.utilidades.UtilidadServicioWeb;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidor.service.UsuarioServicio;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OrdenarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ModoSistemaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import static ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj.ModoMensajeEnum.MENSAJE_ADVERTENCIA;
import static ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj.ModoMensajeEnum.MENSAJE_INCORRECTO;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.LoginRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import ec.com.codesoft.codefaclite.utilidades.archivos.UtilidadesDirectorios;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.ws.codefac.test.service.WebServiceCodefac;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlos
 */
public class LoginModel extends LoginFormDialog{

    private static final Logger LOG = Logger.getLogger(LoginModel.class.getName());
    public boolean salirAplicacion=false;
    
    //private UsuarioServicioIf usuarioServicio;
    private Usuario usuario;
    /**
     * Contenedor principal especialmente necesito este dato para poder setear la session despues de cargar el login
     */
    private GeneralPanelModel panelPrincipal;

    public LoginModel(GeneralPanelModel panelPrincipal) {
        super(null,true);
        valoresIniciales();
        initListenerBotones();
        initListenerCombos();
        initListenerPantalla();
        //this.usuarioServicio=ServiceFactory.getFactory().getUsuarioServicioIf();
        this.panelPrincipal=panelPrincipal;
        
        //Image fondoImg = new javax.swing.ImageIcon(getClass().getResource("/img/general/fondoInicial.jpg")).getImage();
        //getPanelPrincipal().setBorder(new Fondo2(fondoImg));
               
    }

    private void initListenerBotones() {
        
        getBtnSalir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //dispose();
                salirAplicacion=true;
                setVisible(false);
                UtilidadServicioWeb.apagarServicioWeb();
            }
        });
        
        getBtnIngresar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarSistema();
            }
        });
        
        getTxtClave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarSistema();
            }
        });
        
        getTxtUsuario().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ingresarSistema();
            }
        });
    }
    
    public Usuario getUsuarioLogin()
    {
        return usuario;
    }
    
    public DatosLogin getDatosLogin()
    {
        DatosLogin datosLogin=new DatosLogin();
        datosLogin.empresa=obtenerEmpresaSeleccionada();
        datosLogin.sucursal=obtenerSucursalSeleccionada();
        datosLogin.matriz=obtenerMatriz();
        datosLogin.usuario=getUsuarioLogin();
        return datosLogin;        
    }
    
    private Sucursal obtenerMatriz()
    {
        Sucursal sucursal=(Sucursal) getCmbSucursal().getSelectedItem();
        Sucursal matriz=null;
        if(sucursal!=null)
        {
            try {
                matriz = ServiceFactory.getFactory().getSucursalServiceIf().obtenerMatrizPorSucursal(sucursal.getEmpresa());
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return matriz;
    }
    
    private void ingresarSistema()
    {
        
        String clave = new String(getTxtClave().getPassword());
        String usuarioTxt = getTxtUsuario().getText();
        Empresa empresaSeleccionada=(Empresa) getCmbEmpresa().getSelectedItem();
        if (!usuarioTxt.equals("") && !clave.equals("")) {
            try {
                //usuario = usuarioServicio.login(usuarioTxt, clave, (Empresa) getCmbEmpresa().getSelectedItem());
                UsuarioServicioIf usuarioServicio=ServiceFactory.getFactory().getUsuarioServicioIf();
                LoginRespuesta loginRespuesta = usuarioServicio.login(usuarioTxt, clave, empresaSeleccionada);
                
                //Mostrar las alertas del sistema 
                if(loginRespuesta.alertas!=null)
                {
                    DialogoCodefac.mensaje("Alertas",loginRespuesta.obtenerAlertasConFormato(),MENSAJE_ADVERTENCIA);
                }
                
                switch(loginRespuesta.estadoEnum)
                {
                    case CORRECTO_USUARIO:
                        LOG.log(Level.INFO, "Ingresando con el usuario: " + usuarioTxt);
                        setVisible(false);
                        usuario=loginRespuesta.usuario;
                        panelPrincipal.setSessionCodefac(getDatosLogin());
                        break;
                    case INCORRECTO_USUARIO:
                        LOG.log(Level.WARNING, "Error al ingresar con el usuario: " + usuarioTxt+" \n"+LoginRespuesta.EstadoLoginEnum.INCORRECTO_USUARIO.getMensaje());
                        DialogoCodefac.mensaje("Error Login",LoginRespuesta.EstadoLoginEnum.INCORRECTO_USUARIO.getMensaje(), MENSAJE_INCORRECTO);
                        break;
                    case NO_EXISTE_DIRECTORIO_LICENCIA:
                        LOG.log(Level.WARNING, "Error al ingresar con el usuario: " + usuarioTxt+" \n"+LoginRespuesta.EstadoLoginEnum.INCORRECTO_USUARIO.getMensaje());
                        DialogoCodefac.mensaje("Error Login",LoginRespuesta.EstadoLoginEnum.NO_EXISTE_DIRECTORIO_LICENCIA.getMensaje(), MENSAJE_INCORRECTO);
                        Main.seleccionarDirectorioRecursos(empresaSeleccionada);
                        //validacionesEmpresa
                        break;
                    
                    case LICENCIA_DESACTUALIZADA:
                        LOG.log(Level.WARNING, "Error al ingresar con el usuario: " + usuarioTxt+" \n"+LoginRespuesta.EstadoLoginEnum.INCORRECTO_USUARIO.getMensaje());
                        DialogoCodefac.mensaje("Error Login",LoginRespuesta.EstadoLoginEnum.LICENCIA_DESACTUALIZADA.getMensaje(), MENSAJE_INCORRECTO);
                        String[] opciones = {"Ingresar Datos licencia","Cambiar directorio licencia", "Cancelar"};
                        int opcionSeleccionada = DialogoCodefac.dialogoPreguntaPersonalizada("Alerta", "Seleccione una opción para solucionar el problema", MENSAJE_ADVERTENCIA, opciones);
                        switch(opcionSeleccionada)
                        {
                            case 0:
                                pantallaRegistrarLicencia();
                                break;
                            case 1:
                                Main.actualizarDirectorioLicencia(empresaSeleccionada);
                                break;
                                
                        }
                        //validacionesEmpresa
                        break;
                        
                    case PAGOS_PENDIENTES:
                        LOG.log(Level.WARNING, "Error con las fechas de pago excedidas " + usuarioTxt+" \n"+LoginRespuesta.EstadoLoginEnum.INCORRECTO_USUARIO.getMensaje());
                        DialogoCodefac.mensaje("Error Login",LoginRespuesta.EstadoLoginEnum.PAGOS_PENDIENTES.getMensaje(), MENSAJE_INCORRECTO);                        
                        break;

                    default:
                        LOG.log(Level.WARNING, "Error al ingresar con el usuario: " + usuarioTxt+" \n"+LoginRespuesta.EstadoLoginEnum.INCORRECTO_USUARIO.getMensaje());
                        DialogoCodefac.mensaje("Error Login",loginRespuesta.estadoEnum.getMensaje(), MENSAJE_INCORRECTO);
                        break;
                }
                
               
            } catch (RemoteException ex) {
                Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
                DialogoCodefac.mensaje("Error Login", "Datos Incorrectos", MENSAJE_INCORRECTO);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    }
    
    public void pantallaRegistrarLicencia()
    {
        //Crear un dialogo si no existe la licencia o esta desactualizada o con alguna incoherencia
        //ValidarLicenciaModel licenciaDialog = new ValidarLicenciaModel(null, true, existeLicencia);
        Empresa empresa=(Empresa) getCmbEmpresa().getSelectedItem();
        ValidarLicenciaModel licenciaDialog = new ValidarLicenciaModel(null, true,false,empresa);
        licenciaDialog.setVisible(true);
        //licenciaDialog.setValidacionLicenciaCodefac
        //licenciaDialog.licenciaCreada
        /*if (validacion.verificarConexionInternet()) {
            licenciaDialog.setVisible(true);
            if (licenciaDialog.licenciaCreada) {
                return comprobarLicencia(pathBase); //volver a verificar la licencia
            } else {
                return false;
            }
        } else {
            DialogoCodefac.mensaje("Error", "Para activar su producto conéctese a Internet", DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }*/
    }

    private void valoresIniciales() {
        String anioActualStr=UtilidadesFecha.obtenerAnioStr(UtilidadesFecha.getFechaHoy());
        getLblPiePagina().setText("FactuLab software de facturación electrónica @ Factulab "+anioActualStr);
        
        try {
            UtilidadesComboBox.llenarComboBox(getCmbEmpresa(),ServiceFactory.getFactory().getEmpresaServiceIf().obtenerTodosActivos(OrdenarEnum.ASCEDENTE));
            //Setear valores de los combos
            /*List<Empresa> empresas=ServiceFactory.getFactory().getEmpresaServiceIf().obtenerTodos(); //Todo: Cambiar este metodo para solo obtener las empresas activas
            getCmbEmpresa().removeAllItems();
            for (Empresa empresa : empresas) {
                getCmbEmpresa().addItem(empresa);
            }*/
        } catch (RemoteException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cargarSucursalesPorEmpresa();        
        
        getCmbEmpresa().setVisible(false);
        getCmbSucursal().setVisible(false);
        getLblEmpresa().setVisible(false);
        getLblSucursal().setVisible(false);
        
        
    }
    
    private void cargarSucursalesPorEmpresa()
    {
        try {
            //Setear valores de los combos
            
            List<Sucursal> sucursales=ServiceFactory.getFactory().getSucursalServiceIf().consultarActivosPorEmpresa(obtenerEmpresaSeleccionada()); //Todo: Cambiar este metodo para solo obtener las empresas activas
            getCmbSucursal().removeAllItems();
            for (Sucursal sucursal : sucursales) {
                getCmbSucursal().addItem(sucursal);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }catch (ServicioCodefacException ex) {
            Logger.getLogger(LoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initListenerPantalla() {
         addComponentListener(new ComponentListener() { //Artificio para setear en los dialogs el focus
            @Override
            public void componentResized(ComponentEvent e) {}

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {
                getTxtUsuario().requestFocus();
            }

            @Override
            public void componentHidden(ComponentEvent e) {}
        });
        
        
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                salirAplicacion=true;
                setVisible(false);
                dispose();
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
                //salirAplicacion=true;
                //setVisible(false);
            }

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
    }
    
    private Empresa obtenerEmpresaSeleccionada()
    {
        return (Empresa) getCmbEmpresa().getSelectedItem();
    }

    private void initListenerCombos() {
        getCmbEmpresa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(obtenerEmpresaSeleccionada()!=null)
                {
                    cargarSucursalesPorEmpresa();     
                }
            }
        });
    }
    
    //TODO: Metodo Temporal para obtener la sucursal que el usuario accedio
    public Sucursal obtenerSucursalSeleccionada()
    {
        return (Sucursal) getCmbSucursal().getSelectedItem();
    }
    
    public void setearSucursalPorDefecto(Sucursal sucursal)
    {
        if(sucursal!=null)
        {
            getCmbEmpresa().setSelectedItem(sucursal.getEmpresa());
            getCmbSucursal().setSelectedItem(sucursal);
        }
    }
    
    /**
     * Clase de envoltorio solo para agrupar un conjunto de resultados de la pantalla Login
     */
    public class DatosLogin
    {
        public Empresa empresa;
        public Sucursal sucursal;
        public Sucursal matriz;
        public Usuario usuario;
        
    }
    
    
}
