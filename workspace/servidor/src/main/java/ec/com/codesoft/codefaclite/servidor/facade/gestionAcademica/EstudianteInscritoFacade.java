/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class EstudianteInscritoFacade extends AbstractFacade<EstudianteInscrito> {

    public EstudianteInscritoFacade() {
        super(EstudianteInscrito.class);
    }
    
    public Long obtenerTamanioEstudiatesInscritosPorEstudiante(Estudiante estudiante) 
    {

        EstudianteInscrito estudianteInscrito = new EstudianteInscrito();
        estudianteInscrito.getNivelAcademico();
        String queryString = "SELECT count(1) FROM EstudianteInscrito u WHERE u.estado=?1 and u.estudiante=?2 ";

        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(2, estudiante);
        return (Long) query.getSingleResult();

    }
    
    public Long obtenerTamanioEstudiatesInscritosPorCurso(NivelAcademico nivelAcademico)
    {
        EstudianteInscrito estudianteInscrito=new EstudianteInscrito();
        estudianteInscrito.getNivelAcademico();
        String queryString="SELECT count(1) FROM EstudianteInscrito u WHERE u.estado=?1 and u.nivelAcademico=?2 ";
        
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(2, nivelAcademico);
        return (Long) query.getSingleResult();
        
    }

    public List<EstudianteInscrito> obtenerEstudiantesInscritos(NivelAcademico nivel,Periodo periodo) {
        EstudianteInscrito estudianteInscrito;
        //estudianteInscrito.getEstudiante().getApellidos();
        //estudianteInscrito.getEstudiante().getNombres();
        //estudianteInscrito.getNivelAcademico().getPeriodo();
        
        String academico = "";
        if (nivel != null) {
            academico = "u.nivelAcademico=?1";
        } else {
            academico = "1=1";
        }

        try {
            String queryString = "SELECT u FROM EstudianteInscrito u WHERE u.estado=?2 AND " + academico;
            
            if(periodo!=null)
            {
                queryString+=" AND u.nivelAcademico.periodo=?3";
            }
            
            //Ordenar por el nivel del curso
            //queryString+=" order by u.estudiante.apellidos , u.estudiante.nombres, u.nivelAcademico.nivel.orden ";
            queryString+=" order by u.nivelAcademico.nivel.orden asc,u.nivelAcademico, u.estudiante.apellidos asc,u.estudiante.nombres asc";
            
            System.out.println(queryString);
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(2,GeneralEnumEstado.ACTIVO.getEstado());
            
            if(periodo!=null)
            {
                query.setParameter(3, periodo);
            }
            
            //System.err.println("QUERY--->"+query.toString());
            if (nivel != null) {
                query.setParameter(1, nivel);
            }
            
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<Object[]> consultarRepresentanteConEstudiantesYCursosFacade(Periodo periodoActivo)
    {
        //Estudiante e;
        //e.getEstado();
        //Persona cliente;
        //cliente.getEstado();
        //EstudianteInscrito ei;
        //ei.getEstado()
        //Estudiante e;
        //e.getRepresentante()
        
        //cliente.getnom
        String queryString="SELECT C.CLIENTE_ID,E.ESTUDIANTE_ID,EI.ID " +
        "    FROM CLIENTE C " +
        "    LEFT JOIN ESTUDIANTE E " +
        "    ON E.ESTADO=?1 AND (E.REPRESENTANTE1_ID=C.CLIENTE_ID OR E.REPRESENTANTE2_ID=C.CLIENTE_ID) " +
        "        LEFT JOIN ESTUDIANTE_INSCRITO EI " +
        "        ON EI.ESTADO=?2 AND (EI.ESTUDIANTE_ID=E.ESTUDIANTE_ID) "+
        "           LEFT JOIN NIVEL_ACADEMICO NA "+
        "           ON (NA.ID=EI.NIVEL_ACADEMICO_ID ) "+
        "               WHERE C.ESTADO=?3 " + 
        "               AND ((NA.PERIODO_ID IS NULL) OR (NA.PERIODO_ID IS NOT NULL AND NA.PERIODO_ID=?4)) "+ // Linea que me permite solo filtrar los estudiantes inscritos del periodo seleccionado        
        "               ORDER BY C.RAZON_SOCIAL ";
        
        System.out.println(queryString);
        //getEntityManager().createNativeQuery(queryString)
        Query query = getEntityManager().createNativeQuery(queryString);
        query.setParameter(1, GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(2, GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(3, GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(4, periodoActivo.getIdPeriodo());
        //query.setParameter(4, 10);
        /*List<Object[]> resultados=query.getResultList();
        
        List<Object[]> resultadosObjeto=new ArrayList<Object[]>();
        for (Object[] resultado : resultados) {
            Long id=resultado[0]
        }*/
        
        
        return (List<Object[]>) query.getResultList();
        
    }
    
    public List<EstudianteInscrito> buscarPorNivelAcademicoFacade(Periodo periodo, NivelAcademico nivel) throws ServicioCodefacException, java.rmi.RemoteException {
        /*EstudianteInscrito ei;
        ei.getNivelAcademico().getNombre()t
        ei.getEstudiante().getApellidos();
        ei.getEstudiante().getNombres();
        ei.getNivelAcademico().getNivel().getNombre();*/
        //ei.getNivelAcademico().getPeriodo();
        
        String wherePeriodo="";
        if(nivel!=null)
        {
            wherePeriodo=" AND u.nivelAcademico=?2 ";
        }else
        {
            wherePeriodo=" AND u.nivelAcademico.periodo=?2 ";
        }
        
        String queryString = "SELECT u FROM EstudianteInscrito u WHERE u.estado=?1  " + wherePeriodo +" ORDER BY u.nivelAcademico.nivel.orden asc ,u.nivelAcademico.nombre, u.estudiante.apellidos , u.estudiante.nombres ";
        
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1,GeneralEnumEstado.ACTIVO.getEstado());
         
        if(nivel!=null)
        {
           query.setParameter(2,nivel);
        }else
        {
            query.setParameter(2,periodo);
        }
        
        return query.getResultList();
    }

}
