/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.reporte;

/**
 *
 * @author Carlos
 */
public class DetalleReporteData {
    private String codigo;
    private String descripcion;
    private String cantidad;
    private String precio_unitario;
    private String descuento;
    private String precio_total;
    private String subsidio;
    
    private String porcentajeIva;

    public DetalleReporteData() {
    }
    
    

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(String precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(String precio_total) {
        this.precio_total = precio_total;
    }

    public String getSubsidio() {
        return subsidio;
    }

    public void setSubsidio(String subsidio) {
        this.subsidio = subsidio;
    }

    public String getPorcentajeIva() {
        return porcentajeIva;
    }

    public void setPorcentajeIva(String porcentajeIva) {
        this.porcentajeIva = porcentajeIva;
    }
    
    
    
    
    
}
