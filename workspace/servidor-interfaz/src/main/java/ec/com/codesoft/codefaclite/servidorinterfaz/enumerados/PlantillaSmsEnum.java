/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.enumerados;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public enum PlantillaSmsEnum {
    LIBRE("Libre","",new VentanaEnum[]{VentanaEnum.CLIENTE,VentanaEnum.ESTUDIANTES}),
    SALUDO("Saludo",EtiquetaEnum.EMPRESA.getTag()+" le saluda y agredece \n ser parte de nuestros clientes.",new VentanaEnum[]{VentanaEnum.CLIENTE}),
    ACERCARSE_PAGAR("Pagar deuda",EtiquetaEnum.EMPRESA.getTag()+" le informa \n que tiene un saldo pendiente.",new VentanaEnum[]{VentanaEnum.CLIENTE}),
    TRABAJO_TERMINADO("Trabajo listo",EtiquetaEnum.EMPRESA.getTag()+" le informa \n que su trabajo esta listo \n y puede pasar a retirar.",new VentanaEnum[]{VentanaEnum.CLIENTE}),
    
    NOTIFICACION_ESTUDIANTE("Notificación ",EtiquetaEnum.EMPRESA.getTag()+" le informa \nque el estudiante "+EtiquetaEnum.ESTUDIANTE_NOMBRE.getTag()+"\n tiene que venir con el representante",new VentanaEnum[]{VentanaEnum.ESTUDIANTES}),
    NOTIFICACION_DEUDA_ESTUDIANTE("Notificación deuda",EtiquetaEnum.EMPRESA.getTag()+" le informa \nque el estudiante "+EtiquetaEnum.ESTUDIANTE_NOMBRE.getTag()+"\n tiene valores pendientes por pagar",new VentanaEnum[]{VentanaEnum.ESTUDIANTES}),
    
    NOTIFICACION_DEUDA_GRUPO("Notificación deuda",EtiquetaEnum.EMPRESA.getTag()+" le informa \nque el estudiante "+EtiquetaEnum.ESTUDIANTE_NOMBRE.getTag()+"\n tiene un valor pendiente de "+EtiquetaEnum.VALOR_PENDIENTE.getTag()+" ,\n Por favor acercarse a cancelar.Saludos",new VentanaEnum[]{VentanaEnum.NOTIFICACION_DEUDAS});

    /**
     * Lista donde estan disponibles las plantillas
     */
    private String nombre;
    private String mensaje;
    private VentanaEnum[] ventanas;

    private PlantillaSmsEnum(String nombre, String mensaje, VentanaEnum[] ventanas) {
        this.nombre = nombre;
        this.mensaje = mensaje;
        this.ventanas = ventanas;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMensaje() {
        return mensaje;
    }

    public VentanaEnum[] getVentanas() {
        return ventanas;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
    
    /**
     * Metodos personalizados
     */
    
    public static List<PlantillaSmsEnum> findListByVentana(VentanaEnum ventanaEnum)
    {
        List<PlantillaSmsEnum> resultado=new ArrayList<PlantillaSmsEnum>();
        for (PlantillaSmsEnum plantillaSms : PlantillaSmsEnum.values()) {
            for (VentanaEnum ventana : plantillaSms.ventanas) {
                if(ventana.equals(ventanaEnum))
                {
                    resultado.add(plantillaSms);
                }
            }
        }
        
        return resultado;
    }
    
    
    public enum EtiquetaEnum
    {
        EMPRESA("empresa"),
        CLIENTE("cliente"),
        ESTUDIANTE_NOMBRE("estudiante"),
        VALOR_PENDIENTE("valor"),
        ;
        

        private EtiquetaEnum(String nombreEtiqueta) {
            this.nombreEtiqueta=nombreEtiqueta;
        }
        
        private String nombreEtiqueta;
        
        public String getTag()
        {
            return "["+nombreEtiqueta+"]";
        }
        
        
    }
}
