/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.auxiliar;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexItemEspecifico;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.io.Serializable;

/**
 *
 * @author
 */
public class KardexDetalleTmp extends KardexDetalle implements Serializable{
    public boolean seleccion;
    public Bodega bodega;
   
    public KardexDetalle obtenerObjetoOriginal()
    {
        KardexDetalle kardexDetalle=new KardexDetalle();
        kardexDetalle.setCantidad(this.getCantidad());
        kardexDetalle.setCodigoTipoDocumento(this.getCodigoTipoDocumento());
        kardexDetalle.setFechaCreacion(this.getFechaCreacion());
        kardexDetalle.setFechaIngreso(this.getFechaIngreso());
        kardexDetalle.setFechaDocumento(UtilidadesFecha.castDateUtilToSql(this.getFechaIngreso()));
        kardexDetalle.setKardex(this.getKardex());
        kardexDetalle.setPrecioTotal(this.getPrecioTotal());
        kardexDetalle.setPrecioUnitario(this.getPrecioUnitario());
        kardexDetalle.setReferenciaDocumentoId(this.getReferenciaDocumentoId());
        kardexDetalle.setSecuencial(this.getSecuencial());
        kardexDetalle.setPuntoEstablecimiento(this.getPuntoEstablecimiento());
        kardexDetalle.setPuntoEmision(this.getPuntoEmision());
        
        if(getDetallesEspecificos()!=null)
        {
            for (KardexItemEspecifico item : getDetallesEspecificos()) {
                KardexItemEspecificoTemp itemTmp = (KardexItemEspecificoTemp) item;
                kardexDetalle.addDetalle(itemTmp.obtenerObjetoOriginal());
            }
        }
        
        return kardexDetalle;
        
    }

}
