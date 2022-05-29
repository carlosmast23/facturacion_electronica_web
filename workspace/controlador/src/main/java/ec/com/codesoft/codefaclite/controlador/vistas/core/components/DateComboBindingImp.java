/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core.components;

import ec.com.codesoft.codefaclite.controlador.vistas.core.ConverterSwingMvvc;
import ec.com.codesoft.codefaclite.controlador.vistas.core.DateComboBinding;
import ec.com.codesoft.codefaclite.controlador.vistas.core.TableBinding;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class DateComboBindingImp  extends ComponentBindingAbstract<JXDatePicker,DateComboBinding>{

    public ComponentBindingIf value=new ComponentBindingIf<java.util.Date,DateComboBinding>() {
        @Override
        public void getAccion(String nombrePropiedadControlador, ConverterSwingMvvc converter) {
            getComponente().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setValoresAlControlador(getComponente().getDate(), nombrePropiedadControlador, converter);                            
                }
            });
        }

        @Override
        public void setAccion(Date value,String nombrePropiedadControlador,ConverterSwingMvvc converter) {
            getComponente().setDate(value);
        }

        @Override
        public String getNombrePropiedadControlador(DateComboBinding componente) {
            return componente.value();
        }

        @Override
        public Class getConverterClass(DateComboBinding anotacion) {
            return anotacion.converter();
        }
            
    };
    
    @Override
    public void getAccionesComponente(List<ComponentBindingIf> lista) {
        lista.add(value);
    }

    @Override
    public Class<JXDatePicker> getClassComponente() {
        return JXDatePicker.class;
    }
    
    
}
