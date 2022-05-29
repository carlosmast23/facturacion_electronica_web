/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.utilidades;

import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.Licencia;
import ec.com.codesoft.codefaclite.licence.ValidacionLicenciaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Carlos
 */
public class UtilidadLicencia {
    
    public static void crearLicenciaManualConMac(String usuario,Integer cantidadUsuarios,TipoLicenciaEnum tipoLicenciaEnum,List<ModuloCodefacEnum> modulosActivos,String mac,String pathGenerar)
    {
        Licencia licencia=new Licencia();
        licencia.setCantidadClientes(cantidadUsuarios);
        licencia.setUsuario(usuario);
        licencia.setTipoLicenciaEnum(tipoLicenciaEnum);
        licencia.setModulosActivos(modulosActivos);
        
        ValidacionLicenciaCodefac validacion = new ValidacionLicenciaCodefac();
        validacion.setPath(pathGenerar);
        validacion.crearLicenciaMaquina(licencia,mac);           
    }
    
    public static void crearLicenciaManual(String usuario,Integer cantidadUsuarios,TipoLicenciaEnum tipoLicenciaEnum,List<ModuloCodefacEnum> modulosActivos,String pathGenerar)
    {
        Licencia licencia=new Licencia();
        licencia.setCantidadClientes(cantidadUsuarios);
        licencia.setUsuario(usuario);
        licencia.setTipoLicenciaEnum(tipoLicenciaEnum);
        licencia.setModulosActivos(modulosActivos);
        
        ValidacionLicenciaCodefac validacion = new ValidacionLicenciaCodefac();
        validacion.setPath(pathGenerar);
        validacion.crearLicenciaMaquina(licencia);        
        
        
    }
}
