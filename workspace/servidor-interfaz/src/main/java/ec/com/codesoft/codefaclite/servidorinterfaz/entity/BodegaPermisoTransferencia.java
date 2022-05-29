/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author CARLOS_CODESOFT
 */
@Entity
@Table(name = "BODEGA_PERMISO_TRANSFERENCIA")
public class BodegaPermisoTransferencia implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @JoinColumn(name = "BODEGA_PRINCIPAL_ID")
    private Bodega bodegaPrincipal;
    
    @JoinColumn(name = "BODEGA_PERMISO_ID")
    private Bodega bodegaPermiso;

    public BodegaPermisoTransferencia() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bodega getBodegaPrincipal() {
        return bodegaPrincipal;
    }

    public void setBodegaPrincipal(Bodega bodegaPrincipal) {
        this.bodegaPrincipal = bodegaPrincipal;
    }

    public Bodega getBodegaPermiso() {
        return bodegaPermiso;
    }

    public void setBodegaPermiso(Bodega bodegaPermiso) {
        this.bodegaPermiso = bodegaPermiso;
    }

    @Override
    public String toString() {
        return bodegaPermiso.getNombre();
    }
    
}
