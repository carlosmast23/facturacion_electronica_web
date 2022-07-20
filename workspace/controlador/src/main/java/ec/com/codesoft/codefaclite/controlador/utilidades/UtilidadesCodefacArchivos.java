/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.utilidades;

import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import ec.com.codesoft.codefaclite.controlador.vista.configuraciones.AsistenteConfiguracionRapidaControlador;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @auhor
 */
public abstract class UtilidadesCodefacArchivos {
    
    public static void subirArchivoServidor(Empresa empresa,File file,DirectorioCodefac directorioCodefac)
    {
        if (file != null) {
            try {
                SimpleRemoteInputStream istream = new SimpleRemoteInputStream(
                        new FileInputStream(file));

                ParametroCodefac parametroEmpresa = ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.DIRECTORIO_RECURSOS, empresa);
                String pathServidor = parametroEmpresa.getValor();
                ServiceFactory.getFactory().getRecursosServiceIf().uploadFileServer(
                        pathServidor,
                        directorioCodefac,
                        istream,
                        file.getName()
                );

            } catch (FileNotFoundException ex) {
                Logger.getLogger(UtilidadesCodefacArchivos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(UtilidadesCodefacArchivos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
