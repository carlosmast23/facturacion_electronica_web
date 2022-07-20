/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.factura;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteVentaNotaCreditoAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;

/**
 *
 * @auhor
 */
public abstract class FacturaNotaCreditoModelControladorAbstract extends ModelControladorAbstract {
    public SessionCodefacInterface session;
    
    public abstract ComprobanteAdicional crearComprobanteAdicional(String correo, ComprobanteAdicional.Tipo tipoCorreo, ComprobanteAdicional.CampoDefectoEnum campoDefecto);

    public FacturaNotaCreditoModelControladorAbstract(MensajeVistaInterface mensajeVista) {
        super(mensajeVista);
    }
    
    
    public void cargarDatosAdicionales(ComprobanteVentaNotaCreditoAbstract comprobante)
    {
        //Cargar el correo solo cuando exista 
        if (comprobante.getCliente().getCorreoElectronico() != null) {
            
                       
            //Obtiene el campo del correo por defecto sis existe
            FacturaAdicional campoAdicional=(FacturaAdicional) comprobante.obtenerDatoAdicionalPorCampo(ComprobanteAdicional.CampoDefectoEnum.CORREO);
            //Si no existe el campo del correo del cliente lo creo
            
            String correoElectronico=null;
            if(comprobante.getCliente().getCorreoElectronico()!=null && !comprobante.getCliente().getCorreoElectronico().toString().isEmpty())
            {
                correoElectronico=comprobante.getCliente().getCorreoElectronico();
            }

            if (campoAdicional == null) 
            {
                if (correoElectronico != null) 
                {
                    comprobante.addDatoAdicional(crearComprobanteAdicional(correoElectronico, FacturaAdicional.Tipo.TIPO_CORREO, ComprobanteAdicional.CampoDefectoEnum.CORREO));
                    //comprobante.addDatoAdicional(new FacturaAdicional(correoElectronico, FacturaAdicional.Tipo.TIPO_CORREO, ComprobanteAdicional.CampoDefectoEnum.CORREO));
                }
            } 
            else //Si existe el campo del correo del cliente lo edito
            {
                if (correoElectronico != null) 
                {
                    campoAdicional.setValor(correoElectronico);
                }
                else
                {
                    comprobante.getDatosAdicionalesComprobante().remove(campoAdicional);
                }
            }
           

            
            //datosAdicionales.put("email", factura.getCliente().getCorreoElectronico());
        }
        
        //Cargar el numero e celular del cliente
        if (comprobante.getSucursal().getTelefonoCelular() != null) {
            //Obtiene el campo del correo por defecto sis existe
            FacturaAdicional campoAdicional=(FacturaAdicional) comprobante.obtenerDatoAdicionalPorCampo(ComprobanteAdicional.CampoDefectoEnum.CELULAR);
            //Si no existe el campo del correo del cliente lo creo
            
            String numeroCelular=null;
            if(comprobante.getSucursal().getTelefonoCelular()!=null && !comprobante.getSucursal().getTelefonoCelular().toString().isEmpty())
                numeroCelular=comprobante.getSucursal().getTelefonoCelular();
                
            if(campoAdicional==null)
            {
                if(numeroCelular!=null)
                    comprobante.addDatoAdicional(new FacturaAdicional(numeroCelular,FacturaAdicional.Tipo.TIPO_CELULAR,ComprobanteAdicional.CampoDefectoEnum.CELULAR));
            }
            else //Si existe el campo del telefono del cliente lo edito
            {
                if(numeroCelular!=null)
                    campoAdicional.setValor(numeroCelular);
                else
                    comprobante.getDatosAdicionalesComprobante().remove(campoAdicional);
            }                
            
            //datosAdicionales.put("email", factura.getCliente().getCorreoElectronico());
        }
        
    }
}
