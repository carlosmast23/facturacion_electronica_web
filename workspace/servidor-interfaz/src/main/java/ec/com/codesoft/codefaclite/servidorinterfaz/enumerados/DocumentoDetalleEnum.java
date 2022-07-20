/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @auhor
 */
public enum DocumentoDetalleEnum {
    RETENCION_IVA("Retención Renta","RRR",DocumentoEnum.RETENCIONES),
    RETENCION_RENTA("Retención Iva","RRI",DocumentoEnum.RETENCIONES)    
    ;
        
    private String nombre;
    private String codigo;
    private DocumentoEnum documentoEnum;

    private DocumentoDetalleEnum(String nombre,String codigo,DocumentoEnum documentoEnum) {
        this.nombre = nombre;
        this.codigo=codigo;
        this.documentoEnum=documentoEnum;
    }

    public DocumentoEnum getDocumentoEnum() {
        return documentoEnum;
    }

    public void setDocumentoEnum(DocumentoEnum documentoEnum) {
        this.documentoEnum = documentoEnum;
    }

    public String getCodigo() {
        return codigo;
    }
    
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    public static DocumentoDetalleEnum findByCodigo(String codigo)
    {
        for (DocumentoDetalleEnum value : DocumentoDetalleEnum.values()) {
            if(value.getCodigo().equals(codigo))
            {
                return value;
            }
        }
        return null;
    }
    
    public static List<DocumentoDetalleEnum> findListByDocumentoEnum(DocumentoEnum documentoEnum)
    {
        List<DocumentoDetalleEnum> listaResultado=new ArrayList<DocumentoDetalleEnum>();
        for (DocumentoDetalleEnum value : DocumentoDetalleEnum.values()) {
            if(value.getDocumentoEnum().equals(documentoEnum))
            {
                listaResultado.add(value);
            }
        }
        return listaResultado;
    }
    
}
