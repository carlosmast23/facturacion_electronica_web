/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.RetencionFacade;
import ec.com.codesoft.codefaclite.servidor.service.cartera.CarteraService;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import static ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac.CODIGO_NO_APLICA_RETENCIONES;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RetencionServiceIf;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Carlos
 */
public class RetencionService extends ServiceAbstract<Retencion, RetencionFacade> implements RetencionServiceIf {

    RetencionFacade retencionFacade;

    public RetencionService() throws RemoteException {
        super(RetencionFacade.class);
        retencionFacade = new RetencionFacade();
    }

    public Retencion grabar(Retencion entity) throws ServicioCodefacException, RemoteException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                validarRetencion(entity);
                
                
                //Verificar si es una retencion libre o tiene una referencia
                if(entity.getTipoDocumentoEnum().equals(TipoDocumentoEnum.LIBRE))
                {
                    entity.setCompra(null); //Si es libre es decir que no guarda referencia y por tal motivo elimino la compra porque solo debe estar enviado una temporal
                }
                else
                {
                    //Actualizar la referencia de la compra para saber que la retencion ya fue enviada y no cargar de nuevo
                    Compra compra=entity.getCompra();
                    compra.setEstadoRetencion(Compra.RetencionEnumCompras.EMITIDO.getEstado());
                    entityManager.merge(compra);
                }
                                
                entity.setCodigoDocumento(DocumentoEnum.RETENCIONES.getCodigo());
                
                
                ComprobantesService servicioComprobante = new ComprobantesService();
                servicioComprobante.setearSecuencialComprobanteSinTransaccion(entity);
               

                List<RetencionDetalle> detallesEliminar=new ArrayList<>();
                for (RetencionDetalle retencionDetalle : entity.getDetalles()) 
                {
                    if(retencionDetalle.getCodigoRetencionSri().equals(CODIGO_NO_APLICA_RETENCIONES))
                    {
                        detallesEliminar.add(retencionDetalle);
                    }
                    else
                    {
                        entityManager.persist(retencionDetalle);
                    }
                }
                
                //Detalles a eliminar
                entity.getDetalles().removeAll(detallesEliminar);
                
                //Validacion para evitar que la retención se quede sin ningun detalle
                if(entity.getDetalles().size()==0)
                {
                    throw new ServicioCodefacException("No existen detalles de retenciones disponibles para procesar.\nNota:Verifique los códigos que no aplican en las retenciones");
                }

                entityManager.persist(entity);
                entityManager.flush();
                
                grabarCartera(entity);

                //Despues de grabar genero inmediatamente un flush para evitar perder la transacción por causas como perdida de energia
                entityManager.flush();               

            }
        });

        //EntityTransaction transaction = getTransaccion();
        //transaction.begin();        
        //transaction.commit();
        return entity;
    }
    
    private void grabarCartera(Retencion retencion) throws RemoteException, ServicioCodefacException
    {
        //Grabar en la cartera si todo el proceso anterior fue correcto
        CarteraService carteraService = new CarteraService();
        carteraService.grabarDocumentoCartera(retencion, Cartera.TipoCarteraEnum.PROVEEDORES,null,CrudEnum.CREAR);
    }
    
    private void validarRetencion(Retencion retencion) throws ServicioCodefacException, RemoteException
    {
        if(retencion.getDetalles()==null|| retencion.getDetalles().size()==0)
        {
            throw new ServicioCodefacException("No se puede grabar retenciones sin detalles");
        }
        
    
        if(retencion.getProveedor().getIdentificacion().equals(Persona.IDENTIFICACION_CONSUMIDOR_FINAL))
        {
            throw new ServicioCodefacException("No se permite emitir retenciones a consumidor final");
        }
        
        //Validar que no existan retenciones activas aplicadas a la misma compra+
        Retencion retencionExistente=obtenerRetencionPorPreimpresoyProveedor(retencion.getPreimpresoDocumento(),retencion.getProveedor());
        if(retencionExistente!=null)
        {
            throw new ServicioCodefacException("La compra ya fue aplicada la retención en el comprobante "+retencionExistente.getPreimpreso());
        }
        
        
        //TODO: Por el momento quito esta validacion porque ese problema de los duplicados puedo manejar en los ats agrupando nuevamente
        ///Validar que no existan codigos duplicados porque eso no permite el Sri
        /*List<String> codigosRetencion=new ArrayList<String>();
        for (RetencionDetalle detalle : retencion.getDetalles()) {
            if(codigosRetencion.contains(detalle.getCodigoRetencionSri()))
            {
                throw new ServicioCodefacException("Los detalles de la retención no pueden tener códigos duplicados");
            }
            else
            {
                codigosRetencion.add(detalle.getCodigoRetencionSri());
            }
            
        }*/
        
    }
    
    

    @Override
    public void eliminar(Retencion entity) throws RemoteException {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException, RemoteException {
                    ComprobantesService comprobanteService=new ComprobantesService();
                    comprobanteService.eliminarComprobanteSinTransaccion(entity);
                    
                    //entity.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                    entityManager.merge(entity);
                    
                    //Si el documento es diferente de compras cambiar el estado a no enviado la retencion
                    if(!entity.getTipoDocumentoEnum().equals(TipoDocumentoEnum.LIBRE))
                    {
                        Compra compra=entity.getCompra();
                        compra.setEstadoRetencion(Compra.RetencionEnumCompras.NO_EMITIDO.getEstado());
                        entityManager.merge(compra);
                        
                    }
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(RetencionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Object[]> obtenerRetencionesIvaPorCompra(Compra compra,SriRetencion sriRetencion)throws RemoteException
    {
        return retencionFacade.obtenerRetencionesIvaPorCompraFacade(compra,sriRetencion);
        //compra.getSecuencial();
    }
    
    public List<RetencionDetalle> obtenerRetencionesRentaPorCompra(Compra compra,SriRetencion sriRetencion)throws RemoteException
    {
        return retencionFacade.obtenerRetencionesRentaPorCompraFacade(compra,sriRetencion);
        //compra.getSecuencial();
    }
    
    @Override
    public List<RetencionDetalle> obtenerRetencionesReportes(Persona persona, Date fi, Date ff, SriRetencionIva iva, SriRetencionRenta renta, SriRetencion sriRetencion,ComprobanteEntity.ComprobanteEnumEstado estadoEnum,Empresa empresa) throws RemoteException {
        //return retencionFacade.lista(persona, fi, ff, iva, renta,tipo);
        return retencionFacade.obtenerRetencionesReportesFacade(persona, fi, ff, iva, renta,sriRetencion,estadoEnum,empresa);
    }
    
    //TODO: Mejorar para obtener todos los datos de una sola consulta y evitar estar construyendo
    @Override
    public List<Retencion> obtenerRetencionesSinDetalleReportes(Persona persona, Date fi, Date ff, SriRetencionIva iva, SriRetencionRenta renta, SriRetencion sriRetencion,ComprobanteEntity.ComprobanteEnumEstado estadoEnum,Empresa empresa) throws RemoteException {
        
        List<RetencionDetalle> retencionDetalleList=retencionFacade.obtenerRetencionesReportesFacade(persona, fi, ff, iva, renta,sriRetencion,estadoEnum,empresa);
        List<Retencion> retencionList=new ArrayList<Retencion>();
        for (RetencionDetalle retencionDetalle : retencionDetalleList)
        {
            if(!retencionList.contains(retencionDetalle.getRetencion()))
            {
                retencionList.add(retencionDetalle.getRetencion());
            }
        }
        return retencionList;
        //return retencionFacade.lista(persona, fi, ff, iva, renta,tipo);
        
    }
    
    public List<Object[]> obtenerRetencionesCodigo(Persona persona, Date fi, Date ff, SriRetencionIva iva, SriRetencionRenta renta,String tipo) {
        return retencionFacade.retencionesCodigo(persona, fi, ff, iva, renta, tipo);
    }
    
    @Override
    public List<Retencion> obtenerRetencionesPorCompra(Compra compra) throws ServicioCodefacException, RemoteException
    {
        return getFacade().obtenerRetencionesPorCompraFacade(compra);
    }

    public Retencion obtenerRetencionPorPreimpresoyProveedor(String preimpresoCompra, Persona persona)
    {
        List<Retencion> resultado= getFacade().obtenerRetencionPorPreimpresoyProveedor(preimpresoCompra, persona);
        if(resultado.size()>0)
        {
            return resultado.get(0);
        }
        return null;
    }

}
