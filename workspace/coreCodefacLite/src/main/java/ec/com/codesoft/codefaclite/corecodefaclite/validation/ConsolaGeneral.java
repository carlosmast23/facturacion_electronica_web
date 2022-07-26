/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.validation;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author
 */
public class ConsolaGeneral {
    private String[] titulo={"Componente","Observacion"};
    private List<DatoTabla> modeloDatos;

    public ConsolaGeneral() {
       modeloDatos=new ArrayList<DatoTabla>();
    }
    
    
    public DefaultTableModel getModeloTabla()
    {   
        //Crea un modelo para la tabla pero con todas las celdas bloqueadas para editar
        DefaultTableModel modeloTabla = new DefaultTableModel(titulo, 0){
            public boolean isCellEditable(int rowIndex,int columnIndex){return false;}
        };
        Collections.sort(modeloDatos,new Comparator<DatoTabla>() {
            @Override
            public int compare(DatoTabla o1, DatoTabla o2) {
                return o1.componente.toString().compareTo(o2.componente.toString());
            }
        });
        //modeloTabla.
        for (DatoTabla dato : modeloDatos) {
            modeloTabla.addRow(dato.fila);
        }
        return modeloTabla;
       
    }
    
    public void agregarDatos(String titulo,String observacion,Component componente)
    {
        if(!verificarExiste(titulo,observacion,componente))
        {
            Vector<String> fila= new Vector<String>();
            fila.add(titulo);
            fila.add(observacion);    
            this.modeloDatos.add( new DatoTabla(fila, (JTextComponent) componente));
        }
        
    }
    
    public void quitarDato(Component componente)
    {
        for (int i = 0; i < modeloDatos.size();) {
            DatoTabla dato=modeloDatos.get(i);
            if(dato.componente.equals(componente))
            {
                modeloDatos.remove(dato);
            }
            else
            {
                i++;
            }
        }
  
    }
    
    private boolean verificarExiste(String titulo,String observacion,Component componente)
    {
        for (DatoTabla dato : modeloDatos) {
            if(dato.componente.equals(componente) && dato.fila.get(0).equals(titulo) && dato.fila.get(1).equals(observacion))
            {
                return true;
            }
        }
        return false;
              
    }
    
    /**
     * Seleccionar el componente de la vista segun el numero de fila
     * @param fila 
     */
    public void seleccionarFila(int fila)
    {
        DatoTabla dato=modeloDatos.get(fila);
        dato.componente.requestFocus();
        dato.componente.setBackground(new Color(255,255,102));
        dato.componente.selectAll();
    }

    
    public class DatoTabla
    {
        public Vector<String> fila;
        public JTextComponent componente;

        public DatoTabla(Vector<String> fila, JTextComponent componente) {
            this.fila = fila;
            this.componente = componente;
        }
        
        
    }

    public List<DatoTabla> getModeloDatos() {
        return modeloDatos;
    }
    
    
    
}
