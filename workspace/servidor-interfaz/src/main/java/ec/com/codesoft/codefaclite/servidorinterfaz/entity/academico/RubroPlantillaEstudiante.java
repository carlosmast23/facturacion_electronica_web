/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author
 */
@Entity
@Table(name = "RUBRO_PLANTILLA_ESTUDIANTE")
public class RubroPlantillaEstudiante implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @JoinColumn(name = "RUBRO_PLANTILLA_ID")
    private RubroPlantilla rubroPlantilla;
    
    
    @JoinColumn(name = "ESTUDIANTE_INSCRITO_ID")
    @OneToOne(cascade = CascadeType.MERGE) //Con esta relacion se actualiza la referencia del padre cuando edito el estudiante Inscrito
    private EstudianteInscrito estudianteInscrito;
    
    @Column(name = "VALOR_PLANTILLA")
    private BigDecimal valorPlantilla;
    
    @Column(name = "DESCUENTO_PLANTILLA")
    private BigDecimal descuentoPlantilla;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RubroPlantilla getRubroPlantilla() {
        return rubroPlantilla;
    }

    public void setRubroPlantilla(RubroPlantilla rubroPlantilla) {
        this.rubroPlantilla = rubroPlantilla;
    }

    public EstudianteInscrito getEstudianteInscrito() {
        return estudianteInscrito;
    }

    public void setEstudianteInscrito(EstudianteInscrito estudianteInscrito) {
        this.estudianteInscrito = estudianteInscrito;
    }

    public BigDecimal getValorPlantilla() {
        return valorPlantilla;
    }

    public void setValorPlantilla(BigDecimal valorPlantilla) {
        this.valorPlantilla = valorPlantilla;
    }

    public BigDecimal getDescuentoPlantilla() {
        return descuentoPlantilla;
    }

    public void setDescuentoPlantilla(BigDecimal descuentoPlantilla) {
        this.descuentoPlantilla = descuentoPlantilla;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
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
        final RubroPlantillaEstudiante other = (RubroPlantillaEstudiante) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    /**
     * ========================================================================
     *                  METODOS ADICIONALES
     * =======================================================================
     */
    public static Comparator<RubroPlantillaEstudiante> comparadorOrdenarAlfabeticamente=new Comparator<RubroPlantillaEstudiante>() {
        @Override
        public int compare(RubroPlantillaEstudiante o1, RubroPlantillaEstudiante o2) {
            int comparacion=o1.getEstudianteInscrito().getEstudiante().getApellidos().compareTo(o2.getEstudianteInscrito().getEstudiante().getApellidos());
            
            //Si la comparacion es igual a 0 significa que los apellidos son iguales y me toca comparar por otro argumento
            if(comparacion==0)
            {
                comparacion=o1.getEstudianteInscrito().getEstudiante().getNombres().compareTo(o2.getEstudianteInscrito().getEstudiante().getNombres());
            }
            
            return comparacion;
        }
    };
    
    
    
}
