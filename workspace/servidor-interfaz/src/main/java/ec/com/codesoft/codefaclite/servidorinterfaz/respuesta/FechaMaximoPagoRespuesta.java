/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.respuesta;

import java.io.Serializable;

/**
 *
 * @author
 */
public class FechaMaximoPagoRespuesta implements Serializable{
    
    public Integer diasRestantes;
    public EstadoEnum estadoEnum;
    
    public String mensajePagoCerca()
    {
        return "El sistema registra valores pendientes por cancelar , le restan " + diasRestantes + " días para usar el sistema,\n Si no cancela los valores pendientes el sistema automáticamente se bloqueará .";
    }
    
    public String mensajeFechaPagoSuperada()
    {
        return "El sistema detecta valores pendientes de pago y no se puede abrir\\n Por favor cancele los valores pendientes para continuar con el servicio.";
    }
    
    public enum EstadoEnum
    {
        /**
         * Estado cuando no esta regitrado ningun pago
         */
        SIN_VALORES_PENDIENTES,
        /**
         * Estado para indicar que ya se paso la fecha de pago
         */
        FECHA_PAGO_SUPERADA,
        /**
         * Estado para identificar que en pocos dias tiene que cancelar un valor
         */
        PROXIMO_PAGO_CERCA,
        /**
         * Estado que indica que tiene valores pendientes pero no en un tiempo tan proximo
         */
        VALORES_PENDIENTES,
        
    }
}
