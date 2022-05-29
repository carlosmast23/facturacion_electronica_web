/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.varios;

/**
 *
 * @author Carlos
 */
public class PreimpresoFormato {
    public String establecimiento;
    public String puntoEmision;
    public String secuencial;

    public PreimpresoFormato(String preimpreso) {
        if(preimpreso.length()>0)
        {
            establecimiento=preimpreso.substring(0, 3);
            puntoEmision=preimpreso.substring(3, 6);
            secuencial=preimpreso.substring(6);
        }
        else
        {
            establecimiento="";
            puntoEmision="";
            secuencial="";        
        }
    }
    

    public PreimpresoFormato(String establecimiento, String puntoEmision, String secuencial) {
        this.establecimiento = establecimiento;
        this.puntoEmision = puntoEmision;
        this.secuencial = secuencial;
    }
    
    public String formatoConLineas()
    {
        return establecimiento+"-"+puntoEmision+"-"+secuencial;
    }
    
    public String formatoSinLineas()
    {
        return establecimiento+puntoEmision+secuencial;
    }
    
}
