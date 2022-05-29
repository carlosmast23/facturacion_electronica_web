/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.crm;

import ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda.TipoEstablecimientoBusqueda;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TipoEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Zona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TipoEstablecimientoControlador extends ModelControladorAbstract<TipoEstablecimientoControlador.ICommon, TipoEstablecimientoControlador.ISwing,TipoEstablecimientoControlador.IWeb> implements VistaCodefacIf{

    private List<OperadorNegocioEnum> listaOperadores;
    
    private TipoEstablecimiento tipoEstablecimiento;
    
    public TipoEstablecimientoControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, ICommon interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }
    
    private void setearDatosAdicionales()
    {
        tipoEstablecimiento.setEmpresa(session.getEmpresa());
    }
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        listaOperadores = new ArrayList<OperadorNegocioEnum>() {
            {
                add(OperadorNegocioEnum.PROVEEDOR);
                add(OperadorNegocioEnum.CLIENTE);
            }
        };
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        try {
            setearDatosAdicionales();
            ServiceFactory.getFactory().getTipoEstablecimientoServiceIf().grabar(tipoEstablecimiento);
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ZonaControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ERROR));
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        try {
            setearDatosAdicionales();
            ServiceFactory.getFactory().getTipoEstablecimientoServiceIf().editar(tipoEstablecimiento);
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ZonaControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ERROR));
            throw new ExcepcionCodefacLite(ex.getMessage());
        }
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        try {
            
            if(!dialogoPregunta(new CodefacMsj("Esta seguro que desea eliminar",CodefacMsj.TipoMensajeEnum.ADVERTENCIA)))
            {
                throw new ExcepcionCodefacLite("Acción cancelada de eliminar");                
            }
            
            ServiceFactory.getFactory().getTipoEstablecimientoServiceIf().eliminar(tipoEstablecimiento);
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);
            
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ZonaControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ERROR));
            throw new ExcepcionCodefacLite(ex.getMessage());
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
        tipoEstablecimiento=new TipoEstablecimiento();        
        tipoEstablecimiento.setNombre("");
        tipoEstablecimiento.setDescripcion("");
        tipoEstablecimiento.setTipoEnum(OperadorNegocioEnum.CLIENTE);
        
        
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
        return new TipoEstablecimientoBusqueda(session.getEmpresa());
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        this.tipoEstablecimiento=(TipoEstablecimiento) entidad;
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        ////////////////////////////////////////////////////////////////////////////
    ///                          GET AND SET                                ////
    ////////////////////////////////////////////////////////////////////////////

    public TipoEstablecimiento getTipoEstablecimiento() {
        return tipoEstablecimiento;
    }

    public void setTipoEstablecimiento(TipoEstablecimiento tipoEstablecimiento) {
        this.tipoEstablecimiento = tipoEstablecimiento;
    }

    public List<OperadorNegocioEnum> getListaOperadores() {
        return listaOperadores;
    }

    public void setListaOperadores(List<OperadorNegocioEnum> listaOperadores) {
        this.listaOperadores = listaOperadores;
    }
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    ///                          INTERFACES                                 ////
    ////////////////////////////////////////////////////////////////////////////
    
    public interface ICommon
    {
    
    }
    
    public interface ISwing extends ICommon
    {
    
    }
    
    public interface  IWeb extends ICommon
    {
    
    }


}
