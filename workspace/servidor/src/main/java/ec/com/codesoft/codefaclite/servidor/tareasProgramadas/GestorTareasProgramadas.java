/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.tareasProgramadas;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @auhor
 */
public class GestorTareasProgramadas 
{
    
    private static GestorTareasProgramadas instance;
    /**
     * Gestor principal que permite ejecutar tareas programadas
     */
    private ScheduledExecutorService executor;
    
    private List<Runnable> tareasList;

    public GestorTareasProgramadas() {
        this.executor=Executors.newScheduledThreadPool(1);
        this.tareasList=new ArrayList<Runnable>();
    }
    
    /**
     * TODO: Ver si necesito 
     * @param tarea
     * @param periodoInicialEspera
     * @param periodo
     * @param unidadMedida 
     */
    public void agregarTareaProgramada(Runnable tarea,Long periodoInicialEspera,Long periodo,TimeUnit unidadMedida)
    {
        this.tareasList.add(tarea);
        executor.scheduleAtFixedRate(tarea, periodoInicialEspera, periodo, unidadMedida);
    }
    
    public void agregarTareaProgramadaPorDia(Runnable tarea,Integer hora,Integer minutos)
    {
        LocalDateTime horaActual=LocalDateTime.now();
        LocalDateTime horaComparacion=horaActual.withHour(hora).withMinute(minutos).withSecond(0);
        Duration duracion=Duration.between(horaActual,horaComparacion);
        
        long restrasoInicial = duracion.getSeconds();
        
        agregarTareaProgramada(
                tarea, 
                restrasoInicial, 
                TimeUnit.DAYS.toSeconds(1), //Cantidad de segundos que tiene un día para que se repita de forma perioda por dia
                TimeUnit.SECONDS
        );
        
    }
    
    public static GestorTareasProgramadas getInstance()
    {
        if(instance==null)
        {
            instance=new GestorTareasProgramadas();
        }
        return instance;
    }
    
    
    /**
     * =========================================================================
     *                          GET AND SET
     * =========================================================================
     */
    public ScheduledExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ScheduledExecutorService executor) {
        this.executor = executor;
    }
    
}
