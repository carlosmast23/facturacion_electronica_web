/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.reporte;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.guiaRetencion.DestinatariosGuiaRemisionComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.guiaRetencion.DetalleGuiaRemisionComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.guiaRetencion.GuiaRemisionComprobante;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author
 */
public class GuiaRemisionReporte extends ComprobanteElectronicoReporte {

    private GuiaRemisionComprobante comprobante;
    private JasperReport detalleReporte;
    
    public GuiaRemisionReporte(ComprobanteElectronico comprobante,JasperReport detalleReporte) {
        super(comprobante);
        this.comprobante = (GuiaRemisionComprobante) comprobante;
        this.detalleReporte=detalleReporte;
    }

    @Override
    public List<Object> getDetalles() {
        List<Object> destinatarios=new ArrayList<Object>();
        
        for (DestinatariosGuiaRemisionComprobante destinatarioTemp : comprobante.getDestinatarios()) {
            
            GuiaRemisionDestinatariosReporte destinatario=new GuiaRemisionDestinatariosReporte();
            destinatario.setDestino(destinatarioTemp.getDirDestinatario());
            destinatario.setDocumentoAduanero(destinatarioTemp.getDocAduaneroUnico());
            destinatario.setEstablecimientoDestino(destinatarioTemp.getCodEstabDestino());
            destinatario.setFecha_emision(destinatarioTemp.getFechaEmisionDocSustento());
            destinatario.setIdentificacion_persona(destinatarioTemp.getIdentificacionDestinatario());
            destinatario.setMotivo_traslado(destinatarioTemp.getMotivoTraslado());
            destinatario.setNumero_autorizacion(destinatarioTemp.getNumAutDocSustento());
            destinatario.setPreimpreso(destinatarioTemp.getNumDocSustento());
            destinatario.setRazon_social_persona(destinatarioTemp.getRazonSocialDestinatario());
            //destinatario.setRuta(destinatarioTemp.getRuta());
            destinatario.setRuta(destinatarioTemp.getRuta());
            
            /**
             * Agregar los productos de cada destinatario como un subreporte
             */
            List<GuiaRemisionProductosReporte> productos=new ArrayList<GuiaRemisionProductosReporte>();
            if(destinatarioTemp!=null && destinatarioTemp.getDetalles()!=null)
            {
                for (DetalleGuiaRemisionComprobante productoTmp : destinatarioTemp.getDetalles()) {
                    GuiaRemisionProductosReporte producto=new GuiaRemisionProductosReporte();
                    producto.setCantidad(productoTmp.getCantidad()+"");
                    producto.setCodigo_principal(productoTmp.getCodigoInterno());
                    producto.setCodigo_secundario(productoTmp.getCodigoAdicional());
                    producto.setDescripcion(productoTmp.getDescripcion());
                    productos.add(producto);
                }
            }
            
            destinatario.setProductos(new JRBeanCollectionDataSource(productos)); //Agrega la lista de productos al reporte
            destinatarios.add(destinatario);
        }
        return  destinatarios;
    }

    @Override
    protected Map<String, Object> getMapTotales() {
        return new HashMap<String,Object>();
    }

    @Override
    protected List getListFormasPago() {
        return null;
    }

    @Override
    protected Map<String, Object> getMapInfoCliente() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("transportista_identificacion", comprobante.getInfoGuiaRemision().getRucTransportista());
        map.put("trasportista_nombres", comprobante.getInfoGuiaRemision().getRazonSocialTransportista());
        map.put("punto_partida", comprobante.getInfoGuiaRemision().getDirPartida());
        map.put("fechaInicio", comprobante.getInfoGuiaRemision().getFechaIniTransporte());
        map.put("placa", comprobante.getInfoGuiaRemision().getPlaca());
        map.put("fechaFin", comprobante.getInfoGuiaRemision().getFechaFinTransporte());
        map.put("pl_url_detalle",detalleReporte);
        map.put("obligado_contabilidad",comprobante.getInfoGuiaRemision().getObligadoContabilidad());
        return map;
    }
    
}
