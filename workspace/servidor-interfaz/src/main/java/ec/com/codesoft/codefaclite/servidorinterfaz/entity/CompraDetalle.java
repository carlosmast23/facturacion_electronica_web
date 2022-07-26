/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.sri.SriSustentoComprobanteEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author
 */
@Entity
@Table(name = "COMPRA_DETALLE")
public class CompraDetalle implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(name = "PRODUCTO_ID")
    //private Long productoId;
    @Column(name = "CANTIDAD")
    private BigDecimal cantidad;
    @Column(name = "PRECIO_UNITARIO")
    private BigDecimal precioUnitario;
    @Column(name = "DESCUENTO")
    private BigDecimal descuento;
    @Column(name = "VALOR_ICE")
    private BigDecimal valorIce;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    /**
     * Todo: Revisar como corregir este problema porque el total se esta grabando el subtotal sin iva y no le modificado porque la compra funciona con esta logica y genera problemas
     */
    @Column(name = "TOTAL")
    private BigDecimal total;
    @Column(name = "IVA")
    private BigDecimal iva;
    
    @JoinColumn(name="COMPRA_ID")
    @ManyToOne(optional = false)
    private Compra compra;
    
    @JoinColumn(name = "PRODUCTO_PROVEEDOR_ID")
    @ManyToOne
    private ProductoProveedor productoProveedor;
    
    @JoinColumn(name = "SRI_RETENCION_IVA_ID")
    @ManyToOne
    private SriRetencionIva sriRetencionIva;
    
    @JoinColumn(name= "SRI_RETENCION_RENTA_IVA_ID")
    @ManyToOne
    private SriRetencionRenta sriRetencionRenta;
    
    @Column(name="VALOR_RETENCION_IVA")
    private BigDecimal valorSriRetencionIVA;
    
    @Column(name="VALOR_RETENCION_RENTA")
    private BigDecimal valorSriRetencionRenta;
    
    @Column(name = "CODIGO_SUSTENTO_SRI")
    private String codigoSustentoSri;
        
    /**
     * Este campo me permite guardar cual fue el codigo original del proveedor cuando exista un codigo
     * por el momento no lo hago persistente y solo lo uso para poder enviar informacion a la pantalla de COMPRA XML
     */
    //@Column (name = "CODIGO_PROVEEDOR")
    @Transient
    private String codigoProveedor;
    
    public CompraDetalle() {
    }
    
    public Long getId() {
        return id;
    }



    public BigDecimal getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public BigDecimal getValorIce() {
        return valorIce;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
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
    
    //TODO: Metodo temporal hasta poder ver como hacer para grabar correctamente
    //Parece que va a tocar grabar todos los decimales y en este punto redondear
    public BigDecimal obtenerIvaCalculado()
    {
        if (iva.compareTo(BigDecimal.ZERO) == 0) {
            return iva;
        }
        
        return this.total.multiply(new BigDecimal("0.12")).setScale(2, RoundingMode.HALF_UP);
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public ProductoProveedor getProductoProveedor() {
        return productoProveedor;
    }

    public void setProductoProveedor(ProductoProveedor productoProveedor) {
        this.productoProveedor = productoProveedor;
    }
    
    /**
     * Metodo personalizados
     */
    
    public BigDecimal getBaseImponibleRenta()
    {
        return getSubtotal().setScale(2,BigDecimal.ROUND_HALF_UP);
    }
    
    
    public BigDecimal getBaseImponibleIva()
    {
        return calcularValorIva().setScale(2,BigDecimal.ROUND_HALF_UP);
    }

    
    /**
     * Metodo que devuelve el subtotal de la cantidad por el precio unitario y menos el descuento
     * @return 
     */
    public BigDecimal getSubtotal()
    {
        return new BigDecimal(cantidad+"").multiply(precioUnitario).subtract(descuento);
    }
    
    public void calcularSubtotalSinIva()
    {
        total=getSubtotal();
    }
    /**
     * Calcula el valor del iva 
     * @return 
     */
    public BigDecimal calcularValorIva()
    {
        //Todo: revisar el valor del iva 12 que esta quemado
        return getSubtotal().multiply(new BigDecimal("0.12"));
    }
    
    public BigDecimal calcularTotal()
    {
        //Todo: revisar el valor del iva 12 que esta quemado
        return getSubtotal().multiply(new BigDecimal("1.12"));
    }

    public SriRetencionIva getSriRetencionIva() {
        return sriRetencionIva;
    }

    public void setSriRetencionIva(SriRetencionIva sriRetencionIva) {
        this.sriRetencionIva = sriRetencionIva;
    }

    public SriRetencionRenta getSriRetencionRenta() {
        return sriRetencionRenta;
    }

    public void setSriRetencionRenta(SriRetencionRenta sriRetencionRenta) {
        this.sriRetencionRenta = sriRetencionRenta;
    }

    public BigDecimal getValorSriRetencionIVA() {
        return valorSriRetencionIVA;
    }

    public void setValorSriRetencionIVA(BigDecimal valorSriRetencionIVA) {
        this.valorSriRetencionIVA = valorSriRetencionIVA;
    }

    public BigDecimal getValorSriRetencionRenta() {
        return valorSriRetencionRenta;
    }

    public void setValorSriRetencionRenta(BigDecimal valorSriRetencionRenta) {
        this.valorSriRetencionRenta = valorSriRetencionRenta;
    }

    public String getCodigoSustentoSri() {
        return codigoSustentoSri;
    }

    public void setCodigoSustentoSri(String codigoSustentoSri) {
        this.codigoSustentoSri = codigoSustentoSri;
    }
    
    public void setCodigoSustentoSriEnum(SriSustentoComprobanteEnum codigoEnum) {
        this.codigoSustentoSri = codigoEnum.getCodigo();
    }
    
    public SriSustentoComprobanteEnum getCodigoSustentoSriEnum() {
        return SriSustentoComprobanteEnum.obtenerPorCodigo(codigoSustentoSri);        
    }

    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }
            
    
    
    /**
     * Metodos adicionales
     */
    
    
    /**
     * Verificar si el producto esta cobrando iva 0
     * @return 
     */
    public Boolean isImpuestoIvaCero()
    {
        return getProductoProveedor().getProducto().getCatalogoProducto().getIva().getPorcentaje().compareTo(BigDecimal.ZERO)==0;
    }
    
    /**
     * Obtener el valor del total porque se esta grabando mal en los detalles
     * @return 
     */
    public BigDecimal getTotalCalculado() {
        //TODO modificado por el momento hasta solucionar el tema de grabar correctamente los decimales del iva
        return getSubtotal().add(obtenerIvaCalculado()).setScale(2, RoundingMode.HALF_UP);
        //return getSubtotal().add(iva);
    }
    
    
    
    
}
