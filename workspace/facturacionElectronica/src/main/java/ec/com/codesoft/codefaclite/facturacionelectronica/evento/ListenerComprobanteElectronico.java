/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.evento;

import autorizacion.ws.sri.gob.ec.Autorizacion;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.FirmaElectronica;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;

/**
 *
 * @author Carlos
 */
public interface ListenerComprobanteElectronico {
    public abstract void termino();
    public abstract void iniciado(ComprobanteElectronico comprobante);
    public abstract void procesando(int etapa,ClaveAcceso clave);
    public abstract void error(ComprobanteElectronicoException cee);
    public abstract void autorizado(Autorizacion documentoAutorizado);
}
