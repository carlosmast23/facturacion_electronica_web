/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.EstudianteFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class EstudianteService extends ServiceAbstract<Estudiante, EstudianteFacade> implements EstudianteServiceIf {

    EstudianteFacade estudianteFacade;

    public EstudianteService() throws RemoteException {
        super(EstudianteFacade.class);
        estudianteFacade = new EstudianteFacade();
    }

    public List<Estudiante> estudianteSinMatriculaPorPeriodo(Periodo periodo) throws RemoteException {
        return estudianteFacade.getEstudiantesSinMatricula(periodo);
    }
    

    public List<Estudiante> estudianteNuevosSinMatricula() throws RemoteException {
        return estudianteFacade.getEstudiantesNuevos();
    }
    
    public List<Estudiante> obtenerEstudiantesActivos() throws RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        return getFacade().findByMap(mapParametros);
                
    }

    @Override
    public Estudiante grabar(Estudiante entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                
                EstudianteService servicio=new EstudianteService();
                Estudiante estudiante=servicio.buscarPorCedulayEstado(entity.getCedula(), GeneralEnumEstado.ACTIVO);
                
                /////
                if(estudiante!=null)
                {
                    throw new ServicioCodefacException("El estudiante ya existe en el sistema");
                }                        
               
                entity.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                entityManager.persist(entity);
            }
        });
        return entity;
    }
    
    

    public void eliminarEstudiante(Estudiante e)  throws RemoteException ,ServicioCodefacException {
        try {
            EstudianteInscritoService service=new EstudianteInscritoService();
            Long cantidadEstudiantesInscritos=service.obtenerTamanioPorEstudiante(e);
            if(cantidadEstudiantesInscritos>0)
            {
                throw new ServicioCodefacException("No se puede eliminar porque el estudiante esta inscrito");
            }
            else
            {
                ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                    @Override
                    public void transaccion() {
                        e.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                        entityManager.merge(e);
                    }
                });
            }
            
           
        } catch (RemoteException ex) {
            Logger.getLogger(EstudianteService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Estudiante buscarPorCedulayEstado(String cedula, GeneralEnumEstado estado) throws RemoteException, ServicioCodefacException {
        Map<String,Object> mapParametro=new HashMap<String,Object>();
        mapParametro.put("cedula",cedula);
        mapParametro.put("estado",estado.getEstado());
        
        List<Estudiante> resueltos=getFacade().findByMap(mapParametro);
        if(resueltos.size()==0)
            return null;
        else
            return resueltos.get(0);
        
    }

}
