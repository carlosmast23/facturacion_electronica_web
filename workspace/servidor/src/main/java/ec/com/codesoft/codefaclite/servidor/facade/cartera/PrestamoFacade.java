/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.cartera;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Prestamo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.PrestamoCuota;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author
 */
public class PrestamoFacade extends AbstractFacade<Prestamo> {

    public PrestamoFacade() {
        super(Prestamo.class);
    }
    
    public List<PrestamoCuota> buscarCuotasPorPrestamo(Prestamo prestamo){
                
        try {
            //PrestamoCuota pc;
            //pc.getPrestamo()
            //pc.getNumeroCuota()
            String queryString = "SELECT u FROM PrestamoCuota u WHERE u.prestamo<>?1 ORDER BY u.numeroCuota ";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1, prestamo);
            //query.setParameter(2, Factura.EstadoNotaCreditoEnum.ANULADO_PARCIAL.getEstado());
            //query.setParameter(3, ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR.getEstado());
            return (List<PrestamoCuota>) query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
