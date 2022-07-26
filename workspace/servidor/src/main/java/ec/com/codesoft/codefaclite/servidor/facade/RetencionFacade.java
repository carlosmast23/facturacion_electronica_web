/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author
 */
public class RetencionFacade extends AbstractFacade<Retencion> {

    public RetencionFacade() {
        super(Retencion.class);
    }
    
    public List<RetencionDetalle> obtenerRetencionesReportesFacade(Persona persona, Date fi, Date ff, SriRetencionIva iva, SriRetencionRenta renta, SriRetencion sriRetencion,ComprobanteEntity.ComprobanteEnumEstado estadoEnum,Empresa empresa) {
        //RetencionDetalle rd;
        //rd.getRetencion().getEstado();
        //rd.getCodigoRetencionSri();
        //rd.getCodigoRetencionSri();
        //rd.getRetencion().getFechaEmision();
        //rd.getRetencion().getProveedor();
        //String queryString = "SELECT d FROM RetencionDetalle d where d.retencion.estado<>?1 ";
        String queryString = "SELECT d FROM RetencionDetalle d where d.retencion.empresa=?11 ";
        
        if(persona!=null)
        {
            queryString+=" and d.retencion.proveedor=?2 ";
        }
        
        if(fi!=null)
        {
            queryString+=" and d.retencion.fechaEmision>=?3 ";
        }
        
        if(ff!=null)
        {
            queryString+=" and d.retencion.fechaEmision<=?4 ";
        }
        
        if(sriRetencion!=null)
        {
            queryString+=" and d.codigoSri=?5 ";
        }
        
        if(iva!=null)
        {
            queryString+=" and d.codigoRetencionSri=?6 ";
        }
        
        if(renta!=null)
        {
            queryString+=" and d.codigoRetencionSri=?7 ";
        }
        
        if(estadoEnum!=null)
        {
            if(estadoEnum.equals(ComprobanteEntity.ComprobanteEnumEstado.TODOS_SRI))
            {
                queryString+=" and (d.retencion.estado=?9 or d.retencion.estado=?10 ) ";
            }
            else
            {
                queryString+=" and d.retencion.estado=?8 ";
            }
        }
        
        Query query = getEntityManager().createQuery(queryString);
        //query.setParameter(1,ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado());
        
        if(persona!=null)
        {
            query.setParameter(2,persona);
        }
        
        if(fi!=null)
        {
            query.setParameter(3,fi);
        }
        
        if(ff!=null)
        {
            query.setParameter(4,ff);
        }
        
        if(sriRetencion!=null)
        {
            query.setParameter(5,sriRetencion.getCodigo());
        }
        
        if(iva!=null)
        {
            query.setParameter(6,iva.getCodigo().toString());
        }
        
        if(renta!=null)
        {
            query.setParameter(7,renta.getCodigo().toString());
        }

        if (estadoEnum != null)
        {
            if(estadoEnum.equals(ComprobanteEntity.ComprobanteEnumEstado.TODOS_SRI))
            {
                query.setParameter(9,ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());
                query.setParameter(10,ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO_SRI.getEstado());
            }else
            {
                query.setParameter(8,estadoEnum.getEstado());
            }
            
        }

        query.setParameter(11,empresa);
        
        
        return query.getResultList();        
    }
    

    @Deprecated //TODO: Ya se esta generando una nueva version que se llama obtener reteneciones reportes facade
    public List<RetencionDetalle> lista(Persona persona, Date fi, Date ff, SriRetencionIva iva, SriRetencionRenta renta, String tipo) {
        String proveedor = "", fecha = "", retiva = "", retrenta = "", tipor = "";
        if (persona != null) {
            proveedor = "r.proveedor=?1";
        } else {
            proveedor = "1=1";
        }
        if (fi == null && ff != null) {
            fecha = " AND r.fechaEmision <= ?3";
        } else if (fi != null && ff == null) {
            fecha = " AND r.fechaEmision <= ?2";
        } else if (fi == null && ff == null) {
            fecha = "";
        } else {
            fecha = " AND (r.fechaEmision BETWEEN ?2 AND ?3)";
        }

        if (iva != null) {
            retiva = "e.sriRetencionIva=?4";
        } else {
            retiva = "1=1";
        }

        if (renta != null) {
            retrenta = "e.sriRetencionRenta=?5";
        } else {
            retrenta = "1=1";
        }
        if (tipo != null) {
            if (tipo.compareTo("IVA")==0) {
                tipo = "2";
            }
            if (tipo.compareTo("RENTA")==0) {
                tipo = "1";
            }
            tipor = "d.codigoSri=?6";
        } else {
            tipor = "1=1";
        }

        try {//INNER JOIN Retencion r ON d.retencion=r.id  
            String queryString = "SELECT d FROM RetencionDetalle d WHERE " + tipor + " AND d.retencion.id IN(SELECT r.id FROM Retencion r WHERE " + proveedor + fecha + " AND r.compra.id IN(SELECT e.compra.id FROM CompraDetalle e WHERE " + retiva + " AND " + retrenta + ") )";
            Query query = getEntityManager().createQuery(queryString);
            //System.err.println("QUERYD---JC--->" + query.toString());
            if (persona != null) {
                query.setParameter(1, persona);
            }
            if (fi != null) {
                query.setParameter(2, fi);
            }
            if (ff != null) {
                query.setParameter(3, ff);
            }
           
            if (iva != null) {
                query.setParameter(4, iva);
            }
           /* if (renta != null) {
                query.setParameter(5, renta);
            }*/
            if (tipo != null) {
                query.setParameter(6, tipo);
            }
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<RetencionDetalle> obtenerRetencionesRentaPorCompraFacade(Compra compra,SriRetencion sriRetencion) throws RemoteException
    {
        String queryString="SELECT rd FROM RetencionDetalle rd WHERE rd.retencion.compra=?1 and rd.retencion.estado<>?2 and rd.codigoSri=?3 ";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1,compra);
        query.setParameter(2,ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado());
        query.setParameter(3, sriRetencion.getCodigo());
        
        return query.getResultList();
    }
    
    public List<Object[]> obtenerRetencionesIvaPorCompraFacade(Compra compra,SriRetencion sriRetencion)throws RemoteException
    {
        RetencionDetalle rd;
        //rd.getRetencion().getCompra();
        //rd.setCodigoSri(claveDb);
        //rd.getPorcentajeRetener();
        //rd.getValorRetenido();
        //rd.getRetencion().getEstado();
       
        String queryString="SELECT rd.porcentajeRetener,sum(rd.valorRetenido) FROM RetencionDetalle rd WHERE rd.retencion.compra=?1 and rd.retencion.estado<>?2 and rd.codigoSri=?3 group by rd.porcentajeRetener ";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1,compra);
        query.setParameter(2,ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado());
        query.setParameter(3, sriRetencion.getCodigo());
        
        return query.getResultList();
    }

    public List<Object[]> retencionesCodigo(Persona persona, Date fi, Date ff, SriRetencionIva iva, SriRetencionRenta renta, String tipo) {
        String proveedor = "", fecha = "", retiva = "", retrenta = "", tipoc = "";
        if (persona != null) {
            proveedor = "r.proveedor=?1";
        } else {
            proveedor = "1=1";
        }
        if (fi == null && ff != null) {
            fecha = " AND r.fechaEmision <= ?3";
        } else if (fi != null && ff == null) {
            fecha = " AND r.fechaEmision <= ?2";
        } else if (fi == null && ff == null) {
            fecha = "";
        } else {
            fecha = " AND (r.fechaEmision BETWEEN ?2 AND ?3)";
        }

        if (iva != null) {
            retiva = "e.sriRetencionIva=?4";
        } else {
            retiva = "1=1";
        }

        if (renta != null) {
            retrenta = "e.sriRetencionRenta=?5";
        } else {
            retrenta = "1=1";
        }
        if (tipo != null) {
            tipoc = "d.codigoSri=?6";
        } else {
            tipoc = "1=1";
        }

        try {
            String queryString = "SELECT d.codigoRetencionSri,SUM(d.valorRetenido) FROM RetencionDetalle d WHERE " + tipoc + " AND d.retencion.id IN(SELECT r.id FROM Retencion r WHERE " + proveedor + fecha + " AND r.compra.id IN(SELECT e.compra.id FROM CompraDetalle e WHERE " + retiva + " AND " + retrenta + ")) GROUP BY d.codigoRetencionSri";
            //String queryString = "SELECT d.codigoSri,SUM(d.valorRetenido) FROM RetencionDetalle d GROUP BY d.codigoSri";
            Query query = getEntityManager().createQuery(queryString);
            //System.err.println("QUERY 2 --->" + query.toString());
            if (persona != null) {
                query.setParameter(1, persona);
            }
            if (fi != null) {
                query.setParameter(2, fi);
            }
            if (ff != null) {
                query.setParameter(3, ff);
            }
            if (iva != null) {
                query.setParameter(4, iva);
            }
            if (renta != null) {
                query.setParameter(5, renta);
            }
            if (tipo != null) {
                query.setParameter(6, tipo);
            }
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<Retencion> obtenerRetencionesPorCompraFacade(Compra compra)
    {
        Retencion retencion;
        //retencion.getEstado();
        //retencion.getCompra();
        //retencion.getEs
        
        String queryString = "SELECT r FROM Retencion r WHERE r.estado<>?1 and r.estado<>?2 and r.compra=?3 ";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado());
        query.setParameter(2, ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO_SRI.getEstado());
        query.setParameter(3, compra);
        
        return query.getResultList();
    }
    
    public List<Retencion> obtenerRetencionPorPreimpresoyProveedor(String preimpresoCompra, Persona persona)
    {

        String queryString = "SELECT r FROM Retencion r WHERE r.estado<>?1 and r.estado<>?2 and r.proveedor=?3 and r.preimpresoDocumento=?4 ";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado());
        query.setParameter(2, ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO_SRI.getEstado());
        query.setParameter(3, persona);
        query.setParameter(4, preimpresoCompra);
        
        return query.getResultList();
    }
    

}
