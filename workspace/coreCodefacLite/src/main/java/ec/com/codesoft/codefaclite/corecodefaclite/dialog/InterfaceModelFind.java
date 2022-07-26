/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.dialog;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author
 */
public interface InterfaceModelFind<T>
{
    public abstract Vector<ColumnaDialogo> getColumnas();
    /**
     * Obtiene la consulta segun el parametro pasado a la funcion que correspone
     * al campo de texto del dialogo
     * @param AliasParam parametro de busqueda del dialogo para armar el query
     * @return 
     */
    public abstract QueryDialog getConsulta(String filter);
    
    
    public abstract void agregarObjeto(T t,Vector dato);
    //public abstract Boolean buscarObjeto(T t,Object valor);
    public abstract Vector<String> getNamePropertysObject();
    
}
