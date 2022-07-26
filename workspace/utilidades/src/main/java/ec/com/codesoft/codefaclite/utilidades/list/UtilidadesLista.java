/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.list;

//import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author
 */
public abstract class UtilidadesLista {
    public static String castListToString(List lista,String caracter)
    {
        if(lista==null || lista.size()==0)
        {
            return "";
        }
        
        return StringUtils.join(lista,caracter);
    }
    
    public static String castListToString(List lista,String caracter,CastListInterface interfaz)
    {
        List<String> datos=new ArrayList<String>();
        for (Object object : lista) {
            String textoNuevo=interfaz.getString(object);
            datos.add(textoNuevo);
        }
        return StringUtils.join(datos,caracter);
    }
    
    public static <T> List<T> eliminarReferenciaNulas(List<T> datos)
    {
        List<T> listWithoutNulls = datos.stream().filter(Objects::nonNull).collect(Collectors.toList());        
        return listWithoutNulls;
    }

    public static <T> List<T> restarListas(List<T> datosOriginales,List<T> datosNuevos)
    {
        List<T> listaEliminados=new ArrayList<T>();
        for (T datoOriginal : datosOriginales) {
            
            Boolean datoEliminado=true;
            for (T datoNuevo : datosNuevos) {
                if(datoNuevo.equals(datoOriginal))
                {
                    datoEliminado=false;
                }
            }
            
            if(datoEliminado)
            {
                listaEliminados.add(datoOriginal);
            }
            
        }
       
        return listaEliminados;
    }
    
    public static void ordenarLista(List lista,Comparator comparator)
    {
        Collections.sort(lista,comparator);
    }
    
    public interface CastListInterface<T>
    {
        public String getString(T dato);
    }
    
    public static <T> List<T> arrayToList(T[] array){
        List<T> lista = Arrays.asList(array);
        return lista;
    }
    
    public static List<List> dividirLista(Integer tamanioDividir,List listaOriginal)
    {
        //TODO: Comentado por que algo esta afectando al proyecto
        //return Lists.partition(listaOriginal,tamanioDividir);
        return null;
        
    }
    
    
}
