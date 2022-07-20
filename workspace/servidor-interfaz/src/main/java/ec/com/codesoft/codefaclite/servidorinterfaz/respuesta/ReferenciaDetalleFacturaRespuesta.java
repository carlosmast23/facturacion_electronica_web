/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.respuesta;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import java.io.Serializable;

/**
 *
 * @auhor
 */
public class ReferenciaDetalleFacturaRespuesta implements Serializable{
    public CatalogoProducto catalogoProducto;
    public Long referenciaId;
    public String codigoPrincipal;
    public TipoDocumentoEnum tipoDocumentoEnum;
    public Object objecto;

    public ReferenciaDetalleFacturaRespuesta() {
    }
    
    

    public ReferenciaDetalleFacturaRespuesta(CatalogoProducto catalogoProducto, Long referenciaId, TipoDocumentoEnum tipoDocumentoEnum, Object objecto) {
        this.catalogoProducto = catalogoProducto;
        this.referenciaId = referenciaId;
        this.tipoDocumentoEnum = tipoDocumentoEnum;
        this.objecto = objecto;
    }
    
    public String obtenerCodigoPrincipal()
    {
        if(codigoPrincipal!=null && !codigoPrincipal.isEmpty())
        {
            return codigoPrincipal;
        }
        else
        {
            if(referenciaId!=null)
            {
                return referenciaId+"";
            }
        }
        return "";
    }
    
}
