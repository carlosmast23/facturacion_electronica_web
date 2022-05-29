/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoProveedor;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author Carlos
 */
@Entity
@Table(name = "ORDEN_COMPRA_DETALLE")
public class OrdenCompraDetalle implements Serializable{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * TODO: Este valor de la cantidad se debe poner como BigDecimal
     */
    @Column(name = "CANTIDAD")
    private Integer cantidad;

    @Column(name = "PRECIO_UNITARIO")
    private BigDecimal precioUnitario;

    @Column(name = "DESCUENTO")
    private BigDecimal descuento;

    @Column(name = "VALOR_ICE")
    private BigDecimal valorIce;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "TOTAL")
    private BigDecimal total;

    @Column(name = "IVA")
    private BigDecimal iva;
    
    @JoinColumn(name = "PRODUCTO_PROVEEDOR_ID")
    @ManyToOne
    private ProductoProveedor productoProveedor;
    
    @JoinColumn(name="ORDEN_COMPRA_ID")
    @ManyToOne(optional = false)
    private OrdenCompra ordenCompra;
    

    public OrdenCompraDetalle() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getValorIce() {
        return valorIce;
    }

    public void setValorIce(BigDecimal valorIce) {
        this.valorIce = valorIce;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public ProductoProveedor getProductoProveedor() {
        return productoProveedor;
    }

    public void setProductoProveedor(ProductoProveedor productoProveedor) {
        this.productoProveedor = productoProveedor;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
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
        final OrdenCompraDetalle other = (OrdenCompraDetalle) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
    //Metodos personalizados
    
    /**
     * Metodo que devuelve el subtotal de la cantidad por el precio unitario y
     * menos el descuento
     *
     * @return
     */
    public BigDecimal getSubtotal() {
        return new BigDecimal(cantidad + "").multiply(precioUnitario).subtract(descuento);
    }

    public BigDecimal calcularValorIva() {
        //Todo: revisar el valor del iva 12 que esta quemado
        return getSubtotal().multiply(new BigDecimal("0.12"));
    }
    
    public BigDecimal calcularTotal()
    {
        //Todo: revisar el valor del iva 12 que esta quemado
        return getSubtotal().multiply(new BigDecimal("1.12"));
    }

}
