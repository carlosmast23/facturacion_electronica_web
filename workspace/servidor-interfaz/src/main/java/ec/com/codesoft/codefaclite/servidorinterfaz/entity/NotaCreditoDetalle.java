/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "NOTA_CREDITO_DETALLE")
public class NotaCreditoDetalle extends DetalleFacturaNotaCeditoAbstract {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JoinColumn(name="NOTA_CREDITO_ID")
    @ManyToOne(optional = false)
    private NotaCredito notaCredito;
    

        
    /**
     * Variable adicional para grabar el porcentaje del iva
     * @fecha_modificacion 11/10/2018
     */
    //@Column(name = "IVA_PORCENTAJE")
    //private Integer ivaPorcentaje;

    public NotaCreditoDetalle() {
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    public NotaCredito getNotaCredito() {
        return notaCredito;
    }

    public void setNotaCredito(NotaCredito notaCredito) {
        this.notaCredito = notaCredito;
    }



    //public Integer getIvaPorcentaje() {
    //    return ivaPorcentaje;
    //}

    //public void setIvaPorcentaje(Integer ivaPorcentaje) {
    //    this.ivaPorcentaje = ivaPorcentaje;
    //}
    
    /**
     * ==================================================================
     *                  METODOS PERSONALIZADOS
     * ==================================================================     
     */
    
    /**
     * Este valor se supone que debe ser el final tomando en cuenta descuentos , ice e iva
     */
    //public BigDecimal calcularTotalFinal()
    //{
    //    return this.total.add(iva);
    //}

    //Metodos personalizados implementados
    /*public TipoDocumentoEnum getTipoDocumentoEnum()
    {
        return TipoDocumentoEnum.obtenerTipoDocumentoPorCodigo(getTipoReferencia());
    }*/
    
    
}
