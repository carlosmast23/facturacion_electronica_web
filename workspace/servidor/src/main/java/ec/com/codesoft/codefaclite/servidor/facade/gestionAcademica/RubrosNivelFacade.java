/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaMes;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import java.rmi.RemoteException;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class RubrosNivelFacade extends AbstractFacade<RubrosNivel> {

    public RubrosNivelFacade() {
        super(RubrosNivel.class);
    }

    public List<RubrosNivel> findPorPeriodoYMeses(Periodo periodo, CatalogoProducto catalogoProducto, List<RubroPlantillaMes> meses) throws RemoteException {

        String stringQuery = "SELECT rn FROM RubrosNivel rn WHERE rn.catalogoProducto=?1 AND rn.periodo=?2 AND ";

        String stringQueryMeses = "";

        //Solo van entre parentesis si tiene mas datos que 1
        if (meses.size() > 1) {
            stringQueryMeses = "( ";
        }
        
        if (meses.size() > 0) {
            for (RubroPlantillaMes mes : meses) {
                stringQueryMeses = stringQueryMeses + " rn.mesNumero=" + mes.getMesEnum().getNumero() + " AND rn.anio="+mes.getAnio()+ " OR";
            }
        }

        //Cortar el ultimo OR que sobra de la candena;
        if(stringQueryMeses.length()>0)
        {
            stringQueryMeses = stringQueryMeses.substring(0, stringQueryMeses.length() - 3);
        }

        if (meses.size() > 1) {
            stringQueryMeses = stringQueryMeses + ")";
        }

        //Une los querys parciales y genera uno total
        stringQuery += stringQueryMeses;

        Query query = getEntityManager().createQuery(stringQuery);
        query.setParameter(1, catalogoProducto);
        query.setParameter(2, periodo);

        return query.getResultList();
    }

}
