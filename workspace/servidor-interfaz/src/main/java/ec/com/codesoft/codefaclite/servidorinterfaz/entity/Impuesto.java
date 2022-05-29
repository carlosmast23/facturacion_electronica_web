/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "IMPUESTO")
@XmlRootElement
public class Impuesto implements Serializable
{
    public static final String IVA="IVA";
    public static final String ICE="ICE";
    public static final String IRBPNR="IRBPNR";
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_IMPUESTO")
    private Long idImpuesto;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "CODIGO_SRI")
    private String codigoSri;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    //TODO: Esta relacion esta mal por que se supone que un impuesto solo puede tener un impuestoDetalle
    //@OneToMany(cascade= CascadeType.ALL)
    //@JoinColumn(name="ID_IMPUESTO")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "impuesto", fetch = FetchType.EAGER)
    private List<ImpuestoDetalle> detalleImpuestos;

    public Long getIdImpuesto() {
        return idImpuesto;
    }

    public void setIdImpuesto(Long idImpuesto) {
        this.idImpuesto = idImpuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoSri() {
        return codigoSri;
    }

    public void setCodigoSri(String codigoSri) {
        this.codigoSri = codigoSri;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<ImpuestoDetalle> getDetalleImpuestos() {
        return detalleImpuestos;
    }

    public void setDetalleImpuestos(List<ImpuestoDetalle> detalleImpuestos) {
        this.detalleImpuestos = detalleImpuestos;
    }

}

