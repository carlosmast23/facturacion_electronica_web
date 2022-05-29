/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ReenviarComprobanteModelControlador;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.facturacion.panel.ReenviarComprobantePanel;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class ReenviarComprobanteModel extends ReenviarComprobantePanel implements ReenviarComprobanteModelControlador.CommonIf{

    private ReenviarComprobanteModelControlador controlador;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        controlador=new ReenviarComprobanteModelControlador(DialogoCodefac.intefaceMensaje,session,this,ModelControladorAbstract.TipoVista.ESCRITORIO);        
        controlador.iniciar();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        
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
    public Map<Integer, Boolean> permisosFormulario() {
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
    public void cargarVistaTabla(List<ComprobanteElectronico> comprobantes) {
        
        DefaultTableModel modeloTabla= UtilidadesTablas.crearModeloTabla(new String[]{"Selección","Preimpreso","Identificación","Fecha Emisión","Tipo Documento","Clave de acceso"},new Class[]{Boolean.class,String.class,String.class,String.class,String.class,String.class});
        getTblComprobantes().setModel(modeloTabla);
        
        UtilidadesTablas.llenarTablasDatos(
                getTblComprobantes(),  
                comprobantes, 
                new UtilidadesTablas.LlenarDatoTablaIf<ComprobanteElectronico>() {
                    @Override
                    public void agregarDato(List<Object> fila, ComprobanteElectronico dato) {
                        fila.add(false);
                        fila.add(dato.getPreimpreso());
                        fila.add(dato.getIdentificacion());
                        fila.add(dato.getFechaEmision());
                        fila.add(dato.getInformacionTributaria().getCodigoDocumentoEnum().getNombre());
                        fila.add(dato.getInformacionTributaria().getClaveAcceso());
                    }
                }
        );        
    }
    
}
