/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.pos;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaPermiso;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Robert
 */
public class CajaPermisoFacade extends AbstractFacade<CajaPermiso>
{

    public CajaPermisoFacade() 
    {
        super(CajaPermiso.class);
    }
    
    public List<Usuario> buscarUsuariosPorSucursalYLigadosAUnaCaja(Sucursal sucursal, Caja caja)
    {
        try
        {
            String queryString = "Select distinct(cp.usuario) from CajaPermiso cp where cp.caja = ?1 and cp.caja.sucursal = ?2 and cp.estado = ?3";

            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1, caja);
            query.setParameter(2, sucursal);
            query.setParameter(3, GeneralEnumEstado.ACTIVO.getEstado());
            
            return query.getResultList();
        } 
        catch (NoResultException e) 
        {
            return null;
        }
    }
}
