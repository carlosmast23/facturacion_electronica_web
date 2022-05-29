/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author DellWin10
 */
@MappedSuperclass
public class EntityAbstract<T> implements Serializable {
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    
    @Column(name = "FECHA_CREACION")
    protected Timestamp fechaCreacion;
    
    @Column(name = "FECHA_ULTIMA_EDICION")
    protected Timestamp fechaUltimaEdicion;
    
    @JoinColumn(name = "USUARIO_CREACION_ID")
    protected Usuario usuarioCreacion;
    
    @JoinColumn(name = "USUARIO_ULTIMA_EDICION_ID")
    protected Usuario usuarioUltimaEdicion;
    
    @Column(name = "ESTADO")
    protected String estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaUltimaEdicion() {
        return fechaUltimaEdicion;
    }

    public void setFechaUltimaEdicion(Timestamp fechaUltimaEdicion) {
        this.fechaUltimaEdicion = fechaUltimaEdicion;
    }

    public Usuario getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(Usuario usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Usuario getUsuarioUltimaEdicion() {
        return usuarioUltimaEdicion;
    }

    public void setUsuarioUltimaEdicion(Usuario usuarioUltimaEdicion) {
        this.usuarioUltimaEdicion = usuarioUltimaEdicion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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
        final EntityAbstract other = (EntityAbstract) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    ///                     METODOS PERSONALIZADOS
    ///////////////////////////////////////////////////////////////////////////
    
    //Metedos generalizados ya implementados pero no visibles porque no entodos loscasos voy a necesitar
    //Utilizo de forma de artificio para que cuando usen el caso más comun de EstadoEnum ya este programado
    public T getEstadoEnum() {        
        T estadoEnum=(T) GeneralEnumEstado.getEnum(estado);
        return estadoEnum;
    }

    public void setEstadoEnum(T estadoEnum) {
        GeneralEnumEstado estadoEnumOriginal=(GeneralEnumEstado) estadoEnum;
        this.estado = estadoEnumOriginal.getEstado();
    }
    
    
    
}
