 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
//import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
//import static javax.faces.application.ProjectStage.SystemTest;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class ConfiguracionInicialMb implements Serializable{
    
    
    private String ipServidor;
    
    @PostConstruct
    public void init()
    {
        try {
            String propertyHome = System.getenv("CATALINA_HOME");
            String pathProperty=propertyHome+"/../codefac.ini";
            
            InputStream stream = new FileInputStream(pathProperty);
            
            //try {
            //InputStreamReader input = new InputStreamReader(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/ejemplo.prop"));
            Properties propiedades=new Properties();
            propiedades.load(stream);
            System.out.println("Ip servidor archivo init: "+propiedades.get("servicio_web_ip_servidor").toString());
            ipServidor=propiedades.get("servicio_web_ip_servidor").toString();
            ServiceFactory.newController(ipServidor);   
            //Properties propiedades=getPropertiesFromClasspath("ejemplo.prop");
            //String valor=propiedades.get("valor").toString();
            //System.out.println(valor);
            //} catch (IOException ex) {
            //    Logger.getLogger(ConfiguracionInicialMb.class.getName()).log(Level.SEVERE, null, ex);
            //}
            String paginaActual="login.xhtml";
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest origRequest = (HttpServletRequest) context.getExternalContext().getRequest();           
            //FacesContext.getCurrentInstance().getExternalContext().redirect("faces/principal.xhtml");
            FacesContext.getCurrentInstance().getExternalContext().redirect(paginaActual);
        } catch (IOException ex) {
            Logger.getLogger(ConfiguracionInicialMb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String conectarServidor()
    {
        ServiceFactory.newController(ipServidor);   
        FacesContext context = FacesContext.getCurrentInstance();         
        context.addMessage(null, new FacesMessage("Correcto",  "El servidor fue conectado correctamente") );
        //return "principal.xhtml";
        return "principal.xhtml";
    }

    public String getIpServidor() {
        return ipServidor;
    }

    public void setIpServidor(String ipServidor) {
        this.ipServidor = ipServidor;
    }
    
    private Properties getPropertiesFromClasspath(String propFileName)
            throws IOException {
        Properties props = new Properties();
        InputStream inputStream
                = this.getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream == null) {
            throw new FileNotFoundException("property file '" + propFileName
                    + "' not found in the classpath");
        }

        props.load(inputStream);
        return props;
    }
    
}
