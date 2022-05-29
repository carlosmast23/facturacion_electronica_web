/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.excel;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.OrdenarEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoLicenciaEnum;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import ec.com.codesoft.codefaclite.utilidades.texto.UtilidadesTextos;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class Excel<T> 
{
    private Workbook libro;
    private CreationHelper crearAyuda;
    private Sheet hoja1;
    private Row fila;
    private Cell celda;
    private List <Integer> posicionesColumnas;
    private String archivo;
    private String nombreArchivExcel;
            
    public Excel()
    {
        this.libro = new XSSFWorkbook(); //H para xls y X para xlsx
        this.crearAyuda = libro.getCreationHelper();
        this.hoja1 = libro.createSheet("Hoja1");
        this.posicionesColumnas = new ArrayList<>();
        Date date = new Date();
        //String nombreArchivoExcel = "" + date;
        //nombreArchivoExcel = nombreArchivoExcel.replaceAll(" ", "");
        //nombreArchivoExcel = nombreArchivoExcel.replaceAll(":", "");
        String nombreArchivoExcel=UtilidadesArchivos.generarNombreArchivoUnico("reporteExcel",".xlsx");
        //this.archivo = "\\tmp\\"+nombreArchivoExcel+".xlsx";
        this.archivo = "tmp\\"+nombreArchivoExcel;
    }
    
    public void gestionarIngresoInformacionExcel(String[] cabeceraDatosDinamicos, List<ExcelDatosInterface> datosDinamicos) throws FileNotFoundException, IOException, IllegalArgumentException, IllegalAccessException
    {
        Map<String, CellStyle> obtenerEstilo = crearEstilos(libro);
        int numeroDatos = cabeceraDatosDinamicos.length;
        
        //Realizo la cabecera en base a los Datos obtenidos
        crearCabeceraHoja(cabeceraDatosDinamicos, obtenerEstilo);
        /**
         * Colocar el valor de los atributos por cada objeto en las celdas correspondientes 
         * y la ultima referencia de filas ingresadas 
        */
        int f = ingresarDatosCeldasHoja(datosDinamicos, obtenerEstilo);
        
        fila = hoja1.createRow(f);
             
        //Ajustar el tamaño de las celdas en base al tamaño del texto
        for(int i=0;i<numeroDatos;i++)
        {
            hoja1.autoSizeColumn((short)i);
        }
        
        //Sumar las columnas en base a formula Excel, si las filas lo permiten
        sumarCeldasHoja(f);
                
        File archivoSalida = new File(archivo);
        if(!archivoSalida.exists())
        {
            archivoSalida.getParentFile().mkdirs();
        }
        
        FileOutputStream fileOutputStream = new FileOutputStream(archivoSalida);
        libro.write(fileOutputStream);
        fileOutputStream.close();
    }
    
    public void crearCabeceraHoja(String[] cabeceraDatosDinamicos, Map<String, CellStyle> obtenerEstilo)
    {
        fila = hoja1.createRow(0);       
            for(int i = 0; i< cabeceraDatosDinamicos.length; i++)
            {
                celda = fila.createCell(i);
                celda.setCellValue(cabeceraDatosDinamicos[i]);
                celda.setCellStyle(obtenerEstilo.get("titulo"));
                celda.setCellStyle(obtenerEstilo.get("color"));
                celda.setCellStyle(obtenerEstilo.get("centrar"));
            }
            
    }
 
    public int ingresarDatosCeldasHoja(List<ExcelDatosInterface> datos, Map<String,CellStyle> estilo) throws FileNotFoundException, IOException
    {
        int f = 1;
        int c = 0;
        boolean b = true;
        for (ExcelDatosInterface dato : datos) 
        {
            fila = hoja1.createRow(f); 
            List<TipoDato> valores = dato.getDatos();
            c = 0;
            for (TipoDato valorDato: valores) 
            {
                celda = fila.createCell(c++);
                
                TipoDataEnum tipo = valorDato.tipoData;
                switch(tipo)
                {
                    case TEXTO:
                        Object valor = (valorDato.valor!=null)?valorDato.valor:"";
                        celda.setCellValue(valor.toString());
                    break;
                    case FECHA:
                        Object valor2 = (valorDato.valor!=null)?valorDato.valor:"";
                        celda.setCellValue((Date) devolverTipoDato(valor2.toString()));
                        celda.setCellStyle(estilo.get("fecha"));
                    break;
                    case NUMERO:
                        if(valorDato.valor==null || valorDato.valor=="112.00")
                        {
                            System.out.println("detener");
                        }
                        Object valor3 = (valorDato.valor!=null)?valorDato.valor:"0";
                        System.out.println(valor3);
                        celda.setCellValue(Double.parseDouble(valor3.toString()));
                        if(b)
                        {
                            posicionesColumnas.add(c-1);
                        }
                    break; 
                }  
            }
            f+=1;
            b = false;
        }
        return f;
    }
 
    public void sumarCeldasHoja(int f)
    {
        for(Integer posicionColumna: posicionesColumnas)
        {
            String formula = obtenerFormulaSuma(posicionColumna, f);
            celda = fila.createCell((int)posicionColumna);
            celda.setCellType(CellType.FORMULA);
            celda.setCellFormula(formula);
        }
    }
    
    public static Object devolverTipoDato(String dato)
    {
        
        if(UtilidadesTextos.transformarStringDouble(dato)!= null)
        {
            return UtilidadesTextos.transformarStringDouble(dato);
        }
        else if(UtilidadesTextos.transformarStringDate(dato)!= null)
        {
            return UtilidadesTextos.transformarStringDate(dato);
        }else
        {
            return dato;
        }
    }
    
    public String obtenerFormulaSuma(int columna, int fila)
    {
        String letraColumna = UtilidadesTextos.obtenerLetra(columna);
        return "SUM("+letraColumna+"2:"+letraColumna+""+fila+")";
    }
    
    public Map<String, CellStyle> crearEstilos(Workbook libro)
    {
        Map<String,CellStyle> estilos = new HashMap<>();
        CellStyle estiloCelda = libro.createCellStyle();
        
        Font formatoCabecera = libro.createFont();
        formatoCabecera.setFontHeightInPoints((short)12);
        formatoCabecera.setFontName("Calibri");
        formatoCabecera.setBold(true);
        estiloCelda.setFont(formatoCabecera);  
        estilos.put("titulo", estiloCelda);
        
        estiloCelda.setAlignment(HorizontalAlignment.CENTER);
        estilos.put("centrar", estiloCelda);
        
        estiloCelda.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        estiloCelda.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estilos.put("color", estiloCelda);
        
        estiloCelda = libro.createCellStyle();
        estiloCelda.setDataFormat(libro.createDataFormat().getFormat("mm/dd/yyyy"));
        estilos.put("fecha", estiloCelda);
        return estilos;
    }

    public void abrirDocumento()
    {
        try 
        {
            //TODO: Por el momento asumo que cualquier empresa que tenga una liencia gratuita desactivo para todos
            //Organizar de mejor manera por que esta solucion ocupa muchos recursos por que tiene que ahcer consultas al servidorr
            //SOLUCION: Poner que el parametro que necesito venga por el constructo , pero algunas clases por ejemplo de contralador no tiene ese dato y esta demoroso
            List<Empresa> empresasList=ServiceFactory.getFactory().getEmpresaServiceIf().obtenerTodosActivos(OrdenarEnum.ASCEDENTE);
            //Empresa empresaDefecto=empresasList.get(0);
            //TipoLicenciaEnum tipoLicenciaEnum=ServiceFactory.getFactory().getUtilidadesServiceIf().getTipoLicencia(empresaDefecto);            
            //Verificar que los usuarios gratuitos no puedan sacar reportes gratuitos
            //if (tipoLicenciaEnum.equals(TipoLicenciaEnum.GRATIS))
            //{
            //    DialogoCodefac.mensaje(new CodefacMsj("La versión GRATUITA no soporta reportes en excel", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            //    return;
            //}
            
            try
            {
                File path = new File (archivo);
                Desktop.getDesktop().open(path);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        catch (RemoteException ex) 
        {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   

    public Workbook getLibro() {
        return libro;
    }

    public void setLibro(Workbook libro) {
        this.libro = libro;
    }
    
    
    
    public void eliminarDocumento()
    {
        File archivo = new File(this.archivo);
        try{
            if(archivo.delete())
            {
                System.out.println("Se elimino el archivo");
            }
            else
            {
                System.out.println("No se puede eliminar el archivo");
            }
        }catch(Exception exc)
        {
            System.out.println("Error al eliminar el archivo");
        }
                
    }
    
    public enum TipoDataEnum
    {
        TEXTO(String.class),
        NUMERO(Double.class),
        FECHA(Date.class);
        
        private Class clase;
        
        private TipoDataEnum(Class clase)
        {
            this.clase = clase;
        }
        
        public Object convertir(Object valor)
        {
            return clase.cast(valor);
        }
              
    };
    
    public File obtenerArchivo()
    {
        
        File archivo = new File(this.archivo);
        
        if(archivo.exists())
        {
            return archivo;
        }
        
        return null;
        
    }
    
    
}
