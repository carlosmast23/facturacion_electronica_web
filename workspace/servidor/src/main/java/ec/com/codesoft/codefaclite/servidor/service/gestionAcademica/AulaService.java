/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.AulaFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Aula;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AulaServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author Carlos
 */
public class AulaService extends ServiceAbstract<Aula, AulaFacade> implements AulaServiceIf {

    private AulaFacade aulaFacade;

    public AulaService() throws RemoteException {
        super(AulaFacade.class);
        this.aulaFacade = new AulaFacade();
    }
    
    public List<Aula> obtenerAulasActivas() throws RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        return getFacade().findByMap(mapParametros);
    }

    /*
    public Aula grabar(Aula a) throws ServicioCodefacException {
        try {
            aulaFacade.create(a);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(AulaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("La clave principal ya existe en el sistema");
        } catch (DatabaseException ex) {
            Logger.getLogger(AulaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("Error con la base de datos aulas");
        }
        return a;
    }*/

    public void editar(Aula a) {
        aulaFacade.edit(a);
    }

    public void eliminar(Aula a) {
        a.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
        aulaFacade.edit(a);
    }

}
