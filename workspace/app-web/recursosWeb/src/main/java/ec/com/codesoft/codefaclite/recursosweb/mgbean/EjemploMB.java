/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.recursosweb.mgbean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author
 */
@ManagedBean(name = "ejemploMB")
@ViewScoped
public class EjemploMB implements Serializable{
    
    @ManagedProperty("titulo")
    private String titulo;
    
    @PostConstruct
    public void init() 
    {
        titulo = "PERO ESTA NO 9";
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    
    
}
