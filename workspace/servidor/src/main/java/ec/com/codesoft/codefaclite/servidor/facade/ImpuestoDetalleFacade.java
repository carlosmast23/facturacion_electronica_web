/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author PC
 */
public class ImpuestoDetalleFacade extends AbstractFacade<ImpuestoDetalle>
{
    public ImpuestoDetalleFacade()
    {
        super(ImpuestoDetalle.class);
    }
    
     public List<ImpuestoDetalle> getImpuestoVigenteByName(String nombre)
    {
        /*
        Impuesto i;
        i.getNombre();
                
        ImpuestoDetalle id=new ImpuestoDetalle();
        id.getFechaInicio();
        id.getFechaFin();
        id.getImpuesto();
*/
        Date fechaHoy=new Date();
        
        //String queryString = "SELECT id FROM Impuesto i,ImpuestoDetalle id WHERE id.impuesto=i and  id.fechaInicio=>?1 and id.fechaFin<=?1 and i.nombre=?2 ";
        String queryString = "SELECT id "
                + "FROM Impuesto i, ImpuestoDetalle id WHERE "
                + "id.impuesto=i and  "
                + "((?1>=id.fechaInicio and ?1<=id.fechaFin) or id.fechaFin is null) and "
                + "i.nombre=?2";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1,fechaHoy);
        query.setParameter(2,nombre);
        return query.getResultList();
        
    }
       
}
