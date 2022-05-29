/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.ats.jaxb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Carlos
 */
//@XmlRootElement(name = "ventas")
@XmlType(propOrder = {
    "tpIdCliente",
    "idCliente",
    "parteRelVtas",
    "tipoCliente",
    "denoCli",
    "tipoComprobante",
    "tipoEmision",
    "numeroComprobantes",
    "baseNoGraIva",
    "baseImponible",
    "baseImpGrav",
    "montoIva",
    "montoIce",
    "valorRetIva",
    "valorRetRenta",
    "formasDePago"})
public class VentaAts implements Serializable{
    private String tpIdCliente;
    private String idCliente;
    private String tipoCliente; //Campo cuando el cliente es estranjero
    /**
     * Razon social o denomonicacion del cliente
     */
    private String denoCli; //Campo cuando el cliente es estranjero
    /**
     * Este campo debe ser ingresado unicamente cuando el cliente es diferente de cliente final es decir 07
     */
    private String parteRelVtas;
    private String tipoComprobante; // TODO: Este es el codigo del tipo de comprobante de la tabla4
    private String tipoEmision; //Esta relacionado con el valor de TipoEmisionEnum
    private int numeroComprobantes;
    private BigDecimal baseNoGraIva;  //Variable que tiene el subtotal de los productos que no graban iva
    private BigDecimal baseImponible; //Variable que tiene el subtotal sin impuestos
    private BigDecimal baseImpGrav; //Variable que tiene el subtotal con impuestos
    private BigDecimal montoIva;
    //TODO: Ver si tengo que aumentar el valor de compensaciones
    private BigDecimal montoIce;
    private BigDecimal valorRetIva;
    private BigDecimal valorRetRenta;
    
    private List<FormaDePagoAts> formasDePago;

    public VentaAts() {
    }

    @XmlElementWrapper(name = "formasDePago")
    @XmlElement(name = "formaPago")
    public List<FormaDePagoAts> getFormasDePago() {
        return formasDePago;
    }

    @XmlElement(name = "denoCli")
    public String getDenoCli() {
        return denoCli;
    }

    @XmlElement(name = "tpIdCliente")
    public String getTpIdCliente() {
        return tpIdCliente;
    }

    @XmlElement(name = "idCliente")
    public String getIdCliente() {
        return idCliente;
    }

    @XmlElement(name = "parteRelVtas")
    public String getParteRelVtas() {
        return parteRelVtas;
    }

    @XmlElement(name = "tipoComprobante")
    public String getTipoComprobante() {
        return tipoComprobante;
    }

    @XmlElement(name = "tipoEmision")
    public String getTipoEmision() {
        return tipoEmision;
    }

    @XmlElement(name = "numeroComprobantes")
    public int getNumeroComprobantes() {
        return numeroComprobantes;
    }

    @XmlElement(name = "baseNoGraIva")
    public BigDecimal getBaseNoGraIva() {
        return baseNoGraIva;
    }

    @XmlElement(name = "baseImponible")
    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    @XmlElement(name = "baseImpGrav")
    public BigDecimal getBaseImpGrav() {
        return baseImpGrav;
    }

    @XmlElement(name = "montoIva")
    public BigDecimal getMontoIva() {
        return montoIva;
    }

    @XmlElement(name = "montoIce")
    public BigDecimal getMontoIce() {
        return montoIce;
    }

    @XmlElement(name = "valorRetIva")
    public BigDecimal getValorRetIva() {
        return valorRetIva;
    }

    @XmlElement(name = "valorRetRenta")
    public BigDecimal getValorRetRenta() {
        return valorRetRenta;
    }
    

    public void setTpIdCliente(String tpIdCliente) {
        this.tpIdCliente = tpIdCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public void setNumeroComprobantes(int numeroComprobantes) {
        this.numeroComprobantes = numeroComprobantes;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    public void setMontoIva(BigDecimal montoIva) {
        this.montoIva = montoIva;
    }

    public void setFormasDePago(List<FormaDePagoAts> formasDePago) {
        this.formasDePago = formasDePago;
    }

    public void setParteRelVtas(String parteRelVtas) {
        this.parteRelVtas = parteRelVtas;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public void setTipoEmision(String tipoEmision) {
        this.tipoEmision = tipoEmision;
    }

    public void setBaseNoGraIva(BigDecimal baseNoGraIva) {
        this.baseNoGraIva = baseNoGraIva;
    }

    public void setBaseImpGrav(BigDecimal baseImpGrav) {
        this.baseImpGrav = baseImpGrav;
    }

    public void setMontoIce(BigDecimal montoIce) {
        this.montoIce = montoIce;
    }

    public void setValorRetIva(BigDecimal valorRetIva) {
        this.valorRetIva = valorRetIva;
    }

    public void setValorRetRenta(BigDecimal valorRetRenta) {
        this.valorRetRenta = valorRetRenta;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public void setDenoCli(String denoCli) {
        this.denoCli = denoCli;
    }
    
    
    
    
    

    /**
     * OTROS DATOS ADICIONALES
     */
    
    public enum TipoEmisionEnum
    {
        FISICA("Fisica","F"),
        ELECTRONICA("Electronica","E");
        
        public String nombre;
        public String letra;
        
        private TipoEmisionEnum(String nombre,String letra)
        {
            this.nombre=nombre;
            this.letra=letra;
        }        
        
    }
    
}
