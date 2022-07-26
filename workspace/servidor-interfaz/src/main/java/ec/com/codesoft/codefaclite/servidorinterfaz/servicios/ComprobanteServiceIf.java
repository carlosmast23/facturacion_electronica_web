/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import com.healthmarketscience.rmiio.RemoteInputStream;
import ec.com.codesoft.codefaclite.facturacionelectronica.AlertaComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobanteLote;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataFactura;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author
 */
public interface ComprobanteServiceIf extends Remote {
    
    public void autorizarComprobante(ComprobanteEntity comprobanteElectronica) throws RemoteException,ServicioCodefacException;
    
    public boolean procesarComprobantesLotePendiente(Integer etapaInicial,Integer etapaLimite,List<String> clavesAcceso,List<ComprobanteDataInterface> comprobantesProcesos,String ruc,ClienteInterfaceComprobanteLote callbackClientObject,Boolean enviarCorreo,Empresa empresa,Boolean sincrono) throws RemoteException;
    
    public void procesarComprobanteOffline(ComprobanteDataInterface comprobanteData,Factura factura,Usuario usuario,ClienteInterfaceComprobante callbackClientObject) throws RemoteException ;
    
    //public Integer obtenerSecuencialFacturaYAvanzar() throws RemoteException;
    
    public boolean verificarCredencialesFirma(String claveFirma,Empresa empresa) throws RemoteException;

    public boolean procesarComprobantesPendiente(Integer etapaInicial,Integer etapaLimite,String claveAcceso, List<String> correos,ClienteInterfaceComprobante callbackClientObject,Boolean enviarCorreo,Boolean asincrono,Empresa empresa) throws RemoteException;
    
    @Deprecated
    public List<AlertaComprobanteElectronico> procesarComprobantesPendienteSinCallBack(Integer etapaInicial,Integer etapaLimite,String claveAcceso, List<String> correos,Empresa empresa) throws RemoteException,ServicioCodefacException;
    
    //public List<ComprobanteElectronico> getComprobantesObjectByFolder(String carpetaConfiguracion) throws RemoteException;
    
    public List<ComprobanteElectronico> getComprobantesObjectByFolder(String carpetaConfiguracion,Empresa empresa) throws RemoteException;
    
    public Integer getComprobantesObjectByFolderCantidad(String carpetaConfiguracion,Empresa empresa) throws RemoteException;
    
    public byte[] getReporteComprobante(String claveAcceso,Empresa empresa) throws RemoteException;

    public void procesarComprobante(ComprobanteDataInterface comprobanteData,ComprobanteEntity comprobante, Usuario usuario,ClienteInterfaceComprobante callbackClientObject) throws RemoteException;

    public void registerForCallback(ClienteInterfaceComprobante callbackClientObject) throws java.rmi.RemoteException;

    public void unregisterForCallback(ClienteInterfaceComprobante callbackClientObject) throws java.rmi.RemoteException;
    
    /**
     * Este metodo ya no se esta usando
     * @deprecated 
     * @param comprobantesData
     * @param usuario
     * @param ruc
     * @param callbackClientObject
     * @throws RemoteException 
     */
    //public void procesarComprobanteLote(List<ComprobanteDataInterface> comprobantesData,Usuario usuario,String ruc,ClienteInterfaceComprobanteLote callbackClientObject) throws RemoteException;
    
    public boolean verificarDisponibilidadSri(Empresa empresa) throws RemoteException;
    
    public RemoteInputStream obtenerXmlFirmadoComprobante(Empresa empresa,String claveAcceso) throws RemoteException, ServicioCodefacException;
    
    public void eliminarComprobanteSinTransaccion(ComprobanteEntity comprobante) throws RemoteException,ServicioCodefacException;
    
    public boolean eliminarComprobanteFisico(String claveAcceso) throws RemoteException, ServicioCodefacException;
    public boolean eliminarComprobanteFisico(String claveAcceso,String carpeta) throws RemoteException, ServicioCodefacException;
    
    /**
     * Metodo que permite actualizar documentos que no esten clasificados como autorizados
     * @param entidades
     * @throws RemoteException
     * @throws ServicioCodefacException 
     */
    public void actualizarComprobanteDatos(List<ComprobanteEntity> entidades) throws RemoteException, ServicioCodefacException;
    
    /**
     * Metodo que permite enviar varios comprobante pero desde la forma normal es decir de uno en uno
     * Nota: Esto lo utilizo para mandar a procesar datos desde la etapa de enviados porque no se puede enviar desde la seccion de lotes
     * @param etapaInicial
     * @param etapaLimite
     * @param mapClaveAccesoYCorreos
     * @param enviarCorreo
     * @throws RemoteException
     * @throws ServicioCodefacException 
     */
    public List<String> procesarComprobantesPendienteLote(Integer etapaInicial,Integer etapaLimite,Map<String,List<String>> mapClaveAccesoYCorreos,Boolean enviarCorreo,Empresa empresa) throws RemoteException,ServicioCodefacException;
    
    
    public void editar(ComprobanteEntity comprobante) throws RemoteException, ServicioCodefacException;
    
    public List<String> solucionarProblemasEnvioComprobante(String carpetaActual,String claveAcceso,Empresa empresa) throws RemoteException, ServicioCodefacException;
    
    public Integer getSecuencialUltimo(ComprobanteEntity comprobante) throws RemoteException, ServicioCodefacException;
    
    public void generarRideComprobanteNoLegal(ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity comprobante,ComprobanteDataInterface comprobanteData,Usuario usuario) throws RemoteException;
    
    public byte[] getReporteComprobanteComprobante(ComprobanteDataInterface comprobanteData,Usuario usuario,String claveAccesoPersonalizada) throws RemoteException;
    
    
}
