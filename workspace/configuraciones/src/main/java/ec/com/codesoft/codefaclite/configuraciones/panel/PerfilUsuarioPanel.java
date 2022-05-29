/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.codesoft.codefaclite.configuraciones.panel;

import ec.com.codesoft.codefaclite.controlador.aplicacion.ControladorCodefacInterface;
import ec.com.codesoft.codefaclite.corecodefaclite.util.LimpiarAnotacion;
import ec.com.codesoft.codefaclite.corecodefaclite.validation.ValidacionCodefacAnotacion;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.PuntoEmisionUsuario;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.CajaPermiso;
import ec.com.codesoft.codefaclite.servidorinterfaz.entity.pos.TurnoAsignado;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.EnumSiNo;
import ec.com.codesoft.codefaclite.servidorinterfaz.enumerados.GeneralEnumEstado;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Carlos
 */
public abstract class PerfilUsuarioPanel extends ControladorCodefacInterface {

    /**
     * Creates new form PerfilUsuarioPanel
     */
    public PerfilUsuarioPanel() {        
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

        jLabel7 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cmbEstado = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListPerfiles = new javax.swing.JList<>();
        jLabel3 = new javax.swing.JLabel();
        btnAgregarPerfil = new javax.swing.JButton();
        btnQuitarPerfil = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtClave = new javax.swing.JPasswordField();
        txtClaveRepetir = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        lblBlanco = new javax.swing.JLabel();
        lblClaveAnterior = new javax.swing.JLabel();
        txtClaveAnterior = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        txtEmpleado = new javax.swing.JTextField();
        btnBuscarEmpleado = new javax.swing.JButton();
        lblEspacioBlanco1 = new javax.swing.JLabel();
        lblEspacioBlanco2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtParametrosComprobantesElectronicos = new javax.swing.JTextField();
        lblEspacio1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jComboBoxFiltrarFacturas = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btnAgregarPuntoEmision = new javax.swing.JButton();
        btnQuitarPuntoEmision = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListPuntoEmision = new javax.swing.JList<>();
        lblEspacio3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListCajaPermiso = new javax.swing.JList<>();
        btnAgregarCaja = new javax.swing.JButton();
        btnQuitarCaja = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnAgregarTurno = new javax.swing.JButton();
        btnQuitarTurno = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListTurnosCaja = new javax.swing.JList<>();

        jLabel7.setText("jLabel7");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Usuario y Perfiles");

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Usuario:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel1, gridBagConstraints);

        txtUsuario.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtUsuario, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Estado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(cmbEstado, gridBagConstraints);

        jScrollPane1.setViewportView(jListPerfiles);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Perfiles:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel3, gridBagConstraints);

        btnAgregarPerfil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/add2.png"))); // NOI18N
        btnAgregarPerfil.setToolTipText("Agregar un perfil al usuario");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(btnAgregarPerfil, gridBagConstraints);

        btnQuitarPerfil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/cerrar-ico.png"))); // NOI18N
        btnQuitarPerfil.setToolTipText("Quitar el peril");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(btnQuitarPerfil, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Clave:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Clave Repetir:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel5, gridBagConstraints);

        txtClave.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtClave, gridBagConstraints);

        txtClaveRepetir.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtClaveRepetir, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.4;
        jPanel1.add(jLabel6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        jPanel1.add(lblBlanco, gridBagConstraints);

        lblClaveAnterior.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblClaveAnterior.setText("Clave Anterior:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblClaveAnterior, gridBagConstraints);

        txtClaveAnterior.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtClaveAnterior, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("Empleado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jLabel8, gridBagConstraints);

        txtEmpleado.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtEmpleado, gridBagConstraints);

        btnBuscarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/add_user.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(btnBuscarEmpleado, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 2.0;
        jPanel1.add(lblEspacioBlanco1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(lblEspacioBlanco2, gridBagConstraints);

        jTabbedPane1.addTab("Usuario", jPanel1);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText("Parametros Comprobantes Electrónicos:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel10, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtParametrosComprobantesElectronicos, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel3.add(lblEspacio1, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setText("Filtrar facturas:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel11, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jComboBoxFiltrarFacturas, gridBagConstraints);

        jTabbedPane1.addTab("Datos Adicionales", jPanel3);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText("Puntos Emisión:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel9, gridBagConstraints);

        btnAgregarPuntoEmision.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/add2.png"))); // NOI18N
        btnAgregarPuntoEmision.setToolTipText("Agregar un perfil al usuario");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnAgregarPuntoEmision, gridBagConstraints);

        btnQuitarPuntoEmision.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/cerrar-ico.png"))); // NOI18N
        btnQuitarPuntoEmision.setToolTipText("Quitar el peril");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(btnQuitarPuntoEmision, gridBagConstraints);

        jScrollPane2.setViewportView(jListPuntoEmision);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jScrollPane2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.3;
        gridBagConstraints.weighty = 0.1;
        jPanel2.add(lblEspacio3, gridBagConstraints);

        jTabbedPane1.addTab("Puntos de Emisión Permisos", jPanel2);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel12.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel12.setText("Caja:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel12, gridBagConstraints);

        jListCajaPermiso.setMinimumSize(new java.awt.Dimension(100, 80));
        jListCajaPermiso.setPreferredSize(new java.awt.Dimension(100, 80));
        jScrollPane3.setViewportView(jListCajaPermiso);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jScrollPane3, gridBagConstraints);

        btnAgregarCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/add2.png"))); // NOI18N
        btnAgregarCaja.setToolTipText("Agregar un perfil al usuario");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(btnAgregarCaja, gridBagConstraints);

        btnQuitarCaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/cerrar-ico.png"))); // NOI18N
        btnQuitarCaja.setToolTipText("Quitar el peril");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(btnQuitarCaja, gridBagConstraints);

        jLabel13.setText("             ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        jPanel4.add(jLabel13, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel14.setText("Turno:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel14, gridBagConstraints);

        btnAgregarTurno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/add2.png"))); // NOI18N
        btnAgregarTurno.setToolTipText("Agregar un perfil al usuario");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(btnAgregarTurno, gridBagConstraints);

        btnQuitarTurno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/iconos/pequenos/cerrar-ico.png"))); // NOI18N
        btnQuitarTurno.setToolTipText("Quitar el peril");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(btnQuitarTurno, gridBagConstraints);

        jListTurnosCaja.setMinimumSize(new java.awt.Dimension(100, 80));
        jListTurnosCaja.setPreferredSize(new java.awt.Dimension(100, 80));
        jScrollPane4.setViewportView(jListTurnosCaja);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jScrollPane4, gridBagConstraints);

        jTabbedPane1.addTab("Caja", jPanel4);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCaja;
    private javax.swing.JButton btnAgregarPerfil;
    private javax.swing.JButton btnAgregarPuntoEmision;
    private javax.swing.JButton btnAgregarTurno;
    private javax.swing.JButton btnBuscarEmpleado;
    private javax.swing.JButton btnQuitarCaja;
    private javax.swing.JButton btnQuitarPerfil;
    private javax.swing.JButton btnQuitarPuntoEmision;
    private javax.swing.JButton btnQuitarTurno;
    private javax.swing.JComboBox<GeneralEnumEstado> cmbEstado;
    private javax.swing.JComboBox<EnumSiNo> jComboBoxFiltrarFacturas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<CajaPermiso> jListCajaPermiso;
    private javax.swing.JList<String> jListPerfiles;
    private javax.swing.JList<PuntoEmisionUsuario> jListPuntoEmision;
    private javax.swing.JList<TurnoAsignado> jListTurnosCaja;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblBlanco;
    private javax.swing.JLabel lblClaveAnterior;
    private javax.swing.JLabel lblEspacio1;
    private javax.swing.JLabel lblEspacio3;
    private javax.swing.JLabel lblEspacioBlanco1;
    private javax.swing.JLabel lblEspacioBlanco2;
    private javax.swing.JPasswordField txtClave;
    private javax.swing.JPasswordField txtClaveAnterior;
    private javax.swing.JPasswordField txtClaveRepetir;
    private javax.swing.JTextField txtEmpleado;
    private javax.swing.JTextField txtParametrosComprobantesElectronicos;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnAgregarPerfil() {
        return btnAgregarPerfil;
    }

    public void setBtnAgregarPerfil(JButton btnAgregarPerfil) {
        this.btnAgregarPerfil = btnAgregarPerfil;
    }

    public JButton getBtnQuitarPerfil() {
        return btnQuitarPerfil;
    }

    public void setBtnQuitarPerfil(JButton btnQuitarPerfil) {
        this.btnQuitarPerfil = btnQuitarPerfil;
    }

    public JComboBox<GeneralEnumEstado> getCmbEstado() {
        return cmbEstado;
    }

    public void setCmbEstado(JComboBox<GeneralEnumEstado> cmbEstado) {
        this.cmbEstado = cmbEstado;
    }

    

    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido=false ,min=0,nombre = "Clave")
    public JPasswordField getTxtClave() {
        return txtClave;
    }

    public void setTxtClave(JPasswordField txtClave) {
        this.txtClave = txtClave;
    }

    @LimpiarAnotacion
    //@ValidacionCodefacAnotacion(requerido = true, min = 0, nombre = "Clave")
    public JPasswordField getTxtClaveRepetir() {
        return txtClaveRepetir;
    }

    public void setTxtClaveRepetir(JPasswordField txtClaveRepetir) {
        this.txtClaveRepetir = txtClaveRepetir;
    }

    @LimpiarAnotacion
    @ValidacionCodefacAnotacion(requerido=true ,min=0,nombre = "Usuario")
    public JTextField getTxtUsuario() {
        return txtUsuario;
    }

    public void setTxtUsuario(JTextField txtUsuario) {
        this.txtUsuario = txtUsuario;
    }

    @LimpiarAnotacion
    public JPasswordField getTxtClaveAnterior() {
        return txtClaveAnterior;
    }

    public void setTxtClaveAnterior(JPasswordField txtClaveAnterior) {
        this.txtClaveAnterior = txtClaveAnterior;
    }

    public JLabel getLblClaveAnterior() {
        return lblClaveAnterior;
    }

    public void setLblClaveAnterior(JLabel lblClaveAnterior) {
        this.lblClaveAnterior = lblClaveAnterior;
    }
    
    
    

    public JList<String> getjListPerfiles() {
        return jListPerfiles;
    }

    public void setjListPerfiles(JList<String> jListPerfiles) {
        this.jListPerfiles = jListPerfiles;
    }

    public JTextField getTxtEmpleado() {
        return txtEmpleado;
    }

    public void setTxtEmpleado(JTextField txtEmpleado) {
        this.txtEmpleado = txtEmpleado;
    }

    public JButton getBtnBuscarEmpleado() {
        return btnBuscarEmpleado;
    }

    public void setBtnBuscarEmpleado(JButton btnBuscarEmpleado) {
        this.btnBuscarEmpleado = btnBuscarEmpleado;
    }

    public JButton getBtnAgregarPuntoEmision() {
        return btnAgregarPuntoEmision;
    }

    public void setBtnAgregarPuntoEmision(JButton btnAgregarPuntoEmision) {
        this.btnAgregarPuntoEmision = btnAgregarPuntoEmision;
    }

    public JButton getBtnQuitarPuntoEmision() {
        return btnQuitarPuntoEmision;
    }

    public void setBtnQuitarPuntoEmision(JButton btnQuitarPuntoEmision) {
        this.btnQuitarPuntoEmision = btnQuitarPuntoEmision;
    }

    public JList<PuntoEmisionUsuario> getjListPuntoEmision() {
        return jListPuntoEmision;
    }

    public void setjListPuntoEmision(JList<PuntoEmisionUsuario> jListPuntoEmision) {
        this.jListPuntoEmision = jListPuntoEmision;
    }

    public JTextField getTxtParametrosComprobantesElectronicos() {
        return txtParametrosComprobantesElectronicos;
    }

    public void setTxtParametrosComprobantesElectronicos(JTextField txtParametrosComprobantesElectronicos) {
        this.txtParametrosComprobantesElectronicos = txtParametrosComprobantesElectronicos;
    }

    public JComboBox<EnumSiNo> getjComboBoxFiltrarFacturas() {
        return jComboBoxFiltrarFacturas;
    }

    public void setjComboBoxFiltrarFacturas(JComboBox<EnumSiNo> jComboBoxFiltrarFacturas) {
        this.jComboBoxFiltrarFacturas = jComboBoxFiltrarFacturas;
    }

    public JButton getBtnAgregarCaja() {
        return btnAgregarCaja;
    }

    public void setBtnAgregarCaja(JButton btnAgregarCaja) {
        this.btnAgregarCaja = btnAgregarCaja;
    }

    public JButton getBtnQuitarCaja() {
        return btnQuitarCaja;
    }

    public void setBtnQuitarCaja(JButton btnQuitarCaja) {
        this.btnQuitarCaja = btnQuitarCaja;
    }

    public JList<CajaPermiso> getjListCajaPermiso() {
        return jListCajaPermiso;
    }

    public void setjListCajaPermiso(JList<CajaPermiso> jListCajaPermiso) {
        this.jListCajaPermiso = jListCajaPermiso;
    }

    public JButton getBtnAgregarTurno() {
        return btnAgregarTurno;
    }

    public void setBtnAgregarTurno(JButton btnAgregarTurno) {
        this.btnAgregarTurno = btnAgregarTurno;
    }

    public JButton getBtnQuitarTurno() {
        return btnQuitarTurno;
    }

    public void setBtnQuitarTurno(JButton btnQuitarTurno) {
        this.btnQuitarTurno = btnQuitarTurno;
    }

    public JList<TurnoAsignado> getjListTurnosCaja() {
        return jListTurnosCaja;
    }

    public void setjListTurnosCaja(JList<TurnoAsignado> jListTurnosCaja) {
        this.jListTurnosCaja = jListTurnosCaja;
    }    
}