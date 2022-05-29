/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core.components;

import ec.com.codesoft.codefaclite.controlador.vistas.core.ConverterSwingMvvc;
import ec.com.codesoft.codefaclite.controlador.vistas.core.TableBinding;
import ec.com.codesoft.codefaclite.controlador.vistas.core.TextFieldBinding;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class CheckBoxBindingImp extends ComponentBindingAbstract<JCheckBox, CheckBoxBinding> {

    public ComponentBindingIf actionListener=new ComponentBindingIf<Boolean,CheckBoxBinding>() {
        @Override
        public void getAccion(String nombreMetodo, ConverterSwingMvvc converter) {
            getComponente().addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    ejecutarMetodoControlador(nombreMetodo);
                    actualizarBindingVista();
                }

                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {}

                @Override
                public void mouseEntered(MouseEvent e) {}

                @Override
                public void mouseExited(MouseEvent e) {}
            });
            
        }

        @Override
        public void setAccion(Boolean value, String nombrePropiedadControlador, ConverterSwingMvvc converter) {
            
        }

        @Override
        public String getNombrePropiedadControlador(CheckBoxBinding componente) {
            return componente.actionListener();
        }

        @Override
        public Class getConverterClass(CheckBoxBinding anotacion) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    
    }; 
    
    public ComponentBindingIf value=new ComponentBindingIf<Boolean,CheckBoxBinding>()
    {
        @Override
        public void getAccion(String nombrePropiedadControlador, ConverterSwingMvvc converter) {
            getComponente().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setValoresAlControlador(getComponente().isSelected(), nombrePropiedadControlador, converter);
                    actualizarBindingVista();
                }
            });
        }

        @Override
        public void setAccion(Boolean value, String nombrePropiedadControlador, ConverterSwingMvvc converter) {
            if(value==null)
            {
                value=false;
            }
            
            getComponente().setSelected(value);
        }

        @Override
        public String getNombrePropiedadControlador(CheckBoxBinding componente) {
            return componente.value();
        }

        @Override
        public Class getConverterClass(CheckBoxBinding anotacion) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
            
    };
    
    
    @Override
    public void getAccionesComponente(List<ComponentBindingIf> lista) {
        lista.add(value);
        lista.add(actionListener);
    }

    @Override
    public Class<JCheckBox> getClassComponente() {
        return JCheckBox.class;
    }
    
}
