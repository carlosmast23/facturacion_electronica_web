/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.pos;

import ec.com.codesoft.codefaclite.servidor.facade.pos.CajaPermisoFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaPermiso;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.CajaPermisoServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @auhor
 */
public class CajaPermisoService extends ServiceAbstract<CajaPermiso, CajaPermisoFacade> implements CajaPermisoServiceIf
{
    private final CajaPermisoFacade cajaPermisoFacade;
    
    public CajaPermisoService() throws RemoteException 
    {
        super(CajaPermisoFacade.class);
        cajaPermisoFacade = new CajaPermisoFacade();
    }
    
    public List<CajaPermiso> buscarPermisosCajasActivos(Usuario usuario) throws ServicioCodefacException,java.rmi.RemoteException
    {
        //CajaPermiso cajaPermiso;
        //cajaPermiso.getUsuario();
        //cajaPermiso.getEstado();
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("usuario", usuario);
        mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
        return getFacade().findByMap(mapParametros);
    }
    
    /**
     * 
     * @param session
     * @param caja
     * @return 
     */
    @Override
    public List<Usuario> buscarUsuariosPorSucursalYLigadosACaja(Sucursal sucursal, Caja caja)
    {
        return cajaPermisoFacade.buscarUsuariosPorSucursalYLigadosAUnaCaja(sucursal, caja);
        
    }

    @Override
    public List<CajaPermiso> obtenerTodasCajasPorUsuario(Usuario usuario, PuntoEmision puntoEmision)
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("usuario", usuario);
        mapParametros.put("caja.puntoEmision", puntoEmision);
        List<CajaPermiso> cajaPermisos = getFacade().findByMap(mapParametros);
        //if(cajaPermisos.)
        return null;
    }
    
}
