/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import java.sql.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class SriFormaPagoFacade extends AbstractFacade<SriFormaPago> {
    
    public SriFormaPagoFacade() {
        super(SriFormaPago.class);
    }
    
    public List<SriFormaPago> getFormaPagoByDate(Date fecha)
    {
        String queryString = "SELECT e FROM SriFormaPago e WHERE ?1>=e.fechaInicio AND ?1<=e.fechaFin";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, fecha);
        return (List<SriFormaPago>) query.getResultList();
    }
    
}
