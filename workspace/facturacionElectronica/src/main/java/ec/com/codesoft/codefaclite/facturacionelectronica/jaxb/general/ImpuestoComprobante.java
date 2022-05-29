/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
@XmlType(propOrder = {"codigo","codigoPorcentaje","tarifa","baseImponible","valor"})
public class ImpuestoComprobante implements Serializable{
    
    //Este codigo esta relacionado con IMPUESTO GENERAL (IVA,ICE...)
    private String codigo;
    
    //Este codigo esta relacionado con IMPUESTO_DETALLE (IVA 0, IVA 12 , IVA 14)
    private String codigoPorcentaje;
    private BigDecimal tarifa;
    private BigDecimal baseImponible;
    private BigDecimal valor;

    public ImpuestoComprobante() {
    }

    @XmlElement(name="codigo")
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlElement(name="codigoPorcentaje")
    public String getCodigoPorcentaje() {
        return codigoPorcentaje;
    }

    public void setCodigoPorcentaje(String codigoPorcentaje) {
        this.codigoPorcentaje = codigoPorcentaje;
    }

    @XmlElement(name="tarifa")
    public BigDecimal getTarifa() {
        return tarifa;
    }

    public void setTarifa(BigDecimal tarifa) {
        this.tarifa = tarifa;
    }

    @XmlElement(name="baseImponible")
    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    @XmlElement(name="valor")
    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    

}
