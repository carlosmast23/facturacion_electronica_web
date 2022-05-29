/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.dialogmodel;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.sistema.UtilidadesWeb;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Carlos
 */
@ManagedBean
@ViewScoped
public class DatoAdicionalDialogoMb implements Serializable{
    private ComprobanteAdicional comprobanteAdicional;
    private ComprobanteAdicional.Tipo[] tipos;
    private ComprobanteAdicional.Tipo tipoSeleccionado;
    private Boolean habilitarCampo;
    
    @PostConstruct
    public void init()
    {
        comprobanteAdicional=new ComprobanteAdicional();
        tipos=ComprobanteAdicional.Tipo.values();
        tipoSeleccionado=ComprobanteAdicional.Tipo.TIPO_CORREO;
        habilitarCampo=true;
        setearNombreAutomatico();
    }
    
    public void setearNombreAutomatico()
    {
        System.out.println("Evento combo:"+tipoSeleccionado);
        if(!tipoSeleccionado.equals(tipoSeleccionado.TIPO_OTRO))
        {
            habilitarCampo=false;
            comprobanteAdicional.setCampo(tipoSeleccionado.getNombre());
        }
        else
        {
            habilitarCampo=true;
            comprobanteAdicional.setCampo("");
        }
    }
    
    public void retornarValor()
    {
        comprobanteAdicional.setTipoEnum(tipoSeleccionado);
        UtilidadesWeb.retornarResultadoDialogo(comprobanteAdicional);
    }

    public ComprobanteAdicional getComprobanteAdicional() {
        return comprobanteAdicional;
    }

    public void setComprobanteAdicional(ComprobanteAdicional comprobanteAdicional) {
        this.comprobanteAdicional = comprobanteAdicional;
    }

    public ComprobanteAdicional.Tipo[] getTipos() {
        return tipos;
    }

    public void setTipos(ComprobanteAdicional.Tipo[] tipos) {
        this.tipos = tipos;
    }

    public ComprobanteAdicional.Tipo getTipoSeleccionado() {
        return tipoSeleccionado;
    }

    public void setTipoSeleccionado(ComprobanteAdicional.Tipo tipoSeleccionado) {
        this.tipoSeleccionado = tipoSeleccionado;
    }

    public Boolean getHabilitarCampo() {
        return habilitarCampo;
    }

    public void setHabilitarCampo(Boolean habilitarCampo) {
        this.habilitarCampo = habilitarCampo;
    }
    
    
    
    
    
}
