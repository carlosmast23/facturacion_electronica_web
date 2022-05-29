/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Nacionalidad;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "ESTUDIANTE")
@XmlRootElement
public class Estudiante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ESTUDIANTE_ID")
    private Long idEstudiante;
    @Column(name = "CODAUXILIAR")
    private String codigoAuxiliar;
    @Column(name = "CEDULA")
    private String cedula;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "NOMBRES")
    private String nombres;
    @Column(name = "APELLIDOS")
    private String apellidos;
    @Column(name = "GENERO")
    private String genero;
    @Column(name = "FECHA_NACIMIENTO")
    private Date fechaNacimiento;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "CELULAR")
    private String celular;
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "ADICIONALES")
    private String datosAdicionales;
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "DISCAPACIDAD")
    private String discapacidad;
    @Column(name = "CONADIS")
    private String conadis;
    @Column(name = "TIPO_DISCAPACIDAD")
    private String tipoDiscapacidad;
    @Column(name = "PORCENTAJE_DISCAPACIDAD")
    private int porcentajeDiscapacidad;
    @Column(name = "OBS_DISCAPACIDAD")
    private String obsDiscapacidad;
    @Column(name = "ETNIA")
    private String etnia;
    @JoinColumn(name = "REPRESENTANTE1_ID")
    @ManyToOne
    private Persona representante;
    @JoinColumn(name = "REPRESENTANTE2_ID")
    @ManyToOne
    private Persona representante2;
    @JoinColumn(name = "NACIONALIDAD_ID")
    @ManyToOne
    private Nacionalidad nacionalidad;

    public Estudiante() {
    }

    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getCodigoAuxiliar() {
        return codigoAuxiliar;
    }

    public void setCodigoAuxiliar(String codigoAuxiliar) {
        this.codigoAuxiliar = codigoAuxiliar;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDatosAdicionales() {
        return datosAdicionales;
    }

    public void setDatosAdicionales(String datosAdicionales) {
        this.datosAdicionales = datosAdicionales;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Persona getRepresentante() {
        return representante;
    }

    public void setRepresentante(Persona representante) {
        this.representante = representante;
    }

    public String getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(String discapacidad) {
        this.discapacidad = discapacidad;
    }

    public String getConadis() {
        return conadis;
    }

    public void setConadis(String conadis) {
        this.conadis = conadis;
    }

    public String getTipoDiscapacidad() {
        return tipoDiscapacidad;
    }

    public void setTipoDiscapacidad(String tipoDiscapacidad) {
        this.tipoDiscapacidad = tipoDiscapacidad;
    }

    public int getPorcentajeDiscapacidad() {
        return porcentajeDiscapacidad;
    }

    public void setPorcentajeDiscapacidad(int porcentajeDiscapacidad) {
        this.porcentajeDiscapacidad = porcentajeDiscapacidad;
    }


    public String getEtnia() {
        return etnia;
    }

    public void setEtnia(String etnia) {
        this.etnia = etnia;
    }

    public String getObsDiscapacidad() {
        return obsDiscapacidad;
    }

    public void setObsDiscapacidad(String obsDiscapacidad) {
        this.obsDiscapacidad = obsDiscapacidad;
    }

    public Nacionalidad getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Nacionalidad nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Persona getRepresentante2() {
        return representante2;
    }

    public void setRepresentante2(Persona representante2) {
        this.representante2 = representante2;
    }
    
    public GeneralEnumEstado getEstadoEnum()
    {        
        return GeneralEnumEstado.getEnum(estado);
    }
    
    public void setEstadoEnum(GeneralEnumEstado estado)
    {
         setEstado(estado.getEstado());
    }
    
        ///Metodos personalizados 

    public String getNombreCompleto() {
        return apellidos+" "+nombres;
    }

    @Override
    public String toString() {
        return apellidos+" "+nombres;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.idEstudiante);
        return hash;
    }
    
    /**
     * Devuelve el primer apellido y nombre
     * @return 
     */
    public String getNombreSimple()
    {
        return apellidos.split(" ")[0]+" "+nombres.split(" ")[0];
    }
    
    public String obtenerTodosTelefonos()
    {
        return UtilidadesLista.castListToString(Arrays.asList(telefono,celular),"/");
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
        final Estudiante other = (Estudiante) obj;
        if (!Objects.equals(this.idEstudiante, other.idEstudiante)) {
            return false;
        }
        return true;
    }

}
