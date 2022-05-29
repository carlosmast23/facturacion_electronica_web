/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.RubroPlantillaEstudianteFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantilla;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroPlantillaEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.gestionacademica.RubroPlantillaEstudianteServiceIf;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class RubroPlantillaEstudianteService extends ServiceAbstract<RubroPlantillaEstudiante, RubroPlantillaEstudianteFacade> implements RubroPlantillaEstudianteServiceIf{

    private RubroPlantillaEstudianteFacade rubroPlantillaEstudianteFacade;
            
    public RubroPlantillaEstudianteService() throws RemoteException {
        super(RubroPlantillaEstudianteFacade.class);
        this.rubroPlantillaEstudianteFacade=new RubroPlantillaEstudianteFacade();
    }
    
    public List<EstudianteInscrito> obtenerEstudiantesSinRegistrar(RubroPlantilla rubroPlantilla) throws RemoteException
    {        
        return this.rubroPlantillaEstudianteFacade.getEstudiantesSinRegistrar(rubroPlantilla);
    }
    
}
