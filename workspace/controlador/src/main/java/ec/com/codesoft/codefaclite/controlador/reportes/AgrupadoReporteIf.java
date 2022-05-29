/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.reportes;

/**
 * Clase que debe implementar la clase data que va a mandar la lista de datos al reporte
 * @author CARLOS_CODESOFT
 */
public interface AgrupadoReporteIf {

    /**
     * Este campo sirve para setear en el modelo el campo que va a agrupar
     * @param campoAgrupado 
     */
    public void setCampoAgrupado(String campoAgrupado);

    /**
     * Este campo sirve para obtener del modelo el campo que va a agrupar
     * @param campoAgrupado 
     */
    public String getCampoAgrupado();
    
    /**
     * Este metodo sirve para obtener mediante el nombre del campo , el resultado que se quiere agrupar
     * Se recomienda usar un map y campos estaticos para obtener los datos
     * @return 
     */
    public String getValorCampoAgrupar(EnumReporteAgruparIf enumReporteAgruparIf);
    
};
