/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionIva;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencionRenta;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author
 */
public class SriRetencionIvaFacade extends AbstractFacade<SriRetencionIva>{

    public SriRetencionIvaFacade() {
        super(SriRetencionIva.class);
    }

    public List<SriRetencionIva> obtenerTodosOrdenadoPorCodigoFacade() {
        //SriRetencionIva u;
        //u.getCodigo();
        try {            
            String queryString = "SELECT u FROM SriRetencionIva u order by u.codigo desc";
            Query query = getEntityManager().createQuery(queryString);
            return (List<SriRetencionIva>) query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
}
