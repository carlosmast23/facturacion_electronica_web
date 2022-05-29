/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.comprobantes;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Carlos
 */
public class MonitorComprobanteData {
    private static final Color COLOR_BARRA_CARGA=new Color(128, 223, 255); 
    private static final Color COLOR_BARRA_CARGA_FONDO=Color.white; 
            
    private JLabel lblPreimpreso;
    private JProgressBar barraProgreso;
    private JButton btnReporte;
    private JButton btnAbrir;
    private JButton btnCerrar;
    private JasperPrint jasperPrint;
    private Integer posicion;

    public MonitorComprobanteData(Integer posicion) {
        lblPreimpreso = new JLabel("001-002-01232132");
        barraProgreso = new JProgressBar();
        barraProgreso.setForeground(COLOR_BARRA_CARGA);
        barraProgreso.setBackground(COLOR_BARRA_CARGA_FONDO);
        btnReporte = new JButton("R");
        btnAbrir = new JButton("A");
        btnCerrar = new JButton("Xs");
        this.posicion=posicion;
    }

    public JLabel getLblPreimpreso() {
        return lblPreimpreso;
    }

    public void setLblPreimpreso(JLabel lblPreimpreso) {
        this.lblPreimpreso = lblPreimpreso;
    }

    public JProgressBar getBarraProgreso() {
        return barraProgreso;
    }

    public void setBarraProgreso(JProgressBar barraProgreso) {
        this.barraProgreso = barraProgreso;
    }

    public JButton getBtnReporte() {
        return btnReporte;
    }

    public void setBtnReporte(JButton btnReporte) {
        this.btnReporte = btnReporte;
    }

    public JButton getBtnAbrir() {
        return btnAbrir;
    }

    public void setBtnAbrir(JButton btnAbrir) {
        this.btnAbrir = btnAbrir;
    }

    public JButton getBtnCerrar() {
        return btnCerrar;
    }

    public void setBtnCerrar(JButton btnCerrar) {
        this.btnCerrar = btnCerrar;
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

    public void setJasperPrint(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }
    

    
}
