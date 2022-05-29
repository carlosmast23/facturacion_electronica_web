/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.respuesta;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CategoriaMenuEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class MenuCodefacRespuesta implements Serializable{
    /**
     * Listado solo de los modulos activos
     */
    private List<ModuloCodefacEnum> modulosDisponibles;
    /**
     * Listados solo de las categorias que tienen datos
     */
    private Map<ModuloCodefacEnum,List<CategoriaMenuEnum>> categoriasActivasMap;
    
    /**
     * Listado de todas las ventanas disponibles en el sistema
     */
    private List<VentanaEnum> ventanasDisponibles;

    public MenuCodefacRespuesta() {
        modulosDisponibles=new ArrayList<ModuloCodefacEnum>();
        ventanasDisponibles=new ArrayList<VentanaEnum>();
        categoriasActivasMap=new HashMap<ModuloCodefacEnum,List<CategoriaMenuEnum>> ();
        
    }
    
    public void agregarCategoria(ModuloCodefacEnum moduloEnum,CategoriaMenuEnum categoriaEnum)
    {
        List<CategoriaMenuEnum> categorias=categoriasActivasMap.get(moduloEnum);
        if(categorias==null)
        {
            categorias=new ArrayList<CategoriaMenuEnum>();
            categorias.add(categoriaEnum);
            categoriasActivasMap.put(moduloEnum, categorias);
        }
        else
        {
            categorias.add(categoriaEnum);
        }
    }
    
    public List<VentanaEnum> buscarVentanasPorCategoriaYModulo(ModuloCodefacEnum moduloEnum,CategoriaMenuEnum categoriaEnum)
    {
        List<VentanaEnum> ventanasRespuesta=new ArrayList<VentanaEnum>();
        for (VentanaEnum ventanasDisponible : ventanasDisponibles) {
            if(ventanasDisponible.getModulo().equals(moduloEnum) && ventanasDisponible.getCategoriaMenu().equals(categoriaEnum))
            {
                ventanasRespuesta.add(ventanasDisponible);
            }
        }
        return ventanasRespuesta;
    }

    public List<ModuloCodefacEnum> getModulosDisponibles() {
        return modulosDisponibles;
    }

    public void setModulosDisponibles(List<ModuloCodefacEnum> modulosDisponibles) {
        this.modulosDisponibles = modulosDisponibles;
    }

    public Map<ModuloCodefacEnum, List<CategoriaMenuEnum>> getCategoriasActivasMap() {
        return categoriasActivasMap;
    }

    public void setCategoriasActivasMap(Map<ModuloCodefacEnum, List<CategoriaMenuEnum>> categoriasActivas) {
        this.categoriasActivasMap = categoriasActivas;
    }

    public List<VentanaEnum> getVentanasDisponibles() {
        return ventanasDisponibles;
    }

    public void setVentanasDisponibles(List<VentanaEnum> ventanasDisponibles) {
        this.ventanasDisponibles = ventanasDisponibles;
    }
    
    
    
    
}
