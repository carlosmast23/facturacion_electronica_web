/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author PC
 */
public class NotaCreditoFacade extends AbstractFacade<NotaCredito> {

    public NotaCreditoFacade() {
        super(NotaCredito.class);
    }

    public List<NotaCredito> lista(Persona persona, Date fi, Date ff,ComprobanteEntity.ComprobanteEnumEstado estado,Empresa empresa) {

        String cliente = "", fecha = "",estadoStr="";
        if (persona != null) {
            cliente = "u.cliente=?1";
        } else {
            cliente = "1=1";
        }

        if (fi == null && ff != null) {
            fecha = " AND u.fechaEmision <= ?3";
        } else if (fi != null && ff == null) {
            fecha = " AND u.fechaEmision >= ?2";
        } else if (fi == null && ff == null) {
            fecha = "";
        } else {
            fecha = " AND (u.fechaEmision BETWEEN ?2 AND ?3)";
        }
        
        if(estado!=null)
        {
            if(estado.equals(ComprobanteEntity.ComprobanteEnumEstado.TODOS_SRI))
            {
                estadoStr=" AND ( u.estado=?5 or u.estado=?6 ) ";
            }else
            {
                estadoStr=" AND u.estado=?4 ";
            }
        }

                
        try {
            String queryString = "SELECT u FROM NotaCredito u WHERE 1=1 AND u.empresa=?7 AND " + cliente + fecha +estadoStr;
            Query query = getEntityManager().createQuery(queryString);
            
            if (persona != null) {
                query.setParameter(1, persona);
            }
            if (fi != null) {
                query.setParameter(2, fi);
            }
            if (ff != null) {
                query.setParameter(3, ff);
            }
            
            query.setParameter(7,empresa);
            
            if (estado != null) {
                if (estado.equals(ComprobanteEntity.ComprobanteEnumEstado.TODOS_SRI)) {
                    query.setParameter(5,ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());
                    query.setParameter(6,ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO_SRI.getEstado());
                    
                } else {
                    query.setParameter(4,estado.getEstado());
                }
            }
            

            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public BigDecimal buscarsSaldoAfectaNotasCredito(Factura factura)
    {
        /*NotaCredito nc;
        nc.getFactura();
        nc.getEstado();
        nc.getTotal();*/
        
        String queryString="SELECT sum(nc.total) FROM NotaCredito nc WHERE nc.factura=?1 and ( nc.estado=?2 or nc.estado=?3 )";
        Query query=entityManager.createQuery(queryString);
        query.setParameter(1,factura);
        query.setParameter(2,ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());
        query.setParameter(3,ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR.getEstado());
        if(query.getSingleResult()==null)
        {
            return BigDecimal.ZERO;
        }
        return (BigDecimal) query.getSingleResult();
    }

}
