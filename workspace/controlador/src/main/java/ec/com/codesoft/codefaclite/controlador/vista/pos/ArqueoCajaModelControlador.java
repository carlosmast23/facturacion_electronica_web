/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.pos;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.ArqueoCajaBusquedaDialogo;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.ArqueoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ArqueoCajaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @auhor
 */
public class ArqueoCajaModelControlador extends ModelControladorAbstract<ArqueoCajaModelControlador.CommonIf, ArqueoCajaModelControlador.SwingIf, ArqueoCajaModelControlador.WebIf> implements VistaCodefacIf
{
    private ArqueoCaja arqueoCaja;
    private List<ArqueoCajaEnum> estadosList;
        

    public ArqueoCajaModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, ArqueoCajaModelControlador.CommonIf interfaz, TipoVista tipoVista) 
    {
        super(mensajeVista, session, interfaz, tipoVista);
    }
     /**
    * Metodo iniciar
    * @throws java.rmi.RemoteException
    * @throws ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException
    */ 
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        
        arqueoCaja=new ArqueoCaja();
        arqueoCaja.setValorFisico(BigDecimal.ZERO);
        arqueoCaja.setValorTeorico("Cero");
        arqueoCaja.setEstadoEnum(ArqueoCajaEnum.CUADRO);        
        
        
        long date = new java.util.Date().getTime();
        getInterfaz().setFechaRevision(new Date(date));      
        getInterfaz().setHoraRevision(new Date(date));
        
        estadosList = UtilidadesLista.arrayToList(ArqueoCajaEnum.values());
    }

    public void nuevo() throws ExcepcionCodefacLite, RemoteException{
        iniciar();
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {

        try
        {       
            //Datos
            setearDatos();
            //Grabar
            ServiceFactory.getFactory().getArqueoCajaServiceIf().grabar(getArqueoCaja());
            //Mensaje
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
            
        }catch(ServicioCodefacException e)
        {
            mostrarMensaje(new CodefacMsj("Error", e.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO));
            try {
                throw e;
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(CajaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
        
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        try {
            //Datos
            setearDatos();
            //Editar
            ServiceFactory.getFactory().getArqueoCajaServiceIf().editar(getArqueoCaja());
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ArqueoCajaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        try {
            Boolean respuesta = dialogoPregunta(MensajeCodefacSistema.Preguntas.ELIMINAR_REGISTRO);
            if(!respuesta){
                throw new ServicioCodefacException("Error eliminando Arqueo Caja");
            }
            ServiceFactory.getFactory().getArqueoCajaServiceIf().eliminar(getArqueoCaja());
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ArqueoCajaModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        ArqueoCajaBusquedaDialogo arqueoCajaBusquedaDialogo = new ArqueoCajaBusquedaDialogo();
        return arqueoCajaBusquedaDialogo;
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        ArqueoCaja arqueoCaja = (ArqueoCaja) entidad;
        setArqueoCaja(arqueoCaja);
        getInterfaz().setFechaRevision(UtilidadesFecha.getFechaDeTimeStamp(arqueoCaja.getFechaHora()));
        getInterfaz().setHoraRevision(UtilidadesFecha.getHoraDeTimeStamp(arqueoCaja.getFechaHora()));
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //                      GET AND SET
    ////////////////////////////////////////////////////////////////////////////

    public ArqueoCaja getArqueoCaja() {
        return arqueoCaja;
    }

    public void setArqueoCaja(ArqueoCaja arqueoCaja) {
        this.arqueoCaja = arqueoCaja;
    }

    public List<ArqueoCajaEnum> getEstadosList() {
        return estadosList;
    }

    public void setEstadosList(List<ArqueoCajaEnum> estadosList) {
        this.estadosList = estadosList;
    }

    ////////////////////////////////////////////////////////////////////////////
    //                      INTERFACES Y CLASES
    ////////////////////////////////////////////////////////////////////////////
    public interface CommonIf
    {
        public Date getFechaRevision();
        public void setFechaRevision(Date fechaRevision);
        public Date getHoraRevision();
        public void setHoraRevision(Date horaRevision);        
    }
    
    public interface SwingIf extends ArqueoCajaModelControlador.CommonIf
    {
        //TODO: Implementacion de las interfaces solo necesarias para Swing
    }
    
    public interface WebIf extends ArqueoCajaModelControlador.CommonIf
    {
        //TODO: Implementacion de las interafaces solo para la web
    }
    
    public void setearDatos(){
       //Unir las dos fechas en un solo formato; 1era fecha y 2da tiempo seleccionado
       Date fechaHora = UtilidadesFecha.FechaHoraPorUnion(getInterfaz().getFechaRevision(), getInterfaz().getHoraRevision());    
       arqueoCaja.setFechaHora(UtilidadesFecha.castDateSqlToTimeStampSql(fechaHora));
    }
}
