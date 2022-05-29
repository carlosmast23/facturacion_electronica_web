/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author DellWin10
 */
@Entity
@Table(name = "LOTE")
public class Lote extends EntityAbstract<GeneralEnumEstado>{    
    
    @Column(name = "CODIGO")
    private String codigo;
    
    @Column(name = "FECHA_ELABORACION")
    protected Date fechaElaboracion;
    
    @Column(name = "FECHA_VENCIMIENTO")
    protected Date fechaVencimiento;
    
    @JoinColumn(name = "PRODUCTO_ID")
    protected Producto producto;
    
    @JoinColumn(name = "EMPRESA_ID")
    protected Empresa empresa;
    
    /*@Column(name = "TOTAL")
    protected BigDecimal total;
    
    @Column(name = "STOCK")
    protected BigDecimal stock;*/

    public Lote() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(Date fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /*public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }*/
    
    
    ///////////////////////////////////////////////////////////////////////////
    ///                     METODOS PERSONALIZADOS
    ///////////////////////////////////////////////////////////////////////////

    /*@Override
    public void setEstadoEnum(GeneralEnumEstado estadoEnum) {
        super.setEstadoEnum(estadoEnum); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GeneralEnumEstado getEstadoEnum() {
        return super.getEstadoEnum(); //To change body of generated methods, choose Tools | Templates.
    }*/
        
    
}
