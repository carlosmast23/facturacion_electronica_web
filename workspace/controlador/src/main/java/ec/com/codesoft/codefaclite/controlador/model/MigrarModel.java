/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar;
import ec.com.codesoft.codefaclite.controlador.excel.entidades.ExcelMigrarEstudiantes;
import ec.com.codesoft.codefaclite.controlador.excel.entidades.ExcelMigrarProductos;
import ec.com.codesoft.codefaclite.controlador.panel.MigrarPanel;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.academico.Estudiante;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Carlos
 */
public abstract class MigrarModel extends MigrarPanel{

    private static final Logger LOG = Logger.getLogger(MigrarModel.class.getName());
    
    
    
    /**
     * Archivo donde se selecciona 
     */
    //private File archivoMigrar;
    private ExcelMigrar excelMigrar;
    
    //public abstract void construirDatosExcel(File archivo);
    public abstract ExcelMigrar.MigrarInterface getInterfaceMigrar();
    public abstract ExcelMigrar getExcelMigrar() ;
    public abstract InputStream getInputStreamExcel();
        
    
    //private ExcelMigrar excelMigrar;
    
    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        listenerBotonesInit();  
        setTitle(getTitulo());
        agregarOtrosComponentes();
    }
    
    private JFileChooser construirSelectorArchivos()
    {
        JFileChooser jFileChooser=new JFileChooser();
        jFileChooser.setDialogTitle("Seleccione la ubicación para descargar el archivo");
        jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        return jFileChooser;
        
    }
    
    public  String getTitulo()
    {
        return "Sin Titulo";
    }
    
    /**
     * Metodo para agregar mas componentes al inicio de la carga
     */
    public void agregarOtrosComponentes()
    {
    
    }
    
    private boolean copiarArchivo(File ubicacionArchivoCopiar)
    {
        OutputStream outputStream=null;
        try {
            InputStream excelInputStream=getInputStreamExcel();
            File archivoFinal=new File(ubicacionArchivoCopiar,"plantilla.xlsx");
            outputStream = new FileOutputStream(archivoFinal);
            UtilidadesArchivos.grabarInputStreamEnArchivo(excelInputStream, outputStream);
            return true;
            
        }catch(UnsupportedOperationException e){
          LOG.log(Level.SEVERE,e.getMessage());
          e.printStackTrace();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MigrarModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MigrarModel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                outputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(MigrarModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    private void listenerBotonesInit()
    {
        getBtnDescargarPlantilla().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputStream inputStream=RecursoCodefac.PLANTILLAS_EXCEL.getResourceInputStream("compras.xlsx");
                JFileChooser jFileChooser=construirSelectorArchivos();
                
                int seleccion = jFileChooser.showDialog(null, "Seleccionar");
                switch (seleccion) {
                    case JFileChooser.APPROVE_OPTION:
                        File ubicacionArchivoCopiar = jFileChooser.getSelectedFile();
                        if(copiarArchivo(ubicacionArchivoCopiar))
                        {
                            DialogoCodefac.mensaje("Correcto","El archivo fue descargado correctamente",DialogoCodefac.MENSAJE_CORRECTO);
                        }
                        else
                        {
                            DialogoCodefac.mensaje("Error","No se puede descargar el archivo ",DialogoCodefac.MENSAJE_INCORRECTO);
                        }
                        break;
                    case JFileChooser.CANCEL_OPTION:

                        break;
                    case JFileChooser.ERROR_OPTION:

                        break;
                }
            }
        });
        
        getBtnRecargarDatos().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getTxtRutaArchivo().getText().isEmpty())
                {
                    DialogoCodefac.mensaje("Error","No se puede recargar porque no existe un archivo cargado", DialogoCodefac.MENSAJE_INCORRECTO);
                }
                else
                {
                    construirDatosExcel(new File(getTxtRutaArchivo().getText()));
                }
            }
        });
        
        getBtnMigrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                estadoCargando();
                excelMigrar.migrar(getInterfaceMigrar());
                //DefaultTableModel modelo=excelMigrarEstudiantes.construirTabla();
                excelMigrar.construirTabla(getTblDatos());
                //getTblDatos().setModel(modelo);                
                estadoNormal();
            }
        });
        
        getBtnCargarExcel().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ubicacionRespaldo = "";
                JFileChooser fileChooser = new JFileChooser();
                FileFilter filter2 = new FileNameExtensionFilter("Archivo Excel", "xls");
                FileFilter filter = new FileNameExtensionFilter("Archivo Excel Nuevo", "xlsx");
                fileChooser.setFileFilter(filter);
                fileChooser.addChoosableFileFilter(filter2);
                int seleccion = fileChooser.showDialog(null, "Seleccionar el archivo");
                switch (seleccion) {
                    case JFileChooser.APPROVE_OPTION:
                        ubicacionRespaldo = "" + fileChooser.getSelectedFile();
                        getTxtRutaArchivo().setText("" + ubicacionRespaldo);
                        construirDatosExcel(fileChooser.getSelectedFile());
                        //construirTabla();
                        break;
                    case JFileChooser.CANCEL_OPTION:

                        break;
                    case JFileChooser.ERROR_OPTION:

                        break;
                }

            }
        });
    }
    
    private void construirDatosExcel(File archivo)
    {
        try {
            construirExcelMigrar(archivo);
            excelMigrar.leerDatos();
            //getTblDatos().setModel(excelMigrarEstudiantes.construirTabla());
            this.excelMigrar.construirTabla(getTblDatos());
            
            
            
            /*for (ExcelMigrar.FilaResultado filaResultado : resultado) {
            Estudiante estudiante=new Estudiante();
            estudiante.setCedula((String) filaResultado.fila.get(0).valor);
            estudiante.setNombres((String) filaResultado.fila.get(1).valor);
            estudiante.setApellidos((String) filaResultado.fila.get(2).valor);
            estudiantes.add(estudiante);
            }*/
        } catch (ExcelMigrar.ExcepcionMigrar ex) {
            Logger.getLogger(MigrarModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje("Error",ex.getMessage(),DialogoCodefac.MENSAJE_INCORRECTO);
        }
        
    }
    
    public void construirExcelMigrar(File archivo)
    {
        this.excelMigrar=getExcelMigrar();
        this.excelMigrar.setArchivoExel(archivo);
        this.excelMigrar.setHojaActual((int) getCmbNumeroHoja().getSelectedItem()-1);
        construirDatosRequeridos();
        
    }
    
    public void agregarComponenteOtroPanel(Component componente)
    {
        getPnlOtrasOpciones().add(componente);        
    }

    private void construirDatosRequeridos() {
        getPnlCamposRequeridos().removeAll();
        
        for (ExcelMigrar.CampoMigrarInterface campoMigrar : this.excelMigrar.obtenerCampos()) {
            JCheckBox jcheckBox=new JCheckBox(campoMigrar.getNombre());
            jcheckBox.setSelected(campoMigrar.getCampoRequerido());
            getPnlCamposRequeridos().add(jcheckBox);
            //campoMigrar.setCampoRequerido(true);
            
            //AgregarListener para campos requeridos
            jcheckBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    campoMigrar.setCampoRequerido(jcheckBox.isSelected());
                }
            });
            
        }
        
        getPnlCamposRequeridos().revalidate();
        getPnlCamposRequeridos().repaint();

    }
    
    public Object obtenerDatoPlantilla(ExcelMigrar.FilaResultado fila,ExcelMigrarProductos.Enum productoEnum)
    {
        Object precioVentaOfertaObj = fila.getByEnum(productoEnum).valor;
        if (precioVentaOfertaObj != null && !precioVentaOfertaObj.toString().isEmpty()) 
        {
            return precioVentaOfertaObj;
        }
        return null;
    }
    
    
}
