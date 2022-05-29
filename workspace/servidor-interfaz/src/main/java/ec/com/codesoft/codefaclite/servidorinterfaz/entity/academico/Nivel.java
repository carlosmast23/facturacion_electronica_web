/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "NIVEL")
@XmlRootElement
public class Nivel implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIVEL_ID")
    private Long idNivel;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ORDEN")
    private Integer orden;
    @JoinColumn(name = "NIVELPOSTERIOR_ID")  
    @OneToOne
    private Nivel nivelPosterior;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ESTADO")
    private String estado;
    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(Long idNivel) {
        this.idNivel = idNivel;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Nivel getNivelPosterior() {
        return nivelPosterior;
    }

    public void setNivelPosterior(Nivel nivelPosterior) {
        this.nivelPosterior = nivelPosterior;
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
    
    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.idNivel);
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
        final Nivel other = (Nivel) obj;
        if (!Objects.equals(this.idNivel, other.idNivel)) {
            return false;
        }
        return true;
    }
    
    public GeneralEnumEstado getEstadoEnum()
    {
        return GeneralEnumEstado.getEnum(estado);
    }
    
}
