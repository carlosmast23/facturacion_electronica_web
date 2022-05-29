/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.NivelAcademicoFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelAcademicoServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class NivelAcademicoService extends ServiceAbstract<NivelAcademico, NivelAcademicoFacade> implements NivelAcademicoServiceIf {

    private NivelAcademicoFacade nivelAcademicoFacade;
    
    public NivelAcademico obtenerPorNombreYEstadoYPeriodo(String nombre,GeneralEnumEstado estado,Periodo periodo) throws RemoteException
    {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("nombre", nombre);
        mapParametros.put("estado", estado.getEstado());
        mapParametros.put("periodo", periodo);
        
        List<NivelAcademico> resultados=getFacade().findByMap(mapParametros);
        if(resultados.size()>0)
        {
            return resultados.get(0);
        }
        return null;
    }
    
    public NivelAcademico obtenerPorNombreYEstado(String nombre,GeneralEnumEstado estado) throws RemoteException
    {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("nombre", nombre);
        mapParametros.put("estado", estado.getEstado());
        
        List<NivelAcademico> resultados=getFacade().findByMap(mapParametros);
        if(resultados.size()>0)
        {
            return resultados.get(0);
        }
        return null;
    }
    
    public List<NivelAcademico> obtenerTodosActivosPorPeriodo(Periodo periodo) throws RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();        
        mapParametros.put("periodo",periodo);
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        return getFacade().findByMap(mapParametros);
    }

    public NivelAcademicoService() throws RemoteException {
        super(NivelAcademicoFacade.class);
    }

    public void eliminarNivelAcademico(NivelAcademico n) throws RemoteException,ServicioCodefacException {
        
        try {
            EstudianteInscritoService servicio=new EstudianteInscritoService();
            Long cantidadEstudiantesInscritos=servicio.obtenerTamanioEstudiatesInscritosPorCurso(n);
            
            //Si no existe ningun estudiante registrado entonces puede borrar los dato
            if(cantidadEstudiantesInscritos==0)
            {
                ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                    @Override
                    public void transaccion() {
                        n.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                        entityManager.merge(n);
                    }
                });                
            }
            else
            {
                throw new ServicioCodefacException("No se puede eliminar el curso porque existen datos registrados ");
            }
           
        } catch (RemoteException ex) {
            Logger.getLogger(NivelAcademicoService.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    public List<NivelAcademico> buscarPorPeriodo(Periodo p) throws RemoteException, ServicioCodefacException {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("periodo", p);
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());        
        return getFacade().findByMap(mapParametros);

    }
    

}
