/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 *
 * @auhor
 */
public enum DiaEnum {
    LUNES(1,"Lunes"),
    MARTES(2,"Martes"),
    MIERCOLES(3,"Miercoles"),
    JUEVES(4,"Jueves"),
    VIERNES(5,"Viernes"),
    SABADO(6,"Sábado"),
    DOMINGO(7,"Domingo"),
    ;

    private DiaEnum() {
    }

    private DiaEnum(Integer numero, String nombre) {
        this.numero = numero;
        this.nombre = nombre;
    }
        
    private Integer numero;
    private String nombre;

    public Integer getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }
    
    public static DiaEnum buscarPorNumero(Integer numero)
    {
        for (DiaEnum diaEnum : DiaEnum.values()) 
        {
            if(diaEnum.getNumero().equals(numero))
            {
                return diaEnum;
            }
        }
        return null;
    }
    
}
