/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CARLOS_CODESOFT
 */
public class TestReferencia {
    
    public static void main(String[] args) {
        Ejemplo dato=new Ejemplo(2,"a");
        
        cambiarDato(dato);
        System.out.println(dato.numero+" "+dato.nombre);
    }
    
    public static void cambiarDato(Ejemplo dato)
    {
        
    }
       
    
    
    public static class Ejemplo {

        public Integer numero;
        public String nombre;

        public Ejemplo(Integer numero,String nombre) {
            this.numero = numero;
            this.nombre = nombre;
        }

                
        
    }

}
    
    



