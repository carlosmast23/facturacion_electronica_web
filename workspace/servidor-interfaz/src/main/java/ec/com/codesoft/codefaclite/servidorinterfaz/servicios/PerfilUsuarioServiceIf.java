/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PerfilUsuario;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface PerfilUsuarioServiceIf extends ServiceAbstractIf<PerfilUsuario> {
    public List<PerfilUsuario> buscarPorPerfil(Perfil perfil) throws java.rmi.RemoteException;
}
