/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model.disenador;

import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author
 */
public interface DrawInterface {
    public abstract void dibujar(Graphics g,Point desplazamiento,DrawDocumento documento,float escala);
}
