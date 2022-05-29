/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos;

import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.ws.recepcion.Comprobante;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public interface ComprobanteDataInterface {
    public String getCodigoComprobante();

    public String getPuntoEmision();
    
    public String getEstablecimiento();
    
    public String getSecuencial();
    
    //public ComprobanteEntity.TipoEmisionEnum getTipoEmisionEnum();


    public Map<String, String> getMapAdicional();


    public List<String> getCorreos();


    public ComprobanteElectronico getComprobante();

    public ListenerComprobanteElectronico getListenerComprobanteElectronico();
    
    /**
     * Devuelve el id fisico del comprobante que se esta procesando para cambiar de estados
     */
    public Long getComprobanteId();
    
    public Empresa getEmpresa();
    
    public String getDireccionMatriz();

}
