/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.utilidades;

/**
 *
 * @auhor
 */
public class TablaNombreColumna 
{
    private String cabecera;
    private String propiedad;
 
    public TablaNombreColumna(String cabecera, String propiedad) {
        this.cabecera = cabecera;
        this.propiedad = propiedad;
    }

    public String getCabecera() {
        return cabecera;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public String getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(String propiedad) {
        this.propiedad = propiedad;
    }
        
}
