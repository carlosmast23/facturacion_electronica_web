/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios.gestionacademica;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.DescuentoAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ServiceAbstractIf;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @auhor
 */
public interface DescuentoAcademicoServiceIf extends ServiceAbstractIf<DescuentoAcademico>{
    public List<DescuentoAcademico> obtenerDescuentoActivosPorPeriodoActivo()throws ServicioCodefacException, RemoteException;
    public List<DescuentoAcademico> obtenerDescuentoActivosPorPeriodoActivo(DescuentoAcademico.TipoEnum tipoEnum)throws ServicioCodefacException, RemoteException;
}
