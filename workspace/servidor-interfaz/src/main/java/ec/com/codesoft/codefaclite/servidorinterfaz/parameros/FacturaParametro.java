/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.parameros;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Prestamo;
import java.io.Serializable;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class FacturaParametro implements Serializable{

    public FacturaParametro(Factura factura, CarteraParametro carteraPrestamo, Prestamo prestamo) {
        this.factura = factura;
        this.carteraPrestamo = carteraPrestamo;
        this.prestamo = prestamo;
    }
    
    
    
    public Factura factura;
    public CarteraParametro carteraPrestamo;
    public Prestamo prestamo;
}
