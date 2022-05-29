/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.dialog;

import ec.com.codesoft.codefaclite.corecodefaclite.panel.DialogoBuscadorForm;
import ec.com.codesoft.codefaclite.servidorinterfaz.controller.ServiceFactory;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.ParametroCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoBusquedaEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.FuncionesSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.info.ParametrosSistemaCodefac;
import ec.com.codesoft.codefaclite.servidorinterfaz.mensajes.CodefacMsj;
import ec.com.codesoft.codefaclite.servidorinterfaz.util.ParametroUtilidades;
import ec.com.codesoft.codefaclite.utilidades.swing.UtilidadesComboBox;
import ec.com.codesoft.codefaclite.utilidades.varios.UtilidadesDerby;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Carlos
 */
public class BuscarDialogoModel extends DialogoBuscadorForm
{
    private static final String ALIAS_BUSQUEDA="?1000";
    /**
     * Cantidad maxima de filas que va a cargar la tabla 
     */
    private static final int CANTIDAD_FILAS=15;
    /**
     * Setear la pagina actual de la consulta
     */
    private int paginaActual;
    private int tamanioConsulta;
    
    private int cantidadPaginas;
    
    private DefaultTableModel modeloTablaBuscar;
    private InterfaceModelFind model;
    private Object resultado;
    private List<Object> listaResultados;
    
    /**
     * Variable que me permite configurar si la variable de busqueda quiero que normalize sin acentos o buscar de forma identica a lo escrito
     */
    private boolean normalizarTextoBusqueda;

    /*
    public BuscarDialogoModel(DefaultTableModel modeloTablaBuscar) 
    {
        super(null,true);
        this.modeloTablaBuscar = modeloTablaBuscar;
        this.paginaActual=0;
    }*/
    
    public BuscarDialogoModel(InterfaceModelFind model) 
    {
        super(null,true);
        this.model=model;
        iniciarValores();
        initListener();
        //crearConsulta("");
        ejecutarConsulta();
        //cargarDatos(listaResultados);
        establecerPropiedadesIniciales();        
        normalizarTextoBusqueda=false;
    }
    
    private void iniciarValores()
    {
        UtilidadesComboBox.llenarComboBox(getCmbTipoBusqueda(),TipoBusquedaEnum.values());
    }
    
    /**
     * Consulta que obtiene datos segun los datos seteados
     */
    private void consultaSecundaria()
    {
        try {
            String filtro=getTxtBuscar().getText().toLowerCase();
            filtro=(normalizarTextoBusqueda)?UtilidadesDerby.normalizarTextoDerby(filtro):filtro;
            
            String filtroConsuta=filtro;
            
            TipoBusquedaEnum tipoBusquedaEnum=(TipoBusquedaEnum) getCmbTipoBusqueda().getSelectedItem();
            if(tipoBusquedaEnum.equals(TipoBusquedaEnum.EXACTO))
            {
                //Quito los filtro de porcentaje para hacer la busqueda exacta
                filtroConsuta=filtroConsuta.replace("%","");
            } 
            else if(tipoBusquedaEnum.equals(TipoBusquedaEnum.COINCIDENCIA))
            {            
                if(!filtro.contains("%"))
                {
                    filtroConsuta="%"+filtroConsuta+"%";
                }
            }
            
            QueryDialog queryDialog=this.model.getConsulta(filtroConsuta);
            //queryDialog.agregarParametro(1000,"%"+filtro+"%");
            
            int limiteInferior=CANTIDAD_FILAS*(paginaActual-1);
            int limiteSuperior=CANTIDAD_FILAS*(paginaActual);
            //Limpiar los resutados anteriores
            if(listaResultados!=null)
                listaResultados.clear();
            
            convertirMinusculasParametros(queryDialog);
            listaResultados=ServiceFactory.getFactory().getUtilidadesServiceIf().consultaGeneralDialogos(queryDialog.query,queryDialog.getParametros(),limiteInferior,CANTIDAD_FILAS);
            cargarDatos(listaResultados);
            
            setearBotonesSiguienteAtras();
            imprimirTexto();
        } catch (RemoteException ex) {
            Logger.getLogger(BuscarDialogoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void imprimirTexto()
    {
        String piePagina="Resultado "+paginaActual+" - "+cantidadPaginas+ " de "+tamanioConsulta+" registros.";
        getLblPiePagina().setText(piePagina);
    }
    
    private void setearBotonesSiguienteAtras()
    {
        
        if(paginaActual==1)
        {
            getBtnAtras().setEnabled(false);
            getBtnPrimero().setEnabled(false);
        }
        else
        {
            getBtnAtras().setEnabled(true);
            getBtnPrimero().setEnabled(true);
        }
        
        if(paginaActual==cantidadPaginas)
        {
            getBtnSiguiente().setEnabled(false);
            getBtnUltimo().setEnabled(false);
        }
        else
        {
            getBtnSiguiente().setEnabled(true);
            getBtnUltimo().setEnabled(true);
        }
        
        if(cantidadPaginas==0)
        {
            getBtnAtras().setEnabled(false);
            getBtnSiguiente().setEnabled(false);
            getBtnPrimero().setEnabled(false);
            getBtnUltimo().setEnabled(false);
        }
    }
    
    private int obtenerTamanioConsulta()
    {
        try {
            String filtro=getTxtBuscar().getText().toLowerCase();
            filtro=(normalizarTextoBusqueda)?UtilidadesDerby.normalizarTextoDerby(filtro):filtro;
            
            String filtroConsuta=filtro;
            
            TipoBusquedaEnum tipoBusquedaEnum=(TipoBusquedaEnum) getCmbTipoBusqueda().getSelectedItem();
            if(tipoBusquedaEnum.equals(TipoBusquedaEnum.EXACTO))
            {
                //Si la busqueda es exacta quito todo los porcentajes
                filtroConsuta=filtroConsuta.replace("%","");
                //Tambien cambio
                        
            }
            else if(tipoBusquedaEnum.equals(TipoBusquedaEnum.COINCIDENCIA))
            {            
                //Si el filtro ya contiene el porcentaje entonces desactivo la busqueda automatica                
                if(!filtro.contains("%"))
                {
                    filtroConsuta="%"+filtroConsuta+"%";
                }
            }
            QueryDialog queryDialog=this.model.getConsulta(filtroConsuta);
            
            //queryDialog.agregarParametro(1000,"%"+filtro+"%");
            String query=queryDialog.query;
            query=query.toLowerCase();
            int primerCorte=query.indexOf("select")+"select".length();
            int segundoCorte=query.indexOf("from");
            String queryModificado=queryDialog.query.substring(0,primerCorte)+" count(1) "+queryDialog.query.substring(segundoCorte);
            //Eliminar las columnas de ordenar porque no se pueden ejecutar en conjunto con el comando count(1)
            if(queryModificado.toLowerCase().indexOf("order by")>=0)
                queryModificado=queryModificado.substring(0,queryModificado.toLowerCase().indexOf("order by")); //TODO: Analizar como verificar para otros casos que tengan mas espacios entre order y by
            
            System.out.println(queryModificado);            
            convertirMinusculasParametros(queryDialog);
            Long tamanio=ServiceFactory.getFactory().getUtilidadesServiceIf().consultaTamanioGeneralDialogos(queryModificado, queryDialog.getParametros());
            
            return (tamanio!=null)?tamanio.intValue():0;
        } catch (RemoteException ex) {
            Logger.getLogger(BuscarDialogoModel.class.getName()).log(Level.SEVERE, null, ex);
            FuncionesSistemaCodefac.servidorConexionPerdida();
        }
        return 0;
    }
    
    /**
     * Consulta inicial que establece los parametros como cantidad de datos, numero de paginas
     * y otras configuraciones para costruir el dialogo
     */
    public void ejecutarConsulta()
    {
        this.tamanioConsulta=obtenerTamanioConsulta();
        this.paginaActual=1;        
        double paginas=(double)tamanioConsulta/(double)CANTIDAD_FILAS;
        this.cantidadPaginas=(int)(new BigDecimal(paginas).setScale(0,BigDecimal.ROUND_UP).intValue());

        //Enviar un mensaje si no existe datos para no abrir el dialogo sin necesidad

        consultaSecundaria();
        
    }
    
    private void crearModeloTabla()
    {
        Vector<ColumnaDialogo> columnas=this.model.getColumnas();
        Vector<String> titulos=new Vector<String>();
        Vector<Double> tamanios=new Vector<Double>();
        for (ColumnaDialogo columna : columnas) {
            titulos.add(columna.getNombre());
            tamanios.add(columna.getPorcentaje());        }

        this.modeloTablaBuscar=new DefaultTableModel(titulos,0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };
       
        this.getTblTabla().setModel(this.modeloTablaBuscar);
        //Establecer el ancho de las tablas
        TableColumnModel columnasTabla=this.getTblTabla().getColumnModel();
        
        for (int i = 0; i < columnasTabla.getColumnCount(); i++) {
            double tamanio=(double)tamanios.get(i)*(double)getTblTabla().getWidth();
            columnasTabla.getColumn(i).setPreferredWidth((int)tamanio);
        }
        
        
    }
    
    private void cargarDatos(List<Object> datos)
    {
        crearModeloTabla();
        
        for (Object object : datos) 
        {
            Vector<String> dato=new Vector<String>();
            this.model.agregarObjeto(object, dato);
            this.modeloTablaBuscar.addRow(dato);
        }
        this.getTblTabla().setModel(this.modeloTablaBuscar);
    }
    
    public Object getResultado()
    {
        if(resultado!=null)
        {
            return resultado;
        }
        else
        {
            return null;
        }
    }
       
    
    private void initListener()
    {        
        //Todo agregado artificio para setear el focus porque no funciona con un tema
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {}

            @Override
            public void componentMoved(ComponentEvent e) {}

            @Override
            public void componentShown(ComponentEvent e) {
                getTxtBuscar().requestFocus();
            }

            @Override
            public void componentHidden(ComponentEvent e) {}
        });
        
        getTxtBuscar().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if(getTblTabla().getRowCount()>0)
                    {
                        getTblTabla().setRowSelectionInterval(0,0);//Seleccionar la primera fila de la tabla
                        getTblTabla().requestFocus();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    Boolean filtroRapido = ParametroUtilidades.compararSinEmpresa(ParametroCodefac.FILTRO_RAPIDO_BUSQUEDA, EnumSiNo.SI);
                    
                    if (filtroRapido) 
                    {
                        ejecutarConsulta();                        
                    }
                } catch (RemoteException ex) {
                    Logger.getLogger(BuscarDialogoModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        getBtnUltimo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paginaActual=cantidadPaginas;
                consultaSecundaria();
            }
        });
        
        getBtnPrimero().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paginaActual=1;
                consultaSecundaria();                
            }
        });
        
        getBtnSiguiente().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paginaActual++;
                consultaSecundaria();
            }
        });
        
        getBtnAtras().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paginaActual--;
                consultaSecundaria();
            }
        });
        
        getBtnAceptar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila=getTblTabla().getSelectedRow();
                resultado=listaResultados.get(fila);
                dispose();
            }
        });
        
        getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultado=null;
                dispose();
            }
        });
        
        
        getBtnFiltrar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarConsulta();
                
            }
        });
        
        getTxtBuscar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                
                try {
                    Boolean filtroRapido=ParametroUtilidades.compararSinEmpresa(ParametroCodefac.FILTRO_RAPIDO_BUSQUEDA,EnumSiNo.NO);
                    
                    if(filtroRapido || e.getKeyCode()==KeyEvent.VK_ENTER)
                    {
                        ejecutarConsulta();
                    }
                    //else
                    //{
                    //    ejecutarConsulta();
                    
                    
                } catch (RemoteException ex) {
                    Logger.getLogger(BuscarDialogoModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
}); 
        
        /**
         * Agregar listener para que devuelva un resultado cuando el usuario ejecute doble click
         */
        getTblTabla().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table =(JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2) {
                    int fila=getTblTabla().getSelectedRow();
                    resultado=listaResultados.get(fila);
                    dispose();
                    // your valueChanged overridden method 
                }
            }
        });
        
        getTblTabla().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                
                //Solo ejecutar si presiona el evento enter
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {                   
                   int filaSeleccionada=getTblTabla().getSelectedRow();
                   if(filaSeleccionada>=0)   //
                   {
                       resultado=listaResultados.get(filaSeleccionada);
                       dispose();
                   }
                }
                
                if(e.getKeyCode()==KeyEvent.VK_UP)
                {
                    int filaSeleccionada=getTblTabla().getSelectedRow();
                    if(filaSeleccionada==0)
                    {
                        getTxtBuscar().requestFocus();
                    }
                }
                
                if(e.getKeyCode()==KeyEvent.VK_LEFT)
                {
                   getBtnAtras().doClick();
                }
                
                if(e.getKeyCode()==KeyEvent.VK_RIGHT)
                {
                   getBtnSiguiente().doClick();
                }
                
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }
    

    private void establecerPropiedadesIniciales() {
        //Centrar el dialogo
        setPreferredSize(new Dimension(800,410));
        setSize(800, 410);
        setLocationRelativeTo(null);

    }
    
    public void setearTextoBusqueda(String texto)
    {
        getTxtBuscar().setText(texto.toLowerCase());
    }

    public long getTamanioConsulta() {
        return tamanioConsulta;
    }
    
    public Object obtenerResultadoLista(int indice)
    {
        return this.listaResultados.get(indice);
    }
    
    private void convertirMinusculasParametros(QueryDialog queryDialog)
    {
        for (Map.Entry<Integer, Object> en : queryDialog.getParametros().entrySet()) {
            Integer numero = en.getKey();
            Object valor = en.getValue();
            
            //Solo aplicar esta opcion cuando el tipo de dato enviado es String
            if(valor!=null && valor.getClass().equals(String.class))
            {
                queryDialog.getParametros().put(numero, valor); //Remplazo el antiguo valor por el nuevo con minusculas
            }
            
        }

    }

    public boolean getNormalizarTextoBusqueda() {
        return normalizarTextoBusqueda;
    }

    public void setNormalizarTextoBusqueda(boolean normalizarTextoBusqueda) {
        this.normalizarTextoBusqueda = normalizarTextoBusqueda;
    }
    
    

    
    
}
