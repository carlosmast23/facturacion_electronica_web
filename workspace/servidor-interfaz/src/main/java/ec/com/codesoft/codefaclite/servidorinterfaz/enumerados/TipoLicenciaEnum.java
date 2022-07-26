/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;


/**
 *
 * @author
 */
public enum TipoLicenciaEnum {
    /**
     * Licencia gratuita por defecto para los usuario gratuitos
     */
    GRATIS("f","GRATIS","1","1"),
    /**
     * Licencia premiun para usuario de pago
     */
    PRO("p","PREMIUM","1","ilimitados");
    
    private String letra;
    private String nombre;
    private String numeroMaquinas;
    private String numeroUsuarios;

    private TipoLicenciaEnum(String letra, String nombre, String numeroMaquinas, String numeroUsuarios) {
        this.letra = letra;
        this.nombre = nombre;
        this.numeroMaquinas = numeroMaquinas;
        this.numeroUsuarios = numeroUsuarios;
    }
    
    
    
    public static TipoLicenciaEnum getEnumByLetra(String letra)
    {

        for (TipoLicenciaEnum enumerador : TipoLicenciaEnum.values())
        {
            if(enumerador.letra.equals(letra))
            {
                return enumerador;
            }
        }
        return null;
    }
    
    public static TipoLicenciaEnum getEnumByNombre(String nombre)
    {

        for (TipoLicenciaEnum enumerador : TipoLicenciaEnum.values())
        {
            if(enumerador.nombre.equals(nombre))
            {
                return enumerador;
            }
        }
        return null;
    }

    public String getLetra() {
        return letra;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumeroMaquinas() {
        return numeroMaquinas;
    }

    public String getNumeroUsuarios() {
        return numeroUsuarios;
    }
    
    
    
    
    
    
}
