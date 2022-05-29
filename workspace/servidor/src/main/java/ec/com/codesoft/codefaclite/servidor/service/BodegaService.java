/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.BodegaFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.BodegaPermisoTransferencia;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author CodesoftDesarrollo
 */
public class BodegaService extends ServiceAbstract<Bodega, BodegaFacade> implements BodegaServiceIf{

    private BodegaFacade bodegaFacade;

    public BodegaService() throws RemoteException {
        super(BodegaFacade.class);
        this.bodegaFacade = new BodegaFacade();
    }

    /*
    public Bodega grabar(Bodega b) throws ServicioCodefacException {
        try {
            bodegaFacade.create(b);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(BodegaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("La clave principal ya existe en el sistema");
        } catch (DatabaseException ex) {
            Logger.getLogger(BodegaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("Error con la base de datos");
        }
        return b;
    }
*/

    @Override
    public Bodega grabar(Bodega entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                grabarSinTransaccion(entity);
            }
        });
        return entity;
    }
    
    public void grabarSinTransaccion(Bodega entity) throws ServicioCodefacException, RemoteException
    {
        //Esta sucursal solo sirve de guia para grabar null
        if (entity.getSucursal().equals(Sucursal.getSucursalPermitirTodos())) 
        {
            entity.setSucursal(null);
            entity.setEmpresa(null);
        }

        //grabar los detalles de las bodegas con permiso 
        if (entity.getBodegasPermisoTransfereciaList() != null) 
        {
            for (BodegaPermisoTransferencia bodegaPermiso : entity.getBodegasPermisoTransfereciaList()) 
            {
                entityManager.persist(bodegaPermiso);
            }
        }

        entity.setEstadoEnum(GeneralEnumEstado.ACTIVO); //Por Defecto grabo con estado activo
        entityManager.persist(entity);
    }
    

    public void editar(Bodega b) throws ServicioCodefacException, RemoteException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                //Esta sucursal solo sirve de guia para grabar null
                if(b.getSucursal().equals(Sucursal.getSucursalPermitirTodos()))
                {
                    b.setSucursal(null);
                    b.setEmpresa(null);
                }
                
                entityManager.merge(b);
            }
        });
        
    }

    public void eliminar(Bodega b) throws ServicioCodefacException,RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                KardexService kardexService=new KardexService();
                List<Kardex> kardexResultado=kardexService.buscarPorBodega(b);
                
                for (Kardex kardex : kardexResultado) {
                    //if(kardex.getStock()>0)
                    if(kardex.getStock().compareTo(BigDecimal.ZERO)==0)
                    {
                        throw new ServicioCodefacException("No se puede eliminar porque el kardex "+kardex.getProducto().getNombre()+" tiene stock positivo");
                    }
                }
                
                
                b.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                entityManager.merge(b);
            }
        });
                
    }
    
    public Bodega crearBodegaDefectoSinTransaccion(Sucursal sucursal)
    {
        Bodega bodega=new Bodega();
        bodega.setNombre(ParametrosSistemaCodefac.BODEGA_NOMBRE_DEFECTO);
        bodega.setDescripcion("Bodega creada automática");
        bodega.setEmpresa(sucursal.getEmpresa());
        bodega.setEncargado("");
        bodega.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        bodega.setSucursal(sucursal);
        bodega.setTipoBodegaEnum(Bodega.TipoBodegaEnum.VENTA);
        return bodega;
    }
    
    public Bodega buscarPorNombre(String nombre) throws ServicioCodefacException,RemoteException
    {
        Bodega bodega;
        //bodega.getEstado(); //FALTA FILTRAR
        List<Bodega> bodegas=(List<Bodega>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Map<String, Object> mapParametros = new HashMap<String, Object>();
                mapParametros.put("nombre", nombre);
                return getFacade().findByMap(mapParametros);
            }
        });
        
        if(bodegas.size()>0)
        {
            return bodegas.get(0);
        }
        
        return null;
    }
    
    public List<Bodega> obtenerActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException,RemoteException
    {        
        List<Bodega> bodegas=(List<Bodega>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Bodega bodega=new Bodega();
                Map<String, Object> mapParametros = new HashMap<String, Object>();
               
                mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                mapParametros.put("empresa", empresa);
                List<Bodega> resultadoConsulta=getFacade().findByMap(mapParametros);
                
                /**
                 * Obtener resultado de las bodegas generales
                 */
                mapParametros = new HashMap<String, Object>();               
                mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                mapParametros.put("empresa",null);
                resultadoConsulta.addAll(getFacade().findByMap(mapParametros));
                return resultadoConsulta;
                
            }
        });
        return bodegas;
    }
    
    public List<Bodega> obtenerTodosActivos() throws ServicioCodefacException,RemoteException
    {        
        List<Bodega> bodegas=(List<Bodega>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Bodega bodega=new Bodega();
                Map<String, Object> mapParametros = new HashMap<String, Object>();
               
                mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                List<Bodega> resultadoConsulta=getFacade().findByMap(mapParametros);
                
                return resultadoConsulta;
                
            }
        });
        return bodegas;
    }
    
    public List<Bodega> obtenerActivosPorSucursal(Sucursal sucursal) throws ServicioCodefacException,RemoteException
    {        
        List<Bodega> bodegas=(List<Bodega>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Bodega bodega=new Bodega();
                Map<String, Object> mapParametros = new HashMap<String, Object>();
               
                mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                mapParametros.put("sucursal", sucursal);
                List<Bodega> resultadoConsulta=getFacade().findByMap(mapParametros);
                
                /**
                 * Obtener resultado de las bodegas generales
                 */
                mapParametros = new HashMap<String, Object>();               
                mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                mapParametros.put("empresa",null);
                resultadoConsulta.addAll(getFacade().findByMap(mapParametros));
                return resultadoConsulta;
                
            }
        });
        return bodegas;
    }
    
    public Bodega obtenerUnicaBodegaPorSucursal(Sucursal sucursal) throws ServicioCodefacException,RemoteException
    {
        List<Bodega> bodegas=obtenerActivosPorSucursal(sucursal);
        if(bodegas.size()>0)
        {
            return bodegas.get(0);
        }
        return null;
    }
    
    public Bodega obtenerBodegaVenta(Sucursal sucursal) throws ServicioCodefacException,RemoteException
    {        
        List<Bodega> bodegas=(List<Bodega>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                //Primero busco si exite alguna bodega por sucursal asignada
                //Bodega b;
                //b.getTipoBodega();
                Map<String, Object> mapParametros = new HashMap<String, Object>();
                mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                mapParametros.put("sucursal",sucursal);
                mapParametros.put("tipoBodega",Bodega.TipoBodegaEnum.VENTA.getLetra());
                
                List<Bodega> resultado= getFacade().findByMap(mapParametros);
                
                //Tambien busco bodegas generales si no eneuntra por sucursal
                mapParametros = new HashMap<String, Object>();
                mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                mapParametros.put("empresa",null);               
                mapParametros.put("tipoBodega",Bodega.TipoBodegaEnum.VENTA.getLetra());
                resultado.addAll(getFacade().findByMap(mapParametros));
                return resultado;
            }
        });
        
        if(bodegas.size()>0)
        {
            return bodegas.get(0);
        }
        return null;
    }

}
