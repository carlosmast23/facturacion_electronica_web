/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidor.facade.KardexDetalleFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexDetalleServiceIf;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 */
public class KardexDetalleService extends ServiceAbstract<KardexDetalle, KardexDetalleFacade> implements KardexDetalleServiceIf
{
    
    public KardexDetalleService() throws RemoteException {
        super(KardexDetalleFacade.class);
    }
    
    public KardexDetalle consultarPorReferencia(TipoDocumentoEnum tipoDocumentoEnum,Long referenciaDocumentoId,Producto producto) throws RemoteException
    {
        //KardexDetalle kd;
        //kd.getKardex().getProducto();
        //kd.getCodigoTipoDocumento();
        //kd.getCodigoTipoDocumentoEnum();
        //kd.getReferenciaDocumentoId();
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("codigoTipoDocumento",tipoDocumentoEnum.getCodigo());
        mapParametros.put("referenciaDocumentoId",referenciaDocumentoId);
        mapParametros.put("kardex.producto",producto);
        
        List<KardexDetalle> resultado=getFacade().findByMap(mapParametros);
        
        if(resultado.size()>0)
        {
            return resultado.get(0);
        }
        
        return null;
    }
    
}
