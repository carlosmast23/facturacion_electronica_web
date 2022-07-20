/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @auhor
 */
@Entity
@Table(name = "CAJA")
@XmlRootElement
public class Caja implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @JoinColumn(name = "SUCURSAL_ID")
    private Sucursal sucursal;
    
    @JoinColumn(name = "PUNTO_EMISION_ID")
    private PuntoEmision puntoEmision;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "caja", fetch = FetchType.EAGER)
    private List<CajaPermiso> cajasPermiso;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "caja", fetch = FetchType.EAGER)
    private List<CajaSession> cajasSession;

    public Caja() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public CajaEnum getEstadoEnum() {
        return CajaEnum.getEnum(estado);
    }

    public void setEstadoEnum(CajaEnum estadoEnum) {
        if(estadoEnum==null)
        {
            this.estado=null;
        }
        else
        {
            this.estado = estadoEnum.getEstado();
        }
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public PuntoEmision getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(PuntoEmision puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public List<CajaPermiso> getCajasPermiso() {
        return cajasPermiso;
    }

    public void setCajasPermiso(List<CajaPermiso> cajasPermiso) {
        this.cajasPermiso = cajasPermiso;
    }

    public List<CajaSession> getCajasSession() {
        return cajasSession;
    }

    public void setCajasSession(List<CajaSession> cajasSession) {
        this.cajasSession = cajasSession;
    }  
    
    /*
    * Equals and Hascode
    */

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Caja other = (Caja) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + nombre + " - Punto Emisión: " + puntoEmision.getPuntoEmision();
    }
}
