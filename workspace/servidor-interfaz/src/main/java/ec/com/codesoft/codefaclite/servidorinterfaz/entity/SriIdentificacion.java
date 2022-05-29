/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "SRI_IDENTIFICACION")
public class SriIdentificacion implements Serializable{
    
    public static final String CLIENTE="VENTA"; 
    public static final String PROVEEDOR="COMPRA"; 
    
    public static String RUC_IDENTIFICACION="04";
    public static String CEDULA_IDENTIFICACION="05";

    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "TIPO_TRANSACCION")
    private String tipoTransaccion;

    @Column(name = "TIPO_IDENTIFICACION")
    private String tipoIdentificacionNombre;
    
    @Column(name = "TIPO_IDENTIFICACION_LETRA")
    private String tipoIdentificacion;

    @Column(name = "FECHA_INICIO")
    private Date fechaInicio;

    @Column(name = "FECHA_FIN")
    private Date fechaFin;

    public SriIdentificacion() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public String getTipoIdentificacionNombre() {
        return tipoIdentificacionNombre;
    }

    public void setTipoIdentificacionNombre(String tipoIdentificacionNombre) {
        this.tipoIdentificacionNombre = tipoIdentificacionNombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    public void getTipoIdentificacionEnum()
    {
        Persona.TipoIdentificacionEnum.obtenerPorLetra(tipoIdentificacion);
    }

    @Override
    public String toString() {
        return UtilidadesTextos.acortarTexto(tipoIdentificacionNombre,25);
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SriIdentificacion other = (SriIdentificacion) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    public enum  tipoTransaccionEnum
    {
        COMPRA("COMPRA"),VENTA("VENTA");

        private tipoTransaccionEnum(String nombre) {
            this.nombre = nombre;
        }
        
        
        
        private String nombre;

        public String getNombre() {
            return nombre;
        }

        @Override
        public String toString() {
            return "tipoTransaccionEnum{" + "nombre=" + nombre + '}';
        }
        
        
        
    }
    
    
}
