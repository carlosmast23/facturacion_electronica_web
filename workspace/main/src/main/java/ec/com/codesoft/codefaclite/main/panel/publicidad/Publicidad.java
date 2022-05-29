/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.panel.publicidad;

import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author Carlos
 */
public class Publicidad {
    private URL urlImagen;
    private String link;
    private int probabilidad;
    private String toolTipText;

    public Publicidad(URL urlImagen, String link, int probabilidad,String toolTipText) {
        this.urlImagen = urlImagen;
        this.link = link;
        this.probabilidad = probabilidad;
        this.toolTipText=toolTipText;
    }
    
    public ImageIcon getImage()
    {
        return new javax.swing.ImageIcon(urlImagen);
    }

    public URL getUrlImagen() {
        return urlImagen;
    }

    public String getLink() {
        return link;
    }

    public int getProbabilidad() {
        return probabilidad;
    }

    public String getToolTipText() {
        return toolTipText;
    }
    
    
    
}
