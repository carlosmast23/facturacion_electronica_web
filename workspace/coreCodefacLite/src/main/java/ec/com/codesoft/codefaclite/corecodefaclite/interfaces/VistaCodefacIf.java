/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.interfaces;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 *
 * @auhor
 */
public interface VistaCodefacIf {
        /**
     * Este metodo tiene el objetivo de funcionar despues de construir el objeto para interactuar con las variables inyectadas en el formulario
     * por ejemplo con las variables de session , o con funcionalidades del formulario padre
     */
    public abstract void iniciar() throws ExcepcionCodefacLite,RemoteException;
    /**
     * Metodo que se debe implementar si se desea controlar el proceso del boton nuevo
     */
    public abstract void nuevo() throws ExcepcionCodefacLite,RemoteException;
    public abstract void grabar() throws ExcepcionCodefacLite,RemoteException;
    public abstract void editar() throws ExcepcionCodefacLite,RemoteException;
    public abstract void eliminar() throws ExcepcionCodefacLite,RemoteException;
    public abstract void imprimir() throws ExcepcionCodefacLite,RemoteException;
    public abstract void actualizar() throws ExcepcionCodefacLite,RemoteException;
        /**
     * Este metodo se ejecutara despues de grabar o borrar los datos
     * Se deben programar solo metodos personalizados que no se puedan
     * programar con las etiquetas de validacion.
     * Ejemplo:
     *  - Seleccionar ele valor por defecto de un combo box
     *  - Limpiar una tabla de valor agregados
     * 
     * Nota: Este campo tambien sirve para limpiar las variables que se usaron
     * para dejar en blanco los valores
     */
    public abstract void limpiar();
    
        /**
     * Nombre del formulario 
     * @return nombre del formulario
     */
    //public abstract String getNombre();
    
    /**
     * URL de la ayuda que se mostrara en la pantalla auxiliar cuando presionesn
     * el boton de ayuda
     * @return Link de la pagina que contiene las ayudas
     */
    public abstract String getURLAyuda();
    
        /**
     * Implementa una lista de los permisos de esa pantalla para cada tipo de perfil
     * existentes , si la ventana no implementa ningun metodo o recibe null , automaticamente
     * habilita la ventana para todos los tipos de perfiles
     * @return 
     */
    public abstract List<String> getPerfilesPermisos();
    
    /**
     * Obtiene el dialogo que se va a usar para consultar los datos de
     * formulario
     *
     * @return
     */
    public abstract InterfaceModelFind obtenerDialogoBusqueda();

    /**
     * Metodo donde se establece como se van a cargar los datos en pantalla
     * despues que se abra el dialogo
     *
     * @param entidad
     */
    public abstract void cargarDatosPantalla(Object entidad);
    
        /**
     * Habilitar los botones que van a estar disponibles para la pantalla
     * Ejemplo:
    * <pre>
    * <code>
    * Map<Integer,Boolean> permisos=new HashMap<Integer,Boolean>();
    * permisos.put(GeneralPanelInterface.BOTON_NUEVO,true);
    * return permisos;
    * </code>
    * </pre>
     * @return 
     */
    public abstract Map<Integer,Boolean> permisosFormulario();
    
}
