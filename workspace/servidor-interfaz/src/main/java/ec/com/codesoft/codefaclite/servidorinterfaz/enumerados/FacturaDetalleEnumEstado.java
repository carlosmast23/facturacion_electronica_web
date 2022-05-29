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
public enum FacturaDetalleEnumEstado {
    /**
     * Estado por defecto cuando se graba la factura
     */
    FACTURADO("F"),
    /**
     * Estado cuando el detalle de la factura fue anulada por una nota de credito
     */
    ANULADO("A");
    
     private FacturaDetalleEnumEstado(String estado) {
        this.estado = estado;
    }
    private String estado;
}
