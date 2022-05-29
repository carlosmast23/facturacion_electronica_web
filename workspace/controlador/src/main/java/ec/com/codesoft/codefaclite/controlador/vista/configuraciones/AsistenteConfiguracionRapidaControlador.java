/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.vista.configuraciones;

import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import ec.com.codesoft.codefaclite.controlador.dialogos.EsperaSwingWorker;
import ec.com.codesoft.codefaclite.controlador.dialogos.EsperaSwingWorkerIf;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.MensajeCodefacSistema;
import ec.com.codesoft.codefaclite.controlador.utilidades.UtilidadesCodefacArchivos;
import ec.com.codesoft.codefaclite.controlador.utilidades.UtilidadesFirmaElectronica;
import ec.com.codesoft.codefaclite.controlador.vista.factura.ModelControladorAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.facturacionelectronica.ComprobanteElectronicoService;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Empresa;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmision;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Sucursal;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.Usuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.excepciones.ServicioCodefacException;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.directorio.DirectorioCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.other.session.SessionCodefacInterface;
import ec.com.codesoft.codefaclite.utilidades.email.UtilidadesCorreo;
import ec.com.codesoft.codefaclite.utilidades.fecha.UtilidadesFecha;
import ec.com.codesoft.codefaclite.utilidades.seguridad.UtilidadesEncriptar;
import ec.com.codesoft.codefaclite.ws.codefac.test.service.WebServiceCodefac;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CARLOS_CODESOFT
 */
public class AsistenteConfiguracionRapidaControlador extends ModelControladorAbstract<AsistenteConfiguracionRapidaControlador.CommonIf, AsistenteConfiguracionRapidaControlador.SwingIf, AsistenteConfiguracionRapidaControlador.WebIf> implements VistaCodefacIf {

    /**
     * Varible general que identifica cuando tab tiene la ventana //TODO: Para
     * hacer parametrizable seria bueno obtener este dato desde el mismo
     * componente
     */
    private static final Integer TOTAL_COUNT_TAB = 4;
    /**
     * Corregir problema que generar error si no estan iniciadas las variables
     * en la parte susperior en la parte de MVC
     */
    private Integer pestañaActivaTab;

    private Boolean botonTerminarHabilitar;
    
    private File fileEmpresaLogo;
    private File fileFirmaElectronica;

    //private List<ParametroCodefac> listParametroCodefac;
    
    private Empresa empresa;
    private Sucursal sucursal;
    private PuntoEmision puntoEmision;
    private Usuario usuario;
    
    private ParametroCodefac firmaArchivoParametro;
    private ParametroCodefac firmaClaveParametro;
    private ParametroCodefac firmaDuracionParametro;
    private ParametroCodefac firmaFechaEmisionParametro;
    
    private ParametroCodefac correoUsuarioParametro;
    private ParametroCodefac correoClaveParametro;
    private ParametroCodefac correoHostSmtpParametro;
    private ParametroCodefac correoPuertoParametro;
    
    private ParametroCodefac modoFacturacionParametro;
    private List<String> modoFacturacionList;
    
    private String licenciaCorreo;
    private String licenciaClave;
    

    public AsistenteConfiguracionRapidaControlador(MensajeVistaInterface mensajeVista, SessionCodefacInterface session, CommonIf interfaz, TipoVista tipoVista) {
        super(mensajeVista, session, interfaz, tipoVista);
    }

    @Override
    public void iniciar() throws ExcepcionCodefacLite, RemoteException {
        iniciarVariables();
        System.out.println("Iniciar");
    }

    private void iniciarVariables() {
        pestañaActivaTab = 0;
        botonTerminarHabilitar = false;
        
        //listParametroCodefac=new ArrayList<ParametroCodefac>();

        usuario=new Usuario();
        empresa=new Empresa();
        empresa.setContribuyenteRegimenMicroempresasBool(true);
        empresa.setObligadoLlevarContabilidadBool(false);        
        sucursal=new Sucursal();
        sucursal.setCodigoSucursal(1);
        
        puntoEmision=new PuntoEmision();
        puntoEmision.setPuntoEmision(1);
        puntoEmision.setSecuencialFactura(1);
        puntoEmision.setSecuencialGuiaRemision(1);
        puntoEmision.setSecuencialGuiaRemisionInterna(1);
        puntoEmision.setSecuencialLiquidacionCompra(1);
        puntoEmision.setSecuencialNotaCredito(1);
        puntoEmision.setSecuencialNotaDebito(1);
        puntoEmision.setSecuencialNotaVenta(1);
        puntoEmision.setSecuencialNotaVentaInterna(1);
        puntoEmision.setSecuencialRetenciones(1);
        
        //Iniciar Parametros
        firmaArchivoParametro=new ParametroCodefac(ParametroCodefac.NOMBRE_FIRMA_ELECTRONICA);
        firmaClaveParametro=new ParametroCodefac(ParametroCodefac.CLAVE_FIRMA_ELECTRONICA);
        firmaDuracionParametro=new ParametroCodefac(ParametroCodefac.FIRMA_TIEMPO_EXPIRACION_AÑOS,"2");
        firmaFechaEmisionParametro=new ParametroCodefac(ParametroCodefac.FIRMA_FECHA_EMISION);
        
        //Iniciar Parametro correo por defecto        
        try {
            String claveSinEncriptar=UtilidadesEncriptar.desencriptar(ParametrosSistemaCodefac.CORREO_DEFECTO_CLAVE, ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
            correoUsuarioParametro = new ParametroCodefac(ParametroCodefac.CORREO_USUARIO, ParametrosSistemaCodefac.CORREO_DEFECTO_USUARIO);
            correoClaveParametro = new ParametroCodefac(ParametroCodefac.CORREO_CLAVE, claveSinEncriptar);
            correoHostSmtpParametro = new ParametroCodefac(ParametroCodefac.SMTP_HOST, ParametrosSistemaCodefac.CORREO_DEFECTO_HOST);
            correoPuertoParametro = new ParametroCodefac(ParametroCodefac.SMTP_PORT, ParametrosSistemaCodefac.CORREO_DEFECTO_PUERTO);
        } catch (Exception ex) {
            Logger.getLogger(AsistenteConfiguracionRapidaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        modoFacturacionParametro=new ParametroCodefac(ParametroCodefac.MODO_FACTURACION, ComprobanteElectronicoService.MODO_PRUEBAS);
        
        //Datos de la lista del modo de facturacion
        modoFacturacionList=new ArrayList<String>();
        modoFacturacionList.add(ComprobanteElectronicoService.MODO_PRUEBAS);
        modoFacturacionList.add(ComprobanteElectronicoService.MODO_PRODUCCION);
        
        String formatoFecha="dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(formatoFecha);
        String fechaStr=simpleDateFormat.format(UtilidadesFecha.getFechaHoy());
        firmaFechaEmisionParametro.valor=fechaStr;

        //sucursal.get
        //datosPrueba();
    }
    
    private void datosPrueba()
    {
        empresa.setIdentificacion("1724218951001");
        empresa.setRazonSocial("SANCHEZ CARLOS");
        sucursal.setDirecccion("QUITO");
        sucursal.setDirecccion("QUITO");
        usuario.setNick("carlos");
        usuario.setClave("1234");
        usuario.setRepetirClave("1234");
        licenciaCorreo="codesoft_desarrollo@gmail.com";
        licenciaClave="123456";
    }

    @Override
    public void nuevo() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabar() throws ExcepcionCodefacLite, RemoteException {

    }

    @Override
    public void editar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() throws ExcepcionCodefacLite, RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        
        //empresa = new Empresa();
        //empresa.setIdentificacion("1724218951");
        //empresa.setObligadoLlevarContabilidadBool(true);
        //empresa=new Empresa("1717171717");
        //System.out.println("Limpiar");
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getPerfilesPermisos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InterfaceModelFind obtenerDialogoBusqueda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cargarDatosPantalla(Object entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void listenerBtnAvanzarPantalla() {
        //Habilitar el boton de terminar
        if (pestañaActivaTab == TOTAL_COUNT_TAB - 2) {
            botonTerminarHabilitar = true;
        }

        if (pestañaActivaTab < TOTAL_COUNT_TAB - 1) {
            pestañaActivaTab++;
            getInterfaz().activarPestañaActiva(pestañaActivaTab);
        }

    }

    public void listenerBtnAtrasPantalla() {
        if (pestañaActivaTab > 0) {
            pestañaActivaTab--;
            getInterfaz().activarPestañaActiva(pestañaActivaTab);
        }

    }
    
    private void seterDatos()
    {
        if(firmaClaveParametro!=null)
        {
            //Encriptar la clave antes de enviar a grabar
            String claveEncriptada=UtilidadesEncriptar.encriptar(firmaClaveParametro.valor,ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
            firmaClaveParametro.valor=claveEncriptada;
        }
        
        if(correoClaveParametro!=null)
        {
            String claveEncriptada=UtilidadesEncriptar.encriptar(correoClaveParametro.valor, ParametrosSistemaCodefac.LLAVE_ENCRIPTAR);
            correoClaveParametro.valor=claveEncriptada;
        }
    }

    public void listenerBtnTerminar() {
        //TODO: Revisar para mejorar esta parte para que en la parte derecha le aparesca cuales son los campos faltantes
        if (getInterfaz().ejecutarValidadoresVista()) 
        {
            try {
                seterDatos();
                
                empresa=ServiceFactory.getFactory().getEmpresaServiceIf().grabarConfiguracionInicial(
                        empresa, 
                        sucursal, 
                        puntoEmision, 
                        usuario,
                        licenciaCorreo,
                        licenciaClave, 
                        generarListaParametros());
                //Subo los archivos despues de grabar por que primero necesitaba el path donde van a estar los recursos
                subirArchivosServidor();                
                mostrarMensaje(new CodefacMsj("Datos creados correctamente. \n- Utilice su nuevo usuario para ingresar al sistema", CodefacMsj.TipoMensajeEnum.CORRECTO));
                getInterfaz().cerarSession();

            } catch (ServicioCodefacException ex) {
                Logger.getLogger(AsistenteConfiguracionRapidaControlador.class.getName()).log(Level.SEVERE, null, ex);
                mostrarMensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ERROR));
            } catch (RemoteException ex) {
                Logger.getLogger(AsistenteConfiguracionRapidaControlador.class.getName()).log(Level.SEVERE, null, ex);
            }

        } 
        else 
        {
            mostrarMensaje(new CodefacMsj("Faltan campos requeridos por llenar", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
        }

    }
    
    private List<ParametroCodefac> generarListaParametros()
    {
        List<ParametroCodefac> parametros=new ArrayList();
        parametros.add(firmaArchivoParametro);
        parametros.add(firmaClaveParametro);
        parametros.add(firmaDuracionParametro);
        parametros.add(firmaFechaEmisionParametro);
        
        parametros.add(correoUsuarioParametro);
        parametros.add(correoClaveParametro);
        parametros.add(correoHostSmtpParametro);
        parametros.add(correoPuertoParametro);
        
        parametros.add(modoFacturacionParametro);
        
        return parametros;
    }
    
    private void subirArchivosServidor()
    {
        UtilidadesCodefacArchivos.subirArchivoServidor(empresa, fileEmpresaLogo, DirectorioCodefac.IMAGENES);
        
        UtilidadesCodefacArchivos.subirArchivoServidor(empresa, fileFirmaElectronica, DirectorioCodefac.CONFIGURACION);
                
    }
    
    

    public void listenerBtnBuscarFirma() 
    {
        File file=getInterfaz().buscarFileFirmaElectronica();
        if(file!=null)
        {
            //Grabo el nombre de la imagen
            firmaArchivoParametro.valor=file.getName();
            fileFirmaElectronica=file;
            
        }
    }
    
    public void listenerBtnBuscarLogoEmpresa()
    {
        File file=getInterfaz().buscarFileLogoEmpresa();
        if(file!=null)
        {
            //Grabo el nombre de la imagen
            empresa.setImagenLogoPath(file.getName());
            fileEmpresaLogo=file;
            
        }
    }
    
    public void listenerVerificarDatosFirma()
    {
        if(firmaClaveParametro==null || firmaArchivoParametro==null || fileFirmaElectronica==null)
        {
            mostrarMensaje(new CodefacMsj("Por favor ingrese los datos de la firma para validar", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            return;
        }
        
        try {
            UtilidadesFirmaElectronica.verificarFirmaElectronica(empresa, firmaClaveParametro.valor, firmaArchivoParametro.valor,fileFirmaElectronica.toPath());
            mostrarMensaje(new CodefacMsj("Datos de la firma correctos",CodefacMsj.TipoMensajeEnum.CORRECTO));
        } catch (ServicioCodefacException ex) {
            mostrarMensaje(new CodefacMsj(ex.getMessage(), CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
            //Limpiar la clave si la vaidacion falla
            if(firmaClaveParametro!=null)
            {
                firmaClaveParametro.valor="";
            }
            Logger.getLogger(AsistenteConfiguracionRapidaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listenerVerificarCorreo()
    {                
        Object[] parametros={correoUsuarioParametro.valor,correoClaveParametro.valor,correoHostSmtpParametro.valor,correoPuertoParametro.valor};
        EsperaSwingWorker esperaDialog=new EsperaSwingWorker("Validando Correo",parametros,new EsperaSwingWorkerIf() 
        {
            @Override
            public void ejecutarTarea(Object[] parametros) {
                validarCorreo(
                        (String) parametros[0],
                        (String) parametros[1],
                        (String) parametros[2],
                        (String) parametros[3]
                );
            }
        });
        
        esperaDialog.execute();
        //validarCorreo();
        
    }
    
    private void validarCorreo(String usuario,String clave,String host,String puertoStr)
    {
        if(usuario!=null &&
                clave!=null &&
                host!=null &&
                puertoStr!=null )
        {
            try {
                /*System.out.println("usuario: "+correoUsuarioParametro.valor);
                System.out.println("clave: "+correoClaveParametro.valor);
                System.out.println("host: "+correoHostSmtpParametro.valor);
                System.out.println("puerto: "+correoPuertoParametro.valor);*/
                
                UtilidadesCorreo.verificarCredencialesCorreoCodefa(
                        usuario,
                        clave,
                        host,
                        Integer.parseInt(puertoStr));
                
                mostrarMensaje(new CodefacMsj("Datos correctos del sistema", CodefacMsj.TipoMensajeEnum.CORRECTO));
            } catch (Exception ex) {
                Logger.getLogger(AsistenteConfiguracionRapidaControlador.class.getName()).log(Level.SEVERE, null, ex);
                mostrarMensaje(new CodefacMsj(ex.getMessage(),CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                
            }
        }
        else
        {
            mostrarMensaje(new CodefacMsj("Ingrese todos los campos del correo", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
        }
    }
    
    public void listenerVerificarLicencia()
    {
        if(licenciaCorreo==null || licenciaClave==null )
        {
            //Si no tiene los datos ingresados no valida
            return;
        }
        
        Object[] parametro={licenciaCorreo,licenciaClave};
        EsperaSwingWorker esperaSwingWorker=new EsperaSwingWorker("Verificando licencia Codefac",parametro,new EsperaSwingWorkerIf() {
            @Override
            public void ejecutarTarea(Object[] parametros) {
                if(WebServiceCodefac.verificarCredenciales((String)parametros[0],(String)parametros[1]))
                {
                    mostrarMensaje(new CodefacMsj("Los datos de la licencia son correctos", CodefacMsj.TipoMensajeEnum.CORRECTO));
                }
                else
                {
                    mostrarMensaje(new CodefacMsj("Los datos de la licencia son incorrectos", CodefacMsj.TipoMensajeEnum.ADVERTENCIA));
                }
            }
        });        
        esperaSwingWorker.execute();
    }
    

    ///////////////////////////////////////////////////////////////////////////
    //                  METODOS GET AND SET
    ///////////////////////////////////////////////////////////////////////////
    public Boolean getBotonTerminarHabilitar() {
        return botonTerminarHabilitar;
    }

    public void setBotonTerminarHabilitar(Boolean botonTerminarHabilitar) {
        this.botonTerminarHabilitar = botonTerminarHabilitar;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public PuntoEmision getPuntoEmision() {
        return puntoEmision;
    }

    public void setPuntoEmision(PuntoEmision puntoEmision) {
        this.puntoEmision = puntoEmision;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public File getFileFirmaElectronica() {
        return fileFirmaElectronica;
    }

    public void setFileFirmaElectronica(File fileFirmaElectronica) {
        this.fileFirmaElectronica = fileFirmaElectronica;
    }

    public ParametroCodefac getFirmaArchivoParametro() {
        return firmaArchivoParametro;
    }

    public void setFirmaArchivoParametro(ParametroCodefac firmaArchivoParametro) {
        this.firmaArchivoParametro = firmaArchivoParametro;
    }

    public ParametroCodefac getFirmaClaveParametro() {
        return firmaClaveParametro;
    }

    public void setFirmaClaveParametro(ParametroCodefac firmaClaveParametro) {
        this.firmaClaveParametro = firmaClaveParametro;
    }

    public ParametroCodefac getFirmaDuracionParametro() {
        return firmaDuracionParametro;
    }

    public void setFirmaDuracionParametro(ParametroCodefac firmaDuracionParametro) {
        this.firmaDuracionParametro = firmaDuracionParametro;
    }

    public ParametroCodefac getFirmaFechaEmisionParametro() {
        return firmaFechaEmisionParametro;
    }

    public void setFirmaFechaEmisionParametro(ParametroCodefac firmaFechaEmisionParametro) {
        this.firmaFechaEmisionParametro = firmaFechaEmisionParametro;
    }

    public ParametroCodefac getCorreoUsuarioParametro() {
        return correoUsuarioParametro;
    }

    public void setCorreoUsuarioParametro(ParametroCodefac correoUsuarioParametro) {
        this.correoUsuarioParametro = correoUsuarioParametro;
    }

    public ParametroCodefac getCorreoClaveParametro() {
        return correoClaveParametro;
    }

    public void setCorreoClaveParametro(ParametroCodefac correoClaveParametro) {
        this.correoClaveParametro = correoClaveParametro;
    }

    public ParametroCodefac getCorreoHostSmtpParametro() {
        return correoHostSmtpParametro;
    }

    public void setCorreoHostSmtpParametro(ParametroCodefac correoHostSmtpParametro) {
        this.correoHostSmtpParametro = correoHostSmtpParametro;
    }

    public ParametroCodefac getCorreoPuertoParametro() {
        return correoPuertoParametro;
    }

    public void setCorreoPuertoParametro(ParametroCodefac correoPuertoParametro) {
        this.correoPuertoParametro = correoPuertoParametro;
    }

    public String getLicenciaCorreo() {
        return licenciaCorreo;
    }

    public void setLicenciaCorreo(String licenciaCorreo) {
        this.licenciaCorreo = licenciaCorreo;
    }

    public String getLicenciaClave() {
        return licenciaClave;
    }

    public void setLicenciaClave(String licenciaClave) {
        this.licenciaClave = licenciaClave;
    }

    public ParametroCodefac getModoFacturacionParametro() {
        return modoFacturacionParametro;
    }

    public void setModoFacturacionParametro(ParametroCodefac modoFacturacionParametro) {
        this.modoFacturacionParametro = modoFacturacionParametro;
    }

    public List<String> getModoFacturacionList() {
        return modoFacturacionList;
    }

    public void setModoFacturacionList(List<String> modoFacturacionList) {
        this.modoFacturacionList = modoFacturacionList;
    }
    
    
    
    

    ///////////////////////////////////////////////////////////////////////////
    //             METODOS QUE CONTIENEN INTERFACES PARA LA IMPLEMTACION
    ///////////////////////////////////////////////////////////////////////////
    public interface CommonIf {

        //TODO: Implementacion de todas las interfaces comunes
        public void activarPestañaActiva(Integer indiceTab);

        public Boolean ejecutarValidadoresVista();
        
        public File buscarFileLogoEmpresa();
        
        public File buscarFileFirmaElectronica();
        
        public void cerarSession();
    }

    public interface SwingIf extends CommonIf {
        //TODO: Implementacion de las interfaces solo necesarias para Swing
    }

    public interface WebIf extends CommonIf {
        //TODO: Implementacion de las interafaces solo para la web
    }

}
