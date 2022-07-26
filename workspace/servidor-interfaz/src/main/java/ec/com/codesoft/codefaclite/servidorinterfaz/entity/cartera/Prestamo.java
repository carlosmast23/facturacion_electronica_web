/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NombresEntidadesJPA;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
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
 * @author
 */
@Entity
@Table(name = "PRESTAMO")
public class Prestamo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CAPITAL")
    private BigDecimal capital;

    @Column(name = "INTERES")
    private BigDecimal interes;

    @Column(name = "SALDO_CAPITAL")
    private BigDecimal saldoCapital;

    @Column(name = "SALDO_INTERES")
    private BigDecimal saldoInteres;

    @Column(name = "TAZA_INTERES")
    private BigDecimal tazaInteres;

    /**
     * Falta aumentar
     */
    @Column(name = "TOTAL_PRESTAMO")
    private BigDecimal totalPrestamo;

    /**
     * Falta aumenta
     */
    @Column(name = "CUOTA_INICIAL")
    private BigDecimal cuotaInicial;
    
        /**
     * Falta aumenta
     */
    @Column(name = "DIA_PAGO_CUOTA")
    private Integer diaPago;

    @Column(name = "PLAZO")
    private Integer plazo;

    /**
     * TODO: Ver si necesita otra fecha del prestamo y creacion
     */
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;

    @Column(name = "ESTADO")
    private String estado;

    @JoinColumn(name = "FACTURA_ID")
    private Factura venta;

    @JoinColumn(name = "CARTERA_ID")
    private Cartera cartera;

    @JoinColumn(name = "PERSONA_ID")
    private Persona cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getInteres() {
        return interes;
    }

    public void setInteres(BigDecimal interes) {
        this.interes = interes;
    }

    public BigDecimal getSaldoCapital() {
        return saldoCapital;
    }

    public void setSaldoCapital(BigDecimal saldoCapital) {
        this.saldoCapital = saldoCapital;
    }

    public BigDecimal getSaldoInteres() {
        return saldoInteres;
    }

    public void setSaldoInteres(BigDecimal saldoInteres) {
        this.saldoInteres = saldoInteres;
    }

    public BigDecimal getTazaInteres() {
        return tazaInteres;
    }

    public void setTazaInteres(BigDecimal tazaInteres) {
        this.tazaInteres = tazaInteres;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
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

    public Factura getVenta() {
        return venta;
    }

    public void setVenta(Factura venta) {
        this.venta = venta;
    }

    public Cartera getCartera() {
        return cartera;
    }

    public void setCartera(Cartera cartera) {
        this.cartera = cartera;
    }

    public Persona getCliente() {
        return cliente;
    }

    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getTotalPrestamo() {
        return totalPrestamo;
    }

    public void setTotalPrestamo(BigDecimal totalPrestamo) {
        this.totalPrestamo = totalPrestamo;
    }

    public GeneralEnumEstado getEstadoEnum() {
        return GeneralEnumEstado.getEnum(estado);
    }

    public void setEstadoEnum(GeneralEnumEstado estadoEnum) {
        this.estado = estadoEnum.getEstado();
    }

    public BigDecimal getCuotaInicial() {
        return cuotaInicial;
    }

    public void setCuotaInicial(BigDecimal cuotaInicial) {
        this.cuotaInicial = cuotaInicial;
    }

    public Integer getDiaPago() {
        return diaPago;
    }

    public void setDiaPago(Integer diaPago) {
        this.diaPago = diaPago;
    }
    
    

    /**
     * =====================================================================
     * METODOS PERSONALIZADOS
     * =====================================================================
     */
    public void calcularCapital() {
        capital = totalPrestamo.subtract(cuotaInicial);
    }

    public BigDecimal calcularCuotaMensual()
    {
        //BigDecimal ¿interDecimal=
        return capital.divide(new BigDecimal(plazo+""));
    }
}
