/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core.components;

import ec.com.codesoft.codefaclite.controlador.vistas.core.ConverterSwingMvvc;
import java.util.List;
import javafx.scene.control.PasswordField;
import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class PasswordFieldBindingImp extends ComponentBindingAbstract<JPasswordField,PasswordFieldBinding>{
    
    public ComponentBindingIf value=new ComponentBindingIf<String,PasswordFieldBinding>()
    {
        @Override
        public void getAccion(String nombrePropiedadControlador, ConverterSwingMvvc converter) 
        {
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
                    String valorClave=new String(getComponente().getPassword());
                    setValoresAlControlador(valorClave,nombrePropiedadControlador,converter);
                }
            });  
        }

        @Override
        public void setAccion(String value, String nombrePropiedadControlador, ConverterSwingMvvc converter) {
            getComponente().setText(value);
        }

        @Override
        public String getNombrePropiedadControlador(PasswordFieldBinding componente) {
            return componente.value();
        }

        @Override
        public Class getConverterClass(PasswordFieldBinding anotacion) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
            
    };

    @Override
    public void getAccionesComponente(List<ComponentBindingIf> lista) {
        lista.add(value);
    }

    @Override
    public Class<JPasswordField> getClassComponente() {
        return JPasswordField.class;
    }
    
}
