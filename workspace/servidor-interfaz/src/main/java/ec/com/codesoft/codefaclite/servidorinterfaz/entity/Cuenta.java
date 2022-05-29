/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import java.io.Serializable;

/**
 *
 * @author Carlos
 */
public class Cuenta implements  Serializable{
    
    private Long id;
    
    private String codigo;
    
    private String nombre;    
    /**
     * Para saber si es cuenta que agrupa o que pueda hacer asientos
     */
    private String imputable;
    
    /**
     * Referencia a la cuenta padre de la cuenta
     */
    private Cuenta cuentaPadre;
    

    public Cuenta() {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImputable() {
        return imputable;
    }

    public void setImputable(String imputable) {
        this.imputable = imputable;
    }

    public Cuenta getCuentaPadre() {
        return cuentaPadre;
    }

    public void setCuentaPadre(Cuenta cuentaPadre) {
        this.cuentaPadre = cuentaPadre;
    }
    
    
    
    public EnumSiNo getImputableEnum()
    {
        return EnumSiNo.getEnumByLetra(imputable);        
    }

    @Override
    public String toString() {
        return codigo+"  ["+nombre+"]";
    }
    
    
}
