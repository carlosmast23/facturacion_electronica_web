/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.respuesta;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.LoginRespuesta;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class EmpresaLicencia {
    public TipoLicenciaEnum tipoLicencia;
    public List<ModuloCodefacEnum> modulosSistema;
    public Integer cantidadUsuarios;
    public String usuarioLicencia;
    
    public Date fechaUltimaValidacion;
    public String pathEmpresa;
    public List<String> alertas;
    
    
    /**
     * Variable que me permite establecer el estado del resultado
     */
    public LoginRespuesta.EstadoLoginEnum estadoEnum;

    public EmpresaLicencia(TipoLicenciaEnum tipoLicencia, List<ModuloCodefacEnum> modulosSistema, Integer cantidadUsuarios, String usuarioLicencia) {
        this.tipoLicencia = tipoLicencia;
        this.modulosSistema = modulosSistema;
        this.cantidadUsuarios = cantidadUsuarios;
        this.usuarioLicencia = usuarioLicencia;
    }

    public EmpresaLicencia() {
        this.estadoEnum=LoginRespuesta.EstadoLoginEnum.ERROR_DESCONOCIDO;
    }
    
   
    

    
    
}
