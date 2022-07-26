/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.excel.entidades;

import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;
import java.io.File;
import java.util.Date;

/**
 *
 * @author
 */
public class ExcelMigrarEstudiantes extends ExcelMigrar{

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
        IDENTIFICACION("Identificacion",0,String.class),
        NOMBRES("Nombres",1,String.class),
        APELLIDOS("Apellidos",2,String.class),
        GENERO("genero",3,String.class),
        FECHA_NACIMIENTO("fechaNacimiento",4,Date.class),
        IDENTIFICACION_REPRESENTATE_1("representante1",5,String.class),
        IDENTIFICACION_REPRESENTATE_2("representante2",6,String.class),
        CURSO_ACTUAL("curso",7,String.class),
        ESTADO("estado",8,String.class);

        private Enum(String nombre,Integer posicion,Class tipoDato) {
            this.nombre = nombre;
            this.posicion=posicion;
            this.requerido=true;
            this.tipoDato=tipoDato;
        }
        
        private boolean requerido;
        public String nombre;
        public Class tipoDato;
        public Integer posicion;

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
            return this.requerido;
        }

        @Override
        public void setCampoRequerido(boolean requerido){
            this.requerido=requerido;
        }
        
    }
    
}
