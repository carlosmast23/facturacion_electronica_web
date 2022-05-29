/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 * Enum que permite distinguir en los formularios en que tipo de estado nos encontramos
 * si podemos grabar o editar
 * @author Carlos
 */
public enum EstadoFormEnum {
    GRABAR("grabar","G"),
    EDITAR("editar","E");
    
    private final static String SIGNO_APERTURA="[";
    private final static String SIGNO_CIERRE="]";
    
    private String nombre;
    private String letra;

    private EstadoFormEnum(String nombre,String letra) {
        this.nombre = nombre;
        this.letra=letra;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLetra() {
        return letra;
    }
    
    
    
    public String construirFormato(String nombreFormulario)
    {
        return nombreFormulario+" "+" "+SIGNO_APERTURA+nombre+" "+SIGNO_CIERRE;
    }
    
    public static EstadoFormEnum getByLetra(String letra)
    {
        for (EstadoFormEnum value : EstadoFormEnum.values()) {
            if(value.getLetra().equals(letra))
            {
                return value;
            }
        }
        return null;
    }
    
}
