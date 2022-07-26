/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriIdentificacion;
import java.sql.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author
 */
public class SriIdentificacionFacade extends AbstractFacade<SriIdentificacion> {
    
    public SriIdentificacionFacade() {
        super(SriIdentificacion.class);
    }
    
    public List<SriIdentificacion> getSriIdentificacionByTipoTransaccion(String tipo)
    {
        String queryString = "SELECT e FROM SriIdentificacion e WHERE ?1=e.tipoTransaccion";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, tipo);
        return (List<SriIdentificacion>) query.getResultList();
    }
        
}
