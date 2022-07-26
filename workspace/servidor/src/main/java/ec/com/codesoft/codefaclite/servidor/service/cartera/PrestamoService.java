/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidor.service.cartera;

import ec.com.codesoft.codefaclite.servidor.facade.cartera.PrestamoFacade;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceConsulta;
import ec.com.codesoft.codefaclite.servidor.service.MetodoInterfaceTransaccion;
import ec.com.codesoft.codefaclite.servidor.service.ServiceAbstract;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Presupuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Cartera;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.Prestamo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.cartera.PrestamoCuota;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.PrestamoServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author
 */
public class PrestamoService extends ServiceAbstract<Prestamo,PrestamoFacade> implements PrestamoServiceIf  {

    public PrestamoService() throws RemoteException {
        super(PrestamoFacade.class);
    }
    
    public List<PrestamoCuota> buscarCuotasPorPrestamo(Prestamo prestamo) throws RemoteException ,ServicioCodefacException
    {
        return (List<PrestamoCuota>) ejecutarConsulta(new MetodoInterfaceConsulta() {
            @Override
            public Object consulta() throws ServicioCodefacException, RemoteException {
                return getFacade().buscarCuotasPorPrestamo(prestamo);
            }
        });
    }
    
    
    public void grabarSinTransaccion(Prestamo prestamo,Factura factura) throws RemoteException, ServicioCodefacException
    {
        /**
         * ===========================================
         *      CONSULTAR LA CARTERA CREADA
         * ===========================================
         */
        CarteraService carteraService=new CarteraService();
        Cartera carteraGenerada=carteraService.buscarCarteraPorReferencia(
                factura.getId(), 
                factura.getCodigoDocumentoEnum(), 
                GeneralEnumEstado.ACTIVO, 
                Cartera.TipoCarteraEnum.CLIENTE, 
                factura.getSucursalEmpresa());
        
        prestamo.setVenta(factura);
        prestamo.setCliente(factura.getCliente());
        prestamo.setTotalPrestamo(factura.getTotal());
        prestamo.calcularCapital();
        prestamo.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        entityManager.persist(prestamo);
        entityManager.flush();
     
        /**
         * ===============================================
         *      GENERAR LA CUOTA INICIAL
         * ===============================================
         */
        crearCuotaInicial(prestamo);
        
        crearCuotasPrestamo(prestamo);
        
        
        
    }
    
    private void crearCuotaInicial(Prestamo prestamo)
    {
        PrestamoCuota prestamoCuota=new PrestamoCuota();
        prestamoCuota.setTipoEnum(PrestamoCuota.TipoCuotaEnum.CUOTA_INICIAL);
        prestamoCuota.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        prestamoCuota.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        prestamoCuota.setFechaPago(UtilidadesFecha.getFechaHoy());
        prestamoCuota.setFechaPagoGenerado(UtilidadesFecha.getFechaHoy());
        prestamoCuota.setNumeroCuota(0);
        prestamoCuota.setPrestamo(prestamo);
        prestamoCuota.setSaldoCuota(BigDecimal.ZERO);
        prestamoCuota.setValorCuota(prestamo.getCuotaInicial());
        prestamoCuota.setValorPago(prestamo.getCuotaInicial());
        entityManager.persist(prestamoCuota);
    }
    
    /**
     * TODO: Ver alguna forma de unir con el metodo de crear cuota inicial
     * @param prestamo
     * @param tipoCuotaEnum
     * @param fechaPagoGenerado
     * @param numeroCuota
     * @param valorCuota 
     */
    private void crearCuotaPrestamo(Prestamo prestamo,PrestamoCuota.TipoCuotaEnum tipoCuotaEnum,Date fechaPagoGenerado,Integer numeroCuota,BigDecimal valorCuota)
    {
        PrestamoCuota prestamoCuota=new PrestamoCuota();
        prestamoCuota.setTipoEnum(tipoCuotaEnum);
        prestamoCuota.setEstadoEnum(GeneralEnumEstado.ACTIVO);
        prestamoCuota.setFechaCreacion(UtilidadesFecha.getFechaHoy());
        //prestamoCuota.setFechaPago(UtilidadesFecha.getFechaHoy());
        prestamoCuota.setFechaPagoGenerado(fechaPagoGenerado);
        prestamoCuota.setNumeroCuota(numeroCuota);
        prestamoCuota.setPrestamo(prestamo);
        prestamoCuota.setSaldoCuota(valorCuota);
        prestamoCuota.setValorCuota(valorCuota);
        prestamoCuota.setValorPago(BigDecimal.ZERO); //Todo: este campo no tiene sentido porque con saldo y creando un valor en cartera se puede manejar
        entityManager.persist(prestamoCuota);
    }
    
    private void crearCuotasPrestamo(Prestamo prestamo)
    {
        Integer numeroCuotas=prestamo.getPlazo();
        BigDecimal valorCuotaMensual=prestamo.calcularCuotaMensual();
        Integer diaPago=prestamo.getDiaPago();
        
        for (int i = 1; i <= numeroCuotas; i++) {
            Date fechaPago=generarFechaPagoCuota(diaPago, i, prestamo.getFechaCreacion());
            crearCuotaPrestamo(prestamo, PrestamoCuota.TipoCuotaEnum.CUOTA_MENSUAL, fechaPago, i, valorCuotaMensual);
        }
        
    }
    
    private Date generarFechaPagoCuota(Integer diaPago,Integer numeroCuota,Date fechaPrestamo)
    {
        java.util.Date fechaPago=UtilidadesFecha.sumarMesesFecha(fechaPrestamo,numeroCuota);
        return UtilidadesFecha.cambiarDiaFecha(new java.sql.Date(fechaPago.getTime()),diaPago);        
    }
    
    
}
