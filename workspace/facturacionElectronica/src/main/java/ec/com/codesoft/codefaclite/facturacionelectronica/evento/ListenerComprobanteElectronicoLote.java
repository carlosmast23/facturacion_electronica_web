/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.evento;

import autorizacion.ws.sri.gob.ec.Autorizacion;
import ec.com.codesoft.codefaclite.facturacionelectronica.ClaveAcceso;
import ec.com.codesoft.codefaclite.facturacionelectronica.exception.ComprobanteElectronicoException;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import java.util.List;

/**
 *
 * @author
 */
public interface ListenerComprobanteElectronicoLote {
    public abstract void iniciado();     
    public abstract void clavesGeneradas(List<ClaveAcceso> listaClaves); 
    public abstract void datosAutorizados(List<Autorizacion> autorizaciones);
    public abstract void procesando(int etapa);
    public abstract void error(ComprobanteElectronicoException cee);
    public abstract void termino(List<Autorizacion> autorizaciones);
}
