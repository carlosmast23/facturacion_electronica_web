/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @auhor
 */
@Entity
@Table(name = "RUTA_DETALLE")
public class RutaDetalle implements Serializable,Cloneable  {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column (name = "ORDEN")
    private Integer orden;
    
    @Column (name = "ESTADO")
    private String estado;
    
    @JoinColumn (name = "RUTA_ID")
    private Ruta ruta;
    
    @JoinColumn (name = "PERSONA_ESTABLECIMIENTO_ID")
    private PersonaEstablecimiento establecimiento;

    public RutaDetalle() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public GeneralEnumEstado getEstadoEnum() {
        return GeneralEnumEstado.getEnum(estado);
    }

    public void setEstadoEnum(GeneralEnumEstado estadoEnum) {
        this.estado = estadoEnum.getEstado();
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public PersonaEstablecimiento getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(PersonaEstablecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    ///////////////////////////////////////////////////////////////////////////
    ///                     METODOS GET AND SET
    ///////////////////////////////////////////////////////////////////////////
    
    /**
     * Metodo util en especial para editar en la vista
     * @param rutaDetalle 
     */
    public void setObject(RutaDetalle rutaDetalle)
    {
        this.establecimiento=rutaDetalle.getEstablecimiento();
        this.orden=rutaDetalle.getOrden();
    }
    
}
