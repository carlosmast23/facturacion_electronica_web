/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.NacionalidadFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Nacionalidad;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NacionalidadServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CodesoftDesarrollo
 */
public class NacionalidadService extends ServiceAbstract<Nacionalidad, NacionalidadFacade> implements NacionalidadServiceIf {

    NacionalidadFacade nacionalidadFacade;

    public NacionalidadService() throws RemoteException {
        super(NacionalidadFacade.class);
        nacionalidadFacade = new NacionalidadFacade();
    }

    @Override
    public Nacionalidad obtenerDefaultEcuador() throws ServicioCodefacException, RemoteException {
        Map<String,Object> mapBusqueda=new HashMap<String, Object>();
        mapBusqueda.put("iso",Nacionalidad.ISO_NACIONALIDAD_DEFECTO);
        List<Nacionalidad> nacionalidades=obtenerPorMap(mapBusqueda);
        if(nacionalidades.size()>0)
        {
            return nacionalidades.get(0);
        }
        return null;
        
    }
    
     

}
