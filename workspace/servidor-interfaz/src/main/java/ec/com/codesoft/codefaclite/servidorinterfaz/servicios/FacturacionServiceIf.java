/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.servicios;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empleado;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Prestamo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.CarteraParametro;
import ec.com.codesoft.codefaclite.servidorinterfaz.parameros.FacturaParametro;
import ec.com.codesoft.codefaclite.servidorinterfaz.reportData.UtilidadReport;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.FacturaLoteRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.respuesta.ReferenciaDetalleFacturaRespuesta;
import ec.com.codesoft.codefaclite.servidorinterfaz.result.UtilidadResult;
import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public interface FacturacionServiceIf extends ServiceAbstractIf<Factura>
{
    public void editar(Factura factura) throws ServicioCodefacException, RemoteException;
    public Factura grabar(Factura factura) throws ServicioCodefacException,java.rmi.RemoteException,ServicioCodefacException; 
    public Factura grabar(Factura factura,Prestamo prestamo,CarteraParametro carteraParametro) throws RemoteException, ServicioCodefacException;
    public Factura grabar(Factura factura,Empleado empleado) throws ServicioCodefacException,java.rmi.RemoteException,ServicioCodefacException; 
    public List<Factura> obtenerFacturasPorIdentificacion(String identificacion,Empresa empresa) throws java.rmi.RemoteException;
    public List<Factura> consultaDialogo(String param,int limiteMinimo,int limiteMaximo) throws java.rmi.RemoteException;
    //public void editar(Factura factura) throws java.rmi.RemoteException;
    public void editarFactura(Factura factura) throws ServicioCodefacException,java.rmi.RemoteException,ServicioCodefacException; 
    public List<Factura> obtenerTodos()throws java.rmi.RemoteException;
    //public List<Factura> obtenerFacturasReporte(Persona persona,Date fi,Date ff,ComprobanteEntity.ComprobanteEnumEstado estadEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa) throws java.rmi.RemoteException;
    //public List<Factura> obtenerFacturasReporte(PersonaEstablecimiento persona,Date fi,Date ff,ComprobanteEntity.ComprobanteEnumEstado estadEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa,DocumentoEnum documentoEnum,Sucursal sucursal) throws java.rmi.RemoteException;
    public List<Factura> obtenerFacturasReporte(PersonaEstablecimiento persona,Date fi,Date ff,ComprobanteEntity.ComprobanteEnumEstado estadEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa,DocumentoEnum documentoEnum,Sucursal sucursal, Usuario usuario) throws java.rmi.RemoteException;
    public List<Factura> obtenerFacturasReporte(PersonaEstablecimiento persona,Date fi,Date ff,ComprobanteEntity.ComprobanteEnumEstado estadEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa,DocumentoEnum documentoEnum,Sucursal sucursal, Usuario usuario,Empleado vendedor,EnumSiNo enviadoGuiaRemision,Boolean quitarVentasAnuladasNCTotal) throws java.rmi.RemoteException;
    public List<Factura> obtenerFacturasActivas() throws java.rmi.RemoteException;
    //public String getPreimpresoSiguiente() throws java.rmi.RemoteException;
    public void eliminarFactura(Factura factura) throws java.rmi.RemoteException,ServicioCodefacException;
    public Long obtenerSecuencialProformas(Empresa empresa) throws RemoteException;
    public Factura grabarProforma(Factura proforma) throws RemoteException,ServicioCodefacException;
    public void eliminarProforma(Factura factura) throws java.rmi.RemoteException,ServicioCodefacException;
    public List<Factura> consultarProformasReporte(Persona cliente,Date fechaInicial,Date fechaFinal,Empresa empresa,ComprobanteEntity.ComprobanteEnumEstado estado) throws java.rmi.RemoteException,ServicioCodefacException;
    public Factura buscarPorPremimpresoYEstado(Integer secuencial,BigDecimal puntoEstablecimiento,Integer puntoEmision,ComprobanteEntity.ComprobanteEnumEstado estadoEnum) throws RemoteException;    
    public void grabarCartera(Factura factura) throws RemoteException, ServicioCodefacException;
    
    public Factura grabarLiquidacionCompra(Factura liquidacionCompra) throws RemoteException,ServicioCodefacException;
    public ReferenciaDetalleFacturaRespuesta obtenerReferenciaDetalleFactura(TipoDocumentoEnum tipoDocumentoEnum,Long referenciaId) throws java.rmi.RemoteException,ServicioCodefacException;
    public Map<Factura,BigDecimal> obtenerCostoFacturas(List<Factura> facturas) throws RemoteException, ServicioCodefacException;
    public Factura editarProforma(Factura proforma) throws RemoteException,ServicioCodefacException;
    public Long obtenerFacturasReporteTamanio(PersonaEstablecimiento persona,Date fi,Date ff,ComprobanteEntity.ComprobanteEnumEstado estadEnum,Boolean consultarReferidos,Persona referido,Boolean agrupadoReferido,PuntoEmision puntoEmision,Empresa empresa,DocumentoEnum documentoEnum,Sucursal sucursal, Usuario usuario,Empleado vendedor,EnumSiNo enviadoGuiaRemision) throws java.rmi.RemoteException ;
    
    public FacturaLoteRespuesta grabarLote(List<FacturaParametro> facturaList) throws RemoteException,ServicioCodefacException;
    
    public Factura obtenerPedidoVentaDiariaActivo(Sucursal sucursal) throws RemoteException,ServicioCodefacException;
    
    public void enviarCorreoProforma(Factura proforma) throws RemoteException,ServicioCodefacException;
    
    public UtilidadReport consultaUtilidadVentas(Date fechaMenor, Date fechaMayor) throws RemoteException,ServicioCodefacException  ;
    
}
