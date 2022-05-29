/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "PRODUCTO_PROVEEDOR")
public class ProductoProveedor implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column (name = "ID")
    private Long  id;
    
    @Column (name = "ESTADO")
    private String estado;
    
    @Column (name = "DESCRIPCION")
    private String descripcion;
    

    /**
     * Este campo me permite tener guardado en el ultimo costo del producto por
     * cada proveedor especialmente util si requiero sacar un listado de precios
     * por cada proveedor
     */
    @Column (name = "COSTO_ACTUAL")
    private BigDecimal costo;
    
    /**
     * Este campo nos va a permitir enlazar los codigos de proveedores con los
     * codigos internos de los productos especialmente util para pantalla como
     * el de cargar XML directo para poder cargar directamente los productos
     */
    @Column (name = "CODIGO_PROVEEDOR")
    private String codigoProveedor;
    
    @JoinColumn(name = "PRODUCTO_ID")
    private Producto producto;
    
    @JoinColumn(name = "PROVEEDOR_ID")
    private Persona proveedor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
    //public String getConIva() {
    //    return conIva;
    //}

    //public void setConIva(String conIva) {
    //    this.conIva = conIva;
    //}

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Persona getProveedor() {
        return proveedor;
    }

    public void setProveedor(Persona proveedor) {
        this.proveedor = proveedor;
    }

    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }
     
    
}
