/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.sql.Date;

/**
 *
 * @author
 */
public interface CarteraInterface {
    public String getCodigoDocumento();
    public Empresa getEmpresa();
    public Date getFechaCreacion();
    public Date getFechaEmision();
    public Integer getPuntoEmision();
    public Integer getSecuencial();
}
