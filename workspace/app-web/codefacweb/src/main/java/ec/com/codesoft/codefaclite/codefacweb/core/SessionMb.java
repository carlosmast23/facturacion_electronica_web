/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.core;

import ec.com.codesoft.codefaclite.codefacweb.mb.facturacion.BarraProgreso;
import ec.com.codesoft.codefaclite.codefacweb.mb.sistema.UtilidadesWeb;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CategoriaMenuEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.MenuCodefacRespuesta;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author
 */
@ManagedBean
@SessionScoped
public class SessionMb implements Serializable{
    
    //private Integer porcentaje=0;
    private Boolean actualizarMonitor=false;
    /**
     * Entidad que se va a encargar de tener fisicamente los datos de la session
     */
    private SessionCodefac session;
    
    public static final String LOGOUT_PAGE_REDIRECT =
      "/login.xhtml?faces-redirect=true";
    
    public List<BarraProgreso> barraProgresoList; 
    
    /**
     * Variable que me permite almacenar los permisos del perfil para acceso a las diferentes pantallas
     */
    public MenuCodefacRespuesta menuCodefacRespuesta;
    
    /**
     * Modelo del menu para la vista construido segun 
     */
    private Map<ModuloCodefacEnum,MenuModel> menuModelMap;
    
    private Boolean variableFalsa=true;

    @PostConstruct
    public void init() {
        this.barraProgresoList=new ArrayList<BarraProgreso>();
        //this.barraProgresoList.add(new BarraProgreso(90));
        //this.barraProgresoList.add(new BarraProgreso(70));
        //ejemplo = "hola a todos";
    }

    public SessionCodefac getSession() {
        return session;
    }

    public void setSession(SessionCodefac session) {
        this.session = session;
        this.actualizarMonitor=false;
        consultarVentanasUsuario();
        construirMenuVista();
    }

    /**
     * Variable para saber si el usuario esta logeado en el sistema
     * @return 
     */
    public boolean isLoggedIn() {
        return session != null;
    }
    
    /**
     * Terminar la session del usuario
     * @return 
     */
    public String logout()
    {
        System.out.println("salir de la session");
        session=null;
        return LOGOUT_PAGE_REDIRECT;
    }
      
    /*public Integer getProgreso()
    {
        return porcentaje;
    }
    
    public void setProgreso(Integer porcentaje)
    {
        this.porcentaje=porcentaje;
    }*/
    
    public void ejemploContador()
    {
        //this.actualizarMonitor=true;
        System.out.println("Actualizando monitor =>" +actualizarMonitor);
    }
    
    public void ejemploActivar()
    {
        
        //this.actualizarMonitor=!this.actualizarMonitor;
        System.out.println("Activando o apagando monitor");
    }
    
    private void consultarVentanasUsuario()
    {
        try {
            menuCodefacRespuesta=ServiceFactory.getFactory().getPerfilServicioIf().construirMenuPermisosUsuario(session);
            
            for (ModuloCodefacEnum modulosDisponible : menuCodefacRespuesta.getModulosDisponibles()) {
                System.out.println("---->"+modulosDisponible.getNombre());
            }
        } catch (RemoteException ex) {
            Logger.getLogger(SessionMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(SessionMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo que me permite construir el modelo de los menus de las vistas
     */
    private void construirMenuVista()
    {
        menuModelMap=new HashMap<ModuloCodefacEnum, MenuModel>();
                
        for (ModuloCodefacEnum moduloDisponible : menuCodefacRespuesta.getModulosDisponibles()) {
            MenuModel modelMenu = new DefaultMenuModel();
            List<CategoriaMenuEnum> categorias=menuCodefacRespuesta.getCategoriasActivasMap().get(moduloDisponible);
            for (CategoriaMenuEnum categoria : categorias) {
                DefaultSubMenu submenu = construirSubMenu(categoria.getNombre());
                for (VentanaEnum ventanaEnum : menuCodefacRespuesta.buscarVentanasPorCategoriaYModulo(moduloDisponible, categoria)) 
                {
                    /**
                     * Solo construir el menu de las pantallas construidas para la parte web
                     */
                    if(ventanaEnum.getUrlModuloWeb()!=null && !ventanaEnum.getUrlModuloWeb().isEmpty())
                    {
                        DefaultMenuItem itemMenu=construirItemMenu(ventanaEnum.getNombre(),ventanaEnum.getUrlModuloWeb(),"");
                        submenu.getElements().add(itemMenu);
                    }
                }
                modelMenu.getElements().add(submenu);
            }
            menuModelMap.put(moduloDisponible,modelMenu);
        }
        
    }
    
    private DefaultMenuItem construirItemMenu(String nombre,String url,String icono)
    {
        DefaultMenuItem item = new DefaultMenuItem();
        //DefaultMenuItem item = DefaultMenuItem.builder();
        item.setValue(nombre);
        if(url!=null && !url.isEmpty())
        {
            item.setCommand(url);
        }
        item.setIcon("pi pi-home"); //en esta parte va el icono
        item.setAjax(false);
        return item;
    }
    
    private DefaultSubMenu construirSubMenu(String nombre) {
        DefaultSubMenu firstSubmenu = new DefaultSubMenu();
        firstSubmenu.setLabel(nombre);
        firstSubmenu.setExpanded(false);
        return firstSubmenu;
    }
    
    public void limpiarBarrasProgresoFactura()
    {
        this.barraProgresoList.clear();
        System.out.println("Limpiado barras de progreso");  
    }

    public Boolean getActualizarMonitor() {
        return actualizarMonitor;
    }

    public void setActualizarMonitor(Boolean actualizarMonitor) {
        this.actualizarMonitor = actualizarMonitor;
        //this.actualizarMonitor=false;
    }

    public List<BarraProgreso> getBarraProgresoList() {
        return barraProgresoList;
    }

    public void setBarraProgresoList(List<BarraProgreso> barraProgresoList) {
        this.barraProgresoList = barraProgresoList;
    }

    public MenuCodefacRespuesta getMenuCodefacRespuesta() {
        return menuCodefacRespuesta;
    }

    public void setMenuCodefacRespuesta(MenuCodefacRespuesta menuCodefacRespuesta) {
        this.menuCodefacRespuesta = menuCodefacRespuesta;
    }

    public Map<ModuloCodefacEnum, MenuModel> getMenuModelMap() {
        return menuModelMap;
    }

    public void setMenuModelMap(Map<ModuloCodefacEnum, MenuModel> menuModelMap) {
        this.menuModelMap = menuModelMap;
    }

    public Boolean getVariableFalsa() {
        return variableFalsa;
    }

    public void setVariableFalsa(Boolean variableFalsa) {
        this.variableFalsa = variableFalsa;
    }
    
    
    
    
    
}
