/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core.components;

import ec.com.codesoft.codefaclite.controlador.vistas.core.ConverterSwingMvvc;
import ec.com.codesoft.codefaclite.controlador.vistas.core.SpinnerBinding;
import java.util.List;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @auhor
 */
public class SpinnerBindingImp extends ComponentBindingAbstract<JSpinner, SpinnerBinding>{

    public ComponentBindingIf value=new ComponentBindingIf<Object, SpinnerBinding>()
    {
        @Override
        public void getAccion(String nombrePropiedadControlador, ConverterSwingMvvc converter) {
            getComponente().addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    setValoresAlControlador(getComponente().getValue(), nombrePropiedadControlador, converter);
                }
            });
        }

        @Override
        public void setAccion(Object value,String nombrePropiedadControlador,ConverterSwingMvvc converter) {
            if(value!=null)
            {
                getComponente().setValue(value);
            }
        }

        @Override
        public String getNombrePropiedadControlador(SpinnerBinding componente) {
            return componente.value();
        }

        @Override
        public Class getConverterClass(SpinnerBinding anotacion) {
            return anotacion.converter();
        }
        
    };
    
    @Override
    public void getAccionesComponente(List<ComponentBindingIf> lista) {
        lista.add(value);
    }

    @Override
    public Class<JSpinner> getClassComponente() {
        return JSpinner.class;
    }
    
}
