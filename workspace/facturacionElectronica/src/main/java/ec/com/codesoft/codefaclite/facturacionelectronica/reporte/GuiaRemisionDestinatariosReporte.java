/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacionelectronica.reporte;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Carlos
 */
public class GuiaRemisionDestinatariosReporte {
    private String preimpreso;
    private String numero_autorizacion;
    private String motivo_traslado;
    private String destino;
    private String razon_social_persona;
    private String documentoAduanero;
    private String ruta;
    private String fecha_emision;
    private String identificacion_persona;
    private String establecimientoDestino;
    
    private String comprobante;
    private String numero;
    private String fechaEmision;
    
    private JRBeanCollectionDataSource productos;

    public GuiaRemisionDestinatariosReporte() {
    }

    public String getPreimpreso() {
        return preimpreso;
    }

    public void setPreimpreso(String preimpreso) {
        this.preimpreso = preimpreso;
    }

    public String getNumero_autorizacion() {
        return numero_autorizacion;
    }

    public void setNumero_autorizacion(String numero_autorizacion) {
        this.numero_autorizacion = numero_autorizacion;
    }

    public String getMotivo_traslado() {
        return motivo_traslado;
    }

    public void setMotivo_traslado(String motivo_traslado) {
        this.motivo_traslado = motivo_traslado;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getRazon_social_persona() {
        return razon_social_persona;
    }

    public void setRazon_social_persona(String razon_social_persona) {
        this.razon_social_persona = razon_social_persona;
    }

    public String getDocumentoAduanero() {
        return documentoAduanero;
    }

    public void setDocumentoAduanero(String documentoAduanero) {
        this.documentoAduanero = documentoAduanero;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(String fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public String getIdentificacion_persona() {
        return identificacion_persona;
    }

    public void setIdentificacion_persona(String identificacion_persona) {
        this.identificacion_persona = identificacion_persona;
    }

    public String getEstablecimientoDestino() {
        return establecimientoDestino;
    }

    public void setEstablecimientoDestino(String establecimientoDestino) {
        this.establecimientoDestino = establecimientoDestino;
    }

    public JRBeanCollectionDataSource getProductos() {
        return productos;
    }

    public void setProductos(JRBeanCollectionDataSource productos) {
        this.productos = productos;
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
    
    
    
    
    
}
