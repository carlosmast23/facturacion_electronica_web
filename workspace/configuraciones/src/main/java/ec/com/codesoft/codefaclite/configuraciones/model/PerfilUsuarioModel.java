/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.busqueda.PerfilBusquedaDialogo;
import ec.com.codesoft.codefaclite.configuraciones.busqueda.PerfilUsuarioBusquedaDialogo;
import ec.com.codesoft.codefaclite.configuraciones.busqueda.PuntoEmisionBusquedaDialogo;
import ec.com.codesoft.codefaclite.configuraciones.panel.PerfilUsuarioPanel;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.CajaBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.EmpleadoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.TurnoBusquedaDiagolo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PerfilUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmisionUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaPermiso;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Turno;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.TurnoAsignado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.LoginRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author
 */
public class PerfilUsuarioModel extends PerfilUsuarioPanel{
    
    private Usuario usuario;

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        agregarListener();
        valoresIniciales();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        getTxtClaveAnterior().setEnabled(false); //desactivar el campo de clave anterior
        getLblClaveAnterior().setEnabled(false);
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            if (validarGrabar()) 
            {
                UsuarioServicioIf usuarioServicioIf = ServiceFactory.getFactory().getUsuarioServicioIf();
                setearValoresPantalla();
                usuarioServicioIf.grabar(usuario);
                session.setUsuario(usuario);
                DialogoCodefac.mensaje("Correcto", "El usuario se grabo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
            }
            else
            {
                //Si no pasa la validacion lanzo una execepcion
                throw new ExcepcionCodefacLite("Cancelado grabar");                
            }
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error",ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(PerfilUsuarioModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite("cancelado guardar");
        } catch (RemoteException ex) {
            DialogoCodefac.mensaje("Error","Ocurrio un error con el servidor", DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(PerfilUsuarioModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite("cancelado guardar");
        }
    }

    private Boolean validarGrabar()
    {
        String clave=new String(getTxtClave().getPassword());
        String claveRepetida=new String(getTxtClaveRepetir().getPassword());
        if (clave.toString().equals("")) 
        {
            DialogoCodefac.mensaje("Advertencia", "Por favor ingrese una clave valida para grabar", DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        
        if(!clave.equals(claveRepetida))
        {
            DialogoCodefac.mensaje("Advertencia", "Las Claves Ingresadas no son iguales", DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        
        return true;
    }
    
    @Override
    public void editar() throws ExcepcionCodefacLite {
        try {     
            UsuarioServicioIf usuarioServicioIf = ServiceFactory.getFactory().getUsuarioServicioIf();
            
            //Verificar si el usuario desea cambiar de clave
            String clave = new String(getTxtClave().getPassword());
            String claveRepetida = new String(getTxtClaveRepetir().getPassword());
            String claveAnterior = new String(getTxtClaveAnterior().getPassword());

            //Si alguna de estos datos fue modificado asumo que quiere editar la clave
            if (!clave.equals("") || !claveAnterior.equals("") || !claveRepetida.equals("")) {
                if (validarEditarClave(clave, claveRepetida, claveAnterior)) {
                    Usuario usuarioTmp=usuarioServicioIf.cambiarClave(usuario,claveAnterior,clave); //Todo : Mejorar esta parte para solo ejecutar cuando desean cambiar la clave
                    usuario=usuarioTmp;
                } else {
                    throw new ExcepcionCodefacLite("Validacion incorrecta");
                }
            }
            
            
            //Editar el usuario
            setearValoresPantalla();
            usuarioServicioIf.editar(usuario);            
            DialogoCodefac.mensaje("Correcto", "El usuario se edito correctamente", DialogoCodefac.MENSAJE_CORRECTO);
            
            
        } catch (RemoteException ex) {
            DialogoCodefac.mensaje("Error","Ocurrio un error con el servidor", DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(PerfilUsuarioModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite(ex.getMessage());
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error",ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            Logger.getLogger(PerfilUsuarioModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    
    }
        
    private boolean validarEditarClave(String clave,String claveRepetida,String claveAnterior)
    {
        
        if(clave.equals(""))
        {
            DialogoCodefac.mensaje("Validación", "El campo de la clave esta en blanco, no se puede cambiar de clave ", DialogoCodefac.MENSAJE_ADVERTENCIA);
            //return false; //Si la clave esta en blanco entonces no valido nada mas porque asumo que no quiere cambiar la clave
        }
        else //Solo hacer las demas validaciones si existe una clave ingresada
        {
            if (!usuario.getClave().equals(clave) && !clave.equals(claveRepetida)) 
            {
                DialogoCodefac.mensaje("Validación", "Para modificar la clave ,el campo de repetir clave tienen que ser igual ", DialogoCodefac.MENSAJE_ADVERTENCIA);
            }
            else
            {
                if(claveAnterior.equals(""))
                {
                    DialogoCodefac.mensaje("Validación", "Ingrese la clave anterior para cambiar a una nueva", DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
                else
                {
                    try {
                        LoginRespuesta loginRespuesta= ServiceFactory.getFactory().getUsuarioServicioIf().login(usuario.getNick(),claveAnterior,session.getEmpresa());
                        //Si la clave tiene alguna advertencia mando el mensaje
                        if(!loginRespuesta.estadoEnum.equals(loginRespuesta.estadoEnum.CORRECTO_USUARIO))
                        {
                            DialogoCodefac.mensaje("Error",loginRespuesta.obtenerAlertasConFormato(), DialogoCodefac.MENSAJE_ADVERTENCIA);
                        }
                        else
                        {
                            return true; //Si todos los datos son validados retorno true
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(PerfilUsuarioModel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(PerfilUsuarioModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        
        return false;
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        try {
            Boolean pregunta=DialogoCodefac.dialogoPregunta("Advertencia","Esta seguro que desea eliminar el usuario?",DialogoCodefac.MENSAJE_ADVERTENCIA);
            if(!pregunta)
            {
                throw new ExcepcionCodefacLite("Usuario no esta seguro de eliminar");
            }
            
            ServiceFactory.getFactory().getUsuarioServicioIf().eliminar(usuario);
        } catch (RemoteException ex) {
            Logger.getLogger(PerfilUsuarioModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(PerfilUsuarioModel.class.getName()).log(Level.SEVERE, null, ex);            
            DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
        
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
        PerfilUsuarioBusquedaDialogo perfilBusquedaDialogo=new PerfilUsuarioBusquedaDialogo(session.getEmpresa());
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(perfilBusquedaDialogo);
        buscarDialogoModel.setVisible(true);
        Usuario usuarioTemp = (Usuario) buscarDialogoModel.getResultado();

        if (usuarioTemp == null) {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar");
        }
        
        this.usuario=usuarioTemp;
        getTxtUsuario().setText(usuario.getNick());
        getTxtClaveAnterior().setEnabled(true); //desactivar el campo de clave anterior
        getLblClaveAnterior().setEnabled(true);
        getTxtEmpleado().setText((usuario.getEmpleado()!=null)?usuario.getEmpleado().toString():"");
        getCmbEstado().setSelectedItem(usuario.getEstadoEnum());
        getTxtParametrosComprobantesElectronicos().setText(usuario.getParametrosComprobatesElectronicos());
        cargarListaPerfilesUsuario();
        cargarListaPuntosEmision();
        cargarListaCajaPermiso();
        /**
         * Filtrar factura por usuario
         */
        getjComboBoxFiltrarFacturas().setSelectedItem(usuario.getFiltrarFacturaEnum());
    }

    @Override
    public void limpiar() {
        this.usuario=new Usuario();
        getTxtClave().setText("");
        getTxtClaveRepetir().setText("");
        getTxtUsuario().setText("");
        getTxtEmpleado().setText("");
        getTxtParametrosComprobantesElectronicos().setText("");
        cargarListaPerfilesUsuario();
        cargarListaPuntosEmision();
        cargarListaCajaPermiso();
        cargarListaTurnos(null);
        getCmbEstado().setSelectedItem(GeneralEnumEstado.ACTIVO);
        
    }

//    @Override
    public String getNombre() {
        return "Usuario y Roles";
    }

    @Override
    public String getURLAyuda() {
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
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void agregarListener() {
        
        getBtnBuscarEmpleado().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmpleadoBusquedaDialogo empleadosBusquedaDialogo = new EmpleadoBusquedaDialogo();
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(empleadosBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                Empleado empleadoTemp = (Empleado) buscarDialogoModel.getResultado();
                if (empleadoTemp != null) {
                    usuario.setEmpleado(empleadoTemp);
                    getTxtEmpleado().setText(empleadoTemp.toString());
                }
            }
        });
        
        getBtnAgregarPuntoEmision().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PuntoEmisionBusquedaDialogo puntoEmisionDialog=new PuntoEmisionBusquedaDialogo(session.getEmpresa());
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(puntoEmisionDialog);
                buscarDialogoModel.setVisible(true);
                PuntoEmision puntoEmisionTemp = (PuntoEmision) buscarDialogoModel.getResultado();
                if(puntoEmisionTemp!=null)
                {
                    //Si existe un usuario cargado agrega los detalles
                    if(usuario!=null)
                    {
                        PuntoEmisionUsuario puntoEmisionUsuario=new PuntoEmisionUsuario();
                        puntoEmisionUsuario.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                        puntoEmisionUsuario.setUsuario(usuario);
                        puntoEmisionUsuario.setPuntoEmision(puntoEmisionTemp);
                        usuario.addPuntoEmisionUsuario(puntoEmisionUsuario);
                        cargarListaPuntosEmision();
                    }
                }
            }
        });
        
        getBtnAgregarPerfil().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PerfilBusquedaDialogo perfilBusquedaDialogo = new PerfilBusquedaDialogo(session.getEmpresa());
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(perfilBusquedaDialogo);
                buscarDialogoModel.setVisible(true);
                Perfil perfilTemp = (Perfil) buscarDialogoModel.getResultado();
                
                if(perfilTemp!=null)
                {
                    //Si existe un usuario cargado agrega los detalles
                    if(usuario!=null)
                    {
                        PerfilUsuario perfilUsuario=new PerfilUsuario();
                        perfilUsuario.setFechaCreacion(UtilidadesFecha.getFechaHoy());
                        perfilUsuario.setPerfil(perfilTemp);
                        
                        usuario.addPerfilUsuario(perfilUsuario);
                        cargarListaPerfilesUsuario();
                    }
                }
                
            }
        });
        
        getBtnAgregarCaja().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CajaBusquedaDialogo cajaDialogo = new CajaBusquedaDialogo(session);
                BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(cajaDialogo);
                buscarDialogoModel.setVisible(true);
                Caja cajaTemp = (Caja) buscarDialogoModel.getResultado();
                if(cajaTemp != null)
                {
                    if(usuario != null)
                    {
                        CajaPermiso cajaPermiso = new CajaPermiso();
                        cajaPermiso.setCaja(cajaTemp);
                        cajaPermiso.setUsuario(usuario);
                        cajaPermiso.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                        usuario.addCajaPermisoUsuario(cajaPermiso);
                        cargarListaCajaPermiso();
                    }
                }
            }
        });
        
        getBtnAgregarTurno().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CajaPermiso cajaPermiso = getjListCajaPermiso().getSelectedValue();
                if (cajaPermiso == null) 
                {
                    DialogoCodefac.mensaje("Seleccionar un Caja", "Debe seleccionar una caja para asignar el turno", DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
                else
                {
                    TurnoBusquedaDiagolo turnoBusquedaDiagolo = new TurnoBusquedaDiagolo(session);
                    BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(turnoBusquedaDiagolo);
                    buscarDialogoModel.setVisible(true);
                    Turno turnoTemp = (Turno) buscarDialogoModel.getResultado();
                    if(turnoTemp != null)
                    {

                        TurnoAsignado turnoAsignado = new TurnoAsignado();
                        turnoAsignado.setCajaPermiso(cajaPermiso);
                        turnoAsignado.setTurno(turnoTemp);
                        turnoAsignado.setEstadoEnum(GeneralEnumEstado.ACTIVO);

                        cajaPermiso.addTurnoAsignado(turnoAsignado);
                        cargarListaTurnos(cajaPermiso);
                    }
                }
            }
        });
        
        getjListCajaPermiso().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                CajaPermiso cajaPermisoSelect = getjListCajaPermiso().getSelectedValue();
                if(cajaPermisoSelect != null)
                {
                    cargarListaTurnos(cajaPermisoSelect);
                }
            }
        });        
        
        getBtnQuitarPerfil().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila=getjListPerfiles().getSelectedIndex();
                
                if(fila!=-1)
                {
                    usuario.getPerfilesUsuario().remove(fila);
                    cargarListaPerfilesUsuario();
                }
                
            }
        });
        
        getBtnQuitarPuntoEmision().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PuntoEmisionUsuario puntoEmision=getjListPuntoEmision().getSelectedValue();
                
                if(puntoEmision!=null)
                {
                    //usuario.getPuntosEmisionUsuario().remove(puntoEmision);
                    puntoEmision.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                    cargarListaPuntosEmision();
                }
                
            }
        });
        
        getBtnQuitarCaja().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CajaPermiso cajaPermisoUsuario = getjListCajaPermiso().getSelectedValue();
                if(cajaPermisoUsuario != null)
                {
                    cajaPermisoUsuario.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                    cargarListaCajaPermiso();
                }
            }
        });
        
        getBtnQuitarTurno().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CajaPermiso cajaPermiso = getjListCajaPermiso().getSelectedValue();
                if (cajaPermiso == null) 
                {
                    DialogoCodefac.mensaje("Seleccionar un Caja", "Debe seleccionar una caja para elegir el turno a eliminar", DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
                else
                {
                    TurnoAsignado turnoAsignado = getjListTurnosCaja().getSelectedValue();
                    if(turnoAsignado != null)
                    {
                        turnoAsignado.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                        cargarListaTurnos(cajaPermiso);
                    }
                }
            }
        });
        
    }
            
    private void cargarListaPuntosEmision()
    {
        DefaultListModel listaModel=new DefaultListModel();
        
        if(usuario!=null && usuario.getPuntosEmisionUsuario()!=null)
        {
            for (PuntoEmisionUsuario puntoEmisionUsuario : usuario.getPuntosEmisionUsuario()) 
            {
                if(puntoEmisionUsuario.getEstadoEnum().equals(GeneralEnumEstado.ACTIVO))
                {
                    listaModel.addElement(puntoEmisionUsuario);
                }
            }
        }
        
        getjListPuntoEmision().setModel(listaModel);
    }
    
    private void cargarListaCajaPermiso()
    {
        DefaultListModel listaModelCajaPermiso = new DefaultListModel();
        if(usuario != null && usuario.getCajasPermisoUsuario() != null)
        {
            for(CajaPermiso cajaPermiso : usuario.getCajasPermisoUsuario())
            {
                if(cajaPermiso.getEstadoEnum().equals(GeneralEnumEstado.ACTIVO))
                {
                    listaModelCajaPermiso.addElement(cajaPermiso);
                }
            }
        }
        getjListCajaPermiso().setModel(listaModelCajaPermiso);
    }
    
    private void cargarListaTurnos(CajaPermiso cajaPermiso)
    {
        DefaultListModel listaModelTurno = new DefaultListModel();
        if(usuario != null && cajaPermiso != null)
        {
            for(TurnoAsignado turnoAsignado : cajaPermiso.getTurnoAsignadoList())
            {
                if(turnoAsignado.getEstadoEnum().equals(GeneralEnumEstado.ACTIVO))
                {
                    listaModelTurno.addElement(turnoAsignado);
                }
            }
        }
        getjListTurnosCaja().setModel(listaModelTurno);
    }
    
    private void cargarListaPerfilesUsuario()
    {
        DefaultListModel listaModel=new DefaultListModel();
        
        if(usuario!=null && usuario.getPerfilesUsuario()!=null)
        {
            for (PerfilUsuario perfilUsuario : usuario.getPerfilesUsuario()) 
            {
                listaModel.addElement((perfilUsuario.getPerfil()!=null)?perfilUsuario.getPerfil().getNombre():"Sin perfil");
            }
        }
        
        getjListPerfiles().setModel(listaModel);
    }
    

    private void setearValoresPantalla() {
        usuario.setEmpresa(session.getEmpresa());
        usuario.setNick(getTxtUsuario().getText());
        //usuario.setClave(new String(getTxtClave().getPassword()));
        GeneralEnumEstado estadoEnum=(GeneralEnumEstado) getCmbEstado().getSelectedItem();
        usuario.setEstadoEnum(estadoEnum);
        
        //Parametro Adicional para grabar
        usuario.setParametrosComprobatesElectronicos(getTxtParametrosComprobantesElectronicos().getText());

        
        if (estadoFormulario == ESTADO_GRABAR) 
        { //Seteo la clave directo para guardar
            usuario.setClave(new String(getTxtClave().getPassword()));
        } 
        else if (estadoFormulario == ESTADO_EDITAR) {
            /*String clave=new String(getTxtClave().getPassword());
            if(!clave.equals("")) //Setear la clave solo cuando es distinta de vacio
            {
                usuario.setClave(new String(getTxtClave().getPassword()));
            }*/
        }
        EnumSiNo enumSiNo = (EnumSiNo) getjComboBoxFiltrarFacturas().getSelectedItem();
        usuario.setFiltrarFacturaEnum(enumSiNo);
        
    }

    private void valoresIniciales() {
        getCmbEstado().removeAllItems();
        getCmbEstado().addItem(GeneralEnumEstado.ACTIVO);
        getCmbEstado().addItem(GeneralEnumEstado.INACTIVO);
        UtilidadesComboBox.llenarComboBox(getjComboBoxFiltrarFacturas(), EnumSiNo.values());
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
