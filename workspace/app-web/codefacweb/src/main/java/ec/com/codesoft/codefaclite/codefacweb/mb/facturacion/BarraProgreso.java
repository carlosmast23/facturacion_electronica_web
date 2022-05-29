/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.facturacion;

/**
 *
 * @author Carlos
 */
public class BarraProgreso <T> {
    private String mensajeAlerta;
    private Integer porcentaje;
    private InterfazBoton<T> interfazBoton;
    private T dato;

    public BarraProgreso(T dato,InterfazBoton interfazBoton) {
        this.interfazBoton = interfazBoton;
        this.porcentaje=0;
        this.dato=dato;
    }
    

    public BarraProgreso() {
        this.porcentaje=0;
    }
    
    public BarraProgreso(Integer porcentaje) {
        this.porcentaje=porcentaje;
    }
    

    public Integer getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }

    public InterfazBoton getInterfazBoton() {
        return interfazBoton;
    }

    public void setInterfazBoton(InterfazBoton interfazBoton) {
        this.interfazBoton = interfazBoton;
    }
    
    public void imprimir()
    {
        interfazBoton.imprimirListener(dato);
    }
    
    public String titulo()
    {
        return interfazBoton.tituloBarra(dato);
    }
    
    public void alerta()
    {
        interfazBoton.alertaListener(mensajeAlerta);
    }

    public String getMensajeAlerta() {
        return mensajeAlerta;
    }

    public void setMensajeAlerta(String mensajeAlerta) {
        this.mensajeAlerta = mensajeAlerta;
    }
    
    
    

    public interface InterfazBoton<T>
    {
        public void alertaListener(String mensajeAlerta);
        public void imprimirListener(T dato);
        public String tituloBarra(T dato);
        
        //public void cerrar();
    };
    
}
