/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.excel.entidades;

import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;

/**
 *
 * @author
 */
public class ExcelMigrarCompras extends ExcelMigrar{

    @Override
    public CampoMigrarInterface[] obtenerCampos() {
        return ExcelMigrarCompras.Enum.values();
    }

    @Override
    public CampoMigrarInterface getCampoEstado() {
        return ExcelMigrarCompras.Enum.ESTADO;
    }
    
    public enum Enum implements CampoMigrarInterface
    {
        AUTORIZACION("Autorización",0,String.class),
        OBSERVACION("Observación",1,String.class),
        IDENTICACION("Identificación",2,String.class),
        SECUENCIAL("secuencial",3,Double.class),
        PUNTO_ESTABLECIMIENTO("Punto Establecimiento",4,Double.class),
        PUNTO_EMISION("Punto Emisión",5,Double.class),
        FECHA("Fecha",6,String.class),
        SUBTOTAL_IVA("Subtotal Iva",7,Double.class),
        SUBTOTAL_SIN_IVA("Subtotal Sin Iva",8,Double.class),
        DESCUENTO_IVA("Descuento Iva",9,Double.class),
        DESCUENTO_SIN_IVA("Descuento Sin Iva",10,Double.class),
        IVA("Iva",11,Double.class),
        TOTAL("Total",12,Double.class),
        ESTADO("Estado",13,String.class);

        private Enum(String nombre,Integer posicion,Class tipoDato) {
            this.nombre = nombre;
            this.posicion=posicion;
            this.tipoDato=tipoDato;
        }
        
        public String nombre;
        public Integer posicion;
        public Class tipoDato;
        private Boolean requerido;

        @Override
        public String getNombre() {
            return nombre;
        }

        @Override
        public int getPosicion() {
            return posicion;
        }

        @Override
        public Class getTipoDato() {
            return tipoDato;
        }

        @Override
        public Boolean getCampoRequerido() {
            return requerido;
        }

        @Override
        public void setCampoRequerido(boolean requerido) {
            this.requerido=requerido;
        }
        
    }
    
}
