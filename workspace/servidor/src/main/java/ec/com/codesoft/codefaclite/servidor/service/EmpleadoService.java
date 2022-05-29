/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.EmpleadoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EmpleadoServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class EmpleadoService extends ServiceAbstract<Empleado, EmpleadoFacade> implements EmpleadoServiceIf
{
    private EmpleadoFacade empleadoFacade;
    
    public EmpleadoService() throws RemoteException
    {
        super(EmpleadoFacade.class);
        this.empleadoFacade = new EmpleadoFacade();
    }
    
    /*
    @Override
     public Empleado grabar(Empleado b) throws ServicioCodefacException {
        try {
            empleadoFacade.create(b);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(BodegaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("La clave principal ya existe en el sistema");
        }
        return b;
    }
    */
    @Override
    public void editar(Empleado b) {
        empleadoFacade.edit(b);
    }

    @Override
    public void eliminar(Empleado b) {
        b.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
        empleadoFacade.edit(b);
    }

    @Override
    public List<Empleado> buscar() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Empleado> buscarPorDepartamento(Departamento departamento,Empresa empresa) throws ServicioCodefacException, java.rmi.RemoteException
    {
        Map<String, Object> parametroMap = new HashMap<String, Object>();
        parametroMap.put("departamento", departamento);
        //parametroMap.put("departamento", departamento);
        return getFacade().findByMap(parametroMap);
    }
    
    
}
