/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servicios.ServidorSMS;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.EnvioMensajesCallBackInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SmsServiceIf;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class SmsService extends UnicastRemoteObject implements SmsServiceIf{ 

    public SmsService() throws RemoteException {
        super(ParametrosSistemaCodefac.PUERTO_COMUNICACION_RED);
    }
    
    public void enviarMensajes(Map<String,String> mensajesMap,EnvioMensajesCallBackInterface callback) throws RemoteException,ServicioCodefacException
    {
        int totalMensajes=mensajesMap.size();
        int numeroMensaje=1;
                
        for (Map.Entry<String, String> entry : mensajesMap.entrySet()) {
            try {
                String numero = entry.getKey();
                String mensaje = entry.getValue();
                
                enviarMensaje(numero, mensaje);
                
                if(mensajesMap.size()>0)
                    Thread.sleep(2000); //  Esperar para enviar cada 2 segundos los mensajes TODO: Esta opcion debe ser personalizada
                
                //Enviar el porcentaje del total hasta el momento avanzado
                if(callback!=null)
                {
                    double porcentajeUnitario=(double)numeroMensaje++/(double)totalMensajes;
                    callback.procesando((int)porcentajeUnitario*100);
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(SmsService.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public void enviarMensaje(String numero , String mensaje)throws RemoteException,ServicioCodefacException
    {
        ServidorSMS servidorsms=ServidorSMS.getInstance();
        mensaje=UtilidadesTextos.quitaDiacriticos(mensaje); //Funcion que permite quitar acentuasiones y simbolo especiales que pueden generar problemas
        
        //Si no se puede enviar el mensaje manda una excecion al cliente
        if(!servidorsms.enviarMensaje(numero, mensaje))
        {            
            throw new ServicioCodefacException("No se puedo enviar el mensaje, ocurrio un error con el servidor de mensajeria");
        }
    }
    
    public boolean isServicioDisponible()throws RemoteException
    {
        ServidorSMS servidorsms=ServidorSMS.getInstance();
        return servidorsms.servicioDisponible();
    }
    
}
