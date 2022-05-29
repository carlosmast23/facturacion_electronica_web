/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.panel.MantenimientoCodefacPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.utilidades.sql.UtilidadSql;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class MantenimientoCodefacModel extends MantenimientoCodefacPanel{
    
    private List<String> tablasBDListas;
    
    private String nombreTablaSeleccionada;
    
    

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        cargarDatosIniciales();
        listener();
    }
    
    private void listener()
    {
        listenerBotones();
    }
    
    private void listenerBotones()
    {
        getBtnEliminarEntidad().addActionListener(listenerEliminarEntidad);
    }
    
    private ActionListener listenerEliminarEntidad = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
           
            EntidadEliminarEnum entidadEliminar=(EntidadEliminarEnum) getCmbEliminarEntidad().getSelectedItem();
            
            switch(entidadEliminar)            
            {
                case CLIENTES_PROVEEDORES:
                    eliminarClientes();
                    break;
            }
            
            
        }
    };
    
    private void eliminarClientes()
    {
        try {
            List<Object[]> ejemplo = ServiceFactory.getFactory().getParametroCodefacServiceIf().ejecutarConsultaNativaEnum(RecursoCodefacEnum.SQL_BORRAR_CLIENTE_POR_EMPRESA);
            DialogoCodefac.mensaje(MensajeCodefacSistema.AccionesFormulario.ELIMINADO_CORRECTAMENTE);
            System.out.println("Tamanio:" + ejemplo.size());
        } catch (RemoteException ex) {
            Logger.getLogger(MantenimientoCodefacModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(MantenimientoCodefacModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cargarDatosIniciales()
    {
        tablasBDListas=Arrays.asList(Factura.NOMBRE_TABLA,Persona.NOMBRE_TABLA);
        
        //Cargar datos en el combo
        UtilidadesComboBox.llenarComboBox(getCmbEliminarEntidad(),EntidadEliminarEnum.values());
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
    
    public void btnListenerVerificarTablaBaseDatos()
    {
        try {
            ServiceFactory.getFactory().getUtilidadesServiceIf().verficarConsistenciaTabla(nombreTablaSeleccionada);
            DialogoCodefac.mensaje(new CodefacMsj("verificado", CodefacMsj.TipoMensajeEnum.CORRECTO));
        } catch (RemoteException ex) {
            Logger.getLogger(MantenimientoCodefacModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(MantenimientoCodefacModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public void btnListenerCorregirDatosDuplicados()
    {
        try {
            List resultados =ServiceFactory.getFactory().getHerramientasCodefacServiceIf().buscarClavesRepetidasBaseDatosLista(nombreTablaSeleccionada,"ID");
            
            List<Factura> facturasProcesarList = new ArrayList<Factura>();
            for (Object resultado : resultados) {
                try {
                    Long idPk = (Long) resultado;
                    Factura factura = ServiceFactory.getFactory().getFacturacionServiceIf().buscarPorId(idPk);
                    Factura facturaCopia = (Factura) factura.clone();
                    List<FacturaDetalle> detallesNoRepetidos = facturaCopia.getDetalles().stream().distinct().collect(Collectors.toList());                    
                    List<FormaPago> formasPagoNoRepetidos = facturaCopia.getFormaPagos().stream().distinct().collect(Collectors.toList());
                    facturaCopia.setDetalles(detallesNoRepetidos);
                    facturaCopia.setFormaPagos(formasPagoNoRepetidos);
                    
                    //facturaCopia.setId(null);
                    facturasProcesarList.add(facturaCopia);

                    //Logger.getLogger(AbstractFacade.class.getName()).log(Level.WARNING,"ERROR CLAVES DUPLICADOS EN TABLA: "+nombreTabla+" ,ID="+idPk);
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(MantenimientoCodefacModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            ServiceFactory.getFactory().getHerramientasCodefacServiceIf().corregirDatosDuplicadosPk(facturasProcesarList);
            DialogoCodefac.mensaje(new CodefacMsj("Datos Corregidos", CodefacMsj.TipoMensajeEnum.CORRECTO));
        } catch (RemoteException ex) {
            Logger.getLogger(MantenimientoCodefacModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(MantenimientoCodefacModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
        }
    }
    
    /**
     * =========================================================================
     *                              GET AND SET
     * =========================================================================
     */
    
    
    public List<String> getTablasBDListas() {
        return tablasBDListas;
    }

    public void setTablasBDListas(List<String> tablasBDListas) {
        this.tablasBDListas = tablasBDListas;
    }

    public String getNombreTablaSeleccionada() {
        return nombreTablaSeleccionada;
    }

    public void setNombreTablaSeleccionada(String nombreTablaSeleccionada) {
        this.nombreTablaSeleccionada = nombreTablaSeleccionada;
    }

    
    public enum EntidadEliminarEnum
    {
        PRODUCTOS,
        CLIENTES_PROVEEDORES,        
        FACTURAS,
        NOTAS_CREDITO
        
    }
    
}
