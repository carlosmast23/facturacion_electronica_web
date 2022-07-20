/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.core;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @auhor
 */
@ManagedBean(name = "navigationMb")
@ApplicationScoped
public class NavigationMb implements Serializable {

    //public static final Ruta RESPUESTA = new Ruta("respuesta");
    public static final Ruta INDEX = new Ruta("index");
    public static final Ruta LOGIN = new Ruta("login");
    public static final Ruta CONSULTAR_PRODUCTOS = new Ruta("consultarProductos","consultar_productos");
    public static final Ruta CONSULTAR_FACTURAS = new Ruta("consultarFacturas","consultar_facturas");
    public static final Ruta INDEX_NOSOTROS = new Ruta("index", "index.xhtml","section_nosotros");
    public static final Ruta INDEX_CONTACTANOS = new Ruta("index", "index.xhtml","section_contactanos");
    //public static final Ruta BUSQUEDA = new Ruta("busqueda");
    public static final Ruta REGISTRO_PROVEEDOR = new Ruta("registroProveedor", "registro_proveedor.xhtml");

    //private Ruta respuesta = RESPUESTA;
    private Ruta index = INDEX;
    private Ruta login = LOGIN;
    //private Ruta busqueda = BUSQUEDA;
    private Ruta registroProveedor = REGISTRO_PROVEEDOR;
    private Ruta consultarProductos=CONSULTAR_PRODUCTOS;
    private Ruta consultarFacturas=CONSULTAR_FACTURAS;
    
    private Ruta indexNosotros = INDEX_NOSOTROS;
    private Ruta indexContactanos = INDEX_CONTACTANOS;

    public Ruta getIndex() {
        return index;
    }

    public void setIndex(Ruta index) {
        this.index = index;
    }

   
    public Ruta getRegistroProveedor() {
        return registroProveedor;
    }

    public void setRegistroProveedor(Ruta registroProveedor) {
        this.registroProveedor = registroProveedor;
    }

   
    public Ruta getLogin() {
        return login;
    }

    public void setLogin(Ruta login) {
        this.login = login;
    }

    public Ruta getConsultarProductos() {
        return consultarProductos;
    }

    public void setConsultarProductos(Ruta consultarProductos) {
        this.consultarProductos = consultarProductos;
    }

    public Ruta getConsultarFacturas() {
        return consultarFacturas;
    }

    public void setConsultarFacturas(Ruta consultarFacturas) {
        this.consultarFacturas = consultarFacturas;
    }

    public Ruta getIndexNosotros() {
        return indexNosotros;
    }

    public void setIndexNosotros(Ruta indexNosotros) {
        this.indexNosotros = indexNosotros;
    }

    public Ruta getIndexContactanos() {
        return indexContactanos;
    }

    public void setIndexContactanos(Ruta indexContactanos) {
        this.indexContactanos = indexContactanos;
    }
    
    
    

    public static class Ruta {

        private static final String EXTENSION_PAGINAS = ".xhtml";
        private String rutaJsf;
        private String rutaUrl;
        private String seccion;

        public Ruta(String rutaJsf) {
            this.rutaJsf = rutaJsf;
            this.rutaUrl = rutaJsf.concat(EXTENSION_PAGINAS);
        }

        public Ruta(String rutaJsf, String rutaUrl) {
            this.rutaJsf = rutaJsf;
            this.rutaUrl = rutaUrl;
        }
        
        public Ruta(String rutaJsf, String rutaUrl,String seccion) {
            this.rutaJsf = rutaJsf;
            this.rutaUrl = rutaUrl;
            this.seccion=seccion;
        }

        public String obtenerRutaJsf() {
            return rutaJsf;
        }

        public String obtenerRutaUrl() {
            return rutaUrl;  
        }

        public String getRutaJsf() {
            return rutaJsf;
        }

        public void setRutaJsf(String rutaJsf) {
            this.rutaJsf = rutaJsf;
        }

        public String getRutaUrl() {
            return rutaUrl;
        }

        public void setRutaUrl(String rutaUrl) {
            this.rutaUrl = rutaUrl;
        }

        public String getSeccion() {
            return seccion;
        }

        
    }
}

