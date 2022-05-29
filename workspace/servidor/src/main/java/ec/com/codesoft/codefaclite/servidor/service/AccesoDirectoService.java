/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.AccesoDirecto;
import ec.com.codesoft.codefaclite.servidor.facade.AccesoDirectoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AccesoDirectoServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class AccesoDirectoService extends ServiceAbstract<AccesoDirecto,AccesoDirectoFacade> implements AccesoDirectoServiceIf{
    
    public AccesoDirectoService() throws RemoteException {
        super(AccesoDirectoFacade.class);
    }
    
    
    public AccesoDirecto buscarPorNombre(String nombre)  throws RemoteException ,ServicioCodefacException
    {
        Map<String,Object> mapBuscar=new HashMap<String, Object>();
        mapBuscar.put("nombre",nombre);
        List<AccesoDirecto> resultados= getFacade().findByMap(mapBuscar);
        if(resultados.size()>0)
        {
            return resultados.get(0);
        }
        return null;
    }
    
}
