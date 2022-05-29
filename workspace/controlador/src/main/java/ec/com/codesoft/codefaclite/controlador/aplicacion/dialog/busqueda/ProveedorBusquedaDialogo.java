/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class ProveedorBusquedaDialogo implements InterfaceModelFind<PersonaEstablecimiento> {

    private Empresa empresa;

    public ProveedorBusquedaDialogo(Empresa empresa) {
        this.empresa = empresa;
    }
    

    @Override
    public Vector<ColumnaDialogo> getColumnas() {
        Vector<ColumnaDialogo> titulo = new Vector<ColumnaDialogo>();
        titulo.add(new ColumnaDialogo("Identificacion", 0.2d));
        titulo.add(new ColumnaDialogo("Razón Social", 0.3d));
        titulo.add(new ColumnaDialogo("Nombre Legal", 0.3d));
        titulo.add(new ColumnaDialogo("Telefono", 0.15d));
        titulo.add(new ColumnaDialogo("Celular", 0.10d));

        return titulo;

    }

    @Override
    public void agregarObjeto(PersonaEstablecimiento t, Vector dato) {
        dato.add(t.getPersona().getIdentificacion());
        dato.add(t.getPersona().getRazonSocial());
        dato.add(t.getNombreComercial());
        dato.add(t.getTelefonoConvencional());
        dato.add(t.getExtensionTelefono());
        dato.add(t.getTelefonoCelular());

    }

    /*
    @Override
    public Boolean buscarObjeto(Persona t, Object valor) 
    {
        if(t.getIdentificacion().equals(valor.toString()))
        {
            return true;
        }   
        else
        {
            return false;
        }       
    }*/
    @Override
    public QueryDialog getConsulta(String filter) {
        //PersonaEstablecimiento p;
        //p.getPersona().getEstadoEnum();
        //p.getTipo();
        String queryString = "SELECT u FROM PersonaEstablecimiento u WHERE ";
        
        String queryFiltroEmpresa=" and u.persona.empresa=?6 ";
        Boolean datosCompartidosEmpresas=false;
        try {
            datosCompartidosEmpresas=ParametroUtilidades.comparar(empresa,ParametroCodefac.DATOS_COMPARTIDOS_EMPRESA,EnumSiNo.SI);           
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteEstablecimientoBusquedaDialogo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (datosCompartidosEmpresas) 
        {
            //Si los datos son compratidos entre empresas entoces no hago ningun filtro
            queryFiltroEmpresa = "";
        }
        
        queryString += " u.estado='A' AND u.persona.estado=?7 "+queryFiltroEmpresa+" and ( (LOWER(u.nombreComercial) like ?3 or u.persona.identificacion like ?5 or  LOWER(u.persona.razonSocial) like ?2 ) and (u.persona.tipo = ?1 or u.persona.tipo = ?4 ) )";
        QueryDialog queryDialog = new QueryDialog(queryString);
        queryDialog.agregarParametro(1, OperadorNegocioEnum.PROVEEDOR.getLetra());
        queryDialog.agregarParametro(4, OperadorNegocioEnum.AMBOS.getLetra());
        queryDialog.agregarParametro(2, filter);
        queryDialog.agregarParametro(3, filter);
        queryDialog.agregarParametro(5, filter);
                
        if(!datosCompartidosEmpresas)
        {
            queryDialog.agregarParametro(6, empresa);
        }
        queryDialog.agregarParametro(7,GeneralEnumEstado.ACTIVO.getEstado());
        

        return queryDialog;
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
