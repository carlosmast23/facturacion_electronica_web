/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.test;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoEnsamble;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 *
 * @author
 */
public class CreateInterface {
    public static void main(String[] args) {
        imprimir(ProductoEnsamble.class);
                
    }
    
    public static void imprimir(Class clase)
    {
        System.out.println("public interface "+clase.getSimpleName()+"{");
        Method[] metodos=clase.getDeclaredMethods();
                //clase.getDeclaredMethods();
        for (Method metodo : metodos) {
            String metodoRetorno=metodo.getReturnType().getSimpleName();
            Parameter[] parametros= metodo.getParameters();
            String parametrosStr="";
            for (Parameter parametro : parametros) {
                parametrosStr+=parametro.getName();
            }
            
            System.out.println("public "+metodoRetorno+" "+metodo.getName()+"("+parametrosStr+")");
        }
    }
}
