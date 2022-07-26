
import com.sun.imageio.plugins.common.BogusColorSpace;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import org.apache.commons.net.ntp.TimeStamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author
 */
public class TestVarios {
    public static void main(String[] args) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        TimeStamp otraFecha=new TimeStamp(UtilidadesFecha.getPrimerDiaMes(2019,0));
        System.out.println(formatter.format(otraFecha.getDate()));
    }
    
    public static Integer funcion1()
    {
        return funcion2();
    }
    
    public static Integer funcion2()
    {
        return null;
    }
}
