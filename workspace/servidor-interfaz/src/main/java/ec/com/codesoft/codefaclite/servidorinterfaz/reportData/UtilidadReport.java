/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.reportData;

import ec.com.codesoft.codefaclite.servidorinterfaz.result.UtilidadResult;
import java.math.RoundingMode;
import java.util.Vector;

/**
 *
 * @auhor
 */
public class UtilidadReport extends ReportDataAbstract<UtilidadResult>
{

    public UtilidadReport(String tituloReporte) {
        super(tituloReporte);
    }

    @Override
    public String[] getTitulos() {
        return new String[]{
            "Secuencial",
            "Fecha",
            "Razón Social",
            "Identificación",
            "Subtotal",
            "Costo",
            "Utilidad"
        };
    }

    @Override
    public void construirFilaTabla(UtilidadResult dato, Vector<Object> fila) {
        fila.add(dato.getSecuencial());
        fila.add(dato.getFechaEmision());
        fila.add(dato.getRazonSocial());
        fila.add(dato.getIdentificacion());
        fila.add(dato.getSubtotal().setScale(2, RoundingMode.HALF_UP));
        fila.add(dato.getCosto().setScale(2, RoundingMode.HALF_UP));
        fila.add(dato.getUtilidad().setScale(2, RoundingMode.HALF_UP));
    }
    
}
