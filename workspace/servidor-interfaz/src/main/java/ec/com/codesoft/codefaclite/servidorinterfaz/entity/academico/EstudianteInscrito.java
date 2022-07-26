/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author
 */
@Entity
@Table(name = "ESTUDIANTE_INSCRITO")
@XmlRootElement
public class EstudianteInscrito implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @Column(name = "TIPO_MATRICULA_COD")
    private String tipoMatriculaCod;
    
    @Column(name = "BECA")
    private String beca;
    
    @JoinColumn(name = "ESTUDIANTE_ID")
    @ManyToOne
    private Estudiante estudiante;
    
    @JoinColumn(name = "NIVEL_ACADEMICO_ID")
    @ManyToOne
    private NivelAcademico nivelAcademico;

    public EstudianteInscrito() {
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public NivelAcademico getNivelAcademico() {
        return nivelAcademico;
    }

    public void setNivelAcademico(NivelAcademico nivelAcademico) {
        this.nivelAcademico = nivelAcademico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getTipoMatriculaCod() {
        return tipoMatriculaCod;
    }

    public void setTipoMatriculaCod(String tipoMatriculaCod) {
        this.tipoMatriculaCod = tipoMatriculaCod;
    }

    public String getBeca() {
        return beca;
    }

    public void setBeca(String beca) {
        this.beca = beca;
    }
    
    


    @Override
    public String toString() {
        return estudiante.getApellidos()+" "+estudiante.getNombres();
    }
    
    
    //Metodos personalizados
    
    public GeneralEnumEstado getEnumEstado()
    {
        return GeneralEnumEstado.getEnum(estado);
    }
    
    public TipoMatriculaEnum getTipoMatriculaCodEnum()
    {
        return TipoMatriculaEnum.obtenerPorLetra(tipoMatriculaCod);
    }
    
    public EnumSiNo getBecaEnum()
    {
        return EnumSiNo.getEnumByLetra(beca);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final EstudianteInscrito other = (EstudianteInscrito) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    public enum TipoMatriculaEnum {
        ORDINARIA("o", "Ordinaria"),
        EXTRAORDINARIA("e", "Extraordinaria");

        private TipoMatriculaEnum(String letra, String nombre) {
            this.letra = letra;
            this.nombre = nombre;
        }

        private String letra;
        private String nombre;

        public String getLetra() {
            return letra;
        }

        public String getNombre() {
            return nombre;
        }
        
        

        @Override
        public String toString() {
            return nombre;
        }
        
        public static TipoMatriculaEnum obtenerPorLetra(String letra)
        {
            for (TipoMatriculaEnum tipoMatriculaEnum : TipoMatriculaEnum.values()) {
                if(letra.equals(tipoMatriculaEnum.getLetra()))
                {
                    return tipoMatriculaEnum;
                }
            }
            return null;
        }

    };
    
    
}
