/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class PeriodoFacade extends AbstractFacade<Periodo>{

    public PeriodoFacade() {
        super(Periodo.class);
    }
    
    public List<Periodo> getPeriodosSinEliminar() {
        String queryString = "SELECT u FROM Periodo u WHERE u.estado<>?1 ";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, GeneralEnumEstado.ELIMINADO.getEstado());

        return (List<Periodo>) query.getResultList();
    }
}
