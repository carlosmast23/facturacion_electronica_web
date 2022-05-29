/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.SegmentoProductoFacade;
import ec.com.codesoft.codefaclite.servidor.facade.TipoProductoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SegmentoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TipoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SegmentoProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.TipoProductoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.rmi.RemoteException;

/**
 *
 * @author DellWin10
 */
public class TipoProductoService extends ServiceAbstract<TipoProducto, TipoProductoFacade> implements  TipoProductoServiceIf{

    public TipoProductoService() throws RemoteException {
        super(TipoProductoFacade.class);
    }
    
    private void setearDatosGrabar(TipoProducto entity,Empresa empresa,CrudEnum crudEnum)
    {
        entity.setEmpresa(empresa);
    }
    
    private void validarGrabar(TipoProducto segmento,CrudEnum crudEnum) throws ServicioCodefacException
    {           
        if(segmento.getEmpresa()==null)
        {
            throw new ServicioCodefacException("No se puede grabar sin una Empresa");
        }

        
    }
    
    @Override
    public TipoProducto grabar(TipoProducto entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entity.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                
                setDatosAuditoria(entity,usuarioCreacion,CrudEnum.CREAR);
                setearDatosGrabar(entity, empresa,CrudEnum.CREAR);
                validarGrabar(entity, CrudEnum.CREAR);
                entityManager.persist(entity);
                
            }
        });
        return entity;
    }
    
    @Override
    public TipoProducto editar(TipoProducto entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {                                
                setDatosAuditoria(entity,usuarioCreacion,CrudEnum.EDITAR);
                setearDatosGrabar(entity, empresa,CrudEnum.EDITAR);
                editarSinTransaccion(entity);
            }
        });
        return entity;
    }
    
    public void editarSinTransaccion(TipoProducto entity) throws ServicioCodefacException, RemoteException 
    {
        validarGrabar(entity, CrudEnum.EDITAR);
        entityManager.merge(entity);
    }
    
    @Override
    public void eliminar(TipoProducto entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                //TODO: Agregar validacion para solo eliminar los lotes si no tiene ningun saldo disponible
                entity.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(entity);
            }
        });
    }
}
