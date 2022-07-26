/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model.disenador;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteFisicoDisenio;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author
 */
public class DrawDocumento implements DrawInterface{
    /**
     * Documento que se va 
     */
    public ComprobanteFisicoDisenio documentoEntity;
    
    private List<DrawSeccion> secciones;
    
    private int x;
    private int y;
    private int ancho;
    private int alto;

    public DrawDocumento(ComprobanteFisicoDisenio documentoEntity) {
        this.documentoEntity=documentoEntity;
        this.secciones=new ArrayList<DrawSeccion>();
    }

    
    @Override
    public void dibujar(Graphics g,Point desplazamiento,DrawDocumento documento,float escala) {
        g.setColor(Color.white);
        
        this.x=(int) (0+desplazamiento.x);
        this.y= (int) (0+desplazamiento.y);
        this.ancho=(int) (documentoEntity.getAncho()*escala);
        this.alto=(int) (documentoEntity.getAlto()*escala);
        g.fillRect(this.x,this.y,this.ancho,this.alto);
        
        Point desplazamientoTemp=new Point(0,0);
        desplazamientoTemp.x+=desplazamiento.x;
        desplazamientoTemp.y+=desplazamiento.y;
        for (DrawSeccion seccion : secciones) {
            seccion.dibujar(g, desplazamientoTemp, documento,escala);
            desplazamientoTemp.y+=seccion.seccionEntity.getAlto()*escala;
        }
    }
    
    public void agregarSeccion(DrawSeccion seccion)
    {
        this.secciones.add(seccion);
    }

    public List<DrawSeccion> getSecciones() {
        return secciones;
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
   
    
    
}
