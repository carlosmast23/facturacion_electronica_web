/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.pos;

import ec.com.codesoft.codefaclite.servidor.facade.pos.ArqueoCajaFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.ArqueoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.ArqueoCajaServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Robert
 */
public class ArqueoCajaService extends ServiceAbstract<ArqueoCaja, ArqueoCajaFacade> implements ArqueoCajaServiceIf
{
    
    private ArqueoCajaFacade arqueoCajaFacade;
    
    public ArqueoCajaService() throws RemoteException {
        super(ArqueoCajaFacade.class);
        this.arqueoCajaFacade = new ArqueoCajaFacade();
    }

    @Override
    public List<ArqueoCaja> obtenerArqueoCajaPorCaja(Caja caja) throws RemoteException {
        Map<String, Object> map = new HashMap<>();        
        map.put("cajaSession.caja", caja);
        
        List<ArqueoCaja> arqueoCajas = getFacade().findByMap(map);
        
        if(arqueoCajas.size() > 0)
        {
            return arqueoCajas;
        }
        
        return null;
    }
    
}
