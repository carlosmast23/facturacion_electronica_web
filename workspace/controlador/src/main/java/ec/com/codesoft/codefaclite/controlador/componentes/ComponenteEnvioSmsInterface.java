/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.componentes;

import ec.com.codesoft.codefaclite.corecodefaclite.ayuda.componentes.ComponenteEnvioSmsData;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.EnvioMensajesCallBackInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.PlantillaSmsEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public interface ComponenteEnvioSmsInterface {
    
    public List<ComponenteEnvioSmsData> getDataSms();
    public abstract boolean getValidacionEnvioSms();
    public abstract VentanaEnum getVentanaEnum();
    /**
     * Interfaz que envia el metodo para mostrar el estado del proceso en pantalla si manda null, solo se ejcuta el proceso por detras
     * @return 
     */
    public abstract EnvioMensajesCallBackInterface getInterfaceCallback();
    
}
