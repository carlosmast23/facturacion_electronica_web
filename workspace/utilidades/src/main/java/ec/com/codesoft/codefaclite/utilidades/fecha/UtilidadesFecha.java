/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.fecha;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author
 */
public class UtilidadesFecha {
    
    public static java.util.Date getPrimerDiaMes(int anio, int mes)
    {
        Calendar calendario = Calendar.getInstance();
        calendario.set(anio,mes, 1);
        return calendario.getTime();
    }
    
    public static java.util.Date getUltimoDiaMes(int anio, int mes)
    {
        Calendar calendario = Calendar.getInstance();
        calendario.set(anio,mes, 1);
        calendario.set(anio, mes, calendario.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendario.getTime();
    }
    
    
    public static java.util.Date getFechaNTP() throws Exception
    {
        //Generamos un objeto de la clase ObtenerFecha. 
        ObtenerFecha objFecha=new ObtenerFecha(); 
        //Generamos otro objeto de la clase SimpleDateFormat para darle formato a la fecha 
 
        return objFecha.getNTPDate(); 
    }
   
    public static java.sql.Date getFechaHoy() {
        java.util.Date fechaHoy = new java.util.Date();
        return new java.sql.Date(fechaHoy.getTime());
    }
    
    public static Timestamp getFechaHoyTimeStamp()
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
    }

    public static java.util.Date fechaFinMes(java.util.Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));

        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
    }

    public static java.util.Date fechaInicioMes(java.util.Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));

        return calendar.getTime(); // Devuelve el objeto Date con los nuevos días añadidos
    }

    public static java.util.Date hoy() {
        java.util.Date fecha = new java.util.Date();
        return fecha;
    }

/**
 * etodo que obtiene dias de dias
 * @param fechaMenor pendiente
 * @param fechaMayor nada
 * @return dias
 */
    public static int obtenerDistanciaDias(java.util.Date fechaMenor, java.util.Date fechaMayor) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); //todo: Ver si estos dates format se hace un formato global
        //Formatear sin tiempo la fecha para evitar problemas en los calculos por las horas
        //fechaMenor=formato.parse(formato.format(fechaMenor)); //Revisar si no hay problema porque estoy quitando
        //fechaMayor=formato.parse(formato.format(fechaMayor)); //Revisar si no hay problema porque estoy quitando
        LocalDate fechaMenorLocalDate = LocalDate.parse(formato.format(fechaMenor), DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate fechaMayorLocalDate = LocalDate.parse(formato.format(fechaMayor), DateTimeFormatter.ISO_LOCAL_DATE);
        
        Long dias=ChronoUnit.DAYS.between(fechaMenorLocalDate, fechaMayorLocalDate);
                
        //int dias = (int) ((fechaMayor.getTime() - fechaMenor.getTime()) / 86400000);
        return dias.intValue();
    }
    
    public static int obtenerDistanciaConLaFechaActual(java.util.Date fecha)
    {
        return obtenerDistanciaDias(fecha,getFechaHoy());
    }

    public static String formatDate(java.util.Date fecha, String formato) {
        //yyyy MMMMM dd
        //yyyy-MM-dd
        DateFormat dateFormat = new SimpleDateFormat(formato);
        return dateFormat.format(fecha);
    }
    
    public static String formatDate(java.util.Date fecha, SimpleDateFormat simpleDateFormat) {
        //yyyy MMMMM dd
        //yyyy-MM-dd
        //DateFormat dateFormat = new SimpleDateFormat(formato);
        return simpleDateFormat.format(fecha);
    }
    
    public static String obtenerAnioStr(Date date) {

        if (null == date) {

            return "0";

        } else {

            String formato = "yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            return dateFormat.format(date);

        }

    }
    
    public static int obtenerAnio(Date date) {

        return Integer.parseInt(obtenerAnioStr(date));

    }
    
    public static String obtenerMesStr(Date date) {

        if (null == date) {

            return "0";

        } else {

            String formato = "MM";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            return dateFormat.format(date);

        }

    }

    public static int obtenerMes(Date date) {
        return Integer.parseInt(obtenerMesStr(date));
    }
    

    public static String obtenerDiaStr(Date date) {

        if (null == date) {

            return "0";

        } else {

            String formato = "dd";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
            return dateFormat.format(date);

        }

    }

    public static int obtenerDia(Date date) {
        return Integer.parseInt(obtenerDiaStr(date));

    }
    
    /**
     * Obtiene el dia con respecto a la semana
     * @param date
     * @return 
     */
    public static int obtenerDiaSemana(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Integer diaSemana=cal.get(Calendar.DAY_OF_WEEK);
        
        //Corregir problema que devuelve por deecto que el primer dia de la semana es el domingo
        if(diaSemana==1)
        {
            diaSemana=7;
        }else
        {
            diaSemana=diaSemana-1;
        }
        
        return diaSemana;

    }
    
    public static String formatoDiaMesAño(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }
    
    public static java.util.Date fechaProxima(java.util.Date date, int numero, String e)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch(e)
        {
            case "Dia":
                calendar.add(Calendar.DAY_OF_YEAR, numero);
                break;
            case "Mes":
                calendar.add(Calendar.MONTH, numero);
                break;
        };
        return calendar.getTime();
    }
    
    /**
     * @author: Carlos Sánchez 
     * @param date
     * @param dias
     * @return 
     */
    public static java.util.Date sumarDiasFecha(java.util.Date date, int dias)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,dias);
        return calendar.getTime();
    }
            
    public static java.util.Date sumarAniosFecha(java.util.Date date, int anios)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,anios);
        return calendar.getTime();
    }
    
    public static java.util.Date sumarMesesFecha(java.util.Date date, int meses)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,meses);
        return calendar.getTime();
    }

    public static java.sql.Date stringFormatXMLGregorianCalendarToDate(String fechaFormat)
    {
        if(fechaFormat==null || fechaFormat.isEmpty())
        {
            return null;
        }
        
        try {
            XMLGregorianCalendar result = DatatypeFactory.newInstance().newXMLGregorianCalendar(fechaFormat);
            java.sql.Date fechaAutorizacion = new java.sql.Date(result.toGregorianCalendar().getTime().getTime());
            return fechaAutorizacion;
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(UtilidadesFecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Date cambiarDiaFecha(Date fecha,Integer dia)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.set(Calendar.DAY_OF_MONTH,dia);

        return new java.sql.Date(calendar.getTime().getTime()); // Devuelve el objeto Date con los nuevos días añadidos
    }
    
    public static java.util.Date castDateSqlToUtil(java.sql.Date fechaSql)
    {
        if(fechaSql==null)
            return null;
            
        return new java.util.Date(fechaSql.getTime());
    }
    
    public static java.util.Date castTimeSqlToUtil(java.sql.Time horaSql)
    {
        if(horaSql==null)
            return null;
            
        return new java.util.Date(horaSql.getTime());
    }
    
    public static java.sql.Date castDateUtilToSql(java.util.Date fechaUtil)
    {
        if(fechaUtil==null)
            return null;
        
        return new java.sql.Date(fechaUtil.getTime());
    }
    
    public static java.util.Date castStringToDate(String fechaStr,SimpleDateFormat simpleDateFormat)
    {
        try {
            return simpleDateFormat.parse(fechaStr);
        } catch (ParseException ex) {
            Logger.getLogger(UtilidadesFecha.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Timestamp castDateSqlToTimeStampSql(Date date){
        if(date == null){
            return null;
        }
        return new Timestamp(date.getTime());
    }
    
    public static Timestamp castDateSqlToTimeStampSql(java.util.Date date){
        if(date == null){
            return null;
        }
        return new Timestamp(date.getTime());
    }
    
    public static Date FechaHoraPorUnion(Date date, Date time){
        //Date tipo SQL
        GregorianCalendar dateCal = new GregorianCalendar();
        GregorianCalendar timeCal = new GregorianCalendar();    
        dateCal.setTime(date);
        timeCal.setTime(time);
        
        int año = dateCal.get(Calendar.YEAR);
        int mes = dateCal.get(Calendar.MONTH);
        int dia = dateCal.get(Calendar.DAY_OF_MONTH);

        int hora = timeCal.get(Calendar.HOUR_OF_DAY);
        int minutos = timeCal.get(Calendar.MINUTE);
        int segundos = timeCal.get(Calendar.SECOND);
        
        GregorianCalendar dateTime = new GregorianCalendar(año, mes, dia, hora, minutos, segundos);
        java.util.Date dateTimeUitl = dateTime.getTime();
        return UtilidadesFecha.castDateUtilToSql(dateTimeUitl);
    }
    
    public static Date getFechaDeTimeStamp(Timestamp date)
    {
        
        GregorianCalendar dateCal = new GregorianCalendar();
        dateCal.setTime(date);
        
        int año = dateCal.get(Calendar.YEAR);
        int mes = dateCal.get(Calendar.MONTH);
        int dia = dateCal.get(Calendar.DAY_OF_MONTH);
        
        GregorianCalendar onlyDate = new GregorianCalendar(año, mes, dia);
        java.util.Date dateUtil = onlyDate.getTime();
        
        return UtilidadesFecha.castDateUtilToSql(dateUtil);
    }
    
    public static Date getHoraDeTimeStamp(Timestamp date)
    {
        GregorianCalendar timeCal = new GregorianCalendar();
        timeCal.setTime(date);
        
        int horas = timeCal.get(Calendar.HOUR_OF_DAY);
        int minutos = timeCal.get(Calendar.MINUTE);
        int segundos = timeCal.get(Calendar.SECOND);
        
        GregorianCalendar onlyTime = new GregorianCalendar(0,0,0,horas,minutos,segundos);
        java.util.Date dateUtil = onlyTime.getTime();
        
        return UtilidadesFecha.castDateUtilToSql(dateUtil);
    }
    
    public static Timestamp castDateToTimeStamp(java.sql.Date fecha)
    {
        return new Timestamp(fecha.getTime());
    }
    
    public static Timestamp castDateToTimeStamp(java.util.Date fecha)
    {
        return new Timestamp(fecha.getTime());
    }
    
    /**
     * Metodo que me permite verificar si una fecha esta dentro de una cantidad de dias previos
     * @return 
     */
    public static Boolean validarfechaDentroDeRango(Timestamp fechaEmision,Integer diasTolerancia)
    {
        int distanciaDias=obtenerDistanciaConLaFechaActual(fechaEmision);
        if(distanciaDias>diasTolerancia)
        {
            return false;
        }
        return true;
    
    }
    
    public static int obtenerAnioActual()
    {
        return obtenerAnio(getFechaHoy());
    }

}
