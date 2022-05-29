/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core.components;

import ec.com.codesoft.codefaclite.controlador.vistas.core.ConverterSwingMvvc;
import ec.com.codesoft.codefaclite.controlador.vistas.core.TableBinding;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TableBindingImp extends ComponentBindingAbstract<JTable, TableBinding> {

    private static final Integer COLUMNA_OBJETO = 0;
    private static final Integer COLUMNA_CHECK = 1;
    
    private  final TableBindingImp ref=this;

    /**
     * Esta variable temporal me permite saber o controlar que solo se agregue una
     * sola ves el listener al check box para que este ligado el tema de seleccionar todo
     * por que la actual arquitectura no soporta el poder ejecutar una sola vez el metodo
     * set Accion
     * Nota: Esta parte se puede mejorar tal ves poniendo una variable de configuracion para
     * especificar que metodos se deben ejecutar una sola vez por ejemplo un getProperty(UniqueSetAccion=true)
     */
    private Boolean listenerAgregadoCheckBox=false;
    
    private ITableBindingAddData addDataInterface;
    private Boolean modoSelectedCheck;
    private Map<Object, Boolean> mapSeleccionCheck;
    
    public ComponentBindingIf eventoCambiarPropiedadTabla=new ComponentBindingIf<List, TableBinding>()
    {
        @Override
        public void getAccion(String nombrePropiedadControlador, ConverterSwingMvvc converter) {
            getComponente().getModel().addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    if(addDataInterface!=null)
                    {
                        if(e.getFirstRow()>=0 && e.getColumn()>=0)
                        {
                            Object objetoOriginal=getComponente().getModel().getValueAt(e.getFirstRow(),COLUMNA_OBJETO);
                            Object objetoEditado=getComponente().getModel().getValueAt(e.getFirstRow(),e.getColumn());
                            try
                            {
                                addDataInterface.setData(objetoOriginal, objetoEditado, e.getColumn());
                            }catch(UnsupportedOperationException uoe)
                            {}
                        }
                    }
                }
            });
            
            //https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
        }

        @Override
        public void setAccion(List value, String nombrePropiedadControlador, ConverterSwingMvvc converter) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getNombrePropiedadControlador(TableBinding componente) {
            return componente.source();
        }

        @Override
        public Class getConverterClass(TableBinding anotacion) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    
    };
    
        
    public ComponentBindingIf checkBoxSelection=new ComponentBindingIf<JCheckBox,TableBinding>()
    {
        private String nombrePropiedadListaSelection;
        @Override
        public void getAccion(String nombrePropiedadControlador, ConverterSwingMvvc converter) {
            
        }

        @Override
        public void setAccion(JCheckBox value,String nombrePropiedadControlador,ConverterSwingMvvc converter) {
            //value.getse
            if(modoSelectedCheck)
            {
                //Agregar un unica vez el listener del checkBox
                if(!listenerAgregadoCheckBox)
                {
                    /*value.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    });*/
                    
                    value.addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                            Boolean valorSeleccionado=value.isSelected();
                            setListTodosDatosSeleccionCheck(valorSeleccionado);
                            //Actualizo los datos en el modelo de la lista
                            List datosSeleccionado=obtenerListaDatosSeleccionadosCheck();
                            setValoresAlControlador(datosSeleccionado, nombrePropiedadListaSelection, converter);
                            actualizarBindingVista();                            
                        }
                    });
                    listenerAgregadoCheckBox=Boolean.TRUE;
                }
            }
        }

        @Override
        public String getNombrePropiedadControlador(TableBinding componente) {
            nombrePropiedadListaSelection=componente.listSelected();
            return componente.componenteCheckSelect();
        }

        @Override
        public Class getConverterClass(TableBinding anotacion) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    
    };

    /**
     * Metodo que me permite configurar cuando existe varios checks para
     * seleccionar
     */
    public ComponentBindingIf listSelectCheckImp = new ComponentBindingIf<List, TableBinding>() {
        @Override
        public void getAccion(String nombrePropiedadControlador, ConverterSwingMvvc converter) {
            /*getComponente().getModel().addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    List datosSeleccionados=obtenerListaDatosSeleccionadosCheck();
                    setValoresAlControlador(datosSeleccionados, nombrePropiedadControlador, converter);                    
                }
            });*/
            getComponente().addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //Actualizar el modelo de que objetos estan seleccionados cuando utilice el modo check , por que despues de actualizar se pierde esa referencia
                    if (modoSelectedCheck) {
                        DefaultTableModel modeloTabla=(DefaultTableModel) getComponente().getModel();
                        actualizarMapSeleccionCheck(modeloTabla);
                    }
                    
                    List datosSeleccionados = obtenerListaDatosSeleccionadosCheck();
                    setValoresAlControlador(datosSeleccionados, nombrePropiedadControlador, converter);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
        }

        @Override
        public void setAccion(List value,String nombrePropiedadControlador,ConverterSwingMvvc converter) {
            //
        }

        @Override
        public String getNombrePropiedadControlador(TableBinding componente) {
            return componente.listSelected();
        }

        @Override
        public Class getConverterClass(TableBinding anotacion) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    };

    /**
     * Propiedad que me permite activar el modo check de la tabla
     */
    public ComponentBindingIf modoSelectedCheckImp = new ComponentBindingIf<Boolean, TableBinding>() {
        @Override
        public void getAccion(String nombrePropiedadControlador, ConverterSwingMvvc converter) {
        }

        @Override
        public void setAccion(Boolean value,String nombrePropiedadControlador,ConverterSwingMvvc converter) {
        }

        @Override
        public String getNombrePropiedadControlador(TableBinding componente) {
            //obtener el valor directo
            modoSelectedCheck = componente.modeCheck();
            return null;
        }

        @Override
        public Class getConverterClass(TableBinding anotacion) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    };

    public ComponentBindingIf interfaceAddData = new ComponentBindingIf<ITableBindingAddData, TableBinding>() {
        @Override
        public void getAccion(String nombrePropiedadControlador, ConverterSwingMvvc converter) {
        }

        @Override
        public void setAccion(ITableBindingAddData value,String nombrePropiedadControlador,ConverterSwingMvvc converter) {
            addDataInterface = value;
        }

        @Override
        public String getNombrePropiedadControlador(TableBinding componente) {
            return componente.tableAddDataInterface();
        }

        @Override
        public Class getConverterClass(TableBinding anotacion) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    };

    public ComponentBindingIf valueSelect = new ComponentBindingIf<Object, TableBinding>() {
        @Override
        public void getAccion(String nombrePropiedadControlador, ConverterSwingMvvc converter) {

            getComponente().addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int filaSeleccionada = getComponente().getSelectedRow();
                    System.out.println("fila Seleccionada: " + filaSeleccionada);
                    if (filaSeleccionada >= 0) {
                        DefaultTableModel modeloTabla = (DefaultTableModel) getComponente().getModel();
                        if (modeloTabla != null) {
                            //TODO:Asumiendo que el dato que necesito siempre esta en la primera fila                        
                            Object valorSeleccionado = getComponente().getValueAt(filaSeleccionada, 0);

                            setValoresAlControlador(valorSeleccionado, nombrePropiedadControlador, converter);
                            actualizarBindingVista();

                            //Como el anterior codigo vuelve a construir la tabla vuelvo a seleccionar el mismo valor para que se vea visualmente
                            getComponente().setRowSelectionInterval(filaSeleccionada, filaSeleccionada);
                        }

                    }

                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });

        }

        @Override
        public void setAccion(Object value,String nombrePropiedadControlador,ConverterSwingMvvc converter) {
        }

        @Override
        public String getNombrePropiedadControlador(TableBinding componente) {
            return componente.selectValue();
        }

        @Override
        public Class getConverterClass(TableBinding anotacion) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    };

    public ComponentBindingIf value = new ComponentBindingIf<List, TableBinding>() {
        /**
         * Lista anterior para comparar si es un nuevo modelo o que pasa
         */
        private List listaAnterior;
        
        @Override
        public void getAccion(String nombrePropiedadControlador, ConverterSwingMvvc converter) {
            //getComponente()
            //TODO: Este campo seria cuando necesite que desde algun evento del comonente actualizo al modelo de datos
        }

        @Override
        public void setAccion(List value,String nombrePropiedadControlador,ConverterSwingMvvc converter) {
            DefaultTableModel defaultTableModel = (DefaultTableModel) getComponente().getModel();
            if (defaultTableModel != null) {
                
                //Verificar si previamente tiene definida una interfaz
                if (addDataInterface != null) {
                    
                    //Verificar si es el mismo modelo anterior o nuevo nuevo para limpiar los datos de seleccion multiple
                    if(modoSelectedCheck)
                    {
                        if(!value.equals(listaAnterior))
                        {
                            limpiarDatosSeleccionados();
                            listaAnterior=value;
                        }
                    }
                    
                    //Limpiar todos los datos de la tabla
                    UtilidadesTablas.eliminarTodosLosDatos(defaultTableModel);
                    if (value != null) {

                        for (Object valor : value) {
                            Object[] datosAgregar = addDataInterface.addData(valor);
                            //Si el modo Check esta activo agrego el valor de seleccion
                            if (modoSelectedCheck) {
                                Vector<Object> filaConCheck = new Vector<Object>(Arrays.asList(datosAgregar));
                                Boolean valorSeleccionado = obtenerSeleccionMap(filaConCheck.get(0)); //el objeto 0 es el que tiene el objeto de referencia
                                filaConCheck.add(COLUMNA_CHECK, valorSeleccionado); //falta setear el valor correspondiente
                                defaultTableModel.addRow(filaConCheck);
                            } else {
                                defaultTableModel.addRow(datosAgregar);
                            }
                        }
                    }
                }
            }
        }

        @Override
        public String getNombrePropiedadControlador(TableBinding componente) {
            return componente.source();
        }

        @Override
        public Class getConverterClass(TableBinding anotacion) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    };
    
    public ComponentBindingIf controlador = new ComponentBindingIf<Object, TableBinding>() 
    {
        @Override
        public void getAccion(String nombrePropiedadControlador, ConverterSwingMvvc converter) {
            setValoresAlControlador(ref, nombrePropiedadControlador, converter);
        }

        @Override
        public void setAccion(Object value,String nombrePropiedadControlador,ConverterSwingMvvc converter) {
            //getComponente().getSele
        }

        @Override
        public String getNombrePropiedadControlador(TableBinding componente) {
            return componente.controlador();
        }

        @Override
        public Class getConverterClass(TableBinding anotacion) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    };

    @Override
    public void getAccionesComponente(List<ComponentBindingIf> lista) {
        //Permite habilitar los checks en las tablas
        lista.add(modoSelectedCheckImp);
        ///Interfaz que me permite establecer como se deben cargar los datos en la tabla
        lista.add(interfaceAddData);
        // permite establecer la lista de los valores para mostrar en la tabla
        lista.add(value);
        // implementacion que me permite devolver todos los objetos seleccionados en los checks
        lista.add(listSelectCheckImp);
        // Propiedad que me permite devolver el primer objeto de la fila seleccionada que debe corresponder al objeto
        lista.add(valueSelect);
        // Implementacion que permite seleccionar o deseleccionar todos los componentes vinculados
        lista.add(checkBoxSelection);
        // Implementacion para poder setear un valor cuando se modifica el modelo en la tabla
        lista.add(eventoCambiarPropiedadTabla);
        //Implementacion que me permite pasar la  referencia de este controlador a la vista para que tenga mayor control
        lista.add(controlador);

    }

    @Override
    public Class<JTable> getClassComponente() {
        return JTable.class;
    }

    ////////////////////////////////////////////////////////////////////////////
    //                     METODOS PERSONALIZADOS
    ///////////////////////////////////////////////////////////////////////////    
    private Boolean obtenerSeleccionMap(Object objeto) {
        //Si no esta creado el map para controlar los objetos los creamos
        if (mapSeleccionCheck == null) {
            mapSeleccionCheck = new HashMap<Object, Boolean>();
        }

        Boolean valor = mapSeleccionCheck.get(objeto);
        //Si no existe el valor creado ese momento lo seleccionamos
        if (valor == null) {
            valor = false;
            mapSeleccionCheck.put(objeto, valor);
        }

        return valor;
    }

    public void actualizarMapSeleccionCheck(DefaultTableModel model) {
        mapSeleccionCheck = new HashMap<Object, Boolean>(mapSeleccionCheck);

        for (int i = 0; i < model.getRowCount(); i++) {
            Object objeto = model.getValueAt(i, COLUMNA_OBJETO);
            Boolean seleccion = (Boolean) model.getValueAt(i, COLUMNA_CHECK);

            mapSeleccionCheck.put(objeto, seleccion);

        }

    }

    public List obtenerListaDatosSeleccionadosCheck() {
        List datosSeleccionados = new ArrayList();
        if (mapSeleccionCheck != null) {
            for (Map.Entry<Object, Boolean> entry : mapSeleccionCheck.entrySet()) {
                Object dato = entry.getKey();
                Boolean seleccion = entry.getValue();

                if (seleccion) {
                    datosSeleccionados.add(dato);
                }
            }
        }
        return datosSeleccionados;
    }
    
    /**
     * Metodo para seleccionar o deseleccionar todos las filas de la tabla
     * @param seleccion 
     */
    public void setListTodosDatosSeleccionCheck(Boolean seleccion)
    {
        if (mapSeleccionCheck != null) {
            for (Map.Entry<Object, Boolean> entry : mapSeleccionCheck.entrySet()) {
                Object dato = entry.getKey();
                mapSeleccionCheck.put(dato, seleccion);
            }
        }
    }
    
    public void limpiarDatosSeleccionados()
    {
        mapSeleccionCheck = new HashMap<Object, Boolean>();
    }

}
