/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.pos;

import ec.com.codesoft.codefaclite.servidor.facade.pos.CajaSesionFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.IngresoCaja;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaSessionEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.CajaSesionServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @auhor
 */
public class CajaSesionService extends ServiceAbstract<CajaSession, CajaSesionFacade> implements CajaSesionServiceIf
{
    private CajaSesionFacade cajaSesionFacade;
    
    public CajaSesionService() throws RemoteException {
        super(CajaSesionFacade.class);
        this.cajaSesionFacade = new CajaSesionFacade();
    }
    
    @Override
    public CajaSession grabar(CajaSession entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                if(entity.getCaja() == null)
                {
                    throw new ServicioCodefacException("El inicio de sesión de caja debe tener una caja asignada");
                }
                
                if(buscarSiCajaTieneSessionActiva(entity.getCaja()))
                {
                    throw new ServicioCodefacException("La caja ya esta con una sesión activa.\n Posible Solución: Otro Usuario tiene activa la sesión con esta caja");
                }
                
                if(entity.getUsuario() == null)
                {
                    throw  new ServicioCodefacException("El inicio de sesíon de caja debe tener un usuario asignado");
                }
                
                if(entity.getValorApertura() == null)
                {
                    CajaSession cajaSessionUltima = obtenerUltimaCajaSession(entity.getCaja());
                    if(cajaSessionUltima == null)
                    {
                        entity.setValorApertura(BigDecimal.ZERO);
                    }
                    else
                    {
                        entity.setValorApertura(cajaSessionUltima.getValorCierre());
                    }
                }
                
                entity.setEstadoSessionEnum(CajaSessionEnum.ACTIVO);
                entity.setFechaHoraApertura(UtilidadesFecha.getFechaHoyTimeStamp());       
                
                entityManager.persist(entity);
            }
        });
        return entity;
    }
    
    @Override
    public void editar(CajaSession entity) throws ServicioCodefacException, RemoteException 
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                BigDecimal totalVentas = BigDecimal.ZERO;
                boolean grabarAunqueNoTengaVentas = false;
                
                if(entity.getIngresosCaja() == null || entity.getIngresosCaja().isEmpty())
                {
                    grabarAunqueNoTengaVentas = true;
                }
                
                totalVentas = entity.getValorApertura();
                
                if(!grabarAunqueNoTengaVentas)
                {
                    for(IngresoCaja ingresoCaja: entity.getIngresosCaja())
                    {
                        totalVentas = totalVentas.add(ingresoCaja.getValor());
                    }
                }

                entity.setValorCierre(totalVentas);
                entity.setEstadoSessionEnum(CajaSessionEnum.FINALIZADO);
                entity.setFechaHoraCierre(UtilidadesFecha.getFechaHoyTimeStamp());
               
                entityManager.merge(entity);
            }
        });

    }

    @Override
    public boolean buscarSiCajaTieneSessionActiva(Caja caja) {
        Map<String,Object> mapParametros=new HashMap<>();
        mapParametros.put("caja",caja);
        mapParametros.put("estadoCierreCaja",CajaSessionEnum.ACTIVO.getEstado());
        List<CajaSession> cajaSession = getFacade().findByMap(mapParametros);

        return cajaSession.size()>0;
    }

    @Override
    public CajaSession obtenerUltimaCajaSession(Caja caja) {
        return this.cajaSesionFacade.obtenerUltimaCajaSession(caja);
    }
    
    public List<CajaSession> obtenerCajaSessionPorUsuario(Usuario usuario)
    {
        Map<String, Object> mapParametros = new HashMap<>();
        mapParametros.put("usuario", usuario);
        mapParametros.put("estadoCierreCaja", CajaSessionEnum.ACTIVO.getEstado());
        
        List<CajaSession> cajasSession = getFacade().findByMap(mapParametros);
                
        return cajasSession;
    }

    @Override
    public CajaSession obtenerCajaSessionPorPuntoEmisionYUsuario(Integer puntoEmision, Usuario usuario) 
    {
        Map<String, Object> mapParametros = new HashMap<>();
        mapParametros.put("usuario", usuario);
        mapParametros.put("caja.puntoEmision.puntoEmision", puntoEmision);
        mapParametros.put("estadoCierreCaja", CajaSessionEnum.ACTIVO.getEstado());
        
        List<CajaSession> cajasSession = getFacade().findByMap(mapParametros);
        
        if(cajasSession.size() > 0)
        {
            return cajasSession.get(0);
        }
        
        return null;
    }
    
    @Override
    public List<CajaSession> obtenerCajaSessionPorCajaYUsuario(Caja caja, Usuario usuario)
    {
        Map<String, Object> mapParametros = new HashMap<>();
        mapParametros.put("caja", caja);
        mapParametros.put("usuario", usuario);
        mapParametros.put("estadoCierreCaja", CajaSessionEnum.FINALIZADO.getEstado());
        
        List<CajaSession> cajasSession = getFacade().findByMap(mapParametros);
        
        if(cajasSession.size() > 0)
        {
            return cajasSession;
        }
        
        return null;
    }

    @Override
    public List<CajaSession> obtenerCajaSessionPorUsuarioYSucursal(Usuario usuario, Sucursal sucursal) throws RemoteException 
    {
        Map<String, Object> map = new HashMap<>();
        
        map.put("usuario", usuario);
        map.put("caja.sucursal", sucursal);
        map.put("estadoCierreCaja",  CajaSessionEnum.ACTIVO.getEstado());
        
        List<CajaSession> cajasSession = getFacade().findByMap(map);
        
        if(cajasSession.size() > 0)
        {
            return cajasSession;
        }
        
        return null;
    }

    @Override
    public List<CajaSession> obtenerCajaSessionPorCajaUsuarioYFecha(Caja caja, Usuario usuario, Date fechaInicio, Date fechaFin) throws RemoteException {
        
        List<CajaSession> cajasSession = cajaSesionFacade.obtenerCajaSessionPorCajaUsuarioYFecha(caja, usuario, fechaInicio, fechaFin);
        if(cajasSession.size() > 0)
        {
            return cajasSession;
        }        
        return null;
    }

}
