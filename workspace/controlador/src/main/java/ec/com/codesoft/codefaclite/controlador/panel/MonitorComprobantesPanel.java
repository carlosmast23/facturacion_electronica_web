/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.controlador.panel;

import ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.PanelSecundarioAbstract;
import static ec.com.codesoft.codefaclite.controlador.panelessecundariomodel.PanelSecundarioAbstract.PANEL_MONITOR;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author
 */
public abstract class MonitorComprobantesPanel extends PanelSecundarioAbstract{

    /**
     * Creates new form MonitorComprobantesPanel
     */
    public MonitorComprobantesPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        btnLimpiarTodo = new javax.swing.JButton();
        btnQuitarTerminados = new javax.swing.JButton();
        jPanelComponentesCarga = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        btnLimpiarTodo.setText("Quitar Todos");
        btnLimpiarTodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarTodoActionPerformed(evt);
            }
        });
        jPanel2.add(btnLimpiarTodo);

        btnQuitarTerminados.setText("Quitar Terminados");
        jPanel2.add(btnQuitarTerminados);

        add(jPanel2, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout jPanelComponentesCargaLayout = new javax.swing.GroupLayout(jPanelComponentesCarga);
        jPanelComponentesCarga.setLayout(jPanelComponentesCargaLayout);
        jPanelComponentesCargaLayout.setHorizontalGroup(
            jPanelComponentesCargaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );
        jPanelComponentesCargaLayout.setVerticalGroup(
            jPanelComponentesCargaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        add(jPanelComponentesCarga, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimpiarTodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarTodoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLimpiarTodoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpiarTodo;
    private javax.swing.JButton btnQuitarTerminados;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelComponentesCarga;
    // End of variables declaration//GEN-END:variables
        
    
    public JButton getBtnLimpiarTodo() {
        return btnLimpiarTodo;
    }

    public void setBtnLimpiarTodo(JButton btnLimpiarTodo) {
        this.btnLimpiarTodo = btnLimpiarTodo;
    }

    public JPanel getjPanelComponentesCarga() {
        return jPanelComponentesCarga;
    }

    public void setjPanelComponentesCarga(JPanel jPanelComponentesCarga) {
        this.jPanelComponentesCarga = jPanelComponentesCarga;
    }

    
    @Override
    public String getNombrePanel() {
        return PANEL_MONITOR;
    }

    public JButton getBtnQuitarTerminados() {
        return btnQuitarTerminados;
    }

    public void setBtnQuitarTerminados(JButton btnQuitarTerminados) {
        this.btnQuitarTerminados = btnQuitarTerminados;
    }

    
}
