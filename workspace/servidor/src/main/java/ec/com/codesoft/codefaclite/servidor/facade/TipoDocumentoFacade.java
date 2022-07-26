/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.TipoDocumento;
import java.io.Serializable;

/**
 *
 * @author
 */
public class TipoDocumentoFacade extends AbstractFacade<TipoDocumento> implements Serializable{

    public TipoDocumentoFacade() {
        super(TipoDocumento.class);
    }
    
}
