/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Carlos
 */
public class Compensacion implements Serializable{
    private String codigo;
    private BigDecimal tarifa;
    private BigDecimal valor;
    
    public Compensacion() {
    }

    @XmlElement(name = "codigo")
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlElement(name = "tarifa")
    public BigDecimal getTarifa() {
        return tarifa;
    }

    public void setTarifa(BigDecimal tarifa) {
        this.tarifa = tarifa;
    }

    @XmlElement(name = "valor")
    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

}
