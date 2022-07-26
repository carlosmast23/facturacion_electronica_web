/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidor.service.KardexDetalleService;
import ec.com.codesoft.codefaclite.servidor.service.KardexService;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Kardex;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoConsultaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.UtilidadResult;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import es.mityc.firmaJava.libreria.utilidades.Utilidades;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author
 */
public class FacturaFacade extends AbstractFacade<Factura> {

    //aca consuultas con la BBDD
//compara objetos no strings
    public FacturaFacade() {
        super(Factura.class);
    }

    public Query listaQuery(PersonaEstablecimiento persona, Date fi, Date ff, ComprobanteEntity.ComprobanteEnumEstado estadoEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa,DocumentoEnum documentoEnum,Sucursal sucursal, Usuario usuario,Empleado vendedor,EnumSiNo enviadoGuiaRemision,TipoConsultaEnum tipoConsultaEnum,Boolean quitarVentasAnuladasNCTotal) {
        String cliente = "", fecha = "", estadoFactura = "",filtrarReferidos="",ordenarAgrupado="",filtrarSucursal="", usuarioId="",enviadoGuiaRemisionStr="",vendedorStr="",afectaNotaCreditoTotalStr="";
        
        if(vendedor!=null)
        {
            vendedorStr=" AND u.vendedor=?16 ";
        }
        
        if(enviadoGuiaRemision!=null)
        {
            enviadoGuiaRemisionStr=" AND u.estadoEnviadoGuiaRemision= ?15 ";
        }
        
        if(usuario != null && usuario.getFiltrarFacturaEnum().equals(EnumSiNo.SI)){
            usuarioId = " AND u.usuario = ?14 ";
        } else {
            usuarioId = " AND 1=1 ";
        }
        
        if (persona != null) {
            cliente = " u.sucursal=?1 ";
        } else {
            cliente = " 1=1 ";
        }
        
        if (fi == null && ff != null) {
            fecha = " AND u.fechaEmision <= ?3";
        } else if (fi != null && ff == null) {
            fecha = " AND u.fechaEmision >= ?2";
        } else if (fi == null && ff == null) {
            fecha = "";
        } else {
            //fecha = " AND (u.fechaEmision BETWEEN ?2 AND ?3)";
            fecha = " AND (u.fechaEmision >= ?2 AND u.fechaEmision<= ?3)";
        }
        
        if (estadoEnum!= null) {
            //Si la peticion es por todos sri entonces tengo que setear 2 valores
            if(ComprobanteEntity.ComprobanteEnumEstado.TODOS_SRI.equals(estadoEnum))
            {
                estadoFactura = " AND ( u.estado=?10 or u.estado=?11 ) ";
            }
            else
            {                
                estadoFactura = " AND u.estado=?4";
            }
        }
        
        if(agrupadoReferido)
        {
            ordenarAgrupado=" u.referido ,";
        }
        
        if(consultarReferidos)
        {
            filtrarReferidos=" AND u.referido IS NOT NULL ";
            if(referido!=null)
            {            
                filtrarReferidos+=" AND u.referido=?5 ";
            }
        }
        
        if(sucursal!=null)
        {
            filtrarSucursal+=" AND u.sucursalEmpresa=?13 ";
        }
        
        if(quitarVentasAnuladasNCTotal)
        {            
            afectaNotaCreditoTotalStr=" AND ( u.estadoNotaCredito IS NULL OR u.estadoNotaCredito<>?17 ) ";
        }
        
        String filtroPuntoEmision="";
        if(puntoEmision!=null)
        {
            filtroPuntoEmision=" AND u.puntoEmision =?12 ";
        }
        
        String selectStr="SELECT u ";
        if(tipoConsultaEnum.equals(TipoConsultaEnum.TAMANIO))
        {
            selectStr="SELECT COUNT(1) ";
        }
        
        String orderByStr=" ";
        if(tipoConsultaEnum.equals(TipoConsultaEnum.DATOS))
        {
            orderByStr= " ORDER BY" + ordenarAgrupado + " u.secuencial+0 asc";
        }

        String queryString = selectStr+"FROM Factura u WHERE u.empresa=?7 and u.codigoDocumento=?6 and  " + cliente + usuarioId + fecha + estadoFactura + filtrarReferidos + filtroPuntoEmision + filtrarSucursal + enviadoGuiaRemisionStr + vendedorStr+afectaNotaCreditoTotalStr+orderByStr;
                
        
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
        if (estadoEnum != null) {
            if (ComprobanteEntity.ComprobanteEnumEstado.TODOS_SRI.equals(estadoEnum)) {
                query.setParameter(10, ComprobanteEntity.ComprobanteEnumEstado.AUTORIZADO.getEstado());
                query.setParameter(11, ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO_SRI.getEstado());
            } else {
                query.setParameter(4, estadoEnum.getEstado());
            }
        }

        if (consultarReferidos) {
            if (referido != null) {
                query.setParameter(5, referido);
            }
        }

        query.setParameter(6, documentoEnum.getCodigo());
        query.setParameter(7, empresa);

        if (puntoEmision != null) {
            query.setParameter(12, puntoEmision.getPuntoEmision());
        }

        if (sucursal != null) {
            query.setParameter(13, sucursal);
        }

        if (usuario != null && usuario.getFiltrarFacturaEnum().equals(EnumSiNo.SI)) {
            query.setParameter(14, usuario);
        }

        if (enviadoGuiaRemision != null) {
            query.setParameter(15, enviadoGuiaRemision.getLetra());
        }

        if (vendedor != null) {
            query.setParameter(16, vendedor);
        }
        
        if(quitarVentasAnuladasNCTotal)
        {            
            query.setParameter(17,Factura.EstadoNotaCreditoEnum.ANULADO_TOTAL.getEstado());
        }

        return query;

    }
    
    public List<Factura> lista(PersonaEstablecimiento persona, Date fi, Date ff, ComprobanteEntity.ComprobanteEnumEstado estadoEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa,DocumentoEnum documentoEnum,Sucursal sucursal, Usuario usuario,Empleado vendedor,EnumSiNo enviadoGuiaRemision,Boolean quitarVentasAnuladasNCTotal) {
        try {
            Query query=listaQuery(persona, fi, ff, estadoEnum, consultarReferidos, referido, agrupadoReferido, puntoEmision, empresa, documentoEnum, sucursal, usuario, vendedor, enviadoGuiaRemision, TipoConsultaEnum.DATOS,quitarVentasAnuladasNCTotal);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public Long listaConTamanio(PersonaEstablecimiento persona, Date fi, Date ff, ComprobanteEntity.ComprobanteEnumEstado estadoEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa,DocumentoEnum documentoEnum,Sucursal sucursal, Usuario usuario,Empleado vendedor,EnumSiNo enviadoGuiaRemision) 
    {
        Query query=listaQuery(persona, fi, ff, estadoEnum, consultarReferidos, referido, agrupadoReferido, puntoEmision, empresa, documentoEnum, sucursal, usuario, vendedor, enviadoGuiaRemision, TipoConsultaEnum.TAMANIO,false);
        return (Long) query.getSingleResult();
    }
   

    public List<Factura> getFacturaEnable() {
        try {
            
            String queryString = "SELECT u FROM Factura u WHERE u.estado<>?1 AND u.estadoNotaCredito<>?2 AND u.estado<>?3";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1, ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado());
            query.setParameter(2, Factura.EstadoNotaCreditoEnum.ANULADO_PARCIAL.getEstado());
            query.setParameter(3, ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR.getEstado());
            return (List<Factura>) query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Factura> queryDialog(String param, int limiteMinimo, int limiteMaximo) {
        String queryString = "SELECT u FROM Factura u WHERE u.estado<>?1 AND u.estado<>?2 AND u.estado<>?3 ";
        queryString += "AND ( f.cliente.razonSocial like ?1 )";

        Query query = getEntityManager().createQuery(queryString);
        query.setParameter(1, param);
        query.setMaxResults(limiteMaximo);
        query.setFirstResult(limiteMinimo);
        return query.getResultList();

    }
    
    public Boolean verificarFacturaActivaIngresadaConPedido(Factura proforma)
    {
        //Factura f;
        //f.getProforma().
        String queryString = "SELECT count(u) FROM Factura u WHERE u.estado<>?1 AND u.estado<>?2 AND u.proforma=?3  ";
            Query query = getEntityManager().createQuery(queryString);
            query.setParameter(1, ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO.getEstado());
            query.setParameter(2, ComprobanteEntity.ComprobanteEnumEstado.ELIMINADO_SRI.getEstado());
            query.setParameter(3, proforma);
            Long numeroFacturas=(Long) query.getSingleResult();
            if(numeroFacturas>0)
            {
                return true;
            }
            return false;
    }
    
      public Long getSecuencialProforma(Empresa empresa) {
        try {
            //Factura f;
            //f.getEmpresa();
            String queryString="SELECT MAX(CAST (F.SECUENCIAL AS BIGINT)) FROM FACTURA F WHERE F.EMPRESA_ID=?2 AND F.CODIGO_DOCUMENTO=?1"; //TODO: Por el momento dejo una consulta nativa porque tengo un problema al evaluar el secuencial que en la base de datos esta como string pero esta mapeado como entero y al hacer casting en jpql el compilador se confunde
            //String queryString = "SELECT max( CAST(u.secuencial CHAR(64))  ) FROM Factura u WHERE  u.codigoDocumento=?1";
            Query query = getEntityManager().createNativeQuery(queryString);
            query.setParameter(1, DocumentoEnum.PROFORMA.getCodigo());
            query.setParameter(2,empresa.getId());
            System.out.println("QueryStringNative:"+queryString);
            
            Object resultado=query.getSingleResult();
            if(resultado==null)
            {
                //Si no existe ningun valor devuelve el primero
                return 1l;
            }
            
            return (Long) resultado;
        } catch (NoResultException e) {
            return null;
        }
    }
      
      public List<Factura> consultarProformasReporteFacade(Persona cliente,Date fechaInicial,Date fechaFinal,Empresa empresa,ComprobanteEntity.ComprobanteEnumEstado estado) 
      {
          Factura factura;
          //factura.getCodigoDocumento()
          //factura.getEstado();
          //factura.getFechaEmision();
          //factura.getCliente()
          String queryString="SELECT F FROM Factura F WHERE F.empresa=?6 and F.codigoDocumento=?1 ";
          if(fechaInicial!=null)
          {
              queryString+=" AND F.fechaEmision>= ?2";
          }
          
          if(fechaFinal!=null)
          {
              queryString+=" AND F.fechaEmision<= ?3";
          }
          
          if(cliente!=null)
          {
              queryString+=" AND F.cliente= ?4";
          }
          
          if(estado!=null)
          {
              queryString+=" AND F.estado=?5 ";
          }
                    
          //String queryString="SELECT F FROM FACTURA F WHERE F.CODIGO_DOCUMENTO=?1 AND F.cliente=?2";
          Query query=getEntityManager().createQuery(queryString);
          query.setParameter(1,DocumentoEnum.PROFORMA.getCodigo());
          
          if(fechaInicial!=null)
          {
              query.setParameter(2,fechaInicial);
          }
          
          if (fechaFinal != null) {
              query.setParameter(3,fechaFinal);
          }
          
          if (cliente != null) {
              query.setParameter(4,cliente);
          }
          
          if (estado != null) {
              query.setParameter(5, estado.getEstado());
          }
          
          query.setParameter(6,empresa);
          return query.getResultList();
          
      }
      
      
      public Factura buscarPorPremimpresoYEstadoFacade(Integer secuencial,BigDecimal puntoEstablecimiento,Integer puntoEmision,ComprobanteEntity.ComprobanteEnumEstado estadoEnum)
      {
          /*Factura f;
          f.getSecuencial();
          f.getPuntoEmision();
          f.getPuntoEstablecimiento();
          f.getEstadoEnum();*/
          
          String queryString="SELECT F FROM Factura F WHERE F.secuencial=?1 AND F.puntoEmision=?2 AND F.puntoEstablecimiento=?3 AND F.estado=?4 ";
          
          Query query=getEntityManager().createQuery(queryString);
          
          query.setParameter(1,secuencial);
          query.setParameter(2,puntoEmision);
          query.setParameter(3,puntoEstablecimiento);
          query.setParameter(4,estadoEnum.getEstado());
          try
          {
              return (Factura) query.getSingleResult();
          }
          catch(NoResultException nre)
          {
              return null;
          }
      }
      
      public List<UtilidadResult> consultaUtilidadFacade(Date fechaMenor, Date fechaMayor)
      {
          String whereFechaMenor="";
          
          String whereFechaMayor="";
          
          if(fechaMayor!=null)
          {
              whereFechaMayor= " AND F.FECHA_EMISION <= ?1";
          }
          
          if(fechaMenor!=null)
          {
              whereFechaMenor= " AND F.FECHA_EMISION >= ?2";
          }
          
          String queryString="SELECT F.SECUENCIAL,F.FECHA_EMISION ,F.RAZON_SOCIAL,F.IDENTIFICACION,F.ID,FD.SUBTOTAL,FD.COSTO,FD.UTILIDAD FROM FACTURA F INNER JOIN " +
                        "(" +
                        "	SELECT FD.FACTURA_ID ,SUM(FD.TOTAL) AS SUBTOTAL ,SUM(FD.COSTO_PROMEDIO*FD.CANTIDAD) AS COSTO , SUM(FD.TOTAL-FD.COSTO_PROMEDIO*FD.CANTIDAD) AS UTILIDAD FROM FACTURA_DETALLE FD  GROUP BY FD.FACTURA_ID " +
                        ") FD ON F.ID =FD.FACTURA_ID WHERE 1=1  "+whereFechaMayor+whereFechaMenor ;
      
          Query query=getEntityManager().createNativeQuery(queryString);
          
          if(fechaMayor!=null)
          {
              query.setParameter(1,fechaMayor);
          }
          
          if(fechaMenor!=null)
          {
              query.setParameter(2,fechaMenor);
          }
          
          
          List<Object[]> resultado=query.getResultList();
          List<UtilidadResult> resultadoList=new ArrayList<UtilidadResult>();
          for (Object[] objects : resultado) 
          {
              UtilidadResult resultadoObj=new UtilidadResult();
              resultadoObj.constructor(objects);
              resultadoList.add(resultadoObj);
          }
          return resultadoList;
      }
    
}
