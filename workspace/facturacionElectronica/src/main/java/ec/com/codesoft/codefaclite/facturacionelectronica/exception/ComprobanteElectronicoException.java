/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.exception;

/**
 *
 * @author
 */
public class ComprobanteElectronicoException extends Exception {
    
    public static final int ERROR_COMPROBANTE=1;
    public static final int ERROR_ENVIO_CLIENTE=2;
    
    private String etapa;
    private Integer tipoError;
    
    public ComprobanteElectronicoException(ComprobanteElectronicoException cee)
    {
        super(cee.getMessage());
        this.etapa=cee.etapa;
        this.tipoError=cee.tipoError;
    }
    
    public ComprobanteElectronicoException(String msg,String etapa,int error) 
    {
        super(msg);
        this.etapa=etapa;
        this.tipoError=error;
    }

    public String getEtapa() {
        return etapa;
    }

    public Integer getTipoError() {
        return tipoError;
    }
    
    public String obtenerErrorFormato()
    {
        return"Etapa: " + getEtapa() + "\n" + getMessage();
    }
    
    

    
    
}