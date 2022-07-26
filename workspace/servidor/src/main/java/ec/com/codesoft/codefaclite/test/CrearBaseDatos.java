/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author
 */
public class CrearBaseDatos 
{
    public static void main(String[] args) {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            //obtenemos la conexión
            Connection conn = DriverManager.getConnection("jdbc:derby:.\\DB\\Derby2.DB;create=true");
            
            if (conn!=null)
            {
                JOptionPane.showMessageDialog(null,"OK base de datos listo");
                //String creartabla="create table CLIENTE( ID_CLIENTE integer not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) , NOMBRE_SOCIAL varchar(256),TIPO_IDENTIFICACION varchar(30), IDENTIFICACION varchar(13), TIPO_CLIENTE varchar(15),DIRECCION varchar(1024), TELEFONO_CONVENCIONAL varchar(9),EXTENSION_TELEFONO varchar(4), TELEFONO_CELULAR varchar(10), CORREO_ELECTRONICO varchar(60),  primary key (ID_CLIENTE))";
                String creartabla="DROP TABLE CLIENTE";
                //String desc="disconnect;";
                PreparedStatement pstm = conn.prepareStatement(creartabla);
                pstm.execute();
                pstm.close();

                //PreparedStatement pstm2 = conn.prepareStatement(desc);
                //pstm2.execute();
                //pstm2.close();

                JOptionPane.showMessageDialog(null,"BD Creada correctamente");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CrearBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CrearBaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
