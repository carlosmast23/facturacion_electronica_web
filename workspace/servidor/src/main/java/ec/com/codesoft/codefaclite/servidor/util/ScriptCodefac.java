/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.util;

/**
 *
 * @author
 */
public class ScriptCodefac {
    private String query;
   private PrioridadQueryEnum prioridad;
    /**
     * Variable que sirve cuando el usuario no quiere mostrar las alertas al usuario de determinado query si se produce un error
     */
    private boolean mostrarAdvertencia;

    public ScriptCodefac(String query, PrioridadQueryEnum prioridad) {
        this.query = query;
        this.prioridad = prioridad;
        this.mostrarAdvertencia=true;
    }

    public ScriptCodefac(String query, PrioridadQueryEnum prioridad, String mostrarAdvertencia) {
        this.query = query;
        this.prioridad = prioridad;
        this.mostrarAdvertencia =convertirTextoBoolean(mostrarAdvertencia);
    }
    
    private boolean convertirTextoBoolean(String mostrarAdvertencia)
    {
        if(mostrarAdvertencia.toUpperCase().equals("NO"))
        {
            return false;
        }
        
        if(mostrarAdvertencia.toUpperCase().equals("N"))
        {
            return false;
        }
        
        return true;
    }
    

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public PrioridadQueryEnum getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(PrioridadQueryEnum prioridad) {
        this.prioridad = prioridad;
    }

    public boolean isMostrarAdvertencia() {
        return mostrarAdvertencia;
    }

    public void setMostrarAdvertencia(boolean mostrarAdvertencia) {
        this.mostrarAdvertencia = mostrarAdvertencia;
    }
    
    
    
        
    public enum PrioridadQueryEnum
    {
        CREATE_TABLE(1),
        INSERT_COLUMN(2),
        OTHER_SCRIPT(3);
        
        private int numero;

        private PrioridadQueryEnum(int numero) {
            this.numero = numero;
        }

        public int getNumero() {
            return numero;
        }
        
    }
}
