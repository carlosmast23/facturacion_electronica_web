/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class UsuarioFacade extends AbstractFacade<Usuario> {
    
    public UsuarioFacade() 
    {
        super(Usuario.class);
    }
    
    @Deprecated //Metodo que comprara directamente las claves
    public Usuario login(String nick, String clave)
    {
        try
        {
            String queryString = "SELECT u FROM Usuario u WHERE u.nick=?1 AND u.clave=?2";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1,nick);
            query.setParameter(2,clave);
            return (Usuario) query.getSingleResult();
        }
        catch(NoResultException e)
        {
            return null;
        }
    }
    
}
