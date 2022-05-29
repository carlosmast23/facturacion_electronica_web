/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.test;

import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ColumnaDialogo;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfacesPropertisFindWeb;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.QueryDialog;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import java.util.Vector;

/**
 *
 * @author Carlos
 */
public class DialogTest  implements InterfaceModelFind<Empleado>,InterfacesPropertisFindWeb {

    public Vector<ColumnaDialogo> getColumnas() {
        return new Vector<ColumnaDialogo>();
    }

    public QueryDialog getConsulta(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void agregarObjeto(Empleado t, Vector dato) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Vector<String> getNamePropertysObject() {
        return new Vector<String>();
    }
    
}
