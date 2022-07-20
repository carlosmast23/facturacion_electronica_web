/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.hora;

import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @auhor
 */
public class UtilidadesHora 
{
    
    public static Time horaActual() 
    {
        java.util.Date hora = new java.util.Date();
        return new Time(hora.getTime());
    }
    
    /*public static aaa()
    {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    }*/
    
    public static boolean comprobarHoraEnRangoDeTiempo(Time horaInicial, Time horaFinal, Time hora)
    {
        java.sql.Date horaInicialAux = UtilidadesFecha.FechaHoraPorUnion(transformarTimeSqlToDateSql(hora), transformarTimeSqlToDateSql(horaInicial));
        java.sql.Date horaFinalAux = UtilidadesFecha.FechaHoraPorUnion(transformarTimeSqlToDateSql(hora), transformarTimeSqlToDateSql(horaFinal));
        java.sql.Date horaAux = transformarTimeSqlToDateSql(hora);
        
        System.out.println("1.- "+horaAux);
        System.out.println("2.- "+horaInicialAux);
        System.out.println("3.- "+horaFinalAux);
               
        return horaAux.after(horaInicialAux) && horaAux.before(horaFinalAux);
    }
    
    public static java.sql.Date transformarTimeSqlToDateSql(Time hora)
    {
        return new java.sql.Date(hora.getTime());
    }
    
}
