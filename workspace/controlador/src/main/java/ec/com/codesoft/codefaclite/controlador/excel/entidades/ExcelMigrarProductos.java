/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.excel.entidades;

import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;

/**
 *
 * @author Carlos
 */
public class ExcelMigrarProductos extends ExcelMigrar{
        

    @Override
    public CampoMigrarInterface[] obtenerCampos() {
        return Enum.values();
    }

    @Override
    public CampoMigrarInterface getCampoEstado() {
        return Enum.ESTADO;
    }
    
    public enum Enum implements CampoMigrarInterface
    {
        CODIGO("Código",0,String.class,true),
        NOMBRE("Nombres",1,String.class,true),
        COSTO("Costo",2,Double.class,true),
        PRECIO_VENTA_PUBLICO("Precio PVP",3,Double.class,true),
        PRECIO_VENTA_OFERTA("Precio DISTRIB",4,Double.class,false),
        PRECIO_VENTA_PROMEDIO("Precio PROMEDIO",5,Double.class,false),
        PRECIO_VENTA_4("PVP4",6,Double.class,false),
        PRECIO_VENTA_5("PVP5",7,Double.class,false),
        PRECIO_VENTA_6("PVP6",8,Double.class,false),
        CATEGORIA("Categoria",9,String.class,false),
        IVA_PORCENTAJE("IVA Porcentaje",10,Double.class,false),
        MANEJA_INVENTARIO("Maneja Inventario",11,String.class,false),
        BODEGA("Bodega",12,String.class,false),
        STOCK("Stock",13,Double.class,false),
        STOCK_MINIMO("Stock Min",14,Double.class,false),
        MARCA("Marca",15,String.class,false),
        UBICACION("Ubicación",16,String.class,false),
        ESTADO("Estado",17,String.class,false);

        private Enum(String nombre,Integer posicion,Class tipoDato,Boolean requerido) {
            this.nombre = nombre;
            this.posicion=posicion;
            this.tipoDato=tipoDato;
            this.requerido=requerido;
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
