/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

//import com.sun.imageio.plugins.common.BogusColorSpace;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.utilidades.validadores.UtilidadBigDecimal;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import javax.inject.Singleton;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "FACTURA_DETALLE")
public class FacturaDetalle extends DetalleFacturaNotaCeditoAbstract implements Cloneable{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(name = "PRODUCTO_ID")
    //private Long productoId;
   



    @JoinColumn(name = "FACTURA_ID")
    @ManyToOne(optional = false)
    private Factura factura;


    @Column(name = "PRECIO_SIN_SUBSIDIO")
    private BigDecimal precioSinSubsidio; 


    public FacturaDetalle() {
        
    }

    public Long getId() {
        return id;
    }

    

    public void setId(Long id) {
        this.id = id;
    }

    
    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public BigDecimal getPrecioSinSubsidio() {
        return precioSinSubsidio;
    }

    public void setPrecioSinSubsidio(BigDecimal precioSinSubsidio) {
        this.precioSinSubsidio = precioSinSubsidio;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FacturaDetalle other = (FacturaDetalle) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    /**
     * ====================> METODOS PERSONALIZADOS <===================== *
     */
    
    public BigDecimal calcularSubsidio()
    {
        if(precioSinSubsidio!=null)
        {
            return precioSinSubsidio.subtract(getPrecioUnitario()).multiply(getCantidad());
        }
        return BigDecimal.ZERO;
    }

    /**
     * El total bruto se refiere al subtotal incluido el descuento
     * @return 
     */
    /*public BigDecimal calcularTotalBruto()
    {
        return getCantidad().multiply(getPrecioUnitario()).add(getDescuento());
    }*/
    
    public void calcularTotalesDetallesFactura()
    {
        //Calular el total despues del descuento porque necesito esa valor para grabar
        
        calcularTotalDetalle();
        /**
         * Revisar este calculo del iva para no calcular 2 veces al mostrar
         */
        setIvaPorcentaje(getIvaPorcentaje());
        if(getIcePorcentaje()!=null)
        {
            calcularValorIce(getIcePorcentaje());
        }
        
             
        calculaIva();
    }
    
}
