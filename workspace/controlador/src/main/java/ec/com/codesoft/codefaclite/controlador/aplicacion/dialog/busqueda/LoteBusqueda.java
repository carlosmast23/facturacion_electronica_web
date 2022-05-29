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
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @author DellWin10
 */
public class LoteBusqueda implements InterfaceModelFind<Lote>{
    
    private Empresa empresa;
    private Producto productoFiltro;

    public LoteBusqueda(Empresa empresa) {
        this.empresa = empresa;
    }

    public LoteBusqueda(Empresa empresa, Producto productoFiltro) {
        this.empresa = empresa;
        this.productoFiltro = productoFiltro;
    }
    
    
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Código", 0.2d));
        titulo.add(new ColumnaDialogo("Producto", 0.2d));
        titulo.add(new ColumnaDialogo("Fecha Elaboración", 0.3d));
        titulo.add(new ColumnaDialogo("Fecha Caducidad", 0.3d));
        //titulo.add(new ColumnaDialogo("Stock", 0.3d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryString=" SELECT u FROM Lote u where u.estado=?1 and u.empresa=?2 and u.codigo like ?3 ";
        
        if(productoFiltro!=null)
        {
            queryString+=" and u.producto= ?4 ";
        }
        
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1,GeneralEnumEstado.ACTIVO.getEstado());
        queryDialog.agregarParametro(2, empresa);
        queryDialog.agregarParametro(3, filter);
        
        if(productoFiltro!=null)
        {
            queryDialog.agregarParametro(4, productoFiltro);
        }
        
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Lote t, Vector dato) {
        dato.add(t.getCodigo());
        String productoNombre="";
        if(t.getProducto()!=null)
        {
            productoNombre=t.getProducto().getNombre();
        }
        dato.add(productoNombre);
        dato.add(t.getFechaElaboracion());
        dato.add(t.getFechaVencimiento());
        //dato.add(t.getStock());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
