/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.reportdata;

/**
 *
 * @author
 */
public class DetalleFacturaFisicaData {
    
    private String cantidad;
    private String descripcion;
    private String valorUnitario;
    private String valorTotal;
    private String codigoPrincipal;
    private String descuentoDetalle;
    private String ivaDetalle;

    public DetalleFacturaFisicaData() {
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(String valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getCodigoPrincipal() {
        return codigoPrincipal;
    }

    public void setCodigoPrincipal(String codigoPrincipal) {
        this.codigoPrincipal = codigoPrincipal;
    }

    public String getDescuentoDetalle() {
        return descuentoDetalle;
    }

    public void setDescuentoDetalle(String descuentoDetalle) {
        this.descuentoDetalle = descuentoDetalle;
    }

    

    public String getIvaDetalle() {
        return ivaDetalle;
    }

    public void setIvaDetalle(String ivaDetalle) {
        this.ivaDetalle = ivaDetalle;
    }

    
    
    
}
