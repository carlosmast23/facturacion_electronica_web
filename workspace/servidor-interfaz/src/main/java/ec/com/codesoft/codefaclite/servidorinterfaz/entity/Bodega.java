/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author CodesoftDesarrollo
 */
@Entity
@Table(name = "BODEGA")
@XmlRootElement
public class Bodega implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "BODEGA_ID")
    private Long idBodega;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ENCARGADO")
    private String encargado;
    @Column(name = "IMAGEN_PATH")
    private String imagenPath;
    @Column(name = "ESTADO")
    private String estado;
    
    @Column(name = "TIPO_BODEGA")
    private String tipoBodega;
    
    @JoinColumn(name = "SUCURSAL_ID")
    private Sucursal sucursal;
    
    @JoinColumn(name = "EMPRESA_ID")
    private Empresa empresa;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bodegaPermiso", fetch = FetchType.EAGER)
    private List<BodegaPermisoTransferencia> bodegasPermisoTransfereciaList;

    public Long getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(Long idBodega) {
        this.idBodega = idBodega;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getImagenPath() {
        return imagenPath;
    }

    public void setImagenPath(String imagenPath) {
        this.imagenPath = imagenPath;
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

    public String getTipoBodega() {
        return tipoBodega;
    }

    public void setTipoBodega(String tipoBodega) {
        this.tipoBodega = tipoBodega;
    }
    
    public TipoBodegaEnum getTipoBodegaEnum() {
        return TipoBodegaEnum.getByLetra(tipoBodega);
    }

    public void setTipoBodegaEnum(TipoBodegaEnum tipoBodegaEnum) {
        this.tipoBodega = tipoBodegaEnum.getLetra();
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<BodegaPermisoTransferencia> getBodegasPermisoTransfereciaList() {
        return bodegasPermisoTransfereciaList;
    }

    public void setBodegasPermisoTransfereciaList(List<BodegaPermisoTransferencia> bodegasPermisoTransfereciaList) {
        this.bodegasPermisoTransfereciaList = bodegasPermisoTransfereciaList;
    }
    
    
    public void agregarPermisoTransferenciaBodega(BodegaPermisoTransferencia bodegaPermisoTransferencia)
    {
        if(bodegasPermisoTransfereciaList==null)
        {
            this.bodegasPermisoTransfereciaList=new ArrayList<BodegaPermisoTransferencia>();
        }
               
        bodegaPermisoTransferencia.setBodegaPrincipal(this);        
        this.bodegasPermisoTransfereciaList.add(bodegaPermisoTransferencia);
        
    }
    
    
    public BodegaPermisoTransferencia buscarBodegaPermiso(Bodega bodegaPermiso)
    {
        if(bodegasPermisoTransfereciaList!=null)
        {
            for (BodegaPermisoTransferencia bodegaPermisoTransferencia : bodegasPermisoTransfereciaList) {
                if(bodegaPermisoTransferencia.getBodegaPermiso().equals(bodegaPermiso))
                {
                    return bodegaPermisoTransferencia;
                }
            }
        }
        return null;
    }
    
    /**
     * Metodo util en especial para editar en la vista
     * @param rutaDetalle 
     */
    public void setObject(Bodega bodega)
    {
        this.bodegasPermisoTransfereciaList=bodega.getBodegasPermisoTransfereciaList();
        this.descripcion=bodega.getDescripcion();
        this.empresa=bodega.getEmpresa();
        this.encargado=bodega.getEncargado();
        this.estado=bodega.getEstado();
        this.imagenPath=bodega.getImagenPath();
        this.nombre=bodega.getNombre();
        this.sucursal=bodega.getSucursal();
        this.tipoBodega=bodega.getTipoBodega();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.idBodega);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bodega other = (Bodega) obj;
        if (!Objects.equals(this.idBodega, other.idBodega)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
    
    public enum TipoBodegaEnum
    {
        VENTA("v","Venta"),
        ALMACEN("a","Almacen"),
        PRODUCCION("p","Producción");

        private TipoBodegaEnum(String letra, String nombre) {
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
        
        public static TipoBodegaEnum getByLetra(String letra)
        {
            for (TipoBodegaEnum tipoSucursalEnum : TipoBodegaEnum.values()) {
                if(tipoSucursalEnum.getLetra().equals(letra))
                {
                    return tipoSucursalEnum;
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
