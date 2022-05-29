/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.controlador.componentes.ComponenteCorreoInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.ayuda.componentes.ComponenteEnvioSmsData;
import ec.com.codesoft.codefaclite.controlador.componentes.ComponenteEnvioSmsInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.DialogInterfacePanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.sms.ControladorPlantillaSms;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ValidacionCodefacAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.validacionPersonalizadaAnotacion;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.views.InterfazPostConstructPanel;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.crm.panel.ClienteForm;
import ec.com.codesoft.codefaclite.crm.reportdata.DataEjemploReporte;
import ec.com.codesoft.codefaclite.crm.test.EjemploCrm;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.EnvioMensajesCallBackInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriIdentificacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Nacionalidad;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal.TipoSucursalEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TipoEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Zona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.PlantillaSmsEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SmsServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriIdentificacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesJuridicas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author PC
 */
public class ClienteModel extends ClienteForm implements DialogInterfacePanel<Persona>, InterfazPostConstructPanel ,ComponenteEnvioSmsInterface,ComponenteCorreoInterface{

    private static final Logger LOG = Logger.getLogger(ClienteModel.class.getName());
    private static final Integer INDICE_TABLA_OBJETO=0;

    
    /**
     * Modelo para manejar las identificaciones del sri
     */
    private DefaultComboBoxModel<SriIdentificacion> modelComboIdentificacion;
    //List<SriIdentificacion> identificaciones;

    private PersonaServiceIf personaService;
    private Persona persona;
    private String razonSocial;
    //private String comboTipoCliente[] = {"CLIENTE", "SUJETO RETENIDO", "DESTINATARIO"};

    //private int opcionIdentificacion = 4;
    private PersonaEstablecimiento personaEstablecimientoEditar;
    protected OperadorNegocioEnum operadorNegocioDefault;

    public ClienteModel() {
        this.personaService = ServiceFactory.getFactory().getPersonaServiceIf();
        getjTextExtension().setText("0");
        this.razonSocial = "";
        excluirValidaciones();
        cargarTipoClientes();
        cargarDatosIniciales();
        addListenerBotones();
        addListenerTexts();
        addListenerCombos();
        addListenerTablas();
        addListenerPopUps();
        super.mapDatosIngresadosDefault.put(getjTextExtension(),"0");
    }
    
    private void addListenerPopUps()
    {
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem jMenuItemEliminar = new JMenuItem("Eliminar");
        
        jMenuItemEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = getTblEstablecimientos().getSelectedRow();
                if (filaSeleccionada >= 0) {
                    PersonaEstablecimiento establecimiento=(PersonaEstablecimiento) getTblEstablecimientos().getValueAt(filaSeleccionada,INDICE_TABLA_OBJETO);
                    establecimiento.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                    //Construir de nuevo la lista con los datos actualizados
                    construirVistaEstablecimientos();
               }
                
            }
        });
        
       jPopupMenu.add(jMenuItemEliminar);
       getTblEstablecimientos().setComponentPopupMenu(jPopupMenu);
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            if (!prevalidar()) {
                //Cancela el evento guardar porque no prevalido
                throw new ExcepcionCodefacLite("Error al prevalidar");
            }
            setearDatos();
            persona = personaService.grabarModoForzado(persona,procesarModoForzado);
            DialogoCodefac.mensaje("Datos correctos", "El cliente se guardo correctamente", DialogoCodefac.MENSAJE_CORRECTO);
        } catch (ServicioCodefacException ex) {
            //Si se puede reprocesar en modo forzado abro de nuevo la pantalla de grabar
            DialogoCodefac.mensaje("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
            if(ex.getProcesarModoForzado())
            {
                Boolean coninuar= DialogoCodefac.dialogoPregunta(new CodefacMsj("Desea procesar en MODO FORZADO ?", CodefacMsj.TipoMensajeEnum.CORRECTO));
                if(coninuar)
                {
                    procesarModoForzado=true;
                    grabar();
                    return;
                }
            }            
            throw new ExcepcionCodefacLite("Error al prevalidar");
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
            throw new ExcepcionCodefacLite("Error de conexion");
        }

    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        try {
            setearDatos();
            personaService.editarPersona(persona);
            
            DialogoCodefac.mensaje("Correcto","La persona fue editada correctamente",DialogoCodefac.MENSAJE_CORRECTO);
            
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error","Error de comunicación con el servidor",DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite(ex.getMessage());
        } catch (ServicioCodefacException ex) {
            DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
            throw new ExcepcionCodefacLite("Cancelado por error");
        }
    }
    
    private void setearDatos() {        
        persona.setNombres(getjTextNombres().getText());
        persona.setApellidos(getjTextApellidos().getText());
        persona.setRazonSocial(getjTextNombreSocial().getText());
        //persona.setNombreLegal(getTxtNombreLegal().getText());
        
        Persona.TipoIdentificacionEnum tipoIdentificacionEnum=(Persona.TipoIdentificacionEnum) getjComboIdentificacion().getSelectedItem();
        persona.setTipoIdentificacion(tipoIdentificacionEnum.getLetra());
        persona.setIdentificacion(getjTextIdentificacion().getText());
        persona.setTipCliente((String) getjComboTipoCliente().getSelectedItem());
        persona.setCorreoElectronico(getjTextCorreo().getText());
        persona.setEstado(((GeneralEnumEstado) getCmbEstado().getSelectedItem()).getEstado());
        persona.setTipo(((OperadorNegocioEnum) getCmbTipoOperador().getSelectedItem()).getLetra());
        persona.setNacionalidad(((Nacionalidad) getCmbNacionalidad().getSelectedItem()));
        persona.setSriFormaPago((SriFormaPago) getCmbFormaPagoDefecto().getSelectedItem());
        persona.setDiasCreditoCliente((Integer) getTxtDiasCredito().getValue());
        persona.setDiasCreditoProveedor((Integer)getTxtDiasCreditoProveedor().getValue());
        persona.setContactoClienteNombre(getTxtNombreContacto().getText());
        persona.setEmpresa(session.getEmpresa());
        persona.setObservaciones(getTxtObservaciones().getText());
                
        
        //Grabar la variable de obligado a llevar contabilidad
        if(getChkObligadoLlevarContabilidad().isSelected())
            persona.setObligadoLlevarContabilidad(EnumSiNo.SI.getLetra());
        else
            persona.setObligadoLlevarContabilidad(EnumSiNo.NO.getLetra());
        
        //Grabar si el contacto tiene un check box
        if(getChkContacto().isSelected())
            persona.setContactoCliente(EnumSiNo.SI.getLetra());
        else
            persona.setContactoCliente(EnumSiNo.NO.getLetra());
        
        if(!getTxtPorcentajeComision().getText().isEmpty())
        {
            persona.setContactoClientePorcentaje(new BigDecimal(getTxtPorcentajeComision().getText()));
        }
        
        /**
         * Editar los datos de la primera sucursal por defecto
         */
        PersonaEstablecimiento establecimiento=new PersonaEstablecimiento();
        if(estadoFormulario.equals(ESTADO_EDITAR))
        {
            if(persona.getEstablecimientos()!=null && persona.getEstablecimientos().size()>0)
            {
                establecimiento=persona.getEstablecimientos().get(0);                
            }
        }
        
        establecimiento=PersonaEstablecimiento.buildFromPersona(establecimiento, 
                getTxtCodigoPersonalizado().getText(),
                getTxtNombreLegal().getText(), 
                getjTextAreaDireccion().getText(), 
                getjTextReferenciaDireccion().getText(),
                getjTextExtension().getText(), 
                getjTextCelular().getText(), 
                getjTextTelefono().getText(),
                TipoSucursalEnum.MATRIZ, 
                (Zona) getCmbZona().getSelectedItem(),
                (TipoEstablecimiento)getCmbTipoCliente().getSelectedItem(),
                GeneralEnumEstado.ACTIVO
                );
        
        
        if(estadoFormulario.equals(ESTADO_GRABAR))
        {
            final String codigoDefecto="1";
            //Si existe construida y agregada previamente una version a la persona , primero limpio para luego agregar
            persona.quitarEstablecimientoPorCodigo(codigoDefecto);
                    
            establecimiento.setCodigoSucursal(codigoDefecto);
            persona.addEstablecimiento(establecimiento);        
        }
        
        
    }

    @Override
    public void eliminar() {
        Boolean confirmacion = DialogoCodefac.dialogoPregunta("Alerta", "Está seguro que desea eliminar el cliente?", DialogoCodefac.MENSAJE_ADVERTENCIA);
        if (confirmacion) {
            try {
                personaService.eliminar(persona);
                System.out.println("Se elimino correctamente");
            } catch (RemoteException ex) {
                Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
                //throw new ExcepcionCodefacLite("Cancelado por error");
            }
        }

    }

    //@Override
    public String getNombre() {
        return "Cliente";
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
    public void imprimir() {
        /*
        URL path = RecursoCodefac.JASPER_CRM.getResourceURL("reporteEjemplo.jrxml");
        System.out.println(session.getUsuario().getClave());
        Map parameters = new HashMap();
        parameters.put("nombre", "carlos");
        parameters.put("subreporte", "C:\\Users\\Carlos\\Documents\\GitHub\\codefac-lite\\workspace\\recursos\\src\\main\\resources\\reportes\\crm\\");

        List<DataEjemploReporte> data = new ArrayList<DataEjemploReporte>();
        data.add(new DataEjemploReporte("carlos", "1"));
        data.add(new DataEjemploReporte("pedro", "2"));
        ReporteCodefac.generarReporteInternalFramePlantilla(path.getPath(), parameters, data, panelPadre, "Reporte Nuevos ");*/
    }

    @Override
    public String getURLAyuda() {
        return "https://docs.google.com/document/d/e/2PACX-1vRxHiHd5vpEu1In25BKtCXigpl4m1phGAZwNR7Rh2Jm-Xqe7ffQpivlYJsMAWHFBS0BOnYxj4dpUi7H/pub?embedded=true#h.k8fq3dy15zde";
    }

    @Override
    public void actualizar() {
        cargarTipoClientes();
    }
/*
    @Override
    public void buscar() throws ExcepcionCodefacLite {
        //this.panelPadre.crearVentanaCodefac(new ClienteModel(),true);
        ClienteBusquedaDialogo clienteBusquedaDialogo = new ClienteBusquedaDialogo();
        BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);        
        buscarDialogoModel.setVisible(true);        
        Persona personaTmp = (Persona) buscarDialogoModel.getResultado();

        if (personaTmp == null) {
            throw new ExcepcionCodefacLite("Excepcion lanzada desde buscar");
        }
        
        persona=personaTmp;
        cargarDatos();
    
    }
    */

    /**
     * TODO: Ver si se puede utilizar la misma forma de validar que esta en la entidad de Persona en el metodo validar
     * @return 
     */
    @validacionPersonalizadaAnotacion(errorTitulo = "Formato de identificacion")
    public boolean validarIdentificacionSegunOpcionEstablecida() {
        /**
         * Todo: Ver si usar el mismo metodo que esta en la persona de validar cedula , pero primero tengo que setear los datos
         */
        boolean verificador = false;
        Persona.TipoIdentificacionEnum tipoIdentificacion=(Persona.TipoIdentificacionEnum) getjComboIdentificacion().getSelectedItem();
        
        if(tipoIdentificacion==null)
            return false;
        
        switch (tipoIdentificacion) {
            case RUC:
                //verificador = UtilidadesJuridicas.validarTodosRuc(getjTextIdentificacion().getText());
                verificador=true;
                break;
            case CEDULA:
                verificador=true;
                //verificador = UtilidadesJuridicas.validarCedula(getjTextIdentificacion().getText());
                break;
            case PASAPORTE:
            case SIN_DEFINIR:
                verificador = true;
                break;
            case CLIENTE_FINAL:
                verificador=(getjTextIdentificacion().getText().equals("9999999999999"))?true:false;
                break;
            default :
                verificador = false;
                break;
        }
        return verificador;
    }
    
    
    private void cargarDatos() {
        getjTextNombres().setText(persona.getNombres());
        getjTextApellidos().setText(persona.getApellidos());
        getjTextNombreSocial().setText(persona.getRazonSocial());
        getjTextIdentificacion().setText("" + persona.getIdentificacion());
       
        //persona.setNombreLegal(getjTextNombreSocial().getText());
        
        
        //getjComboIdentificacion().setSelectedIndex(comboIdentificacion(persona.getIdentificacion()));
        getjComboIdentificacion().setSelectedItem(persona.getTipoIdentificacionEnum());
        //getjComboIdentificacion().setSelectedItem(persona.getSriTipoIdentificacion());
        getjTextIdentificacion().setText(persona.getIdentificacion());
        getjComboTipoCliente().setSelectedItem(persona.getTipCliente());
        getjTextCorreo().setText(persona.getCorreoElectronico());
        getCmbEstado().setSelectedItem(GeneralEnumEstado.getEnum(persona.getEstado()));
        getCmbTipoOperador().setSelectedItem(persona.getTipoEnum());
        getCmbNacionalidad().setSelectedItem(persona.getNacionalidad());
        getCmbFormaPagoDefecto().setSelectedItem(persona.getSriFormaPago());
        getTxtNombreContacto().setText(persona.getContactoClienteNombre());
        getTxtObservaciones().setText(persona.getObservaciones());
        
        if(persona.getDiasCreditoCliente()!=null)
            getTxtDiasCredito().setValue(persona.getDiasCreditoCliente());
        
        if (persona.getDiasCreditoProveedor()!= null)
            getTxtDiasCreditoProveedor().setValue(persona.getDiasCreditoProveedor());
        
        //Seleccionar si es obligado a llevar contabilidad
        if(persona.getObligadoLlevarContabilidadEnum()!=null)
        {
            if(persona.getObligadoLlevarContabilidadEnum().equals(EnumSiNo.SI))
            {
                getChkObligadoLlevarContabilidad().setSelected(true);
            }
            else
            {
                getChkObligadoLlevarContabilidad().setSelected(false);
            }
        }
        
        
        if(persona.getContactoCliente()!=null)
        {
            if(persona.getContactoClientesEnum().equals(EnumSiNo.SI))
            {
                getChkContacto().setSelected(true);
            }
            else
            {
                getChkContacto().setSelected(false);
            }
        }
        else
        {
            getChkContacto().setSelected(false);
        }
        
        getTxtPorcentajeComision().setText((persona.getContactoClientePorcentaje()!=null)?persona.getContactoClientePorcentaje().toString():"");
        

        System.out.println("Datos cargados ");
    }

    @validacionPersonalizadaAnotacion(errorTitulo = "formato otra validacion incorrecto")
    public boolean validarOtro() {
        return true;
    }


    @Override
    public void limpiar() {
        try {
            persona = new Persona();
            /**
             * Seleccionando valores por defecto que se deben seleccionar
             * despues de limpiar
             *
             * @author carlos
             */

            SriIdentificacionServiceIf service = ServiceFactory.getFactory().getSriIdentificacionServiceIf();
            SriIdentificacion id;
            //Map<String, Object> parametros = new HashMap<String, Object>();
            //parametros.put("codigo", "05");
            SriIdentificacion identificacion = service.buscarPorCodigo("05");
            getjComboIdentificacion().setSelectedItem(identificacion);

            getjComboTipoCliente().setSelectedIndex(0);
            getCmbTipoOperador().setSelectedItem(operadorNegocioDefault);
            getjTextExtension().setText("0");
            
            //getCmbFormaPagoDefecto().setSelectedIndex(0);

            //Setear el valor por defecto
            getCmbEstado().setSelectedItem(GeneralEnumEstado.ACTIVO);
            
            getTxtPorcentajeComision().setText("0");
            getChkContacto().setSelected(false);
            getTxtDiasCredito().setValue(0);
            getTxtDiasCreditoProveedor().setValue(0);
            getTxtNombreLegal().setText("");
            this.razonSocial = "";
            limpiarCrearEstablecimiento();
            getTblEstablecimientos().setModel(new DefaultTableModel());
            
             /**
             * ========================================================
             * SELECCIONAR LA FORMA DE PAGO POR DEFECTO
             * ========================================================
             */
            SriFormaPago sriFormaPagoDefecto = ParametroUtilidades.obtenerValorBaseDatos(session.getEmpresa(), ParametroCodefac.FORMA_PAGO_POR_DEFECTO_PANTALLA_CLIENTE, new ParametroUtilidades.ComparadorInterface() {
                @Override
                public Object consultarParametro(String nombreParametro) {
                    try {
                        return ServiceFactory.getFactory().getSriFormaPagoServiceIf().buscarPorId(Long.parseLong(nombreParametro));
                    } catch (RemoteException ex) {
                        Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return null;
                }
            });

            if (sriFormaPagoDefecto != null) {
                getCmbFormaPagoDefecto().setSelectedItem(sriFormaPagoDefecto);
            }
            
            getCmbZona().setSelectedItem(null);
            getCmbTipoCliente().setSelectedItem(null);
            
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        getCmbNacionalidad().setSelectedIndex(52);

    }

    /**
     * Cargar los tipos de clientes de la base de datos
     */
    private void cargarTipoClientes() {
        /**
         * Cargar los valores por defecto de las identificaciones
         */
        //SriServiceIf servicioSri = ServiceFactory.getFactory().getSriServiceIf();
        //identificaciones = servicioSri.obtenerIdentificaciones(SriIdentificacion.CLIENTE);
        getjComboIdentificacion().removeAllItems();
        for (Persona.TipoIdentificacionEnum tipoIdentificacion : Persona.TipoIdentificacionEnum.values()) {
            getjComboIdentificacion().addItem(tipoIdentificacion);
        }
    }

    @Override
    public Persona getResult() throws ExcepcionCodefacLite {

        try {
            grabar();
            return persona;
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
    }

    private void cargarDatosIniciales() {
        getPnlSms().setControlador(this);
        getPnlCorreo().setCorreoInterface(this);
        operadorNegocioDefault=OperadorNegocioEnum.CLIENTE;
        /**
         * Cargando los estados por defecto
         */
        getCmbEstado().removeAllItems();
        for (GeneralEnumEstado enumerador : GeneralEnumEstado.values()) {
            getCmbEstado().addItem(enumerador);
        }
        
        getCmbTipoEstablecimiento().removeAllItems();
        for (TipoSucursalEnum tipoSucursal : Sucursal.TipoSucursalEnum.values()) {
            getCmbTipoEstablecimiento().addItem(tipoSucursal);
        }
        
        try {
            
            List<Zona> zonaList=ServiceFactory.getFactory().getZonaServiceIf().obtenerActivos();
            UtilidadesComboBox.llenarComboBox(getCmbZonaOficina(),zonaList);
            UtilidadesComboBox.llenarComboBox(getCmbZona(),zonaList);
            
            //TODO: Editar para solo cargar los que estan clasificados como tipo cliente
            List<TipoEstablecimiento> tipoEstablecimientoList=ServiceFactory.getFactory().getTipoEstablecimientoServiceIf().obtenerActivos(OperadorNegocioEnum.CLIENTE);
            UtilidadesComboBox.llenarComboBox(getCmbTipoCliente(),tipoEstablecimientoList);
            UtilidadesComboBox.llenarComboBox(getCmbTipoClienteOficina(),tipoEstablecimientoList);
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }

    private boolean prevalidar() {
        if (getCmbEstado().getSelectedItem().equals(GeneralEnumEstado.ELIMINADO)) {
            DialogoCodefac.mensaje("Advertencia", "Si desea eliminar el cliente seleccione el boton de eliminar,seleccione otro estado", DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }

        return true;
    }

    @Override
    public void iniciar() {
        try {
            /**
             * Cargar los datos por defecto de los tipos de operadores
             */
            getCmbTipoOperador().removeAllItems();
            OperadorNegocioEnum list[] = OperadorNegocioEnum.values();
            for (OperadorNegocioEnum operadorNegocioEnum : list) {
                getCmbTipoOperador().addItem(operadorNegocioEnum);
            }
            
            try {
                List<Nacionalidad> nacion = ServiceFactory.getFactory().getNacionalidadServiceIf().obtenerTodos();
                getCmbNacionalidad().removeAllItems();
                for (Nacionalidad n : nacion) {
                    getCmbNacionalidad().addItem(n);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //Cargar las formas de pago vigentes en el SRI
            List<SriFormaPago> formasPago=ServiceFactory.getFactory().getSriServiceIf().obtenerFormasPagoActivo();
            getCmbFormaPagoDefecto().removeAllItems();
            for (SriFormaPago formaPago : formasPago)
            {
                getCmbFormaPagoDefecto().addItem(formaPago);
            }
            
            ParametroUtilidades.obtenerValorParametro(session.getEmpresa(),ParametroCodefac.FORMA_PAGO_POR_DEFECTO_PANTALLA_CLIENTE);
            
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void nuevo() {
        
    }

    public void addListenerCombos() {
        
         
        getjComboIdentificacion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Persona.TipoIdentificacionEnum tipoIdentificacionEnum=(Persona.TipoIdentificacionEnum) getjComboIdentificacion().getSelectedItem();
                //opcionIdentificacion = Integer.parseInt(((SriIdentificacion) getjComboIdentificacion().getSelectedItem()).getCodigo());
                
                //Validario comonente cuando sea diferente de vacio dependiendo la opcion de identificacion
                if (!getjTextIdentificacion().getText().equals("")) {
                    panelPadre.validarPorGrupo(ValidacionCodefacAnotacion.GRUPO_FORMULARIO, NOMBRE_VALIDADOR_IDENTIFICACION);
                }
                
                // Verifico que si el codigo es igual a la cedula desahabilito esos 2 datos
                if(tipoIdentificacionEnum!=null && tipoIdentificacionEnum.equals(tipoIdentificacionEnum.CEDULA))
                {
                    getTxtNombreLegal().setText("");
                    getTxtNombreLegal().setEnabled(false);
                    getjTextNombreSocial().setEnabled(false);
                }
                else //Verifico si es cualquier otro dato solicito los 2 campos de nombre legal y razon social
                {
                    getTxtNombreLegal().setEnabled(true);
                    getjTextNombreSocial().setEnabled(true);
                }
            }
        });
        
        getCmbTipoOperador().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OperadorNegocioEnum enumerador=(OperadorNegocioEnum) getCmbTipoOperador().getSelectedItem();
                
                if(enumerador!=null)
                {
                    if(enumerador.equals(OperadorNegocioEnum.CLIENTE))
                    {
                        getLblOligadoLlevarContabilidad().setVisible(false);
                        getChkObligadoLlevarContabilidad().setVisible(false);
                    }
                    else
                    {
                        getLblOligadoLlevarContabilidad().setVisible(true);
                        getChkObligadoLlevarContabilidad().setVisible(true);                    
                    }
                }
            }
        });

    }
    
    public boolean isNumeric(String cadena) {
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }

    public void addListenerTexts() {
        
        getjTextIdentificacion().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                listenerBuscarDatoIngresado();
            }
        });
        
        
        getjTextNombres().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                construirNombreSocial();
            }
        });
        
        getjTextApellidos().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                construirNombreSocial();
            }
        });
                
        getjTextNombres().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {}

            @Override
            public void focusLost(FocusEvent evt) {
                construirNombreSocial();
            }
        });

        getjTextApellidos().addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent evt) {}

            @Override
            public void focusLost(FocusEvent evt) {
                construirNombreSocial();
            }
        });
    }
    
    private void seleccionarTipoIdentificacion() {
        if (getjTextIdentificacion().getText().length() == 10) {
            getjComboIdentificacion().setSelectedItem(Persona.TipoIdentificacionEnum.CEDULA);
        } else if (getjTextIdentificacion().getText().length() == 13) {
            getjComboIdentificacion().setSelectedItem(Persona.TipoIdentificacionEnum.RUC);
        } else {
            //getjComboIdentificacion().setSelectedItem(Persona.TipoIdentificacionEnum.PASAPORTE);
        }

    }

    public void listenerBuscarDatoIngresado()
    {
        
        //Seleccionar el tipo de identificacion de forma automatica
        seleccionarTipoIdentificacion();
                
        //Este metodo solo funciona si el estado es grabar
        if(estadoFormulario.equals(ESTADO_GRABAR))
        {
            //Si el dato ingresado es vacio no hago validaciones
            if (getjTextIdentificacion().getText().trim().isEmpty()) 
            {
                return;
            }
            
            try {
                
                Persona persona=ServiceFactory.getFactory().getPersonaServiceIf().buscarPorIdentificacion(getjTextIdentificacion().getText(),session.getEmpresa());
                //Si no esta ingresado ninguna persona continuar con el proceso normal
                if(persona==null)
                {
                    return ;
                }
                
                if(!persona.getEstadoEnum().equals(GeneralEnumEstado.ACTIVO))
                {
                    DialogoCodefac.mensaje("Advertencia", "En el historial ya existen datos ingresados.\nEdite los datos que requiere y presione guardar.", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    cargarDatosParaEditar(persona);
                    getCmbTipoOperador().setSelectedItem(operadorNegocioDefault); //Seteo el operador de negocio que vaya a trabajar
                    getCmbEstado().setSelectedItem(GeneralEnumEstado.ACTIVO);
                    return;
                }

                
                if (persona.getTipoEnum().equals(operadorNegocioDefault) || persona.getTipoEnum().equals(OperadorNegocioEnum.AMBOS)) 
                {
                    DialogoCodefac.mensaje("Advertencia", "Ya existe ingresado un dato con esa identificación", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    return;
                }
                
                
                if(persona.getTipoEnum().equals(OperadorNegocioEnum.CLIENTE) && operadorNegocioDefault.equals(OperadorNegocioEnum.PROVEEDOR))
                {
                    DialogoCodefac.mensaje("Advertencia","El registro ya se encuentra ingresada como cliente.\nEdite los datos que requiere y presione guardar.", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    cargarDatosParaEditar(persona);
                    
                }
                
                if(persona.getTipoEnum().equals(OperadorNegocioEnum.PROVEEDOR) && operadorNegocioDefault.equals(OperadorNegocioEnum.CLIENTE))
                {
                    DialogoCodefac.mensaje("Advertencia","El registro ya se encuentra ingresada como proveedor.\nEdite los datos que requiere y presione guardar.", DialogoCodefac.MENSAJE_ADVERTENCIA);                    
                    cargarDatosParaEditar(persona);
                }
                
                
                
                
            } catch (RemoteException ex) {
                Logger.getLogger(ClienteModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
   
    
    //TODO: CONSUMIR ESTE DATO DESDE LA PANTALLA GENERAL PARA TENER UN METODO GENERAL QUE HAGA ESTA FUNCION
    private void cargarDatosParaEditar(Persona entidad)
    {
        
        panelPadre.cambiarEstadoFormularioEditar(this);
        cargarDatosPantalla(entidad);
        getCmbTipoOperador().setSelectedItem(OperadorNegocioEnum.AMBOS);
        
    }
    
    private void construirNombreSocial()
    {
        getjTextNombreSocial().setText(Persona.construirRazonSocial(getjTextNombres().getText(), getjTextApellidos().getText()));
        //getjTextNombreSocial().setText(getjTextApellidos().getText() + " " + getjTextNombres().getText());
    }

    @Override
    public List<String> getPerfilesPermisos() {
        List<String> permisos = new ArrayList<String>();
        permisos.add(Perfil.PERFIl_ADMINISTRADOR);
        permisos.add(Perfil.PERFIl_OPERADOR);
        return permisos;
    }

    @Override
    public void postConstructorExterno(Object[] parametros) {        
        
        //Cargar el tipo de operador de negocio : cliente , proveedor, ambos          
        OperadorNegocioEnum operadorNegocioEnum=(OperadorNegocioEnum) parametros[0];
        operadorNegocioDefault=operadorNegocioEnum;
        getCmbTipoOperador().setSelectedItem(operadorNegocioEnum);
        
        String identificacion=(String) parametros[1];
        getjTextIdentificacion().setText(identificacion);
        
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        ClienteEstablecimientoBusquedaDialogo clienteBusquedaDialogo = new ClienteEstablecimientoBusquedaDialogo(session);
        //BuscarDialogoModel buscarDialogoModel = new BuscarDialogoModel(clienteBusquedaDialogo);
        return clienteBusquedaDialogo;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        persona=((PersonaEstablecimiento) entidad).getPersona();
        cargarDatos();
        cargarEstablecimientoPorDefecto();
    }

    private void addListenerBotones() {
        getBtnAgregarEstablecimiento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEstablecimiento();
            }
        });
        
        getBtnEditarEstablecimiento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                editarEstablecimiento();
                getBtnAgregarEstablecimiento().setEnabled(true);
                getBtnEditarEstablecimiento().setEnabled(false);
                cargarEstablecimientoPorDefecto();
            }
        });
    }
    
    private void editarEstablecimiento() {
        if (validarAgregarEstablecimiento()) {
            crearEstablecimiento(personaEstablecimientoEditar);
            construirVistaEstablecimientos();
            limpiarCrearEstablecimiento();

        }
    }
    
    private void agregarEstablecimiento()
    {
        if(validarAgregarEstablecimiento())
        {
            persona.addEstablecimiento(crearEstablecimiento(null));
            construirVistaEstablecimientos();
            limpiarCrearEstablecimiento();
            
        }
    }
    
    private PersonaEstablecimiento crearEstablecimiento(PersonaEstablecimiento personaEstablecimiento) {
        
        if(personaEstablecimiento==null)
        {
            personaEstablecimiento = new PersonaEstablecimiento();
            personaEstablecimiento.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        }        
        
        //personaEstablecimiento.setCodigoPersonalizado(gettxtco);
        personaEstablecimiento.setCodigoPersonalizado(getTxtCodigoPersonalizadoOficina().getText());
        personaEstablecimiento.setNombreComercial(getTxtNombreLegalEstablecimiento().getText());
        personaEstablecimiento.setCodigoSucursal(getTxtCodigoEstablecimiento().getValue().toString());
        //personaEstablecimiento.setLatitud(new BigDecimal(getTxtLatitud().getText()));
        //personaEstablecimiento.setLatitud(new BigDecimal(getTxtLongitud().getText()));
        
        personaEstablecimiento.setCorreoElectronico(""); //implementar de forma posterior
        personaEstablecimiento.setDireccion(getjTextAreaDireccionEstablecimiento().getText());
        personaEstablecimiento.setReferenciaDireccion(getjTextReferenciaDireccionEstablecimiento().getText());
        personaEstablecimiento.setExtensionTelefono(getjTextExtensionEstablecimiento().getText());
        personaEstablecimiento.setPersona(persona);
        personaEstablecimiento.setTelefonoCelular(getjTextCelularEstablecimiento().getText());
        personaEstablecimiento.setTelefonoConvencional(getjTextTelefonoEstablecimiento().getText());
        TipoSucursalEnum tipoSucursalEnum = (TipoSucursalEnum) getCmbTipoEstablecimiento().getSelectedItem();
        Zona zona=(Zona) getCmbZonaOficina().getSelectedItem();
        
        personaEstablecimiento.setTipoEstablecimiento((TipoEstablecimiento) getCmbTipoClienteOficina().getSelectedItem());
        personaEstablecimiento.setZona(zona);
        personaEstablecimiento.setTipoSucursal(tipoSucursalEnum.getCodigo());
        return personaEstablecimiento;
    }
    
    private void construirVistaEstablecimientos()
    {
        String[] titulos={"","Nombre Establecimiento","dirección","cod Sucursal","tipo"};
        UtilidadesTablas.crearModeloTabla(titulos,new Class[]{Object.class,String.class,String.class,String.class,String.class});
        DefaultTableModel modeloTabla=new DefaultTableModel(titulos,0);
        
        if(persona.getEstablecimientos()!=null)
        {
            for (PersonaEstablecimiento establecimiento : persona.getEstablecimientosActivos()) {
                
                modeloTabla.addRow(new Object[]{
                    establecimiento,
                    establecimiento.getNombreComercial(),
                    establecimiento.getDireccion(),
                    establecimiento.getCodigoSucursal(),
                    establecimiento.getTipoSucursalEnum().getNombre()});
            }
        }
                
        getTblEstablecimientos().setModel(modeloTabla);
        UtilidadesTablas.ocultarColumna(getTblEstablecimientos(),0);
    }

    //Lista de componentes que se deben excluir de las validaciona automaticas
    private void excluirValidaciones() {
       listaExclusionComponentes.add(getPnlSms().getTxtMensajeTexto());
    }
    
    @Override
    public boolean getValidacionEnvioSms() {
        if (!estadoFormulario.equals(ESTADO_EDITAR)) {
            DialogoCodefac.mensaje("Error", "Por favor seleccione un cliente para enviar el mensaje", DialogoCodefac.MENSAJE_INCORRECTO);
            return false;
        }
        return true;
    }

    public Map<PlantillaSmsEnum.EtiquetaEnum, String> getPlantillaTags() {
        Map<PlantillaSmsEnum.EtiquetaEnum, String> mapParametros = new HashMap<PlantillaSmsEnum.EtiquetaEnum, String>();
        mapParametros.put(PlantillaSmsEnum.EtiquetaEnum.EMPRESA, session.getEmpresa().getNombreLegal());
        return mapParametros;
    }
    
    @Override
    public List<ComponenteEnvioSmsData> getDataSms() {                
        ComponenteEnvioSmsData componenteEnvioSmsData=new ComponenteEnvioSmsData(persona.getEstablecimientos().get(0).getTelefonoCelular(),getPlantillaTags());
        List<ComponenteEnvioSmsData> dataList=new ArrayList<ComponenteEnvioSmsData>();
        dataList.add(componenteEnvioSmsData);
        return dataList;
    }

    @Override
    public VentanaEnum getVentanaEnum() {
        return VentanaEnum.CLIENTE;
    }

    @Override
    public EnvioMensajesCallBackInterface getInterfaceCallback() {
        return null;
    }

    @Override
    public String getCorreo() {
        String correo="";
        if(persona!=null)
            correo=persona.getCorreoElectronico();
                    
        return correo;
    }
    
    private void limpiarCrearEstablecimiento()
    {
        getTxtCodigoPersonalizadoOficina().setText("");
        getTxtNombreLegalEstablecimiento().setText("");
        getTxtCodigoPersonalizado().setText("");
        getjTextAreaDireccionEstablecimiento().setText("");
        getjTextReferenciaDireccionEstablecimiento().setText("");
        getjTextTelefonoEstablecimiento().setText("");
        getjTextExtensionEstablecimiento().setText("");
        getjTextCelularEstablecimiento().setText("");
        getTxtCodigoEstablecimiento().setValue(1);
        getCmbTipoEstablecimiento().setSelectedIndex(0);
        getCmbZonaOficina().setSelectedItem(null);
        getCmbTipoClienteOficina().setSelectedItem(null);
        
        getTxtLatitud().setText(BigDecimal.ZERO.toString());
        getTxtLongitud().setText(BigDecimal.ZERO.toString());
        
    }

    private boolean validarAgregarEstablecimiento() {
        if(getTxtNombreLegalEstablecimiento().getText().isEmpty())
        {
            DialogoCodefac.mensaje("Campo Requerido","Campo de Nombre Legal requerido",DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        
        if(getjTextAreaDireccionEstablecimiento().getText().isEmpty())
        {
            DialogoCodefac.mensaje("Campo Requerido","Campo de Dirección requerido",DialogoCodefac.MENSAJE_ADVERTENCIA);
            return false;
        }
        
        return true;
    }
    
        
    private void cargarEstablecimientoPorDefecto() {
        PersonaEstablecimiento establecimiento=persona.getEstablecimientos().get(0);
        if(establecimiento!=null)
        {
            getTxtNombreLegal().setText(establecimiento.getNombreComercial());
            getTxtCodigoPersonalizado().setText(establecimiento.getCodigoPersonalizado());
            getjTextAreaDireccion().setText(establecimiento.getDireccion());
            getjTextReferenciaDireccion().setText(establecimiento.getReferenciaDireccion());
            getjTextTelefono().setText(establecimiento.getTelefonoConvencional());
            getjTextExtension().setText(establecimiento.getExtensionTelefono());
            getjTextCelular().setText(establecimiento.getTelefonoCelular());
            getCmbZona().setSelectedItem(establecimiento.getZona());
            getCmbTipoCliente().setSelectedItem(establecimiento.getTipoEstablecimiento());
            construirVistaEstablecimientos();
        }
    }

    private void addListenerTablas() {
        getTblEstablecimientos().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada=getTblEstablecimientos().getSelectedRow();
                if(filaSeleccionada>=0)
                {
                    cargarDatosEstablecimiento(filaSeleccionada);
                    getBtnAgregarEstablecimiento().setEnabled(false);
                    getBtnEditarEstablecimiento().setEnabled(true);
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
    }
    
    private void cargarDatosEstablecimiento(int filaSeleccionada) {
        personaEstablecimientoEditar= persona.getEstablecimientos().get(filaSeleccionada);
        
        getTxtCodigoPersonalizadoOficina().setText(personaEstablecimientoEditar.getCodigoPersonalizado());
        getTxtNombreLegalEstablecimiento().setText(personaEstablecimientoEditar.getNombreComercial());
        String codigoEstablecimiento=(personaEstablecimientoEditar.getCodigoSucursal()!=null)?personaEstablecimientoEditar.getCodigoSucursal():"1";
        getTxtCodigoEstablecimiento().setValue(new Integer(codigoEstablecimiento));
        getTxtLatitud().setText((personaEstablecimientoEditar.getLatitud()!=null)?personaEstablecimientoEditar.getLatitud().toString():"0");
        getTxtLongitud().setText((personaEstablecimientoEditar.getLongitud()!=null)?personaEstablecimientoEditar.getLongitud().toString():"0");
        
        
        getTxtCodigoPersonalizado().setText(personaEstablecimientoEditar.getCodigoPersonalizado());
        getjTextAreaDireccionEstablecimiento().setText(personaEstablecimientoEditar.getDireccion());
        getjTextReferenciaDireccionEstablecimiento().setText(personaEstablecimientoEditar.getReferenciaDireccion());
        getjTextExtensionEstablecimiento().setText(personaEstablecimientoEditar.getExtensionTelefono());
        getjTextCelularEstablecimiento().setText(personaEstablecimientoEditar.getTelefonoCelular());
        getjTextTelefonoEstablecimiento().setText(personaEstablecimientoEditar.getTelefonoConvencional());
        getCmbTipoEstablecimiento().setSelectedItem(personaEstablecimientoEditar.getTipoSucursalEnum());
        getCmbZonaOficina().setSelectedItem(personaEstablecimientoEditar.getZona());
        getCmbTipoClienteOficina().setSelectedItem(personaEstablecimientoEditar.getTipoEstablecimiento());
        //PersonaEstablecimiento personaEstablecimiento = new PersonaEstablecimiento();
        //personaEstablecimiento.setPersona(persona);
        //personaEstablecimiento.setTelefonoConvencional(getjTextTelefono().getText());
        //TipoSucursalEnum tipoSucursalEnum = (TipoSucursalEnum) getCmbTipoEstablecimiento().getSelectedItem();
        //personaEstablecimiento.setTipoSucursalEnum(TipoSucursalEnum.MATRIZ);
        
    }

    @Override
    public Empresa getEmpresa() {
        return session.getEmpresa();
    }
    

}
