/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author CodesoftDesarrollo 1
 */
@Entity
@Table(name = "DEPARTAMENTO")
public class Departamento implements Serializable
{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "CODIGO")
    private String codigo;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @Column(name = "TIPO")
    private String tipo;
    
    public Departamento() {
        
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public void setEstado(GeneralEnumEstado estadoEnum) {
        this.estado = estadoEnum.getEstado();
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public TipoEnum getTipoEnum() {
        return TipoEnum.buscarPorLetra(tipo);
    }

    public void setTipoEnum(TipoEnum tipoEnum) {
        this.tipo = tipoEnum.getLetra();
    }
    

    @Override
    public String toString() {
        return ""+ codigo + " - " + nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final Departamento other = (Departamento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    public enum TipoEnum
    {
        Otros("Otros","o"),
        Ventas("Ventas","v"),
        /**
         * Categoria utilizada para vendedores que deben facturar externamente desde la web o la app y solo deben ver sus clientes de ruta
         */
        Vendedores_Externos("Vendedores Externos","e"),
        Supervisor("Supervisor","s");

        private TipoEnum(String nombre, String letra) {
            this.nombre = nombre;
            this.letra = letra;
        }
        
        private String nombre;
        private String letra;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getLetra() {
            return letra;
        }

        public void setLetra(String letra) {
            this.letra = letra;
        }
        
        public static TipoEnum buscarPorLetra(String letra)
        {
            for (TipoEnum value : TipoEnum.values()) {
                if(value.getLetra().equals(letra))
                {
                    return value;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return nombre;
        }
        
        
        
    }
    
    
}
