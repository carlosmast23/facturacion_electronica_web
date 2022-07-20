/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.other;

import ec.com.codesoft.codefaclite.main.init.Main;
import ec.com.codesoft.codefaclite.servidor.tareasProgramadas.GestorTareasProgramadas;
import ec.com.codesoft.codefaclite.servidor.tareasProgramadas.RespaldoProgramadoTarea;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase donde confinguro todos los metodos relacionados con las tareas programadas
 * para no tener mucho codigo en la clase del main
 * @auhor
 */
public class TareasProgramadasCodefac {
    
    private static TareasProgramadasCodefac instance;
    
    private GestorTareasProgramadas gestorTareas;

    private TareasProgramadasCodefac() {
        this.gestorTareas = GestorTareasProgramadas.getInstance();
    }
    
    public void iniciar()
    {
        agregarTareaRespaldoProgramado(gestorTareas);
        Logger.getLogger(Main.class.getName()).log(Level.INFO,"Iniciando gestor de TAREAS PROGRAMADAS ...");
    }
    
    /**
     * TODO: Ver mejor si esta parte debe ir incluido en la clase de GestorTareasProgrmadas
     * @param gestorTareas 
     */
    private static void agregarTareaRespaldoProgramado(GestorTareasProgramadas gestorTareas)    
    {
        try {
            //Primero verificar que tiene el PARAMETRO DE CONFIGURACION para ver si puedo activar el servicio de respaldo automatico
            String valor=ParametroUtilidades.obtenerValorParametroSinEmpresa(ParametroCodefac.ParametrosRespaldoDB.DB_RESPALDO_HORA_PROGRAMADA);
            if(valor!=null && !valor.trim().isEmpty())
            {
                Integer horaInicio=Integer.parseInt(valor);
                if(horaInicio>0)
                {
                    //Iniciar el respaldo programado de aplicaciones a la hora exacta con 0 minutos
                    gestorTareas.agregarTareaProgramadaPorDia(new RespaldoProgramadoTarea(),horaInicio,0);
                    Logger.getLogger(TareasProgramadasCodefac.class.getName()).log(Level.INFO,"Agregada RESPALDOS PROGRAMADOS DIARIOS al gestor de TAREAS PROGRAMADAS a las "+horaInicio);
                }
            }
            
        } catch (Exception e)
        {
            Logger.getLogger(TareasProgramadasCodefac.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
        

    public GestorTareasProgramadas getGestorTareas() {
        return gestorTareas;
    }

    public void setGestorTareas(GestorTareasProgramadas gestorTareas) {
        this.gestorTareas = gestorTareas;
    }
    
    public static TareasProgramadasCodefac getInstance()
    {
        if(instance==null)
        {
            instance=new TareasProgramadasCodefac();
        }
        return instance;
    }
    
    
}
