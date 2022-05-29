/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "IMPUESTO_DETALLE")
@XmlRootElement
public class ImpuestoDetalle implements Serializable
{
    public static final Integer CODIGO_IVA_DOCE=2;
    public static final Integer CODIGO_IVA_CERO=0;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID_IMPUESTO_DETALLE")
    private Long idImpuestoDetalle;
    
    @Column (name= "CODIGO")
    private Integer codigo;
    
    @Column (name= "NOMBRE")
    private String nombre;
    
    /**
     * El porcentaje esta guardado en decimales ejemplo 12% (0,12)
     */
    @Column (name= "PORCENTAJE")
    private BigDecimal porcentaje;
    
    /**
     * La tarifa es el mismo valor del porcentaje pero expresado en un número entero
     */
    @Column (name = "TARIFA")
    private Integer tarifa;
    
    @Column (name= "DESCRIPCION")
    private String descripcion;
    
    @Column (name= "FECHA_INICIO")
    private Date fechaInicio;
    
    @Column (name= "FECHA_FIN")
    private Date fechaFin;
        
    //@ManyToOne
    @JoinColumn(name="ID_IMPUESTO")
    @ManyToOne(optional = false)
    private Impuesto impuesto;
    
    
    public Long getIdImpuestoDetalle() {
        return idImpuestoDetalle;
    }

    public void setIdImpuestoDetalle(Long idImpuestoDetalle) {
        this.idImpuestoDetalle = idImpuestoDetalle;
    }

    public Impuesto getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Impuesto impuesto) {
        this.impuesto = impuesto;
    }

    

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
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

    
    public Integer getTarifa() {
        return tarifa;
    }

    public void setTarifa(Integer tarifa) {
        this.tarifa = tarifa;
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

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.idImpuestoDetalle);
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
        final ImpuestoDetalle other = (ImpuestoDetalle) obj;
        if (!Objects.equals(this.idImpuestoDetalle, other.idImpuestoDetalle)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
