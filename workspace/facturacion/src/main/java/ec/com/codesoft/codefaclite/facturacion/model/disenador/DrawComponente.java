/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model.disenador;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComponenteComprobanteFisico;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author
 */
public class DrawComponente implements DrawInterface{
    
    /**
     * Seccion contenedora del componente
     */
    private DrawSeccion drawSeccion;
    
    public ComponenteComprobanteFisico componenteEntity;
    public static final int TAMANIO_LETRA=15;
    
    
    /**
     * Variable para saber cuando el mouse esta seleccionado
     */
    private boolean seleccionadoMouse;    
    
    /**
     * Variable para saber si el componente no esta seleccionado
     */
    private boolean seleccionado;
    
    /**
     * Posicion actual el componente
     */
    private int x;
    /**
     * Posicion actual del componente
     */
    private int y;
    /**
     * Ancho del componente
     */
    private int width;
    /**
     * Alto el componente
     */
    private int height;

    public DrawComponente(ComponenteComprobanteFisico componenteEntity,DrawSeccion drawSeccion) {
        this.componenteEntity=componenteEntity;
        this.seleccionado=false;
        this.seleccionadoMouse=false;
        this.drawSeccion=drawSeccion;
    }
    
    
    
    @Override
    public void dibujar(Graphics g, Point desplazamiento, DrawDocumento documento,float escala) {
        
        //Si el componente esta oculto entonces no se muestra en pantalla
        if(componenteEntity.getOculto().equals("s"))
        {
            return;
        }
        
        g.setFont(new Font("Arial", Font.PLAIN, (int) (12*escala)));
        
        //Obteniendo todas las posiciones
        this.x=(int) (componenteEntity.getX()*escala+desplazamiento.x);
        this.y= (int) (componenteEntity.getY()*escala+desplazamiento.y);
        this.width= (int) (componenteEntity.getAncho()*escala);
        this.height= (int) (componenteEntity.getAlto()*escala);
        
        //Si el componente esta selccionado cambio el color de fondo
        if(seleccionado || seleccionadoMouse)
        {
            if(seleccionadoMouse)
                g.setColor(new Color(255,179, 0, 50));
            else
                g.setColor(new Color(255,255, 0, 100));
            
            g.fillRect(this.x,this.y,this.width,this.height);
        }
        
        int tipoLetra=Font.PLAIN;
        if(componenteEntity.getNegrita().equals("s"))
        {
            tipoLetra=Font.BOLD;
        }
            
            
        Font font = new Font("Arial",tipoLetra, (int) (componenteEntity.getTamanioLetra()*escala));
        g.setFont(font);
        
        int desfazY=(int) (componenteEntity.getAlto()*escala-componenteEntity.getTamanioLetra()*escala);
        desfazY=(int)((double)desfazY/(double)2);
        
        g.setColor(Color.BLACK);
        g.drawString(componenteEntity.getNombre(), (int) (componenteEntity.getX()*escala+desplazamiento.x+5*escala), (int) (componenteEntity.getY()*escala+desplazamiento.y+componenteEntity.getTamanioLetra()*escala+desfazY));
        
        g.setColor(Color.green);
        g.drawRect(this.x,this.y,this.width,this.height);
        
        
    }

    public ComponenteComprobanteFisico getComponenteEntity() {
        return componenteEntity;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public boolean isSeleccionadoMouse() {
        return seleccionadoMouse;
    }

    public void setSeleccionadoMouse(boolean seleccionadoMouse) {
        this.seleccionadoMouse = seleccionadoMouse;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public DrawSeccion getDrawSeccion() {
        return drawSeccion;
    }
    
    
    
    
    
    
    
    
}
