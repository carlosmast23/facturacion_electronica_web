/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.retencion.DetalleRetencionComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.retencion.InformacionRetencion;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.retencion.RetencionComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriIdentificacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriIdentificacionServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadValidador;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ComprobanteDataRetencion implements ComprobanteDataInterface,Serializable {
    
    private Retencion retencion;
    private Map<String,String> mapInfoAdicional;
    private List<String> correosAdicionales;
    private ListenerComprobanteElectronico listener;

    public ComprobanteDataRetencion(Retencion retencion) {
        this.retencion = retencion;
    }
    
    
    @Override
    public String getCodigoComprobante() {
        return ComprobanteEnum.COMPROBANTE_RETENCION.getCodigo();
    }

    @Override
    public String getSecuencial() {
        String secuencial = retencion.getSecuencial().toString();
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
        if(retencion!=null && retencion.getProveedor()!=null)
            correos.add(retencion.getProveedor().getCorreoElectronico());
        
        //Agregar correos adicionales , solo si estan seteados los valores de los correos       
        if(this.correosAdicionales!=null)
        {
            for (String correo : this.correosAdicionales) {
                if(!correos.contains(correo))
                {
                    correos.add(correo);
                }
            }
        }
        return correos;
    }

    @Override
    public ComprobanteElectronico getComprobante() {
        RetencionComprobante retencionComprobante=new RetencionComprobante();
        InformacionRetencion info=new InformacionRetencion();
        
        //TODO:Revisar que codigo debe ir aqui , aunque en el SRI dice que es opcional
        //info.setContribuyenteEspecial("123");
        info.setDirEstablecimiento(UtilidadValidador.normalizarTexto(retencion.getDireccionEstablecimiento()));
        info.setFechaEmision(ComprobantesElectronicosUtil.dateToString(retencion.getFechaEmision()));
        
        SriIdentificacionServiceIf servicioSri=ServiceFactory.getFactory().getSriIdentificacionServiceIf();
        SriIdentificacion sriIdentificacion=null;
        try {
            sriIdentificacion=servicioSri.obtenerPorTransaccionEIdentificacion(retencion.getProveedor().getTipoIdentificacionEnum(), SriIdentificacion.tipoTransaccionEnum.VENTA);
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobanteDataNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(sriIdentificacion!=null && sriIdentificacion.getCodigo().equals(SriIdentificacion.CEDULA_IDENTIFICACION))
            info.setIdentificacionSujetoRetenido(retencion.getProveedor().getIdentificacion());
        else
            info.setIdentificacionSujetoRetenido(UtilidadesTextos.llenarCarateresDerecha(retencion.getProveedor().getIdentificacion(), 13, "0"));
        /**
         * Verificar que valor no mas acepta
         */
        info.setObligadoContabilidad(retencion.getObligadoLlevarContabilidad());
        
        if (retencion.getContribuyenteEspecial() != null && !retencion.getContribuyenteEspecial().trim().isEmpty()) {
            info.setContribuyenteEspecial(retencion.getContribuyenteEspecial());
        }
        
        info.setPeriodoFiscal(retencion.getPeriodoFiscal());
        info.setRazonSocialSujetoRetenido(UtilidadValidador.normalizarTexto(retencion.getProveedor().getRazonSocial()));
        
        //Todo: Revisar este caso porque en los clientes coincide pero para las proveedores ya no coincide el codigo de tipo de identifiacion
        info.setTipoIdentificacionSujetoRetenido(sriIdentificacion.getCodigo());

        /**
         * Llenar los detalles de las retenciones
         */
        List<DetalleRetencionComprobante> detalles=new ArrayList<DetalleRetencionComprobante>();
        
        for (RetencionDetalle detalle : retencion.getDetalles()) {
            
            DetalleRetencionComprobante detalleComprobante=new DetalleRetencionComprobante();
            
            detalleComprobante.setCodigo(detalle.getCodigoSri());
            detalleComprobante.setCodigoRetencion(detalle.getCodigoRetencionSri());
            
            detalleComprobante.setBaseImponible(detalle.getBaseImponible());
            detalleComprobante.setPorcentajeRetener(detalle.getPorcentajeRetener());
            detalleComprobante.setValorRetenido(detalle.getValorRetenido());
            
            //Todo: por el momento solo guardo el 001 porque solo se emiten retenciones de facturas, pero este campo deberia guardarse para los ats supongo
            detalleComprobante.setCodDocSustento("01");
            detalleComprobante.setNumDocSustento(retencion.getPreimpresoDocumento());
            //detalleComprobante.setNumDocSustento(retencion.getCompra().getPreimpreso().replace("-",""));
            detalleComprobante.setFechaEmisionDocSustento(UtilidadesFecha.formatDate(retencion.getFechaEmisionDocumento(),"dd/MM/yyyy"));
            //detalleComprobante.setFechaEmisionDocSustento(UtilidadesFecha.formatDate(retencion.getCompra().getFechaFactura(),"dd/MM/yyyy"));
            
            detalles.add(detalleComprobante);                                
        }

        retencionComprobante.setInfoRetencion(info);
        retencionComprobante.setDetalles(detalles);
        
        return retencionComprobante;
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
        return retencion.getPuntoEmision().toString();
    }

    @Override
    public String getEstablecimiento() {
        return retencion.getPuntoEstablecimiento().toString();
    }

    @Override
    public Empresa getEmpresa() {
        return retencion.getEmpresa();
    }

    @Override
    public String getDireccionMatriz() {
        return retencion.getDireccionMatriz();
    }
    
    
    
}
