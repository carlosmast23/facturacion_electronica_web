/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo
 */
public interface PeriodoServiceIf extends ServiceAbstractIf<Periodo>{
    public List<Periodo> obtenerPeriodoActivo() throws RemoteException;
    public Periodo obtenerUnicoPeriodoActivo() throws RemoteException;
    public List<Periodo> obtenerPeriodosSinEliminar() throws RemoteException;
    public void setearPeriodoActivo(Periodo periodoActivar) throws RemoteException,ServicioCodefacException;
    public Periodo buscarPorNombreyEstado(String nombre,GeneralEnumEstado estado) throws RemoteException;
}
