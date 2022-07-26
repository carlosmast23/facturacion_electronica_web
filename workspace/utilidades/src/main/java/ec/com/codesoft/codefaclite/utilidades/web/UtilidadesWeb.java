/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.web;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
//import sun.applet.Main;

/**
 *
 * @author
 */
public class UtilidadesWeb {

    private static final Logger LOG = Logger.getLogger(UtilidadesWeb.class.getName());
    

    public static void abrirPaginaWebNavigador(String url) {
        try {
            Desktop dk = Desktop.getDesktop();
            dk.browse(new URI(url));
        } catch (URISyntaxException ex) {
            Logger.getLogger(UtilidadesWeb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean descargarArchivo(String nombreArchivo,String url, String carpeta)
    {
         try {
            String name = nombreArchivo;//nombre del archivo destino

            //Directorio destino para las descargas
            String folder = "";
            
            if(!carpeta.equals(""))
            {
                folder = carpeta+"/";
                //Crea el directorio de destino en caso de que no exista
                File dir = new File(folder);

                if (!dir.exists()) {
                    if (!dir.mkdir()) {
                        return false; // no se pudo crear la carpeta de destino
                    }
                }
            }

            File file = new File(folder + name);

            URLConnection conn = new URL(url).openConnection();
            //conn.setConnectTimeout(3000); //Espera 3 segundos para verificar si existe conexion
            conn.connect();
            int tamanioTotal = conn.getContentLength();
            
            BigDecimal tamanioArchivo = new BigDecimal((float) tamanioTotal / (float) (1024));
            
            LOG.log(Level.INFO,"\nempezando descarga: \n");
            LOG.log(Level.INFO,">> URL: " + url);
            LOG.log(Level.INFO,">> Nombre: " + name);
            LOG.log(Level.INFO,">> tamaño: " + tamanioArchivo + " kb");
            
            InputStream in = conn.getInputStream();
            FileOutputStream out = new FileOutputStream(file);


            long size = 0;
            int b = 0;
            int i = 0;
            while (b != -1) {
                b = in.read();
                i++;
                if (b != -1) {
                    size = out.getChannel().size();
                    out.write(b);
                }

                if (i % 100000 == 0) {
                    BigDecimal tamanio = new BigDecimal((float) size / (float) (1024));
                    //System.out.println(tamanio.setScale(3, RoundingMode.HALF_UP) + " kb");
                    int porcentaje=(int) ((float) size * 100 / (float) tamanioTotal);
                    //getBarraProgreso().setValue(porcentaje);
                }
            }

            out.close();
            in.close();
        } catch (IOException ex) {
            LOG.log(Level.WARNING, "No se puede descargar el archivo: "+url);
            Logger.getLogger(UtilidadesWeb.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
           
         return true;

    }
    public static String construirUrlGoogleMaps(BigDecimal latitud , BigDecimal longitud)
    {
        final String urlBase="https://www.google.com/maps/search/?api=1&?1,?2";
        
        if(latitud==null || longitud==null)
        {
            return urlBase;
        }
        
        String urlFinal=urlBase
                .replace("?1",latitud.toString())
                .replace("?2",longitud.toString());
        
        return urlFinal;
    }
}
