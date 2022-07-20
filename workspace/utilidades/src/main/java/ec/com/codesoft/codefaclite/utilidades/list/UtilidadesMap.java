/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @auhor
 */
public abstract class UtilidadesMap {
    
    static <T,M,N> Map<M,N> fromArrayToCollection(T[] a, Collection<T> c) {
        for (T o : a) {
            c.add(o); // Correct
        }
        return null;
    }

    public static <C,V,L> Map<C,V> crearMapDesdeList(List<L> listaDatos,MapCastListIf<C,V,L> interfaz)
    {
        Map<C,V> resultadoMap=new HashMap<C,V>();
        for (L listaDato : listaDatos) 
        {
            C clave=interfaz.getClave(listaDato);
            V valor=interfaz.getValor(listaDato);
                        
            resultadoMap.put(clave,valor);
        }
        
        return resultadoMap;
    }
    

    public static List castMapToList(Map map)
    {
        return new ArrayList(map.values());
    }
    
    public interface MapCastListIf<C,V,L>
    {
        public C getClave(L dato);
        public V getValor(L dato);
    }
    
}
