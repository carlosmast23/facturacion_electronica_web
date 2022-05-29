/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.interfaz;

import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteData;
import java.util.List;

/**
 *
 * @author Carlos
 */
public interface InterfaceCallbakClient {
    public void terminoProceso(List<ComprobanteData> comprobantes);
}
