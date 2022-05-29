/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.reporte;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionAdicional;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.TotalImpuesto;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Carlos
 */
public abstract class ComprobanteElectronicoReporte 
{
    public ComprobanteElectronico comprobante;
    //public InputStream imagenEmpresa;
    
    public abstract List<Object> getDetalles();
    protected abstract Map<String,Object> getMapTotales();
    protected abstract List getListFormasPago();
    protected abstract Map<String,Object> getMapInfoCliente();

    public ComprobanteElectronicoReporte(ComprobanteElectronico comprobante) {
        this.comprobante = comprobante;
    }
    
    
    //TODO: Ver si se puede optimizar el parametro para poner como propiedad
    protected Map<String,Object> getMapInfoTributaria(Map<ComprobanteEnum, String> aliasNombreDocumentosMap)
    {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("ruc",comprobante.getInformacionTributaria().getRuc());
        //String secuencial=comprobante.getInformacionTributaria().getPuntoEmision()+"-"+comprobante.getInformacionTributaria().getEstablecimiento()+"-"+comprobante.getInformacionTributaria().getSecuencial();
        String secuencial=comprobante.getPreimpreso();
        map.put("secuencial",secuencial);
        map.put("autorizacion",comprobante.getInformacionTributaria().getClaveAcceso());
        //map.put("estado","");
        map.put("fecha_hora_autorizacion","");
        
        //Buscar si tiene un alias para el nombre del documento
        ComprobanteEnum comprobanteEnum = comprobante.getInformacionTributaria().getCodigoDocumentoEnum();
        String nombreDocumento = comprobanteEnum.getNombre();
        if(aliasNombreDocumentosMap!=null)
        {
            String nombreDocumentoTmp=aliasNombreDocumentosMap.get(comprobante.getInformacionTributaria().getCodigoDocumentoEnum());
            if(nombreDocumentoTmp!=null && !nombreDocumentoTmp.trim().isEmpty())
            {
                nombreDocumento=nombreDocumentoTmp;
            }
        }
        
        map.put("nombre_documento",nombreDocumento);
        String codAmbiente=comprobante.getInformacionTributaria().getAmbiente();
        if(codAmbiente.equals(ComprobanteElectronicoService.CODIGO_SRI_MODO_PRODUCCION.toString()))
        {
             map.put("ambiente",ComprobanteElectronicoService.MODO_PRODUCCION);
        }
        else
            map.put("ambiente",ComprobanteElectronicoService.MODO_PRUEBAS);
        
        return map;
    }
    
    protected Map<String, Object> getMapInfoEmpresa() {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("razon_social",comprobante.getInformacionTributaria().getRazonSocial());
        map.put("nombre_legal",comprobante.getInformacionTributaria().getNombreComercial());
        map.put("direccion",comprobante.getInformacionTributaria().getDirecionMatriz());
        //Cambiar por la direccion de las sucursales cuando exista
        String direccionEstablecimiento=comprobante.getInformacionTributaria().getDirecionMatriz();
        if(comprobante.getDireccionEstablecimiento()!=null && !comprobante.getDireccionEstablecimiento().isEmpty())
        {
            direccionEstablecimiento=comprobante.getDireccionEstablecimiento();
        }
    
        map.put("direccion_sucursal",direccionEstablecimiento);
        map.put("contribuyente_especial",comprobante.getContribuyenteEspecial());
        map.put("obligado_contabilidad",comprobante.getObligadoLlevarContabilidad());
        return map;
    }
    
    public List<InformacionAdicional> getListInfoAdifional()
    {
        return comprobante.getInformacionAdicional();
    }
    

    public Map<String,Object> getMapReporte(Map<ComprobanteEnum, String> aliasNombreDocumentosMap)
    {
        //try {
            Map<String,Object> map=new HashMap<String,Object>();
            map.putAll(getMapInfoTributaria(aliasNombreDocumentosMap));
            map.putAll(getMapInfoEmpresa());
            map.putAll(getMapTotales());
            map.putAll(getMapInfoCliente());
            
            //map.put("imagen_logo",(imagenEmpresa!=null)?IOUtils.toBufferedInputStream(imagenEmpresa):null);
            map.put("formaPagoList", getListFormasPago());
            map.put("informacionAdicionalList", getListInfoAdifional());
            return map;
        //} catch (IOException ex) {
        //    Logger.getLogger(ComprobanteElectronicoReporte.class.getName()).log(Level.SEVERE, null, ex);
        //}
        //return null;
    }
    
    public ImpuestosTotalesResponse calcularImpuestos(List<TotalImpuesto> impuestos)
    { 
        BigDecimal subTotalCero=BigDecimal.ZERO;
        BigDecimal subTotalImpuesto=BigDecimal.ZERO;
        BigDecimal iva=BigDecimal.ZERO;
        //BigDecimal descuentos=BigDecimal.ZERO;
        BigDecimal ice=BigDecimal.ZERO;
        
        for (TotalImpuesto impuesto : impuestos) {
            
            if(impuesto.getCodigo().equals("2"))//TODO: Parametriza esta valor por el momento e codigo 1 significa IVA
            {
                if(impuesto.getValor().compareTo(BigDecimal.ZERO)==0)
                {
                    subTotalCero=subTotalCero.add(impuesto.getBaseImponible());

                }
                else
                {
                    subTotalImpuesto=subTotalImpuesto.add(impuesto.getBaseImponible());
                    iva=iva.add(impuesto.getValor());
                }
            }
            else if(impuesto.getCodigo().equals("3")) //TODO: Parametrizar este valor por el momento significa ICE
            {
                ice=ice.add(impuesto.getValor());
            }
        }
        
        ImpuestosTotalesResponse respuesta=new ImpuestosTotalesResponse();
        respuesta.ice=ice;
        respuesta.iva=iva;
        respuesta.subTotalCero=subTotalCero;
        respuesta.subTotalImpuesto=subTotalImpuesto;
        return respuesta;
    }
    
    public class ImpuestosTotalesResponse
    {
        public BigDecimal subTotalCero=BigDecimal.ZERO;
        public BigDecimal subTotalImpuesto=BigDecimal.ZERO;
        public BigDecimal iva=BigDecimal.ZERO;
        public BigDecimal ice=BigDecimal.ZERO;

        public ImpuestosTotalesResponse() {
        }

        
    }
    
}
