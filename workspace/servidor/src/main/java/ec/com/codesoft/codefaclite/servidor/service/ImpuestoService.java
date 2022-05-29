/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidor.facade.ImpuestoDetalleFacade;
import ec.com.codesoft.codefaclite.servidor.facade.ImpuestoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoServiceIf;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author PC
 */
public class ImpuestoService extends ServiceAbstract<Impuesto, ImpuestoFacade> implements ImpuestoServiceIf
{
    private ImpuestoFacade impuestoFacade;
    private ImpuestoDetalleFacade impuestoDetalleFacade;

    public ImpuestoService() throws RemoteException
    {
        super(ImpuestoFacade.class);
        impuestoFacade = new ImpuestoFacade();
        impuestoDetalleFacade=new ImpuestoDetalleFacade();
    }
   /*
    public Impuesto grabar(Impuesto i)
    {
        try {
            impuestoFacade.create(i);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(ImpuestoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(ImpuestoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }*/
    
    public void editar(Impuesto i)
    {
        impuestoFacade.edit(i);
    }
    
    public void eliminar(Impuesto i)
    {
        impuestoFacade.remove(i);
    }
    
    public Impuesto obtenerImpuestoPorCodigo(String nombre)
    {
        return impuestoFacade.getByName(nombre);
    }
    
    public Impuesto obtenerImpuestoPorVigencia(String nombre)
    {
        return impuestoFacade.getByImpuestoVigente(nombre);
    }
    
    public List<Impuesto> obtenerTodos()
    {
        return impuestoFacade.findAll();
    }
    
    public List<ImpuestoDetalle> obtenerDetalle()
    {
        return impuestoDetalleFacade.findAll();
    }
}
