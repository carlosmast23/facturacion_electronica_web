/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author
 */
@MappedSuperclass
public abstract class ComprobanteEntity<T extends ComprobanteAdicional> implements Serializable {

    public abstract List<T> getDatosAdicionalesComprobante();

    public abstract void addDatoAdicionalAbstract(T comprobanteAdicional);
    //public abstract void(ComprobanteEntity comprobante);

    @Column(name = "CLAVE_ACCESO")
    protected String claveAcceso;

    @Column(name = "SECUENCIAL")
    protected Integer secuencial;

    @Column(name = "PUNTO_ESTABLECIMIENTO")
    protected BigDecimal puntoEstablecimiento;

    @Column(name = "PUNTO_EMISION")
    protected Integer puntoEmision;

    //@Column(name = "EMPRESA_ID")
    //protected Long empresaId;
    //@Column(name = "USUARIO_ID")
    @JoinColumn(name = "USUARIO_ID")
    protected Usuario usuario;
    //protected Long usuarioId;

    @Column(name = "FECHA_CREACION")
    protected Timestamp fechaCreacion;

    @Column(name = "FECHA_EMISION")
    @Temporal(TemporalType.DATE)
    protected Date fechaEmision;

    @Column(name = "RAZON_SOCIAL")
    protected String razonSocial;

    @Column(name = "IDENTIFICACION")
    protected String identificacion;

    /**
     * Este campo se va a referir a la direccion del cliente
     */
    @Column(name = "DIRECCION")
    protected String direccion;

    /**
     * Este campo se refiere a la direccion desde donde se esta facturando
     */
    @Column(name = "DIRECCION_ESTABLECIMIENTO")
    protected String direccionEstablecimiento;

    @Column(name = "DIRECCION_MATRIZ")
    protected String direccionMatriz;

    @Column(name = "TELEFONO")
    protected String telefono;

    @Column(name = "ESTADO")
    protected String estado;

    @Column(name = "OBLIGADO_LLEVAR_CONTABILIDAD")
    protected String obligadoLlevarContabilidad;

    //Para saber el tipo de emision si fue electronica o manual
    @Column(name = "TIPO_FACTURACION")
    private String tipoFacturacion;

    //Para grabar el codigo de los documentos que me va a sservir posiblemente en los ats
    @Column(name = "CODIGO_DOCUMENTO")
    private String codigoDocumento;

    @JoinColumn(name = "EMPRESA_ID")
    private Empresa empresa;

    /**
     * La fecha de cuando fue autorizado el documento , información importante
     * en especial para que puedan anular comprobante en el Sri
     */
    @Column(name = "FECHA_AUTORIZACION_SRI")
    protected Date fechaAutorizacionSri;

    /**
     * Tipo de ambiente de produccion o pruebas en el que fue emitido el
     * comprobante
     */
    @Column(name = "TIPO_AMBIENTE")
    protected String tipoAmbiente;
    
    
    @Column(name = "CONTRIBUYENTE_ESPECIAL")
    protected String contribuyenteEspecial;

    /**
     *
     */
    @JoinColumn(name = "SUCURSAL_EMPRESA_ID")
    protected Sucursal sucursalEmpresa;
    
    //NOTA: Dejo solo como long por que si hago mucho JoinColum cada vez que obtengo el objeto se hace muy pesado
    @Column(name = "PUNTO_EMISION_ID")
    protected Long puntoEmisionId;

    public ComprobanteEntity() {
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public Integer getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(Integer secuencial) {
        this.secuencial = secuencial;
    }

    public BigDecimal getPuntoEstablecimiento() {
        return puntoEstablecimiento;
    }

    public void setPuntoEstablecimiento(BigDecimal puntoEstablecimiento) {
        this.puntoEstablecimiento = puntoEstablecimiento;
    }

    public Integer getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(Integer puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    /*public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }*/

 /*public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }*/
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setEstadoEnum(ComprobanteEnumEstado estadoEnum) {
        this.estado = estadoEnum.getEstado();
    }

    public String getTipoFacturacion() {
        return tipoFacturacion;
    }

    public void setTipoFacturacion(String tipoFacturacion) {
        this.tipoFacturacion = tipoFacturacion;
    }

    public String getCodigoDocumento() {
        return codigoDocumento;
    }

    public void setCodigoDocumento(String codigoDocumento) {
        this.codigoDocumento = codigoDocumento;
    }

    public String getObligadoLlevarContabilidad() {
        return obligadoLlevarContabilidad;
    }

    public void setObligadoLlevarContabilidad(String obligadoLlevarContabilidad) {
        this.obligadoLlevarContabilidad = obligadoLlevarContabilidad;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getDireccionEstablecimiento() {
        return direccionEstablecimiento;
    }

    public void setDireccionEstablecimiento(String direccionEstablecimiento) {
        this.direccionEstablecimiento = direccionEstablecimiento;
    }

    public String getDireccionMatriz() {
        return direccionMatriz;
    }

    public void setDireccionMatriz(String direccionMatriz) {
        this.direccionMatriz = direccionMatriz;
    }

    public Date getFechaAutorizacionSri() {
        return fechaAutorizacionSri;
    }

    public void setFechaAutorizacionSri(Date fechaAutorizacionSri) {
        this.fechaAutorizacionSri = fechaAutorizacionSri;
    }

    public String getTipoAmbiente() {
        return tipoAmbiente;
    }

    public void setTipoAmbiente(String tipoAmbiente) {
        this.tipoAmbiente = tipoAmbiente;
    }

    public Sucursal getSucursalEmpresa() {
        return sucursalEmpresa;
    }

    public void setSucursalEmpresa(Sucursal sucursalEmpresa) {
        this.sucursalEmpresa = sucursalEmpresa;
    }

    public String getContribuyenteEspecial() {
        return contribuyenteEspecial;
    }

    public void setContribuyenteEspecial(String contribuyenteEspecial) {
        this.contribuyenteEspecial = contribuyenteEspecial;
    }

    public Long getPuntoEmisionId() {
        return puntoEmisionId;
    }

    public void setPuntoEmisionId(Long puntoEmisionId) {
        this.puntoEmisionId = puntoEmisionId;
    }

    
    
    public void addDatoAdicional(T comprobante) {
        if (comprobante.getTipoEnum().equals(ComprobanteAdicional.Tipo.TIPO_OTRO)  || comprobante.getTipoEnum().equals(ComprobanteAdicional.Tipo.TIPO_GUIA_REMISION) ) {
            addDatoAdicionalAbstract(comprobante);
        } else {
            if (comprobante.getTipoEnum().equals(ComprobanteAdicional.Tipo.TIPO_CORREO)) {
                addDatosAdicionalCorreo(comprobante);
            }
        }
    }

    /**
     * ==================> Metodos Personalizados
     * <================================ @return
     */
    protected void addDatosAdicionalCorreo(T comprobanteAdicional) {

        //if (this.datosAdicionales == null) {
        //    this.datosAdicionales = new ArrayList<FacturaAdicional>();
        //}
        //Buscar si existe un correo anterior para nombrar de forma secuencial
        Integer numeroMaximo = 0;
        if (getDatosAdicionalesComprobante() != null) {
            for (ComprobanteAdicional datoAdicional : getDatosAdicionalesComprobante()) {
                if (datoAdicional.getTipo().equals(comprobanteAdicional.getTipo())) {
                    if (datoAdicional.getNumero() > numeroMaximo) {
                        numeroMaximo = datoAdicional.getNumero();
                    }
                }
            }
        }

        comprobanteAdicional.setNumero(numeroMaximo + 1);
        //Modificar el nombre si el correo es mas de 2
        if (comprobanteAdicional.getNumero() > 1) {
            comprobanteAdicional.setCampo(comprobanteAdicional.getCampo() + " " + comprobanteAdicional.getNumero());
        }

        addDatoAdicionalAbstract(comprobanteAdicional);

    }

    public ComprobanteAdicional obtenerDatoAdicionalPorCampo(ComprobanteAdicional.CampoDefectoEnum campo) {
        if (getDatosAdicionalesComprobante() != null) {
            for (ComprobanteAdicional comprobanteAdicional : getDatosAdicionalesComprobante()) {
                if (comprobanteAdicional.getCampo().equals(campo.getNombre())) {
                    return comprobanteAdicional;
                }
            }
        }
        return null;
    }
    
    public List<ComprobanteAdicional> obtenerCorreos()
    {
        List<ComprobanteAdicional> resultado=new ArrayList<ComprobanteAdicional>();
        if(getDatosAdicionalesComprobante()!=null)
        {
            for (ComprobanteAdicional comprobanteAdicional : getDatosAdicionalesComprobante()) {
                if(comprobanteAdicional.getTipoEnum().equals(ComprobanteAdicional.Tipo.TIPO_CORREO))
                {
                    resultado.add(comprobanteAdicional);
                }
            }
        }
        return resultado;
    }
    
    public String obtenerCorreosStr()
    {
        List<ComprobanteAdicional> resultado=obtenerCorreos();
        return  UtilidadesLista.castListToString(resultado,",",new UtilidadesLista.CastListInterface<ComprobanteAdicional>() {
                @Override
                public String getString(ComprobanteAdicional dato) {
                    return dato.getValor();
                }
            });
    }

    /**
     * Elimina datos adicionales de la factura como correos o codigos de enlace
     * de los documentos
     */
    public void eliminarTodosDatosAdicionales() {
        if (getDatosAdicionalesComprobante() != null) {
            getDatosAdicionalesComprobante().clear();
        }
    }

    public String getPreimpreso() {
        
        //TODO: Este artificio utilizo porque genera para imprimir presupuestos desde la interfaz web
        if (puntoEmision == null) {
            puntoEmision = 0;
        }
        
        if(puntoEstablecimiento==null)
        {
            puntoEstablecimiento=BigDecimal.ZERO;
        }
        
        if(secuencial==null)
        {
            secuencial=0;
        }

        return UtilidadesTextos.llenarCarateresIzquierda(puntoEstablecimiento.toString(), 3, "0") + "-" + UtilidadesTextos.llenarCarateresIzquierda(puntoEmision.toString(), 3, "0") + "-" + UtilidadesTextos.llenarCarateresIzquierda(secuencial + "", 9, "0");
    }

    public DocumentoEnum getCodigoDocumentoEnum() {
        return DocumentoEnum.obtenerDocumentoPorCodigo(codigoDocumento);
    }
    
    public void setCodigoDocumentoEnum(DocumentoEnum documentoEnum) {
        this.codigoDocumento=documentoEnum.getCodigo();
    }

    public TipoEmisionEnum getTipoFacturacionEnum() {
        return TipoEmisionEnum.getEnumByEstado(tipoFacturacion);
    }

    public ComprobanteEntity.ComprobanteEnumEstado getEstadoEnum() {
        return ComprobanteEntity.ComprobanteEnumEstado.getEnum(estado);
    }

    public enum ComprobanteEnumEstado implements ParametroUtilidades.ComparadorInterface<ComprobanteEnumEstado>{
        /**
         * Cuando la factura se grabo y se autorizo en el SRI y no aplica
         * ninguna nota de credito
         */
        AUTORIZADO("A", "Autorizado"),
        /**
         * Estado cuando se graba la factura en la base de datos pero no esta
         * autorizado en el SRI
         */
        SIN_AUTORIZAR("S", "Sin Autorizar"),
        /**
         * Estado eliminado solo permitido si el comprobante no fue autorizado ,
         * en especial para las pruebas o casos que toque volver a generar el
         * comprobante porque estaba mal estructurado
         */
        ELIMINADO("E", "Eliminado"),
        /**
         * Este estado debere manejarse para saber que una factura autorizada
         * fue eliminada para el caso cuando se elimine desde el portal del SRI
         */
        ELIMINADO_SRI("N", "Anulado Sri"),
        /**
         * Estado auxiliar para decir al sistema que tiene buscar autorizados y
         * anulados por el Sri
         */
        TODOS_SRI("T", "Todos Sri"),
        /**
         * Estado que solo sirve para las proformas para saber que estan facturas
         * TODO: Buscar otra forma mejor de ubicar el estado de la proformas por que esta mesclando la logica de los comprobantes
         */
        FACTURADO_PROFORMA("F","Facturado");

        private ComprobanteEnumEstado(String estado, String nombre) {
            this.estado = estado;
            this.nombre = nombre;
        }

        private String estado;
        private String nombre;

        public static ComprobanteEnumEstado getEnum(String estado) {

            if(estado==null)
            {
                return null;
            }
            
            for (ComprobanteEnumEstado enumerador : ComprobanteEnumEstado.values()) {
                if (enumerador.estado.equals(estado)) {
                    return enumerador;
                }
            }
            return null;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return nombre;
        }

        @Override
        public ComprobanteEnumEstado consultarParametro(String nombreParametro) {
            return getEnum(nombreParametro);
        }

    }

    /**
     * Enumerado que me sirve para saber el tipo de emision si fue electronica o
     * manual
     */
    public enum TipoEmisionEnum {
        ELECTRONICA("e", "E", "Electrónica"),
        NORMAL("m", "F", "Manual");

        private TipoEmisionEnum(String letra, String codigoSri, String nombre) {
            this.letra = letra;
            this.nombre = nombre;
            this.codigoSri = codigoSri;
        }

        /**
         * Este va a ser el codigo principal que voy a grabar en la base de
         * datos para todo los casos
         */
        private String letra;
        private String nombre;
        private String codigoSri;

        public String getLetra() {
            return letra;
        }

        public String getNombre() {
            return nombre;
        }

        public String getCodigoSri() {
            return codigoSri;
        }

        public static TipoEmisionEnum getEnumByEstado(String estado) {

            for (TipoEmisionEnum enumerador : TipoEmisionEnum.values()) {
                if (enumerador.letra.equals(estado)) {
                    return enumerador;
                }
            }
            return null;
        }

        public static TipoEmisionEnum getEnumByCodigoSri(String codigoSri) {

            for (TipoEmisionEnum enumerador : TipoEmisionEnum.values()) {
                if (enumerador.codigoSri.equals(codigoSri)) {
                    return enumerador;
                }
            }
            return null;
        }

        public static TipoEmisionEnum getEnumByLetra(String letra) {

            for (TipoEmisionEnum enumerador : TipoEmisionEnum.values()) {
                if (enumerador.letra.equals(letra)) {
                    return enumerador;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return nombre;
        }

    }

    public enum TipoAmbienteEnum {
        PRODUCCION("p", "PRODUCCIÓN", "Producción"),
        PRUEBAS("b", "PRUEBAS", "Pruebas");

        private String letra;
        /**
         * Se refiere al nombre que me devuelve en el documento de autorizado
         * del Sri
         */
        private String nombreSRI;
        private String nombre;

        private TipoAmbienteEnum(String letra, String nombreSRI, String nombre) {
            this.letra = letra;
            this.nombreSRI = nombreSRI;
            this.nombre = nombre;
        }

        public String getLetra() {
            return letra;
        }

        public String getNombreSRI() {
            return nombreSRI;
        }

        public String getNombre() {
            return nombre;
        }

        public static TipoAmbienteEnum buscarPorNombreSri(String nombreSri) {
            for (TipoAmbienteEnum enumerador : TipoAmbienteEnum.values()) {

                if (enumerador.getNombreSRI().equals(nombreSri)) {
                    return enumerador;
                }
            }
            return null;
        }

    }
    
    
}
