/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.servidorinterfaz.controller;

import ec.com.codesoft.codefaclite.servidorinterfaz.entity.HerramientasCodefacServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AccesoDirectoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ActualizarSistemaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AlertaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AtsServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.AulaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.BodegaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CatalogoProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CategoriaProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraFacturaReembolsoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.CompraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteFisicoDisenioServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.DepartamentoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EmpleadoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EmpresaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteInscritoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.EstudianteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturaDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.FacturacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexItemEspecificoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.KardexServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.LoteSeviceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.MarcaProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.MesaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NacionalidadServiceIf;
//import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.MatriculaEstudianteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelAcademicoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NivelServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.NotaCreditoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenCompraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenTrabajoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.OrdenTrabajoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PerfilUsuarioServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PeriodoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PermisoVentanaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoEnsambleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoProveedorServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubroEstudianteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RubrosNivelServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriIdentificacionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UsuarioServicioIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.UtilidadesServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraCruceServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.CarteraServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.gestionacademica.RubroPlantillaEstudianteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.gestionacademica.RubroPlantillaServiceIf;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PerfilServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaEstablecimientoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresupuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PresupuestoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RetencionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SmsServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriFormaPagoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionIvaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionRentaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SriRetencionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SucursalServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.DestinatarioGuiaRemisionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.DetalleProductoGuiaRemisionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.GuiaRemisionAdicionalServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.GuiaRemisionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.transporte.TransportistaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PuntoEmisionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PuntoEmisionUsuarioServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RutaDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RutaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.SegmentoProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.TipoDocumentoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.TipoEstablecimientoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.TipoProductoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ZonaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.PrestamoCuotaCargoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.PrestamoCuotaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.PrestamoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.cartera.PrestamoTablaInteresServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.gestionacademica.DescuentoAcademicoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.ArqueoCajaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.CajaPermisoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.CajaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.CajaSesionServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.IngresoCajaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.TurnoAsignadoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.TurnoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.pos.VentaServiceIf;

/**
 *
 * @author Carlos
 */
public abstract class ServiceFactory {
    
    /**
     * Ip del servidor del cual se va a consumir los servicios
     */
    public String ipServidor;
    public static ServiceFactory serviceController;
    
    /**
     * Obtiene la instancia del controlador actual
     * @return 
     */
    public static ServiceFactory getFactory()
    {
        return serviceController;
    }
    
        /**
     * Metodos que devuelven los servicios     *      * 
     */
    public ProductoServiceIf getProductoServiceIf(){return (ProductoServiceIf) getRecursosRMI(ProductoServiceIf.class);};
    public PersonaServiceIf getPersonaServiceIf(){return (PersonaServiceIf) getRecursosRMI(PersonaServiceIf.class);};
    public ParametroCodefacServiceIf getParametroCodefacServiceIf(){return (ParametroCodefacServiceIf) getRecursosRMI(ParametroCodefacServiceIf.class);};
    public UtilidadesServiceIf getUtilidadesServiceIf(){return (UtilidadesServiceIf) getRecursosRMI(UtilidadesServiceIf.class);};
    public AccesoDirectoServiceIf getAccesoDirectoServiceIf(){return (AccesoDirectoServiceIf) getRecursosRMI(AccesoDirectoServiceIf.class);};
    public BodegaServiceIf getBodegaServiceIf(){return (BodegaServiceIf) getRecursosRMI(BodegaServiceIf.class);};
    public CategoriaProductoServiceIf getCategoriaProductoServiceIf(){return (CategoriaProductoServiceIf) getRecursosRMI(CategoriaProductoServiceIf.class);};
    public CompraDetalleServiceIf getCompraDetalleServiceIf(){return (CompraDetalleServiceIf) getRecursosRMI(CompraDetalleServiceIf.class);};
    public CompraServiceIf getCompraServiceIf(){return (CompraServiceIf) getRecursosRMI(CompraServiceIf.class);};
    public ComprobanteFisicoDisenioServiceIf getComprobanteFisicoDisenioServiceIf(){return (ComprobanteFisicoDisenioServiceIf) getRecursosRMI(ComprobanteFisicoDisenioServiceIf.class);};
    public EmpresaServiceIf getEmpresaServiceIf(){return (EmpresaServiceIf) getRecursosRMI(EmpresaServiceIf.class);};
    public FacturacionServiceIf getFacturacionServiceIf(){return (FacturacionServiceIf) getRecursosRMI(FacturacionServiceIf.class);};
    public ImpuestoDetalleServiceIf getImpuestoDetalleServiceIf(){return (ImpuestoDetalleServiceIf) getRecursosRMI(ImpuestoDetalleServiceIf.class);};
    public ImpuestoServiceIf getImpuestoServiceIf(){return (ImpuestoServiceIf) getRecursosRMI(ImpuestoServiceIf.class);};
    public KardexDetalleServiceIf getKardexDetalleServiceIf(){return (KardexDetalleServiceIf) getRecursosRMI(KardexDetalleServiceIf.class);};
    public KardexItemEspecificoServiceIf getItemEspecificoServiceIf(){return (KardexItemEspecificoServiceIf) getRecursosRMI(KardexItemEspecificoServiceIf.class);};
    public KardexServiceIf getKardexServiceIf(){return (KardexServiceIf) getRecursosRMI(KardexServiceIf.class);};
    public NotaCreditoServiceIf getNotaCreditoServiceIf(){return (NotaCreditoServiceIf) getRecursosRMI(NotaCreditoServiceIf.class);};
    public PerfilServiceIf getPerfilServicioIf(){return (PerfilServiceIf) getRecursosRMI(PerfilServiceIf.class);};
    public ProductoEnsambleServiceIf getProductoEnsambleServiceIf(){return (ProductoEnsambleServiceIf) getRecursosRMI(ProductoEnsambleServiceIf.class);};
    public ProductoProveedorServiceIf getProductoProveedorServiceIf(){return (ProductoProveedorServiceIf) getRecursosRMI(ProductoProveedorServiceIf.class);};
    public SriIdentificacionServiceIf getSriIdentificacionServiceIf(){return (SriIdentificacionServiceIf) getRecursosRMI(SriIdentificacionServiceIf.class);};
    public SriServiceIf getSriServiceIf(){return (SriServiceIf) getRecursosRMI(SriServiceIf.class);};
    public UsuarioServicioIf getUsuarioServicioIf(){return (UsuarioServicioIf) getRecursosRMI(UsuarioServicioIf.class);};
    public ComprobanteServiceIf getComprobanteServiceIf(){return (ComprobanteServiceIf) getRecursosRMI(ComprobanteServiceIf.class);};
    public RecursosServiceIf getRecursosServiceIf(){return (RecursosServiceIf) getRecursosRMI(RecursosServiceIf.class);};
    public AulaServiceIf getAulaServiceIf(){return (AulaServiceIf) getRecursosRMI(AulaServiceIf.class);};
    public EstudianteServiceIf getEstudianteServiceIf(){return (EstudianteServiceIf) getRecursosRMI(EstudianteServiceIf.class);};
    //public MatriculaEstudianteServiceIf getMatriculaEstudianteServiceIf(){return (MatriculaEstudianteServiceIf) getRecursosRMI(MatriculaEstudianteServiceIf.class);};
    public NivelAcademicoServiceIf getNivelAcademicoServiceIf(){return (NivelAcademicoServiceIf) getRecursosRMI(NivelAcademicoServiceIf.class);};
    public NivelServiceIf getNivelServiceIf(){return (NivelServiceIf) getRecursosRMI(NivelServiceIf.class);};
    public PeriodoServiceIf getPeriodoServiceIf(){return (PeriodoServiceIf) getRecursosRMI(PeriodoServiceIf.class);};
    public RubrosNivelServiceIf getRubrosNivelServiceIf(){return (RubrosNivelServiceIf) getRecursosRMI(RubrosNivelServiceIf.class);};
    public PermisoVentanaServiceIf getPermisoVentanaServiceIf(){return (PermisoVentanaServiceIf) getRecursosRMI(PermisoVentanaServiceIf.class);};
    public EstudianteInscritoServiceIf getEstudianteInscritoServiceIf(){return (EstudianteInscritoServiceIf) getRecursosRMI(EstudianteInscritoServiceIf.class);};
    public RubroEstudianteServiceIf getRubroEstudianteServiceIf(){return (RubroEstudianteServiceIf) getRecursosRMI(RubroEstudianteServiceIf.class);};
    public NacionalidadServiceIf getNacionalidadServiceIf(){return (NacionalidadServiceIf) getRecursosRMI(NacionalidadServiceIf.class);};
    public CatalogoProductoServiceIf getCatalogoProductoServiceIf(){return (CatalogoProductoServiceIf) getRecursosRMI(CatalogoProductoServiceIf.class);};
    public CarteraServiceIf getCarteraServiceIf(){return (CarteraServiceIf) getRecursosRMI(CarteraServiceIf.class);};
    public CarteraDetalleServiceIf getCarteraDetalleServiceIf(){return (CarteraDetalleServiceIf) getRecursosRMI(CarteraDetalleServiceIf.class);};
    public CarteraCruceServiceIf getCarteraCruceServiceIf(){return (CarteraCruceServiceIf) getRecursosRMI(CarteraCruceServiceIf.class);};
    public RubroPlantillaServiceIf getRubroPlantillaServiceIf(){return (RubroPlantillaServiceIf) getRecursosRMI(RubroPlantillaServiceIf.class);};
    public RubroPlantillaEstudianteServiceIf getRubroPlantillaEstudianteServiceIf(){return (RubroPlantillaEstudianteServiceIf) getRecursosRMI(RubroPlantillaEstudianteServiceIf.class);};
    public PerfilUsuarioServiceIf getPerfilUsuarioServiceIf(){return (PerfilUsuarioServiceIf) getRecursosRMI(PerfilUsuarioServiceIf.class);};
    public SriFormaPagoServiceIf getSriFormaPagoServiceIf(){return (SriFormaPagoServiceIf) getRecursosRMI(SriFormaPagoServiceIf.class);};
    public SriRetencionIvaServiceIf getSriRetencionIvaServiceIf(){return (SriRetencionIvaServiceIf) getRecursosRMI(SriRetencionIvaServiceIf.class);};
    public SriRetencionRentaServiceIf getSriRetencionRentaServiceIf(){return (SriRetencionRentaServiceIf) getRecursosRMI(SriRetencionRentaServiceIf.class);};
    public RetencionServiceIf getRetencionServiceIf(){return (RetencionServiceIf) getRecursosRMI(RetencionServiceIf.class);};
    public SriRetencionServiceIf getSriRetencionServiceIf(){return (SriRetencionServiceIf) getRecursosRMI(SriRetencionServiceIf.class);};
    public OrdenCompraServiceIf getOrdenCompraServiceIf(){return (OrdenCompraServiceIf) getRecursosRMI(OrdenCompraServiceIf.class);};
    public OrdenTrabajoServiceIf getOrdenTrabajoServiceIf(){return (OrdenTrabajoServiceIf) getRecursosRMI(OrdenTrabajoServiceIf.class);};
    public OrdenTrabajoDetalleServiceIf getOrdenDetalleTrabajoServiceIf(){return (OrdenTrabajoDetalleServiceIf) getRecursosRMI(OrdenTrabajoDetalleServiceIf.class);};
    public EmpleadoServiceIf getEmpleadoServiceIf(){return (EmpleadoServiceIf) getRecursosRMI(EmpleadoServiceIf.class);};
    public DepartamentoServiceIf getDepartamentoServiceIf(){return (DepartamentoServiceIf) getRecursosRMI(DepartamentoServiceIf.class);};
    public PresupuestoServiceIf getPresupuestoServiceIf(){return (PresupuestoServiceIf) getRecursosRMI(PresupuestoServiceIf.class);};
    public PresupuestoDetalleServiceIf getPresupuestoDetalleServiceIf(){return (PresupuestoDetalleServiceIf) getRecursosRMI(PresupuestoDetalleServiceIf.class);};
    public TransportistaServiceIf getTransportistaServiceIf(){return (TransportistaServiceIf) getRecursosRMI(TransportistaServiceIf.class);};    
    public DestinatarioGuiaRemisionServiceIf getDestinatarioGuiaRemisionServiceIf(){return (DestinatarioGuiaRemisionServiceIf) getRecursosRMI(DestinatarioGuiaRemisionServiceIf.class);};
    public DetalleProductoGuiaRemisionServiceIf getDetalleProductoGuiaRemisionServiceIf(){return (DetalleProductoGuiaRemisionServiceIf) getRecursosRMI(DetalleProductoGuiaRemisionServiceIf.class);};
    public GuiaRemisionServiceIf getGuiaRemisionServiceIf(){return (GuiaRemisionServiceIf) getRecursosRMI(GuiaRemisionServiceIf.class);};
    public GuiaRemisionAdicionalServiceIf getGuiaRemisionAdicionalServiceIf(){return (GuiaRemisionAdicionalServiceIf) getRecursosRMI(GuiaRemisionAdicionalServiceIf.class);};
    public SmsServiceIf getSmsServiceIf(){return (SmsServiceIf) getRecursosRMI(SmsServiceIf.class);};
    public AtsServiceIf getAtsServiceIf(){return (AtsServiceIf) getRecursosRMI(AtsServiceIf.class);};
    public SucursalServiceIf getSucursalServiceIf(){return (SucursalServiceIf) getRecursosRMI(SucursalServiceIf.class);};
    public PuntoEmisionServiceIf getPuntoVentaServiceIf(){return (PuntoEmisionServiceIf) getRecursosRMI(PuntoEmisionServiceIf.class);};
    public ActualizarSistemaServiceIf getActualizarSistemaServiceIf(){return (ActualizarSistemaServiceIf) getRecursosRMI(ActualizarSistemaServiceIf.class);};
    public PersonaEstablecimientoServiceIf getPersonaEstablecimientoServiceIf(){return (PersonaEstablecimientoServiceIf) getRecursosRMI(PersonaEstablecimientoServiceIf.class);};
    public FacturaDetalleServiceIf getFacturaDetalleServiceIf(){return (FacturaDetalleServiceIf) getRecursosRMI(FacturaDetalleServiceIf.class);};
    public TipoDocumentoServiceIf getTipoDocumentoServiceIf(){return (TipoDocumentoServiceIf) getRecursosRMI(TipoDocumentoServiceIf.class);};
    public PuntoEmisionUsuarioServiceIf getPuntoEmisionUsuarioServiceIf(){return (PuntoEmisionUsuarioServiceIf) getRecursosRMI(PuntoEmisionUsuarioServiceIf.class);};
    public PrestamoTablaInteresServiceIf getPrestamoTablaInteresServiceIf(){return (PrestamoTablaInteresServiceIf) getRecursosRMI(PrestamoTablaInteresServiceIf.class);};
    
    public PrestamoCuotaCargoServiceIf getPrestamoCuotaCargoServiceIf(){return (PrestamoCuotaCargoServiceIf) getRecursosRMI(PrestamoCuotaCargoServiceIf.class);};
    public PrestamoCuotaServiceIf getPrestamoCuotaServiceIf(){return (PrestamoCuotaServiceIf) getRecursosRMI(PrestamoCuotaServiceIf.class);};
    public PrestamoServiceIf getPrestamoServiceIf(){return (PrestamoServiceIf) getRecursosRMI(PrestamoServiceIf.class);};
    public DescuentoAcademicoServiceIf getDescuentoAcademicoServiceIf(){return (DescuentoAcademicoServiceIf) getRecursosRMI(DescuentoAcademicoServiceIf.class);};
    public AlertaServiceIf getAlertaServiceIf(){return (AlertaServiceIf) getRecursosRMI(AlertaServiceIf.class);};
    
    public CajaServiceIf getCajaServiceIf(){return (CajaServiceIf) getRecursosRMI(CajaServiceIf.class);};
    public CajaSesionServiceIf getCajaSesionServiceIf(){return (CajaSesionServiceIf) getRecursosRMI(CajaSesionServiceIf.class);};
    public ArqueoCajaServiceIf getArqueoCajaServiceIf(){return (ArqueoCajaServiceIf) getRecursosRMI(ArqueoCajaServiceIf.class);};
    public CajaPermisoServiceIf getCajaPermisoServiceIf(){return (CajaPermisoServiceIf) getRecursosRMI(CajaPermisoServiceIf.class);};
    public IngresoCajaServiceIf getIngresoCajaServiceIf(){return (IngresoCajaServiceIf) getRecursosRMI(IngresoCajaServiceIf.class);};
    public TurnoServiceIf getTurnoServiceIf(){return (TurnoServiceIf) getRecursosRMI(TurnoServiceIf.class);};
    public TurnoAsignadoServiceIf getTurnoAsignadoServiceIf(){return (TurnoAsignadoServiceIf) getRecursosRMI(TurnoAsignadoServiceIf.class);};
    public VentaServiceIf getVentaServiceIf(){return (VentaServiceIf) getRecursosRMI(VentaServiceIf.class);};
    
    public ZonaServiceIf getZonaServiceIf(){return (ZonaServiceIf) getRecursosRMI(ZonaServiceIf.class);};
    public TipoEstablecimientoServiceIf getTipoEstablecimientoServiceIf(){return (TipoEstablecimientoServiceIf) getRecursosRMI(TipoEstablecimientoServiceIf.class);};
    public RutaServiceIf getRutaServiceIf(){return (RutaServiceIf) getRecursosRMI(RutaServiceIf.class);};
    public RutaDetalleServiceIf getRutaDetalleServiceIf(){return (RutaDetalleServiceIf) getRecursosRMI(RutaDetalleServiceIf.class);};
    public HerramientasCodefacServiceIf getHerramientasCodefacServiceIf(){return (HerramientasCodefacServiceIf) getRecursosRMI(HerramientasCodefacServiceIf.class);};
    
    public MarcaProductoServiceIf getMarcaProductoServiceIf(){return (MarcaProductoServiceIf) getRecursosRMI(MarcaProductoServiceIf.class);};
    
    public CompraFacturaReembolsoServiceIf getCompraFacturaReembolsoServiceIf(){return (CompraFacturaReembolsoServiceIf) getRecursosRMI(CompraFacturaReembolsoServiceIf.class);};
    public LoteSeviceIf getLoteSeviceIf(){return (LoteSeviceIf) getRecursosRMI(LoteSeviceIf.class);};
    public MesaServiceIf getMesaSeviceIf(){return (MesaServiceIf) getRecursosRMI(MesaServiceIf.class);};
    public SegmentoProductoServiceIf getSegmentoProductoServiceIf(){return (SegmentoProductoServiceIf) getRecursosRMI(SegmentoProductoServiceIf.class);};
    public TipoProductoServiceIf getTipoProductoServiceIf(){return (TipoProductoServiceIf) getRecursosRMI(TipoProductoServiceIf.class);};
    
    
    /**
     * Crea una nueva instancia el controlados para manejar por el cliente
     * @param ipServidor 
     */
    public static void newController(String ipServidor)
    {
        serviceController=new ServiceFactory(ipServidor) {};        
    }
    
    
    private Map<Class,Remote> mapRecursosRMI;

    private ServiceFactory(String ipServidor) 
    {
        this.ipServidor=ipServidor;
        this.mapRecursosRMI=new HashMap<Class,Remote>();
    }
    
    
    /**
     * Metodo que permite crear u obtener el servicio atravez de RMI
     * @param clase
     * @return 
     */
    public Remote getRecursosRMI(Class clase)
    {
        Remote remote= mapRecursosRMI.get(clase);
        
        if(remote==null)
        {
            try {
                Registry registro= LocateRegistry.getRegistry(ipServidor,ParametrosSistemaCodefac.PUERTO_COMUNICACION_RED);    
                //for (String object : registro.list()) {
                //    System.out.println("Algo==>"+object);
                //}
                
                //Naming.lookup(ipServidor);
                //String ipServidorInterno="192.168.100.13";
                //remote= registro.lookup("rmi://"+ipServidor+":"+ParametrosSistemaCodefac.PUERTO_COMUNICACION_RED+"/"+clase.getSimpleName());
                System.out.println(clase.getName());
                remote= registro.lookup(clase.getName());
                //remote= registro.lookup("ec.com.codesoft.codefaclite.servidor.service.PersonaService");
                mapRecursosRMI.put(clase,remote);
                
                
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(null,"Error de conexión con el servidor\n\nPosibles Causas: \n - Verifique que el equipo tiene conexión a internet. \n - Verifique que la IP DEL SERVIDOR está ingresada correctamente. \n - Verifique que el sistema esta abierto en el servidor. \n - Otros problemas de red. ","Error",JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(ServiceFactory.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(0);
            } catch (NotBoundException ex) {
                Logger.getLogger(ServiceFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return remote;
    }
    
    /**
     * Permite saber si existe una conexion creada anteriormente
     * @return 
     */
    public static boolean isActiveController()
    {
        return serviceController !=null;
    }
    
}
