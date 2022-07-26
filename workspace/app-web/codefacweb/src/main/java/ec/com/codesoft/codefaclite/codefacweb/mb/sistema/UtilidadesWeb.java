/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.sistema;

import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.UtilidadWeb;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.PrimeFaces;

/**
 *
 * @author
 */
public class UtilidadesWeb {
    
    public static final String FACES_REDIRECT="faces-redirect";

    /*
    public void exportarPDF(ActionEvent actionEvent) throws JRException, IOException{
		Map<String,Object> parametros= new HashMap<String,Object>();
		parametros.put("txtUsuario", "MitoCode");
		
		File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/rptJSF.jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(),parametros, new JRBeanCollectionDataSource(this.getPersonas()));
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-disposition","attachment; filename=jsfReporte.pdf");
		ServletOutputStream stream = response.getOutputStream();
		
		JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
		
		stream.flush();
		stream.close();
		FacesContext.getCurrentInstance().responseComplete();
	}*/

    public static void exportarPDF(JasperPrint jasperPrint) throws JRException, IOException {

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        //response.addHeader("Content-disposition","attachment; filename=jsfReporte.pdf"); //Con este metodo descarga en el navegador
        response.setContentType("application/pdf");  //Con este metodo abre en el navegador
        ServletOutputStream stream = response.getOutputStream();

        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

        stream.flush();
        stream.close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    public static void redirigirPaginaInterna(String pagina) {
        FacesContext contex = FacesContext.getCurrentInstance();
        //contex.getExternalContext().redirect(pagina);
        contex.getApplication().getNavigationHandler().handleNavigation(contex, null, pagina);
    }

    public static void redirigirPaginaExterna(String pagina) {
        try {
            FacesContext contex = FacesContext.getCurrentInstance();
            contex.getExternalContext().redirect(pagina);
            //contex.getApplication().getNavigationHandler().handleNavigation(contex, null, pagina);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void actualizaComponente(String id) 
    {        
        PrimeFaces.current().ajax().update(id); 
    }  
     
    
    /**
     * Obtiene los parametros enviados desde la peticion
     * @return 
     */
    public static Map<String,String> obtenerParametrosPeticion()
    {
        Map<String,String> mapParametros=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        
        for (Map.Entry<String, String> entry : mapParametros.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            System.out.println("Key="+key+",Val="+val);
            
        }
        
        return mapParametros;
    }
    
    public static String buscarParametroPeticion(String clave)
    {
        return obtenerParametrosPeticion().get(clave);
    }
    
    public static void ejecutarJavascript(String script)
    {
        PrimeFaces current = PrimeFaces.current();
        current.executeScript(script); //Todo: Parametrizar y poner en una funcion aparte este dialogo        
    }
    
    public static void abrirDialogo(String nombreDialogo,Integer ancho)
    {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        //sessionMap.put("busquedaClase", modeloBusqueda); 

        //Esstablecer porpiedades que se van a enviar al dialogo en map
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("resizable", true);
        options.put("draggable", false);
        options.put("modal", true);
        options.put("width", ancho+15);
        //options.put("height", 340);
        //options.put("busquedaClase", new EmpleadoBusquedaDialogo() ); //TODO: Mando por defecto un dialogo por defecto
        //String nombreDialogoBusqueda = "dialogo_busqueda";
        //PrimeFaces.current().dialog()
        PrimeFaces.current().dialog().openDynamic(nombreDialogo, options, null);
        
    }
    
    public static void retornarResultadoDialogo(Object resultado)
    {
        PrimeFaces.current().dialog().closeDynamic(resultado);
    }
    
    public static String agregarParametroGet(String url,Map<String,String> mapParametros,Boolean redirigir)
    {
        //Solo crear el parametro de faces redirect si no existe creado previamente
        if(url.indexOf(FACES_REDIRECT)<0)
        {
            mapParametros.put(FACES_REDIRECT,redirigir.toString());
        }
        return agregarParametroGet(url, mapParametros);
    }
    
    public static String agregarParametroGet(String url,Map<String,String> mapParametros)
    {
        String parametros="?";
        //Si ya existe creados parametros en la url empiezo directamente con el simbolo de agregar nuevo parametro
        if(url.indexOf("?")>=0)
        {
            parametros="&";
        }
        
        for (Map.Entry<String, String> entry : mapParametros.entrySet()) {
            String clave=entry.getKey();
            String valor=entry.getValue();
            parametros+=clave+"="+valor+"&";
        }
        parametros=parametros.substring(0,parametros.length()-1);
        return url.concat(parametros);
    }
    
}
