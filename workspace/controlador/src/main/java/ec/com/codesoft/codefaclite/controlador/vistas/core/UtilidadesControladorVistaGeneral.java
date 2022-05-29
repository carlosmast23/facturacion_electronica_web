/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vistas.core;

import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.FuncionesSistemaCodefac;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class UtilidadesControladorVistaGeneral {
    
    public static void ejecutarAccionVista(VistaCodefacIf vista,EjecutarVistaIf ejecutarVistaIf) throws ExcepcionCodefacLite
    {
        if(vista==null)
            return;
        
        try {
            
            ejecutarVistaIf.ejecutar();//Metodo que se ejecuta despues de construir el objeto
            
        } catch (java.lang.UnsupportedOperationException uoe) {
            //Logger.getLogger(UtilidadesControladorVistaGeneral.class.getName()).log(Level.SEVERE, null, uoe);
            System.err.println("Metodo no implementado");
            
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(UtilidadesControladorVistaGeneral.class.getName()).log(Level.SEVERE, null, ex);
            //System.err.println("Cancelado metodo iniciar");
            throw ex;
            
        } catch (RemoteException ex) {
            Logger.getLogger(UtilidadesControladorVistaGeneral.class.getName()).log(Level.SEVERE, null, ex);
            FuncionesSistemaCodefac.servidorConexionPerdida();
        }
    }
    
    public interface EjecutarVistaIf
    {
        public void ejecutar() throws UnsupportedOperationException,ExcepcionCodefacLite,RemoteException ;
    }
    
}
