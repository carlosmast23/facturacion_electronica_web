/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.banco;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
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
@Table(name = "BANCO")
public class Banco implements Serializable{
    @Id
    @Column(name = "BODEGA_ID")
    private Long id;
    
    @Column(name = "CODIGO")
    private String codigo;
    
    @Column(name = "CODIGO_MULTICASH")
    private String codigoMulticash;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @JoinColumn(name = "EMPRESA_ID")
    private Empresa empresa;

    public Banco() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoMulticash() {
        return codigoMulticash;
    }

    public void setCodigoMulticash(String codigoMulticash) {
        this.codigoMulticash = codigoMulticash;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    
    
}
