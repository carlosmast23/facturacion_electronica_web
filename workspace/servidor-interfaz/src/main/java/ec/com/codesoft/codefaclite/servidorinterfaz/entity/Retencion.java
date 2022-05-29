/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.varios.PreimpresoFormato;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
 * @author Carlos
 */
@Entity
@Table(name = "RETENCION")
@XmlRootElement
public class Retencion extends ComprobanteEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TIPO_IDENTIFICACION_ID")
    private Long tipoClienteId;

    @Column(name = "FECHA_EMISION")
    private Date fechaEmision;
    
    @Column(name = "PREIMPRESO_COMPRA")
    private String preimpresoDocumento;

    /**
     * Fecha emision documento corresponde a la fecha de la factura de compra
     */
    @Column(name = "FECHA_EMISION_COMPRA")
    private Date fechaEmisionDocumento;

    @JoinColumn(name = "PROVEEDOR_ID")
    @ManyToOne
    private Persona proveedor;

    @JoinColumn(name = "COMPRA_ID")
    @ManyToOne
    private Compra compra;
    
    /**
     * Variable para saber de que tipo es la retencion ejemplo si es libre o tiene referencias
     */
    @Column(name = "TIPO_DOCUMENTO")
    private String tipoDocumento;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "retencion", fetch = FetchType.EAGER)
    private List<RetencionDetalle> detalles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "retencion", fetch = FetchType.EAGER)
    private List<RetencionAdicional> datosAdicionales;

    public Retencion() {

    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    
    public Integer getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(Integer secuencial) {
        this.secuencial = secuencial;
    }

    public Persona getProveedor() {
        return proveedor;
    }

    public void setProveedor(Persona proveedor) {
        this.proveedor = proveedor;
    }

    public List<RetencionDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<RetencionDetalle> detalles) {
        this.detalles = detalles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Long getTipoClienteId() {
        return tipoClienteId;
    }

    public void setTipoClienteId(Long tipoClienteId) {
        this.tipoClienteId = tipoClienteId;
    }

    public String getPreimpresoDocumento() {
        return preimpresoDocumento;
    }
    
    public PreimpresoFormato getPreimpresoDocumentoEnum() {
        return new PreimpresoFormato(preimpresoDocumento);
    }

    public void setPreimpresoDocumento(String preimpresoDocumento) {
        this.preimpresoDocumento = preimpresoDocumento;
    }

    public Date getFechaEmisionDocumento() {
        return fechaEmisionDocumento;
    }

    public void setFechaEmisionDocumento(Date fechaEmisionDocumento) {
        this.fechaEmisionDocumento = fechaEmisionDocumento;
    }
    
    

    public List<RetencionAdicional> getDatosAdicionales() {
        return datosAdicionales;
    }

    public void setDatosAdicionales(List<RetencionAdicional> datosAdicionales) {
        this.datosAdicionales = datosAdicionales;
    }

    public void addDatoAdicional(String campo, String valor) {
        RetencionAdicional dato = new RetencionAdicional();
        dato.setCampo(campo);
        dato.setNumero(0);
        dato.setTipo(FacturaAdicional.Tipo.TIPO_OTRO.getLetra());
        dato.setValor(valor);

        addDatoAdicional(dato);
    }

    public void addDatoAdicional(RetencionAdicional datoAdicional) {
        if (this.datosAdicionales == null) {
            this.datosAdicionales = new ArrayList<RetencionAdicional>();
        }
        datoAdicional.setRetencion(this);
        this.datosAdicionales.add(datoAdicional);
    }

    public void addDatosAdicionalCorreo(String correo) {
        RetencionAdicional retencionAdicional = new RetencionAdicional();
        retencionAdicional.setCampo(RetencionAdicional.CampoDefectoEnum.CORREO.getNombre());
        retencionAdicional.setRetencion(this);
        retencionAdicional.setTipo(FacturaAdicional.Tipo.TIPO_CORREO.getLetra());
        retencionAdicional.setValor(correo);

        if (this.datosAdicionales == null) {
            this.datosAdicionales = new ArrayList<RetencionAdicional>();
        }

        //Buscar si existe un correo anterior para nombrar de forma secuencial
        Integer numeroMaximo = 0;
        for (RetencionAdicional datoAdicional : datosAdicionales) {
            if (datoAdicional.getTipo().equals(FacturaAdicional.Tipo.TIPO_CORREO.getLetra())) {
                if (datoAdicional.getNumero() > numeroMaximo) {
                    numeroMaximo = datoAdicional.getNumero();
                }
            }
        }

        retencionAdicional.setNumero(numeroMaximo + 1);
        //Modificar el nombre si el correo es mas de 2
        if (retencionAdicional.getNumero() > 1) {
            retencionAdicional.setCampo(RetencionAdicional.CampoDefectoEnum.CORREO.getNombre() + " " + retencionAdicional.getNumero());
        }

        this.datosAdicionales.add(retencionAdicional);

    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    
    
    public TipoDocumentoEnum getTipoDocumentoEnum()
    {
        return TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(tipoDocumento);
    }

    public RetencionAdicional obtenerDatoAdicionalPorCampo(String nombre) {
        if (this.datosAdicionales != null) {
            for (RetencionAdicional retencionAdicional : datosAdicionales) {
                if (retencionAdicional.getCampo().equals(nombre)) {
                    return retencionAdicional;
                }
            }
        }
        return null;
    }
    
    public BigDecimal obtenerTotalNotaCredito()
    {
        if(detalles==null)return BigDecimal.ZERO;
        
        BigDecimal total=BigDecimal.ZERO;
        for (RetencionDetalle detalle : detalles) {
            total=total.add(detalle.getValorRetenido());
        }
        return total;
    }
    
    public BigDecimal getTotalValorRetenido(String codigoRetencion)
    {
        //ServiceFactory.getFactory().getSriRetencionRentaServiceIf().obtenerTodosOrdenadoPorCodigo()
        BigDecimal total=BigDecimal.ZERO;
        for (RetencionDetalle detalle : detalles) 
        {
            if(detalle.getCodigoSri().equals(codigoRetencion))
            {
                total=total.add(detalle.getValorRetenido());
            }
        }
        return total;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
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
        final Retencion other = (Retencion) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    /**
     * Metodos personalizados
     */
    /**
     * Formas de pago adicional
     */
    public void addDetalle(RetencionDetalle detalleRetencion) {
        if (this.detalles == null) {
            this.detalles = new ArrayList<RetencionDetalle>();
        }
        
        //Agregupar las retenciones que tiene el mismo codigo
        for(RetencionDetalle detalle : detalles)
        {
            if(detalle.getCodigoRetencionSri().equals(detalleRetencion.getCodigoRetencionSri()) &&
                    detalle.getCodigoSri().equals(detalle.getCodigoSri()) &&
                    detalle.getPorcentajeRetener().compareTo(detalleRetencion.getPorcentajeRetener())==0)
            {
                
                detalle.setValorRetenido(detalle.getValorRetenido().add(detalleRetencion.getValorRetenido()));
                detalle.setBaseImponible(detalle.getBaseImponible().add(detalleRetencion.getBaseImponible()));
                return;
            }
            
        }
        detalleRetencion.setRetencion(this);
        this.detalles.add(detalleRetencion);
        

    }
    
    /**
     * Obtiene el preimpreso del documento pero con formato con lineas medias
     * @return 
     */
    public String getPreimpresoDocumentoFormato()
    {
        String formatoPreimpreso="";
        if(preimpresoDocumento!=null && !preimpresoDocumento.isEmpty())
        {
            formatoPreimpreso=preimpresoDocumento.substring(0, 3)+"-"+preimpresoDocumento.substring(3,6)+"-"+preimpresoDocumento.substring(6);
        }
        return formatoPreimpreso;
    }

    public String getPeriodoFiscal() {
        return UtilidadesFecha.obtenerMesStr(fechaEmision) + "/" + UtilidadesFecha.obtenerAnioStr(fechaEmision);
    }
    
    //TODO: Analizar un codigo unico para usar tambien la funcionalidad de la pantalla de factura
    public void addDatosAdicionalCorreo(String correo,FacturaAdicional.Tipo tipoCorreo,FacturaAdicional.CampoDefectoEnum campoDefecto)
    {
        RetencionAdicional retencionAdicional=new RetencionAdicional();
        retencionAdicional.setCampo(campoDefecto.getNombre());
        retencionAdicional.setRetencion(this);
        retencionAdicional.setTipo(tipoCorreo.getLetra());
        retencionAdicional.setValor(correo);
        
        if (this.datosAdicionales == null) {
            this.datosAdicionales = new ArrayList<RetencionAdicional>();
        }
        
        //Buscar si existe un correo anterior para nombrar de forma secuencial
        Integer numeroMaximo=0;
        for (RetencionAdicional datoAdicional : datosAdicionales) {            
            if(datoAdicional.getTipo().equals(tipoCorreo.getLetra()))
            {
                if(datoAdicional.getNumero()>numeroMaximo)
                {
                    numeroMaximo=datoAdicional.getNumero();
                }
            }
        }
        
        retencionAdicional.setNumero(numeroMaximo+1);
        //Modificar el nombre si el correo es mas de 2
        if(retencionAdicional.getNumero()>1)
        {
            retencionAdicional.setCampo(campoDefecto.getNombre()+" "+retencionAdicional.getNumero());
        }

        this.datosAdicionales.add(retencionAdicional);
    
    }

    @Override
    public List<ComprobanteAdicional> getDatosAdicionalesComprobante() {
        return (List<ComprobanteAdicional>)(ArrayList<?>)getDatosAdicionales();
    }

    /**
     * TODO: Revisa si este codigo esta duplicado
     * @param comprobanteAdicional 
     */
    @Override
    public void addDatoAdicionalAbstract(ComprobanteAdicional comprobanteAdicional) {
        RetencionAdicional datoAdicional=(RetencionAdicional) comprobanteAdicional;
        if(this.datosAdicionales==null)
        {
            this.datosAdicionales=new ArrayList<RetencionAdicional>();
        }
        datoAdicional.setRetencion(this);
        this.datosAdicionales.add(datoAdicional);
    }
    
    /**
     * TODO: Ver si puedo unificar este metodo con el resto de los comprobantes electronicos
     * @return 
     */
    public Map<String, String> getMapAdicional() {
        Map<String, String> parametroMap = new LinkedHashMap<String, String>();
        if (getDatosAdicionales() != null) {
            for (RetencionAdicional datoAdicional : getDatosAdicionales()) {
                parametroMap.put(datoAdicional.getCampo(), datoAdicional.getValor());
            }
        }
        return parametroMap;
    }

    

}
