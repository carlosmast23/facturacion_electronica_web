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
public enum MesEnum {
    ENERO(1,"enero"),
    FEBRERO(2,"febrero"),
    MARZO(3,"marzo"),
    ABRIL(4,"abril"),
    MAYO(5,"mayo"),
    JUNIO(6,"junio"),
    JULIO(7,"julio"),
    AGOSTO(8,"agosto"),
    SEPTIEMBRE(9,"septiembre"),
    OCTUBRE(10,"octubre"),
    NOVIEMBRE(11,"noviembre"),
    DICIEMBRE(12,"diciembre");

    private MesEnum(int numero,String nombre) {
        this.numero = numero;
        this.nombre=nombre;
    }

    private String nombre;
    
    private int numero;

    @Override
    public String toString() {
        return nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumero() {
        return numero;
    }
    
    
    
    public static MesEnum obtenerPorNumero(Integer numero)
    {
        for (MesEnum mesEnum : MesEnum.values()) 
        {
            if(numero.equals(mesEnum.getNumero()))
            {
                return mesEnum;
            }
        }
        return null;
    }
    
    

    
}
