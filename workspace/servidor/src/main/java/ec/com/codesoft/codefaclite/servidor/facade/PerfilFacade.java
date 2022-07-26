/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PerfilUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author
 */
public class PerfilFacade extends AbstractFacade<Perfil>{

    public PerfilFacade() {
        super(Perfil.class);
    }
    
    public List<Perfil> getPerfilesByUsuario(Usuario usuario)
    {
        try
        {
            /*
            PerfilUsuario perfilUsuario;
            perfilUsuario.getPerfil();
            Perfil perfil;*/
            //"SELECT c1, c2 FROM Country c1 INNER JOIN c1.neighbors c2";
            
            String queryString = "SELECT p FROM PerfilUsuario pu INNER JOIN pu.perfil p WHERE pu.usuario=?1";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1,usuario);
            return (List<Perfil>) query.getResultList();
        }
        catch(NoResultException e)
        {
            return null;
        }
    }
   
    
   
}
