/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.crm;

import ec.com.codesoft.codefaclite.codefacweb.core.DialogoWeb;
import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.sistema.UtilidadesWeb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.MensajeMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.UtilidadWeb;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ClienteEstablecimientoBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.crm.model.ClienteModel;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Nacionalidad;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EstadoFormEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import java.math.BigDecimal;
import java.util.Map;
import javax.faces.context.FacesContext;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class ClienteMb extends GeneralAbstractMb implements DialogoWeb<Persona>, Serializable {

    private static final String TITULO_CLIENTE="Cliente";  
    private static final String TITULO_PROVEEDOR="Proveedor";
    private Persona cliente;
    private List<Nacionalidad> nacionalidadesList;
    private GeneralEnumEstado[] estadosEnumList;
    private String tiposClientes[];
    private OperadorNegocioEnum operadoresNegocio[];
    private List<SriFormaPago> sriFormaPagoList;


    private Nacionalidad nacionalidadSeleccionada;
    private GeneralEnumEstado estadoSeleccionada;
    private OperadorNegocioEnum operadorNegocioSeleccionado;
    private SriFormaPago sriFormaPagoSeleccionada;
    private String tituloPagina;   

    private Boolean identificacionPasaporte;
    private PersonaEstablecimiento establecimientoDefecto;
    
    /**
     * Variable por el momento para poder almacenar la referencia del objeto a retornar cuando es dialogo para ver como se
     * puede programar de forma mas elegante de forma predeterminada;
     */
    private Persona personaReturnDialog=null;

    @PostConstruct
    private void init() { 
        
        if(tituloPagina==null)
        {
            //UtilidadesWeb.buscarParametroPeticion("tipo");        
            String titulo=UtilidadesWeb.buscarParametroPeticion("tipo");
            if(titulo==null)
            {
                tituloPagina="Error Sin Definir Cliente o Proveedor";
            }
            else
            {
                if(titulo.equals("cliente"))
                {
                    tituloPagina=TITULO_CLIENTE;
                }else if(titulo.equals("proveedor"))
                {
                    tituloPagina=TITULO_PROVEEDOR;
                }
            }
        }
        
        cliente = new Persona();
        cargarListas();
        cargarDatosDefecto();

    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        init();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        setearDatos(false);
        if (validarDatosVista()) {
            try {
                cliente=ServiceFactory.getFactory().getPersonaServiceIf().grabar(cliente);
                personaReturnDialog=cliente;
                //mostrarDialogoResultado(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
                MensajeMb.mensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
                nuevo();

            } catch (ServicioCodefacException ex) {
                MensajeMb.mostrarMensajeDialogo("Error", ex.getMessage(), FacesMessage.SEVERITY_ERROR);
                Logger.getLogger(ClienteMb.class.getName()).log(Level.SEVERE, null, ex);
                throw new ExcepcionCodefacLite(ex.getMessage());
            } catch (RemoteException ex) {
                Logger.getLogger(ClienteMb.class.getName()).log(Level.SEVERE, null, ex);
                throw new ExcepcionCodefacLite(ex.getMessage());
            }
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        setearDatos(true);
        if (validarDatosVista()) {
            try {
                ServiceFactory.getFactory().getPersonaServiceIf().editar(cliente);
                MensajeMb.mensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
                nuevo();

            } catch (ServicioCodefacException ex) {
                MensajeMb.mensaje(new CodefacMsj("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO));
                Logger.getLogger(ClienteMb.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(ClienteMb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, UnsupportedOperationException {

    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, UnsupportedOperationException {

    }

    @Override
    public void buscar() throws ExcepcionCodefacLite, UnsupportedOperationException {

    }

    

    @Override
    public String titulo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        return tituloPagina;
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        return new ClienteEstablecimientoBusquedaDialogo(sessionMb.getSession());
    }

    public Persona getCliente() {
        return cliente;
    }

    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }

    /* private void setearDatosAdicionales() {
        this.cliente.setTipoIdentificacionEnum(identificacionSeleccionada);
        this.cliente.setNacionalidad(nacionalidadSeleccionada);
        this.cliente.setEstadoEnum(estadoSeleccionada);
        ClienteModel.crearEstablecimiento(EstadoFormEnum.GRABAR, cliente, interfaceVista);
    }*/
    public List<Nacionalidad> getNacionalidadesList() {
        return nacionalidadesList;
    }

    public void setNacionalidadesList(List<Nacionalidad> nacionalidadesList) {
        this.nacionalidadesList = nacionalidadesList;
    }

    public GeneralEnumEstado[] getEstadosEnumList() {
        return estadosEnumList;
    }

    public void setEstadosEnumList(GeneralEnumEstado[] estadosEnumList) {
        this.estadosEnumList = estadosEnumList;
    }

    public Nacionalidad getNacionalidadSeleccionada() {
        return nacionalidadSeleccionada;
    }

    public void setNacionalidadSeleccionada(Nacionalidad nacionalidadSeleccionada) {
        this.nacionalidadSeleccionada = nacionalidadSeleccionada;
    }

    public GeneralEnumEstado getEstadoSeleccionada() {
        return estadoSeleccionada;
    }

    public void setEstadoSeleccionada(GeneralEnumEstado estadoSeleccionada) {
        this.estadoSeleccionada = estadoSeleccionada;
    }

    private void cargarListas() {
        try {
            //identificacionesEnumList = Persona.TipoIdentificacionEnum.values();
            tiposClientes = Persona.tiposClientes;
            nacionalidadesList = ServiceFactory.getFactory().getNacionalidadServiceIf().obtenerTodos();
            estadosEnumList = GeneralEnumEstado.values();
            operadoresNegocio = OperadorNegocioEnum.values();
            sriFormaPagoList = ServiceFactory.getFactory().getSriServiceIf().obtenerFormasPagoActivo();
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteMb.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setearDatos(Boolean editar) {
        cliente.setEmpresa(sessionMb.getSession().getEmpresa());
        cliente.setTipoIdentificacionEnum(obtenerTipoIdentificacion());
        cliente.setNacionalidad(nacionalidadSeleccionada);
        cliente.setEstadoEnum(estadoSeleccionada);
        cliente.setSriFormaPago(sriFormaPagoSeleccionada);
        cliente.setTipoEnum(operadorNegocioSeleccionado);
        cliente.setEmpresa(sessionMb.getSession().getEmpresa());
        
        if(!editar) //Cuando se va a grabar
        {
            establecimientoDefecto.setEstadoEnum(GeneralEnumEstado.ACTIVO);
            establecimientoDefecto.setCodigoSucursal("1");
            establecimientoDefecto.setTipoSucursalEnum(Sucursal.TipoSucursalEnum.MATRIZ);
            cliente.addEstablecimiento(establecimientoDefecto);      
        }
        

    }

    private Persona.TipoIdentificacionEnum obtenerTipoIdentificacion() {
        if (identificacionPasaporte) {
            return Persona.TipoIdentificacionEnum.PASAPORTE;
        } else {
            if (cliente.getIdentificacion().length() == 13) //Tamanio del ruc , ver si se crea una variable global para este valor
            {
                return Persona.TipoIdentificacionEnum.RUC;
            } else //Caso contrario asumo que es cedula
            {
                return Persona.TipoIdentificacionEnum.CEDULA;   
            }
        }
    }
    
    public void abrirGoogleMapsUbicacion()
    {
        UtilidadesWeb.redirigirPaginaExterna(establecimientoDefecto.getUrlGoogleMaps());      
    }

    private boolean validarDatosVista() {
        return true; 
    }

    public Boolean getIdentificacionPasaporte() {
        return identificacionPasaporte;
    }

    public void setIdentificacionPasaporte(Boolean identificacionPasaporte) {
        this.identificacionPasaporte = identificacionPasaporte;
    }

    public String[] getTiposClientes() {
        return tiposClientes;
    }

    public void setTiposClientes(String[] tiposClientes) {
        this.tiposClientes = tiposClientes;
    }

    public OperadorNegocioEnum[] getOperadoresNegocio() {
        return operadoresNegocio;
    }

    public void setOperadoresNegocio(OperadorNegocioEnum[] operadoresNegocio) {
        this.operadoresNegocio = operadoresNegocio;
    }

    public OperadorNegocioEnum getOperadorNegocioSeleccionado() {
        return operadorNegocioSeleccionado;
    }

    public void setOperadorNegocioSeleccionado(OperadorNegocioEnum operadorNegocioSeleccionado) {
        this.operadorNegocioSeleccionado = operadorNegocioSeleccionado;
    }

    public List<SriFormaPago> getSriFormaPagoList() {
        return sriFormaPagoList;
    }

    public void setSriFormaPagoList(List<SriFormaPago> sriFormaPagoList) {
        this.sriFormaPagoList = sriFormaPagoList;
    }

    public SriFormaPago getSriFormaPagoSeleccionada() {
        return sriFormaPagoSeleccionada;
    }

    public void setSriFormaPagoSeleccionada(SriFormaPago sriFormaPagoSeleccionada) {
        this.sriFormaPagoSeleccionada = sriFormaPagoSeleccionada;
    }

    private void cargarDatosDefecto() {
        establecimientoDefecto=new PersonaEstablecimiento();
        cliente.setDiasCreditoCliente(0);

        try {
            nacionalidadSeleccionada = ServiceFactory.getFactory().getNacionalidadServiceIf().obtenerDefaultEcuador();
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ClienteMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteMb.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(tituloPagina.equals(TITULO_PROVEEDOR))
        {
            operadorNegocioSeleccionado=OperadorNegocioEnum.PROVEEDOR;
        }
    }
    
    public void ejemplo()
    {
    
    }

    public void eventoNombreKeyUp() {
        System.out.println("evento razon social");
        System.out.println(cliente.getNombres() + " " + cliente.getApellidos());
        cliente.contruirRazonSocialConNombreYApellidos();
        System.out.println(cliente.getRazonSocial());
    }

    public Persona getResultDialogo() {
        return personaReturnDialog;
    }

    public String getTituloPagina() {
        return tituloPagina;
    }

    public void setTituloPagina(String tituloPagina) {
        this.tituloPagina = tituloPagina;
    }

    public PersonaEstablecimiento getEstablecimientoDefecto() {
        return establecimientoDefecto;
    }

    public void setEstablecimientoDefecto(PersonaEstablecimiento establecimientoDefecto) {
        this.establecimientoDefecto = establecimientoDefecto;
    }

    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void cargarDatosPantalla(Object entidad) {
        cliente = ((PersonaEstablecimiento) (entidad)).getPersona();

        ///Setear si es pasaporte o no
        identificacionPasaporte = false;
        if (cliente.getTipoIdentificacionEnum().equals(Persona.TipoIdentificacionEnum.PASAPORTE)) {
            identificacionPasaporte = true;
        }

        //Setear la nacionalidad
        nacionalidadSeleccionada = cliente.getNacionalidad();

        //Setear el estado
        estadoSeleccionada = cliente.getEstadoEnum();

        //Setear el tipo de cliente
        operadorNegocioSeleccionado = cliente.getTipoEnum();

        //Setear al forma de defecto
        sriFormaPagoSeleccionada = cliente.getSriFormaPago();
        
        //Setear el primer establecimiento para editar
        if(cliente.getEstablecimientos()!=null && cliente.getEstablecimientos().size()>0)
            establecimientoDefecto=cliente.getEstablecimientos().get(0);
        else
            establecimientoDefecto=new PersonaEstablecimiento();
    }

    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void remoteAction() 
    {        
        String longitud = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get("longitud");   
        
        String latitud = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get("latitud");
        
        establecimientoDefecto.setLatitud(new BigDecimal(latitud));  
        establecimientoDefecto.setLongitud(new BigDecimal(longitud));
        
        System.out.println("Latitud: "+latitud+", Longitud: "+longitud); 
                
    }
    
    

}
