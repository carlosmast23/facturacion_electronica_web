/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.excel;

import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.component.Component;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import static org.apache.poi.hssf.usermodel.HeaderFooter.file;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author
 */
public abstract class ExcelMigrar {
    
    private static final String ESTADO_MIGRADO="Migrado";
    private static final String ESTADO_DATO_DUPLICADO="El dato ya existe en el sistema";
    private static final String ESTADO_SIN_MIGRADO="Sin Migrar";
    
    public static String LOG_ULTIMO_CAMPO_LEIDO="";
    /**
     * Archivo excel donde va a contener los datos para migrar
     */
    private File archivoExel;
    
    /**
     * Cabecera con los tipos de datos
     */
    //private Class[] tiposDatosCabecera;
    
    /**
     * Nombre de la hoja actual para trabajar
     */
    private int hojaActual;
    
    /**
     * Metodo que va a almacenar la cabecera de las tablas
     */
    //private CampoMigrarInterface[] camposEnum;
    //private String[] tituloTabla;
    
    /**
     * Datos de la consulta donde se van a almacenar
     */
    private List<FilaResultado> datosResultado;
    
    

    
    
    public abstract CampoMigrarInterface[] obtenerCampos();
    public abstract CampoMigrarInterface getCampoEstado();

    public ExcelMigrar() {
        this.hojaActual=0;
    }
    

    public ExcelMigrar(File archivoExel) {
        this.archivoExel = archivoExel;
        this.hojaActual=0;
    }    
    
    private void validacionInicial(XSSFSheet sheet ) throws ExcepcionMigrar
    {
        if(sheet.getLastRowNum()<1)
        {
            new ExcepcionMigrar("El documento no tiene datos para migrar");
        }
        
        /**
         * Validar que los tipos de datos ingresados sean los correctos con los formatos del excel
         */
        Row primeraFila = sheet.getRow(1);        
        List<Class> tiposDatosCabecera= getTipoDatos();
                       
        //for (int i = 0; i < primeraFila.getLastCellNum(); i++) {
        for (int i = 0; i < tiposDatosCabecera.size(); i++) {
            Cell celda = primeraFila.getCell(i);
            //System.out.println(celda.getCellTypeEnum().name());
            if(celda==null)
            {
                continue;
            }
            
            
            System.out.println(celda.getCellTypeEnum().toString());
            
            switch (celda.getCellTypeEnum()) {
                case STRING:
                    //System.out.println(celda.getStringCellValue());
                    if(!tiposDatosCabecera.get(i).equals(String.class))
                    {
                       throw new ExcepcionMigrar("Se esperaba un tipo String pero la columna con valor <"+celda.getStringCellValue()+"> tiene un tipo de dato diferente  "+tiposDatosCabecera.get(i).getName());
                    }
                    break;

                case NUMERIC:
                    
                    if(HSSFDateUtil.isCellDateFormatted(celda))
                    {
                        if(!tiposDatosCabecera.get(i).equals(Date.class))
                        {
                            throw new ExcepcionMigrar("Se esperaba un tipo Date pero la columna con valor <"+celda.getDateCellValue()+"> tiene un tipo de dato diferente  "+tiposDatosCabecera.get(i).getName());
                        }
                    }else
                    {
                        if(!tiposDatosCabecera.get(i).equals(Double.class))
                        {
                           throw new ExcepcionMigrar("Se esperaba un tipo Double pero la columna con valor <" +celda.getNumericCellValue()+"> tiene un tipo de dato diferente  "+tiposDatosCabecera.get(i).getName());
                        }
                    }
                    break;
                    
                
            }

        }
        
        
    }
    
    public List<Class> getTipoDatos()
    {
        List<Class> tiposDatos=new ArrayList<Class>() ;

        CampoMigrarInterface[] datos=obtenerCampos();
        List<CampoMigrarInterface> datosList=Arrays.asList(datos);;
        UtilidadesLista.ordenarLista(datosList, new Comparator<CampoMigrarInterface>() {
            @Override
            public int compare(CampoMigrarInterface o1, CampoMigrarInterface o2) {
                return new Integer(o1.getPosicion()).compareTo(new Integer(o2.getPosicion()));
            }
        });
        
        for (CampoMigrarInterface dato : datosList) 
        {            
            tiposDatos.add(dato.getTipoDato());
        }
        
        return tiposDatos;

    }
    
    public void migrar(MigrarInterface interfaz)
    {
        for (FilaResultado filaResultado : datosResultado) {
            //Solo ejecutar este proceso con los datos que faltan de migrar
            if(!filaResultado.migrado)
            {
                try {
                    //try {
                    verificarDatosRequerido(filaResultado);
                    interfaz.procesar(filaResultado);
                    filaResultado.migrado=true;
                    /*} catch (Exception ex) {
                    Logger.getLogger(ExcelMigrar.class.getName()).log(Level.SEVERE, null, ex);
                    filaResultado.migrado=false;
                    filaResultado.error=ex.getMessage();
                    } */
                } catch (ExcepcionExcel ex) {
                    filaResultado.migrado=false;
                    filaResultado.error=ex.getMessage();
                    
                } catch (ExcepcionExcelRegistroDuplicado ex) {
                    filaResultado.migrado=false;
                    filaResultado.error=ESTADO_DATO_DUPLICADO;

                }
            }   
        }
    }
    
    public void verificarDatosRequerido(FilaResultado filaResultado) throws ExcelMigrar.ExcepcionExcel
    {
                
        for (CampoMigrarInterface campoMigrarInterface : obtenerCampos()) {
            Class clase= campoMigrarInterface.getTipoDato();
            
            if(!campoMigrarInterface.equals(getCampoEstado()))
            {
                if(campoMigrarInterface.getCampoRequerido())
                {
                    verificarError(filaResultado.getByEnum(campoMigrarInterface), campoMigrarInterface.getNombre());                

                }
            }
            
        } 
    }
    
    private void verificarError(Object dato,String nombre)  throws ExcelMigrar.ExcepcionExcel
    {
        if(dato==null  || dato.toString().isEmpty())
        {
            throw new ExcelMigrar.ExcepcionExcel("El campo "+nombre+" es requerido");
        }
        
    }
    
    /**
     * Funcion que se encarga de leer los datos del archivo de excel
     * @return
     * @throws ec.com.codesoft.codefaclite.controlador.excel.ExcelMigrar.ExcepcionMigrar 
     */
    public List<FilaResultado> leerDatos() throws ExcepcionMigrar
    {
        datosResultado=new ArrayList<FilaResultado>();
        int tamanioColumna= obtenerTituloTabla().size();
        
        FileInputStream file = null;
        try {
            file = new FileInputStream(archivoExel);
            // leer archivo excel
            XSSFWorkbook worbook = new XSSFWorkbook(file);
            //obtener la hoja que se va leer
            XSSFSheet sheet = worbook.getSheetAt(hojaActual);
            //worbook.getSheet(hojaActual)
            //obtener todas las filas de la hoja excel
            validacionInicial(sheet);
            
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            Row row;
            // se recorre cada fila hasta el final
            while (rowIterator.hasNext()) {
                
                row = rowIterator.next();
                //se obtiene las celdas por fila
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell cell;
                //se recorre cada celda
                FilaResultado filaResultado=new FilaResultado();

                for(int i=0;i<tamanioColumna-1;i++)
                {
                    //Esto me sirve para no leer datos que no esten dentro del rango de las columnas de los titulos
                    //if(i>=(tamanioColumna-1))
                    //{
                    //    break;
                    //}
                    
                    // se obtiene la celda en específico y se la imprime
                    cell = row.getCell(i);
                    
                    //cuando hay campos nulos los lleno con un valor de texto vacio
                    if(cell==null)
                    {
                        filaResultado.agregarDato(new CampoResultado<String>(String.class,"",obtenerCampos()[i]),false);
                    }
                    else
                    {
                    
                        switch (cell.getCellTypeEnum()) {
                            case STRING:

                                filaResultado.agregarDato(new CampoResultado<String>(String.class, cell.getStringCellValue(), obtenerCampos()[i]), false);
                                //System.out.print(cell.getStringCellValue() + " | ");
                                break;
                            case NUMERIC:
                                //Cuando es numero tambien puede ser fechas
                                if(HSSFDateUtil.isCellDateFormatted(cell))
                                {

                                    filaResultado.agregarDato(new CampoResultado<Date>(Date.class, cell.getDateCellValue(), obtenerCampos()[i]), false);
                                }
                                else
                                {
                                    filaResultado.agregarDato(new CampoResultado<Double>(Double.class, cell.getNumericCellValue(), obtenerCampos()[i]), false);
                                }
                                break;

                            case BLANK:
                                filaResultado.agregarDato(new CampoResultado<String>(String.class, "", obtenerCampos()[i]), false);
                                break;

                            case _NONE:
                                //System.out.println("none");
                                break;
                        }
                    }
                }
                /*
                while (cellIterator.hasNext()) {
                    
                    //Esto me sirve para no leer datos que no esten dentro del rango de las columnas de los titulos
                    if(i>=(tamanioColumna-1))
                    {
                        break;
                    }
                    
                    // se obtiene la celda en específico y se la imprime
                    cell = cellIterator.next();
                    
                    switch (cell.getCellTypeEnum()) {
                        case STRING:
                            
                            filaResultado.agregarDato(new CampoResultado<String>(String.class,cell.getStringCellValue(),obtenerCampos()[i]),false);
                            //System.out.print(cell.getStringCellValue() + " | ");
                            break;
                        case NUMERIC:
                            filaResultado.agregarDato(new CampoResultado<Double>(Double.class,cell.getNumericCellValue(),obtenerCampos()[i]),false);
                            
                            break;
                            
                        case BLANK:
                            filaResultado.agregarDato(new CampoResultado<String>(String.class,"",obtenerCampos()[i]),false);
                            break;
                        
                        case _NONE:
                            //System.out.println("none");
                            break;

                            
                    }
                    
                    //i++;
                }*/
                datosResultado.add(filaResultado);
                
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelMigrar.class.getName()).log(Level.SEVERE, null, ex);
        }catch (ExcepcionMigrar ex)
        {
            throw ex; 
        }
        catch (IOException ex) {
            Logger.getLogger(ExcelMigrar.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(ExcelMigrar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return datosResultado;
    }
    
    /*public void setEnumCampos(CampoMigrarInterface[] campos)
    {
        this.camposEnum=campos;
    }*/
    
    private Vector<String> obtenerTituloTabla()
    {
       Vector<String> datosTitulo=new Vector<String>();
       CampoMigrarInterface[] camposEnum=obtenerCampos();
        for (CampoMigrarInterface campo : camposEnum) {
            datosTitulo.add(campo.getNombre());
        }
        //datosTitulo.add("Estado");
        
        return datosTitulo;
    }
    
    public void construirTabla(JTable tabla)
    {
        DefaultTableModel  modeloDefecto=new DefaultTableModel(obtenerTituloTabla(),0);
                
        for (FilaResultado fila : datosResultado) {
            Object[] datos=fila.fila.toArray();
            
            
            if(!fila.migrado)
            {
                //Cuando no existe ni ningun error mostrar el texto de sin migrar
                if(fila.error==null || fila.error.isEmpty())
                {
                    datos=ArrayUtils.add(datos,ESTADO_SIN_MIGRADO);
                }
                else //Cuando existe un error mostrar dicho error
                {
                    datos=ArrayUtils.add(datos,fila.error);
                }   
            }
            else
            {
                datos=ArrayUtils.add(datos,ESTADO_MIGRADO);
            }
            
            
            modeloDefecto.addRow(datos);
            //modeloDefecto.add
            //ArrayUtils.add(fila.fila.toArray(), hojaActual, fila)
            
            
        }
        
        tabla.setModel(modeloDefecto);
        
        /**
         * Pintar la ultima columna segun el estado
         */
        tabla.setDefaultRenderer(tabla.getColumnClass(0), new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                //System.out.println(row+":"+column);
                if (column == getCampoEstado().getPosicion()) {
                    
                    //Si por algun motivo no existe ningun dato pinto de otro color para ver el eror
                    if(value==null)
                    {
                        setBackground(Color.green);
                    }
                    else
                    {

                        if (value.toString().equals(ESTADO_SIN_MIGRADO)) {
                            setBackground(Color.white);
                        } else if (value.toString().equals(ESTADO_MIGRADO)) {
                            setBackground(Color.GREEN);
                        } else if(value.toString().equals(ESTADO_DATO_DUPLICADO)){
                            setBackground(Color.lightGray);
                        }else
                        {
                            setBackground(Color.orange); //Cuando existe un error
                        }
                    }
                } else {
                    setBackground(Color.white);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); //To change body of generated methods, choose Tools | Templates.
            }          
            
        });

    }
    
    
    //public void setTiposDatosCabecera(Class[] tiposDatosCabecera) {
    //    this.tiposDatosCabecera = tiposDatosCabecera;
    //}

    //public void setTituloTabla(String[] tituloTabla) {
    //    this.tituloTabla = tituloTabla;
    //}

    public void setArchivoExel(File archivoExel) {
        this.archivoExel = archivoExel;
    }
    
    
    
    
    public class ExcepcionMigrar extends Exception
    {

        public ExcepcionMigrar(String message) {
            super(message);
        }
        
    }
    

    /**
     * Clase que obtiene los resultados de la fila
     */
    public class FilaResultado
    {
        public List<CampoResultado> fila;
        public boolean migrado;
        public String error;
       
        public FilaResultado() {
            fila=new ArrayList<CampoResultado>();
            migrado=false;
            error="";
        }
        
        public void agregarDato(CampoResultado campoResultado,boolean migrado)
        {
            fila.add(campoResultado);
            this.migrado=migrado;
        }
        
        public CampoResultado get(int posicion)
        {
            return fila.get(posicion);
        }
        
        
        
        public CampoResultado getByEnum(CampoMigrarInterface campoEnum)
        {
            ExcelMigrar.LOG_ULTIMO_CAMPO_LEIDO=campoEnum.getNombre();
            for (CampoResultado campoResultado : fila) 
            {
                //System.out.println(campoResultado.campoEnum.getNombre());
                if(campoEnum.getNombre().equals(campoResultado.campoEnum.getNombre()))
                {
                    return campoResultado;
                }                
            }
            return null;
        }
        
        
              
        
    }

    public void setHojaActual(int hojaActual) {
        this.hojaActual = hojaActual;
    }
    
    
    
    
    /**
     * Clase que obtiene cada celda
     * @param <T> 
     */
    public class CampoResultado<T>
    {
        public CampoMigrarInterface campoEnum;
        public Class clase;
        public T valor;

        public CampoResultado(Class clase, T valor,CampoMigrarInterface campoEnum) {
            this.clase = clase;
            this.valor = valor;
            this.campoEnum=campoEnum;
        }

        @Override
        public String toString() {
            return valor.toString();
        }
        
    }
    
    public interface CampoMigrarInterface
    {
        public abstract String getNombre();
        public abstract int getPosicion();
        public abstract Class getTipoDato();
        public abstract Boolean getCampoRequerido();
        public abstract void setCampoRequerido(boolean requerido);
    };
    
    /**
     * Interface que permite establecer las validaciones especificas por cada pantalla
     */
    public interface MigrarInterface 
    {
        public void procesar(FilaResultado fila) throws ExcepcionExcel,ExcepcionExcelRegistroDuplicado;       
    };    
    
    
    public static class ExcepcionExcel extends Exception {

        public ExcepcionExcel(String message) {
            super(message);
        }
    };
    
    /**
     * Excepcion que permite comunicar que el dato ya fue ingresado
     */
    public static class ExcepcionExcelRegistroDuplicado extends Exception {

        public ExcepcionExcelRegistroDuplicado(String message) {
            super(message);
        }
    };
    

}
