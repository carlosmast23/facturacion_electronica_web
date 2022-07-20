/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaSessionEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @auhor
 */
@Entity
@Table(name = "CAJA_SESSION")
@XmlRootElement
public class CajaSession implements Serializable
{
    /*
    * Atributos
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "FECHA_HORA_APERTURA")
    private Timestamp fechaHoraApertura;
    @Column(name = "FECHA_HORA_CIERRE")
    private Timestamp fechaHoraCierre;
    @Column(name = "VALOR_APERTURA")
    private BigDecimal valorApertura;
    @Column(name = "VALOR_CIERRE")
    private BigDecimal valorCierre;
    @Column(name = "ESTADO_CIERRE_CAJA")
    private String estadoCierreCaja;
    
    /*
    * Foreign Key
    */
    @JoinColumn(name = "CAJA_ID")
    @ManyToOne
    private Caja caja;
    
    @JoinColumn(name = "USUARIO_ID")
    @ManyToOne
    private Usuario usuario;
    
    @JoinColumn(name = "VENTA_ID")
    @ManyToOne
    private Venta venta;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cajaSession", fetch = FetchType.EAGER)
    private List<ArqueoCaja> arqueosCaja;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cajaSession", fetch = FetchType.EAGER)
    private List<IngresoCaja> ingresosCaja;
    /*
    * Constructor
    */
    public CajaSession() {
    }
    
    /*
    * Get and Set
    */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getFechaHoraApertura() {
        return fechaHoraApertura;
    }

    public void setFechaHoraApertura(Timestamp fechaHoraApertura) {
        this.fechaHoraApertura = fechaHoraApertura;
    }

    public Timestamp getFechaHoraCierre() {
        return fechaHoraCierre;
    }

    public void setFechaHoraCierre(Timestamp fechaHoraCierre) {
        this.fechaHoraCierre = fechaHoraCierre;
    }

    public BigDecimal getValorApertura() {
        return valorApertura;
    }

    public void setValorApertura(BigDecimal valorApertura) {
        this.valorApertura = valorApertura;
    }

    public BigDecimal getValorCierre() {
        return valorCierre;
    }

    public void setValorCierre(BigDecimal valorCierre) {
        this.valorCierre = valorCierre;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }
    
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public CajaEnum getEstadoEnum(){
        return CajaEnum.getEnum(estado);
    }
    
    public void setEstadoEnum(CajaEnum estadoCajaEnum){
        if(estadoCajaEnum == null)
        {
            this.estado = null;
        }
        else
        {
            this.estado = estadoCajaEnum.getEstado();
        }
    }
            
     public String getEstadoCierreCaja() {
        return estadoCierreCaja;
    }

    public void setEstadoCierreCaja(String estadoCierreCaja) {
        this.estadoCierreCaja = estadoCierreCaja;
    }
    
    public CajaSessionEnum getEstadoSessionEnum()
    {
        return CajaSessionEnum.getEnum(estadoCierreCaja);
    }
    
    public void setEstadoSessionEnum(CajaSessionEnum estadoCajaSessionEnum)
    {
        if(estadoCajaSessionEnum == null)
        {
            estadoCierreCaja = null;
        }
        else
        {
            estadoCierreCaja = estadoCajaSessionEnum.getEstado();
        }
    }

    public List<ArqueoCaja> getArqueosCaja() {
        return arqueosCaja;
    }

    public void setArqueosCaja(List<ArqueoCaja> arqueosCaja) {
        this.arqueosCaja = arqueosCaja;
    }

    public List<IngresoCaja> getIngresosCaja() {
        return ingresosCaja;
    }

    public void setIngresosCaja(List<IngresoCaja> ingresosCaja) {
        this.ingresosCaja = ingresosCaja;
    }
    
    public void addIngresoCaja(IngresoCaja ingresoCaja){
        if(this.ingresosCaja == null)
        {
            this.ingresosCaja = new ArrayList<>();
        }
        
        ingresoCaja.setCajaSession(this);
        this.ingresosCaja.add(ingresoCaja);
    }
    
    /*
    * Equals
    */

    @Override
    public int hashCode() {
        int hash = 7;
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
        final CajaSession other = (CajaSession) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
