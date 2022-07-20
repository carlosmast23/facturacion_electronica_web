/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.respuesta;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import java.io.Serializable;

/**
 *
 * @auhor
 */
public class TransferenciaBodegaRespuesta implements Serializable{
    public KardexDetalle kardexDetalle;
    public KardexDetalle kardexDetalleBodegaDestinoTransferencia;
    

    public TransferenciaBodegaRespuesta(KardexDetalle kardexDetalle, KardexDetalle kardexDetalleBodegaDestinoTransferencia) {
        this.kardexDetalle = kardexDetalle;
        this.kardexDetalleBodegaDestinoTransferencia = kardexDetalleBodegaDestinoTransferencia;
    }

    
        
}
