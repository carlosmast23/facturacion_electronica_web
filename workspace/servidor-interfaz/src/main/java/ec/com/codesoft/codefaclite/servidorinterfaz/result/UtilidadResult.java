/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.result;

import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author DellWin10
 */
public class UtilidadResult extends AbstractResult{
    
    private String secuencial;
    
    public Date fechaEmision;
    
    private String razonSocial;
            
    public String identificacion;
    
    public Long facturaId;
    
    public BigDecimal subtotal;
    
    public BigDecimal costo;
    
    public BigDecimal utilidad;
    
    private String fechaEmisionStr;

    
    @Override
    public void constructor(Object[] dato) {
        secuencial=(String) dato[0];
        fechaEmision=(Date) dato[1];
        razonSocial=(String) dato[2];
        identificacion=(String)dato[3];
        facturaId=(Long) dato[4];
        subtotal=(BigDecimal) dato[5];
        costo=(BigDecimal) dato[6];
        utilidad=(BigDecimal) dato[7];
        
        //Setear la fecha de emision
        fechaEmisionStr=ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA.format(fechaEmision);
        
        if(costo==null)
        {
            costo=BigDecimal.ZERO;
        }
        
        if(utilidad==null)
        {
            utilidad=BigDecimal.ZERO;
        }
        costo.setScale(2, RoundingMode.HALF_UP);
        utilidad.setScale(2, RoundingMode.HALF_UP);
    }

    public String getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(String secuencial) {
        this.secuencial = secuencial;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public Long getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Long facturaId) {
        this.facturaId = facturaId;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public BigDecimal getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(BigDecimal utilidad) {
        this.utilidad = utilidad;
    }

    public String getFechaEmisionStr() {
        return fechaEmisionStr;
    }

    public void setFechaEmisionStr(String fechaEmisionStr) {
        this.fechaEmisionStr = fechaEmisionStr;
    }
    
    

    
}

