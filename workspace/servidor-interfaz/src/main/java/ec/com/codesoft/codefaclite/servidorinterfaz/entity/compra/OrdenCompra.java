/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.compra;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import es.mityc.firmaJava.ocsp.config.ProveedorInfo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "ORDEN_COMPRA")
public class OrdenCompra implements Serializable{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  
    
    @JoinColumn(name = "EMPRESA_ID")
    private Empresa empresa;
    //@Column(name = "EMPRESA_ID")
    //private Long empresa_id;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @Column(name = "USUARIO_ID")
    private Long usuarioId;
    
    @Column(name = "FECHA_INGRESO")
    private Date fechaIngreso;
    
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;
    
    @Column(name = "OBSERVACION")
    private String observacion;
    
    @Column(name = "CODIGO_TIPO_DOCUMENTO")
    private String codigoTipoDocumento;
    
    @Column(name = "IVA_SRI_ID")
    private Long ivaSriId;
    
    @Column(name = "IVA")
    private BigDecimal iva;
    
    @Column(name = "TOTAL")
    private BigDecimal total;
    
        /**
     * Valor del descuento de los productos que cobran iva
     */
    @Column(name = "DESCUENTO_IVA")
    private BigDecimal descuentoImpuestos;
    /**
     * Valor del descuento de los productos que no cobran iva
     */    
    @Column(name = "DESCUENTO_IVA_CERO")
    private BigDecimal descuentoSinImpuestos;    
    
    @Column(name = "SUBTOTAL_IVA")
    private BigDecimal subtotalImpuestos;
    
    @Column(name = "SUBTOTAL_IVA_CERO")
    private BigDecimal subtotalSinImpuestos;
    
    
    @JoinColumn(name = "PROVEEDOR_ID")
    @ManyToOne    
    private Persona proveedor;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ordenCompra", fetch = FetchType.EAGER)
    private List<OrdenCompraDetalle> detalles;

    public OrdenCompra() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getCodigoTipoDocumento() {
        return codigoTipoDocumento;
    }

    public void setCodigoTipoDocumento(String codigoTipoDocumento) {
        this.codigoTipoDocumento = codigoTipoDocumento;
    }

    public Long getIvaSriId() {
        return ivaSriId;
    }

    public void setIvaSriId(Long ivaSriId) {
        this.ivaSriId = ivaSriId;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDescuentoImpuestos() {
        return descuentoImpuestos;
    }

    public void setDescuentoImpuestos(BigDecimal descuentoImpuestos) {
        this.descuentoImpuestos = descuentoImpuestos;
    }

    public BigDecimal getDescuentoSinImpuestos() {
        return descuentoSinImpuestos;
    }

    public void setDescuentoSinImpuestos(BigDecimal descuentoSinImpuestos) {
        this.descuentoSinImpuestos = descuentoSinImpuestos;
    }

    public BigDecimal getSubtotalImpuestos() {
        return subtotalImpuestos;
    }

    public void setSubtotalImpuestos(BigDecimal subtotalImpuestos) {
        this.subtotalImpuestos = subtotalImpuestos;
    }

    public BigDecimal getSubtotalSinImpuestos() {
        return subtotalSinImpuestos;
    }

    public void setSubtotalSinImpuestos(BigDecimal subtotalSinImpuestos) {
        this.subtotalSinImpuestos = subtotalSinImpuestos;
    }

    public Persona getProveedor() {
        return proveedor;
    }

    public void setProveedor(Persona proveedor) {
        this.proveedor = proveedor;
    }

    public List<OrdenCompraDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<OrdenCompraDetalle> detalles) {
        this.detalles = detalles;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    
    
    
    public GeneralEnumEstado getEstadoEnum()
    {
        return GeneralEnumEstado.getEnum(estado);
    }
    
    public void addDetalle(OrdenCompraDetalle detalle) {
        if (this.detalles == null) {
            this.detalles = new ArrayList<OrdenCompraDetalle>();
        }
        detalle.setOrdenCompra(this);
        this.detalles.add(detalle);

    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
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
        final OrdenCompra other = (OrdenCompra) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    //Metodos Personalizados

    
    
}
