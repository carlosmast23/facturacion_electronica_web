/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.actualizacion;

import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.NotaCredito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmisionUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Retencion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.DestinatarioGuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.transporte.GuiaRemision;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ActualizacionSistemaUtil {

    private static final Logger LOG = Logger.getLogger(ActualizacionSistemaUtil.class.getName());
    
    
    
    public static void ejecutarNuevoMetodoEstatico()
    {
        System.out.println("Metodo ejecutado estatico");
    }
    
    private static void actualizarComprobante(ComprobanteEntity comprobante)
    {
        if(comprobante.getFechaAutorizacionSri()==null)
        {
            
        }
    }
    
    @Deprecated //TODO:No se esta usando
    public static void actualizaComprobantesElectronicos()
    {
        List<ComprobanteEntity> listaComprobantesActualizar=new ArrayList<ComprobanteEntity>();
        
        try {
            List<Factura> facturas=ServiceFactory.getFactory().getFacturacionServiceIf().obtenerTodos();
            listaComprobantesActualizar.addAll(facturas);
            
            List<NotaCredito> notasCredito=ServiceFactory.getFactory().getNotaCreditoServiceIf().obtenerTodos();
            listaComprobantesActualizar.addAll(notasCredito);
            
            List<Retencion> retenciones = ServiceFactory.getFactory().getRetencionServiceIf().obtenerTodos();
            listaComprobantesActualizar.addAll(retenciones);

            List<GuiaRemision> guiasRemision = ServiceFactory.getFactory().getGuiaRemisionServiceIf().obtenerTodos();
            listaComprobantesActualizar.addAll(guiasRemision);
            
            
            ServiceFactory.getFactory().getComprobanteServiceIf().actualizarComprobanteDatos(listaComprobantesActualizar);
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(ActualizacionSistemaUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ActualizacionSistemaUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo que permite crear por lo menos una sucursal para cada empresa que esta creada
     */
    public static void actualizarSucursalesEmpresas()
    {
        //Crear una sucursal principal de todas los clientes disponibles
        try {
            List<Persona> personas=ServiceFactory.getFactory().getPersonaServiceIf().obtenerTodos();
            for (Persona persona : personas) {
                
                if(persona.getEstablecimientos()==null || persona.getEstablecimientos().size()==0)
                {                
                    PersonaEstablecimiento establecimiento=new PersonaEstablecimiento();
                    establecimiento.setCodigoSucursal("1");
                    establecimiento.setCorreoElectronico(persona.getCorreoElectronico());
                    //establecimiento.setDireccion(persona.getDireccion());
                    establecimiento.setExtensionTelefono(persona.getExtensionTelefono());
                    establecimiento.setNombreComercial(persona.getNombreLegal());
                    establecimiento.setPersona(persona);
                    establecimiento.setTelefonoCelular(persona.getTelefonoCelular());
                    establecimiento.setTelefonoConvencional(persona.getTelefonoConvencional());
                    establecimiento.setTipoSucursalEnum(Sucursal.TipoSucursalEnum.MATRIZ);                
                    persona.addEstablecimiento(establecimiento);

                    try {                   
                        ServiceFactory.getFactory().getPersonaServiceIf().editarPersona(persona);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(ActualizacionSistemaUtil.class.getName()).log(Level.SEVERE, null, ex);
                        LOG.log(Level.WARNING,persona.getRazonSocial()+" => "+ex.getMessage());
                    }
                }
            }
            
            
            //Actualizar la sucursal de todas las facturas
            List<Factura> facturas= ServiceFactory.getFactory().getFacturacionServiceIf().obtenerTodos();
            for (Factura factura : facturas) {
                if(factura.getCliente()!=null && factura.getCliente().getEstablecimientos()!=null && factura.getCliente().getEstablecimientos().size()>0)
                {
                    factura.setSucursal(factura.getCliente().getEstablecimientos().get(0));
                    try {
                        ServiceFactory.getFactory().getFacturacionServiceIf().editarFactura(factura);
                    } catch (ServicioCodefacException ex) {
                        Logger.getLogger(ActualizacionSistemaUtil.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }               
                
            }
        
            
            List<GuiaRemision> guiaRemisionResultados=ServiceFactory.getFactory().getGuiaRemisionServiceIf().obtenerTodos();
            for (GuiaRemision guiaRemision : guiaRemisionResultados) {
                for (DestinatarioGuiaRemision destinatarioGuiaRemision : guiaRemision.getDestinatarios()) {
                    
                    if(destinatarioGuiaRemision.getDestinatorio()!=null && destinatarioGuiaRemision.getDestinatorio().getEstablecimientos()!=null && destinatarioGuiaRemision.getDestinatorio().getEstablecimientos().size()>0)
                    {
                        destinatarioGuiaRemision.setSucursal(destinatarioGuiaRemision.getDestinatorio().getEstablecimientos().get(0));
                        try {
                            ServiceFactory.getFactory().getGuiaRemisionServiceIf().editarGuiaRemision(guiaRemision);
                        } catch (ServicioCodefacException ex) {
                            Logger.getLogger(ActualizacionSistemaUtil.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(ActualizacionSistemaUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo que me permite establecer la relacion para actualizar de todos los usuarios con los puntos de emision
     */
    public static void actualizarPuntoEmisionUsuario()
    {
        try {
            List<Usuario> usuarios=ServiceFactory.getFactory().getUsuarioServicioIf().obtenerTodos();
            List<PuntoEmision> puntosEmision=ServiceFactory.getFactory().getPuntoVentaServiceIf().obtenerTodos();
            for (Usuario usuario : usuarios) {                
                for (PuntoEmision puntoEmision : puntosEmision) {
                    PuntoEmisionUsuario puntoUsuario=new PuntoEmisionUsuario();
                    puntoUsuario.setUsuario(usuario);
                    puntoUsuario.setPuntoEmision(puntoEmision);
                    ServiceFactory.getFactory().getPuntoEmisionUsuarioServiceIf().grabar(puntoUsuario);
                }
            }
           
        } catch (RemoteException ex) {
            Logger.getLogger(ActualizacionSistemaUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ActualizacionSistemaUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void actualizarMicroempresaARimpe()
    {
        try {
            List<Empresa> empresaList=ServiceFactory.getFactory().getEmpresaServiceIf().obtenerTodos();
            for (Empresa empresa : empresaList) {
                if(empresa.getContribuyenteRegimenMicroempresasEnum()!=null && empresa.getContribuyenteRegimenMicroempresasEnum().equals(EnumSiNo.SI))                
                {
                    empresa.setContribuyenteRegimenMicroempresasEnum(EnumSiNo.NO);
                    empresa.setRimpeEmprendedoresEnum(EnumSiNo.SI);
                }
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(ActualizacionSistemaUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
