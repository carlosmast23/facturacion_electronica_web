/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.reportdata;

/**
 *
 * @author Carlos
 */
public class DataEjemploReporte {
     public String nombre;
        public String valor;

        public DataEjemploReporte(String nombre, String valor) {
            this.nombre = nombre;
            this.valor = valor;
        }

      
        public String getValor() {
            return valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
}
