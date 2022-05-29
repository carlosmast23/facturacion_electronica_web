/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author CodesoftDesarrollo 1
 */
@Entity
@Table(name = "EMPLEADO")
public class Empleado implements Serializable
{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "CODIGO")
    private String codigo;
    
    @Column(name = "NOMBRES")
    private String nombres;
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Column(name = "CEDULA")
    private String identificacion;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "TELEFONO_CONVENCIONAL")
    private String telefonoConvencional;
    @Column(name = "TELEFONO_CELULAR")
    private String telefonoCelular;
    @Column(name = "CORREO_ELECTRONICO")
    private String correoElectronico;
    @Column(name = "GENERO")
    private String sexo;
    @Column(name = "CARGO")
    private String cargo;
    @Column(name = "ESTADO")
    private String estado;
    
//    @JoinColumn(name = "PERSONA_ID")
//    @ManyToOne    
//    private Persona cliente;
    
    @JoinColumn(name = "DEPARTAMENTO_ID")
    @ManyToOne    
    private Departamento departamento;
    
    @JoinColumn(name = "NACIONALIDAD_ID")
    @ManyToOne
    private Nacionalidad nacionalidad;
    
    //TODO: Falta aumentar la empresa
            
    public Empleado() {
        
    }
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

//    public Persona getCliente() {
//        return cliente;
//    }
//
//    public void setCliente(Persona cliente) {
//        this.cliente = cliente;
//    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
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

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Nacionalidad getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Nacionalidad nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
    
    public String getNombresCompletos()
    {
        return nombres+" "+apellidos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    
     

    @Override
    public String toString() {
        String nombreCompleto = this.apellidos + " " +this.nombres;
        return ""+((departamento!=null)?departamento.getNombre():"sin departamento")+ " - " + nombreCompleto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Empleado other = (Empleado) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    
}
