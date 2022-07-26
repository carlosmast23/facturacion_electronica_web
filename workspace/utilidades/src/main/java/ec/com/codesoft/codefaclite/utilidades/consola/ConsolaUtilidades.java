/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.consola;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author
 */
public abstract class ConsolaUtilidades {
    
    /**
     * Permite ejecutar un proceso en la consola 
     * @param comando
     * @throws IOException 
     */
    public static void ejecutarProcesoConsola(List<String> comando) throws IOException
    {
        ProcessBuilder pb = new ProcessBuilder()
                .command(comando);
        pb.command();
        Process p = pb.start();
    }
    
    
    
}
