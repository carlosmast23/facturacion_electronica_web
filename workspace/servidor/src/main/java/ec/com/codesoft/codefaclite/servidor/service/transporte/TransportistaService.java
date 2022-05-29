/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.transporte;

import ec.com.codesoft.codefaclite.servidor.facade.BodegaFacade;
import ec.com.codesoft.codefaclite.servidor.facade.transporte.TransportistaFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Transportista;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.TransportistaServiceIf;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author CodesoftDesarrollo
 */
public class TransportistaService extends ServiceAbstract<Transportista, TransportistaFacade> implements TransportistaServiceIf
{
    private TransportistaFacade transportistaFacade;

    public TransportistaService() throws RemoteException
    {
        super(TransportistaFacade.class);
        this.transportistaFacade = new TransportistaFacade();   
    }
    /*
    public Transportista grabar(Transportista t) throws ServicioCodefacException {
        try {
            transportistaFacade.create(t);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(Transportista.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("La clave principal ya existe en el sistema");
        } catch (DatabaseException ex) {
            Logger.getLogger(Transportista.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("Error con la base de datos");
        }
        return t;
    }*/

    public void editar(Transportista t) {
        this.transportistaFacade.edit(t);
    }

    public void eliminar(Transportista t) {
        t.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
        this.transportistaFacade.edit(t);
    }
    
    
            
}
