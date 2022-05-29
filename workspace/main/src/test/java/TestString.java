/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TestString {
    public static void main(String[] args)
    {
        Integer numero=15;
        String numeroStr=(numero.toString().length()==1)?"0"+numero:numero.toString();
        System.out.println(numeroStr);
    }
}
