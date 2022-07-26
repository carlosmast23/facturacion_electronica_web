/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantilla;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaEstudiante;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author
 */
public class RubroPlantillaEstudianteFacade extends AbstractFacade<RubroPlantillaEstudiante>{
    
    public RubroPlantillaEstudianteFacade() {
        super(RubroPlantillaEstudiante.class);
    }
    
    public List<EstudianteInscrito> getEstudiantesSinRegistrar(RubroPlantilla rubroPlantilla)
    {
        //EstudianteInscrito ei;
        //RubroPlantillaEstudiante rpe;
        //rpe.getRubroPlantilla()

        String subQueryString="SELECT count(rpe2.id) RubroPlantillaEstudiante rpe2 WHERE rpe2.estudianteInscrito=ei AND rp2.rubroPlantilla=?1";
                
        String queryString=" SELECT ei EstudianteInscrito ei,RubroPlantillaEstudiante rpe  WHERE rpe.rubroPlantilla=?1 AND "+subQueryString+">0";
        
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1,rubroPlantilla);
        
        return query.getResultList();

    }
    
}
