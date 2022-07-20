/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.respuesta;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @auhor
 */
public class FacturaLoteRespuesta implements Serializable{
    
    public List<Factura> procesadosList;
    public List<Error> noProcesadosList;

    public FacturaLoteRespuesta() {
        this.procesadosList=new ArrayList();
        this.noProcesadosList=new ArrayList<Error>();
    }

    
    
    public void agregarFacturaProcesada(Factura factura)
    {
        if(procesadosList==null)
        {
            procesadosList=new ArrayList<Factura>();
        }
        procesadosList.add(factura);
    }
    
    public void agregarFacturaNoProcesada(Error error)
    {
        if(noProcesadosList==null)
        {
            noProcesadosList=new ArrayList<Error>();
        }
        noProcesadosList.add(error);
    }
    
    public String costruirMensajeError()
    {
        String mensaje="Las siguientes facturas tuvieron problemas al procesar:\n ";
        for (Error error : noProcesadosList) 
        {
            mensaje+="Factura: "+error.facturaError.getPreimpreso()+" Error:"+error.error+"\n";
        }
        return mensaje;
    }
    
    
    public static class Error implements Serializable
    {
        public Factura facturaError;
        public String error;

        public Error(Factura facturaError, String error) {
            this.facturaError = facturaError;
            this.error = error;
        }
        
        
    }
    
}
