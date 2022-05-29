/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "PRESTAMO_CUOTA")
public class PrestamoCuota implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NUMERO_CUOTA")
    private Integer numeroCuota;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "VALOR_CUOTA")
    private BigDecimal valorCuota;

    @Column(name = "SALDO_CUOTA")
    private BigDecimal saldoCuota;

    @Column(name = "VALOR_PAGO")
    private BigDecimal valorPago;

    @Column(name = "FECHA_PAGO_GENERADA")
    private Date fechaPagoGenerado;

    @Column(name = "FECHA_PAGO")
    private Date fechaPago;

    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;

    @Column(name = "ESTADO")
    private String estado;

    @JoinColumn(name = "PRESTAMO_ID")
    private Prestamo prestamo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroCuota() {
        return numeroCuota;
    }

    public void setNumeroCuota(Integer numeroCuota) {
        this.numeroCuota = numeroCuota;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValorCuota() {
        return valorCuota;
    }

    public void setValorCuota(BigDecimal valorCuota) {
        this.valorCuota = valorCuota;
    }

    public BigDecimal getSaldoCuota() {
        return saldoCuota;
    }

    public void setSaldoCuota(BigDecimal saldoCuota) {
        this.saldoCuota = saldoCuota;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public Date getFechaPagoGenerado() {
        return fechaPagoGenerado;
    }

    public void setFechaPagoGenerado(Date fechaPagoGenerado) {
        this.fechaPagoGenerado = fechaPagoGenerado;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public GeneralEnumEstado getEstadoEnum() {
        return GeneralEnumEstado.getEnum(estado);
    }

    public void setEstadoEnum(GeneralEnumEstado estadoEnum) {
        this.estado = estadoEnum.getEstado();
    }
    
    public TipoCuotaEnum getTipoEnum() {
        return TipoCuotaEnum.buscarPorLetra(this.tipo);
    }

    public void setTipoEnum(TipoCuotaEnum tipoEnum) {
        this.tipo = tipoEnum.getLetra();
    }
    
    /**
     * =======================================================================
     *                        METODOS PERSONALIZADOS
     * =======================================================================
     */
    public enum TipoCuotaEnum implements Serializable
    {
        CUOTA_INICIAL("i","Cuota Inicial"),
        CUOTA_MENSUAL("m","Cuota Mensual");
        
        private String letra;
        private String nombre;

        private TipoCuotaEnum(String letra, String nombre) {
            this.letra = letra;
            this.nombre = nombre;
        }

        public String getLetra() {
            return letra;
        }

        public String getNombre() {
            return nombre;
        }
        
        public static TipoCuotaEnum buscarPorLetra(String letra)
        {
            for (TipoCuotaEnum value : TipoCuotaEnum.values()) {
                if(value.getLetra().equals(letra))
                {
                    return value;
                }
            }
            return null;
        }
        
        
    }

}
