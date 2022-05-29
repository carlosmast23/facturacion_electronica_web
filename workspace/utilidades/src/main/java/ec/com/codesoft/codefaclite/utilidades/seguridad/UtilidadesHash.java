/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.seguridad;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Carlos
 */
public class UtilidadesHash {
    
    public static boolean verificarHashBcrypt(String texto,String codigoHash)
    {
        return BCrypt.checkpw(texto, codigoHash);
    }
    
    public static String generarHashBcrypt(String texto)
    {
        return BCrypt.hashpw(texto,BCrypt.gensalt(12));
    }
}
