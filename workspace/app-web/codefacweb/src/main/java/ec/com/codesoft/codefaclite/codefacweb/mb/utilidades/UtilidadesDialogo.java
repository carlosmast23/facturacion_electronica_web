/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.utilidades;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @auhor
 */
public abstract class UtilidadesDialogo {
    
    public static void abrirDialogoBusqueda(InterfaceModelFind modeloBusqueda) {
        //find();
        //System.out.println("Abriendo dialogo busqueda");

        //Establecer objeto de la clase que tiene la implemetacion del dialogo de busqueda que necesito para construir el dialogo web
        //TODO: Solucion temporal porque es una gasto innesario de memoria , buscar otra forma
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sessionMap.put("busquedaClase", modeloBusqueda); 

        //Esstablecer porpiedades que se van a enviar al dialogo en map
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("draggable", false);
        options.put("modal", true);
        options.put("responsive", true);
        options.put("width", "80%");
        options.put("contentWidth", "100%");
        //responsive="true"
        //options.put("busquedaClase", new EmpleadoBusquedaDialogo() ); //TODO: Mando por defecto un dialogo por defecto
        String nombreDialogoBusqueda = "dialogo_busqueda";
        //PrimeFaces.current().dialog()
        PrimeFaces.current().dialog().openDynamic(nombreDialogoBusqueda, options, null);
    }
    
    public static void abrirDialogoFormulario(String nombreDialogoBusqueda,Map<String, List<String>> parametros)
    {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", true);
        options.put("draggable", false);
        options.put("modal", true);
        options.put("responsive", true);
        options.put("width", "70%");
        options.put("height", "90%");
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        //options.put("busquedaClase", new EmpleadoBusquedaDialogo() ); //TODO: Mando por defecto un dialogo por defecto
        //String nombreDialogoBusqueda = "cliente";
        

        Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put("isDialog", Arrays.asList("true")); //TODO: Parametrizar esta variable
        //params.put("tipo", Arrays.asList("cliente"));
        params.putAll(parametros);
        
        PrimeFaces.current().dialog().openDynamic(nombreDialogoBusqueda, options, params);
    }
    
}
