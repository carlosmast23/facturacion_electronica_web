/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Ruta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RutaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DiaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @auhor
 */
public class RutaFacade extends AbstractFacade<Ruta> {

    public RutaFacade() {
        super(Ruta.class);
    }
    
    public List<Ruta> consultarRutaActivaPorVendedorYClienteFacade(Empleado vendedor,PersonaEstablecimiento clienteOficina,DiaEnum diaEnum)
    {
        /*Ruta ruta;
        ruta.getDiaVisita();
        ruta.getEstado();
        ruta.getVendedor();
        RutaDetalle rutaDetalle;
        rutaDetalle.getEstablecimiento();*/
        
        String queryStr="SELECT r FROM RutaDetalle rd JOIN rd.ruta r WHERE r.estado=?1 and r.vendedor=?2 and rd.establecimiento=?3 and r.diaVisita=?4 ";
        Query query=getEntityManager().createQuery(queryStr);
        query.setParameter(1,GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(2,vendedor);
        query.setParameter(3,clienteOficina);
        query.setParameter(4,diaEnum.getNumero());
        return query.getResultList();
    }
    
}
