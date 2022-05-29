/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.recursos;

import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.io.InputStream;

/**
 *
 * @author DellWin10
 */
public enum RecursoCodefacEnum 
{
    SQL_BORRAR_CLIENTE_POR_EMPRESA("BORRAR_CLIENTE_POR_EMPRESA.sql"),
    SQL_BORRAR_INVENTARIO("BORRAR_INVENTARIO.sql"),
    SQL_BORRAR_VENTAS("BORRAR_VENTAS.sql");
    
    private String nombre;

    private RecursoCodefacEnum(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
    
    /**
     * Obtiene el archivo como texto
     * @return 
     */
    public String getValueString()
    {
        InputStream inputStreamReporte= RecursoCodefac.SQL_CODEFAC.getResourceInputStream(nombre);
        String queryDatos= UtilidadesTextos.getStringFromInputStream(inputStreamReporte);
        return queryDatos;
    }
    
}
