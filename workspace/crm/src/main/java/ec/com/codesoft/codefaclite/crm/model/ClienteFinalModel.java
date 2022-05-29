/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;

/**
 *
 * @author Carlos
 */
public class ClienteFinalModel extends ClienteModel{ 

    public ClienteFinalModel() {
        super();
        getCmbTipoOperador().setSelectedItem(OperadorNegocioEnum.PROVEEDOR);
    }
    
}
