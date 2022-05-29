/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "PUNTO_EMISION_USUARIO")
public class PuntoEmisionUsuario implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;
    
    @JoinColumn(name = "PUNTO_EMISION_ID")
    private PuntoEmision puntoEmision;
    
    @Column(name = "ESTADO")
    private String estado;

    public PuntoEmisionUsuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public PuntoEmision getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(PuntoEmision puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public GeneralEnumEstado getEstadoEnum() {
        return GeneralEnumEstado.getEnum(estado);
    }

    public void setEstadoEnum(GeneralEnumEstado enumEstado) {
        this.estado = enumEstado.getEstado();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.id);
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
        final PuntoEmisionUsuario other = (PuntoEmisionUsuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    

    @Override
    public String toString() {
        String codigoSucursal=(puntoEmision.getSucursal()!=null)?puntoEmision.getSucursal().getCodigoSucursalFormatoTexto():"Sin Asignar";
       
        //return usuario.getNick()+" (" + puntoEmision.getSucursal().getCodigoSucursalFormatoTexto()+"-"+puntoEmision.puntoEmisionFormatoTexto()+" )";
        return usuario.getNick()+" (" + codigoSucursal+"-"+puntoEmision.puntoEmisionFormatoTexto()+" )";
    }
    
    
    
}
