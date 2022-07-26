/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ConstrainViolationExceptionSQL;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.PersistenciaDuplicadaException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import org.apache.derby.shared.common.error.DerbySQLIntegrityConstraintViolationException;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.exceptions.DatabaseException;

/**
 *
 * @author
 */
public abstract class AbstractFacade<T>
{
    public static EntityManager entityManager;
    
    /**
     * Datos para setear las variables globales de conexion a la base de datos
     */
    public static String usuarioDb;
    public static String claveDb;
    
    public static final String namePersistence="pu_ejemplo";
    private Class<T> entityClass;
    

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    //protected abstract EntityManager getEntityManager();
    public EntityManager getEntityManager()
    {
        //EntityManagerFactory factory=Persistence.createEntityManagerFactory(namePersistence);
        return entityManager;
    }

    //Metodo eliminado porque esta en desuso y puede generar muchos problemas de persistencia
    /*
    public void create(T entity) throws ConstrainViolationExceptionSQL,DatabaseException{
        try
        {
            EntityTransaction tx= getEntityManager().getTransaction();
            tx.begin();
            //getEntityManager().getTransaction().begin();
            getEntityManager().persist(entity);
            entityManager.flush();
            tx.commit();
        }catch(PersistenceException e)
        {

            if(e.getCause()!=null && e.getCause().getClass().equals(DatabaseException.class) )
            {
                DatabaseException dbe=(DatabaseException) e.getCause();
                //TODO: Esta valifacion de la claves primarias es solo para la base de datos derby
                if(dbe.getCause()!=null && dbe.getCause().getClass().equals(DerbySQLIntegrityConstraintViolationException.class))
                {
                    DerbySQLIntegrityConstraintViolationException constrainViolation = (DerbySQLIntegrityConstraintViolationException) dbe.getCause();
                    System.out.println(constrainViolation.getMessage());
                    throw new ConstrainViolationExceptionSQL("Ya existe un registro registrado con la clave primaria");
                }
                throw dbe;
            }

            
        }

        //getEntityManager().getTransaction().commit();
    }
    */

    /**
     * @deprecated 
     * @param entity 
     */
    public void edit(T entity) {
        EntityTransaction tx= getEntityManager().getTransaction();
        tx.begin();
        getEntityManager().merge(entity);
        tx.commit();
    }

    public void remove(T entity) {
        getEntityManager().getTransaction().begin();
        getEntityManager().remove(getEntityManager().merge(entity));
        getEntityManager().getTransaction().commit();
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }
    
    public List<T> findByMap(Map<String, Object> parametros)
    {
        String queryString = buildQueryString(entityClass, parametros);
        Query query=getEntityManager().createQuery(queryString);
        
        /**
         * Setear los valores
         */
        if (parametros != null) {
            for (String parametro : parametros.keySet()) {
                query.setParameter(parametro.replace(".",""), parametros.get(parametro));
            }
        }
        query.setFlushMode(FlushModeType.COMMIT);
        //query.setHint(QueryHints.READ_ONLY, HintValues.TRUE);
        return query.getResultList();

    }   
    

    
    private String buildQueryString(Class clase, Map<String, Object> parametros) {
        String objectName = "e";

        String queryString = "from " + clase.getSimpleName() + " " + objectName;
        if (parametros != null && parametros.size() > 0) {
            String where =buildWhere(parametros, objectName);
            queryString += " where " + where;
        }
        return queryString;
    }
    
    //Convierte el Mapa a string(estructura query) del string objeto que se envia
	public static String buildWhere(Map aMap, String objectName) {

		Set keys = aMap.keySet();

		String query = " ";

		Iterator it = keys.iterator();
		int propertyNumber = keys.size();
		int i = 1;
		while (it.hasNext()) {
			String propertyKey = (String) it.next();
			Object property = aMap.get(propertyKey);

			if (property instanceof String) {

				String propertyString = (String) property;

				if ( propertyString.contains("null") ){
					query = query + objectName + "." + propertyKey + " is "
					+ propertyString;
					it.remove();
				}else if (propertyString.contains("%")) {

					query = query + objectName + "." + propertyKey + " like :"
							+ propertyKey;
				} else {
					query = query + objectName + "." + propertyKey + " = :"
							+ propertyKey.replace(".","");
				}

			} else {

				if ( property == null ){
					query = query + objectName + "." + propertyKey + " is null ";
					it.remove();
				}else
					query = query + objectName + "." + propertyKey + " = :"
						+ propertyKey.replace(".","");
			}

			if (i == propertyNumber) {
				query = query + " ";
			} else {
				query = query + " and ";
			}

			i++;
		}
		//System.out.println("Parte del Where del Query: " + query);
		return query;
	}
    
    public static void cargarEntityManager() throws PersistenceException,PersistenciaDuplicadaException
    {
        try 
        {
            //Esta linea se ejecuta si existe la base de datos
            //TODO: Utilizar propertys para cambiar el url cuando es en Linux porque no funciona y tiene otra sintaxis
            Map<String,String> properties=new HashMap<String,String>();
            properties.put("javax.persistence.jdbc.user", AbstractFacade.usuarioDb);
            properties.put("javax.persistence.jdbc.password", AbstractFacade.claveDb);
            
            entityManager=Persistence.createEntityManagerFactory(namePersistence,properties).createEntityManager();
        }
        catch(PersistenceException e)
        {
            e.printStackTrace();
            DatabaseException dbe=(DatabaseException) e.getCause();
            
            SQLException sqlException= (SQLException) dbe.getCause();

            while(sqlException!=null)
            {
                System.out.println(sqlException.getCause());
                System.out.println(sqlException.getErrorCode());
                System.out.println(sqlException.getMessage());
                System.out.println(sqlException.getSQLState());
                System.out.println("---------------------------------");

                if(sqlException.getErrorCode()==45000)
                {
                    throw new PersistenciaDuplicadaException("Ya existe una versión de Codefac abierto");
                }
                /*
                if(sqlException.getErrorCode()==40000)
                {
                    throw new PersistenceException("No existe base de datos");
                }*/

                sqlException=sqlException.getNextException();

            }
 
            throw new PersistenceException("No existe base de datos");
        }
    }
    
    /**
     * 
     * @param nombreTabla
     * @param nombrePK
     * @return RETORNA VERDADERO SI ENCUENTRA CLAVES REPETIDAS
     */
        
    /**
     * TODO: Ver como parametrizar el nombre del esquema que parece que siempre es el mismo nombre del usuario al momento de crear la base de datos
     * @param nombreTabla 
     */
    public static void consultarConsistenciaTabla(String nombreTabla)
    {
        String queryString="VALUES SYSCS_UTIL.SYSCS_CHECK_TABLE ('LAGOS' ,'?1')";
        queryString=queryString.replace("?1",nombreTabla);
               
        Query query = entityManager.createNativeQuery(queryString);
        List resultado=query.getResultList();        
        System.out.println("resultado:"+resultado);
    }
    
    
    public static List<Object> findStaticDialog(String queryStr,Map<Integer,Object> map,int limiteMinimo,int limiteMaximo) {
        System.out.println("[Dialog]"+queryStr);
        Query query = entityManager.createQuery(queryStr);
        //Agregar los parametros del map al query
        for (Map.Entry<Integer, Object> entry : map.entrySet()) {
            Integer key = entry.getKey();
            Object value = entry.getValue();
            query.setParameter(key, value);
        }
        
        query.setMaxResults(limiteMaximo);
        query.setFirstResult(limiteMinimo);
        return query.getResultList();
    }
    
    
    public static Long findCountStaticDialog(String queryStr,Map<Integer,Object> map) {
        Query query = entityManager.createQuery(queryStr);
        //Agregar los parametros del map al query
        for (Map.Entry<Integer, Object> entry : map.entrySet()) {
            Integer key = entry.getKey();
            Object value = entry.getValue();
            query.setParameter(key, value);
        }
        
        return (Long) query.getSingleResult();
    }
    
    /**
     * Metodo que se encarga de desasoriar una entidad gestionada para poder hacer acciones
     * sobre el objecto pero que no se reflejen en la persistencia con la base de datoss
     */
    public static void detachEntity(Object obj)
    {
        //entityManager.contains(obj) para saber si el dato es administrable
        entityManager.detach(obj);
    }
    
    /**
     * Desasocia todos los objectos hijos de objeto de la persistencia para que no
     * sean entidades administradas
     * @param obj 
     */
    //TODO implementar metodo cuando tenga referencias recursivas
    public static void detachRecursive(Object obj)
    {
        entityManager.detach(obj);
        Method[] metodos= obj.getClass().getMethods();
        for (Method metodo : metodos) {
            //Verifica que el tipo de dato sea una lista
            if(List.class.equals(metodo.getReturnType()))
            {
                try {
                    List<Object> list= (List<Object>) metodo.invoke(obj);
                    for (Object object : list) {
                        detachRecursive(object);
                    }
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(AbstractFacade.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public static Boolean buscarClavesRepetidasBaseDatos(String nombreTabla,String nombrePK)
    {
        //SELECT ID,COUNT (ID) FROM FACTURA GROUP BY ID HAVING COUNT(ID)>1;
        List resultado=buscarClavesRepetidasBaseDatosLista(nombreTabla, nombrePK);
        if(resultado.size()>0)
        {
            return true;
        }
        return false;
    }
    
        public static List buscarClavesRepetidasBaseDatosLista(String nombreTabla,String nombrePK)
    {
        //SELECT ID,COUNT (ID) FROM FACTURA GROUP BY ID HAVING COUNT(ID)>1;
        String queryString=" SELECT ?ID FROM ?NOMBRE_TABLA GROUP BY ID HAVING COUNT(?ID)>1";
        queryString=queryString.replace("?ID",nombrePK);
        queryString=queryString.replace("?NOMBRE_TABLA",nombreTabla);
                
        Query query = entityManager.createNativeQuery(queryString);
        
        List resultados=query.getResultList();
        
        //if(resultados.size()==0)
        //{
        //    return new ArrayList();
        //}
        
        for (Object resultado : resultados) 
        {            
            Long idPk=(Long) resultado;
            Logger.getLogger(AbstractFacade.class.getName()).log(Level.WARNING,"ERROR CLAVES DUPLICADOS EN TABLA: "+nombreTabla+" ,ID="+idPk);
        }
        return resultados;
    }


    
    public static EntityTransaction crearTransaccion()
    {
        return entityManager.getTransaction();
    }

}