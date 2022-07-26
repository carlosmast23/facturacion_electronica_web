/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FacturaComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.util.ComprobantesElectronicosUtil;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author
 */
public class ClaveAcceso implements Serializable{
    
    public String clave;
    
    public String fecha;
    public String tipoComprobante;
    public String identificacion;
    public String tipoAmbiente;
    public String valorDefecto;
    public String puntoEstablecimiento;
    public String puntoEmision;
    public String secuencial;
    public String codigoBatch;

    public ClaveAcceso(String clave) {
        this.clave = clave;
        this.construirDatos();
    }
    
   private void construirDatos()
   {
       String claveTemporal=this.clave;
       this.fecha=claveTemporal.substring(0,8);
       this.tipoComprobante=claveTemporal.substring(8,10);
       this.identificacion=claveTemporal.substring(10,23);
       //this.tipoAmbiente=claveTemporal.substring(0,1);
       //this.valorDefecto=claveTemporal.substring(0,6);
       this.puntoEstablecimiento=claveTemporal.substring(24,27);
       this.puntoEmision=claveTemporal.substring(27,30);
       this.secuencial=claveTemporal.substring(30,39);
       //this.codigoBatch=claveTemporal.substring(0,8);
       
   }
   
   public Class getClassTipoComprobante()
   {    
       return ComprobanteEnum.getEnumByCodigo(tipoComprobante).getClase();
   }
   
   public ComprobanteEnum getTipoComprobante()
   {    
       return ComprobanteEnum.getEnumByCodigo(tipoComprobante);
   }
   
   public Integer getSecuencialInteger()
   {
       return Integer.parseInt(secuencial);
   }
    
    
    public String obtenerClaveAcceso() {
        Vector<String> claveAcceso = new Vector<>();
        //SimpleDateFormat formateador = new SimpleDateFormat("ddmmyyyy");
        /*String fechaFormat = ComprobantesElectronicosUtil.formatSimpleDate(comprobante.getFechaEmision());
        claveAcceso.add(fechaFormat);

        claveAcceso.add(getTipoComprobante());

        //String identificacionFormat=UtilidadesTextos.llenarCarateresDerecha(comprobante.getIdentificacion(),12, "0");
        claveAcceso.add(comprobante.getIdentificacion());

        claveAcceso.add(getTipoCodigoAmbiente());*/

        /**
         * Valor por defecto serie que no se que se pone
         */
        claveAcceso.add("001001");

        /**
         * Secuendial del comprobante
         */
        //String secuencialFormat = UtilidadesTextos.llenarCarateresIzquierda(comprobante.getInformacionTributaria().getSecuencial(), 9, "0");
        //claveAcceso.add(secuencialFormat);

        /**
         * Codigo numerico , que paree que sirve cuando se procesan archivos en
         * bach y por defecto se pone con 0
         */
        claveAcceso.add("00000000");

        /**
         * Clave del tipo de emision, para el metodo offline solo existe el modo
         * 1 que significa modo normal antes existia el modo contingencia
         */
        claveAcceso.add(ComprobanteElectronico.MODO_FACTURACION_NORMAL);

        /**
         * Digito verificador
         */
        String digito = UtilidadesTextos.calcularModulo11(UtilidadesTextos.castVectorToString(claveAcceso));
        claveAcceso.add(digito);

        return UtilidadesTextos.castVectorToString(claveAcceso);
    }
    
    public String getPreimpreso()
    {
        return this.puntoEstablecimiento+"-"+this.puntoEmision+"-"+this.secuencial;
    }
    
}
