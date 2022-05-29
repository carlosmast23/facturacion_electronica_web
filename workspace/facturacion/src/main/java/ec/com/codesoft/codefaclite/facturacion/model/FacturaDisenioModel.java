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
import ec.com.codesoft.codefaclite.controlador.core.swing.ReporteCodefac;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.DrawCanvas;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.DrawComponente;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.DrawDocumento;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.DrawSeccion;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.LienzoDisenador;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.ManagerReporteFacturaFisica;
import ec.com.codesoft.codefaclite.facturacion.model.disenador.RepaintInterface;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturaDisenioPanel;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturaDisenoPanel;
import ec.com.codesoft.codefaclite.facturacion.reportdata.DetalleFacturaFisicaData;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.BandaComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComponenteComprobanteFisico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteFisicoDisenio;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteFisicoDisenioServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Carlos
 */
public class FacturaDisenioModel extends FacturaDisenoPanel implements RepaintInterface {

    private FacturaDisenioModel facturaDisenioModel;
    private DrawCanvas canvas;
    private List<DrawComponente> componentesDraw;
    
    /**
     * Guarda la referencia al ultimo componente seleccionado por el mouse
     */
    private DrawComponente componenteSeleccionadoMouse;
    
    /**
     * Variable para saber si se esta dando click sobre un componente para mover de posicion
     */
    private DrawComponente mouseSeleccionadoComponente;
    
    int desfazMouseX=0;
    int desfazMouseY=0; 

    public FacturaDisenioModel() {
        this.repaintInterface = this;
        cargarDatos();
        facturaDisenioModel = this;

        cargarComboSeccion((ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem());
        cargarComboComponentes((BandaComprobante) getCmbSeccion().getSelectedItem());

        agregarListener();
        agregarListenerCamposTexto();
        cargarDatosSeleccion();
        
        this.mouseSeleccionadoComponente=null;

    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        //validarPermisosIniciales();
        
        cargarDocumentoGrafico((ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem());
        seleccionarComponenteActual();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
        try {
            ComprobanteFisicoDisenioServiceIf servicio =ServiceFactory.getFactory().getComprobanteFisicoDisenioServiceIf();
            ComprobanteFisicoDisenio comprobante= (ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem();
            servicio.editar(comprobante);
            DialogoCodefac.mensaje("Correcto","Los datos fueron grabados correctamente",DialogoCodefac.MENSAJE_CORRECTO);
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaDisenioModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioCodefacException ex) {
            Logger.getLogger(FacturaDisenioModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        ComprobanteFisicoDisenio comprobanteDiseño= (ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem();
        InputStream reporteOriginal =null;
        if(comprobanteDiseño.getNombre().equals("Nota de Venta"))        
        {
            reporteOriginal = RecursoCodefac.JASPER_COMPROBANTES_FISICOS.getResourceInputStream("nota_venta.jrxml");
        }else if(comprobanteDiseño.getNombre().equals("Factura"))        
        {
            reporteOriginal = RecursoCodefac.JASPER_COMPROBANTES_FISICOS.getResourceInputStream("factura_fisica.jrxml");
        }
        //InputStream reporteOriginal = RecursoCodefac.JASPER_COMPROBANTES_FISICOS.getResourceInputStream("factura_fisica.jrxml");
        ManagerReporteFacturaFisica manager = new ManagerReporteFacturaFisica(reporteOriginal);
        ComprobanteFisicoDisenio documento = (ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem();
        manager.setearNuevosValores(documento);
        InputStream reporteNuevo = manager.generarNuevoDocumento();

        //String xmlStr= UtilidadesTextos.getStringFromInputStream(reporteNuevo);
        //System.out.println(xmlStr);
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("fechaEmision", "12/01/2017");
        parametros.put("razonSocial", "Nombre Apellido");
        parametros.put("direccion", "Quito");
        parametros.put("telefono", "022339281");
        parametros.put("correoElectronico", "ejemplo@hotmail.es");
        parametros.put("identificacion", "1212121212");

        parametros.put("subtotalImpuesto", "100");
        parametros.put("subtotalSinImpuesto", "0");
        parametros.put("descuento", "0");
        parametros.put("subtotalConDescuento", "100");
        parametros.put("valorIva", "10");
        parametros.put("total", "100");
        parametros.put("iva", "12");



        List<DetalleFacturaFisicaData> detalles = new ArrayList<DetalleFacturaFisicaData>();
        DetalleFacturaFisicaData detalle = new DetalleFacturaFisicaData();
        detalle.setCantidad("1");
        detalle.setDescripcion("MOUSE OPTICO");
        detalle.setValorTotal("12");
        detalle.setValorUnitario("12");
        detalles.add(detalle);
        
        detalle = new DetalleFacturaFisicaData();
        detalle.setCantidad("2");
        detalle.setDescripcion("TECLADO");
        detalle.setValorTotal("10");
        detalle.setValorUnitario("10");
        detalles.add(detalle);

        try {
            ReporteCodefac.generarReporteInternalFrame(reporteNuevo, parametros, detalles, panelPadre, "Muestra Previa",null);
        } catch (ExcepcionCodefacLite ex) {
            Logger.getLogger(FacturaDisenioModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
        }

    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite {
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
        return "Diseño Comprobantes";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_IMPRIMIR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void agregarListener() {

        getjPanel1().addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                //System.out.println("mouseDragged");
                if(mouseSeleccionadoComponente!=null)
                {
                    System.out.println("moviendo posicion");
                    int x=e.getX()-mouseSeleccionadoComponente.getDrawSeccion().getX();
                    int y=e.getY()-mouseSeleccionadoComponente.getDrawSeccion().getY();                    
                    
                    int xFinal=(int) ((x-desfazMouseX)/canvas.getEscalaDecimales());
                    int yFinal=(int) ((y-desfazMouseY)/canvas.getEscalaDecimales());
                    mouseSeleccionadoComponente.getComponenteEntity().setX(xFinal);
                    mouseSeleccionadoComponente.getComponenteEntity().setY(yFinal);
                    getTxtX().setValue(xFinal);
                    getTxtY().setValue(yFinal);
                    getjPanel1().repaint();

                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //System.out.println(e.getX()+"-"+e.getY());
                
                DrawComponente componente=obtenerComponenteSeleccionado(e.getX(),e.getY());
                if(componente!=null)
                {
                    if(componenteSeleccionadoMouse!=null)
                        componenteSeleccionadoMouse.setSeleccionadoMouse(false);
                    
                    componenteSeleccionadoMouse=componente;                    
                    componente.setSeleccionadoMouse(true);
                    getjPanel1().repaint();
                }
                else
                {
                    //seleccionarComponenteMouse(false);
                    if (componenteSeleccionadoMouse != null) {
                        componenteSeleccionadoMouse.setSeleccionadoMouse(false);
                    }
                    getjPanel1().repaint();
                }
                
                
            }
        });
        
        getjPanel1().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("clicked");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
                DrawComponente componente=obtenerComponenteSeleccionado(e.getX(),e.getY());
                if(componente!=null)
                {                    
                    desfazMouseX = e.getX() - componente.getX();
                    desfazMouseY = e.getY() - componente.getY();
                    mouseSeleccionadoComponente=componente;
                    seleccionarComponenteMouse();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                if(mouseSeleccionadoComponente!=null)
                {
                    mouseSeleccionadoComponente=null;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                //cargarDocumentoGrafico((ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem());
                getjPanel1().revalidate();
                getjPanel1().repaint();
            }

        });

        getCmbDocumento().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getCmbDocumento().getSelectedIndex() >= 0) {
                    cargarComboSeccion((ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem());
                    cargarDocumentoGrafico((ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem());
                    cargarDatosSeleccion();
                    getjPanel1().repaint();
                }
            }
        });

        getCmbSeccion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                BandaComprobante banda=(BandaComprobante) getCmbSeccion().getSelectedItem();
                if(banda!=null)
                {
                    cargarComboComponentes(banda);
                    cargarDatosSeleccion();
                }
            }
        });

        getCmbComponente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarDatosSeleccion();
                seleccionarComponenteActual();

            }
        });

        getBtnDown().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                componente.setY(componente.getY() + 5);
                getjPanel1().repaint();
                cargarDatosSeleccion();
            }
        });

        getBtnArriba().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                componente.setY(componente.getY() - 5);
                getjPanel1().repaint();
                cargarDatosSeleccion();
            }
        });

        getBtnDerecha().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                componente.setX(componente.getX() + 5);
                getjPanel1().repaint();
                cargarDatosSeleccion();
            }
        });

        getBtnIzquierda().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                componente.setX(componente.getX() - 5);
                getjPanel1().repaint();
                cargarDatosSeleccion();

            }
        });
    }
    
    /**
     * Metodo que me permite establecer en el panel de control solo el elemento seleccionado
     */
    private void seleccionarComponenteMouse()
    {
        BandaComprobante bandaComprobante = mouseSeleccionadoComponente.getDrawSeccion().getSeccionEntity();
        getCmbSeccion().setSelectedItem(bandaComprobante);
        getCmbComponente().setSelectedItem(mouseSeleccionadoComponente.getComponenteEntity());
    }
    /**
     * Obtiene uno de los componentes seleccionados si esta en la posicion seleccionado por el mouse
     * @param x
     * @param y
     * @return 
     */
    private DrawComponente obtenerComponenteSeleccionado(int x, int y) 
    {
        for (DrawComponente drawComponente : componentesDraw) {
            int x1=drawComponente.getX();
            int y1=drawComponente.getY();
            int ancho=drawComponente.getWidth();
            int alto=drawComponente.getHeight();
            if((x>=x1 && x<=(x1+ancho)) && (y>=y1 && y<=(y1+alto)))
            {
                return drawComponente;
            }
        }        
        return null;
    }
    
    private void seleccionarComponenteMouse(boolean opcion) {
        //Quitar la seleccion de todos los componentes
        for (DrawComponente drawComponente : componentesDraw) {
            drawComponente.setSeleccionadoMouse(false);
        }
    }

    private void seleccionarComponenteActual() {
        //Quitar la seleccion de todos los componentes
        quitarSeleccionComponentes();

        //Cargar el componente Grafico
        if (getCmbComponente().getSelectedIndex() >= 0) {
            ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
            DrawComponente draw = buscarComponente(componente);
            if(draw!=null)
            {
                draw.setSeleccionado(true);
                getjPanel1().repaint();
            }
        }
    }

    private void cargarDatosSeleccion() {
        if (getCmbDocumento().getSelectedIndex() >= 0) {
            ComprobanteFisicoDisenio documento = (ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem();
            getTxtAnchoDocumento().setValue(documento.getAncho());
            getTxtAltoDocumento().setValue(documento.getAlto());
        }

        if (getCmbSeccion().getSelectedIndex() >= 0) {
            BandaComprobante banda = (BandaComprobante) getCmbSeccion().getSelectedItem();
            getTxtAltoSeccion().setValue(banda.getAlto());
        }

        if (getCmbComponente().getSelectedIndex() >= 0) {
            ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
            getTxtX().setValue(componente.getX());
            getTxtY().setValue(componente.getY());
            getTxtAnchoComponente().setValue(componente.getAncho());
            getTxtAltoComponente().setValue(componente.getAlto());
            getCmbTamanioLetra().setValue(componente.getTamanioLetra());

            if (componente.getNegrita().equals("s")) {
                getChkNegrita().setSelected(true);
            } else {
                getChkNegrita().setSelected(false);
            }
            
            if (componente.getOculto().equals("s")) {
                getChkOculto().setSelected(true);
            } else {
                getChkOculto().setSelected(false);
            }

        }

    }

    /**
     * Quita la seccion de todos los componentes
     *
     * @param componente
     * @return
     */
    private void quitarSeleccionComponentes() {
        for (DrawComponente drawComponente : componentesDraw) {
            drawComponente.setSeleccionado(false);
        }
    }

    private DrawComponente buscarComponente(ComponenteComprobanteFisico componente) {
        for (DrawComponente drawComponente : componentesDraw) {
            if (drawComponente.getComponenteEntity().equals(componente)) {
                return drawComponente;
            }
        }
        return null;
    }

    private void cargarComboComponentes(BandaComprobante banda) {
        getCmbComponente().removeAllItems();
        for (ComponenteComprobanteFisico componente : banda.getComponentes()) {
            getCmbComponente().addItem(componente);
        }
    }

    private void cargarComboSeccion(ComprobanteFisicoDisenio documento) {
        getCmbSeccion().removeAllItems();
        for (BandaComprobante bandaComprobante : documento.getSecciones()) {
            getCmbSeccion().addItem(bandaComprobante);
        }
    }

    /**
     * Carga el documento seleccionado en el panel grafico
     *
     * @param documento
     */
    private void cargarDocumentoGrafico(ComprobanteFisicoDisenio documento) {
        DrawDocumento drawDocumento = new DrawDocumento(documento);
        //Cargar el componente Grafico
        this.componentesDraw = new ArrayList<DrawComponente>();
        for (BandaComprobante seccion : documento.getSecciones()) {
            DrawSeccion drawSeccion = new DrawSeccion(seccion);

            for (ComponenteComprobanteFisico componente : seccion.getComponentes()) {
                DrawComponente drawComponente = new DrawComponente(componente,drawSeccion);
                drawSeccion.agregarComponente(drawComponente);
                componentesDraw.add(drawComponente);
            }
            drawDocumento.agregarSeccion(drawSeccion);
        }
        this.canvas = new DrawCanvas(drawDocumento);

    }

    /*
    private void iniciarLienzo() {

        this.canvas=new DrawCanvas(null);
        SpringLayout layout = new SpringLayout();
        LienzoDisenador lienzo = new LienzoDisenador(this.canvas);
        lienzo.setLayout(layout);
        getjScrollPane1().setViewportView(lienzo);
    }*/
    private void cargarDatos() {
        try {
            getCmbDocumento().removeAllItems();
            ComprobanteFisicoDisenioServiceIf servicio = ServiceFactory.getFactory().getComprobanteFisicoDisenioServiceIf();
            List<ComprobanteFisicoDisenio> documentos = servicio.obtenerTodos();
            for (ComprobanteFisicoDisenio documento : documentos) {
                //Esto sirve para desasociar la entidad y que no se reflejen los cambios directamente con la base de datos
                //ServiceAbstract.desasociarEntidadRecursivo(documento);
                getCmbDocumento().addItem(documento);
            }
            //System.exit(0);
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaDisenioModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void repaint(Graphics g) {
        Dimension dimension = getjPanel1().getSize();
        canvas.dibujar(g, dimension);
    }

    private void agregarListenerCamposTexto() {

        getTxtAnchoDocumento().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ComprobanteFisicoDisenio compobante = (ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem();
                int numero = Integer.parseInt(getTxtAnchoDocumento().getValue().toString());
                compobante.setAncho(numero);
                getjPanel1().repaint();
            }
        });

        getTxtAltoDocumento().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ComprobanteFisicoDisenio compobante = (ComprobanteFisicoDisenio) getCmbDocumento().getSelectedItem();
                int numero = Integer.parseInt(getTxtAltoDocumento().getValue().toString());
                compobante.setAlto(numero);
                getjPanel1().repaint();
            }
        });

        getTxtX().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                int numero = Integer.parseInt(getTxtX().getValue().toString());
                componente.setX(numero);
                getjPanel1().repaint();

            }
        });

        getTxtY().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                int numero = Integer.parseInt(getTxtY().getValue().toString());
                componente.setY(numero);
                getjPanel1().repaint();
            }
        });

        getTxtAnchoComponente().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                int numero = Integer.parseInt(getTxtAnchoComponente().getValue().toString());
                componente.setAncho(numero);
                getjPanel1().repaint();
            }
        });

        getTxtAltoComponente().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                int numero = Integer.parseInt(getTxtAltoComponente().getValue().toString());
                componente.setAlto(numero);
                getjPanel1().repaint();
            }
        });

        getTxtAltoSeccion().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (getCmbSeccion().getSelectedIndex() >= 0) {
                    BandaComprobante banda = (BandaComprobante) getCmbSeccion().getSelectedItem();
                    banda.setAlto(Integer.parseInt(getTxtAltoSeccion().getValue().toString()));
                    getjPanel1().repaint();
                }
            }
        });

        getCmbTamanioLetra().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                int tamanio = Integer.parseInt(getCmbTamanioLetra().getValue().toString());
                componente.setTamanioLetra(tamanio);
                getjPanel1().repaint();
            }
        });

        getChkNegrita().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                if (getChkNegrita().isSelected()) {
                    componente.setNegrita("s");
                } else {
                    componente.setNegrita("n");
                }
                getjPanel1().repaint();
            }
        });
        
        getChkOculto().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 ComponenteComprobanteFisico componente = (ComponenteComprobanteFisico) getCmbComponente().getSelectedItem();
                if (getChkOculto().isSelected()) {
                    componente.setOculto("s");
                } else {
                    componente.setOculto("n");
                }
                getjPanel1().repaint();
            }
        });
        
        getSliderZoom().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                canvas.setZoom(getSliderZoom().getValue());
                getjPanel1().repaint();
            }
        });
    }

    //TODO: Ya no puedo hacer la validacion con una variable general por que puedo tener los 2 modos funcionado al mismo tiempo
    /*private void validarPermisosIniciales() throws ExcepcionCodefacLite{
        if (!session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).valor.equals(ComprobanteEntity.TipoEmisionEnum.NORMAL.getLetra())) {
            DialogoCodefac.mensaje("Acceso no pemitido","Esta ventana solo esta disponible en modo Factuación manual",DialogoCodefac.MENSAJE_ADVERTENCIA);
            dispose();
            throw new ExcepcionCodefacLite("No cumple validacion inicial");

        }
    }*/

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
