/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model.disenador;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.BandaComprobante;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Rectangle2D;

/**
 *
 * @author
 */
public class DrawSeccion implements DrawInterface{
    
    public BandaComprobante seccionEntity;
    
    private List<DrawComponente> componentes;
    
    private int x;
    private int y;
    private int ancho;
    private int alto;

    public DrawSeccion(BandaComprobante seccionEntity) {
        this.seccionEntity=seccionEntity;
        this.componentes=new ArrayList<DrawComponente>();
    }

    @Override
    public void dibujar(Graphics g, Point desplazamiento,DrawDocumento documento,float escala) {
        g.setColor(Color.LIGHT_GRAY);
        
        this.x=(int) (desplazamiento.x);
        this.y=(int) (desplazamiento.y);
        this.ancho=(int) (documento.documentoEntity.getAncho()*escala);
        this.alto=(int) (seccionEntity.getAlto()*escala);        
        
        g.drawRect(this.x,this.y,this.ancho,this.alto);
        //g.drawString(nombre, (desplazamiento.x+documento.ancho)*3/4, desplazamiento.y+alto+50);
        //Rectangle rectangle=new Rectangle((int) (desplazamiento.x), (int) (desplazamiento.y), (int) (documento.documentoEntity.getAncho()*escala), (int) (seccionEntity.getAlto()*escala));
        Rectangle rectangle=new Rectangle(this.x,this.y,this.ancho,this.alto);
        g.setColor(new Color(220,220,220));
        drawCenteredString(g, seccionEntity.getTitulo(),rectangle,new Font("Arial", Font.PLAIN, (int) (25*escala)));
        
        /**
         * Dibujar Componentes
         */
        
        for (DrawComponente componente : componentes) {
            componente.dibujar(g,desplazamiento, documento,escala);
        }
        
    }
    
    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    public void agregarComponente(DrawComponente componente)
    {
        this.componentes.add(componente);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public BandaComprobante getSeccionEntity() {
        return seccionEntity;
    }
    
    
    
}
