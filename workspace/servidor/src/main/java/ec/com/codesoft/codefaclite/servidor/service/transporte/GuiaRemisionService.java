/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.transporte;

import ec.com.codesoft.codefaclite.servidor.facade.FacturaDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.facade.transporte.DetalleProductoGuiaRemisionFacade;
import ec.com.codesoft.codefaclite.servidor.facade.transporte.GuiaRemisionFacade;
import ec.com.codesoft.codefaclite.servidor.service.ComprobantesService;
import ec.com.codesoft.codefaclite.servidor.service.FacturaDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.FacturacionService;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceConsulta;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Transportista;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DestinatarioGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DetalleProductoGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.GuiaRemisionServiceIf;
import ec.com.codesoft.codefaclite.ws.recepcion.Comprobante;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class GuiaRemisionService extends ServiceAbstract<GuiaRemision,GuiaRemisionFacade> implements GuiaRemisionServiceIf{

    public GuiaRemisionService() throws RemoteException {
        super(GuiaRemisionFacade.class);
    }
    
    private void validarGuiaRemision(GuiaRemision entity) throws ServicioCodefacException, RemoteException
    {
        if(entity.getCodigoDocumentoEnum()==null)
        {
            throw new ServicioCodefacException("No se puede grabar sin selecciona un Documento");
        }
        
        for (DestinatarioGuiaRemision destinatario : entity.getDestinatarios()) {
            
            if(destinatario.getDetallesProductos()==null || destinatario.getDetallesProductos().size()==0)
            {
                throw new ServicioCodefacException("No se puede grabar sin detalles para la factura "+destinatario.getPreimpreso());
            }
            
            if(destinatario.getRuta()==null || destinatario.getRuta().trim().isEmpty())
            {
                destinatario.setRuta("Sin ruta");
            }
            
            if(destinatario.getAutorizacionNumero()==null || destinatario.getAutorizacionNumero().isEmpty())
            {
                throw new ServicioCodefacException("No se puede grabar una guia de remisión sin autorización de la factura");
            }
            
            if(entity.getCodigoDocumentoEnum().equals(DocumentoEnum.GUIA_REMISION))
            {
                if(destinatario.getAutorizacionNumero().length()<=9)
                {
                    throw new ServicioCodefacException("El tamanio de la  autorización debe ser mayor que 9 digitos");
                }
            }
            
            for (DetalleProductoGuiaRemision detallesProducto : destinatario.getDetallesProductos()) 
            {
                if(detallesProducto.getCantidad()<=0)
                {
                    throw new ServicioCodefacException("No se puede emitir guias de remision con cantidad iguales o menores que cero");
                }
                
                FacturaDetalleFacade facturaDetalleFacade=new FacturaDetalleFacade();
                FacturaDetalle facturaDetalle= facturaDetalleFacade.find(detallesProducto.getReferenciaId());
                
                //Verificar que los saldos no sean superiores a los diponibles en las facturas
                BigDecimal saldo=consultarSaldoDetalleFactura(facturaDetalle);
                if(new BigDecimal(detallesProducto.getCantidad()+"").compareTo(saldo)>0)
                {
                    throw new ServicioCodefacException("La cantidad del producto "+detallesProducto.getDescripcion()+" es superior al saldo de "+saldo+" pendiente en la factura");
                }
                
            }
            
            
            
        }   
        
        if(entity.getCodigoDocumentoEnum().equals(DocumentoEnum.GUIA_REMISION))
        {
            for (DestinatarioGuiaRemision destinatario : entity.getDestinatarios()) 
            {
                if(!destinatario.getFacturaReferencia().getCodigoDocumentoEnum().equals(DocumentoEnum.FACTURA))
                {
                    throw new ServicioCodefacException("No se puede grabar una Guía de Remisión con detalles de otros documentos diferentes de Facturas");
                }
            }
        }
        
        /**
         * Validar que todos los detalles de las facturas estan activas por cualquier motivo antes de procesar
         */
        for (DestinatarioGuiaRemision destinatario : entity.getDestinatarios()) {
            FacturacionService facturaService=new FacturacionService();
            //Actualizo la referencia de la factura para evitar tener alguna modificación
            Factura factura=facturaService.buscarPorId(destinatario.getFacturaReferencia().getId());
            if(factura.getEstadoEnum().equals(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO) || factura.getEstadoEnum().equals(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO_SRI))
            {
                throw  new ServicioCodefacException("No se puede procesar por que la factura: "+factura.getPreimpreso()+" fue eliminada");
                
            }
            
            //Eliminar tambien las NC que afecten a facturas que les anules por completo
            if(factura.getEstadoNotaCreditoEnum().equals(Factura.EstadoNotaCreditoEnum.ANULADO_TOTAL))
            {
                throw  new ServicioCodefacException("No se puede procesar por que la factura: "+factura.getPreimpreso()+" esta afectada por una Nota Crédito");
            }
        }
    }

    public GuiaRemision grabar(GuiaRemision entity) throws ServicioCodefacException, RemoteException {
        //Validaciones previas antes de grabar
        validarGuiaRemision(entity);
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() {
                try {
                    ComprobantesService servicioComprobante = new ComprobantesService();
                    //entity.setCodigoDocumento(DocumentoEnum.GUIA_REMISION.getCodigo());
                    
                    servicioComprobante.setearSecuencialComprobanteSinTransaccion(entity);
                    
                    if(entity.getDestinatarios()!=null)
                    {
                        for (DestinatarioGuiaRemision destinatario : entity.getDestinatarios()) 
                        {                            
                            if(destinatario.getDetallesProductos()!=null)
                            {
                                for (DetalleProductoGuiaRemision detalleProducto : destinatario.getDetallesProductos()) {
                                    entityManager.persist(detalleProducto);
                                }
                            }
                            entityManager.persist(destinatario);
                        }
                    }                    
                   //CAMBIAR ESTADOS DE LAS FACTURAS QUE VAN A SER ENVIADAS
                   
                    for (DestinatarioGuiaRemision destinatario : entity.getDestinatarios()) {
                        for (DetalleProductoGuiaRemision detallesProducto : destinatario.getDetallesProductos()) {
                            Long facturaId=detallesProducto.getReferenciaId();
                            FacturaDetalleFacade facturaDetalleFacade=new FacturaDetalleFacade();
                            FacturaDetalle facturaDetalle= facturaDetalleFacade.find(facturaId);;
                            
                            //TODO: Por el momento dejo pendiente de validar cuando un mismo producto puede ir en partes en varias guias de remision
                            Factura facturaEditar=facturaDetalle.getFactura();
                            if(facturaEditar.getTipoFacturacionEnum().equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA))
                            {
                                System.out.println("Factura editar: "+facturaEditar.getPreimpreso());
                                facturaEditar.setEstadoEnviadoGuiaRemisionEnum(EnumSiNo.SI);
                                entityManager.merge(facturaEditar);
                            }
                        }
                        
                    }
                    
                    entityManager.persist(entity);
                } catch (RemoteException ex) {
                    Logger.getLogger(GuiaRemisionService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(GuiaRemisionService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        return entity;
    }
    
    public void editarGuiaRemision(GuiaRemision guiaRemision) throws ServicioCodefacException, RemoteException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entityManager.merge(guiaRemision);
            }
        });
    }
    
    public List<GuiaRemision> obtenerConsulta(Date fechaInicial,Date fechaFinal,ComprobanteEntity.ComprobanteEnumEstado estado,Transportista transportista,Persona destinatario,String codigoProducto,Empresa empresa) throws ServicioCodefacException, RemoteException
    {
        return (List<GuiaRemision>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                return getFacade().obtenerConsultaFacade(fechaInicial, fechaFinal,estado,transportista,destinatario,codigoProducto,empresa);
            }
        });
                
    }
    
    //TODO: Optimizar esta parte
    public BigDecimal consultarSaldoDetalleFactura(FacturaDetalle facturaDetalle) throws ServicioCodefacException, RemoteException
    {
        DetalleProductoGuiaRemision detalle;
        //detalle.getDestinatario().getFacturaReferencia()
        //g.get
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("referenciaId", facturaDetalle.getId());
        mapParametros.put("destinatario.facturaReferencia", facturaDetalle.getFactura());
        DetalleProductoGuiaRemisionFacade facade=new DetalleProductoGuiaRemisionFacade();
        
        List<DetalleProductoGuiaRemision> listaGuiasRemision = facade.findByMap(mapParametros);
        
        BigDecimal totalEnviado=BigDecimal.ZERO;
        for (DetalleProductoGuiaRemision detalleGuia : listaGuiasRemision) 
        {
            if(detalleGuia.getDestinatario().getGuiaRemision().getEstadoEnum().equals(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO) || detalleGuia.getDestinatario().getGuiaRemision().getEstadoEnum().equals(ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR))
            {
                totalEnviado=totalEnviado.add(new BigDecimal(detalleGuia.getCantidad()));
            }
        }
        return facturaDetalle.getCantidad().subtract(totalEnviado);
    }
    

    @Override
    public void eliminar(GuiaRemision entity) throws RemoteException {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException, RemoteException {
                    ComprobantesService comprobanteService = new ComprobantesService();
                    comprobanteService.eliminarComprobanteSinTransaccion(entity);
                    
                    //cambiar el estado de la factura para que vuelvan volver a reenviar en otra guia de remision
                    for (DestinatarioGuiaRemision destinatario : entity.getDestinatarios()) {
                        if(destinatario.getFacturaReferencia()!=null)
                        {   //
                            destinatario.getFacturaReferencia().setEstadoEnviadoGuiaRemisionEnum(EnumSiNo.NO);
                            entityManager.merge(destinatario.getFacturaReferencia()); 
                        }
                    }

                    //entity.setEstado(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado());
                    entityManager.merge(entity);
                    //entity.setEstado(estado);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(GuiaRemisionService.class.getName()).log(Level.SEVERE, null, ex);            
        }
    }
    
    
    
    
}
