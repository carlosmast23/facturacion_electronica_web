/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.PermisoVentanaFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PermisoVentana;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PermisoVentanaServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author
 */
public class PermisoVentanaService extends ServiceAbstract<PermisoVentana,PermisoVentanaFacade> implements PermisoVentanaServiceIf{

    public PermisoVentanaService() throws RemoteException {
        super(PermisoVentanaFacade.class);
    }
    
    public PermisoVentana crearPermisoVentanaConTodosPermisoSinTransaccion(Perfil perfil,String codigo)
    {
        PermisoVentana permisoVentana=new PermisoVentana();
        permisoVentana.setNombreClase(codigo);
        permisoVentana.setPerfil(perfil);
        permisoVentana.setPermisoBuscarEnum(EnumSiNo.SI);
        permisoVentana.setPermisoEditarEnum(EnumSiNo.SI);
        permisoVentana.setPermisoEliminarEnum(EnumSiNo.SI);
        permisoVentana.setPermisoGrabarEnum(EnumSiNo.SI);
        permisoVentana.setPermisoImprimirEnum(EnumSiNo.SI);
        entityManager.persist(permisoVentana);
        return permisoVentana;
    }

    
    
}
