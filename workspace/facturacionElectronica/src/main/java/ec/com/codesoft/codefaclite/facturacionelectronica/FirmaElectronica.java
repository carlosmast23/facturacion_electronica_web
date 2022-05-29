/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica;

import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.util.X509Utils;
import ec.com.codesoft.codefaclite.facturacionelectronica.util.sri.ValidacionBasica;
import es.mityc.firmaJava.libreria.utilidades.UtilidadTratarNodo;
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.FirmaXML;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.javasign.EnumFormatoFirma;
import es.mityc.javasign.pkstore.IPKStoreManager;
import es.mityc.javasign.pkstore.keystore.KSStore;
import es.mityc.javasign.xml.refs.InternObjectToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertStoreException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;

/**
 *
 * @author Carlos
 */
public class FirmaElectronica {
    private String almacenCertificadoClave;
    private String claveFirma;

    public FirmaElectronica(String almacenCertificadoClave) {
        this.almacenCertificadoClave = almacenCertificadoClave;
    }

    public FirmaElectronica(String almacenCertificadoClave, String claveFirma) {
        this.almacenCertificadoClave = almacenCertificadoClave;
        this.claveFirma = claveFirma;
    }

    public static boolean FirmaVerificar(String rutaAlmacenCertificado,String passwordAlmacenCertificado)
    {
        boolean resultado=false;
        FileInputStream file=null;
         try {
            KeyStore clave=null;
            clave=KeyStore.getInstance("PKCS12");
            file=new FileInputStream(rutaAlmacenCertificado);
            clave.load(file,
                    passwordAlmacenCertificado.toCharArray());       
            //file.close();
            resultado= true;
        } catch (KeyStoreException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            if(ex.getMessage().equals("keystore password was incorrect"))
            {
               resultado= false;
            }
            
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         /**
          * Cerrar el archivo si esta abierto
          */
        if (file != null) {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(FirmaElectronica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return resultado;
    }
    
    public Document firmar(String recursoParaFirmar)throws ComprobanteElectronicoException
    {
        try {
            IPKStoreManager keyStore= obtenerAlmacenFirma(almacenCertificadoClave, claveFirma);
            
            //Validar cunado el sistema no puede encontrar el archivo de la firma
            if(keyStore==null)
            {
                throw new Exception(
                        "El sistema no puede encontrar el archivo de la firma");
            }
            
            //String almacenAlias = getAlias(keyStore);
            X509Certificate certificado = null;
            try {
                //certificado = (X509Certificate) keyStore
                //        .getCertificate(almacenAlias);
                certificado=getFirstCertificate(keyStore);
                if (certificado == null) {
                    throw new Exception(
                            "No se ha encontrado certificado para firmar");
                }
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
            
            
            PrivateKey llavePrivada = null;
            
            llavePrivada = (PrivateKey) keyStore.getPrivateKey(certificado);

            Provider proveedor = keyStore.getProvider(certificado);
            DataToSign datosParaFirmar = createDataToSign(recursoParaFirmar);
            
            FirmaXML firma = new FirmaXML();
            Document documentoFirmado = null;
            
            //===================================//
            //          CODIGO TEMPORAL
            //TODO: Terminar luego de implementar para evitar errores de rucs que no corresponden
            // o firmas que tienen el campo de RUC vacio
            //===================================//
            //String ruc=X509Utils.getExtensionIdentifier(certificado);
            
            
            try {
                Object[] resultado = firma.signFile(certificado, datosParaFirmar,
                        llavePrivada, proveedor);
                documentoFirmado = (Document) resultado[0];
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new Exception(
                        "Se ha producido un error al firmar el documento");
            }
            return documentoFirmado;
            //String rutaArchivoFirmado = getRutaSalida() + File.separatorChar
            //        + getSignatureFileName();
            //guardarDocumento(documentoFirmado,"dir");
        } catch (Exception ex) {
            Logger.getLogger(FirmaElectronica.class.getName()).log(Level.SEVERE, null, ex);
            throw new ComprobanteElectronicoException(ex.getMessage(),"Firma",ComprobanteElectronicoException.ERROR_COMPROBANTE);
        }
        //return null;
    }
    
    /*private void guardarDocumento(Document documento, String rutaArchivo)
            throws Exception {
        File file = new File(rutaArchivo);
        //crear toda la ruta si no existe
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            //file.mkdir();
        }
        
        
        FileOutputStream flujoSalida = null;
        try {
            try {
                flujoSalida = new FileOutputStream(rutaArchivo);
                UtilidadTratarNodo.saveDocumentToOutputStream(documento,
                        flujoSalida, true);
            } finally {
                flujoSalida.close();
            }
        } catch (FileNotFoundException e) {
            throw new Exception("Error al salvar el documento");
        }
    }*/
    
    protected DataToSign createDataToSign(String recursoParaFirmar) throws Exception {
        DataToSign datosParaFirmar = new DataToSign();
        datosParaFirmar.setXadesFormat(EnumFormatoFirma.XAdES_BES);
        datosParaFirmar.setEsquema(XAdESSchemas.XAdES_132);
        datosParaFirmar.setXMLEncoding("UTF-8");
        datosParaFirmar.setEnveloped(true);
        datosParaFirmar.addObject(new ObjectToSign(new InternObjectToSign("comprobante"), "contenido comprobante", null, "text/xml", null));
        datosParaFirmar.setParentSignNode("comprobante");
        try {
            Document documentoParaFirmar = obtenerDocumento(recursoParaFirmar);
            datosParaFirmar.setDocument(documentoParaFirmar);
        } catch (Exception e) {        
            e.printStackTrace();
            //Creo una excepcion más entendible de facil interpretación
            if(e.getMessage().equals("Error al interpretar el documento"))
            {
                throw new Exception("El documento que intenta firmar tiene CARACTERES NO COMPATIBLES\nPosible Solución: Revisar signos o caracteres especiales que puedan causar conflicto y modificar el texto ");
            }
            else
            {
                throw new Exception(e.toString());
            }
        }
        return datosParaFirmar;
    }
    
    protected Document obtenerDocumento(String recurso) throws Exception {
		Document documento = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		File archivo = new File(recurso);
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			documento = db.parse(archivo);
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
            throw new Exception("Error al interpretar el documento");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("Error al interpretar el documento");
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            throw new Exception("Error al interpretar el documento");
        }
		return documento;
	}
    
    private String getAlias(KeyStore keyStore) throws Exception {
        String almacenAlias = null;
        Enumeration<String> nombres;
        try {
            nombres = keyStore.aliases();
            while (nombres.hasMoreElements()) {
                String almacenAliasTemporal = nombres.nextElement();
                if (keyStore.isKeyEntry(almacenAliasTemporal)) {
                    almacenAlias = almacenAliasTemporal;
                }
            }
        } catch (KeyStoreException e) {
            throw new Exception(e.toString());
        }
        return almacenAlias;
    }
    
    /*
    private KeyStore obtenerAlmacenFirma(String rutaAlmacenCertificado,String passwordAlmacenCertificado)
    {
        try {
            KeyStore clave=null;
            clave=KeyStore.getInstance("PKCS12");
            FileInputStream file=new FileInputStream(rutaAlmacenCertificado);
            clave.load(new FileInputStream(rutaAlmacenCertificado),
                    passwordAlmacenCertificado.toCharArray());            
            return clave;
        } catch (KeyStoreException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }*/
    
    private IPKStoreManager obtenerAlmacenFirma(String rutaAlmacenCertificado,String passwordAlmacenCertificado) throws ComprobanteElectronicoException
    {
        try {
            IPKStoreManager storeManager = null;
            KeyStore clave=null;
            clave=KeyStore.getInstance("PKCS12");
            FileInputStream file=new FileInputStream(rutaAlmacenCertificado);
            clave.load(new FileInputStream(rutaAlmacenCertificado),
                    passwordAlmacenCertificado.toCharArray());     
            
            storeManager = new KSStore(clave, new PassStoreKS(passwordAlmacenCertificado));
            return storeManager;
        } catch (KeyStoreException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
            throw new ComprobanteElectronicoException("No se puede encontrar el archivo de la firma","Firma",ComprobanteElectronicoException.ERROR_COMPROBANTE);
        } catch (IOException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(ComprobanteElectronicoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
        /**
     * Metodo para corregir problema con el certificado x509 correcto que exige el
     * Sri desde el 08/01/2020
     *
     * @author CARLOS SANCHEZ
     * @param storeManager
     * @return
     * @throws Exception
     * @Referencia
     * https://github.com/rolandopalermo/veronica-open-api/commit/8958028270e52359290e86902971bd062c2d9a9c
     * Notas: Se debe comparar que la firma devuelta sea digitalSignature
     * KeyUsage ::= BIT STRING { digitalSignature (0), nonRepudiation (1),
     * keyEncipherment (2), dataEncipherment (3), keyAgreement (4), keyCertSign
     * (5), cRLSign (6), encipherOnly (7), decipherOnly (8) }
     */
    private X509Certificate getFirstCertificate(final IPKStoreManager storeManager) throws Exception {
        try {
            List<X509Certificate> certs = null;
            certs = storeManager.getSignCertificates();
            if ((certs == null) || (certs.size() == 0)) {
                throw new Exception("La lista de certificados se encuentra vacía.");
            }
            for (X509Certificate cert : certs) {
                if (cert.getKeyUsage()[0]) { //Compara con el campo 0 porque si esta en true entonces es digitalSignature
                    return cert;
                }
            }
            //X509Certificate certificate = certs.get(1).getKe;
            //return certificate;
        } catch (CertStoreException ex) {
            throw new Exception(ex.getMessage());
        }
        return null;
    }
}
