/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobante.reporte;

import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ControladorReporteFactura.TipoReporteEnum;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import ec.com.codesoft.codefaclite.controlador.reportes.AgrupadoReporteIf;
import ec.com.codesoft.codefaclite.controlador.reportes.EnumReporteAgruparIf;
import ec.com.codesoft.codefaclite.controlador.reportes.NombreCampoAgrupadoIf;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CodesoftDesarrollo
 */
public class ReporteFacturaData implements ExcelDatosInterface,Cloneable,AgrupadoReporteIf  {
    
    public Boolean mostrarReferido=false;
    
    protected String campoAgrupado;

    protected String numeroFactura;
    protected String fechaFactura;
    protected String identificacionCliente;
    protected String razonSocialCliente;
    protected String nombreLegalCliente;
    protected String estadoFactura;
    protected String tipoEmision;
    protected String documento;
    protected String subtotalDoceFactura;
    protected String subtotalCeroFactura;
    protected String descFactura;
    protected String ivaDoceFactura;
    protected String totalFactura;
    protected String valorAfecta;
    protected String referencia;
    protected String totalFinal;
    protected String claveAcceso;
    
    ///campo adicional para filrar por el punto de venta
    protected String puntoEmision;
    
    //============> Campos adicionales para los referidos <===================//
    protected String referido;
    protected String referidoIdentificacion;
    protected String referidoPorcentaje;
    protected String valorComision;
    
    //============> Campos adicionales para el vendedor y fecha maxima de pago <====================//
    protected String fechaMaximaPago;
    protected String vendedor;
    protected String costo;
    
    /**
     * Es la fecha de autorizacion del comprobante
     */
    protected String fechaAutorizacion;

    /**
     * Estos 2 productos voy a utilizar cuando necesite un reporte agrupado por productos
     */
    protected String codigoProducto;
    protected String nombreProducto;
    protected String categoria;
    protected String cantidad;
    /**
     * Este parametro me permite sacar un reporte de productos agrupado por precio para el reporte de ventas
     */
    protected String precioUnitarioReporte;
    
    protected String formaPago;
    
    /**
     * Datos para empresas de distribucion
     */
    protected String zona;
    protected String ruta;
    
    public ReporteFacturaData() {
    }
    
    

    public ReporteFacturaData(String numeroFactura, String fechaFactura,String fechaAutorizacion, String identificacionCliente, String razonSocialCliente, String nombreLegalCliente, String estadoFactura,String tipoDocumento,String documento, String subtotalDoceFactura, String subtotalCeroFactura, String descFactura, String ivaDoceFactura, String totalFactura,String valorAfecta,String referencia,String totalFinal,String referido,String referidoIdentificacion,String referidoPorcentaje,String valorComision,String claveAcceso,String puntoEmision) {
        this.numeroFactura = numeroFactura;
        this.fechaFactura = fechaFactura;
        this.fechaAutorizacion=fechaAutorizacion;
        this.identificacionCliente = identificacionCliente;
        this.razonSocialCliente = razonSocialCliente;
        this.nombreLegalCliente = nombreLegalCliente;
        this.estadoFactura = estadoFactura;
        this.tipoEmision=tipoDocumento;
        this.documento=documento;
        this.subtotalDoceFactura = subtotalDoceFactura;
        this.subtotalCeroFactura = subtotalCeroFactura;
        this.descFactura = descFactura;
        this.ivaDoceFactura = ivaDoceFactura;
        this.totalFactura = totalFactura;
        this.valorAfecta=valorAfecta;
        this.referencia=referencia;
        this.totalFinal=totalFinal;
        this.referido=referido;
        this.referidoIdentificacion=referidoIdentificacion;
        this.referidoPorcentaje=referidoPorcentaje;
        this.valorComision=valorComision;
        this.claveAcceso=claveAcceso;
        this.puntoEmision=puntoEmision;
    }

        
    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public String getIdentificacionCliente() {
        return identificacionCliente;
    }

    public void setIdentificacionCliente(String identificacionCliente) {
        this.identificacionCliente = identificacionCliente;
    }

    public String getRazonSocialCliente() {
        return razonSocialCliente;
    }

    public void setRazonSocialCliente(String razonSocialCliente) {
        this.razonSocialCliente = razonSocialCliente;
    }

    public String getNombreLegalCliente() {
        return nombreLegalCliente;
    }

    public void setNombreLegalCliente(String nombreLegalCliente) {
        this.nombreLegalCliente = nombreLegalCliente;
    }

    public String getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(String estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    public String getSubtotalDoceFactura() {
        return subtotalDoceFactura;
    }

    public void setSubtotalDoceFactura(String subtotalDoceFactura) {
        this.subtotalDoceFactura = subtotalDoceFactura;
    }

    public String getSubtotalCeroFactura() {
        return subtotalCeroFactura;
    }

    public void setSubtotalCeroFactura(String subtotalCeroFactura) {
        this.subtotalCeroFactura = subtotalCeroFactura;
    }

    public String getDescFactura() {
        return descFactura;
    }

    public void setDescFactura(String descFactura) {
        this.descFactura = descFactura;
    }

    public String getIvaDoceFactura() {
        return ivaDoceFactura;
    }

    public void setIvaDoceFactura(String ivaDoceFactura) {
        this.ivaDoceFactura = ivaDoceFactura;
    }

    public String getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(String totalFactura) {
        this.totalFactura = totalFactura;
    }

    public String getTipoEmision() {
        return tipoEmision;
    }

    public void setTipoEmision(String tipoDocumento) {
        this.tipoEmision = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getValorAfecta() {
        return valorAfecta;
    }

    public void setValorAfecta(String valorAfecta) {
        this.valorAfecta = valorAfecta;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTotalFinal() {
        return totalFinal;
    }

    public void setTotalFinal(String totalFinal) {
        this.totalFinal = totalFinal;
    }

    public String getReferido() {
        return referido;
    }

    public void setReferido(String referido) {
        this.referido = referido;
    }

    public String getReferidoIdentificacion() {
        return referidoIdentificacion;
    }

    public void setReferidoIdentificacion(String referidoIdentificacion) {
        this.referidoIdentificacion = referidoIdentificacion;
    }

    public String getReferidoPorcentaje() {
        return referidoPorcentaje;
    }

    public void setReferidoPorcentaje(String referidoPorcentaje) {
        this.referidoPorcentaje = referidoPorcentaje;
    }

    public String getValorComision() {
        return valorComision;
    }

    public void setValorComision(String valorComision) {
        this.valorComision = valorComision;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public String getFechaMaximaPago() {
        return fechaMaximaPago;
    }

    public void setFechaMaximaPago(String fechaMaximaPago) {
        this.fechaMaximaPago = fechaMaximaPago;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(String puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public String getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(String fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPrecioUnitarioReporte() {
        return precioUnitarioReporte;
    }

    public void setPrecioUnitarioReporte(String precioUnitarioReporte) {
        this.precioUnitarioReporte = precioUnitarioReporte;
    }

    public String getCampoAgrupado() {
        return campoAgrupado;
    }

    public void setCampoAgrupado(String campoAgrupado) {
        this.campoAgrupado = campoAgrupado;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    
    
    
    

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    

    @Override
    public List<TipoDato> getDatos() {
        
        if(mostrarReferido)
            return getDatosReporteComisiones();
        else
            return getDatosReporteFactura();
        
    }

    public List<TipoDato> getDatosReporteFactura()
    {    
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();

        tiposDatos.add(new TipoDato(this.claveAcceso, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.fechaMaximaPago, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.vendedor, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.nombreProducto, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.numeroFactura, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.referencia, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.fechaFactura, Excel.TipoDataEnum.FECHA));
        tiposDatos.add(new TipoDato((this.fechaAutorizacion!=null)?this.fechaAutorizacion:"", Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.identificacionCliente, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.razonSocialCliente, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.nombreLegalCliente, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.ruta, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.zona, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.documento, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.estadoFactura, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.tipoEmision, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.subtotalDoceFactura, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.subtotalCeroFactura, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.descFactura, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.ivaDoceFactura, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.valorAfecta, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.totalFinal, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.costo, Excel.TipoDataEnum.NUMERO));

        return tiposDatos;
    }
    
    
    public List<TipoDato> getDatosReporteComisiones()
    {    
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();        
        tiposDatos.add(new TipoDato(this.referidoIdentificacion, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.referido, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.referidoPorcentaje, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.valorComision, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.numeroFactura, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.referencia, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.fechaFactura, Excel.TipoDataEnum.FECHA));
        tiposDatos.add(new TipoDato((this.fechaAutorizacion!=null)?this.fechaAutorizacion:"", Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.identificacionCliente, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.razonSocialCliente, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.nombreLegalCliente, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.documento, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.estadoFactura, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.tipoEmision, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.subtotalDoceFactura, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.subtotalCeroFactura, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.descFactura, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.ivaDoceFactura, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.valorAfecta, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.totalFinal, Excel.TipoDataEnum.NUMERO));

        return tiposDatos;
    }

    @Override
    public String getValorCampoAgrupar(EnumReporteAgruparIf enumReporteAgruparIf) {
        
        TipoReporteEnum tipoRepoteEnum=(TipoReporteEnum) enumReporteAgruparIf; 
                
        switch(tipoRepoteEnum)
        {
            case AGRUPADO_POR_VENDEDOR:return vendedor;
            case AGRUPADO_POR_PUNTO_EMISION:return puntoEmision;
            case AGRUPADO_POR_PRODUCTO:return nombreProducto;
            case AGRUPADO_POR_CATEGORIA:return categoria;
            case AGRUPADO_POR_FORMA_PAGO:return formaPago;
            case AGRUPADO_POR_VALOR:return precioUnitarioReporte;
            case AGRUPADO_POR_RUTA:return ruta;
            case AGRUPADO_POR_ZONA:return zona;
        }
        
        /*String nombreCampo=nombreCampoAgrupadoIf.getNombreCampoAgrupado();
        
        if(ValoresAgrupar.VENDEDOR.nombre.equals(nombreCampo))
        {
            return nombreCampo;
        }
        else if(ValoresAgrupar.PUNTO_EMISION.nombre.equals(nombreCampo))
        {
            return puntoEmision;
        }
        else if(ValoresAgrupar.VENDEDOR.nombre.equals(nombreCampo))
        {
            return puntoEmision;
        }
        
        TipoReporteEnum tipo;
        tipo.
        
        return "";*/
        return null;
    }
    
    

    /*public static class ValoresAgrupar implements NombreCampoAgrupadoIf
    {
        public static ValoresAgrupar VENDEDOR=new ValoresAgrupar("vendedor");
        public static ValoresAgrupar PUNTO_EMISION=new ValoresAgrupar("punto_emision");
        public static ValoresAgrupar PRODUCTO=new ValoresAgrupar("punto_emision");
        public static ValoresAgrupar CATEGORIA=new ValoresAgrupar("punto_emision");
        public static ValoresAgrupar FORMA_PAGO=new ValoresAgrupar("punto_emision");
        public static ValoresAgrupar VALOR=new ValoresAgrupar("punto_emision");
        public static ValoresAgrupar PUNTO_EMISION=new ValoresAgrupar("punto_emision");
        public static ValoresAgrupar PUNTO_EMISION=new ValoresAgrupar("punto_emision");
        

        public ValoresAgrupar(String nombre) {
            this.nombre = nombre;
        }
        
        public String nombre;

        @Override
        public String getNombreCampoAgrupado() {
            return nombre;
        }
    }*/
    

}
