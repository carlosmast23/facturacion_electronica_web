/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PerfilUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidor.facade.PerfilFacade;
import ec.com.codesoft.codefaclite.servidor.facade.PerfilUsuarioFacade;
import ec.com.codesoft.codefaclite.servidor.facade.UsuarioFacade;
import ec.com.codesoft.codefaclite.servidor.util.UtilidadesServidor;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmisionUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.EmpresaLicencia;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ModoSistemaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.FechaMaximoPagoRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.LoginRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesHash;
import ec.com.codesoft.codefaclite.ws.codefac.test.service.WebServiceCodefac;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author
 */
public class UsuarioServicio extends ServiceAbstract<Usuario,UsuarioFacade> implements UsuarioServicioIf{

    private static final Logger LOG = Logger.getLogger(UsuarioServicio.class.getName());
    
    UsuarioFacade usuarioFacade=new UsuarioFacade();
    PerfilUsuarioFacade perfilUsuarioFacade=new PerfilUsuarioFacade();
    PerfilFacade perfilFacade=new PerfilFacade();
    

    public UsuarioServicio() throws RemoteException {
        super(UsuarioFacade.class);
        this.usuarioFacade=new UsuarioFacade();
    }    
    
    /**
     * Metodo que permite validar si primero existen problemas con la licencia del, cliente
     */
    private boolean validacionLicencia(EmpresaLicencia empresaLicencia,LoginRespuesta loginRespuesta) throws RemoteException, ServicioCodefacException
    {
                
        //Si la licencia es distinta de correcta termino el Login y aviso al usuario final
        loginRespuesta.alertas = empresaLicencia.alertas;
        //Si la comprobacion de la licencia es incorrecta devuel el ERROR
        if (!empresaLicencia.estadoEnum.equals(empresaLicencia.estadoEnum.LICENCIA_CORRECTA)) {
            loginRespuesta.estadoEnum = empresaLicencia.estadoEnum;
            return false;
        }
        return true;
            
    }
    
    /**
     * 
     * @return Devuelve verdadero si no tiene deudas o no tienes deudas vencidas
     */
    private Boolean validarDeudasSistema(Empresa empresa,EmpresaLicencia empresaLicencia,LoginRespuesta loginRespuesta)
    {
        FechaMaximoPagoRespuesta respuestaPago = verificarFechaMaximaPago(empresaLicencia.usuarioLicencia, empresa);
        if (respuestaPago.estadoEnum.equals(respuestaPago.estadoEnum.FECHA_PAGO_SUPERADA)) 
        {
            loginRespuesta.estadoEnum = loginRespuesta.estadoEnum.PAGOS_PENDIENTES;
            //return loginRespuesta;
            return false;
        } else if (respuestaPago.estadoEnum.equals(respuestaPago.estadoEnum.PROXIMO_PAGO_CERCA)) //Validacion solo para emitir una alerta de la fecha de pago 
        {
            if (loginRespuesta.alertas == null) {
                loginRespuesta.alertas = Arrays.asList(respuestaPago.mensajePagoCerca());
            } else {
                loginRespuesta.alertas.addAll(Arrays.asList(respuestaPago.mensajePagoCerca()));
            }
        }
        return true;
    }
    
    /**
     * Metodo que me permite verificar si el usuario puede acceder al sistema tanto por credenciales o por otro motivo
     * @param nick
     * @param clave
     * @param empresa
     * @return
     * @throws java.rmi.RemoteException
     * @throws ServicioCodefacException 
     */
    public LoginRespuesta login(String nick,String clave,Empresa empresa) throws java.rmi.RemoteException,ServicioCodefacException
    {
        LoginRespuesta loginRespuesta=new LoginRespuesta();
        
        /////////==========> VALIDAR LA LICENCIA DE LA EMPRESA PARA VER SI TIENE PERMISO PARA ABRIR EL SISTEMA <==========//////
               
        //Solo hago la validación cuando ya tiene creado la empresa porque la primera vez debe dejar ingresar al sistema para que puedan configurar los datos principales
        if(empresa!=null)
        {
            UtilidadesService servicioUtilidades=new UtilidadesService();
            EmpresaLicencia empresaLicencia = servicioUtilidades.obtenerLicenciaEmpresa(empresa);
            //Si no logro validar la licencia devuelvo la respuesta que fue agregado en el metodo validacionLicencia
            if(!validacionLicencia(empresaLicencia, loginRespuesta))
            {
                return loginRespuesta;
            }
           
            //=========> VALIDAR QUE NO TENGA DEUDAS EN EL SISTEMA PARA SEGUIR USANDO <================================///
            //Si tiene deudas que no permite abrir el sistema devuelvo el ERROR que fue agregado en el metodo loginRespuesta
            if(!validarDeudasSistema(empresa, empresaLicencia, loginRespuesta))
            {
                return loginRespuesta;
            }
        }
              
        
        ////////===========> VALIDACION DE LOS USUARIOS DE LA EMPRESA <=================================================== /////            
        if(!nick.equals("") && !clave.equals(""))
        {
            /**
             * Procedeimiento para buscar usuarios de diferentes tipos configuracion ,root, usuarios del sistema
             * el proceso en orden de validación es:
             * 1.- Validar si es un usurio ROOT
             * 2.- Validar si es un usurio de Configuracio
             * 3.- Validar cualquier usuario creado en el sistema
             */
            
            //Consultando variable de la cantidad de usuarios para validacion para el usuarios admin de configuracion inicial
            UsuarioServicio usuarioService = new UsuarioServicio();
            Integer cantidadUsuariosActivos = usuarioService.obtenerCantidadUsuariosActivosPorEmpresa(empresa);

            
            //Validacion para verificar si no es un usuario root es decir para soporte
            if(nick.toLowerCase().indexOf("root")>=0) //Si contiene la palabra root asumo que es de soporte
            {
                //Consultar el usuario root
                Map<String, Object> mapParametros = new HashMap<String, Object>();
                mapParametros.put("nick", Usuario.SUPER_USUARIO);
                Usuario usuarioRoot = null; //variable para consultar la variable root
                try {
                    UsuarioServicio usuarioServicio=new UsuarioServicio();
                    usuarioRoot = usuarioServicio.obtenerPorMap(mapParametros).get(0);//obtiene el usuario root de la base de datos 
                    usuarioRoot.isRoot = true;
                    usuarioRoot.setEmpresa(empresa); //Seteo con el nombre de la empresa que vayan a usar
                } catch (RemoteException ex) {
                    Logger.getLogger(UsuarioServicio.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(UsuarioServicio.class.getName()).log(Level.SEVERE, null, ex);
                }

                //Cuando esta en modo produccion para el root consulto desde los web services
                if(ParametrosSistemaCodefac.MODO.equals(ModoSistemaEnum.PRODUCCION)) 
                {
                    if(WebServiceCodefac.getVerificarSoporte(nick, clave))
                    {                            
                            //usuario=usuarioRoot;
                            LOG.log(Level.INFO, "Ingresando con el usuario root: "+nick);
                            loginRespuesta.usuario=usuarioRoot;
                            loginRespuesta.estadoEnum=LoginRespuesta.EstadoLoginEnum.CORRECTO_USUARIO;
                            //setVisible(false);
                    }
                    else
                    {
                        LOG.log(Level.WARNING, "Error al ingresar con el usuario: " + nick);     
                        loginRespuesta.estadoEnum=LoginRespuesta.EstadoLoginEnum.INCORRECTO_USUARIO;
                    }
                }
                else
                {
                    //Seteo directemente una clave independiente de lo que este grabado en la base, ademas como solo es para modo desarrollo no afecta para el desarrollo
                    if(clave.equals("1234")) //Todo: ver si se setea esta variable de forma global aunque no hay necesidad
                    {
                        //usuario=usuarioRoot;
                        LOG.log(Level.INFO, "Ingresando con el usuario root en produccion: " + nick);
                        loginRespuesta.usuario=usuarioRoot;
                        loginRespuesta.estadoEnum=LoginRespuesta.EstadoLoginEnum.CORRECTO_USUARIO;
                        //setVisible(false);
                    }
                    else
                    {
                        loginRespuesta.estadoEnum=LoginRespuesta.EstadoLoginEnum.INCORRECTO_USUARIO;
                        //DialogoCodefac.mensaje("Error Login","Datos Incorrectos para root en modo desarrollo",DialogoCodefac.MENSAJE_INCORRECTO);
                    }

                }
                    
            }
            //Validacion cuando no tiene usuarios para ver si tiene que ingresar con el usuario Admin
            else if(cantidadUsuariosActivos == 0)
            {
                //Validacion para el usuario de configuracion admin
                if (clave.toLowerCase().equals(ParametrosSistemaCodefac.CREDENCIALES_USUARIO_CONFIGURACION)) 
                {
                    Map<String, Object> mapParametros = new HashMap<String, Object>();
                    mapParametros.put("nick", Usuario.SUPER_USUARIO);
                    List<Usuario> resultados = getFacade().findByMap(mapParametros);
                    LOG.log(Level.INFO, "Ingresando con usuario de configuracion");
                    loginRespuesta.estadoEnum = LoginRespuesta.EstadoLoginEnum.CORRECTO_USUARIO;
                    loginRespuesta.usuario = resultados.get(0);
                } 
                else 
                {
                    //Cuando el usuario ingresa por primera vez y no sabe las credenciales mando la advertencia
                    loginRespuesta.estadoEnum = LoginRespuesta.EstadoLoginEnum.ERROR_INGRESO_PRIMERA_VEZ;
                    return loginRespuesta;
                }
            }
            else //Validacion para usuarios normales que no son root
            {            
                //usuario=usuarioServicio.login(usuarioTxt,clave);
                Usuario usuario=verificarCredencialesUsuario(nick, clave,empresa);
                if(usuario!=null)
                {
                    loginRespuesta.usuario=usuario;
                    if(usuario.getEstadoEnum().equals(GeneralEnumEstado.ACTIVO))
                    {
                        LOG.log(Level.INFO, "Ingresando con el usuario: "+nick);
                        loginRespuesta.estadoEnum=LoginRespuesta.EstadoLoginEnum.CORRECTO_USUARIO;
                    }else if(usuario.getEstadoEnum().equals(GeneralEnumEstado.INACTIVO))
                    {
                        LOG.log(Level.INFO, "Error Usuario inactivo: "+nick);                        
                        loginRespuesta.estadoEnum=LoginRespuesta.EstadoLoginEnum.INACTIVO_USUARIO;
                    }else if(usuario.getEstadoEnum().equals(GeneralEnumEstado.ELIMINADO))
                    {
                        LOG.log(Level.INFO, "Error Usuario eliminado: "+nick);                        
                        loginRespuesta.estadoEnum=LoginRespuesta.EstadoLoginEnum.USUARIO_ELIMINADO;
                    }
                }
                else
                {
                    LOG.log(Level.WARNING, "Error al ingresar con el usuario: "+nick);                    
                    loginRespuesta.estadoEnum=LoginRespuesta.EstadoLoginEnum.INCORRECTO_USUARIO;
                }
            }

        }

        return loginRespuesta;        
    }
    
    private Usuario verificarCredencialesUsuario(String nick,String clave,Empresa empresa)
    {
        Usuario usaurio;
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("nick",nick);
        mapParametros.put("empresa",empresa);
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        
        List<Usuario> usuarios=usuarioFacade.findByMap(mapParametros);
        
        if(usuarios.size()>0)
        {
            if(UtilidadesHash.verificarHashBcrypt(clave,usuarios.get(0).getClave()))
            {
                return usuarios.get(0);
            }
        }
        
        return null;
    }
    
    public void eliminar(Usuario entity) throws java.rmi.RemoteException {
        EntityTransaction transaccion=getTransaccion();
        transaccion.begin();
        entity.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
        entityManager.merge(entity);
        transaccion.commit();
    }
    
    public Usuario cambiarClave(Usuario usuario,String claveAnterior,String claveNueva) throws java.rmi.RemoteException, ServicioCodefacException
    {
        //TODO: Complentar la validacion completa validando con el usuario guardado y el nuevo usuario para ver si los datos coinciden
        //Actualizo las referencia del nuevo objecto a editar
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() {
                String claveHash=UtilidadesHash.generarHashBcrypt(claveNueva);
                usuario.setClave(claveHash);
                entityManager.merge(usuario);
                entityManager.flush();
            }
        });
        //usuario.setClave("Modificando clave");
        System.out.println(usuario);
        return usuario;

    }
    
    
    public void editar(Usuario entity) throws ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                //EntityTransaction transaccion = getTransaccion();
                //transaccion.begin();

                Usuario usuarioOriginal = getFacade().find(entity.getId());

                //Verificar que no sea el usuario root el quieren editar
                //usuariosActivos.size() > 0 && ParametrosSistemaCodefac.MODO.equals(ModoSistemaEnum.PRODUCCION)
                if (usuarioOriginal.getNick().equals(Usuario.SUPER_USUARIO) && ParametrosSistemaCodefac.MODO.equals(ModoSistemaEnum.PRODUCCION )) {
                    throw new ServicioCodefacException("El usuario root no se puede editar");

                }

                ///Funcionalidad que permite eliminar perfiles que fueron eliminados
                if (usuarioOriginal.getPerfilesUsuario() != null) {
                    for (PerfilUsuario perfilUsuario : usuarioOriginal.getPerfilesUsuario()) {

                        //Si en el nuevo objeto que mando a editar no contiene el perfil usuario lo elimino
                        if (!entity.getPerfilesUsuario().contains(perfilUsuario)) {
                            //Elimino de la persistencia
                            entityManager.remove(perfilUsuario);
                        }

                    }
                }

                //Funcionalidad que permite agregar nuevos perfiles agregados
                if (entity.getPerfilesUsuario() != null) {
                    for (PerfilUsuario perfilUsuario : entity.getPerfilesUsuario()) {

                        //Si en el objeto origina no tiene el perfil lo agrego
                        if (!usuarioOriginal.getPerfilesUsuario().contains(perfilUsuario)) {
                            //Elimino de la persistencia
                            entityManager.persist(perfilUsuario);
                        }

                    }
                }
                
                /**
                 * =========METODOS PARA AGREGAR Y QUITAR PUNTOS DE EMISION
                 */
                ///Funcionalidad que permite eliminar perfiles que fueron eliminados
                /*if (usuarioOriginal.getPuntosEmisionUsuario()!= null) {
                    for (PuntoEmisionUsuario puntoEmisionUsuario : usuarioOriginal.getPuntosEmisionUsuario()) {

                        //Si en el nuevo objeto que mando a editar no contiene el perfil usuario lo elimino
                        if (!entity.getPuntosEmisionUsuario().contains(puntoEmisionUsuario)) {
                            //Elimino de la persistencia
                            puntoEmisionUsuario.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                            entityManager.merge(puntoEmisionUsuario);
                            //entityManager.remove(puntoEmisionUsuario);
                        }

                    }
                }*/

                //Funcionalidad que permite agregar nuevos perfiles agregados
                if (entity.getPuntosEmisionUsuario() != null) {
                    for (PuntoEmisionUsuario puntoEmisionUsuario : entity.getPuntosEmisionUsuario()) {

                        //Si en el objeto origina no tiene el perfil lo agrego
                        if (!usuarioOriginal.getPuntosEmisionUsuario().contains(puntoEmisionUsuario)) {
                            //Elimino de la persistencia
                            entityManager.persist(puntoEmisionUsuario);
                        }
                        else //Si existe el dato lo actualizo porciacaso se haya cambiado el estado por ejemplo eliminado
                        {
                            entityManager.merge(puntoEmisionUsuario);
                        }

                    }
                }

                //Actualizo el objeto editado
                entityManager.merge(entity);
                //Actualizo las referencia del nuevo objecto a editar
                //entity.setClave(UtilidadesHash.generarHashBcrypt(entity.getClave()));
                //entityManager.merge(entity);
                //transaccion.commit();
            }
        });
        
    }
    
    public Usuario grabar(Usuario entity) throws ServicioCodefacException,java.rmi.RemoteException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                grabarSinTransaccion(entity,true);
            }
        });
                
        return entity;
    
    }
    
    public void grabarSinTransaccion(Usuario entity,Boolean validarConLicencia) throws ServicioCodefacException,java.rmi.RemoteException
    {
        if(validarConLicencia)
        {
            if (UtilidadesServidor.mapEmpresasLicencias.get(entity.getEmpresa()).tipoLicencia.equals(TipoLicenciaEnum.GRATIS)) {
                Map<String, Object> mapParametros = new HashMap<String, Object>();
                mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
                mapParametros.put("empresa", entity.getEmpresa());
                List<Usuario> usuariosActivos = obtenerPorMap(mapParametros);
                if (usuariosActivos.size() > 0 && ParametrosSistemaCodefac.MODO.equals(ModoSistemaEnum.PRODUCCION)) {
                    throw new ServicioCodefacException("En la licencia gratuita solo puede crear 1 usuario \n Si desea mas usuarios necesita una licencia PREMIUN");
                }
            }
        }

        entity.setClave(UtilidadesHash.generarHashBcrypt(entity.getClave())); //Cifrar la clave para que no puede ser legible 
        entityManager.persist(entity);

        //Grabar los puntos de emision asigandos al usuario
        if (entity.getPuntosEmisionUsuario() != null) {
            for (PuntoEmisionUsuario puntoEmisionUsuario : entity.getPuntosEmisionUsuario()) {
                if (puntoEmisionUsuario.getId() == null) {
                    entityManager.persist(puntoEmisionUsuario);
                }
            }
        }

    }
    
    @Deprecated //Todo este metodo ya esta definido en guardar
    public void grabarUsuario(Usuario usuario,String nombrePerfil) throws ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                usuario.setClave(UtilidadesHash.generarHashBcrypt(usuario.getClave()));
                entityManager.persist(usuario);
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("nombre", nombrePerfil);
                List<Perfil> perfilesList = perfilFacade.findByMap(parametros);
                Perfil perfil = null;

                if (perfilesList.size() > 0) { //Por defecto esta seteando el primer perfil que encuentra, revisar este tema 
                    perfil = perfilesList.get(0);
                }

                PerfilUsuario perfilUsuario = new PerfilUsuario();
                perfilUsuario.setUsuario(usuario);
                perfilUsuario.setPerfil(perfil);
                perfilUsuario.setFechaCreacion(new java.sql.Date(new Date().getTime()));

                entityManager.persist(perfilUsuario);

                //Actualizar la referencia del usuario
                usuario.addPerfilUsuario(perfilUsuario);
                if(perfil!=null)
                {
                    entityManager.merge(perfil);
                }
            }
        });
       
    }
    
    public Usuario consultarUsuarioActivoPorEmpresa(String nick,Empresa empresa) throws ServicioCodefacException,java.rmi.RemoteException
    {
        Map<String, Object> mapParametros = new HashMap<String, Object>();        
        mapParametros = new HashMap<String, Object>();
        mapParametros.put("nick", "root");
        //mapParametros.put("empresa",empresa); TODO: Terminar de implementar esta funcionalidad
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        //UsuarioServicioIf usuarioServiceIf = ServiceFactory.getFactory().getUsuarioServicioIf();
        List<Usuario> usuarios=getFacade().findByMap(mapParametros);
        if(usuarios.size()>0)
        {
            return usuarios.get(0);
        }
        return null;
    }
    
    private static FechaMaximoPagoRespuesta verificarFechaMaximaPago(String usuario,Empresa empresa) {
        FechaMaximoPagoRespuesta respuesta=new FechaMaximoPagoRespuesta();
        
        Date fechaLimite = WebServiceCodefac.obtenerFechaLimitePago(usuario);

        if (fechaLimite != null) {
            int diasFaltantes = UtilidadesFecha.obtenerDistanciaDias(UtilidadesFecha.getFechaHoy(), fechaLimite);
            respuesta.diasRestantes=diasFaltantes;
            respuesta.estadoEnum=FechaMaximoPagoRespuesta.EstadoEnum.VALORES_PENDIENTES;

            Integer diasPreviosAlerta=7; //Por defecto si no tienen algun valor se muestran las fechas de pago con 7 dias
            String diasPagoParametro=ParametroUtilidades.obtenerValorParametro(empresa, ParametroCodefac.DIAS_ALERTA_PAGO);
            if(diasPagoParametro!=null && !diasPagoParametro.isEmpty())
            {
                diasPreviosAlerta=Integer.parseInt(diasPagoParametro);
            }
            
            //diasFaltantes=diasFaltantes+1; //Le sumo un digito porqe la distancia me devuelve con un numero menos TODO: revisar esta parte
            if (diasFaltantes <= 0) {//Validacion cuando ya no tenga dias de espera ya no permite acceder al sistema
                //DialogoCodefac.mensaje("Error", "El sistema detecta valores pendientes de pago y no se puede abrir\n Por favor cancele los valores pendientes para continuar con el servicio.", DialogoCodefac.MENSAJE_INCORRECTO);
                //System.exit(0);
                respuesta.estadoEnum=FechaMaximoPagoRespuesta.EstadoEnum.FECHA_PAGO_SUPERADA;
            } else if (diasFaltantes <= diasPreviosAlerta) {
                //DialogoCodefac.mensaje("Advertencia", "El sistema registra valores pendientes por cancelar , le restan " + diasFaltantes + " días para usar el sistema,\n Si no cancela los valores pendientes el sistema automáticamente se bloqueará .", DialogoCodefac.MENSAJE_ADVERTENCIA);
                respuesta.estadoEnum=FechaMaximoPagoRespuesta.EstadoEnum.PROXIMO_PAGO_CERCA;
            }
        }
        else
        {
            //TODO: Mejorar este metodo porque el error de sin valores pendientes le puede salir porque no tiene internet
            respuesta.estadoEnum=FechaMaximoPagoRespuesta.EstadoEnum.SIN_VALORES_PENDIENTES; 
        }
        return respuesta;

    }
    
    public Integer obtenerCantidadUsuariosActivosPorEmpresa(Empresa empresa) throws ServicioCodefacException,java.rmi.RemoteException
    {
        //TODO: Optimizar para obtener directamente por Query
        //Usuario u;
        //u.getEmpresa();
        //u.getEstado();
        Map<String,Object> mapParametros=new HashMap<String,Object>();
        mapParametros.put("empresa",empresa);
        mapParametros.put("estado", GeneralEnumEstado.ACTIVO.getEstado());
        
        List<Usuario> resultadoList=getFacade().findByMap(mapParametros);
        return resultadoList.size();
    }
    
    @Deprecated
    //TODO: Al parecer este metodo no se usa por que realmente no se tiene un usuario en la base de datos, solo es temporal
    // Analizar si debo borrar
    public Usuario obtenerUsuarioConfiguracion() throws ServicioCodefacException,java.rmi.RemoteException
    {
        //Usuario u;
        //u.get
        //Usuario usuario=null;
        Map<String,Object> mapParametro=new HashMap<String,Object>();
        mapParametro.put("nick",ParametrosSistemaCodefac.CREDENCIALES_USUARIO_CONFIGURACION);
                
        List<Usuario> resultados=getFacade().findByMap(mapParametro);
        if(resultados.size()>0)
        {
            resultados.get(0);
        }
        else
        {            
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException, RemoteException {
                    Usuario usuario=new Usuario();
                    usuario.setNick(ParametrosSistemaCodefac.CREDENCIALES_USUARIO_CONFIGURACION);
                    usuario.setClave(ParametrosSistemaCodefac.CREDENCIALES_USUARIO_CONFIGURACION);
                    usuario.setEstadoEnum(GeneralEnumEstado.ACTIVO);     
                    entityManager.persist(usuario);
                }
            });
            
            return getFacade().findByMap(mapParametro).get(0);
        }
        return null;
    }
      
    
}
