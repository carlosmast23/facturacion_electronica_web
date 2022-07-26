/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidor.facade.PerfilFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PermisoVentana;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CategoriaMenuEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ModoSistemaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.MenuCodefacRespuesta;
import java.rmi.RemoteException;
import java.util.List;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PerfilServiceIf;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;
//import javax.swing.JMenu;
//import javax.swing.JMenuItem;

/**
 *
 * @author
 */
public class PerfilService extends ServiceAbstract<Perfil,PerfilFacade> implements PerfilServiceIf{
    private static final Logger LOG = Logger.getLogger(PerfilService.class.getName());

    private PerfilFacade perfilFacade;
    public PerfilService() throws RemoteException 
    {
        super(PerfilFacade.class);
        this.perfilFacade=new PerfilFacade();
    }
    
    
    public Perfil grabar(Perfil entity) throws ServicioCodefacException,java.rmi.RemoteException
    {
        EntityTransaction transaction=getTransaccion();
        transaction.begin();
        entity.setEstado(GeneralEnumEstado.ACTIVO.getEstado());
        entityManager.persist(entity);
        transaction.commit();
        return entity;
    }
    
    public void eliminar(Perfil entity) throws java.rmi.RemoteException
    {
        EntityTransaction transaction=getTransaccion();
        transaction.begin();        
        entity.setEstado(GeneralEnumEstado.ELIMINADO.getEstado());
        entityManager.merge(entity);
        transaction.commit();
    }
    
    public void editar(Perfil entity)
    {
        try {
            EntityTransaction transaction=getTransaccion();
            transaction.begin();
            
            Perfil perfilSinModificar = buscarPorId(entity.getId());
            for (PermisoVentana permisoVentana : perfilSinModificar.getVentanasPermisos()) {
                //Comprabar si algun objeto fue eliminado
                if (!entity.getVentanasPermisos().contains(permisoVentana)) {
                    entityManager.remove(permisoVentana);
                }
            }            
            
            entityManager.merge(entity);
            transaction.commit();
        } catch (RemoteException ex) {
            Logger.getLogger(PerfilService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Perfil> obtenerPerfilesPorUsuario(Usuario usuario)
    {
        return this.perfilFacade.getPerfilesByUsuario(usuario);
    }
    
    /**
     * Funcion utilizada para que al momento de crear una empresa ya tenga perfil para que pueda asignar a los usuarios
     */
    public Perfil crearPerfilPorDefectoSinTransaccion(Empresa empresa) throws ServicioCodefacException
    {        
        try {
            Perfil perfilDefecto=new Perfil();
            perfilDefecto.setDescripcion("perfil por defecto");
            perfilDefecto.setEmpresa(empresa);
            perfilDefecto.setEstadoEnum(GeneralEnumEstado.ACTIVO);
            perfilDefecto.setNombre(Perfil.PERFIL_DEFECTO);
            
            entityManager.persist(perfilDefecto);
            
            PermisoVentanaService permisoService=new PermisoVentanaService();
            List<PermisoVentana> ventanas=new ArrayList<PermisoVentana>();
            
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.CLIENTE.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.ASISTENTE_CONFIGURACION.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.SUCURSAL.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.PUNTO_EMISION.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.PROVEEDOR.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.PRODUCTO.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.IMPRIMIR_CODIGO_BARRAS.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.FACTURACION.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.FACTURACION_PEDIDO_LOTE.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.EMPRESA.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.COMPROBANTE_CONFIGURACION.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.UTILIDAD_COMPROBANTE.getCodigo()));            
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.NOTA_CREDITO.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.FACTURA_REPORTE.getCodigo()));   
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.CLIENTE_REPORTE.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.PROVEEDOR_REPORTE.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.PRODUCTO_REPORTE.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.FACTURA_DISENIO.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.COMPRA.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.COMPRA_REPORTE.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.COMPRA_REPORTE_PRODUCTO.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.BODEGA.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.CATEGORIA_PRODUCTO.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.KARDEX.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.PERFILES.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.SQL_CONSOLA.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.USUARIOS.getCodigo()));            
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.RESPALDAR_INFORMACION.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.CONFIGURACION_DEFECTO.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.DEPARTAMENTO.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.EMPLEADO.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.PROFORMA_VENTA.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.PROFORMA_REPORTE.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.GESTION_INVENTARIO.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.STOCK_MINIMO.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.STOCK_REPORTE.getCodigo()));
            ventanas.add(permisoService.crearPermisoVentanaConTodosPermisoSinTransaccion(perfilDefecto,VentanaEnum.REFERIDO_REPORTE.getCodigo())); 
            
            
            //ventanas.add(e)

            perfilDefecto.setVentanasPermisos(ventanas);
            entityManager.merge(perfilDefecto);
            return perfilDefecto;
            
        } catch (RemoteException ex) {
            Logger.getLogger(PerfilService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    public Perfil consultarPerfileDefectoPorEmpresa(Empresa empresa) throws RemoteException, ServicioCodefacException
    {
        Map<String,Object> mapParametros=new HashMap<String, Object>();
        mapParametros.put("empresa", empresa);
        mapParametros.put("estado",GeneralEnumEstado.ACTIVO.getEstado());
        mapParametros.put("nombre", Perfil.PERFIL_DEFECTO);
        List<Perfil> resultadoList=getFacade().findByMap(mapParametros);
        if(resultadoList.size()>0)
        {
            return resultadoList.get(0);
        }
        return null;
    }
    
    public MenuCodefacRespuesta construirMenuPermisosUsuario(SessionCodefac sessionCodefac) throws RemoteException, ServicioCodefacException
    {
        //List<JMenu> menus=new ArrayList<JMenu>();
        MenuCodefacRespuesta respuesta=new MenuCodefacRespuesta();
        
                
        for (ModuloCodefacEnum moduloSistema : ModuloCodefacEnum.values()) {
            
             if (moduloSistema.equals(ModuloCodefacEnum.RESTAURANTE)) 
             {
                System.out.println("revisar ...");
            }
                
                //JMenu menuModulo = new JMenu(moduloSistema.getNombre());
                //menuModulo.setIcon(moduloSistema.getIcono());
                //menuModulo.setFont(new Font("Arial",2,15));
                boolean existenCategorias=false;
                
                for (CategoriaMenuEnum categoriaEnum : CategoriaMenuEnum.values()) {
                    //JMenu menuCategoria=new JMenu(categoriaEnum.getNombre());
                    //menuCategoria.setIcon(categoriaEnum.getIcono());
                    //menuCategoria.setFont(new Font("Arial", 0, 13));
                    
                    boolean existenMenuItem=false;
                    for (VentanaEnum menuControlador : VentanaEnum.getListValues())  //Todo: Analizar para que la variables ventanasMenuList pueda setera cada vez que busco las pantalla que pertence al menu y se vayagn quitando de la lista para acelerar el proceso
                    {
                        //Si la ventana no pertecene a la categoria no hago mas validaciones
                        if(!menuControlador.getCategoriaMenu().equals(categoriaEnum))
                        {
                            continue; //salta a la siguiente vuelta
                        }
                        
                        //Verificacion cuando es un modulo habilitado
                        boolean agregarAlMenu=false;
                        
                        //Si esta en modo de desarrollo carga todos las opciones de los menus
                        if(ParametrosSistemaCodefac.MODO.equals(ModoSistemaEnum.DESARROLLO))
                        {
                            if (menuControlador.getModulo().equals(moduloSistema)) 
                            {
                                agregarAlMenu=true;
                            }
                        }
                        else //Si esta en modo de produccion hago las validaciones normales
                        {                        
                            //Validacion de las ventanas cuando el usuario es GRATIS o no tiene creado ningun otro usuario y esta entrando con el usuario de soporte de ADMIN
                            //Solo habilito los acceso que son del gratuito hasta que pueda terminar de configurar      
                            //TODO: Talvez esta parte se debe mejorar por que ya se hace la misma consulta para poder dar acceso al usuario en el login
                            UsuarioServicio usuarioServicio=new UsuarioServicio();
                            Integer numeroUsuarios=usuarioServicio.obtenerCantidadUsuariosActivosPorEmpresa(sessionCodefac.getEmpresa());
                             
                            if(sessionCodefac.getTipoLicenciaEnum().equals(TipoLicenciaEnum.GRATIS) || numeroUsuarios==0)
                            {
                                //Si el tipo de licencia de la pantala es gratis le activo solo las pantallas disponibles para esta modalidad
                                if (menuControlador.getModulo().equals(moduloSistema)) 
                                {                                                                
                                    //El acceso es el mismo para cualquier usuario gratis y para el administrador
                                    if(menuControlador.getTipoLicenciaEnum().equals(TipoLicenciaEnum.GRATIS)) 
                                    {
                                        agregarAlMenu = true;
                                    }

                                }
                            }
                            else //Validacion para usuarios premiun
                            {                        
                                if(isModuloPermitido(moduloSistema,sessionCodefac))
                                {
                                    if(menuControlador.getModulo().equals(moduloSistema))
                                    {                                    
                                        if(verificarMenuUsuario(menuControlador,sessionCodefac) || sessionCodefac.getUsuario().isRoot)
                                        {
                                            agregarAlMenu=true;
                                        }

                                      }
                                }
                                else //Verificacion cuando no es un modulo habilitado
                                {
                                    //Solo agregar otras ventanas de otros modulos si el menu pertenece al modulo actual
                                    //Nota: sin esta linea pueden aparecer varios enlaces a esta ventana desde otros menus de modulos
                                    if (menuControlador.getModulo().equals(moduloSistema)) {
                                        //Verifica si es super usuario carga todos los modulos


                                        //Verifica si la pantalla adicional deberia agregarse porque esta depende de otra que si se cargo el modulo
                                        if (menuControlador.verificarPermisoModuloAdicional(sessionCodefac.getModulos())) 
                                        {
                                            //Verifica si el usuario tienes permisos para esa pantalla o son son super usuarios
                                            if(verificarMenuUsuario(menuControlador,sessionCodefac) || sessionCodefac.getUsuario().isRoot)
                                            {
                                                agregarAlMenu = true;
                                            }
                                        } 


                                    }


                                }

                            }
                        }
                        
                        //Esta pantalla filtra que solo se agregue si pertenece al modulo y a la submenu corecto
                        if (menuControlador.getCategoriaMenu().equals(categoriaEnum)&& agregarAlMenu ) {
                            existenMenuItem = true;
                            
                            
                            //Agregar atajo de teclado si existe
                            if(menuControlador.getTeclaAtajo()!=null)
                            {
                                //menuVentana.setAccelerator(KeyStroke.getKeyStroke(menuControlador.getTeclaAtajo(),InputEvent.ALT_MASK));
                            }
                            
                            respuesta.getVentanasDisponibles().add(menuControlador);
                            //menuCategoria.add(menuVentana);

                            
                        }
                        
                    }
                    
                    if(existenMenuItem)
                    {
                        //menuModulo.add(menuCategoria);
                        respuesta.agregarCategoria(moduloSistema, categoriaEnum);
                        existenCategorias=true;
                    }
                    
                } 
                
                //Si existen categorias agrego el modulo
                if(existenCategorias)
                {
                    respuesta.getModulosDisponibles().add(moduloSistema);
                    //menus.add(menuModulo);
                }
            //}
            
            
        }
        //return menus;
        return respuesta;
    }
    
    /*
    Metodo que permite verificar si el usuario tiene permiso para el menu seleccionado
    */
    private boolean verificarMenuUsuario(VentanaEnum ventanaEnum,SessionCodefac sessionCodefac)
    {
        List<Perfil> perfiles=sessionCodefac.getPerfiles();
        for (Perfil perfil : perfiles) {
            //Verificar si tiene permisos dentro de cada perfil asignado al usuario
            for (PermisoVentana permisoVentana : perfil.getVentanasPermisos()) {
                
                if(permisoVentana.getNombreClase().equals("ARQUEO_CAJA"))
                {
                    System.out.println("ERRO VENTANA:" + permisoVentana.getNombreClase());    
                }
                if(permisoVentana.getVentanaEnum().equals(ventanaEnum))
                {
                    return true;
                }
            }
 
        } 
        return false;
    }
    
    private boolean isModuloPermitido(ModuloCodefacEnum moduloVerificar,SessionCodefac sessionCodefac)
    {
        List<ModuloCodefacEnum> modulosPermitidos =sessionCodefac.getModulos();
        for (ModuloCodefacEnum modulosPermitido : modulosPermitidos) {
            if(modulosPermitido.equals(moduloVerificar))
            {
                return true;
            }
        }
        //Si no encuentra ninguna coincidencia manda false
        return false;
    
    }
    
}
