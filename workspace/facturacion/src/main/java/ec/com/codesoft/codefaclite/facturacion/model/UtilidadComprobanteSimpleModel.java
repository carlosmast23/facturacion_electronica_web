/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;

/**
 *
 * @author Carlos
 */
public class UtilidadComprobanteSimpleModel extends UtilidadComprobanteAvanzadoModel{

    @Override
    protected void iniciarComponentes() {
        /**
         * Cargar las carpetas disponibles de los comprobantes
         */
        getCmbCarpetaComprobante().removeAllItems();
        getCmbCarpetaComprobante().addItem(ComprobanteElectronicoService.CARPETA_FIRMADOS_SIN_ENVIAR);
        getCmbCarpetaComprobante().addItem(ComprobanteElectronicoService.CARPETA_ENVIADOS_SIN_RESPUESTA);
        cargarEtapas();
        
        getCmbEstadoLimiteProcesar().setVisible(false);
        getLblEtapaFinalProcesar().setVisible(false);
    }
    
}
