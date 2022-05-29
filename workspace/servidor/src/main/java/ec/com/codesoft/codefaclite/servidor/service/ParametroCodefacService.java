/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefacEnum;
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidor.facade.ParametroCodefacFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;
import ec.com.codesoft.codefaclite.utilidades.sql.UtilidadSql;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author Carlos
 */
public class ParametroCodefacService extends ServiceAbstract<ParametroCodefac,ParametroCodefacFacade> implements ParametroCodefacServiceIf{

    private ParametroCodefacFacade parametroCodefacFacade;

    public ParametroCodefacService() throws RemoteException {
        super(ParametroCodefacFacade.class);
        parametroCodefacFacade=new ParametroCodefacFacade();
    }
    
    public Map<String ,ParametroCodefac> getParametrosMap(Empresa empresaIf) throws java.rmi.RemoteException
    {
        try {
            return (Map<String, ParametroCodefac>) ejecutarConsulta(new MetodoInterfaceConsulta() {
                @Override
                public Object consulta() throws ServicioCodefacException, RemoteException {
                    Map<String, ParametroCodefac> parametrosCodefacMap = new HashMap<String, ParametroCodefac>();
                    //System.out.println("EJECUNTANDO CONSULTA DE getParametrosMap <--------------------------");
                    
                    //ALERTA: REVISAR QUE ESTA LINEA DE CODIGO GENERA  EL SIGUIENTE LOG
                    //A signal was attempted before wait() on ConcurrencyManager. This normally means that an attempt was made to 
                    //commit or rollback a transaction before it was started, or to rollback a transaction twice.
                    List<ParametroCodefac> parametros = getFacade().getParametrosMapByEmpresa(empresaIf);
                    for (ParametroCodefac parametro : parametros) {
                        parametrosCodefacMap.put(parametro.getNombre(), parametro);
                    }
                    return parametrosCodefacMap;
                    //return getFacade().getParametrosMapByEmpresa(empresaIf);
                }
            });
            
            //return parametrosCodefacMap;
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ParametroCodefacService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new HashMap<>();
    }
    
    public ParametroCodefac getParametroByNombre(String nombre,Empresa empresa) throws java.rmi.RemoteException
    {
        try {
            return (ParametroCodefac) ejecutarConsulta(new MetodoInterfaceConsulta() {
                @Override
                public Object consulta() throws ServicioCodefacException, RemoteException {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("nombre", nombre);
                    map.put("empresa", empresa);
                    List<ParametroCodefac> parametroCodefacList = getFacade().findByMap(map);
                    if (parametroCodefacList != null && parametroCodefacList.size() > 0)
                        return parametroCodefacList.get(0);
                    else
                        return null;
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ParametroCodefacService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public ParametroCodefac getParametroByNombreSinEmpresa(String nombre) throws java.rmi.RemoteException
    {
        try {
            return (ParametroCodefac) ejecutarConsulta(new MetodoInterfaceConsulta() {
                @Override
                public Object consulta() throws ServicioCodefacException, RemoteException {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("nombre", nombre);
                    List<ParametroCodefac> parametroCodefacList = getFacade().findByMap(map);
                    if (parametroCodefacList != null && parametroCodefacList.size() > 0)
                        return parametroCodefacList.get(0);
                    else
                        return null;
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ParametroCodefacService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * TODO: El nombre esta medio confuso , por que en realidad este metodo edita y graba al mismo tiempo
     * @param parametro
     * @throws java.rmi.RemoteException 
     */
    public void editarParametros(List<ParametroCodefac> parametro) throws java.rmi.RemoteException
    {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() throws ServicioCodefacException, RemoteException {
                    editarParametrosSinTransaccion(parametro);
                }
            });
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ParametroCodefacService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void editarParametrosSinTransaccion(List<ParametroCodefac> parametros) throws java.rmi.RemoteException
    {
        for (ParametroCodefac parametroCodefac : parametros) {
            if (parametroCodefac.getId() == null) //Si no existe el dato lo grabo
            {
                entityManager.persist(parametroCodefac);
            } else //Si existe el dato solo lo edito
            {
                entityManager.merge(parametroCodefac);
            }
        }    
    }
    
    public void grabarOEditar(ParametroCodefac parametro) throws java.rmi.RemoteException,ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                //TODO: Analizar si debo tener creado 2 metodos por separado para grabar y editar
                if(parametro.getId()==null)
                {
                    entityManager.persist(parametro);
                }
                else
                {
                    entityManager.merge(parametro);
                }
            }
        });
        return  ;
    }
    
    public void grabarOEditar(Empresa empresa,String parametroNombre,String valor) throws java.rmi.RemoteException,ServicioCodefacException
    {
        ParametroCodefac parametroCodefac=getParametroByNombre(parametroNombre,empresa);
        //Si no existe creando antes creo un nuevo parametroCodefac
        if(parametroCodefac==null)
        {
            parametroCodefac=new ParametroCodefac();
            parametroCodefac.setEmpresa(empresa);
            parametroCodefac.setNombre(parametroNombre);
        }
        parametroCodefac.setValor(valor);
        grabarOEditar(parametroCodefac);
    }
    
    
    /**
     * Edita todos los parametros 
     * @param parametro 
     * @deprecated porque cuando se actualizan todos los valores causan problemas en algunos formularios
     */
    public void editarParametros(Map<String ,ParametroCodefac> parametro) throws java.rmi.RemoteException
    {
        try {
            ejecutarTransaccion(new MetodoInterfaceTransaccion() {
                @Override
                public void transaccion() {
                    for (Map.Entry<String, ParametroCodefac> entry : parametro.entrySet()) {
                        //String key = entry.getKey();
                        ParametroCodefac value = entry.getValue();
                        
                        if(value.getId()==null) //Si no existe el dato lo grabo
                        {
                            entityManager.persist(value);
                        }
                        else //Si existe el dato solo lo edito
                        {
                            entityManager.merge(value);
                        }
                        
                    }
                }
            });
            /*
            for (Map.Entry<String, ParametroCodefac> entry : parametro.entrySet()) {
            String key = entry.getKey();
            ParametroCodefac value = entry.getValue();
            parametroCodefacFacade.edit(value);
            }*/
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ParametroCodefacService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    public ParametroCodefac grabar(ParametroCodefac parametro) throws java.rmi.RemoteException
    {
        try {
            parametroCodefacFacade.create(parametro);
        } catch (ConstrainViolationExceptionSQL ex) {
            Logger.getLogger(ParametroCodefacService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(ParametroCodefacService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parametro;
    }*/
    
    public List<ParametroCodefac> buscarParametrosPorMap(Map<String,Object> map) throws java.rmi.RemoteException
    {
        return parametroCodefacFacade.findByMap(map);
    }
    
    public void procesoBloqueadoPrueba() throws java.rmi.RemoteException, ServicioCodefacException
    {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                try {
                    System.out.println("Iniciada transaccion");
                    Thread.sleep(10000000);
                    System.out.println("Terminada transaccion");
                } catch (InterruptedException ex) {
                    Logger.getLogger(ParametroCodefacService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void crearParametroPorDefectoEmpresaSinTrasaccion(Empresa empresa) throws java.rmi.RemoteException,ServicioCodefacException
    {
        //Datos de un correo por defecto para que puedan hacer pruebas
        entityManager.persist(crearObjectoSinTransaccion(empresa, ParametroCodefac.CORREO_USUARIO, ParametrosSistemaCodefac.CORREO_DEFECTO_USUARIO));
        entityManager.persist(crearObjectoSinTransaccion(empresa, ParametroCodefac.CORREO_CLAVE, ParametrosSistemaCodefac.CORREO_DEFECTO_CLAVE));
        entityManager.persist(crearObjectoSinTransaccion(empresa, ParametroCodefac.SMTP_HOST, ParametrosSistemaCodefac.CORREO_DEFECTO_HOST));
        entityManager.persist(crearObjectoSinTransaccion(empresa, ParametroCodefac.SMTP_PORT, ParametrosSistemaCodefac.CORREO_DEFECTO_PUERTO));        
        
    }
    
    private ParametroCodefac crearObjectoSinTransaccion(Empresa empresa,String nombre, String valor)
    {
        ParametroCodefac parametro=new ParametroCodefac();
        parametro.setEmpresa(empresa);
        parametro.setNombre(nombre);
        parametro.setValor(valor);
        return parametro;
    }
    
    public List ejecutarConsultaNativaEnum(RecursoCodefacEnum queryEnum) throws RemoteException,ServicioCodefacException
    {
        InputStream inputStreamReporte= RecursoCodefac.SQL_CODEFAC.getResourceInputStream(queryEnum.getNombre());
        String queryDatos= UtilidadesTextos.getStringFromInputStream(inputStreamReporte);
        String[] queryList= queryDatos.split(";");
        
        List resultado=new ArrayList();
        for (String string : queryList) 
        {
            resultado.addAll(ejecutarConsultaNativa(string));
        }
        return resultado;
    }
    
    public List ejecutarVariasConsultaNativa(String queryStr) throws RemoteException,ServicioCodefacException
    {
        String[] queryList= queryStr.split(";");
        
        List resultado=new ArrayList();
        for (String string : queryList) 
        {
            resultado.addAll(ejecutarConsultaNativa(string));
        }
        return resultado;
    }
    
    /**
     * TODO: Mover a otro servicio mas entendible para este metodo , lo dejo por el momento en esta pantalla porque necsito un entorno de servicios
     * @param queryStr
     * @return
     * @throws RemoteException
     * @throws ServicioCodefacException 
     */
    public List ejecutarConsultaNativa(String queryStr) throws RemoteException,ServicioCodefacException
    {
        try
        {
            Query query=AbstractFacade.entityManager.createNativeQuery(queryStr);
            //Verificar si es un query de actualizacion o consulta
            if(queryStr.toLowerCase().indexOf("select ")>=0)
            {                
                return query.getResultList();
            }
            else
            {   
                List<Object[]> resultado=new ArrayList<Object[]>();
                
               
                //Agregar las filas afectadas
                Integer numeroFilasAfectadas=(Integer) ejecutarTransaccionConResultado(new MetodoInterfaceTransaccionResultado() {
                    
                    @Override
                    public Object transaccion() throws ServicioCodefacException, RemoteException {
                        return query.executeUpdate();
                    }                    
                });
                Object[] resultadoArray={numeroFilasAfectadas};
                resultado.add(resultadoArray);
                return resultado;
                
            }
            
            
        }catch(Exception e)
        {
            throw new ServicioCodefacException(e.getMessage());
        }
        
    }
}
