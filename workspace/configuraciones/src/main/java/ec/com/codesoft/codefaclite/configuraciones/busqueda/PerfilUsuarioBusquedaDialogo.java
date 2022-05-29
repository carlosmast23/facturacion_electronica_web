/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PerfilUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;

/**
 *
 * @author Carlos
 */
public class PerfilUsuarioBusquedaDialogo implements InterfaceModelFind<Usuario> {

    private Empresa empresa;

    public PerfilUsuarioBusquedaDialogo(Empresa empresa) {
        this.empresa = empresa;
    }
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Usuario", 100));        
        titulo.add(new ColumnaDialogo("Empleado", 200));
        titulo.add(new ColumnaDialogo("Punto Emisión", 50));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString = "SELECT u FROM Usuario u WHERE u.empresa=?4 and (u.estado=?1 or u.estado=?2 ) and  ";
        queryString+=" ( LOWER(u.nick) like ?3 )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2,GeneralEnumEstado.INACTIVO.getEstado());
        queryDialog.agregarParametro(3,filter);
        queryDialog.agregarParametro(4,empresa);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Usuario t, Vector dato) {
        dato.add(t.getNick());
        
        if(t.getEmpleado()!=null)
        {
            dato.add(t.getEmpleado().getNombresCompletos());
        }
        else
        {
            dato.add("");
        }
        
        dato.add(t.imprimirPuntoEmision());
        
    }

    /*
    @Override
    public Boolean buscarObjeto(Usuario t, Object valor) {
        return t.getNick().equals(valor.toString());
    }*/

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
