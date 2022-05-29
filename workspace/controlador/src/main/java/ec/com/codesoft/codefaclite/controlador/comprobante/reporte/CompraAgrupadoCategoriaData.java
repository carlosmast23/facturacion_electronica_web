/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobante.reporte;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class CompraAgrupadoCategoriaData implements ExcelDatosInterface {
    private String categoria;
    private String producto;
    private String compra;
    private String fecha;
    private BigDecimal subtotalDoce;
    private BigDecimal subtotalCero;
    private BigDecimal subtotalDescuento;
    private BigDecimal iva;
    private BigDecimal total;
    private String sustentoSri;
    
    private String descripcionDetalle;
    private String descripcionCompra;
    
    

    public CompraAgrupadoCategoriaData() {
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getSubtotalDoce() {
        return subtotalDoce;
    }

    public void setSubtotalDoce(BigDecimal subtotalDoce) {
        this.subtotalDoce = subtotalDoce;
    }

    public BigDecimal getSubtotalCero() {
        return subtotalCero;
    }

    public void setSubtotalCero(BigDecimal subtotalCero) {
        this.subtotalCero = subtotalCero;
    }

    public BigDecimal getSubtotalDescuento() {
        return subtotalDescuento;
    }

    public void setSubtotalDescuento(BigDecimal subtotalDescuento) {
        this.subtotalDescuento = subtotalDescuento;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCompra() {
        return compra;
    }

    public void setCompra(String compra) {
        this.compra = compra;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSustentoSri() {
        return sustentoSri;
    }

    public void setSustentoSri(String sustentoSri) {
        this.sustentoSri = sustentoSri;
    }

    public String getDescripcionDetalle() {
        return descripcionDetalle;
    }

    public void setDescripcionDetalle(String descripcionDetalle) {
        this.descripcionDetalle = descripcionDetalle;
    }

    public String getDescripcionCompra() {
        return descripcionCompra;
    }

    public void setDescripcionCompra(String descripcionCompra) {
        this.descripcionCompra = descripcionCompra;
    }

    
    
    

    @Override
    public List<TipoDato> getDatos() {
        List<TipoDato> tiposDatos = new ArrayList<>();
        tiposDatos.add(new TipoDato(this.categoria,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.descripcionCompra,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.descripcionDetalle,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.producto,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.compra,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.sustentoSri,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.fecha,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.subtotalDoce,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.subtotalCero,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.subtotalDescuento,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.iva,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.total,Excel.TipoDataEnum.NUMERO));
        return tiposDatos;
    }
    
    
    
}
