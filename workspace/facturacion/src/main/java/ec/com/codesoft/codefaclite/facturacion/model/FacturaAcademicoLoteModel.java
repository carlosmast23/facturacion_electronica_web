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
import ec.com.codesoft.codefaclite.facturacion.callback.ClienteFacturaImplComprobante;
import ec.com.codesoft.codefaclite.facturacion.callback.ClienteFacturaLoteImplComprobante;
import ec.com.codesoft.codefaclite.facturacion.panel.FacturaAcademicoLotePanel;
import ec.com.codesoft.codefaclite.facturacionelectronica.evento.ListenerComprobanteElectronicoLote;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobante;
import ec.com.codesoft.codefaclite.servidorinterfaz.callback.ClienteInterfaceComprobanteLote;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataFactura;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.ComprobanteDataInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ComprobanteEntity;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Factura;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaAdicional;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.FacturaDetalle;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.EstudianteInscrito;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.NivelAcademico;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Periodo;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubroEstudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.RubrosNivel;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DatosAdicionalesComprobanteEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.DocumentoEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoDocumentoEnum;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.eclipse.persistence.sessions.factories.SessionFactory;

/**
 *
 * @author
 */
public class FacturaAcademicoLoteModel extends FacturaAcademicoLotePanel {

    /**
     * Map que permite almacenar todos los datos por curso y luego por
     * estudiante y rubros a facturar
     */
    private Map<NivelAcademico, Map<EstudianteInscrito, List<RubroEstudiante>>> mapDatosFacturar;

    /**
     * Informacion del map por defecto cargado
     */
    private Map<EstudianteInscrito, List<RubroEstudiante>> mapEstudianteRubros;

    private FacturaAcademicoLoteModel instancia = this;

    @Override
    public void iniciar() throws ExcepcionCodefacLite {
        cargarValoresIniciales();
        listenerCombos();
        listenerBotones();
        listenerListas();
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        mapDatosFacturar = new HashMap<NivelAcademico, Map<EstudianteInscrito, List<RubroEstudiante>>>();
        mapEstudianteRubros = new HashMap<EstudianteInscrito, List<RubroEstudiante>>();
    }

//    @Override
    public String getNombre() {
        return "Factura Académica por Lote";
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
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void cargarValoresIniciales() {
        //cargar los periodos actuales
        try {
            List<Periodo> periodos = ServiceFactory.getFactory().getPeriodoServiceIf().obtenerPeriodoActivo();
            for (Periodo periodo : periodos) {
                getCmbPeriodo().addItem(periodo);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void listenerCombos() {
        getCmbPeriodo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Periodo periodoSeleccionado = (Periodo) getCmbPeriodo().getSelectedItem();
                    //Map<String, Object> mapParametros = new HashMap<String, Object>();
                    //mapParametros.put("periodo", periodoSeleccionado);
                    List<NivelAcademico> niveles = ServiceFactory.getFactory().getNivelAcademicoServiceIf().buscarPorPeriodo(periodoSeleccionado);

                    //Cargar todos los niveles disponibles para ese periodo activo
                    getCmbNivelAcademico().removeAllItems();
                    for (NivelAcademico nivel : niveles) {
                        getCmbNivelAcademico().addItem(nivel);
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        getCmbNivelAcademico().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    getCmbRubrosNivel().removeAllItems();
                    NivelAcademico nivelSeleccionado = (NivelAcademico) getCmbNivelAcademico().getSelectedItem();
                    //Map<String, Object> mapParametros = new HashMap<String, Object>();
                    //Cargar rubros generales para todos los niveles
                    //mapParametros.put("nivel", null);
                    //mapParametros.put("periodo", nivelSeleccionado.getPeriodo());
                    List<RubrosNivel> rubrosSinNivel = ServiceFactory.getFactory().getRubrosNivelServiceIf().buscarPorPeriodoYNivel(nivelSeleccionado.getPeriodo(),null);
                    for (RubrosNivel rubrosNivel : rubrosSinNivel) {
                        getCmbRubrosNivel().addItem(rubrosNivel);
                    }

                    //Cargar rubros exclusivos de los niveles actuales
                    //mapParametros.clear();
                    //mapParametros.put("nivel", nivelSeleccionado.getNivel());
                    //mapParametros.put("periodo", nivelSeleccionado.getPeriodo());
                    List<RubrosNivel> rubros = ServiceFactory.getFactory().getRubrosNivelServiceIf().buscarPorPeriodoYNivel(nivelSeleccionado.getPeriodo(),nivelSeleccionado.getNivel());

                    //Agregar todos los rubros disponibles para el nivels
                    for (RubrosNivel rubro : rubros) {
                        getCmbRubrosNivel().addItem(rubro);
                    }

                    //Agregar los estudiantes del nivel a la tabla
                    cargarEstudiantesNuevos();
                    cargarTabla();

                } catch (RemoteException ex) {
                    Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        getCmbRubrosNivel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //cargarEstudiantesNuevos();
                //cargarTabla();

            }
        });

    }

    private void cargarEstudiantesNuevos() {
        mapEstudianteRubros = new HashMap<EstudianteInscrito, List<RubroEstudiante>>();

        NivelAcademico nivelAcademico = (NivelAcademico) getCmbNivelAcademico().getSelectedItem();
        Periodo periodo=(Periodo) getCmbPeriodo().getSelectedItem();
        //RubrosNivel rubroNivel = (RubrosNivel) getCmbRubrosNivel().getSelectedItem();

        if (nivelAcademico != null) {
            try {
                //Map<String, Object> mapParametros = new HashMap<String, Object>();
                //mapParametros.put("nivelAcademico", nivelAcademico);
                List<EstudianteInscrito> estudiantesInscritos = ServiceFactory.getFactory().getEstudianteInscritoServiceIf().buscarPorNivelAcademico(periodo,nivelAcademico);

                for (EstudianteInscrito estudiantesInscrito : estudiantesInscritos) {
                    mapEstudianteRubros.put(estudiantesInscrito, new ArrayList<RubroEstudiante>());
                }
            } catch (RemoteException ex) {
                Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void cargarTabla() {

        DefaultTableModel modeloTabla = crearModeloTabla(crearTituloTabla(), crearColumnasTabla());

        for (Map.Entry<EstudianteInscrito, List<RubroEstudiante>> entry : mapEstudianteRubros.entrySet()) {
            EstudianteInscrito estudianteInscrito = entry.getKey();
            List<RubroEstudiante> listaRubrosEstudiante = entry.getValue();

            Vector<Object> datos = new Vector<Object>();
            datos.add(true);
            datos.add(estudianteInscrito);

            ListModel<RubrosNivel> rubrosList = getLstRubrosFacturar().getModel();

            for (int i = 0; i < rubrosList.getSize(); i++) {
                boolean rubroEncontrado = false;
                for (RubroEstudiante rubroEstudiante : listaRubrosEstudiante) {
                    if (rubroEstudiante.getRubroNivel().equals(rubrosList.getElementAt(i))) {
                        rubroEncontrado = true;
                        break;
                    }
                }

                if (rubroEncontrado) {
                    datos.add(true);
                } else {
                    datos.add(false);
                }

            }

            modeloTabla.addRow(datos);

        }

        //Setear los datos creados en la tabla
        getTblEstudiantesFacturar().setModel(modeloTabla);

    }

    private Class[] crearColumnasTabla() {
        Vector<Class> columnas = new Vector<Class>();
        columnas.add(Boolean.class);
        columnas.add(EstudianteInscrito.class);

        ListModel<RubrosNivel> rubrosList = getLstRubrosFacturar().getModel();

        for (int i = 0; i < rubrosList.getSize(); i++) {
            columnas.add(Boolean.class);
        }

        return columnas.toArray(new Class[columnas.size()]);

    }

    private String[] crearTituloTabla() {
        Vector<String> titulo = new Vector<String>();
        titulo.add("Opcion");
        titulo.add("Alumno");

        ListModel<RubrosNivel> rubrosList = getLstRubrosFacturar().getModel();

        for (int i = 0; i < rubrosList.getSize(); i++) {
            titulo.add(rubrosList.getElementAt(i).getNombre());
        }

        return titulo.toArray(new String[titulo.size()]);

    }

    private DefaultTableModel crearModeloTabla(String titulos[], Class[] tipoDatoFilas) {
        DefaultTableModel defaultTableModel = new javax.swing.table.DefaultTableModel(titulos, 0) {
            public Class getColumnClass(int columnIndex) {
                return tipoDatoFilas[columnIndex];
            }
        };
        return defaultTableModel;
    }

    private void listenerBotones() {
        getBtnAgregarRubrosNivel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RubrosNivel rubrosNivel = (RubrosNivel) getCmbRubrosNivel().getSelectedItem();
                agregarRubroLista(rubrosNivel);
                cargarRubrosLista();
                cargarTabla();
            }
        });

        getBtnAgregarCurso().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NivelAcademico nivelAcademico = (NivelAcademico) getCmbNivelAcademico().getSelectedItem();

                if (mapEstudianteRubros != null) {
                    Map<EstudianteInscrito, List<RubroEstudiante>> mapTemporal = mapDatosFacturar.get(nivelAcademico);
                    if (mapTemporal == null) {
                        mapDatosFacturar.put(nivelAcademico, mapEstudianteRubros);
                    }
                }
                cargarCursosLista();
            }
        });

        getBtnFacturar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<ComprobanteDataInterface> comprobantes = facturarLote();
                    grabarFacturas(comprobantes);
                    DialogoCodefac.mensaje("Correcto", "Las facturas se están autorizando", DialogoCodefac.MENSAJE_CORRECTO);

                    ClienteInterfaceComprobanteLote cic = new ClienteFacturaLoteImplComprobante(instancia);

                    //TODO: Terminar de autorizar pero con un metodo valido para autorizar en lote
                    //ServiceFactory.getFactory().getComprobanteServiceIf().procesarComprobanteLote(comprobantes, session.getUsuario(),session.getEmpresa().getIdentificacion(),cic);

                } catch (RemoteException ex) {
                    Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    private void grabarFacturas(List<ComprobanteDataInterface> comprobantes) {
        for (ComprobanteDataInterface comprobante : comprobantes) {
            try {
                ComprobanteDataFactura comprobanteFactura = (ComprobanteDataFactura) comprobante;
                Factura factura = comprobanteFactura.getFactura();
                factura = ServiceFactory.getFactory().getFacturacionServiceIf().grabar(factura);
                comprobanteFactura.setFactura(factura);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RemoteException ex) {
                Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Obtiene todos los detalles de las facturas para facturar
     *
     * @return
     */
    private List<ComprobanteDataInterface> facturarLote() {
        //mapDatosFacturar;
        List<ComprobanteDataInterface> comprobantesLista = new ArrayList<ComprobanteDataInterface>();

        for (Map.Entry<NivelAcademico, Map<EstudianteInscrito, List<RubroEstudiante>>> entry : mapDatosFacturar.entrySet()) {
            NivelAcademico nivelAcademico = entry.getKey();
            Map<EstudianteInscrito, List<RubroEstudiante>> rubrosEstudianteMap = entry.getValue();

            for (Map.Entry<EstudianteInscrito, List<RubroEstudiante>> entry1 : rubrosEstudianteMap.entrySet()) {
                EstudianteInscrito estudianteInscrito = entry1.getKey();
                List<RubroEstudiante> rubrosEstudiantes = entry1.getValue();

                ///Generar los datos de la factura
                Factura factura = generarFactura(estudianteInscrito, rubrosEstudiantes);

                //Datos adicionales en la factura
                factura.addDatoAdicional(new FacturaAdicional(estudianteInscrito.getEstudiante().getRepresentante().getCorreoElectronico(),ComprobanteAdicional.Tipo.TIPO_CORREO,ComprobanteAdicional.CampoDefectoEnum.CORREO));
                factura.addDatoAdicional(new FacturaAdicional(DatosAdicionalesComprobanteEnum.NOMBRE_ESTUDIANTE.getNombre(), estudianteInscrito.getEstudiante().getNombreCompleto(),ComprobanteAdicional.Tipo.TIPO_OTRO));
                factura.addDatoAdicional(new FacturaAdicional(DatosAdicionalesComprobanteEnum.CODIGO_ESTUDIANTE.getNombre(), estudianteInscrito.getEstudiante().getIdEstudiante().toString(),ComprobanteAdicional.Tipo.TIPO_OTRO));

                ComprobanteDataFactura comprobanteData = new ComprobanteDataFactura(factura);
                comprobanteData.setMapInfoAdicional(getMapAdicional(factura));
                comprobantesLista.add(comprobanteData);

            }

        }
        return comprobantesLista;
    }

    private Map<String, String> getMapAdicional(Factura factura) {
        Map<String, String> parametroMap = new HashMap<String, String>();
        for (FacturaAdicional datoAdicional : factura.getDatosAdicionales()) {
            parametroMap.put(datoAdicional.getCampo(), datoAdicional.getValor());
        }
        return parametroMap;
    }

    private Factura generarFactura(EstudianteInscrito estudianteInscrito, List<RubroEstudiante> listaRubros) {
        Factura factura = new Factura();
        //factura.setClaveAcceso(title);
        factura.setCliente(estudianteInscrito.getEstudiante().getRepresentante());
        factura.setCodigoDocumento(DocumentoEnum.FACTURA.getCodigo());
        factura.setDescuentoImpuestos(BigDecimal.ZERO);
        factura.setDescuentoSinImpuestos(BigDecimal.ONE);
        factura.setDireccion(estudianteInscrito.getEstudiante().getRepresentante().getEstablecimientos().get(0).getDireccion());
        factura.setEmpresa(session.getEmpresa());
        //factura.setEstado(ComprobanteEntity.ComprobanteEnumEstado.SIN_AUTORIZAR.getEstado());
        factura.setFechaCreacion(UtilidadesFecha.castDateToTimeStamp(UtilidadesFecha.getFechaHoy()));
        factura.setFechaEmision(UtilidadesFecha.getFechaHoy());
        factura.setIdentificacion(estudianteInscrito.getEstudiante().getRepresentante().getIdentificacion());

        //TODO: Revisar esta parte para ver la facturacion en lote
        /*
        factura.setPuntoEmision(session.getParametrosCodefac().get(ParametroCodefac.PUNTO_EMISION).valor);
        factura.setPuntoEstablecimiento(session.getParametrosCodefac().get(ParametroCodefac.ESTABLECIMIENTO).valor);

        //Cuando la facturacion es electronica
        if (session.getParametrosCodefac().get(ParametroCodefac.TIPO_FACTURACION).getValor().equals(ComprobanteEntity.TipoEmisionEnum.ELECTRONICA.getLetra())) {
            factura.setSecuencial(Integer.parseInt(session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_FACTURA).valor));
        } else //cuando la facturacion es normal
        {
            factura.setSecuencial(Integer.parseInt(session.getParametrosCodefac().get(ParametroCodefac.SECUENCIAL_FACTURA_FISICA).valor));
        }
        */
        //FIN COMENTADO PARA REVISAR

        factura.setRazonSocial(session.getEmpresa().getRazonSocial());
        factura.setTelefono(estudianteInscrito.getEstudiante().getRepresentante().getEstablecimientos().get(0).getTelefonoConvencional());
        agregarDetallesFactura(factura, listaRubros);

        calcularTotalesFactura(factura);
        return factura;
    }

    private void agregarDetallesFactura(Factura factura, List<RubroEstudiante> listaRubros) {
        for (RubroEstudiante rubro : listaRubros) {
            FacturaDetalle facturaDetalle = new FacturaDetalle();
            facturaDetalle.setCantidad(BigDecimal.ONE);
            facturaDetalle.setDescripcion(rubro.getRubroNivel().getNombre());
            facturaDetalle.setDescuento(BigDecimal.ZERO);

            facturaDetalle.setPrecioUnitario(rubro.getValor());

            facturaDetalle.setReferenciaId(rubro.getId());
            //facturaDetalle.setCatalogoProducto(rubro.get);
            facturaDetalle.setTipoDocumento(TipoDocumentoEnum.ACADEMICO.getCodigo());
            facturaDetalle.setTotal(facturaDetalle.getCantidad().multiply(facturaDetalle.getPrecioUnitario()));
            facturaDetalle.setValorIce(BigDecimal.ZERO);

            if (rubro.getRubroNivel().getCatalogoProducto().getIva().getTarifa().equals(0)) {
                facturaDetalle.setIva(BigDecimal.ZERO);
            } else {

                BigDecimal iva = facturaDetalle.getTotal().multiply(obtenerIvaDefault()).setScale(2, BigDecimal.ROUND_HALF_UP);
                facturaDetalle.setIva(iva);
            }

            //Agregar el detalle a la factura
            factura.addDetalle(facturaDetalle);

        }
    }

    /*
    Calcula los totales y subtotales de la facturas
     */
    private void calcularTotalesFactura(Factura factura) {
        BigDecimal subtotalSinImpuestos = BigDecimal.ZERO;
        BigDecimal subtotalConImpuestos = BigDecimal.ZERO;
        BigDecimal ivaTotal = BigDecimal.ZERO;

        for (FacturaDetalle facturaDetalle : factura.getDetalles()) {
            try {
                //TODO: Verificar si se puede optimizar para no hacer una segunda llamada a la base de datos para consultar la referencia del detalle de la factura
                RubroEstudiante rubroEstudiante = ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorId(facturaDetalle.getReferenciaId());
                if (rubroEstudiante.getRubroNivel().getCatalogoProducto().getIva().getTarifa().equals(0)) {
                    subtotalSinImpuestos = subtotalSinImpuestos.add(facturaDetalle.getTotal());
                } else {
                    subtotalConImpuestos = subtotalConImpuestos.add(facturaDetalle.getTotal());
                    ivaTotal = ivaTotal.add(facturaDetalle.getIva());
                }
            } catch (RemoteException ex) {
                Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Setear los subtotales
        factura.setSubtotalImpuestos(subtotalConImpuestos);
        factura.setSubtotalSinImpuestos(subtotalSinImpuestos);
        factura.setIva(ivaTotal);

        //Por defecto los descuentos van en 0 en esta pantalla
        factura.setDescuentoImpuestos(BigDecimal.ZERO);
        factura.setDescuentoSinImpuestos(BigDecimal.ZERO);

        //Calcular el total
        factura.setTotal(
                factura.getSubtotalImpuestos().subtract(factura.getDescuentoImpuestos()).
                        add(factura.getSubtotalSinImpuestos().subtract(factura.getDescuentoSinImpuestos())).
                        add(factura.getIva()));

        System.out.println(factura.getSubtotalImpuestos());
        System.out.println(factura.getDescuentoImpuestos());
        System.out.println(factura.getSubtotalSinImpuestos());
        System.out.println(factura.getDescuentoSinImpuestos());
        System.out.println(factura.getIva());

        System.out.println(factura.getTotal());

    }

    private BigDecimal obtenerIvaDefault() {
        String ivaStr = session.getParametrosCodefac().get(ParametroCodefac.IVA_DEFECTO).getValor();
        return new BigDecimal(ivaStr).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
    }

    private void cargarRubrosLista() {
        DefaultListModel<RubrosNivel> modelList = new DefaultListModel<RubrosNivel>();

        for (Map.Entry<EstudianteInscrito, List<RubroEstudiante>> entry1 : mapEstudianteRubros.entrySet()) {
            EstudianteInscrito estudianteInscrito = entry1.getKey();
            List<RubroEstudiante> lista = entry1.getValue();

            for (RubroEstudiante rubroEstudiante : lista) {
                //Agregar solo si no existe el rubro en la lista
                if (!modelList.contains(rubroEstudiante.getRubroNivel())) {
                    modelList.addElement(rubroEstudiante.getRubroNivel());
                }
            }

        }

        getLstRubrosFacturar().setModel(modelList);

    }

    private void cargarCursosLista() {
        DefaultListModel<NivelAcademico> modelList = new DefaultListModel<NivelAcademico>();
        for (Map.Entry<NivelAcademico, Map<EstudianteInscrito, List<RubroEstudiante>>> entry : mapDatosFacturar.entrySet()) {
            NivelAcademico nivelAcademico = entry.getKey();
            Map<EstudianteInscrito, List<RubroEstudiante>> datosCursos = entry.getValue();

            modelList.addElement(nivelAcademico);
        }
        getLstCursosFacturar().setModel(modelList);
    }

    private void agregarRubroLista(RubrosNivel rubroNivel) {

        for (Map.Entry<EstudianteInscrito, List<RubroEstudiante>> entry : mapEstudianteRubros.entrySet()) {
            try {
                EstudianteInscrito estudianteInscrito = entry.getKey();
                List<RubroEstudiante> rubrosLista = entry.getValue();

                //Map<String, Object> mapParametro = new HashMap<String, Object>();
                //mapParametro.put("estudianteInscrito", estudianteInscrito);
                //mapParametro.put("rubroNivel", rubroNivel);

                List<RubroEstudiante> rubrosEstudiante = ServiceFactory.getFactory().getRubroEstudianteServiceIf().buscarPorEstudianteInscritoYRubroNivel(estudianteInscrito,rubroNivel);

                if (rubrosEstudiante.size() > 0) {
                    RubroEstudiante rubroEstudiante = rubrosEstudiante.get(0);
                    rubrosLista.add(rubroEstudiante);
                }

            } catch (RemoteException ex) {
                Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServicioCodefacException ex) {
                Logger.getLogger(FacturaAcademicoLoteModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void listenerListas() {
        getLstCursosFacturar().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //int indiceSeleccionado=e.getFirstIndex();
                NivelAcademico nivelAcademico = getLstCursosFacturar().getSelectedValue();
                mapEstudianteRubros = mapDatosFacturar.get(nivelAcademico);
                cargarRubrosLista();
                cargarTabla();
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

}
