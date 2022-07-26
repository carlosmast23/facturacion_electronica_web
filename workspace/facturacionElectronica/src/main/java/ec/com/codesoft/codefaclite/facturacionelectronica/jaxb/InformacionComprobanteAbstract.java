/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.jaxb;

import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.factura.FormaPagoComprobante;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.TotalImpuesto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author
 */
@XmlTransient
public abstract class InformacionComprobanteAbstract implements Serializable{
    protected String fechaEmision;
    protected String dirEstablecimiento;
    
     /**
     * Esta variable se refiere al subtotal antes de impuesto y menos los descuentos
     */
    protected BigDecimal totalSinImpuestos;
    protected BigDecimal totalDescuento;
    protected BigDecimal importeTotal;
    
    protected String obligadoContabilidad;
    
    protected String contribuyenteEspecial;
    
    protected List<TotalImpuesto> totalImpuestos;
    protected List<FormaPagoComprobante> formaPagos;
    
    /**
     * Metodos abstractos para implementar el seteo de los datos principales
     * @return 
     */
    public abstract void setTipoIdentificacion(String tipoIdentificacion);
    public abstract void setRazonSocial(String razonSocial);
    public abstract void setIdentificacion(String identificacion);
    public abstract void setDireccion(String direccion);

    
    @XmlElement(name = "fechaEmision")
    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    @XmlElement(name = "dirEstablecimiento")
    public String getDirEstablecimiento() {
        return dirEstablecimiento;
    }

    public void setDirEstablecimiento(String dirEstablecimiento) {
        this.dirEstablecimiento = dirEstablecimiento;
    }
    
    @XmlElement(name = "totalSinImpuestos")
    public BigDecimal getTotalSinImpuestos() {
        return totalSinImpuestos;
    }


    public void setTotalSinImpuestos(BigDecimal totalSinImpuestos) {
        this.totalSinImpuestos = totalSinImpuestos;
    }

    @XmlElement(name = "totalDescuento")
    public BigDecimal getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(BigDecimal totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    @XmlElement(name = "importeTotal")
    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }
    
    

    @XmlElementWrapper(name = "totalConImpuestos")
    @XmlElement(name = "totalImpuesto")
    public List<TotalImpuesto> getTotalImpuestos() {
        return totalImpuestos;
    }

    @XmlElementWrapper(name = "pagos")
    @XmlElement(name = "pago")
    public List<FormaPagoComprobante> getFormaPagos() {
        return formaPagos;
    }

    public void setFormaPagos(List<FormaPagoComprobante> formaPagos) {
        this.formaPagos = formaPagos;
    }
    
    

    public void setTotalImpuestos(List<TotalImpuesto> totalImpuestos) {
        this.totalImpuestos = totalImpuestos;
    }

    @XmlElement(name = "obligadoContabilidad")    
    public String getObligadoContabilidad() {
        return obligadoContabilidad;
    }

    public void setObligadoContabilidad(String obligadoContabilidad) {
        this.obligadoContabilidad = obligadoContabilidad;
    }

    @XmlElement(name = "contribuyenteEspecial")    
    public String getContribuyenteEspecial() {
        return contribuyenteEspecial;
    }

    public void setContribuyenteEspecial(String contribuyenteEspecial) {
        this.contribuyenteEspecial = contribuyenteEspecial;
    }
    
    
    
}
