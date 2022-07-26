/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
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

/**
 *
 * @author
 */
@Entity
@Table(name = "PERFIL")
public class Perfil implements Serializable{
    
    public static final String PERFIl_ADMINISTRADOR="ADMIN";
    public static final String PERFIl_OPERADOR="OPERADOR";
    
    public static final String PERFIL_GRATIS="Default";
    
    public static final String PERFIL_DEFECTO="defecto";
    
    private static final long serialVersionUID = 1L;
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private Long  id;
    
    @Column (name = "NOMBRE")
    private String nombre;
    
    @Column (name = "DESCRIPCION")
    private String descripcion;
    
    @Column (name = "ESTADO")
    private String estado;
    
    @JoinColumn(name = "EMPRESA_ID")
    private Empresa empresa;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perfil",fetch = FetchType.EAGER)
    private List<PermisoVentana> ventanasPermisos;

    public Perfil() {
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
    
    public GeneralEnumEstado getEstadoEnum() {
        return GeneralEnumEstado.getEnum(estado);
    }

    public void setEstadoEnum(GeneralEnumEstado estadoEnum) {
        this.estado = estadoEnum.getEstado();
    }

    public List<PermisoVentana> getVentanasPermisos() {
        return ventanasPermisos;
    }

    public void setVentanasPermisos(List<PermisoVentana> ventanasPermisos) {
        this.ventanasPermisos = ventanasPermisos;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final Perfil other = (Perfil) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    

    public void addPermisoVentana(PermisoVentana permisoVentana)
    {
        if(this.ventanasPermisos==null)
        {
            this.ventanasPermisos=new ArrayList<PermisoVentana>();
        }
        permisoVentana.setPerfil(this);
        this.ventanasPermisos.add(permisoVentana);
        
    }
    

    
}
