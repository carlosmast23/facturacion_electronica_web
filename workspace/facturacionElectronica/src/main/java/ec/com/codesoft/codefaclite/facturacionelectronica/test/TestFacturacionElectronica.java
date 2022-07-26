/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.test;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.ServicioSri;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.InformacionFactura;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionTributaria;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.ws.recepcion.Mensaje;
import es.mityc.firmaJava.libreria.xades.FirmaXML;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author
 */
public class TestFacturacionElectronica {
    public static void main(String[] args) {
        try {
            //ComprobanteElectronico comprobante=crearFactura();            
            //servicio.procesarComprobante();
            
            /**
             * Enviando documento al SRI
             */
            ServicioSri servicioSri=new ServicioSri("","");
            if(servicioSri.verificarConexionRecepcion())
            {
                System.out.println("Existe conexion");
                if(servicioSri.enviar())
                {
                    System.out.println("Documento enviados");
                }
                else
                {
                    for (Mensaje mensaje : servicioSri.getMensajes()) {
                        System.out.println(mensaje.getIdentificador()+"-"+mensaje.getMensaje()+"-"+mensaje.getInformacionAdicional());
                    }
                }
            }
        } catch (ComprobanteElectronicoException ex) {
            Logger.getLogger(TestFacturacionElectronica.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public static ComprobanteElectronico crearFactura()
    {
        FacturaComprobante factura=new FacturaComprobante();
        InformacionTributaria infoTributaria=new InformacionTributaria();
        infoTributaria.setAmbiente("01");
        infoTributaria.setClaveAcceso("124332231893891238918293");
        infoTributaria.setCodigoDocumento("01");
        infoTributaria.setDirecionMatriz("sangolqui ecuador");
        infoTributaria.setEstablecimiento("01");
        infoTributaria.setNombreComercial("Codesoft");
        infoTributaria.setPuntoEmision("02");
        infoTributaria.setRazonSocial("Codesoft");
        infoTributaria.setRuc("1724218951");
        infoTributaria.setSecuencial("00000001");
        infoTributaria.setTipoEmision("02");
        
        factura.setInformacionTributaria(infoTributaria);
        
        InformacionFactura informacionFactura=new InformacionFactura();
        java.util.Date fechaHoy=new java.util.Date();
        informacionFactura.setFechaEmision(ComprobantesElectronicosUtil.dateToString(new Date(fechaHoy.getTime())));
        
        informacionFactura.setIdentificacionComprador("17242131515");
        factura.setInformacionComprobante(informacionFactura);
        
        return factura;
    }
}
