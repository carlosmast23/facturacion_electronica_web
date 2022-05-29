/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.cartera;

import ec.com.codesoft.codefaclite.servidor.facade.cartera.CarteraCruceFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraCruce;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraCruceServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class CarteraCruceService extends ServiceAbstract<CarteraCruce,CarteraCruceFacade> implements CarteraCruceServiceIf
{

    public CarteraCruceService() throws RemoteException {
        super(CarteraCruceFacade.class);
    }
    
    public List<CarteraCruce>  buscarPorCarteraDetalle(CarteraDetalle carteraDetalle) throws ServicioCodefacException, java.rmi.RemoteException
    {
        Map<String, Object> parametros = new HashMap<String, Object>();
        //if(cartera.getTipoCarteraEnum().equals(tipoCarteraEnum.CL))
        //{
        parametros.put("carteraDetalle", carteraDetalle);
        //}
        return getFacade().findByMap(parametros);
    }
    
    public List<CarteraCruce>  buscarPorCarteraAfecta(Cartera carteraAfecta) throws ServicioCodefacException, java.rmi.RemoteException
    {
        Map<String, Object> parametros = new HashMap<String, Object>();
        //if(cartera.getTipoCarteraEnum().equals(tipoCarteraEnum.CL))
        //{
        parametros.put("carteraAfectada", carteraAfecta);
        //}
        return getFacade().findByMap(parametros);
    }
    
}