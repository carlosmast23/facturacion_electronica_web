/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.reportData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class DashBoardData implements Serializable{
    
    private String ejemplo;
    //private List<DashboardProductoTopData> productosList;

    public DashBoardData() {
        //this.productosList = new ArrayList<DashboardProductoTopData>();
    }
        
    
    /////////////METODOS PERSONALZIADOS ///////////////
    /*public void agregarProducto(DashboardProductoTopData productoData)
    {
        if(this.productosList==null)
        {
            this.productosList = new ArrayList<DashboardProductoTopData>();
        }
        this.productosList.add(productoData);
    }*/
    
    ///////////// METODOS GET AND SET /////////////////

    public String getEjemplo() {
        return ejemplo;
    }

    public void setEjemplo(String ejemplo) {
        this.ejemplo = ejemplo;
    }

    /*public List<DashboardProductoTopData> getProductosList() {
        return productosList;
    }

    public void setProductosList(List<DashboardProductoTopData> productosList) {
        this.productosList = productosList;
    }*/
        
    
    public static class DashboardProductoTopData implements Serializable
    {

        public DashboardProductoTopData(String nombre, String valor) {
            this.nombre = nombre;
            this.valor = valor;
        }
                
        private String nombre;
        private String valor;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getValor() {
            return valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }
        
        
        
    }
    
}


