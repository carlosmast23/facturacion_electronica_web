/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal.TipoSucursalEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.web.UtilidadesWeb;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author
 */
@Entity
@Table(name = "PERSONA_ESTABLECIMIENTO")
public class PersonaEstablecimiento implements  Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "CODIGO_SUCURSAL")
    private String codigoSucursal;
    
    @Column(name = "CODIGO_PERSONALIZADO")
    private String codigoPersonalizado;
    
    @Column(name = "NOMBRE_COMERCIAL")
    private String nombreComercial;
    
    @Column(name = "DIRECCION")
    private String direccion;
    
    @Column(name = "REFERENCIA_DIRECCION")
    private String referenciaDireccion;
    
    @Column(name = "TELEFONO_CONVENCIONAL")
    private String telefonoConvencional;
    
    @Column(name = "EXTENSION_TELEFONO")
    private String extensionTelefono;
    
    @Column(name = "TELEFONO_CELULAR")
    private String telefonoCelular;
    
    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;

    @Column(name = "TIPO_SUCURSAL")
    private String tipoSucursal;
    
    @Column(name = "LATITUD")
    private BigDecimal latitud;
    
    @Column(name = "LONGITUD")
    private BigDecimal longitud;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @JoinColumn(name ="PERSONA_ID")
    private Persona persona;
    
    @JoinColumn(name ="ZONA_ID")
    private Zona zona;
    
    @JoinColumn(name ="TIPO_ESTABLECIMIENTO_ID")
    private TipoEstablecimiento tipoEstablecimiento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoConvencional() {
        return telefonoConvencional;
    }

    public void setTelefonoConvencional(String telefonoConvencional) {
        this.telefonoConvencional = telefonoConvencional;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getTipoSucursal() {
        return tipoSucursal;
    }

    public void setTipoSucursal(String tipoSucursal) {
        this.tipoSucursal = tipoSucursal;
    }
    
    public TipoSucursalEnum getTipoSucursalEnum() {
        return TipoSucursalEnum.getEnum(tipoSucursal);
    }

    public void setTipoSucursalEnum(TipoSucursalEnum tipoSucursalEnum) {
        this.tipoSucursal = tipoSucursalEnum.getCodigo();
    }

    public String getExtensionTelefono() {
        return extensionTelefono;
    }

    public void setExtensionTelefono(String extensionTelefono) {
        this.extensionTelefono = extensionTelefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getCodigoPersonalizado() {
        return codigoPersonalizado;
    }

    public void setCodigoPersonalizado(String codigoPersonalizado) {
        this.codigoPersonalizado = codigoPersonalizado;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public TipoEstablecimiento getTipoEstablecimiento() {
        return tipoEstablecimiento;
    }

    public void setTipoEstablecimiento(TipoEstablecimiento tipoEstablecimiento) {
        this.tipoEstablecimiento = tipoEstablecimiento;
    }

    public String getReferenciaDireccion() {
        return referenciaDireccion;
    }

    public void setReferenciaDireccion(String referenciaDireccion) {
        this.referenciaDireccion = referenciaDireccion;
    }
    
    public String getUrlGoogleMaps()
    {
        return UtilidadesWeb.construirUrlGoogleMaps(latitud, longitud);
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

    public void setEstadoEnum(GeneralEnumEstado estadoEnum) {
        this.estado = estadoEnum.getEstado();
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
        final PersonaEstablecimiento other = (PersonaEstablecimiento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    
    /**
     * ==============> DATOS ADICIONALES PARA MOSTRAR EN LAS DIALOGOS WEB <=================
     * TODO: Ver si se puede encontrar una mejor solucion porque no resulta practica esta solucion
     */
    public String getoutIdentificacion()
    {
        return this.persona.getIdentificacion();
    }
    
    public String getoutRazonSocial()
    {
        return this.persona.getRazonSocial();
    }
    
    
    
    public static PersonaEstablecimiento buildFromPersona(PersonaEstablecimiento establecimiento,String codigo,String nombreComercial,String direccion,String referenciadireccion,String extensionTelefono,String telefonoCelular,String telefonoConvencional,TipoSucursalEnum tipoEnum,Zona zona,TipoEstablecimiento tipoEstablecimiento,GeneralEnumEstado estadoEnum)
    {
        //PersonaEstablecimiento personaEstablecimiento = new PersonaEstablecimiento();
        establecimiento.setCodigoPersonalizado(codigo);
        establecimiento.setNombreComercial(nombreComercial);
        establecimiento.setDireccion(direccion);
        establecimiento.setReferenciaDireccion(referenciadireccion);
        establecimiento.setExtensionTelefono(extensionTelefono);
        //establecimiento.setPersona(persona);
        establecimiento.setTelefonoCelular(telefonoCelular);
        establecimiento.setTelefonoConvencional(telefonoConvencional);
        establecimiento.setTipoSucursalEnum(tipoEnum);
        establecimiento.setZona(zona);
        establecimiento.setTipoEstablecimiento(tipoEstablecimiento);
        establecimiento.setEstadoEnum(estadoEnum);
        return establecimiento;
    }
    
    
}
