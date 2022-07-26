/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.varios;

import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadValidador;
import static ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadValidador.normalizarTexto;

/**
 *
 * @author
 */
public class UtilidadesDerby {
    
    /**
     * Metodo estandar que va a usar la base de datos derby para buscar sin importar acentos ni letras especiales y convirtiendo todo a minusculas
     * @param texto
     * @return 
     */
    public static String normalizarTextoDerby(String texto)
    {
        texto=texto.toLowerCase(); //Transformar a minusculas para buscar de una forma estandar
        return UtilidadValidador.normalizarTexto(texto);
    }
}
