/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.recursos;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public enum RecursoCodefac {
    IMAGENES_ICONOS("img/iconos"),
    IMAGENES_ICONOS_DIALOGOS("img/iconos/dialogos"),
    IMAGENES_GENERAL("img/general"),
    IMAGENES_ACCESO_DIRECTO("img/accesoDirecto"),
    IMAGENES_REDES_SOCIALES("img/redes_sociales"),
    IMAGENES_PUBLICIDAD("img/publicidad"),
    SQL("sql"),
    SQL_CODEFAC("sql/codefac"),
    JASPER_INVENTARIO("reportes/inventario"),
    JASPER_CARTERA("reportes/cartera"),
    JASPER_CRM("reportes/crm"),
    JASPER_TRANSPORTE("reportes/transporte"),
    JASPER_FACTURACION("reportes/facturacion"),
    JASPER_COMPRA("reportes/compra"),
    JASPER_ACADEMICO("reportes/academico"),
    JASPER_ESTUDIANTE("reportes/estudiante"),
    JASPER_COMPROBANTES_ELECTRONICOS("reportes/comprobantes_electronicos"),
    JASPER_COMPROBANTES_FISICOS("reportes/comprobantes_fisicos"),
    JASPER_SERVICIO("reportes/servicio"),
    JASPER_POS("reportes/pos"),
    JASPER("reportes"),
    HTML("html/factura_electronica"),
    AYUDA("ayuda"),
    PLANTILLAS_EXCEL("plantillas_excel");

    private String subPathResource;

    private RecursoCodefac(String subPathResource) {
        this.subPathResource = subPathResource;
    }

    public URL getURL() {
        return getClass().getResource(subPathResource);
    }

    public String getPath() {
        return getClass().getResource(subPathResource).getPath();
    }

    public URL getResourceURL(String file) {
        return getClass().getResource("/" + subPathResource + "/" + file);
    }

    //public String getResourcePath(String file) {
    //    return getClass().getResource("/" + subPathResource + "/" + file).getPath();
    //}

    public String getResourcesParentPath(String file) {
        URL path = getResourceURL(file);
        File archivo = new File(path.toExternalForm());
        return archivo.getParentFile().toURI().getPath();
    }

    public InputStream getResourceInputStream(String file) {
        return getClass().getResourceAsStream("/" + subPathResource + "/" + file);
    }

}
