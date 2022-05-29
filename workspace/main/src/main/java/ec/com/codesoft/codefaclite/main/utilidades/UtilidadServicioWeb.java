/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.utilidades;

import ec.com.codesoft.codefaclite.main.init.Main;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class UtilidadServicioWeb {
    
    private static final Logger LOG = Logger.getLogger(Main.class.getName());
    
    public static void activarServicioWeb()
    {
        try {
            LOG.log(Level.INFO, "Servicio web activado");
            //List<String> comando=Arrays.asList("cd","apache-tomcat-7.0.94");
            List<String> comando = Arrays.asList("tomcat9/bin/startup.bat", "tomcat9");
            //Primer comando
            ProcessBuilder pb = new ProcessBuilder()
                    .command(comando);
            pb.command();
            Process p = pb.start();

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * TODO: Optimizar esta parte porque en algun lado deberia grabar si esta activo para no ejecutar imnecesariamente
     */
    public static void apagarServicioWeb()
    {
        try {
            LOG.log(Level.INFO, "Servicio web apagado");
            //List<String> comando=Arrays.asList("cd","apache-tomcat-7.0.94");
            List<String> comando = Arrays.asList("tomcat9/bin/shutdown.bat", "tomcat9");
            //Primer comando
            ProcessBuilder pb = new ProcessBuilder()
                    .command(comando);
            pb.command();
            Process p = pb.start();

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
