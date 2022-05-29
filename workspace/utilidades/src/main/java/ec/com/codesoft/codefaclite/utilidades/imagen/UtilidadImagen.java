/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.imagen;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadImagen {
    public static InputStream castImputStreamForReport(InputStream input) {
        
        try {
            BufferedImage image = ImageIO.read(input); 
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            return is;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UtilidadImagen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadImagen.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                input.close();
            } catch (IOException ex) {
                Logger.getLogger(UtilidadImagen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
        

    }
    
    public static Image castInputStreamToImage(InputStream inputStream)
    {
        try {
            return ImageIO.read(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
            
    public static ImageInputStream castBufferImputStream(InputStream input) {
        
        try {
            ImageInputStream iis=ImageIO.createImageInputStream(input);
        } catch (IOException ex) {
            Logger.getLogger(UtilidadImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Image convertirOutPutStreamToImage(ByteArrayOutputStream bos) {
       
        try {
            byte [] data = bos.toByteArray();
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            BufferedImage bImage2 = ImageIO.read(bis);
            return bImage2;
        } catch (IOException ex) {
            Logger.getLogger(UtilidadImagen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
