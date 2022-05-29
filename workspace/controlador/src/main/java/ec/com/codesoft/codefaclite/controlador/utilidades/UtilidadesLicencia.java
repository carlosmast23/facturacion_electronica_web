/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.utilidades;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.Licencia;
import ec.com.codesoft.codefaclite.ws.codefac.test.service.WebServiceCodefac;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author CARLOS_CODESOFT
 */
public abstract class UtilidadesLicencia {

    public static Boolean crearLicencia(Empresa empresa,String usuarioTexto,Boolean actualizaLicencia) 
    {
        //Verificar si existe la licencia para solo descargar
        String licencia = null;
        try {
            licencia = WebServiceCodefac.getLicencia(usuarioTexto);
        } catch (Exception ex) {
            Logger.getLogger(UtilidadesLicencia.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Cargo los datos de la licencia de internet si existe
        Licencia licenciaInternet = new Licencia();
        try {
            licenciaInternet.cargarLicenciaOnline(usuarioTexto);
        } catch (Exception ex) {
            Logger.getLogger(UtilidadesLicencia.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Si la licencia existe
        if (!licencia.equals("fail")) {
            //Si existe en el servidor la licencia solo vuelve a descargar
            Licencia licenciaDescargada = new Licencia();
            licenciaDescargada.setUsuario(usuarioTexto);
            licenciaDescargada.setLicencia(licencia);
            licenciaDescargada.setTipoLicenciaEnum(licenciaInternet.getTipoLicenciaEnum());
            licenciaDescargada.setCantidadClientes(licenciaInternet.getCantidadClientes());
            licenciaDescargada.cargarModulosOnline();

            try {
                ServiceFactory.getFactory().getUtilidadesServiceIf().crearLicenciaDescargada(empresa, licenciaInternet);
                //validacionLicenciaCodefac.crearLicenciaDescargada(licenciaDescargada);
            } catch (RemoteException ex) {
                Logger.getLogger(UtilidadesLicencia.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(UtilidadesLicencia.class.getName()).log(Level.SEVERE, null, ex);
            }
                        
            return true;// Si la licencia se crea correctamente entonces retorno true
        }
        return false;
        
    }
}
