/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.core;

import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import java.io.Serializable;
import org.primefaces.PrimeFaces;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author Carlos
 */
public abstract class GeneralAbstractMb implements Serializable,VistaCodefacIf {

    public abstract void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException;

    public abstract void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException;

    public abstract void editar() throws ExcepcionCodefacLite, UnsupportedOperationException;

    public abstract void imprimir() throws ExcepcionCodefacLite, UnsupportedOperationException;

    public abstract void eliminar() throws ExcepcionCodefacLite, UnsupportedOperationException;

    public abstract void buscar() throws ExcepcionCodefacLite, UnsupportedOperationException;

    //public abstract void cargarBusqueda(Object obj) throws ExcepcionCodefacLite, UnsupportedOperationException;

    public abstract String titulo() throws ExcepcionCodefacLite, UnsupportedOperationException;

    public abstract InterfaceModelFind obtenerDialogoBusqueda();
    //public abstract BuscarDialogoModel obtenerDialogoBusqueda() throws ExcepcionCodefacLite;

    public ResultadoDialogo resultadoDialogo = new ResultadoDialogo();
   
    /**
     * variable que me permite saber si se tiene que mostrar un resultado despues de grabra
     * variable util para saber si tengo que limpiar despues de grabar
     */
    public Boolean mostrarResultadoGrabar=false;
    
    private ReporteDialogListener dialogoReporte;
    
    @ManagedProperty(value = "#{sessionMb}")
    protected SessionMb sessionMb;

    public String linkAyuda() {
        System.out.println("ayuda presionada");
        return "hola";
    }

    public ResultadoDialogo getResultadoDialogo() {
        return resultadoDialogo;
    }

    public void setResultadoDialogo(ResultadoDialogo resultadoDialogo) {
        this.resultadoDialogo = resultadoDialogo;
    }

    protected void mostrarDialogoResultado(CodefacMsj codefacMensaje){
        resultadoDialogo.setMensaje(codefacMensaje.mensaje);
        resultadoDialogo.setTitulo(codefacMensaje.titulo);
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dialogResultado').show();"); //Todo: Parametrizar y poner en una funcion aparte este dialogo
        mostrarResultadoGrabar=true; 
    }
    
    /**
     * Permite Mostrar un dialogo para generar reportes
     */
    protected void mostrarDialogoReporte(ReporteDialogListener listenerReporte)
    {
        this.dialogoReporte=listenerReporte;
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dialogReporte').show();"); //Todo: Parametrizar y poner en una funcion aparte este dialogo
        System.out.println("mostrarDialogoReporte()");
    }
    
    public void aceptarResultado()
    {
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dialogResultado').hide();"); //Todo: Parametrizar y poner en una funcion aparte este dialogo        
    }

    public SessionMb getSessionMb() {
        return sessionMb;
    }

    public void setSessionMb(SessionMb sessionMb) {
        this.sessionMb = sessionMb;
    }

    public Boolean getMostrarResultadoGrabar() {
        return mostrarResultadoGrabar;
    }

    public void setMostrarResultadoGrabar(Boolean mostrarResultadoGrabar) {
        this.mostrarResultadoGrabar = mostrarResultadoGrabar;
    }

    public ReporteDialogListener getDialogoReporte() {
        return dialogoReporte;
    }
    
    
    
    
    
    
}
