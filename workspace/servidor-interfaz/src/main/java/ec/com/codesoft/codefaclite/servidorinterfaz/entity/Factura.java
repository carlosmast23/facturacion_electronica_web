/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional.CampoDefectoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadValidador;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.persistence.Transient;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = Factura.NOMBRE_TABLA)
public class Factura extends ComprobanteVentaNotaCreditoAbstract<FacturaAdicional> implements Cloneable {

    public static final String NOMBRE_TABLA="FACTURA";
    public static final String NOMBRE_PK="ID";
    
    private static final long serialVersionUID = -1238278914412853684L;

    //public static final String ESTADO_FACTURADO="F";
    //public static final String ESTADO_ANULADO="A";
    //public static final String ESTADO_PENDIENTE_FACTURA_ELECTRONICA="P";
    @Id
    @Column(name = Factura.NOMBRE_PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(name = "TIPO_IDENTIFICACION_ID")
    //private Long tipoClienteId;
    //@Column(name = "IVA_SRI_ID")
    @JoinColumn(name = "IVA_SRI_ID")
    private ImpuestoDetalle ivaSriId;

    @Column(name = "TOTAL_SUBSIDIO")
    private BigDecimal totalSubsidio;

    @Column(name = "ESTADO_NOTA_CREDITO")
    private String estadoNotaCredito;

    @Column(name = "TIPO_IDENTIFICACION_CODIGO_SRI")
    private String tipoIdentificacionCodigoSri;

    @Column(name = "ESTADO_ENVIADO_GUIA_REMISION")
    private String estadoEnviadoGuiaRemision;

    @Column(name = "FECHA_VENCIMIENTO_FACTURA")
    protected Date fechaVencimiento;

    @Column(name = "COD_ORIGEN_TRANSACCION")
    private String codigoOrigenTransaccion;

    @JoinColumn(name = "REFERIDO_ID")
    @ManyToOne
    private Persona referido;

    @JoinColumn(name = "TIPO_IDENTIFICACION_ID")
    private SriIdentificacion sriIdentificacion;

    @JoinColumn(name = "VENDEDOR_ID")
    private Empleado vendedor;

    @JoinColumn(name = "USUARIO_ID")
    private Usuario usuario;

    @JoinColumn(name = "PEDIDO_ID")
    private Factura proforma;

    @Column(name = "ZONA_ID")
    private Long zonaId;

    @Column(name = "ZONA_NOMBRE")
    private String zonaNombre;

    @Column(name = "RUTA_ID")
    private Long rutaId;

    @Column(name = "RUTA_NOMBRE")
    private String rutaNombre;

    @Column(name = "VENTA_CREDITO")
    private String ventaCredito;

    @Column(name = "DIAS_CREDITO")
    private Integer diasCredito;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura", fetch = FetchType.EAGER)
    private List<FacturaDetalle> detalles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura", fetch = FetchType.EAGER)
    private List<FormaPago> formaPagos;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura", fetch = FetchType.EAGER)
    private List<FacturaAdicional> datosAdicionales;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "factura", fetch = FetchType.EAGER)
    private List<NotaCredito> notasCredito;

    public Factura() {
        this.datosAdicionales = new ArrayList<FacturaAdicional>();
        this.formaPagos = new ArrayList<FormaPago>();

    }

    public Long getId() {
        return id;
    }

    public ImpuestoDetalle getIvaSriId() {
        return ivaSriId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIvaSriId(ImpuestoDetalle ivaSriId) {
        this.ivaSriId = ivaSriId;
    }

    public List<FacturaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<FacturaDetalle> detalles) {
        this.detalles = detalles;
    }


    /*public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }*/
    public List<FormaPago> getFormaPagos() {
        return formaPagos;
    }

    public void setFormaPagos(List<FormaPago> formaPagos) {
        this.formaPagos = formaPagos;
    }

    public List<FacturaAdicional> getDatosAdicionales() {
        return datosAdicionales;
    }

    public void setDatosAdicionales(List<FacturaAdicional> datosAdicionales) {
        this.datosAdicionales = datosAdicionales;
    }

    public String getEstadoNotaCredito() {
        return estadoNotaCredito;
    }

    public void setEstadoNotaCredito(String estadoNotaCredito) {
        this.estadoNotaCredito = estadoNotaCredito;
    }

    public Persona getReferido() {
        return referido;
    }

    public void setReferido(Persona referido) {
        this.referido = referido;
    }

    public SriIdentificacion getSriIdentificacion() {
        return sriIdentificacion;
    }

    public void setSriIdentificacion(SriIdentificacion sriIdentificacion) {
        this.sriIdentificacion = sriIdentificacion;
    }

    public String getTipoIdentificacionCodigoSri() {
        return tipoIdentificacionCodigoSri;
    }

    public void setTipoIdentificacionCodigoSri(String tipoIdentificacionCodigoSri) {
        this.tipoIdentificacionCodigoSri = tipoIdentificacionCodigoSri;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getEstadoEnviadoGuiaRemision() {
        return estadoEnviadoGuiaRemision;
    }

    public void setEstadoEnviadoGuiaRemision(String estadoEnviadoGuiaRemision) {
        this.estadoEnviadoGuiaRemision = estadoEnviadoGuiaRemision;
    }

    public EnumSiNo getEstadoEnviadoGuiaRemisionEnum() {
        return EnumSiNo.getEnumByLetra(estadoEnviadoGuiaRemision);
    }

    public void setEstadoEnviadoGuiaRemisionEnum(EnumSiNo estadoEnviadoGuiaRemisionEnum) {
        this.estadoEnviadoGuiaRemision = estadoEnviadoGuiaRemisionEnum.getLetra();
    }

    public String getCodigoOrigenTransaccion() {
        return codigoOrigenTransaccion;
    }

    public void setCodigoOrigenTransaccion(String codigoOrigenTransaccion) {
        this.codigoOrigenTransaccion = codigoOrigenTransaccion;
    }

    public OrigenTransaccionEnum getCodigoOrigenTransaccionEnum() {
        return OrigenTransaccionEnum.buscarPorCodigo(codigoOrigenTransaccion);
    }

    public void setCodigoOrigenTransaccionEnum(OrigenTransaccionEnum codigoOrigenTransaccionEnum) {
        this.codigoOrigenTransaccion = codigoOrigenTransaccionEnum.getCodigo();
    }

    /**
     * Informacion adicional
     */
    public EstadoNotaCreditoEnum getEstadoNotaCreditoEnum() {
        return EstadoNotaCreditoEnum.getEnum(estadoNotaCredito);
    }
    
    public void setEstadoNotaCreditoEnum(EstadoNotaCreditoEnum estadoNotaCreditoEnum) 
    {
        this.estadoNotaCredito=estadoNotaCreditoEnum.getEstado();
    }

    public void addDetalle(FacturaDetalle detalle) {
        if (this.detalles == null) {
            this.detalles = new ArrayList<FacturaDetalle>();
        }
        detalle.setFactura(this);
        this.detalles.add(detalle);
    }

    /**
     * Formas de pago adicional
     */
    public void addFormaPago(FormaPago formaPago) {
        if (this.formaPagos == null) {
            this.formaPagos = new ArrayList<FormaPago>();
        }
        formaPago.setFactura(this);
        this.formaPagos.add(formaPago);

    }

    public void restarValorFormaPago(BigDecimal total) {
        //TODO: Terminar de implementar para varias formas de pago
        if (this.formaPagos != null) {
            for (FormaPago formaPago : formaPagos) {
                formaPago.setTotal(formaPago.getTotal().subtract(total));
            }
        }
    }

    public BigDecimal getTotalFormasPago() {
        BigDecimal totalFormasPago = BigDecimal.ZERO;
        int res;
        if (formaPagos != null) {
            for (FormaPago fp : formaPagos) {
                totalFormasPago = totalFormasPago.add(fp.getTotal());
            }
        }
        return totalFormasPago;
        //totalFormasPago = totalFormasPago.add(valorTotalFormaDePago);

    }

    public BigDecimal getTotalFormasPagoCartera() {
        BigDecimal totalFormasPago = BigDecimal.ZERO;
        if (formaPagos != null) {
            for (FormaPago fp : formaPagos) {
                if (fp.getSriFormaPago().getAlias().equals(SriFormaPago.FORMA_PAGO_CARTERA_ALIAS)) {
                    totalFormasPago = totalFormasPago.add(fp.getTotal());
                }
            }
        }
        return totalFormasPago;

    }

    public BigDecimal getTotalFormasPagoSinCartera() {
        BigDecimal totalFormasPago = BigDecimal.ZERO;
        if (formaPagos != null) {
            for (FormaPago fp : formaPagos) {
                if (!fp.getSriFormaPago().getAlias().equals(SriFormaPago.FORMA_PAGO_CARTERA_ALIAS)) {
                    totalFormasPago = totalFormasPago.add(fp.getTotal());
                }
            }
        }
        return totalFormasPago;
        //totalFormasPago = totalFormasPago.add(valorTotalFormaDePago);

    }

    public FormaPago buscarFormaPagoSinGrabar(SriFormaPago sriFormaPago) {
        if (formaPagos != null) {
            for (FormaPago formaPago : formaPagos) {
                if (sriFormaPago.getAlias().equals(formaPago.getSriFormaPago().getAlias())) {
                    return formaPago;
                }
            }
        }
        return null;
    }

    /**
     * Obtiene una forma de pago que se distita de cartera
     *
     * @return
     */
    public FormaPago buscarFormaPagoDistintaDeCartera() {
        if (formaPagos != null) {
            for (FormaPago formaPago : formaPagos) {
                if (!formaPago.getSriFormaPago().getAlias().equals(SriFormaPago.FORMA_PAGO_CARTERA_ALIAS)) {
                    return formaPago;
                }
            }
        }
        return null;
    }

    public List<FormaPago> buscarListaFormasPagoDistintaDeCartera() {
        List<FormaPago> formasPago = new ArrayList<FormaPago>();
        if (formaPagos != null) {
            for (FormaPago formaPago : formaPagos) {
                if (!formaPago.getSriFormaPago().getAlias().equals(SriFormaPago.FORMA_PAGO_CARTERA_ALIAS)) {
                    formasPago.add(formaPago);
                }
            }
        }
        return formasPago;
    }

    public FormaPago buscarFormaPagoConCartera() {
        if (formaPagos != null) {
            for (FormaPago fp : formaPagos) {
                if (fp.getSriFormaPago().getAlias().equals(SriFormaPago.FORMA_PAGO_CARTERA_ALIAS)) {
                    return fp;
                }
            }
        }
        return null;
    }

    public BigDecimal getTotalSubsidio() {
        return totalSubsidio;
    }

    public void setTotalSubsidio(BigDecimal totalSubsidio) {
        this.totalSubsidio = totalSubsidio;
    }

    public Long getZonaId() {
        return zonaId;
    }

    public void setZonaId(Long zonaId) {
        this.zonaId = zonaId;
    }

    public Long getRutaId() {
        return rutaId;
    }

    public void setRutaId(Long rutaId) {
        this.rutaId = rutaId;
    }

    public String getZonaNombre() {
        return zonaNombre;
    }

    public void setZonaNombre(String zonaNombre) {
        this.zonaNombre = zonaNombre;
    }

    public String getRutaNombre() {
        return rutaNombre;
    }

    public void setRutaNombre(String rutaNombre) {
        this.rutaNombre = rutaNombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
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
        final Factura other = (Factura) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public Empleado getVendedor() {
        return vendedor;
    }

    public void setVendedor(Empleado vendedor) {
        this.vendedor = vendedor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Factura getProforma() {
        return proforma;
    }

    public void setProforma(Factura proforma) {
        this.proforma = proforma;
    }

    public String getVentaCredito() {
        return ventaCredito;
    }

    public void setVentaCredito(String ventaCredito) {
        this.ventaCredito = ventaCredito;
    }

    public Boolean getVentaCreditoBool() {
        EnumSiNo enumSiNo = getVentaCreditoEnum();
        if (enumSiNo != null) {
            return getVentaCreditoEnum().getBool();
        }
        return false;
    }

    public void setVentaCreditoBool(Boolean ventaCreditoBool) {
        this.ventaCredito = EnumSiNo.getEnumByBoolean(ventaCreditoBool).getLetra();
    }

    public EnumSiNo getVentaCreditoEnum() {
        return EnumSiNo.getEnumByLetra(ventaCredito);
    }

    public void setVentaCreditoEnum(EnumSiNo ventaCreditoEnum) {
        this.ventaCredito = ventaCreditoEnum.getLetra();
    }

    public Integer getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(Integer diasCredito) {
        this.diasCredito = diasCredito;
    }

    /**
     * Metodo que devuelve los datos adicionales de la factura en tipo de dato
     * Map
     */
    public Map<String, String> getMapAdicional() {
        Map<String, String> parametroMap = new LinkedHashMap<String, String>();
        if (getDatosAdicionales() != null) {
            for (FacturaAdicional datoAdicional : getDatosAdicionales()) {
                parametroMap.put(datoAdicional.getCampo(), datoAdicional.getValor());
            }
        }
        return parametroMap;
    }

    /**
     * Metodo que permite saber si en la factura fueron ingresados correos
     *
     * @return
     */
    public boolean verificarExistenCorreosIngresados() {
        if (datosAdicionales != null) {
            for (FacturaAdicional datosAdicional : datosAdicionales) {
                if (datosAdicional.getTipo().equals(FacturaAdicional.Tipo.TIPO_CORREO.getLetra())) {
                    if (datosAdicional.getValor() != null && !datosAdicional.getValor().isEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public BigDecimal calcularSubsidio() {
        BigDecimal subSidioTotal = BigDecimal.ZERO;
        if (detalles != null) {
            for (FacturaDetalle detalle : detalles) {
                subSidioTotal = subSidioTotal.add(detalle.calcularSubsidio());
            }
        }
        return subSidioTotal;
    }

    @Override
    public List<FacturaAdicional> getDatosAdicionalesComprobante() {
        return getDatosAdicionales();
        //List<FacturaAdicional> detalles=getDatosAdicionales();
        //return (List<FacturaAdicional>)(ArrayList<?>)detalles;
    }

    @Override
    public void addDatoAdicionalAbstract(FacturaAdicional comprobanteAdicional) {
        FacturaAdicional datoAdicional = (FacturaAdicional) comprobanteAdicional;
        if (this.datosAdicionales == null) {
            this.datosAdicionales = new ArrayList<FacturaAdicional>();
        }
        datoAdicional.setFactura(this);
        this.datosAdicionales.add(datoAdicional);
    }

    @Override
    public List<DetalleFacturaNotaCeditoAbstract> getDetallesComprobante() {
        return (List<DetalleFacturaNotaCeditoAbstract>) (List<?>) detalles;
    }

    /**
     * Estados que me permiten distinger en que seccion del programa se esta
     * generando la factura o pedido
     */
    public enum OrigenTransaccionEnum {
        ESCRITORIO("Escritorio", "esc"),
        APLICACION_WEB("Web", "web"),
        WIDGETS_VENTA_DIARIA("Venta Diaria Widget", "wvd");

        private String nombre;
        private String codigo;

        private OrigenTransaccionEnum(String nombre, String codigo) {
            this.nombre = nombre;
            this.codigo = codigo;
        }

        public String getCodigo() {
            return codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public static OrigenTransaccionEnum buscarPorCodigo(String codigo) {
            for (OrigenTransaccionEnum value : OrigenTransaccionEnum.values()) {
                if (value.getCodigo().equals(codigo)) {
                    return value;
                }
            }
            return null;
        }

    }

    public enum EstadoNotaCreditoEnum {
        /**
         * Estado por defecto cuando no aplique ninguna nota de credito a la
         * factura
         */
        SIN_ANULAR("N", "Sin anular"),
        /**
         * Estado anulado cuando una nota de credito anulo totalmente la factura
         */
        ANULADO_TOTAL("A", "Anulado Total"),
        /**
         * Estado cuando aplica una nota de credito pero no sobre el total de la
         * factura
         */
        ANULADO_PARCIAL("P", "Anulado Parcial");

        private EstadoNotaCreditoEnum(String estado, String nombre) {
            this.estado = estado;
            this.nombre = nombre;
        }

        private String estado;
        private String nombre;

        public String getEstado() {
            return estado;
        }

        public String getNombre() {
            return nombre;
        }

        public static EstadoNotaCreditoEnum getEnum(String estado) {

            for (EstadoNotaCreditoEnum enumerador : EstadoNotaCreditoEnum.values()) {
                if (enumerador.getEstado().equals(estado)) {
                    return enumerador;
                }
            }
            return null;
        }

    }

    public GeneralEnumEstado getEnumEstadoProforma() {
        return GeneralEnumEstado.getEnum(estado);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Factura facturaClonada = (Factura) super.clone(); //To change body of generated methods, choose Tools | Templates.
        
        if(facturaClonada.getFormaPagos()!=null)
        {
            for (FormaPago formaPago : facturaClonada.getFormaPagos()) {
                formaPago.setId(null);
                formaPago.setFactura(facturaClonada);
            }
        }
        
        if(facturaClonada.getDetalles()!=null)
        {
            for (FacturaDetalle facturaDetalle : facturaClonada.getDetalles()) {
                facturaDetalle.setId(null);
                facturaDetalle.setFactura(facturaClonada);
            }
        }
        
        if(facturaClonada.getDatosAdicionales()!=null)
        {
            for (FacturaAdicional datoAdicional : facturaClonada.getDatosAdicionales()) {
                datoAdicional.setId(null);
                datoAdicional.setFactura(facturaClonada);
            }
        }
        //if(facturaClonada.)
        //{
        
        //}
        
        return facturaClonada;
    }

    /**
     * Metodo que me prmite obtener facturas dividas especialmente util en la
     * facturación manual cuando necesito facturar una cantidad más grande de
     * detalles que la que permite la hoja fisica
     *
     * @param limiteDetalle
     * @return
     */
    public List<Factura> dividirFactura(int limiteDetalle) throws ServicioCodefacException {
        if (limiteDetalle == 0) {
            throw new ServicioCodefacException("No se tiene configurado un límite para los detalles de las facturas físicas");
        }

        List<Factura> nuevasFacturasDivididas = new ArrayList<Factura>();
        for (int i = 0; i < this.detalles.size(); i = i + limiteDetalle) {
            Factura nuevaFactura = nuevaFacturaConLimite(i, i + limiteDetalle);
            nuevasFacturasDivididas.add(nuevaFactura);
        }
        return nuevasFacturasDivididas;
    }

    public Factura nuevaFacturaConLimite(int limiteInicia, int limiteFinal) {
        if (limiteFinal > detalles.size()) {
            limiteFinal = detalles.size();
        }

        Factura facturaNueva = null;
        try {
            facturaNueva = (Factura) clone();
            facturaNueva.setCliente(null);
            facturaNueva.setCliente(getCliente());

            facturaNueva.detalles = new ArrayList<>();

            List<FacturaDetalle> detallesTmp = new ArrayList<FacturaDetalle>(detalles.subList(limiteInicia, limiteFinal));
            //List<FacturaDetalle> detallesNuevos=new ArrayList<FacturaDetalle>();

            for (FacturaDetalle detalleTmp : detallesTmp) {
                //detalleTmp.setFactura(facturaNueva);
                //Genero una copia del detalle original para agregar a mi nueva factura
                FacturaDetalle facturaDetalleCopia=(FacturaDetalle) detalleTmp.clone();
                facturaNueva.addDetalle(facturaDetalleCopia);
            }

            ///////////////////////////////////////////////////
            facturaNueva.datosAdicionales = new ArrayList<>();

            List<FacturaAdicional> facturaAdicionalListTmp=new ArrayList<FacturaAdicional>(datosAdicionales);
            
            for (FacturaAdicional detalleTmp : facturaAdicionalListTmp) {
                //detalleTmp.setFactura(facturaNueva);
                facturaNueva.addDatoAdicional(detalleTmp);
            }
            //////////////////////////////////////////////////
            facturaNueva.formaPagos = new ArrayList<>();

            List<FormaPago> formaPagoTmpList =new ArrayList<FormaPago>(formaPagos);
            
            for (FormaPago detalleTmp : formaPagoTmpList) {
                //detalleTmp.setFactura(facturaNueva);
                facturaNueva.addFormaPago(detalleTmp);
            }
            facturaNueva.calcularTotalesDesdeDetalles();

        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Factura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return facturaNueva;
    }

    /**
     * Metodo que me permite obtener la cantidad de productos vendidos en la
     * factura
     *
     * @return
     */
    public BigDecimal cantidadTotalProductos() {
        BigDecimal cantidadTotal = BigDecimal.ZERO;

        if (detalles != null) {
            for (FacturaDetalle detalle : detalles) {
                cantidadTotal = cantidadTotal.add(detalle.getCantidad());
            }
        }

        return cantidadTotal;
    }
    
    public String getFechaEmisionFormat()
    {
        if(fechaEmision!=null)
        {
            return ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA.format(fechaEmision);
        }
        return "";
    }
    
    public List<FacturaDetalle> getDetallesOrdenados()
    {
        List<FacturaDetalle> facturaDetalleList=detalles;
        UtilidadesLista.ordenarLista(facturaDetalleList, new Comparator<FacturaDetalle>() {
            @Override
            public int compare(FacturaDetalle facturaDetalle1, FacturaDetalle facturaDetalle2) 
            {
                if(facturaDetalle1.getId()!=null && facturaDetalle2.getId()!=null)
                {
                    return facturaDetalle1.getId().compareTo(facturaDetalle2.getId());
                }
                else
                {
                    return 0; //Si no tienen un id se asume que son iguales
                }
                
            }
        });
        return facturaDetalleList;
    }

}
