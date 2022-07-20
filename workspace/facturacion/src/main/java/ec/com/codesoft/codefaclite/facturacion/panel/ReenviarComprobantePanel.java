/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.facturacion.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import javax.swing.JTable;

/**
 *
 * @auhor
 */
public abstract class ReenviarComprobantePanel extends ControladorCodefacInterface {

    /**
     * Creates new form ReenviarComprobantePanel
     */
    public ReenviarComprobantePanel() {
        initComponents();
        setTitle(VentanaEnum.REENVIO_COMPROBANTES.getNombre());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblComprobantes = new javax.swing.JTable();

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("jLabel1");
        jPanel1.add(jLabel1, new java.awt.GridBagConstraints());

        jScrollPane1.setViewportView(tblComprobantes);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(jScrollPane1, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblComprobantes;
    // End of variables declaration//GEN-END:variables

    public JTable getTblComprobantes() {
        return tblComprobantes;
    }

    public void setTblComprobantes(JTable tblComprobantes) {
        this.tblComprobantes = tblComprobantes;
    }

    
}
