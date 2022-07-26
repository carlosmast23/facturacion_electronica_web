/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.main.panel.WidgetVirtualMall;
import ec.com.codesoft.codefaclite.virtuallmall.WebServiceVirtuallMall;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author
 */
public class WidgetVirtualMallModelo extends WidgetVirtualMall {

    public WidgetVirtualMallModelo(JDesktopPane parentPanel) {
        super(parentPanel);
        agregarListenerBotones();
        agregarListenerMovimiento();

        int n = getSliderPrioridad().getValue();
        getLblTextprioridad().setText(obtenerTextoPrioridad(n));
    }

    @Override
    public JPanel getPanelMovimiento() {
        JPanel panel = getPanelTitulo();
        return panel;
    }

    private void agregarListenerBotones() {

        getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String busqueda = getTxtBuscador().getText();
                String numero = getTxtCelular().getText();
                String nivel = obtenerNivelPrioridad(getSliderPrioridad().getValue());

                //Validacion enviar virtuall mall 
                if (busqueda.equals("") || numero.equals("")) {
                    DialogoCodefac.mensaje("Campos requeridos", "Por favor ingrese el número de celular y su busqueda para continuar", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    return;
                }

                boolean respuesta = WebServiceVirtuallMall.enviarBusqueda(busqueda, numero, nivel);
                if (respuesta) {
                    DialogoCodefac.mensaje("Correcto", "Su busqueda fue enviada correctamente \n Sus resultados se enviaran al celular", DialogoCodefac.MENSAJE_CORRECTO);
                    getTxtBuscador().setText("");
                } else {
                    DialogoCodefac.mensaje("Error", "Lo sentimos su busqueda no puede ser registrada \n intente nuevamente en unos minutos", DialogoCodefac.MENSAJE_INCORRECTO);
                }
            }
        });

        getLblImagenLogo().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    String url = "http://www.vm.codesoft-ec.com/";
                    Desktop dk = Desktop.getDesktop();
                    dk.browse(new URI(url));
                } catch (IOException ex) {
                    Logger.getLogger(HiloPublicidadCodefac.class.getName()).log(Level.SEVERE, null, ex);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(HiloPublicidadCodefac.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        getSliderPrioridad().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int n = getSliderPrioridad().getValue();
                getLblTextprioridad().setText(obtenerTextoPrioridad(n));
            }
        });
    }

    private String obtenerNivelPrioridad(int nivel) {
        switch (nivel) {
            case 0:
                return "u";
            case 1:
                return "a";
            case 2:
                return "m";
            case 3:
                return "d";
            default:
                return "d";
        }
    }

    private String obtenerTextoPrioridad(int nivel) {
        switch (nivel) {
            case 0:
                return "Urgente";
            case 1:
                return "Alta";
            case 2:
                return "Media";
            case 3:
                return "Moderada";
            default:
                return "Moderada";
        }
    }

}
