/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.transporte;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DestinatarioGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class DestinatarioGuiaRemisionFacade extends AbstractFacade<DestinatarioGuiaRemision> {
    
    public DestinatarioGuiaRemisionFacade() {
        super(DestinatarioGuiaRemision.class);
    }
    
    
    public List<DestinatarioGuiaRemision> obtenerGuiaRemision(Factura factura)
    {
        List<DestinatarioGuiaRemision> resultado=new ArrayList<DestinatarioGuiaRemision>();
        try {            
            String queryString = "SELECT u FROM DestinatarioGuiaRemision u WHERE u.facturaReferencia=?1";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1, factura);
            
            //List<DestinatarioGuiaRemision> resultadoList= (List<DestinatarioGuiaRemision>) query.getResultList();
            resultado= (List<DestinatarioGuiaRemision>) query.getResultList();                       
            
        } catch (NoResultException e) {
            e.printStackTrace();
            //return null;
        }
        return resultado;
    }
    
}
