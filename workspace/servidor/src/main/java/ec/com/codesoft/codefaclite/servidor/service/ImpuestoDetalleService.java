/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidor.facade.ImpuestoDetalleFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesMap;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author PC
 */
public class ImpuestoDetalleService extends ServiceAbstract<ImpuestoDetalle,ImpuestoDetalleFacade> implements ImpuestoDetalleServiceIf
{
    private ImpuestoDetalleFacade impuestoDetalleFacade;

    public ImpuestoDetalleService() throws java.rmi.RemoteException
    {
        super(ImpuestoDetalleFacade.class);
        impuestoDetalleFacade= new ImpuestoDetalleFacade();
    }
    /*
    public ImpuestoDetalle grabar(ImpuestoDetalle i) throws ServicioCodefacException,java.rmi.RemoteException
    {
        try {
            impuestoDetalleFacade.create(i);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(ImpuestoDetalleService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(ImpuestoDetalleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    } */   
    
    public void eliminar(ImpuestoDetalle i) throws java.rmi.RemoteException
    {
        impuestoDetalleFacade.remove(i);
    }
    
    public List<ImpuestoDetalle> buscarImpuestoDetallePorMap(Map<String,Object> map) throws java.rmi.RemoteException
    {
        return impuestoDetalleFacade.findByMap(map);        
    }
    
    public List<ImpuestoDetalle> obtenerIvaVigente() throws java.rmi.RemoteException
    {
        return impuestoDetalleFacade.getImpuestoVigenteByName(Impuesto.IVA);
    }
    
    public Map<Integer,ImpuestoDetalle> obtenerTodosMap() throws java.rmi.RemoteException,ServicioCodefacException
    {
        List<ImpuestoDetalle> impuestosDetalleList= obtenerTodos();
        Map<Integer, ImpuestoDetalle> mapResultado = UtilidadesMap.crearMapDesdeList(impuestosDetalleList, new UtilidadesMap.MapCastListIf<Integer, ImpuestoDetalle, ImpuestoDetalle>() {
                @Override
                public Integer getClave(ImpuestoDetalle dato) {
                    return dato.getCodigo();
                }

                @Override
                public ImpuestoDetalle getValor(ImpuestoDetalle dato) {
                    return dato;
                }
            });
        return mapResultado;
    }
    
    public ImpuestoDetalle buscarPorTarifa(Integer tarifa) throws ServicioCodefacException,java.rmi.RemoteException
    {
        List<ImpuestoDetalle> resultados=(List<ImpuestoDetalle>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Map<String,Object> mapParametros=new HashMap<String, Object>();
                mapParametros.put("tarifa",tarifa);
                return getFacade().findByMap(mapParametros);
            }
        });
        
        if(resultados.size()>0)
        {
            return resultados.get(0);
        }
        return null;
    }
    
    public ImpuestoDetalle buscarPorCodigo(Integer codigo) throws ServicioCodefacException,java.rmi.RemoteException
    {
        List<ImpuestoDetalle> resultados=(List<ImpuestoDetalle>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Map<String,Object> mapParametros=new HashMap<String, Object>();
                mapParametros.put("codigo",codigo);
                return getFacade().findByMap(mapParametros);
            }
        });
        
        if(resultados.size()>0)
        {
            return resultados.get(0);
        }
        return null;
    }

}
