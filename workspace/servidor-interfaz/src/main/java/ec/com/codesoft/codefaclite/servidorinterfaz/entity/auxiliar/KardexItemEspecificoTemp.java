/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.auxiliar;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.KardexItemEspecifico;

/**
 *
 * @author Carlos
 */
public class KardexItemEspecificoTemp extends KardexItemEspecifico{
    public boolean seleccion;
    
    //TODO: Ver alguna forma de actualizar todos los campos si agrego mas atributos
    public KardexItemEspecifico obtenerObjetoOriginal()
    {
        KardexItemEspecifico item=new KardexItemEspecifico();
        item.setCodigoEspecifico(this.getCodigoEspecifico());
        item.setEstado(this.getEstado());
        item.setKardexDetalle(this.getKardexDetalle()); //TODO: Analizar este tema de agregar el kardex detalle porque se esta agregando con la antigua referencia
        item.setObservaciones(this.getObservaciones());
        return item;
    }
}
