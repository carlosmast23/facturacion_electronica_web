
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.main.utilidades.UtilidadLicencia;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
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
public class TestGenerarLicenciaManual {
    
    public static void main(String[] args)
    {
        List<ModuloCodefacEnum> modulosActivos=new ArrayList<ModuloCodefacEnum>();
        //Este orden tiene que ser especifico segun el archivo de los enum porque de otra forma no funciona
        modulosActivos.add(ModuloCodefacEnum.FACTURACION);
        modulosActivos.add(ModuloCodefacEnum.CRM);
        modulosActivos.add(ModuloCodefacEnum.TRANSPORTE);
        modulosActivos.add(ModuloCodefacEnum.SISTEMA);

        String mac="00-26-B6-F1-E3-04";
        
        UtilidadLicencia.crearLicenciaManualConMac("lubricadora",1,TipoLicenciaEnum.PRO, modulosActivos,mac, "C:/Temp/licenciaToshiba");
    }
    
}
