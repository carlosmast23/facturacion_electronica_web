/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.reportes;


//import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ReporteFacturaData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public abstract class UtilidadReporteJasper {

    public static void obtenerDatosReporteAgrupado(EnumReporteAgruparIf enumReporteAgruparIf, List data) {
        //String titulo = "Ventas "+tipoReporteEnum.getNombre();
        //InputStream path=getReporteAgrupado();
        List<AgrupadoReporteIf> datosProcesar = (ArrayList<AgrupadoReporteIf>) (ArrayList<?>) data;

        procesarDatosReporte(enumReporteAgruparIf, datosProcesar);

        //ordenarListaPorPrecio(data);
        // ReporteCodefac.generarReporteInternalFramePlantilla(path, mapParametrosReportePdf(), datosProcesar, panelPadre,titulo, OrientacionReporteEnum.HORIZONTAL,FormatoHojaEnum.A4);
    }

    /**
     * Metodo principal que me permite llenar el dato de FILTRO y tambien
     * ordenar los datos de acuerdo al parametro para AGRUPAR
     */
    private static void procesarDatosReporte(EnumReporteAgruparIf enumReporteAgruparIf, List<AgrupadoReporteIf> reporteData) {
        llenarDatoAgrupacion(enumReporteAgruparIf, reporteData);
        ordenarAgrupar(reporteData);

    }

    private static void llenarDatoAgrupacion(EnumReporteAgruparIf enumReporteAgruparIf, List<AgrupadoReporteIf> agrupadoReporteIf) {
        for (AgrupadoReporteIf reporteFacturaData : agrupadoReporteIf) {
            String datoAgrupar = enumReporteAgruparIf.getCampoAgrupar().obtenerCampoAgrupar(reporteFacturaData);
            reporteFacturaData.setCampoAgrupado(datoAgrupar);
        }
    }

    /**
     * Metodo que me permite agrupar los datos antes de poder procesar para el
     * reporte
     *
     * @param reporteData
     */
    private static void ordenarAgrupar(List<AgrupadoReporteIf> reporteData) {
        Collections.sort(reporteData, new Comparator<AgrupadoReporteIf>() {
            public int compare(AgrupadoReporteIf obj1, AgrupadoReporteIf obj2) {
                if(obj1.getCampoAgrupado()==null)
                {
                    Logger.getLogger(UtilidadReporteJasper.class.getName()).log(Level.WARNING,"El campo agrupado no esta configurado");
                }
                
                return obj1.getCampoAgrupado().compareTo(obj2.getCampoAgrupado());
            }
        });
    }

}
