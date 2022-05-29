/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.reportData;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class FechaCaducidadData  implements Serializable{
    
    private String codigoPersonalizado;
    private String nombreBodega;
    private String codigoLote;
    private String nombreProducto;
    private String fechaCaducidad;
    private String stock;
    private String valorUnitario;

    public FechaCaducidadData(String codigoPersonalizado, String nombreBodega, String codigoLote, String nombreProducto, String fechaCaducidad, String stock, String valorUnitario) {
        this.codigoPersonalizado = codigoPersonalizado;
        this.nombreBodega = nombreBodega;
        this.codigoLote = codigoLote;
        this.nombreProducto = nombreProducto;
        this.fechaCaducidad = fechaCaducidad;
        this.stock = stock;
        this.valorUnitario = valorUnitario;
    }

    public String getCodigoPersonalizado() {
        return codigoPersonalizado;
    }

    public void setCodigoPersonalizado(String codigoPersonalizado) {
        this.codigoPersonalizado = codigoPersonalizado;
    }

    public String getNombreBodega() {
        return nombreBodega;
    }

    public void setNombreBodega(String nombreBodega) {
        this.nombreBodega = nombreBodega;
    }

    public String getCodigoLote() {
        return codigoLote;
    }

    public void setCodigoLote(String codigoLote) {
        this.codigoLote = codigoLote;
    }
    

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(String valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    /*@Override
    public List<TipoDato> getDatos() {
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();        
        tiposDatos.add(new TipoDato(this.codigoPersonalizado,Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.nombreBodega,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.codigoLote, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.nombreProducto, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.fechaCaducidad, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.stock,Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.valorUnitario, Excel.TipoDataEnum.NUMERO));
        
        
        return tiposDatos;
    }
    */
    
    
}
