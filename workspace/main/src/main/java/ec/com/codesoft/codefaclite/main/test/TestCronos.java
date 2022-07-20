package ec.com.codesoft.codefaclite.main.test;


import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javafx.util.converter.LocalDateTimeStringConverter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @auhor
 */
public class TestCronos implements Serializable {

    public static void main(String args[]) {
        //ZonedDateTime now=ZonedDateTime.now();
        //ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/Los_Angeles"));
        //ZonedDateTime nextRun = now.withHour(9).withMinute(22).withSecond(0);
        //if (now.compareTo(nextRun) > 0) {
        //    nextRun = nextRun.plusDays(1);
        //}

        LocalDateTime horaActual=LocalDateTime.now();
        LocalDateTime horaComparacion=horaActual.withHour(10).withMinute(0).withSecond(0);
        Duration duracion=Duration.between(horaActual,horaComparacion);
        
        //Duration duration = Duration.between(now, nextRun);
        long initalDelay = duracion.getSeconds();
        System.out.println("Minutos diferencia: "+duracion.getSeconds()/60);
        
        /**
         * =====================================================================
         *                  CODIGO DE EJEMPLO PROBADO
         * =====================================================================
         */
        
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Task task1 = new Task ("Ejemplo tarea 1");
         
        System.out.println("The time is : " + new Date());
         
        /*executor.scheduleAtFixedRate(
                task1,
                initalDelay,
                TimeUnit.DAYS.toSeconds(1), //Esto sirve para obtener cuantos segundos corresponden a un día entero
                TimeUnit.SECONDS);*/
        executor.scheduleAtFixedRate(task1, 1, 2, TimeUnit.MINUTES);
        //ScheduledFuture<?> result = executor.scheduleAtFixedRate(task1, 2, 5, TimeUnit.SECONDS);
         
        /*try {
            TimeUnit.MILLISECONDS.sleep(20000);
        } 
        catch (InterruptedException e) {
            e.printStackTrace();
        }*/
         
        //executor.shutdown();
        
        /*Timer timer;
        timer = new Timer();

        TimerTask task = new TimerTask() {
            int tic = 0;

            @Override
            public void run() {
                if (tic % 2 == 0) {
                    System.out.println("TIC");
                } else {
                    System.out.println("TOC");
                }
                tic++;
            }
        };
        // Empezamos dentro de 10ms y luego lanzamos la tarea cada 1000ms
        timer.schedule(task, 10, 1000);*/
        
        System.out.println("El tiempo de fin es : " + new Date());
        

    }
    
    static class Task implements Runnable
    {
        private String name;

        public Task(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public void run() 
        {
            try {
                System.out.println("Doing a task during : " + name + " - Time - " + new Date());
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
