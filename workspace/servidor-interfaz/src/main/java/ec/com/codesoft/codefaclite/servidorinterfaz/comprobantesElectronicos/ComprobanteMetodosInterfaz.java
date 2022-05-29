/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlos
 */
public interface ComprobanteMetodosInterfaz<T> {

    public abstract String getCodigoComprobante();

    public abstract String getSecuencial();

    public abstract Map<String, String> getMapAdicional();

    public abstract List<String> getCorreos();

    //public abstract List<InformacionAdicional> getInformacionAdicional();
    /**
     * Implementar el modelo del comprobante exeptuando los valores de
     * informacion tributaria que se implementa solo
     *
     * @return
     */
    public abstract T getComprobante();
}
