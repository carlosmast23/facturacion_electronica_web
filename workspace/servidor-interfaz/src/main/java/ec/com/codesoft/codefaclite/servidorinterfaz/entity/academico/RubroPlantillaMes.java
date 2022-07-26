/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
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
 * @author
 */
@Entity
@Table(name = "RUBRO_PLANTILLA_MES")
public class RubroPlantillaMes implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NUMERO_MES")
    private Integer numeroMes;
    
    @Column(name = "ANIO")
    private Integer anio;

    @JoinColumn(name = "RUBRO_PLANTILLA_ID")
    private RubroPlantilla rubroPlantilla;
    
    /**
     * Rubro para saber el nivel con rubro de nivel esta ligado para poder reacer alguna operación como por ejemplo eliminar
     */
    @JoinColumn(name = "RUBRO_NIVEL_ID")
    private RubrosNivel rubroNivel;
    
    
    public RubroPlantillaMes() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroMes() {
        return numeroMes;
    }

    public void setNumeroMes(Integer numeroMes) {
        this.numeroMes = numeroMes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public RubroPlantilla getRubroPlantilla() {
        return rubroPlantilla;
    }

    public void setRubroPlantilla(RubroPlantilla rubroPlantilla) {
        this.rubroPlantilla = rubroPlantilla;
    }

    public RubrosNivel getRubroNivel() {
        return rubroNivel;
    }

    public void setRubroNivel(RubrosNivel rubroNivel) {
        this.rubroNivel = rubroNivel;
    }
    
    
    
    
    
        
    //Metodos personalizados
    
    public MesEnum getMesEnum()
    {
        return MesEnum.obtenerPorNumero(numeroMes);
    }
/*
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final RubroPlantillaMes other = (RubroPlantillaMes) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
*/
    @Override
    public String toString() {
        return MesEnum.obtenerPorNumero(numeroMes) + "-" + anio;
    }
    
    
    
    
}
