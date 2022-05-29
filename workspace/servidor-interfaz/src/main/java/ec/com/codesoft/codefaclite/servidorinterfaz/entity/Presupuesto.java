/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FechaFormatoEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author CodesoftDesarrollo 1
 */
@Entity
@Table(name = "PRESUPUESTO")
@XmlRootElement
public class Presupuesto implements Serializable
{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "EMPRESA_ID")
    private Long empresaId;
    
    @Column(name = "USUARIO_ID")
    private Long usuarioId;
    
    @Column(name = "CODIGO")
    private String codigo;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    
    @Column(name = "ESTADO")
    private String estado;
    
    //Todo: Cambiar el nombre por fecha creacion , para saber que es la fecha cuando se creo
    @Column(name = "FECHA_CREACION")
    private Date fechaCreacion;
    
    @Column(name = "FECHA_PRESUPUESTO")
    private Date fechaPresupuesto;    
    
    /**
     * Fecha que establece hasta cuando es valido el presupuesto
     */
    @Column(name = "FECHA_VALIDEZ")
    private Date fechaValidez;
    
    @Column(name = "DESCUENTO_COMPRA")
    private BigDecimal descuentoCompra;

    @Column(name = "DESCUENTO_VENTA")
    private BigDecimal descuentoVenta;

    @Column(name = "TOTAL_COMPRA")
    private BigDecimal totalCompra;

    /**
     * TODO: Este total en ventas realmente es el subtotal cambiar el nombre
     */
    @Column(name = "TOTAL_VENTA")
    private BigDecimal totalVenta;            
            
    
    @JoinColumn(name = "ORDEN_TRABAJO_DETALLE_ID")
    @OneToOne
    private OrdenTrabajoDetalle ordenTrabajoDetalle;
    
    @JoinColumn(name = "CLIENTE_ID")
    @ManyToOne
    private Persona persona;
    
    @JoinColumn(name = "CATALOGO_PRODUCTO_ID")
    @ManyToOne
    private CatalogoProducto catalogoProducto;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "presupuesto", fetch = FetchType.EAGER)
    private List<PresupuestoDetalle>  presupuestoDetalles;
    
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


    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public EstadoEnum getEstadoEnum() {
        return EstadoEnum.getByLetra(estado);
    }

    public void setEstadoEnum(EstadoEnum estadoEnum) {
        this.estado = estadoEnum.getLetra();
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    
    
    public OrdenTrabajoDetalle getOrdenTrabajoDetalle() {
        return ordenTrabajoDetalle;
    }

    public void setOrdenTrabajoDetalle(OrdenTrabajoDetalle ordenTrabajoDetalle) {
        this.ordenTrabajoDetalle = ordenTrabajoDetalle;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<PresupuestoDetalle> getPresupuestoDetalles() {
        return presupuestoDetalles;
    }

    public void setPresupuestoDetalles(List<PresupuestoDetalle> presupuestoDetalles) {
        this.presupuestoDetalles = presupuestoDetalles;
    }

    public Date getFechaPresupuesto() {
        return fechaPresupuesto;
    }

    public void setFechaPresupuesto(Date fechaPresupuesto) {
        this.fechaPresupuesto = fechaPresupuesto;
    }

    public Date getFechaValidez() {
        return fechaValidez;
    }

    public void setFechaValidez(Date fechaValidez) {
        this.fechaValidez = fechaValidez;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public BigDecimal getDescuentoCompra() {
        return descuentoCompra;
    }

    public void setDescuentoCompra(BigDecimal descuentoCompra) {
        this.descuentoCompra = descuentoCompra;
    }

    public BigDecimal getDescuentoVenta() {
        return descuentoVenta;
    }

    public void setDescuentoVenta(BigDecimal descuentoVenta) {
        this.descuentoVenta = descuentoVenta;
    }

    public BigDecimal getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(BigDecimal totalCompra) {
        this.totalCompra = totalCompra;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public CatalogoProducto getCatalogoProducto() {
        return catalogoProducto;
    }

    public void setCatalogoProducto(CatalogoProducto catalogoProducto) {
        this.catalogoProducto = catalogoProducto;
    }
    
    
    
    
    
    public void addDetalle(PresupuestoDetalle detalle)
    {
        if(this.presupuestoDetalles == null)
        {
            this.presupuestoDetalles = new ArrayList<>();
        }
        detalle.setPresupuesto(this);
        this.presupuestoDetalles.add(detalle);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.codigo);
        hash = 47 * hash + Objects.hashCode(this.descripcion);
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
        final Presupuesto other = (Presupuesto) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }    

    /**
     * Metodos Personalizados
     */
    public BigDecimal calcularTotalMenosDescuentos()
    {
        return totalVenta.subtract(descuentoVenta);
    }
    
    public ResultadoTotales obtenerMapReporteTotales(String rucEmpresa)
    {
        BigDecimal totalProveedores=BigDecimal.ZERO;
        BigDecimal totalProduccionInterna= BigDecimal.ZERO;
        
        if(getPresupuestoDetalles()!=null)
        {
            for (PresupuestoDetalle presupuestoDetalle : getPresupuestoDetalles()) {

                //Si encuentra que el cliente es la misma empresa que esta usando el software lo registra como produccion interna
                if(presupuestoDetalle.getPersona().getIdentificacion().equals(rucEmpresa))
                {
                    totalProduccionInterna=totalProduccionInterna.add(presupuestoDetalle.calcularTotalCompra());
                }
                else //Cualquier otro tipo de ingreso lo considero como de proveedoresv
                {
                    totalProveedores=totalProveedores.add(presupuestoDetalle.calcularTotalCompra());
                }
            }
        }
        ResultadoTotales resultado=new ResultadoTotales();
        
        resultado.valorPagarCliente=calcularTotalMenosDescuentos();
        resultado.valoresProveedores=totalProveedores;
        resultado.produccionInterna=totalProduccionInterna;

        BigDecimal utilidad
                = calcularTotalMenosDescuentos().
                        subtract(totalProveedores).
                        subtract(totalProduccionInterna);
        
        resultado.utilidad=utilidad;
                
        return resultado;
    }
    
    public class ResultadoTotales {
        public BigDecimal valorPagarCliente;
        public BigDecimal valoresProveedores;
        public BigDecimal produccionInterna;
        public BigDecimal utilidad;
    }

    
    public enum EstadoEnum
    {
        /**
         * Estado inicial en el cual se guarda cualquier presupuesto y lo que siginifica es que esta todavia en proceso y que aun no se termina
         * de presupuestar y por tal motivo no se puede facturar, y el usuario debe poder cambiar al estado siguiente cuando lo considere necesario
         */
        PRESUPUESTANDO("Presupuestando","p"),
        
        /**
         * Estado intermediaron entre el terminado y seria utilizado principalmente si existe un supervisor que se encarga
         * de revisar y aprobar los presupuestos , sin este estado previo no se podria cambiar a los posteriores, pero este
         * estado deberia existir una configuracion para establecer si desea usar este modo, porque puede resultar lento el proceso
         * para los negocios que requeren ninguna aprobacion
         */
        APROBADO("Aprobado","a"),
        
        /**
         * Estado utilizado para indicar que el presupuesto fue finalizado si existe un proceso alterno que acompoña
         * por ejemplo la mano de obra o alguien esta elaborando el producto , en este estado se deberia informar al 
         * cliente para que pueda acercarse para cancelar y emitir la factura
         */
        TERMINADO("Terminado","t"),
        
        /**
         * Estado estado es utilizado para indicar que un presupuesto no se facturo o no se termino por cualquier razon
         * En este estado se puede modificar y se puede cambiar a terminando si en el futuro se desea facturar pero es 
         * para llevar un control de los presupuestos que fueron abandonados
         */
        ABANDONADO("Abandonado","b"),
        
        /**
         * Estado utilizado cuando existe una nota de credito que afecta al presupuesto 
         * En este estado tampoco se debe poder modificar , pero si se debe poder habilitar
         * el presupuesto cambiado al estado terminado si desean facturar nuevamente
         */
        ANULADO("Anulado","n"),
        
        /**
         * Estado seteado cuando al presupuesto esta ligado una factura emitida
         * En este estado se supone que ya no se pueden modificar valores de venta porque los valores facturados
         * quedarian descuadrados
         */
        FACTURADO("Facturado","f");
        
        private String nombre;
        private String letra;

        private EstadoEnum(String nombre, String letra) {
            this.nombre = nombre;
            this.letra = letra;
        }

        public String getNombre() {
            return nombre;
        }

        public String getLetra() {
            return letra;
        }

        @Override
        public String toString() {
            return nombre;
        }
        
        public static EstadoEnum getByLetra(String letra)
        {
            for (EstadoEnum estadoEnum : EstadoEnum.values()) {
                if(estadoEnum.getLetra().equals(letra))
                {
                    return estadoEnum;
                }
            }
            
            return null;
        }
        
        
        
    }
    
}
