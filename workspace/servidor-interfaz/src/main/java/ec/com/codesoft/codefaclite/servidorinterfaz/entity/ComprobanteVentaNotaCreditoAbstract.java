/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.CatalogoProducto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.ReferenciaDetalleFacturaRespuesta;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author CARLOS_CODESOFT
 */
@MappedSuperclass
public abstract class ComprobanteVentaNotaCreditoAbstract<T extends ComprobanteAdicional> extends ComprobanteEntity<T> {

    public abstract List<DetalleFacturaNotaCeditoAbstract> getDetallesComprobante();
    /**
     * Valor del iva cobrado
     */
    @Column(name = "IVA")
    protected BigDecimal iva;

    @Column(name = "TOTAL")
    protected BigDecimal total;
    
    /**
     * Valor del descuento de los productos que no cobran iva
     */    
    @Column(name = "DESCUENTO_IVA_CERO")
    protected BigDecimal descuentoSinImpuestos;
    
    /**
     * Valor del descuento de los productos que cobran iva
     */
    @Column(name = "DESCUENTO_IVA")
    protected BigDecimal descuentoImpuestos;
    
    @Column(name = "SUBTOTAL_IVA_CERO")
    protected BigDecimal subtotalSinImpuestos;
    
    @Column(name = "SUBTOTAL_IVA")
    private BigDecimal subtotalImpuestos;
    /**
     * Valor del iva cobrado
     */
    @Column(name = "VALOR_ICE")
    private BigDecimal ice;
    
    @JoinColumn(name = "CLIENTE_ID")
    @ManyToOne
    private Persona cliente;
    
    /**
     * Se refiere a la sucucursal del cliente
     */
    @JoinColumn(name = "SUCURSAL_ID")
    @ManyToOne    
    private PersonaEstablecimiento sucursal;


    public ComprobanteVentaNotaCreditoAbstract() {
    }
    
    

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDescuentoSinImpuestos() {
        return descuentoSinImpuestos;
    }

    public void setDescuentoSinImpuestos(BigDecimal descuentoSinImpuestos) {
        this.descuentoSinImpuestos = descuentoSinImpuestos;
    }

    public BigDecimal getDescuentoImpuestos() {
        return descuentoImpuestos;
    }

    public void setDescuentoImpuestos(BigDecimal descuentoImpuestos) {
        this.descuentoImpuestos = descuentoImpuestos;
    }

    public BigDecimal getSubtotalSinImpuestos() {
        return subtotalSinImpuestos;
    }

    public void setSubtotalSinImpuestos(BigDecimal subtotalSinImpuestos) {
        this.subtotalSinImpuestos = subtotalSinImpuestos;
    }

    public BigDecimal getSubtotalImpuestos() {
        return subtotalImpuestos;
    }

    public void setSubtotalImpuestos(BigDecimal subtotalImpuestos) {
        this.subtotalImpuestos = subtotalImpuestos;
    }

    public BigDecimal getIce() {
        return ice;
    }

    public void setIce(BigDecimal ice) {
        this.ice = ice;
    }

    public Persona getCliente() {
        return cliente;
    }

    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }

    public PersonaEstablecimiento getSucursal() {
        return sucursal;
    }

    public void setSucursal(PersonaEstablecimiento sucursal) {
        this.sucursal = sucursal;
    }
    
    

    /**
     * ==========================================================================
     * METODOS MERSONALIZADOS
     * ==========================================================================
     */
    
    public BigDecimal getSubtotalImpuestosMenosDescuento() {
        return subtotalImpuestos.subtract(descuentoImpuestos);
    }
    
    public BigDecimal getSubtotalSinImpuestosMenosDescuento() {
        return subtotalSinImpuestos.subtract(descuentoSinImpuestos);
    }
    
    public void calcularTotalesDesdeDetalles() {
        List<DetalleFacturaNotaCeditoAbstract> detalles=(List<DetalleFacturaNotaCeditoAbstract>) getDetallesComprobante();
        //Solo calcular si la variables de detalles fue creada
        if (detalles == null || detalles.size() == 0) {
            this.total = BigDecimal.ZERO;
            this.descuentoSinImpuestos = BigDecimal.ZERO;
            this.descuentoImpuestos = BigDecimal.ZERO;
            this.subtotalSinImpuestos = BigDecimal.ZERO;
            this.subtotalImpuestos = BigDecimal.ZERO;
            this.iva = BigDecimal.ZERO;
            this.ice = BigDecimal.ZERO;
            return;
        }

        BigDecimal total = BigDecimal.ZERO; //total de la factura        
        BigDecimal subTotalSinImpuestos = BigDecimal.ZERO;//Sin el descuento
        BigDecimal subTotalConImpuestos = BigDecimal.ZERO;//Sin los descuentos        
        BigDecimal descuentoSinImpuestos = BigDecimal.ZERO; //
        BigDecimal descuentoConImpuestos = BigDecimal.ZERO; //        
        BigDecimal impuestoIva = BigDecimal.ZERO; //        
        BigDecimal ivaDecimal = BigDecimal.ZERO; //Todo: Variable donde se almacena el iva de uno de los detalles (pero si tuviera varias ivas distintos de 0 , se generaria poroblemas)
        BigDecimal ice = BigDecimal.ZERO;
        

        for (DetalleFacturaNotaCeditoAbstract detalle : detalles) {

            //Sumar el valor del Ice
            ice = ice.add(detalle.getValorIce());
            //Sumar los subtotales
            //TODO: Ver si estos calculos los puede hacer internamente en la clase FacturaDetalle
            if (detalle.getIvaPorcentaje().equals(0)) 
            {
                subTotalSinImpuestos = subTotalSinImpuestos.add(detalle.getPrecioUnitario().multiply(detalle.getCantidad()));
                descuentoSinImpuestos = descuentoSinImpuestos.add((detalle.getDescuento()!=null)?detalle.getDescuento():BigDecimal.ZERO);
            } 
            else 
            {
                subTotalConImpuestos = subTotalConImpuestos.add(detalle.getPrecioUnitario().multiply(detalle.getCantidad()));
                descuentoConImpuestos = descuentoConImpuestos.add((detalle.getDescuento()!=null)?detalle.getDescuento():BigDecimal.ZERO);

                ivaDecimal = new BigDecimal(detalle.getIvaPorcentaje().toString()).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
                impuestoIva = subTotalConImpuestos.add(ice).subtract(descuentoConImpuestos).multiply(ivaDecimal);
            }

        }

        this.ice = ice;

        //Calcula el total de los totales
        total = subTotalSinImpuestos.subtract(descuentoSinImpuestos)
                .add(subTotalConImpuestos.subtract(descuentoConImpuestos))
                .add(impuestoIva)
                .add(ice);

        /**
         * ============================================================================================================
         * 1.- primero redondear el TOTAL con 2 decimales para hacer el calculo
         * inverso y que los demas valores cuadren
         * ============================================================================================================
         */
        this.total = total.setScale(2, BigDecimal.ROUND_HALF_UP); //valor final con 2 decimales

        /**
         * ============================================================================================================
         * 2.- redondear el DESCUENTO Y SUBTOTAL de los totales que no voy a
         * calcular impuestos
         * ============================================================================================================
         */
        this.descuentoSinImpuestos = descuentoSinImpuestos.setScale(2, BigDecimal.ROUND_HALF_UP); //Este valor no se mueve porque debe ser fijo de 2 decimales segun el sri
        this.subtotalSinImpuestos = subTotalSinImpuestos.setScale(2, BigDecimal.ROUND_HALF_UP);// Este valor se redondea y tampoco ya no se mueve porque no interfiere con el iva donde se descuadra //TODO: PERO REVISAR ESTA AFIRMACION

        /**
         * ==================================================================================
         * 3.- obtener el SUBTOTAL CON IMPUESTOS el cual contiene el iva y el
         * ice Nota: No debo redondear porque los calculos anteriores ya estan
         * redondeados a 2 decimales
         * ==================================================================================
         */
        BigDecimal totalConImpuestos = this.total.subtract(this.subtotalSinImpuestos).add(this.descuentoSinImpuestos);

        /**
         * ==================================================================================
         * 4.- Redondear el valor del Ice directo TODO: Revisar si este paso no
         * genera problemas
         * ==================================================================================
         */
        this.ice = this.ice.setScale(2, BigDecimal.ROUND_HALF_UP);

        /**
         * ==================================================================================
         * 4.- Calcular el valor antes del Iva es decir el subtotal con
         * descuento + ice
         * ==================================================================================
         */
        BigDecimal ivaDecimalTmp = ivaDecimal.add(BigDecimal.ONE); //1.12 por ejemplo

        BigDecimal subtotalMenosImpuestosConIce = totalConImpuestos.divide(ivaDecimalTmp, 2, BigDecimal.ROUND_HALF_UP);

        /**
         * ==================================================================================
         * 6.- Redondear el valor del descuento con impuestos porque este valor
         * no influye con otro valores previos
         * ==================================================================================
         */
        this.descuentoImpuestos = descuentoConImpuestos.setScale(2, BigDecimal.ROUND_HALF_UP);

        /**
         * ==================================================================================
         * 7.- Calcular el valor del SUBTOTAL IMPUESTOS SIN TOMAR EN CUENTA
         * DESCUENTOS
         * ==================================================================================
         */
        this.subtotalImpuestos = subtotalMenosImpuestosConIce.subtract(this.ice).add(this.descuentoImpuestos);

        /**
         * =========================================================================================
         * 8.- Calcular el valor del IVA que simplemente es restar del SUBTOTAL
         * TOTAL MENOS SUBTOTAL CON IMPUESTOS EL ICE
         * ==========================================================================================
         */
        //Calcular el iva de la resta del del total -subtotal
        this.iva = totalConImpuestos.subtract(subtotalMenosImpuestosConIce);
        //this.iva=this.iva.setScale(2,BigDecimal.ROUND_HALF_UP);        

    }
    
    public Map<ImpuestoDetalle,List<DetalleFacturaNotaCeditoAbstract>> obtenerIceMap()
    {
        Map<ImpuestoDetalle,List<DetalleFacturaNotaCeditoAbstract>> mapResultado=new HashMap<ImpuestoDetalle,List<DetalleFacturaNotaCeditoAbstract>>();
        try {
            for (DetalleFacturaNotaCeditoAbstract detalle : this.getDetallesComprobante()) {
                CatalogoProducto catalogoProducto = detalle.getCatalogoProducto();
                
                //TODO: Solucion Temporal cuando no tengo grabado ese dato para hacer retrocompatible el nuevo codigo
                if (detalle.getCatalogoProducto() == null) 
                {
                    ReferenciaDetalleFacturaRespuesta detalleData=ServiceFactory.getFactory().getFacturacionServiceIf().obtenerReferenciaDetalleFactura(detalle.getTipoDocumentoEnum(), detalle.getReferenciaId());
                    catalogoProducto=detalleData.catalogoProducto;
                }
                
                
                //Obtener una lista de map con el TIPO DE IMPUESTO
                if(catalogoProducto.getIce()!=null)
                {                    
                    if(catalogoProducto.getIce().getImpuesto().getDetalleImpuestos().size()>0)
                    {
                        ImpuestoDetalle impuestoDetalle=catalogoProducto.getIce().getImpuesto().getDetalleImpuestos().get(0);                                
                        
                        List<DetalleFacturaNotaCeditoAbstract> listTmp=mapResultado.get(impuestoDetalle);                        
                        if(listTmp==null)
                        {
                            listTmp=new ArrayList<DetalleFacturaNotaCeditoAbstract>();
                        }
                        
                        listTmp.add(detalle);
                        mapResultado.put(impuestoDetalle, listTmp);
                    }
                }
                
            }
            //return mapResultado;
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobanteVentaNotaCreditoAbstract.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ComprobanteVentaNotaCreditoAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mapResultado;
    }

}
