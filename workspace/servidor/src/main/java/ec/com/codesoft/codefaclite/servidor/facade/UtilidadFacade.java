/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @auhor
 */
public class UtilidadFacade extends AbstractFacade<Object>
{

    public UtilidadFacade() {
        super(Object.class);
    }
    
    /**
     * Atencion: Este codigo solo funciona cuando previamente tiene un sufijo por empresa , o todos los datos se pueden mesclar entre empresas
     * y solo funciona para tablas que tenga un campo llamado CODIGO
     * @param prefijo
     * @param nombreTabla
     * @return 
     */
    public Integer obtenerCodigoMaximo(String prefijo,String nombreTabla)
    {
        //String query=" Select ";
        String queryString = "SELECT MAX(u.codigo) FROM :nombreTabla u WHERE u.codigo LIKE ?1 ";
        queryString=queryString.replace(":nombreTabla",nombreTabla);
        
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1,prefijo+"%");
        
        String codigo=null;
        try
        {
            codigo=query.getSingleResult().toString();
        }catch(NoResultException nre)
        {
            nre.printStackTrace();
        }catch(NullPointerException e)
        {
            e.printStackTrace();
        }
        
        Integer resultado=1;
        if(codigo!=null)
        {
            resultado=Integer.parseInt(codigo.replace(prefijo,""));
            resultado++;
        }
        
        return resultado;
    }
    
    
    /**
     * TODO:Optimizar para luego optener el código teniendo en cuenta la empresa
     * Solucion.- tener un where por defecto en el query , pero que permita enviar un map con el dato de remplazo para casos especiales
     * @param nombreTabla
     * @return 
     */
    public Integer obtenerCodigoMaximoPorId(String nombreTabla,String nombreCampoPk)
    {
        //String query=" Select ";
        String queryString = "SELECT MAX(u.:nombreCampo) FROM :nombreTabla u ";
        queryString=queryString.replace(":nombreCampo",nombreCampoPk);
        queryString=queryString.replace(":nombreTabla",nombreTabla);
        
        Query query = getEntityManager().createNativeQuery(queryString);
        
        Object resultado=null;
        try
        {
            resultado=query.getSingleResult();
        }catch(NoResultException nre)
        {
            nre.printStackTrace();
        }catch(NullPointerException e)
        {
            e.printStackTrace();
        }
        
        //Integer resultado=1;
        if(resultado==null)
        {
            return 0;
        }
        else
        {
            return Integer.parseInt(resultado+"");
        }
        
        
    }
}
