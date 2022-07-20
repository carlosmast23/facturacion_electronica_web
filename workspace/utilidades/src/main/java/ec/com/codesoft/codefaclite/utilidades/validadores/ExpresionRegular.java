/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.validadores;

/**
 * TODO: Convertir Esta clase en Enum y cambiar de nombre por que realmente solo es una clase para almacenar datos
 * @auhor
 */
public final class ExpresionRegular 
{
    public static final String textoSinSaltosLinea="[^\\n]*";
    /**
     * Texto simple 
     * Ejm: Texto simple que tiene caracteres como - guion, . punto, _línea baja y , coma
    */
    public static final String textoSimpleSinNumeros = "^[A-Za-zÀ-ÿ\\u00f1\\u00d1\\s.\\_\\-\\,\\ ]*$";
    /**
     * Texto simple 
     * Ejm: Texto simple que tiene caracteres como - guion, . punto, _línea baja y , coma
    */
    public static final String textoSimple = "^[A-Za-zÀ-ÿ0-9\\u00f1\\u00d1\\s.\\_\\-\\,\\ ]*$";
    
    /**
     * Texto simple 
     * Ejm: Texto simple que permite todo lo que tiene su primera opcion menos coma
    */
    public static final String textoSimple2 ="^[A-Za-zÀ-ÿ0-9\\u00f1\\u00d1\\s.\\_\\-\\ ]*$";
    
    /**
     * Número celular de 10 digitos
     * Ejm: 0697426212
    */
    public static final String telefonoCelular = "^[0][0-9]{9}$";
    
    /**
     * Formato estandar de correo electrónico
     * Ejm: correo@algo.ecu
    */
    public static final String email = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@[a-z0-9-]+(.[a-z0-9-]+)*(.[a-z]{2,4})$";
    
    /**
     * Solo numeros positivos 
     * Ejm: 972
    */
    public static final String soloNumeros = "^[0-9]*$";
    
    /**
     * Validacion para numeros con decimales
     */
    public static final String NUMEROS_DECIMALES="^[0-9]+([.][0-9]+)?$";
    
    /**
     * Número de teléfono convencional anteponiendo el código provincial
     * Ejm: 022625072 
    */
    public static final String telefonoConvencional = "^[0][0-9]{8}$";
    
    /**
     * Texto Simple que permite la validacion de un nombre de razón social sin caracteres especiales
     * Ejm: Codesoft Desarrollo 1
    */
    public static final String nombreSocial = "^[A-Za-z0-9À-ÿ\\u00f1\\u00d1\\s]*$";
    
    /**
     * Número que permite validar el ingreso de numeros enteros para precios
     * Ejm: 98.89 - 9 - 100.1
     */
    public static final String numerosRealesPositivos = "^[0-9]+([.][0-9]+)?$"; 
    
    /**
     * Número 
     * Ejm: 15, 1, 100
    */
    public static final String soloNumeros2 = "^[0-9]+$";

}
