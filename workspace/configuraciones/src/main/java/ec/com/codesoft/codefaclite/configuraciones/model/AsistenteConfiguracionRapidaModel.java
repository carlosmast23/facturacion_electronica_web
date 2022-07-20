/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.panel.AsistenteConfiguracionRapidaPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.vista.configuraciones.AsistenteConfiguracionRapidaControlador;
import ec.com.codesoft.codefaclite.controlador.vista.crm.RutaModelControlador;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSwing;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSwingX;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @auhor
 */
public class AsistenteConfiguracionRapidaModel  extends AsistenteConfiguracionRapidaPanel implements AsistenteConfiguracionRapidaControlador.SwingIf,ControladorVistaIf{

    private AsistenteConfiguracionRapidaControlador controlador;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        controlador=new AsistenteConfiguracionRapidaControlador(DialogoCodefac.intefaceMensaje, session, this, ModelControladorAbstract.TipoVista.ESCRITORIO);                
        //listenerBotones();
        agregarListenerLblAyudas();
    }
    
    public void agregarListenerLblAyudas()
    {
        UtilidadesSwing.agregarLinkLabel(getLblAyudaCorreo(), ParametrosSistemaCodefac.LINK_PERMISOS_CORREO_CODEFAC);
        UtilidadesSwing.agregarLinkLabel(getLblLinkRegistro(), ParametrosSistemaCodefac.LINK_REGISTRO_CODEFAC);
        UtilidadesSwing.agregarLinkLabel(getLblAyudaFirmaElectrónica(),ParametrosSistemaCodefac.LINK_FIMA_ELECTRONICA_CODEFAC);
        UtilidadesSwing.agregarLinkLabel(getLblRequisitosPrevios(),ParametrosSistemaCodefac.LINK_REQUISITOS_PREVIOS);
        
    }
       

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    @Override
    public void activarPestañaActiva(Integer indiceTab) {
        getTabVentana().setSelectedIndex(indiceTab);        
    }
    
    /**
     * ======================================================================
     *                        METODOS GET AND SET
     * ======================================================================
     */

    public AsistenteConfiguracionRapidaControlador getControlador() {
        return controlador;
    }

    public void setControlador(AsistenteConfiguracionRapidaControlador controlador) {
        this.controlador = controlador;
    }

    @Override
    public Boolean ejecutarValidadoresVista() {
        return panelPadre.validarPorGrupo(null);
    }

    @Override
    public ModelControladorAbstract getControladorVista() {
        return controlador;
    }

    @Override
    public File buscarFileLogoEmpresa() {
        String[] filtros={"png", "jpg", "bmp"};
        JFileChooser jFileChooser=UtilidadesSwing.getJFileChooserPreBuild("Buscar Logo","Logo Imagen",filtros);
        int seleccion=jFileChooser.showDialog(null,"Abrir");
        //Si devuelve una respuesta ejecuto el metodo para grabar
        if(seleccion==JFileChooser.APPROVE_OPTION)
        {
            return jFileChooser.getSelectedFile();
        }    
        return null;
    }

    @Override
    public File buscarFileFirmaElectronica() {
        String[] filtros={"p12", "pfx"};
        JFileChooser jFileChooser=UtilidadesSwing.getJFileChooserPreBuild("Buscar Firma","Firma",filtros);
        int seleccion=jFileChooser.showDialog(null,"Abrir");
        //Si devuelve una respuesta ejecuto el metodo para grabar
        if(seleccion==JFileChooser.APPROVE_OPTION)
        {
            return jFileChooser.getSelectedFile();
        }    
        return null;
    }
    
    @Override
    public void cerarSession()
    {
        panelPadre.cerrarSession();
    }
    
    
    
    
}
