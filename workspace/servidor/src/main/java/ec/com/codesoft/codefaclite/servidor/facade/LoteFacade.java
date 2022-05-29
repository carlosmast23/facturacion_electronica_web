/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidor.facade;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Producto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.FechaCaducidadResult;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author DellWin10
 */
public class LoteFacade extends AbstractFacade<Lote>{

    public LoteFacade() {
        super(Lote.class);
    }
   
    public Long verificarExistenLotes(Empresa empresa)
    {
        //Lote lote=n;
        String queryStr=" SELECT count(l.codigo) FROM Lote l WHERE l.estado=?1 and l.empresa=?2 ";
        Query query=getEntityManager().createQuery(queryStr);
        query.setParameter(1, GeneralEnumEstado.ACTIVO.getEstado());
        query.setParameter(2, empresa);
        return (Long) query.getSingleResult();
    }
    
    public List<FechaCaducidadResult> reporteFechaCaducidadFacade(Sucursal sucursal,Bodega bodega,Date fechaReferencia)
    {
        Producto producto;
        String whereBodega="";
        if(bodega!=null)
        {
           whereBodega=" AND B.BODEGA_ID = ?2 ";
        }
        //producto.getEstado();
        //producto.getManejarInventario();
        //producto.setEmpresa(empresa);
                
        String queryString="SELECT P.CODIGO_PERSONALIZADO,B.NOMBRE AS BODEGA,L.CODIGO,P.NOMBRE,L.FECHA_VENCIMIENTO,K.STOCK,P.VALOR_UNITARIO FROM KARDEX K INNER JOIN LOTE L ON K.LOTE_ID = L.ID INNER JOIN PRODUCTO P ON K.PRODUCTO_ID =P.ID_PRODUCTO INNER JOIN BODEGA B ON B.BODEGA_ID =K.BODEGA_ID WHERE P.ESTADO='A' AND B.ESTADO='A' AND K.ESTADO ='A' AND B.SUCURSAL_ID=?1 AND L.FECHA_VENCIMIENTO<?3 AND K.STOCK>0 "+whereBodega;
        Query query = getEntityManager().createNativeQuery(queryString);
        query.setParameter(1,sucursal.getId());
        query.setParameter(3,fechaReferencia);
        
        if(bodega!=null)
        {
            query.setParameter(2, bodega.getIdBodega());
        }
        
        List<Object[]> resultadoOriginalList=query.getResultList();
        List<FechaCaducidadResult> resultadoList=new ArrayList<FechaCaducidadResult>();
        
        for (Object[] objects : resultadoOriginalList) {
            FechaCaducidadResult datoTmp=new FechaCaducidadResult();
            datoTmp.constructor(objects);
            resultadoList.add(datoTmp);
        }
        
        return resultadoList;
        
    }
    
}
