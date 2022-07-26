/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDescuentoRubroEnum;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesPorcentajes;
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
 * @author
 */
@Entity
@Table(name = "RUBRO_ESTUDIANTE")
@XmlRootElement
public class RubroEstudiante implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "VALOR")    
    private BigDecimal valor;
    
    @Column(name = "SALDO")
    private BigDecimal saldo;
    
    @Column(name = "ESTADO_FACTURA")
    private String estadoFactura;
    
    @Column(name = "TIPO_DESCUENTO ")
    private String tipoDescuento;    

    @Column(name = "NOMBRE_DESCUENTO  ")
    private String nombreDescuento;

    @Column(name = "PORCENTAJE_DESCUENTO  ")
    private Integer procentajeDescuento;
    
    @Column(name = "VALOR_DESCUENTO")
    private BigDecimal valorDescuento;
    
    @Column(name = "FECHA_GENERADO")
    private Date fechaGenerado;
    
    /**
     * Estado que contrala si esta activo, anulado , o eliminado
     */
    @Column(name = "ESTADO")
    private String estado;
    
    @JoinColumn(name = "RUBRO_NIVEL_ID")
    @ManyToOne
    private RubrosNivel rubroNivel;
    
    @JoinColumn(name = "ESTUDIANTE_INSCRITO_ID")
    @ManyToOne
    private EstudianteInscrito estudianteInscrito;

    public RubroEstudiante() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RubrosNivel getRubroNivel() {
        return rubroNivel;
    }

    public void setRubroNivel(RubrosNivel rubroNivel) {
        this.rubroNivel = rubroNivel;
    }

    public EstudianteInscrito getEstudianteInscrito() {
        return estudianteInscrito;
    }

    public void setEstudianteInscrito(EstudianteInscrito estudianteInscrito) {
        this.estudianteInscrito = estudianteInscrito;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(String estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoDescuento() {
        return tipoDescuento;
    }

    public void setTipoDescuento(String tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }

    public String getNombreDescuento() {
        return nombreDescuento;
    }

    public void setNombreDescuento(String nombreDescuento) {
        this.nombreDescuento = nombreDescuento;
    }

    public Integer getProcentajeDescuento() {
        return procentajeDescuento;
    }

    public void setProcentajeDescuento(Integer procentajeDescuento) {
        this.procentajeDescuento = procentajeDescuento;
    }

    public Date getFechaGenerado() {
        return fechaGenerado;
    }

    public void setFechaGenerado(Date fechaGenerado) {
        this.fechaGenerado = fechaGenerado;
    }

    public BigDecimal getValorDescuento() {
        return valorDescuento;
    }

    public void setValorDescuento(BigDecimal valorDescuento) {
        this.valorDescuento = valorDescuento;
    }
    
    
    /**
     * Metodos personalizados
     * @return 
     */
    
    public GeneralEnumEstado getEstadoEnum()
    {
        
        return GeneralEnumEstado.getEnum(estado);
    }
    
        
    public FacturacionEstadoEnum getEstadoFacturaEnum() {
        return FacturacionEstadoEnum.buscarPorLetra(estadoFactura);       
    } 
    
    public TipoDescuentoRubroEnum getTipoDescuentoEnum()
    {
        return TipoDescuentoRubroEnum.buscarPorLetra(tipoDescuento);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.id);
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
        final RubroEstudiante other = (RubroEstudiante) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getRubroNivel().getNombre()+"["+this.valor+"]";
    }
    
    public BigDecimal obtenerValorSaldoDescuento()
    {
        return obtenerSaldoIncluidoDescuento().subtract(saldo);
    }
    
    public BigDecimal obtenerSaldoIncluidoDescuento()
    {
        //Si por algun motivo este valor esta grabado como nulo
        if(procentajeDescuento==null)
        {
            procentajeDescuento=0;
        }
        
        if(procentajeDescuento>=100)
        {
            return valorDescuento;// TODO: Verificar que no de problema cuando el descuento sea de un 100%
        }
        else
        {
            return UtilidadesPorcentajes.construirValorAntesDeDescuento(saldo,new BigDecimal(procentajeDescuento));
        }
    }
    
    
    public enum FacturacionEstadoEnum {
        /**
         * Estado cuando exista cancelado en su totalidad el rubro y este facturado
         */
        FACTURADO("f"),
        /**
         * Estado cuando solo esta generado la deuda pero aun no existe factura
         */
        SIN_FACTURAR("s"),
        /**
         * Estado cuando se a pagado una parte de la factura
         */
        FACTURA_PARCIAL("p");

        private FacturacionEstadoEnum(String letra) {
            this.letra = letra;
        }

        private String letra;

        public static FacturacionEstadoEnum buscarPorLetra(String letra) {
            for (FacturacionEstadoEnum facturacionEstadoEnum : FacturacionEstadoEnum.values()) {
                if (facturacionEstadoEnum.getLetra().equals(letra)) {
                    return facturacionEstadoEnum;
                }
            }
            return null;
        }

        public String getLetra() {
            return letra;
        }
        
        
    }
    
}
