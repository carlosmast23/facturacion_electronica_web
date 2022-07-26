/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.Licencia;
import ec.com.codesoft.codefaclite.licence.ValidacionLicenciaCodefac;
import ec.com.codesoft.codefaclite.licence.NoExisteLicenciaException;
import ec.com.codesoft.codefaclite.licence.ValidacionLicenciaExcepcion;
import ec.com.codesoft.codefaclite.main.panel.ValidarLicenciaDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.service.UsuarioServicio;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PerfilUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.varios.InterfazRed;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadVarios;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSistema;
import ec.com.codesoft.codefaclite.ws.codefac.test.service.WebServiceCodefac;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import java.awt.Desktop;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import org.eclipse.persistence.internal.sessions.factories.SessionsFactory;
import sun.applet.Main;

/**
 *
 * @author
 */
public class ValidarLicenciaModel extends ValidarLicenciaDialog{
    
    public Boolean licenciaCreada;
    public Boolean actualizaLicencia;
    public Empresa empresa;
    
    public ValidarLicenciaModel(Frame parent, boolean modal,Boolean actualizarLicencia,Empresa empresa) {
        super(parent, modal);
        addListener();
        iniciarComponentes();
        this.licenciaCreada=false;
        this.actualizaLicencia=actualizarLicencia;
        this.empresa=empresa;
    }

    private void addListener() {
        addListenerButtons();
        addListenerChecks();
    }
    
    /**
     * TODO: Unificar esta parte con la de la pantalla de configuracion rapida
     */
    private void crearLicencia()
    {

        String usuarioTxt = getTxtUsuarioVerificar().getText();
        //Crea la nueva licencia con el usuario
        Licencia licencia = new Licencia();
        try {
            licencia.cargarLicenciaOnline(usuarioTxt);
            InterfazRed interfaRed=(InterfazRed) getCmbInterfacesRedRegistrar().getSelectedItem();
            licencia.setNombreInterfazRed(interfaRed.nombre);
        } catch (Exception ex) {
            Logger.getLogger(ValidarLicenciaModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        Properties propiedad = null;
        try {
            propiedad = ServiceFactory.getFactory().getUtilidadesServiceIf().crearLicencia(empresa, licencia);

            //Properties propiedad = validacionLicenciaCodefac.crearLicenciaMaquina(licencia);
        } catch (RemoteException ex) {
            Logger.getLogger(ValidarLicenciaModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ValidarLicenciaModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Actualizar la nueva licencia en el servidor
        WebServiceCodefac.actualizarLicencia(getTxtUsuarioVerificar().getText(), propiedad.getProperty(Licencia.PROPIEDAD_LICENCIA));

                
    }
    

    private void addListenerButtons() {
        getBtnSalirRegistro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        getBtnSalirRegistro1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        getBtnSalirVerificar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        getBtnRegistrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearLicencia();
                
                if(!getChkNoCrearUsuario().isSelected())
                {
                    //Verificar que no exista el usuario admin
                    if(getTxtUsuarioRegistrar().getText().equals("admin") || getTxtUsuarioRegistrar().getText().equals("root"))
                    {
                        DialogoCodefac.mensaje("Error","No se puede crear un usuario con la palabra admin o root",DialogoCodefac.MENSAJE_INCORRECTO);
                        return;
                    }


                    //Genera un nuevo usuario con los datos ingresados                
                    UsuarioServicioIf servicio=ServiceFactory.getFactory().getUsuarioServicioIf();
                    Usuario usuario=new Usuario();
                    String clave=new String(getTxtClaveRegistrar().getPassword());
                    usuario.setClave(clave);
                    usuario.setNick(getTxtUsuarioRegistrar().getText());    
                    usuario.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                    usuario.setEmpresa(empresa);

                    try {

                        servicio.grabarUsuario(usuario,Perfil.PERFIL_GRATIS);

                    } catch (ServicioCodefacException ex) {
                        DialogoCodefac.mensaje("Error", ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
                        return;
                    } catch (RemoteException ex) {
                        Logger.getLogger(ValidarLicenciaModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                licenciaCreada=true;
                
                DialogoCodefac.mensaje("Felicidades","La licencia fue creada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
                dispose();
                
            }
        });
        
        /**
         * TODO: Unificar esta logica con la clase de UtilidadesLicencia para que quede homologada con el servicio de empresa para el formulario de CONFIGURACION RAPIDA
         */
        getBtnVerificar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuarioTexto=getTxtUsuarioVerificar().getText().trim();
                if(WebServiceCodefac.verificarCredenciales(usuarioTexto,new String(getTxtClaveVerificar().getPassword())))
                { 
                    //Verificar si existe la licencia para solo descargar
                    String licencia=null;
                    try {
                        licencia = WebServiceCodefac.getLicencia(usuarioTexto);
                    } catch (Exception ex) {
                        Logger.getLogger(ValidarLicenciaModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                                        
                    Licencia licenciaInternet=new Licencia();
                    try {
                        licenciaInternet.cargarLicenciaOnline(usuarioTexto);
                    } catch (Exception ex) {
                        Logger.getLogger(ValidarLicenciaModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    //No hace verificaciones porque esta accion solo es accesible desde la pantalla de menu
                    //y se supone que ya esta validando la licencia anterior
                    if(actualizaLicencia)
                    {
                        getjTabbedPane1().setEnabledAt(0, false);
                        getjTabbedPane1().setEnabledAt(1, false);
                        getjTabbedPane1().setEnabledAt(2, true);
                        getjTabbedPane1().setSelectedIndex(2);
                        TipoLicenciaEnum licenciaEnum= licenciaInternet.getTipoLicenciaEnum();
                        getLblTipoLicenciaActualizar().setText(licenciaEnum.getNombre());
                        getLblNumeroMaquinasActualizar().setText(licenciaInternet.getCantidadClientes()+"");
                        getLblNumeroUsuariosActualizar().setText(licenciaEnum.getNumeroUsuarios());
                        
                        //Agregar a la lista los modulos a actualizar
                        DefaultListModel<ModuloCodefacEnum> modeloLista=new DefaultListModel<ModuloCodefacEnum>();
                        for (ModuloCodefacEnum modulo : licenciaInternet.getModulosActivos()) 
                        {
                            modeloLista.addElement(modulo);
                        }
                        getLstModulos().setModel(modeloLista);
                        
                        return; //dtener la ejecucion
                        
                    }
                    
                    //Si la licencia existe
                    if(!licencia.equals("fail"))
                    {             
                        //Si existe en el servidor la licencia solo vuelve a descargar
                        Licencia licenciaDescargada=new Licencia();
                        licenciaDescargada.setUsuario(usuarioTexto);
                        licenciaDescargada.setLicencia(licencia);
                        licenciaDescargada.setTipoLicenciaEnum(licenciaInternet.getTipoLicenciaEnum());
                        licenciaDescargada.setCantidadClientes(licenciaInternet.getCantidadClientes());
                        licenciaDescargada.cargarModulosOnline();
                        
                        try {
                            ServiceFactory.getFactory().getUtilidadesServiceIf().crearLicenciaDescargada(empresa, licenciaInternet);
                            //validacionLicenciaCodefac.crearLicenciaDescargada(licenciaDescargada);
                        } catch (RemoteException ex) {
                            Logger.getLogger(ValidarLicenciaModel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ServicioCodefacException ex) {
                            Logger.getLogger(ValidarLicenciaModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        licenciaCreada=true;
                        DialogoCodefac.mensaje("Adverencia","La licencia ya esta registrada y fue descargada",DialogoCodefac.MENSAJE_ADVERTENCIA);
                        dispose();
                        return ;//Detener la ejecucion
                    }
                    else //Si la licencia no existe abre la pantall de actualizar datos
                    {
                        getjTabbedPane1().setEnabledAt(0, false);
                        getjTabbedPane1().setEnabledAt(1, true);
                        getjTabbedPane1().setEnabledAt(2, false);
                        getjTabbedPane1().setSelectedIndex(1);

                        TipoLicenciaEnum licenciaEnum = licenciaInternet.getTipoLicenciaEnum();
                        //Actualizar los labes para actualizar o para crear
                        getLblTipoLicenciaRegistro().setText(licenciaEnum.getNombre());
                        getLblNumeroMaquinaRegistro().setText(licenciaInternet.getCantidadClientes() + "");
                        getLblNumeroUsuarioRegistro().setText(licenciaEnum.getNumeroUsuarios());

                        //Setear las variables del usuario y la clave del la pagina web
                        getTxtUsuarioRegistrar().setText(usuarioTexto);
                        getTxtClaveRegistrar().setText(new String(getTxtClaveVerificar().getPassword()));
                    }           
                    
                }
                else
                {
                    DialogoCodefac.mensaje("Error","El usuario o la clave son incorrectas", DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
            }
        });
        
        getBtnActualizarLicencia().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    
                //Crear la nueva licencia con los datos de esta maquina
                crearLicencia();
                DialogoCodefac.mensaje("Felicidades", "Su licencia fue actualizada correctamente \n-Por favor ingrese nuevamente en el sistema para reflejar los nuevos cambios ", DialogoCodefac.MENSAJE_CORRECTO);
                licenciaCreada=true;//Variable para indicar que la licencia se actualizo correctamente
                dispose();
                //ec.com.codesoft.codefaclite.main.init.Main.iniciarComponentes();

            }
        });
        
        getLblRegistro().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UtilidadesSistema.abrirUrlNavegador(ParametrosSistemaCodefac.LINK_REGISTRO_CODEFAC);
                //Desktop dk = Desktop.getDesktop("");
                //dk.browse(new URI("http://www.cf.codesoft-ec.com/index.php/general/registro"));
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
        
    }
    
    
    
    public static void main(String[] args)
    {
        ValidarLicenciaModel validar=new ValidarLicenciaModel(null,true,true,null); //TODO:Esta parte queda temporal de esta manera porque puede ser que o funcione porque no se esta mandando ninguna empresa
        validar.setVisible(true);
    }

    private void iniciarComponentes() {
        getjTabbedPane1().setEnabledAt(0, true);
        getjTabbedPane1().setEnabledAt(1, false);
        getjTabbedPane1().setEnabledAt(2, false);
        
        try {
            //Cargar opciones en el Combo para las tarjetas de red
            List<InterfazRed> interfacesRed=UtilidadVarios.obtenerInterfazValidas();
            UtilidadesComboBox.llenarComboBox(getCmbInterfacesRedRegistrar(), interfacesRed);
        } catch (SocketException ex) {
            Logger.getLogger(ValidarLicenciaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void addListenerChecks() {
        getChkNoCrearUsuario().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                
                Boolean seleccionado=false;
                if (e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                    seleccionado=true;
                }
                getTxtUsuarioRegistrar().setEnabled(!seleccionado);
                getTxtClaveRegistrar().setEnabled(!seleccionado);
                
            }
        });
    }
    
    
    
    
}
