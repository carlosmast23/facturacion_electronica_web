/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import java.rmi.Remote;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface PersonaServiceIf extends ServiceAbstractIf<Persona>{
    
    public Persona grabarConValidacion(Persona p, Boolean validarCedula,Boolean modoForzado) throws ServicioCodefacException, java.rmi.RemoteException ;    
    
    public Persona grabar(Persona p) throws ServicioCodefacException,java.rmi.RemoteException;    
   
    public void editar(Persona p) throws ServicioCodefacException, java.rmi.RemoteException;
    
    public void eliminar(Persona p) throws ServicioCodefacException, java.rmi.RemoteException;
    
    public List<Persona> buscar() throws java.rmi.RemoteException;
    
    public List<Persona> buscarPorTipo(OperadorNegocioEnum tipoEnum, GeneralEnumEstado estado,Empresa empresa) throws java.rmi.RemoteException;
    
    public Persona buscarPorIdentificacionYestado(String identificacion,GeneralEnumEstado estado) throws ServicioCodefacException,java.rmi.RemoteException;
    
    public Persona buscarPorIdentificacion(String identificacion,Empresa empresa) throws java.rmi.RemoteException;
    
    //TODO: Ver si se hace un solo metodo que el de editar pero falta agregar la exepcion de ServicioCodefacException
    public void editarPersona(Persona p) throws ServicioCodefacException,java.rmi.RemoteException;
    
    public Persona buscarConsumidorFinal(Empresa empresa) throws ServicioCodefacException, java.rmi.RemoteException;
    
    public Persona crearConsumidorFinal(Empresa empresa) throws ServicioCodefacException, java.rmi.RemoteException;
    
    public Persona crearPlantillaPersona(Empresa empresa,String identificacion,Persona.TipoIdentificacionEnum tipoIdentificacionEnum,String razonSocial,String direccion,OperadorNegocioEnum operadorNegocioEnum) throws ServicioCodefacException, java.rmi.RemoteException;
    
    public Persona grabarModoForzado(Persona p,Boolean modoForzado) throws ServicioCodefacException, java.rmi.RemoteException;
    
}
