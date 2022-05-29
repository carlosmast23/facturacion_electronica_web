/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import java.io.Serializable;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Carlos
 */
public enum ModuloCodefacEnum implements Serializable{
    CONTABILIDAD("Contabilidad","CONT",
            "o","modulo_contabilidad",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("modulos/contabilidad.png")),
    
    FACTURACION("Facturacion","FAC",
            "f","modulo_facturacion",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("modulos/facturacion.png")),
    
    POS("Pos","POS",
            "p","modulo_pos",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("modulos/pos.png")),
    
    CRM("Crm","CRM",
            "c","modulo_crm",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("modulos/crm.png")),
    
    SERVICIOS("Servicios","SER",
            "s","modulo_servicios",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("modulos/servicios.png")),
    
    COMPRA("Compra","COM",
            "b","modulo_compra",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("modulos/compra.png")),
    
    CARTERA("Cartera","CAR",
            "w","modulo_cartera",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("modulos/cartera.png")),
    
    INVENTARIO("Inventario","INVS",
            "i","modulo_inventario",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("modulos/inventario.png")),        
    
    GESTIONA_ACADEMICA("Académico","ACAS",
            "a","modulo_gestion_academica",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("modulos/gestion_academica.png")),
    
    TRANSPORTE("Transporte","TRAN",
            "a","modulo_transporte",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("modulos/transporte.png")), //TODO: por el momento no modifico el valor de esa letra que esta duplicada porque por ejemplo con los clientes de la guarderia van a tener problemas , mejor verificar que saben las credenciales para actualizar
    
    RESTAURANTE("Restaurante","REST",
            "r","modulo_restaurante",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("modulos/restaurante.png")),
    
    SISTEMA("Sistema","SIST",
            "t","modulo_sistema",RecursoCodefac.IMAGENES_ICONOS.getResourceURL("modulos/sistema.png"))
    
    ;
    
    private String nombre;
    private String codigo;
    private String nombrePropiedad;
    private ImageIcon icono;
    
    /**
     * Este campo es importante que este sincronizado con la base de datos del administrador
     * Nota: los valores seteados ya no cambiar porque las licencias van a funcionar con las letras y puede genera problema si se cambian los nombres
     */
    private String letra;

    private ModuloCodefacEnum(String nombre, String codigo,String letra, String nombrePropiedad,URL path) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.letra=letra;
        this.nombrePropiedad = nombrePropiedad;
        setImageIcon(path);
    }

    private void setImageIcon(URL path)
    {
        icono=new ImageIcon(path);
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombrePropiedad() {
        return nombrePropiedad;
    }

    public ImageIcon getIcono() {
        return icono;
    }

    public String getLetra() {
        return letra;
    }
    
    

    @Override
    public String toString() {
        return nombre;
    }
    
    //Metodos personalizados
    
    public static ModuloCodefacEnum getModuloCodefacEnumByCodigo(String codigo)
    {
        for (ModuloCodefacEnum modulo : ModuloCodefacEnum.values()) {
            if(modulo.getCodigo().equals(codigo))
            {
                return modulo;
            }
        }
        return null;
    }
    
}
