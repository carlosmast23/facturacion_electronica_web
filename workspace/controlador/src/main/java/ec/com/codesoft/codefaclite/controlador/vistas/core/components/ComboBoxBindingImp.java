/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core.components;

import ec.com.codesoft.codefaclite.controlador.vistas.core.ConverterSwingMvvc;
import ec.com.codesoft.codefaclite.controlador.vistas.core.TextFieldBinding;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @auhor
 */
public class ComboBoxBindingImp extends ComponentBindingAbstract<JComboBox,ComboBoxBinding>{

    private ComponentBindingIf source=new ComponentBindingIf<List,ComboBoxBinding>() {
        //Todo: Variable temporal para evitar que remplacen la misma fuente de datos sin son iguales
        public List valueTemp;
        @Override
        public void getAccion(String nombrePropiedadControlador,ConverterSwingMvvc converter) {
            
        }

        @Override
        public void setAccion(List value,String nombrePropiedadControlador,ConverterSwingMvvc converter) {
            if(valueTemp==value)
                return;
                
            UtilidadesComboBox.llenarComboBox(getComponente(), value);
            
            valueTemp=value;
            
        }

        @Override
        public String getNombrePropiedadControlador(ComboBoxBinding componente) {
            return componente.source();
        }

        @Override
        public Class getConverterClass(ComboBoxBinding anotacion) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }


       
            
    };
    
    
    private ComponentBindingIf valueSelect=new ComponentBindingIf<Object,ComboBoxBinding>() {
        
        @Override
        public void getAccion(String nombrePropiedadControlador,ConverterSwingMvvc converter) {
            getComponente().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setValoresAlControlador(getComponente().getSelectedItem(), nombrePropiedadControlador,converter);
                }
            });
        }

        @Override
        public void setAccion(Object value,String nombrePropiedadControlador,ConverterSwingMvvc converter) {
            Object componente=getComponente();
            getComponente().setSelectedItem(value);
        }

        @Override
        public String getNombrePropiedadControlador(ComboBoxBinding componente) {
            return componente.valueSelect();
        }

        @Override
        public Class getConverterClass(ComboBoxBinding anotacion) {
            return anotacion.converter();
        }


            
    };
    
    @Override
    public void  getAccionesComponente(List<ComponentBindingIf> lista) {
        lista.add(source);
        lista.add(valueSelect);
    }

    @Override
    public Class<JComboBox> getClassComponente() {
        return JComboBox.class;
    }

    
    
}
