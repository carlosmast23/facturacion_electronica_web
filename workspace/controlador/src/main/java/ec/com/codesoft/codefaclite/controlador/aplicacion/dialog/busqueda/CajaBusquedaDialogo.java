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
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import java.util.Vector;

/**
 *
 * @author Robert
 */
public class CajaBusquedaDialogo implements InterfaceModelFind<Caja>
{
    //private Sucursal sucursal;
    //private PuntoEmision puntoEmision;
    private SessionCodefacInterface session;
    
    public CajaBusquedaDialogo(SessionCodefacInterface session) 
    {
        this.session = session;
    }

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> columnasTitulo = new Vector<>();
        columnasTitulo.add(new ColumnaDialogo("Sucursal", 0.2d));
        columnasTitulo.add(new ColumnaDialogo("Punto Emisión", 0.2d));
        columnasTitulo.add(new ColumnaDialogo("Caja", 0.2d));
        columnasTitulo.add(new ColumnaDialogo("Descripción", 0.2d));
        return columnasTitulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT c FROM Caja c WHERE ";
        
        if(this.session == null)
        {
            queryString+=" ( LOWER(c.nombre) like ?1 and (c.estado) like ?2)";
        }else
        {
            queryString+=" ( LOWER(c.nombre) like ?1 and (c.estado) like ?2 and (c.sucursal) = ?3)";    
        }
        
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1, filter);
        queryDialog.agregarParametro(2, GeneralEnumEstado.ACTIVO.getEstado());
        if(this.session != null)
        {
            queryDialog.agregarParametro(3, this.session.getMatriz());
        }
        
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Caja t, Vector dato) {
        dato.add(t.getSucursal().toString());
        dato.add(t.getPuntoEmision().toString());
        dato.add(t.getNombre());
        dato.add(t.getDescripcion());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        Vector<String> propiedades = new Vector<String>();
        propiedades.add("sucursal.nombre");
        propiedades.add("puntoEmision.descripcion");
        propiedades.add("nombre");
        propiedades.add("descripcion");
        return propiedades;
    }
    

}
