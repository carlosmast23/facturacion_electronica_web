/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Robert
 */
@Entity
@Table(name = "INGRESO_CAJA")
@XmlRootElement
public class IngresoCaja implements Serializable
{
    /*
    * Atributos
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "VALOR")
    private BigDecimal valor;
    
    /*
     * Constructor
     */
    public IngresoCaja() {
    }

    /*
    * Foreing Key
    */
    @JoinColumn(name = "CAJA_SESSION_ID")
    @ManyToOne
    private CajaSession cajaSession;
    
    @JoinColumn(name = "FACTURA_ID")
    @ManyToOne
    private Factura factura;
    
    /*
    * Get and Set
    */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public CajaSession getCajaSession() {
        return cajaSession;
    }

    public void setCajaSession(CajaSession cajaSession) {
        this.cajaSession = cajaSession;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }
   
    /*
    * Equals
    */

    @Override
    public int hashCode() {
        int hash = 3;
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
        final IngresoCaja other = (IngresoCaja) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
