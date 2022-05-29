/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;

/**
 *
 * @author PC
 */
public class EmpresaBusquedaDialogo implements InterfaceModelFind<Empresa>
{

    @Override
    public Vector<ColumnaDialogo> getColumnas() 
    {
        Vector<ColumnaDialogo> titulo = new Vector<>();
        titulo.add(new ColumnaDialogo("RUC", 0.3d));
        titulo.add(new ColumnaDialogo("Nombre Social", 0.3d));
        //titulo.add(new ColumnaDialogo("Nombre Comercial", 0.3d));
        titulo.add(new ColumnaDialogo("Contribuyente Especial", 0.3d));
        titulo.add(new ColumnaDialogo("Contabilidad", 0.3d));
        titulo.add(new ColumnaDialogo("Nombre Legal", 0.3d));
        
        return titulo;
        
    }


    @Override
    public void agregarObjeto(Empresa t, Vector dato) 
    {
        dato.add(t.getIdentificacion());
        dato.add(t.getRazonSocial());
        //dato.add(t.getDireccion());
        dato.add(t.getContribuyenteEspecial());
        dato.add(t.getObligadoLlevarContabilidad());
        dato.add(t.getNombreLegal());
    }
/*
    @Override
    public Boolean buscarObjeto(Empresa t, Object valor) 
    {
        return t.getIdentificacion().equals(valor.toString());   
    }*/

    @Override
    public QueryDialog getConsulta(String filter) {       
        String queryString = "SELECT u FROM Empresa u WHERE ";
        queryString+=" ( LOWER(u.razonSocial) like ?1 )";
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,filter);
        return queryDialog;
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
