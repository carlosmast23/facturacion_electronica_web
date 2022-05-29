/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.com.codesoft.codefaclite.servidor.service;

import ec.com.codesoft.codefaclite.servidor.facade.LoteFacade;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Bodega;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Lote;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CrudEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.FechaCaducidadData;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ReportDataAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.ReporteFechaCaducidadReport;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.FechaCaducidadResult;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.LoteSeviceIf;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DellWin10
 */
public class LoteService extends ServiceAbstract<Lote, LoteFacade> implements LoteSeviceIf{

    public LoteService() throws RemoteException {
        super(LoteFacade.class);
    }
    
    private void validarGrabar(Lote lote,CrudEnum crudEnum) throws ServicioCodefacException
    {           
        if(lote.getEmpresa()==null)
        {
            throw new ServicioCodefacException("No se puede grabar sin una Empresa");
        }
        
        if(UtilidadesTextos.verificarNullOVacio(lote.getCodigo()))
        {
            throw new ServicioCodefacException("Debe ingresar un código de lote para grabar");
        }
        
    }

    @Override
    public Lote grabar(Lote entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                entity.setEstadoEnum(GeneralEnumEstado.ACTIVO);
                
                setDatosAuditoria(entity,usuarioCreacion,CrudEnum.CREAR);
                setearDatosGrabar(entity, empresa,CrudEnum.CREAR);
                validarGrabar(entity, CrudEnum.CREAR);
                entityManager.persist(entity);
                
            }
        });
        return entity;
    }
    
    private void setearDatosGrabar(Lote entity,Empresa empresa,CrudEnum crudEnum)
    {
        entity.setEmpresa(empresa);
        /*if(crudEnum.equals(CrudEnum.CREAR))
        {
            entity.setStock(BigDecimal.ZERO);
            entity.setTotal(BigDecimal.ZERO);
        }*/
    }
    
    @Override
    public Lote editar(Lote entity,Empresa empresa,Usuario usuarioCreacion) throws ServicioCodefacException, RemoteException {
        
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {                                
                setDatosAuditoria(entity,usuarioCreacion,CrudEnum.EDITAR);
                setearDatosGrabar(entity, empresa,CrudEnum.EDITAR);
                editarSinTransaccion(entity);
            }
        });
        return entity;
    }
    
    public void editarSinTransaccion(Lote entity) throws ServicioCodefacException, RemoteException 
    {
        validarGrabar(entity, CrudEnum.EDITAR);
        entityManager.merge(entity);
    }
    
    public boolean existenLotesIngresados(Empresa empresa) throws ServicioCodefacException, RemoteException 
    {
        if(getFacade().verificarExistenLotes(empresa)>0)
        {
            return true;
        }
        return false;
    }

    @Override
    public void eliminar(Lote entity) throws ServicioCodefacException, RemoteException {
        ejecutarTransaccion(new MetodoInterfaceTransaccion() {
            @Override
            public void transaccion() throws ServicioCodefacException, RemoteException {
                //TODO: Agregar validacion para solo eliminar los lotes si no tiene ningun saldo disponible
                entity.setEstadoEnum(GeneralEnumEstado.ELIMINADO);
                entityManager.merge(entity);
            }
        });
    }
 
    
    public ReportDataAbstract reporteFechaCaducidad(Sucursal sucursal,Bodega bodega,Date fechaReferencia) throws ServicioCodefacException, RemoteException 
    {
        List<FechaCaducidadResult> resultDataList=getFacade().reporteFechaCaducidadFacade(sucursal, bodega,fechaReferencia);
        
        List<FechaCaducidadData> reporteDataList=new ArrayList<FechaCaducidadData>();
        
        for (FechaCaducidadResult dato : resultDataList) 
        {
            // TODO: Ver si esta parte convertir debe estar en la clase para no hacer tanto codigo que no correcponde
            DateFormat dateFormat= ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA;
            FechaCaducidadData fechaCaducidadData=new FechaCaducidadData(
                    dato.getCodigoPersonalizado(), 
                    dato.getNombreBodega(), 
                    dato.getCodigoLote(), 
                    dato.getNombreProducto(), 
                    dateFormat.format(dato.getFechaCaducidad()), 
                    dato.getStock().setScale(2, RoundingMode.HALF_UP).toString(), 
                    dato.getValorUnitario().setScale(2, RoundingMode.HALF_UP).toString());
           reporteDataList.add(fechaCaducidadData);
        }
        
        ReporteFechaCaducidadReport reporte=new ReporteFechaCaducidadReport("Productos por Caducar");
        reporte.setDetalleList(reporteDataList);    
        return reporte;
    }
    
    
}
