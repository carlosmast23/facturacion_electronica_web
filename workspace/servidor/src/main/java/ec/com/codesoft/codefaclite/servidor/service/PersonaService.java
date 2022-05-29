/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.facade.PersonaEstablecimientoFacade;
import ec.com.codesoft.codefaclite.servidor.facade.PersonaFacade;
import ec.com.codesoft.codefaclite.servidor.util.ExcepcionDataBaseEnum;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesExcepciones;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona.TipoIdentificacionEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author PC
 */
public class PersonaService extends ServiceAbstract<Persona, PersonaFacade> implements PersonaServiceIf {

    private PersonaFacade personaFacade;

    public PersonaService() throws RemoteException {
        super(PersonaFacade.class);
        this.personaFacade = new PersonaFacade();
    }

    public void editarPersona(Persona p) throws ServicioCodefacException, java.rmi.RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                validarCliente(p, Boolean.TRUE, CrudEnum.EDITAR,false);
                for (PersonaEstablecimiento establecimiento : p.getEstablecimientos()) {
                    if (establecimiento.getId() == null) {
                        entityManager.persist(establecimiento);
                    } else {
                        entityManager.merge(establecimiento);
                    }
                }
                entityManager.merge(p);
            }
        });
    }

    public Persona grabar(Persona p) throws ServicioCodefacException, java.rmi.RemoteException {
        return grabarConValidacion(p, true,false);
    }
    
    public Persona grabarModoForzado(Persona p,Boolean modoForzado) throws ServicioCodefacException, java.rmi.RemoteException {
        return grabarConValidacion(p, true,modoForzado);
    }

    public Persona grabarConValidacion(Persona p, Boolean validarCedula,Boolean modoForzado) throws ServicioCodefacException, java.rmi.RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {

                validarCliente(p, validarCedula, CrudEnum.CREAR,modoForzado);
                /*if (p.getEstablecimientos() == null || p.getEstablecimientos().size() == 0) {
                    //Si no tiene un establecimiento lo creo automaticamente 
                    PersonaEstablecimiento personaEstablecimiento = PersonaEstablecimiento.buildFromPersona(p);
                    personaEstablecimiento.setCodigoSucursal("1");
                    personaEstablecimiento.setCorreoElectronico(""); //implementar de forma posterior
                    personaEstablecimiento.setTipoSucursalEnum(Sucursal.TipoSucursalEnum.MATRIZ);
                    //Si no ingreso un nombre comercial se graba como matriz
                    //if(personaEstablecimiento.getNombreComercial()==null || personaEstablecimiento.getNombreComercial().isEmpty())
                    //{
                    //    personaEstablecimiento.setNombreComercial(Sucursal.TipoSucursalEnum.MATRIZ.getNombre());
                    //}
                    
                    //entityManager.persist(personaEstablecimiento);
                    p.addEstablecimiento(personaEstablecimiento);
                }*/

                p.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                p.setFechaCreacion(UtilidadesFecha.getFechaHoyTimeStamp());
                //Grabar los nuevos establecimientos
                for (PersonaEstablecimiento establecimiento : p.getEstablecimientos()) 
                {
                    entityManager.persist(establecimiento);
                }
                entityManager.persist(p);
            }
        });
        return p;

    }

    private void validarCliente(Persona persona, Boolean validarCedula, CrudEnum crudEnum,Boolean modoForzado) throws ServicioCodefacException, java.rmi.RemoteException {
        /**
         * Validaciones previas de los datos
         */
        if (validarCedula) {
            if(!modoForzado)
            {
                if (!persona.validarCedula()) {
                    throw new ServicioCodefacException("Error al validar la identificación",true);
                }
            }
        }

        if (persona.getRazonSocial() == null || persona.getRazonSocial().trim().isEmpty()) {
            throw new ServicioCodefacException("La razón social no puede ser vacia");
        }

        if (persona.getEstablecimientos() == null || persona.getEstablecimientos().size() == 0) {
            throw new ServicioCodefacException("No se puede crear el registro sin establecimientos");
        }

        //TODO: Separar en otra funcion esta logica para tener un codigo más modular
        //TODO: Mejorar para tambien hacer la validacion con datos GUARDADOS para evitar problemas , actualmente solo queda con los datos que supuestamente vienen desde la vista pero esto no es seguro
        //Validar que los detalles no puedan tener el mismo cópdigo de la sucursal
        for (int i = 0; i < persona.getEstablecimientos().size(); i++) {
            PersonaEstablecimiento establecimiento = persona.getEstablecimientos().get(i);
            for (int j = i + 1; j < persona.getEstablecimientos().size(); j++) {
                if (establecimiento.getEstadoEnum().equals(GeneralEnumEstado.ACTIVO)) {
                    PersonaEstablecimiento establecimientoTmp = persona.getEstablecimientos().get(j);
                    if (establecimientoTmp.getCodigoSucursal().equals(establecimiento.getCodigoSucursal())) {
                        throw new ServicioCodefacException("No se puede grabar el CLIENTE con 2 establecimientos que tienen el mismo CÓDIGO");
                    }
                }
            }

            if (establecimiento.getDireccion() == null || establecimiento.getDireccion().trim().isEmpty()) {
                throw new ServicioCodefacException("No se puede grabar el CLIENTE sin DIRECCIÓN");
            }
        }

        //Si se esta grabando e editando un consumidor final no se puede editar el consumidor final
        if (persona.getIdentificacion().equals("9999999999999")) {
            //Si cambian este dato el sistema por defecto lo deja como el original
            persona.setRazonSocial(Usuario.CONSUMIDOR_FINAL_NOMBRE);
        }

        //NOTA: Esta validacion siempre debe ir al ultimo de este metodo
        //Si es un crud verifico sin los datos editados y consultados son los mismos
        if (crudEnum.equals(CrudEnum.EDITAR)) {
            Persona personaTmp = getFacade().find(persona.getIdCliente());
            if (personaTmp.getIdentificacion().equals(persona.getIdentificacion())) {
                return;
            }
        }

        if (buscarPorIdentificacion(persona.getIdentificacion(), persona.getEmpresa()) != null) {
            throw new ServicioCodefacException("Ya existe ingresado un cliente con la misma identificación");
        }

    }

    public void editar(Persona p) throws ServicioCodefacException, java.rmi.RemoteException {
        personaFacade.edit(p);

    }

    public void eliminar(Persona p) throws ServicioCodefacException, java.rmi.RemoteException {
        //personaFacade.remove(p);
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                p.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
                entityManager.merge(p);

                PersonaEstablecimiento personaEstablecimiento;
            }
        });

    }

    public List<Persona> buscar() {
        return personaFacade.findAll();
    }

    public Persona buscarPorIdentificacionYestado(String identificacion, GeneralEnumEstado estado) throws ServicioCodefacException, java.rmi.RemoteException {

        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("identificacion", identificacion);
        mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());

        List<Persona> resultados = getFacade().findByMap(mapParametros);
        if (resultados.size() == 0) {
            return null;
        } else {
            return resultados.get(0);
        }

    }

    @Override
    public List<Persona> buscarPorTipo(OperadorNegocioEnum tipoEnum, GeneralEnumEstado estado, Empresa empresa) throws java.rmi.RemoteException {
        return getFacade().buscarPorTipoFacade(tipoEnum, estado, empresa);
    }

    /**
     * TODO: Editado para partir buscando desde un establecimiento por que si
     * solo busco del cliente puede que no tenga establecimientos
     *
     * @param identificacion
     * @param empresa
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public Persona buscarPorIdentificacion(String identificacion, Empresa empresa) throws java.rmi.RemoteException {
        PersonaEstablecimientoFacade personaEstablecimientoFacade = new PersonaEstablecimientoFacade();
        PersonaEstablecimiento personaEstablecimiento;
        //Persona p;
        //p.getIdentificacion();
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("persona.identificacion", identificacion);
        mapParametros.put("persona.empresa", empresa);
        mapParametros.put("persona.estado", GeneralEnumEstado.ACTIVO.getEstado());

        List<PersonaEstablecimiento> establecimientos = personaEstablecimientoFacade.findByMap(mapParametros);

        //List<Persona> personas = getFacade().findByMap(mapParametros);
        if (establecimientos.size() > 0) {
            if (establecimientos.get(0).getPersona() != null) {
                return establecimientos.get(0).getPersona();
            }
        }

        return null;

    }

    @Override
    public Persona buscarConsumidorFinal(Empresa empresa) throws ServicioCodefacException, java.rmi.RemoteException {
        PersonaServiceIf cliente = ServiceFactory.getFactory().getPersonaServiceIf();
        Map<String, Object> clienteMap = new HashMap<String, Object>();
        //Persona p;
        //p.getIdentificacion();
        clienteMap.put("identificacion", Persona.IDENTIFICACION_CONSUMIDOR_FINAL);
        clienteMap.put("empresa", empresa);
        clienteMap.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
        List<Persona> resultados = getFacade().findByMap(clienteMap);
        if (resultados.size() > 0) {
            return resultados.get(0);
        }
        return null;
    }

    //TODO: Ver si este metodo se mueve a la propia clase de PERSONA
    public Persona crearPlantillaPersona(Empresa empresa, String identificacion, TipoIdentificacionEnum tipoIdentificacionEnum, String razonSocial, String direccion, OperadorNegocioEnum operadorNegocioEnum) throws ServicioCodefacException, java.rmi.RemoteException {
        //Crear la plantilla de la persona
        Persona persona = new Persona();
        persona.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        persona.setIdentificacion(identificacion);
        persona.setRazonSocial(razonSocial);
        persona.setTipClienteEnum(Persona.TipoClienteEnum.CLIENTE); //Todo: este campo toca analizar
        persona.setTipoEnum(operadorNegocioEnum);
        persona.setTipoIdentificacionEnum(tipoIdentificacionEnum);
        persona.setEmpresa(empresa);

        //Crear un establecimiento por defecto
        PersonaEstablecimiento establecimiento = new PersonaEstablecimiento();
        establecimiento.setCodigoSucursal("1");
        establecimiento.setNombreComercial(razonSocial);
        establecimiento.setDireccion(direccion);
        establecimiento.setPersona(persona);
        establecimiento.setTipoSucursalEnum(Sucursal.TipoSucursalEnum.MATRIZ);
        establecimiento.setEstadoEnum(GeneralEnumEstado.ACTIVO);

        persona.setEstablecimientos(Arrays.asList(establecimiento));
        return persona;
    }

    //TODO: Unir con el metodo de crearPlantillaPersona
    public Persona crearConsumidorFinalSinTransaccion(Empresa empresa) {
        Persona persona = new Persona();
        persona.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        persona.setIdentificacion(Persona.IDENTIFICACION_CONSUMIDOR_FINAL);
        persona.setRazonSocial(Usuario.CONSUMIDOR_FINAL_NOMBRE);
        persona.setTipClienteEnum(Persona.TipoClienteEnum.CLIENTE);
        persona.setTipoEnum(OperadorNegocioEnum.AMBOS);
        persona.setTipoIdentificacionEnum(Persona.TipoIdentificacionEnum.CLIENTE_FINAL);
        persona.setEmpresa(empresa);
        entityManager.persist(persona);

        PersonaEstablecimiento establecimiento = new PersonaEstablecimiento();
        establecimiento.setCodigoSucursal("1");
        establecimiento.setNombreComercial(Usuario.CONSUMIDOR_FINAL_NOMBRE);
        establecimiento.setDireccion("s/n"); //TODO: Significa sin direccion
        establecimiento.setPersona(persona);
        establecimiento.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        establecimiento.setTipoSucursalEnum(Sucursal.TipoSucursalEnum.MATRIZ);
        entityManager.persist(establecimiento);

        //Agrear el establecimiento creado a la lista de la persona para grabar
        persona.setEstablecimientos(Arrays.asList(establecimiento));
        entityManager.merge(persona);

        return persona;
    }

    public Persona crearConsumidorFinal(Empresa empresa) throws ServicioCodefacException, java.rmi.RemoteException {
        MetodoInterfaceTransaccion transaccion = new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                crearConsumidorFinalSinTransaccion(empresa);
            }
        };
        ejecutarTransaccion(transaccion);

        return buscarConsumidorFinal(empresa);
    }

}
