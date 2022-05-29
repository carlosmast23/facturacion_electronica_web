/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb;

import java.io.Serializable;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author Carlos
 */
public class FormaDePagoAts implements Serializable {
    private String formaPago;

    public FormaDePagoAts() {
    }
    
    @XmlValue()
    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.formaPago);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FormaDePagoAts other = (FormaDePagoAts) obj;
        if (!Objects.equals(this.formaPago, other.formaPago)) {
            return false;
        }
        return true;
    }
    
    
    
}
