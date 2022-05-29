/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.util;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public abstract class ParametroUtilidades {
    /**
     * Metodo que me permite comparar un parametro con un enum para ver si se cumple con un condición enviada por parametro
     * @param empresa
     * @param nombreParametro nombre dado por la clase de ParemetroCodefac
     * @param valorComparar
     * @return
     * @throws RemoteException 
     */
    /*public static Boolean comparar(Empresa empresa,String nombreParametro,EnumSiNo valorComparar) throws RemoteException
    {
        ParametroCodefac parametroCodefac = ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(nombreParametro,empresa);
        if (parametroCodefac != null) {
            //Solo si tiene parametro positivo intento construir el ensamble
            if (parametroCodefac.getValor() != null && EnumSiNo.getEnumByLetra(parametroCodefac.getValor()).equals(valorComparar)) {
                return true;
            }
        }
        return false;
    }*/
    
    public static <T extends ComparadorInterface> Boolean comparar(Empresa empresa,String nombreParametro,T valorComparar) throws RemoteException
    {
        String valorParametro=obtenerValorParametro(empresa, nombreParametro);
        if(valorParametro!=null)
        {
            T resultadoValor=(T) valorComparar.
                    consultarParametro(valorParametro);
            
            if(resultadoValor!=null && resultadoValor.equals(valorComparar))
            {
                return true;
            }        
        }
        return false; 
    }
    
    public static <T extends ComparadorInterface> Boolean compararSinEmpresa(String nombreParametro,T valorComparar) throws RemoteException
    {
        String valorParametro=obtenerValorParametroSinEmpresa(nombreParametro);
        if(valorParametro!=null)
        {
            T resultadoValor=(T) valorComparar.
                    consultarParametro(valorParametro);
            
            if(resultadoValor!=null && resultadoValor.equals(valorComparar))
            {
                return true;
            }        
        }
        return false; 
    }

    
    /**
     * 
     * @param empresa
     * @param nombreParametro
     * @return Si no encuentra nada devuelve null
     * @throws RemoteException 
     */
    public static String obtenerValorParametro(Empresa empresa , String nombreParametro) 
    {
        try {
            ParametroCodefac parametroCodefac = ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(nombreParametro,empresa);
            if (parametroCodefac != null) {
                //Solo si tiene parametro positivo intento construir el ensamble
                if(parametroCodefac.getValor()!=null)
                {
                    return parametroCodefac.getValor();
                }
                
            }
               
        } catch (RemoteException ex) {
            Logger.getLogger(ParametroUtilidades.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String obtenerValorParametroSinEmpresa(String nombreParametro) 
    {
        try {
            ParametroCodefac parametroCodefac = ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombreSinEmpresa(nombreParametro);
            if (parametroCodefac != null) {
                //Solo si tiene parametro positivo intento construir el ensamble
                if(parametroCodefac.getValor()!=null)
                {
                    return parametroCodefac.getValor();
                }
                
            }
               
        } catch (RemoteException ex) {
            Logger.getLogger(ParametroUtilidades.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Mejorar este tema para poder retorar por plantilla el tipo de objeto directo
     * TODO: Cambiar que reenviae la excepcion Remote Excepcion , deberia validar en este mismo metodo
     * porque genera mucho codigo basura en las clases que lo usan
     * @param <T>
     * @param empresa
     * @param nombreParametro
     * @param interfaceConsulta
     * @return
     * @throws RemoteException 
     */
    public static <T extends Object> T obtenerValorBaseDatos(Empresa empresa , String nombreParametro,ComparadorInterface interfaceConsulta) throws RemoteException
    {
        String idParametro=obtenerValorParametro(empresa, nombreParametro);
        if(idParametro!=null && !idParametro.toString().isEmpty())
        {
            return (T) interfaceConsulta.consultarParametro(idParametro);
        }
        return null;
    }
    
    public static <T extends ComparadorInterface> T obtenerValorParametroEnum(Empresa empresa , String nombreParametro,T instancia) 
    {
        String valor = obtenerValorParametro(empresa, nombreParametro);
        if (valor != null) {
            return (T) instancia.consultarParametro(valor);
        }
        return null;
    }
    
    public interface ComparadorInterface<T>
    {
        /**
         * Metodo que me permite consultar el resultado 
         * @param nombreParametro
         * @return 
         */
        public T consultarParametro(String nombreParametro);
       
    }
}
