/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author
 */
@Entity
@Table(name = "DETALLE_PRODUCTO_GUIA_REMISION")
public class DetalleProductoGuiaRemision implements Serializable{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "CODIGO_INTERNO")
    private String codigoInterno;
    @Column(name = "CODIGO_ADICIONAL")
    private String codigoAdicional;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "CANTIDAD")
    private Integer cantidad;
    
    @Column(name = "REFERENCIA_ID")
    private Long referenciaId; //Almacena la referencia al producto , TODO: verificar si tambien se graba cuando son de otro lado la referencia por ejemplo de presupuestos , por el momento se asume que viene de la tabla de productos
    

    @JoinColumn(name="DESTINATARIO_ID")
    @ManyToOne(optional = false)    
    private DestinatarioGuiaRemision destinatario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getCodigoAdicional() {
        return codigoAdicional;
    }

    public void setCodigoAdicional(String codigoAdicional) {
        this.codigoAdicional = codigoAdicional;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Long getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(Long referenciaId) {
        this.referenciaId = referenciaId;
    }

    public DestinatarioGuiaRemision getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(DestinatarioGuiaRemision destinatario) {
        this.destinatario = destinatario;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final DetalleProductoGuiaRemision other = (DetalleProductoGuiaRemision) obj;
        
        if(this.id==null)
        {
            return super.equals(obj);
        }
        else
        {        
            if (!Objects.equals(this.id, other.id)) {
                return false;
            }
        }
        return true;
    }
    
    
    
    
    
}
