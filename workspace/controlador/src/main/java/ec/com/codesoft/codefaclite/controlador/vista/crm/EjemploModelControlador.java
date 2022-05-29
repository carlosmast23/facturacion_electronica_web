/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.crm;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.vista.crm.EjemploModelControlador.CommonIf;
import ec.com.codesoft.codefaclite.controlador.vista.crm.EjemploModelControlador.SwingIf;
import ec.com.codesoft.codefaclite.controlador.vista.crm.EjemploModelControlador.WebIf;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract.MensajeVistaInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class EjemploModelControlador extends ModelControladorAbstract<CommonIf,SwingIf,WebIf> implements VistaCodefacIf{
    
    //@ControladorCampoTextoAnot(nombre = "txtCampoTexto")
    private String saludo="texto sin modificar";
    private Boolean visibleCampo=true;
    
    private List<EnumSiNo> datosCombo;
    
    private EnumSiNo datoSeleccionado;
        
    public EjemploModelControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CommonIf interfaz,TipoVista tipoVista) {
        super(mensajeVista, session, interfaz,tipoVista);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //              METODOS GET AND SET DE LAS PROPIEDADES
    ////////////////////////////////////////////////////////////////////////////

    public String getSaludo() {
        return saludo;
    }

    public void setSaludo(String saludo) {
        this.saludo = saludo;
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        saludo="hola querido mundo";
        visibleCampo=false;
        datosCombo=new ArrayList<>();
        datosCombo.add(EnumSiNo.NO);
        datosCombo.add(EnumSiNo.SI);
        
        System.out.println("ejecutando metodo init controlador ...");
    }
    
    public void listenerBotonEjemplo()
    {
        DialogoCodefac.mensaje(new CodefacMsj("Combo Seleccionado"+datoSeleccionado,CodefacMsj.TipoMensajeEnum.CORRECTO));
    }
    

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        System.out.println("ejecutando metodo limpiar controlador ...");
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Boolean getVisibleCampo() {
        return visibleCampo;
    }

    public void setVisibleCampo(Boolean visibleCampo) {
        this.visibleCampo = visibleCampo;
    }

    public List<EnumSiNo> getDatosCombo() {
        return datosCombo;
    }

    public void setDatosCombo(List<EnumSiNo> datosCombo) {
        this.datosCombo = datosCombo;
    }

    public EnumSiNo getDatoSeleccionado() {
        return datoSeleccionado;
    }

    public void setDatoSeleccionado(EnumSiNo datoSeleccionado) {
        this.datoSeleccionado = datoSeleccionado;
    }
    
    
    
    
    
    ///////////////////////////////////////////////////////////////////////////
    //             METODOS QUE CONTIENEN INTERFACES PARA LA IMPLEMTACION
    ///////////////////////////////////////////////////////////////////////////
    
    public interface CommonIf
    {
        //TODO: Implementacion de todas las interfaces comunes
    }
    
    public interface SwingIf extends CommonIf
    {
        //TODO: Implementacion de las interfaces solo necesarias para Swing
    }
    
    public interface WebIf extends CommonIf
    {
        //TODO: Implementacion de las interafaces solo para la web
    }
    
    
    
}
