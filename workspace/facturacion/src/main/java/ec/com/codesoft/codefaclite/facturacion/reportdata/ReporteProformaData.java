/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.reportdata;

import ec.com.codesoft.codefaclite.controlador.comprobante.reporte.ReporteFacturaData;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.TipoDato;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class ReporteProformaData extends ReporteFacturaData{
 
    @Override
    public List<TipoDato> getDatos() {
        List<TipoDato> tiposDatos = new ArrayList<TipoDato>();

        tiposDatos.add(new TipoDato(this.numeroFactura, Excel.TipoDataEnum.TEXTO));
        //tiposDatos.add(new TipoDato(this.referencia, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.fechaFactura, Excel.TipoDataEnum.FECHA));
        tiposDatos.add(new TipoDato(this.identificacionCliente, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.razonSocialCliente, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.nombreLegalCliente, Excel.TipoDataEnum.TEXTO));
        //tiposDatos.add(new TipoDato(this.documento, Excel.TipoDataEnum.TEXTO));
       // tiposDatos.add(new TipoDato(this.estadoFactura, Excel.TipoDataEnum.TEXTO));
       // tiposDatos.add(new TipoDato(this.tipoEmision, Excel.TipoDataEnum.TEXTO));
        tiposDatos.add(new TipoDato(this.subtotalDoceFactura, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.subtotalCeroFactura, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.descFactura, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.ivaDoceFactura, Excel.TipoDataEnum.NUMERO));
        //tiposDatos.add(new TipoDato(this.valorAfecta, Excel.TipoDataEnum.NUMERO));
        tiposDatos.add(new TipoDato(this.totalFinal, Excel.TipoDataEnum.NUMERO));

        return tiposDatos;
    }
    
}
