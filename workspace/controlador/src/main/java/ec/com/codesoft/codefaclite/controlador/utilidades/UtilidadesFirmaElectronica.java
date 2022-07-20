/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.utilidades;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.facturacionelectronica.FirmaElectronica;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *TODO: Talvez utilizar otra nomenclatura denombres de clases para diferenciar que utilidades estan relacionadas con el sistema codefac
 * y las otras que tiene un ambito general
 * @auhor
 */
public abstract class UtilidadesFirmaElectronica {
    
    public static void verificarFirmaElectronica(Empresa empresa,String claveFirma,String nombreFirma,Path firmaPathLocal) throws ServicioCodefacException{
        try {
            //Validacion iniciales
            if(claveFirma==null)
            {
                throw new ServicioCodefacException("No existe ingresada una clave para comprobar");
            }
            
            
            //String claveFirma = new String(getTxtClaveFirma().getPassword());
            //String nombreArchivo = getTxtNombreFirma().getText();
            //TODO:Cambiar la copia de archivos por un servicio de transferencia de archivos
            String rutaDestino = "";
            
            String pathFirma = "";

            if (firmaPathLocal != null) { //Si selecciona una archivo para recien grabar se verifica con el archivo del cliente
                //TODO: Ver si se puede mejorar y hacer la validacion enviando archivo y clave para tener mas modular el procesos de firma
                pathFirma = firmaPathLocal.toString();
                if (!claveFirma.equals("") && !pathFirma.equals("")) {
                    if (!FirmaElectronica.FirmaVerificar(pathFirma, claveFirma)) 
                    {
                        //getTxtClaveFirma().setText("");
                        throw new ServicioCodefacException("La Clave de la firma es incorrecta, ingrese nuevamente.");
                        //DialogoCodefac.mensaje("Error Clave", "La Clave de la firma es incorrecta, ingrese nuevamente.", DialogoCodefac.MENSAJE_INCORRECTO);

                    }
                }
            } else {
                //Cuando el archivo de la firma ya esta en el servidor se consulta por un servicio

                Boolean validacion = ServiceFactory.getFactory().getComprobanteServiceIf().verificarCredencialesFirma(claveFirma,empresa);

                if (!validacion) {
                    //getTxtClaveFirma().setText("");
                    //DialogoCodefac.mensaje("Error Clave", "La Clave de la firma es incorrecta, ingrese nuevamente.", DialogoCodefac.MENSAJE_INCORRECTO);
                    throw new ServicioCodefacException("La Clave de la firma es incorrecta, ingrese nuevamente.");
                }

            }
        } catch (RemoteException ex) {
            Logger.getLogger(UtilidadesFirmaElectronica.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServicioCodefacException(ex.getMessage());
        }

    }
}
