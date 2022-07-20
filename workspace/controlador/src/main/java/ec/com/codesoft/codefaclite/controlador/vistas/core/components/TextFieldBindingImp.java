/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core.components;

import ec.com.codesoft.codefaclite.controlador.vistas.core.ConverterSwingMvvc;
import ec.com.codesoft.codefaclite.controlador.vistas.core.TextFieldBinding;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @auhor
 */
public class TextFieldBindingImp extends ComponentBindingAbstract<JTextField,TextFieldBinding>{

    

    public ComponentBindingIf value=new ComponentBindingIf<String,TextFieldBinding>() 
    {
        @Override
        public void getAccion(String nombrePropiedadControlador,ConverterSwingMvvc converter) {
            
            getComponente().getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    update();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    update();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    update();
                }
                
                private void update()
                {
                    setValoresAlControlador(getComponente().getText(),nombrePropiedadControlador,converter);
                }
            });                        
        }

        @Override
        public void setAccion(String value,String nombrePropiedadControlador,ConverterSwingMvvc converter) {
            getComponente().setText(value);
        }

        @Override
        public String getNombrePropiedadControlador(TextFieldBinding anotacion) {
            return anotacion.value();
        }

        @Override
        public Class getConverterClass(TextFieldBinding anotacion) {
            return anotacion.converter();
        }

    };
    
    
    
    public ComponentBindingIf editable=new ComponentBindingIf<Boolean,TextFieldBinding>() {
        @Override
        public void getAccion(String nombrePropiedadControlador,ConverterSwingMvvc converter) {
            getComponente().addPropertyChangeListener("editable",new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    setValoresAlControlador(getComponente().isEditable(),nombrePropiedadControlador,converter);
                }
            });
        }

        @Override
        public void setAccion(Boolean value,String nombrePropiedadControlador,ConverterSwingMvvc converter) {
            getComponente().setEditable(value);
        }

        @Override
        public String getNombrePropiedadControlador(TextFieldBinding componente) {
            return componente.editable();
        }

        @Override
        public Class getConverterClass(TextFieldBinding anotacion) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }


            
    };
    
    
    public ComponentBindingIf actionListener=new ComponentBindingIf<Boolean,TextFieldBinding>() {
        @Override
        public void getAccion(String nombreMetodo,ConverterSwingMvvc converter) {
            getComponente().addPropertyChangeListener("editable",new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    ejecutarMetodoControlador(nombreMetodo);
                }
            });
        }

        @Override
        public void setAccion(Boolean value,String nombrePropiedadControlador,ConverterSwingMvvc converter) {
            getComponente().setEditable(value);
        }

        @Override
        public String getNombrePropiedadControlador(TextFieldBinding componente) {
            return componente.actionListener();
        }

        @Override
        public Class getConverterClass(TextFieldBinding anotacion) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }


    }; 
    
    
    @Override
    public void getAccionesComponente(List<ComponentBindingIf> lista) {        
        lista.add(value);
        lista.add(editable);
        lista.add(actionListener);
    }

    @Override
    public Class<JTextField> getClassComponente() {
        return JTextField.class;
    }

    
}
