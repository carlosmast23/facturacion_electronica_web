/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "KARDEX_DETALLE")
public class KardexDetalle implements Serializable ,Cloneable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Referencia del documento que se grabo COMPRAS-VENTAS-ETC
     */
    @Column(name = "REFERENCIA_DOCUMENTO_ID")
    private Long referenciaDocumentoId;
    
    
    /**
     * Variable para saber si el ingreso es positivo o negativo en el cardex
     */
    @Column(name = "CODIGO_TIPO_DOCUMENTO")
    private String codigoTipoDocumento;
    
    @Column(name = "CANTIDAD")
    private BigDecimal cantidad;
    
    @Column(name = "PRECIO_UNITARIO")
    private BigDecimal precioUnitario;
    
    @Column(name = "PRECIO_TOTAL")
    private BigDecimal precioTotal;
    
    @Column(name = "FECHA_CREACION")
    private Timestamp fechaCreacion;
    
    @Column(name = "FECHA_INGRESO")
    private Timestamp fechaIngreso;
    
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    
    @Column(name = "NOMBRE_LEGAL")
    private String nombreLegal;
    
    @Column(name = "SECUENCIAL")
    protected Integer secuencial;

    @Column(name = "PUNTO_ESTABLECIMIENTO")
    protected String puntoEstablecimiento;

    @Column(name = "PUNTO_EMISION")
    protected String puntoEmision;
    
    @Column(name = "FECHA_DOCUMENTO")
    private Date fechaDocumento;
    
    /**
     * Variable para almacenar el tipo de documento de la referencia para luego poder hacar una consulta
     */
    //@Column(name = "CODIGO_TIPO_DOCUMENTO_REFERENCIA")
    //private String codigoTipoDocumentoReferencia;
    

    @JoinColumn(name = "KARDEX_ID")
    @ManyToOne
    private Kardex kardex;
    
    //@JoinColumn(name = "LOTE_ID")
    //@ManyToOne
    //private Lote lote;    
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kardexDetalle")
    private List<KardexItemEspecifico> detallesEspecificos;
    
    

    public KardexDetalle() {
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReferenciaDocumentoId() {
        return referenciaDocumentoId;
    }

    public void setReferenciaDocumentoId(Long referenciaDocumentoId) {
        this.referenciaDocumentoId = referenciaDocumentoId;
    }


    public String getCodigoTipoDocumento() {
        return codigoTipoDocumento;
    }

    public void setCodigoTipoDocumento(String codigoTipoDocumento) {
        this.codigoTipoDocumento = codigoTipoDocumento;
    }
    
    
    public TipoDocumentoEnum getCodigoTipoDocumentoEnum() {
        return TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(codigoTipoDocumento);
    }

    public void setCodigoTipoDocumentoEnum(TipoDocumentoEnum codigoTipoDocumentoEnum) {
        this.codigoTipoDocumento = codigoTipoDocumentoEnum.getCodigo();
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Kardex getKardex() {
        return kardex;
    }

    public void setKardex(Kardex kardex) {
        this.kardex = kardex;
    }

    public List<KardexItemEspecifico> getDetallesEspecificos() {
        return detallesEspecificos;
    }

    public void setDetallesEspecificos(List<KardexItemEspecifico> detallesEspecificos) {
        this.detallesEspecificos = detallesEspecificos;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Timestamp getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Timestamp fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreLegal() {
        return nombreLegal;
    }

    public void setNombreLegal(String nombreLegal) {
        this.nombreLegal = nombreLegal;
    }

    public Integer getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(Integer secuencial) {
        this.secuencial = secuencial;
    }

    public String getPuntoEstablecimiento() {
        return puntoEstablecimiento;
    }

    public void setPuntoEstablecimiento(String puntoEstablecimiento) {
        this.puntoEstablecimiento = puntoEstablecimiento;
    }

    public String getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(String puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public Date getFechaDocumento() {
        return fechaDocumento;
    }

    public void setFechaDocumento(Date fechaDocumento) {
        this.fechaDocumento = fechaDocumento;
    }

    /*public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }*/
    
    

    /*public String getCodigoTipoDocumentoReferencia() {
        return codigoTipoDocumentoReferencia;
    }

    public void setCodigoTipoDocumentoReferencia(String codigoTipoDocumentoReferencia) {
        this.codigoTipoDocumentoReferencia = codigoTipoDocumentoReferencia;
    }
    
    public TipoDocumentoEnum getCodigoTipoDocumentoReferenciaEnum() {
        return TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(codigoTipoDocumentoReferencia);
    }

    public void setCodigoTipoDocumentoReferenciaEnum(TipoDocumentoEnum codigoTipoDocumentoEnum) {
        this.codigoTipoDocumentoReferencia = codigoTipoDocumentoEnum.getCodigo();
    }*/
        
        /**
     * Aregando datos adicionales de los item de los kardex
     */
    public void addDetalle(KardexItemEspecifico detalle)
    {
        if(this.detallesEspecificos==null)
        {
            this.detallesEspecificos=new ArrayList<KardexItemEspecifico>();
        }
        detalle.setKardexDetalle(this);
        this.detallesEspecificos.add(detalle);
        
    }
    
    public String getPreimpreso()
    {
        if (secuencial == null) 
        {
            return "";
        }
        else
        {
            return UtilidadesTextos.llenarCarateresIzquierda(puntoEmision,3,"0")+"-"+UtilidadesTextos.llenarCarateresIzquierda(puntoEstablecimiento,3,"0")+"-"+UtilidadesTextos.llenarCarateresIzquierda(secuencial+"",9,"0");
        }
    }
    
    public void recalcularTotal()
    {
        this.cantidad=(getDetallesEspecificos()!=null)?new BigDecimal(getDetallesEspecificos().size()):BigDecimal.ZERO;
        
        this.precioTotal=precioUnitario.multiply(cantidad);
    }
    
    public void recalcularTotalSinGarantia()
    {
        //this.cantidad=(getDetallesEspecificos()!=null)?getDetallesEspecificos().size():0;
        this.precioTotal=precioUnitario.multiply(cantidad);
    }
    
    

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.id);
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
        final KardexDetalle other = (KardexDetalle) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
