/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.DescuentoAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantilla;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaMes;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.AplicarDescuentoAcademicoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface RubroEstudianteServiceIf extends ServiceAbstractIf<RubroEstudiante> {
    
    /**
     * Obtiene los rubros de matricula por el estudiante inscrito y que este activo
     * @param estudianteInscrito
     * @return
     * @throws RemoteException 
     */
    public List<RubroEstudiante> obtenerRubroMatriculaPorEstudianteInscrito(EstudianteInscrito estudianteInscrito) throws RemoteException;

    public List<RubroEstudiante> obtenerRubrosActivosPorEstudiantesInscrito(EstudianteInscrito estudianteInscrito) throws RemoteException;
    
    public List<RubroEstudiante> obtenerRubrosEstudiantesPorRubros(List<RubrosNivel> rubros) throws RemoteException;
    
    public RubroPlantilla crearRubroEstudiantesDesdePlantila(RubroPlantilla rubroPlantilla, MesEnum mesEnum, String nombreRubroMes,Integer anio,DescuentoAcademico descuentoAcademico,AplicarDescuentoAcademicoEnum aplicarDescuentoEnum) throws RemoteException,ServicioCodefacException;

    //public void crearRubrosEstudiantes(List<EstudianteInscrito> estudiantes, RubrosNivel rubroNivel) throws RemoteException;
    public void crearRubrosEstudiantes(List<EstudianteInscrito> estudiantes, RubrosNivel rubroNivel) throws RemoteException ,ServicioCodefacException;

    public void crearRubrosEstudiantes(List<RubroEstudiante> rubrosEstudiantes) throws RemoteException;
    
    
    public void eliminarRubrosEstudiantes(List<RubroEstudiante> rubrosEstudiantes) throws RemoteException;
            

    public List<RubroEstudiante> obtenerDeudasEstudiante(Estudiante estudiante,Periodo periodo) throws java.rmi.RemoteException;

    public List<Object[]> obtenerRubroPeriodoGrupo(Periodo periodo,Date fechaInicio,Date fechaFin) throws RemoteException;

    public List<RubroEstudiante> buscarRubrosMes(EstudianteInscrito est,Periodo periodo, CatalogoProducto catalogoProducto, List<RubroPlantillaMes> meses) throws java.rmi.RemoteException;
    
    public Long contarRubrosEstudiantePorRubroNivel(RubrosNivel rubroNivel) throws RemoteException;
    
    public void actualizarRubrosEstudiante(List<RubroEstudiante> rubroEstudiantes) throws RemoteException;
    
    public void eliminarMesRubroPlantilla(RubroPlantillaMes rubroPlantillaMes) throws RemoteException, ServicioCodefacException;
    
    public List<RubroEstudiante> consultarPorEstudianteInscritoSinFacturar(EstudianteInscrito estudianteInscrito) throws RemoteException;
    
    public List<RubroEstudiante> buscarPorEstudianteInscritoYRubroNivel(EstudianteInscrito estudianteInscrito,RubrosNivel rubroNivel) throws ServicioCodefacException, RemoteException;
    
    public List<RubroEstudiante> buscarPorEstudianteInscritoYRubroNivelActivos(EstudianteInscrito estudianteInscrito, RubrosNivel rubroNivel) throws ServicioCodefacException, RemoteException;
    
}
