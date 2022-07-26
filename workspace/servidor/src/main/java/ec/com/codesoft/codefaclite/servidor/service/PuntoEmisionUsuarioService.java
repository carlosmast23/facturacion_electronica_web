/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.PuntoEmisionUsuarioFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmisionUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PuntoEmisionUsuarioServiceIf;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 */
public class PuntoEmisionUsuarioService extends ServiceAbstract<PuntoEmisionUsuario,PuntoEmisionUsuarioFacade> implements PuntoEmisionUsuarioServiceIf
{
    public PuntoEmisionUsuarioService() throws RemoteException {
        super(PuntoEmisionUsuarioFacade.class);
    }

    @Override
    public PuntoEmisionUsuario grabar(PuntoEmisionUsuario entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entity.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                entityManager.persist(entity);
            }
        });
        return entity; //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override   
    public List<PuntoEmisionUsuario> obtenerActivoPorUsuario(Usuario usuario,Sucursal sucursal) throws ServicioCodefacException, RemoteException
    {
        return (List<PuntoEmisionUsuario>)ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Map<String,Object> mapParametros=new  HashMap<String,Object>();
                mapParametros.put("usuario",usuario);
                mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
                mapParametros.put("puntoEmision.sucursal",sucursal);
                return getFacade().findByMap(mapParametros);
            }
        });
    }
    
    @Override   
    public List<PuntoEmisionUsuario> obtenerActivosPorSucursal(Sucursal sucursal) throws ServicioCodefacException, RemoteException
    {
        //PuntoEmisionUsuario pe;
        //pe.getPuntoEmision().getSucursal().
        return (List<PuntoEmisionUsuario>)ejecutarConsulta(new MetodoInterfaceConsulta() 
        {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Map<String,Object> mapParametros=new  HashMap<String,Object>();
                mapParametros.put("puntoEmision.sucursal",sucursal);
                mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
                return getFacade().findByMap(mapParametros);
            }
        });
        
    }
    
    public List<PuntoEmision> obtenerActivosPorSucursalCastPuntoEmision(Usuario usuario,Sucursal sucursal) throws ServicioCodefacException, RemoteException
    {
        List<PuntoEmision> puntosEmision=new ArrayList<PuntoEmision>();
        List<PuntoEmisionUsuario> resultado=obtenerActivoPorUsuario(usuario, sucursal);
        for (PuntoEmisionUsuario puntoEmisionUsuario : resultado) {
            puntosEmision.add(puntoEmisionUsuario.getPuntoEmision());            
        }
        return puntosEmision;        
    }
    
}