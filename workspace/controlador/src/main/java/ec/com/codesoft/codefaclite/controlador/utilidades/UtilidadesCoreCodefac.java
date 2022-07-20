/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.utilidades;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.interfaces.ControladorVistaIf;
import ec.com.codesoft.codefaclite.controlador.vistas.core.UtilidadesControladorVistaGeneral;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @auhor
 */
public class UtilidadesCoreCodefac {
    
    public static VistaCodefacIf getControladorTodoVista(VistaCodefacIf panel)
    {
        if(panel instanceof ControladorVistaIf)
        {
            ControladorVistaIf vistaTemp=(ControladorVistaIf) panel;
            if(vistaTemp.getControladorVista()!=null)
            {
                if(vistaTemp.getControladorVista() instanceof VistaCodefacIf)
                {
                    //return (VistaCodefacIf) vistaTemp.getControladorVista();
                    return (VistaCodefacIf) vistaTemp.getControladorVista();
                }
            }
        }
        return null;
    }
     /**
     * Interfaz que permite ejecutar de forma generica el metodo iniciar de las vistas
     * @param vistaCodefacIf 
     */
    public static void ejecutarIniciar(VistaCodefacIf vistaCodefacIf) throws ExcepcionCodefacLite
    {
        UtilidadesControladorVistaGeneral.ejecutarAccionVista(vistaCodefacIf,new UtilidadesControladorVistaGeneral.EjecutarVistaIf() {
            @Override
            public void ejecutar() throws UnsupportedOperationException, ExcepcionCodefacLite, RemoteException {
                vistaCodefacIf.iniciar();
            }
        });
    }
    
    public static void ejecutarNuevo(VistaCodefacIf vistaCodefacIf)
    {
        try {
            UtilidadesControladorVistaGeneral.ejecutarAccionVista(vistaCodefacIf,new UtilidadesControladorVistaGeneral.EjecutarVistaIf() {
                @Override
                public void ejecutar() throws UnsupportedOperationException, ExcepcionCodefacLite, RemoteException {
                    vistaCodefacIf.nuevo();
                }
            });
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(UtilidadesCoreCodefac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void ejecutarLimpiar(VistaCodefacIf vistaCodefacIf)
    {
        try {
            UtilidadesControladorVistaGeneral.ejecutarAccionVista(vistaCodefacIf,new UtilidadesControladorVistaGeneral.EjecutarVistaIf() {
                @Override
                public void ejecutar() throws UnsupportedOperationException, ExcepcionCodefacLite, RemoteException {                    
                    vistaCodefacIf.limpiar();
                }
            });
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(UtilidadesCoreCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } catch(UnsupportedOperationException uoe)
        {
            //TODO: no imprimir nada
        }
    }
    
    public static void ejecutarCargarDatosPantalla(VistaCodefacIf vistaCodefacIf,Object dato)
    {
        try {
            UtilidadesControladorVistaGeneral.ejecutarAccionVista(vistaCodefacIf,new UtilidadesControladorVistaGeneral.EjecutarVistaIf() {
                @Override
                public void ejecutar() throws UnsupportedOperationException, ExcepcionCodefacLite, RemoteException {                    
                    vistaCodefacIf.cargarDatosPantalla(dato);
                }
            });
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(UtilidadesCoreCodefac.class.getName()).log(Level.SEVERE, null, ex);
        } catch(UnsupportedOperationException uoe)
        {
            //TODO: no imprimir nada
        }
    }
}
