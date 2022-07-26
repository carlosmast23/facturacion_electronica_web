/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.RetencionAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Transportista;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author
 */
@Entity
@Table(name = "GUIA_REMISION")
public class GuiaRemision extends ComprobanteEntity<GuiaRemisionAdicional> implements  Serializable{
    
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "DIRECCION_PARTIDA")
    private String direccionPartida;    
    @Column(name = "RISE")
    private String rise;
    @Column(name = "OBLIGADO_LLEVAS_CONTABILIDAD")
    private String obligadoLlevarContabilidad; //Aumentar este campo al ingresar los datos
    @Column(name = "CONTRIBUYENTE_ESPECIAL")
    private String contribuyenteEspecial;
    @Column(name = "FECHA_INICIO_TRANSPORTE")
    private Date fechaIniciaTransporte;
    @Column(name = "FECHA_FIN_TRANSPORTE")
    private Date fechaFinTransporte;
    @Column(name = "PLACA")
    private String placa;
    
    @JoinColumn(name = "TRANSPORTISTA_ID")
    @ManyToOne
    private Transportista transportista;   
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "guiaRemision",fetch = FetchType.EAGER)
    private List<DestinatarioGuiaRemision> destinatarios;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "guiaRemision", fetch = FetchType.EAGER)
    private List<GuiaRemisionAdicional> datosAdicionales;

    public GuiaRemision() {
    }
      
    
    public List<DestinatarioGuiaRemision> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(List<DestinatarioGuiaRemision> destinatarios) {
        this.destinatarios = destinatarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccionPartida() {
        return direccionPartida;
    }

    public void setDireccionPartida(String direccionPartida) {
        this.direccionPartida = direccionPartida;
    }

    public Transportista getTransportista() {
        return transportista;
    }

    public void setTransportista(Transportista transportista) {
        this.transportista = transportista;
    }

    public String getRise() {
        return rise;
    }

    public void setRise(String rise) {
        this.rise = rise;
    }

    public String getObligadoLlevarContabilidad() {
        return obligadoLlevarContabilidad;
    }

    public void setObligadoLlevarContabilidad(String obligadoLlevarContabilidad) {
        this.obligadoLlevarContabilidad = obligadoLlevarContabilidad;
    }

    public String getContribuyenteEspecial() {
        return contribuyenteEspecial;
    }

    public void setContribuyenteEspecial(String contribuyenteEspecial) {
        this.contribuyenteEspecial = contribuyenteEspecial;
    }

    public Date getFechaIniciaTransporte() {
        return fechaIniciaTransporte;
    }

    public void setFechaIniciaTransporte(Date fechaIniciaTransporte) {
        this.fechaIniciaTransporte = fechaIniciaTransporte;
    }

    public Date getFechaFinTransporte() {
        return fechaFinTransporte;
    }

    public void setFechaFinTransporte(Date fechaFinTransporte) {
        this.fechaFinTransporte = fechaFinTransporte;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    
    public void addDatosAdicionalCorreo(String correo, FacturaAdicional.Tipo tipoCorreo, FacturaAdicional.CampoDefectoEnum campoDefecto) {
        
        //Validacion para no ingresar correos vacios o nulos
        if(correo==null || correo.trim().isEmpty())
        {
           return ; 
        }
        
        GuiaRemisionAdicional guiaRemisionAdicional = new GuiaRemisionAdicional();
        guiaRemisionAdicional.setCampo(campoDefecto.getNombre());
        guiaRemisionAdicional.setGuiaRemision(this);
        guiaRemisionAdicional.setTipo(tipoCorreo.getLetra());
        guiaRemisionAdicional.setValor(correo);

        if (this.datosAdicionales == null) {
            this.datosAdicionales = new ArrayList<GuiaRemisionAdicional>();
        }

        //Buscar si existe un correo anterior spara nombrar de forma secuencial
        Integer numeroMaximo = 0;
        for (GuiaRemisionAdicional datoAdicional : datosAdicionales) {
            if (datoAdicional.getTipo().equals(tipoCorreo.getLetra())) {
                if (datoAdicional.getNumero() > numeroMaximo) {
                    numeroMaximo = datoAdicional.getNumero();
                }
            }
        }

        guiaRemisionAdicional.setNumero(numeroMaximo + 1);
        //Modificar el nombre si el correo es mas de 2
        if (guiaRemisionAdicional.getNumero() > 1) {
            guiaRemisionAdicional.setCampo(campoDefecto.getNombre() + " " + guiaRemisionAdicional.getNumero());
        }

        this.datosAdicionales.add(guiaRemisionAdicional);

    }
    
    /**
     * Agregar Destinatarios a la guia de remision
     * @param detalle 
     */
    public void addDestinario(DestinatarioGuiaRemision detalle) {
        if (this.destinatarios == null) {
            this.destinatarios = new ArrayList<DestinatarioGuiaRemision>();
        }
        detalle.setGuiaRemision(this);
        this.destinatarios.add(detalle);
    }

    public Integer obtenerTotalProductos()
    {
        Integer cantidad=0;
        if(destinatarios!=null)
        {
            for (DestinatarioGuiaRemision destinatario : destinatarios) 
            {
                for (DetalleProductoGuiaRemision detalle : destinatario.getDetallesProductos()) {
                    cantidad+=detalle.getCantidad();
                }
            }
        }
        return cantidad;
    }

    public List<GuiaRemisionAdicional> getDatosAdicionales() {
        return datosAdicionales;
    }

    public void setDatosAdicionales(List<GuiaRemisionAdicional> datosAdicionales) {
        this.datosAdicionales = datosAdicionales;
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
        final GuiaRemision other = (GuiaRemision) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    /**
     * =============> DATOS ADICIONALES <===============
     */
    public Integer obtenerTotalItems()
    {
        Integer total=0;
        if(destinatarios!=null)
        {
            for (DestinatarioGuiaRemision destinatario : destinatarios) {
                if(destinatario.getDetallesProductos()!=null)
                {
                    for (DetalleProductoGuiaRemision detalleProducto : destinatario.getDetallesProductos()) {
                       total+=detalleProducto.getCantidad(); 
                    }
                }
            }
        }
        return total;
    }

    /**
     * ===================> obtener los numeros de las facturas <==================
     */    
    
    public List<String> obtenerFacturasTransportadas()
    {
        List<String> facturasPreimpreso=new ArrayList<String>();
        if(destinatarios!=null)
        {
            for (DestinatarioGuiaRemision destinatario : destinatarios) {
                if(destinatario.getDetallesProductos()!=null)
                {
                    facturasPreimpreso.add(destinatario.getPreimpreso());
                }
            }
        }
        return facturasPreimpreso;
    }

    @Override
    public List<GuiaRemisionAdicional> getDatosAdicionalesComprobante() {
        return getDatosAdicionales();
    }

    /**
     * TODO: Revisa si este codigo esta duplicado
     * @param comprobanteAdicional 
     */
    @Override
    public void addDatoAdicionalAbstract(GuiaRemisionAdicional comprobanteAdicional) {
        GuiaRemisionAdicional datoAdicional=(GuiaRemisionAdicional) comprobanteAdicional;
        if(this.datosAdicionales==null)
        {
            this.datosAdicionales=new ArrayList<GuiaRemisionAdicional>();
        }
        datoAdicional.setGuiaRemision(this);
        this.datosAdicionales.add(datoAdicional);
    }
    
    public void eliminarDetalleProducto(DetalleProductoGuiaRemision detalle)
    {
        if(destinatarios!=null)
        {
            for (DestinatarioGuiaRemision destinatario : destinatarios) 
            {
                //Eliminar en cualquier listado de destinatarios
                destinatario.getDetallesProductos().remove(detalle);
            }
        }
    }
    
    /**
     * TODO: Ver si puedo unificar este metodo con el resto de los comprobantes electronicos
     * @return 
     */
    public Map<String, String> getMapAdicional() {
        Map<String, String> parametroMap = new LinkedHashMap<String, String>();
        if (getDatosAdicionales() != null) {
            for (GuiaRemisionAdicional datoAdicional : getDatosAdicionales()) {
                parametroMap.put(datoAdicional.getCampo(), datoAdicional.getValor());
            }
        }
        return parametroMap;
    }
} 
