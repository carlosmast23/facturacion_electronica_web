/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.proxy;

import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author
 */
public class ReporteProxy {
    public RecursoCodefac recursoCodefac;
    public String nombre;
    public JasperReport jasperReport;

    public ReporteProxy(RecursoCodefac recursoCodefac,String nombre,JasperReport jasperReport) {
        this.recursoCodefac = recursoCodefac;
        this.nombre=nombre;
        this.jasperReport=jasperReport;
    }
    
    
    public static List<ReporteProxy> lista=new ArrayList<ReporteProxy>();
    
    /*public static void consultarOAgregar(RecursoCodefac recursoCodefac,String nombre)
    {
        ReporteProxy nuevo=new ReporteProxy(recursoCodefac,nombre);
        ReporteProxy consultado=buscar(nuevo);
        if(consultado==null)
        {
            agregar(nuevo);
        }
        return nul
    }*/
    
    public static void agregar(RecursoCodefac recursoCodefac,String nombre,JasperReport jasperReport)
    {
        //Solo agregar el objeto si no existe
        ReporteProxy nuevo=new ReporteProxy(recursoCodefac,nombre,jasperReport);
        if(buscar(recursoCodefac,nombre)==null)
        {
            lista.add(nuevo);
        }
    }
    
    public static JasperReport buscar(RecursoCodefac recursoCodefac,String nombre)
    {
        for (ReporteProxy reporte : lista) {
            if(reporte.recursoCodefac.equals(recursoCodefac) && reporte.nombre.equals(nombre))
            {
                return reporte.jasperReport;
            }
        }
        return null;
    }
    
}
