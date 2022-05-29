/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;

/**
 * TODO: Ver si unifico este dialogo con persona
 * @author Carlos
 */
public class ReferidoBusquedaDialogo extends ClienteBusquedaDialogo{

    @Override
    public QueryDialog getConsulta(String filter) {
        //Persona persona=new Persona();
        //persona.getContactoCliente();
        String queryString = "SELECT u FROM Persona u WHERE u.contactoCliente=?4 and ";
        queryString += " ( LOWER(u.razonSocial) like ?1 ) ";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, filter);
        queryDialog.agregarParametro(4, EnumSiNo.SI.getLetra());
        return queryDialog;
    }
    
}
