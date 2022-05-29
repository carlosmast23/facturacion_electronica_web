/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.aplicacion.dialog.busqueda;


import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import java.util.Vector;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Departamento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Ruta;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RutaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TODO: Ver como unificar el dialogo con la factura y otras que usan similares
 * @author PC
 */
public class ClienteEstablecimientoBusquedaDialogo implements InterfaceModelFind<PersonaEstablecimiento> ,InterfacesPropertisFindWeb
{
    private Empresa empresa;
    private Boolean moduloAcademicoActivo;
    
    private Boolean filtrarClientesRuta;
    private Empleado empleado;
    
    /**
     * TODO: Pensar en un futuro otra forma estandar para poder ordenar cualquier campo
     * Variable que me permite identificar si se quiere crear el dialogo con la primera columna como la razon social
     */
    private Boolean primeraColumnaNombre=false;

    public ClienteEstablecimientoBusquedaDialogo(SessionCodefacInterface sessionCodefac) {
        this.empresa = sessionCodefac.getEmpresa();
        this.moduloAcademicoActivo=sessionCodefac.verificarExisteModulo(ModuloCodefacEnum.GESTIONA_ACADEMICA);
        verificarFiltroPorRuta(sessionCodefac);
    }
    
    @Override
    public Vector<ColumnaDialogo> getColumnas() 
    {
        Vector<ColumnaDialogo> titulo=new Vector<ColumnaDialogo>();
        if(primeraColumnaNombre)
        {
            titulo.add(new ColumnaDialogo("Razón Social ",0.3d));
            titulo.add(new ColumnaDialogo("Identificacion",0.15d));            
        }
        else
        {
            titulo.add(new ColumnaDialogo("Identificacion",0.15d));
            titulo.add(new ColumnaDialogo("Razón Social ",0.3d));
        }
        
        titulo.add(new ColumnaDialogo("Nombre Legal",0.2d));
        
        if(!moduloAcademicoActivo)
        {
            titulo.add(new ColumnaDialogo("Telefono",0.1d));
            titulo.add(new ColumnaDialogo("Celular",0.1d));
        }
        else
        {
            titulo.add(new ColumnaDialogo("Estudiante",0.2d));
        }
        
        return titulo;
      
    }

    @Override
    public void agregarObjeto(PersonaEstablecimiento t, Vector dato) 
    {
        if(primeraColumnaNombre)
        {            
            dato.add(t.getPersona().getRazonSocial());
            dato.add(t.getPersona().getIdentificacion());
        }
        else
        {
            dato.add(t.getPersona().getIdentificacion());
            dato.add(t.getPersona().getRazonSocial());
        }
            
        dato.add((t.getNombreComercial()!=null)?t.getNombreComercial():"");
        if(!moduloAcademicoActivo)
        {
            dato.add(t.getTelefonoConvencional());       
            dato.add(t.getTelefonoCelular());
        }
        else
        {
            dato.add(UtilidadesLista.castListToString(t.getPersona().getEstudiantes()," - "));
        }
   
    }

    @Override
    public QueryDialog getConsulta(String filter) {
        
        String queryFiltroEmpresa=" AND u.persona.empresa=?2 ";
        
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
        
        
        String leftJoinEstudiante="";
        if(moduloAcademicoActivo && !filtrarClientesRuta)
        {
            leftJoinEstudiante+=" LEFT JOIN u.persona.estudiantes e ";
        }
        
        /**
         * Si tiene que filtrar por rutas 
         */
        String queryString = "";
        if(filtrarClientesRuta)
        {
            queryString = "SELECT DISTINCT u FROM RutaDetalle rd LEFT JOIN rd.establecimiento u LEFT JOIN rd.ruta r LEFT JOIN r.vendedor v WHERE v=?4 AND ";
        }
        else
        {
            queryString = "SELECT DISTINCT  u FROM PersonaEstablecimiento u "+leftJoinEstudiante+"  WHERE 1=1 AND ";
        }
        
        queryString+=" u.estado='A' AND u.persona.estado=?3 "+queryFiltroEmpresa+" AND ( LOWER(u.persona.razonSocial) like ?1 OR u.persona.identificacion like ?1 OR LOWER(u.nombreComercial) like ?1 ";
        
        //Agregar Where para modulo academico
        if(moduloAcademicoActivo && !filtrarClientesRuta)
        {
            queryString+=" OR LOWER(e.apellidos) like ?1 OR LOWER(e.nombres) like ?1 ";
        }
        queryString+=" )";
        
        QueryDialog queryDialog=new QueryDialog(queryString);
        queryDialog.agregarParametro(1,filter.toLowerCase());

        
        if(!datosCompartidosEmpresas)
        {
            queryDialog.agregarParametro(2,empresa);
        }        
        
        queryDialog.agregarParametro(3,GeneralEnumEstado.ACTIVO.getEstado());
        
        if(filtrarClientesRuta)
        {
            queryDialog.agregarParametro(4,this.empleado);
        }
        
        return queryDialog;
    }
    
    
    
    private void verificarFiltroPorRuta(SessionCodefacInterface sessionCodefac)
    {
        filtrarClientesRuta=false;
        Empleado empleado=sessionCodefac.getUsuario().getEmpleado();
        if(empleado!=null)
        {
            Departamento departamento=empleado.getDepartamento();
            if(departamento!=null)
            {
                if(departamento.getTipoEnum().equals(Departamento.TipoEnum.Vendedores_Externos))
                {
                    filtrarClientesRuta=true;
                    this.empleado=empleado;
                }
            }
        }
        
      
    }

    @Override
    public Vector<String> getNamePropertysObject() {
        Vector<String> propiedades = new Vector<String>();
        
        if(primeraColumnaNombre)
        {
            propiedades.add("persona.razonSocial");
            propiedades.add("persona.identificacion");            
        }
        else
        {
            propiedades.add("persona.identificacion");
            propiedades.add("persona.razonSocial");
        }
        
        propiedades.add("nombreComercial");//TODO: Ver como puedo hacer para establecer una propiedad personalizada
        if(!moduloAcademicoActivo)
        {
            propiedades.add("telefonoConvencional");
            propiedades.add("telefonoCelular");
        }
        else
        {
            propiedades.add("telefonoConvencional");
        }
        return propiedades;
    }

    public Boolean getPrimeraColumnaNombre() {
        return primeraColumnaNombre;
    }

    public void setPrimeraColumnaNombre(Boolean primeraColumnaNombre) {
        this.primeraColumnaNombre = primeraColumnaNombre;
    }
    
    
    
}
