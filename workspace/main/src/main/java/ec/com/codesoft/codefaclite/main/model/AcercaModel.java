/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.main.panel.AcercaDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.web.UtilidadesWeb;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author
 */
public class AcercaModel extends AcercaDialog{
    
    private static AcercaModel objStatic;    
    
    public static AcercaModel getInstance()
    {
        if(objStatic==null)
        {
            objStatic=new AcercaModel(null, true);
        }
        return objStatic;
        
    }
    
    public AcercaModel(Frame parent, boolean modal) {
        super(parent, modal);
        addListener();
        cargarValoresIniciales();
    }

    private void addListener() {
        listenerLabels();
    }

    private void listenerLabels() {
        getLblPaginaDesarrolladores().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UtilidadesWeb.abrirPaginaWebNavigador("http://www.codesoft-ec.com");
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        getLblPaginaOficial().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UtilidadesWeb.abrirPaginaWebNavigador("http://www.vm.codesoft-ec.com/");
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
    }
    
    public void setLicencia(String licencia)
    {
        getLblLicencia().setText(licencia);
    }
    
    public void setUsuario(String usuario)
    {
        getLblUsuario().setText(usuario);
    }

    private void cargarValoresIniciales() {
        getLblVersion().setText("Codefac Lite "+ParametrosSistemaCodefac.VERSION);
        String anioActualStr=UtilidadesFecha.obtenerAnioStr(UtilidadesFecha.getFechaHoy());
        getLblPiePagina().setText("Codefac @ 2010 - "+anioActualStr+" Codesoft");
    }
    
}
