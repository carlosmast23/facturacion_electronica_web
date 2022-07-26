
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author
 */
public class TestTipoDocumento {
    public static void main(String[] args)
    {
        List<ModuloCodefacEnum> modulosCodefacEnum=new ArrayList<ModuloCodefacEnum>();
        //modulosCodefacEnum.add(ModuloCodefacEnum.FACTURACION);
        System.out.println(TipoDocumentoEnum.obtenerTipoDocumentoPorModulo(ModuloCodefacEnum.FACTURACION,null));
    }    
}
