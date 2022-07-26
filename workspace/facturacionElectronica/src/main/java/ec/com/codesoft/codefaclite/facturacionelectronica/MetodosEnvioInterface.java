/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica;

import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 */
public interface MetodosEnvioInterface { 
    public abstract void enviarCorreo(String mensaje,String subject,List<String> destinatorios,Map<String,String> pathFiles) throws Exception;
    public abstract void cerrarSesion();
}
