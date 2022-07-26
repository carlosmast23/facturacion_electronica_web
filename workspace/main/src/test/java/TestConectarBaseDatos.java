
import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author
 */
public class TestConectarBaseDatos {
    public static void main(String[] args) {
        try {
            AbstractFacade.usuarioDb="root";
            AbstractFacade.claveDb="Code17bwbtj";
            

            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            Connection conn = DriverManager.getConnection("jdbc:derby:Derby2.DB;databaseName=codefac;user="+AbstractFacade.usuarioDb+";password="+AbstractFacade.claveDb);
            System.out.println("Base de datos");
            
            Statement s = conn.createStatement();
            PreparedStatement pstm = conn.prepareStatement(" select * from estudiante where NORMALIZAR(nombres)='asdad' ");
            pstm.execute();
            
            
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TestConectarBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TestConectarBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
