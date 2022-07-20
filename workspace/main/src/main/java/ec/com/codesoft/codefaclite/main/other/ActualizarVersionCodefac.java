/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.main.other;

import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.main.archivos.ArchivoConfiguracionesCodefac;
import ec.com.codesoft.codefaclite.main.init.Main;
import ec.com.codesoft.codefaclite.main.model.DescargaModel;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.utilidades.file.UtilidadesArchivos;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesSistema;
import ec.com.codesoft.codefaclite.utilidades.web.UtilidadesWeb;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @auhor
 */
public class ActualizarVersionCodefac {
    
    public static void verificarUltimaVersionCodefac()
    {
        ////////////////////////////////////////////////////////////////////
        ///  BUSCAR EL REPOSITORIO PARA ACTUALIZAR EN DESARROLLO O ESTABLE
        ////////////////////////////////////////////////////////////////////
        PropertiesConfiguration propiedadesArchivo=ArchivoConfiguracionesCodefac.getInstance().getPropiedadesIniciales();
        String modoActualizacion = propiedadesArchivo.getString(ArchivoConfiguracionesCodefac.CAMPO_MODO_ACTUALIZACION);
        String path = ParametrosSistemaCodefac.REPOSITORIO_ACTUALIZACION_ESTABLE; //Por defecto el repositorio para actualizar siempre es el estable

        if (modoActualizacion != null && modoActualizacion.equals(ArchivoConfiguracionesCodefac.ModoActualizacionEnum.DESARROLLO.getNombre())) {
            path = ParametrosSistemaCodefac.REPOSITORIO_ACTUALIZACION_DESARROLLO;
        }
        
        //String path="http://localhost:8080/codefac_pagina/uploads/versiones/";
        String carpetaDescarga="tmp"; //nombre de la carpeta para almacenar en el directoro TODO: Crear una variable global paa hacer referenca al directorio temporal
        
        String nameUltimaVersion="codefac.jar"; //Nombre del archivo de la nueva version de Codefac para descargar        
        String nameVersionPropiedades="ultimaVersion.codefac"; //Nombre del archivo de las propiedades para comparar si tenemos la última version
        String nameUpdater="updater.jar"; //Nombre del archivo updater que se encarga de hacer la actualizacion
        
        //DESCARGAR el archivo de PROPIEDADES de la ULTIMA VERSION vigente
        if(UtilidadesWeb.descargarArchivo(nameVersionPropiedades, path+nameVersionPropiedades, carpetaDescarga))
        {
            Logger.getLogger(ActualizarVersionCodefac.class.getName()).log(Level.INFO, "Descarga archivo de que contiene el número de la última versión");
            Properties propiedadesIniciales = new Properties();
            try {
                propiedadesIniciales.load(new FileReader(carpetaDescarga+"/"+nameVersionPropiedades));
                String ultimaVersion=propiedadesIniciales.getProperty("version"); //TODO: Parametrizar estos datos
                
                //Verificar si el MODO FORZAR ACTUALIZAR ESTA ACTIVO
                Boolean forzarActualizacion=verificarForzarActualizacionPropiedadesInit();
                
                //Solo actualizar si la VERSION instalada es MENOR a la disponible en internet o esta activado la propiedad de FORZAR ACTUALIZAR
                if(UtilidadesSistema.compareVersion(ParametrosSistemaCodefac.VERSION,ultimaVersion)==-1 || forzarActualizacion)
                //if(true)
                {
                    if(!DialogoCodefac.dialogoPregunta("Actualizar Codefac","Existe una nueva versión disponible , desea actualizar ahora?", DialogoCodefac.MENSAJE_CORRECTO))
                    {
                        //Si el usuario no desea actualizar la version se termina la funcion si actualizar
                        return;
                    }
                    
                    //Descargar el archivo updater que es el encargado de instalar la nueva version descargada
                    //el updater debe descargarse en la raiz
                    UtilidadesWeb.descargarArchivo(nameUpdater, path + nameUpdater, "");
                    Logger.getLogger(ActualizarVersionCodefac.class.getName()).log(Level.INFO, "Descargado updater para instalar las actualizaciones");
                    
                    //Lista para descargar la ultima version disponible en el repositorio web de los archivos de codefac
                    List<ArchivoDescarga> archivosDescargar=new ArrayList<ArchivoDescarga>();
                   
                    archivosDescargar.add(new ArchivoDescarga(nameUltimaVersion,path+nameUltimaVersion,carpetaDescarga));
                    archivosDescargar.addAll(buscarLibreriasActualizar(path,carpetaDescarga)); //Obtiene una lista de librerias de descargar para actualizar
                    
                    
                    List<String> excepcionesOptimizacion=new ArrayList<String>();
                    excepcionesOptimizacion.add(nameUltimaVersion);
                            
                    DescargaModel descargaModel=new DescargaModel(archivosDescargar,excepcionesOptimizacion);
                    descargaModel.empezarDescarga();
                    descargaModel.setVisible(true);
                    
                    if(!descargaModel.getDescargaCompleta())
                    {
                        DialogoCodefac.mensaje("Advertencia","El proceso de actualización fue cancelado",DialogoCodefac.MENSAJE_ADVERTENCIA);
                        System.exit(0); //Salir del sistema
                    }
                    else
                    {
                        //Quitar las etiquetas de MODO DE ACTUALIZACION y MODO FORZADO por que puede causar conflicto si el sistema se sigue actualizando sin realizar pruebas                                                
                        try {
                            propiedadesArchivo.clearProperty(ArchivoConfiguracionesCodefac.CAMPO_MODO_ACTUALIZACION);
                            propiedadesArchivo.clearProperty(ArchivoConfiguracionesCodefac.CAMPO_FORZAR_ACTUALIZACION);
                            propiedadesArchivo.save();
                        } catch (ConfigurationException ex) {
                            Logger.getLogger(ActualizarVersionCodefac.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        //Ejecutar el updater para que se encargue de hacer la actualicacion de la nueva version
                        try {
                            //String carpeta = "";
                            String pid=obtenerPIDProcesoActual();
                            //List<String> comando = Arrays.asList("java","-jar","updater.jar "+pid);
                            /**
                             * La variable pid sirve para enviar como parametro a updater.jar y luego permita matar el proceso actual si por algun motivo no se termina
                             */
                            List<String> comando = Arrays.asList("java","-jar","updater.jar",pid);
                            ProcessBuilder pb = new ProcessBuilder()
                                    .command(comando);
                            Process p = pb.start();
                            //System.exit(0); //Terminar la ejecucion del hilo actual , porque el updater se encargara de lanzar la nueva version
                            Runtime.getRuntime().halt(0);//TODO: Metodo que permite cerrar el proceso actual de forma abructa a diferencia de Systema.Exit que cierra de forma ordenada los procesos

                        } catch (IOException ex) {
                            Logger.getLogger(ActualizarVersionCodefac.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(ActualizarVersionCodefac.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        else
        {
            Logger.getLogger(Main.class.getName()).log(Level.WARNING,"No se puede descargar el archivo que contiene el numero de version");            
        }
        
    }
    
    private static Boolean verificarForzarActualizacionPropiedadesInit()
    {
        String campoForzarActualizacion=ArchivoConfiguracionesCodefac.getInstance().obtenerValor(ArchivoConfiguracionesCodefac.CAMPO_FORZAR_ACTUALIZACION);
        EnumSiNo enumForzarActualizacion=EnumSiNo.getEnumByLetra(campoForzarActualizacion);
        if(enumForzarActualizacion!=null)
        {
            if(enumForzarActualizacion.equals(EnumSiNo.SI))
            {
                return true;
            }
        }
        return false;
    }
    
    private static String obtenerPIDProcesoActual()
    {
        //Todo: Hacer una validacion que si por algun motido no puede obtener el pid del proceso no lance ninguna excepcion
        String id = ManagementFactory.getRuntimeMXBean().getName();
        String[] ids = id.split("@");
        //System.out.println(Integer.parseInt(ids[0]));
        return ids[0];
    }
    
    /**
     * @deprecated  Parece que voy a tener que actualizar todo , para evitar problemas de archivos que se actualicen de forma incorrecta
     * Lista de las librerias que tienen que descargarse de manera OBLIGATORIA
     * @param path
     * @param carpetaDescarga
     * @return 
     */
    private static List<ArchivoDescarga> buscarLibreriasActualizar(String path,String carpetaDescarga)
    {
        final String ARCHIVO_LISTA_LIBRERIAS = "librerias.txt";
        UtilidadesWeb.descargarArchivo(ARCHIVO_LISTA_LIBRERIAS, path + ARCHIVO_LISTA_LIBRERIAS, carpetaDescarga);
        //Obtiene un array con todos los nombres de las librias disponibles en linea
        List<String> libreriasOnline=UtilidadesArchivos.leerArchivoPlano(carpetaDescarga + "/" + ARCHIVO_LISTA_LIBRERIAS); 
        File archivoLibrerias=new File("lib"); //Busca la carpeta de librerias del computador
        
        //Verifica si el directorio existe obtengo una lista de las librerias actuales para comparar
        //List<String> listaLibreriasDescargadas=new ArrayList<String>();
        //if(archivoLibrerias.exists())
        //{
        //    String[] libreriasActuales=archivoLibrerias.list();
        //    listaLibreriasDescargadas = new ArrayList<String>(Arrays.asList(libreriasActuales));
        //}
        
        //Verifico cuales son las LIBRERIAS QUE FALTAN POR DESCARGAR
        HashSet<String> conjuntoOnline=new HashSet<String>(libreriasOnline);
        //HashSet<String> conjuntoDescargado=new HashSet<String>(listaLibreriasDescargadas);
        //conjuntoOnline.removeAll(conjuntoDescargado); //elimino los conjuntos que ya estan descargados y estos son los que faltan descargar
        
        ////Librerias por defecto que siempre se deben actualizar porque son parte de la funcionalidad del sistema
        ////TODO: Estar siempre alerta porque si se aumenta un modulo toca agregar en esta parte
        conjuntoOnline.add("cartera-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("compra-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("configuraciones-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("controlador-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("coreCodefacLite-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("crm-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("facturacion-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("facturacionElectronica-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("gestionAcademica-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("inventario-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("recursos-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("servicios-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("servidor-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("servidor-interfaz-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("utilidades-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("ws-client-iess-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("ws-codefac-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("ws-virtualmall-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("transporte-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("recursosWeb-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("impuestos-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("pos-1.0-SNAPSHOT.jar");
        conjuntoOnline.add("codefac.war");
       
        //Crear el map con los datos para descargar
        List<ArchivoDescarga> listLibreriasDescargar=new ArrayList<ArchivoDescarga>();
        for (Iterator<String> iterator = conjuntoOnline.iterator(); iterator.hasNext();) {
            String nombreLibreria = iterator.next();
            listLibreriasDescargar.add(new ArchivoDescarga(nombreLibreria+".new", path+nombreLibreria,"lib"));
        }
        return listLibreriasDescargar;
    }


    
}
