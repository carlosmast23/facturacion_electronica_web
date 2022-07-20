/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
import java.util.ArrayList;
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
@Table(name = "CAJA_PERMISO")
@XmlRootElement
public class CajaPermiso implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;
    
    @JoinColumn(name = "CAJA_ID")
    private Caja caja;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cajaPermiso", fetch = FetchType.EAGER)
    private List<TurnoAsignado> turnoAsignadoList; 
   
    public CajaPermiso() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
    public GeneralEnumEstado getEstadoEnum(){
        return GeneralEnumEstado.getEnum(estado);
    }
    
    public void setEstadoEnum(GeneralEnumEstado generalEnumEstado){
        if(generalEnumEstado == null)
            this.estado = null;
        else
            this.estado = generalEnumEstado.getEstado();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public List<TurnoAsignado> getTurnoAsignadoList() {
        return turnoAsignadoList;
    }

    public void setTurnoAsignadoList(List<TurnoAsignado> turnoAsignadoList) {
        this.turnoAsignadoList = turnoAsignadoList;
    }
    
    public void addTurnoAsignado(TurnoAsignado turnoAsignado)
    {
        if(turnoAsignadoList==null)
        {
            turnoAsignadoList=new ArrayList<>();
        }
        
        turnoAsignado.setCajaPermiso(this);
        turnoAsignadoList.add(turnoAsignado);
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final CajaPermiso other = (CajaPermiso) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Caja: " + caja.getNombre() + " - Punto Emisión: " + caja.getPuntoEmision();
    }
}
