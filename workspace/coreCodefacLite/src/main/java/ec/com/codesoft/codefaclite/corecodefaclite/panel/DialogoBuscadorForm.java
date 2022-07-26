/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.corecodefaclite.panel;

import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.TipoBusquedaEnum;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author
 */
public class DialogoBuscadorForm extends javax.swing.JDialog {

    /**
     * Creates new form DialogoBuscadorForm
     */
    public DialogoBuscadorForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblPiePagina = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnPrimero = new javax.swing.JButton();
        btnAtras = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblTextoBuscar = new javax.swing.JLabel();
        btnFiltrar = new javax.swing.JButton();
        txtBuscar = new javax.swing.JTextField();
        cmbTipoBusqueda = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTabla = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Buscar");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/check-ico.png"))); // NOI18N
        btnAceptar.setText("Aceptar");
        jPanel2.add(btnAceptar);

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/cancel-ico.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        jPanel2.add(btnCancelar);

        jPanel1.add(jPanel2, java.awt.BorderLayout.LINE_END);

        lblPiePagina.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblPiePagina.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPiePagina.setToolTipText("");
        lblPiePagina.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblPiePagina, java.awt.BorderLayout.CENTER);

        btnPrimero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/flecha_primero.png"))); // NOI18N
        btnPrimero.setToolTipText("Inicio");
        jPanel4.add(btnPrimero);

        btnAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/flechaIzq.png"))); // NOI18N
        btnAtras.setToolTipText("Atras");
        jPanel4.add(btnAtras);

        btnSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/flechaDer.png"))); // NOI18N
        btnSiguiente.setToolTipText("Siguiente");
        jPanel4.add(btnSiguiente);

        btnUltimo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/flecha_ultimo.png"))); // NOI18N
        btnUltimo.setToolTipText("Fin");
        jPanel4.add(btnUltimo);

        jPanel1.add(jPanel4, java.awt.BorderLayout.LINE_START);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        lblTextoBuscar.setText("   Ingrese el texto a buscar:   ");
        jPanel3.add(lblTextoBuscar, new java.awt.GridBagConstraints());

        btnFiltrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/find2-ico.png"))); // NOI18N
        btnFiltrar.setText("Filtrar");
        btnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel3.add(btnFiltrar, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel3.add(txtBuscar, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        jPanel3.add(cmbTipoBusqueda, gridBagConstraints);

        getContentPane().add(jPanel3, java.awt.BorderLayout.PAGE_START);

        tblTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        )
        {public boolean isCellEditable(int row, int column){return false;}}
    );
    tblTabla.setUpdateSelectionOnSort(false);
    jScrollPane1.setViewportView(tblTabla);

    getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFiltrarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DialogoBuscadorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogoBuscadorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogoBuscadorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogoBuscadorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogoBuscadorForm dialog = new DialogoBuscadorForm(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnPrimero;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnUltimo;
    private javax.swing.JComboBox<TipoBusquedaEnum> cmbTipoBusqueda;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPiePagina;
    private javax.swing.JLabel lblTextoBuscar;
    private javax.swing.JTable tblTabla;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnAceptar() {
        return btnAceptar;
    }

    public void setBtnAceptar(JButton btnAceptar) {
        this.btnAceptar = btnAceptar;
    }

    public JButton getBtnCancelar() {
        return btnCancelar;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnCancelar = btnCancelar;
    }

    public JLabel getLblPiePagina() {
        return lblPiePagina;
    }

    public void setLblPiePagina(JLabel lblPiePagina) {
        this.lblPiePagina = lblPiePagina;
    }

    public JLabel getLblTextoBuscar() {
        return lblTextoBuscar;
    }

    public void setLblTextoBuscar(JLabel lblTextoBuscar) {
        this.lblTextoBuscar = lblTextoBuscar;
    }

    public JTable getTblTabla() {
        return tblTabla;
    }

    public void setTblTabla(JTable tblTabla) {
        this.tblTabla = tblTabla;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public void setTxtBuscar(JTextField txtBuscar) {
        this.txtBuscar = txtBuscar;
    }

    public JButton getTxtFiltrar() {
        return btnFiltrar;
    }

    public void setTxtFiltrar(JButton txtFiltrar) {
        this.btnFiltrar = txtFiltrar;
    }

    public JButton getBtnFiltrar() {
        return btnFiltrar;
    }

    public void setBtnFiltrar(JButton btnFiltrar) {
        this.btnFiltrar = btnFiltrar;
    }

    public JButton getBtnAtras() {
        return btnAtras;
    }

    public void setBtnAtras(JButton btnAtras) {
        this.btnAtras = btnAtras;
    }

    public JButton getBtnSiguiente() {
        return btnSiguiente;
    }

    public void setBtnSiguiente(JButton btnSiguiente) {
        this.btnSiguiente = btnSiguiente;
    }

    public JButton getBtnPrimero() {
        return btnPrimero;
    }

    public void setBtnPrimero(JButton btnPrimero) {
        this.btnPrimero = btnPrimero;
    }

    public JButton getBtnUltimo() {
        return btnUltimo;
    }

    public void setBtnUltimo(JButton btnUltimo) {
        this.btnUltimo = btnUltimo;
    }

    public JComboBox<TipoBusquedaEnum> getCmbTipoBusqueda() {
        return cmbTipoBusqueda;
    }

    public void setCmbTipoBusqueda(JComboBox<TipoBusquedaEnum> cmbTipoBusqueda) {
        this.cmbTipoBusqueda = cmbTipoBusqueda;
    }
    
    
    

}
