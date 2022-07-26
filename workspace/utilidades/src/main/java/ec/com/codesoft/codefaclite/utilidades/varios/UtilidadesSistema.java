/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.varios;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public abstract class UtilidadesSistema {
     /**
     * Metodo que compara una version con otra y devuelve 1 si es mayor la version , 0 si es igual y -1 si es menor 
     * @param version1
     * @return 
     */
    public static int compareVersion(String version1,String version2)
    {
        String[] componentesVersion=version1.split("\\.");
        String[] componentesVersion2=version2.split("\\."); 
        
        int i=0;
        while(true)
        {
            Integer valor1=0;
            if(i<componentesVersion.length)
            {           
                try
                {
                    valor1=Integer.parseInt(componentesVersion[i]);                
                }catch(java.lang.NumberFormatException nfe)
                {
                    System.out.println("revisar");
                }
            }
            
            Integer valor2=0;
            if(i<componentesVersion2.length)
            {
                valor2=Integer.parseInt(componentesVersion2[i]);
            }
            
            //Comparar las 2 versiones
            if(valor1.compareTo(valor2)!=0)
            {
                return valor1.compareTo(valor2);
            }
            
            //Verificar condicion para terminar
            if(i>=componentesVersion.length && i>=componentesVersion2.length)
            {
                return 0;
            }
            
            i++;
            
        }
        

    }
    
    public static void abrirUrlNavegador(String url) 
    {
        try {
            Desktop dk = Desktop.getDesktop();
            dk.browse(new URI(url));
        } catch (URISyntaxException ex) {
            Logger.getLogger(UtilidadesSistema.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadesSistema.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void abrirDocumento(File path) throws IOException 
    {
            Desktop.getDesktop().open(path);
    }
    
    
}
