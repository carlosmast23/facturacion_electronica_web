/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.varios;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @auhor
 */
public class UtilidadesReflexion {

    public static <T> void ejecutarAnotacionEnPropiedades(Class clase, Class claseAnotacion, AnotacionIf<T> interfaz) {
        Field[] campos = clase.getFields();
        for (Field campo : campos) {
            T anotacion = (T) campo.getAnnotation(claseAnotacion);
            if (anotacion != null) {
                interfaz.ejecutar(anotacion);
            }
        }

    }

    public static <T> List<ResponseAnotacion<T>> buscarAnotacionEnPropiedades(Class clase, Class<T> claseAnotacion) {
        List<ResponseAnotacion<T>> resultado = new ArrayList<ResponseAnotacion<T>>();
        Field[] campos = clase.getDeclaredFields();
        for (Field campo : campos) {
            Class anotacionTmp = claseAnotacion;
            T anotacion = (T) campo.getAnnotation(anotacionTmp);
            if (anotacion != null) {
                ResponseAnotacion<T> dato = new ResponseAnotacion<T>(campo, anotacion);
                resultado.add(dato);
            }
        }
        return resultado;
    }
    
    public static <T> List<ResponseAnotacionMetodo> buscarAnotacionEnMetodos(Class clase, Class<T> claseAnotacion) {
        List<ResponseAnotacionMetodo> resultado = new ArrayList<ResponseAnotacionMetodo>();
        Method[] metodos = clase.getMethods();
        for (Method metodo : metodos) {
            Class anotacionTmp = claseAnotacion;
            T anotacion = (T) metodo.getAnnotation(anotacionTmp);
            if (anotacion != null) {
                ResponseAnotacionMetodo<T> dato = new ResponseAnotacionMetodo<T>(metodo, anotacion);
                resultado.add(dato);
            }
        }
        return resultado;
    }
    
    public static  Method buscarMetodoPorNombre(Class claseBase,String nombreMetodo) {

        try {
            Method[] metodosLista=claseBase.getMethods();
            for (Method metodo : metodosLista) {
                if(metodo.getName().equals(nombreMetodo))
                {
                    return metodo;
                }
            }

        } catch (SecurityException ex) {
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static <T> T buscarComponentePorNombrePropiedad(Object objectoBase, String nombre, Class<T> tipoClase) {

        try {
            List<Field> campos = obtenerTodosLosCampos(new ArrayList<Field>(),objectoBase.getClass());
            for (Field campo : campos) {
                if (campo.getName().equals(nombre)) {
                    campo.setAccessible(true);
                    return (T) campo.get(objectoBase);
                }
            }
           

        } catch (SecurityException ex) {
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static <T> T obtenerValorDelCampo(Field campo, Object context, Class<T> tipoClase) {
        try {
            campo.setAccessible(true);
            return (T) campo.get(context);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static <T> T obtenerValorDelMetodo(Method metodo, Object context, Class<T> tipoClase) {
                
        try {            
            metodo.setAccessible(true);
            return (T)metodo.invoke(context);
            //
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, "ERROR ACCEDIENDO AL METODO: "+metodo.getName());
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, "ERROR ACCEDIENDO AL METODO: "+metodo.getName());
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, "ERROR ACCEDIENDO AL METODO: "+metodo.getName());
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void ejcutarMetodo(Method metodo, Object context) {
                
        try {
            //metodo.setAccessible(true);
            metodo.invoke(context);
            //
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void setearValorDelCampo(Field campo, Object context,Object value) {
        try {
            campo.setAccessible(true);
            campo.set(context, value);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void setearValorDelMetodo(Method metodo, Object context,Object value) {
        try {
            //metodo.setAccessible(true);
            metodo.invoke(context, value);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE,"El tipo de datos recibido como ARGUMENTO es INCORRECTO");
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    


    /**
     * Metodo que obtiene de forma recursiva todos los campos cuando estan
     * heredando
     *
     * @param fields
     * @param type
     * @return
     */
    public static List<Field> obtenerTodosLosCampos(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            obtenerTodosLosCampos(fields, type.getSuperclass());
        }

        return fields;
    }
    
    public static List<Method> obtenerTodosLosMetodosPublicos(List<Method> metodos, Class<?> type,int nivel) {
        metodos.addAll(Arrays.asList(type.getMethods()));

        if (type.getSuperclass() != null) {
            
            //TODO: Validacion por nivel
            if(nivel==0)
                return metodos;
            
            obtenerTodosLosMetodosPublicos(metodos, type.getSuperclass(),nivel--);
        }

        return metodos;
    }
    
    public static Field buscarCampoPorNombre(Class claseContexto,String nombre)
    {
        List<Field> campos = obtenerTodosLosCampos(new ArrayList<Field>(),claseContexto.getClass());
        for (Field campo : campos) {
            System.out.println("campo: "+campo.getName());
            if(campo.getName().equals(nombre))
            {
                return campo;
            }
        }      
        return null;
    }
    
    /**
     * Metodo de utilidad cuando se trabaja entre metodos get y set y se desea
     * poder intercambiar para poder escribir o leer
     * @param nombre
     * @param getSetEnum
     * @return 
     */
    public static String castNameMethodGetOrSet(String nombre,GetSetEnum getSetEnum)
    {
        //obtener el nombre si get ni set
        String nombreOriginal=nombre.replace(GetSetEnum.GET.nombre,"").replace(GetSetEnum.SET.nombre,"");
        //poner la primera letra en mayuscula siguiente la conversion de nombres
        nombreOriginal=StringUtils.capitalize(nombreOriginal);
        //agregar el prefijo segun la necesidad
        return getSetEnum.nombre.concat(nombreOriginal);
        
    }

    ////////////////////////////////////////////////////////////////////////////
    //      CLASES E INTERFACES ADICIONALES PARA LA CLASE
    ////////////////////////////////////////////////////////////////////////////
    public enum GetSetEnum
    {
        GET("get"),
        SET("set");
        
        private String nombre;

        private GetSetEnum(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }
        
    }
    
    public static class ResponseAnotacion<T> {

        public Field field;
        public T anotacion;

        public ResponseAnotacion(Field field, T anotacion) {
            this.field = field;
            this.anotacion = anotacion;
        }

    }
    
    public static class ResponseAnotacionMetodo<T> {

        public Method metodo;
        public T anotacion;

        public ResponseAnotacionMetodo(Method metodo, T anotacion) {
            this.metodo = metodo;
            this.anotacion = anotacion;
        }

    }

    public interface AnotacionIf<T> {

        public void ejecutar(T anotacion);
    }
}
