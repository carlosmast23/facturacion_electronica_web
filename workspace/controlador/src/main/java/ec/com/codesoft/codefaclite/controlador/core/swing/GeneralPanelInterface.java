/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.core.swing;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.controlador.vistas.core.components.ComponentBindingAbstract;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.ObserverUpdateInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.corecodefaclite.interfaces.VistaCodefacIf;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ComponenteSecundarioAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ConsolaGeneral;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.jdesktop.swingx.VerticalLayout;

/**
 *
 * @author
 */
public abstract class GeneralPanelInterface extends javax.swing.JInternalFrame implements VistaCodefacIf
{
    public static final int BOTON_GRABAR = 1;
    public static final int BOTON_ELIMINAR = 2;
    public static final int BOTON_IMPRIMIR = 3;
    public static final int BOTON_AYUDA = 4;
    public static final int BOTON_NUEVO = 5;
    public static final int BOTON_REFRESCAR = 6;
    public static final int BOTON_BUSCAR = 7;
    
    /**
     * Lista que me permite almacenar la lista de componentes binding
     */
    public List<ComponentBindingAbstract> bindingComponentList;
    
    /**
     * Rererencia que va a almacenar cual es el dialogo principal  de la aplicacion para interfacturar con los campos de busqueda
     */
   // private BuscarDialogoModel dialogoBusqueda;
    
    public InterfazComunicacionPanel panelPadre;
    public ConsolaGeneral consola;
    
    /**
     * Variables para corregir bugs de los internal frames
     */
    public boolean sinAcciones=false;
    public boolean formularioCerrando=false;
    public boolean modoDialogo=false;
    
    /**
     * Evento propietario que contiene el metodo para poder utilizar dialogos
     */
    public ObserverUpdateInterface formOwner;
    
    /**
     * Variable que setea cual fue el formulario padre para devolver el focus
     * //TODO: Ver alguna forma de optimizar esta parte
     */
    public GeneralPanelInterface formOwnerFocus;
    
    public static final String ESTADO_GRABAR="G";
    public static final String ESTADO_EDITAR="E";
    
    /**
     * Variable que contiene el estado actual del formulario para que el desarrollado pueda consultar
     */
    public String estadoFormulario;
    public EstadoFormularioEnum estadoFormularioEnum;
    
    
    
   
    /**
     * Lista que me permite grabar componentes que se desean excluir para no tomar en cuenta para la validacion de no salir si existen datos ingresados 
     */
    public List<JComponent> listaExclusionComponentes;
    
    /**
     * Variable para controlar si desea activar o desactivar esta validacion de cuando existen datos ingresados
     * Esto deberia desactivarse por ejemplo para reportes
     */
    public Boolean validacionDatosIngresados;
    
    /**
     * Variable de utilidad solo para facilitar el trabajo para obtener la referencia al objecto actual desde una clase interna o anonima
     */
    public GeneralPanelInterface formularioActual;
    
    /**
     * Map que me permite tener almacenados si algunos campos tienen valores por defecto para la validacion de datos ingresados
     */
    public Map<JComponent,Object> mapDatosIngresadosDefault;
    
    /**
     * Lista de botones auxiliares para la barra lateral izquierda de la pantalla
     */
    private Map<String,List<Component>> mapComponentesLaterales;
    /**
     * Map para saber que componentes deben estar visibles y cuales no
     */
    @Deprecated //TODO: Ver aluna forma de usar solo el el mapComponentesLaterales , por que este map solo se usa para identificar una propiedad del map y se vuelve dificil de entender el codigo
    private Map<String,Boolean> mapComponentesLateralesVisibles;
    /**
     * El siguiente map me permite grabar las instancias de cada componente de grupo que contiene el panel lateral secuandario para poder manipular desde los formularios
     * key = titulo pantall
     * value= instancia del panel de las categorias
     */
    private Map<String,JXTaskPane> mapPanelesSecundariosBuild;
    
    //Variable auxiliar que me va a permitir elegir cuando se tiene que procesar algo en modo forzado
    public Boolean procesarModoForzado=false;

    public GeneralPanelInterface() {
        this.formularioActual=this;
        this.listaExclusionComponentes=new ArrayList<JComponent>();
        this.mapDatosIngresadosDefault=new HashMap<JComponent,Object>();
        this.mapComponentesLaterales=new HashMap<String,List<Component>>();
        this.mapComponentesLateralesVisibles=new HashMap<String,Boolean>();
        
        this.validacionDatosIngresados=true;     
        this.bindingComponentList=new ArrayList<ComponentBindingAbstract>();
    }
    
    
    
    
    /**
     * Metodo que se ejecuta despues de presionar el boton de buscar     * 
     * Es recomendable usar este metodo solo para buscar datos de la propia clase
     */
    public void buscar() throws ExcepcionCodefacLite,RemoteException
    {
        //Esto lo pongo de esta manera porque Codefac construye automaticamente el metodo buscar desde el controlador
        //y ya no ahy necesidad de implementar, pero si en ultimo caso el usuario tiene la necesidad de escribir su
        //metodo personalizado de busqueda puede sobrescribir este metodo 
        throw new UnsupportedOperationException("Metodo no implementado"); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    /**
     * Metodo que establece si deseas controlar el ciclo de vida de la pantalla
     * o que se controle automaticamente.
     * Ejemplo: Despues de guardar se limpian automaticamentes los datos
     */
    public Boolean cicloVida=true;
    
    
    /**
     * Variable que permite grabar los componentes que estaban desactivos
     * para cuando se vuelvan a activar el formulario conserver el estado
     * de los que estaban desactivados antes de desactivar todo
     */
    private List<Component> componentesTemporales;
    
    /**
     * Metodo para establecer un icono por defecto cuando este cargando la pantalla
     */
    
        
    public void estadoCargando()
    {
            componentesTemporales=new ArrayList<Component>();

            setEnableRec(this,false);
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));


    }
    
    private void setEnableRec(Component container, boolean enable){
        
        if(!container.equals(this)) //para que no desactive el panel padre
        {

            if (!enable) {
                if (!container.isEnabled()) {
                    componentesTemporales.add(container);
                }
                container.setEnabled(enable);
            }
            else
            {
                if(!componentesTemporales.contains(container))
                    container.setEnabled(enable);
            }
        }
        


    try {
        Component[] components= ((Container) container).getComponents();
        for (int i = 0; i < components.length; i++) {
           
            setEnableRec(components[i], enable);
        }
    } catch (ClassCastException e) {

    }
}

    public void estadoNormal()
    {
        setEnableRec(this,true);
        this.setCursor(Cursor.getDefaultCursor());
        //this.setVisible(true);
    }

     /**
     * Metodo que me permite saber si existe datos ingresados y no existe nongun dato y puedo salir sin grabar
     * @return 
     */
    public boolean salirSinGrabar() {
        //Esta validacion solo debe funcionar en el estado de grabar y si esta activa la opcion de validar
        if(estadoFormulario!=ESTADO_GRABAR || !validacionDatosIngresados)
        {
            return true;
        }
        
        //TODO: Ver si se puede validar para otros componentes como combox
        Field[] campos = getClass().getSuperclass().getDeclaredFields();
        for (Field campo : campos) {
            
            if (campo.getType().equals(JTextField.class) || campo.getType().equals(JTextArea.class)) {
                try {
                    campo.setAccessible(true);
                    Object campoSeleccionado= campo.get(this);
                    
                    //Verificar si el campo no se encuentra dentro de la lista de exclusiones
                    if(listaExclusionComponentes.contains(campoSeleccionado))
                    {
                        continue;
                    }
                    
                    JTextComponent campoTexto = (JTextComponent) campoSeleccionado;
                    Object valorDefecto=mapDatosIngresadosDefault.get(campoTexto);
                    String valorCampo=campoTexto.getText().trim();
                    
                    //Verifico si tiene ingresado cualquier valor que no sea por defecto significa que cambio el valor
                    if(valorDefecto!=null)
                    {
                        if (!valorDefecto.toString().equals(valorCampo) ) {
                            if(!valorCampo.equals(""))
                            {
                                return false;
                            }
                        }
                    }
                    else
                    {
                        if (!valorCampo.equals("")) {
                            return false;
                        }
                    }


                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GeneralPanelInterface.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GeneralPanelInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return true;

    }

    /**
     * Agrega componentes laterales para que se muestren en la parte derecha de la pantalla
     * @param nombreCategoria
     * @param componente 
     */
    public void agregarComponenteLateral(String nombreCategoria,Component componente)
    {
        List<Component> componentes=mapComponentesLaterales.get(nombreCategoria);
        if(componentes==null)
        {
            componentes=new ArrayList<Component>();
            componentes.add(componente);
            mapComponentesLaterales.put(nombreCategoria, componentes);
        }
        else
        {
            componentes.add(componente);
        }
    }
    
    /**
     * Metodo que recostruye las interfaces con botones adicionales si existen para construir un menu lateral
     */
    public void reconstruirPantalla()
    {
        buscarComponentesSecundarios();
        
        //Validacion que cuando no existe componentes laterales no reconstruye nada
        if(this.mapComponentesLaterales.size()==0)
        {
            return ;
        }
        
        Component[] componentesPantalla=getContentPane().getComponents();
        getContentPane().removeAll();
        
        getContentPane().setLayout(new BorderLayout());
        
         JXTaskPaneContainer panelVertical = new JXTaskPaneContainer();
         panelVertical.setBackground(new Color(99, 130, 191));
         

        this.mapPanelesSecundariosBuild=new HashMap<String,JXTaskPane>(); 
        for (Map.Entry<String, List<Component>> entry : mapComponentesLaterales.entrySet()) 
        {
            String tituloPanel = entry.getKey();
            List<Component> componentes = entry.getValue();
            
            JXTaskPane taskpanel = new JXTaskPane();
            taskpanel.setTitle(tituloPanel);
            taskpanel.setCollapsed(!mapComponentesLateralesVisibles.get(tituloPanel));
                        
            //LLenar con todos los componentes enviados
            for (Component componente : componentes) {
                
                //Alinear el contenido
                if(componente instanceof JButton)
                {
                    ((JButton)componente).setHorizontalAlignment(SwingConstants.LEFT);
                }
                
                taskpanel.add(componente);
                this.mapPanelesSecundariosBuild.put(tituloPanel,taskpanel);
                mapPanelesSecundariosBuild.put(tituloPanel,taskpanel);
                //panelVerticalAgrupador.add(componente);
            }
            
            panelVertical.add(taskpanel);            
            
        }

        //Posiciono el panel vertical en la parte derecha de los formularios        
        getContentPane().add(panelVertical,BorderLayout.LINE_START);
        
        
        //Ubico todos los demas componentes en la parte central
        for (Component componente : componentesPantalla) 
        {
            getContentPane().add(componente,BorderLayout.CENTER);
        }
        
        
    }
    
    
    public JXTaskPane buscarPanelCategoriaLateral(String tituloCategoria)
    {
        if(mapPanelesSecundariosBuild!=null)
        {
            for (Map.Entry<String, JXTaskPane> entry : mapPanelesSecundariosBuild.entrySet()) {
                String titulo = entry.getKey();
                JXTaskPane jxTaskPane = entry.getValue();
                
                if(titulo.equals(tituloCategoria))
                {
                    return jxTaskPane;
                }
            }
        }
        
        return null;
    }
    
    private void buscarComponentesSecundarios()
    {
        this.mapComponentesLaterales=new HashMap<String,List<Component>>();
        this.mapComponentesLateralesVisibles=new HashMap<String,Boolean>();
        
        Class classVentana=this.getClass();
        Method[] metodos=classVentana.getMethods();
        
        for (Method metodo : metodos) {
            ComponenteSecundarioAnotacion anotacion=metodo.getAnnotation(ComponenteSecundarioAnotacion.class);
            //System.out.println(metodo.getName());
            if(anotacion!=null)
            {
                try {
                    String nombreCategoria=anotacion.nombreCategoria();
                    boolean visible= anotacion.visible();
                    if(metodo.getName().indexOf("get")!=0)
                    {
                        continue;
                    }
                    
                    Component componente=(Component) metodo.invoke(this);
                    agregarComponenteLateral(nombreCategoria,componente);
                    
                    this.mapComponentesLateralesVisibles.put(nombreCategoria,visible);
                    
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GeneralPanelInterface.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GeneralPanelInterface.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(GeneralPanelInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void agregarBindingComponent(ComponentBindingAbstract bindingAbstract)
    {
        this.bindingComponentList.add(bindingAbstract);
    }
    
    /**
     * Actualizar solo valores de los componentes vinculados
     */
    public void actualizarBindingCompontValues()
    {
        actualizarBindingComponent(false,true);
    }
    
    public void actualizarBindingComponent(Boolean get,Boolean set)
    {
        for (ComponentBindingAbstract componentBindingAbstract : this.bindingComponentList) {
            componentBindingAbstract.ejecutar(get, set);
        }
    }
    
    
    
    /**
     * Evento que se activa cuando se realiza al cambio de estado en el formulario
     */
    public void eventoCambiarEstado()
    {
        //TODO: solo creo un metodo vacio para sobreescribir y no hacer una interfaz que me obligue a modificar a toditas las pantallas
    }

    
    public EstadoFormularioEnum getEstadoFormularioEnum()
    {
        return EstadoFormularioEnum.getEnum(estadoFormulario);
    }
    
    @Override
    public String toString() {
        VentanaEnum ventanaEnum=VentanaEnum.getByClass(this.getClass());
        if(ventanaEnum!=null)
        {
            return ventanaEnum.getNombre();
        }
        return "";
    }
    
    public class PanelSecundario
    {
        public boolean visible;
        public List<Component> componentes;
        
    }
    
    
    public enum EstadoFormularioEnum
    {
        GRABAR(ESTADO_GRABAR),
        EDITAR(ESTADO_EDITAR);
        
        private String letra;
        
        private  EstadoFormularioEnum(String letra)
        {
            this.letra=letra;
        }

        public String getLetra() {
            return letra;
        }
        
        public static EstadoFormularioEnum getEnum(String letra)
        {
            for (EstadoFormularioEnum enumerador : EstadoFormularioEnum.values()) {
                if(enumerador.getLetra().equals(letra))
                {
                    return enumerador;
                }
            }
            return null;
        }
                
    }
    
    
    
}
