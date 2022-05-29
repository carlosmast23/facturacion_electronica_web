/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import ec.com.codesoft.codefaclite.configuraciones.panel.ComprobantesConfiguracionPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.dialog.ProcesoSegundoPlano;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.FirmaElectronica;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Impuesto;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ImpuestoDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Perfil;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Persona;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoDetalleServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ImpuestoServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.PersonaServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac.TipoEnvioComprobanteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ModoSistemaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.email.CorreoElectronico;
import ec.com.codesoft.codefaclite.utilidades.email.PropiedadCorreo;
import ec.com.codesoft.codefaclite.utilidades.email.SmtpNoExisteException;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;
import ec.com.codesoft.codefaclite.utilidades.varios.DialogoCopiarArchivos;
import es.mityc.firmaJava.libreria.utilidades.Utilidades;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import static java.awt.image.ImageObserver.SOMEBITS;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Carlos
 */
public class ComprobantesConfiguracionModel extends ComprobantesConfiguracionPanel {

    private Map<String, ParametroCodefac> parametros;
    private List<ParametroCodefac> parametrosEditar;
    private ParametroCodefacServiceIf parametroCodefacService;
    private ImpuestoDetalleServiceIf impuestoDetalleService;
    private JFileChooser jFileChooser;
    private Path origen = null;
    
    /**
     * Path que me permite guardar la imagen de fondo
     */
    private Path origenImagen=null;
    //private Path destino = null;
    private Persona cliente;
    private PersonaServiceIf clienteService;
    
    private DialogoCopiarArchivos dialogoCopiarFondoEscritorio;
    

    public ComprobantesConfiguracionModel() {
        
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            //getTxtClaveFirma().setEnabled(true);
            actualizarDatosVista();
            moverTodosArchivos();
            
            /**
             * Grabar el fondo de escritorio
             */
            if(dialogoCopiarFondoEscritorio.origen!=null && dialogoCopiarFondoEscritorio.destino!=null)
            {
                //Todo:Analizar esta parte porque con em metodo moverArchivo hace una copia local , no en el servidor.
                dialogoCopiarFondoEscritorio.moverArchivo();
                ParametroCodefac parametro = parametros.get(ParametroCodefac.IMAGEN_FONDO);
                parametro.setValor(dialogoCopiarFondoEscritorio.destino.getFileName().toString());
                panelPadre.establecerImagenFondo();
                
            }
            
            this.parametroCodefacService.editarParametros(parametrosEditar);
            this.panelPadre.actualizarTituloCodefac();
            /**
             * Establesco el ciclo de vida en el cual me encuentro
             */
            this.estadoFormulario = GeneralPanelInterface.ESTADO_GRABAR;
            DialogoCodefac.mensaje("Actualizado datos", "Los datos de los parametros fueron actualizados", DialogoCodefac.MENSAJE_CORRECTO);            
            
            dispose(); //TODO: En esta parte analizar porque cuando se sale del formulario no se borra de la lista de ventas abiertas del menu
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobantesConfiguracionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        //super.estadoFormulario = GeneralPanelInterface.ESTADO_EDITAR;

        //Validaciones adicionales para validar segun el tipo de usuario Logueado
        
        /*
        if (!session.verificarExistePerfil(Perfil.PERFIl_ADMINISTRADOR)) {
            getCmbModoFacturacion().setEnabled(false);
            getCmbIvaDefault().setEnabled(false);
        }*/
    }

//    @Override
    public String getNombre() {
        return "Configuración de comprobantes";
    }

    @Override
    public String getURLAyuda() {
        return "http://www.cf.codesoft-ec.com/ayuda#ecomprobantes";
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    private void actualizarDatosVista() {
        parametrosEditar=new ArrayList<ParametroCodefac>();
              
        String ivaDefacto = ((ImpuestoDetalle) getCmbIvaDefault().getSelectedItem()).getTarifa().toString();
        setearParametro(ParametroCodefac.IVA_DEFECTO,ivaDefacto);
        
        setearParametro(ParametroCodefac.CORREO_USUARIO,getTxtCorreoElectronico().getText());
        
        setearParametro(ParametroCodefac.CORREO_CLAVE,UtilidadesEncriptar.encriptar(new String(getTxtPasswordCorreo().getPassword()),ParametrosSistemaCodefac.LLAVE_ENCRIPTAR));
        
        setearParametro(ParametroCodefac.CLAVE_FIRMA_ELECTRONICA,UtilidadesEncriptar.encriptar(new String(getTxtClaveFirma().getPassword()),ParametrosSistemaCodefac.LLAVE_ENCRIPTAR));
        
        setearParametro(ParametroCodefac.MODO_FACTURACION,getCmbModoFacturacion().getSelectedItem().toString());
        
        //setearParametro(ParametroCodefac.TIPO_FACTURACION,((ComprobanteEntity.TipoEmisionEnum)getCmbTipoFacturacion().getSelectedItem()).getLetra());
        
        setearParametro(ParametroCodefac.SMTP_HOST,getTxtSmtpHost().getText());
        
        setearParametro(ParametroCodefac.SMTP_PORT,getTxtSmtpPuerto().getValue().toString());
        
        setearParametro(ParametroCodefac.DIRECTORIO_RECURSOS,getTxtDirectorioRecurso().getText());
                
        //Setear la fecha de emision para poder lanzar una alerta
        String fechaFirmaEmisionStr="";//Cuando no exista firma solo graba vacio
        if(getCmbFechaEmisionFirma().getDate()!=null)
        {
            fechaFirmaEmisionStr=ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA.format(getCmbFechaEmisionFirma().getDate());
        }
        setearParametro(ParametroCodefac.FIRMA_FECHA_EMISION,fechaFirmaEmisionStr);
        
        //Setear la duracion de la firma para poder lanzar una alerta
        String aniosDuracionFirma="";
        if(getTxtDuracionFirma().getValue()!=null)
        {
            aniosDuracionFirma=getTxtDuracionFirma().getValue().toString();
        }
        setearParametro(ParametroCodefac.FIRMA_TIEMPO_EXPIRACION_AÑOS,aniosDuracionFirma);
        
        
        ParametroCodefac.TipoEnvioComprobanteEnum tipoEnvioEnum=(ParametroCodefac.TipoEnvioComprobanteEnum) getCmbTipoEnvioComprobante().getSelectedItem();
        setearParametro(ParametroCodefac.TIPO_ENVIO_COMPROBANTE,tipoEnvioEnum.getLetra());
        
        //verificarFirmaElectronica();
    }
    
    private void setearParametro(String parametro,String valor)
    {
        ParametroCodefac parametroCodefac=new ParametroCodefac();
        if(parametros.get(parametro)!=null)
        {
            parametroCodefac=parametros.get(parametro);
        }
        else
        {
            parametroCodefac.setNombre(parametro);
        }        
 
        parametroCodefac.setEmpresa(session.getEmpresa());
        parametroCodefac.setValor(valor);    
        parametrosEditar.add(parametroCodefac);
        
    }

    private void cargarDatosConfiguraciones() {
        try {
            ParametroCodefac parametroCodefac=null;
            parametros = parametroCodefacService.getParametrosMap(session.getEmpresa());
            //ParametroCodefac param = parametros.get(ParametroCodefac.SECUENCIAL_FACTURA);
            
            getTxtDirectorioRecurso().setText((parametros.get(ParametroCodefac.DIRECTORIO_RECURSOS)!=null)?parametros.get(ParametroCodefac.DIRECTORIO_RECURSOS).getValor():"");
            
            
            getTxtCorreoElectronico().setText(parametros.get(ParametroCodefac.CORREO_USUARIO).getValor());
            
            String claveEncriptada=parametros.get(ParametroCodefac.CORREO_CLAVE).getValor();
            String claveDesencriptada=UtilidadesEncriptar.desencriptar(claveEncriptada, ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
            
            getTxtPasswordCorreo().setText(claveDesencriptada);
            //getTxtPasswordCorreo().setText(UtilidadesEncriptar.desencriptar(parametros.get(ParametroCodefac.CORREO_CLAVE).getValor(),ParametrosSistemaCodefac.LLAVE_ENCRIPTAR));
            
            parametroCodefac=parametros.get(ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA);
            if(parametroCodefac!=null)
            {
                getTxtNombreFirma().setText(parametroCodefac.getValor());
            }
            //getTxtFondoEscritorio().setText(parametros.get(ParametroCodefac.IMAGEN_FONDO).getValor());
            
            parametroCodefac=parametros.get(ParametroCodefac.CLAVE_FIRMA_ELECTRONICA);
            if(parametroCodefac!=null)
            {
                getTxtClaveFirma().setText(UtilidadesEncriptar.desencriptar(parametroCodefac.getValor(),ParametrosSistemaCodefac.LLAVE_ENCRIPTAR));
            }
            
            
            if(parametros.get(ParametroCodefac.IMAGEN_FONDO)!=null)
            {
                getTxtFondoEscritorio().setText(parametros.get(ParametroCodefac.IMAGEN_FONDO).getValor());
            }
            
            //TODO: Mejorar esta parte buscando el iva por defecto
            Map<String, Object> map = new HashMap<String, Object>();
            parametroCodefac=parametros.get(ParametroCodefac.IVA_DEFECTO);
            if(parametroCodefac!=null)
            {
                map.put("tarifa", Integer.parseInt(parametroCodefac.getValor()));
                List<ImpuestoDetalle> lista = impuestoDetalleService.buscarImpuestoDetallePorMap(map);
                getCmbIvaDefault().getModel().setSelectedItem(lista.get(0));
            }
            
            /**
             * Cargar el modo de facturacion por defecto
             */
            parametroCodefac=parametros.get(ParametroCodefac.MODO_FACTURACION);
            if(parametroCodefac!=null)
            {
                String modoProduccion = parametros.get(ParametroCodefac.MODO_FACTURACION).getValor();
                getCmbModoFacturacion().setSelectedItem(modoProduccion);
            }
            
            /**
             * Cargar el tipo de facturacion
             */
            /*parametroCodefac=parametros.get(ParametroCodefac.TIPO_FACTURACION);
            if(parametroCodefac!=null)
            {
                String letra=parametroCodefac.getValor();
                getCmbTipoFacturacion().setSelectedItem(ComprobanteEntity.TipoEmisionEnum.getEnumByEstado(letra));
            }
            
            listenerCmbTipoFacturacion(); //modifica las acciones para esta accion*/
            
            getTxtSmtpHost().setText(parametros.get(ParametroCodefac.SMTP_HOST).getValor());
            getTxtSmtpPuerto().setValue(new Integer(parametros.get(ParametroCodefac.SMTP_PORT).getValor()));
            
            /**
             * Setear la fecha de la firma
             */
            
            String firmaFechaEmisionStr=ParametroUtilidades.obtenerValorParametro(session.getEmpresa(),ParametroCodefac.FIRMA_FECHA_EMISION);
            if(firmaFechaEmisionStr!=null)
            {
                parametroCodefac=parametros.get(ParametroCodefac.FIRMA_FECHA_EMISION);
                if(parametroCodefac!=null && parametroCodefac.getValor()!=null && !parametroCodefac.getValor().trim().isEmpty())
                {
                    Date fechaEmisionFirma=ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA.parse(parametroCodefac.getValor());
                    getCmbFechaEmisionFirma().setDate(fechaEmisionFirma);   
                }
            }
            else
            {
                getCmbFechaEmisionFirma().setDate(null);
            }
            
            /**
             * Setear la fecha de expiracion de la firma
             */
            String tiempoExpiracionFirmaStr=ParametroUtilidades.obtenerValorParametro(session.getEmpresa(),ParametroCodefac.FIRMA_TIEMPO_EXPIRACION_AÑOS);
            if(tiempoExpiracionFirmaStr!=null)
            {
                Integer tiempoAniosVigenciaFirma=Integer.parseInt(tiempoExpiracionFirmaStr);
                getTxtDuracionFirma().setValue(tiempoAniosVigenciaFirma);
            }
            else
            {
                getTxtDuracionFirma().setValue(0);
            }
            
            
            parametroCodefac=parametros.get(ParametroCodefac.TIPO_ENVIO_COMPROBANTE);
            if(parametroCodefac!=null)
            {
                getCmbTipoEnvioComprobante().setSelectedItem(TipoEnvioComprobanteEnum.buscarPorLetra(parametroCodefac.valor));
            }
            
            
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobantesConfiguracionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ComprobantesConfiguracionModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void cargarDatosIva() 
    {
        
        try {
            ImpuestoServiceIf impuestoService = ServiceFactory.getFactory().getImpuestoServiceIf();
            Impuesto iva = impuestoService.obtenerImpuestoPorCodigo(Impuesto.IVA);
            ImpuestoDetalle impuestoDefecto=null;
            
            String ivaStr=ParametrosSistemaCodefac.IVA_DEFECTO;
            
            Integer ivaPorDefecto=new Integer(ivaStr);
            
            for (ImpuestoDetalle impuesto : iva.getDetalleImpuestos()) 
            {
                getCmbIvaDefault().addItem(impuesto);                
                if(ivaPorDefecto.equals(impuesto.getTarifa()))
                {
                    impuestoDefecto=impuesto;
                }                
            }
            
            //Seleccionar por defecto el iva actual que se encuentra en vigencia
            getCmbIvaDefault().setSelectedItem(impuestoDefecto);
            
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobantesConfiguracionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*private void cargarTipoFactura()
    {
        getCmbTipoFacturacion().removeAllItems();
        ComprobanteEntity.TipoEmisionEnum[] tipos= ComprobanteEntity.TipoEmisionEnum.values();
        for (ComprobanteEntity.TipoEmisionEnum tipo : tipos) {
            getCmbTipoFacturacion().addItem(tipo);
        }
    }*/

    private void cargarModosFacturacion() {
        getCmbModoFacturacion().addItem(ComprobanteElectronicoService.MODO_PRUEBAS);
        getCmbModoFacturacion().addItem(ComprobanteElectronicoService.MODO_PRODUCCION);
        
        
        getCmbTipoEnvioComprobante().removeAllItems();
        for (ParametroCodefac.TipoEnvioComprobanteEnum tipoEnvio : TipoEnvioComprobanteEnum.values()) {
            getCmbTipoEnvioComprobante().addItem(tipoEnvio);
        }
    }

    public Path cargarDatosArchivos(File archivoEscogido,JTextField textField) {
        File archivo = archivoEscogido;
        String rutaArchivo = archivo.getPath();
        String nombreArchivo = archivo.getName();
        textField.setText(nombreArchivo);
        //TODO:Cambiar la copia de archivos por un servicio de transferencia de archivos
        //rutaDestino += nombreArchivo;
        return establecerDondeMoverArchivo(rutaArchivo/*, rutaDestino*/);
    }

    public Path establecerDondeMoverArchivo(String rutaArchivo/*,String rutaDestino*/) {
        return FileSystems.getDefault().getPath(rutaArchivo);
        //this.destino = FileSystems.getDefault().getPath(rutaDestino);
    }
    
    public void moverTodosArchivos()
    {
        moverArchivo(origen, getTxtDirectorioRecurso().getText(), DirectorioCodefac.CONFIGURACION, ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA);
        moverArchivo(origenImagen, getTxtFondoEscritorio().getText(), DirectorioCodefac.IMAGENES,ParametroCodefac.IMAGEN_FONDO);
        
    }

    /**
     * TODO: Mover a un metodo general por que se puede reutilizar esta funcionalidad
     * Metodo para mover las firmas
     */
    public void moverArchivo(Path origen,String directorio,DirectorioCodefac directorioCodefac,String nombreParametro) {
        try {
            //Verifica que solo cuando exista un origen y destino exista se copien los datos            
            if (origen == null) {
                return;
            }
            
            //Servicio para poder copiar los datos
            SimpleRemoteInputStream istream = new SimpleRemoteInputStream(
                    new FileInputStream(origen.toFile()));
            
            //Obtener el directorio de los recursos
            String directorioServidor=(parametros.get(ParametroCodefac.DIRECTORIO_RECURSOS)!=null)?parametros.get(ParametroCodefac.DIRECTORIO_RECURSOS).valor:directorio;
            ServiceFactory.getFactory().getRecursosServiceIf().uploadFileServer(directorioServidor,directorioCodefac, istream,origen.getFileName().toString());
            
            getTxtNombreFirma().setText("" + origen.getFileName());
            
            setearParametro(nombreParametro,origen.getFileName().toString());


        } catch (FileNotFoundException ex) {
            Logger.getLogger(ComprobantesConfiguracionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobantesConfiguracionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    private void addListenerButtons() {
        
        getBtnBuscarDirectorio().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                seleccionarDirectorioRecursos();
            }
        });
        
        getBtnFirmaElectronica().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                jFileChooser = new JFileChooser();
                jFileChooser.setDialogTitle("Elegir archivo");
                jFileChooser.setFileFilter(new FileNameExtensionFilter("Firma Electronica SRI", "p12"));
                
                int seleccion = jFileChooser.showDialog(null, "Abrir");
                switch (seleccion) {
                    case JFileChooser.APPROVE_OPTION:
                        origen=cargarDatosArchivos(jFileChooser.getSelectedFile(),getTxtNombreFirma());
                        break;
                    case JFileChooser.CANCEL_OPTION:

                        break;
                    case JFileChooser.ERROR_OPTION:

                        break;
                }

            }
        });

        getTxtClaveFirma().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void focusLost(FocusEvent e) {
                verificarFirmaElectronica();
            }
        });

        getTxtPasswordCorreo().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void focusLost(FocusEvent e) {
                //No validar cuando el password esta vacio
                if ((new String(getTxtPasswordCorreo().getPassword())).length() == 0) {
                    return;
                }

                try {
                    DialogoCodefac.mostrarDialogoCargando(new ProcesoSegundoPlano() {
                        @Override
                        public void procesar() {
                            verificarCredencialesCorreo();
                        }
                        
                        @Override
                        public String getMensaje() {
                            return "Validando Correo";
                        }
                    });
                } catch (ExcepcionCodefacLite ex) {
                    Logger.getLogger(ComprobantesConfiguracionModel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        
        getBtnBuscarImagen().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                jFileChooser = new JFileChooser();
                jFileChooser.setDialogTitle("Elegir imagen");
                int seleccion = jFileChooser.showDialog(null, "Abrir");
                
                switch (seleccion) 
                {
                    case JFileChooser.APPROVE_OPTION:
                        origenImagen=cargarDatosArchivos(jFileChooser.getSelectedFile(),getTxtFondoEscritorio());
                        break;
                    case JFileChooser.CANCEL_OPTION:

                        break;
                    case JFileChooser.ERROR_OPTION:

                        break;
                }

            }
        });
        
        /*getCmbTipoFacturacion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listenerCmbTipoFacturacion();
            }
        });*/
    }
    
    /*private void listenerCmbTipoFacturacion()
    {
        ComprobanteEntity.TipoEmisionEnum tipo = (ComprobanteEntity.TipoEmisionEnum) getCmbTipoFacturacion().getSelectedItem();
        if (tipo.equals(ComprobanteEntity.TipoEmisionEnum.NORMAL)) {
            activarOpcionesFacturarElectronica(false);
        } else {
            activarOpcionesFacturarElectronica(true);
        }
    }*/
    
    private void activarOpcionesFacturarElectronica(boolean opcion)
    {
        getPanelFacturacionElectronica().setVisible(opcion);
        /*().setEnabled(!opcion);
        getTxtGuiaRemisionSecuencialFisico().setEnabled(!opcion);
        getTxtNotaCreditoSecuencialFisico().setEnabled(!opcion);
        getTxtNotaDebitoSecuencialFisico().setEnabled(!opcion);
        getTxtGuiaRemisionSecuencialFisico().setEnabled(!opcion);
        getTxtRetencionesSecuencialFisico().setEnabled(!opcion);   
        getTxtNotaVentaSecuencialFisico().setEnabled(!opcion);
        
        getTxtFacturaSecuencial().setEnabled(opcion);
        getTxtGuiaRemisionSecuencial().setEnabled(opcion);
        getTxtNotaCreditoSecuencial().setEnabled(opcion);
        getTxtNotaDebitoSecuencial().setEnabled(opcion);
        getTxtGuiaRemisionSecuencial().setEnabled(opcion);
        getTxtRetencionesSecuencial().setEnabled(opcion);        */

    }
    
    private PropiedadCorreo obtenerPropiedadesCorreo()
    {
        if(getTxtSmtpHost().getText()!=null && !getTxtSmtpHost().getText().isEmpty())
        {
            PropiedadCorreo propiedadCorreo=new PropiedadCorreo(getTxtSmtpHost().getText(), (int) getTxtSmtpPuerto().getValue());
            return propiedadCorreo;
        }
        return null;
    }

    /**
     * TODO: Unir con el metodo general de UtilidadesCorreo
     */
    private void verificarCredencialesCorreo() {

        try {
            List<String> correos = new ArrayList<String>();
            correos.add(getTxtCorreoElectronico().getText());
            String desc = "Bienvenido a Codefac-Lite. <br>"
                    + "Estimado/a usuario le informamos que su cuenta en Codefac-Lite ha sido activada exitosamente. Ahora ya puedes aprovechar los beneficios de nuestro sistema de facturación electrónica.\n"
                    + "<br><br> <b>NOTA.- Este mensaje fue enviado automáticamente por el sistema, por favor no responda a este correo.</b>";
            CorreoElectronico correoElectronico = new CorreoElectronico(getTxtCorreoElectronico().getText(),"Sistema Codefac" ,new String(getTxtPasswordCorreo().getPassword()), desc, correos, "Notificación Codefac",obtenerPropiedadesCorreo());
            correoElectronico.sendMail();
            //TODO: Verificar si se va a dar uso de esta funcionalidad
            //TODO: Agregar una variable para la informacion del consumidor final
            //configurarCorreoDeConsumidorFinal();
            //DialogoCodefac.mensaje("Exito","El correo y la clave son correctos",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (AuthenticationFailedException ex) {
            System.out.println("Fallo al autentificar el usuario");
            getTxtPasswordCorreo().setText("");
            DialogoCodefac.mensaje("Error Correo", "Las credenciales de su correo son incorrectas", DialogoCodefac.MENSAJE_INCORRECTO);

        } catch (MessagingException ex) {
            Logger.getLogger(ComprobantesConfiguracionModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error Correo", "Los datos ingresados son incorrectos.\n"+ex.getMessage(), DialogoCodefac.MENSAJE_INCORRECTO);
        } catch (SmtpNoExisteException ex) {
            System.out.println("Fallo al autentificar el usuario");
            getTxtPasswordCorreo().setText("");
            DialogoCodefac.mensaje("Error Correo", "Ingrese un correo valido", DialogoCodefac.MENSAJE_INCORRECTO);
        }
    }

    /**
     * TODO: Hacer la validacion utilizando el metodo creado en UtilidadesFirmaElectronica
     */
    private void verificarFirmaElectronica() {
        try {
            String claveFirma = new String(getTxtClaveFirma().getPassword());
            String nombreArchivo = getTxtNombreFirma().getText();
            //TODO:Cambiar la copia de archivos por un servicio de transferencia de archivos
            String rutaDestino = "";
            
            String pathFirma = "";

            if (origen != null) { //Si selecciona una archivo para recien grabar se verifica con el archivo del cliente
                //TODO: Ver si se puede mejorar y hacer la validacion enviando archivo y clave para tener mas modular el procesos de firma
                pathFirma = origen.toString();
                if (!claveFirma.equals("") && !pathFirma.equals("")) {
                    if (!FirmaElectronica.FirmaVerificar(pathFirma, claveFirma)) {
                        getTxtClaveFirma().setText("");
                        DialogoCodefac.mensaje("Error Clave", "La Clave de la firma es incorrecta, ingrese nuevamente.", DialogoCodefac.MENSAJE_INCORRECTO);

                    }
                }
            } else {
                //Cuando el archivo de la firma ya esta en el servidor se consulta por un servicio

                Boolean validacion = ServiceFactory.getFactory().getComprobanteServiceIf().verificarCredencialesFirma(claveFirma,session.getEmpresa());

                if (!validacion) {
                    getTxtClaveFirma().setText("");
                    DialogoCodefac.mensaje("Error Clave", "La Clave de la firma es incorrecta, ingrese nuevamente.", DialogoCodefac.MENSAJE_INCORRECTO);

                }

            }
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobantesConfiguracionModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void iniciar() {        
        impuestoDetalleService = ServiceFactory.getFactory().getImpuestoDetalleServiceIf();
        this.parametroCodefacService = ServiceFactory.getFactory().getParametroCodefacServiceIf();
        cargarDatosIva();
        //cargarTipoFactura();
        cargarModosFacturacion();
        cargarDatosConfiguraciones();
        jFileChooser = new JFileChooser();
        jFileChooser.setDialogTitle("Elegir archivo");
        jFileChooser.setFileFilter(new FileNameExtensionFilter("Firma Electronica SRI", "p12"));
        dialogoCopiarFondoEscritorio=new DialogoCopiarArchivos("Elegir archivo", "Imagen Escritorio", "jpg","png","bpm");
        addListenerButtons();
        addListenerCombos();
        /**
         * Desactivo el ciclo de vida para controlar manualmente
         */
        super.cicloVida = false;
        super.validacionDatosIngresados=false;
        
        listenerTextos();
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        List<String> permisosPerfil = new ArrayList<String>();
        //permisosPerfil.add(Perfil.PERFIl_OPERADOR);
        //permisosPerfil.add(Perfil.PERFIl_ADMINISTRADOR);
        return permisosPerfil;
    }

    public void configurarCorreoDeConsumidorFinal() {
        try {
            clienteService = ServiceFactory.getFactory().getPersonaServiceIf();
            for (Persona c : clienteService.buscar()) {
                if (c.getRazonSocial().equals("Cliente Final")) { 
                    cliente = c;
                }
            }
            cliente.setCorreoElectronico(getTxtCorreoElectronico().getText());
            clienteService.editar(cliente);
        } catch (RemoteException ex) {
            Logger.getLogger(ComprobantesConfiguracionModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(ComprobantesConfiguracionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void addListenerCombos() {
        
        getCmbModoFacturacion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String modoFacturacion = getCmbModoFacturacion().getSelectedItem().toString();

                if (ComprobanteElectronicoService.MODO_PRODUCCION.equals(modoFacturacion)) {
                    DialogoCodefac.mensaje("Alerta", "Recuerde que en el modo producción todos los documentos son legales y autorizados por el SRI", DialogoCodefac.MENSAJE_ADVERTENCIA);
                } else if (ComprobanteElectronicoService.MODO_PRUEBAS.equals(modoFacturacion)) {
                    DialogoCodefac.mensaje("Alerta", "Recuerde que esta opción debe estar activa solo para hacer pruebas", DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
            }
        });
        
        
    }

    private void listenerTextos() {
        getTxtCorreoElectronico().addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            /**
             * Verificar que cuando pierde el focus verifique si existen datos por defecto para cargar de los datos smtp de los correos electronicos
             * @param e 
             */
            @Override
            public void focusLost(FocusEvent e) {
                setearDatosPorDefectoSmtp(getTxtCorreoElectronico().getText());
            }
        });
    }
    
    
    private void setearDatosPorDefectoSmtp(String correo)
    {
        PropiedadCorreo propiedadPorDefecto=PropiedadCorreo.obtenerPropiedadesPorDefecto(correo);
        if(propiedadPorDefecto!=null)
        {
            getTxtSmtpHost().setText(propiedadPorDefecto.getHost());
            getTxtSmtpPuerto().setValue(propiedadPorDefecto.getPort());
        }
        //else
        //{
        //    getTxtSmtpHost().setText("");
        //    getTxtSmtpPuerto().setValue("");        
        //}
    }
    
    private void seleccionarDirectorioRecursos()
    {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int respuesta = fc.showOpenDialog(this);
        //Comprobar si se ha pulsado Aceptar
        if (respuesta == JFileChooser.APPROVE_OPTION) 
        {
            //Crear un objeto File con el archivo elegido
            File archivoElegido = fc.getSelectedFile().getAbsoluteFile();
            getTxtDirectorioRecurso().setText(archivoElegido.getAbsolutePath());
            //Mostrar el nombre del archvivo en un campo de texto
            //if()
        }
        
        
    }

}
