/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.facturacionelectronica.jaxb.ComprobanteElectronico;
import ec.com.codesoft.codefaclite.main.panel.WidgetNotificacionesCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.common.AlertaResponse;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.common.AlertaResponse.TipoAdvertenciaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModoProcesarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ComprobanteServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.tabla.UtilidadesTablas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carlos
 */
public class WidgetNotificacionCodefacModelo extends WidgetNotificacionesCodefac{

    private final static String TITULO_PAGINA="Notificaciones Codefac";
    private Empresa empresa;
    public WidgetNotificacionCodefacModelo(JDesktopPane parentPanel,Empresa empresa) {
        super(parentPanel); 
        getLblNotificaciones().setText(TITULO_PAGINA);
        this.empresa=empresa;
        listenerBotones();
        actualizarNotificaciones(ModoProcesarEnum.NORMAL);
        
    }

    
    @Override
    public JPanel getPanelMovimiento() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
   
    public void actualizarNotificaciones(ModoProcesarEnum modoEnum) {
        //Eejcutar proceso de notrificaciones en segundo plano
        limpiarDatos();
        getLblNotificaciones().setText(TITULO_PAGINA+ " [ Cargando ... ]");
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Logger.getLogger(WidgetNotificacionCodefacModelo.class.getName()).log(Level.INFO,"Actualizando Notificaciones en el servidor");
                    //ServiceFactory.getFactory().getAlertaServiceIf().procesoBloqueado(empresa);
                    //ServiceFactory.getFactory().getParametroCodefacServiceIf().procesoBloqueadoPrueba();
                    
                    
                    List<AlertaResponse> alertas = ServiceFactory.getFactory().getAlertaServiceIf().actualizarNotificacionesCargaRapida(empresa);
                    String[] tituloTabla = {"Tipo", "Problema", "Solución"};
                    
                    UtilidadesTablas.LlenarDatoTablaIf interfazLlenarDatos=new UtilidadesTablas.LlenarDatoTablaIf<AlertaResponse>() {
                        @Override
                        public void agregarDato(List<Object> fila, AlertaResponse dato) {
                            fila.add(dato.tipoAdvertenciaEnum.toString());
                            fila.add(dato.descripcion);
                            fila.add(dato.solucion);
                        }
                    };
                    
                    //Cargar datos rapidos
                    UtilidadesTablas.llenarTablasDatos(
                            getTblNotificaciones(),
                            tituloTabla,
                            alertas,
                            interfazLlenarDatos
                    );
                    
                    definirFormatoTabla();
                    
                    //cargar datos lentos
                    List<AlertaResponse> alertasLentas = ServiceFactory.getFactory().getAlertaServiceIf().actualizarNotificacionesCargaLenta(empresa,modoEnum);
                    UtilidadesTablas.llenarTablasDatos(
                            getTblNotificaciones(),
                            alertasLentas, 
                            interfazLlenarDatos);

                    //DefaultTableModel modeloTabla=new DefaultTableModel(tituloTabla,0);
                    //Agregando notificacions de los comprobantes en el caso de que exista            
                    //Agregando notificacion de la firma con 1 mes de anticipación si esta próximo a cumplirse la fecha tope
                    //Metodo que permite visualizar o no el cuadro de notificaciones en el caso de que si exista
                    if (getTblNotificaciones().getRowCount() == 0) {
                        setVisible(false);
                    } else {
                        setVisible(true);
                    }
                    //getTblNotificaciones().setModel(modeloTabla);
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(WidgetNotificacionCodefacModelo.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(WidgetNotificacionCodefacModelo.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Pongo nuevamente el titulo original
                getLblNotificaciones().setText(TITULO_PAGINA);

            }
        }).start();

    }
    
    
    
    /**
     * Metodo que se encarga de definir el tamañoa de las columnas y adicional el color de cada fila de la notificación
     */
    private void definirFormatoTabla()
    {
        //UtilidadesTablas.cambiarTamanioColumnas(getTblNotificaciones(),new Integer[]{50,200,100});
        UtilidadesTablas.definirTamanioColumnas(getTblNotificaciones(), new Integer[]{80, 280, 100});

        getTblNotificaciones().setDefaultRenderer(getTblNotificaciones().getColumnClass(0), new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                //System.out.println(row+":"+column);
                if (column == 0) {

                    //Si por algun motivo no existe ningun dato pinto de otro color para ver el eror
                    if (value == null) {
                        setBackground(Color.green);
                    } else {

                        if (value.toString().equals(TipoAdvertenciaEnum.ADVERTENCIA.toString())) {
                            setBackground(Color.orange);
                        } else if (value.toString().equals(TipoAdvertenciaEnum.GRAVE.toString())) {
                            setBackground(Color.red);
                        } else {
                            setBackground(Color.white);
                        }
                    }
                } else {
                    setBackground(Color.white);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
            }

        });
    }

    private void listenerBotones() {
        getBtnActualizarNotificaciones().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarNotificaciones(ModoProcesarEnum.FORZADO);
            }
        });
    }
    
    private void limpiarDatos() 
    {
        getTblNotificaciones().setModel(new DefaultTableModel());
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    
    
}
