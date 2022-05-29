/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.util;


import ec.com.codesoft.codefaclite.facturacionelectronica.util.sri.AutoridadesCertificantes;
import ec.com.codesoft.codefaclite.facturacionelectronica.util.sri.X500NameGeneral;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DERSequence;
import java.util.List;
import org.bouncycastle.x509.extension.X509ExtensionUtil;
import org.bouncycastle.asn1.DERObject;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DEROctetString;

/**
 * TODO: Hacer una metodo que me devuelva todos los OID 
 * Objetos Identificadores Únicos de los Certificados

 * @author CARLOS_CODESOFT
 */
public class X509Utils
{
    public static final int digitalSignature = 0;
    public static final int nonRepudiation = 1;
    public static final int keyEncipherment = 2;
    public static final int dataEncipherment = 3;
    public static final int keyAgreement = 4;
    public static final int keyCertSign = 5;
    public static final int cRLSign = 6;
    
    public static String getExtensionIdentifier(final X509Certificate cert) throws IOException 
    {
        return getExtensionIdentifier(cert, obtenerOidAutoridad(cert));
    }
    
    public static String getExtensionIdentifier(final X509Certificate cert, final String oid) throws IOException {
        String id = null;
        DERObject derObject = null;
        final byte[] extensionValue = cert.getExtensionValue(oid);
        //final byte[] extensionValue = cert.getExtensionValue("1.3.6.1.4.1.37947.3.9");
        if (cert.getIssuerDN().toString().contains(AutoridadesCertificantes.CONSEJO_JUDICATURA.getCn())) {
            try {
                derObject = buscarRucConsejoJudicatura(cert, oid);
            }
            catch (CertificateParsingException ex) {
                Logger.getLogger(X509Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (extensionValue != null) {
            derObject = toDERObject(extensionValue);
            if (derObject instanceof DEROctetString) {
                final DEROctetString derOctetString = (DEROctetString)derObject;
                derObject = toDERObject(derOctetString.getOctets());
            }
        }
        if (derObject != null) {
            id = derObject.toString();
        }
        else {
            id = null;
        }
        return id;
    }
    
    
    
    public static DERObject buscarRucConsejoJudicatura(final X509Certificate cert, final String oid) throws CertificateParsingException {
        final DERObject derObject = null;
        final Collection<List> coleccionDatosAlternativos = (Collection<List>)X509ExtensionUtil.getSubjectAlternativeNames(cert);
         System.out.println("============== FIRMA FIRMA ================================");
        for (final List<Object> listaDatosAlternativo : coleccionDatosAlternativos) {           
            for (final Object datoAlternativo : listaDatosAlternativo) {
                if (datoAlternativo instanceof DERSequence) {
                    final DERSequence datoDERSequence = (DERSequence)datoAlternativo;
                    
                    System.out.println(datoDERSequence);
                    
                    final DERObjectIdentifier derObjectIdentifier = (DERObjectIdentifier)datoDERSequence.getObjectAt(0);
                    if (derObjectIdentifier.toString().equals(oid)) {
                        final DERTaggedObject derTaggedObject = (DERTaggedObject)datoDERSequence.getObjectAt(1);
                        return derTaggedObject.getObject().toASN1Object();
                    }
                    continue;
                }
            }
            
        }
        return derObject;
    }
    
    private static void impimirDatosDelCertificado()
    {
    
    }
    
    public static DERObject toDERObject(final byte[] data) throws IOException {
        final ByteArrayInputStream inStream = new ByteArrayInputStream(data);
        final ASN1InputStream derInputStream = new ASN1InputStream((InputStream)inStream);
        return derInputStream.readObject();
    }
    
    public static String obtenerOidAutoridad(final X509Certificate certificado) {
        String oidRaiz = null;
        final X500NameGeneral x500emisor = new X500NameGeneral(certificado.getIssuerDN().getName());
        final String nombreAutoridad = x500emisor.getCN();
        if (nombreAutoridad.contains(AutoridadesCertificantes.BANCO_CENTRAL.getCn())) {
            oidRaiz = AutoridadesCertificantes.BANCO_CENTRAL.getOid();
        }
        else if (nombreAutoridad.contains(AutoridadesCertificantes.ANF.getCn())) {
            oidRaiz = AutoridadesCertificantes.ANF.getOid();
        }
        else if (nombreAutoridad.contains(AutoridadesCertificantes.SECURITY_DATA.getCn())) {
            oidRaiz = AutoridadesCertificantes.SECURITY_DATA.getOid();
        }
        else if (nombreAutoridad.contains(AutoridadesCertificantes.SECURITY_DATA_SUB_1.getCn())) {
            oidRaiz = AutoridadesCertificantes.SECURITY_DATA_SUB_1.getOid();
        }
        else if (nombreAutoridad.contains(AutoridadesCertificantes.CONSEJO_JUDICATURA.getCn())) {
            oidRaiz = AutoridadesCertificantes.CONSEJO_JUDICATURA.getOid().concat(".1");
        }
        else if (nombreAutoridad.contains(AutoridadesCertificantes.ANF_ECUADOR_CA1.getCn())) {
            oidRaiz = AutoridadesCertificantes.ANF_ECUADOR_CA1.getOid();
        }
        oidRaiz = oidRaiz.concat(".3.11");
        return oidRaiz;
    }
    
}
