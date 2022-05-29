/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.ActualizarSistemaFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ActualizarSistema;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ActualizarSistemaServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ActualizarSistemaService extends ServiceAbstract<ActualizarSistema,ActualizarSistemaFacade> implements ActualizarSistemaServiceIf{

    public ActualizarSistemaService() throws RemoteException {
        super(ActualizarSistemaFacade.class);
    }

    public List<ActualizarSistema> obtenerCambiosPendientes() throws RemoteException
    {
        try {
            return (List<ActualizarSistema>) ejecutarConsulta(new MetodoInterfaceConsulta() {
                @Override
                public Object consulta() throws ServicioCodefacException, RemoteException {
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("cambioActualizado", EnumSiNo.NO.getLetra());
                    return getFacade().findByMap(mapParametros);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ActualizarSistemaService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /*public void actualizarEstado(EnumSiNo enumSiNo,ActualizarSistema entidad) throws RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("cambioActualizado", enumSiNo.getLetra());
        return getFacade().findByMap(mapParametros);
    }*/
    
}
