/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core.components;

/**
 *
 * @author CARLOS_CODESOFT
 */
public interface ITableBindingAddData<T> {
    public Object[] addData(T value);
    //Evento que se ejecuta cuando un nuevo valor se selecciona en el modelo
    public void setData(T objetoOriginal,Object objetoModificado,Integer columnaModificada);
}
