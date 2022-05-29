/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.core;

import ec.com.codesoft.codefaclite.codefacweb.mb.sistema.UtilidadesWeb;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OrdenarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import java.io.InputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Controlador que me permite contralar el comportamiento de funcionalidad
 * reutilizable en las pantallas que no requieren acceso al sistema
 *
 * @author CARLOS_CODESOFT
 * TODO: Cambiar nombre a Controller
 */
@ManagedBean(name = "controllerPublicoMb")
@ViewScoped
public class ControllerPublicoMb implements Serializable {

    private static final String GET_ID_EMPRESA = "idEmpresa";

    /**
     * Referencia de la empresa seleccionada para mostrar la información de la
     * landingPage
     */
    private Empresa empresaSeleccionada;

    @PostConstruct
    public void init() {
        System.out.println("init ControllerPublicoMb ... ");
        buscarEmpresaSeleccionada();
    }
    
    /*private void cargarImagenEmpresa()
    {
        InputStream inputStream = RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream(ParametrosSistemaCodefac.ComprobantesElectronicos.LOGO_SIN_FOTO);{
        
    }*/

    private void buscarEmpresaSeleccionada() {
        String idEmpresaStr = UtilidadesWeb.buscarParametroPeticion(GET_ID_EMPRESA);
        try {
            if (idEmpresaStr == null || idEmpresaStr.isEmpty()) {
                //Si no existe una parameatro ingresada por get busco por defecto la primera empresa
                empresaSeleccionada = ServiceFactory.getFactory().getEmpresaServiceIf().obtenerTodosActivos(OrdenarEnum.ASCEDENTE).get(0);

            } else {
                empresaSeleccionada = ServiceFactory.getFactory().getEmpresaServiceIf().buscarPorId(Long.parseLong(idEmpresaStr));

            }
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerPublicoMb.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public String construirUrl(NavigationMb.Ruta ruta)
    {
        String urlRuta=ruta.getRutaUrl();
        Map<String,String> mapParametros=new HashMap<String,String>();
        mapParametros.put(GET_ID_EMPRESA,empresaSeleccionada.getId().toString());
        String urlConParametros=UtilidadesWeb.agregarParametroGet(urlRuta,mapParametros,Boolean.TRUE);
        //Agregar la seccion a la URL
        if(ruta.getSeccion()!=null)
        {
            urlConParametros=urlConParametros+"#"+ruta.getSeccion();
        }
        System.out.println("RutaUrl: "+urlConParametros);
        return urlConParametros;
    }
    
    public String construirUrl(NavigationMb.Ruta ruta,Boolean redirigir)
    {       
        String urlNueva=construirUrl(ruta);
        System.out.println("RutaUrl: "+urlNueva);
        System.out.println("Redirigiendo ....");
        UtilidadesWeb.redirigirPaginaInterna(urlNueva);
        System.out.println("terminando Redirigiendo ....");
        return urlNueva;
    }

    public Empresa getEmpresaSeleccionada() {
        return empresaSeleccionada;
    }

    public void setEmpresaSeleccionada(Empresa empresaSeleccionada) {
        this.empresaSeleccionada = empresaSeleccionada;
    }

}
