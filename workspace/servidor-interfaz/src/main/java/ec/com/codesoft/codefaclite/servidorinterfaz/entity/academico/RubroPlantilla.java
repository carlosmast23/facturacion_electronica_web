/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.MesEnum;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import es.mityc.firmaJava.libreria.utilidades.UtilidadFechas;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "RUBRO_PLANTILLA")
public class RubroPlantilla implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "VALOR")
    private BigDecimal valor;
    
    @Column(name = "DIAS_CREDITO")
    private Integer diasCredito;
 
    @JoinColumn(name = "CATALOGO_PRODUCTO_ID")
    private CatalogoProducto catalogoProducto;

    @JoinColumn(name = "PERIODO_ID")
    private Periodo periodo;
    
    @JoinColumn(name = "ESTADO")
    private String estado;
    
    /**
     * @FECHA_CREADO: 16/10/2018
     */
    @Column(name = "TIPO_VALOR")
    private String tipoValor;
 
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rubroPlantilla",fetch = FetchType.EAGER)
    private List<RubroPlantillaEstudiante> detalles;
 
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rubroPlantilla",fetch = FetchType.EAGER)
    private List<RubroPlantillaMes> mesesGenerados;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

 

    public CatalogoProducto getCatalogoProducto() {
        return catalogoProducto;
    }

    public void setCatalogoProducto(CatalogoProducto catalogoProducto) {
        this.catalogoProducto = catalogoProducto;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public List<RubroPlantillaEstudiante> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<RubroPlantillaEstudiante> detalles) {
        this.detalles = detalles;
    }

    

    public Integer getDiasCredito() {
        return diasCredito;
    }

    public void setDiasCredito(Integer diasCredito) {
        this.diasCredito = diasCredito;
    }

    public List<RubroPlantillaMes> getMesesGenerados() {
        return mesesGenerados;
    }

    public void setMesesGenerados(List<RubroPlantillaMes> mesesGenerados) {
        this.mesesGenerados = mesesGenerados;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public GeneralEnumEstado getEstadoEnum()
    {
        return GeneralEnumEstado.getEnum(estado);
    }

    public String getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }
    
    public TipoValorEnum tipoValorEnum()
    {
        return TipoValorEnum.getObject(tipoValor);
    }
    
    // METODO PERSONALIZADO //////////////////
    
    /**
     * Metodo que devuelve solo los detalles de estudiantes activos inscritos en la plantilla
     * @return 
     */
    public List<RubroPlantillaEstudiante> getDetallesActivos() {
        List<RubroPlantillaEstudiante> listaResultado=new ArrayList<RubroPlantillaEstudiante>();
        
        for (RubroPlantillaEstudiante detalle : detalles) {
            //Verifica que devuelva estudiantes que existan y que tengan estado activo
            if(detalle.getEstudianteInscrito()!=null && detalle.getEstudianteInscrito().getEnumEstado().equals(GeneralEnumEstado.ACTIVO))
            {
                listaResultado.add(detalle);
            }
        }   
        return listaResultado;
    }
    
    public void adddDetalle(RubroPlantillaEstudiante detalle)
    {
        if(detalles==null)
        {
            detalles=new ArrayList<RubroPlantillaEstudiante>();
        }
        
        detalle.setRubroPlantilla(this);
        detalles.add(detalle);
    }
    
    public void addMesGenerado(RubroPlantillaMes mes)
    {
    
        if(mesesGenerados==null)
        {
            mesesGenerados=new ArrayList<RubroPlantillaMes>();            
        }
        mes.setRubroPlantilla(this);
        mesesGenerados.add(mes);
    }
    
    public List<RubroPlantillaMes> obtenerTodosMesesGenerar()
    {
        List<RubroPlantillaMes> rubrosMes=new ArrayList<RubroPlantillaMes>();
        
        //Generar todos los meses segun el periodo
        Date fechaInicio=periodo.getFechaInicio();
        Date fechaFinal=periodo.getFechaFin();
        
        Integer anioInicial=UtilidadesFecha.obtenerAnio(fechaInicio);
        Integer anioFinal=UtilidadesFecha.obtenerAnio(fechaFinal);
        
        Integer mesInicial=UtilidadesFecha.obtenerMes(fechaInicio);
        Integer mesFinal=UtilidadesFecha.obtenerMes(fechaFinal);
        
        Integer mesInicialContador=mesInicial;
        
        for (int anio = anioInicial; anio <= anioFinal; anio++) {
            for (int mes =mesInicialContador;mes<=12;mes++) 
            {
                RubroPlantillaMes rpm=new RubroPlantillaMes();
                rpm.setAnio(anio);
                rpm.setNumeroMes(mes);
                rpm.setRubroPlantilla(this);
                
                rubrosMes.add(rpm);
                
                //Comparar si ya termino solo cuando este en el anio final
                if(anio==anioFinal)
                {
                    if(mesFinal==mes)
                    {
                        break;
                    }
                }
            }
            mesInicialContador=1;// Seteo en 0 porque despues de la primera vez los meses empiezan desde enero
        }
        
        return rubrosMes;
    }
    
    
    public List<RubroPlantillaMes> obtenerMesesSinGenerar()
    {
        List<RubroPlantillaMes> rubrosMes=obtenerTodosMesesGenerar();       
   
        if(rubrosMes!=null)
        {
            for (int i=0;i<rubrosMes.size();) {
                //Si ya existe en la lista los borro
                RubroPlantillaMes mes=rubrosMes.get(i);
                
                if(buscarPorAnioYMes(mes.getAnio(),mes.getNumeroMes()))
                {
                    rubrosMes.remove(i);
                }
                else
                {
                    i++;
                }
            }
        }
        
        
        
        return rubrosMes;
    }
    
    private Boolean buscarPorAnioYMes(Integer anio, Integer mes)
    {
        if(mesesGenerados!=null)
        {
            for (RubroPlantillaMes mesPlantilla : mesesGenerados) {
                if(mesPlantilla.getAnio().equals(anio) && mesPlantilla.getNumeroMes().equals(mes))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.id);
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
        final RubroPlantilla other = (RubroPlantilla) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    public enum TipoValorEnum {
        VALOR_FIJO("Fijo", "F"),
        VALOR_VARIABLE("Variable", "V");

        private TipoValorEnum(String nombre, String codigo) {
            this.nombre = nombre;
            this.codigo = codigo;
        }

        public String nombre;
        public String codigo;

        @Override
        public String toString() {
            return nombre;
        }
        
        public static TipoValorEnum getObject(String codigo)
        {
            for (TipoValorEnum object : TipoValorEnum.values()) 
            {
                if(object.codigo.equals(codigo))
                {
                    return object;
                }
            }
            return null;
        }

    }

    
}

