/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.model;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.main.other.ArchivoDescarga;
import ec.com.codesoft.codefaclite.main.panel.DescargaDialog;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import java.awt.Frame;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author Carlos
 */
public class DescargaModel extends DescargaDialog implements Runnable{
    
    /**
     * Map que va a contener como clave el nombre de archivo , y como valor la url del archivo a descargar
     * @return 
     */
    private List<ArchivoDescarga> archivosDescargar;
    /**
     * Listado de archivos que no tienen que estar en ninguna optimizacion para que funione la actualizacion
     * principalmente el Codefac.jar por que es el archivo principal que aveces comparadando tamaños no se actualza
     * pero es necesario que ese archivo siempre se actualice
     */
    private List<String> excepcionesOptimizacion;
    
    private DefaultListModel<String> modeloLista;
    
    private Boolean descargaCompleta;


    public DescargaModel(List<ArchivoDescarga> archivosDescargar,List<String> excepcionesOptimizacion) {
        super(null,true);
        this.archivosDescargar=archivosDescargar;
        this.excepcionesOptimizacion=excepcionesOptimizacion;
        setLocationRelativeTo(null);
        cargarListaArchivos();
        descargaCompleta=false;
    }
    
    
    private void cargarListaArchivos()
    {
        modeloLista=new DefaultListModel<String>();
        for (ArchivoDescarga archivoDescarga : archivosDescargar) {
            String nombreArchivo = archivoDescarga.nombreArchivo;
            modeloLista.addElement(nombreArchivo); 
        }
        getLstArchivosPendientes().setModel(modeloLista);
    }
    
    
    public DescargaModel(Frame parent, boolean modal) {
        super(parent, modal);
    }
    
    public void empezarDescarga()
    {
        new Thread(this).start(); //Empezar hilo para comenzar el proceso de descarga
    }
    
    private void empezarDescargaHilo()
    {
        for (ArchivoDescarga archivoDescarga : archivosDescargar) {
            try {
                String name = archivoDescarga.nombreArchivo;  //nombre del archivo destino
                String url = archivoDescarga.url; //dirección url del recurso a descargar
                
                
                //try {
                
                //Directorio destino para las descargas
                String folder = archivoDescarga.destino + "/";
                
                //Crea el directorio de destino en caso de que no exista
                File dir = new File(folder);
                
                if (!dir.exists()) {
                    if (!dir.mkdir()) {
                        return; // no se pudo crear la carpeta de destino
                    }
                }
                
                File file = new File(folder + name + ".tmp");//Cuando empiece la descarga esta con punto tmp si se cancela y no termina de descargar
                
                URLConnection conn = new URL(url).openConnection();
                //Con este codigo espera unos 7 segundos antes de desconectar la session
                
                int intentosMaximos=8;
                //int numero de intentos maximos
                for (int i = 0; i < intentosMaximos;) {                
                    try
                    {
                        conn.setConnectTimeout(7000);
                        conn.connect();
                        break;
                    }catch(SocketTimeoutException stoe)
                    {
                        stoe.printStackTrace();
                        i++;
                    }
                    
                    //Si no se logra terminar de conectar despues de algunos intentos temino el aplicativo
                    if(i==(intentosMaximos-1))
                    {
                        return;
                    }                    
                }
                
                
                int tamanioTotal = conn.getContentLength();                
                File archivoActual = new File("lib/" + name.replace(".new","") );
                int tamanioArchivoActual=(int) archivoActual.length();
                
                //Verifico que el archivo no esta en la lista de excepcion
                //TODO: Principalemente por el archivo codefac.jar
                if(!excepcionesOptimizacion.contains(name))
                {
                    //Si los archivos son iguales entonces solo descargo los que faltan
                    if(tamanioTotal==tamanioArchivoActual )
                    {
                        System.out.println(name+" Archivo literalmente iguales");
                        modeloLista.removeElement(name);
                        continue;
                    }
                }
                
                
                BigDecimal tamanioArchivo = new BigDecimal((float) tamanioTotal / (float) (1024));
                
                /**
                 * ---------------------------------------------------------
                 *      ACTUALIZAR EN LA PANTALLA DEL FORMULARIO
                 * ---------------------------------------------------------
                 */
                getLblNombreArchivo().setText(name);
                getLblTamanio().setText(tamanioArchivo.setScale(2, BigDecimal.ROUND_HALF_UP) + " kb");
                getLblSitio().setText(url);
                getBarraProgreso().setValue(0); //Empiezo una nueva descarga y seteo el valor del porcentaje en 0
                
                InputStream in = conn.getInputStream();
                FileOutputStream out = new FileOutputStream(file);
                
                long size = 0;
                int b = 0;
                int i = 0;
                //Variable para poder identificar bytes vacios y si recibe mas de una cantidad permitir al usuario cancelar la operación
                //Integer bytesVacios=0;
                while (b != -1) {
                    b = in.read();
                    //System.out.println(b);
                    i++;
                    if (b != -1) {
                        size = out.getChannel().size();
                        out.write(b);                        
                    }                    
                    //Cuando no recibe nada
                    /*if( b==0) 
                    {
                        System.out.println("byte vacio ["+bytesVacios+"]");
                        bytesVacios++;
                    }
                    else
                    {
                        bytesVacios=0;
                    }*/
                    
                    //Si ha recibido mas de 1000 bytes vacios ocurrio algun error de comunicación y cancelo la actualización
                    //TODO: Esta funcionalidad queda en modo de prueba porque aveces probando cuando no tiene internet isgue leyendo 0 degana
                    /*if(bytesVacios>1000)
                    {
                        DialogoCodefac.mensaje("Error de comunicación con el servidor",DialogoCodefac.MENSAJE_INCORRECTO);
                        return;
                    }*/
                    
                    
                    /**
                     * ---------------------------------------------------------
                     *      CALCULAR EL PORCENTAJE DE DESCARGA
                     * ---------------------------------------------------------
                     */
                    if (i % 10000 == 0) {
                        BigDecimal tamanio = new BigDecimal((float) size / (float) (1024));
                        //System.out.println(tamanio.setScale(3, RoundingMode.HALF_UP) + " kb");
                        int porcentaje = (int) ((float) size * 100 / (float) tamanioTotal);
                        getBarraProgreso().setValue(porcentaje);                        
                    }
                }
                
                out.close();
                in.close();
                UtilidadesArchivos.renombrarArchivo(file, name); //Nombre final sin la extension tmp
                
                //TODO: Agregar una validacion para al final verificar que el tamaño final del archivo es igual al archivo que esta en internet
                
                //Si termina correctamente quitamos de lista de archivos a descargar
                modeloLista.removeElement(name);
            } catch (MalformedURLException ex) {
                Logger.getLogger(DescargaModel.class.getName()).log(Level.SEVERE, null, ex);
                return;
            } catch (IOException ex) {
                Logger.getLogger(DescargaModel.class.getName()).log(Level.SEVERE, null, ex);
                return;
            } catch (Exception e)
            {
                Logger.getLogger(DescargaModel.class.getName()).log(Level.SEVERE, null, e);
                return;
            }
        }
        descargaCompleta=true;
        
    }

    @Override
    public void run() {
        empezarDescargaHilo();
        dispose();
    }

    public Boolean getDescargaCompleta() {
        return descargaCompleta;
    }
    
    
    
}
