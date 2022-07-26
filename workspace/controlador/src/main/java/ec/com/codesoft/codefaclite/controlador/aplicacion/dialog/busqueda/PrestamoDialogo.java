/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Prestamo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import java.util.Vector;

/**
 *
 * @author
 */
public class PrestamoDialogo implements InterfaceModelFind<Prestamo> {

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        //titulo.add(new ColumnaDialogo("Id", 0.2d));
        titulo.add(new ColumnaDialogo("Cliente", 0.2d));
        titulo.add(new ColumnaDialogo("Preimpreso", 0.3d));
        //titulo.add(new ColumnaDialogo("Documento", 0.15d));
        //titulo.add(new ColumnaDialogo("tipo", 0.15d));
        titulo.add(new ColumnaDialogo("Interes", 0.15d));
        titulo.add(new ColumnaDialogo("Cuota inicial", 0.15d));
        titulo.add(new ColumnaDialogo("Plazo", 0.1d));        
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        Prestamo prestamo;
        
        String queryString = "SELECT u FROM Prestamo u  ";
        //queryString += " ( LOWER(u.razonSocial) like ?1 OR u.identificacion like ?1  AND ( u.tipo like ?2 or u.tipo like ?3))";
        QueryDialog queryDialog = new QueryDialog(queryString);
        //queryDialog.agregarParametro(1, filter);
        //queryDialog.agregarParametro(2, OperadorNegocioEnum.CLIENTE.getLetra());
        //queryDialog.agregarParametro(3, OperadorNegocioEnum.AMBOS.getLetra());
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Prestamo t, Vector dato) {
        dato.add(t.getCliente().getRazonSocial());
        if(t.getVenta()!=null)
        {
            dato.add(t.getVenta().getPreimpreso());
        }
        else
        {
            dato.add("");
        }
        dato.add(t.getInteres());
        dato.add(t.getCuotaInicial());
        dato.add(t.getPlazo());
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
