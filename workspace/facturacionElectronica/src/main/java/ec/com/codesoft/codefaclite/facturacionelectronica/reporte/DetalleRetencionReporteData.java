/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.reporte;

/**
 *
 * @author Carlos
 */
public class DetalleRetencionReporteData {
    
    private String comprobante;
    private String numero;
    private String fechaEmision;
    private String ejercicioFiscal;
    private String baseImponible;
    private String impuesto;
    private String codigo;
    private String porcentajeRetencion;
    private String valorRetenido;

    public DetalleRetencionReporteData() {
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getEjercicioFiscal() {
        return ejercicioFiscal;
    }

    public void setEjercicioFiscal(String ejercicioFiscal) {
        this.ejercicioFiscal = ejercicioFiscal;
    }

    public String getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(String baseImponible) {
        this.baseImponible = baseImponible;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getPorcentajeRetencion() {
        return porcentajeRetencion;
    }

    public void setPorcentajeRetencion(String porcentajeRetencion) {
        this.porcentajeRetencion = porcentajeRetencion;
    }

    public String getValorRetenido() {
        return valorRetenido;
    }

    public void setValorRetenido(String valorRetenido) {
        this.valorRetenido = valorRetenido;
    }
    
    
    
}
