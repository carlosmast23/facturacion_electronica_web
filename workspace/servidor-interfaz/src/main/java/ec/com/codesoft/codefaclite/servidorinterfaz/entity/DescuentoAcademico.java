/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
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
import javax.persistence.Table;

/**
 *
 * @author CARLOS_CODESOFT
 */
@Entity
@Table(name = "DESCUENTO_ACADEMICO")
public class DescuentoAcademico implements Serializable{
    /**
     * Variable temporal para poder trabajar cuando quiere cambiar descuento a 0
     * o entras palabras no quiere aplicar descuento
     */
    public static final DescuentoAcademico descuentoCero=new DescuentoAcademico("Descuento Cero", BigDecimal.ZERO);
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "PORCENTAJE")
    private BigDecimal porcentaje;
    
    @Column(name = "ESTADO")    
    private String estado;
    
    @Column(name = "TIPO")    
    private String tipo;
    
    @JoinColumn(name = "PERIODO_ID")
    private Periodo periodo;

    public DescuentoAcademico(String nombre, BigDecimal porcentaje) {
        this.nombre = nombre;
        this.porcentaje = porcentaje;
    }    
    

    public DescuentoAcademico() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
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

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public TipoEnum getTipoEnum() {
        return TipoEnum.obtenerPorLetra(tipo);
    }

    public void setTipoEnum(TipoEnum tipoEnum) {
        this.tipo = tipoEnum.getLetra();
    }
    
    

    @Override
    public String toString() {
        return "["+porcentaje+"%] "+nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final DescuentoAcademico other = (DescuentoAcademico) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    /**
     * ========================================================
     *          DATOS ADICIONALES ACADEMICO
     * ========================================================
     */
    
    public enum TipoEnum
    {
        INDIVIDUAL("i","Individual"),
        GRUPAL("g","grupal");

        private TipoEnum(String letra, String nombre) {
            this.letra = letra;
            this.nombre = nombre;
        }
                
        private String letra;
        
        private String nombre;

        public String getLetra() {
            return letra;
        }

        public String getNombre() {
            return nombre;
        }

        @Override
        public String toString() {
            return nombre;
        }
        
        public static TipoEnum obtenerPorLetra(String letra)
        {
            for (TipoEnum value : TipoEnum.values()) {
                if(value.getLetra().equals(letra))
                {
                    return value;
                }
            }
            return null;
        }
        
    }
    
    
    
}
