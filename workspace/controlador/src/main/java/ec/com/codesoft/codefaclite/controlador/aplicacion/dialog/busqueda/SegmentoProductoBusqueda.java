/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SegmentoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TipoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author DellWin10
 */
public class SegmentoProductoBusqueda implements InterfaceModelFind<SegmentoProducto>{
    
    private Empresa empresa;
    
    public SegmentoProductoBusqueda(Empresa empresa) {
        this.empresa = empresa;
    }

    
    
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Nombre", 0.2d));
        titulo.add(new ColumnaDialogo("Descripción", 0.2d));
        //titulo.add(new ColumnaDialogo("Stock", 0.3d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString=" SELECT u FROM SegmentoProducto u where u.estado=?1 and u.empresa=?2 and u.nombre like ?3 ";
        

        
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2, empresa);
        queryDialog.agregarParametro(3, filter);
        

        
        return queryDialog;
    }

    @Override
    public void agregarObjeto(SegmentoProducto t, Vector dato) {
        dato.add(t.getNombre());        
        dato.add(t.getDescripcion());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
