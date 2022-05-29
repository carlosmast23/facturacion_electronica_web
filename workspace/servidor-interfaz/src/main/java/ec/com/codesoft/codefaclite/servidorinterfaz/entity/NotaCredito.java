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
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TODO: Corregir que existen propiedades duplicadas tanto en NotaCredito como en ComprobanteVentaNotaCreditoAbstract
 * @author Carlos
 */
@Entity
@Table(name = "NOTA_CREDITO")
public class NotaCredito extends ComprobanteVentaNotaCreditoAbstract<NotaCreditoAdicional>{
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(name = "CLAVE_ACCESO")
    //private String claveAcceso;
    //@Column(name = "EMPRESA_ID")
    //private Long empresaId;
    @Column(name = "TIPO_IDENTIFICACION_ID")
    private Long tipoClienteId;
    //@Column(name = "SECUENCIAL")
    //private Integer secuencial;
    //@Column(name = "PUNTO_ESTABLECIMIENTO")
    //private String puntoEstablecimiento;
    //@Column(name = "PUNTO_EMISION")
    //private String puntoEmision;
    //@Column(name = "FECHA_NOTA_CREDITO")
    //private Date fechaNotaCredito;
    //@Column(name = "FECHA_CREACION")
    //private Date fechaCreacion;
    
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
    
    @Column(name = "SUBTOTAL_SIN_IMPUESTOS")
    private BigDecimal subtotalSinImpuesto;
    
    @Column(name = "SUBTOTAL_DOCE")
    private BigDecimal subtotalImpuestos;
    
    @Column(name = "SUBTOTAL_CERO")
    private BigDecimal subtotalSinImpuestos;
    
    @Column(name = "VALOR_IVA_DOCE")
    private BigDecimal iva;
    
    //@Column(name = "VALOR_IVA_CERO")
    //private BigDecimal valorIvaCero;
    
    @Column(name = "IVA_SRI_ID")
    private Long ivaSriId;
    @Column(name = "TOTAL")
    private BigDecimal total;
    
    @Column(name = "VALOR_ICE")
    private BigDecimal ice;
    //@Column(name = "USUARIO_ID")
    //private Long usuarioId;
    //@Column(name = "ESTADO")
    //private String estado;
    @Column(name = "RAZON_MODIFICADO")
    private String razonModificado;
    //@Column(name = "RAZON_SOCIAL")
    //private String razonSocial;
    //@Column(name = "IDENTIFICACION")
    //private String identificacion;
    //@Column(name = "DIRECCION")
    //private String direccion;
    //@Column(name = "telefono")
    //private String telefono;
    
    @JoinColumn(name = "FACTURA_ID")
    @ManyToOne    
    private Factura factura;


    /*@JoinColumn(name = "CLIENTE_ID")
    @ManyToOne    
    private Persona cliente;*/
    
    @Column(name = "TIPO_DOCUMENTO")
    private String tipoDocumento;
    
    @Column(name = "FECHA_EMISION_DOC_SUSTENTO")
    protected Date fechaEmisionDocSustento;
    
    @Column(name = "NUM_DOC_MODIFICADO")
    protected String numDocModificado;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notaCredito",fetch = FetchType.EAGER)
    private List<NotaCreditoDetalle> detalles;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notaCredito",fetch = FetchType.EAGER)
    private List<NotaCreditoAdicional> datosAdicionales;

    public NotaCredito() {
        descuentoImpuestos=BigDecimal.ZERO;
        descuentoSinImpuestos=BigDecimal.ZERO;
        ice=BigDecimal.ZERO;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    /*public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }*/

    public Long getTipoClienteId() {
        return tipoClienteId;
    }

    public void setTipoClienteId(Long tipoClienteId) {
        this.tipoClienteId = tipoClienteId;
    }

    public Integer getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(Integer secuencial) {
        this.secuencial = secuencial;
    }

    /*public String getPuntoEstablecimiento() {
        return puntoEstablecimiento.;
    }

    public void setPuntoEstablecimiento(String puntoEstablecimiento) {
        this.puntoEstablecimiento = puntoEstablecimiento;
    }*/

    /*public String getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(String puntoEmision) {
        this.puntoEmision = puntoEmision;
    }*/

    /*public Date getFechaNotaCredito() {
        return fechaNotaCredito;
    }

    public void setFechaNotaCredito(Date fechaNotaCredito) {
        this.fechaNotaCredito = fechaNotaCredito;
    }*/

    public java.sql.Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(java.sql.Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public BigDecimal getSubtotalSinImpuesto() {
        return subtotalSinImpuesto;
    }

    public void setSubtotalSinImpuesto(BigDecimal subtotalSinImpuesto) {
        this.subtotalSinImpuesto = subtotalSinImpuesto;
    }

    public BigDecimal getSubtotalDoce() {
        return subtotalImpuestos;
    }

    public void setSubtotalDoce(BigDecimal subtotalDoce) {
        this.subtotalImpuestos = subtotalDoce;
    }

    public BigDecimal getSubtotalCero() {
        return subtotalSinImpuestos;
    }

    public void setSubtotalCero(BigDecimal subtotalCero) {
        this.subtotalSinImpuestos = subtotalCero;
    }

    public BigDecimal getValorIvaDoce() {
        return iva;
    }

    public void setValorIvaDoce(BigDecimal valorIvaDoce) {
        this.iva = valorIvaDoce;
    }
    /*
    public BigDecimal getValorIvaCero() {
        return valorIvaCero;
    }

    public void setValorIvaCero(BigDecimal valorIvaCero) {
        this.valorIvaCero = valorIvaCero;
    }*/

    public Long getIvaSriId() {
        return ivaSriId;
    }

    public void setIvaSriId(Long ivaSriId) {
        this.ivaSriId = ivaSriId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    /*public Persona getCliente() {
        return cliente;
    }

    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }*/

    public List<NotaCreditoDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<NotaCreditoDetalle> detalles) {
        this.detalles = detalles;
    }

    public String getRazonModificado() {
        return razonModificado;
    }

    public void setRazonModificado(String razonModificado) {
        this.razonModificado = razonModificado;
    }
    
    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<NotaCreditoAdicional> getDatosAdicionales() {
        return datosAdicionales;
    }

    public void setDatosAdicionales(List<NotaCreditoAdicional> datosAdicionales) {
        this.datosAdicionales = datosAdicionales;
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

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
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

    public Date getFechaEmisionDocSustento() {
        return fechaEmisionDocSustento;
    }

    public void setFechaEmisionDocSustento(Date fechaEmisionDocSustento) {
        this.fechaEmisionDocSustento = fechaEmisionDocSustento;
    }

    public String getNumDocModificado() {
        return numDocModificado;
    }

    public void setNumDocModificado(String numDocModificado) {
        this.numDocModificado = numDocModificado;
    }
    
    public BigDecimal getIce() {
        return ice;
    }

    public void setIce(BigDecimal ice) {
        this.ice = ice;
    }

    
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final NotaCredito other = (NotaCredito) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
       
    /**
     * Metodos personalizados
     */
    
    public ComprobanteEnumEstado getEstadoEnum()
    {
        return ComprobanteEnumEstado.getEnum(estado);
    }    
    
        /**
     * Informacion personaliazada
     */
    public void addDetalle(NotaCreditoDetalle detalle)
    {
        if(this.detalles==null)
        {
            this.detalles=new ArrayList<NotaCreditoDetalle>();
        }
        detalle.setNotaCredito(this);
        this.detalles.add(detalle);
        
    }    
    
    public void addDatoAdicional(NotaCreditoAdicional datoAdicional)
    {
        if(this.datosAdicionales==null)
        {
            this.datosAdicionales=new ArrayList<NotaCreditoAdicional>();
        }
        datoAdicional.setNotaCredito(this);
        this.datosAdicionales.add(datoAdicional);
    }
    
    public void addDatosAdicionalCorreo(String correo)
    {
        NotaCreditoAdicional datoAdicional=new NotaCreditoAdicional();
        datoAdicional.setCampo(NotaCreditoAdicional.CampoDefectoEnum.CORREO.getNombre());
        datoAdicional.setNotaCredito(this);
        datoAdicional.setTipo(FacturaAdicional.Tipo.TIPO_CORREO.getLetra());
        datoAdicional.setValor(correo);
        
        if (this.datosAdicionales == null) {
            this.datosAdicionales = new ArrayList<NotaCreditoAdicional>();
        }
        
        //Buscar si existe un correo anterior para nombrar de forma secuencial
        Integer numeroMaximo=0;
        for (NotaCreditoAdicional datoAdicionalTmp : datosAdicionales) {            
            if(datoAdicionalTmp.getTipo().equals(FacturaAdicional.Tipo.TIPO_CORREO.getLetra()))
            {
                if(datoAdicionalTmp.getNumero()>numeroMaximo)
                {
                    numeroMaximo=datoAdicionalTmp.getNumero();
                }
            }
        }
        
        datoAdicional.setNumero(numeroMaximo+1);
        //Modificar el nombre si el correo es mas de 2
        if(datoAdicional.getNumero()>1)
        {
            datoAdicional.setCampo(NotaCreditoAdicional.CampoDefectoEnum.CORREO.getNombre()+" "+datoAdicional.getNumero());
        }

        this.datosAdicionales.add(datoAdicional);
    
    }


    
    
    
    public void calcularTotalesDesdeDetalles()
    {
        //Solo calcular si la variables de detalles fue creada
        if(detalles==null || detalles.size()==0)
        {
            this.total=BigDecimal.ZERO;
            this.descuentoSinImpuestos=BigDecimal.ZERO;
            this.descuentoImpuestos=BigDecimal.ZERO;
            this.subtotalSinImpuestos=BigDecimal.ZERO;            
            this.subtotalSinImpuestos=BigDecimal.ZERO;
            this.iva=BigDecimal.ZERO;
            return;
        }
        
        BigDecimal total=BigDecimal.ZERO; //total de la factura
        
        BigDecimal subTotalSinImpuestos=BigDecimal.ZERO;//Sin el descuento
        BigDecimal subTotalConImpuestos=BigDecimal.ZERO;//Sin los descuentos
        
        BigDecimal descuentoSinImpuestos=BigDecimal.ZERO; //
        BigDecimal descuentoConImpuestos=BigDecimal.ZERO; //
        
        BigDecimal impuestoIva=BigDecimal.ZERO; //
        
        BigDecimal ivaDecimal=BigDecimal.ZERO; //Todo: Variable donde se almacena el iva de uno de los detalles (pero si tuviera varias ivas distintos de 0 , se generaria poroblemas)
        
        for (NotaCreditoDetalle detalle : detalles) {
            //Sumar los subtotales
            if(detalle.getIvaPorcentaje().equals(0))
            {
                subTotalSinImpuestos=subTotalSinImpuestos.add(detalle.getPrecioUnitario().multiply(detalle.getCantidad()));
                descuentoSinImpuestos=descuentoSinImpuestos.add(detalle.getDescuento());
            }
            else
            {                
                subTotalConImpuestos=subTotalConImpuestos.add(detalle.getPrecioUnitario().multiply(detalle.getCantidad()));
                descuentoConImpuestos=descuentoConImpuestos.add(detalle.getDescuento());
                
                ivaDecimal=new BigDecimal(detalle.getIvaPorcentaje().toString()).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP);
                impuestoIva=subTotalConImpuestos.subtract(descuentoConImpuestos).multiply(ivaDecimal);
            }
           
            
        }
        
        //Calcula el total de los totales
        total=subTotalSinImpuestos.subtract(descuentoSinImpuestos)
                .add(subTotalConImpuestos.subtract(descuentoConImpuestos))
                .add(impuestoIva);
        
       
        /**
         * Recalcular los valores partiendo del total para redondear con 2 cifras y que los valores cuadren
         */
        //total=total.setScale(2,BigDecimal.ROUND_HALF_UP);
        this.total=total.setScale(2,BigDecimal.ROUND_HALF_UP); //valor final con 2 decimales
        
        this.descuentoSinImpuestos=descuentoSinImpuestos.setScale(2,BigDecimal.ROUND_HALF_UP); //Este valor no se mueve porque debe ser fijo de 2 decimales segun el sri
        this.subtotalSinImpuestos=subTotalSinImpuestos.setScale(2,BigDecimal.ROUND_HALF_UP);// Este valor se redondea y tampoco ya no se mueve porque no interfiere con el iva donde se descuadra //TODO: PERO REVISAR ESTA AFIRMACION
        
        //---------------- CALCULOS PARA LOS VALORES CON IMPUESTAS QUE ES LA PARTE DONDE GENERAN PROBLEMAS ------------------------///
        BigDecimal ivaDecimalTmp=ivaDecimal.add(BigDecimal.ONE); //1.12 por ejemplo
        
        //Valor total solo de los valores que tienen impuestos
        BigDecimal totalConImpuestos=this.total.subtract(this.subtotalSinImpuestos).add(this.descuentoSinImpuestos); //esto ya tiene 2 decimales no debo redondear
        
        //Estos valores seteo directo porque solo pueden tener 2 decimales en los calculos y no deberia cambiar porque generarian confusion
        this.descuentoImpuestos = descuentoConImpuestos.setScale(2,BigDecimal.ROUND_HALF_UP);
        
        //Calculo el subtotal ya restado el descuento diviendo para 1.12 por ejemplo
        BigDecimal subtotalMenosImpuestos=totalConImpuestos.divide(ivaDecimalTmp,2,BigDecimal.ROUND_HALF_UP);
        
        //Al subtotal menos impuesto le sumo el descuento y ya tengo el subtotal original
        this.subtotalImpuestos=subtotalMenosImpuestos.add(this.descuentoImpuestos);
        
        //Calcular el iva de la resta del del total -subtotal
        this.iva=totalConImpuestos.subtract(subtotalMenosImpuestos);
 
    
    }

    @Override
    public BigDecimal getSubtotalImpuestosMenosDescuento() {
        return subtotalImpuestos.subtract(descuentoImpuestos);
    }

    @Override
    public BigDecimal getSubtotalSinImpuestosMenosDescuento() {
        return subtotalSinImpuestos.subtract(descuentoSinImpuestos);
    }
    
    

    @Override
    public List<NotaCreditoAdicional> getDatosAdicionalesComprobante() {
        return (List<NotaCreditoAdicional>)(ArrayList<?>)getDatosAdicionales();
    }

    @Override
    public void addDatoAdicionalAbstract(NotaCreditoAdicional comprobanteAdicional) {
         NotaCreditoAdicional datoAdicional=(NotaCreditoAdicional) comprobanteAdicional;
        if(this.datosAdicionales==null)
        {
            this.datosAdicionales=new ArrayList<NotaCreditoAdicional>();
        }
        datoAdicional.setNotaCredito(this);
        this.datosAdicionales.add(datoAdicional);
    }

    @Override
    public List<DetalleFacturaNotaCeditoAbstract> getDetallesComprobante() {
        return (List<DetalleFacturaNotaCeditoAbstract>)(List<?>)detalles;
    }
    
    /**
     * TODO: Ver si puedo unificar este metodo con el resto de los comprobantes electronicos
     * @return 
     */
    public Map<String, String> getMapAdicional() {
        Map<String, String> parametroMap = new LinkedHashMap<String, String>();
        if (getDatosAdicionales() != null) {
            for (NotaCreditoAdicional datoAdicional : getDatosAdicionales()) {
                parametroMap.put(datoAdicional.getCampo(), datoAdicional.getValor());
            }
        }
        return parametroMap;
    }

    
}
