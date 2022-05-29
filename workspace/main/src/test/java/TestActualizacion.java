
import ec.com.codesoft.codefaclite.main.actualizacion.ActualizacionSistemaUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carlos
 */
public class TestActualizacion {
    public static void main(String[] args)
    {
        try {
            System.out.println("ejemplo");
            Method metodo=ActualizacionSistemaUtil.class.getMethod("ejecutarNuevoMetodoEstatico",null);
            metodo.invoke(null);
            /*Method[] metodos= ActualizacionSistema.class.getMethods();
            for (Method metodo : metodos) {
            metodo.
            System.out.println(metodo.getName());
            }*/
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(TestActualizacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(TestActualizacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TestActualizacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(TestActualizacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(TestActualizacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
