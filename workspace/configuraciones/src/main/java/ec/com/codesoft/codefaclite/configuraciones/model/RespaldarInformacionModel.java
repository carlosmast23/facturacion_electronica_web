/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import ec.com.codesoft.codefaclite.configuraciones.panel.RespaldarInformacionPanel;
import ec.com.codesoft.codefaclite.controlador.dialog.DialogoCodefac;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.controlador.core.swing.GeneralPanelInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.RespaldosModelUtilidades;
import ec.com.codesoft.codefaclite.controlador.vistas.core.SpinnerBinding;
import ec.com.codesoft.codefaclite.recursos.RecursoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.comprobantesElectronicos.CorreoCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.ParametroCodefacServiceIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.servicios.RecursosServiceIf;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.list.UtilidadesLista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author CodesoftDesarrollo 1
 */
public class RespaldarInformacionModel extends RespaldarInformacionPanel
{
    //private EnumSiNo enumSeleccionado;
    private List<EnumSiNo> listEnumSiNo;    
    //private Integer horaProgramada;
    
    private ParametroCodefac parametroHoraProgramada;
    private ParametroCodefac parametroRespaldarSalir;
    
    
    
    //private Path origenPath;
    //private Path destinoPath;
    private JFileChooser jFileChooser;
    //private String ubicacionRespaldo;
    //private String nombreCarpetaRelpaldo;
    //private ParametroCodefacServiceIf parametroCodefacServiceIf;
    //private Map<String, ParametroCodefac> parametro;
    //private Boolean existeDirectorio = false;
           
    @Override
    public void iniciar() throws ExcepcionCodefacLite 
    {
        //this.parametroCodefacServiceIf = ServiceFactory.getFactory().getParametroCodefacServiceIf();
        //this.ubicacionRespaldo = "";
        this.jFileChooser = new JFileChooser();
        this.jFileChooser.setDialogTitle("Elegir ubicación para respaldar información");
        this.jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
        agregarListener();
        iniciarValores();
        //obtenerUbicacionCarpetaRespaldo();
        cargarValoresGrabados();
    }
    
    private void cargarValoresGrabados()
    {
        String ubicacionRespaldo=RespaldosModelUtilidades.obtenerUbicacionCarpetaRespaldo(session.getEmpresa(),false);
        getTxtUbicacionRespaldo().setText(ubicacionRespaldo);        
        
        ParametroCodefacServiceIf service=ServiceFactory.getFactory().getParametroCodefacServiceIf();
        try {
            parametroHoraProgramada=service.getParametroByNombre(ParametroCodefac.ParametrosRespaldoDB.DB_RESPALDO_HORA_PROGRAMADA, session.getEmpresa());
            parametroRespaldarSalir=service.getParametroByNombre(ParametroCodefac.ParametrosRespaldoDB.DB_RESPALDO_AUTOMATICO_SALIR, session.getEmpresa());
            
            //Si los valores son nulos entonces creo las variables en balnco
            if(parametroHoraProgramada==null)
            {
                parametroHoraProgramada=new ParametroCodefac(ParametroCodefac.ParametrosRespaldoDB.DB_RESPALDO_HORA_PROGRAMADA,"");
                parametroHoraProgramada.setEmpresa(session.getEmpresa());
            }
            
            if(parametroRespaldarSalir==null)
            {
                parametroRespaldarSalir=new ParametroCodefac(ParametroCodefac.ParametrosRespaldoDB.DB_RESPALDO_AUTOMATICO_SALIR,"");
                parametroRespaldarSalir.setEmpresa(session.getEmpresa());
            }
            
        } catch (RemoteException ex) {
            Logger.getLogger(RespaldarInformacionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void iniciarValores()
    {
        listEnumSiNo=Arrays.asList(EnumSiNo.NO,EnumSiNo.SI);
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite {
            /**
             * Grabar la ubicación de respaldos de la información
             */
            //Boolean respuesta = null;
            try{
                if(!getTxtUbicacionRespaldo().getText().equals(""))
                {
                    ParametroCodefac parametroDirectorio = this.session.getParametrosCodefac().get(ParametroCodefac.DIRECTORIO_RESPALDO);
                    //Si no existe el parametro entonces creo uno nuevo
                    if(parametroDirectorio==null)
                    {
                        parametroDirectorio=new ParametroCodefac(ParametroCodefac.DIRECTORIO_RESPALDO);
                        parametroDirectorio.setEmpresa(session.getEmpresa());
                    }
                    
                    parametroDirectorio.setValor(getTxtUbicacionRespaldo().getText());
                                        
                    ParametroCodefacServiceIf parametroCodefacServiceIf= ServiceFactory.getFactory().getParametroCodefacServiceIf();
                    List<ParametroCodefac> parametrosList=Arrays.asList(parametroDirectorio,parametroHoraProgramada,parametroRespaldarSalir);
                    parametroCodefacServiceIf.editarParametros(parametrosList);                        
                    
                    DialogoCodefac.mensaje("Actualizado datos", "Los datos de los parametros fueron actualizados", DialogoCodefac.MENSAJE_CORRECTO);
                    dispose();
                }
                else
                {
                    DialogoCodefac.mensaje("Advertencia", "Necesita establecer una ruta para los respaldos", DialogoCodefac.MENSAJE_ADVERTENCIA);
                }
            }
            catch(RemoteException exc)
            {
                System.out.println("Error guardando parametro de path respaldo");
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
    public String getNombre() {
        return "Respaldar Información";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        Map<Integer, Boolean> permisos = new HashMap<Integer, Boolean>();
        permisos.put(GeneralPanelInterface.BOTON_GRABAR, true);
        permisos.put(GeneralPanelInterface.BOTON_AYUDA, true);
        return permisos;
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void copiarDirectorio(InputStream origen, File destino) throws IOException 
    {
        FileUtils.copyInputStreamToFile(origen,destino);        
    }
    
    private void copiarArchivo(File source, File target) throws IOException 
    {        
        try 
        (
            InputStream in = new FileInputStream(source);
            OutputStream out = new FileOutputStream(target)
        ) 
        {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) 
            {
                out.write(buf, 0, length);
            }
        }
    }
    
    public static void copiarArchivoXml(String pathOrigen, String pathDestino) {
        try {
            Path origenPath = FileSystems.getDefault().getPath(pathOrigen);
            Path destinoPath = FileSystems.getDefault().getPath(pathDestino);
            
            File file = destinoPath.toFile();
            //crear toda la ruta si no existe
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                //file.mkdir();
            }    
            Files.copy(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            
        }
    }
    
    public void agregarListener()
    {   
        getBtnRespaldar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    RespaldosModelUtilidades.generarRespaldoUbicacion(getChkEnviarCorreo().isSelected(),session.getEmpresa(),null,false);
                    DialogoCodefac.mensaje("Correcto","El proceso termino correctamente",DialogoCodefac.MENSAJE_CORRECTO);
                    //generarRespaldoUbicacion(getChkEnviarCorreo().isSelected());
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(RespaldarInformacionModel.class.getName()).log(Level.SEVERE, null, ex);
                    DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                }
            }    
        });
        
        getBtnGuardarLocalizacion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleccion = jFileChooser.showDialog(null, "Seleccionar");
                switch (seleccion) {
                    case JFileChooser.APPROVE_OPTION:
                        String ubicacionRespaldo = "" + jFileChooser.getSelectedFile();
                        getTxtUbicacionRespaldo().setText(""+ubicacionRespaldo);
                        DialogoCodefac.mensaje("Advertencia", "Debe guardar la nueva ubicación para que se actualice el lugar de respaldo", DialogoCodefac.MENSAJE_ADVERTENCIA);
                        break;
                    case JFileChooser.CANCEL_OPTION:

                        break;
                    case JFileChooser.ERROR_OPTION:

                        break;
                }
            }
        });
        
        getBtnEnviarBDCorreoPersonalizado().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    
                    String correoEnviar=getTxtCorreoPersonalizado().getText();
                    RespaldosModelUtilidades.generarRespaldoUbicacion(true,session.getEmpresa(),correoEnviar,false);
                    DialogoCodefac.mensaje("Correcto","El proceso termino correctamente",DialogoCodefac.MENSAJE_CORRECTO);
                    //generarRespaldoUbicacion(getChkEnviarCorreo().isSelected());
                } catch (ServicioCodefacException ex) {
                    Logger.getLogger(RespaldarInformacionModel.class.getName()).log(Level.SEVERE, null, ex);
                    DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                }
            }
        });
    }
    
    /**
     * Envia un respaldo al correo de la misma empresa con el archivo adjunto
     */
    public void enviarRespaldoCorreoEmpresa(File fileRespaldo)
    {
        try {
            String fechaStr=ParametrosSistemaCodefac.FORMATO_ESTANDAR_FECHA.format(UtilidadesFecha.getFechaHoy());
            CorreoCodefac correoCodefac = new CorreoCodefac();
            String tituloCorreo="Respaldo BaseDatos "+fechaStr;
            String mensajeCorreo="El respaldo de la base de datos de Codefac de la fecha "+fechaStr+" lo puede descargar como archivo adjunto";
            String correoEmpresa=session.getParametrosCodefac().get(ParametroCodefac.CORREO_USUARIO).valor;
            List correosList=Arrays.asList(correoEmpresa);
            Map<String,String> mapArchivosAdjuntos=Collections.singletonMap(fileRespaldo.getName(),fileRespaldo.getPath());
            
            correoCodefac.enviarCorreo(session.getEmpresa(),mensajeCorreo,tituloCorreo,correosList,mapArchivosAdjuntos);
        } catch (CorreoCodefac.ExcepcionCorreoCodefac ex) {
            Logger.getLogger(RespaldarInformacionModel.class.getName()).log(Level.SEVERE, null, ex);
            DialogoCodefac.mensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
        }
    }
    
    /**
     * Genera el respaldo en una ubicación del disco previamente seleccionado
     */
    /*public void generarRespaldoUbicacion(Boolean enviarCorreo)
    {
        try
                {
                    
                    if(!ubicacionRespaldo.equals(""))
                    {
                        crearNombreCarpetaRespaldo();
                        
                        RecursosServiceIf service=ServiceFactory.getFactory().getRecursosServiceIf();                        
                        InputStream inputDb = RemoteInputStreamClient.wrap(service.getDataBaseResources());                        
                        
                        
                        destinoPath = FileSystems.getDefault().getPath(ubicacionRespaldo+"\\"+nombreCarpetaRelpaldo+".zip");
                        //File recursosDirectorio = origenPath.toFile();
                        File destinoDirectorio = destinoPath.toFile();
                        //Utilizar una funciona estandar en utilidades
                        copiarDirectorio(inputDb, destinoDirectorio);
                        
                        if(enviarCorreo)
                        {
                            enviarRespaldoCorreoEmpresa(destinoDirectorio);
                        }
                        
                        DialogoCodefac.mensaje("Correcto","El proceso termino correctamente",DialogoCodefac.MENSAJE_CORRECTO);
                    }
                    else
                    {
                        DialogoCodefac.mensaje("Advertencia", "Debe seleccionar una ubicación para los respaldos", DialogoCodefac.MENSAJE_ADVERTENCIA);
                    }
                }catch(Exception exc)
                {
                    DialogoCodefac.mensaje(new CodefacMsj(exc.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                    exc.printStackTrace();                    
                    System.out.println("Error al respaldar información: " + exc);
                }
    }*/
    
    /*public void crearNombreCarpetaRespaldo()
    {
        this.nombreCarpetaRelpaldo = "" + new Date();
        this.nombreCarpetaRelpaldo = this.nombreCarpetaRelpaldo.replaceAll(" ","");
        this.nombreCarpetaRelpaldo = this.nombreCarpetaRelpaldo.replaceAll(":","");
    }*/
    
    /*public void obtenerUbicacionCarpetaRespaldo()
    {
        try {
            ParametroCodefac parametroDirectorioRespaldo= ServiceFactory.getFactory().getParametroCodefacServiceIf().getParametroByNombre(ParametroCodefac.DIRECTORIO_RESPALDO, session.getEmpresa());
            //this.parametro = this.parametroCodefacServiceIf.getParametrosMap(session.getEmpresa());
            //ParametroCodefac p = this.parametro.get(ParametroCodefac.DIRECTORIO_RESPALDO);
            if(parametroDirectorioRespaldo!=null && parametroDirectorioRespaldo.getValor() != null)
            {
                this.ubicacionRespaldo = parametroDirectorioRespaldo.getValor();
                getTxtUbicacionRespaldo().setText(parametroDirectorioRespaldo.getValor());
                
            }
            else
            {
                getTxtUbicacionRespaldo().setText("");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(RespaldarInformacionModel.class.getName()).log(Level.SEVERE, null, ex);
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

    
    /**
     * =========================================================================
     *                              GET AND SET
     * =========================================================================
     */


    public List<EnumSiNo> getListEnumSiNo() {
        return listEnumSiNo;
    }

    public void setListEnumSiNo(List<EnumSiNo> listEnumSiNo) {
        this.listEnumSiNo = listEnumSiNo;
    }

    public ParametroCodefac getParametroHoraProgramada() {
        return parametroHoraProgramada;
    }

    public void setParametroHoraProgramada(ParametroCodefac parametroHoraProgramada) {
        this.parametroHoraProgramada = parametroHoraProgramada;
    }

    public ParametroCodefac getParametroRespaldarSalir() {
        return parametroRespaldarSalir;
    }

    public void setParametroRespaldarSalir(ParametroCodefac parametroRespaldarSalir) {
        this.parametroRespaldarSalir = parametroRespaldarSalir;
    }

    
    
    
}
