/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Nivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo
 */
public interface NivelAcademicoServiceIf extends ServiceAbstractIf<NivelAcademico>{
    public List<NivelAcademico> obtenerTodosActivosPorPeriodo(Periodo periodo) throws RemoteException;
    public void eliminarNivelAcademico(NivelAcademico n) throws RemoteException,ServicioCodefacException;
    public NivelAcademico obtenerPorNombreYEstadoYPeriodo(String nombre,GeneralEnumEstado estado,Periodo periodo) throws RemoteException;
    public NivelAcademico obtenerPorNombreYEstado(String nombre,GeneralEnumEstado estado) throws RemoteException;
    public List<NivelAcademico> buscarPorPeriodo(Periodo p) throws RemoteException,ServicioCodefacException;
    
}
