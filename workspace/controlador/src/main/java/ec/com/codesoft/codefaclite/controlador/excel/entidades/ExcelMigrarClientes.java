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
public class ExcelMigrarClientes extends ExcelMigrar {

    @Override
    public CampoMigrarInterface[] obtenerCampos() {
        return ExcelMigrarClientes.Enum.values();
    }

    @Override
    public CampoMigrarInterface getCampoEstado() {
        return ExcelMigrarClientes.Enum.ESTADO;
    }
    
    public enum Enum implements CampoMigrarInterface
    {
        IDENTIFICACION("Identificación",0,String.class),
        NOMBRES("Nombres",1,String.class),
        APELLIDOS("Apellidos",2,String.class),
        RAZON_SOCIAL("Razon Social",3,String.class),
        NOMBRE_COMERCIAL("Nombre Comercial",4,String.class),
        DIRECCION("Direccion",5,String.class),
        TELEFONO("Telefono",6,String.class),
        CELULAR("Celular",7,String.class),
        CORREO("Correo",8,String.class),
        ESTADO("Estado",9,String.class);

        private Enum(String nombre,Integer posicion,Class tipoDato) {
            this.nombre = nombre;
            this.posicion=posicion;
            this.tipoDato=tipoDato;
            this.requerido=false;
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
