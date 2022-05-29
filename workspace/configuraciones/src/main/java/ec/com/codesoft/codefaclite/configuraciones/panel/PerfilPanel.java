/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.util.LimpiarAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ValidacionCodefacAnotacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.CategoriaMenuEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.ModuloCodefacEnum;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.VentanaEnum;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public abstract class PerfilPanel extends ControladorCodefacInterface {

    /**
     * Creates new form PerfilPanel
     */
    public PerfilPanel() {
        initComponents();
        //ModuloCodefacEnum modulo;
        //CategoriaMenuEnum modulo;
                
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

        jLabel1 = new javax.swing.JLabel();
        txtNombrePerfil = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDatos = new javax.swing.JTable();
        btnQuitar = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        cmbModulo = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cmbCategoria = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cmbVentana = new javax.swing.JComboBox<>();
        chkGrabar = new javax.swing.JRadioButton();
        chkEditar = new javax.swing.JRadioButton();
        chkBuscar = new javax.swing.JRadioButton();
        chkImprimir = new javax.swing.JRadioButton();
        chkEliminar = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        lblEspacioBlanco = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Perfiles");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Nombre:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 341;
        gridBagConstraints.weightx = 0.1;
        getContentPane().add(txtNombrePerfil, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Modulo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jLabel3, gridBagConstraints);

        tblDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tblDatos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 553;
        gridBagConstraints.ipady = 159;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jScrollPane3, gridBagConstraints);

        btnQuitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/cerrar-ico.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        getContentPane().add(btnQuitar, gridBagConstraints);

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/add2.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        getContentPane().add(btnAgregar, gridBagConstraints);

        cmbModulo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 86;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(cmbModulo, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText("Categoria:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jLabel6, gridBagConstraints);

        cmbCategoria.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 86;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(cmbCategoria, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Ventana:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        getContentPane().add(jLabel4, gridBagConstraints);

        cmbVentana.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 219;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        getContentPane().add(cmbVentana, gridBagConstraints);

        chkGrabar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkGrabar.setText("Grabar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 7;
        getContentPane().add(chkGrabar, gridBagConstraints);

        chkEditar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkEditar.setText("Editar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 7;
        getContentPane().add(chkEditar, gridBagConstraints);

        chkBuscar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkBuscar.setText("Buscar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 13;
        gridBagConstraints.gridy = 7;
        getContentPane().add(chkBuscar, gridBagConstraints);

        chkImprimir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkImprimir.setText("Imprimir");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 7;
        getContentPane().add(chkImprimir, gridBagConstraints);

        chkEliminar.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        chkEliminar.setText("Eliminar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 7;
        getContentPane().add(chkEliminar, gridBagConstraints);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new java.awt.GridBagConstraints());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.weightx = 0.4;
        getContentPane().add(lblEspacioBlanco, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JRadioButton chkBuscar;
    private javax.swing.JRadioButton chkEditar;
    private javax.swing.JRadioButton chkEliminar;
    private javax.swing.JRadioButton chkGrabar;
    private javax.swing.JRadioButton chkImprimir;
    private javax.swing.JComboBox<CategoriaMenuEnum> cmbCategoria;
    private javax.swing.JComboBox<ModuloCodefacEnum> cmbModulo;
    private javax.swing.JComboBox<VentanaEnum> cmbVentana;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblEspacioBlanco;
    private javax.swing.JTable tblDatos;
    private javax.swing.JTextField txtNombrePerfil;
    // End of variables declaration//GEN-END:variables

    public JComboBox<CategoriaMenuEnum> getCmbCategoria() {
        return cmbCategoria;
    }

    public void setCmbCategoria(JComboBox<CategoriaMenuEnum> cmbCategoria) {
        this.cmbCategoria = cmbCategoria;
    }

    public JComboBox<ModuloCodefacEnum> getCmbModulo() {
        return cmbModulo;
    }

    public void setCmbModulo(JComboBox<ModuloCodefacEnum> cmbModulo) {
        this.cmbModulo = cmbModulo;
    }

    public JComboBox<VentanaEnum> getCmbVentana() {
        return cmbVentana;
    }

    public void setCmbVentana(JComboBox<VentanaEnum> cmbVentana) {
        this.cmbVentana = cmbVentana;
    }


    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public void setBtnAgregar(JButton btnAgregar) {
        this.btnAgregar = btnAgregar;
    }

    public JButton getBtnQuitar() {
        return btnQuitar;
    }

    public void setBtnQuitar(JButton btnQuitar) {
        this.btnQuitar = btnQuitar;
    }

    public JRadioButton getChkBuscar() {
        return chkBuscar;
    }

    public void setChkBuscar(JRadioButton chkBuscar) {
        this.chkBuscar = chkBuscar;
    }

    public JRadioButton getChkEditar() {
        return chkEditar;
    }

    public void setChkEditar(JRadioButton chkEditar) {
        this.chkEditar = chkEditar;
    }

    public JRadioButton getChkEliminar() {
        return chkEliminar;
    }

    public void setChkEliminar(JRadioButton chkEliminar) {
        this.chkEliminar = chkEliminar;
    }

    public JRadioButton getChkGrabar() {
        return chkGrabar;
    }

    public void setChkGrabar(JRadioButton chkGrabar) {
        this.chkGrabar = chkGrabar;
    }

    public JRadioButton getChkImprimir() {
        return chkImprimir;
    }

    public void setChkImprimir(JRadioButton chkImprimir) {
        this.chkImprimir = chkImprimir;
    }

    public JTable getTblDatos() {
        return tblDatos;
    }

    public void setTblDatos(JTable tblDatos) {
        this.tblDatos = tblDatos;
    }

    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido=true ,min=0,nombre = "Nombre del Perfil")
    public JTextField getTxtNombrePerfil() {
        return txtNombrePerfil;
    }

    public void setTxtNombrePerfil(JTextField txtNombrePerfil) {
        this.txtNombrePerfil = txtNombrePerfil;
    }

    
    

    
    
    
}