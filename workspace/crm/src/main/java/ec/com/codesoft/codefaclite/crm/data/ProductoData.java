/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.data;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author
 */
public class ProductoData implements ExcelDatosInterface
{
    private String codigoPrincipal;
    private String tipoProducto;
    private String nombre;
    private String valorUnitario;
    private String iva;
    private String total;
    private String impuestoIva;
    private String categoria;

    public ProductoData() {
    }

    public String getCodigoPrincipal() {
        return codigoPrincipal;
    }

    public void setCodigoPrincipal(String codigoPrincipal) {
        this.codigoPrincipal = codigoPrincipal;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(String valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public String getImpuestoIva() {
        return impuestoIva;
    }

    public void setImpuestoIva(String impuestoIva) {
        this.impuestoIva = impuestoIva;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    
    
    
    

    @Override
    public List<TipoDato> getDatos() 
    {
        List<TipoDato> tiposDatos = new ArrayList<>();
        tiposDatos.add(new TipoDato(this.codigoPrincipal,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.tipoProducto,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.nombre,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.categoria,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.valorUnitario,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.iva,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.total,Excel.TipoDataEnum.NUMERO));
        
        
        //tiposDatos.add(new TipoDato(this.impuestoIva,Excel.TipoDataEnum.TEXTO));
        return tiposDatos;
    }
    
    
}
