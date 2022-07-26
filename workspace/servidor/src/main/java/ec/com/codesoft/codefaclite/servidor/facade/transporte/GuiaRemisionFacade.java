/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.transporte;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Transportista;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author
 */
public class GuiaRemisionFacade extends AbstractFacade<GuiaRemision>{
    
    public GuiaRemisionFacade() {
        super(GuiaRemision.class);
    }
    
    public List<GuiaRemision> obtenerConsultaFacade(Date fechaInicial,Date fechaFinal,ComprobanteEntity.ComprobanteEnumEstado estado, Transportista transportista,Persona destinatario,String codigoProducto,Empresa empresa) throws ServicioCodefacException, RemoteException    
    {
        //GuiaRemision guia;
        ///guia.getDestinatarios().get(0).getDetallesProductos().get(0).getDescripcion();
        //guia.getDestinatarios().get(0).getDestinatorio();
        //guia.getTransportista();
        //guia.getFechaEmision()
        //guia.getEs
        //guia.getFechaIniciaTransporte();
        String queryString="select distinct u from GuiaRemision u Join u.destinatarios d Join d.detallesProductos dp where u.empresa=?11 ";
        
        if(fechaInicial!=null)
        {
            queryString+=" AND u.fechaEmision>=?1 ";
        }
        
        if(fechaFinal!=null)
        {
            queryString+="AND u.fechaEmision<=?2 ";
        }
        
        if(estado!=null)
        {
            if(estado.equals(ComprobanteEntity.ComprobanteEnumEstado.TODOS_SRI))
            {
                queryString+=" AND (u.estado=?6 or u.estado=?7 )" ;
            }
            else
            {
                queryString+=" AND u.estado=?5 " ;
            }
            
        }
        
        if(transportista!=null)
        {
            queryString+=" AND u.transportista=?8 " ;
        }
        
        if(destinatario!=null)
        {
            queryString+=" AND d.destinatorio=?9 " ;
        }

        if(codigoProducto!=null)
        {
            queryString+=" AND dp.descripcion=?10 " ; //TODO: Ver alguna mas eficaz de buscar
        }        
        
        
        /**
         * ===================> SETEAR VALORES <=====================
         */
        
        Query query = getEntityManager().createQuery(queryString);

        if (fechaInicial != null) {
            query.setParameter(1,fechaInicial);
        }

        if (fechaFinal != null) {
            query.setParameter(2,fechaFinal);
        }
        
        if(estado!=null)
        {
            if(estado.equals(ComprobanteEntity.ComprobanteEnumEstado.TODOS_SRI))
            {
                query.setParameter(6,ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());
                query.setParameter(7,ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO_SRI.getEstado());
            }else
            {
                query.setParameter(5,estado.getEstado());
            }            
        }
        
        if(transportista!=null)
        {
            query.setParameter(8,transportista);
        }
        
        if(destinatario!=null)
        {
            query.setParameter(9,destinatario);
        }
        
        if(codigoProducto!=null)
        {
            query.setParameter(10,codigoProducto);
        }  
        
        query.setParameter(11,empresa);
                
        return query.getResultList();        
    }
    
}
