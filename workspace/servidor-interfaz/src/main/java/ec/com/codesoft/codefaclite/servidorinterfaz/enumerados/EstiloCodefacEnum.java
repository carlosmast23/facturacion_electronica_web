/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import java.util.Iterator;

/**
 *
 * @author
 */
public enum EstiloCodefacEnum {
    DEFAULT("Default","javax.swing.plaf.metal.MetalLookAndFeel"),
    GLASS("Glass","com.seaglasslookandfeel.SeaGlassLookAndFeel"),
    TEXTURE("Texture","com.jtattoo.plaf.texture.TextureLookAndFeel"),
    MCWIN("McWin","com.jtattoo.plaf.mcwin.McWinLookAndFeel"),
    MINT("Mint","com.jtattoo.plaf.mint.MintLookAndFeel"),
    GRAPHITE("Graphite","com.jtattoo.plaf.graphite.GraphiteLookAndFeel"),
    FAST("Fast","com.jtattoo.plaf.fast.FastLookAndFeel"),
    ALUMINIUM("Aluminium","com.jtattoo.plaf.aluminium.AluminiumLookAndFeel"),
    AERO("Aero","com.jtattoo.plaf.aero.AeroLookAndFeel"),
    ACRYL("Acryl","com.jtattoo.plaf.acryl.AcrylLookAndFeel"),
    SMART("Smart","com.jtattoo.plaf.smart.SmartLookAndFeel"),;
    

    private EstiloCodefacEnum(String nombre, String className) {
        this.nombre = nombre;
        this.className = className;
    }
    
    private String nombre;
    private String className;

    public String getNombre() {
        return nombre;
    }

    public String getClassName() {
        return className;
    }
    
    public static EstiloCodefacEnum findByNombre(String nombre)
    {
        for(EstiloCodefacEnum estiloEnum : EstiloCodefacEnum.values()) 
        {
            if(estiloEnum.getNombre().equals(nombre))
            {
                return estiloEnum;
            }
            
        }
        return null;
    }
    
}
