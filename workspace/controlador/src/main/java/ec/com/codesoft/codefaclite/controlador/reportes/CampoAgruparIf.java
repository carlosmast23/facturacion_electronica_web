/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.reportes;

import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ReporteFacturaData;

/**
 *
 * @author CARLOS_CODESOFT
 */
/**
 * Interfaz que tiene que tener como propiedad en los ENUMS que van a establecer como se debe ordenar los enums
 * agrupados
 */
public interface CampoAgruparIf {

    public String obtenerCampoAgrupar(AgrupadoReporteIf dato);
};
