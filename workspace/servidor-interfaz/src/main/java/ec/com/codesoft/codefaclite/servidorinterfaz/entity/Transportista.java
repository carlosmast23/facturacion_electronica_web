/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 *
 * @author CodesoftDesarrollo
 */
@Entity
@Table(name = "TRANSPORTISTA")
public class Transportista implements Serializable
{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "IDENTIFICACION")
    private String identificacion;
    @Column(name = "NOMBRES")
    private String nombres;
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Column(name = "NOMBRE_COMERCIAL")
    private String nombreComercial;
    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "PLACA_VEHICULO")
    private String placaVehiculo;
    @Column(name = "TELEFONO_CELULAR")
    private String telefonoCelular;
    @Column(name = "TELEFONO_CONVENCIONAL")
    private String telefonoConvencional;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "TIPO_IDENTIFICACION")
    private String tipoIdentificacion;
    
    @JoinColumn(name = "EMPRESA_ID")
    private Empresa empresa;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getTelefonoConvencional() {
        return telefonoConvencional;
    }

    public void setTelefonoConvencional(String telefonoConvencional) {
        this.telefonoConvencional = telefonoConvencional;
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

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }
    
    public Persona.TipoIdentificacionEnum getTipoIdentificacionEnum() {
        return Persona.TipoIdentificacionEnum.obtenerPorLetra(tipoIdentificacion);
    }
    
    public void setTipoIdentificacionEnum(Persona.TipoIdentificacionEnum enumerador) {
        this.tipoIdentificacion = enumerador.getLetra();
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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
        final Transportista other = (Transportista) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
  
}
