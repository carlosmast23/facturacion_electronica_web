/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.utilidades.calculos;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author Carlos
 */
public abstract class UtilidadesCalculo {
    
    public static String evaluarExpresion(String expresion)
    {

        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            String respuesta=engine.eval(expresion).toString();
            return respuesta;
        } catch (ScriptException ex) {
            Logger.getLogger(UtilidadesCalculo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "error";
    }
    
}
