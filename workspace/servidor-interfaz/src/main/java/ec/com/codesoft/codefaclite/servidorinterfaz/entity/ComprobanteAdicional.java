/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Carlos
 */
@MappedSuperclass
public class ComprobanteAdicional implements Serializable{
    
    //public abstract ComprobanteAdicional crearInstancia(ComprobanteEntity padre);
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    
    @Column(name = "CAMPO")
    private String campo;
    
    @Column(name = "VALOR")
    private String valor;
    
    @Column(name = "TIPO")
    private String tipo;
    
    
    /**
     * Tipo de dato que se utiliza para numerar tipo de datos de un mismo tipo por ejemplo
     * los correo, correo 1 , correo 2 , correo 3
     */
    @Column(name = "NUMERO")
    private Integer numero;

    public ComprobanteAdicional() {
    }

    public ComprobanteAdicional(String campo, String valor, ComprobanteAdicional.Tipo tipo) {
        this.campo = campo;
        this.valor = valor;
        this.tipo = tipo.getLetra();
    }
    
    public ComprobanteAdicional(String correo, ComprobanteAdicional.Tipo tipo, ComprobanteAdicional.CampoDefectoEnum campoDefecto) {
        //ComprobanteAdicional comprobante = crearInstancia(padre);
        //comprobante.setCampo(CampoDefectoEnum.);
        this.campo=campoDefecto.getNombre();
        this.tipo=tipo.getLetra();
        this.valor=correo;
    }
    
    
   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public void setTipoEnum(Tipo tipoEnum) {
        this.tipo = tipoEnum.getLetra();
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    
    public Tipo getTipoEnum() {
        return Tipo.getByLetra(tipo);
    }
    
    /**
     * =====================> METODOS PERSONALIZADOS ==================>
     */
    /*public ComprobanteAdicional crearComprobanteAdicional(ComprobanteEntity padre,String correo,ComprobanteAdicional.Tipo tipoCorreo,ComprobanteAdicional.CampoDefectoEnum campoDefecto)
    {
        ComprobanteAdicional comprobante= crearInstancia(padre);
        //comprobante.setCampo(CampoDefectoEnum.);
        comprobante.setCampo(campoDefecto.getNombre());
        comprobante.setTipo(tipoCorreo.getLetra());
        comprobante.setValor(correo);
        return comprobante;
    }*/

    /*public void setTipoEnum(Tipo tipo) {
        this.tipo=tipo.getLetra();
    }*/

    /**
     * Enum que contiene los nombres de los campos por defecto que puede almacenar esta tabla
     */
    public enum CampoDefectoEnum
    {               
        CELULAR("celular"),
        CORREO("correo"),
        FECHA_VENCIMIENTO("Fecha Maxima de Pago"),
        GUIA_REMISION("Guia de Remision"),
        VENDEDOR("Vendedor");
        
        private String nombre;

        private CampoDefectoEnum(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }
        
        public static CampoDefectoEnum obtenerPorNombre(String nombre)
        {
            for (CampoDefectoEnum enumerador : CampoDefectoEnum.values()) {
                if(enumerador.nombre.equals(nombre))
                {
                    return enumerador;
                }
            }
            return null;
        }
    }
    
    
    
    public enum Tipo
    {
        TIPO_CORREO("c","correo"),
        TIPO_CELULAR("t","celular"),
        TIPO_GUIA_REMISION("g","Guía Remisión"),
        TIPO_OTRO("o","otro");

        private Tipo(String letra, String nombre) {
            this.letra = letra;
            this.nombre = nombre;
        }
        
        private String letra;
        private String nombre;

        public String getLetra() {
            return letra;
        }

        public String getNombre() {
            return nombre;
        }
        
        public static Tipo getByLetra(String letra)
        {
            for (Tipo value : Tipo.values()) {
                if(value.getLetra().equals(letra))
                {
                    return value;
                }
            }
            return null;
        }
        
    }    
    
}
