/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.crm.model;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.Excel;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelDatosInterface;
import ec.com.codesoft.codefaclite.controlador.model.ReporteDialogListener;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.crm.data.ClienteData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PersonaEstablecimiento;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OperadorNegocioEnum;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos
 */
public class ClienteReporte extends ControladorCodefacInterface{

    public ClienteReporte() {        
        
    }
    
    private void imprimirReporte() throws IOException, FileNotFoundException, IllegalArgumentException, IllegalAccessException
    {
        try {
            InputStream path = RecursoCodefac.JASPER_CRM.getResourceInputStream("reporteClientes.jrxml");
            Map parameters = new HashMap();
            List<ClienteData> data = new ArrayList<ClienteData>();
            //PersonaServiceIf service=ServiceFactory.getFactory().getPersonaServiceIf();
            //List<Persona> clientes=service.obtenerTodos(); //Todo: Obtener filtrar solo por clientes
            List<Persona> clientes=obtenerConsulta(); //Todo: Obtener filtrar solo por clientes
            
            for (Persona cliente : clientes) {
                PersonaEstablecimiento establecimiento=(cliente.getEstablecimientos()!=null && cliente.getEstablecimientos().size()>0)?cliente.getEstablecimientos().get(0):null;
                ClienteData clienteData=new ClienteData();
                clienteData.setDireccion((establecimiento!=null)?establecimiento.getDireccion():"");
                clienteData.setEmail(cliente.getCorreoElectronico());
                clienteData.setIdentificacion(cliente.getIdentificacion());
                clienteData.setNombresCompletos(cliente.getRazonSocial());
                clienteData.setNombreLegal((establecimiento!=null)?establecimiento.getNombreComercial():"");
                clienteData.setTelefono((establecimiento!=null)?establecimiento.getTelefonoCelular():"");
                data.add(clienteData);
            }
            
            Collections.sort(data, new Comparator<ClienteData>(){
                public int compare(ClienteData obj1, ClienteData obj2)
                {
                    return obj1.getNombresCompletos().compareTo(obj2.getNombresCompletos());
                }
            });
            
            DialogoCodefac.dialogoReporteOpciones( new ReporteDialogListener() {
                @Override
                public void excel() {
                    try{
                        Excel excel = new Excel();
                        String nombreCabeceras[] = {"Identificación", "Nombres completos","Nombre Legal", "Telefono", "Dirección","Email"};
                        excel.gestionarIngresoInformacionExcel(nombreCabeceras, data);
                        excel.abrirDocumento();
                    }
                    catch(Exception exc)
                    {
                        exc.printStackTrace();
                        DialogoCodefac.mensaje("Error","El archivo Excel se encuentra abierto",DialogoCodefac.MENSAJE_INCORRECTO);
                    }  
                }

                @Override
                public void pdf() {
                    ReporteCodefac.generarReporteInternalFramePlantilla(path, parameters, data, panelPadre, getNombreReporte());
                    dispose();
                    setVisible(false);
                }
            });
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(ClienteReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Persona> obtenerConsulta() throws RemoteException
    {
        /*Persona persona;
        persona.getTipo()
        persona.getTipoEnum().*/
        PersonaServiceIf service=ServiceFactory.getFactory().getPersonaServiceIf();
        return service.buscarPorTipo(OperadorNegocioEnum.CLIENTE,GeneralEnumEstado.ACTIVO,session.getEmpresa()); //Todo: Obtener filtrar solo por clientes
    }

    public String getNombreReporte()
    {
        return "Reporte Clientes";
    }
    
    
    @Override
    public void grabar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
    public String getNombre() {
        return "Cliente Reporte";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite{
        try {
            imprimirReporte();
        } catch (IOException ex) {
            Logger.getLogger(ClienteReporte.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ClienteReporte.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ClienteReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new ExcepcionCodefacLite("Cerrar Ventan");
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
