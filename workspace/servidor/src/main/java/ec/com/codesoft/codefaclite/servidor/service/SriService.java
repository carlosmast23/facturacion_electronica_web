/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriIdentificacion;
import ec.com.codesoft.codefaclite.servidor.facade.SriFormaPagoFacade;
import ec.com.codesoft.codefaclite.servidor.facade.SriIdentificacionFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriServiceIf;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class SriService extends UnicastRemoteObject implements SriServiceIf
{
    private SriFormaPagoFacade sriFormaPagoFacade;
    private SriIdentificacionFacade sriIdentificacionFacade;

    public SriService() throws java.rmi.RemoteException {
        super(ParametrosSistemaCodefac.PUERTO_COMUNICACION_RED);
        this.sriFormaPagoFacade = new SriFormaPagoFacade();
        this.sriIdentificacionFacade = new SriIdentificacionFacade();
    }
    
    public SriFormaPago obtenerFormarPagoDefecto() throws java.rmi.RemoteException
    {
        //Todo:Cambiar por algun parametro del sistema para que sepa cual forma de pago buscar
        String codigoFormaPago="01";
        
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("codigo",codigoFormaPago);
        
        List<SriFormaPago> formasPago=sriFormaPagoFacade.findByMap(mapParametros);
        if(formasPago.size()>0)
        {
            return formasPago.get(0);
        }
        return null;
    }
    
    public SriFormaPago obtenerFormarPagoConCartera() throws java.rmi.RemoteException
    {
    //Todo:Cambiar por algun parametro del sistema para que sepa cual forma de pago buscar
        String aliasFormaPago="Cartera";
        
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("alias",aliasFormaPago);
        
        List<SriFormaPago> formasPago=sriFormaPagoFacade.findByMap(mapParametros);
        if(formasPago.size()>0)
        {
            return formasPago.get(0);
        }
        return null;
    }
    
    public List<SriFormaPago> obtenerFormasPagoActivo() throws java.rmi.RemoteException
    {
        java.util.Date fechaActual=new java.util.Date();
        
        //return sriFormaPagoFacade.getFormaPagoByDate(new Date(fechaActual.getTime()));
        return sriFormaPagoFacade.findAll();
    }
    
    /**
     * Obtiene el tipo de identificacion segun el tipo de proveedor
     * Los valor por defecto se encuentro en la tabla SriIdentificacion
     * SriIdentificacion.CLIENTE
     * SriIdentificacion.PROVEEDOR
     * @param tipo
     * @return 
     */
    public List<SriIdentificacion> obtenerIdentificaciones(String tipo) throws java.rmi.RemoteException
    {

        return sriIdentificacionFacade.getSriIdentificacionByTipoTransaccion(tipo);
    }
    
}

