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
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexItemEspecifico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.util.Vector;

/**
 *
 * @auhor
 */
public class ProductoInventarioEspecificoDialogo implements InterfaceModelFind<KardexItemEspecifico> , InterfacesPropertisFindWeb {

    private Producto producto;

    public ProductoInventarioEspecificoDialogo(Producto producto) {
        this.producto = producto;
    }
    
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Código Especifico", 0.5d));
        titulo.add(new ColumnaDialogo("Estado", 0.5d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        String queryConsulta="SELECT kie FROM Kardex k,KardexDetalle kd,KardexItemEspecifico kie where kie.kardexDetalle=kd and kd.kardex=k and k.producto=?1 ";
        QueryDialog queryDialog=new QueryDialog(queryConsulta);
        queryDialog.agregarParametro(1,producto);
        //queryDialog.agregarParametro(2,filter);
        return queryDialog;
    }

    @Override
    public void agregarObjeto(KardexItemEspecifico t, Vector dato) {
        dato.add(t.getCodigoEspecifico());
        dato.add(t.getEstadoEnum().getNombre());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
