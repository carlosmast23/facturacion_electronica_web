/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.sri;

import java.util.Date;

/**
 * TABLA 5 DE LOS ATS
 * Todo: Falta implementar un campo para saber que documentos legales son los que puede usar
 * @author
 */
public enum SriSustentoComprobanteEnum 
{
    CREDITO_TRIBUTARIO_IVA(
            "01",
            "Crédito Tributario para declaración de IVA",
            "Crédito Tributario para declaración de IVA (servicios y bienes distintos de inventarios y activos fijos)",
            new Date(2000,01,01),
            null),
    
    COSTO_O_GASTO_IR(
            "02",
            "Costo o Gasto para declaración de IR",
            "Costo o Gasto para declaración de IR (servicios y bienes distintos de inventarios y activos fijos)",
            new Date(2000,01,01),
            null),
    
    ACTIVO_FIJO_DECLARACION_IVA(
            "03",
            "Activo Fijo - Crédito Tributario declaración IVA",
            "Activo Fijo - Crédito Tributario para declaración de IVA",
            new Date(2000,01,01),
            null),
    
    ACTIVO_FIJO_DECLARACION_IR(
            "04",
            "Activo Fijo - Costo o Gasto declaración IR",
            "Activo Fijo - Costo o Gasto para declaración de IR",
            new Date(2000,01,01),
            null),
    
    LIQUIDACION_GASTOS_VIAJE(
            "05",
            "Liquidación Gastos de Viaje, hospedaje y alimentación ",
            "Liquidación Gastos de Viaje, hospedaje y alimentación Gastos IR (a nombre de empleados y no de la empresa)",
            new Date(2000,01,01),
            null),
    
    INVENTARIO_CREDITO_TRIBUTARIO_IVA(
            "06",
            "Inventario - Crédito Tributario declaración IVA",
            "Inventario - Crédito Tributario para declaración de IVA",
            new Date(2000,01,01),
            null),
    
        INVENTARIO_DECLARACION_IR(
            "07",
            "Inventario - Costo o Gasto para declaración de IR",
            "Inventario - Costo o Gasto para declaración de IR",
            new Date(2000,01,01),
            null),
        
        VALOR_PAGADO_REEMBOLSO_GASTOS(
            "08",
            "Valor pagado para solicitar Reembolso de Gasto",
            "Valor pagado para solicitar Reembolso de Gasto (intermediario)",
            new Date(2000,01,01),
            null),
        
        REEMBOLSO_POR_SINIESTROS(
            "09",
            "Reembolso por Siniestros",
            "Reembolso por Siniestros",
            new Date(2000,01,01),
            null),
        
        DISTRIBUCION_DIVIDENDOS(
            "10",
            "Distribución de Dividendos, Beneficios o Utilidades",
            "Distribución de Dividendos, Beneficios o Utilidades",
            new Date(2000, 01, 01),
            null),
        
        CONVENIO_DEBITO(
            "11",
            "Convenios de débito o recaudación para IFI´s",
            "Convenios de débito o recaudación para IFI´s",
            new Date(2015, 03, 01),
            null),
        
        IMPUESTOS_RETENCIONES_PRESUNTIVOS(
            "12",
            "Impuestos y retenciones presuntivos",
            "Impuestos y retenciones presuntivos",
            new Date(2015, 03, 01),
            null),
        
        VALORES_RECONOCIDOS_SECTOR_PUBLIC(
            "13",
            "Valores reconocidos por el sector público",
            "Valores reconocidos por entidades del sector público a favor de sujetos pasivos",
            new Date(2015, 03, 01),
            null),
        
        VALORES_RECONOCIDOS_SECTOR_PUBLICO(
            "14",
            "Valores facturados a operadoras de transporte",
            "Valores facturados por socios a operadoras de transporte (que no constituyen gasto de dicha operadora)",
            new Date(2018, 01, 01),
            null),
        
        CASOS_ESPECIALES(
            "00",
            "Casos especiales que no aplica en los anteriores",
            "Casos especiales cuyo sustento no aplica en las opciones anteriores",
            new Date(2000, 01, 01),
            null),
        
        ;

    private String Codigo;
    private String descripcionCorta;
    private String tipoDeSustento;
    private Date fechaInicio;
    private Date fechaFin;

    private SriSustentoComprobanteEnum(String Codigo, String descripcionCorta, String tipoDeSustento, Date fechaInicio, Date fechaFin) {
        this.Codigo = Codigo;
        this.descripcionCorta = descripcionCorta;
        this.tipoDeSustento = tipoDeSustento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String getCodigo() {
        return Codigo;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public String getTipoDeSustento() {
        return tipoDeSustento;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    @Override
    public String toString() {
        return "["+Codigo+"] "+descripcionCorta;
    }
    
    static public SriSustentoComprobanteEnum obtenerPorCodigo(String codigo)
    {
        for (SriSustentoComprobanteEnum value : SriSustentoComprobanteEnum.values()) {
            if(value.getCodigo()!=null && value.getCodigo().equals(codigo))
            {
                return value;
            }
        }
        return null;
    }
    
    
    
    
    
    
}
