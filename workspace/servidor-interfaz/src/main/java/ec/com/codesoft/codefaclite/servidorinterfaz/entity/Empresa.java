/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author
 */
@Entity
@Table(name = "EMPRESA")
public class Empresa implements Serializable {

    public static final String NO_LLEVA_CONTABILIDAD = "NO";
    public static final String SI_LLEVA_CONTABILIDAD = "SI";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    public Long id;
    //@Column(name = "TELEFONOS")
    //private String telefonos;
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Column(name = "NOMBRE_LEGAL")
    private String nombreLegal;
    //@Column(name = "DIRECCION")
    //private String direccion;
    @Column(name = "IDENTIFICACION")
    private String identificacion;
    @Column(name = "OBLIGADO_LLEVAR_CONTABILIDAD")
    private String obligadoLlevarContabilidad;
    @Column(name = "CONTRIBUYENTE_ESPECIAL")
    private String contribuyenteEspecial;
    @Column(name = "LOGO_IMAGEN_PATH")
    private String imagenLogoPath;
    //@Column(name = "CELULAR")
    //private String celular;
    @Column(name = "FACEBOOK")
    private String facebook;
    
    @Column(name = "INSTAGRAM")
    private String instagram;
    
    @Column(name = "TEXTO1")
    private String adicional;
    
    @Column(name = "CODIGO")
    private String codigo;
    
    @Column(name = "ESTADO")
    private String estado;
    
    @Column(name = "ORDEN")
    private Integer orden;
    
    @Column(name = "CONTRIBUYENTE_REGIMEN_MICROEMPRESAS")
    private String contribuyenteRegimenMicroempresas;

    @Column(name = "AGENTE_RETENCION_RESOLUCION")
    private String agenteRetencionResolucion;
    
    @Column(name = "RIMPE_NEGOCIOS_POPULARES")
    private String rimpeNegociosPopulares;
    
    @Column(name = "RIMPE_EMPRENDEDORES")
    private String rimpeEmprendedores;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresa",fetch = FetchType.EAGER)
    private List<Sucursal> sucursales;

    public Empresa() {
    }

    public Empresa(String identificacion) {
        this.identificacion = identificacion;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //public String getTelefonos() {
    //    return telefonos;
    //}

    //public void setTelefonos(String telefonos) {
    //    this.telefonos = telefonos;
    //}

    /*
    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }*/

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreLegal() {
        return nombreLegal;
    }

    public void setNombreLegal(String nombreLegal) {
        this.nombreLegal = nombreLegal;
    }

    public EnumSiNo getObligadoLlevarContabilidadEnum() {
        return EnumSiNo.getEnumByNombre(obligadoLlevarContabilidad);
    }

    public String getObligadoLlevarContabilidad() {
        return obligadoLlevarContabilidad;
    }

    public void setObligadoLlevarContabilidad(String obligadoLlevarContabilidad) {
        this.obligadoLlevarContabilidad = obligadoLlevarContabilidad;
    }
    
    public Boolean getObligadoLlevarContabilidadBool() {
        if(obligadoLlevarContabilidad!=null)
        {
            EnumSiNo enumSiNo=EnumSiNo.getEnumByNombre(obligadoLlevarContabilidad);
            if(enumSiNo!=null)
            {
                return enumSiNo.getBool();
            }
        }
            
        return false;
    }

    public void setObligadoLlevarContabilidadBool(Boolean obligadoLlevarContabilidadBool) {
        this.obligadoLlevarContabilidad = EnumSiNo.getEnumByBoolean(obligadoLlevarContabilidadBool).getNombre().toUpperCase();
    }


    public String getContribuyenteEspecial() {
        return contribuyenteEspecial;
    }

    public void setContribuyenteEspecial(String contribuyenteEspecial) {
        this.contribuyenteEspecial = contribuyenteEspecial;
    }

    public String getImagenLogoPath() {
        return imagenLogoPath;
    }

    public void setImagenLogoPath(String imagenLogoPath) {
        this.imagenLogoPath = imagenLogoPath;
    }

    /*
    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }*/

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getAdicional() {
        return adicional;
    }

    public void setAdicional(String adicional) {
        this.adicional = adicional;
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<Sucursal> sucursales) {
        this.sucursales = sucursales;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
    
    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }
    
    

    public String getContribuyenteRegimenMicroempresas() {
        return contribuyenteRegimenMicroempresas;
    }

    public void setContribuyenteRegimenMicroempresas(String contribuyenteRegimenMicroempresas) {
        this.contribuyenteRegimenMicroempresas = contribuyenteRegimenMicroempresas;
    }
    
    public Boolean getContribuyenteRegimenMicroempresasBool() {
        if(contribuyenteRegimenMicroempresas!=null)
        {
            EnumSiNo enumSiNo=EnumSiNo.getEnumByLetra(contribuyenteRegimenMicroempresas);
            if(enumSiNo!=null)
            {
                return enumSiNo.getBool();
            }
        }
            
        return false;
    }

    public void setContribuyenteRegimenMicroempresasBool(Boolean contribuyenteRegimenMicroempresasBool) {
        this.contribuyenteRegimenMicroempresas = EnumSiNo.getEnumByBoolean(contribuyenteRegimenMicroempresasBool).getLetra();
    }
    
    public EnumSiNo getContribuyenteRegimenMicroempresasEnum() {
        return EnumSiNo.getEnumByLetra(contribuyenteRegimenMicroempresas);
    }

    public void setContribuyenteRegimenMicroempresasEnum(EnumSiNo contribuyenteRegimenMicroempresasEnum) {
        this.contribuyenteRegimenMicroempresas = contribuyenteRegimenMicroempresasEnum.getLetra();
    }

    public String getRimpeNegociosPopulares() {
        return rimpeNegociosPopulares;
    }

    public void setRimpeNegociosPopulares(String rimpeNegociosPopulares) {
        this.rimpeNegociosPopulares = rimpeNegociosPopulares;
    }

    public String getRimpeEmprendedores() {
        return rimpeEmprendedores;
    }

    public void setRimpeEmprendedores(String rimpeEmprendedores) {
        this.rimpeEmprendedores = rimpeEmprendedores;
    }
    

    public EnumSiNo getRimpeNegociosPopularesEnum() {
        return EnumSiNo.getEnumByLetra(rimpeNegociosPopulares);
    }

    public void setRimpeNegociosPopularesEnum(EnumSiNo rimpeNegociosPopularesEnum) {
        this.rimpeNegociosPopulares = rimpeNegociosPopularesEnum.getLetra();
    }

    public EnumSiNo getRimpeEmprendedoresEnum() {
        return EnumSiNo.getEnumByLetra(rimpeEmprendedores);
    }

    public void setRimpeEmprendedoresEnum(EnumSiNo rimpeEmprendedoresEnum) {
        this.rimpeEmprendedores = rimpeEmprendedoresEnum.getLetra();
    }
    
    
    

    public String getAgenteRetencionResolucion() {
        return agenteRetencionResolucion;
    }

    public void setAgenteRetencionResolucion(String agenteRetencionResolucion) {
        this.agenteRetencionResolucion = agenteRetencionResolucion;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }
    
    
    /**
     * Metodo que permite obtener por defecto el nombre comercial pero si no tiene devuelve la razon social
     * @return 
     */
    
    public String obtenerNombreEmpresa()
    {
        String nombreEmpresa="";
        if(nombreLegal!=null && !nombreLegal.trim().isEmpty())
        {
            nombreEmpresa=nombreLegal;
        }
        else if(razonSocial!=null && !razonSocial.trim().isEmpty() )
        {
            nombreEmpresa=razonSocial;
        }
        
        return nombreEmpresa;
    }
    

    @Override
    public String toString() {
        String nameString="";
        if(nombreLegal!=null && !nombreLegal.isEmpty())
        {
            nameString=nombreLegal;
        }
        else if(razonSocial!=null && !razonSocial.isEmpty())
        {
            nameString=razonSocial;
        }
        return nameString;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Empresa other = (Empresa) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

   
    
    
    
    
}
