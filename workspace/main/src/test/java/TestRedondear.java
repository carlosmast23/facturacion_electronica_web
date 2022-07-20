
import java.math.BigDecimal;
import java.math.RoundingMode;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @auhor
 */
public class TestRedondear {
    public static void main(String args[])
    {
        //String numeroStr="19.8747600000";
        String numeroStr="19.8749";
        BigDecimal numeroBigDecimal=new BigDecimal(numeroStr);
        
        System.out.println(numeroBigDecimal.setScale(2, RoundingMode.HALF_UP));
        System.out.println(numeroBigDecimal.setScale(2, RoundingMode.UP));
        System.out.println(numeroBigDecimal.setScale(2, RoundingMode.CEILING));
        System.out.println(numeroBigDecimal.setScale(2, RoundingMode.DOWN));
        System.out.println(numeroBigDecimal.setScale(2, RoundingMode.FLOOR));
        System.out.println(numeroBigDecimal.setScale(2, RoundingMode.HALF_DOWN));
        System.out.println(numeroBigDecimal.setScale(2, RoundingMode.HALF_EVEN));        
        //System.out.println(numeroBigDecimal.setScale(2, RoundingMode.UNNECESSARY));
    }
    
}
