/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesJuridicas;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
 * @author
 */
@Entity
@Table(name =Persona.NOMBRE_TABLA)
@XmlRootElement
public class Persona implements Serializable, Comparable<Persona> {
    
    public static final String NOMBRE_TABLA="CLIENTE";

    public static final String IDENTIFICACION_CONSUMIDOR_FINAL = "9999999999999";

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CLIENTE_ID")
    private Long idCliente;
    @Column(name = "NOMBRES")
    private String nombres;
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Column(name = "IDENTIFICACION")
    private String identificacion;
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;

    @Deprecated
    @Column(name = "NOMBRE_LEGAL")
    private String nombreLegal;

    //@Deprecated
    //@Column(name = "DIRECCION")
    //private String direccion;
    @Deprecated
    @Column(name = "TELEFONO_CONVENCIONAL")
    private String telefonoConvencional;

    @Deprecated
    @Column(name = "EXTENSION_TELEFONO")
    private String extensionTelefono;

    @Deprecated
    @Column(name = "TELEFONO_CELULAR")
    private String telefonoCelular;

    @Column(name = "TIPO_CLIENTE")
    private String tipCliente;

    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;
    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "TIPO_IDENTIFICACION")
    private String tipoIdentificacion;

    @Column(name = "OBLIGADO_LLEVAR_CONTABILIDAD")
    private String obligadoLlevarContabilidad;

    @Column(name = "DIAS_CREDITO_CLIENTE")
    private Integer diasCreditoCliente;
    
    @Column(name = "DIAS_CREDITO_PROVEEDOR")
    private Integer diasCreditoProveedor;

    @JoinColumn(name = "NACIONALIDAD_ID")
    @ManyToOne
    private Nacionalidad nacionalidad;

    @JoinColumn(name = "SRI_FORMA_PAGO_ID")
    private SriFormaPago sriFormaPago;

    /**
     * Variable para saber si el envia clientes recomendados
     */
    @Column(name = "CONTACTO_CLIENTES")
    private String contactoCliente;

    @Column(name = "CONTACTO_CLIENTES_PORCENTAJE")
    private BigDecimal contactoClientePorcentaje;

    //@JoinColumn(name = "SRI_IDENTIFICACION_ID")
    //private SriIdentificacion sriTipoIdentificacion;
    /**
     * Variable para identificar el tipo de la persona, si es proveedor ,
     * cliente, o ambos
     */
    @Column(name = "TIPO_OPERADOR")
    private String tipo;
    
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    
    @Column(name = "FECHA_CREACION")
    protected Timestamp fechaCreacion;

    /**
     * Este variable solo es informativa para saber el nombre de la persona que
     * tenemos de contacto dentro de la empresa
     */
    @Column(name = "CONTACTO_CLIENTE_NOMBRE")
    private String contactoClienteNombre;

    @JoinColumn(name = "EMPRESA_ID")
    private Empresa empresa;
    

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "persona", fetch = FetchType.EAGER)
    private List<PersonaEstablecimiento> establecimientos;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "representante", fetch = FetchType.EAGER)
    private List<Estudiante> estudiantes;

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idcliente) {
        this.idCliente = idcliente;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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

    public String getTipCliente() {
        return tipCliente;
    }

    public void setTipCliente(String tipCliente) {
        this.tipCliente = tipCliente;
    }

    public void setTipClienteEnum(TipoClienteEnum enumerador) {
        this.tipCliente = enumerador.nombre;
    }

    //public String getDireccion() {
    //    return direccion;
    //}
    //public void setDireccion(String direccion) {
    //    this.direccion = direccion;
    //}
    public String getTelefonoConvencional() {
        return telefonoConvencional;
    }

    public void setTelefonoConvencional(String telefonoConvencional) {
        this.telefonoConvencional = telefonoConvencional;
    }

    public String getExtensionTelefono() {
        return extensionTelefono;
    }

    public void setExtensionTelefono(String extensionTelefono) {
        this.extensionTelefono = extensionTelefono;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public GeneralEnumEstado getEstadoEnum() {
        return GeneralEnumEstado.getEnum(estado);
    }

    public void setEstadoEnum(GeneralEnumEstado estado) {
        this.estado = estado.getEstado();
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Nacionalidad getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Nacionalidad nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public SriFormaPago getSriFormaPago() {
        return sriFormaPago;
    }

    public void setSriFormaPago(SriFormaPago sriFormaPago) {
        this.sriFormaPago = sriFormaPago;
    }

    //public SriIdentificacion getSriTipoIdentificacion() {
    //    return sriTipoIdentificacion;
    //}
    //public void setSriTipoIdentificacion(SriIdentificacion sriTipoIdentificacion) {
    //    this.sriTipoIdentificacion = sriTipoIdentificacion;
    //}
    public String getObligadoLlevarContabilidad() {
        return obligadoLlevarContabilidad;
    }

    public void setObligadoLlevarContabilidad(String obligadoLlevarContabilidad) {
        this.obligadoLlevarContabilidad = obligadoLlevarContabilidad;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public void setTipoIdentificacionEnum(TipoIdentificacionEnum enumerador) {
        this.tipoIdentificacion = enumerador.letra;
    }

    public String getContactoCliente() {
        return contactoCliente;
    }

    public void setContactoCliente(String contactoCliente) {
        this.contactoCliente = contactoCliente;
    }

    public void setContactoClienteEnum(EnumSiNo enumSiNO) {
        this.contactoCliente = enumSiNO.getLetra();
    }

    public BigDecimal getContactoClientePorcentaje() {
        return contactoClientePorcentaje;
    }

    public void setContactoClientePorcentaje(BigDecimal contactoClientePorcentaje) {
        this.contactoClientePorcentaje = contactoClientePorcentaje;
    }

    public void setObligadoLlevarContabilidadEnum(EnumSiNo enumSiNo) {
        this.obligadoLlevarContabilidad = enumSiNo.getLetra();
    }

    public Integer getDiasCreditoCliente() {
        return diasCreditoCliente;
    }

    public void setDiasCreditoCliente(Integer diasCreditoCliente) {
        this.diasCreditoCliente = diasCreditoCliente;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    public Boolean quitarEstablecimientoPorCodigo(String codigoSucursal)
    {
        if(this.establecimientos!=null)
        {
            for (PersonaEstablecimiento establecimiento : this.establecimientos) {
                if(establecimiento.getCodigoSucursal().equals(codigoSucursal))
                {
                    this.establecimientos.remove(establecimiento);
                    return true;
                }
            }
        }
        return false;
    }

    public void addEstablecimiento(PersonaEstablecimiento establecimiento) {
        if (this.establecimientos == null) {
            this.establecimientos = new ArrayList<PersonaEstablecimiento>();
        }
        establecimiento.setPersona(this);
        this.establecimientos.add(establecimiento);

    }

    ///Metodos personalizados
    public boolean isClienteFinal() {
        if (identificacion.equals(IDENTIFICACION_CONSUMIDOR_FINAL)) {
            return true;
        }
        return false;
    }

    public String getNombresCompletos() {
        String nombresTmp = (nombres != null) ? nombres : "";
        String apellidosTmp = (apellidos != null) ? apellidos : "";
        String nombresCompletosTmp=nombresTmp + " " + apellidosTmp;
        
        if(nombresCompletosTmp.trim().isEmpty())
        {
            return razonSocial;
        }
        
        return nombresTmp + " " + apellidosTmp;
    }

    public String getNombreSimple() {
        return apellidos.split(" ")[0] + " " + nombres.split(" ")[0];
    }

    /*
    public String getTelefonosTodos() {
        String telefonos = "";

        if (telefonoConvencional != null && !telefonoConvencional.equals("")) {
            telefonos = telefonoConvencional;
        }

        if (telefonoCelular != null && !telefonoCelular.equals("")) {
            //Si ya existe un telefono anterior agregado aumento el separador
            if (!telefonos.equals("")) {
                telefonos = telefonos + " - ";
            }

            telefonos = telefonos + telefonoCelular;
        }

        return telefonos;

    }*/
    public List<PersonaEstablecimiento> getEstablecimientos() {
        return establecimientos;
    }
    
    ///Metodo que devuelve solo los establecimientos activos
    public List<PersonaEstablecimiento> getEstablecimientosActivos() {
        List<PersonaEstablecimiento> establecimientosList=new ArrayList<PersonaEstablecimiento>();
        
        if(establecimientos!=null)
        {
            //TODO: Remplazar esto por alguna funcion en las utilidades de listas
            for (PersonaEstablecimiento establecimiento : establecimientos) 
            {
                if(establecimiento.getEstadoEnum().equals(GeneralEnumEstado.ACTIVO))
                {
                    establecimientosList.add(establecimiento);
                }
            }
            //return establecimientos.stream().filter(e -> e.getEstadoEnum().equals(GeneralEnumEstado.ACTIVO)).collect(Collectors.toList());
        }
        return establecimientosList;
    }

    public void setEstablecimientos(List<PersonaEstablecimiento> establecimientos) {
        this.establecimientos = establecimientos;
    }

    public TipoIdentificacionEnum getTipoIdentificacionEnum() {
        return TipoIdentificacionEnum.obtenerPorLetra(tipoIdentificacion);
    }

    public EnumSiNo getObligadoLlevarContabilidadEnum() {
        return EnumSiNo.getEnumByLetra(obligadoLlevarContabilidad);
    }

    public EnumSiNo getContactoClientesEnum() {
        return EnumSiNo.getEnumByLetra(contactoCliente);
    }

    public void setTipoEnum(OperadorNegocioEnum operadorEnum) {
        this.tipo = operadorEnum.getLetra();
    }

    public String getContactoClienteNombre() {
        return contactoClienteNombre;
    }

    public void setContactoClienteNombre(String contactoClienteNombre) {
        this.contactoClienteNombre = contactoClienteNombre;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public Integer getDiasCreditoProveedor() {
        return diasCreditoProveedor;
    }

    public void setDiasCreditoProveedor(Integer diasCreditoProveedor) {
        this.diasCreditoProveedor = diasCreditoProveedor;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    
    
    

    /**
     * ==================> Metodos utilidaes <=====================
     */
    public static String construirRazonSocial(String nombre, String apellido) {
        nombre = (nombre != null) ? nombre : "";
        apellido = (apellido != null) ? apellido : "";
        return apellido + " " + nombre;
    }

    /**
     * *
     * ==================> Validadores de los campos <======================
     * TODO: Terminar de hacer la validacion para casos mas complicados @return
     */
    public boolean validarCedula() {
        boolean verificador = false;
        Persona.TipoIdentificacionEnum tipoIdentificacion = getTipoIdentificacionEnum();

        if (tipoIdentificacion == null) {
            return false;
        }

        switch (tipoIdentificacion) {
            case RUC:
                verificador = UtilidadesJuridicas.validarTodosRuc(identificacion);
                break;
            case CEDULA:
                verificador = UtilidadesJuridicas.validarCedula(identificacion);
                break;
            case PASAPORTE:
            case SIN_DEFINIR:
                verificador = true;
                break;
            case CLIENTE_FINAL:
                verificador = (identificacion.equals("9999999999999")) ? true : false;
                break;
            default:
                verificador = false;
                break;
        }
        return verificador;
    }

    public void contruirRazonSocialConNombreYApellidos() {
        this.razonSocial = construirRazonSocial(nombres, apellidos);
    }

    public String imprimirDireccionPorDefecto() {
        if (establecimientos != null) {
            for (PersonaEstablecimiento establecimiento : establecimientos) {
                return establecimiento.getDireccion();
            }
        }
        return "";
    }

    public String imprimirTelefonoPorDefecto() {
        if (establecimientos != null) {
            for (PersonaEstablecimiento establecimiento : establecimientos) {
                if (establecimiento.getTelefonoCelular() != null) {
                    return establecimiento.getTelefonoCelular();
                }
            }
        }
        return "";
    }
    

    public ValidacionCedulaEnum validarIdentificacion() {
        if (identificacion == null) {
            return ValidacionCedulaEnum.DATO_NO_INGRESADO;
        }

        if (identificacion.length() == 0) {
            return ValidacionCedulaEnum.DATO_VACIO;
        }

        //Hacer validaciones de tamanio y texto cuando es diferente de pasaporte y sin definir
        if (!getTipoIdentificacionEnum().equals(TipoIdentificacionEnum.PASAPORTE) && !getTipoIdentificacionEnum().equals(TipoIdentificacionEnum.SIN_DEFINIR)) {
            if (identificacion.length() != 10 && identificacion.length() != 13) {
                return ValidacionCedulaEnum.TAMANIO_INCORRECTO;
            }

            try {
                new BigDecimal(identificacion);
            } catch (Exception e) {
                return ValidacionCedulaEnum.FORMATO_INCORRECTO;
            }
        }

        return ValidacionCedulaEnum.VALIDACION_CORRECTA;
    }

    public enum ValidacionCedulaEnum {
        DATO_NO_INGRESADO("Identificación sin ingresar"),
        DATO_VACIO("Identificación vacia"),
        TAMANIO_INCORRECTO("El tamaño de la Identificación es incorrecta"),
        FORMATO_INCORRECTO("La Identificación tiene caracteres no permitidos"),
        VALIDACION_CORRECTA;

        private String mensaje;

        private ValidacionCedulaEnum() {

        }

        private ValidacionCedulaEnum(String mensaje) {
            this.mensaje = mensaje;
        }

        public String getMensaje() {
            return mensaje;
        }

    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identificacion != null ? identificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.identificacion == null && other.identificacion != null) || (this.identificacion != null && !this.identificacion.equals(other.identificacion))) {
            return false;
        }
        return true;
    }

    /**
     * Datos adicionales
     *
     * @return
     */
    public String obtenerTodosTelefonos()
    {
        List<String> telefonos=new ArrayList<String>();
        if(establecimientos!=null)
        {
            for (PersonaEstablecimiento establecimiento : establecimientos) {
                if(establecimiento.getTelefonoCelular()!=null && !establecimiento.getTelefonoCelular().isEmpty())
                {
                    telefonos.add(establecimiento.getTelefonoCelular());
                }
                
                if(establecimiento.getTelefonoConvencional()!=null && !establecimiento.getTelefonoConvencional().isEmpty())
                {
                    telefonos.add(establecimiento.getTelefonoConvencional());
                }                
            }
        }
        
        return UtilidadesLista.castListToString(telefonos,"/");
    }

    public OperadorNegocioEnum getTipoEnum() {
        return OperadorNegocioEnum.getEnum(tipo);
    }

    @Override
    public String toString() {
        //return identificacion;
        return identificacion + " - " + getRazonSocial();
    }

    @Override
    public int compareTo(Persona o) {
        return this.getIdCliente().compareTo(o.getIdCliente());
    }

    public static final String[] tiposClientes = {"CLIENTE",
        "SUJETO RETENIDO",
        "DESTINATARIO"};

    public enum TipoClienteEnum {
        CLIENTE("CLIENTE"),
        SUJETO_RETENIDO("SUJETO RETENIDO"),
        DESTINATARIO("DESTINATARIO");

        private TipoClienteEnum(String nombre) {
            this.nombre = nombre;
        }

        public String nombre;

    };

    /**
     * CORRESPONDE A LA TABLA 2
     */
    public enum TipoIdentificacionEnum {
        RUC("R", "Ruc", "04", "01"),
        CEDULA("C", "Cédula", "05", "02"),
        PASAPORTE("P", "Pasaporte", "06", "03"),
        /**
         * Este tipo de DATO INTERNO me va a servir por ejemplo cuando NO TENGO LOS DATOS de un cliente pero igual necesito registrar
         * por ejemplo para manejar modulos de CRM
         */
        SIN_DEFINIR("S", "Sin Definir", "00", "00"),
        CLIENTE_FINAL("F", "Consumidor Final", "9999999999999", new BigDecimal("200"), "07", "07");

        private String letra;
        private String nombre;
        private String identificacion;
        private BigDecimal montoMaximo;
        private String codigoSriVenta;
        private String codigoSriCompra;

        private TipoIdentificacionEnum(String letra, String nombre, String codigoSriVenta, String codigoSriCompra) {
            this.letra = letra;
            this.nombre = nombre;
            this.codigoSriVenta = codigoSriVenta;
            this.codigoSriCompra = codigoSriCompra;
        }

        private TipoIdentificacionEnum(String letra, String nombre, String identificacion, BigDecimal montoMaximo, String codigoSriVenta, String codigoSriCompra) {
            this.letra = letra;
            this.nombre = nombre;
            this.identificacion = identificacion;
            this.montoMaximo = montoMaximo;
            this.codigoSriVenta = codigoSriVenta;
            this.codigoSriCompra = codigoSriCompra;
        }

        public String getLetra() {
            return letra;
        }

        public String getNombre() {
            return nombre;
        }

        public String getIdentificacion() {
            return identificacion;
        }

        public BigDecimal getMontoMaximo() {
            return montoMaximo;
        }

        public String getCodigoSriVenta() {
            return codigoSriVenta;
        }

        public String getCodigoSriCompra() {
            return codigoSriCompra;
        }

        public static TipoIdentificacionEnum obtenerPorLetra(String letra) {
            for (Persona.TipoIdentificacionEnum tipoIdentificacion : TipoIdentificacionEnum.values()) {
                if (tipoIdentificacion.getLetra().equals(letra)) {
                    return tipoIdentificacion;
                }
            }
            return null;
        }
        
        

        @Override
        public String toString() {
            return nombre;
        }

    }

}
