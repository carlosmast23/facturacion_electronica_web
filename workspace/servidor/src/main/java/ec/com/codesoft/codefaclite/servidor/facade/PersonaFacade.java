/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import java.util.List;
//import ec.com.codesoft.codefaclite.servidor.entity.Persona;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos
 */
public class PersonaFacade extends AbstractFacade<Persona>
{
    //@PersistenceContext(unitName = "persistenceUnitCodefac")
    //private EntityManager em;

    public PersonaFacade() 
    {
        super(Persona.class);
    }    
    
    /*
    @Override
    protected EntityManager getEntityManager() {
        //EntityManagerFactory factory=Persistence.createEntityManagerFactory("pu_ejemplo");
        //em= factory.createEntityManager();
        return em;
    }*/
    
    public int addNumbers(int numberA, int numberB) {
        return numberA - numberB;
    }
    
    public List<Persona> buscarPorTipoFacade(OperadorNegocioEnum tipoEnum,GeneralEnumEstado estado,Empresa empresa)
    {
        //Persona persona;
        //persona.getEmpresa();
        ////persona.getTipo();
        String queryString = "SELECT p FROM Persona p WHERE (p.tipo=?1 or p.tipo=?2 ) and p.estado=?3 and p.empresa=?4";
        Query query = getEntityManager().createQuery(queryString);
        
        query.setParameter(1,OperadorNegocioEnum.AMBOS.getLetra());
        query.setParameter(2,tipoEnum.getLetra());
        query.setParameter(3,estado.getEstado());
        query.setParameter(4,empresa);
        
        return query.getResultList();
    }

    
}
