
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesHash;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Carlos
 */
public class TestHash {
    public static void main(String[] args) {
        String clave="123";
        String hash=UtilidadesHash.generarHashBcrypt(clave);
        
        System.out.println(hash);
        System.out.println(UtilidadesHash.verificarHashBcrypt("123","$2a$12$meSmp.deopaRI9UYSHdoIu4lYDa40cTKipNqR/Wjdeq4L93.jTmpm"));
    }
}
