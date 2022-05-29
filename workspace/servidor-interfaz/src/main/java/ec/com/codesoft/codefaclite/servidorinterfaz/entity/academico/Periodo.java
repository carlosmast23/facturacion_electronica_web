/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "PERIODO")
@XmlRootElement
public class Periodo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PERIODO_ID")
    private Long idPeriodo;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "FECHA_INICIO")
    private Date fechaInicio;
    @Column(name = "FECHA_FIN")
    private Date fechaFin;
    @Column(name = "OBSERVACION")
    private String observaciones;
    @Column(name = "ESTADO")
    private String estado;

    public Long getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Long idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    //Metodos Personalizados
    public GeneralEnumEstado getEStadoEnum()
    {
        return GeneralEnumEstado.getEnum(estado);
    }
    
    /**
     * Todo: Ver si este metodo se une o se puede generealizar porque en RubroPlantilla existe uno que realiza exactamente lo mismo
     * @return 
     */
    public List<RubroPlantillaMes> obtenerTodosMesesGenerar()
    {
        List<RubroPlantillaMes> rubrosMes=new ArrayList<RubroPlantillaMes>();
        
        //Generar todos los meses segun el periodo
        Date fechaInicio=getFechaInicio();
        Date fechaFinal=getFechaFin();
        
        Integer anioInicial=UtilidadesFecha.obtenerAnio(fechaInicio);
        Integer anioFinal=UtilidadesFecha.obtenerAnio(fechaFinal);
        
        Integer mesInicial=UtilidadesFecha.obtenerMes(fechaInicio);
        Integer mesFinal=UtilidadesFecha.obtenerMes(fechaFinal);
        
        Integer mesInicialContador=mesInicial;
        
        for (int anio = anioInicial; anio <= anioFinal; anio++) {
            for (int mes =mesInicialContador;mes<=12;mes++) 
            {
                RubroPlantillaMes rpm=new RubroPlantillaMes();
                rpm.setAnio(anio);
                rpm.setNumeroMes(mes);
                
                rubrosMes.add(rpm);
                
                //Comparar si ya termino solo cuando este en el anio final
                if(anio==anioFinal)
                {
                    if(mesFinal==mes)
                    {
                        break;
                    }
                }
            }
            mesInicialContador=1;// Seteo en 0 porque despues de la primera vez los meses empiezan desde enero
        }
        
        return rubrosMes;
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.idPeriodo);
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
        final Periodo other = (Periodo) obj;
        if (!Objects.equals(this.idPeriodo, other.idPeriodo)) {
            return false;
        }
        return true;
    }
    
    
}
