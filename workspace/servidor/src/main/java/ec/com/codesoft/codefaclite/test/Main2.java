/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.test;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidor.facade.PersonaFacade;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author
 */
public class Main2 {
    public static void main(String[] args) {
       //eliminarDatos();
        //grabar();
        //editar();
        //eliminar();
        imprimir();
    }
    
    
    public static void grabar()
    {
        Persona p=new Persona();
        p.setIdentificacion("148331837");
        //p.setNombre("JUAN");
        
        //Persona p2=new Persona();
        //p2.setCedula("1451");
        //p2.setNombre("IORI3");
        
        PersonaFacade personaFacade=new PersonaFacade();
        //personaFacade.create(p);
        //personaFacade.create(p2);
    }
    
    public static void eliminarDatos()
    {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            //obtenemos la conexión
            Connection conn = DriverManager.getConnection("jdbc:derby:.\\DB\\Derby2.DB;create=true");
            
            if (conn!=null)
            {
                //JOptionPane.showMessageDialog(null,"OK base de datos listo");
                String creartabla="delete from Persona";
                //String desc="disconnect;";
                PreparedStatement pstm = conn.prepareStatement(creartabla);
                pstm.execute();
                pstm.close();

                //PreparedStatement pstm2 = conn.prepareStatement(desc);
                //pstm2.execute();
                //pstm2.close();

                //JOptionPane.showMessageDialog(null,"BD Creada correctamente");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CrearBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CrearBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void imprimir()
    {
        PersonaFacade personaFacade=new PersonaFacade();
        List<Persona> personasList=personaFacade.findAll();
        for (Persona persona : personasList) {
            //System.out.println(persona.getNombreLegal());
        }
    }
    
    public static void editar()
    {

        PersonaFacade personaFacade=new PersonaFacade();
        List<Persona> personasList=personaFacade.findAll();
        for (Persona persona : personasList) {
            //persona.setNombreLegal(persona.getNombreLegal()+"-");
            personaFacade.edit(persona);
            //System.out.println(persona.getNombre());
        }
    }
    
    public static void eliminar()
    {
        PersonaFacade personaFacade=new PersonaFacade();
        List<Persona> personasList=personaFacade.findAll();
        for (Persona persona : personasList) {
            //persona.setNombreLegal(persona.getNombreLegal()+"-");
            personaFacade.remove(persona);
            return;
            //System.out.println(persona.getNombre());
        }
    }
}
