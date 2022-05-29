/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Prestamo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.PrestamoCuota;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceAbstractIf;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface PrestamoServiceIf extends ServiceAbstractIf<Prestamo>{
    public List<PrestamoCuota> buscarCuotasPorPrestamo(Prestamo prestamo) throws RemoteException ,ServicioCodefacException;
}
