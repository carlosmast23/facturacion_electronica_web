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

/**
 *
 * @auhor
 */
@Entity
@Table(name = "MESA")
public class Mesa extends EntityAbstract<Mesa.MesaEstadoEnum>{

    public Mesa() {
    }
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NUMERO")
    private Integer numero;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "CAPACIDAD")
    private Integer capacidad;
    
    @Column(name = "ESTADO")
    private String estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
    @Override
    public MesaEstadoEnum getEstadoEnum() {
        return MesaEstadoEnum.getEnum(estado);
    }

    @Override
    public void setEstadoEnum(MesaEstadoEnum estadoEnum) {
        this.estado = estadoEnum.letra;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Mesa other = (Mesa) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    public enum MesaEstadoEnum 
    {
        LIBRE("L","Libre"),
        ELIMINADO("E","Eliminado"),
        OCUPADO("O","Ocupado");

        private MesaEstadoEnum(String letra, String nombre) {
            this.letra = letra;
            this.nombre = nombre;
        }
        
        private String letra;
        private String nombre;

        public String getLetra() {
            return letra;
        }

        public String getNombre() {
            return nombre;
        }
        
        public static MesaEstadoEnum getEnum(String letra) {
        for (MesaEstadoEnum enumerador : MesaEstadoEnum.values()) {
            if (enumerador.letra.equals(letra)) {
                return enumerador;
            }
        }
        return null;
    }
        
        
    }
    
        
}
