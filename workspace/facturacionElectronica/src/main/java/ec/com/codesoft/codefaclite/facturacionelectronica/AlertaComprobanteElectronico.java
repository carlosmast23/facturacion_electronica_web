/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author
 */
public class AlertaComprobanteElectronico implements Serializable{
    public String mensaje;
    public int etapa; //TODO: ver si mejor de tener un numero convertir en un enum para llevar mas organizado este tema
    public TipoMensajeEnum tipoMensaje;

    public AlertaComprobanteElectronico(String mensaje, int etapa, TipoMensajeEnum tipoMensaje) {
        this.mensaje = mensaje;
        this.etapa = etapa;
        this.tipoMensaje = tipoMensaje;
    }
    
    
    public String obtenerMensaje()
    {
        return "Tipo:"+tipoMensaje+"\nMensaje:"+this.mensaje+"\n\n";
    }
    
    /**
     * Metodo que me permite unir un conjunto de alertas con mismo formato para mostrar al usuario
     * @param alertas
     * @return 
     */
    public static String unirTodasAlertas(List<AlertaComprobanteElectronico> alertas)
    {
        String mensajeCompleto = "";
        for (AlertaComprobanteElectronico alerta : alertas) {
            mensajeCompleto += alerta.obtenerMensaje();
        }
        return mensajeCompleto;
    }
    
    public enum TipoMensajeEnum
    {
        /**
         * Cuando sea un mensaje sin importancia que no afecta al proceso, pero se requiere comunicar al usuario
         */
        INFORMATIVO,
        /**
         * Cuando se un mensaje que afecta al proceso pero que de igual manera se puede concluir pero con alguna tarea pendiente
         */
        ADVERTENCIA,
        /**
         * Cuando el mensaje es muy importante corregir porque esta afectando al proceso , y apesar que termine el proceso no se garantiza que sea correcto
         */
        GRAVE,
        
        /**
         * Tipo de informe solo para errores fatales del proceso que se consideran excepcion
         * Todo: Ver si adicionales de las excepciones tambien se implementa este error para que el usuario pueda tener un control mas especifico del manejo de errores y todos sus mensajes
         */
        ERROR,
    }
}
