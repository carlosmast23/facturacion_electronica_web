/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core.components;

import ec.com.codesoft.codefaclite.controlador.vistas.core.ConverterSwingMvvc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;

/**
 *
 * @auhor
 */
public class ButtonBindingImp extends ComponentBindingAbstract<JButton,ButtonBinding> {
    
    private ComponentBindingIf habilitarBoton=new ComponentBindingIf<Boolean,ButtonBinding>() {
        @Override
        public void getAccion(String nombrePropiedadControlador, ConverterSwingMvvc converter) {
            getComponente().addPropertyChangeListener("enable",new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    setValoresAlControlador(getComponente().isEnabled(), nombrePropiedadControlador, converter);
                }
            });
        }

        @Override
        public void setAccion(Boolean value, String nombrePropiedadControlador, ConverterSwingMvvc converter) {
            getComponente().setEnabled(value);
        }

        @Override
        public String getNombrePropiedadControlador(ButtonBinding componente) {
            return componente.habilitarBoton();
        }

        @Override
        public Class getConverterClass(ButtonBinding anotacion) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

    private ComponentBindingIf actionListener=new ComponentBindingIf<Object,ButtonBinding>() {
        @Override
        public void getAccion(String nombreMetodo,ConverterSwingMvvc converter) {
            getComponente().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ejecutarMetodoControlador(nombreMetodo);
                    actualizarBindingVista();
                }
            });
        }

        @Override
        public void setAccion(Object value,String nombrePropiedadControlador,ConverterSwingMvvc converter) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getNombrePropiedadControlador(ButtonBinding componente) {
            return componente.actionListener();
        }

        @Override
        public Class getConverterClass(ButtonBinding anotacion) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }


    };
    
    @Override
    public void getAccionesComponente(List<ComponentBindingIf> lista) {
        lista.add(actionListener);
        lista.add(habilitarBoton);
    }

    @Override
    public Class<JButton> getClassComponente() {
        return JButton.class;
    }
    
}
