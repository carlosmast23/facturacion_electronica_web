/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author
 */
public class EstudianteFacade extends AbstractFacade<Estudiante>{

    public EstudianteFacade() {
        super(Estudiante.class);
    }
    
    public List<Estudiante> getEstudiantesSinMatricula(Periodo periodo)
    {
        try {
            //Estudiante e;
            //e.getApellidos();
            //EstudianteInscrito ei;
            //ei.getEstudiante()
            //ei.getNivelAcademico().getPeriodo();            
            String queryString = "SELECT e FROM Estudiante e where "
                    + "( SELECT COUNT(ei.id) FROM EstudianteInscrito ei WHERE ei.estudiante=e AND ei.nivelAcademico.periodo=?1 ) "
                    + "=0";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1,periodo);
            return (List<Estudiante>) query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<Estudiante> getEstudiantesNuevos()
    {
        try {
            String queryString = "SELECT e FROM Estudiante e where "
                    + "( SELECT COUNT(ei.id) FROM EstudianteInscrito ei WHERE ei.estudiante=e and ei.estado=?1) "
                    + "=0";
            Query query = getEntityManager().createQuery(queryString);            
            query.setParameter(1,GeneralEnumEstado.ACTIVO.getEstado());
            return (List<Estudiante>) query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}
