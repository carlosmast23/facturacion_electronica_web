/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaPermiso;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaSession;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CajaSessionEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.util.Vector;

/**
 *
 * @author Robert
 */
public class CajaSessionBusquedaDialogo implements InterfaceModelFind<CajaSession>
{

    private SessionCodefacInterface session;

    public CajaSessionBusquedaDialogo(SessionCodefacInterface session) {
        this.session = session;
    }
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> columnasTitulo = new Vector<>();
        columnasTitulo.add(new ColumnaDialogo("Caja", 0.2d));
        columnasTitulo.add(new ColumnaDialogo("Punto Emisión", 0.2d));
        columnasTitulo.add(new ColumnaDialogo("Sucursal", 0.2d));
        return columnasTitulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "";
        
        queryString += "Select cs from CajaSession cs Where cs.estadoCierreCaja = ?2 ";
        
        if(session.getUsuario() != null)
        {
            queryString += "AND cs.usuario = ?3 ";
        }
        
        if(session.getSucursal() != null)
        {
            queryString += "AND cs.caja.sucursal = ?4 ";
        }
        
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(2, CajaSessionEnum.ACTIVO.getEstado());
        
        if(session.getUsuario() != null)
        {
            queryDialog.agregarParametro(3, session.getUsuario());
        }
        if(session.getSucursal() != null)
        {
            queryDialog.agregarParametro(4, session.getSucursal());
        }
       
        return queryDialog;
    }

    @Override
    public void agregarObjeto(CajaSession t, Vector dato) {
        dato.add(t.getCaja().getNombre());
        dato.add(t.getCaja().getPuntoEmision().getPuntoEmision());
        dato.add(t.getCaja().getSucursal());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        Vector<String> propiedades = new Vector<>();
        propiedades.add("caja.nombre");
        propiedades.add("caja.puntoEmision");
        propiedades.add("caja.sucursal");
        return propiedades;
    }
    
    
}
