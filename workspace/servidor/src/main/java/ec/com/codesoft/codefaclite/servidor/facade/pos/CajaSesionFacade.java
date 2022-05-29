/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.pos;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaSessionEnum;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Robert
 */
public class CajaSesionFacade extends AbstractFacade<CajaSession> {
    
    public CajaSesionFacade() {
        super(CajaSession.class);
    }
    
    public CajaSession obtenerUltimaCajaSession(Caja caja) 
    {
        try
        {
            String stringQuery = "Select cs from CajaSession cs where cs.caja = ?1 and cs.estadoCierreCaja = ?2 order by cs.fechaHoraCierre desc";

            Query query = getEntityManager().createQuery(stringQuery);
            query.setParameter(1, caja);
            query.setParameter(2, CajaSessionEnum.FINALIZADO.getEstado());

            List<CajaSession> resultadoLista= query.getResultList();
            
            if(resultadoLista.size()>0)
            {
                return resultadoLista.get(0);
            }
        } 
        catch (NoResultException e) 
        {
           return null;
        }
        
        return null;
    }
    
    public List<CajaSession> obtenerCajaSessionPorCajaUsuarioYFecha(Caja caja, Usuario usuario, Date fechaInicio, Date fechaFin)
    {
        try
        {
            Timestamp fechaInicioTimeStamp = UtilidadesFecha.castDateSqlToTimeStampSql(fechaInicio);
            Timestamp fechaFinTimeStamp = UtilidadesFecha.castDateSqlToTimeStampSql(fechaFin);
            
            String stringQuery = "Select cs from CajaSession cs where cs.caja = ?1 and cs.usuario = ?2 and cs.estadoCierreCaja = ?3 ";
            String queryStringFecha="";
            
            if(fechaInicio!=null)
            {
                queryStringFecha+=" AND  cs.fechaHoraApertura>=?4 ";
            }

            if(fechaFin!=null)
            {
                queryStringFecha+=" AND  cs.fechaHoraCierre<=?5 ";
            }

            stringQuery += queryStringFecha;
            Query query = getEntityManager().createQuery(stringQuery);
            query.setParameter(1, caja);
            query.setParameter(2, usuario);
            query.setParameter(3, CajaSessionEnum.FINALIZADO.getEstado());

            if(fechaInicio!=null)
            {
                query.setParameter(4, fechaInicioTimeStamp);
            }

            if(fechaFin!=null)
            {
                query.setParameter(5, fechaFinTimeStamp);
            }

            return query.getResultList();
            
        }catch (NoResultException e) {
            return null;
        }
    }
    
}
