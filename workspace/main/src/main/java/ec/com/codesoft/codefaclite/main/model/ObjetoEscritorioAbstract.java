/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.controlador.core.swing.InterfazComunicacionPanel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import org.jdesktop.swingx.JXTitledPanel;

/**
 *
 * @author Carlos
 */
public abstract class ObjetoEscritorioAbstract extends JPanel{
    
    /**
     * Referencia que me permite consumir los metodos del controlador principal
     */
    public InterfazComunicacionPanel panelPadre;
    
    public boolean presionando;
    
    private IconoInterfaz iconoInterfaz;
    private Point ubicacionRelativaMouse;
    private JDesktopPane parentPanel;
    /**
     * Panel que permite contralar el movimiento del panel padre
     */
    public abstract JPanel getPanelMovimiento();

    public ObjetoEscritorioAbstract(JDesktopPane parentPanel) 
    {
        this.parentPanel=parentPanel;
        this.presionando=false;
        //agregarListener();
    }
    
    /**
     * Metodo pendiente para agregar el titulo de un panel
     * TODO: terminar de imlementra
     */
    private void agregarTituloPantalla()
    {
        //JXTitledPanel tPanel = new JXTitledPanel("Encabezado", cPanel);
        //tPanel.setTitlePainter(pintor);
        //tPanel.setTitleForeground(FG_COLOR);

    }
    
    protected void agregarListenerMovimiento()
    {
        JPanel panelMovimiento= getPanelMovimiento();
        panelMovimiento.addMouseListener(new MouseListener() {
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
                    //int tamanioX=(int)((double)(getWidth())/(double)2);
                    Point point=getLocation();
                    //point.x+=tamanioX;
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
        
        panelMovimiento.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                //parentPanel.getLocationOnScreen().
                Point rectanguloPadre= parentPanel.getLocationOnScreen();
                int x=e.getXOnScreen()-rectanguloPadre.x-ubicacionRelativaMouse.x;
                int y=e.getYOnScreen()-rectanguloPadre.y-ubicacionRelativaMouse.y;
                setBounds(new Rectangle(x, y,getWidth(),getHeight()));
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
}
