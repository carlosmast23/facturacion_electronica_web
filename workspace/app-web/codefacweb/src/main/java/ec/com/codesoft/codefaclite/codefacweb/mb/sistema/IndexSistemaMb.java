/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.codefacweb.mb.sistema;

import ec.com.codesoft.codefaclite.codefacweb.core.GeneralAbstractMb;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.common.AlertaResponse;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;

/**
 *
 * @author
 */
@ManagedBean
@ViewScoped
public class IndexSistemaMb  extends GeneralAbstractMb implements Serializable {
    
    private static final String CLIENTE_ETIQUETA="Cliente";
    //private static final String PROFORMA_ETIQUETA="Proforma";
    private static final String FACTURA_ETIQUETA="Factura"; 
    
    private Boolean detenerActualizarAlerta;
    
    private MindmapNode root;

    private MindmapNode selectedNode;
    
    private List<AlertaResponse> alertasSistemas;
    
    @PostConstruct
    private void init()
    {
        detenerActualizarAlerta=false;
        //cargarAlertas();  
    }
    
    public void cargarAlertas()
    {
        System.out.println("Cargando alertas del sistema");
        try {
             System.out.println("procesando ...");
            alertasSistemas=ServiceFactory.getFactory().getAlertaServiceIf().actualizarNotificacionesCargaRapida(sessionMb.getSession().getEmpresa());
            System.out.println("fin procesando ...");
            detenerActualizarAlerta=true;
        } catch (RemoteException ex) {
            Logger.getLogger(IndexSistemaMb.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(IndexSistemaMb.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Fin Cargar Alertas");
    }

    public IndexSistemaMb() {
        root = new DefaultMindmapNode(FACTURA_ETIQUETA, "Google WebSite", "FFCC00", true);

        MindmapNode ips = new DefaultMindmapNode(CLIENTE_ETIQUETA, "IP Numbers", "FFFFFF", true);
        //MindmapNode ns = new DefaultMindmapNode(PROFORMA_ETIQUETA, "Namespaces", "FFFFFF", true);
        //MindmapNode malware = new DefaultMindmapNode("Academico", "Malicious Software", "6e9ebf", true);

        //ns.addNode(ips);
        root.addNode(ips);
        
        //root.addNode(ns);
        //root.addNode(malware);
    }

    public MindmapNode getRoot() {
        return root;
    }

    public MindmapNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(MindmapNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public void onNodeSelect(SelectEvent event) {
        MindmapNode node = (MindmapNode) event.getObject();
        //if(node.getLabel().equals(PROFORMA_ETIQUETA))
        //{
        //    UtilidadesWeb.redirigirPaginaInterna("proforma");
        //}else 
        if(node.getLabel().equals(CLIENTE_ETIQUETA))
        {
            UtilidadesWeb.redirigirPaginaInterna("cliente");
        }else if(node.getLabel().equals(FACTURA_ETIQUETA))
        {
            UtilidadesWeb.redirigirPaginaInterna("factura");
        }
        
    }

    public void onNodeDblselect(SelectEvent event) {
        this.selectedNode = (MindmapNode) event.getObject();
    }

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
        return "Bienvenido";
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<AlertaResponse> getAlertasSistemas() { 
        return alertasSistemas;
    }

    public void setAlertasSistemas(List<AlertaResponse> alertasSistemas) {
        this.alertasSistemas = alertasSistemas;
    }

    public Boolean getDetenerActualizarAlerta() {
        return detenerActualizarAlerta;
    }

    public void setDetenerActualizarAlerta(Boolean detenerActualizarAlerta) {
        this.detenerActualizarAlerta = detenerActualizarAlerta;
    }

    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void limpiar() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    
    
    
    
    
}
