/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.webservice;

import ec.com.codesoft.codefaclite.codefacweb.webservice.response.UsuarioResponse;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Carlos
 */
@Path("otro")
public class OtroResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of OtroResource
     */
    public OtroResource() {
    }

    /**
     * Retrieves representation of an instance of ec.com.codesoft.codefaclite.codefacweb.webservice.OtroResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarios() {
        List<UsuarioResponse> respuesta=new ArrayList<UsuarioResponse>();
        List<Usuario> usuarios=new ArrayList<Usuario>();
        try {
            usuarios=ServiceFactory.getFactory().getUsuarioServicioIf().obtenerTodos();
            for (Usuario usuario : usuarios) {
                respuesta.add(UsuarioResponse.factory(usuario));
            }
        } catch (RemoteException ex) {
            Logger.getLogger(OtroResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.ok().entity(respuesta).build();
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        
    }

    /**
     * PUT method for updating or creating an instance of OtroResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_HTML)
    public void putHtml(String content) {
    }
}
