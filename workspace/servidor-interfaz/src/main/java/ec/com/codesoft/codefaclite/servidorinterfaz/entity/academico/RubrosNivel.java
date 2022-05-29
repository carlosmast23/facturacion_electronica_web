/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;
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
 * @author Carlos
 */
@Entity
@Table(name = "RUBROS_NIVEL")
@XmlRootElement
public class RubrosNivel implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "DIAS_CREDITO")
    private Integer diasCredito;
    
    @Column(name = "MES_NUMERO")
    private Integer mesNumero;
    
    @Column(name = "ANIO")
    private Integer anio;
    
    @Column(name = "DESCUENTO_PORCENTAJE")
    private BigDecimal descuentoPorcentaje;

    @JoinColumn(name = "NIVEL_ID")
    @ManyToOne
    private Nivel nivel;

    @JoinColumn(name = "PERIODO_ID")
    @ManyToOne
    private Periodo periodo;

    @JoinColumn(name = "CATALOGO_PRODUCTO_ID")
    @ManyToOne    
    private CatalogoProducto catalogoProducto;
    
    /*@Column(name = "TIPO_RUBRO")
    private String tipoRubro;*/

    @Column(name = "VALOR")    
    private BigDecimal valor;
    
    @Column(name = "ESTADO")    
    private String estado;


    public RubrosNivel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Integer getMesNumero() {
        return mesNumero;
    }

    public void setMesNumero(Integer mesNumero) {
        this.mesNumero = mesNumero;
    }
    
    

    /*
    public String getTipoRubro() {
        return tipoRubro;
    }

    public void setTipoRubro(String tipoRubro) {
        this.tipoRubro = tipoRubro;
    }*/

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public CatalogoProducto getCatalogoProducto() {
        return catalogoProducto;
    }

    public void setCatalogoProducto(CatalogoProducto catalogoProducto) {
        this.catalogoProducto = catalogoProducto;
    }

    public Integer getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(Integer diasCredito) {
        this.diasCredito = diasCredito;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getDescuentoPorcentaje() {
        return descuentoPorcentaje;
    }

    public void setDescuentoPorcentaje(BigDecimal descuentoPorcentaje) {
        this.descuentoPorcentaje = descuentoPorcentaje;
    }
    
    
    
    
    
    /**
     * Metodos personalizados
     * @return 
     */
    public MesEnum getMesEnum()
    {
        return MesEnum.obtenerPorNumero(mesNumero);
    }
    
    public GeneralEnumEstado getEstadoEnum()
    {
        return GeneralEnumEstado.getEnum(estado);
    }

    @Override
    public String toString() {
        return nombre+" [ "+valor+"$ ]";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.id);
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
        final RubrosNivel other = (RubrosNivel) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
