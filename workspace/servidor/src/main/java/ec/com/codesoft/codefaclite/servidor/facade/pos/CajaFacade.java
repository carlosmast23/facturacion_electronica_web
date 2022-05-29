/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.pos;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class CajaFacade extends AbstractFacade<Caja>{

    public CajaFacade() 
    {
        super(Caja.class);
    }
    
    public List<Caja> buscarCajasAutorizadasParaUsuario(Usuario usuario)
    {
        try
        {
            String queryString = "Select distinct(cp.caja) from CajaPermiso cp where cp.usuario = ?1";

            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1, usuario);
            return query.getResultList();
           
        } 
        catch (NoResultException e) 
        {
            return null;
        }
        
    }
    
}
