
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carlos
 */
public class TestUtilidades {
    public static void main(String[] args) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            
            Date fecha1=format.parse("2019-02-05");
            Date fecha2=format.parse("2019-02-05");
            
            int distancia=UtilidadesFecha.obtenerDistanciaDias(fecha1, fecha2);
            System.out.println(distancia);
                    } catch (ParseException ex) {
            Logger.getLogger(TestUtilidades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
