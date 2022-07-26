/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.FacturaDetalleFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturaDetalleServiceIf;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class FacturaDetalleService extends ServiceAbstract<FacturaDetalle, FacturaDetalleFacade> implements FacturaDetalleServiceIf {

    public FacturaDetalleService() throws RemoteException {
        super(FacturaDetalleFacade.class);
    }
    
    
    public Object getReferenciaDetalle(FacturaDetalle facturaDetalle) throws ServicioCodefacException,java.rmi.RemoteException
    {
        try {
            TipoDocumentoEnum tipoReferenciaEnum=facturaDetalle.getTipoDocumentoEnum();
            switch (tipoReferenciaEnum) {
                case ACADEMICO:
                    RubroEstudiante rubroEstudiante = ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                    return rubroEstudiante;
                    
                case PRESUPUESTOS:
                    Presupuesto presupuesto=ServiceFactory.getFactory().getPresupuestoServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                    return presupuesto;
                    
                case INVENTARIO:
                case LIBRE:
                    Producto producto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                    return producto;
                    
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaDetalleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
