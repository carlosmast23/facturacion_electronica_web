/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.result;

import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class FechaCaducidadResult extends AbstractResult{
    
    private String codigoPersonalizado;
    private String nombreBodega;
    private String codigoLote;
    private String nombreProducto;
    private Date fechaCaducidad;
    private BigDecimal stock;
    private BigDecimal valorUnitario;

    @Override
    public void constructor(Object[] dato) 
    {
        this.codigoPersonalizado=(String) dato[0];
        this.nombreBodega=(String) dato[1];
        this.codigoLote=(String) dato[2];
        this.nombreProducto=(String) dato[3];
        this.fechaCaducidad=UtilidadesFecha.getFechaDeTimeStamp((Timestamp)dato[4]);
        this.stock=new BigDecimal((Long) dato[5]);
        this.valorUnitario=(BigDecimal) dato[6];
        
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

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
    
    
    
}
