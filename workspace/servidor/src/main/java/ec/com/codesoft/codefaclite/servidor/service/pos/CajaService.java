/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.pos;

import ec.com.codesoft.codefaclite.servidor.facade.pos.CajaFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.CajaServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class CajaService extends ServiceAbstract<Caja,CajaFacade> implements CajaServiceIf{

    private final CajaFacade cajaFacade;
    
    public CajaService() throws RemoteException {
        super(CajaFacade.class);
        cajaFacade = new CajaFacade();
    }

    @Override
    public Caja grabar(Caja entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                if(entity.getPuntoEmision() == null)
                    throw new ServicioCodefacException("No existe un punto de emisión seleccionado");
                
                if(entity.getSucursal() == null)
                    throw new ServicioCodefacException("No existe una sucursal seleccionada");
                
                entityManager.persist(entity);
            }
        });
        return entity;
    }

    
    @Override
    public void eliminar(Caja entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                CajaEnum estado = CajaEnum.ELIMINADO;
                entity.setEstadoEnum(estado);
                entityManager.merge(entity);
            }
        });
    }

    @Override
    public List<Caja> buscarCajasAutorizadasPorUsuario(Usuario usuario) throws RemoteException {
        return this.cajaFacade.buscarCajasAutorizadasParaUsuario(usuario);
    }
    
    @Override
    public List<Caja> buscarCajasPorSucursal(SessionCodefacInterface session)
    {
        Map<String, Object> mapParametros = new HashMap<>();
        mapParametros.put("sucursal", session.getSucursal());
        mapParametros.put("estado", CajaEnum.ACTIVO.getEstado());
        List<Caja> cajas = getFacade().findByMap(mapParametros);

        return (cajas.size() > 0)? cajas : null;    
    }
}
