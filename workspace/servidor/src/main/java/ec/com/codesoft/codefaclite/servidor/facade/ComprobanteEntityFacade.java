/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.FlushModeType;
import javax.persistence.Query;

/**
 *
 * @author
 */          
public class ComprobanteEntityFacade extends AbstractFacade<ComprobanteEntity> {

    public ComprobanteEntityFacade() {
        super(ComprobanteEntity.class);
    }
    
    
    public List<ComprobanteEntity> validarSecuencialRepetidoComprobanteFacade(Empresa empresa,DocumentoEnum documentoEnum,BigDecimal puntoEstablecimiento,Integer puntoEmision,Integer Secuencial)
    {

        String nombreTabla=getNameTableByDocument(documentoEnum);
        
        /*Factura f;
        f.getCodigoDocumento()
        //f.getEmpresa();
        f.getPuntoEstablecimiento();
        f.getPuntoEmision();
        f.getSecuencial();*/
        
        String queryString = "SELECT f FROM "+nombreTabla+" f WHERE f.estado<>?1 AND f.empresa=?2 AND f.puntoEstablecimiento=?3 AND f.puntoEmision=?4 AND f.secuencial=?5 AND f.codigoDocumento=?6 ";
        Query query = getEntityManager().createQuery(queryString);
        
        query.setParameter(1, ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado()); //TODO: Buscar cualquier }
        
        query.setParameter(2, empresa);
        query.setParameter(3, puntoEstablecimiento);
        query.setParameter(4, puntoEmision);
        query.setParameter(5, Secuencial);
        query.setParameter(6, documentoEnum.getCodigo());
        //TODO: Cambio temporal para ver si resuelve un problema al momento de facturar en el ID de Nacionalidad
        query.setFlushMode(FlushModeType.COMMIT);
        return query.getResultList();
        
    }
    
    /**
     * TODO: Unificar logica con el metodo de arriba
     * @param empresa
     * @param documentoEnum
     * @param puntoEstablecimiento
     * @param puntoEmision
     * @param Secuencial
     * @return 
     */
    public Integer getSecuencialUltimoFacade(Empresa empresa,DocumentoEnum documentoEnum,BigDecimal puntoEstablecimiento,Integer puntoEmision)
    {
        String nombreTabla=getNameTableByDocument(documentoEnum);
        
        /*Factura f;
        f.getCodigoDocumento()
        //f.getEmpresa();
        f.getPuntoEstablecimiento();
        f.getPuntoEmision();
        f.getSecuencial();*/
        
        String queryString = "SELECT max( CAST(f.secuencial as INT) ) FROM "+nombreTabla+" f WHERE f.estado<>?1 AND f.empresa=?2 AND f.puntoEstablecimiento=?3 AND f.puntoEmision=?4  AND f.codigoDocumento=?6 ";
        Query query = getEntityManager().createQuery(queryString);
        
        query.setParameter(1, ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado()); //TODO: Buscar cualquier 
        query.setParameter(2, empresa);
        query.setParameter(3, puntoEstablecimiento);
        query.setParameter(4, puntoEmision);
        query.setParameter(6, documentoEnum.getCodigo());
        
        
        String queryString2 = "SELECT f FROM "+nombreTabla+" f WHERE f.estado<>?1 AND f.empresa=?2 AND f.puntoEstablecimiento=?3 AND f.puntoEmision=?4  AND f.codigoDocumento=?6 ";
        Query query2 = getEntityManager().createQuery(queryString2);
        
        query2.setParameter(1, ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado()); //TODO: Buscar cualquier 
        query2.setParameter(2, empresa);
        query2.setParameter(3, puntoEstablecimiento);
        query2.setParameter(4, puntoEmision);
        query2.setParameter(6, documentoEnum.getCodigo());
        
        Object resultado=query2.getResultList();
        
        return (Integer) query.getSingleResult();
    }
    
    private String getNameTableByDocument(DocumentoEnum documentoEnum)
    {
        String nombreTabla="";
        switch(documentoEnum)
        {
            case FACTURA:
                nombreTabla="Factura";
                break;

            case NOTA_VENTA:
                nombreTabla="Factura";
                break;
                
            case NOTA_VENTA_INTERNA:
                nombreTabla="Factura";
                break;


            case RETENCIONES:
                nombreTabla="Retencion";
                break;

            case NOTA_CREDITO:
                 nombreTabla="NotaCredito";
                break;

            case GUIA_REMISION_INTERNA:
            case GUIA_REMISION:
                 nombreTabla="GuiaRemision";
                break;
                
            case LIQUIDACION_COMPRA:
                 nombreTabla="Factura";
                break;
        }
        return nombreTabla;
    }
}
