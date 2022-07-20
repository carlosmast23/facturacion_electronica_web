/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.respuesta;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @auhor
 */
public class RotacionInventarioRespuesta {
    public Producto producto;
    public BigDecimal cantidadVentas;
    public BigDecimal stockPromedio;
    
    /**
     * Se supone que solo deberia grabar en esta pantalla los registros que afectaron al inventario con ingresos
     */
    public BigDecimal cantidadStockCompras;
    //public BigDecimal indiceRotacionInventario;
    //public BigDecimal 
    
    public BigDecimal getIndiceRotacionInventario()
    {
        return cantidadVentas.divide(stockPromedio,2,BigDecimal.ROUND_HALF_UP);
    }
    
    
    public BigDecimal getPorcentajeRotacionInventario()
    {
        BigDecimal indiceRotacionInventario=getIndiceRotacionInventario();
        return cantidadStockCompras.divide(indiceRotacionInventario,2,BigDecimal.ROUND_HALF_UP);
    }
}
