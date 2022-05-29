package ec.com.codesoft.codefaclite.codefacweb.mb.test;

import ec.com.codesoft.codefaclite.codefacweb.core.SessionMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.LoginMb;
import static ec.com.codesoft.codefaclite.codefacweb.mb.LoginMb.obtenerPerfilesUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OrdenarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.MenuCodefacRespuesta;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author CARLOS_CODESOFT
 */
@ManagedBean
@ViewScoped
public class EjemploMenu implements Serializable {

    private MenuModel model;
    private MenuCodefacRespuesta menuCodefacRespuesta;

    private SessionCodefac construirSession(Usuario usuario, Empresa empresa, Sucursal sucursal) {
        try {
            SessionCodefac session = ServiceFactory.getFactory().getUtilidadesServiceIf().getSessionPreConstruido(empresa);

            session.setEmpresa(empresa);

            session.setUsuario(usuario);
            session.setPerfiles(obtenerPerfilesUsuario(usuario));
            session.setSucursal(sucursal);//Todo:Seteado por defecto
            session.setMatriz(sucursal);//TODO: Falta buscar la matriz de esa sucursal
            return session;

        } catch (RemoteException ex) {
            Logger.getLogger(LoginMb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @PostConstruct
    public void init() {
        try {
            ServiceFactory.newController("192.168.100.7"); 
            System.out.println("Conexion iniciada");
            Empresa empresa=ServiceFactory.getFactory().getEmpresaServiceIf().obtenerTodosActivos(OrdenarEnum.ASCEDENTE).get(0);
            Usuario usuario=ServiceFactory.getFactory().getUsuarioServicioIf().consultarUsuarioActivoPorEmpresa("soporte", empresa);
            Sucursal sucursal=ServiceFactory.getFactory().getSucursalServiceIf().consultarActivosPorEmpresa(empresa).get(0);
            
            SessionCodefac session=construirSession(usuario, empresa, sucursal);
            menuCodefacRespuesta=ServiceFactory.getFactory().getPerfilServicioIf().construirMenuPermisosUsuario(session);
            
            model = new DefaultMenuModel();
            
            DefaultSubMenu firstSubmenu = new DefaultSubMenu();
            firstSubmenu.setLabel("Dynamic Submenu");
            
            DefaultMenuItem item = new DefaultMenuItem();
            //DefaultMenuItem item = DefaultMenuItem.builder();
            item.setValue("External");
            item.setUrl("http://www.primefaces.org");
            item.setIcon("pi pi-home");
            
            firstSubmenu.getElements().add(item);
            
            //model.getElements().add(firstSubmenu);
            
            //Second submenu
            DefaultSubMenu secondSubmenu = new DefaultSubMenu();
            secondSubmenu.setLabel("Dynamic Actions");
            //secondSubmenu.setExpanded(true);
            
            item = new DefaultMenuItem();
            item.setValue("Save");
            item.setIcon("pi pi-save");
            item.setCommand("#{menuView.save}");
            //item.setUpdate("messages");
            secondSubmenu.getElements().add(item);
            
            item = new DefaultMenuItem();
            item.setValue("Delete");
            item.setIcon("pi pi-times");
            item.setCommand("#{menuView.delete}");
            item.setAjax(false);
            secondSubmenu.getElements().add(item);
            
            item = new DefaultMenuItem();
            item.setValue("Redirect");
            item.setIcon("pi pi-search");
            item.setCommand("#{menuView.redirect}");
            secondSubmenu.getElements().add(item);
            
            secondSubmenu.getElements().add(firstSubmenu);
            
            
            model.getElements().add(secondSubmenu);
        } catch (RemoteException ex) {
            Logger.getLogger(EjemploMenu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(EjemploMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MenuModel getModel() {
        return model;
    }

    public void setModel(MenuModel model) {
        this.model = model;
    }

    public MenuCodefacRespuesta getMenuCodefacRespuesta() {
        return menuCodefacRespuesta;
    }

    public void setMenuCodefacRespuesta(MenuCodefacRespuesta menuCodefacRespuesta) {
        this.menuCodefacRespuesta = menuCodefacRespuesta;
    }

    
}
