/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author
 */
@Entity
@Table(name = "NACIONALIDAD")
@XmlRootElement
public class Nacionalidad implements Serializable {
    
    public static String ISO_NACIONALIDAD_DEFECTO="ECU"; //TODO: Ver si esta variable debe estar como configuracion global

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "NACIONALIDAD_ID")
    private Long idNacionalidad;
    @Column(name = "PAIS_NAC")
    private String pais;
    @Column(name = "GENTILICIO_NAC")
    private String nombre;
    @Column(name = "ISO_NAC")
    private String iso;

    public Long getIdNacionalidad() {
        return idNacionalidad;
    }

    public void setIdNacionalidad(Long idNacionalidad) {
        this.idNacionalidad = idNacionalidad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    
    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.idNacionalidad);
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
        final Nacionalidad other = (Nacionalidad) obj;
        if (!Objects.equals(this.idNacionalidad, other.idNacionalidad)) {
            return false;
        }
        return true;
    }
    

}
