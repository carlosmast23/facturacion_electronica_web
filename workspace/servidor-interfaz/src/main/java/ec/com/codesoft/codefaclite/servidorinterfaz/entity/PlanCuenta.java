/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class PlanCuenta implements Serializable{
    
    private List<Cuenta> cuentas;

    public PlanCuenta() {
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }
    
    
    /**
     * METODOS PERSONALIZADOS
     */
    
    /**
     * Obtiene una lista de cuentas que no tiene padres
     * @return 
     */
    public List<Cuenta> obtenerCuentasPrincipales()
    {
        List<Cuenta> cuentasPrincipales=new ArrayList<Cuenta>();
        
        for (Cuenta cuenta : cuentas) {
            if(cuenta.getCuentaPadre()==null)
            {
                cuentasPrincipales.add(cuenta);
            }
        }
        
        return cuentasPrincipales;
    }
    
}
