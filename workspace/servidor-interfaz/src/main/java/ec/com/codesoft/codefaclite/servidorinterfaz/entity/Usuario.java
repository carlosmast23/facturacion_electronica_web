/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaPermiso;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable{
    
    public static final String SUPER_USUARIO="root";
    public static final String CONSUMIDOR_FINAL_NOMBRE="CONSUMIDOR FINAL";
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    //@Id
    @Column (name = "NICK")
    private String nick;
    
    @Column (name = "CLAVE")
    private String clave;
    
    @Column (name = "ESTADO")
    private String estado;
    
    //PARAMETRO QUE ME PERMITE FILTRAR FACTURAS DE OTROS USUARIOS
    @Column(name = "FILTRAR_FACTURA")
    private String filtrarFactura;
    
    //PARAMETROS_COMPROBANTES_ELECTRONICOS
    @Column (name = "PARAMETROS_COMPROBANTES_ELECTRONICOS")
    private String parametrosComprobatesElectronicos;
    
    @JoinColumn(name = "EMPLEADO_ID")
    @ManyToOne 
    private Empleado empleado;
    
    @JoinColumn(name = "EMPRESA_ID")
    private Empresa empresa;
    
    /**
     * Variable que me permite almacenar de forma temporal para ver si las claves estan duplicadas
     */
    @Transient
    private String repetirClave;
    
    /**
     * Variable que me sirve solo para saber si el usuario ingresado es root
     */
    @Transient
    public boolean isRoot;
    
    /**
     * Variable que me permite saber si el usuario solo es para configurar la primera vez
     */
    //@Transient
    //public boolean isConfig;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario",fetch = FetchType.EAGER)
    private List<PerfilUsuario> perfilesUsuario;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario",fetch = FetchType.EAGER)
    private List<PuntoEmisionUsuario> puntosEmisionUsuario;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.EAGER)
    private List<CajaPermiso> cajasPermisoUsuario;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.EAGER)
    private List<CajaSession> cajasSessionUsuario;
    
    public Usuario(String nick, String clave) {
        this.nick = nick;
        this.clave = clave;
        this.isRoot=false;
        
    }

    public Usuario() {
        this.isRoot=false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public GeneralEnumEstado getEstadoEnum() 
    {
        return GeneralEnumEstado.getEnum(estado);
    }

    public void setEstadoEnum(GeneralEnumEstado estadoEnum) {
        this.estado = estadoEnum.getEstado();
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getParametrosComprobatesElectronicos() {
        return parametrosComprobatesElectronicos;
    }

    public void setParametrosComprobatesElectronicos(String parametrosComprobatesElectronicos) {
        this.parametrosComprobatesElectronicos = parametrosComprobatesElectronicos;
    }
    
    
    

    public List<PerfilUsuario> getPerfilesUsuario() {
        return perfilesUsuario;
    }

    public void setPerfilesUsuario(List<PerfilUsuario> perfilesUsuario) {
        this.perfilesUsuario = perfilesUsuario;
    }

    public List<PuntoEmisionUsuario> getPuntosEmisionUsuario() {
        return puntosEmisionUsuario;
    }

    public void setPuntosEmisionUsuario(List<PuntoEmisionUsuario> puntosEmisionUsuario) {
        this.puntosEmisionUsuario = puntosEmisionUsuario;
    }

    public String getFiltrarFactura() {
        return filtrarFactura;
    }

    public void setFiltrarFactura(String filtrarFactura) {
        this.filtrarFactura = filtrarFactura;
    }
    
    public EnumSiNo getFiltrarFacturaEnum(){
        return EnumSiNo.getEnumByLetra(this.filtrarFactura);
    }
    
    public void setFiltrarFacturaEnum(EnumSiNo enumSiNo){
        this.filtrarFactura = enumSiNo.getLetra();
    }

    public List<CajaPermiso> getCajasPermisoUsuario() {
        return cajasPermisoUsuario;
    }

    public void setCajasPermisoUsuario(List<CajaPermiso> cajasPermisoUsuario) {
        this.cajasPermisoUsuario = cajasPermisoUsuario;
    }

    public List<CajaSession> getCajasSessionUsuario() {
        return cajasSessionUsuario;
    }

    public void setCajasSessionUsuario(List<CajaSession> cajasSessionUsuario) {
        this.cajasSessionUsuario = cajasSessionUsuario;
    }

    public String getRepetirClave() {
        return repetirClave;
    }

    public void setRepetirClave(String repetirClave) {
        this.repetirClave = repetirClave;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.nick);
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
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.nick, other.nick)) {
            return false;
        }
        return true;
    }
    
    
    

    @Override
    public String toString() {
        return "" + nick;
    }

    /**
     * Metodo que permite agregar un PerfilUsuario a la lista
     * @param perfilUsuario 
     */
    
    public void addPerfilUsuario(PerfilUsuario perfilUsuario)
    {
        if(this.perfilesUsuario==null)
        {
            this.perfilesUsuario=new ArrayList<PerfilUsuario>();
        }
        perfilUsuario.setUsuario(this);
        
        this.perfilesUsuario.add(perfilUsuario);
        
    }
    
    public void addPuntoEmisionUsuario(PuntoEmisionUsuario puntoEmision)
    {
        if(this.puntosEmisionUsuario==null)
        {
            this.puntosEmisionUsuario=new ArrayList<PuntoEmisionUsuario>();
        }
        puntoEmision.setUsuario(this);
        
        this.puntosEmisionUsuario.add(puntoEmision);
        
    }
    
    public void addCajaPermisoUsuario(CajaPermiso cajaPermiso){
        if(this.cajasPermisoUsuario == null)
        {
            this.cajasPermisoUsuario = new ArrayList<>();
        }
        if(!NoRepetirCajaPermisoUsuario(cajaPermiso))
        {
            cajaPermiso.setUsuario(this);
            this.cajasPermisoUsuario.add(cajaPermiso);
        }
    }
    
    public void addCajaSessionUsuario(CajaSession cajaSession)
    {
        if(this.cajasSessionUsuario == null)
        {
            this.cajasSessionUsuario = new ArrayList<>();
        }
        cajaSession.setUsuario(this);
        this.cajasSessionUsuario.add(cajaSession);
    }
    
    public boolean NoRepetirCajaPermisoUsuario(CajaPermiso cajaPermiso){
        for (CajaPermiso cp : this.cajasPermisoUsuario) {
            if(cp.getCaja().equals(cajaPermiso.getCaja()) && cp.getCaja().getEstadoEnum().equals(CajaEnum.ELIMINADO)) 
                return true;
        }
        return false;
    }
       
    public String formatoPuntoEmisionActivos()
    {
        List<String> puntosActivos=new  ArrayList<String>();
        if(this.puntosEmisionUsuario!=null)
        {
            for (PuntoEmisionUsuario puntoTmp : this.puntosEmisionUsuario) {
                if(puntoTmp.getEstadoEnum().equals(puntoTmp.getEstadoEnum().ACTIVO))
                {
                    puntosActivos.add(puntoTmp.getPuntoEmision().getPuntoEmision().toString());
                }
            }
            return UtilidadesLista.castListToString(puntosActivos,",");
            
        }
        return "";
    }
    
    public String imprimirPuntoEmision()
    {
        String puntoEmisionTxt="";
        if(puntosEmisionUsuario!=null)
        {
            for (PuntoEmisionUsuario puntoEmisionUsuario : this.puntosEmisionUsuario) {
                if(!puntoEmisionUsuario.getEstadoEnum().equals(GeneralEnumEstado.ELIMINADO))
                {
                    puntoEmisionTxt+=puntoEmisionUsuario.getPuntoEmision().puntoEmisionFormatoTexto()+"  ";
                }
            }
        }
        return puntoEmisionTxt;
    }    
    
    /**
     * TODO: Este metodo solo se debe usar si la clave aun no esta encriptada
     * @return 
     */
    public Boolean verificarClavesIguales()
    {        
        if(clave!=null && repetirClave!=null)
        {
            if(clave.equals(repetirClave))
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * ========================================================================
     *                  METODOS ADICIONALES
     * ========================================================================
     */
    
    public List<CajaPermiso> buscarPermisosCajasActivosService()
    {
        try {
            return ServiceFactory.getFactory().getCajaPermisoServiceIf().buscarPermisosCajasActivos(this);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<CajaPermiso>();
    }
}
