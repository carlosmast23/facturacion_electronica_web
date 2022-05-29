/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios.gestionacademica;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantilla;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceAbstractIf;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface RubroPlantillaEstudianteServiceIf extends ServiceAbstractIf<RubroPlantillaEstudiante>{
    public List<EstudianteInscrito> obtenerEstudiantesSinRegistrar(RubroPlantilla rubroPlantilla) throws RemoteException;
}
