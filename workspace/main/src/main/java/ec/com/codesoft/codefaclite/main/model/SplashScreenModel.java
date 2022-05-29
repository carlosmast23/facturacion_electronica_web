/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.main.panel.SplashScreenFrame;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class SplashScreenModel extends SplashScreenFrame implements Runnable {

    private Integer porcentajeCarga = 0;
    private Thread hiloPorcentaje;
    private List<DataPorcentaje> listPorcentajes;
    private Integer etapa;
    private boolean fin;
    private DataPorcentaje datoPorcentajeAnterior=null;

    public SplashScreenModel() {
        listPorcentajes = new ArrayList<DataPorcentaje>();
        //agregarPorcentaje(0,"Iniciando todos ");
        this.etapa = 0;
        
        Image fondoImg = new javax.swing.ImageIcon(getClass().getResource("/img/general/fondoInicial.jpg")).getImage();
        getPanelContenedor().setBorder(new Fondo2(fondoImg));
        //setBorder(new Fondo(fondoImg));
        
        //setear icono
        setIconImage(ParametrosSistemaCodefac.iconoSistema);
    }

    public void agregarPorcentaje(Integer porcentaje, String mensaje) {
        DataPorcentaje data = new DataPorcentaje();
        data.porcentaje = porcentaje;
        data.mensaje = mensaje;
        listPorcentajes.add(data);
        this.fin=false;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(20);
                DataPorcentaje datoPorcentaje = getDato();
                /*
                if(!datoPorcentajeAnterior.equals(datoPorcentaje) || )
                {
                    porcentajeCarga=datoPorcentaje.porcentaje;
                    datoPorcentajeAnterior=datoPorcentaje;
                }*/
                
                if (datoPorcentaje != null) {
                    if (porcentajeCarga > datoPorcentaje.porcentaje) {
                        //datoPorcentaje.porcentaje;
                    } else {
                        getjBarraProgreso().setValue(porcentajeCarga++);
                        getLblEtiquetas().setText(datoPorcentaje.mensaje+" ...");
                    }
                } else {
                    if(porcentajeCarga<=100)
                    {
                        getjBarraProgreso().setValue(porcentajeCarga++);
                    }
                    else
                    {
                        if(fin)
                            break;
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(SplashScreenModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class DataPorcentaje {

        public Integer porcentaje;
        public String mensaje;
    }

    public void siguiente() {
        this.etapa++;
        if(this.listPorcentajes.get(etapa-1)!=null)
        {
            //if(this.porcentajeCarga<this.listPorcentajes.get(etapa).porcentaje)
            //{
                this.porcentajeCarga=this.listPorcentajes.get(etapa-1).porcentaje;
            //}
        }
    }

    public void iniciar() {
        hiloPorcentaje = new Thread(this);
        hiloPorcentaje.start();
    }

    public DataPorcentaje getDato() {
        if (etapa<listPorcentajes.size()) {
            
            if(datoPorcentajeAnterior==null)
            {
                datoPorcentajeAnterior=listPorcentajes.get(etapa);
            }
            
            return listPorcentajes.get(etapa);
        } else {
            return null;
        }
    }
    
    public void termino()
    {
        this.fin=true;
        this.dispose();
    }
}
