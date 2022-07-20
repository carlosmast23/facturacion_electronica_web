/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.panel.SqlPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @auhor
 */
public class SqlModel extends SqlPanel {

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        listenerBotones();
        listenerComboBox();
        cargarDatos();
    }
    
    private void cargarDatos()
    {
        getCmbQuerysGuardados().removeAllItems();
        getCmbQuerysGuardados().addItem(null);
        for (RecursoCodefacEnum value : RecursoCodefacEnum.values()) {
            getCmbQuerysGuardados().addItem(value);
        }
        
    }
    
    private void listenerComboBox()
    {
        getCmbQuerysGuardados().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RecursoCodefacEnum sqlEnum=(RecursoCodefacEnum) getCmbQuerysGuardados().getSelectedItem();
                if(sqlEnum!=null)
                {
                    getTxtAreaSql().setText(sqlEnum.getValueString());
                }
            }
        });
    }
    
    private void listenerBotones()
    {
                
        getBtnEjecutarComandos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    getTxtErrores().setText("");
                    String queryStr=getTxtAreaSql().getText();
                    List<Object[]> ejemplo=ServiceFactory.getFactory().getParametroCodefacServiceIf().ejecutarVariasConsultaNativa(queryStr);
                    System.out.println("Tamanio:"+ejemplo.size());
                    construirModeloTabla(ejemplo);
                    
                    DialogoCodefac.mensaje(new CodefacMsj("Proceso ejecutado, recuerde REINICIAR EL SISTEMA antes de CONTINUAR usando por su SEGURIDAD", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(SqlModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(SqlModel.class.getName()).log(Level.SEVERE, null, ex);
                    getTxtErrores().setText(ex.getMessage());
                    DialogoCodefac.mensaje(new CodefacMsj("Error al ejecutar el proceso revise el mensaje",CodefacMsj.TipoMensajeEnum.ERROR));
                }
           }
        });
    }
    
    private void construirModeloTabla(List<Object[]> datos)
    {
        String[] titulos={"Dato1","Dato2"};
        DefaultTableModel modeloTabla=null;
        for (Object[] filas : datos) {
            if(modeloTabla==null)
            {
                modeloTabla=new DefaultTableModel(construirTitulo(filas),0);
            }
            System.out.println("filas"+filas.toString());
            modeloTabla.addRow(filas);
        }
        
        
        getTblResultados().setModel(modeloTabla);
    }
    
    private Vector construirTitulo(Object[] filas)
    {    
        Vector titulo=new Vector();
        
        int numeroColumna=1;
        for (Object object : filas) 
        {
            titulo.add("Columna"+numeroColumna++);
        }
        
        return titulo;
        
    }
    
    //private void 

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
    
}
