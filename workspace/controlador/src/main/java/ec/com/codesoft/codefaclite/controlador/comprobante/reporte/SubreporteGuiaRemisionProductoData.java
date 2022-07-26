/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobante.reporte;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.DetalleFacturaComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DetalleProductoGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.sessions.factories.SessionFactory;

/**
 *
 * @author
 */
public class SubreporteGuiaRemisionProductoData {
    private String codigoInterno;
    private String nombre;
    private int cantidad;
    private BigDecimal subtotal;

    public SubreporteGuiaRemisionProductoData(String codigoInterno,String nombre, int cantidad, BigDecimal subtotal) {
        this.codigoInterno=codigoInterno;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }
    
    
    
    /**
     * TODO: Optmizar el reporte para ver si puedo hacer la consulta de los nombre, subtotal del producto directamente en la primera 
     * @param detalle
     * @param productoNuevo 
     */
    public static void agregarDatoLista(List<SubreporteGuiaRemisionProductoData> detalle,DetalleProductoGuiaRemision productoNuevo)
    {
        FacturaDetalle facturaDetalle=null;
        BigDecimal subtotalProducto=BigDecimal.ZERO;
        String nombreProducto="NombreProducto";
        
        try {
            facturaDetalle = ServiceFactory.getFactory().getFacturaDetalleServiceIf().buscarPorId(Long.valueOf(productoNuevo.getReferenciaId()));  // TODO: En esta parte falta implentar la referencia cuando se grabe la guia de remision con una referencia libre es decire al producto          
            subtotalProducto=facturaDetalle.getSubtotalRestadoDescuentos();            
            
        } catch (RemoteException ex) {
            Logger.getLogger(SubreporteGuiaRemisionProductoData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (SubreporteGuiaRemisionProductoData subReporte : detalle) 
        {
            if(subReporte.getCodigoInterno().equals(productoNuevo.getCodigoInterno()))
            {
                
                subReporte.subtotal=subReporte.subtotal.add(subtotalProducto);
                subReporte.cantidad+=productoNuevo.getCantidad();
                
                //subReporte.nombre=productoNuevo.get;
                return;
            }            
        }
        
        //Solo consultar el producto cuando es necesario
        if(facturaDetalle.getTipoDocumentoEnum().equals(TipoDocumentoEnum.LIBRE) || facturaDetalle.getTipoDocumentoEnum().equals(TipoDocumentoEnum.INVENTARIO))
        {
            try {
                Producto producto = ServiceFactory.getFactory().getProductoServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                nombreProducto = producto.getNombre();
            } catch (RemoteException ex) {
                Logger.getLogger(SubreporteGuiaRemisionProductoData.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } //TODO:Falta implementar otros casos por ejemplo cuando viene de un presupuesto

        
        //Si no existe agregado un datos con esos datos los creo y agrego
        detalle.add(new SubreporteGuiaRemisionProductoData(
                productoNuevo.getCodigoInterno(), 
                nombreProducto, 
                productoNuevo.getCantidad(), 
                subtotalProducto));
        
    }
}
