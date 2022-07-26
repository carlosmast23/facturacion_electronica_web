/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade.cartera;

import ec.com.codesoft.codefaclite.servidor.facade.AbstractFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import java.sql.Date;
import java.util.List;
import javax.persistence.NoResultException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraCruce;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.CarteraDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CarteraEstadoReporteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CategoriaMenuEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoCategoriaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.FlushModeType;
import javax.persistence.Query;

/**
 *
 * @author
 */
public class CarteraFacade extends AbstractFacade<Cartera>
{    
    public CarteraFacade() 
    {
        super(Cartera.class);
    }
      
    public List<Cartera> getCarteraSaldoCero(Persona persona,Long segundaReferenciaId, Date fi, Date ff,DocumentoCategoriaEnum categoriaMenuEnum,Cartera.TipoCarteraEnum tipoCartera,Cartera.TipoSaldoCarteraEnum tipoSaldoEnum,Cartera.TipoOrdenamientoEnum tipoOrdenamientoEnum,CarteraEstadoReporteEnum carteraEstadoReporteEnum,Sucursal sucursal,DocumentoEnum documentoEnum)
    {
        String cliente = "";
        String fecha = "";
        String saldo = "";
        String whereTipoCarteraVencida="";
        String whereDocumento="";
        
        
        if (persona != null) {
            cliente = "c.persona=?1";
        } else {
            cliente = "1=1";
        }
        if (fi == null && ff != null) {
            fecha = " AND c.fechaEmision <= ?3";
        } else if (fi != null && ff == null) {
            fecha = " AND c.fechaEmision >= ?2";
        } else if (fi == null && ff == null) {
            fecha = "";
        } else {
            fecha = " AND (c.fechaEmision BETWEEN ?2 AND ?3)";
        }
        
        Cartera cartera;
        
        
        if(tipoSaldoEnum.equals(Cartera.TipoSaldoCarteraEnum.SIN_SALDO))
        {
            saldo = " AND c.saldo=0 ";    
        }
        else if(tipoSaldoEnum.equals(Cartera.TipoSaldoCarteraEnum.CON_SALDO))
        {
            saldo=" AND c.saldo>0 ";
        }
      
        ////////////////////////////////////////////
        //FILTRAR POR EL TIPO DE CARTERA VENCIDA  //
        ////////////////////////////////////////////
        if(carteraEstadoReporteEnum.equals(CarteraEstadoReporteEnum.VENCIDA))
        {
            whereTipoCarteraVencida=" AND c.fechaFinCredito < CURRENT_DATE ";
        }else if(carteraEstadoReporteEnum.equals(CarteraEstadoReporteEnum.SIN_VENCER))
        {
            whereTipoCarteraVencida=" AND c.fechaFinCredito >= CURRENT_DATE ";
        }

        String whereDocumentos=obtenerDocumentosDesdeCategoriaDocumento(categoriaMenuEnum,"c.codigoDocumento");
        
        if(documentoEnum!=null)
        {
            whereDocumento=" AND c.codigoDocumento=?7 ";
        }
        
        //Verificar si requiere realizar un filtro por la SEGUNDA REFERENCIA de la CARTERA
        //TODO: Mejorar por que tambien depende del TIPO DE LA SEGUNDA REFERENCIA por que puede obtener resultos equivocados, por el momento funciona por que solo exist eun tipo de ABONO CARTERA
        String whereSegundaReferencia="";
        if(segundaReferenciaId!=null)
        {
            whereSegundaReferencia=" AND c.segundaReferenciaId=?8 ";
        }
        
        
        String orderBy="";
        if(tipoOrdenamientoEnum.equals(tipoOrdenamientoEnum.POR_PREIMPRESO))
        {
            orderBy=" ORDER BY CAST(c.puntoEmision AS BIGINT) ,CAST(c.puntoEstablecimiento AS BIGINT) ,CAST(c.secuencial AS BIGINT)  asc ";
        }else if(tipoOrdenamientoEnum.equals(tipoOrdenamientoEnum.POR_RAZON_SOCIAL))
        {
            orderBy=" ORDER BY c.persona.razonSocial ";
        }else if(tipoOrdenamientoEnum.equals(tipoOrdenamientoEnum.POR_FECHA))
        {
            orderBy=" ORDER BY c.fechaEmision desc ";
        }

        
        //Cartera c; c.getSucursal();
        /*c.getPuntoEmision();
        c.getPuntoEstablecimiento();
        c.getSecuencial();
        c.getPersona().getRazonSocial();*/
        
        try {
            String queryString = "SELECT c FROM Cartera c WHERE " + cliente + fecha + saldo +whereTipoCarteraVencida+whereDocumento+whereSegundaReferencia+ " AND ("+whereDocumentos+") AND c.sucursal =?6 AND c.tipoCartera=?4 AND c.estado=?5  "+orderBy;            
            //System.out.println("QUERY==> "+queryString);
            Query query = getEntityManager().createQuery(queryString);
            if (persona != null) {
                query.setParameter(1, persona);
            }
            if (fi != null) {
                query.setParameter(2, fi);
            }
            if (ff != null) {
                query.setParameter(3, ff);
            }
            
            if(tipoCartera!=null)
            {
                query.setParameter(4,tipoCartera.getLetra());
            }
            
            query.setParameter(5,GeneralEnumEstado.ACTIVO.getEstado());
            query.setParameter(6,sucursal);
            
            if(documentoEnum!=null)
            {
                query.setParameter(7, documentoEnum.getCodigo());
            }
            
            if(segundaReferenciaId!=null)
            {
                query.setParameter(8,segundaReferenciaId);
            }
            
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    private String obtenerDocumentosDesdeCategoriaDocumento(DocumentoCategoriaEnum documentoCategoria,String alias)
    {
        List<DocumentoEnum> documentos=DocumentoEnum.obtenerPorCategoria(documentoCategoria);
        String whereDocumento=" ";
        Boolean primeraIteracion=true;
        for (DocumentoEnum documento: documentos) {
            if(primeraIteracion)
            {                
                primeraIteracion=false;
            }else
            {
                whereDocumento+=" OR ";
            }
            whereDocumento+=alias+"='"+documento.getCodigo()+"' " ;
        }
        return whereDocumento;
        //DocumentoCategoriaEnum documentoCategoria
    }
    
    public List<CarteraCruce> getMovimientoCartera(Persona persona)
    {
        //CarteraCruce cc;
        //c.getCarteraAfectada().getPersona();
        
        String queryString = "SELECT u FROM CarteraCruce u WHERE u.carteraAfectada.persona=?1 ";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, persona);
        return query.getResultList();
        
    }
    
    /**
     * Metodo que obtiene los valores cruzados para un documento de la cartera que le afecta cruces como por ejemplo facturas
     * @param cartera
     * @return 
     */
    public BigDecimal obtenerValorCruceCarteraAfecta(Cartera cartera)
    {
        CarteraCruce carteraCruce;

        String queryString = "SELECT SUM(u.valor) FROM CarteraCruce u WHERE u.carteraAfectada=?1 ";
        Query query = getEntityManager().createQuery(queryString);
        query.setFlushMode(FlushModeType.AUTO);
        query.setParameter(1, cartera);
        Number sumatoria=(Number) query.getSingleResult();
        return new BigDecimal(sumatoria.toString());

    }
    
    
    /**
     * Metodo que obtiene los valores cruzados desde una cartera detalle
     * @param carteraDetalle
     * @return 
     */
    public BigDecimal obtenerValorCruceCarteraDetalle(CarteraDetalle  carteraDetalle)
    {
        //carteraDetalle.get
        //carteraCruce.get        
        String queryString = "SELECT SUM(u.valor) FROM CarteraCruce u WHERE u.carteraDetalle=?1 ";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, carteraDetalle);
        Number sumatoria=(Number) query.getSingleResult();
        return new BigDecimal(sumatoria.toString());

    }
    
     /**
     * Metodo que obtiene los valores cruzados para un documento de la cartera que afecta cruces como por ejemplo abonos
     * @param cartera
     * @return 
     */
    public BigDecimal obtenerValorCruceCarteraAfectados(Cartera cartera)
    {
        String queryString2 = "SELECT distinct u FROM CarteraCruce u WHERE u.carteraDetalle.cartera=?1 ";
        Query query2 = getEntityManager().createQuery(queryString2);
        query2.setParameter(1, cartera);
        List<CarteraCruce> carteras=query2.getResultList();
        for (CarteraCruce carteraResult : carteras) {
            System.out.println("cod: "+carteraResult.getId()+" ,valor= "+carteraResult.getValor());
        }
        
        String queryString = "SELECT distinct SUM(u.valor) FROM CarteraCruce u WHERE u.carteraDetalle.cartera=?1 ";
        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, cartera);
        Number sumatoria=(Number) query.getSingleResult();
        if(sumatoria==null)
        {
            return BigDecimal.ZERO;
        }
        else
        {
            return new BigDecimal(sumatoria.toString());
        }
    }
    
    public List<Cartera> obtenerCarteraPorCobrarFacade(Persona cliente,Empresa empresa)
    {
        /*Cartera c;
        c.getSaldo();*/
        String whereDocumentos=obtenerDocumentosDesdeCategoriaDocumento(DocumentoCategoriaEnum.COMPROBANTE_INGRESOS_EGRESOS,"c.codigoDocumento");
        String queryString = "SELECT c FROM Cartera c WHERE c.persona=?1 and c.estado=?2 and c.sucursal.empresa=?3 and c.saldo>0 and c.tipoCartera=?4 and ( "+whereDocumentos+" )";
        Query query=getEntityManager().createQuery(queryString);
        query.setParameter(1,cliente);
        query.setParameter(2,GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(3,empresa);
        query.setParameter(4,Cartera.TipoCarteraEnum.CLIENTE.getLetra());
        return query.getResultList();
    }
    
    //TODO: Unir con el codigo superior
    public List<Cartera> obtenerCarteraPorCobrarEstudianteFacade(Estudiante estudiante,Empresa empresa)
    {
        /*Cartera c;
        c.getSaldo();*/
        String whereDocumentos=obtenerDocumentosDesdeCategoriaDocumento(DocumentoCategoriaEnum.COMPROBANTE_INGRESOS_EGRESOS,"c.codigoDocumento");
        String queryString = "SELECT c FROM Cartera c WHERE c.segundaReferenciaId=?1 and c.estado=?2 and c.sucursal.empresa=?3 and c.saldo>0 and c.tipoCartera=?4 and ( "+whereDocumentos+" )";
        Query query=getEntityManager().createQuery(queryString);
        query.setParameter(1,estudiante.getIdEstudiante());
        query.setParameter(2,GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(3,empresa);
        query.setParameter(4,Cartera.TipoCarteraEnum.CLIENTE.getLetra());
        return query.getResultList();
    }
    
    public BigDecimal obtenerSaldoDisponibleCruzarFacade(Persona cliente,Empresa empresa)
    {
        /*Cartera cartera;
        cartera.getCarteraDocumentoEnum().getCategoria().COMPROBANTE_INGRESOS_EGRESOS;
        cartera.getCodigoDocumento()
        cartera.getTipoCarteraEnum().CLIENTE;
        cartera.getTipoCartera()*/
        String whereDocumentos=obtenerDocumentosDesdeCategoriaDocumento(DocumentoCategoriaEnum.COMPROBANTE_INGRESOS_EGRESOS,"c.codigoDocumento");
        String queryString = "SELECT SUM(c.saldo) FROM Cartera c WHERE c.persona=?1 and c.estado=?2 and c.sucursal.empresa=?3 and c.saldo>0 and c.tipoCartera=?4 and ( "+whereDocumentos+" )";
        
        Query query=getEntityManager().createQuery(queryString);
        query.setParameter(1,cliente);
        query.setParameter(2,GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(3,empresa);
        query.setParameter(4,Cartera.TipoCarteraEnum.CLIENTE.getLetra());
        return (BigDecimal) query.getSingleResult();
    }
    
    //TODO: Ver si se puede unir el metodo con el de arriba
    public BigDecimal obtenerSaldoDisponibleCruzarEstudianteFacade(Estudiante estudiante,Empresa empresa)
    {
        //Cartera cartera;
        //cartera.getSegundaReferenciaId();
        String whereDocumentos=obtenerDocumentosDesdeCategoriaDocumento(DocumentoCategoriaEnum.COMPROBANTE_INGRESOS_EGRESOS,"c.codigoDocumento");
        String queryString = "SELECT SUM(c.saldo) FROM Cartera c WHERE c.segundaReferenciaId=?1 and c.estado=?2 and c.sucursal.empresa=?3 and c.saldo>0 and c.tipoCartera=?4 and ( "+whereDocumentos+" )";
        Query query=getEntityManager().createQuery(queryString);
        query.setParameter(1,estudiante.getIdEstudiante());
        query.setParameter(2,GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(3,empresa);
        query.setParameter(4,Cartera.TipoCarteraEnum.CLIENTE.getLetra());
        return (BigDecimal) query.getSingleResult();
        
    }
    
}
