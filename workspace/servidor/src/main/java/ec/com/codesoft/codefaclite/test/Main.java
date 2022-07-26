/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.test;

//import ec.com.codesoft.codefaclite.servidor.entity.Ejemplo;
//import ec.com.codesoft.codefaclite.servidor.entity.Persona;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.facade.PersonaFacade;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author
 */
public class Main {
    private static EntityManagerFactory factory;
    private static EntityManager entityManager;
     
    public static void main(String[] args) {
        
        factory=Persistence.createEntityManagerFactory("pu_ejemplo");
        entityManager=factory.createEntityManager();
        
        Persona p=new Persona();
        p.setIdentificacion("1282872");
        //p.setNombreLegal("PEPEMUENTES" );
        
        entityManager.getTransaction().begin();
        entityManager.persist(p);
        entityManager.getTransaction().commit();
        
        javax.persistence.criteria.CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Persona.class));
        List<Persona> lista=entityManager.createQuery(cq).getResultList();
        for (Persona persona : lista) {
            //System.out.println(persona.getIdentificacion()+" - "+persona.getNombreLegal());
            
        }
        
        //entityManager.merge(ejemplo);
        
        //PersonaFacade ef=new PersonaFacade();
        //ef.create(p);
        /*List<Ejemplo> lista=ef.findAll();
        for (Ejemplo ejemplo1 : lista) {
            System.out.println(ejemplo1);
        }*/
        System.err.println("funcionado ...");
    }
}
