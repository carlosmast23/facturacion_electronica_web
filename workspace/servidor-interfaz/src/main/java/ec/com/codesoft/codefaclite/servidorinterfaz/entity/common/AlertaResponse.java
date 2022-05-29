/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.common;

import java.io.Serializable;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class AlertaResponse implements Serializable{
    
    public TipoAdvertenciaEnum tipoAdvertenciaEnum;
    public String descripcion;
    public String solucion;

    public AlertaResponse(TipoAdvertenciaEnum tipoAdvertenciaEnum, String descripcion, String solucion) {
        this.tipoAdvertenciaEnum = tipoAdvertenciaEnum;
        this.descripcion = descripcion;
        this.solucion = solucion;
    }

    public TipoAdvertenciaEnum getTipoAdvertenciaEnum() {
        return tipoAdvertenciaEnum;
    }

    public void setTipoAdvertenciaEnum(TipoAdvertenciaEnum tipoAdvertenciaEnum) {
        this.tipoAdvertenciaEnum = tipoAdvertenciaEnum;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSolucion() {
        return solucion;
    }

    public void setSolucion(String solucion) {
        this.solucion = solucion;
    }
    
    
    
    public enum TipoAdvertenciaEnum
    {
        INFORMATIVO("Info"),
        ADVERTENCIA("Advertencia"),
        GRAVE("Grave");
        
        private String grave;

        private TipoAdvertenciaEnum(String grave) {
            this.grave = grave;
        }

        @Override
        public String toString() {
            return grave;
        }
        
        
    }
    
    
}
