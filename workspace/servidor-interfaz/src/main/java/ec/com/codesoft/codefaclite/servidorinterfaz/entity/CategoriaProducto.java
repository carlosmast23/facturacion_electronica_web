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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author CodesoftDesarrollo
 */
@Entity
@Table(name = "CATEGORIA_PRODUCTO")
@XmlRootElement
public class CategoriaProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CATPRODUCTO_ID")
    private Long idCatProducto;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "IMAGEN_PATH")
    private String imagenPath;
    @Column(name = "ESTADO")
    private String estado;
    
    @JoinColumn(name = "EMPRESA_ID")
    private Empresa empresa;

    public Long getIdCatProducto() {
        return idCatProducto;
    }

    public void setIdCatProducto(Long idCatProducto) {
        this.idCatProducto = idCatProducto;
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

    public String getImagenPath() {
        return imagenPath;
    }

    public void setImagenPath(String imagenPath) {
        this.imagenPath = imagenPath;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
        
    public GeneralEnumEstado getEstadoEnum() {
        return GeneralEnumEstado.getEnum(estado);
    }

    public void setEstadoEnum(GeneralEnumEstado estadoEnum) {
        this.estado = estadoEnum.getEstado();
    }
    

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.idCatProducto);
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
        final CategoriaProducto other = (CategoriaProducto) obj;
        if (!Objects.equals(this.idCatProducto, other.idCatProducto)) {
            return false;
        }
        return true;
    }
    
    

}
