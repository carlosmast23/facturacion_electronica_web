/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.other;

/**
 *
 * @author Carlos
 */
public class ArchivoDescarga {
    public String nombreArchivo;
    public String url;
    public String destino;

    public ArchivoDescarga(String nombreArchivo, String url, String destino) {
        this.nombreArchivo = nombreArchivo;
        this.url = url;
        this.destino = destino;
    }
}
