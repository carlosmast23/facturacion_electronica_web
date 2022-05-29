/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.webservice.response;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;

/**
 *
 * @author Carlos
 */
public class UsuarioResponse {
    private String nick;
    private String estado;    

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
    public static UsuarioResponse factory(Usuario usuario)
    {
        UsuarioResponse respuesta=new UsuarioResponse();
        respuesta.estado="Activo";
        respuesta.nick=usuario.getNick();
        return respuesta;
    }
}
