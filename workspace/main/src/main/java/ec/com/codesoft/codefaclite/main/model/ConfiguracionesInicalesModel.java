/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.main.other.BaseDatosCredenciales;
import ec.com.codesoft.codefaclite.main.panel.ConfiguracionesInicialesDialogo;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author
 */
public class ConfiguracionesInicalesModel extends ConfiguracionesInicialesDialogo{
    
    public boolean datosGrabados;
    private ModoEnum modo;
            
    public ConfiguracionesInicalesModel(ModoEnum modo) 
    {        
        super(null,true);
        this.modo=modo;
        activarPestaña();
        listenerBotones();
        this.datosGrabados=false;
    }
    
    private void activarPestaña()
    {
        switch(this.modo)
        {
            case ACCEDER:
                getTabCredenciales().setEnabledAt(0,true);
                getTabCredenciales().setEnabledAt(1,false);
                getTabCredenciales().setSelectedIndex(0);
                break;
                
            case REGISTRAR:
                getTabCredenciales().setEnabledAt(0, false);
                getTabCredenciales().setEnabledAt(1, true);
                getTabCredenciales().setSelectedIndex(1);
                break;
        }
    }

    private void listenerBotones() {
        
        getBtnAceptarIngreso().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = getTxtUsuarioIngreso().getText();
                String clave = new String(getTxtClaveIngreso().getPassword());

                //Validaciones de que los campos no esten blanco
                if (usuario.equals("") || clave.equals("")) {
                    DialogoCodefac.mensaje("Advertencia", "Por favor ingrese todos los campos para continuar", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    return;
                }
                
                grabarDatos(usuario,clave);                 
                dispose();
            }
        });
        
        
        getBtnAceptarRegistro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario=getTxtUsuarioRegistro().getText();
                String clave=new String(getTxtClaveRegistro().getPassword());
                String claveRepetida=new String(getTxtRepetirClaveRegistro().getPassword());
                
                //Validaciones de que los campos no esten blanco
                if(usuario.equals("") || clave.equals("") || claveRepetida.equals(""))
                {
                    DialogoCodefac.mensaje("Advertencia","Por favor ingrese todos los campos para continuar",DialogoCodefac.MENSAJE_ADVERTENCIA);
                    return;
                }
                
                //Validacion de que ingrese las dos contraseñas iguales
                if(!clave.equals(claveRepetida))
                {
                    DialogoCodefac.mensaje("Advertencia","Las claves ingresadas son distintas",DialogoCodefac.MENSAJE_ADVERTENCIA);
                    return;
                }
                
                grabarDatos(usuario,clave);
                DialogoCodefac.mensaje("Correcto","Las credenciales para la base de datos fueron grabados correctamente \n\n Nota: Se recomienda anotar estos datos en un lugar seguro,\n porque son importanes y requeridos para el funcionamiento del software ",DialogoCodefac.MENSAJE_ADVERTENCIA);
                dispose();
            }
        });        
        
        getBtnCancelarRegistro().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean respuesta=DialogoCodefac.dialogoPregunta("Advertencia","Si no ingresa las credenciales no puede iniciar al sistema \nDesea salir de todos modos?",DialogoCodefac.MENSAJE_ADVERTENCIA);
                
                if(respuesta)
                {
                    //Si se cancela me salgo del sistema porque no se puede acceder sin las credenciales de la base de datos
                    System.exit(0);
                }
                        
            }
        });
        
        getBtnCancelarIngreso().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); //Si elige cancelar se sale del sistema porque los datos son ncesarios
            }
        });
    }
    
    private void grabarDatos(String usuario,String clave)
    {
        BaseDatosCredenciales credenciales = new BaseDatosCredenciales();
        credenciales.setUsuario(usuario);
        credenciales.setClave(clave);
        datosGrabados = true;
    }
    
    public enum ModoEnum
    {
       REGISTRAR,
       ACCEDER;
    }
}
