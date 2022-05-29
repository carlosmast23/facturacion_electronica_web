/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.general;

/**
 *
 * @author Carlos
 */
public class ParametrosClienteEscritorio {
    
    /**
     * Variable para saber el tipo de cliente que se esta conectado a un cliente de la aplicacion de escritorio
     * Porque de eso depende algunas limitaciones en especial de componentes de callback
     */
    public static TipoClienteSwingEnum tipoClienteEnum=TipoClienteSwingEnum.LOCAL;
    
    /**
     * TODO:Metodo temporal para corregir un problema que sucede con los callback de la factura cuando es ip publica
     * Referencia para saber si el cliente inicio con una ip publica
     */
    public static Integer puertoCallBack=0;
    
    public enum TipoClienteSwingEnum
    {
        LOCAL("Local"),
        REMOTO("Remoto");
        
        private String nombre;

        private TipoClienteSwingEnum(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }
        
        public static TipoClienteSwingEnum buscarPorNombre(String nombre)
        {
            for (TipoClienteSwingEnum valor : TipoClienteSwingEnum.values()) {
                if(valor.getNombre().equals(nombre))
                {
                    return valor;
                }
            }
            return null;
        }
        
    };
}
