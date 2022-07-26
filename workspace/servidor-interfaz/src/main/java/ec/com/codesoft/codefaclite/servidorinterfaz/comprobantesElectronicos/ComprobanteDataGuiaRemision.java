/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.guiaRetencion.DestinatariosGuiaRemisionComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.guiaRetencion.DetalleGuiaRemisionComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.guiaRetencion.GuiaRemisionComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.guiaRetencion.InformacionGuiaRemision;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.retencion.InformacionRetencion;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriIdentificacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DestinatarioGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DetalleProductoGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriIdentificacionServiceIf;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadValidador;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class ComprobanteDataGuiaRemision implements ComprobanteDataInterface,Serializable {
    
    private GuiaRemision guiaRemision;
    private Map<String,String> mapInfoAdicional;
    private List<String> correosAdicionales;
    private ListenerComprobanteElectronico listener;

    public ComprobanteDataGuiaRemision(GuiaRemision guiaRemision) {
        this.guiaRemision = guiaRemision;
    }
    
    
    @Override
    public String getCodigoComprobante() {
        if(guiaRemision.getCodigoDocumentoEnum().equals(DocumentoEnum.GUIA_REMISION))
        {
            return ComprobanteEnum.GUIA_REMISION.getCodigo();
        }else if(guiaRemision.getCodigoDocumentoEnum().equals(DocumentoEnum.GUIA_REMISION_INTERNA))
        {
            return ComprobanteEnum.GUIA_REMISION_INTERNA.getCodigo();
        }
        return null;
    }

    @Override
    public String getSecuencial() {
        String secuencial = guiaRemision.getSecuencial().toString();
        return UtilidadesTextos.llenarCarateresIzquierda(secuencial, 9, "0");
    }

    @Override
    public Map<String, String> getMapAdicional() {
        if(mapInfoAdicional!=null)
        {
            for (Map.Entry<String, String> entry : mapInfoAdicional.entrySet()) {
                String key = entry.getKey();
                String value = UtilidadValidador.normalizarTextoCorreo(entry.getValue());
                mapInfoAdicional.put(key, value);
            }
        }
        return mapInfoAdicional;
    }

    @Override
    public List<String> getCorreos() {
        List<String> correos=new ArrayList<String>();
        /*
        if(guiaRemision!=null && guiaRemision.getProveedor()!=null)
            correos.add(guiaRemision.getProveedor().getCorreoElectronico());
        
        //Agregar correos adicionales , solo si estan seteados los valores de los correos       
        if(this.correosAdicionales!=null)
        {
            for (String correo : this.correosAdicionales) {
                if(!correos.contains(correo))
                {
                    correos.add(correo);
                }
            }
        }*/
        return correos;
    }

    @Override
    public ComprobanteElectronico getComprobante() {
        GuiaRemisionComprobante guiaRemicion=new GuiaRemisionComprobante();
        InformacionGuiaRemision info=new InformacionGuiaRemision();
        
        //Revisar que codigo debe ir aqui , aunque en el SRI dice que es opcional
        info.setContribuyenteEspecial("123");
        info.setDirEstablecimiento(UtilidadValidador.normalizarTexto(guiaRemision.getDireccionEstablecimiento()));
        info.setFechaIniTransporte(ComprobantesElectronicosUtil.dateToString(guiaRemision.getFechaIniciaTransporte()));
        info.setFechaFinTransporte(ComprobantesElectronicosUtil.dateToString(guiaRemision.getFechaFinTransporte()));
        info.setDirPartida(UtilidadValidador.normalizarTexto(guiaRemision.getDireccionPartida()));
        info.setRazonSocialTransportista(UtilidadValidador.normalizarTexto(guiaRemision.getRazonSocial()));
        //info.setTipoIdentificacionTransportista(""); //Buscar tipo de identificacion
        info.setRucTransportista(guiaRemision.getTransportista().getIdentificacion());
        //info.setRise(""); TODO:Analizar en que caso debe salir
        info.setPlaca(guiaRemision.getPlaca());
        
        
        //info.setFechaEmision(ComprobantesElectronicosUtil.dateToString(guiaRemision.getFechaEmision()));
        
        
        SriIdentificacionServiceIf servicioSri = ServiceFactory.getFactory().getSriIdentificacionServiceIf();
        SriIdentificacion sriIdentificacion = null;
        try {
            sriIdentificacion = servicioSri.obtenerPorTransaccionEIdentificacion(guiaRemision.getTransportista().getTipoIdentificacionEnum(), SriIdentificacion.tipoTransaccionEnum.VENTA);
            info.setTipoIdentificacionTransportista(sriIdentificacion.getCodigo());
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobanteDataNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*try {
            sriIdentificacion=servicioSri.obtenerPorTransaccionEIdentificacion(guiaRemision.getProveedor().getTipoIdentificacionEnum(), SriIdentificacion.tipoTransaccionEnum.VENTA);
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobanteDataNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(sriIdentificacion!=null && sriIdentificacion.getCodigo().equals(SriIdentificacion.CEDULA_IDENTIFICACION))
            info.setIdentificacionSujetoRetenido(guiaRemision.getProveedor().getIdentificacion());
        else
            info.setIdentificacionSujetoRetenido(UtilidadesTextos.llenarCarateresDerecha(guiaRemision.getProveedor().getIdentificacion(), 13, "0"));
        
        */
        /**
         * Verificar que valor no mas acepta
         */
        info.setObligadoContabilidad(guiaRemision.getObligadoLlevarContabilidad()); //Esta parte deberia agregar en la informacion del transportista
        
        if (guiaRemision.getContribuyenteEspecial() != null && !guiaRemision.getContribuyenteEspecial().trim().isEmpty()) {
            info.setContribuyenteEspecial(guiaRemision.getContribuyenteEspecial());
        }
        
        //Todo: Revisar este caso porque en los clientes coincide pero para las proveedores ya no coincide el codigo de tipo de identifiacion

        /**
         * Llenar los detalles de las retenciones
         */
        List<DestinatariosGuiaRemisionComprobante> detalleDestinatarios=new ArrayList<DestinatariosGuiaRemisionComprobante>();
        
        for (DestinatarioGuiaRemision destinatario : guiaRemision.getDestinatarios()) {
            DestinatariosGuiaRemisionComprobante destinatarioData=new DestinatariosGuiaRemisionComprobante();
            destinatarioData.setCodDocSustento("01");
            destinatarioData.setCodEstabDestino(destinatario.getCodigoEstablecimiento());//Todo: Verificar porque este campo deberia trabajar con sucursales
            
            //TODO: Este código solo es temporal para corregir unos problemas de esta versión, pero en un futuro se debe quitar
            if(destinatario.getDireccionDestino()==null || destinatario.getDireccionDestino().trim().isEmpty())
            {
                destinatario.setDireccionDestino("Sin especificar");
            }
            
            destinatarioData.setDirDestinatario(UtilidadValidador.normalizarTexto(destinatario.getDireccionDestino()));
            destinatarioData.setDocAduaneroUnico(" ");//Todo: Ver este campo debe estar grabando pero no esta
            destinatarioData.setFechaEmisionDocSustento(ComprobantesElectronicosUtil.dateToString(destinatario.getFechaEmision()));
            destinatarioData.setIdentificacionDestinatario(destinatario.getDestinatorio().getIdentificacion()); //Todo: este dato deberia mejor estar grabado porque se supone que no se puden modificar por ningun motivo
            destinatarioData.setMotivoTraslado(destinatario.getMotivoTranslado());
            destinatarioData.setNumAutDocSustento(destinatario.getAutorizacionNumero());
            destinatarioData.setRazonSocialDestinatario(UtilidadValidador.normalizarTexto(destinatario.getRazonSocial()));
            destinatarioData.setRuta(destinatario.getRuta());
            destinatarioData.setNumDocSustento(destinatario.getPreimpreso());
            detalleDestinatarios.add(destinatarioData);
            
            ///////// Obtener los detalles de los productos de cada destinatario ///////////////////////////
            List<DetalleGuiaRemisionComprobante> detalleProductos=new ArrayList<DetalleGuiaRemisionComprobante>();
            for (DetalleProductoGuiaRemision producto : destinatario.getDetallesProductos()) {
                DetalleGuiaRemisionComprobante productoData=new DetalleGuiaRemisionComprobante();
                productoData.setCantidad(producto.getCantidad());
                //productoData.setCodigoAdicional(producto.getCodigoAdicional());
                productoData.setCodigoInterno(producto.getCodigoInterno());
                productoData.setDescripcion(producto.getDescripcion());
                detalleProductos.add(productoData);
            }
            destinatarioData.setDetalles(detalleProductos);
        }

        guiaRemicion.setInfoGuiaRemision(info);
        guiaRemicion.setDestinatarios(detalleDestinatarios);
        
        return guiaRemicion;
    }

    @Override
    public ListenerComprobanteElectronico getListenerComprobanteElectronico() {
        return listener;
    }

    @Override
    public Long getComprobanteId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setMapInfoAdicional(Map<String, String> mapInfoAdicional) {
        this.mapInfoAdicional = mapInfoAdicional;
    }

    @Override
    public String getPuntoEmision() {
        return this.guiaRemision.getPuntoEmision().toString();
    }

    @Override
    public String getEstablecimiento() {
        return this.guiaRemision.getPuntoEstablecimiento().toString();
    }

    @Override
    public Empresa getEmpresa() {
        return guiaRemision.getEmpresa();
    }

    @Override
    public String getDireccionMatriz() {
        return guiaRemision.getDireccionMatriz();
    }
    
    
    
}
