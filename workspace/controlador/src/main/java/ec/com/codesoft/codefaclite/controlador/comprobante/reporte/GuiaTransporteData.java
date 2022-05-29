/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobante.reporte;

import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class GuiaTransporteData implements ExcelDatosInterface{
    //String[] titulos={"Preimpreso","Transportista","Identificación","Estado","FechaInicio","FechaFin","Dir Partida","Placa"};
    private String claveAcceso;
    private String preimpreso;
    private String transportista;
    private String identififacion;
    private String estado;
    private String fechaInicio;
    private String fechaFin;
    private String direccionPartida;
    private String placa;
    private String cantidadItems;
    private String destinatarios;
    private String facturas;

    public GuiaTransporteData() {
    }

    public String getPreimpreso() {
        return preimpreso;
    }

    public void setPreimpreso(String preimpreso) {
        this.preimpreso = preimpreso;
    }

    public String getTransportista() {
        return transportista;
    }

    public void setTransportista(String transportista) {
        this.transportista = transportista;
    }

    public String getIdentififacion() {
        return identififacion;
    }

    public void setIdentififacion(String identififacion) {
        this.identififacion = identififacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDireccionPartida() {
        return direccionPartida;
    }

    public void setDireccionPartida(String direccionPartida) {
        this.direccionPartida = direccionPartida;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public String getCantidadItems() {
        return cantidadItems;
    }

    public void setCantidadItems(String cantidadItems) {
        this.cantidadItems = cantidadItems;
    }

    public String getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(String destinatarios) {
        this.destinatarios = destinatarios;
    }
    
    public String getFacturas() {
        return facturas;
    }

    public void setFacturas(String facturas) {
        this.facturas = facturas;
    }
    
    

    @Override
    public List<TipoDato> getDatos() {
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();
        
        tiposDatos.add(new TipoDato(this.claveAcceso,Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.preimpreso,Excel.TipoDataEnum.TEXTO));    
        tiposDatos.add(new TipoDato(this.identififacion,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.transportista, Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.destinatarios,Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.estado, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.fechaInicio, Excel.TipoDataEnum.TEXTO));        
        tiposDatos.add(new TipoDato(this.fechaFin,Excel.TipoDataEnum.TEXTO));
        //tiposDatos.add(new TipoDato(this.direccionPartida,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.facturas,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.placa,Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.cantidadItems,Excel.TipoDataEnum.NUMERO));
        return tiposDatos;
    }

    
     
    
}
