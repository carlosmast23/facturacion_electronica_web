/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author
 */
public class AtsFacade extends AbstractFacade<Object>{
    
    public AtsFacade() {
        super(Object.class);
    }
    
    public void consultarVentasAts()
    {
        /*
        Factura factura;
        factura.getEstad
        
        try {
            String queryString = "SELECT u FROM Factura F WHERE F.estado=?1  GROUP BY F.Cliente";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1, ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO); //TODO: Ahorita solo consulta los documentos autorizados
            query.setParameter(2, clave);
            return (Usuario) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }*/
    }
    
    
    
}
