/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.SriRetencionFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 */
public class SriRetencionService extends ServiceAbstract<SriRetencion,SriRetencionFacade> implements SriRetencionServiceIf{

    public SriRetencionService() throws RemoteException {
        super(SriRetencionFacade.class);
    }
    
    public SriRetencion consultarPorNombre(String nombre) throws RemoteException
    {
       //SriRetencion sriRetencion=new SriRetencion();
       //sriRetencion.getNombre()
       Map<String,Object> mapParametros=new HashMap<String, Object>();
       mapParametros.put("nombre",nombre);
       
       List<SriRetencion> resultados=getFacade().findByMap(mapParametros);
       return resultados.get(0);
    }
    
}
