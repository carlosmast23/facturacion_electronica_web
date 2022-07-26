/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoDetalleEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author
 */
@Entity
@Table(name = "CARTERA_DETALLE")
public class CarteraDetalle implements Serializable,Cloneable {
    
    private static Long idTemporal=1l;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    /**
     * Guarda el tipo de referencia para saber directamente de donde viene a donde
     * esta enalazado el producto, rubro o servicio
     */
    @Column(name = "TIPO_REFERENCIA")
    private String tipoReferencia;
    
    @Column(name = "REFERENCIA_ID")
    private Long referenciaId;
    
    @Column(name = "TOTAL")
    private BigDecimal total;
    
    @Column(name = "SALDO")
    private BigDecimal saldo;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;    
    
    @Column(name = "CODIGO_DETALLE_DOCUMENTO")
    private String codigoDetalleDocumento;
        
    @JoinColumn(name="CARTERA_ID")
    @ManyToOne(optional = false)
    private Cartera cartera;
    
    
    @OneToMany(mappedBy = "carteraDetalle", fetch = FetchType.EAGER)
    private List<CarteraCruce> cruces;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoReferencia() {
        return tipoReferencia;
    }

    public void setTipoReferencia(String tipoReferencia) {
        this.tipoReferencia = tipoReferencia;
    }
    
    public DocumentoEnum getTipoReferenciaEnum() {
        return DocumentoEnum.obtenerDocumentoPorCodigo(tipoReferencia);
    }

    public void setTipoReferenciaEnum(DocumentoEnum documentoEnum) {
        this.tipoReferencia = documentoEnum.getCodigo();
    }
    
    

    public Long getReferenciaId() {
        return referenciaId;
    }

    public void setReferenciaId(Long referenciaId) {
        this.referenciaId = referenciaId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Cartera getCartera() {
        return cartera;
    }

    public void setCartera(Cartera cartera) {
        this.cartera = cartera;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<CarteraCruce> getCruces() {
        return cruces;
    }

    public void setCruces(List<CarteraCruce> cruces) {
        this.cruces = cruces;
    }

    public String getCodigoDetalleDocumento() {
        return codigoDetalleDocumento;
    }

    public void setCodigoDetalleDocumento(String codigoDetalleDocumento) {
        this.codigoDetalleDocumento = codigoDetalleDocumento;
    }
    
    
    public DocumentoDetalleEnum getCodigoDetalleDocumentoEnum() {
        return DocumentoDetalleEnum.findByCodigo(codigoDetalleDocumento);
    }

    public void setCodigoDetalleDocumentoEnum(DocumentoDetalleEnum codigoDetalleDocumentoEnum) {
        if(codigoDetalleDocumentoEnum!=null)
        {
            this.codigoDetalleDocumento = codigoDetalleDocumentoEnum.getCodigo();
        }
        else
        {
            this.codigoDetalleDocumento=null;
        }
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
        final CarteraDetalle other = (CarteraDetalle) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    public CarteraDetalle clone(){
        CarteraDetalle obj=null;
        try{
            obj=(CarteraDetalle) super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }
    
    //TODO: Optimizar solucion por que como son id que genera por cada instancia de los clientes se pueden chocar por algun motivo
    //SOLUCION: Parece que los ids deberia generar el servidor
    public Long generarIdTemporal()
    {
        return idTemporal++;
    }
    
    
    
}
