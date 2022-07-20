/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core;

import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ButtonBinding;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ButtonBindingImp;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.CheckBoxBinding;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.CheckBoxBindingImp;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ComboBoxBinding;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ComboBoxBindingImp;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ComponentBindingAbstract;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.DateComboBindingImp;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.PasswordFieldBinding;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.PasswordFieldBindingImp;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.SpinnerBindingImp;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.TableBindingImp;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.TextFieldBindingImp;
import java.util.HashMap;
import java.util.Map;

/**
 * Lista donde se tienen que agregar todos los binding MVC que van a interactuar con los formularios SWING
 * @auhor
 */
public class RoutingComponentBinding {
    
    public static Map<Class,Class> routingMap=new HashMap<Class, Class>();
    static
    {
        routingMap.put(TextFieldBinding.class,TextFieldBindingImp.class);
        routingMap.put(ComboBoxBinding.class,ComboBoxBindingImp.class);
        routingMap.put(ButtonBinding.class,ButtonBindingImp.class);    
        routingMap.put(SpinnerBinding.class,SpinnerBindingImp.class);
        routingMap.put(TableBinding.class,TableBindingImp.class);
        routingMap.put(DateComboBinding.class,DateComboBindingImp.class);
        routingMap.put(CheckBoxBinding.class,CheckBoxBindingImp.class);
        routingMap.put(PasswordFieldBinding.class,PasswordFieldBindingImp.class);
        
    }
    
}
