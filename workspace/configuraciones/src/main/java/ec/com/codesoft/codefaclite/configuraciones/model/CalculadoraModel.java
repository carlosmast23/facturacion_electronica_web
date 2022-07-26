/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.model;

import ec.com.codesoft.codefaclite.configuraciones.panel.CalculadoraPanel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.BuscarDialogoModel;
import ec.com.codesoft.codefaclite.corecodefaclite.dialog.InterfaceModelFind;
import ec.com.codesoft.codefaclite.corecodefaclite.excepcion.ExcepcionCodefacLite;
import ec.com.codesoft.codefaclite.utilidades.calculos.UtilidadesCalculo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author
 */
public class CalculadoraModel extends CalculadoraPanel{
    String textoCalculadora="";

    public CalculadoraModel() {
        this.cicloVida=false;
        addListenerBotones();
        
    }
    
    private void addListenerBotones() {
        
              
        getBtnIgual().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    String respuesta=UtilidadesCalculo.evaluarExpresion(getJtxtAreaCalculadora().getText());
                    textoCalculadora=respuesta;
                    getJtxtAreaCalculadora().setText(respuesta);
            }
        });
        
        getBtn0().addActionListener(new ListenerBotones("0"));
        getBtn1().addActionListener(new ListenerBotones("1"));
        getBtn2().addActionListener(new ListenerBotones("2"));
        getBtn3().addActionListener(new ListenerBotones("3"));
        getBtn4().addActionListener(new ListenerBotones("4"));
        getBtn5().addActionListener(new ListenerBotones("5"));
        getBtn6().addActionListener(new ListenerBotones("6"));
        getBtn7().addActionListener(new ListenerBotones("7"));
        getBtn8().addActionListener(new ListenerBotones("8"));
        getBtn9().addActionListener(new ListenerBotones("9"));
        
        getBtnSumar().addActionListener(new ListenerBotones("+"));
        getBtnRestar().addActionListener(new ListenerBotones("-"));
        getBtnMultiplicar().addActionListener(new ListenerBotones("*"));
        getBtnDividir().addActionListener(new ListenerBotones("/"));
        getBtnDecimal().addActionListener(new ListenerBotones("."));
        
        
        
        
        
        
        
        this.getJtxtAreaCalculadora().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String respuesta=UtilidadesCalculo.evaluarExpresion(textoCalculadora);
                    textoCalculadora=respuesta;
                }
                else
                {
                    String letra = String.valueOf(e.getKeyChar());
                    textoCalculadora += letra;
                    getJtxtAreaCalculadora().setText(textoCalculadora);
                }
                actulizarTexto();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
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
    
           
    private class ListenerBotones implements ActionListener
    {
        String letra;

        public ListenerBotones(String letra) {
            this.letra = letra;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            agregarTexto(letra);
        }
    
    }
    
    private void agregarTexto(String texto)
    {
        textoCalculadora+=texto;
        actulizarTexto();
    }
    private void actulizarTexto()
    {
        getJtxtAreaCalculadora().setText(textoCalculadora);
    }
    

    
    @Override
    public void grabar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void imprimir() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void buscar() throws ExcepcionCodefacLite {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiar() {
        
    }

    //@Override
    public String getNombre() {
        return "Calculadora";
    }

    @Override
    public String getURLAyuda() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<Integer, Boolean> permisosFormulario() {
        return new HashMap<Integer, Boolean>();
    }

    @Override
    public void iniciar() {
        this.getJtxtAreaCalculadora().requestFocusInWindow();
    }

    @Override
    public void nuevo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
