/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model.disenador;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

/**
 *
 * @author Carlos
 */
public class LienzoDisenador extends JPanel{

    private DrawCanvas drawCanvas;

    public LienzoDisenador(DrawCanvas drawCanvas) {
        this.drawCanvas = drawCanvas;
    }

    /*
    @Override
    public void paint(Graphics g) {
        if(this.drawCanvas!=null)
        {
            this.drawCanvas.dibujar(g,new Point(0,0),null);
        }
    }*/

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        //this.drawCanvas.dibujar(g,new Point(0,0),null);
    }
    
    
    
    
}
