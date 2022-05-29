/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.CompraFacturaReembolsoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Compra;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CompraFacturaReembolso;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraFacturaReembolsoServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author DellWin10
 */
public class CompraFacturaReembolsoService extends ServiceAbstract<CompraFacturaReembolso, CompraFacturaReembolsoFacade> implements CompraFacturaReembolsoServiceIf{

    public CompraFacturaReembolsoService() throws RemoteException {
        super(CompraFacturaReembolsoFacade.class);
    }
    
    public List<CompraFacturaReembolso> buscarPorCompra(Compra compra)  throws ServicioCodefacException, RemoteException
    {
        Map<String, Object> mapParametros = new HashMap<String, Object>();
        mapParametros.put("compra", compra);
        //compra.get
        List<CompraFacturaReembolso> resultadoCompra = getFacade().findByMap(mapParametros);
        return resultadoCompra;
        
    }    
    
}
