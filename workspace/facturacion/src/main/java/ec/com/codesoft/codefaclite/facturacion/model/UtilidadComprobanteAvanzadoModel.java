/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.general.ParametrosClienteEscritorio;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.facturacion.callback.ClienteFacturaLoteImplComprobante;
import ec.com.codesoft.codefaclite.facturacion.callback.ClienteUtilidadImplComprobante;
import ec.com.codesoft.codefaclite.facturacion.interfaz.InterfaceCallbakClient;
import ec.com.codesoft.codefaclite.facturacion.panel.UtilidadComprobantePanel;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.general.InformacionAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobanteLote;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteData;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac.TipoEnvioComprobanteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author
 */
public class UtilidadComprobanteAvanzadoModel extends UtilidadComprobantePanel {
    
    private static final int COLUMNA_SELECCION=0;
    private static final int COLUMNA_CLAVE_ACCESO=4;
    

    private UtilidadComprobanteAvanzadoModel formThis=this;
    
    private JInternalFrame frame;
    private DefaultTableModel tableModel;
    private List<ComprobanteElectronico> comprobantes;
    private DefaultListModel listaModel;

    public UtilidadComprobanteAvanzadoModel() {
        iniciarComponentes();
        addBotonListener();
        addComboListener();
        addTableListener();
        addCheckListener();
        addListenerPopUps();
        frame = this;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    }

//    @Override
    public String getNombre() {
        return "Utilidades Comprobante";
    }

    @Override
    public String getURLAyuda() {
        return "http://www.cf.codesoft-ec.com/ayuda#eutilidad";
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_NUEVO, false);
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, false);
        permisos.put(GeneralPanelInterface.BOTON_BUSCAR, false);
        permisos.put(GeneralPanelInterface.BOTON_ELIMINAR, false);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, false);
        permisos.put(GeneralPanelInterface.BOTON_REFRESCAR, false);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    
    private void cargarDatosComprobantesTabla(List<ComprobanteElectronico> comprobantes) {
        String[] titulo = new String[]{"Seleccion","tipo Documento","Preimpreso","Fecha","Clave Acceso"};

        this.tableModel=UtilidadesTablas.crearModeloTabla(titulo,new Class[]{Boolean.class,String.class,String.class,String.class,String.class});
        if(comprobantes!=null)
        {
            for (ComprobanteElectronico comprobante : comprobantes) {
                Vector<Object> fila = new Vector<>();
                fila.add(false);
                if(comprobante.getInformacionTributaria().getCodigoDocumentoEnum()!=null)
                {
                    fila.add(comprobante.getInformacionTributaria().getCodigoDocumentoEnum().getNombre());
                }
                else
                {
                    fila.add("SIN DOCUMENTO");
                }
                
                fila.add(comprobante.getInformacionTributaria().getPreimpreso());
                fila.add(comprobante.getFechaEmision());
                fila.add(comprobante.getInformacionTributaria().getClaveAcceso());
                this.tableModel.addRow(fila);
            }
        }
        this.getTblComprobantes().setModel(tableModel);
    }
    
    private void cargarSiguienteEtapaPorCarpeta(String carpeta)
    {
        //Por defecto en procesar etapa selecciono todos
        getCmbEstadoLimiteProcesar().setSelectedIndex(0);
        //Por defecto estaba seleccionando el siguiente nivel de la carpeta
        /*
        switch (carpeta) {

            case ComprobanteElectronicoService.CARPETA_GENERADOS:
                getCmbEstadoLimiteProcesar().setSelectedIndex(3);
                break;

            case ComprobanteElectronicoService.CARPETA_FIRMADOS:
                getCmbEstadoLimiteProcesar().setSelectedIndex(4);
                break;

            case ComprobanteElectronicoService.CARPETA_ENVIADOS:
                getCmbEstadoLimiteProcesar().setSelectedIndex(5);
                break;

            case ComprobanteElectronicoService.CARPETA_AUTORIZADOS:
                getCmbEstadoLimiteProcesar().setSelectedIndex(6);
                break;

            case ComprobanteElectronicoService.CARPETA_NO_AUTORIZADOS:
                getCmbEstadoLimiteProcesar().setSelectedIndex(6);
                break;

            case ComprobanteElectronicoService.CARPETA_RIDE:
                getCmbEstadoLimiteProcesar().setSelectedIndex(7);
                break;
        }*/
    
    }

    private void addBotonListener() {
        
        getBtnAgregarCorreo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String valor=JOptionPane.showInputDialog(null,"Ingrese un correo:");
                listaModel.addElement((valor.equals("")?"":valor));
            }
        });
        
        getBtnEliminarCorreo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer indice=getjListCorreos().getSelectedIndex();
                if(indice>=0)
                {
                    listaModel.remove(indice);
                }
            }
        });
        


        getBtnSiguienteEtapa().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String etapa = getCmbCarpetaComprobante().getSelectedItem().toString();
                Integer etapaLimite = getCmbEstadoLimiteProcesar().getSelectedIndex();
                
                /**
                 * Agrega un valor alto para que genere todas las etapas restantes de comprobante
                 */
                if(etapaLimite.equals(0))
                {
                    etapaLimite=100;
                }
                
                
                switch (etapa) {

                    case ComprobanteElectronicoService.CARPETA_GENERADOS:
                        procesarComprobanteLote(ComprobanteElectronicoService.ETAPA_GENERAR+1,etapaLimite);
                        break;

                    case ComprobanteElectronicoService.CARPETA_FIRMADOS:
                        procesarComprobanteLote(ComprobanteElectronicoService.ETAPA_FIRMAR+1,etapaLimite);
                        break;
                        
                    case ComprobanteElectronicoService.CARPETA_FIRMADOS_SIN_ENVIAR:
                        if(getChkEnvioCorreo().isSelected())
                            procesarComprobanteLote(ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE, etapaLimite);
                        else
                            procesarComprobanteLote(ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE + 1, etapaLimite);
                        //procesarComprobante(ComprobanteElectronicoService.ETAPA_ENVIO_COMPROBANTE + 1, etapaLimite);
                        break;

                    case ComprobanteElectronicoService.CARPETA_ENVIADOS_SIN_RESPUESTA: 
                        //TODO:Se deja el proceso normal porque con el proceso en lote no funciona par alos comprobantes que se quedaron en envio, analizar si para los ue se quedan en el envio en lote debe ser lo mismo, porque si se queda en esta etapa despues de procesar en lote talvez no funcione con este metodo
                        procesarComprobante(ComprobanteElectronicoService.ETAPA_ENVIAR+1, etapaLimite);
                        //procesarComprobanteLote(ComprobanteElectronicoService.ETAPA_ENVIAR+1, etapaLimite);
                        break;
                        
                    case ComprobanteElectronicoService.CARPETA_AUTORIZADOS:
                        procesarComprobanteLote(ComprobanteElectronicoService.ETAPA_AUTORIZAR+1,etapaLimite);
                        break;

                    case ComprobanteElectronicoService.CARPETA_NO_AUTORIZADOS:
                        procesarComprobanteLote(ComprobanteElectronicoService.ETAPA_AUTORIZAR+1,etapaLimite);
                        break;
                        
                    case ComprobanteElectronicoService.CARPETA_RIDE:
                        procesarComprobanteLote(ComprobanteElectronicoService.ETAPA_RIDE+1,etapaLimite);
                        break;
                }
                
            }
        });
    }

    
    
    private void procesarComprobante(Integer etapaInicial,Integer etapaLimite) 
    {
        
        try {
            //Buscar todos las filas seleccionadas
            List<String> clavesAcceso=obtenerClavesAccesoTabla();

            if (clavesAcceso.size() == 0) {
                DialogoCodefac.mensaje("Advertencia", "No existen datos para procesar", DialogoCodefac.MENSAJE_ADVERTENCIA);
                return;
            }

            panelPadre.cambiarCursorEspera();
            //for (String claveAcceso : clavesAcceso) {

                ComprobanteServiceIf comprobanteServiceIf = ServiceFactory.getFactory().getComprobanteServiceIf();
                ClienteUtilidadImplComprobante callBack = new ClienteUtilidadImplComprobante(this);
                
                List<String> errores=comprobanteServiceIf.procesarComprobantesPendienteLote(etapaInicial, etapaLimite, getMapClaveAccesoYCorreos(),getChkEnvioCorreo().isSelected(),session.getEmpresa());                
                //Mostrar los errores del Sistema
                String errorMsg="";
                if(errores.size()>0)
                {
                    for (String error : errores) {
                        errorMsg+=error+"\n\n";
                    }
                    DialogoCodefac.mensaje("Errores",errorMsg,DialogoCodefac.MENSAJE_INCORRECTO);
                }
            //}
            panelPadre.actualizarNotificacionesCodefac();
            panelPadre.cambiarCursorNormal();
            getCmbCarpetaComprobante().setSelectedIndex(getCmbCarpetaComprobante().getSelectedIndex()); //Volver a cargar los comprobantes para actualizar y que no aparesca los que ya fueron enviados

        } catch (RemoteException ex) {
            Logger.getLogger(UtilidadComprobanteAvanzadoModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(UtilidadComprobanteAvanzadoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Map<String,List<String>> getMapClaveAccesoYCorreos()
    {
        List<String> clavesAcceso=obtenerClavesAccesoTabla();
        Map<String,List<String>> mapClaveAccesoYCorreos=new HashMap<String,List<String>>();
        
        for (String claveAcceso : clavesAcceso) {
            mapClaveAccesoYCorreos.put(claveAcceso,new ArrayList<String>());
        }
        return mapClaveAccesoYCorreos;
    }
    

    
    private void procesarComprobanteLote(Integer etapaInicial,Integer etapaLimite) 
    {
        try {
            //Buscar todos las filas seleccionadas
            List<String> clavesAcceso=obtenerClavesAccesoTabla();
            
            if(clavesAcceso.size()==0)
            {
                DialogoCodefac.mensaje("Advertencia","No existen datos para procesar",DialogoCodefac.MENSAJE_ADVERTENCIA);
                return;
            }
            
            if(clavesAcceso.size()>0)
            {
                estadoCargando();
                ClienteInterfaceComprobanteLote cic = new ClienteFacturaLoteImplComprobante(this,new InterfaceCallbakClient() {
                    @Override
                    public void terminoProceso(List<ComprobanteData> comprobantes) {
                        formThis.estadoNormal();
                        getCmbCarpetaComprobante().setSelectedIndex(getCmbCarpetaComprobante().getSelectedIndex()); //Vuelve a cargar los comprobantes
                    }
                });
                
                //ParametrosClienteEscritorio.tipoClienteEnum=ParametrosClienteEscritorio.TipoClienteSwingEnum.REMOTO;
                Boolean procesoSincronico=false;
                if(ParametrosClienteEscritorio.tipoClienteEnum.equals(ParametrosClienteEscritorio.TipoClienteSwingEnum.REMOTO))
                {
                    cic=null; //Si es un cliente remoto el que ejecuta la accion no puede hacer callback para veriicar
                    procesoSincronico=true;
                }
                
                Logger.getLogger(UtilidadComprobanteAvanzadoModel.class.getName()).log(Level.INFO,"Preparando comprobantes en el cliente para mandar al servidor para procesar");
                ServiceFactory.getFactory().getComprobanteServiceIf().procesarComprobantesLotePendiente(etapaInicial, etapaLimite, clavesAcceso,null, session.getEmpresa().getIdentificacion(),cic,getChkEnvioCorreo().isSelected(),session.getEmpresa(),procesoSincronico);
                Logger.getLogger(UtilidadComprobanteAvanzadoModel.class.getName()).log(Level.INFO,"Terminado proceso de enviar los comprobantes al servidor");
                
                if(ParametrosClienteEscritorio.tipoClienteEnum.equals(ParametrosClienteEscritorio.TipoClienteSwingEnum.REMOTO))
                {
                    estadoNormal();
                    getCmbCarpetaComprobante().setSelectedIndex(getCmbCarpetaComprobante().getSelectedIndex()); //Vuelve a cargar los comprobantes
                }
                //estadoNormal();
            }
            else
            {
                DialogoCodefac.mensaje("Advertencia","Seleccione datos para procesar",DialogoCodefac.MENSAJE_ADVERTENCIA);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(UtilidadComprobanteAvanzadoModel.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        
    }
    
    private List<String> obtenerClavesAccesoTabla() {
        List<String> clavesAcceso = new ArrayList<String>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Boolean opcion = (Boolean) tableModel.getValueAt(i, COLUMNA_SELECCION);
            if (opcion) {
                String claveAcceso = tableModel.getValueAt(i, COLUMNA_CLAVE_ACCESO).toString();
                clavesAcceso.add(claveAcceso);
                System.out.println(claveAcceso);
            }
        }
        return clavesAcceso;

    }
    
    private List<String> obtenerCorreos()
    {
        List<String> correos=new ArrayList<String>();
        for (int i = 0; i < listaModel.getSize(); i++) {
            correos.add(listaModel.get(i).toString());
        }
        return correos;
    }

    


    protected void iniciarComponentes() {
        /**
         * Cargar las carpetas disponibles de los comprobantes
         */
        getCmbCarpetaComprobante().removeAllItems();
        getCmbCarpetaComprobante().addItem(ComprobanteElectronicoService.CARPETA_GENERADOS);
        getCmbCarpetaComprobante().addItem(ComprobanteElectronicoService.CARPETA_FIRMADOS);
        getCmbCarpetaComprobante().addItem(ComprobanteElectronicoService.CARPETA_FIRMADOS_SIN_ENVIAR);
        getCmbCarpetaComprobante().addItem(ComprobanteElectronicoService.CARPETA_ENVIADOS_SIN_RESPUESTA);
        getCmbCarpetaComprobante().addItem(ComprobanteElectronicoService.CARPETA_AUTORIZADOS);
        getCmbCarpetaComprobante().addItem(ComprobanteElectronicoService.CARPETA_NO_AUTORIZADOS);
        getCmbCarpetaComprobante().addItem(ComprobanteElectronicoService.CARPETA_RIDE);
        
        cargarEtapas();

    }

    protected void cargarEtapas() {
        getCmbEstadoLimiteProcesar().removeAllItems();

        getCmbEstadoLimiteProcesar().addItem("procesar todo");

        getCmbEstadoLimiteProcesar().addItem("generar");

        getCmbEstadoLimiteProcesar().addItem("prevalidar");

        getCmbEstadoLimiteProcesar().addItem("firmar");

        getCmbEstadoLimiteProcesar().addItem("enviar SRI");

        getCmbEstadoLimiteProcesar().addItem("autorizar SRI");

        getCmbEstadoLimiteProcesar().addItem("generar RIDE");

        getCmbEstadoLimiteProcesar().addItem("envio comprobante");

    }

    @Override
    public void iniciar() {
        getCmbCarpetaComprobante().setSelectedItem(ComprobanteElectronicoService.CARPETA_FIRMADOS_SIN_ENVIAR);
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addComboListener() {
            getCmbCarpetaComprobante().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    //TODO: Revisar esta validacion temporal porque no existe la carpeta de no autorizado
                    if (getCmbCarpetaComprobante().getSelectedItem().equals(ComprobanteElectronicoService.CARPETA_NO_AUTORIZADOS)) {
                        return;
                    }
                    
                                       
                    ComprobanteServiceIf comprobanteServiceIf = ServiceFactory.getFactory().getComprobanteServiceIf();
                    comprobantes = comprobanteServiceIf.getComprobantesObjectByFolder(getCmbCarpetaComprobante().getSelectedItem().toString(),session.getEmpresa());
                    cargarDatosComprobantesTabla(comprobantes);
                    cargarSiguienteEtapaPorCarpeta(getCmbCarpetaComprobante().getSelectedItem().toString());
                   

                    
                } catch (RemoteException ex) {
                    Logger.getLogger(UtilidadComprobanteAvanzadoModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void addTableListener() {
            getTblComprobantes().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Integer indice = getTblComprobantes().getSelectedRow();

                ComprobanteElectronico comprobante = comprobantes.get(indice);
                listaModel = new DefaultListModel();
                List<InformacionAdicional> infoAdicional = comprobante.getInformacionAdicional();
                if(infoAdicional!=null)
                {
                    for (InformacionAdicional informacionAdicional : infoAdicional) {
                        if (informacionAdicional.getNombre().indexOf(ComprobanteAdicional.CampoDefectoEnum.CORREO.getNombre()) >= 0) {
                            listaModel.addElement(informacionAdicional.getValor());
                        }
                    }
                }
                getjListCorreos().setModel(listaModel);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addCheckListener() {
        getChkSeleccionarTodo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean seleccion=getChkSeleccionarTodo().isSelected();
                
                for (int i=0;i<getTblComprobantes().getRowCount();i++) {
                    getTblComprobantes().setValueAt(seleccion,i,COLUMNA_SELECCION);
                }
            }
        });
    }

    private void addListenerPopUps() {
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem jMenuItemDatoAdicional = new JMenuItem("Eliminar");
        JMenuItem jMenuItemSolucionarProblema = new JMenuItem("Solucionar Problema");
        
        jMenuItemDatoAdicional.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = getTblComprobantes().getSelectedRow();
                if (filaSeleccionada >= 0) {
                    String claveAcesso = (String) getTblComprobantes().getValueAt(filaSeleccionada, COLUMNA_CLAVE_ACCESO); //Obtener el objeto de la columna
                    Boolean respuesta=DialogoCodefac.dialogoPregunta("Advertencia","Esta seguro que desea elminar el xml de la carpeta?",DialogoCodefac.MENSAJE_ADVERTENCIA);
                    if(respuesta)
                    {
                        String nombreCarpeta=getCmbCarpetaComprobante().getSelectedItem().toString();
                        try {
                            ServiceFactory.getFactory().getComprobanteServiceIf().eliminarComprobanteFisico(claveAcesso,nombreCarpeta);
                            DialogoCodefac.mensaje("Correcto","El xml fue eliminado correctamente", DialogoCodefac.MENSAJE_CORRECTO);
                            getCmbCarpetaComprobante().setSelectedIndex(getCmbCarpetaComprobante().getSelectedIndex());
                        } catch (RemoteException ex) {
                            Logger.getLogger(UtilidadComprobanteAvanzadoModel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ServicioCodefacException ex) {
                            Logger.getLogger(UtilidadComprobanteAvanzadoModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
    
        jMenuItemSolucionarProblema.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                int indice=getTblComprobantes().getSelectedRow();
                if(indice<0)
                {
                    return ; //Validacion que solo permite ejecutar el proceso si esta seleccionado un comprobante
                }
                
                panelPadre.cambiarCursorEspera();
                ComprobanteElectronico comprobante = comprobantes.get(indice);
                String carpetaSeleccionada=(String) getCmbCarpetaComprobante().getSelectedItem();
                try {
                    List<String> mensajes=ServiceFactory.getFactory().getComprobanteServiceIf().solucionarProblemasEnvioComprobante(
                            carpetaSeleccionada,
                            comprobante.getInformacionTributaria().getClaveAcceso(),
                            session.getEmpresa());
                    
                    if(mensajes.size()>0)
                    {
                        DialogoCodefac.mensaje("Advertencia",UtilidadesLista.castListToString(mensajes,","), indice);
                    }
                    actualizarDatosTabla();
                    panelPadre.actualizarNotificacionesCodefac();
                    

                } catch (RemoteException ex) {
                    Logger.getLogger(UtilidadComprobanteAvanzadoModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(UtilidadComprobanteAvanzadoModel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                panelPadre.cambiarCursorNormal();
                
            }
        });
        
        jPopupMenu.add(jMenuItemDatoAdicional);
        jPopupMenu.add(jMenuItemSolucionarProblema);
        getTblComprobantes().setComponentPopupMenu(jPopupMenu);
    }
    
    private void actualizarDatosTabla()
    {
        getCmbCarpetaComprobante().setSelectedIndex(getCmbCarpetaComprobante().getSelectedIndex());
    }

}
