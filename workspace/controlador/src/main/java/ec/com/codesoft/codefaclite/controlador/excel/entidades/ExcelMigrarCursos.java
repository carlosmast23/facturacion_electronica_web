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
public class ExcelMigrarCursos extends ExcelMigrar {

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
        NOMBRE("Nombre",0),
        DESCRIPCION("Descripción",1),
        NIVEL("Nivel",2),
        PERIODO("Periodo",3),
        ESTADO("Estado Migración",4);

        private Enum(String nombre,Integer posicion) {
            this.nombre = nombre;
            this.posicion=posicion;
        }
        
        public String nombre;
        public Integer posicion;
        private boolean requerido;

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
            return String.class;
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
