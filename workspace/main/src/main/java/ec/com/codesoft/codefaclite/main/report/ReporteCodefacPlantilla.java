/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.report;

import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.InterfazComunicacionPanel;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class ReporteCodefacPlantilla extends ReporteCodefac{
    
   public static void generarReporteInternalFrame(String pathReporte,Map parametros,Collection datos,InterfazComunicacionPanel panelPadre,String tituloReporte)
   {
       generarAtributosPlantilla(parametros);
       generarReporteInternalFrame(pathReporte, parametros, datos, panelPadre, tituloReporte);
   }
   
   public static void generarAtributosPlantilla(Map parametros)
   {
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        parametros.put("fecha_hora",formateador.format(new Date()));
        parametros.put("usuario","Pepito");
        parametros.put("direccion","Sangolqui");
        parametros.put("nombre_empresa","CODEPEPITO");
        parametros.put("telefono","2333167-0987651233");
        parametros.put("SUBREPORT_DIR",RecursoCodefac.JASPER.getPath());
   }
    

}
