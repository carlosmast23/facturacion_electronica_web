package ec.com.codesoft.codefaclite.servidorinterfaz.entity.banco;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @auhor
 */
@Entity
@Table(name = "BODEGA")
public class CuentaBanco implements Serializable{
    
    @Id
    @Column(name = "BODEGA_ID")
    private Long id;
    
    @Column(name = "NUMERO_CUENTA")
    private String numeroCuenta;
    
    @Column(name = "TIPO_CUENTA")
    private String tipoCuenta;
    
    @Column(name = "CLASIFICACION_INTERNA")
    private String clasificacionInterna;
    
    @Column(name = "SECUENCIAL_CHEQUERA")
    private Integer secuencialChequera;
    
    @Column(name = "SALDO_TOTAL")
    private BigDecimal saldoTotal;
    
    @Column(name = "ESTADO")
    private String estado;

    public CuentaBanco() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
    
    public TipoCuentaBancariaEnum getTipoCuentaEnum() {
        return TipoCuentaBancariaEnum.buscarPorLetra(tipoCuenta);
    }

    public void setTipoCuentaEnum(TipoCuentaBancariaEnum tipoCuentaEnum) {
        this.tipoCuenta = tipoCuentaEnum.getLetra();
    }

    public String getClasificacionInterna() {
        return clasificacionInterna;
    }

    public void setClasificacionInterna(String clasificacionInterna) {
        this.clasificacionInterna = clasificacionInterna;
    }
    
    public ClasificacionCuentaBancariaEnum getClasificacionInternaEnum() {
        return ClasificacionCuentaBancariaEnum.buscarPorLetra(clasificacionInterna);
    }

    public void setClasificacionInterna(ClasificacionCuentaBancariaEnum clasificacionInternaEnum) {
        this.clasificacionInterna = clasificacionInternaEnum.getLetra();
    }

    public Integer getSecuencialChequera() {
        return secuencialChequera;
    }

    public void setSecuencialChequera(Integer secuencialChequera) {
        this.secuencialChequera = secuencialChequera;
    }

    public BigDecimal getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(BigDecimal saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    /**
     * =======================================================================
     *                      DATOS ADICIONALES
     * =======================================================================
     */
    public enum TipoCuentaBancariaEnum
    {
        AHORROS("a","Cuenta de Ahorros"),
        CORRIENTE("c","Cuenta Corrientes");
        
        private String letra;
        private String nombre;

        private TipoCuentaBancariaEnum(String letra, String nombre) {
            this.letra = letra;
            this.nombre = nombre;
        }

        public String getLetra() {
            return letra;
        }

        public String getNombre() {
            return nombre;
        }
        
        public static TipoCuentaBancariaEnum buscarPorLetra(String letra)
        {
            for (TipoCuentaBancariaEnum value : TipoCuentaBancariaEnum.values()) {
                if(value.getLetra().equals(letra))
                {
                    return value;
                }
            }
            return null;
        }
        
    }
    
    public enum ClasificacionCuentaBancariaEnum
    {
        INTERNO_EMPRESA("i","Interno Empresa"),
        CLIENTE_PROVEEDOR("c","Cliente o Proveedor");
        
        private String letra;
        private String nombre;

        private ClasificacionCuentaBancariaEnum(String letra, String nombre) {
            this.letra = letra;
            this.nombre = nombre;
        }

        public String getLetra() {
            return letra;
        }

        public String getNombre() {
            return nombre;
        }
        
        public static ClasificacionCuentaBancariaEnum buscarPorLetra(String letra)
        {
            for (ClasificacionCuentaBancariaEnum value : ClasificacionCuentaBancariaEnum.values()) {
                if(value.getLetra().equals(letra))
                {
                    return value;
                }
            }
            return null;
        }
        
        
    }
    
    
}
