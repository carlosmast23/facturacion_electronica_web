/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "SRI_RETENCION_RENTA")
public class SriRetencionRenta implements Serializable {
    
    public static final String CODIGO_RETENCION_RENTA="1";
    public static final String CODIGO_RETENCION_IVA="2";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODIGO_SRI")
    private String codigo;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "PORCENTAJE")
    private BigDecimal porcentaje;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "FECHA_INICIO")
    private Date fechaInicio;

    @Column(name = "FECHA_FIN")
    private Date fechaFin;
    
    @JoinColumn(name = "RETENCION_ID")
    @ManyToOne    
    private SriRetencion retencion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public SriRetencion getRetencion() {
        return retencion;
    }

    public void setRetencion(SriRetencion retencion) {
        this.retencion = retencion;
    }
    
    

    @Override
    public String toString()
    {
        String nombreFormato=(this.nombre.length()>40)?this.nombre.substring(0,40)+"...":this.nombre;
        return this.codigo + " - " + nombreFormato + " - " + this.porcentaje+"%"; 
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final SriRetencionRenta other = (SriRetencionRenta) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
}
