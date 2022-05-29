/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;


import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

/**
 *
 * @author Carlos
 */
public class Fondo2 implements Border
{
    private BufferedImage image;

    public Fondo2(BufferedImage image) {
        this.image = image;
    }
    
    public Fondo2(Image image) {
        this.image = toBufferedImage(image);
    }
    /*
    public Fondo(Image image) {
        this.image = toBufferedImage(image);
    }*/
    
    /**
 * Converts a given Image into a BufferedImage
 *
 * @param img The Image to be converted
 * @return The converted BufferedImage
 */
public static BufferedImage toBufferedImage(Image img)
{
    if (img instanceof BufferedImage)
    {
        return (BufferedImage) img;
    }

    // Create a buffered image with transparency
    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

    // Draw the image on to the buffered image
    Graphics2D bGr = bimage.createGraphics();
    bGr.drawImage(img, 0, 0, null);
    bGr.dispose();

    // Return the buffered image
    return bimage;
}
    

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        //System.out.println(width+" - "+height);
        BufferedImage imgResize=toBufferedImage(new ImageIcon(image.getScaledInstance(width, height,0)).getImage());
        //int x0=x+(width-imgResize.getWidth())/2;
        //int y0=x+(height-imgResize.getHeight())/2;
        g.drawImage(imgResize,x,y,null);
        
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
    
}
