/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.reporte;

/**
 *
 * @author
 */
public class GuiaRemisionProductosReporte {
    private String cantidad;
    private String descripcion;
    private String codigo_principal;
    private String codigo_secundario;

    public GuiaRemisionProductosReporte() {
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

    public String getCodigo_principal() {
        return codigo_principal;
    }

    public void setCodigo_principal(String codigo_principal) {
        this.codigo_principal = codigo_principal;
    }

    public String getCodigo_secundario() {
        return codigo_secundario;
    }

    public void setCodigo_secundario(String codigo_secundario) {
        this.codigo_secundario = codigo_secundario;
    }
    
    
}
