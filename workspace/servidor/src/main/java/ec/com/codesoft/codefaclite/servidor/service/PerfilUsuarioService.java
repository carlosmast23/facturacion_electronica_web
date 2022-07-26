
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.PerfilUsuarioFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PerfilUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PerfilUsuarioServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 */
public class PerfilUsuarioService extends ServiceAbstract<PerfilUsuario,PerfilUsuarioFacade> implements PerfilUsuarioServiceIf{

    public PerfilUsuarioService() throws RemoteException {
        super(PerfilUsuarioFacade.class);
    }

    @Override
    public List<PerfilUsuario> buscarPorPerfil(Perfil perfil) throws RemoteException {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("perfil",perfil);            
        return getFacade().findByMap(mapParametros);
    }
    
    
}
