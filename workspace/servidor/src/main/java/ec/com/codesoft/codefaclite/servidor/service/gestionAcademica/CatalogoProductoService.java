/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.CatalogoProductoFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CatalogoProductoServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 */
public class CatalogoProductoService extends ServiceAbstract<CatalogoProducto,CatalogoProductoFacade> implements CatalogoProductoServiceIf{
            
    public CatalogoProductoService() throws RemoteException {
        super(CatalogoProductoFacade.class);
    }
    
    public List<CatalogoProducto> obtenerPorModulo(ModuloCodefacEnum modulo) throws RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("moduloCod",modulo.getCodigo());
        return getFacade().findByMap(mapParametros);
    }
    
    public CatalogoProducto obtenerPorNombre(String nombre) throws RemoteException {
        CatalogoProducto cp;
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("nombre",nombre);
        List<CatalogoProducto> catalogos=getFacade().findByMap(mapParametros);
        if(catalogos.size()>0)
        {
            return catalogos.get(0);
        }
        return null;
    }
    
}
