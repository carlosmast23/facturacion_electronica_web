/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.OrdenTrabajoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class PresupuestoFacade extends AbstractFacade<Presupuesto> {

    public PresupuestoFacade() {
        super(Presupuesto.class);
    }

    public List<OrdenTrabajoDetalle> listarOrdenTrabajo(OrdenTrabajo ordenTrabajo) {
        String queryString = " Select DISTINCT (otd) From Presupuesto p INNER JOIN p.ordenTrabajoDetalle otd where otd.ordenTrabajo = ?1";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, ordenTrabajo);
        return query.getResultList();
    }

    public List<Presupuesto> consultarPresupuestos(Date fechaInicial, Date fechaFinal, Persona cliente, Presupuesto.EstadoEnum estadoEnum) {
        //Presupuesto presupuesto;
        //presupuesto.getEstado();        
        String queryString = " Select DISTINCT p From Presupuesto p where 1=1 ";

        if (fechaInicial != null) {
            queryString += " AND p.fechaPresupuesto>=?1";
        }

        if (fechaFinal != null) {
            queryString += " AND p.fechaPresupuesto<=?2";
        }

        if (cliente != null) {
            queryString += " AND p.persona=?3";
        }

        if (estadoEnum != null) {
            queryString += " AND p.estado=?4";
        }

        Query query = getEntityManager().createQuery(queryString);

        if (fechaInicial != null) {
            query.setParameter(1, fechaInicial);
        }

        if (fechaFinal != null) {
            query.setParameter(2, fechaFinal);
        }

        if (cliente != null) {
            query.setParameter(3, cliente);
        }

        if (estadoEnum != null) {
            query.setParameter(4, estadoEnum.getLetra());
        }
        return query.getResultList();
    }
}
