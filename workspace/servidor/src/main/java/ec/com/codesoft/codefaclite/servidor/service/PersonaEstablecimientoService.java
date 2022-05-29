/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.PersonaEstablecimientoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaEstablecimientoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections.map.HashedMap;

/**
 *
 * @author Carlos
 */
public class PersonaEstablecimientoService extends ServiceAbstract<PersonaEstablecimiento,PersonaEstablecimientoFacade> implements PersonaEstablecimientoServiceIf {

    public PersonaEstablecimientoService() throws RemoteException {
        super(PersonaEstablecimientoFacade.class);
    }
 
    public List<PersonaEstablecimiento> buscarActivoPorIdentificacion(String identificacion, Empresa empresa) throws ServicioCodefacException, java.rmi.RemoteException 
    {
        
        Boolean datosCompartidosEmpresas=false;
        try {
            datosCompartidosEmpresas=ParametroUtilidades.comparar(empresa,ParametroCodefac.DATOS_COMPARTIDOS_EMPRESA,EnumSiNo.SI);           
        } catch (RemoteException ex) {
            Logger.getLogger(PersonaEstablecimientoService.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        //PersonaEstablecimiento pe;
        //pe.getPersona().getEstadoEnum()
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("persona.identificacion", identificacion);
        if(!datosCompartidosEmpresas)
        {
            mapParametros.put("persona.empresa", empresa);
        }
        mapParametros.put("persona.estado", GeneralEnumEstado.ACTIVO.getEstado());
        
        return  getFacade().findByMap(mapParametros); //Todo crear mejor un metodo que ya obtener filtrado los datos
    }
}
