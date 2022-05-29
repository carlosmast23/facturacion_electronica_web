/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OrdenarEnum;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author PC
 */
public class EmpresaFacade extends AbstractFacade<Empresa>
{
    
    public EmpresaFacade() 
    {
        super(Empresa.class);
    }
    
    public List<Empresa> obtenerTodosActivosFacade(OrdenarEnum ordenarEnum)
    {
        try
        {   
            String queryString = "SELECT e FROM Empresa e WHERE e.estado=?1 ORDER BY e.orden "+ ordenarEnum.getValor();
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1,GeneralEnumEstado.ACTIVO.getEstado());
            return (List<Empresa>) query.getResultList();
        }
        catch(NoResultException e)
        {
            return null;
        }
    }
    
}
