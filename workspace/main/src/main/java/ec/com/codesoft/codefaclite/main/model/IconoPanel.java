/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import javafx.scene.Parent;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.DesktopPaneUI;

/**
 *
 * @author Carlos
 */
public class IconoPanel extends JPanel{
    
    public boolean presionando;
    private JLabel lblTitulo;
    private JLabel lblImagen;
    private JDesktopPane parentPanel;
    
    private Color colorFondo= new Color(188, 188, 188, 100);
    private Point ubicacionRelativaMouse;
    private int width=64;
    private int heigth=94;
    
    private IconoInterfaz iconoInterfaz;
    
    
    public IconoPanel(String titulo,URL urlImagen,JDesktopPane parentPanel,int x, int y) 
    {
        super();
        this.parentPanel=parentPanel;
        establecerPropiedadesPanel();
        agregarListener();
        this.lblTitulo=new JLabel(titulo);
        this.lblImagen=new JLabel();
        this.lblImagen.setIcon(new javax.swing.ImageIcon(urlImagen));
        //System.out.println("tamanio lbl: "+lblTitulo.getSize().width);
        int tamanioX=(int)((double)(lblTitulo.getText().length()*8-64)/(double)2);
        setBounds(x-tamanioX,y,lblTitulo.getText().length()*8,heigth);
        add(lblImagen);
        add(lblTitulo);
        
        
    }
    
    
    
    private void establecerPropiedadesPanel()
    {
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setBackground(colorFondo);
        setOpaque(false);
        this.presionando=false;
    }
    
    private void agregarListener()
    {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                   if(iconoInterfaz!=null)
                   {
                       iconoInterfaz.doubleClick();
                   }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                presionando=true;
                ubicacionRelativaMouse=e.getPoint();
                //System.out.println("presionado");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                presionando=false;
                if (iconoInterfaz != null) {
                    int tamanioX=(int)((double)(lblTitulo.getText().length()*8-64)/(double)2);
                    Point point=getLocation();
                    point.x+=tamanioX;
                    iconoInterfaz.grabarNuevaPosicion(point);
                }             

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setOpaque(true);
                
                parentPanel.repaint();
                //revalidate();
                //repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setOpaque(false);
                //
                parentPanel.repaint();
                //revalidate();

            }
        });
        
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                //parentPanel.getLocationOnScreen().
                Point rectanguloPadre= parentPanel.getLocationOnScreen();
                int x=e.getXOnScreen()-rectanguloPadre.x-ubicacionRelativaMouse.x;
                int y=e.getYOnScreen()-rectanguloPadre.y-ubicacionRelativaMouse.y;
                setBounds(new Rectangle(x, y,lblTitulo.getText().length()*8, 94));
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //System.out.println("icono moviendo");
            }
        });
        
    }
    
    public void addListenerIcono(IconoInterfaz iconoInterfaz)
    {
        this.iconoInterfaz=iconoInterfaz;
    }

    public IconoInterfaz getIconoInterfaz() {
        return iconoInterfaz;
    }
    
    public void setNuevaPosicion(int x, int y)
    {
        setLocation(x, y);
        getIconoInterfaz().grabarNuevaPosicion(new Point(x, y));
    }
    
    
    public String getTitulo()
    {
        return lblTitulo.getText();
    }
    
    
    
}
