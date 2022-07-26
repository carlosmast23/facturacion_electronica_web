/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.factura;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.general.ParametrosClienteEscritorio;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataNotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCreditoAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCreditoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NotaCreditoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
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
public class NotaCreditoModelControlador extends FacturaNotaCreditoModelControladorAbstract{
    
    public NotaCreditoModelInterface interfaz;
    public SessionCodefacInterface session;

    public NotaCreditoModelControlador(NotaCreditoModelInterface interfaz, SessionCodefacInterface session, MensajeVistaInterface mensajeVista) {
        super(mensajeVista);
        this.interfaz = interfaz;
        this.session = session;
    }
    
    
    
    public void setearDatosFacturaEnNotaCredito(Factura factura,NotaCredito notaCredito)
    {
        notaCredito.setFactura(factura);
        notaCredito.setNumDocModificado(factura.getPreimpreso());
        setearDatosProveedor(factura.getCliente(),notaCredito); 
        cargarDatosNotaCredito(notaCredito);
        cargarDatosAdicionales(notaCredito);
        //cargarTablaDatosAdicionales();
        //mostrarDatosNotaCredito();
    }
    
    private void cargarDatosNotaCredito(NotaCredito notaCredito) {
        
        /**
         * Setear datos de la factura a la nota de credito
         */
        notaCredito.setTotal(notaCredito.getFactura().getTotal());
        notaCredito.setValorIvaDoce(notaCredito.getFactura().getIva());
        notaCredito.setSubtotalCero(notaCredito.getFactura().getSubtotalSinImpuestos());
        notaCredito.setSubtotalDoce(notaCredito.getFactura().getSubtotalImpuestos());
        notaCredito.setCliente(notaCredito.getFactura().getCliente());
        notaCredito.setDescuentoImpuestos(notaCredito.getFactura().getDescuentoImpuestos());
        notaCredito.setDescuentoSinImpuestos(notaCredito.getFactura().getDescuentoSinImpuestos());
        notaCredito.setIce(notaCredito.getFactura().getIce());
        
        /**
         * CargarDetallesNotaCredito
         */
        List<FacturaDetalle> detallesFactura = notaCredito.getFactura().getDetalles();
        if(notaCredito.getDetalles()!=null)
            notaCredito.getDetalles().clear();
        
        for (FacturaDetalle facturaDetalle : detallesFactura) {
            NotaCreditoDetalle notaDetalle = new NotaCreditoDetalle();
            notaDetalle.setCantidad(facturaDetalle.getCantidad());
            notaDetalle.setDescripcion(facturaDetalle.getDescripcion());
            //System.out.println(facturaDetalle.getDescuento());
            notaDetalle.setDescuento(facturaDetalle.getDescuento());
            notaDetalle.setIva(facturaDetalle.getIva());
            notaDetalle.setPrecioUnitario(facturaDetalle.getPrecioUnitario());
            notaDetalle.setReferenciaId(facturaDetalle.getReferenciaId());
            notaDetalle.setCatalogoProducto(facturaDetalle.getCatalogoProducto());
            notaDetalle.setTipoDocumento(facturaDetalle.getTipoDocumento());
            notaDetalle.setTotal(facturaDetalle.getTotal());
            notaDetalle.setValorIce(facturaDetalle.getValorIce());
            notaDetalle.setIvaPorcentaje(facturaDetalle.getIvaPorcentaje());
            notaDetalle.setIcePorcentaje(facturaDetalle.getIcePorcentaje());

            notaCredito.addDetalle(notaDetalle);
        }
        
      
        
    }

    
    public void setearDatosProveedor(Persona proveedor,NotaCredito notaCredito)
    {
        notaCredito.setCliente(proveedor);
        notaCredito.setTelefono(proveedor.getEstablecimientos().get(0).getTelefonoConvencional());
        notaCredito.setDireccion(proveedor.getEstablecimientos().get(0).getDireccion());
        notaCredito.setRazonSocial(proveedor.getRazonSocial());
    }
    
    private void cargarDatosAdicionales(NotaCredito notaCredito)
    {
        //Si vuelve a escoger otra factura se borran los datos adicionales
         if(notaCredito.getDatosAdicionales()!=null)
            notaCredito.getDatosAdicionales().clear();
        
         List<FacturaAdicional> datosAdicional=notaCredito.getFactura().getDatosAdicionales();
         if(datosAdicional!=null)
         {
             List<NotaCreditoAdicional> datosAdicionalNotaCredito=new ArrayList<NotaCreditoAdicional>();
             for (FacturaAdicional facturaDetalle : datosAdicional) {
                 NotaCreditoAdicional notaCreditoAdicional=new NotaCreditoAdicional();
                 notaCreditoAdicional.setCampo(facturaDetalle.getCampo());
                 notaCreditoAdicional.setNotaCredito(notaCredito);
                 notaCreditoAdicional.setNumero(facturaDetalle.getNumero());
                 notaCreditoAdicional.setTipo(facturaDetalle.getTipo());
                 notaCreditoAdicional.setValor(facturaDetalle.getValor());
                 datosAdicionalNotaCredito.add(notaCreditoAdicional);
             }
             notaCredito.setDatosAdicionales(datosAdicionalNotaCredito);
         }
    }
    
    public void grabar() throws ExcepcionCodefacLite {
    
        NotaCredito notaCredito=interfaz.obtenerNotaCredito();
                
        try {
            NotaCredito notaCreditoGrabada;
            NotaCreditoServiceIf servicio = ServiceFactory.getFactory().getNotaCreditoServiceIf();
            setearValoresNotaCredito();

            if (!validarDatosNotaCredito()) {
                throw new ExcepcionCodefacLite("Error Validación");
            }

            notaCredito = servicio.grabar(notaCredito);
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.GUARDADO);
            notaCreditoGrabada = notaCredito;//graba una referencia con ambiento del metodo para los listener

            ComprobanteDataNotaCredito comprobanteData = new ComprobanteDataNotaCredito(notaCredito);

            comprobanteData.setMapInfoAdicional(getMapAdicional(notaCredito));

            ClienteInterfaceComprobante cic = interfaz.obtenerClienteInterfaceComprobante(notaCreditoGrabada);

            if (ParametrosClienteEscritorio.tipoClienteEnum.equals(ParametrosClienteEscritorio.TipoClienteSwingEnum.REMOTO)) {
                cic = null;
            }

            ComprobanteServiceIf comprobanteServiceIf = ServiceFactory.getFactory().getComprobanteServiceIf();
            comprobanteServiceIf.procesarComprobante(comprobanteData, notaCredito, session.getUsuario(), cic);

            interfaz.procesarMonitor(notaCreditoGrabada);

        } catch (ServicioCodefacException ex) {
            Logger.getLogger(NotaCreditoModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO));            
            throw new ExcepcionCodefacLite(ex.getMessage());
        } catch (RemoteException ex) {
            Logger.getLogger(NotaCreditoModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void editar() throws ExcepcionCodefacLite {
    
        NotaCredito notaCredito=interfaz.obtenerNotaCredito();
                
        try {
            NotaCredito notaCreditoGrabada;
            NotaCreditoServiceIf servicio = ServiceFactory.getFactory().getNotaCreditoServiceIf();
            setearValoresNotaCredito();

            if (!validarDatosNotaCredito()) {
                throw new ExcepcionCodefacLite("Error Validación");
            }

            servicio.editarNotaCredito(notaCredito);
            mostrarMensaje(MensajeCodefacSistema.AccionesFormulario.EDITADO);
            //notaCreditoGrabada = notaCredito;//graba una referencia con ambiento del metodo para los listener

            //ComprobanteDataNotaCredito comprobanteData = new ComprobanteDataNotaCredito(notaCredito);

            //comprobanteData.setMapInfoAdicional(getMapAdicional(notaCredito));

            //ClienteInterfaceComprobante cic = interfaz.obtenerClienteInterfaceComprobante(notaCreditoGrabada);

            //if (ParametrosClienteEscritorio.tipoClienteEnum.equals(ParametrosClienteEscritorio.TipoClienteSwingEnum.REMOTO)) {
            //    cic = null;
            //}

            //ComprobanteServiceIf comprobanteServiceIf = ServiceFactory.getFactory().getComprobanteServiceIf();
            //comprobanteServiceIf.procesarComprobante(comprobanteData, notaCredito, session.getUsuario(), cic);

            //interfaz.procesarMonitor(notaCreditoGrabada);

        } catch (ServicioCodefacException ex) {
            Logger.getLogger(NotaCreditoModelControlador.class.getName()).log(Level.SEVERE, null, ex);
            mostrarMensaje(new CodefacMsj("Error", ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO));            
            throw new ExcepcionCodefacLite(ex.getMessage());
        } catch (RemoteException ex) {
            Logger.getLogger(NotaCreditoModelControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void setearValoresNotaCredito()
    {
        NotaCredito notaCredito=interfaz.obtenerNotaCredito();
        notaCredito.setEmpresa(session.getEmpresa());
        //notaCredito.setEstado(Factura.ESTADO_FACTURADO);
        notaCredito.setFechaCreacion(UtilidadesFecha.castDateToTimeStamp(UtilidadesFecha.getFechaHoy()));
        notaCredito.setRazonModificado(interfaz.obtenerTxtMotivoAnulacion());
                
        notaCredito.setFechaEmision(interfaz.obtenerDateFechaEmision());
        
        PuntoEmision puntoEmisionSeleccionado= interfaz.obtenerPuntoEmisionSeleccionado();
        //notaCredito.setPuntoEmision(session.getParametrosCodefac().get(ParametroCodefac.PUNTO_EMISION).valor);
        //notaCredito.setPuntoEstablecimiento(session.getParametrosCodefac().get(ParametroCodefac.ESTABLECIMIENTO).valor);
        if(puntoEmisionSeleccionado!=null)
        {
            notaCredito.setPuntoEmision(puntoEmisionSeleccionado.getPuntoEmision());
            notaCredito.setPuntoEmisionId(puntoEmisionSeleccionado.getId());
            notaCredito.setPuntoEstablecimiento(new BigDecimal(puntoEmisionSeleccionado.getSucursal().getCodigoSucursal().toString()));
        }
        
        //notaCredito.setSecuencial(Integer.parseInt(session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_NOTA_CREDITO).valor));
        notaCredito.setObligadoLlevarContabilidad(session.getEmpresa().getObligadoLlevarContabilidad());
        notaCredito.setContribuyenteEspecial(session.getEmpresa().getContribuyenteEspecial());
        //notaCredito.setSubtotalCero(BigDecimal.ZERO);
        
        
        //Actualizar en el documento de nota_credito el nuevo tipo de documento
        TipoDocumentoEnum tipoDocumentoEnum=interfaz.obtenerCmbTipoDocumento();
        notaCredito.setTipoDocumento(tipoDocumentoEnum.getCodigo());
        
        notaCredito.setDireccionEstablecimiento(session.getSucursal().getDirecccion());
        notaCredito.setDireccionMatriz(session.getMatriz().getDirecccion());
        notaCredito.setUsuario(session.getUsuario());
        notaCredito.setSucursalEmpresa(session.getSucursal());
        
        switch(tipoDocumentoEnum)
        {
            case LIBRE:
                //TODO: Validar que estos campos en la nota de credito esten ingresados correctamente
                notaCredito.setNumDocModificado(interfaz.obtenerTxtPreimpresoProveedor());
                if(interfaz.obtenerCmbFechaCompra()!=null)
                {
                    notaCredito.setFechaEmisionDocSustento(new java.sql.Date(interfaz.obtenerCmbFechaCompra().getTime()));
                }
                break;
                
            case VENTA:
                if(notaCredito.getFactura()!=null)
                {
                    notaCredito.setNumDocModificado(notaCredito.getFactura().getPreimpreso());
                    notaCredito.setFechaEmisionDocSustento(new java.sql.Date(notaCredito.getFactura().getFechaEmision().getTime()));
                }
                break;
        
        }

    }
    
    public Map<String,String> getMapAdicional(NotaCredito notaCredito)
    {
        Map<String,String> parametroMap=new HashMap<String ,String>();
        if(notaCredito.getDatosAdicionales()!=null)
        {
            for (NotaCreditoAdicional datoAdicional : notaCredito.getDatosAdicionales()) 
            {
                parametroMap.put(datoAdicional.getCampo(),datoAdicional.getValor());
            }
        }
        return parametroMap;
    }
    
    /**
     * TODO:Ver como hacer que los dialogos podemos crear para cada interfaz
     * @return 
     */
    private boolean validarDatosNotaCredito()
    {
        NotaCredito notaCredito=interfaz.obtenerNotaCredito();
        if(notaCredito.getCliente()==null)
        {
            mostrarMensaje(new CodefacMsj("Error Validación","Por favor seleccione un cliente",DialogoCodefac.MENSAJE_INCORRECTO));            
            return false;
        }
        
        if(notaCredito.getDetalles()==null || notaCredito.getDetalles().size()==0)
        {
            mostrarMensaje(new CodefacMsj("Error Validación","Por favor ingrese detalles al comprobante",DialogoCodefac.MENSAJE_INCORRECTO));            
            return false;
        }
        
        
        //Actualizar en el documento de nota_credito el nuevo tipo de documento
        TipoDocumentoEnum tipoDocumentoEnum=interfaz.obtenerCmbTipoDocumento();
        if(tipoDocumentoEnum.equals(TipoDocumentoEnum.LIBRE))
        {
            if(notaCredito.getFechaEmisionDocSustento()==null)
            {
                mostrarMensaje(new CodefacMsj("Error Validación","Por favor seleccione la fecha de emisión",DialogoCodefac.MENSAJE_INCORRECTO));
                return false;
            }
            
            if(notaCredito.getNumDocModificado().replaceAll("-","").replaceAll(" ","").isEmpty())
            {
                mostrarMensaje(new CodefacMsj("Error Validación","Por favor ingrese el preimpreso de la factura",DialogoCodefac.MENSAJE_INCORRECTO));
                return false;
            }
        }
        
        
        return true;    
    }

    @Override
    public ComprobanteAdicional crearComprobanteAdicional(String correo, ComprobanteAdicional.Tipo tipoCorreo, ComprobanteAdicional.CampoDefectoEnum campoDefecto) {
        return new NotaCreditoAdicional(correo, FacturaAdicional.Tipo.TIPO_CORREO, ComprobanteAdicional.CampoDefectoEnum.CORREO);
    }
    
    public interface NotaCreditoModelInterface
    {
        public NotaCredito obtenerNotaCredito();
        public Date obtenerCmbFechaCompra();
        public Date obtenerDateFechaEmision();
        public TipoDocumentoEnum obtenerCmbTipoDocumento();
        public String obtenerTxtMotivoAnulacion();
        public String obtenerTxtPreimpresoProveedor();
        public PuntoEmision obtenerPuntoEmisionSeleccionado();
        public ClienteInterfaceComprobante obtenerClienteInterfaceComprobante(NotaCredito notaCredito);
        public void procesarMonitor(NotaCredito notaCredito);
    }
}
