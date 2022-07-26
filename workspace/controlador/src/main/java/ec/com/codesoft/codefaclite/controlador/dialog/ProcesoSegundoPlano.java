/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.dialog;

import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;

/**
 *
 * @author
 */
public interface ProcesoSegundoPlano {
    public void procesar() throws ExcepcionCodefacLite;
    public String getMensaje();
}
