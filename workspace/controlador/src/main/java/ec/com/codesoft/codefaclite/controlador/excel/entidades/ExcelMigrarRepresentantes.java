/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.excel.entidades;

import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;
import java.io.File;

/**
 *
 * @author Carlos
 */
public class ExcelMigrarRepresentantes  extends ExcelMigrar{

    

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
        IDENTIFICACION("Identificación",0,String.class,true),
        NOMBRES("Nombres",1,String.class,false),
        APELLIDOS("Apellidos",2,String.class,false),
        RAZON_SOCIAL("Razon Social",3,String.class,true),
        DIRECCION("Direccion",4,String.class,false),
        TELEFONO("Telefono",5,String.class,false),
        CELULAR("Celular",6,String.class,false),
        CORREO("Correo",7,String.class,false),
        ESTADO("Estado",8,String.class,false);

        private Enum(String nombre,Integer posicion,Class tipoDato,Boolean requerido) {
            this.nombre = nombre;
            this.posicion=posicion;
            this.tipoDato=tipoDato;
            this.requerido=requerido;
        }
        
        public String nombre;
        public Integer posicion;
        public Class tipoDato;
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
            return tipoDato;
        }

        @Override
        public Boolean getCampoRequerido() {
            return this.requerido;
        }

        @Override
        public void setCampoRequerido(boolean requerido) {
            this.requerido=requerido;
        }
        
    }
    
    
}
