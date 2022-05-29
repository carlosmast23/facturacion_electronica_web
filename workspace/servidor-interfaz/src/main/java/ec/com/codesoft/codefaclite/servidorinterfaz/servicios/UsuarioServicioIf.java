/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.LoginRespuesta;
import java.rmi.Remote;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface UsuarioServicioIf extends ServiceAbstractIf<Usuario>
{
    public Usuario consultarUsuarioActivoPorEmpresa(String nick,Empresa empresa) throws ServicioCodefacException,java.rmi.RemoteException;
    public LoginRespuesta login(String nick,String clave,Empresa empresa) throws java.rmi.RemoteException,ServicioCodefacException;
    public void grabarUsuario(Usuario usuario,String nombrePerfil) throws ServicioCodefacException,java.rmi.RemoteException;
    public Usuario cambiarClave(Usuario usuario,String claveAnterior,String claveNueva) throws java.rmi.RemoteException, ServicioCodefacException;
    
    public void grabarSinTransaccion(Usuario entity,Boolean validarConLicencia) throws ServicioCodefacException,java.rmi.RemoteException;
}
