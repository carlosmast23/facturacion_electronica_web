/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.crm;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.codefacweb.mb.utilidades.UtilidadesReporteWeb;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.enumerador.OrientacionReporteEnum;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.crm.data.ClienteData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.FormatoHojaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author CARLOS_CODESOFT
 */
@ManagedBean
@ViewScoped
public class ClienteReporteMb extends GeneralAbstractMb  {
    
    private String ejemplo="hola mundo";

    @Override
    public void nuevo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String titulo() throws ExcepcionCodefacLite, UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void imprimirReporte()
    {
        System.err.println("imprimirReporte()"); 
        ejemplo="imprimir algo";
        mostrarDialogoReporte(new ReporteDialogListener() {
            public void excel() {
                System.out.println("imprimiendo excel");
            }

            public void pdf() {
                InputStream path = RecursoCodefac.JASPER_CRM.getResourceInputStream("reporteClientes.jrxml");
                Map parameters = new HashMap();
                List<ClienteData> data = new ArrayList<ClienteData>();
                List<Persona> clientes = obtenerConsulta(); //Todo: Obtener filtrar solo por clientes

                for (Persona cliente : clientes) {
                    PersonaEstablecimiento establecimiento = (cliente.getEstablecimientos() != null && cliente.getEstablecimientos().size() > 0) ? cliente.getEstablecimientos().get(0) : null;
                    ClienteData clienteData = new ClienteData();
                    clienteData.setDireccion((establecimiento != null) ? establecimiento.getDireccion() : "");
                    clienteData.setEmail(cliente.getCorreoElectronico());
                    clienteData.setIdentificacion(cliente.getIdentificacion());
                    clienteData.setNombresCompletos(cliente.getRazonSocial());
                    clienteData.setNombreLegal((establecimiento != null) ? establecimiento.getNombreComercial() : "");
                    clienteData.setTelefono((establecimiento != null) ? establecimiento.getTelefonoCelular() : "");
                    data.add(clienteData);
                }

                JasperPrint jasperPrint = ReporteCodefac.construirReporte(path, parameters, data, sessionMb.getSession(), "Clientes", OrientacionReporteEnum.VERTICAL, FormatoHojaEnum.A4);
                UtilidadesReporteWeb.generarReporteHojaNuevaPdf(jasperPrint, "Clientes");
            }
        });
        
        
        
    }
    
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        
        //ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, getNombreReporte());
    }
    
    public List<Persona> obtenerConsulta()
    {
        try {
            /*Persona persona;
            persona.getTipo()
            persona.getTipoEnum().*/
            PersonaServiceIf service=ServiceFactory.getFactory().getPersonaServiceIf();
            return service.buscarPorTipo(OperadorNegocioEnum.CLIENTE,GeneralEnumEstado.ACTIVO,sessionMb.getSession().getEmpresa()); //Todo: Obtener filtrar solo por clientes
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteReporteMb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getEjemplo() {
        return ejemplo;
    }

    public void setEjemplo(String ejemplo) {
        this.ejemplo = ejemplo;
    }
    
    
}
