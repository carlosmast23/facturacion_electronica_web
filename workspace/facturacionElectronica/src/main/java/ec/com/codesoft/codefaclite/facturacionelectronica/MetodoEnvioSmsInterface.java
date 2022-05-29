/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public interface MetodoEnvioSmsInterface {
    public abstract void enviarMensaje(List<String> numeros,String mensaje) throws Exception;
}
