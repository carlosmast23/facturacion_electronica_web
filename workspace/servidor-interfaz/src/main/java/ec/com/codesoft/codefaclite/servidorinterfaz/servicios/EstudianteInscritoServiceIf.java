/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 */
public interface EstudianteInscritoServiceIf extends ServiceAbstractIf<EstudianteInscrito>{
    
    /**
     * Obtiene los estudiantes inscritos que pertenescan a ese Estudiante y a el periodo
     */
    public List<EstudianteInscrito> obtenerEstudiantesInscritosPorPeriodoYEstudiante(Periodo periodo,Estudiante estudiante) throws RemoteException ;
    /**
     * Metodo que permite registrar un estudiante y crea el rubro de matricula para que pueda facturar posteriormente
     */
    public EstudianteInscrito matricularEstudiante(EstudianteInscrito estudianteInscrito,RubroEstudiante rubroMatricula) throws RemoteException,ServicioCodefacException;
    
    public void matriculaEstudianteByList(List<EstudianteInscrito> estudiantesPorMatricular) throws RemoteException;
    
    public void matricularEstudiantesByMap(Map<NivelAcademico,List<Estudiante>> mapEstudiantes) throws RemoteException;
    
    public List<EstudianteInscrito> obtenerEstudiantesInscritos(NivelAcademico nivel,Periodo periodo) throws java.rmi.RemoteException;
    
    public List<EstudianteInscrito> buscarPorNivelAcademico(Periodo periodo,NivelAcademico nivel) throws ServicioCodefacException, java.rmi.RemoteException;
    
    public List<EstudianteInscrito> obtenerEstudiantesInscritosPorPeriodo(Periodo periodo) throws RemoteException;
    
    public void eliminarEstudiantesInscrito(List<EstudianteInscrito> estudiantesEliminar) throws RemoteException;
    
    public Long obtenerTamanioEstudiatesInscritosPorCurso(NivelAcademico nivelAcademico) throws RemoteException;
    
    public List<Object[]> consultarRepresentanteConEstudiantesYCursos() throws RemoteException;
    
    public EstudianteInscrito obtenerPorEstudianteYNivelYEstado(Estudiante estudiante,NivelAcademico nivel,GeneralEnumEstado estado ) throws RemoteException;
    
    public EstudianteInscrito buscarEstudianteMatriculadoPeriodoActivo(Estudiante estudiante) throws ServicioCodefacException, java.rmi.RemoteException;
    
}
