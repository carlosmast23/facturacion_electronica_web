/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author CodesoftDesarrollo 1
 */
@Entity
@Table(name = "DETALLE_ORDEN_TRABAJO")
public class OrdenTrabajoDetalle implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "NOTAS")
    private String notas;

    @Column(name = "FECHA_ENTREGA")
    private Date fechaEntrega;

    @Column(name = "ESTADO")
    private String estado;

    @Column(name = "TIPO_ORDEN_TRABAJO")
    private String tipoOrdenTrabajo;

    @Column(name = "PRIORIDAD")
    private String prioridad;

    @Column(name = "TITULO")
    private String titulo;

    @JoinColumn(name = "ORDEN_TRABAJO_ID")
    @ManyToOne(optional = false)
    private OrdenTrabajo ordenTrabajo;

    @JoinColumn(name = "EMPLEADO_ID")
    @ManyToOne(optional = false)
    private Empleado empleado;

    /**
     * Este valor se lo crea porque el departamente es propio de a donde se
     * asigna el trabajo sin importar el empleado
     */
    @JoinColumn(name = "DEPARTAMENTO_ID")
    @ManyToOne
    private Departamento departamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public EstadoEnum getEstadoEnum() {
        return EstadoEnum.getEnum(estado);
    }

    public void setEstadoEnum(EstadoEnum estadoEnum) {
        this.estado = estadoEnum.getLetra();
    }

    public String getTipoOrdenTrabajo() {
        return tipoOrdenTrabajo;
    }

    public void setTipoOrdenTrabajo(String tipoOrdenTrabajo) {
        this.tipoOrdenTrabajo = tipoOrdenTrabajo;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public OrdenTrabajo getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public String toString() {
        return "N° " + id + " - " + descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final OrdenTrabajoDetalle other = (OrdenTrabajoDetalle) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public enum EstadoEnum {
        /**
         * Identificador para saber qe ordenes esta emitida per no usada en ningun presupuestp
         */
        RECIBIDO("R", "Recibido"),
        /**
         * Identificador para saber que la orden ya esta usada en algun presupuesto
         */
        PRESUPUESTADO("P", "Presupuestado"),
        /**
         * Estado para saber si se termino de realizar el presupueto y que ya esta facturado
         */
        TERMINADO("T", "Terminado"),
        /**
         * Identificador para saber que esta anulada el detalle de la orden de trabajo
         */
        ANULADO("A", "Anulado");

        private EstadoEnum(String letra, String nombre) {
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

        public static EstadoEnum getEnum(String letra) {

            for (EstadoEnum enumerador : EstadoEnum.values()) {
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

}
