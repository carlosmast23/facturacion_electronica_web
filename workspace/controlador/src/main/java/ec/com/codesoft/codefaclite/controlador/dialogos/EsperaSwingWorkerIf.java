/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.dialogos;

/**
 *
 * @auhor
 */
public interface EsperaSwingWorkerIf {
    
    /**
     * TODO: Se usa este artificio por que en las clases internas las variables
     * de un ambito superior se pierden los valores
     * @param parametros 
     */
    public void ejecutarTarea(Object[] parametros);
        
}
