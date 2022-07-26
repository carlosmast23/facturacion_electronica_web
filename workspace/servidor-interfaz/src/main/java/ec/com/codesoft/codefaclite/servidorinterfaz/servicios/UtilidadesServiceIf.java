/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.Licencia;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.EmpresaLicencia;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.DashBoardData;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ReportDataAbstract;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author
 */
public interface UtilidadesServiceIf extends Remote
{
    /**
     * Metodo que me permite sincronizar con la persistencia el objecto actual
     * @param entity
     * @return
     * @throws java.rmi.RemoteException 
     */
    public Object mergeEntity(Object entity) throws java.rmi.RemoteException;
    public List<Object> consultaGeneralDialogos(String query, Map<Integer, Object> map, int limiteMinimo, int limiteMaximo) throws java.rmi.RemoteException;
    public Long consultaTamanioGeneralDialogos(String query, Map<Integer, Object> map) throws java.rmi.RemoteException;
    public boolean verificarConexionesServidor(Empresa empresa) throws java.rmi.RemoteException;
    public TipoLicenciaEnum getTipoLicencia(Empresa empresa) throws java.rmi.RemoteException;
    public List<ModuloCodefacEnum> getModulosSistema(Empresa empresa)  throws RemoteException;
    /**
     * Me devuelve un objeto session con algunos datos preconstruidos
     * @return
     * @throws RemoteException 
     */
    public SessionCodefac getSessionPreConstruido(Empresa empresa)  throws RemoteException;
    
    public EmpresaLicencia obtenerLicenciaEmpresa(Empresa empresa) throws RemoteException,ServicioCodefacException;
    
    public Properties crearLicencia(Empresa empresa,Licencia licencia) throws RemoteException,ServicioCodefacException;
    
    public Properties crearLicenciaDescargada(Empresa empresa,Licencia licencia) throws RemoteException,ServicioCodefacException;
    
    public Properties crearLicenciaDescargada(Empresa empresa,Licencia licencia,String pathBase) throws RemoteException,ServicioCodefacException;
    
    public Properties crearLicencia(Empresa empresa,Licencia licencia,String pathBase) throws RemoteException,ServicioCodefacException;
    
    public void verficarConsistenciaTabla(String nombreTabla) throws RemoteException,ServicioCodefacException;
    
    public Integer obtenerCodigoMaximoPorId(String nombreTabla,String nombreCampoPk) throws RemoteException,ServicioCodefacException;
    
    public ReportDataAbstract<DashBoardData> consultarDashboard(Date fechaInicio,Date fechaFin) throws RemoteException,ServicioCodefacException;
    
}
