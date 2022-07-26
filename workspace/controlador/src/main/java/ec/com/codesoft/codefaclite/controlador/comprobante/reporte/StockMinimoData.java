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
 * @author
 */
public class StockMinimoData implements ExcelDatosInterface{
    private String codigo;
    private String lote;
    private String producto;
    private String stock;
    private String cantidadMinima;
    private String categoria;
    private String ubicacion;
    private String costo;
    private String bodega;
    private BigDecimal pvp1;
    private BigDecimal utilidad1;
    
    private List<StockUnicoData> detalles;

    public StockMinimoData() {
        detalles=new ArrayList<StockUnicoData>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(String cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }
    
      

    public List<StockUnicoData> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<StockUnicoData> detalles) {
        this.detalles = detalles;
    }
    
    public void agregarDetalle(StockUnicoData stockUnicoData)
    {
        if(this.detalles==null)
        {
            this.detalles=new ArrayList<StockUnicoData>();
        }
        
        this.detalles.add(stockUnicoData);
    }

    public BigDecimal getPvp1() {
        return pvp1;
    }

    public void setPvp1(BigDecimal pvp1) {
        this.pvp1 = pvp1;
    }

    public BigDecimal getUtilidad1() {
        return utilidad1;
    }

    public void setUtilidad1(BigDecimal utilidad1) {
        this.utilidad1 = utilidad1;
    }
    
    
    

    @Override
    public List<TipoDato> getDatos() {
        
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();        
        tiposDatos.add(new TipoDato(this.codigo,Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.lote,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.bodega,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.producto, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.categoria, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.ubicacion, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.stock,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.pvp1,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.cantidadMinima, Excel.TipoDataEnum.NUMERO));        
        tiposDatos.add(new TipoDato(this.costo,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.utilidad1,Excel.TipoDataEnum.NUMERO));
        
        
        return tiposDatos;
    }
    
    
    
    
}
