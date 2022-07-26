/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;

/**
 *
 * @author
 */
public class FacturaBusquedaPresupuesto implements InterfaceModelFind<Presupuesto> 
{
    /**
     * Entidad para filtrar los presupuesto por cliente
     */
    private Persona cliente;

    public FacturaBusquedaPresupuesto(Persona cliente) {
        this.cliente = cliente;
    }

    public FacturaBusquedaPresupuesto() {
    }
    
    
    

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("Cod Presupuesto", 0.2d));
        titulo.add(new ColumnaDialogo("Cod Orden.T", 0.2d));
        titulo.add(new ColumnaDialogo("Cliente", 0.3d));
        titulo.add(new ColumnaDialogo("estado", 0.15d));
        titulo.add(new ColumnaDialogo("fecha", 0.15d));
        titulo.add(new ColumnaDialogo("total", 0.1d));
        return titulo;
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        //Presupuesto p;
        //p.getOrdenTrabajoDetalle().getOrdenTrabajo().getCliente().getRazonSocial();
        String queryString = "SELECT u FROM Presupuesto u WHERE ( u.estado=?1 ) AND ";
        queryString+=(cliente!=null)?" u.ordenTrabajoDetalle.ordenTrabajo.cliente=?3 AND":"";
        queryString+=" ( LOWER(u.codigo) like ?2 OR  LOWER(u.ordenTrabajoDetalle.ordenTrabajo.cliente.razonSocial) like ?2 OR CAST(u.id CHAR(64) ) like ?2 )";
        
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,Presupuesto.EstadoEnum.TERMINADO.getLetra()); //Solo buscar los prespuestos que esten con estado terminado
        queryDialog.agregarParametro(2,filter);
        
        if(cliente!=null) //Ejecutar solo si escoge un cliente para filtrar los datos
        {
            queryDialog.agregarParametro(3,cliente);
        }   
        return queryDialog;
    }

    @Override
    public void agregarObjeto(Presupuesto t, Vector dato) {
        dato.add(t.getId()+"");
        dato.add(t.getOrdenTrabajoDetalle().getOrdenTrabajo().getId()+"");
        dato.add(t.getOrdenTrabajoDetalle().getOrdenTrabajo().getCliente().getNombresCompletos());
        dato.add((t.getEstadoEnum()!=null)?t.getEstadoEnum().getNombre():"Sin estado");
        dato.add(t.getFechaPresupuesto());
        dato.add(t.getTotalVenta());
        
        
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
