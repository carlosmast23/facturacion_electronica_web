/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.utilidades.reporte.UtilidadesUnidadesMedida;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "COMPROBANTE_FISICO_DISENIO")
public class ComprobanteFisicoDisenio implements Serializable {
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column (name = "NOMBRE")
    private String nombre;
    
    @Column (name = "ANCHO")
    private int ancho;
    
    @Column (name = "ALTO")
    private int alto;
    
    @Column (name = "CODIGO_DOCUMENTO")
    private String codigoDocumento;
    
    @Column (name = "PPP")
    private int ppp;

    @OneToMany(cascade = {CascadeType.ALL,CascadeType.DETACH}, mappedBy = "documento",fetch = FetchType.EAGER)
    private List<BandaComprobante> secciones;
    
    public ComprobanteFisicoDisenio() {
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

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public int getPpp() {
        return ppp;
    }

    public void setPpp(int ppp) {
        this.ppp = ppp;
    }
    
    

    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }
    
    

    public List<BandaComprobante> getSecciones() {
        return secciones;
    }
    
    

    @Override
    public String toString() {
        return nombre;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    ///                     METODO PERSONALIZADO
    ///////////////////////////////////////////////////////////////////////////
    
    public int getAnchoColumna()
    {
        //todo: por el momento dejo seteado este valor, aunque deberia calcularse
        return ancho-5;
    }
    
    public int getAnchoCm() {
        return (int) UtilidadesUnidadesMedida.convertirPixelesACm(ancho, ppp);
    }

    
    public int getAltoCm() {
        return (int) UtilidadesUnidadesMedida.convertirPixelesACm(alto, ppp);
    }
    
    public void setAnchoCm(int anchoCm) {
        this.ancho = (int) UtilidadesUnidadesMedida.convertirCmAPixeles(anchoCm, ppp);
    }

    public void setAltoCm(int altoCm) {
        this.alto = (int) UtilidadesUnidadesMedida.convertirCmAPixeles(altoCm, ppp);
    }
    
}
