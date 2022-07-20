/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.Caja;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaPermiso;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.util.Vector;

/**
 *
 * @auhor
 */
public class CajaPermisoBusquedaDialogo implements InterfaceModelFind<CajaPermiso>
{
    private SessionCodefacInterface session;
    
    public CajaPermisoBusquedaDialogo(SessionCodefacInterface session) 
    {
        this.session = session;
    }

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> columnasTitulo = new Vector<>();
        columnasTitulo.add(new ColumnaDialogo("Usuario", 0.2d));
        columnasTitulo.add(new ColumnaDialogo("Caja", 0.2d));
        columnasTitulo.add(new ColumnaDialogo("Descripción", 0.2d));
        return columnasTitulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        
        String queryString = "SELECT cp FROM CajaPermiso cp WHERE ";
        
        if(this.session != null)
        {
            queryString+=" ( LOWER(cp.usuario.nick) like ?1 or LOWER(cp.caja.nombre) like ?2) and (cp.estado) like ?3 and (cp.caja.sucursal) like ?4";     
        }
        else
        {
            queryString+=" ( LOWER(cp.usuario.nick) like ?1 or LOWER(cp.caja.nombre) like ?2) and (cp.estado) like ?3";     
        }
        
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,filter);
        queryDialog.agregarParametro(2,filter);
        queryDialog.agregarParametro(3,GeneralEnumEstado.ACTIVO.getEstado());
        
        if(this.session != null)
        {
            queryDialog.agregarParametro(4, this.session.getSucursal());
        }
        
        return queryDialog;
    }

    @Override
    public void agregarObjeto(CajaPermiso cp, Vector dato) {
        dato.add(cp.getUsuario().getNick());
        dato.add(cp.getCaja().getNombre());
        dato.add(cp.getDescripcion());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        Vector<String> propiedades = new Vector<String>();
        propiedades.add("usuario.nick");
        propiedades.add("caja.nombre");
        propiedades.add("descripcion");
        return propiedades;
    }

}
