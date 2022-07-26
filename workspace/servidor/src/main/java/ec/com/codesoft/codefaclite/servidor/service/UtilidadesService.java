/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.licence.NoExisteLicenciaException;
import ec.com.codesoft.codefaclite.licence.ValidacionLicenciaCodefac;
import ec.com.codesoft.codefaclite.licence.ValidacionLicenciaExcepcion;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidor.facade.UtilidadFacade;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesServidor;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.EmpresaLicencia;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ModoSistemaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.Licencia;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.DashBoardData;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ReportDataAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.LoginRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.ValidacionRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UtilidadesServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesCodigos;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author
 */
public class UtilidadesService extends UnicastRemoteObject implements UtilidadesServiceIf {

    public UtilidadesService() throws RemoteException {
        super(ParametrosSistemaCodefac.PUERTO_COMUNICACION_RED);
    }

    //TODO: Verificar porque no esta funcionando este metodo
    public Object mergeEntity(Object entity) throws java.rmi.RemoteException {
        return AbstractFacade.entityManager.merge(entity);
    }

    public List<Object> consultaGeneralDialogos(String query, Map<Integer, Object> map, int limiteMinimo, int limiteMaximo) throws java.rmi.RemoteException {
        return AbstractFacade.findStaticDialog(query, map, limiteMinimo, limiteMaximo);
    }

    public Long consultaTamanioGeneralDialogos(String query, Map<Integer, Object> map) throws java.rmi.RemoteException {
        return AbstractFacade.findCountStaticDialog(query, map);
    }
    
    public Properties crearLicencia(Empresa empresa,Licencia licencia) throws RemoteException,ServicioCodefacException
    {
        ParametroCodefacService servicio=new ParametroCodefacService();
        String pathBase=servicio.getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS, empresa).getValor();
        ValidacionLicenciaCodefac validacion = new ValidacionLicenciaCodefac(pathBase);
        return validacion.crearLicenciaMaquina(licencia);
    }
    
    public Properties crearLicencia(Empresa empresa,Licencia licencia,String pathBase) throws RemoteException,ServicioCodefacException
    {
        //ParametroCodefacService servicio=new ParametroCodefacService();
        //String pathBase=servicio.getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS, empresa).getValor();
        ValidacionLicenciaCodefac validacion = new ValidacionLicenciaCodefac(pathBase);
        return validacion.crearLicenciaMaquina(licencia);
    }
    
    public Properties crearLicenciaDescargada(Empresa empresa,Licencia licencia) throws RemoteException,ServicioCodefacException
    {
        ParametroCodefacService servicio=new ParametroCodefacService();
        String pathBase=servicio.getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS, empresa).getValor();
        return crearLicenciaDescargada(empresa, licencia, pathBase);
    }
    
    public Properties crearLicenciaDescargada(Empresa empresa,Licencia licencia,String pathBase) throws RemoteException,ServicioCodefacException
    {
        ValidacionLicenciaCodefac validacion = new ValidacionLicenciaCodefac(pathBase);
        return validacion.crearLicenciaDescargada(licencia);
    }
    

    @Override
    public boolean verificarConexionesServidor(Empresa empresa) throws RemoteException {
        int numeroConexionesPermitidas = 1;

        //Solo verificar los numero se conexiones para usuarios preimun , cuando es gratis siempre solo va a permitir 1 conexion
        if (UtilidadesServidor.mapEmpresasLicencias.get(empresa).tipoLicencia.equals(TipoLicenciaEnum.PRO)) {
            numeroConexionesPermitidas = UtilidadesServidor.mapEmpresasLicencias.get(empresa).cantidadUsuarios;
        }

        try {
            String hostCliente = RemoteServer.getClientHost();

            if (UtilidadesServidor.hostConectados.contains(hostCliente)) {
                return true; //Existe en la lista de usuarios permitidos                
            } else if (UtilidadesServidor.hostConectados.size() < numeroConexionesPermitidas) {
                UtilidadesServidor.hostConectados.add(hostCliente);
                if (UtilidadesServidor.monitorUpdate != null) {

                    String[] stockArr = new String[UtilidadesServidor.hostConectados.size()];
                    stockArr = UtilidadesServidor.hostConectados.toArray(stockArr);
                    UtilidadesServidor.monitorUpdate.actualizarNumeroConexiones(stockArr);

                }
                return true;
            }

            //Si ya supera el numero de conexiones permitidas retorno falso
            return false;
        } catch (ServerNotActiveException ex) {
            Logger.getLogger(UtilidadesService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public TipoLicenciaEnum getTipoLicencia(Empresa empresa) throws RemoteException {
        return UtilidadesServidor.mapEmpresasLicencias.get(empresa).tipoLicencia;
    }

    @Override
    public List<ModuloCodefacEnum> getModulosSistema(Empresa empresa) throws RemoteException {
        return UtilidadesServidor.mapEmpresasLicencias.get(empresa).modulosSistema;
    }
    

    /**
     * TODO: Verificar por que no setear directamente la empresa
     * @param empresa
     * @return
     * @throws RemoteException 
     */
    @Override
    public SessionCodefac getSessionPreConstruido(Empresa empresa) throws RemoteException {
        SessionCodefac session = new SessionCodefac();
        session.setEmpresa(empresa);
        
        if(empresa==null) //Si no tiene seleccionado construyo una session en blanco , esto es util para ingresar por primera vez al sistema
        {
            session.setTipoLicenciaEnum(TipoLicenciaEnum.GRATIS);
            List<ModuloCodefacEnum> modulosCodefacPorDefecto=new ArrayList<ModuloCodefacEnum>();
            modulosCodefacPorDefecto.add(ModuloCodefacEnum.SISTEMA);
            session.setModulos(modulosCodefacPorDefecto);
            
            return session;
        }
       
        
        TipoLicenciaEnum tipoLicencia = UtilidadesServidor.mapEmpresasLicencias.get(empresa).tipoLicencia;
        session.setTipoLicenciaEnum(tipoLicencia);
        session.setModulos(UtilidadesServidor.mapEmpresasLicencias.get(empresa).modulosSistema);
        session.setUsuarioLicencia(UtilidadesServidor.mapEmpresasLicencias.get(empresa).usuarioLicencia);
        
        return session;
    }
    
    public EmpresaLicencia obtenerLicenciaEmpresa(Empresa empresa) throws RemoteException,ServicioCodefacException
    {
        //Verificar si tengo que validar la licencia o solo devolver los datos grabados en memoria
        if(UtilidadesServidor.mapEmpresasLicencias.get(empresa)!=null)
        {
            EmpresaLicencia empresaLicencia=UtilidadesServidor.mapEmpresasLicencias.get(empresa);
            if(empresaLicencia.fechaUltimaValidacion.compareTo(UtilidadesFecha.getFechaHoy())==0)
            {
                return empresaLicencia; //Si existe la licencia grabada previamente y la fecha es la del mismo dia devuelvo los datos del proxi
            }
        }
        
        
        EmpresaLicencia empresaLicencia=new EmpresaLicencia();
        
        
        ParametroCodefacService servicio=new ParametroCodefacService();
        ParametroCodefac parametroDirectorio=servicio.getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS, empresa);
        if(parametroDirectorio==null)
        {
            empresaLicencia.estadoEnum=LoginRespuesta.EstadoLoginEnum.NO_EXISTE_DIRECTORIO_LICENCIA;
            return empresaLicencia;
        }
        
        String pathBase=servicio.getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS, empresa).getValor();
        /**
         * Realizar Analisis para verificar si existe la licencia instalada
         */
        if (!comprobarLicencia(pathBase)) {
            //throw new ServicioCodefacException("Error al validar las licencias");
            empresaLicencia.estadoEnum=LoginRespuesta.EstadoLoginEnum.LICENCIA_DESACTUALIZADA;
            return empresaLicencia;
            //System.exit(0);
        } else {

            //Buscar el tipo de licencia para setear en el sistema
            ValidacionLicenciaCodefac validacion = new ValidacionLicenciaCodefac(pathBase);
            TipoLicenciaEnum tipoLicencia = validacion.getLicencia().getTipoLicenciaEnum();

            //Esta validacion es solo para usuario premium para cuando no paguen y tengamos que disminuir la licencia
            if (!TipoLicenciaEnum.GRATIS.equals(tipoLicencia)) {
                ValidacionRespuesta validacionResp=validacionCodefacOnline(validacion,empresa);
                
                //Verifico que si tiene un problema la licencia retorno falso y muestros las alertas
                empresaLicencia.alertas=validacionResp.alertas;
                if(!validacionResp.estadoEnum.equals(LoginRespuesta.EstadoLoginEnum.LICENCIA_CORRECTA))
                {
                    empresaLicencia.estadoEnum=validacionResp.estadoEnum;                    
                    return empresaLicencia;
                }
                    
                validacion = new ValidacionLicenciaCodefac(pathBase);
                tipoLicencia = validacion.getLicencia().getTipoLicenciaEnum();
            }

            //Este valor seteo para que sea accesible desde el servidor
            //TODO: Verficar si se puede mejorar esta linea de codigo
            empresaLicencia.tipoLicencia=tipoLicencia;
            empresaLicencia.modulosSistema=validacion.getLicencia().getModulosSistema();
            empresaLicencia.cantidadUsuarios=Integer.parseInt(validacion.obtenerLicencia().getString(Licencia.PROPIEDAD_CANTIDAD_CLIENTES));
            empresaLicencia.usuarioLicencia=validacion.obtenerLicencia().getString(Licencia.PROPIEDAD_USUARIO);
            
            ParametroCodefacService servicioParametros=new ParametroCodefacService();
            empresaLicencia.pathEmpresa=servicioParametros.getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS, empresa).valor;
            
            empresaLicencia.fechaUltimaValidacion=UtilidadesFecha.getFechaHoy(); //Obtiene la fecha cuando se valido la licencia
            UtilidadesServidor.mapEmpresasLicencias.put(empresa, empresaLicencia); //Seteo el valor de la empresa con la ultima validacion
            empresaLicencia.estadoEnum=LoginRespuesta.EstadoLoginEnum.LICENCIA_CORRECTA;
            
        }
        return empresaLicencia;
    }
    
    private static boolean comprobarLicencia(String pathBase) throws ServicioCodefacException{
        
        //Si esta en modo desarrollo omito el tema de comprobar la licencias
        if(ParametrosSistemaCodefac.MODO.equals(ModoSistemaEnum.DESARROLLO))
        {
            return true;
        }

        
        ValidacionLicenciaCodefac validacion = new ValidacionLicenciaCodefac();
        validacion.setPath(pathBase);
        
        Boolean existeLicencia=false;

        if (validacion.verificarExisteLicencia()) {
            try {
                if (validacion.validar()) {
                    return true;
                } else {//Si la licencia es incorrecta abre el dialogo de verificacion
                    //DialogoCodefac.mensaje("Error", "No se puede validar la licencia, Posibles causas:\n - La licencia esta desactualizada \n - El archivo de la licencia fue modificado", DialogoCodefac.MENSAJE_INCORRECTO);
                    existeLicencia=true;
                }
            } catch (ValidacionLicenciaExcepcion ex) {
                Logger.getLogger(UtilidadesService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoExisteLicenciaException ex) {
                Logger.getLogger(UtilidadesService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }  //Cuando no existe la licencia

        return false;
    }
    
    public static ValidacionRespuesta validacionCodefacOnline(ValidacionLicenciaCodefac validacion,Empresa empresa) throws ServicioCodefacException {

        ValidacionRespuesta validacionRespuesta=new ValidacionRespuesta();
        
        /**
         * Dias Limite para verificar la licencia en ese periodo de tiempo
         */
        int diasLimiteVerificacion=14;
        /**
         * Numero de dias antes de empezar a verificar la licencia
         */
        int diasToleraciaVerificacion=5;
        
        try {
            ParametroCodefacServiceIf servicio = ServiceFactory.getFactory().getParametroCodefacServiceIf();
            /**
             * Verificar si la licencia actual es la misma que tiene el servidor
             */
            ParametroCodefac parametroFechaValidacion = servicio.getParametroByNombre(ParametroCodefac.ULTIMA_FECHA_VALIDACION,empresa);
            if (parametroFechaValidacion != null && !parametroFechaValidacion.getValor().equals("")) {
                //String fechaStr = parametroFechaValidacion.getValor();
                String fechaStr = UtilidadesEncriptar.desencriptar(parametroFechaValidacion.getValor(),ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
                System.out.println(fechaStr);
                if (!fechaStr.equals("")) {
                    try {
                        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                        Date fechaUltimaRevision = formato.parse(fechaStr);
                        
                        Date fechaRevisar=UtilidadesFecha.hoy(); //Por feecto compara con la hora del sistema
                        try
                        {
                            fechaRevisar=UtilidadesFecha.getFechaNTP(); //Si no existe internet hace el calculo con la hora del sistema
                        }
                        catch(java.lang.NoSuchMethodError nse)
                        {
                            nse.printStackTrace();
                        } catch (Exception ex) {
                            Logger.getLogger(UtilidadesService.class.getName()).log(Level.SEVERE, null, ex);
                        }
                                
                        
                        int dias = UtilidadesFecha.obtenerDistanciaDias(fechaUltimaRevision, fechaRevisar);//Esta fecha 

                        //Validacion para evitar que cambien fechas del sistema o que corrompan la fecha poniendo una fecha superior
                        if (dias < 0) {
                            //throw new ServicioCodefacException("No se puede validar su licencia ,inconsistencia con las fechas");
                            validacionRespuesta.estadoEnum=LoginRespuesta.EstadoLoginEnum.LICENCIA_ERROR_FECHA_INCONSISTENTE;
                            return validacionRespuesta;
                        }

                        //Revisar la licencia cada 5 dias con un rango maximo de 14 dias 
                        if (dias > diasToleraciaVerificacion && dias < diasLimiteVerificacion) {
                            if (verificarLicenciaOnline(validacion)) {
                                grabarFechaRevision(parametroFechaValidacion,empresa,false);
                            }
                            else
                            {
                                validacionRespuesta.estadoEnum=LoginRespuesta.EstadoLoginEnum.LICENCIA_CORRECTA;
                                String mensajeAdvertencia="Le quedan "+(diasLimiteVerificacion-dias)+" días para verificar su licencia por internet. \n\nCausas: \n - No tiene conexion a internet por varios días \n - Esta usando una versión  ilegal \n\n Si el problema persiste comuníquese con un asesor\n Nota: Si no soluciona el problema pasado la fecha limite el programa yo no funcionara";
                                validacionRespuesta.alertas=Arrays.asList(mensajeAdvertencia);
                                //TODO: Pendiente como devolver advertencias al cliente
                                //DialogoCodefac.mensaje("Advertencia", "Le quedan "+(diasLimiteVerificacion-dias)+" días para verificar su licencia por internet. \n\nCausas: \n - No tiene conexion a internet por varios días \n - Esta usando una versión  ilegal \n\n Si el problema persiste comuníquese con un asesor\n Nota: Si no soluciona el problema pasado la fecha limite el programa yo no funcionara" , dias);
                            }
                        }

                        //Si execede los dias limite sin validar por internet ya no permite el acceso
                        if (dias >= diasLimiteVerificacion) {
                            if (verificarLicenciaOnline(validacion)) {
                                grabarFechaRevision(parametroFechaValidacion,empresa, false);
                            } else {
                                //Si no se logro validar la licencia durante 30 dias ya no se abre el software
                                //throw new ServicioCodefacException("No se puede validar su licencia , verifique su conexión a internet");                                
                                validacionRespuesta.estadoEnum=LoginRespuesta.EstadoLoginEnum.LICENCIA_ERROR_FECHAS;
                                return validacionRespuesta;
                            }
                        }

                    } catch (ParseException ex) {
                        Logger.getLogger(UtilidadesService.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    if (verificarLicenciaOnline(validacion)) //no se pone en un if, porque esta controlado en el metodo si no existe salir
                    {
                        grabarFechaRevision(parametroFechaValidacion,empresa, false);
                    } else {
                        //Si no se logro validar la licencia por primera vez  no se abre el software
                        //throw new ServicioCodefacException("No se puede validar su licencia , verifique su conexión a internet");    
                        validacionRespuesta.estadoEnum=LoginRespuesta.EstadoLoginEnum.LICENCIA_ERROR_INTERNET;
                        return validacionRespuesta;
                        //DialogoCodefac.mensaje("Error", "No se puede validar su licencia , verifique su conexión a internet", DialogoCodefac.MENSAJE_INCORRECTO);
                        //System.exit(0);

                    }
                }

            } else //cuando no se tiene registro de la fecha de validacion
            {
                if (verificarLicenciaOnline(validacion)) {
                    grabarFechaRevision(parametroFechaValidacion,empresa, true);
                } else {
                    //Si no se logro validar la licencia por primera vez  no se abre el software
                    //throw new ServicioCodefacException("No se puede validar su licencia , verifique su conexión a internet");    
                    validacionRespuesta.estadoEnum=LoginRespuesta.EstadoLoginEnum.LICENCIA_ERROR_INTERNET;
                    return validacionRespuesta;
                    //DialogoCodefac.mensaje("Error", "No se puede validar su licencia , verifique su conexión a internet", DialogoCodefac.MENSAJE_INCORRECTO);
                    //System.exit(0);
                }

            }
        } catch (RemoteException ex) {
            Logger.getLogger(UtilidadesService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UtilidadesService.class.getName()).log(Level.SEVERE, null, ex);

        }
        
        validacionRespuesta.estadoEnum=LoginRespuesta.EstadoLoginEnum.LICENCIA_CORRECTA;
        return validacionRespuesta;
    }
    
    public static boolean verificarLicenciaOnline(ValidacionLicenciaCodefac validacion) throws ServicioCodefacException 
    {
            //Si esta en modo desarrollo omito el tema de comprobar la licencias
            if (ParametrosSistemaCodefac.MODO.equals(ModoSistemaEnum.DESARROLLO)) {
                return true;
            }            
            
            String usuario=validacion.obtenerLicencia().getString(Licencia.PROPIEDAD_USUARIO);
            Licencia licenciaOnline=new Licencia();
            
            try
            {
                licenciaOnline.cargarLicenciaOnline(usuario);
            }catch(Exception e)
            {
                //TODO: Si exite un problema al consultar la licencia en internet retorno falso
                return false;
            }
            
            
            Licencia licenciaFisica=new Licencia();
            licenciaFisica.cargarPropiedadesLicenciaFisica(validacion.obtenerLicencia());


            if(licenciaOnline.compararOtraLicencia(licenciaFisica))
            {
                return true;
            }
            else //Si existe diferencias con la otra licencia lanza el dialogo para actualizar la licencia
            {                
                
                throw new ServicioCodefacException("Su licencia esta desactualizada o es incorrecta. \n Por favor actualice su licencia para continuar");
                /*ValidarLicenciaModel licenciaDialog = new ValidarLicenciaModel(null, true, true);
                licenciaDialog.setValidacionLicenciaCodefac(validacion);
                if (validacion.verificarConexionInternet()) {
                    licenciaDialog.setVisible(true);
                    if (licenciaDialog.licenciaCreada) {
                        return comprobarLicencia(validacion.getPath()); //volver a verificar la licencia
                    } else {
                        return false;
                    }
                } else {
                    DialogoCodefac.mensaje("Error", "Para activar su producto conéctese a Internet", DialogoCodefac.MENSAJE_INCORRECTO);
                    return false;
                }*/

            }            

    }
    
    /**
     * Funcion que graba la fecha de la ultima revision de la licencia y me
     * permite hacer un control para evitar que el softare funcione sin
     * verificar alteraciones en su licencia o cuando se requiera cambiar el
     * tipo de licencia por ejemplo cuando no realiza los pagos de la licencia
     *
     * @param parametroFechaValidacion
     * @param crear
     */
    private static void grabarFechaRevision(ParametroCodefac parametroFechaValidacion,Empresa empresa ,boolean crear) {
        try {
            if (crear) {
                parametroFechaValidacion = new ParametroCodefac();
                parametroFechaValidacion.setNombre(ParametroCodefac.ULTIMA_FECHA_VALIDACION);
                parametroFechaValidacion.setEmpresa(empresa);
            }

            ParametroCodefacService servicio = new ParametroCodefacService();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            
            Date fechaRevisar = UtilidadesFecha.hoy(); //Por feecto compara con la hora del sistema
            try {
                fechaRevisar = UtilidadesFecha.getFechaNTP(); //Si no existe internet hace el calculo con la hora del sistema
            } catch (java.lang.NoSuchMethodError nse) {
                nse.printStackTrace();
            } catch (Exception ex) {
                Logger.getLogger(UtilidadesService.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String dateStr=format.format(fechaRevisar);
            parametroFechaValidacion.setValor(UtilidadesEncriptar.encriptar(dateStr,ParametrosSistemaCodefac.LLAVE_ENCRIPTAR));
            if (crear) {
                servicio.grabar(parametroFechaValidacion);
            } else {
                servicio.editar(parametroFechaValidacion);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(UtilidadesService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UtilidadesService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Integer obtenerCodigoMaximoPorId(String nombreTabla,String nombreCampoPk) throws RemoteException,ServicioCodefacException
    {
        UtilidadFacade utilidadFacade=new UtilidadFacade();
        return utilidadFacade.obtenerCodigoMaximoPorId(nombreTabla, nombreCampoPk);
    }
    
    public String crearCodigoPorEmpresaYSucursalSinTransaccion(Sucursal sucursal,String codigoDocumento,String nombreTabla) throws RemoteException,ServicioCodefacException
    {
       // final String SEPARADOR_CODIGO="-";
        
        String codigoEmpresa=sucursal.getEmpresa().getCodigo();
        String codigoSucursal=sucursal.getCodigo();
        
        if(codigoEmpresa==null || codigoEmpresa.isEmpty())
        {
            throw new ServicioCodefacException("No tiene código de la empresa");
        }
        
        if(codigoSucursal==null || codigoSucursal.isEmpty())
        {
            throw new ServicioCodefacException("No tiene código de la sucursal");
        }
        
        String prefijo=UtilidadesCodigos.generarPrefijo(codigoEmpresa, codigoSucursal, codigoDocumento,ParametrosSistemaCodefac.CARACTER_SEPARACION_CODIGO);
        
        UtilidadFacade utilidadFacade=new UtilidadFacade();
        Integer numeracionNueva=utilidadFacade.obtenerCodigoMaximo(prefijo, nombreTabla);
        return UtilidadesCodigos.generarFormatoCodigo(prefijo,numeracionNueva,ParametrosSistemaCodefac.TAMANIO_CODIGOS);
        
    }
    
    public void verficarConsistenciaTabla(String nombreTabla) throws RemoteException,ServicioCodefacException
    {
        AbstractFacade.consultarConsistenciaTabla(nombreTabla);
    }
    
    
    public ReportDataAbstract<DashBoardData> consultarDashboard(Date fechaInicio,Date fechaFin) throws RemoteException,ServicioCodefacException
    {
        ReportDataAbstract<DashBoardData> reporte=new ReportDataAbstract<DashBoardData>("DashBoard") {
            @Override
            public String[] getTitulos() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void construirFilaTabla(DashBoardData dato, Vector<Object> fila) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        //Agregar parametros
        reporte.agregarParametro("ventasDiarias", "130");
        
        DashBoardData dashBoardData=new DashBoardData();
                
        DashBoardData.DashboardProductoTopData producto1=new DashBoardData.DashboardProductoTopData("Producto 1", "10");
        DashBoardData.DashboardProductoTopData producto2=new DashBoardData.DashboardProductoTopData("Producto 2", "10");
        DashBoardData.DashboardProductoTopData producto3=new DashBoardData.DashboardProductoTopData("Producto 3", "10");
        
        List<DashBoardData.DashboardProductoTopData> listaDetalle=new ArrayList<DashBoardData.DashboardProductoTopData>();
        
        listaDetalle.add(producto1);
        listaDetalle.add(producto2);
        listaDetalle.add(producto3);
        
        reporte.agregarParametro("productosList",listaDetalle);
        
        
        
        reporte.agregarDetalle(dashBoardData);
        
        return reporte;
    
    }


}
