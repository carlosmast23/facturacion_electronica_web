/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface EmpleadoServiceIf  extends ServiceAbstractIf<Empleado>
{
    public Empleado grabar(Empleado e) throws ServicioCodefacException,java.rmi.RemoteException;
    public void editar(Empleado e) throws java.rmi.RemoteException;
    public void eliminar(Empleado e) throws java.rmi.RemoteException;
    public List<Empleado> buscar() throws java.rmi.RemoteException;
    public List<Empleado> buscarPorDepartamento(Departamento departamento,Empresa empresa) throws ServicioCodefacException, java.rmi.RemoteException;
}
