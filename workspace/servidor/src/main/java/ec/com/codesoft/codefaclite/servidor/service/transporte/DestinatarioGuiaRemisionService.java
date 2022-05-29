/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.transporte;

import ec.com.codesoft.codefaclite.servidor.facade.transporte.DestinatarioGuiaRemisionFacade;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DestinatarioGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.DestinatarioGuiaRemisionServiceIf;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class DestinatarioGuiaRemisionService extends ServiceAbstract<DestinatarioGuiaRemision,DestinatarioGuiaRemisionFacade> implements DestinatarioGuiaRemisionServiceIf{
    
    public DestinatarioGuiaRemisionService() throws RemoteException
    {
        super(DestinatarioGuiaRemisionFacade.class);        
    }
    
    public List<GuiaRemision> buscarGuiaRemisionPorFactura(Factura factura) throws ServicioCodefacException, RemoteException
    {
        List<DestinatarioGuiaRemision> destinatarioList=getFacade().obtenerGuiaRemision(factura);
        List<GuiaRemision> guiaRemisionList=new ArrayList<GuiaRemision>();
        
        for (DestinatarioGuiaRemision destinatarioGuiaRemision : destinatarioList) {
            if(!guiaRemisionList.contains(destinatarioGuiaRemision.getGuiaRemision()))
            {
                guiaRemisionList.add(destinatarioGuiaRemision.getGuiaRemision());
            }
            
        }
        
        return guiaRemisionList;
    }
}
