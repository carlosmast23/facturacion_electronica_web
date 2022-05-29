/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ProductoEnsamble;
import ec.com.codesoft.codefaclite.servidor.facade.ProductoEnsambleFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoEnsambleServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class ProductoEnsambleService extends ServiceAbstract<ProductoEnsamble,ProductoEnsambleFacade> implements  ProductoEnsambleServiceIf
{

    public ProductoEnsambleService() throws RemoteException {
        super(ProductoEnsambleFacade.class);
    }
    
    public List<ProductoEnsamble> buscarPorProducto(Producto producto) throws ServicioCodefacException, java.rmi.RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("productoEnsamble", producto);
        return getFacade().findByMap(mapParametros);
    }
    
}
