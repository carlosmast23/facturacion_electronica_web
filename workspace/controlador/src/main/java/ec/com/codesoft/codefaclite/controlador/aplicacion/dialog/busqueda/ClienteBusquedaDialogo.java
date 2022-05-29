/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;

/**
 *TODO:Parece una clase en desuso porque no tiene ni para filtrar por empresa
 * @author PC
 * @deprecated 
 */
public class ClienteBusquedaDialogo implements InterfaceModelFind<Persona>, InterfacesPropertisFindWeb {

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Identificacion", 0.2d));
        titulo.add(new ColumnaDialogo("Nombre", 0.3d));
        titulo.add(new ColumnaDialogo("Telefono", 0.15d));
        titulo.add(new ColumnaDialogo("Extension", 0.10d));
        titulo.add(new ColumnaDialogo("Celular", 0.10d));
        titulo.add(new ColumnaDialogo("Correo", 0.15d));

        return titulo;

    }

    @Override
    public void agregarObjeto(Persona t, Vector dato) {
        dato.add(t.getIdentificacion());
        dato.add(t.getRazonSocial());
        System.out.println(t.getRazonSocial());
        PersonaEstablecimiento establecimiento=t.getEstablecimientos().get(0);
        dato.add((establecimiento!=null)?establecimiento.getTelefonoConvencional():"");
        dato.add((establecimiento!=null)?establecimiento.getExtensionTelefono():"");
        dato.add((establecimiento!=null)?establecimiento.getTelefonoCelular():"");
        dato.add(t.getCorreoElectronico());

    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //Persona p;
        //p.getIdentificacion();
        String queryString = "SELECT u FROM Persona u WHERE ";
        queryString += " ( LOWER(u.razonSocial) like ?1 OR u.identificacion like ?1  AND ( u.tipo like ?2 or u.tipo like ?3))";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, filter);
        queryDialog.agregarParametro(2, OperadorNegocioEnum.CLIENTE.getLetra());
        queryDialog.agregarParametro(3, OperadorNegocioEnum.AMBOS.getLetra());
        return queryDialog;
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        Vector<String> propiedades = new Vector<String>();
        propiedades.add("identificacion");
        propiedades.add("razonSocial");
        propiedades.add("");//TODO: Ver como puedo hacer para establecer una propiedad personalizada
        propiedades.add("");
        propiedades.add("");
        propiedades.add("correoElectronico");
        return propiedades;
    }

}
