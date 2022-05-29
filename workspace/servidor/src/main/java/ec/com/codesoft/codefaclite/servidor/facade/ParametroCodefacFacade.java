/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import java.util.List;
import javax.persistence.FlushModeType;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class ParametroCodefacFacade extends AbstractFacade<ParametroCodefac>
{
    
    public ParametroCodefacFacade() 
    {
        super(ParametroCodefac.class);
    }
    
    public List<ParametroCodefac> getParametrosMapByEmpresa(Empresa empresa) 
    {
        //ParametroCodefac parametroCodefac;
        //parametroCodefac.getEmpresa()
        String queryString = "SELECT p FROM ParametroCodefac p WHERE p.empresa=?1 ";
        
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1,empresa); 
        query.setFlushMode(FlushModeType.COMMIT);
        return query.getResultList();
    }
    
}

