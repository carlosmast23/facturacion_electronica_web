/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author
 */
@Entity
@Table(name = "BANDA_COMPROBANTE")
public class BandaComprobante implements Serializable{
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column (name = "NOMBRE")
    private String nombre;
    
    @Column (name = "TITULO")
    private String titulo;
    
    @Column(name = "ORDEN")
    private Integer orden;
    
    @Column (name = "ALTO")
    private int alto;
    
    @JoinColumn(name = "COMPROBANTE_FISICO_ID")
    @ManyToOne
    private ComprobanteFisicoDisenio documento;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seccion",fetch = FetchType.EAGER)
    private List<ComponenteComprobanteFisico> componentes;
    

    public BandaComprobante() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<ComponenteComprobanteFisico> getComponentes() {
        return componentes;
    }
    
    

    @Override
    public String toString() {
        return titulo;
    }
    
    
    
    
}
