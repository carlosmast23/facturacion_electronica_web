/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class ProveedorReporte extends ClienteReporte{

    @Override
    public String getNombreReporte() {
        return "Reporte Proveedores";
    }
    
    @Override    
    public List<Persona> obtenerConsulta() throws RemoteException {
       PersonaServiceIf service=ServiceFactory.getFactory().getPersonaServiceIf();
        return service.buscarPorTipo(OperadorNegocioEnum.PROVEEDOR,GeneralEnumEstado.ACTIVO,session.getEmpresa()); //Todo: Obtener filtrar solo por clientes
    }
    
    
}
