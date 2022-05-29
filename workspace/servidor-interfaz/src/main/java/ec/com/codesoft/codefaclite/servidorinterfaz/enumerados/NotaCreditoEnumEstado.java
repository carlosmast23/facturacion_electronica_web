/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

/**
 *
 * @author Carlos
 */
public enum NotaCreditoEnumEstado{
    /**
     * Estado cuando se graba la factura en la base de datos pero no esta autorizado en el SRI
     */    
    SIN_AUTORIZAR("S"),
    /**
     * Estado por defecto cuando se graba la factura
     */
    TERMINADO("T"),
    /**
     * Estado cuando la nota de credito fue anulada por una nota de credito
     */
    ANULADO("A");

    private NotaCreditoEnumEstado(String estado) {
        this.estado = estado;
    }
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
    public static Enum getEnum(String estado,Class clase)
    {
        clase.cast(clase);
        for (NotaCreditoEnumEstado enumerador : NotaCreditoEnumEstado.values())
        {
            if(enumerador.getEstado().equals(estado))
            {
                return enumerador;
            }
        }
        return null;
    }
}
