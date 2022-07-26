/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.ayuda.componentes;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.PlantillaSmsEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import java.util.Map;

/**
 *
 * @author
 */
public class ComponenteEnvioSmsData {
    
    private String numeroTelefono;
    private Map<PlantillaSmsEnum.EtiquetaEnum,String> plantillaTags;

    public ComponenteEnvioSmsData(String numeroTelefono, Map<PlantillaSmsEnum.EtiquetaEnum, String> plantillaTags) {
        this.numeroTelefono = numeroTelefono;
        this.plantillaTags = plantillaTags;
    }


    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public Map<PlantillaSmsEnum.EtiquetaEnum, String> getPlantillaTags() {
        return plantillaTags;
    }

    public void setPlantillaTags(Map<PlantillaSmsEnum.EtiquetaEnum, String> plantillaTags) {
        this.plantillaTags = plantillaTags;
    }
 
    
}
