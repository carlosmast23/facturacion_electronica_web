/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexItemEspecifico;
import ec.com.codesoft.codefaclite.servidor.facade.KardexItemEspecificoFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexItemEspecificoServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public class KardexItemEspecificoService extends ServiceAbstract<KardexItemEspecifico, KardexItemEspecificoFacade> implements  KardexItemEspecificoServiceIf
{
    
    public KardexItemEspecificoService() throws RemoteException {
        super(KardexItemEspecificoFacade.class);
    } 
    
    public int obtenerCantidadItemsEspecificosPorKardex(Producto producto) throws java.rmi.RemoteException,ServicioCodefacException
    {
        //KardexItemEspecifico kie;
        //kie.getKardexDetalle().getKardex().getProducto();
        return (int) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Map<String,Object> mapParametros=new HashMap<String,Object>();
                mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
                mapParametros.put("kardexDetalle.kardex.producto",producto);
                return getFacade().findByMap(mapParametros).size();
            }
        });
    }
    
    public List<KardexItemEspecifico> obtenerItemsEspecificosPorProducto(Producto producto) throws java.rmi.RemoteException,ServicioCodefacException
    {
        //KardexItemEspecifico kie;
        //kie.getKardexDetalle().getKardex().getProducto();
        return (List) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                Map<String,Object> mapParametros=new HashMap<String,Object>();
                mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
                mapParametros.put("kardexDetalle.kardex.producto",producto);
                return getFacade().findByMap(mapParametros);
            }
        });
    }
}
