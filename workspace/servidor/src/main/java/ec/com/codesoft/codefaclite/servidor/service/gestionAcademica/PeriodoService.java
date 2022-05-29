/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.gestionAcademica;

import ec.com.codesoft.codefaclite.servidor.facade.gestionAcademica.PeriodoFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PeriodoServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author CodesoftDesarrollo
 */
public class PeriodoService extends ServiceAbstract<Periodo, PeriodoFacade> implements PeriodoServiceIf {

    private PeriodoFacade periodoFacade;

    public PeriodoService() throws RemoteException {
        super(PeriodoFacade.class);
        this.periodoFacade = new PeriodoFacade();
    }
    
    public Periodo buscarPorNombreyEstado(String nombre,GeneralEnumEstado estado) throws RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("nombre",nombre);
        mapParametros.put("estado",estado.getEstado());
        
        List<Periodo> periodos=getFacade().findByMap(mapParametros);
        if(periodos.size()>0)
        {
            return periodos.get(0);
        }
        return null;
    }

    /*
    public Periodo grabar(Periodo p) throws ServicioCodefacException {
        try {
            periodoFacade.create(p);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(AulaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("La clave principal ya existe en el sistema");
        } catch (DatabaseException ex) {
            Logger.getLogger(AulaService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException("Error con la base de datos periodo");
        }
        return p;
    }*/
    
    public List<Periodo> obtenerPeriodoActivo() throws RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        List<Periodo> periodos= getFacade().findByMap(mapParametros);
        return periodos;
    }
    
    public Periodo obtenerUnicoPeriodoActivo() throws RemoteException
    {
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        List<Periodo> periodos= getFacade().findByMap(mapParametros);
        if(periodos.size()>0)
        {
            return periodos.get(0);
        }
        return null;
    }
    
    
    
    public void setearPeriodoActivo(Periodo periodoActivar) throws RemoteException,ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                List<Periodo> periodos=obtenerPeriodosSinEliminar();
                for (Periodo periodo : periodos) 
                {
                    if(periodoActivar.equals(periodo))
                    {                        
                        periodo.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
                    }
                    else
                    {
                        periodo.setEstado(GeneralEnumEstado.INACTIVO.getEstado());
                    }
                    entityManager.merge(periodo);
                }
                
            }
        });
    }
    
    public List<Periodo> obtenerPeriodosSinEliminar() throws RemoteException
    {
        return getFacade().getPeriodosSinEliminar();
    }

    public void editar(Periodo p) {
        periodoFacade.edit(p);
    }

    public void eliminar(Periodo p) {
        p.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
        periodoFacade.edit(p);
    }
}
