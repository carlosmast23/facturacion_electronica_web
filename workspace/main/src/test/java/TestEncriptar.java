
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;
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
public class TestEncriptar {
    public static void main(String[] args) {
        try {
            //String datosEncriptado=UtilidadesEncriptar.encriptar("2020-09-01",ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
            //System.out.println(datosEncriptado);
            //System.out.println(UtilidadesEncriptar.desencriptar("5gdOfWztXqM\\=", ParametrosSistemaCodefac.LLAVE_ENCRIPTAR));
            //String datosSinEncriptar=UtilidadesEncriptar.desencriptar("5gdOfWztXqM\\=", ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
            String datosSinEncriptar=UtilidadesEncriptar.desencriptar("mdRHVgtT+rAqYNnKIxEPiA==", ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
            System.out.println(datosSinEncriptar);
        } catch (Exception ex) {
            Logger.getLogger(TestEncriptar.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
}
