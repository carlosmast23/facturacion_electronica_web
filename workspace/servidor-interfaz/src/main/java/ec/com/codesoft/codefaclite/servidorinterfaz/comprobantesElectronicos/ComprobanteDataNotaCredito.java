/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.InformacionComprobanteAbstract;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.ImpuestoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.TotalImpuesto;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito.DetalleNotaCreditoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito.InformacionNotaCredito;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.notacredito.NotaCreditoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.CategoriaProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCreditoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriIdentificacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.ReferenciaDetalleFacturaRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriIdentificacionServiceIf;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadValidador;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class ComprobanteDataNotaCredito extends ComprobanteDataFacturaNotaCreditoAbstract {
    private NotaCredito notaCredito;
    private Map<String,String> mapInfoAdicional;
    private List<String> correosAdicionales;
    private ListenerComprobanteElectronico listener;
    
    public ComprobanteDataNotaCredito(NotaCredito notaCredito) {
        this.notaCredito=notaCredito;
    }

    @Override
    public String getCodigoComprobante() {
        return ComprobanteEnum.NOTA_CREDITO.getCodigo();
    }

    @Override
    public String getSecuencial() {
       // String secuencial= this.session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_NOTA_CREDITO).getValor();
       String secuencial= notaCredito.getSecuencial().toString();       
       return UtilidadesTextos.llenarCarateresIzquierda(secuencial,9,"0");
    }

    @Override
    public Map<String, String> getMapAdicional() {
        //Validar el tipo de texto para quitar carcteres especiales
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
        if(notaCredito!=null && notaCredito.getCliente()!=null)
            correos.add(notaCredito.getCliente().getCorreoElectronico());
        
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
    
    public class InfoComprobante implements InfoComprobanteInterface{
        
        private InformacionNotaCredito info;

        public InfoComprobante(InformacionNotaCredito info) {
            this.info = info;
        }

        @Override
        public void setFechaEmision(String fechaEmision) {
            this.info.setFechaEmision(fechaEmision);
        }

        @Override
        public void setDirEstablecimiento(String dirEstablecimiento) {
            this.info.setDirEstablecimiento(dirEstablecimiento);
        }

        @Override
        public void setIdentificacion(String identificacion) {
            this.info.setIdentificacionComprador(identificacion);
        }

        @Override
        public void setObligadoContabilidad(String obligadoContabilidad) {
            this.info.setObligadoContabilidad(obligadoContabilidad);
        }

        @Override
        public void setTipoIdentificacion(String tipoIdentificacion) {
            this.info.setTipoIdentificacionComprador(tipoIdentificacion);
        }

        @Override
        public void setContribuyenteEspecial(String contribuyenteEspecial) {
            this.info.setContribuyenteEspecial(contribuyenteEspecial);
        }
    
            
    }

    @Override
    public NotaCreditoComprobante getComprobante() {
        NotaCreditoComprobante notaCreditoComprobante=new NotaCreditoComprobante();
        InformacionNotaCredito info=new InformacionNotaCredito();
        info.setCodDocModificado(ComprobanteEnum.FACTURA.getCodigo());
        llenarInformacionComprobante(new InfoComprobante(info), notaCredito);
        
        //Revisar este dato porque solo se debe poner cuando sea contribuyente especial
        //info.setContribuyenteEspecial(claveAcceso);
        
        //info.setDirEstablecimiento(UtilidadValidador.normalizarTexto(notaCredito.getDireccionEstablecimiento()));
        //info.setFechaEmision(ComprobantesElectronicosUtil.dateToString(new java.sql.Date(notaCredito.getFechaEmision().getTime())));
        info.setFechaEmisionDocSustento(ComprobantesElectronicosUtil.dateToString(notaCredito.getFechaEmisionDocSustento()));
        
        /*SriIdentificacionServiceIf servicioSri=ServiceFactory.getFactory().getSriIdentificacionServiceIf();
        SriIdentificacion sriIdentificacion=null;
        try {
            sriIdentificacion=servicioSri.obtenerPorTransaccionEIdentificacion(notaCredito.getCliente().getTipoIdentificacionEnum(), SriIdentificacion.tipoTransaccionEnum.VENTA);
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobanteDataNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(sriIdentificacion!=null && sriIdentificacion.getCodigo().equals(SriIdentificacion.CEDULA_IDENTIFICACION))
        {
            info.setIdentificacionComprador(notaCredito.getCliente().getIdentificacion());
        }
        else
        {
            info.setIdentificacionComprador(UtilidadesTextos.llenarCarateresDerecha(notaCredito.getCliente().getIdentificacion(), 13, "0"));
        }*/
        /**
         * Verificar que valor no mas acepta
         */
        info.setMoneda("DOLAR");
        
        info.setMotivo(notaCredito.getRazonModificado());
        info.setNumDocModificado(notaCredito.getNumDocModificado());
        //info.setObligadoContabilidad(notaCredito.getObligadoLlevarContabilidad());
        info.setRazonSocialComprador(UtilidadValidador.normalizarTexto(notaCredito.getCliente().getRazonSocial()));
        
        /**
         * Consultar para que sirve el rise
         */
        //info.setRise(claveAcceso);
        
        //info.setTipoIdentificacionComprador(UtilidadValidador.normalizarTexto(getSriIdentificacion(notaCredito).getCodigo()));
        //info.setTotalImpuestos(totalImpuestos);
        info.setTotalSinImpuestos(notaCredito.getSubtotalDoce().add(notaCredito.getSubtotalCero()).subtract(notaCredito.getDescuentoImpuestos()).subtract(notaCredito.getDescuentoSinImpuestos()));
        info.setValorModificacion(notaCredito.getTotal());
        
        
        Map<Integer,TotalImpuesto> mapTotalImpuestos=new HashMap<Integer,TotalImpuesto>();
        
                /**
         * Informacion de los detalles
         */
        List<DetalleNotaCreditoComprobante> detallesComprobante=new ArrayList<DetalleNotaCreditoComprobante>();
        List<NotaCreditoDetalle> detallesNotaCredito= notaCredito.getDetalles();
        
        for (NotaCreditoDetalle detalleNotaCredito : detallesNotaCredito) {
            try {
                
                CatalogoProducto catalogoProducto=null;
                DetalleNotaCreditoComprobante detalle=new DetalleNotaCreditoComprobante();                
                ReferenciaDetalleFacturaRespuesta respuesta= ServiceFactory.getFactory().getFacturacionServiceIf().obtenerReferenciaDetalleFactura(detalleNotaCredito.getTipoDocumentoEnum(),detalleNotaCredito.getReferenciaId());
                detalle.setCodigoInterno(respuesta.referenciaId+"");
                
                detalle.setCantidad(detalleNotaCredito.getCantidad());
                detalle.setDescripcion(UtilidadValidador.normalizarTexto(detalleNotaCredito.getDescripcion()));
                //Establecer el descuento en el aplicativo
                detalle.setDescuento(detalleNotaCredito.getDescuento().setScale(2,BigDecimal.ROUND_HALF_UP));
                detalle.setPrecioTotalSinImpuesto(detalleNotaCredito.getTotal());
                detalle.setPrecioUnitario(detalleNotaCredito.getPrecioUnitario());
                
                
                //facturaDetalle.getProducto().get
                
                /**
                 * Agregado impuesto que se cobran a cada detalle individual
                 */
                
                //-------------> FIN <----------------
                //listaComprobantes.add(impuesto);
                
                detalle.setImpuestos(calcularImpuestos(detalleNotaCredito));
                
                detallesComprobante.add(detalle);
            } catch (RemoteException ex) {
                Logger.getLogger(ComprobanteDataNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(ComprobanteDataNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        notaCreditoComprobante.setDetalles(detallesComprobante);
        notaCreditoComprobante.setInfoNotaCredito(info);
        
                /**
         * Crear los impuestos totales
         */
        
        //notaCreditoComprobante.getInfoNotaCredito().setTotalImpuestos(crearImpuestosTotales(mapTotalImpuestos));
        notaCreditoComprobante.getInfoNotaCredito().setTotalImpuestos(crearImpuestosTotales(notaCredito));
        
        //Todo: Ver si se elimina el correo porque esta opcion no se esta usando para generar el comprobante
        notaCreditoComprobante.setCorreos(getCorreos());
        
        return notaCreditoComprobante;
    }

    public NotaCredito getNotaCredito() {
        return notaCredito;
    }

    public void setNotaCredito(NotaCredito notaCredito) {
        this.notaCredito = notaCredito;
    }

    public Map<String, String> getMapInfoAdicional() {
        return mapInfoAdicional;
    }

    public void setMapInfoAdicional(Map<String, String> mapInfoAdicional) {
        this.mapInfoAdicional = mapInfoAdicional;
    }

    public List<String> getCorreosAdicionales() {
        return correosAdicionales;
    }

    public void setCorreosAdicionales(List<String> correosAdicionales) {
        this.correosAdicionales = correosAdicionales;
    }

    public ListenerComprobanteElectronico getListener() {
        return listener;
    }

    public void setListener(ListenerComprobanteElectronico listener) {
        this.listener = listener;
    }
    
    

    @Override
    public ListenerComprobanteElectronico getListenerComprobanteElectronico() {
        return listener;
    }

    @Override
    public Long getComprobanteId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getPuntoEmision() {
        return notaCredito.getPuntoEmision().toString();
    }

    @Override
    public String getEstablecimiento() {
        return notaCredito.getPuntoEstablecimiento().toString();
    }

    @Override
    public Empresa getEmpresa() {
        return notaCredito.getEmpresa();
    }

    @Override
    public String getDireccionMatriz() {
        return notaCredito.getDireccionMatriz();
    }
    
}
