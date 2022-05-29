/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DiaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author CARLOS_CODESOFT
 */
@Entity
@Table(name = "RUTA")
public class Ruta implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private Long  id;
    
    @Column (name = "CODIGO")
    private String codigo;
    
    @Column (name = "NOMBRE")
    private String nombre;
    
    @Column (name = "DIA_VISITA")
    private Integer diaVisita;
    
    @Column (name = "ESTADO")
    private String estado;
    
    @JoinColumn(name = "EMPLEADO_ID")
    private Empleado vendedor;
    
    @JoinColumn(name = "EMPRESA_ID")
    private Empresa empresa;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ruta",fetch = FetchType.EAGER)
    private List<RutaDetalle> detalles;

    public Ruta() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getDiaVisita() {
        return diaVisita;
    }

    public void setDiaVisita(Integer diaVisita) {
        this.diaVisita = diaVisita;
    }
    
    public DiaEnum getDiaVisitaEnum() {
        return DiaEnum.buscarPorNumero(diaVisita);
    }

    public void setDiaVisitaEnum(DiaEnum diaVisitaEnum) {
        this.diaVisita = diaVisitaEnum.getNumero();
    }

    public Empleado getVendedor() {
        return vendedor;
    }

    public void setVendedor(Empleado vendedor) {
        this.vendedor = vendedor;
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
        if(estadoEnum==null)
        {
            this.estado=null;
        }
        else
        {
            this.estado = estadoEnum.getEstado();
        }
    }

    public List<RutaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<RutaDetalle> detalles) {
        this.detalles = detalles;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final Ruta other = (Ruta) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    
    ///////////////////////////////////////////////////////////////////////////
    ///                   METODOS PERSONALIZADOS
    //////////////////////////////////////////////////////////////////////////
    public List<RutaDetalle> getDetallesOrdenadoPorOrden()
    {
        List<RutaDetalle> resultado=new ArrayList<RutaDetalle>(getDetallesActivos());
        
        Collections.sort(resultado, new Comparator<RutaDetalle>() {
            @Override
            public int compare(RutaDetalle o1, RutaDetalle o2) {
                return o1.getOrden().compareTo(o2.getOrden());
            }
        });
        return resultado;
    }
    
    public List<RutaDetalle> getDetallesActivos()
    {
        List<RutaDetalle> resultado=new ArrayList<RutaDetalle>();
        
        if(detalles!=null)
        {
            for (RutaDetalle detalle : detalles) 
            {
                if(detalle.getEstadoEnum().equals(GeneralEnumEstado.ACTIVO))
                {
                    resultado.add(detalle);
                }
            }
        }
        return resultado;
    }
    
    public void addDetalle(RutaDetalle rutaDetalle)
    {
        if(this.detalles==null)
        {
            this.detalles=new ArrayList<RutaDetalle>();
        }
        
        rutaDetalle.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        rutaDetalle.setRuta(this);
        this.detalles.add(rutaDetalle);
    }
    
    public Boolean verificarDatoDuplicado(RutaDetalle rutaDetalle)
    {
        if(this.detalles!=null)
        {
            for (RutaDetalle rutaDetalleTmp : this.detalles) 
            {
                GeneralEnumEstado estadoEnum=rutaDetalleTmp.getEstadoEnum();
                if(rutaDetalleTmp.getEstablecimiento().equals(rutaDetalle.getEstablecimiento()) 
                        && estadoEnum.equals(GeneralEnumEstado.ACTIVO))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Metodo de verificacion cuando voy a editar por que no tiene que tener en cuento el objeto original
     * @param rutaDetalle
     * @param RutaDetalleExcepcion
     * @return 
     */
    public Boolean verificarDatoDuplicado(RutaDetalle rutaDetalle,RutaDetalle RutaDetalleExcepcion)
    {
        if(this.detalles!=null)
        {
            for (RutaDetalle rutaDetalleTmp : this.detalles) 
            {
                if(RutaDetalleExcepcion!=rutaDetalleTmp)
                {
                    GeneralEnumEstado estadoEnum=rutaDetalleTmp.getEstadoEnum();
                    if(rutaDetalleTmp.getEstablecimiento().equals(rutaDetalle.getEstablecimiento()) 
                            && estadoEnum.equals(GeneralEnumEstado.ACTIVO))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static RutaDetalle getInstanceActivo()
    {
        RutaDetalle rutaDetalle=new RutaDetalle();
        rutaDetalle.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        rutaDetalle.setOrden(0);
        return rutaDetalle;
    }
    
}
