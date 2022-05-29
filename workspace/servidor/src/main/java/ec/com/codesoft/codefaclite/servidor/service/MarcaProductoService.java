/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.MarcaProductoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.MarcaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.MarcaProductoServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class MarcaProductoService extends ServiceAbstract<MarcaProducto,MarcaProductoFacade> implements MarcaProductoServiceIf{

    public MarcaProductoService() throws RemoteException {
        super(MarcaProductoFacade.class);
    }
    
    private void validarGrabar(MarcaProducto marcaProducto) throws ServicioCodefacException, RemoteException
    {
        if(marcaProducto.getEmpresa()==null)
        {
            throw new ServicioCodefacException("Error Validación: No se puede grabar sin registrar una empresa");
        }
        
    }

    @Override
    public MarcaProducto grabar(MarcaProducto marcaProducto) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                validarGrabar(marcaProducto);
                marcaProducto.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                entityManager.persist(marcaProducto);
            }
        });
        return marcaProducto;
    }

    @Override
    public void editar(MarcaProducto marcaProducto) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                validarGrabar(marcaProducto);
                entityManager.merge(marcaProducto);
            }
        });
    }

    @Override
    public void eliminar(MarcaProducto marcaProducto) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                marcaProducto.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(marcaProducto);
            }
        });
    }
    
    public List<MarcaProducto> obtenerActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException, RemoteException
    {
        return (List<MarcaProducto>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Map<String,Object> mapParameros=new HashMap<String, Object>();
                mapParameros.put("empresa", empresa);
                mapParameros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                return getFacade().findByMap(mapParameros);
            }
        } );
    }
    
    
    
    
}
