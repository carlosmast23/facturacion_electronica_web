/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import java.io.Serializable;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author
 */
public enum CategoriaMenuEnum implements Serializable{
    GESTIONAR("Gestionar",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("categoriamenu/gestionar.png")),
    PROCESOS("Procesos",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("categoriamenu/proceso.png")),
    DEUDAS_ACADEMICOS("Rubros",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("categoriamenu/deudas.png")),
    MATRICULA("Matricula",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("categoriamenu/inscripcion.png")),
    REPORTES("Reportes",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("categoriamenu/reporte.png")),
    MIGRAR("Migrar",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("categoriamenu/carpeta.png")),
    UTILIDADES("Utilidades",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("categoriamenu/utilidades.png"));  ;
    
    private String nombre;
    private ImageIcon icono;

    private CategoriaMenuEnum(String nombre,URL path ) {
        this.nombre = nombre;
        setImageIcon(path);
    }
    
    private void setImageIcon(URL path) {
        icono = new ImageIcon(path);
    }

    public String getNombre() {
        return nombre;
    }

    public ImageIcon getIcono() {
        return icono;
    }
    
    
}
