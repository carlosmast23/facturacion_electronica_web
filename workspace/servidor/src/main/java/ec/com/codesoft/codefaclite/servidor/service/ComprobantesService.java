/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import autorizacion.ws.sri.gob.ec.Autorizacion;
import autorizacion.ws.sri.gob.ec.Mensaje;
import com.google.gson.Gson;
import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import ec.com.codesoft.codefaclite.facturacionelectronica.AlertaComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoAutorizado;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteEnum;
import ec.com.codesoft.codefaclite.facturacionelectronica.FirmaElectronica;
import ec.com.codesoft.codefaclite.facturacionelectronica.MetodoEnvioSmsInterface;
import ec.com.codesoft.codefaclite.facturacionelectronica.MetodosEnvioInterface;
import ec.com.codesoft.codefaclite.facturacionelectronica.ServicioSri;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronicoLote;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionAdicional;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionTributaria;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesServidor;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobanteLote;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteData;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataFactura;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteMensaje;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.CorreoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriFormaPago;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.SriRetencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.NotaCreditoEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servicios.ServidorSMS;
import ec.com.codesoft.codefaclite.servidor.facade.ComprobanteEntityFacade;
import ec.com.codesoft.codefaclite.servidor.facade.FacturaFacade;
import ec.com.codesoft.codefaclite.servidor.facade.NotaCreditoFacade;
import ec.com.codesoft.codefaclite.servidor.facade.RetencionFacade;
import ec.com.codesoft.codefaclite.servidor.facade.transporte.GuiaRemisionFacade;
import ec.com.codesoft.codefaclite.servidor.service.transporte.GuiaRemisionService;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataLiquidacionCompra;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataNotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataRetencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity.ComprobanteEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCreditoAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemisionAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.proxy.ReporteProxy;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.formato.ComprobantesUtilidades;
import ec.com.codesoft.codefaclite.ws.recepcion.Comprobante;
import ec.com.codesoft.codefaclite.utilidades.imagen.UtilidadImagen;
import ec.com.codesoft.codefaclite.utilidades.rmi.UtilidadesRmi;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadVarios;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.jfree.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**UtilidadesRmi.deserializar(byteReporte);
 *
 * @author
 */
public class ComprobantesService extends ServiceAbstract<ComprobanteEntity,ComprobanteEntityFacade> implements ComprobanteServiceIf{

    private static final Logger LOG = Logger.getLogger(ComprobantesService.class.getName());
    
    

    /**
     * Graba una lista e clientes suscritos para responder
     */
    private Vector<ClienteInterfaceComprobante> clientesLista;
    
    
    public ComprobantesService() throws RemoteException {
        //super();
        clientesLista=new Vector<ClienteInterfaceComprobante>();
    }
    
    
    public void eliminarComprobanteSinTransaccion(ComprobanteEntity comprobante) throws RemoteException,ServicioCodefacException
    {
        if (comprobante.getEstadoEnum().equals(ComprobanteEnumEstado.AUTORIZADO)) {
            comprobante.setEstadoEnum(ComprobanteEnumEstado.ELIMINADO_SRI);
        } else {
            comprobante.setEstadoEnum(ComprobanteEnumEstado.ELIMINADO);
        }

        entityManager.merge(comprobante);
    }
    
    //INFO: Metodo que me sirve para cambiar el Estado a Autorizado en especial para coorregir problemas que toca cambiar el estado manualmente desde los clientes
    public void autorizarComprobante(ComprobanteEntity comprobanteElectronica) throws RemoteException,ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {                
                comprobanteElectronica.setEstadoEnum(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO);
                entityManager.merge(comprobanteElectronica);
                Logger.getLogger(ComprobantesService.class.getName()).log(Level.INFO,"El comprobante "+comprobanteElectronica.getPreimpreso()+" fue autorizado desde el metodo autorizarComprobante() en la clase ComprobantesService, para cambiar de estado manualmente");
            }
        });        
    }
    
    
    public boolean verificarCredencialesFirma(String claveFirma,Empresa empresa) throws RemoteException
    {        
        ParametroCodefacService servicioParametros = new ParametroCodefacService();
        Map<String, ParametroCodefac> parametrosMap = servicioParametros.getParametrosMap(empresa);

        String pathFirma = parametrosMap.get(ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA).getValor();
        String rutaDestino = parametrosMap.get(ParametroCodefac.DIRECTORIO_RECURSOS).valor + "/" + ComprobanteElectronicoService.CARPETA_CONFIGURACION + "/" + pathFirma;

        if (!claveFirma.equals("") && !pathFirma.equals("")) {
            if (FirmaElectronica.FirmaVerificar(rutaDestino, claveFirma)) {
                return true;
            }
        }

        return false;
    }
    
    private void setearComprobanteLote(List<ComprobanteDataInterface> comprobantesProcesos,ComprobanteElectronicoService comprobanteElectronico) throws RemoteException
    {
        if(comprobantesProcesos!=null)
        {
            List<ComprobanteElectronico> comprobantesConvertidoList=new ArrayList<ComprobanteElectronico>();
            for (ComprobanteDataInterface comprobantesProceso : comprobantesProcesos) {
                ComprobanteElectronico datoConvertido=convertirComprobanteDatoToComprobateElectronico(comprobantesProceso);
                comprobantesConvertidoList.add(datoConvertido);
            }
            comprobanteElectronico.setComprobantesLote(comprobantesConvertidoList);
            
        }
    }
    
    private void setearClavesAccesoLote(List<ClaveAcceso> listaClaves,Empresa empresa)
    {
        for (ClaveAcceso claveAcceso : listaClaves) 
        {
            ComprobanteEntity comprobanteEntity=consultarPorClaveAcceso(claveAcceso, empresa);
            comprobanteEntity.setClaveAcceso(claveAcceso.clave);
            
            try {
                ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                    @Override
                    public void transaccion() throws ServicioCodefacException, RemoteException {
                        entityManager.merge(comprobanteEntity);
                    }
                });
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }
    
    public boolean procesarComprobantesLotePendiente(Integer etapaInicial,Integer etapaLimite,List<String> clavesAcceso,List<ComprobanteDataInterface> comprobantesProcesos,String ruc,ClienteInterfaceComprobanteLote callbackClientObject,Boolean enviarCorreo,Empresa empresa,Boolean sincrono) throws RemoteException
    {
        Logger.getLogger(ComprobantesService.class.getName()).log(Level.INFO,"Ejecutando procesarComprobantesLotePendiente en el SERVIDOR ... ");
        ComprobanteElectronicoService comprobanteElectronico= new ComprobanteElectronicoService();
        comprobanteElectronico.setEnviarCorreos(enviarCorreo);
        cargarConfiguraciones(comprobanteElectronico,empresa);
        comprobanteElectronico.setEtapaActual(etapaInicial);
        //comprobanteElectronico.setClaveAcceso(claveAcceso);
        comprobanteElectronico.setEtapaLimiteProcesar(etapaLimite);
        comprobanteElectronico.setClavesAccesoLote(clavesAcceso);
        setearComprobanteLote(comprobantesProcesos, comprobanteElectronico);
        //comprobanteElectronico.setComprobantesLote(comprobantesProcesos);
        comprobanteElectronico.setRuc(ruc);
        
        Integer secuencialLote=null;
        try {
            secuencialLote = obtenerSecuencialLote(empresa); //Verificar que solo debe dar un secuencial si la etapa es superior a enviar comprobante
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        comprobanteElectronico.setSecuencialLote(secuencialLote);
        
        Logger.getLogger(ComprobantesService.class.getName()).log(Level.INFO,"Cargando configuraciones procesarComprobantesLotePendiente en el SERVIDOR ... ");
        
        comprobanteElectronico.addActionListerComprobanteElectronicoLote(new ListenerComprobanteElectronicoLote() {
            private boolean existeConexionRemota=true;
            @Override
            public void iniciado() {
                try {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.INFO,"metodo iniciado() en procesarComprobantesLotePendiente en el SERVIDOR ... ");
                    if(callbackClientObject==null)// Si se envia nulo el objeto que tampoco ejecute el callback con el cliente
                    {
                        Logger.getLogger(ComprobantesService.class.getName()).log(Level.INFO,"callbackClientObject=NULL en procesarComprobantesLotePendiente en el SERVIDOR ... ");
                        existeConexionRemota=false;
                    }
                    else
                    {
                        Logger.getLogger(ComprobantesService.class.getName()).log(Level.INFO,"callbackClientObject=TRUE antes de llamar callback en procesarComprobantesLotePendiente en el SERVIDOR ... ");
                        callbackClientObject.iniciado();
                        Logger.getLogger(ComprobantesService.class.getName()).log(Level.INFO,"callbackClientObject=TRUE despues de llamar callback en procesarComprobantesLotePendiente en el SERVIDOR ... ");
                    }
                } catch (RemoteException ex) {
                    existeConexionRemota=false;
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void clavesGeneradas(List<ClaveAcceso> listaClaves) {
                Logger.getLogger(ComprobantesService.class.getName()).log(Level.INFO,"metodo clavesGeneradas() en procesarComprobantesLotePendiente en el SERVIDOR ... ");
                //TODO: Metodo que devuelve las claves generadas
                //try {
                    if(existeConexionRemota)
                    {
                        //callbackClientObject.procesando(etapa, clave);
                    }                    
                             
                    setearClavesAccesoLote(listaClaves, empresa);
                    //Si se genera la etapa firmar entonces seteo la clave de acceso
                    //if (etapa == ComprobanteElectronicoService.ETAPA_FIRMAR) {
                        //comprobanteOriginal.setClaveAcceso(clave.clave);

                        /*ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                            @Override
                            public void transaccion() {
                                entityManager.merge(comprobanteOriginal);
                            }
                        });*/
                    //}
                    
                    
                    
                //} catch (ServicioCodefacException ex) {
                //    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                //}
            }

            @Override
            public void datosAutorizados(List<Autorizacion> autorizaciones) {
                //TODOS: Lista de los documentos autorizados
                Logger.getLogger(ComprobantesService.class.getName()).log(Level.INFO,"metodo datosAutorizados() en procesarComprobantesLotePendiente en el SERVIDOR ... ");
                
                for (Autorizacion autorizacion: autorizaciones) {
                    try {                                        
                        setearDatosComprobanteAutorizado(autorizacion);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }

            @Override
            public void procesando(int etapa) {
                Logger.getLogger(ComprobantesService.class.getName()).log(Level.INFO,"metodo procesando() en procesarComprobantesLotePendiente en el SERVIDOR ... etapa= "+etapa);
                try {
                    if(existeConexionRemota)
                    {
                        callbackClientObject.procesando(etapa);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void error(ComprobanteElectronicoException cee) {
                Logger.getLogger(ComprobantesService.class.getName()).log(Level.INFO,"metodo error() en procesarComprobantesLotePendiente en el SERVIDOR ... ");
                try {
                    if(existeConexionRemota)
                    {
                        callbackClientObject.error(cee);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void termino(List<Autorizacion> autorizaciones) {
                try {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.INFO,"metodo termino() en procesarComprobantesLotePendiente en el SERVIDOR ... ");
                    //Cambiar el estado de los comprabantes que si fueron autorizados
                    //cambiarEstadoLoteAutorizaciones(autorizaciones,ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO);
                    if(existeConexionRemota)
                    {
                        callbackClientObject.termino(castDatosComprobanteElectronico(autorizaciones,comprobanteElectronico.getServicioSri()));
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });        
        
        //TODO: Este artificio se usa para esperar que se procesen los comprobantes cuando es remoto un cliente
        if(sincrono)
        {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.INFO,"Ejcutando metodo Sincrono en procesarComprobantesLotePendiente en el SERVIDOR ... ");
            comprobanteElectronico.procesarSincronico(true);
        }
        else
        {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.INFO,"Ejcutando metodo Asincrono en procesarComprobantesLotePendiente en el SERVIDOR ... ");
            comprobanteElectronico.procesar(true);
        }
        return true;
    }
    
   
    public List<AlertaComprobanteElectronico> procesarComprobantesPendienteSinCallBack(Integer etapaInicial,Integer etapaLimite,String claveAcceso, List<String> correos,Empresa empresa) throws RemoteException,ServicioCodefacException
    {
        //Empresa empresa=obtenerEmpresaPorClaveAcceso(claveAcceso);
        
        ComprobanteElectronicoService comprobanteElectronico= new ComprobanteElectronicoService();
        cargarConfiguraciones(comprobanteElectronico,empresa);
        
        comprobanteElectronico.setCorreosElectronicos(correos);
        
        comprobanteElectronico.setEtapaActual(etapaInicial);
        comprobanteElectronico.setClaveAcceso(claveAcceso);
        comprobanteElectronico.setEtapaLimiteProcesar(etapaLimite);
        comprobanteElectronico.enviarSoloCorreosAdjuntos=true;
        
        //TODO; Revisar esta parte
        ParametroCodefac parametroCodefac = ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.TIPO_ENVIO_COMPROBANTE,empresa);
        if (parametroCodefac != null && parametroCodefac.getValor().equals(ParametroCodefac.TipoEnvioComprobanteEnum.ENVIAR_AUTORIZADO.getLetra())) {
           comprobanteElectronico.setEnviarCorreoComprobanteAutorizado(true);
        }

        
        comprobanteElectronico.procesarComprobante();
        List<AlertaComprobanteElectronico> alertas=comprobanteElectronico.getAlertas();
        for (AlertaComprobanteElectronico alerta : alertas) {
            if(alerta.tipoMensaje.equals(AlertaComprobanteElectronico.TipoMensajeEnum.ERROR))
            {
                throw new ServicioCodefacException(alerta.mensaje); //Si encuentra algun error grave lanzo una excepcion
            }
        }
        
        return alertas;
    }
    
    public List<String> procesarComprobantesPendienteLote(Integer etapaInicial,Integer etapaLimite,Map<String,List<String>> mapClaveAccesoYCorreos,Boolean enviarCorreo,Empresa empresa) throws RemoteException,ServicioCodefacException 
    {
        List<String> errores=new ArrayList<String>();
        for (Map.Entry<String, List<String>> entry : mapClaveAccesoYCorreos.entrySet()) {
            String claveAcceso= entry.getKey();
            List<String> correos = entry.getValue();            
            procesarComprobantesPendiente(etapaInicial, etapaLimite, claveAcceso, correos, new ClienteInterfaceComprobante() {
                @Override
                public void termino(byte[] byteJasperPrint, List<AlertaComprobanteElectronico> alertas) throws RemoteException {}

                @Override
                public void iniciado() throws RemoteException {}

                @Override
                public void procesando(int etapa, ClaveAcceso clave) throws RemoteException {}

                @Override
                public void error(ComprobanteElectronicoException cee, String claveAcceso) throws RemoteException {
                    errores.add("Clave Acceso:"+claveAcceso+"\nError:"+cee.getMessage());
                }
            }, enviarCorreo,false,empresa); //todo: ver si se hace parametrizable este valor de asincrono o no asincrono , por defecto esta no asincrono            
        }
        return errores; //Esta opcion va a permitir que espera a que se termine el proceso y no funcione en segundo plano
    
    }          
    
    public boolean procesarComprobantesPendiente(Integer etapaInicial,Integer etapaLimite,String claveAcceso, List<String> correos,ClienteInterfaceComprobante callbackClientObject,Boolean enviarCorreo,Boolean asincrono,Empresa empresa) throws RemoteException
    {
        //Empresa empresa=obtenerEmpresaPorClaveAcceso(claveAcceso);
        ComprobanteElectronicoService comprobanteElectronico= new ComprobanteElectronicoService();
        cargarConfiguraciones(comprobanteElectronico,empresa);
        
        comprobanteElectronico.setCorreosElectronicos(correos);
        comprobanteElectronico.setEnviarCorreos(enviarCorreo);
        
        //Esta validacion la realizo para cuando quieran enviar por correo y la etapa sea superior a la de enviar normalmente , envio al final del proceso
        if(etapaInicial>ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE && enviarCorreo)
        {
            comprobanteElectronico.setEnviarCorreoComprobanteAutorizado(true);
        }
    
        //if(callbackClientObject!=null)
        //{
            comprobanteElectronico.addActionListerComprobanteElectronico(new ListenerComprobanteElectronico() {
                private boolean existeConexionRemota=true;
                
                @Override
                public void termino() {
                    try {
                        if(existeConexionRemota)
                        {
                            if(callbackClientObject!=null)
                            {
                                callbackClientObject.termino(null,comprobanteElectronico.getAlertas());
                            }
                            
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                @Override
                public void iniciado(ComprobanteElectronico comprobante) {
                    try {
                        if(callbackClientObject!=null)
                        {
                            callbackClientObject.iniciado();
                        }
                        
                    } catch (RemoteException ex) {
                        existeConexionRemota=false;
                        Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                @Override
                public void procesando(int etapa, ClaveAcceso clave) {
                    try {
                        if(existeConexionRemota)
                        {
                            if(callbackClientObject!=null)
                            {
                                callbackClientObject.procesando(etapa, clave);
                            }
                        }
                        
                        //Setear el campo de seteado a factura solo si pasa la etapa de autorizar
                        /*if (etapa == ComprobanteElectronicoService.ETAPA_AUTORIZAR) {
                            
                            ComprobanteEntity comprobante=obtenerComprobantePorClaveAcceso(clave);
                            comprobante.setEstado(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());

                            try {
                                ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                                    @Override
                                    public void transaccion() {
                                        entityManager.merge(comprobante);
                                    }
                                });

                                //Enviar mensaje
                                //ServidorSMS.getInstance().enviarMensaje("994905332","La factura"+clave.secuencial+" fue enviada a su correo");
                            } catch (ServicioCodefacException ex) {
                                Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }*/
                        
                    } catch (RemoteException ex) {
                        Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                @Override
                public void error(ComprobanteElectronicoException cee) {
                    try {
                        if(existeConexionRemota)
                        {
                            if(callbackClientObject!=null)
                            {
                                callbackClientObject.error(cee,"");
                            }
                            
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            @Override
            public void autorizado(Autorizacion documentoAutorizado) {
                    try {
                        setearDatosComprobanteAutorizado(documentoAutorizado);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            });
        //}
        
        comprobanteElectronico.setEtapaActual(etapaInicial);
        comprobanteElectronico.setClaveAcceso(claveAcceso);
        comprobanteElectronico.setEtapaLimiteProcesar(etapaLimite);
        //comprobanteElectronico
        //if(callbackClientObject!=null) //Si  tiene comunicación bidereccional entonces ejecuta el proceso con hilos
        //{
        if(asincrono)
        {
            comprobanteElectronico.procesar(false); //Manda a procesar en un hilo aparte los comprobantess
        }
        else
        {
            comprobanteElectronico.procesarComprobante();
        }
        //}
        //else //Si el proceso no requiere comunicación bidireccional ejecuto directamente en el hilo principal
        //{
            //TODO: Analizar el caso que sucede cuando 
            //comprobanteElectronico.procesarComprobante();
            //comprobanteElectronico.getAlertas();
        //}
        return true;
    }
    
    private void setearDatosComprobanteAutorizado(Autorizacion documentoAutorizado ) throws ServicioCodefacException, RemoteException
    {
        if(documentoAutorizado.getNumeroAutorizacion()==null || documentoAutorizado.getNumeroAutorizacion().isEmpty())
        {
            return;
        }
        
        //try {
            ClaveAcceso clave = new ClaveAcceso(documentoAutorizado.getNumeroAutorizacion());
            ComprobanteEntity comprobante = obtenerComprobantePorClaveAcceso(clave);
            if (comprobante != null) {
                //comprobante.setEstado(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());
                setearDatosAutorizacionComprobanteConTransaccion(comprobante, documentoAutorizado);
                /*ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                    @Override
                    public void transaccion() throws ServicioCodefacException, RemoteException {
                        entityManager.merge(comprobante);
                    }
                });*/

            } else {
                LOG.log(Level.SEVERE, "Error se autorizo el comprobante pero no se encuentra el registro; " + documentoAutorizado.getNumeroAutorizacion());
            }
        //} catch (RemoteException ex) {
        //    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        //} catch (ServicioCodefacException ex) {
        //    LOG.log(Level.SEVERE, "Error: " + ex.getMessage() + " \n Clave Acceso:" + documentoAutorizado.getNumeroAutorizacion());
        //}
    }
    
    /**
     * Todo:Ver si este metodo puedo poner en algun lugar para que pueda reutilizar varias veces
     * @param claveAccesoStr
     * @return 
     */
    private ComprobanteEntity obtenerComprobantePorClaveAcceso(ClaveAcceso claveAcceso) throws RemoteException
    {
        final String NOMBRE_CAMPO="claveAcceso";
        //ClaveAcceso claveAcceso=new ClaveAcceso(claveAccesoStr);
        //TODO: Tener en cuenta que talvez no se deberian tomar en cuenta los eliminados
        // Porque puede ser que este generando nuevamente el comprobante y tengo otro similar y va a generar problemas
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put(NOMBRE_CAMPO,claveAcceso.clave);
        
        switch(claveAcceso.getTipoComprobante())
        {
            case FACTURA:
            case LIQUIDACION_COMPRA:
                FacturaFacade servicio=new FacturaFacade();                
                List<Factura> comprobantes=servicio.findByMap(mapParametros);
                if(comprobantes.size()>0)
                    return comprobantes.get(0);
                break;

            case COMPROBANTE_RETENCION:
                RetencionFacade servicio2 = new RetencionFacade();
                List<Retencion> comprobantes2 = servicio2.findByMap(mapParametros);
                if (comprobantes2.size() > 0) {
                    return comprobantes2.get(0);
                }
                break;

            case GUIA_REMISION:
                GuiaRemisionFacade servicio3 = new GuiaRemisionFacade();
                List<GuiaRemision> comprobantes3 = servicio3.findByMap(mapParametros);
                if (comprobantes3.size() > 0) {
                    return comprobantes3.get(0);
                }
                break;

            case NOTA_CREDITO:
                NotaCreditoFacade servicio4 = new NotaCreditoFacade();
                List<NotaCredito> comprobantes4 = servicio4.findByMap(mapParametros);
                if (comprobantes4.size() > 0) {
                    return comprobantes4.get(0);
                }
                break;

            case NOTA_DEBITO:
                break;

        }
        return null;
    }
    
    public List<ComprobanteElectronico> getComprobantesObjectByFolder(String carpetaConfiguracion,Empresa empresa) throws RemoteException
    {
        //Esta validacion se la hacer porque la primera vez que no tiene une empresa por defecto no debe hacer esta validacion
        if(empresa==null)
        {
            return new ArrayList<ComprobanteElectronico>();
        }
        
        ParametroCodefacService parametroService=new ParametroCodefacService();
        String path= parametroService.getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS,empresa).valor;
        ParametroCodefac modoFacturacionParam=parametroService.getParametroByNombre(ParametroCodefac.MODO_FACTURACION, empresa);
        
        //Si tiene configurado el modo de facturacion retorno vacio
        if(modoFacturacionParam==null)
        {
            return new ArrayList<>();
        }
            
        String modoFacturacion=modoFacturacionParam.valor;
        String pathComprobantes="";
        
        if (modoFacturacion.equals(ComprobanteElectronicoService.MODO_PRODUCCION)) {
            pathComprobantes =path+"/"+ DirectorioCodefac.COMPROBANTES_PRODUCCION.getNombre();
        } else {
            pathComprobantes =path+"/"+ DirectorioCodefac.COMPROBANTES_PRUEBAS.getNombre();
        }
        
        return ComprobantesElectronicosUtil.getComprobantesObjectByFolder(pathComprobantes,carpetaConfiguracion);
        
    }
    
    
    public Integer getComprobantesObjectByFolderCantidad(String carpetaConfiguracion,Empresa empresa) throws RemoteException
    {
        //Esta validacion se la hacer porque la primera vez que no tiene une empresa por defecto no debe hacer esta validacion
        if(empresa==null)
        {
            return 0;
        }
        
        ParametroCodefacService parametroService=new ParametroCodefacService();
        String path= parametroService.getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS,empresa).valor;
        ParametroCodefac modoFacturacionParam=parametroService.getParametroByNombre(ParametroCodefac.MODO_FACTURACION, empresa);
        
        //Si tiene configurado el modo de facturacion retorno vacio
        if(modoFacturacionParam==null)
        {
            return 0;
        }
            
        String modoFacturacion=modoFacturacionParam.valor;
        String pathComprobantes="";
        
        if (modoFacturacion.equals(ComprobanteElectronicoService.MODO_PRODUCCION)) {
            pathComprobantes =path+"/"+ DirectorioCodefac.COMPROBANTES_PRODUCCION.getNombre();
        } else {
            pathComprobantes =path+"/"+ DirectorioCodefac.COMPROBANTES_PRUEBAS.getNombre();
        }
        
        return ComprobantesElectronicosUtil.getComprobantesObjectByFolderCantidad(pathComprobantes,carpetaConfiguracion);
        
    }
    
    /**
     * Regresa un jasperPrint para que pueda generar un reporte
     * @param claveAcceso
     * @return
     * @throws RemoteException 
     */
    public byte[] getReporteComprobante(String claveAcceso,Empresa empresa) throws RemoteException
    {
        try {
            //Metodos para obtener la empresa para hacer el pie de pagina con esos datos
            ClaveAcceso claveAccesoObj=new ClaveAcceso(claveAcceso);
            EmpresaService empresaService=new EmpresaService();
            //Empresa empresa=empresaService.buscarPorIdentificacion( claveAccesoObj.identificacion);
            //Empresa empresa=empresaService.buscarPorId(empresa.getId());
            
            ComprobanteElectronicoService comprobanteElectronico = new ComprobanteElectronicoService();
            //Cargar recursos para el reporte
            cargarDatosRecursos(comprobanteElectronico,empresa);
            //mapReportePlantilla(empresa);
            cargarConfiguraciones(comprobanteElectronico,empresa);
            comprobanteElectronico.setClaveAcceso(claveAcceso);
            //JasperPrint jasperPrint=comprobanteElectronico.getPrintJasper();
            ComprobanteEntity comprobanteEntity=obtenerComprobantePorClaveAcceso(claveAccesoObj);
            ComprobanteDataInterface comprobanteDataIf=convertirComprobanteEntityToDataFacturacionElectronica(comprobanteEntity);
            if(comprobanteDataIf!=null)
            {
                ComprobanteElectronico comprobanteElectronicoData=convertirComprobanteDatoToComprobateElectronico(comprobanteDataIf);

                String fechaAutorizacionStr="";
                if(comprobanteEntity.getFechaAutorizacionSri()!=null)
                {
                    fechaAutorizacionStr=ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA_HORA.format(comprobanteEntity.getFechaAutorizacionSri());
                }

                //TODO: Optimizar para ver si se puede setear este dato desde una etapa previa para tener mejor organizado el codigo
                comprobanteElectronicoData.getInformacionTributaria().setClaveAcceso(claveAcceso);            
                JasperPrint jasperPrint=comprobanteElectronico.getPrintJasperComprobante(comprobanteElectronicoData, claveAcceso, fechaAutorizacionStr);
                return UtilidadesRmi.serializar(jasperPrint);
            }
            //JasperPrint jasperPrint=comprobanteElectronico.getPrintJasper();
            
            
        } catch (IOException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    /**
     * TODO: Remplazar este metodo por uno similar que actualmente esta en ControladorFacturacion
     * @return 
     */
    public ComprobanteDataInterface convertirComprobanteEntityToDataFacturacionElectronica(ComprobanteEntity comprobanteEntity)
    {
        if(comprobanteEntity==null)
        {
            return null;
        }
        
        if(comprobanteEntity.getCodigoDocumentoEnum().equals(DocumentoEnum.FACTURA))
        {
            Factura factura=(Factura) comprobanteEntity;
            ComprobanteDataFactura comprobanteData = new ComprobanteDataFactura(factura);
            comprobanteData.setMapInfoAdicional(factura.getMapAdicional());
            return comprobanteData;
        } 
        else if(comprobanteEntity.getCodigoDocumentoEnum().equals(DocumentoEnum.LIQUIDACION_COMPRA))
        {
            Factura factura=(Factura) comprobanteEntity;
            ComprobanteDataLiquidacionCompra comprobanteData=new ComprobanteDataLiquidacionCompra(factura);
            comprobanteData.setMapInfoAdicional(factura.getMapAdicional());
            return comprobanteData;
        } 
        else if(comprobanteEntity.getCodigoDocumentoEnum().equals(DocumentoEnum.NOTA_VENTA_INTERNA))
        {
            Factura factura=(Factura) comprobanteEntity;
            ComprobanteDataFactura comprobanteData = new ComprobanteDataFactura(factura);
            comprobanteData.setMapInfoAdicional(factura.getMapAdicional());
            return comprobanteData;
        }   
        else if(comprobanteEntity.getCodigoDocumentoEnum().equals(DocumentoEnum.NOTA_CREDITO))
        {
            NotaCredito notaCredito=(NotaCredito) comprobanteEntity;             
            ComprobanteDataNotaCredito comprobanteData = new ComprobanteDataNotaCredito(notaCredito);
            comprobanteData.setMapInfoAdicional(notaCredito.getMapAdicional());
            return comprobanteData;
        }
        else if(comprobanteEntity.getCodigoDocumentoEnum().equals(DocumentoEnum.RETENCIONES))
        {
            Retencion retencion=(Retencion) comprobanteEntity;             
            ComprobanteDataRetencion comprobanteData = new ComprobanteDataRetencion(retencion);
            comprobanteData.setMapInfoAdicional(retencion.getMapAdicional());
            return comprobanteData;
        }
        else if(comprobanteEntity.getCodigoDocumentoEnum().equals(DocumentoEnum.GUIA_REMISION))
        {
            GuiaRemision guiaRemision=(GuiaRemision) comprobanteEntity;             
            ComprobanteDataGuiaRemision comprobanteData = new ComprobanteDataGuiaRemision(guiaRemision);
            comprobanteData.setMapInfoAdicional(guiaRemision.getMapAdicional());
            return comprobanteData;
        }
        
        
        
        return null;
    }
    
    public byte[] getReporteComprobanteComprobante(ComprobanteDataInterface comprobanteData,Usuario usuario,String claveAccesoPersonalizada) throws RemoteException
    {
        try {
            //Metodos para obtener la empresa para hacer el pie de pagina con esos datos
            //ClaveAcceso claveAccesoObj=new ClaveAcceso(claveAcceso);
            //EmpresaService empresaService=new EmpresaService();
            //Empresa empresa=empresaService.buscarPorIdentificacion( claveAccesoObj.identificacion);
            //Empresa empresa=empresaService.buscarPorId(empresa.getId());
            
            //ComprobanteElectronicoService comprobanteElectronico = new ComprobanteElectronicoService();
            //Cargar recursos para el reporte
            //cargarDatosRecursos(comprobanteElectronico,empresa);
            //mapReportePlantilla(empresa);
            //cargarConfiguraciones(comprobanteElectronico,empresa);a
            //comprobanteElectronico.setClaveAcceso(claveAcceso);
            ComprobanteElectronicoService comprobanteElectronico= cargarConfiguracionesInicialesComprobantes(comprobanteData, usuario);
            if(claveAccesoPersonalizada!=null)
            {
                //comprobanteElectronico.setClaveAcceso(claveAccesoPersonalizada);
                comprobanteElectronico.getComprobante().getInformacionTributaria().setClaveAcceso(claveAccesoPersonalizada);
            }
            JasperPrint jasperPrint=comprobanteElectronico.getPrintJasperComprobante(comprobanteElectronico.getComprobante(), null);
            return UtilidadesRmi.serializar(jasperPrint);
        } catch (IOException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
      
    private Integer obtenerSecuencialLote(Empresa empresa) throws RemoteException,ServicioCodefacException
    {

        ParametroCodefacServiceIf servicio = new ParametroCodefacService();
        ParametroCodefac parametroCodefac = servicio.getParametroByNombre(ParametroCodefac.SECUENCIAL_LOTE, empresa);
        //Si la variable para enviar por lote no se encuentra creada generar un numero secuencial
        if (parametroCodefac == null) {
            parametroCodefac = new ParametroCodefac();
            parametroCodefac.setEmpresa(empresa);
            parametroCodefac.setNombre(ParametroCodefac.SECUENCIAL_LOTE);
            parametroCodefac.setValor("0");//Si no existe el primer dato lo creo en la base de datos
            entityManager.persist(parametroCodefac);
        }
        Integer secuencialLote = Integer.parseInt(parametroCodefac.getValor());
        parametroCodefac.setValor((secuencialLote + 1) + "");
        entityManager.merge(parametroCodefac);
        return secuencialLote;

    }
    /**
     * Metodo que permite procesar varios comprobante en Lote
     * @param comprobanteData 
     */
    /*public void procesarComprobanteLote(List<ComprobanteDataInterface> comprobantesData,Usuario usuario,String ruc,ClienteInterfaceComprobanteLote callbackClientObject) throws RemoteException
    {
        ComprobanteElectronicoService comprobanteElectronico= cargarConfiguracionesInicialesComprobantesLote(comprobantesData, usuario);
        
        //comprobantesData.get(0).getEmpresa()
        Integer secuencialLote=null;
        try {
            secuencialLote = obtenerSecuencialLote(comprobantesData.get(0).getEmpresa());
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        comprobanteElectronico.setSecuencialLote(secuencialLote);
        comprobanteElectronico.setRuc(ruc);
        
        comprobanteElectronico.addActionListerComprobanteElectronicoLote(new ListenerComprobanteElectronicoLote() {
            @Override
            public void iniciado() {
                 try {
                     //Seteado las claves de acceso                     
                    cambiarEstadoLotes(comprobantesData,ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR);
                    callbackClientObject.iniciado();
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void procesando(int etapa) {
                try {
                    callbackClientObject.procesando(etapa);
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void error(ComprobanteElectronicoException cee) {
                try {
                    callbackClientObject.error(cee);
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void termino(List<Autorizacion> autorizaciones) {
                try {
                    //comprobanteElectronico.getServicioSri();
                    cambiarEstadoLotes(comprobantesData,ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO);
                    callbackClientObject.termino(castDatosComprobanteElectronico(autorizaciones,comprobanteElectronico.getServicioSri()));
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void clavesGeneradas(List<ClaveAcceso> listaClaves) {
                setClaveAccesoLotes(comprobantesData,listaClaves);
            }

            @Override
            public void datosAutorizados(List<Autorizacion> autorizaciones) {
                    cambiarAutorizadoLotes(autorizaciones);
            }
        });
        
    
        comprobanteElectronico.procesar(true);
    }*/
    
        /**
     * Setear los valores del las claves de acceso con su comprobante en la base de datos
     * @param comprobantesData
     * @param listaClaves 
     */
    protected void setClaveAccesoLotes(List<ComprobanteDataInterface> comprobantesData,List<ClaveAcceso> listaClaves)
    {
        for (ComprobanteDataInterface comprobanteDataInterface : comprobantesData) {
            try {
                String codigoComprobante=comprobanteDataInterface.getCodigoComprobante();
                ComprobanteEnum comprobanteEnum=ComprobanteEnum.getEnumByCodigo(codigoComprobante);
   
                switch(comprobanteEnum)
                {
                    case  FACTURA:
                        Long id=comprobanteDataInterface.getComprobanteId();
                        FacturacionService servicio=new FacturacionService();
                        Factura factura=servicio.buscarPorId(id);
                        ClaveAcceso claveAcceso=buscarClaveAccesoPorFactura(factura, listaClaves);
                        factura.setClaveAcceso(claveAcceso.clave);
                        servicio.editar(factura);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    
    /**
     * Busca una clave
     * @param factura
     * @param listaClaves
     * @return 
     */
    protected ClaveAcceso buscarClaveAccesoPorFactura(Factura factura,List<ClaveAcceso> listaClaves)
    {
        //TODO: Falta implementar una busqueda mas detallada por punto de emision, sucursal y tipo de documento
        for (ClaveAcceso claveAcceso : listaClaves) {
            System.out.println(claveAcceso.getSecuencialInteger());
            System.out.println(factura.getSecuencial());
            if(claveAcceso.getSecuencialInteger().equals(factura.getSecuencial()))
            {
                return claveAcceso;
            }
        }
        return null;
    }
    
    /*protected void cambiarEstadoLotes(List<ComprobanteDataInterface> comprobantesData,ComprobanteEntity.ComprobanteEnumEstado estado)
    {
        for (ComprobanteDataInterface comprobanteDataInterface : comprobantesData) {
            try {
                String codigoComprobante=comprobanteDataInterface.getCodigoComprobante();
                ComprobanteEnum comprobanteEnum=ComprobanteEnum.getEnumByCodigo(codigoComprobante);
                
                switch(comprobanteEnum)
                {
                    case  FACTURA:
                        Long id=comprobanteDataInterface.getComprobanteId();
                        FacturacionService servicio=new FacturacionService();
                        Factura factura=servicio.buscarPorId(id);
                        factura.setEstadoEnum(estado);
                        servicio.editar(factura);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }*/
    
    /*protected void cambiarAutorizadoLotes(List<Autorizacion> autorizaciones)
    {
        for (Autorizacion autorizacion : autorizaciones) {
            if (autorizacion.getEstado().equals("AUTORIZADO")) 
            {
                try {
                    FacturacionService servicio = new FacturacionService();
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("claveAcceso",autorizacion.getNumeroAutorizacion());
                    List<Factura> facturas= servicio.getFacade().findByMap(mapParametros);
                    if(facturas.size()>0)
                    {
                        Factura facturaEditar=facturas.get(0);
                        facturaEditar.setEstadoEnum(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO);
                        servicio.editar(facturaEditar);
                        
                        //Crear cartera de los comprobantes autorizados
                        Cartera cartera=crearCarteraFactura(facturaEditar);
                        entityManager.persist(cartera);                        
                    }
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }            
        }
    }*/
    
    private Cartera crearCarteraFactura(Factura factura)
    {
        Cartera cartera=new Cartera();
        cartera.setCodigoDocumento(factura.getCodigoDocumento());
        cartera.setFechaCreacion(UtilidadesFecha.getFechaHoyTimeStamp());
        cartera.setPersona(factura.getCliente());
        cartera.setReferenciaID(factura.getId());
        cartera.setSaldo(factura.getTotal().subtract(factura.getIva()));
        cartera.setTotal(factura.getTotal().subtract(factura.getIva()));
        
        for (FacturaDetalle detalle : factura.getDetalles()) {
            CarteraDetalle carteraDetalle=new CarteraDetalle();
            carteraDetalle.setCartera(cartera);
            carteraDetalle.setReferenciaId(detalle.getReferenciaId());
            carteraDetalle.setTipoReferencia(detalle.getTipoDocumento());
            carteraDetalle.setTotal(detalle.getTotal().subtract(detalle.getIva()));
        }
        return cartera;
    }   

    
    private List<ComprobanteData> castDatosComprobanteElectronico(List<Autorizacion> autorizaciones,ServicioSri servicioSri)
    {
        List<ComprobanteData> comprobantes=new ArrayList<ComprobanteData>();
        
        for (Autorizacion autorizacion : autorizaciones) {
            ComprobanteData comprobanteData=new ComprobanteData();
            comprobanteData.setAmbiente(autorizacion.getAmbiente());
            comprobanteData.setEstado(autorizacion.getEstado());
            comprobanteData.setFechaAutorizacion(autorizacion.getFechaAutorizacion()+"");
            //comprobanteData.setNumeroAutorizacion(autorizacion.getNumeroAutorizacion());
            
            //Convertir de texto a un comprobante fisico
            ComprobanteElectronico comprobanteElectronico=servicioSri.castComprobanteToAutorizacion(autorizacion);
                        
            if(comprobanteElectronico!=null)
            {
                comprobanteData.setNumeroAutorizacion(comprobanteElectronico.getInformacionTributaria().getClaveAcceso());
                comprobanteData.setPreimpreso(comprobanteElectronico.getInformacionTributaria().getPreimpreso());
                comprobanteData.setComprobante(comprobanteElectronico);
            }
            else
            {
                if(autorizacion.getNumeroAutorizacion()!=null)
                {
                    comprobanteData.setNumeroAutorizacion(autorizacion.getNumeroAutorizacion());
                    ClaveAcceso claveAcceso= new ClaveAcceso(autorizacion.getNumeroAutorizacion());                
                    comprobanteData.setPreimpreso(claveAcceso.getPreimpreso());
                }
                
                System.out.println("No se puede transformar el comprobante");
                //System.out.println(autorizacion.getComprobante());
            }
            
            //Compiar los mensajes
            Autorizacion.Mensajes mensajes=autorizacion.getMensajes();
            List<ComprobanteMensaje> mensajesData=new ArrayList<ComprobanteMensaje>();
            
            if(mensajes!=null && mensajes.getMensaje()!=null)
            {
                for (Mensaje mensajeAutorizacion : mensajes.getMensaje()) 
                {
                    ComprobanteMensaje comprobanteMensaje=new ComprobanteMensaje();
                    comprobanteMensaje.setIdentificador(mensajeAutorizacion.getIdentificador());
                    comprobanteMensaje.setInformacionAdicional(mensajeAutorizacion.getInformacionAdicional());
                    comprobanteMensaje.setMensaje(mensajeAutorizacion.getMensaje());
                    comprobanteMensaje.setTipo(mensajeAutorizacion.getTipo());

                    mensajesData.add(comprobanteMensaje);
                }
            }
            comprobanteData.setMensajes(mensajesData);
            comprobantes.add(comprobanteData);
        }
        
        return comprobantes;
    }
    
    public void procesarComprobanteOffline(ComprobanteDataInterface comprobanteData,Factura factura,Usuario usuario,ClienteInterfaceComprobante callbackClientObject) throws RemoteException {
        ComprobanteElectronicoService comprobanteElectronico= cargarConfiguracionesInicialesComprobantes(comprobanteData, usuario);
        //Generar proceso hasta el envio del comprobante
        comprobanteElectronico.setEtapaLimiteProcesar(ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE);
        
        comprobanteElectronico.addActionListerComprobanteElectronico(new ListenerComprobanteElectronico() {
            private boolean existeConexionRemota=true;
            @Override
            public void termino() {
                try {
                    //Si la factura termina corectamente grabo el estado y numero de autorizacion
                    FacturacionService facturacionService=new FacturacionService();
                   
                    factura.setClaveAcceso(comprobanteElectronico.getClaveAcceso());
                    factura.setEstadoEnum(ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR);
                    
                    try {
                        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                            @Override
                            public void transaccion() {
                                entityManager.merge(factura);
                            }                    
                        });
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    byte[] serializedPrint= getReporteComprobante(comprobanteElectronico.getClaveAcceso(),factura.getEmpresa()); //Todo: Revisar si esta procesando correctamente      
                    if(existeConexionRemota)
                    {
                        callbackClientObject.termino(serializedPrint,comprobanteElectronico.getAlertas());
                    }

                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void iniciado(ComprobanteElectronico comprobante) {
                try {
                    //Si no existe un objeto callback tampoco ejecuto el reto de proceso callback
                    if(callbackClientObject==null)
                    {
                        existeConexionRemota=false;
                    }
                    else
                    {                        
                        callbackClientObject.iniciado();
                    }
                } catch (RemoteException ex) {
                    existeConexionRemota=false;
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void procesando(int etapa, ClaveAcceso clave) {
                try {
                    if(existeConexionRemota)
                    {
                        callbackClientObject.procesando(etapa,clave);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void error(ComprobanteElectronicoException cee) {
                try {
                    if(existeConexionRemota)
                    {
                        callbackClientObject.error(cee, comprobanteElectronico.getClaveAcceso());
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void autorizado(Autorizacion documentoAutorizado) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                //Todo: Este metodo no se implementa porque se supone que no va a terminar de autorizar porque no llega a esta esa etapa
            }
        });
        
        comprobanteElectronico.procesar(false);
        

    }
    
    public boolean verificarDisponibilidadSri(Empresa empresa) throws RemoteException
    {
        ComprobanteElectronicoService comprobanteElectronico=new ComprobanteElectronicoService();
        cargarDirectoriosWebService(comprobanteElectronico,empresa);
        return comprobanteElectronico.disponibilidadServidorSri(ParametrosSistemaCodefac.INTENTOS_MAXIMO_VERICAR_CONEXION_SRI);
    }
    
    /**
     *
     * @param comprobante el comprobante a procesar facturas, notas de credito
     * con los datos finales implementados
     */
    public void procesarComprobante(ComprobanteDataInterface comprobanteData,ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity comprobante,Usuario usuario,ClienteInterfaceComprobante callbackClientObject) throws RemoteException {
                
        ComprobanteElectronicoService comprobanteElectronico= cargarConfiguracionesInicialesComprobantes(comprobanteData, usuario);
        procesarComprobanteExtend(comprobanteElectronico, comprobante, callbackClientObject);

    }
    
    /**
     * Artificio para poder generar otros documentos con el formato de los ride pero no legales por ejemplo notas de venta interna
     */
    public void generarRideComprobanteNoLegal(ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity comprobante,ComprobanteDataInterface comprobanteData,Usuario usuario) throws RemoteException
    {
        //comprobanteData.getComprobante().getInformacionTributaria().setClaveAcceso(comprobante.getPreimpreso());
        ComprobanteElectronicoService comprobanteElectronico= cargarConfiguracionesInicialesComprobantes(comprobanteData, usuario);
        comprobanteElectronico.generarRideManual();
    }
    
    private void procesarComprobanteExtend(ComprobanteElectronicoService comprobanteElectronico,ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity comprobanteOriginal,ClienteInterfaceComprobante callbackClientObject)
    {
        //comprobanteOriginal.validarDatosAdicionales();
        comprobanteOriginal.setEstadoEnum(ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR);
        
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() {
                    entityManager.merge(comprobanteOriginal);
                }        
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }

                //Agregar el listener
        comprobanteElectronico.addActionListerComprobanteElectronico(new ListenerComprobanteElectronico() {
            /**
             * Variable que me permite verificar la primera si existe conexion para no hacer comprobaciones
             */
            private boolean existeConexionRemota=true;
            
            @Override
            public void termino() {
                try {
                    //Si la factura termina corectamente grabo el estado y numero de autorizacion
                    byte[] serializedPrint= getReporteComprobante(comprobanteElectronico.getClaveAcceso(),comprobanteOriginal.getEmpresa());   
                    
                    if(existeConexionRemota)
                    {
                        callbackClientObject.termino(serializedPrint,comprobanteElectronico.getAlertas());     
                    }
                    
                } catch (RemoteException ex) {                    
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void iniciado(ComprobanteElectronico comprobante) {
                try {
                    if(callbackClientObject!=null)
                    {
                        callbackClientObject.iniciado();                        
                    }else
                    {
                        existeConexionRemota=false;
                    }
                    
                } catch (RemoteException ex) {
                    existeConexionRemota=false;
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void procesando(int etapa, ClaveAcceso clave) {
                try {
                    if(existeConexionRemota)
                    {
                        callbackClientObject.procesando(etapa, clave);
                    }                    
                                       
                    //Si se genera la etapa firmar entonces seteo la clave de acceso
                    if (etapa == ComprobanteElectronicoService.ETAPA_FIRMAR) {
                        comprobanteOriginal.setClaveAcceso(clave.clave);

                        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                            @Override
                            public void transaccion() {
                                entityManager.merge(comprobanteOriginal);
                            }
                        });
                    }
                    
                    
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }

            @Override
            public void error(ComprobanteElectronicoException cee) {
                try {
                    if(existeConexionRemota)
                    {
                        callbackClientObject.error(cee,comprobanteElectronico.getClaveAcceso());
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void autorizado(Autorizacion documentoAutorizado) {
                
                try {
                    ComprobanteEntity comprobanteEditar=entityManager.merge(comprobanteOriginal);
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.INFO,"Procesando Autorizar Preimpreso: "+comprobanteEditar.getPreimpreso()+" | documento: "+comprobanteEditar.getCodigoDocumentoEnum().getNombre());
                    setearDatosAutorizacionComprobanteConTransaccion(comprobanteEditar,documentoAutorizado);
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.INFO,"Documento AUTORIZADO Correctamente: "+comprobanteEditar.getPreimpreso()+" | documento: "+comprobanteEditar.getCodigoDocumentoEnum().getNombre());                    
                    
                    
                    //setearDatosAutorizac (comprobanteEditar,documentoAutorizado);
                    
                    /*try {
                    ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                    @Override
                    public void transaccion() {
                    //Setear el campo de seteado a factura solo si pasa la etapa de autorizar
                    //ComprobanteEntity comprobanteOriginal=buscarPorId(comprobanteOriginal.getId)
                    ComprobanteEntity comprobanteEditar=entityManager.merge(comprobanteOriginal);
                    LOG.log(Level.INFO,"Actualizando la informacion del comprobante ");
                    //comprobanteOriginal.setEstado(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());
                    //setearDatosAutorizacionComprobante(comprobanteOriginal,documentoAutorizado);
                    //entityManager.merge(comprobanteOriginal);
                    //comprobanteEditar.setEstadoEnum(ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO);
                    LOG.log(Level.INFO,"Cambiando el estado Autorizado del comprobante ");
                    setearDatosAutorizacionComprobanteConTransaccion(comprobanteEditar,documentoAutorizado);
                    LOG.log(Level.INFO,"Cambiando el resto de datos del comprobante autorizado");
                    //entityManager.merge(comprobanteEditar);
                    LOG.log(Level.INFO,"Guardando cambios del comprobante autorizado");
                    }
                    });

                    //Enviar mensaje
                    //ServidorSMS.getInstance().enviarMensaje("994905332","La factura"+clave.secuencial+" fue enviada a su correo");
                    } catch (ServicioCodefacException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                    }*/
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        
        //Proceso el comprobante
        comprobanteElectronico.procesar(false);
    
    }
    
    private void setearDatosAutorizacionComprobanteConTransaccion(ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity comprobanteOriginal,Autorizacion documentoAutorizado) throws ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                if(documentoAutorizado.getEstado().equals("AUTORIZADO"))
                {                    
                    comprobanteOriginal.setEstadoEnum(ComprobanteEnumEstado.AUTORIZADO);
                    XMLGregorianCalendar fechaXml = documentoAutorizado.getFechaAutorizacion();
                    java.sql.Date fechaAutorizacion = new java.sql.Date(fechaXml.toGregorianCalendar().getTime().getTime());
                    comprobanteOriginal.setFechaAutorizacionSri(fechaAutorizacion);

                    ComprobanteEntity.TipoAmbienteEnum enumAmbiente = ComprobanteEntity.TipoAmbienteEnum.buscarPorNombreSri(documentoAutorizado.getAmbiente());
                    if (enumAmbiente != null) {
                        comprobanteOriginal.setTipoAmbiente(enumAmbiente.getLetra());
                    }
                    
                     entityManager.merge(comprobanteOriginal);
                     Logger.getLogger(ComprobantesService.class.getName()).log(Level.INFO,"El comprobante "+comprobanteOriginal.getPreimpreso()+" fue autorizado desde el metodo setearDatosAutorizacionComprobanteConTransaccion() en la clase ComprobantesService");
                }

            }
        });        
                
    }
    
    
    private ComprobanteElectronicoService cargarConfiguracionesInicialesComprobantesLote(List<ComprobanteDataInterface> comprobantesData,Usuario usuario) throws RemoteException
    {
        ComprobanteElectronicoService comprobanteElectronico = new ComprobanteElectronicoService();
        
        List<ComprobanteElectronico> comprobantesElectronico=new ArrayList<ComprobanteElectronico>();
        
        for (ComprobanteDataInterface comprobanteData : comprobantesData) {
            ComprobanteElectronico comprobante= comprobanteData.getComprobante();
            //Agregando informacionTributaria
            comprobante.setInformacionTributaria(getInfoInformacionTributaria(comprobanteData));
            
            //Validacion para verificar que si no existen datos adicionales no se agregue nada
            List<InformacionAdicional> informacionAdicional = getInformacionAdicional(comprobanteData);
            if (informacionAdicional != null && informacionAdicional.size() > 0) {
                comprobante.setInformacionAdicional(informacionAdicional);
            }
            
            comprobantesElectronico.add(comprobante);
        }        
        //Agregar datos adicionales del Reporte
        comprobanteElectronico.setMapAdicionalReporte(mapReportePlantilla(null));
        
        //Cargar los correos que se van a usar para enviar los datos
        //comprobanteElectronico.setCorreosElectronicos(comprobanteData.getCorreos());

        
        comprobanteElectronico.setComprobantesLote(comprobantesElectronico);
        
        //Cargar configuraciones por defecto para los comprobantes
        cargarConfiguraciones(comprobanteElectronico,comprobantesData.get(0).getEmpresa());

        //Etapa desde la cual va a procesar los comprobantes
        comprobanteElectronico.setEtapaActual(ComprobanteElectronicoService.ETAPA_GENERAR);
    
        return comprobanteElectronico;
    }
    
    /**
     * Convertir clase intermediaria en el formato para procesar electronicamente
     * @return 
     */
    private ComprobanteElectronico convertirComprobanteDatoToComprobateElectronico(ComprobanteDataInterface comprobanteData) throws RemoteException
    {
        ComprobanteElectronico comprobante= comprobanteData.getComprobante();
        
        //Agregando informacionTributaria
        comprobante.setInformacionTributaria(getInfoInformacionTributaria(comprobanteData));
        
        //Validacion para verificar que si no existen datos adicionales no se agregue nada
        List<InformacionAdicional> informacionAdicional = getInformacionAdicional(comprobanteData);
        if (informacionAdicional != null && informacionAdicional.size() > 0) 
        {
            //Validar que si son mas de 15 datos solo coja los 15 primeros por que no permite enviar más para la facturación electrónica
            if(informacionAdicional.size()>15)
            {
                comprobante.setInformacionAdicional(informacionAdicional.subList(0,15));
            }
            else
            {
                comprobante.setInformacionAdicional(informacionAdicional);
            }
        }
        
        return comprobante;
        
    }
    
    private ComprobanteElectronicoService cargarConfiguracionesInicialesComprobantes(ComprobanteDataInterface comprobanteData,Usuario usuario) throws RemoteException
    {
        /**
         * Metodo del modulo de facturacion electronica que contiene la interfaz
         * para facturar electronicamente
         */
        //ComprobanteElectronico comprobante= comprobanteData.getComprobante();
        ComprobanteElectronico comprobante= convertirComprobanteDatoToComprobateElectronico(comprobanteData);
        ComprobanteElectronicoService comprobanteElectronico = new ComprobanteElectronicoService();

        //Agregando informacionTributaria
        //comprobante.setInformacionTributaria(getInfoInformacionTributaria(comprobanteData));

        //Validacion para verificar que si no existen datos adicionales no se agregue nada
        /*List<InformacionAdicional> informacionAdicional = getInformacionAdicional(comprobanteData);
        if (informacionAdicional != null && informacionAdicional.size() > 0) 
        {
            //Validar que si son mas de 15 datos solo coja los 15 primeros por que no permite enviar más para la facturación electrónica
            if(informacionAdicional.size()>15)
            {
                comprobante.setInformacionAdicional(informacionAdicional.subList(0,15));
            }
            else
            {
                comprobante.setInformacionAdicional(informacionAdicional);
            }
        }*/
        
        
        //Agregar datos adicionales del Reporte
        comprobanteElectronico.setMapAdicionalReporte(mapReportePlantilla(comprobanteData.getEmpresa()));
        
        //Cargar los correos que se van a usar para enviar los datos
        comprobanteElectronico.setCorreosElectronicos(comprobanteData.getCorreos());

        comprobanteElectronico.setComprobante(comprobante);
        
        //Cargar configuraciones por defecto para los comprobantes
        cargarConfiguraciones(comprobanteElectronico,comprobanteData.getEmpresa());

        //Etapa desde la cual va a procesar los comprobantes
        comprobanteElectronico.setEtapaActual(ComprobanteElectronicoService.ETAPA_GENERAR);
        
        //Consultar la modalidad como se van a procesar los correos
        ParametroCodefacService parametroCodefacService = new ParametroCodefacService();
        ParametroCodefac parametroTipoEnvio=parametroCodefacService.getParametroByNombre(ParametroCodefac.TIPO_ENVIO_COMPROBANTE,comprobanteData.getEmpresa());
        if(parametroTipoEnvio!=null && ParametroCodefac.TipoEnvioComprobanteEnum.buscarPorLetra(parametroTipoEnvio.valor).equals(ParametroCodefac.TipoEnvioComprobanteEnum.ENVIAR_AUTORIZADO))
        {
            //Si requiere que se envie el correo con el xml autorizado se pone en true
            comprobanteElectronico.setEnviarCorreoComprobanteAutorizado(true);
        }
    
        return comprobanteElectronico;
    }

    private InformacionTributaria getInfoInformacionTributaria(ComprobanteDataInterface comprobanteData) throws RemoteException {
        InformacionTributaria infoTributaria = new InformacionTributaria();
        //InformacionTributaria infoTributaria = comprobanteData.getComprobante().getInformacionTributaria();
        ParametroCodefacService parametroCodefacService = new ParametroCodefacService();
        //EmpresaService empresaService = new EmpresaService();
        Empresa empresa = comprobanteData.getEmpresa();
        Map<String, ParametroCodefac> parametroCodefacMap = parametroCodefacService.getParametrosMap(empresa);

        if (parametroCodefacMap.get(ParametroCodefac.MODO_FACTURACION).valor.equals(ComprobanteElectronicoService.MODO_PRODUCCION)) {
            infoTributaria.setAmbiente(ComprobanteElectronicoService.CODIGO_SRI_MODO_PRODUCCION + "");
        } else {
            infoTributaria.setAmbiente(ComprobanteElectronicoService.CODIGO_SRI_MODO_PRUEBAS + "");
        }

        infoTributaria.setClaveAcceso("");                
        infoTributaria.setCodigoDocumento(comprobanteData.getCodigoComprobante());
        
        /**
         * Obtener la direccion de la matriz de la empresa seleccionada
         */
        SucursalService sucursalService=new SucursalService();
        Sucursal matriz=null;
        try {
            matriz = sucursalService.obtenerMatrizPorSucursal(empresa);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        //Si existe seteada grabada una direccion la ingreso
        if(comprobanteData.getDireccionMatriz()!=null && !comprobanteData.getDireccionMatriz().isEmpty())
        {
            infoTributaria.setDirecionMatriz(comprobanteData.getDireccionMatriz());
        }
        else //Si no existe grabada una direccion selecciono la de la empresa
        {
            infoTributaria.setDirecionMatriz((matriz!=null && matriz.getDirecccion()!=null && !matriz.getDirecccion().isEmpty())?matriz.getDirecccion():"SIN DIRECCION");
        }

        //String establecimiento = parametroCodefacMap.get(ParametroCodefac.ESTABLECIMIENTO).valor;
        infoTributaria.setEstablecimiento(ComprobantesUtilidades.formatoEstablecimiento( comprobanteData.getEstablecimiento()));
        
        if(empresa.getNombreLegal()!=null && !empresa.getNombreLegal().isEmpty())
        {
            infoTributaria.setNombreComercial(empresa.getNombreLegal());
        }

        //String puntoEmision = parametroCodefacMap.get(ParametroCodefac.PUNTO_EMISION).valor;
        infoTributaria.setPuntoEmision(ComprobantesUtilidades.formatoEstablecimiento( comprobanteData.getPuntoEmision()));

        infoTributaria.setRazonSocial(empresa.getRazonSocial());
        infoTributaria.setRuc(empresa.getIdentificacion());
        infoTributaria.setSecuencial(comprobanteData.getSecuencial());
        infoTributaria.setTipoEmision(ComprobanteElectronico.MODO_FACTURACION_NORMAL);
        
        //Asignar datos cuando es microempresa
        if(empresa.getContribuyenteRegimenMicroempresasEnum()!=null && empresa.getContribuyenteRegimenMicroempresasEnum().equals(EnumSiNo.SI))
        {
            infoTributaria.asignarRegimenMicroempresas();
        }
        
        //Asignar dato cuando es agente de retencion
        if(empresa.getAgenteRetencionResolucion()!=null && !empresa.getAgenteRetencionResolucion().trim().isEmpty())
        {
            infoTributaria.setAgenteRetencion(empresa.getAgenteRetencionResolucion());
        }
        
        return infoTributaria;
    }

    private List<InformacionAdicional> getInformacionAdicional(ComprobanteDataInterface comprobanteData) {
        List<InformacionAdicional> listaInfoAdicional = new ArrayList<InformacionAdicional>();

        Map<String, String> map = comprobanteData.getMapAdicional();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                InformacionAdicional info = new InformacionAdicional();
                info.setNombre(key);
                info.setValor(value);
                listaInfoAdicional.add(info);
            }
        }
        return listaInfoAdicional;
    }
    
    private void cargarDirectoriosWebService(ComprobanteElectronicoService servicio,Empresa empresa) {
        
        try {
            ParametroCodefacService parametroCodefacService = new ParametroCodefacService();
            Map<String, ParametroCodefac> parametroCodefacMap = parametroCodefacService.getParametrosMap(empresa);
            
            //Por defecto si no existe un modo configurado selecciono produccion
            String modoFacturacion=ComprobanteElectronicoService.MODO_PRODUCCION;
            
            if(parametroCodefacMap.get(ParametroCodefac.MODO_FACTURACION)!=null)
            {
                modoFacturacion = parametroCodefacMap.get(ParametroCodefac.MODO_FACTURACION).valor;
            }
            
            servicio.setModoFacturacion(modoFacturacion);
            /**
             * Cargar los web services dependiendo el modo de facturacion
             */
            if (ComprobanteElectronicoService.MODO_PRODUCCION.equals(modoFacturacion)) {
                String autorizacion = ParametrosSistemaCodefac.SRI_WS_AUTORIZACION;
                servicio.setUriAutorizacion(autorizacion);
                
                String recepcion = ParametrosSistemaCodefac.SRI_WS_RECEPCION;
                servicio.setUriRecepcion(recepcion);
                
            } else {
                String autorizacion = ParametrosSistemaCodefac.SRI_WS_AUTORIZACION_PRUEBA;
                servicio.setUriAutorizacion(autorizacion);
                
                String recepcion = ParametrosSistemaCodefac.SRI_WS_RECEPCION_PRUEBA;
                servicio.setUriRecepcion(recepcion);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    //Metodo que me permite establecer un alias a los nombres de los documentos
    private void cargarNombresAliasDocumentos(ComprobanteElectronicoService servicio,Empresa empresa)
    {
        Map<ComprobanteEnum, String> aliasNombreDocumentosMap=new HashMap<ComprobanteEnum,String>();
        //Buscar el nombre de alias para las notas de venta interna
        String aliasNotaVentaInterna=ParametroUtilidades.obtenerValorParametro(empresa,ParametroCodefac.AliasNombresDocumentos.NOTA_VENTA_INTERNA_ALIAS);
        if(aliasNotaVentaInterna!=null && !aliasNotaVentaInterna.trim().isEmpty())
        {
            //Si tiene asignado un nombre le pongo al map para configurar
            aliasNombreDocumentosMap.put(ComprobanteEnum.NOTA_VENTA_INTERNA, aliasNotaVentaInterna);
            
        }        
        servicio.setAliasNombreDocumentosMap(aliasNombreDocumentosMap);
        
    }

    private void cargarConfiguraciones(ComprobanteElectronicoService servicio,Empresa empresa) {
        try {
            ParametroCodefacService parametroCodefacService = new ParametroCodefacService();
            Map<String, ParametroCodefac> parametroCodefacMap = parametroCodefacService.getParametrosMap(empresa);
            //String pathBase de los directorios            
            servicio.setPathBase(UtilidadesServidor.mapEmpresasLicencias.get(empresa).pathEmpresa);
            
            //Si no existe esos datos no se cargan para procesar
            String nombreFirma=ParametroUtilidades.obtenerValorParametro(empresa,ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA);
            if(nombreFirma!=null)
            {
                servicio.setNombreFirma(parametroCodefacMap.get(ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA).valor);
                servicio.setClaveFirma(UtilidadesEncriptar.desencriptar(parametroCodefacMap.get(ParametroCodefac.CLAVE_FIRMA_ELECTRONICA).valor,ParametrosSistemaCodefac.LLAVE_ENCRIPTAR));
            }
            String modoFacturacion = parametroCodefacMap.get(ParametroCodefac.MODO_FACTURACION).valor;
            servicio.setModoFacturacion(modoFacturacion);

            cargarDatosRecursos(servicio,empresa);
            
            cargarNombresAliasDocumentos(servicio, empresa);

            /**
             * Cargar los web services dependiendo el modo de facturacion
             */
            if (ComprobanteElectronicoService.MODO_PRODUCCION.equals(modoFacturacion)) {
                String autorizacion = ParametrosSistemaCodefac.SRI_WS_AUTORIZACION;
                servicio.setUriAutorizacion(autorizacion);

                String recepcion = ParametrosSistemaCodefac.SRI_WS_RECEPCION;
                servicio.setUriRecepcion(recepcion);

            } else {
                String autorizacion = ParametrosSistemaCodefac.SRI_WS_AUTORIZACION_PRUEBA;
                servicio.setUriAutorizacion(autorizacion);

                String recepcion = ParametrosSistemaCodefac.SRI_WS_RECEPCION_PRUEBA;
                servicio.setUriRecepcion(recepcion);
            }

            /**
             * Cargar variables para el envio del correo
             */
            cargarConfiguracionesCorreo(servicio,empresa);
            cargarConfiguracionesSms(servicio);
            
            
            String footer = UtilidadVarios.getStringHtmltoUrl(RecursoCodefac.HTML.getResourceInputStream("footer_codefac.html"));
            ParametroCodefac parametroFooter=parametroCodefacMap.get(ParametroCodefac.FORMATO_MENSAJE_COMPROBANTE_ELECTRONICO);
            if(parametroFooter!=null && parametroFooter.getValor()!=null)
            {
                footer = parametroFooter.getValor();
            }            
            
            servicio.setFooterMensajeCorreo(footer);

            /**
             * Cargar datos de las formas de pago
             */
            SriServiceIf service = ServiceFactory.getFactory().getSriServiceIf();
            List<SriFormaPago> formasPagoSri = service.obtenerFormasPagoActivo();
            Map<String, String> mapFormasPago = new HashMap<String, String>();
            for (SriFormaPago sriFormaPago : formasPagoSri) {
                mapFormasPago.put(sriFormaPago.getCodigo(), sriFormaPago.getNombre());
            }
            servicio.setMapCodeAndNameFormaPago(mapFormasPago);
            
            /**
             * Cargar los tipos de documentos para las facturas
             */
            
            List<SriRetencion> retenciones=ServiceFactory.getFactory().getSriRetencionServiceIf().obtenerTodos();
            Map<String, String> mapTipoRetencion = new HashMap<String, String>();
            for (SriRetencion retencion : retenciones) {
                mapTipoRetencion.put(retencion.getCodigo(), retencion.getNombre());
            }
            servicio.setMapCodeAndNameTipoRetecion(mapTipoRetencion);
            
            /**
             * Cargar el nombre de los documentos
             */
            ComprobanteEnum[] codigosComprobante= ComprobanteEnum.values();
            Map<String, String> mapTipoDocumento = new HashMap<String, String>();
            for (ComprobanteEnum comprobanteEnum : codigosComprobante) 
            {
                mapTipoDocumento.put(comprobanteEnum.getCodigo(),comprobanteEnum.getNombre());
            }
            servicio.setMapCodeAndNameTipoDocumento(mapTipoDocumento);
            
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void cargarDatosRecursos(ComprobanteElectronicoService servicio,Empresa empresa) throws RemoteException {
        ParametroCodefacService parametroCodefacService = new ParametroCodefacService();
        RecursosServiceIf service= ServiceFactory.getFactory().getRecursosServiceIf();
        
        //EmpresaService empresaService = new EmpresaService();
        //Empresa empresa = empresaService.obtenerTodos().get(0);
        Map<String, ParametroCodefac> parametroCodefacMap = parametroCodefacService.getParametrosMap(empresa);
        /**
         * Setear variables de configuracion para los reportes
         */
        servicio.setPathFacturaJasper(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourceURL("facturaReporte.jrxml"));
        servicio.setPathNotaCreditoJasper(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourceURL("notaCreditoReporte.jrxml"));
        servicio.setPathRetencionJasper(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourceURL("retencion.jrxml"));
        servicio.setPathGuiaRemisionJasper(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourceURL("guiaRemision.jrxml"));
        
        //Cargar el reporte para las guias de remision
        JasperReport reporteDetalleGuiaRemision=cargarRecursoJasperProxy(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS,"guiaRemisionDetalle.jrxml");
        servicio.setJasperSubReporteGuiaRemision(reporteDetalleGuiaRemision);
        
        
        servicio.setPathParentJasper(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS.getResourcesParentPath("facturaReporte.jrxml"));
        
        JasperReport reportFormaPago=cargarRecursoJasperProxy(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS,"forma_pago.jrxml");
        servicio.setReporteFormaPago(reportFormaPago);
        
        JasperReport  reportDatosAdicionales=cargarRecursoJasperProxy(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS,"datos_adicionales.jrxml");
        servicio.setReporteInfoAdicional(reportDatosAdicionales);
        
        JasperReport reportDatoOtrosAdicionales=cargarRecursoJasperProxy(RecursoCodefac.JASPER_COMPROBANTES_ELECTRONICOS,"datos_adicionalesA4.jrxml");
        servicio.setReporteInfoOtroAdicional(reportDatoOtrosAdicionales);
        
        
        servicio.setMapAdicionalReporte(mapReportePlantilla(empresa)); //Todo: revisar si esto esta biena
        //servicio.pathLogoImagen = RecursoCodefac.IMAGENES_GENERAL.getResourceURL("sin_imagen.jpg").getPath();
        //Segun el tipo de licencia cargar los recursos
        
        servicio.pathLogoImagen = UtilidadImagen.castInputStreamToImage(RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream(ParametrosSistemaCodefac.ComprobantesElectronicos.LOGO_SIN_FOTO));
        if (UtilidadesServidor.mapEmpresasLicencias.get(empresa).tipoLicencia.equals(TipoLicenciaEnum.PRO)) {

            InputStream inputStream = null;
            try {

                String imagenLogo = empresa.getImagenLogoPath();      
                
                //Si no existe imagen grabada en la base de datos ,muestra la imagen por defecto
                if(imagenLogo==null || imagenLogo.trim().isEmpty())
                {
                    inputStream=RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream(ParametrosSistemaCodefac.ComprobantesElectronicos.LOGO_SIN_FOTO);
                }
                else
                {                    
                    String pathImagen = UtilidadesServidor.mapEmpresasLicencias.get(empresa).pathEmpresa + "/" + DirectorioCodefac.IMAGENES.getNombre() + "/" + imagenLogo;
                    inputStream = new FileInputStream(pathImagen);
                    //Si no existe imagen en la version de pago setea un imagen por defecto
                    if (inputStream == null) {
                        inputStream = RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream(ParametrosSistemaCodefac.ComprobantesElectronicos.LOGO_SIN_FOTO);
                    }
                    
                }

                //Seteo la imagen de logo de la empresa
                servicio.pathLogoImagen =UtilidadImagen.castInputStreamToImage(inputStream);
                //servicio.pathLogoImagen = new File(pathImagen.get;
            } catch (FileNotFoundException ex) {
                servicio.pathLogoImagen = UtilidadImagen.castInputStreamToImage(RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream(ParametrosSistemaCodefac.ComprobantesElectronicos.LOGO_SIN_FOTO));
                LOG.log(Level.WARNING,"No esta definido el logo de la empresa");
                //Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    private Map<String, Object> mapReportePlantilla(Empresa empresa) throws RemoteException {
        RecursosServiceIf service= ServiceFactory.getFactory().getRecursosServiceIf();
        ParametroCodefacService parametroCodefacService = new ParametroCodefacService();
        
        Map<String, ParametroCodefac> parametroCodefacMap = parametroCodefacService.getParametrosMap(empresa);
        
        //TODO:Revisar esta parte porque va a salir con la informacion de la matriz princiapl ,talvez deberia salir con la informacion de la sucursal que estan usando
        SucursalService sucursalService=new SucursalService();
        Sucursal matriz=null;
        try {
            matriz= sucursalService.obtenerMatrizPorSucursal(empresa);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("pl_telefonos",(matriz.getTelefono()!=null)?matriz.getTelefono():matriz.getTelefono());        
        parametros.put("pl_celular", (matriz.getCelular()!=null)?matriz.getCelular():matriz.getCelular());
        parametros.put("pl_facebook",(empresa.getFacebook()!=null)?empresa.getFacebook():"");
        parametros.put("pl_instagram",(empresa.getInstagram()!=null)?empresa.getInstagram():"");
        
        
        if (UtilidadesServidor.mapEmpresasLicencias.get(empresa).pathEmpresa.equals(TipoLicenciaEnum.GRATIS)) {
            parametros.put("pl_adicional",ParametrosSistemaCodefac.MensajesSistemaCodefac.MENSAJE_PIE_PAGINA_GRATIS);
        } else {
            parametros.put("pl_adicional",(empresa.getAdicional()!=null)?empresa.getAdicional():"");
        } 
        
        //Agregar el campo para saber si es regimen microempresas
        EnumSiNo regimenMicroEmpresasEnum=(empresa.getContribuyenteRegimenMicroempresasEnum()!=null)?empresa.getContribuyenteRegimenMicroempresasEnum():EnumSiNo.NO;
        parametros.put("pl_contribuyenteRegimenMicroempresas", regimenMicroEmpresasEnum.getLetra());
        
        if(empresa.getAgenteRetencionResolucion()!=null)
        {
            parametros.put("pl_agenteRetencionResolucion", empresa.getAgenteRetencionResolucion());
        }
        

        InputStream input=null;
        
        //Si la licencia es gratis entonces cargar por defecto una imagen por defecto
        if(UtilidadesServidor.mapEmpresasLicencias.get(empresa).pathEmpresa.equals(TipoLicenciaEnum.GRATIS))
        {
            input=RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream(ParametrosSistemaCodefac.ComprobantesElectronicos.LOGO_SIN_FOTO);
        }
        else //Si la licencia es de pago entonces carga la imagen de la empresa
        {
            //Verifica si esta guardado el path de la imagen 
            if(empresa.getImagenLogoPath()!=null && !empresa.getImagenLogoPath().equals("") )
            {
                RemoteInputStream remoteInputStream = service.getResourceInputStreamByFile(empresa,DirectorioCodefac.IMAGENES, empresa.getImagenLogoPath());

                if (remoteInputStream != null) {
                    try {
                        input = RemoteInputStreamClient.wrap(remoteInputStream);
                    } catch (IOException ex) {
                        Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
                        input = RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream(ParametrosSistemaCodefac.ComprobantesElectronicos.LOGO_SIN_FOTO);
                    }
                } else //Si no existe imagen cargar por defecto una imagen en blanco
                {
                    input = RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream(ParametrosSistemaCodefac.ComprobantesElectronicos.LOGO_SIN_FOTO);
                }
            }
            else
            {
                input = RecursoCodefac.IMAGENES_GENERAL.getResourceInputStream(ParametrosSistemaCodefac.ComprobantesElectronicos.LOGO_SIN_FOTO);
            }           
            
        }
        
        //Agregar datos de las leyendas para las firmas
        parametros.put("campoFirma1",ParametroUtilidades.obtenerValorParametro(empresa, ParametroCodefac.LEYENDA_FIRMA_FACTURA_1));
        parametros.put("campoFirma2",ParametroUtilidades.obtenerValorParametro(empresa, ParametroCodefac.LEYENDA_FIRMA_FACTURA_2));
        parametros.put("campoFirma3",ParametroUtilidades.obtenerValorParametro(empresa, ParametroCodefac.LEYENDA_FIRMA_FACTURA_3));
        
        
        parametros.put("pl_url_img1", UtilidadImagen.castInputStreamToImage(input)); 
        parametros.put("pl_img_instagram", (UtilidadImagen.castInputStreamToImage(RecursoCodefac.IMAGENES_REDES_SOCIALES.getResourceInputStream("instagram.png"))));
        parametros.put("pl_img_facebook", (UtilidadImagen.castInputStreamToImage(RecursoCodefac.IMAGENES_REDES_SOCIALES.getResourceInputStream("facebook.png"))));
        parametros.put("pl_img_whatsapp", (UtilidadImagen.castInputStreamToImage(RecursoCodefac.IMAGENES_REDES_SOCIALES.getResourceInputStream("whatsapp.png"))));
        parametros.put("pl_img_telefono", (UtilidadImagen.castInputStreamToImage(RecursoCodefac.IMAGENES_REDES_SOCIALES.getResourceInputStream("telefono.png"))));
        
        JasperReport reportPiePagina=cargarRecursoJasperProxy(RecursoCodefac.JASPER,"pie_pagina.jrxml");
        JasperReport reportFirmaFactura=cargarRecursoJasperProxy(RecursoCodefac.JASPER,"firmas_factura.jrxml");
        
        parametros.put("pl_url_piepagina", reportPiePagina);
        parametros.put("pl_firmas_factura", reportFirmaFactura);
        
        return parametros;
    }
    
    private JasperReport cargarRecursoJasperProxy(RecursoCodefac recursoCodefac,String nombreReporteJrxml)
    {
        RecursosServiceIf service= ServiceFactory.getFactory().getRecursosServiceIf();
        InputStream inputStream=null;
        JasperReport reportJasper=null;
        try {
            //reportPiePagina=ReporteProxy.buscar(RecursoCodefac.JASPER, "pie_pagina.jrxml");
            reportJasper=ReporteProxy.buscar(recursoCodefac, nombreReporteJrxml);
            if(reportJasper==null)
            {
                //inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(RecursoCodefac.JASPER, "pie_pagina.jrxml"));
                inputStream=RemoteInputStreamClient.wrap(service.getResourceInputStream(recursoCodefac, nombreReporteJrxml));
                reportJasper = JasperCompileManager.compileReport(inputStream);
                ReporteProxy.agregar(recursoCodefac, nombreReporteJrxml, reportJasper);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return reportJasper;
    }
    
    private void cargarConfiguracionesSms(ComprobanteElectronicoService servicio)
    {
        servicio.setMetodoEnvioSmsInterface(new MetodoEnvioSmsInterface() {
            @Override
            public void enviarMensaje(List<String> numeros, String mensaje) throws Exception {
                ServidorSMS servidorSms=ServidorSMS.getInstance();
                for (String numero : numeros) {
                    servidorSms.enviarMensaje(numero, mensaje);
                }
            }
        });
        
        
    }
    
    private void cargarConfiguracionesCorreo(ComprobanteElectronicoService servicio,Empresa empresa) throws RuntimeException
    {
        
        MetodosEnvioInterface envioCorreoIf=new MetodosEnvioInterface() {
            private CorreoCodefac correo;
            
            private void crearObjectoCorreo(String mensaje, String subject, List<String> destinatorios, Map<String, String> pathFiles)
            {
                correo = new CorreoCodefac() {
                };
            }
            
            @Override
            public void enviarCorreo(String mensaje, String subject, List<String> destinatorios, Map<String, String> pathFiles) throws Exception 
            {
                //Si es la primera conexion que se va a ejecutar creo el primero objecto
                if(correo==null)
                {
                    crearObjectoCorreo(mensaje, subject, destinatorios, pathFiles);
                    correo.modoSession=true;
                }
                
                try
                {
                    correo.enviarCorreo(empresa, mensaje, subject, destinatorios, pathFiles);
                }catch(RuntimeException e)
                {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void cerrarSesion() {
                if(correo!=null)
                {
                    correo.cerrarSesionCodefacCorreo();
                }
            }
        };
        
        
        servicio.setMetodoEnvioInterface(envioCorreoIf);
    }

    @Override
    public void registerForCallback(ClienteInterfaceComprobante callbackClientObject) throws RemoteException {
        if (!(clientesLista.contains(callbackClientObject))) {
            clientesLista.addElement(callbackClientObject);            
        }
    }
    
    private synchronized void doCallbacks() throws RemoteException {
        for (int i = 0; i < clientesLista.size(); i++) {
            ClienteInterfaceComprobante nextClient= (ClienteInterfaceComprobante) clientesLista.elementAt(i);
            //nextClient.termino();
        } // for
    } // function

    @Override
    public void unregisterForCallback(ClienteInterfaceComprobante callbackClientObject) throws RemoteException {
        if (clientesLista.removeElement(callbackClientObject)) {
            System.out.println("Quitando registro cliente ");
        } else {
            System.out.println("El cliente no estaba registrado");
        }
    }
    
    private void agregarDatosAdicionalVendedor(ComprobanteEntity comprobante)
    {
        DocumentoEnum documento=comprobante.getCodigoDocumentoEnum();
        if(documento.equals(DocumentoEnum.FACTURA) || documento.equals(DocumentoEnum.NOTA_VENTA) || documento.equals(DocumentoEnum.NOTA_VENTA_INTERNA) )
        {
            Factura factura=(Factura) comprobante;
            if(factura.getVendedor()!=null)
            {
                FacturaAdicional facturaAdicional=new FacturaAdicional(
                        ComprobanteAdicional.CampoDefectoEnum.VENDEDOR.getNombre(),
                        factura.getVendedor().getNombresCompletos(),
                        ComprobanteAdicional.Tipo.TIPO_OTRO);

                factura.addDatoAdicional(facturaAdicional);
            }
            
        }
    
    }
    
    /**
     * Metodo que me permite agregar parametros propios de cada usuario al comprobante electronico
     * @param comprobante 
     */
    private void agregarParametrosPorUsuario(ComprobanteEntity comprobante) throws RemoteException
    {
        Usuario usuario=comprobante.getUsuario();
        String parametrosUsuario=usuario.getParametrosComprobatesElectronicos();
        if(parametrosUsuario!=null && !parametrosUsuario.isEmpty())
        {
            agregarParametroComprobante(comprobante, parametrosUsuario);
        }
        
        Empleado empleado=comprobante.getUsuario().getEmpleado();
        if(empleado!=null)
        {
            //TODO:parametrizar las etiquetas que estan quemadas
            if(ParametroUtilidades.comparar(comprobante.getEmpresa(),ParametroCodefac.FACTURACION_RIDE_DIRECCION_EMPLEADO,EnumSiNo.SI))
            {
                if(empleado.getDireccion()!=null && !empleado.getDireccion().trim().isEmpty())
                {
                    ComprobanteAdicional comprobanteAdicional=construirDatoAdicionalSinTransaccion(comprobante,"*Dirección",empleado.getDireccion());
                    comprobante.addDatoAdicional(comprobanteAdicional);
                }
            }
            
            if(ParametroUtilidades.comparar(comprobante.getEmpresa(),ParametroCodefac.FACTURACION_RIDE_RUC_EMPLEADO,EnumSiNo.SI))
            {
                ComprobanteAdicional comprobanteAdicional=construirDatoAdicionalSinTransaccion(comprobante,"*RUC",empleado.getIdentificacion());
                comprobante.addDatoAdicional(comprobanteAdicional);
            }
            
            if(ParametroUtilidades.comparar(comprobante.getEmpresa(),ParametroCodefac.FACTURACION_RIDE_RAZON_SOCIAL_EMPLEADO,EnumSiNo.SI))
            {
                ComprobanteAdicional comprobanteAdicional=construirDatoAdicionalSinTransaccion(comprobante,"*Razón Social",empleado.getNombresCompletos());
                comprobante.addDatoAdicional(comprobanteAdicional);
            }        
        }
        
        if (ParametroUtilidades.comparar(comprobante.getEmpresa(), ParametroCodefac.FACTURACION_RIDE_PUNTO_EMISION_EMPLEADO, EnumSiNo.SI)) {

            String puntosEmisionFormato = comprobante.getUsuario().formatoPuntoEmisionActivos();
            ComprobanteAdicional comprobanteAdicional = construirDatoAdicionalSinTransaccion(comprobante, "*Punto de Emisión", puntosEmisionFormato);
            comprobante.addDatoAdicional(comprobanteAdicional);
        }
        
        //Datos adicionales solo para cuando el documento es una factura
        if (comprobante.getCodigoDocumentoEnum().equals(DocumentoEnum.FACTURA)) 
        {
            Factura factura = (Factura) comprobante;
            if (ParametroUtilidades.comparar(comprobante.getEmpresa(), ParametroCodefac.FACTURACION_RIDE_REFERENCIA_DIRECCION, EnumSiNo.SI)) {
                String direccionReferencia = factura.getSucursal().getReferenciaDireccion();
                if (direccionReferencia != null && !direccionReferencia.trim().isEmpty()) {
                    ComprobanteAdicional comprobanteAdicional = construirDatoAdicionalSinTransaccion(comprobante, "*Referencia Dirección", direccionReferencia);
                    comprobante.addDatoAdicional(comprobanteAdicional);
                }
            }

            if (ParametroUtilidades.comparar(comprobante.getEmpresa(), ParametroCodefac.FACTURACION_RIDE_CODIGO_PERSONALIZADO, EnumSiNo.SI)) {
                String codigoPersonalizado = factura.getSucursal().getCodigoPersonalizado();
                if (codigoPersonalizado != null && !codigoPersonalizado.trim().isEmpty()) {
                    ComprobanteAdicional comprobanteAdicional = construirDatoAdicionalSinTransaccion(comprobante, "Código Personalizado", codigoPersonalizado);
                    comprobante.addDatoAdicional(comprobanteAdicional);
                }
            }
        }
        
    }
    
    private void agregarParametrosGenerales(ComprobanteEntity comprobante) throws RemoteException, ServicioCodefacException
    {
        ParametroCodefacService parametroService=new ParametroCodefacService();
        ParametroCodefac parametroCodefac=parametroService.getParametroByNombre(ParametroCodefac.VARIABLES_GENERAL_COMPROBANTES_ELECTRONICOS,comprobante.getEmpresa());
        if(parametroCodefac!=null && parametroCodefac.getValor()!=null && !parametroCodefac.getValor().isEmpty())
        {            
            agregarParametroComprobante(comprobante, parametroCodefac.getValor());
            
            //parametroCodefac.getValor();
        }
        //agregarParametrosConfiguracionEmpresa(comprobante);
    }
    
    /**
     * Agregar datos adiucionales que fueron configurados en la empresa como regimen impositivo y otras cosas
     * TODO: Metodo que ya no se va a usar por que esso datos van directamente en los xml
     */
    /*@Deprecated
    private void agregarParametrosConfiguracionEmpresa(ComprobanteEntity comprobante)
    {
        //Leyenda del regimen de microempresa
        EnumSiNo contribuyenteMicorempresas=comprobante.getEmpresa().getContribuyenteRegimenMicroempresasEnum();
        if(contribuyenteMicorempresas!=null && contribuyenteMicorempresas.equals(EnumSiNo.SI))
        {
            construirDatoAdicionalSinTransaccion(comprobante, "Contribuyente Régimen Microempresas"," ");
            //ComprobanteAdicional campoAdicional=new ComprobanteAdicional("Contribuyente Régimen Microempresas"," ", ComprobanteAdicional.Tipo.TIPO_OTRO);
            //comprobante.addDatoAdicional(campoAdicional);
        }
        
        //Leyenda de las personas que son agentes de retención
        String agenteRetencion=comprobante.getEmpresa().getAgenteRetencionResolucion();                
        if(agenteRetencion!=null && !agenteRetencion.trim().isEmpty())
        {
            String numeroRetencionStr[]=agenteRetencion.split("-");
            Integer numeroResolucion=Integer.parseInt(numeroRetencionStr[numeroRetencionStr.length-1]);
            construirDatoAdicionalSinTransaccion(comprobante, "Agente de Retención","No. Resolución: "+numeroResolucion);
            //ComprobanteAdicional campoAdicional=new ComprobanteAdicional("Agente de Retención","No. Resolución: "+numeroResolucion, ComprobanteAdicional.Tipo.TIPO_OTRO);
            //comprobante.addDatoAdicional(campoAdicional);
        }
        
        
        
    }*/
    
    private void agregarParametroComprobante(ComprobanteEntity comprobante,String txtJson)
    {
        Gson json = new Gson();
        Properties propiedades = json.fromJson(txtJson, Properties.class);
        for (Enumeration e = propiedades.keys(); e.hasMoreElements();) {
            // Obtenemos el objeto
            Object clave = e.nextElement();
            String valor = propiedades.getProperty(clave.toString());

            DocumentoEnum documentoEnum = comprobante.getCodigoDocumentoEnum();
            switch (documentoEnum) {
                
                case NOTA_VENTA_INTERNA:
                case FACTURA:
                    Factura factura = (Factura) comprobante;
                    FacturaAdicional datoAdicional = new FacturaAdicional();
                    datoAdicional.setCampo(clave.toString());
                    datoAdicional.setValor(valor);
                    datoAdicional.setTipoEnum(ComprobanteAdicional.Tipo.TIPO_OTRO);
                    datoAdicional.setFactura(factura);
                    factura.addDatoAdicional(datoAdicional);
                    break;

                case NOTA_VENTA:
                    //Factura factura=(Factura) comprobante;
                    //factura.addDatoAdicional((FacturaAdicional) comprobanteAdicional);
                    break;

                case RETENCIONES:
                    Retencion retencion = (Retencion) comprobante;
                    RetencionAdicional datoAdicional2 = new RetencionAdicional();
                    datoAdicional2.setCampo(clave.toString());
                    datoAdicional2.setValor(valor);
                    datoAdicional2.setTipoEnum(ComprobanteAdicional.Tipo.TIPO_OTRO);
                    datoAdicional2.setRetencion(retencion);
                    retencion.addDatoAdicional(datoAdicional2);

                    break;

                case NOTA_CREDITO:
                    NotaCredito notaCredito = (NotaCredito) comprobante;
                    NotaCreditoAdicional datoAdicional3 = new NotaCreditoAdicional();
                    datoAdicional3.setCampo(clave.toString());
                    datoAdicional3.setValor(valor);
                    datoAdicional3.setTipoEnum(ComprobanteAdicional.Tipo.TIPO_OTRO);
                    datoAdicional3.setNotaCredito(notaCredito);
                    notaCredito.addDatoAdicional(datoAdicional3);
                    break;

                case GUIA_REMISION:
                    GuiaRemision guiaRemision = (GuiaRemision) comprobante;
                    GuiaRemisionAdicional datoAdicional4 = new GuiaRemisionAdicional();
                    datoAdicional4.setCampo(clave.toString());
                    datoAdicional4.setValor(valor);
                    datoAdicional4.setTipoEnum(ComprobanteAdicional.Tipo.TIPO_OTRO);
                    datoAdicional4.setGuiaRemision(guiaRemision);
                    //guiaRemision.addDatoAdic(datoAdicional4); //Todo: Faclta setear valores para este caso
                    break;
            }

            //}
        }
    }

    /**
     * TODO: Cambiar el nombre del metodo a setear secuencial y otros datos adicionales
     * @param comprobante
     * @throws RemoteException
     * @throws ServicioCodefacException 
     */
    public void setearSecuencialComprobanteSinTransaccion(ComprobanteEntity comprobante) throws RemoteException, ServicioCodefacException
    {
        
        PuntoEmisionService puntoEmisionService=new PuntoEmisionService();
        
        PuntoEmision puntoEmision=puntoEmisionService.buscarPorId(comprobante.getPuntoEmisionId());
        //PuntoEmision puntoEmision=puntoEmisionService.obtenerPorCodigo(comprobante.getPuntoEmision(),comprobante.getSucursalEmpresa());
        
        if(puntoEmision==null)
        {
            throw new ServicioCodefacException("No existe definido un punto de emisión");
        }
        
        //Setea para saber si es facturacion fisica o electronica el código
        comprobante.setTipoFacturacion(puntoEmision.getTipoFacturacion());
        
        //Obtiene los secuenciales eletrónicos
        DocumentoEnum documentoEnum = comprobante.getCodigoDocumentoEnum();
        Integer secuencial=puntoEmision.getSecuencialPorDocumento(documentoEnum);
        validarSecuencial(secuencial);
        
        //Aumentaro el código de la númeracion en el punto de emisión
        puntoEmision.setSecuencialPorDocumento(documentoEnum, secuencial+1);        
             
        //Aumentar el código de la numeración en los parametros
        comprobante.setSecuencial(secuencial);
        
        //Validacion para evitar ingresar comprobantes repetidos        
        validarSecuencialRepetidoComprobante(comprobante,puntoEmision,true);
        
        //GRABAR los datos recien modificados
        entityManager.merge(puntoEmision);
        entityManager.flush();
        
        /**
         * ====================================================================
         *          DATOS ADICIONALES DE LA FACTURA
         * ====================================================================
         */
        
        //Agregar campos ocultos que no se deben mostrar en los detalles de las facturas electronicas
        //TODO: Este cambio se hizo para el señor Centeno que decia que no aparescan algunos detalles en la factura
        agregarEtiquetaDetallesCamposOcultos(comprobante);        
                
        //Agregado parametro generales en datos adicionales
        agregarParametrosGenerales(comprobante);
        
        //Agregando parametros adicionales por usuario a datos adicionales
        agregarParametrosPorUsuario(comprobante);
        
        //Agregado datos adicionales de los vendedores
        agregarDatosAdicionalVendedor(comprobante);
        
        //Agregando estado de los documentos dependiendo el tipo
        setearEstadoComoprobante(comprobante);
        
        //Agrega informacion adicional del sistema en este caso como el RIMPE
        setearOtrosDatosAdicionalesSistema(comprobante);
        
 
        
    }
    
    private void setearOtrosDatosAdicionalesSistema(ComprobanteEntity comprobante)
    {
       EnumSiNo rimpeEnumEmprendedoresSiNo=comprobante.getEmpresa().getRimpeEmprendedoresEnum();
       if(rimpeEnumEmprendedoresSiNo!=null && rimpeEnumEmprendedoresSiNo.equals(EnumSiNo.SI))
       {
           
           ComprobanteAdicional comprobanteAdicional=construirDatoAdicionalSinTransaccion(comprobante,"*Tipo","Contribuyente Régimen RIMPE");
           comprobante.addDatoAdicional(comprobanteAdicional);
           //agregarParametroComprobante(comprobante, parametroCodefac.getValor());           
       }      
       
       EnumSiNo rimpeEnumNegocioPopularSiNo=comprobante.getEmpresa().getRimpeNegociosPopularesEnum();
       if(rimpeEnumNegocioPopularSiNo!=null && rimpeEnumNegocioPopularSiNo.equals(EnumSiNo.SI))
       {
           
           ComprobanteAdicional comprobanteAdicional=construirDatoAdicionalSinTransaccion(comprobante,"*Tipo","Contribuyente Negocio Popular - Régimen RIMPE");
           if(comprobanteAdicional!=null)
           {
                comprobante.addDatoAdicional(comprobanteAdicional);
           }
           //agregarParametroComprobante(comprobante, parametroCodefac.getValor());           
       }      
        
    }
    
    private void setearEstadoComoprobante(ComprobanteEntity comprobante)
    {
        //Si el documento es nota de venta interna se supone que no son documentos autorizados
        //TODO: Esta de generalizar este caso para todos los documentos que no son legales para poner en estado sin autorizar
        if(comprobante.getCodigoDocumentoEnum().equals(DocumentoEnum.NOTA_VENTA_INTERNA))
        {
            comprobante.setEstadoEnum(ComprobanteEnumEstado.SIN_AUTORIZAR);
            return;
        }
        
        if(comprobante.getTipoFacturacionEnum().equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA))
        {
            comprobante.setEstadoEnum(ComprobanteEnumEstado.SIN_AUTORIZAR);
        }else if(comprobante.getTipoFacturacionEnum().equals(ComprobanteEntity.TipoEmisionEnum.NORMAL))
        {
            comprobante.setEstadoEnum(ComprobanteEnumEstado.AUTORIZADO);
        }
    
    }
   
    /**
     * TODO: Ver como se puede poner restriciones por ejemplo para no superar el limite de caracteres al agregar la etiqueta de Hidden
     * @param comprobante
     * @throws RemoteException
     * @throws ServicioCodefacException 
     */
    private void agregarEtiquetaDetallesCamposOcultos(ComprobanteEntity comprobante) throws RemoteException, ServicioCodefacException
    {
        DocumentoEnum documentoEnum = comprobante.getCodigoDocumentoEnum();
        switch(documentoEnum)
        {
            case FACTURA:
            case NOTA_VENTA_INTERNA:
            case NOTA_VENTA:
                Factura factura=(Factura) comprobante;
                for (FacturaDetalle detalle : factura.getDetalles()) {
                    ProductoService productoService=new ProductoService();
                    
                    switch(detalle.getTipoDocumentoEnum())
                    {
                        case LIBRE:
                        case INVENTARIO:
                            Producto producto=productoService.buscarPorId(detalle.getReferenciaId());
                            if(producto.getOcultarDetalleVentaEnum().equals(EnumSiNo.SI))
                            {
                                detalle.setDescripcion(detalle.getDescripcion()+" "+ParametrosSistemaCodefac.ETIQUETA_OCULTAR_DETALLE_FACTURA);
                                entityManager.merge(detalle);                                
                            }
                        break;
                    }
                    //productoService.
                }
 
                break;
        }
    }
    
    private void validarSecuencial(Integer secuencial) throws RemoteException, ServicioCodefacException
    {
        if(secuencial==null)
        {
            throw new ServicioCodefacException("Error,Secuencial sin valor Asignado");
        }
        
        if(secuencial<0)
        {
            throw new ServicioCodefacException("Error,Secuencial negativo invaido");
        }
        
    }
    
    
    private ComprobanteEntity consultarPorClaveAcceso(ClaveAcceso claveAcceso,Empresa empresa)
    {
        //claveAcceso
        
        DocumentoEnum documentoEnum=DocumentoEnum.obtenerPorComprobanteEnum(claveAcceso.getTipoComprobante());
        BigDecimal puntoEstablecimiento=new BigDecimal(claveAcceso.puntoEstablecimiento);
        Integer puntoEmision=Integer.parseInt(claveAcceso.puntoEmision);
        Integer secuencial=Integer.parseInt(claveAcceso.secuencial);
        
        ComprobanteEntityFacade facadeEntity=new ComprobanteEntityFacade();
        
        List<ComprobanteEntity> comprobantesEncontradoList=facadeEntity.validarSecuencialRepetidoComprobanteFacade(
                empresa, 
                documentoEnum, 
                puntoEstablecimiento, 
                puntoEmision, 
                secuencial
        );
        
        
        if(comprobantesEncontradoList.size()>0)
        {
            return comprobantesEncontradoList.get(0);
        }
        
        return null;
    }
    
    /**
     * 
     * @param comprobante
     * @return Boolean retorar true cuando existe un registro y false cuando no existe
     */
    private void validarSecuencialRepetidoComprobante(ComprobanteEntity comprobante,PuntoEmision puntoEmisionOriginal,Boolean forzarCorregir) throws RemoteException, ServicioCodefacException
    {
        Empresa empresa=comprobante.getEmpresa();
        DocumentoEnum documentoEnum=comprobante.getCodigoDocumentoEnum();
        BigDecimal puntoEstablecimiento=comprobante.getPuntoEstablecimiento();
        Integer puntoEmision=comprobante.getPuntoEmision();
        Integer secuencial=comprobante.getSecuencial();
        ComprobanteEntityFacade facadeEntity=new ComprobanteEntityFacade();
        
        List<ComprobanteEntity> resultado=facadeEntity.validarSecuencialRepetidoComprobanteFacade(
                empresa,
                documentoEnum, 
                puntoEstablecimiento, 
                puntoEmision,
                secuencial);
        
        if(resultado.size()>0)
        {
            ///////////////////////////////////////////////////////////////////
            //  CORRECION TEMPORAL PARA PROBLEMAS DE DESFAZ DE LOS SECUENCIALES
            //  TODO: Esta solución debe esar de forma temporal hasta ver poruqe
            //        se desfazan los puntos de emision
            ///////////////////////////////////////////////////////////////////
            if(forzarCorregir)
            {
                Integer secuencialCorregido=facadeEntity.getSecuencialUltimoFacade(
                        empresa, 
                        documentoEnum, 
                        puntoEstablecimiento, 
                        puntoEmision);

                //Aumentar secuencial corregido
                secuencialCorregido++;
                        
                puntoEmisionOriginal.setSecuencialPorDocumento(documentoEnum, secuencialCorregido+1);
                comprobante.setSecuencial(secuencialCorregido);            
                Logger.getLogger(ComprobantesService.class.getName()).log(Level.WARNING, null,"Corregido secuencial "+secuencial+" del Documento +"+documentoEnum.getNombre());
                validarSecuencialRepetidoComprobante(comprobante, puntoEmisionOriginal, false);
            }
            else
            {
                throw new ServicioCodefacException("Ya existe un comprobante con el preimpreso "+comprobante.getPreimpreso()+" ingresado");
            }
        }
        //return false;
    }
    
    public Integer getSecuencialUltimo(ComprobanteEntity comprobante) throws RemoteException, ServicioCodefacException
    {
        Empresa empresa=comprobante.getEmpresa();
        DocumentoEnum documentoEnum=comprobante.getCodigoDocumentoEnum();
        BigDecimal puntoEstablecimiento=comprobante.getPuntoEstablecimiento();
        Integer puntoEmision=comprobante.getPuntoEmision();
        Integer secuencial=comprobante.getSecuencial();
        ComprobanteEntityFacade facadeEntity=new ComprobanteEntityFacade();
        
        Integer resultado=facadeEntity.getSecuencialUltimoFacade(
                empresa,
                documentoEnum, 
                puntoEstablecimiento, 
                puntoEmision);
        
        if(resultado==0)
        {
            resultado=1;
        }
        return resultado;
        //return false;
    }
    
    
    /**
     * Por defecto este metodo busca primero el xml autorizado , si no encuentra devuelve el firmado
     * @param empresa
     * @param claveAcceso
     * @return
     * @throws RemoteException
     * @throws ServicioCodefacException 
     */
    public RemoteInputStream obtenerXmlFirmadoComprobante(Empresa empresa,String claveAcceso) throws RemoteException, ServicioCodefacException
    {
        try {
            File file=null;//archivo para mostrar
            ComprobanteElectronicoService comprobanteService=new ComprobanteElectronicoService();
            
            cargarConfiguraciones(comprobanteService,empresa);
            
            String pathXmlAutorizados=comprobanteService.getPathComprobanteConClaveAcceso(ComprobanteElectronicoService.CARPETA_AUTORIZADOS,claveAcceso);
            file=new File(pathXmlAutorizados);
            
            if(!file.exists())
            {
                String pathXml=comprobanteService.getPathComprobanteConClaveAcceso(ComprobanteElectronicoService.CARPETA_FIRMADOS,claveAcceso);
                file=new File(pathXml);
            }
            
            SimpleRemoteInputStream istream = new SimpleRemoteInputStream(new FileInputStream(file));
            return istream;
            
            //return UtilidadesRmi.serializar(file);
        } catch (IOException ex) {
            Logger.getLogger(ComprobantesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new ServicioCodefacException("Xml no disponible");
    }
    
    
    public boolean eliminarComprobanteFisico(String claveAcceso) throws RemoteException, ServicioCodefacException
    {
        ClaveAcceso claveAccesoObj = new ClaveAcceso(claveAcceso);
        EmpresaService empresaService = new EmpresaService();
        Empresa empresa = empresaService.buscarPorIdentificacion(claveAccesoObj.identificacion);
        
       ComprobanteElectronicoService comprobanteService=new ComprobanteElectronicoService();
       cargarConfiguraciones(comprobanteService,empresa);
       
       String pathCarpetaSinEnviar=comprobanteService.getPathComprobanteConClaveAcceso(ComprobanteElectronicoService.CARPETA_FIRMADOS_SIN_ENVIAR,claveAcceso);
       File file=new File(pathCarpetaSinEnviar);
       if (file.exists()) {
            file.delete();           
        }
       
       String pathCarpetaEnviados=comprobanteService.getPathComprobanteConClaveAcceso(ComprobanteElectronicoService.CARPETA_ENVIADOS_SIN_RESPUESTA,claveAcceso);
       file=new File(pathCarpetaEnviados);
       if (file.exists()) {
            file.delete();           
        }
       
       return true;
       
    }
    
    public boolean eliminarComprobantesFisico(String claveAcceso,String carpeta) throws RemoteException, ServicioCodefacException {
        
        ClaveAcceso claveAccesoObj = new ClaveAcceso(claveAcceso);
        EmpresaService empresaService = new EmpresaService();
        Empresa empresa = empresaService.buscarPorIdentificacion(claveAccesoObj.identificacion);

        ComprobanteElectronicoService comprobanteService = new ComprobanteElectronicoService();
        cargarConfiguraciones(comprobanteService,empresa);

        String pathCarpetaSinEnviar = comprobanteService.getPathComprobanteConClaveAcceso(carpeta, claveAcceso);
        File file = new File(pathCarpetaSinEnviar);
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;

    }
    
    @Deprecated //TODO:No se esta usando
    public void actualizarComprobanteDatos(List<ComprobanteEntity> entidades) throws RemoteException, ServicioCodefacException
    {
        //Si no hay ningun dato para procesar no hago nada
        if(entidades.size()==0)
            return;
        
        EmpresaService empresaService = new EmpresaService();
        Empresa empresa = empresaService.buscarPorIdentificacion(entidades.get(0).getEmpresa().getIdentificacion());

        ComprobanteElectronicoService comprobanteService = new ComprobanteElectronicoService();
        cargarConfiguraciones(comprobanteService, empresa);
            
        for (ComprobanteEntity entidad : entidades) {
            //Busacar el comprobante en la carpeta de los autorizados
            if (entidad.getClaveAcceso() != null) {
                String pathXmlAutorizado = comprobanteService.getPathComprobanteConClaveAcceso(ComprobanteElectronicoService.CARPETA_AUTORIZADOS, entidad.getClaveAcceso());
                ComprobanteElectronicoAutorizado autorizado = new ComprobanteElectronicoAutorizado();
                
                //Si no se puede construir el objeto xml no se hace ninguna otra accion
                if(autorizado.construirDesdeArchivo(pathXmlAutorizado)==false)
                {
                    LOG.log(Level.WARNING,"No se puede actualizar el comprobante porque no existe el xml autorizado con clave de acceso: "+entidad.getClaveAcceso());
                    continue;
                }

                java.sql.Date fechaAutorizado=autorizado.obtenerFecha();
                if(fechaAutorizado!=null)
                {
                    entidad.setFechaAutorizacionSri(fechaAutorizado);
                }

                ComprobanteEntity.TipoAmbienteEnum enumAmbiente = ComprobanteEntity.TipoAmbienteEnum.buscarPorNombreSri(autorizado.getAmbiente());
                if (enumAmbiente != null) {
                    entidad.setTipoAmbiente(enumAmbiente.getLetra());
                }
                
                //Cambiar el estado si el anterior es eliminado a elminado desde el Sri
                if(entidad.getEstadoEnum().equals(ComprobanteEnumEstado.ELIMINADO))
                {
                    entidad.setEstadoEnum(ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO_SRI);
                }

                ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                    @Override
                    public void transaccion() throws ServicioCodefacException, RemoteException {
                        entityManager.merge(entidad);
                    }
                });

            }
        }
        
    }
    
    public void consultarDocumentoAutorizado()
    {
    
    }

    public boolean eliminarComprobanteFisico(String claveAcceso,String carpeta) throws RemoteException, ServicioCodefacException {
        
        ClaveAcceso claveAccesoObj = new ClaveAcceso(claveAcceso);
        EmpresaService empresaService = new EmpresaService();
        Empresa empresa = empresaService.buscarPorIdentificacion(claveAccesoObj.identificacion);

        ComprobanteElectronicoService comprobanteService = new ComprobanteElectronicoService();
        cargarConfiguraciones(comprobanteService,empresa);

        String pathCarpetaSinEnviar = comprobanteService.getPathComprobanteConClaveAcceso(carpeta, claveAcceso);
        File file = new File(pathCarpetaSinEnviar);
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;

    }
    
    public void editar(ComprobanteEntity comprobante) throws RemoteException, ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entityManager.merge(comprobante);
            }
        });
    }
    
    
    public List<String> solucionarProblemasEnvioComprobante(String carpetaActual,String claveAcceso,Empresa empresa) throws RemoteException, ServicioCodefacException
    {
        ComprobanteElectronicoService service=crearComprobanteEletronico(empresa);
        if (carpetaActual.equals(ComprobanteElectronicoService.CARPETA_FIRMADOS_SIN_ENVIAR)) 
        {
            service.copiarComprobantesElectronicos(claveAcceso, carpetaActual, ComprobanteElectronicoService.CARPETA_ENVIADOS_SIN_RESPUESTA);
            
            Map<String,List<String>> mapComprobante=new HashMap<String,List<String>>();
            mapComprobante.put(claveAcceso,new ArrayList<String>());            
            List<String> errores=procesarComprobantesPendienteLote(
                    ComprobanteElectronicoService.ETAPA_AUTORIZAR, 
                    99999, 
                    mapComprobante, 
                    false,
                    empresa);
            
            if(service.verificarExisteArchivo(claveAcceso, ComprobanteElectronicoService.CARPETA_ENVIADOS_SIN_RESPUESTA))
            { 
                //Si el archivo no se proceso elimino de la carpeta temporal
                service.eliminarComprobanteElectronico(claveAcceso, ComprobanteElectronicoService.CARPETA_ENVIADOS_SIN_RESPUESTA);
            }
            else
            {
                //Si el archivo ya se proceso y no existe en la carpeta temporal elimino tambien de la carpeta original
                service.eliminarComprobanteElectronico(claveAcceso, ComprobanteElectronicoService.CARPETA_FIRMADOS_SIN_ENVIAR);
            }
                       
            return errores;
            
        } else if (carpetaActual.equals(ComprobanteElectronicoService.CARPETA_ENVIADOS_SIN_RESPUESTA)) 
        {
            service.copiarComprobantesElectronicos(claveAcceso, carpetaActual, ComprobanteElectronicoService.CARPETA_FIRMADOS_SIN_ENVIAR);
            
            Map<String,List<String>> mapComprobante=new HashMap<String,List<String>>();
            mapComprobante.put(claveAcceso,new ArrayList<String>());            
            List<String> errores=procesarComprobantesPendienteLote(
                    ComprobanteElectronicoService.ETAPA_ENVIAR, 
                    99999, 
                    mapComprobante, 
                    false,
                    empresa);            
           
            service.eliminarComprobanteElectronico(claveAcceso, ComprobanteElectronicoService.CARPETA_FIRMADOS_SIN_ENVIAR);
            
            return errores;
        } else 
        {
            //TODO: Metodo no implementado
        }
        return new ArrayList<String>();
        
    }
    
    
    private ComprobanteElectronicoService crearComprobanteEletronico(Empresa empresa)
    {
        ComprobanteElectronicoService comprobanteElectronico= new ComprobanteElectronicoService();
        cargarConfiguraciones(comprobanteElectronico, empresa);
        return comprobanteElectronico;
        
    }
    
    private ComprobanteEntity consultarPorAutorizacion(String claveAcceso)
    {
        //ComprobanteEntity comprobante;
        //comprobante.getClaveAcceso();
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("claveAcceso",claveAcceso);
        
        List<ComprobanteEntity> comprobantes=getFacade().findByMap(mapParametros);
        if(comprobantes.size()>0)
        {
            return comprobantes.get(0);
        }
        return null;
        
    }
    
    /**
     * TODO:Falta agregar para liquidaciones de compra
     * @param comprobante
     * @param campo
     * @param valor
     * @return 
     */
    public ComprobanteAdicional construirDatoAdicionalSinTransaccion(ComprobanteEntity comprobante,String campo,String valor)
    {
        DocumentoEnum documentoEnum = comprobante.getCodigoDocumentoEnum();
            switch (documentoEnum) {
                case FACTURA:
                    Factura factura = (Factura) comprobante;
                    FacturaAdicional datoAdicional = new FacturaAdicional();
                    datoAdicional.setCampo(campo);
                    datoAdicional.setValor(valor);
                    datoAdicional.setTipoEnum(ComprobanteAdicional.Tipo.TIPO_OTRO);
                    datoAdicional.setFactura(factura);
                    factura.addDatoAdicional(datoAdicional);
                    return datoAdicional;

               

                case RETENCIONES:
                    Retencion retencion = (Retencion) comprobante;
                    RetencionAdicional datoAdicional2 = new RetencionAdicional();
                    datoAdicional2.setCampo(campo);
                    datoAdicional2.setValor(valor);
                    datoAdicional2.setTipoEnum(ComprobanteAdicional.Tipo.TIPO_OTRO);
                    datoAdicional2.setRetencion(retencion);
                    retencion.addDatoAdicional(datoAdicional2);
                    return datoAdicional2;

                case NOTA_CREDITO:
                    NotaCredito notaCredito = (NotaCredito) comprobante;
                    NotaCreditoAdicional datoAdicional3 = new NotaCreditoAdicional();
                    datoAdicional3.setCampo(campo);
                    datoAdicional3.setValor(valor);
                    datoAdicional3.setTipoEnum(ComprobanteAdicional.Tipo.TIPO_OTRO);
                    datoAdicional3.setNotaCredito(notaCredito);
                    notaCredito.addDatoAdicional(datoAdicional3);
                    return datoAdicional3;

                case GUIA_REMISION:
                    GuiaRemision guiaRemision = (GuiaRemision) comprobante;
                    GuiaRemisionAdicional datoAdicional4 = new GuiaRemisionAdicional();
                    datoAdicional4.setCampo(campo);
                    datoAdicional4.setValor(valor);
                    datoAdicional4.setTipoEnum(ComprobanteAdicional.Tipo.TIPO_OTRO);
                    datoAdicional4.setGuiaRemision(guiaRemision);
                    //guiaRemision.addDatoAdic(datoAdicional4); //Todo: Faclta setear valores para este caso
                    return datoAdicional4;
            }
            return null;

    }


}
