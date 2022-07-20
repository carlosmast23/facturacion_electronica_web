/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core.components;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.controlador.vista.transporte.GuiaRemisionLoteControlador;
import ec.com.codesoft.codefaclite.controlador.vistas.core.ConverterSwingMvvc;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesReflexion;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 *Las 2 implemntacion genericas son 
 * T=componente de la vista ejemplo: jtextField
 * A= corresponde al tipo de la anotacion
 * @auhor
 */
public abstract class ComponentBindingAbstract<T,A> {
    /**
     * Variable que va a tener los datos del tipo de clase y el Method qu implenta el get para obtener el componente
     */
    //private UtilidadesReflexion.ResponseAnotacionMetodo anotacion;
    /**
     * Nombre de la propiedad en el controlador
     */
    //private String nombrePropiedadControlador;
    /**
     * Objeto del contexto del controlador
     */
    private VistaCodefacIf controlador;
    
    private T componenteVista;
    
    private A anotacionBinding;
    
    public abstract void getAccionesComponente(List<ComponentBindingIf> lista);
    /**
     * Obtiene la clase de los componentes de la vista
     * @return 
     */
    public abstract Class<T> getClassComponente();
    
    /**
     * Metodo que me permite corregir un problema con los listener
     * @return 
     */
    //public List<String> listaListenersCache=new ArrayList<String>();
    
    public void init(VistaCodefacIf panel,UtilidadesReflexion.ResponseAnotacionMetodo anotacion)
    {
        //Obtengo el componente de la vista
        componenteVista=UtilidadesReflexion.obtenerValorDelMetodo(anotacion.metodo,panel,getClassComponente());  
        anotacionBinding=(A) anotacion.anotacion;
        this.controlador=panel;
        //nombrePropiedadControlador=anotacion.anotacion.value();
            
        //    String valorPropiedad= UtilidadesReflexion.buscarComponentePorNombrePropiedad(controlador, nombrePropiedadControlador,String.class);
        //    jtextField.setText(valorPropiedad);
    }

    /*public void init(UtilidadesReflexion.ResponseAnotacionMetodo anotacion,ControladorCodefacInterface panel)
    {
        T componente = UtilidadesReflexion.obtenerValorDelMetodo(anotacion.metodo, panel, getClassComponente());
        
        String nombrePropiedadControlador = anotacion.anotacion.value();

        String valorPropiedad = UtilidadesReflexion.buscarComponentePorNombrePropiedad(controlador, nombrePropiedadControlador, String.class);
        //jtextField.setText(valorPropiedad);
    }*/
    
    /**
     * Metodo que permite ejecutar los getters y setters del controlador a la vista y viseversa
     * @param get obtiene datos de al vista al controlador
     * @param set setea los datos del controlador a la vista
     */
    public void ejecutar(Boolean get,Boolean set)
    {
        List<ComponentBindingIf> listaComponentes=new ArrayList<ComponentBindingIf>();
        getAccionesComponente(listaComponentes);
        for (ComponentBindingIf componentBindingIf : listaComponentes) 
        {            
            //Obtiene el nombre de la propiedad vinculada akl controlador
            String nombrePropiedad=componentBindingIf.getNombrePropiedadControlador(anotacionBinding);          
            
            //Buscar si tiene asignado un converter
            ConverterSwingMvvc converter=getConverter(componentBindingIf);            
            
            //Validacion cuando no tiene asignada ningun dato que vincula al controlador
            //Ejemplo @Anotacion(dato='')
            if(nombrePropiedad==null || nombrePropiedad.isEmpty())
            {
                continue;
            }
            
            if(get)
            {
                //Vincula el componente de la vista para poder setear al controlador cuando se produsca un evento
                componentBindingIf.getAccion(nombrePropiedad,converter);
            }

            if(set)
            {
                if(nombrePropiedad.equals("bodegaOrigenList"))
                {
                    System.out.println("Analizar");
                }
                
                //Obtiene le valor que alamacena la propiedad en la vista
                Object valorPropiedadControlador = getValorCampoControlador(nombrePropiedad);
                
                if(converter!=null)
                {
                    //System.out.println("Ejecutando metodo mvc :"+valorPropiedadControlador.toString());
                    valorPropiedadControlador=converter.castPropertyToComponente(valorPropiedadControlador);
                }
                
                //Envia los valores del controlador para setar con una propiedad de los componentes de la vista
                componentBindingIf.setAccion(valorPropiedadControlador,nombrePropiedad,converter);
            }
                        
        }
        
    }
    
    public ConverterSwingMvvc getConverter(ComponentBindingIf componentBindingIf)
    {
        ConverterSwingMvvc converter=null;
        try {
            Class claseConverter = componentBindingIf.getConverterClass(anotacionBinding);
            if(!claseConverter.equals(ConverterSwingMvvc.Error.class))
            {
                converter = (ConverterSwingMvvc) claseConverter.newInstance();
            }
            
        } catch (UnsupportedOperationException uoe) {
            //TODO:Ningun mensaje para evitar codgo basura en los logs
        } catch (InstantiationException ex) {
            //Logger.getLogger(ComponentBindingAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            //Logger.getLogger(ComponentBindingAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return converter;
    }
    
    public Object getValorCampoControlador(String nombrePropiedad)
    {
        String getMetodoPropiedadStr=nombrePropiedad;
        
        //Solo buscar propiedades en metodos cuando son propiedades
        if(nombrePropiedad.toLowerCase().indexOf("listener")>=0)
        {
            return null;
            //
        }
        
        String[] propiedadesRecursivas=nombrePropiedad.split("\\.");
        if(propiedadesRecursivas.length==0)
        {
            propiedadesRecursivas=new String[]{nombrePropiedad};
        }
        
        Object contexto=controlador;
        for (String propiedad : propiedadesRecursivas) {
            getMetodoPropiedadStr=UtilidadesReflexion.castNameMethodGetOrSet(propiedad, UtilidadesReflexion.GetSetEnum.GET);
            Method getMetodoPropiedad=UtilidadesReflexion.buscarMetodoPorNombre(contexto.getClass(), getMetodoPropiedadStr);
            
            if(getMetodoPropiedad==null)
            {
                if(propiedad.toLowerCase().equals("controlador"))
                {
                    Logger.getLogger(ComponentBindingAbstract.class.getName()).log(Level.SEVERE, "NO SE ENCONTRO CONTROLADOR");
                }          
                else
                {
                    Logger.getLogger(ComponentBindingAbstract.class.getName()).log(Level.SEVERE, "ADVERTENCIA: NO SE ENCUENTRA LA PROPIEDAD: "+propiedad);
                }
                break;
            }
            
            //return UtilidadesReflexion.buscarComponentePorNombrePropiedad(controlador,nombrePropiedad,Object.class);
            contexto=UtilidadesReflexion.obtenerValorDelMetodo(getMetodoPropiedad,contexto,Object.class);
            
            //Si el contexto es null no continuo
            if(contexto==null)
                break;
            
        }
        return contexto;
        
    }
    
    public void setValoresAlControlador(Object value,String nombrePropiedadControlador,ConverterSwingMvvc converter)
    {
        String[] propiedadesRecursivas=nombrePropiedadControlador.split("\\.");
        if(propiedadesRecursivas.length==0)
        {
            propiedadesRecursivas=new String[]{nombrePropiedadControlador};
        }
        
        
        Object contexto=controlador;        
        for (int i = 0; i < propiedadesRecursivas.length-1; i++) {
            String getMetodoPropiedadStr=UtilidadesReflexion.castNameMethodGetOrSet(propiedadesRecursivas[i], UtilidadesReflexion.GetSetEnum.GET);
            Method getMetodoPropiedad=UtilidadesReflexion.buscarMetodoPorNombre(contexto.getClass(), getMetodoPropiedadStr);            
            if(getMetodoPropiedad==null)
                break;            
            //return UtilidadesReflexion.buscarComponentePorNombrePropiedad(controlador,nombrePropiedad,Object.class);
            contexto=UtilidadesReflexion.obtenerValorDelMetodo(getMetodoPropiedad,contexto,Object.class);
            if(contexto==null)
            {
                return;
            }
        }
        
        String nombreMetodoSet=UtilidadesReflexion.castNameMethodGetOrSet(propiedadesRecursivas[propiedadesRecursivas.length-1],UtilidadesReflexion.GetSetEnum.SET);
        Method metodoSetPropiedad=UtilidadesReflexion.buscarMetodoPorNombre(contexto.getClass(),nombreMetodoSet);
        
        if(metodoSetPropiedad==null)
            return ;
        
        //Si existe un converter utilizar para transformar los valores
        if(converter!=null)
            value=converter.castComponentToProperty(value);
        
        UtilidadesReflexion.setearValorDelMetodo(metodoSetPropiedad, contexto,value);
        
    }
    
    /**
     * Actualiza los modelos con las vista
     */
    public void actualizarBindingVista()
    {
        ejecutarMetodoControlador("actualizarBindingCompontValues");
    }
    
    /**
     * TODO: Este metodo va ser usado directamente solo los listener para corregir un problema que se
     * activan al inicio del aplicativo sin querer
     * @param nombreMetodo 
     */
    public void ejecutarMetodoControlador(String nombreMetodo)
    {
        String[] metodosRecursivos=nombreMetodo.split("\\.");
        if(metodosRecursivos.length==0)
        {
            metodosRecursivos=new String[]{nombreMetodo};
        }
        
        Object contexto=controlador;    
        for (int i = 0; i < metodosRecursivos.length-1; i++) {
            String getMetodoPropiedadStr=UtilidadesReflexion.castNameMethodGetOrSet(metodosRecursivos[i], UtilidadesReflexion.GetSetEnum.GET);
            Method getMetodoPropiedad=UtilidadesReflexion.buscarMetodoPorNombre(contexto.getClass(), getMetodoPropiedadStr);            
            if(getMetodoPropiedad==null)
                break;            
            //return UtilidadesReflexion.buscarComponentePorNombrePropiedad(controlador,nombrePropiedad,Object.class);
            contexto=UtilidadesReflexion.obtenerValorDelMetodo(getMetodoPropiedad,contexto,Object.class);
        }
        
        if(contexto==null)
            return;
        
        String nombreMetodoFinal=metodosRecursivos[metodosRecursivos.length-1];
        Method metodoEjecutar=UtilidadesReflexion.buscarMetodoPorNombre(contexto.getClass(),nombreMetodoFinal);
        
        if (metodoEjecutar == null) 
        {
            //TODO: Por el momento solo imprimo el log para saber donde esta el prolema pero deveria devolver la exepcion a un nivel superior
            String mensajeError="Error de reflexion al ejecutar el metodo null : " +nombreMetodoFinal;
            System.err.println(mensajeError);
            Logger.getLogger(UtilidadesReflexion.class.getName()).log(Level.SEVERE, null, mensajeError);
        }   
        else
        {        
            UtilidadesReflexion.ejcutarMetodo(metodoEjecutar,contexto);
        }
       
    }
    

    public T getComponente() {
        return componenteVista;
    }

    public void setComponente(T Componente) {
        this.componenteVista = Componente;
    }
    
    
}
