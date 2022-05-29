/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobante.reporte;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class CompraDataReporte  implements ExcelDatosInterface
{
    private String preimpreso;
    private String documento;
    private String identificacion;
    private String nombre;
    private String fecha;
    private String subtotal;
    private String subtotal0;
    private String subtotal12;
    private String descuento;
    private String descuento0;
    private String descuento12;
    private String iva;
    private String total;
    private String autorizacion;

    public CompraDataReporte() {
       
    }
   
    public String getPreimpreso() {
        return preimpreso;
    }

    public void setPreimpreso(String preimpreso) {
        this.preimpreso = preimpreso;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getSubtotal0() {
        return subtotal0;
    }

    public void setSubtotal0(String subtotal0) {
        this.subtotal0 = subtotal0;
    }

    public String getSubtotal12() {
        return subtotal12;
    }

    public void setSubtotal12(String subtotal12) {
        this.subtotal12 = subtotal12;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getDescuento0() {
        return descuento0;
    }

    public void setDescuento0(String descuento0) {
        this.descuento0 = descuento0;
    }

    public String getDescuento12() {
        return descuento12;
    }

    public void setDescuento12(String descuento12) {
        this.descuento12 = descuento12;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
    
    

    @Override
    public List<TipoDato> getDatos() 
    {
        List<TipoDato> tiposDatos = new ArrayList<>();
        tiposDatos.add(new TipoDato(this.preimpreso,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.documento,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.autorizacion,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.identificacion,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.nombre,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.fecha,Excel.TipoDataEnum.FECHA));
        tiposDatos.add(new TipoDato(this.subtotal12,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.subtotal0,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.descuento,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.iva,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.total,Excel.TipoDataEnum.NUMERO));
        return tiposDatos;
    }
    
    
    
}
