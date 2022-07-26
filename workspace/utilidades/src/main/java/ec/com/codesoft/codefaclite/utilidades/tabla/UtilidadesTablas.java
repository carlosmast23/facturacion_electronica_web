/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.tabla;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.jdesktop.swingx.JXComboBox;

/**
 *
 * @author
 */
public abstract class UtilidadesTablas {
    
    public static int alinearCentro = SwingConstants.CENTER;
    public static int alinearDerecha = SwingConstants.RIGHT;
    public static int alinearIzquierda = SwingConstants.LEFT;
    
    public static void definirTamanioColumnas(JTable jtable,Integer[] tamanios)
    {
        for (int i = 0; i < tamanios.length; i++) {
            jtable.getColumnModel().getColumn(i).setMaxWidth(tamanios[i]);
            jtable.getColumnModel().getColumn(i).setPreferredWidth(tamanios[i]);
        }        

    }
    
    public static void definirTamanioColumnasPorMap(JTable jtable,Map<Integer,Integer> tamaniosMap)
    {
        for (Map.Entry<Integer, Integer> entry : tamaniosMap.entrySet()) {
            Integer columna = entry.getKey();
            Integer tamanio = entry.getValue();
            
            jtable.getColumnModel().getColumn(columna).setPreferredWidth(tamanio);
            
        }

    }
    
    public static <T> T obtenerDatoSeleccionado(JTable tabla,int columna)
    {
        int fila=tabla.getSelectedRow();
        if(fila>=0)
        {
            return (T)tabla.getValueAt(fila, columna);
        }
        return null;
    }
      
    
    public static void ocultarColumna(JTable jtable,int columna)
    {
        jtable.getColumnModel().getColumn(columna).setMaxWidth(0);
        jtable.getColumnModel().getColumn(columna).setMinWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(columna).setMaxWidth(0);
        jtable.getTableHeader().getColumnModel().getColumn(columna).setMinWidth(0);
    }
    
    public static void alinearTodasColumnasTabla(JTable tabla, int alinear)
    {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(alinear);
        for(int c=0 ; c<tabla.getColumnModel().getColumnCount(); c++)
        {
            tabla.getColumnModel().getColumn(c).setCellRenderer(tcr);
        }
    }
    
    public static void alinearColumnasTabla(JTable tabla, int alinear, Integer[] columnas)
    {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(alinear);
        for(int columna : columnas )
        {
            tabla.getColumnModel().getColumn(columna).setCellRenderer(tcr);
        }

    }
    
    public static void eliminarTodosLosDatos(DefaultTableModel tablaModel)
    {
        while (tablaModel.getRowCount()>0) {
            tablaModel.removeRow(0);
        }
    }
    
    public static DefaultTableModel crearModeloTabla(String titulos[],Class[] tipoDatoFilas)
    {
         DefaultTableModel defaultTableModel=new javax.swing.table.DefaultTableModel(titulos,0) 
         {
             public Class getColumnClass(int columnIndex) {
                return tipoDatoFilas [columnIndex];
            }
             
        };    
         return defaultTableModel;
    }
    
    
    public static DefaultTableModel crearModeloTabla(String titulos[],Class[] tipoDatoFilas,Boolean[] puedeEditar)
    {
         DefaultTableModel defaultTableModel=new javax.swing.table.DefaultTableModel(titulos,0) 
         {
             public Class getColumnClass(int columnIndex) {
                 return tipoDatoFilas[columnIndex];
             }

             @Override
             public boolean isCellEditable(int row, int column) {
                 if(column>=puedeEditar.length)
                 {
                    return true; //Si no existe datos para alguna columna por defecto queda como editable
                 }
                 else
                 {
                    return puedeEditar[column];
                 }
             }
             
        };    
         return defaultTableModel;
    }

    
    public static void cambiarColorFila(JTable table) {
        Enumeration<TableColumn> en = table.getColumnModel().getColumns();
        while (en.hasMoreElements()) {
            TableColumn tc = en.nextElement();
            tc.setCellRenderer(new CellRenderer());
        }
    }
    
    /**
     * @deprecated Este metodo no funciona no se ni porque esta escrito
     * @param table
     * @param puedeEditar 
     */
    public static void bloquearColumnasTabla(JTable table, Boolean[] puedeEditar)
    {
        table = new JTable(){
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return puedeEditar[columnIndex];
            }
        };
    }
    
    public static void bloquerTodasColumnasTabla(JTable table)
    {
        
    }
    
    public static void cambiarTipoColumna(JTable jTable,Integer columna,JComboBox jComboBox)
    {
        jTable.getColumnModel().getColumn(columna).setCellEditor(new DefaultCellEditor(jComboBox));
    }
    
    public static void cambiarTipoColumna(JTable jTable,Integer columna,JTextField jTextField)
    {
        jTable.getColumnModel().getColumn(columna).setCellEditor(new DefaultCellEditor(jTextField));
    }
    
    public static void cambiarTipoColumna(JTable jTable,Integer columna,JCheckBox jCheckBox)
    {
        jTable.getColumnModel().getColumn(columna).setCellEditor(new DefaultCellEditor(jCheckBox));
    }
    
    /**
     * Funcion que ubica el cursor al final de la tabla
     * @param jtable 
     */
    public static void ubicarFinalTabla(JTable jtable)
    {
        jtable.scrollRectToVisible(jtable.getCellRect(jtable.getRowCount()-1,0, true));
    }
    
    public static void cambiarTamanioColumnas(JTable table , Integer[] valoresColumnas)
    {
        int total=0;
        for (int i = 0; i < valoresColumnas.length; i++) 
        {
           total+=valoresColumnas[i];
        }
        
        double porcentajes[]=new double[valoresColumnas.length];        
        for (int i = 0; i < porcentajes.length; i++) {
            porcentajes[i]=(double)((double)valoresColumnas[i]/(double)total);
        }
        
        TableColumnModel columnModel = table.getColumnModel();        
        for (int i=0;i<columnModel.getColumnCount();i++) 
        {
            TableColumn columna=columnModel.getColumn(i);
            int tamanioTabla=table.getSize().width;
            double tamanioColumna=porcentajes[i]*tamanioTabla;
            columna.setPreferredWidth((int) tamanioColumna);
            columna.setMaxWidth((int) tamanioColumna);
            //columna.se
        }
        
    }
    
    /**
     * Cambiar propiedad de la tabla para editar la celda apenas pierda el focus
     * TODO:Revisar porque esta funcionalidad no vale con el programa pero si vale con otro ejemplos
     */
    public static void editarTablaEditarCuandoPierdeFoco(JTable jtable)
    {
        jtable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
    }
    
    /**
     * Esta funcion solo sirve para forzar que los datos de la tabla se actualicen por ejemplo cuando el usuario no presiona enter
     * o cuando presiona enter
     * @param jtable 
     */
    public static void setearValoresEditadosTabla(JTable jtable)
    {
        if (jtable.isEditing())
            jtable.getCellEditor().stopCellEditing();
    }
    
    /**}
     * Metodo que me permite crear una nueva tabla creado un modelo
     * @param <T>
     * @param jtable
     * @param titulos
     * @param datos
     * @param interfaz 
     */
    public static <T> void  llenarTablasDatos(JTable jtable,String[] titulos,List<T> datos,LlenarDatoTablaIf<T> interfaz)
    {
        DefaultTableModel modeloTabla= new DefaultTableModel(titulos,0);
        for (T dato : datos) {
            
            List<Object> datoNuevo=new ArrayList<Object>();
            
            //TODO: Agregar notificacion para mandar error cuando agregue mas datos de los necesarios
            
            interfaz.agregarDato(datoNuevo,dato);
            modeloTabla.addRow(datoNuevo.toArray());
        }
        jtable.setModel(modeloTabla);
        
    }
    
    /**
     * Metodo que me permite llenar datos usando el mismo modelo
     * @param <T>
     * @param jtable
     * @param datos
     * @param interfaz 
     */
    public static <T> void  llenarTablasDatos(JTable jtable,List<T> datos,LlenarDatoTablaIf<T> interfaz)
    {
        DefaultTableModel modeloTabla= (DefaultTableModel) jtable.getModel();        
        for (T dato : datos) {
            
            List<Object> datoNuevo=new ArrayList<Object>();
            
            //TODO: Agregar notificacion para mandar error cuando agregue mas datos de los necesarios
            
            interfaz.agregarDato(datoNuevo,dato);
            modeloTabla.addRow(datoNuevo.toArray());
        }
        jtable.setModel(modeloTabla);
        
    }
    
    /**
     * DEFINICIONES DE CLASES E INTERFACES ADICIONALES 
     */
    public interface LlenarDatoTablaIf<T>
    {
        public void agregarDato(List<Object> fila,T dato);
                
    }
   
}
